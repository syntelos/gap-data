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

        boolean canCreate = this.canCreate(path,accept,logon);
        boolean canUpdate = this.canUpdate(path,accept,logon);
        boolean canGoto = this.canGoto(path,accept,logon);
        boolean canDelete = this.canDelete(path,accept,logon);


        TemplateDictionary head;
        if (canUpdate){
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","update");
            head.setVariable("tool_nameCamel","Update");
        }
        if (canCreate){
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","create");
            head.setVariable("tool_nameCamel","Create");
        }
        if (canGoto){
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","goto");
            head.setVariable("tool_nameCamel","Goto");
        }
        if (canDelete){
            head = top.addSection("tool_head","head.tool.html");
            head.setVariable("tool_name","delete");
            head.setVariable("tool_nameCamel","Delete");
        }


        TemplateDictionary tool;
        if (canUpdate){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineUpdate(path,accept,logon,tool,once);

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
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineUpdate(path,accept,logon,tool,true);
        }
        if (canCreate){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineCreate(path,accept,logon,tool,once);

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
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineCreate(path,accept,logon,tool,true);
        }
        if (canGoto){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineGoto(path,accept,logon,tool,once);

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
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineGoto(path,accept,logon,tool,true);
        }
        if (canDelete){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineDelete(path,accept,logon,tool,once);

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
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineDelete(path,accept,logon,tool,true);
        }
        return top;
    }
    protected TemplateDictionary doGetDefineUpdate(Path path, Accept accept, Logon logon, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","update");
        tool.setVariable("tool_nameCamel","Update");
        if (logon.serviceAdmin){
            tool.setVariable("tool_titleUri","/icons/update-up-200x50-a00.png");
            tool.setVariable("tool_buttonUri","/icons/update-up-b-200x50-a00.png");
            tool.setVariable("tool_buttonOffUri","/icons/update-up-b-200x50-aaa.png");
        }
        else {
            tool.setVariable("tool_titleUri","/icons/update-up-200x50-000.png");
            tool.setVariable("tool_buttonUri","/icons/update-up-b-200x50-000.png");
            tool.setVariable("tool_buttonOffUri","/icons/update-up-b-200x50-aaa.png");
        }
        return tool;
    }
    protected TemplateDictionary doGetDefineCreate(Path path, Accept accept, Logon logon, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","create");
        tool.setVariable("tool_nameCamel","Create");
        if (logon.serviceAdmin){
            tool.setVariable("tool_titleUri","/icons/create-cr-200x50-a00.png");
            tool.setVariable("tool_buttonUri","/icons/create-cr-b-200x50-a00.png");
            tool.setVariable("tool_buttonOffUri","/icons/create-cr-b-200x50-aaa.png");
        }
        else {
            tool.setVariable("tool_titleUri","/icons/create-cr-200x50-000.png");
            tool.setVariable("tool_buttonUri","/icons/create-cr-b-200x50-000.png");
            tool.setVariable("tool_buttonOffUri","/icons/create-cr-b-200x50-aaa.png");
        }
        return tool;
    }
    protected TemplateDictionary doGetDefineGoto(Path path, Accept accept, Logon logon, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","goto");
        tool.setVariable("tool_nameCamel","Goto");
        if (logon.serviceAdmin){
            tool.setVariable("tool_titleUri","/icons/goto-gt-200x50-a00.png");
            tool.setVariable("tool_buttonUri","/icons/goto-gt-b-200x50-a00.png");
            tool.setVariable("tool_buttonOffUri","/icons/goto-gt-b-200x50-aaa.png");
        }
        else {
            tool.setVariable("tool_titleUri","/icons/goto-gt-200x50-000.png");
            tool.setVariable("tool_buttonUri","/icons/goto-gt-b-200x50-000.png");
            tool.setVariable("tool_buttonOffUri","/icons/goto-gt-b-200x50-aaa.png");
        }
        return tool;
    }
    protected TemplateDictionary doGetDefineDelete(Path path, Accept accept, Logon logon, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","delete");
        tool.setVariable("tool_nameCamel","Delete");
        if (logon.serviceAdmin){
            tool.setVariable("tool_titleUri","/icons/delete-de-200x50-a00.png");
            tool.setVariable("tool_buttonUri","/icons/delete-de-b-200x50-a00.png");
            tool.setVariable("tool_buttonOffUri","/icons/delete-de-b-200x50-aaa.png");
        }
        else {
            tool.setVariable("tool_titleUri","/icons/delete-de-200x50-000.png");
            tool.setVariable("tool_buttonUri","/icons/delete-de-b-200x50-000.png");
            tool.setVariable("tool_buttonOffUri","/icons/delete-de-b-200x50-aaa.png");
        }
        return tool;
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
