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
package gap.data;


import gap.data.*;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated short list.
 */
@Generated(value={"gap.service.OD","odl/list-short.xtm"},date="2009-10-19T18:15:36.343Z")
public final class ListShortTemplateTemplateNode
    extends gap.util.AbstractList<TemplateNode>
    implements gap.data.List.Short<TemplateNode>
{

    private final static long serialVersionUID = 2;

    public final static String AncestorKeyFieldName = "key";

    public final static String ParentTypeName = "Template";

    public final static String ChildTypeName = "TemplateNode";


    protected transient Template parent;


    public ListShortTemplateTemplateNode(Template parent) {
        super();
        if (null != parent){
            this.parent = parent;
            this.ancestorKeyFieldName = AncestorKeyFieldName;
            this.setValueClassAncestorKey();
        }
        else
            throw new IllegalArgumentException();
    }
    public ListShortTemplateTemplateNode(){
        super();
    }


    public void destroy(){
        this.parent = null;
        this.clearBuffer();
    }
    public Template getParent(){
        Template parent = this.parent;
        if (null == parent){
            Key parentKey = this.ancestorKey;
            if (null != parentKey){
                parent = Template.Get(parentKey);
                if (null != parent)
                    this.parent = parent;
                else
                    throw new IllegalStateException("Missing parent, user employed wrong constructor (pre store).");
            }
            else
                throw new IllegalStateException("Missing ancestor key.");
        }
        return parent;
    }
    public void setValueClassAncestorKey(){
        Key key = this.getParent().getClassFieldKeyValue();
        if (key != this.ancestorKey){
            this.ancestorKey = key;
            this.query = TemplateNode.CreateQueryFor(this.ancestorKey);
        }
    }
    public TemplateNode fetch(Filter filter){
        Key ancestor = this.ancestorKey;
        if (null != ancestor){
            Query query = TemplateNode.CreateQueryFor(ancestor,filter);
            return TemplateNode.Query1(query);
        }
        else
            throw new IllegalStateException("Missing ancestor key.");
    }
}
