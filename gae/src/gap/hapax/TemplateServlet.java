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
package gap.hapax;


import gap.*;
import gap.data.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

/**
 * Plain HTTP protocol for managing templates.
 * 
 * <h3>Template management protocol</h3>
 * 
 * <h4>CLEAN</h4>
 * 
 * Valid in LOCK state, invalid in UNLOCK state.  Drop all instances
 * of TemplateNode and Template.
 * 
 * <h5>Request</h5>
 *
 * <pre>
 * GET /templates/clean
 * </pre>
 *
 * <h5>Responses</h5>
 * 
 * <dl>
 * <dt>200 Ok</dt>
 * <dd>Body empty.  Command completed.</dd>
 * 
 * <dt>403 Not authorized</dt>
 * <dd>Body empty.  Not admin.</dd>
 * 
 * <dt>500 Exception</dt>
 * <dd>Body contains plain text stack trace</dd>
 * 
 * <dt>501 Unrecognized command</dt>
 * <dd>Body empty</dd>
 * 
 * <dt>400 Missing command</dt>
 * <dd>Body empty</dd>
 * </dl>
 *
 * @see Template
 */
public final class TemplateServlet
    extends gap.service.Servlet
{
    public static enum Cmd {
        CLEAN, LIST, UNKNOWN;

        public static Cmd For(String s){
            if (null == s || 1 > s.length())
                return Cmd.UNKNOWN;
            else {
                try {
                    return Cmd.valueOf(s.toUpperCase());
                }
                catch (RuntimeException exc){

                    return Cmd.UNKNOWN;
                }
            }
        }
    }

    public TemplateServlet() {
        super();
    }


    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.isAdmin){
            Cmd cmd = Cmd.For(req.getSource());
            switch(cmd){
            case CLEAN:{
                List.Primitive<Key> list = Template.QueryNKey(Template.CreateQueryFor());
                for (Key key: list){
                    TemplateNode.DeleteChildrenOf(key);
                    Template.Delete(key);
                }
                this.ok(req,rep);
                break;
            }
            case LIST:{
                String name = req.getGroup();
                if (null != name){
                    Template template = Template.ForLongName(name);
                    if (null != template){
                        rep.setContentTypeText();
                        PrintWriter out = rep.getWriter();
                        {
                            List.Short<TemplateNode> list = template.getTemplateTargetHapax();
                            for (TemplateNode node: list){
                                Text text = node.getNodeContent();
                                if (null != text){
                                    out.print(text.getValue());
                                }
                            }
                        }
                        this.ok(req,rep);
                    }
                    else
                        this.error(req,rep,404,"Not found");
                }
                else
                    this.error(req,rep,400,"Bad request");
                break;
            }
            default:
                this.error(req,rep,400,"Bad request");
                break;
            }
        }
        else
            this.error(req,rep,403,"Not authorized");
    }
}
