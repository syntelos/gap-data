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
package tt;


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
@Generated(value={"gap.service.OD","ListShort.java"},date="2012-01-05T13:59:05.254Z")
public abstract class ListShortAB
    extends gap.util.AbstractList<B>
    implements gap.data.List.Short<B>
{

    private final static long serialVersionUID = 1;

    public final static String ParentTypeName = "A";

    public final static String ChildTypeName = "B";

    public final static Class ParentTypeClass = A.class;

    public final static Class ChildTypeClass = B.class;


    protected transient A parent;


    public ListShortAB(A parent) {
        this();
        if (null != parent){
            this.parent = parent;
            this.setValueClassAncestorKey();
        }
        else
            throw new IllegalArgumentException();
    }
    public ListShortAB(){
        super();
        this.page = Page.Short;
    }


    public void destroy(){
        this.parent = null;
        this.clearBuffer();
    }
    public A getParent(){
        A parent = this.parent;
        if (null == parent){
            Key parentKey = this.ancestorKey;
            if (null != parentKey){
                parent = A.Get(parentKey);
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
            this.query = B.CreateQueryFor(this.ancestorKey);
        }
    }
    public B fetch(Filter filter){
        Key ancestor = this.ancestorKey;
        if (null != ancestor){
            Query query = B.CreateQueryFor(ancestor,filter);
            return B.Query1(query);
        }
        else
            throw new IllegalStateException("Missing ancestor key.");
    }
    public Iterable<B> list(Filter filter, Page page){
        Key ancestor = this.ancestorKey;
        if (null != ancestor){
            Query query = B.CreateQueryFor(ancestor,filter);
            return B.QueryN(query,page);
        }
        else
            throw new IllegalStateException("Missing ancestor key.");
    }
    public List.Short<B> clone(){
        List list = super.clone();
        return (List.Short<B>)list;
    }
    public List.Short<B> add(B item){
        super.add(item);
        return this;
    }
    /**
     * @see gap.data.List$Short#nhead(int)
     */
    public Iterable<B> nhead(int count){
        final BigTable[] buffer = this.buffer;
        if (null != buffer){
            final int size = this.gross;
            if (0 > count){
                return (Iterable<B>)(new BufferIterator());
            }
            else if (size == buffer.length || count < buffer.length){
                if (count < size){
                    B[] re = (B[])(new BigTable[count]);
                    System.arraycopy(buffer,0,re,0,count);
                    return (Iterable<B>)(new BufferIterator(re));
                }
                else
                    return (Iterable<B>)(new BufferIterator(buffer));
            }
            else {
                Query q = new Query(B.KIND.getName(),this.ancestorKey).addSort(B.DefaultSortBy,Query.SortDirection.ASCENDING);
                Page p = new Page(0,count);
                return Store.QueryNClass(q,p);
            }
        }
        else
            return (Iterable<B>)(new BufferIterator());
    }
    /**
     * @see gap.data.List$Short#ntail(int)
     */
    public Iterable<B> ntail(int count){
        final BigTable[] buffer = this.buffer;
        if (null != buffer){
            final int size = this.gross;
            if (0 > count){
                return (Iterable<B>)(new BufferIterator());
            }
            else if (size == buffer.length){
                if (count < size){
                    final int x = (size-count-1);
                    if (x < buffer.length){
                        B[] re = (B[])(new BigTable[count]);
                        System.arraycopy(buffer,x,re,0,count);
                        return (Iterable<B>)(new BufferIterator(re));
                    }
                    else
                        return (Iterable<B>)(new BufferIterator());
                }
                else
                    return (Iterable<B>)(new BufferIterator(buffer));
            }
            else {
                Query q = new Query(B.KIND.getName(),this.ancestorKey).addSort(B.DefaultSortBy,Query.SortDirection.DESCENDING);
                Page p = new Page(0,count);
                return Store.QueryNClass(q,p);
            }
        }
        else
            return (Iterable<B>)(new BufferIterator());
    }

}
