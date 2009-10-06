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
 * Short and long list base class.  Instances are generated via {@link
 * gap.service.OD}.
 * 
 * @author jdp
 */
public abstract class AbstractList<V>
    extends Object
    implements List<V>
{
    /**
     * Short list base class.
     */
    public abstract static class Short<V>
        extends AbstractList<V>
        implements List.Short<V>
    {

        protected Short(){
            super();
        }

    }
    /**
     * Long list base class. 
     */
    public abstract static class Long<V>
        extends AbstractList<V>
        implements List.Long<V>
    {

        protected Long(){
            super();
        }

    }
    /**
     * Buffer iterator. 
     */
    public final class BufferIterator<V>
        extends Object
        implements java.util.Iterator<V>
    {
        private final Object[] buffer;
        private final int count;
        private int index;


        public BufferIterator(Object[] buffer){
            super();
            this.buffer = buffer;
            this.count = ((null == buffer)?(0):(buffer.length));
        }

        public boolean hasNext(){
            return (this.index < this.count);
        }
        public V next(){
            int index = this.index;
            if (index < this.count){
                V next = (V)this.buffer[index];
                this.index++;
                return next;
            }
            else
                throw new java.util.NoSuchElementException(java.lang.String.valueOf(index));
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
    }


    protected Key ancestorKey;

    protected String ancestorKeyFieldName;

    protected Object[] buffer;


    protected AbstractList(){
        super();
    }

    /**
     * Attempt buffer fill when empty
     */
    public final void fill(){
        if (this.isEmpty())
            this.refill();
    }
    /**
     * Attempt buffer fill every time
     */
    public abstract void refill();

    public final Key getValueClassAncestorKey(){
        return this.ancestorKey;
    }
    public final boolean hasValueClassAncestorKey(){
        return (null != this.ancestorKey);
    }
    public final boolean isCompleteValueClassAncestorKey(){
        Key ancestorKey = this.ancestorKey;
        if (null != ancestorKey)
            return ancestorKey.isComplete();
        else
            return false;
    }
    public final void setValueClassAncestorKey(Key key){
        this.ancestorKey = key;
    }
    public final String getValueClassAncestorKeyFieldName(){
        return this.ancestorKeyFieldName;
    }
    public final boolean hasValueClassAncestorKeyFieldName(){
        return (null != this.ancestorKeyFieldName);
    }
    public final void setValueClassAncestorKeyFieldName(String name){
        this.ancestorKeyFieldName = name;
    }
    public final int size(){
        Object[] buffer = this.buffer;
        if (null == buffer)
            return 0;
        else
            return buffer.length;
    }
    public final boolean isEmpty(){
        return (0 == this.size());
    }
    public final boolean isNotEmpty(){
        return (0 != this.size());
    }
    public boolean contains(V instance){
        return (-1 != this.indexInBuffer(instance));
    }
    public boolean containsNot(V instance){
        return (-1 == this.indexInBuffer(instance));
    }
    public AbstractList clone(){
        try {
            AbstractList clone = (AbstractList)super.clone();
            if (null != this.buffer)
                clone.buffer = (Object[])this.buffer.clone();
            return clone;
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new java.lang.InternalError();
        }
    }
    public final int compareTo(Collection<V> collection){
        if (null == collection)
            return Compares.NoIntersection;
        else if (collection.isEmpty()){
            if (this.isEmpty())
                return Compares.Equivalent;
            else
                return Compares.NoIntersection;
        }
        else {
            boolean hit = false, miss = false;
            for (V item : collection){
                if (this.contains(item)){
                    hit = true;
                    if (miss)
                        break;
                }
                else {
                    miss = true;
                    if (hit)
                        break;
                }
            }
            if (hit){
                if (miss)
                    return Compares.NoIntersection;
                else
                    return Compares.Equivalent;
            }
            else
                return Compares.NoIntersection;
        }
    }
    public final java.util.Iterator<V> iterator(){
        return new BufferIterator<V>(this.buffer);
    }
    protected final List<V> addToBuffer(V instance){
        if (null != instance){
            if (this.containsNot(instance)){
                Object[] buffer = this.buffer;
                if (null == buffer)
                    this.buffer = new Object[]{instance};
                else {
                    int len = buffer.length;
                    Object[] copier = new Object[len+1];
                    System.arraycopy(buffer,0,copier,0,len);
                    copier[len] = instance;
                    this.buffer = copier;
                }
            }
            return this;
        }
        else
            throw new IllegalArgumentException();
    }
    protected final int indexInBuffer(V instance){
        Object[] buffer = this.buffer;
        if (null != buffer){
            for (int cc = 0, count = buffer.length; cc < count; cc++){
                Object item = buffer[cc];
                if (instance == item || item.equals(instance))
                    return cc;
            }
        }
        return -1;
    }
}
