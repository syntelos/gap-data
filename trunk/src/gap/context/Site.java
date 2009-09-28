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
package gap.context;

import gap.data.TemplateDescriptor;
import gap.service.Accept;
import gap.service.FileManager;
import gap.service.Logon;
import gap.service.Path;

import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;

import javax.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.LogRecord;

/**
 * 
 */
public class Site
    extends gap.service.Context
{


    public Site(){
        super();
    }


    protected void contextInitialized(ServletContext servletContext){
        /*
         * Create editable copies of builtin templates, copy into the
         * datastore.
         */
        final String base = "";
        File webinf = new File("WEB-INF");
        File templates = new File(webinf,"templates");
        String[] fileNameList = templates.list();
        for (String fileName: fileNameList){
            if (fileName.endsWith(".xtm")){
                String name = fileName.substring(0,(fileName.length()-4));
                TemplateDescriptor templateD = TemplateDescriptor.ForBaseName(base,name);
                if (null == templateD){
                    File templateFile = new File(templates,fileName);
                    /*
                      templateD = new TemplateDescriptor("",basename);
                      .
                      .
                      .
                      templateD.save();
                     */
                }
            }
        }
    }

}
