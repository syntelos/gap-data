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

import java.util.Date;

/**
 * Generated once bean data user.
 */
public final class TemplateNode
    extends TemplateNodeData
{

    public TemplateNode() {
        super();
    }
    public TemplateNode(Key ancestor, String nodeType, Integer offset) {
        super(ancestor,  nodeType,  offset);
    }
    public TemplateNode(Key ancestor, String nodeType, Integer offset, Integer lno) {
        super(ancestor,  nodeType,  offset);
        this.setLineNumber(lno);
    }
    public TemplateNode(Key ancestor, String nodeType, Integer offset, Integer lno, Text nodeContent) {
        super(ancestor,  nodeType,  offset);
        this.setNodeContent(nodeContent);
        this.setLineNumber(lno);
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
}
