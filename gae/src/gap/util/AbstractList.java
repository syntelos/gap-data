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

import json.Json;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/**
 * Short and long list base class.  Instances are generated via {@link
 * gap.service.OD}.
 * 
 * @author jdp
 */
public abstract class AbstractList<V extends TableClass>
    extends Object
    implements List<V>
{

    /**
     * Buffer iterator. 
     */
    public final class BufferIterator<V>
        extends Object
        implements java.lang.Iterable<V>,
                   java.util.Iterator<V>
    {
        private final TableClass[] buffer;
        private final int count;
        private int index;


        public BufferIterator(){
            super();
            this.buffer = null;
            this.count = 0;
        }
        public BufferIterator(TableClass[] buffer){
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


    protected Key ancestorKey;

    protected Query query;

    protected Page page = null;

    protected transient TableClass[] buffer;

    protected transient boolean fillable = true;

    protected transient int gross;

    protected transient boolean hitEnd;


    protected AbstractList(){
        super();
    }


    public void init(){
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
            BigTableIterator<V> iterable = Store.QueryNClass(query,page);
            this.gross = iterable.gross;
            this.hitEnd = iterable.hitEnd;
            this.clearBuffer();
            for (V table: iterable){
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
     * Get value from datastore without adding into this list buffer,
     * and without disturbing the relationship between this page and
     * this buffer.
     */
    public abstract V fetch(Filter filter);
    /**
     * List values from datastore without touch this list buffer, and
     * without disturbing the relationship between this page and this
     * buffer.
     */
    public abstract Iterable<V> list(Filter filter, Page page);

    public final Query getQuery(){
        return this.query;
    }
    public final int getStartIndex(){
        Page page = this.page;
        if (null == page)
            return 0;
        else
            return this.page.startIndex;
    }
    public final int getLimit(){
        Page page = this.page;
        if (null == page)
            return this.size();
        else
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
    public final boolean hasPage(){
        return (null != this.page);
    }
    public final Page getPage(){
        return this.page;
    }
    public final void setPage(Page page){
        this.page = page;
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
        TableClass[] buffer = this.buffer;
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
            TableClass[] buffer = this.buffer;
            if (null != buffer && index < buffer.length)
                return (V)buffer[index];
        }
        throw new java.lang.ArrayIndexOutOfBoundsException(String.valueOf(index));
    }
    public V set(int index, V value){
        if (-1 < index){
            TableClass[] buffer = this.buffer;
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
                clone.buffer = (TableClass[])this.buffer.clone();
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
                    return Compares.Intersects;
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
    /**
     * @param page Default null, dynamic buffering not paging
     */
    protected void buffering(Page page){
    }
    protected void buffered(V instance, int index){
    }
    public final List<V> addToBuffer(V instance){
        if (null != instance){
            TableClass[] buffer = this.buffer;
            if (null == buffer){
                this.buffer = new TableClass[]{instance};
                this.buffered(instance,0);
            }
            else {
                int len = buffer.length;
                TableClass[] copier = new TableClass[len+1];
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
        TableClass[] buffer = this.buffer;
        if (null != buffer){
            for (int cc = 0, count = buffer.length; cc < count; cc++){
                TableClass item = buffer[cc];
                if (instance == item || item.equals(instance))
                    return cc;
            }
        }
        return -1;
    }
    public final int indexInBuffer(Key key){
        if (null != key){
            TableClass[] buffer = this.buffer;
            if (null != buffer){
                for (int cc = 0, count = buffer.length; cc < count; cc++){
                    TableClass item = buffer[cc];

                    if (BigTable.Equals(key,item.getKey()))
                        return cc;
                }
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
            TableClass[] buffer = this.buffer;
            int len = buffer.length;
            int term = (len-1);
            TableClass[] copy = new TableClass[term];

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
        TableClass[] buffer = this.buffer;
        if (null != buffer){
            for (int cc = 0, count = buffer.length; cc < count; cc++){
                TableClass instance = buffer[cc];
                instance.drop();
            }
            this.buffer = null;
        }
    }
    public boolean save(long timeout)
        throws java.lang.InterruptedException
    {
        TableClass[] buffer = this.buffer;
        if (null != buffer){
            Lock lock = this.getParent().getLock();
            if (lock.enter(timeout)){
                try {
                    for (int cc = 0, count = buffer.length; cc < count; cc++){

                        TableClass instance = buffer[cc];

                        if (instance.isDirty()){

                            instance.save();
                        }
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
    public boolean save()
        throws java.lang.InterruptedException
    {
        TableClass[] buffer = this.buffer;
        if (null != buffer){
            Lock lock = this.getParent().getLock();
            if (lock.enter()){
                try {
                    for (int cc = 0, count = buffer.length; cc < count; cc++){

                        TableClass instance = buffer[cc];

                        if (instance.isDirty()){

                            instance.save();
                        }
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
    public abstract Iterable<V> nhead(int count);
    /**
     * @see gap.data.List$Short#ntail(int)
     */
    public abstract Iterable<V> ntail(int count);
    /**
     * @see gap.data.List$Short#xhead(int)
     */
    public Iterable<V> xhead(int count){
        return this.nhead(this.gross-count);
    }
    /**
     * @see gap.data.List$Short#xtail(int)
     */
    public Iterable<V> xtail(int count){
        return this.ntail(this.gross-count);
    }
    public boolean fromJson(Json json){
        throw new UnsupportedOperationException();
    }
}
