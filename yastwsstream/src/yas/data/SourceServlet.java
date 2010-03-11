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


import gap.*;
import gap.data.*;
import gap.hapax.*;

import com.google.appengine.api.datastore.*;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;

/**
 * Generated once.  This source file will not be overwritten
 * unless deleted, so it can be edited.
 *
 * @see Source
 */
public final class SourceServlet
    extends YasServlet
{
    protected final static class TemplateNames {
        protected final static TemplateName Sources = new TemplateName("sources");
    }
    public enum Op {
        Add, Drop, Update, Start, Stop, Nil;

        public final static Op For(Request req){
            try {
                return valueOf(req.getParameter("op"));
            }
            catch (Exception exc){
                return Nil;
            }
        }
    }


    public SourceServlet() {
        super();
    }


    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.isLoggedIn()){
            Target target = Target.Instance();
            if (IsAdmin(req,target))
                this.doGet2(req,rep);
            else
                this.error(req,rep,403,"Access denied");
        }
        else
            this.error(req,rep,403,"Require login");
    }
    private void doGet2(Request req, Response rep)
        throws ServletException, IOException
    {
        BigTableIterator<Source> sources = Source.Sources();
        for (Source source: sources){
            req.addSection(TemplateNames.Sources,source);
        }
        this.render(req,rep,"source.html");
    }
    protected void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.isLoggedIn()){
            Target target = Target.Instance();
            if (IsAdmin(req,target)){
                String twitterId = Twitter.Name.Clean(req.getParameter("twitterId"));
                String logonId = Email.Clean(req.getParameter("logonId"));

                switch (Op.For(req)){
                case Add:
                    if (null != twitterId){
                        Source source = Source.ForLongTwitterId(twitterId);
                        if (null == source)
                            source = Source.GetCreateLong(twitterId);

                        if (null != logonId){
                            source.setLogonId(logonId);
                            source.save();
                        }
                        this.doGet2(req,rep);
                    }
                    else
                        this.error(req,rep,403,"Missing parameters");
                    return;
                case Drop:
                    if (null != twitterId){
                        Source source = Source.ForLongTwitterId(twitterId);
                        if (null != source)
                            source.drop();

                        this.doGet2(req,rep);
                    }
                    else
                        this.error(req,rep,403,"Missing parameters");
                    return;
                case Update:                    
                    if (null != twitterId){
                        if (null != logonId){
                            Source source = Source.ForLongTwitterId(twitterId);
                            source.setLogonId(logonId);
                            source.save();
                        }
                        this.doGet2(req,rep);
                    }
                    else
                        this.error(req,rep,403,"Missing parameters");
                    return;
                case Start:
                    TaskServlet.UpdateQueueCommands();
                    TaskServlet.UpdateQueueFavorites();
                    this.doGet2(req,rep);
                    return;
                case Stop:
                    this.error(req,rep,403,"Unimplemented");
                    return;
                case Nil:
                    this.doGet2(req,rep);
                    return;
                }
            }
            else
                this.error(req,rep,403,"Access denied");
        }
        else
            this.error(req,rep,403,"Require login");
            
    }
}
