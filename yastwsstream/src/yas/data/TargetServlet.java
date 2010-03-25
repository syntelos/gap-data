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
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;

/**
 * Configure target.
 *
 * @see Target
 */
public final class TargetServlet
    extends YasServlet
{

    public TargetServlet() {
        super();
    }


    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.isLoggedIn()){
            Target target = Target.Instance();
            if (IsAdmin(req,target)){
                if (null != target){

                    String twitterId = target.getTwitterId();
                    if (null != twitterId)
                        req.setVariable("twitterId",twitterId);
                    else
                        req.setVariable("twitterId",gap.Version.Target);

                    String twitterKey = target.getTwitterKey();
                    if (null != twitterKey)
                        req.setVariable("twitterKey",twitterKey);

                    String twitterSecret = target.getTwitterSecret();
                    if (null != twitterSecret)
                        req.setVariable("twitterSecret",twitterSecret);

                    String logonId = target.getLogonId();
                    if (null == logonId)
                        logonId = req.getLogonId();
                    
                    req.setVariable("logonId",logonId);

                }
                else {
                    String logonId = req.getLogonId();
                    
                    req.setVariable("logonId",logonId);

                    req.setVariable("twitterId",gap.Version.Target);
                }
                this.render(req,rep,"target.html");
            }
            else
                this.error(req,rep,403,"Access denied");
        }
        else
            this.error(req,rep,403,"Require login");
    }
    protected void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.isLoggedIn()){
            Target target = Target.Instance();
            if (IsAdmin(req,target)){
                String twitterId = req.getParameter("twitterId");
                String twitterKey = req.getParameter("twitterKey");
                String twitterSecret = req.getParameter("twitterSecret");
                String logonId = req.getParameter("logonId");
                if (null != twitterId && null != twitterKey && null != twitterSecret && null != logonId){

                    if (null == target){
                        target = new Target(twitterId);
                        target.setTwitterKey(twitterKey);
                        target.setTwitterSecret(twitterSecret);
                        target.setLogonId(logonId);
                        target.save();
                        /*
                         */
                        req.setVariable("twitterId",twitterId);
                        req.setVariable("twitterKey",twitterKey);
                        req.setVariable("twitterSecret",twitterSecret);
                        req.setVariable("logonId",logonId);
                        this.render(req,rep,"target.html");
                    }
                    else {
                        target.setTwitterId(twitterId);
                        target.setTwitterKey(twitterKey);
                        target.setTwitterSecret(twitterSecret);
                        target.setLogonId(logonId);
                        target.save();
                        /*
                         */
                        req.setVariable("twitterId",twitterId);
                        req.setVariable("twitterKey",twitterKey);
                        req.setVariable("twitterSecret",twitterSecret);
                        req.setVariable("logonId",logonId);
                        this.render(req,rep,"target.html");
                    }
                }
                else
                    this.error(req,rep,400,"Missing fields");
            }
            else
                this.error(req,rep,403,"Access denied");
        }
        else
            this.error(req,rep,403,"Require login");
    }

}
