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

import gap.data.TemplateDescriptor;
import gap.service.Accept;
import gap.service.FileManager;
import gap.service.Logon;
import gap.service.Path;

import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Bound to path <code>'/*'</code>
 */
public class Site
    extends gap.service.Servlet
{


    public Site(){
        super();
    }


    protected void doGet(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        if (accept.accept("text/html")){
            try {
                Template template = FileManager.Get().getTemplate("index.html");
                if (null != template)
                    this.render(path, accept, logon, template, logon.dict, rep);
                else
                    this.error(path,accept,logon,req,rep,404,"Not found.");
            }
            catch (TemplateException exc){
                LogRecord rec = new LogRecord(Level.SEVERE,"error");
                rec.setThrown(exc);
                Log.log(rec);
                this.error(path,accept,logon,req,rep,500,"Internal error.",exc);
            }
        }
        else
            rep.setStatus(400,"Unrecognized request.");
    }
}
