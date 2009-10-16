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
import static gap.data.Template.Field.*;
import static gap.data.Tool.Field.*;
import static gap.data.Tools.Set.*;


public abstract class ToolingFilter
    extends Object
    implements gap.data.DictionaryInto.DataFilter
{

    public final static class Templates
        extends ToolingFilter
    {
        public final static Templates Instance = new Templates();

        public Templates(){
            super();
        }
        public String acceptAs(Template template, Template.Field field){
            switch (field){
            case Name:{
                String name = template.getName();
                if (name.startsWith("div.")){
                    if (name.regionMatches(4,"logon.html",0,10))
                        return "logon";
                    else if (name.regionMatches(4,"overlay.html",0,12))
                        return "overlay";
                }
                return field.getFieldName();
            }
            case TemplateSourceHapax:
                return null;
            default:
                return field.getFieldName();
            }
        }
        public String acceptAs(gap.data.BigTable instance, Kind instanceKind, Field field){

            return this.acceptAs((Template)instance,(Template.Field)field);
        }
    }
    public final static class Tools
        extends ToolingFilter
    {

        private final Request request;
        private final boolean canCreate;
        private final boolean canUpdate;
        private final boolean canGoto;
        private final boolean canDelete;
        private final boolean canExport;
        private final boolean canImport;

        public Tools(Request req){
            super();
            this.request = req;
            this.canCreate = req.isMember;
            this.canUpdate = req.isPartner;
            this.canGoto   = true;
            this.canDelete = req.isAdmin;
            this.canExport = req.isMember;
            this.canImport = req.isPartner;
        }

        
        public String acceptAs(gap.data.BigTable instance, Kind instanceKind, Field field){

            return this.acceptAs((Tool)instance,(Tool.Field)field);
        }
        public String acceptAs(Tool tool, Tool.Field field){
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
        protected boolean isExecutable(Tool tool){
            gap.data.Tools.Set set = gap.data.Tools.Set.For(tool);
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
    }

}
