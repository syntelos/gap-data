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
package gap.util;

import gap.data.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/**
 * Temporary list, not valid under I/O operations including
 * serialization and storage.
 * 
 * @author jdp
 */
public class ShortList<V extends BigTable>
    extends AbstractList<V>
    implements List.Short<V>
{

    protected final String parentTypeName;

    protected final Class parentTypeClass;

    protected final String childTypeName;

    protected final Class childTypeClass;

    protected BigTable parent;


    public ShortList(BigTable parent, V child){
        super();
        if (null != parent && null != child){

            this.parent = parent;

            this.parentTypeName = parent.getClassKind().getName();
            this.parentTypeClass = parent.getClass();

            this.setValueClassAncestorKey();

            this.childTypeName = child.getClassKind().getName();
            this.childTypeClass = child.getClass();

            this.add(child);
        }
        else
            throw new IllegalArgumentException();
    }


    public void destroy(){
        this.parent = null;
        this.clearBuffer();
    }
    public BigTable getParent(){
        BigTable parent = this.parent;
        if (null == parent)
            throw new IllegalStateException("Missing parent, this class is not valid under I/O operations.");
        else
            return parent;
    }
    public String getParentTypeName(){
        return this.parentTypeName;
    }
    public String getChildTypeName(){
        return this.childTypeName;
    }
    public Class getParentTypeClass(){
        return this.parentTypeClass;
    }
    public Class getChildTypeClass(){
        return this.childTypeClass;
    }
    public void setValueClassAncestorKey(){
        Key key = this.getParent().getKey();
        if (key != this.ancestorKey){
            this.ancestorKey = key;
            this.query = new Query(this.childTypeName,this.ancestorKey);
        }
    }
    public V fetch(Filter filter){
        Key ancestor = this.ancestorKey;
        if (null != ancestor){
            Query query = new Query(this.childTypeName,this.ancestorKey);
            filter.update(query);
            return (V)Store.Query1Class(query);
        }
        else
            throw new IllegalStateException("Missing ancestor key.");
    }
    public Iterable<V> list(Filter filter, Page page){
        Key ancestor = this.ancestorKey;
        if (null != ancestor){
            Query query = new Query(this.childTypeName,this.ancestorKey);
            filter.update(query);
            return (Iterable<V>)Store.QueryNClass(query);
        }
        else
            throw new IllegalStateException("Missing ancestor key.");
    }
    public List.Short<V> clone(){
        List list = super.clone();
        return (List.Short<V>)list;
    }
    public List.Short<V> add(V item){
        super.add(item);
        return this;
    }
    /**
     * @see gap.data.List$Short#nhead(int)
     */
    public Iterable<V> nhead(int count){
        final BigTable[] buffer = this.buffer;
        if (null != buffer){
            final int size = this.gross;
            if (0 > count){
                return (Iterable<V>)(new BufferIterator());
            }
            else if (size == buffer.length || count < buffer.length){
                if (count < size){
                    V[] re = (V[])(new BigTable[count]);
                    System.arraycopy(buffer,0,re,0,count);
                    return (Iterable<V>)(new BufferIterator(re));
                }
                else
                    return (Iterable<V>)(new BufferIterator(buffer));
            }
            else {
                Query q = new Query(this.childTypeName,this.ancestorKey);
                Page p = new Page(0,count);
                return Store.QueryNClass(q,p);
            }
        }
        else
            return (Iterable<V>)(new BufferIterator());
    }
    /**
     * @see gap.data.List$Short#ntail(int)
     */
    public Iterable<V> ntail(int count){
        final BigTable[] buffer = this.buffer;
        if (null != buffer){
            final int size = this.gross;
            if (0 > count){
                return (Iterable<V>)(new BufferIterator());
            }
            else if (size == buffer.length){
                if (count < size){
                    final int x = (size-count-1);
                    if (x < buffer.length){
                        V[] re = (V[])(new BigTable[count]);
                        System.arraycopy(buffer,x,re,0,count);
                        return (Iterable<V>)(new BufferIterator(re));
                    }
                    else
                        return (Iterable<V>)(new BufferIterator());
                }
                else
                    return (Iterable<V>)(new BufferIterator(buffer));
            }
            else {
                Query q = new Query(this.childTypeName,this.ancestorKey);
                Page p = new Page(0,count);
                return Store.QueryNClass(q,p);
            }
        }
        else
            return (Iterable<V>)(new BufferIterator());
    }
}
