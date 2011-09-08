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

    protected Query query;

    protected Page page = Page.Default;

    protected transient BigTable[] buffer;

    protected transient boolean fillable = true;

    private transient int gross;

    private transient boolean hitEnd;


    protected AbstractList(){
        super();
    }


    public final void init(){
        this.fill();
    }
    /**
     * Attempt buffer fill once only per instance.
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
        Query query = this.query;
        if (null != query){
            Page page = this.page;
            this.buffering(page);
            BigTableIterator<BigTable> iterable = Store.QueryN(query,page);
            this.gross = iterable.gross;
            this.hitEnd = iterable.hitEnd;
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

    public abstract String getParentTypeName();

    public abstract String getChildTypeName();

    public abstract Class getParentTypeClass();

    public abstract Class getChildTypeClass();

    /**
     * Get value from datastore without adding into the list buffer,
     * and without disturbing the relationship between the page and
     * the buffer.
     */
    public abstract V fetch(Filter filter);

    public final Query getQuery(){
        return this.query;
    }
    public final int getStartIndex(){
        return this.page.startIndex;
    }
    public final int getLimit(){
        return this.page.count;
    }
    public final int getGross(){
        return this.gross;
    }
    public final boolean getHitEnd(){
        return this.hitEnd;
    }
    public final boolean hitEnd(){
        return this.hitEnd;
    }
    public final Page getPage(){
        return this.page;
    }
    public final void setPage(Page page){
        if (null != page)
            this.page = page;
        else
            throw new IllegalArgumentException();
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
    public V set(int index, V value){
        if (-1 < index){
            BigTable[] buffer = this.buffer;
            if (null != buffer && index < buffer.length){
                buffer[index] = value;
                return value;
            }
        }
        throw new java.lang.ArrayIndexOutOfBoundsException(String.valueOf(index));
    }
    public List<V> clone(){
        try {
            AbstractList<V> clone = (AbstractList<V>)super.clone();
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
    protected void buffering(Page page){
    }
    protected void buffered(BigTable instance, int index){
    }
    public final List<V> addToBuffer(BigTable instance){
        if (null != instance){
            BigTable[] buffer = this.buffer;
            if (null == buffer){
                this.buffer = new BigTable[]{instance};
                this.buffered(instance,0);
            }
            else {
                int len = buffer.length;
                BigTable[] copier = new BigTable[len+1];
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
    public final V removeFromBuffer (V item) {

        int index = this.indexInBuffer(item);
        if (0 > index) {
            return null;
        }
        else {
            BigTable[] buffer = this.buffer;
            int len = buffer.length;
            int term = (len-1);
            BigTable[] copy = new BigTable[term];

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
    public void drop(){
        BigTable[] buffer = this.buffer;
        if (null != buffer){
            for (int cc = 0, count = buffer.length; cc < count; cc++){
                BigTable instance = buffer[cc];
                instance.drop();
            }
            this.buffer = null;
        }
    }
    public boolean save(long timeout)
        throws java.lang.InterruptedException
    {
        BigTable[] buffer = this.buffer;
        if (null != buffer){
            Lock lock = this.getParent().getLock();
            if (lock.enter(timeout)){
                try {
                    for (int cc = 0, count = buffer.length; cc < count; cc++){
                        BigTable instance = buffer[cc];
                        instance.save();
                    }
                    return true;
                }
                finally {
                    lock.exit();
                }
            }
            else
                return false;
        }
        else
            return true;
    }
    public List<V> add(V instance){

        return this.addToBuffer(instance);
    }
    public int indexOf(V instance){

        return this.indexInBuffer(instance);
    }
    public V remove (V instance) {

        return this.removeFromBuffer(instance);
    }
    /**
     * @see gap.data.List$Short#nhead(int)
     */
    public V[] nhead(int count){
        final Object[] buffer = this.buffer;
        if (null != buffer){
            final int size = buffer.length;
            if (0 > count){
                count += size;
                if (0 > count)
                    return (V[])(new Object[0]);
            }

            if (count < size){
                V[] re = (V[])(new Object[count]);
                System.arraycopy(buffer,0,re,0,count);
                return re;
            }
            else
                return (V[])buffer;
        }
        else
            return (V[])(new Object[0]);
    }
    /**
     * @see gap.data.List$Short#ntail(int)
     */
    public V[] ntail(int count){
        final Object[] buffer = this.buffer;
        if (null != buffer){
            final int size = buffer.length;
            if (0 > count){
                count += size;
                if (0 > count)
                    return (V[])(new Object[0]);
            }

            if (count < size){
                final int x = (size-count);
                if (x < size){
                    V[] re = (V[])(new Object[count]);
                    System.arraycopy(buffer,x,re,0,count);
                    return re;
                }
                else
                    return (V[])(new Object[0]);
            }
            else
                return (V[])buffer;
        }
        else
            return (V[])(new Object[0]);
    }
    /**
     * @see gap.data.List$Short#xhead(int)
     */
    public V[] xhead(int count){
        return this.nhead(this.size()-count);
    }
    /**
     * @see gap.data.List$Short#xtail(int)
     */
    public V[] xtail(int count){
        return this.ntail(this.size()-count);
    }
}
