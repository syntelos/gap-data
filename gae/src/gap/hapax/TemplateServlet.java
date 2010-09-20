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

    private final static Page ALL = new Page(0,Short.MAX_VALUE);


    public TemplateServlet() {
        super();
    }


    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.isAdmin){
            String cmd = req.getPath(0);
            if (null != cmd){

                if ("clean".equals(cmd)){
                    try {
                        List.Primitive<Key> list = Template.QueryKeyN(Template.CreateQueryFor(),ALL);
                        for (Key key: list){
                            Store.DeleteCollection(TemplateNode.KIND,TemplateNode.CreateQueryFor(key));
                            Store.Delete(key);
                        }


                        rep.setStatus(200,"Ok");
                    }
                    catch (Exception any){

                        rep.setStatus(500,"Exception");
                        rep.setContentType("text/plain");

                        any.printStackTrace(rep.getWriter());
                    }
                }
                else
                    this.error(req,rep,501,"Unrecognized command");
            }
            else
                this.error(req,rep,400,"Missing command");
        }
        else
            this.error(req,rep,403,"Not authorized");
    }
}
