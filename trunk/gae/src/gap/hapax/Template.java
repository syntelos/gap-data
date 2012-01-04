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
import java.util.Date;

/**
 * Generated once bean data user.
 */
public final class Template
    extends TemplateData
{
    public final static String ToString(Template template){
        StringBuilder string = new StringBuilder();

        List.Short<TemplateNode> list = template.getTemplateTargetHapax();
        for (TemplateNode node: list){
            final Text text = node.getNodeContent();
            final TemplateNodeType nodeType = TemplateNodeType.For(node);
            if (null != nodeType){

                String textString;
                if (null != text)
                    textString = text.getValue();
                else
                    textString = null;


                switch(nodeType){
                case SectionOpen:
                    string.append("{{#");

                    if (null != textString)
                        string.append(textString);
                    string.append("}}");
                    break;
                case SectionClose:
                    string.append("{{/");
                    if (null != textString)
                        string.append(textString);
                    string.append("}}");
                    break;
                case Variable:
                    string.append("{{=");
                    if (null != textString)
                        string.append(textString);
                    string.append("}}");
                    break;
                case Text:
                    if (null != textString)
                        string.append(textString);
                    break;
                case Include:
                    string.append("{{>");
                    if (null != textString)
                        string.append(textString);
                    string.append("}}");
                    break;
                case Comment:
                    string.append("{{!");
                    if (null != textString)
                        string.append(textString);
                    string.append("}}");
                    break;
                default:
                    break;
                }
            }
        }
        return string.toString();
    }


    public Template() {
        super();
    }
    public Template(String name) {
        super( name);
    }



    public void onread(){
    }
    public void onwrite(){
    }
    public java.io.Serializable valueOf(gap.data.Field field, boolean mayInherit){
        return (java.io.Serializable)Field.Get((Field)field,this,mayInherit);
    }
    public void define(gap.data.Field field, java.io.Serializable value){
        Field.Set((Field)field,this,value);
    }
    public void drop(){
        Delete(this);
    }
    public void clean(){
        Clean(this);
    }
    public void save(){
        Save(this);
    }
    public void store(){
        Store(this);
    }
    public void parse(){
        this.setLastModified(System.currentTimeMillis());
        this.save();

        List.Short<TemplateNode> templateNodes = this.getTemplateTargetHapax();
        try {
            TemplateParser.Parse(this,templateNodes);
        }
        catch (IOException parserConflict){
            /*
             * Safe to ignore as the template node set has been dropped
             */
        }
    }
    public String toString(){

        return Template.ToString(this);
    }
}
