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


import gap.data.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.*;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated short list.
 */
@Generated(value={"gap.service.OD","ListShort.java"},date="2012-06-18T22:42:57.447Z")
public abstract class ListShortTemplateTemplateNode
    extends gap.util.AbstractList<TemplateNode>
    implements gap.data.List.Short<TemplateNode>
{

    private final static long serialVersionUID = 4;

    public final static String ParentTypeName = "Template";

    public final static String ChildTypeName = "TemplateNode";

    public final static Class ParentTypeClass = Template.class;

    public final static Class ChildTypeClass = TemplateNode.class;


    protected transient Template parent;


    public ListShortTemplateTemplateNode(Template parent) {
        this();
        if (null != parent){
            this.parent = parent;
            this.setValueClassAncestorKey();
        }
        else
            throw new IllegalArgumentException();
    }
    public ListShortTemplateTemplateNode(){
        super();
        /*
         * Not Paging -- Short lists are for small membership.
         */
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
    public String getParentTypeName(){
        return ParentTypeName;
    }
    public String getChildTypeName(){
        return ChildTypeName;
    }
    public Class getParentTypeClass(){
        return ParentTypeClass;
    }
    public Class getChildTypeClass(){
        return ChildTypeClass;
    }
    public void setValueClassAncestorKey(){
        Key key = this.getParent().getKey();
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
    public Iterable<TemplateNode> list(Filter filter, Page page){
        Key ancestor = this.ancestorKey;
        if (null != ancestor){
            Query query = TemplateNode.CreateQueryFor(ancestor,filter);
            return TemplateNode.QueryN(query,page);
        }
        else
            throw new IllegalStateException("Missing ancestor key.");
    }
    public List.Short<TemplateNode> clone(){
        List list = super.clone();
        return (List.Short<TemplateNode>)list;
    }
    public List.Short<TemplateNode> add(TemplateNode item){
        super.add(item);
        return this;
    }
    /**
     * @see gap.data.List$Short#nhead(int)
     */
    public Iterable<TemplateNode> nhead(int count){
        final TableClass[] buffer = this.buffer;
        if (null != buffer){
            final int size = this.gross;
            if (0 > count){
                return (Iterable<TemplateNode>)(new BufferIterator());
            }
            else if (size == buffer.length || count < buffer.length){
                if (count < size){
                    TemplateNode[] re = (TemplateNode[])(new TableClass[count]);
                    System.arraycopy(buffer,0,re,0,count);
                    return (Iterable<TemplateNode>)(new BufferIterator(re));
                }
                else
                    return (Iterable<TemplateNode>)(new BufferIterator(buffer));
            }
            else {
                Query q = new Query(TemplateNode.KIND.getName(),this.ancestorKey).addSort(TemplateNode.DefaultSortBy,Query.SortDirection.ASCENDING);
                Page p = new Page(0,count);
                return Store.QueryNClass(q,p);
            }
        }
        else
            return (Iterable<TemplateNode>)(new BufferIterator());
    }
    /**
     * @see gap.data.List$Short#ntail(int)
     */
    public Iterable<TemplateNode> ntail(int count){
        final TableClass[] buffer = this.buffer;
        if (null != buffer){
            final int size = this.gross;
            if (0 > count){
                return (Iterable<TemplateNode>)(new BufferIterator());
            }
            else if (size == buffer.length){
                if (count < size){
                    final int x = (size-count-1);
                    if (x < buffer.length){
                        TemplateNode[] re = (TemplateNode[])(new TableClass[count]);
                        System.arraycopy(buffer,x,re,0,count);
                        return (Iterable<TemplateNode>)(new BufferIterator(re));
                    }
                    else
                        return (Iterable<TemplateNode>)(new BufferIterator());
                }
                else
                    return (Iterable<TemplateNode>)(new BufferIterator(buffer));
            }
            else {
                Query q = new Query(TemplateNode.KIND.getName(),this.ancestorKey).addSort(TemplateNode.DefaultSortBy,Query.SortDirection.DESCENDING);
                Page p = new Page(0,count);
                return Store.QueryNClass(q,p);
            }
        }
        else
            return (Iterable<TemplateNode>)(new BufferIterator());
    }

}
