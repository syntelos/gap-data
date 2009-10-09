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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

/**
 * Short and long list base class.  Instances are generated via {@link
 * gap.service.OD}.
 * 
 * @author jdp
 */
public abstract class AbstractList<V extends BigTable>
    extends Object
    implements List<V>
{

    /**
     * Buffer iterator. 
     */
    public final class BufferIterator<V>
        extends Object
        implements java.util.Iterator<V>
    {
        private final BigTable[] buffer;
        private final int count;
        private int index;


        public BufferIterator(BigTable[] buffer){
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

    protected transient BigTable[] buffer;

    protected Query query;

    protected int startIndex = 0;

    protected int limit = gap.service.Parameters.Special.Page.Default;

    protected boolean fillable = true;


    protected AbstractList(){
        super();
    }


    public final void init(){
        this.fill();
    }
    /**
     * Attempt buffer fill once only.
     */
    public final List<V> fill(){
        if (this.fillable){
            this.fillable = false;
            return this.refill();
        }
        else
            return this;
    }
    /**
     * Attempt buffer fill every time
     */
    public final List<V> refill(){
        Query query = this.getQuery();
        if (null != query){
            FetchOptions page = FetchOptions.Builder.withLimit(this.limit).offset(this.startIndex);

            Iterable<BigTable> iterable = Store.QueryNIterable(query,page);
            this.clearBuffer();
            for (BigTable table: iterable){
                this.addToBuffer(table);
            }
            return this;
        }
        else
            throw new IllegalStateException("Missing query.");
    }
    public abstract void destroy();

    public abstract BigTable getParent();

    public final Query getQuery(){
        return this.query;
    }
    public final int getStartIndex(){
        return this.startIndex;
    }
    public final void setStartIndex(int startIndex){
        if (0 < startIndex)
            this.startIndex = startIndex;
        else
            throw new IllegalArgumentException(String.valueOf(startIndex));
    }
    public final int getLimit(){
        return this.limit;
    }
    public final void setLimit(int limit){
        if (0 < limit)
            this.limit = limit;
        else
            throw new IllegalArgumentException(String.valueOf(limit));
    }
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
    public abstract void setValueClassAncestorKey();

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
        BigTable[] buffer = this.buffer;
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
    public V get(int index){
        if (-1 < index){
            BigTable[] buffer = this.buffer;
            if (null != buffer && index < buffer.length)
                return (V)buffer[index];
        }
        throw new java.lang.ArrayIndexOutOfBoundsException(String.valueOf(index));
    }
    public AbstractList clone(){
        try {
            AbstractList clone = (AbstractList)super.clone();
            if (null != this.buffer)
                clone.buffer = (BigTable[])this.buffer.clone();
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
    public final void clearBuffer(){
        this.buffer = null;
    }
    public final List<V> addToBuffer(BigTable instance){
        if (null != instance){
            BigTable[] buffer = this.buffer;
            if (null == buffer)
                this.buffer = new BigTable[]{instance};
            else {
                int len = buffer.length;
                BigTable[] copier = new BigTable[len+1];
                System.arraycopy(buffer,0,copier,0,len);
                copier[len] = instance;
                this.buffer = copier;
            }
            return this;
        }
        else
            throw new IllegalArgumentException();
    }
    public final int indexInBuffer(BigTable instance){
        BigTable[] buffer = this.buffer;
        if (null != buffer){
            for (int cc = 0, count = buffer.length; cc < count; cc++){
                BigTable item = buffer[cc];
                if (instance == item || item.equals(instance))
                    return cc;
            }
        }
        return -1;
    }
}
