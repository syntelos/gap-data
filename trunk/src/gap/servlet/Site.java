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

import gap.*;
import gap.data.*;
import static gap.data.Tool.Field.*;
import static gap.data.Tools.Set.*;
import gap.util.*;
import gap.service.*;

import hapax.TemplateDictionary;

/**
 * Bound to path <code>'/*'</code>
 */
public class Site
    extends gap.service.Servlet
{
    public final static class DefaultToolingFilter
        extends Object
        implements gap.data.DictionaryInto.DataFilter
    {
        private final static Kind KindResource = Kind.For("Resource");
        private final static Kind KindTool = Kind.For("Tool");


        private final Request request;
        private final boolean canCreate;
        private final boolean canUpdate;
        private final boolean canGoto;
        private final boolean canDelete;
        private final boolean canExport;
        private final boolean canImport;

        public DefaultToolingFilter(Servlet servlet, Request req){
            super();
            this.request = req;
            this.canCreate = req.isMember;
            this.canUpdate = req.isPartner;
            this.canGoto   = true;
            this.canDelete = req.isAdmin;
            this.canExport = req.isMember;
            this.canImport = req.isPartner;
        }

        protected boolean isExecutable(Tool tool){
            Tools.Set set = Tools.Set.For(tool);
            if (null == set)
                return this.request.isPartner;
            else {
                switch (set){
                case Update:
                    return this.canUpdate;
                case Create:
                    return this.canCreate;
                case Goto:
                    return this.canGoto;
                case Delete:
                    return this.canDelete;
                case Export:
                    return this.canExport;
                case Import:
                    return this.canImport;
                default:
                    return this.request.isPartner;
                }
            }
        }
        protected String filterAs(Tool tool, Tool.Field field){
            switch (field){
            case TitleHiGraphicUri:
                if (this.request.isAdmin)
                    return "titleGraphicUri";
                else
                    return null;

            case TitleLoGraphicUri:
                if (this.request.isAdmin)
                    return null;
                else if (this.isExecutable(tool))
                    return "titleGraphicUri";

            case ButtonHiGraphicUri:
                if (this.request.isAdmin)
                    return "buttonGraphicUri";
                else
                    return null;

            case ButtonLoGraphicUri:
                if (this.request.isAdmin)
                    return null;
                else if (this.isExecutable(tool))
                    return "buttonGraphicUri";

            case ButtonOffGraphicUri:
                if (this.isExecutable(tool))
                    return null;
                else
                    return "buttonGraphicUri";

            case FunctionClassfileJvm:
                return null;
            default:
                return field.getFieldName();
            }
        }
        public String acceptAs(gap.data.BigTable instance, Kind instanceKind, Field field){
            if (KindTool == instanceKind)
                return this.filterAs((Tool)instance,(Tool.Field)field);
            else if (KindResource == instanceKind){
                if (Resource.Field.ServletClassfileJvm == field)
                    return null;
            }
            return field.getFieldName();
        }
    }

    public Site(){
        super();
    }


    @Override
    protected TemplateDictionary doGetDefine(Request req, Response rep){

        TemplateDictionary top = super.doGetDefine(req,rep);


            boolean canCreate = req.isMember;
            boolean canUpdate = req.isPartner;
            boolean canGoto   = true;
            boolean canDelete = req.isAdmin;
            boolean canExport = req.isMember;
            boolean canImport = req.isPartner;
        //        TemplateDictionary head;
        //         if (canUpdate){
        //             head = top.addSection("head","head.tool.html");
        //             head.setVariable("tool_name","update");
        //             head.setVariable("tool_nameCamel","Update");
        //         }

        TemplateDictionary tool;
        if (canUpdate){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineUpdate(req,rep,tool,once);

                if (once){
                    once = false;
                    tool = top.addSection("overlay","div.overlay.html");
                    tool.addSection("tool_form","form.update.html");
                }
                else
                    break;
            }
            while (true);
        }
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineUpdate(req,rep,tool,true);
        }
        if (canCreate){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineCreate(req,rep,tool,once);

                if (once){
                    once = false;
                    tool = top.addSection("overlay","div.overlay.html");
                    tool.addSection("tool_form","form.create.html");
                }
                else
                    break;
            }
            while (true);
        }
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineCreate(req,rep,tool,true);
        }
        if (canGoto){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineGoto(req,rep,tool,once);

                if (once){
                    once = false;
                    tool = top.addSection("overlay","div.overlay.html");
                    tool.addSection("tool_form","form.goto.html");
                }
                else
                    break;
            }
            while (true);
        }
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineGoto(req,rep,tool,true);
        }
        if (canDelete){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineDelete(req,rep,tool,once);

                if (once){
                    once = false;
                    tool = top.addSection("overlay","div.overlay.html");
                    tool.addSection("tool_form","form.delete.html");
                }
                else
                    break;
            }
            while (true);
        }
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineDelete(req,rep,tool,true);
        }
        if (canExport){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineExport(req,rep,tool,once);

                if (once){
                    once = false;
                    tool = top.addSection("overlay","div.overlay.html");
                    tool.addSection("tool_form","form.export.html");
                }
                else
                    break;
            }
            while (true);
        }
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineExport(req,rep,tool,true);
        }
        if (canImport){
            tool = top.addSection("tool","div.tool.html");
            boolean once = true;
            do {
                this.doGetDefineImport(req,rep,tool,once);

                if (once){
                    once = false;
                    tool = top.addSection("overlay","div.overlay.html");
                    tool.addSection("tool_form","form.import.html");
                }
                else
                    break;
            }
            while (true);
        }
        else {
            tool = top.addSection("tool","div.tool.off.html");

            this.doGetDefineImport(req,rep,tool,true);
        }
        return top;
    }
    protected TemplateDictionary doGetDefineUpdate(Request req, Response rep, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","update");
        tool.setVariable("tool_nameCamel","Update");
        if (req.isAdmin){
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
    protected TemplateDictionary doGetDefineCreate(Request req, Response rep, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","create");
        tool.setVariable("tool_nameCamel","Create");
        if (req.isAdmin){
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
    protected TemplateDictionary doGetDefineGoto(Request req, Response rep, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","goto");
        tool.setVariable("tool_nameCamel","Goto");
        if (req.isAdmin){
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
    protected TemplateDictionary doGetDefineDelete(Request req, Response rep, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","delete");
        tool.setVariable("tool_nameCamel","Delete");
        if (req.isAdmin){
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
    protected TemplateDictionary doGetDefineExport(Request req, Response rep, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","export");
        tool.setVariable("tool_nameCamel","Export");
        if (req.isAdmin){
            tool.setVariable("tool_titleUri","/icons/export-ex-200x50-a00.png");
            tool.setVariable("tool_buttonUri","/icons/export-ex-b-200x50-a00.png");
            tool.setVariable("tool_buttonOffUri","/icons/export-ex-b-200x50-aaa.png");
        }
        else {
            tool.setVariable("tool_titleUri","/icons/export-ex-200x50-000.png");
            tool.setVariable("tool_buttonUri","/icons/export-ex-b-200x50-000.png");
            tool.setVariable("tool_buttonOffUri","/icons/export-ex-b-200x50-aaa.png");
        }
        return tool;
    }
    protected TemplateDictionary doGetDefineImport(Request req, Response rep, TemplateDictionary tool, boolean isDivTool){
        tool.setVariable("tool_name","import");
        tool.setVariable("tool_nameCamel","Import");
        if (req.isAdmin){
            tool.setVariable("tool_titleUri","/icons/import-im-200x50-a00.png");
            tool.setVariable("tool_buttonUri","/icons/import-im-b-200x50-a00.png");
            tool.setVariable("tool_buttonOffUri","/icons/import-im-b-200x50-aaa.png");
        }
        else {
            tool.setVariable("tool_titleUri","/icons/import-im-200x50-000.png");
            tool.setVariable("tool_buttonUri","/icons/import-im-b-200x50-000.png");
            tool.setVariable("tool_buttonOffUri","/icons/import-im-b-200x50-aaa.png");
        }
        return tool;
    }

}
