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
package gap.servlet;

import gap.Request;
import gap.Response;
import gap.hapax.TemplateName;
import yas.data.Target;

import java.io.IOException;
import javax.servlet.ServletException;

/**
 * Bound to path <code>'/*'</code>
 */
public class Site
    extends yas.data.YasServlet
{
    protected static class TemplateNames {

        protected final static TemplateName Admin = new TemplateName("admin");
    }


    public Site(){
        super();
    }


    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        Target target = Target.Instance();
        if (null != target){
            String twitterId = target.getTwitterId();
            if (null != twitterId)
                req.setVariable("twitterId",twitterId);
            else
                req.setVariable("twitterId",gap.Version.Target);
        }
        else
            req.setVariable("twitterId",gap.Version.Target);

        if (IsAdmin(req,target))
            req.showSection(TemplateNames.Admin);

        super.doGet(req,rep);
    }
}
