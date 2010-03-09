/*
 * Gap Data
 * Copyright (C) 2009 John Pritchard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package yas.data;

import yas.data.Command.Identifier.* ;

import gap.*;
import gap.data.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.labs.taskqueue.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import javax.servlet.ServletException;

/**
 * Commands process is task driven.
 * 
 * Perform twitter search for target hashtag, execute unseen commands
 *
 * @see Command
 */
public final class CommandServlet
    extends TaskServlet
{

    public CommandServlet() {
        super();
    }


    protected void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
        UpdateQueueCommands();
        try {
            Target target = Target.Instance();
            if (null != target){
                String targetId = target.getTwitterId();
                int targetIdLen = targetId.length();
                Feed feed = Twitter.Command.Search(target);
                lxl.Map<String,Source> sourceCache = new lxl.Map<String,Source>();
                for (Feed.Data data : feed){
                    try {
                        Source source = SourceFor(sourceCache,data.author);
                        if (null != source && (!targetId.equalsIgnoreCase(source.getTwitterId()))){
                            Key commandKey = Command.KeyLongFor(data.guid);
                            Command command = Command.Get(commandKey);
                            if (null == command){
                                command = new Command(commandKey,data);
                                command.save();
                                switch (command.identifier){
                                case help:
                                    Twitter.Command.Reply(target,data,"Help http://"+gap.Version.Target+".appspot.com/ #"+targetId);
                                    break;
                                case stats:
                                    Twitter.Command.Reply(target,data,"Stats Ok #"+targetId);
                                    break;
                                case sources:
                                    BigTableIterator<Source> list = Sources();
                                    StringBuilder listString = new StringBuilder();
                                    for (Source src: list){
                                        String twid = src.getTwitterId();
                                        int len = listString.length();
                                        if (0 != len){
                                            if (140 > (len + twid.length() + 10 + targetIdLen))
                                                break;
                                            else
                                                listString.append(' ');
                                        }
                                        listString.append('@');
                                        listString.append(twid);
                                    }
                                    listString.insert(0,"Sources ");
                                    Twitter.Command.Reply(target,data,listString.toString());
                                    break;
                                case source:
                                    Source.GetCreateLong(command.name);
                                    Twitter.Command.Reply(target,data,"Source @"+command.name);
                                    break;
                                case drop:
                                    Source that = Source.ForLongTwitterId(command.name);
                                    if (null != that){
                                        that.drop();
                                        Twitter.Command.Reply(target,data,"Source Dropped @"+command.name);
                                    }
                                    else
                                        Twitter.Command.Reply(target,data,"Source not found @"+command.name);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception drop){
                    }
                }
            }
            else
                this.error(req,rep,500,"Target not configured");
        }
        catch (Exception exc){
            this.error(req,rep,500,"Unexpected error",exc);
        }
    }
}
