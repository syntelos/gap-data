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


    @Override
    protected TemplateDictionary doGetDefine(Path path, Accept accept, Logon logon){

        TemplateDictionary top = super.doGetDefine(path,accept,logon);

        top.setVariable("logon","div.logon.html");

        boolean admin = logon.serviceAdmin;
        boolean canCreate = this.canCreate(path,accept,logon);
        boolean canUpdate = this.canUpdate(path,accept,logon);
        boolean canGoto = this.canGoto(path,accept,logon);
        boolean canDelete = this.canDelete(path,accept,logon);
        boolean usingTools = false;


        TemplateDictionary head;
        if (canUpdate){
            usingTools = true;
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","update");
            head.setVariable("tool_nameCamel","Update");
        }
        if (canCreate){
            usingTools = true;
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","create");
            head.setVariable("tool_nameCamel","Create");
        }
        if (canGoto){
            usingTools = true;
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","goto");
            head.setVariable("tool_nameCamel","Goto");
        }
        if (canDelete){
            usingTools = true;
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","delete");
            head.setVariable("tool_nameCamel","Delete");
        }
        if (usingTools){

            TemplateDictionary tool;
            if (canUpdate){
                tool = top.addSection("tool","div.tool.html");
                boolean once = true;
                do {
                    tool.setVariable("tool_name","update");
                    tool.setVariable("tool_nameCamel","Update");
                    if (admin)
                        tool.setVariable("tool_bgUri","/icons/update-up-200x50-a00.png");
                    else
                        tool.setVariable("tool_bgUri","/icons/update-up-200x50-000.png");

                    if (once){
                        once = false;
                        tool = top.addSection("tools_overlay","div.overlay.html");
                        tool.addSection("tool_form","form.update.html");
                    }
                    else
                        break;
                }
                while (true);
            }
            if (canCreate){
                tool = top.addSection("tool","div.tool.html");
                boolean once = true;
                do {
                    tool.setVariable("tool_name","create");
                    tool.setVariable("tool_nameCamel","Create");
                    if (admin)
                        tool.setVariable("tool_bgUri","/icons/create-cr-200x50-a00.png");
                    else
                        tool.setVariable("tool_bgUri","/icons/create-cr-200x50-000.png");

                    if (once){
                        once = false;
                        tool = top.addSection("tools_overlay","div.overlay.html");
                        tool.addSection("tool_form","form.create.html");
                    }
                    else
                        break;
                }
                while (true);
            }
            if (canGoto){
                tool = top.addSection("tool","div.tool.html");
                boolean once = true;
                do {
                    tool.setVariable("tool_name","goto");
                    tool.setVariable("tool_nameCamel","Goto");
                    if (admin)
                        tool.setVariable("tool_bgUri","/icons/goto-gt-200x50-a00.png");
                    else
                        tool.setVariable("tool_bgUri","/icons/goto-gt-200x50-000.png");

                    if (once){
                        once = false;
                        tool = top.addSection("tools_overlay","div.overlay.html");
                        tool.addSection("tool_form","form.goto.html");
                    }
                    else
                        break;
                }
                while (true);
            }
            if (canDelete){
                tool = top.addSection("tool","div.tool.html");
                boolean once = true;
                do {
                    tool.setVariable("tool_name","delete");
                    tool.setVariable("tool_nameCamel","Delete");
                    if (admin)
                        tool.setVariable("tool_bgUri","/icons/delete-de-200x50-a00.png");
                    else
                        tool.setVariable("tool_bgUri","/icons/delete-de-200x50-000.png");

                    if (once){
                        once = false;
                        tool = top.addSection("tools_overlay","div.overlay.html");
                        tool.addSection("tool_form","form.delete.html");
                    }
                    else
                        break;
                }
                while (true);

            }
        }
        return top;
    }
    @Override
    protected Template doGetTemplate(Path path, Accept accept, Logon logon, FileManager fm)
        throws TemplateException
    {
        if (accept.accept("text/html"))
            return fm.getTemplate("index.html");
        else
            return null;
    }

}
