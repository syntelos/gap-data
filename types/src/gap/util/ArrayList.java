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
 * A gap data list.
 * 
 * @author jdp
 */
public class ArrayList<V>
    extends Object
    implements List.Short<V>
{

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


    protected transient Object[] buffer;



    public ArrayList(){
        super();
    }


    public void init(){
    }
    public void destroy(){
        this.buffer = null;
    }
    public final void clearBuffer(){
        this.buffer = null;
    }
    public List<V> clone(){
        try {
            ArrayList<V> clone = (ArrayList<V>)super.clone();
            if (null != this.buffer)
                clone.buffer = (Object[])this.buffer.clone();
            return clone;
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new java.lang.InternalError();
        }
    }
    public void drop(){
        throw new UnsupportedOperationException();
    }
    public boolean save(long timeout)
        throws java.lang.InterruptedException
    {
        throw new UnsupportedOperationException();
    }
    public Query getQuery(){
        throw new UnsupportedOperationException();
    }
    public Page getPage(){
        throw new UnsupportedOperationException();
    }
    public boolean hitEnd(){
        throw new UnsupportedOperationException();
    }
    public V fetch(Filter filter){
        throw new UnsupportedOperationException();
    }
    public Key getValueClassAncestorKey(){
        throw new UnsupportedOperationException();
    }
    public boolean hasValueClassAncestorKey(){
        return false;
    }
    public boolean isCompleteValueClassAncestorKey(){
        return false;
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
    public List<V> add(V instance){
        return this.addToBuffer(instance);
    }
    public V get(int index){
        if (-1 < index){
            Object[] buffer = this.buffer;
            if (null != buffer && index < buffer.length)
                return (V)buffer[index];
        }
        throw new java.lang.ArrayIndexOutOfBoundsException(String.valueOf(index));
    }
    public V set(int index, V value){
        if (-1 < index){
            Object[] buffer = this.buffer;
            if (null != buffer && index < buffer.length){
                V previous = (V)buffer[index];
                buffer[index] = value;
                return previous;
            }
        }
        throw new java.lang.ArrayIndexOutOfBoundsException(String.valueOf(index));
    }
    public final List<V> addToBuffer(V instance){
        if (null != instance){
            Object[] buffer = this.buffer;
            if (null == buffer){
                this.buffer = new Object[]{instance};
                this.buffered(instance,0);
            }
            else {
                int len = buffer.length;
                Object[] copier = new Object[len+1];
                System.arraycopy(buffer,0,copier,0,len);
                copier[len] = instance;
                this.buffer = copier;
                this.buffered(instance,len);
            }
            return this;
        }
        else
            throw new IllegalArgumentException();
    }
    public final int indexInBuffer(V instance){
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
    protected void buffered(V instance, int index){
    }
}
