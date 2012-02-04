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

import gap.data.Collection;
import gap.data.Filter;
import gap.data.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/**
 * A gap data list for standalone use, not table class fields.
 *
 * Instances of this class are not compatible with Object I/O
 * including serialization and storage.
 * 
 * @see AbstractData#Add
 * @see TemplateName
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
        implements java.lang.Iterable<V>,
                   java.util.Iterator<V>
    {
        private final Object[] buffer;
        private final int count;
        private int index;


        public BufferIterator(){
            super();
            this.buffer = null;
            this.count = 0;
        }
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
        public java.util.Iterator<V> iterator(){
            return this;
        }
    }


    protected transient Object[] buffer;



    public ArrayList(){
        super();
    }
    public ArrayList(V[] list){
        super();
        if (null != list)
            this.buffer = (Object[])list.clone();
    }


    public void init(){
    }
    public void destroy(){
        this.buffer = null;
    }
    public final void clearBuffer(){
        this.buffer = null;
    }
    public List.Short<V> clone(){
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
        this.buffer = null;
    }
    public boolean save()
        throws java.lang.InterruptedException
    {
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
    public List.Short<V> add(V instance){

        return this.addToBuffer(instance);
    }
    public int indexOf(V instance){

        return this.indexInBuffer(instance);
    }
    public V remove (V instance) {

        return this.removeFromBuffer(instance);
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
    public final List.Short<V> addToBuffer(V instance){
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
    public final V removeFromBuffer (V item) {

        int index = this.indexInBuffer(item);
        if (0 > index) {
            return null;
        }
        else {
            Object[] buffer = this.buffer;
            int len = buffer.length;
            int term = (len-1);
            Object[] copy = new Object[term];

            if (0 == index){
                System.arraycopy(buffer,1,copy,0,term);
            }
            else if (term == index){
                System.arraycopy(buffer,0,copy,0,term);
            }
            else {
                System.arraycopy(buffer,0,copy,0,index);
                System.arraycopy(buffer,(index+1),copy,index,(term-index));
            }
            this.buffer = copy;

            return item;
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
    protected void buffered(V instance, int index){
    }
    /**
     * @see gap.data.List$Short#nhead(int)
     */
    public Iterable<V> nhead(int count){
        final Object[] buffer = this.buffer;
        if (null != buffer){
            final int size = buffer.length;
            if (0 > count){
                count += size;
                if (0 > count)
                    return (Iterable<V>)(new BufferIterator());
            }

            if (count < size){
                V[] re = (V[])(new Object[count]);
                System.arraycopy(buffer,0,re,0,count);
                return (Iterable<V>)(new BufferIterator(re));
            }
            else
                return (Iterable<V>)(new BufferIterator(buffer));
        }
        else
            return (Iterable<V>)(new BufferIterator());
    }
    /**
     * @see gap.data.List$Short#ntail(int)
     */
    public Iterable<V> ntail(int count){
        final Object[] buffer = this.buffer;
        if (null != buffer){
            final int size = buffer.length;
            if (0 > count){
                count += size;
                if (0 > count)
                    return (Iterable<V>)(new BufferIterator());
            }

            if (count < size){
                final int x = (size-count-1);
                if (x < size){
                    V[] re = (V[])(new Object[count]);
                    System.arraycopy(buffer,x,re,0,count);
                    return (Iterable<V>)(new BufferIterator(re));
                }
                else
                    return (Iterable<V>)(new BufferIterator());
            }
            else
                return (Iterable<V>)(new BufferIterator(buffer));
        }
        else
            return (Iterable<V>)(new BufferIterator());
    }
    /**
     * @see gap.data.List$Short#xhead(int)
     */
    public Iterable<V> xhead(int count){
        return this.nhead(this.size()-count);
    }
    /**
     * @see gap.data.List$Short#xtail(int)
     */
    public Iterable<V> xtail(int count){
        return this.ntail(this.size()-count);
    }
}
