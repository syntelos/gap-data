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

 * 
 * @author jdp
 */
public abstract class AbstractListPrimitive<V>
    extends Object
    implements List.Primitive<V>
{
    private final static long serialVersionUID = 1L;


    public final class ArrayIterator<V>
        extends Object
        implements java.util.Iterator<V>
    {
        private final Object[] list;
        private final int count;
        private int index;


        public ArrayIterator(Object[] list){
            super();
            this.list = list;
            this.count = ((null == list)?(0):(list.length));
        }

        public boolean hasNext(){
            return (this.index < this.count);
        }
        public V next(){
            int index = this.index;
            if (index < this.count){
                V next = (V)this.list[index];
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

    protected Object[] list;



    protected AbstractListPrimitive(){
        super();
    }


    public void init(){
    }
    public void destroy(){
        this.list = null;
    }
    public abstract gap.Primitive getType();

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
        Object[] list = this.list;
        if (null == list)
            return 0;
        else
            return list.length;
    }
    public final boolean isEmpty(){
        return (0 == this.size());
    }
    public final boolean isNotEmpty(){
        return (0 != this.size());
    }
    public final boolean contains(V instance){
        return (-1 != this.indexOf(instance));
    }
    public final boolean containsNot(V instance){
        return (-1 == this.indexOf(instance));
    }
    public final List.Primitive<V> add(V instance){
        if (null != instance){
            Object[] list = this.list;
            if (null == list)
                this.list = new Object[]{instance};
            else {
                int len = list.length;
                Object[] copier = new Object[len+1];
                System.arraycopy(list,0,copier,0,len);
                copier[len] = instance;
                this.list = copier;
            }
            return this;
        }
        else
            throw new IllegalArgumentException();
    }
    protected final int append(V instance){
        if (null != instance){
            int idx = this.indexOf(instance);
            if (-1 == idx){
                Object[] list = this.list;
                if (null == list){
                    this.list = new Object[]{instance};
                    return 0;
                }
                else {
                    int len = list.length;
                    Object[] copier = new Object[len+1];
                    System.arraycopy(list,0,copier,0,len);
                    copier[len] = instance;
                    this.list = copier;
                    return len;
                }
            }
            else
                return idx;
        }
        else
            throw new IllegalArgumentException();
    }
    public final List.Primitive<V> remove(V instance){
        return this.remove(this.indexOf(instance));
    }
    public final List.Primitive<V> remove(int index){
        if (-1 != index){
            Object[] list = this.list;
            int len = list.length;
            int term = (len-1);
            Object[] copier = new Object[term];
            if (0 == index){
                System.arraycopy(list,1,copier,0,term);
                this.list = copier;
            }
            else if (term == index){
                System.arraycopy(list,0,copier,0,term);
                this.list = copier;
            }
            else {
                System.arraycopy(list,0,copier,0,index);
                System.arraycopy(list,(index+1),copier,index,term-index);
                this.list = copier;
            }
        }
        return this;
    }
    public final boolean has(int index){
        if (-1 < index){
            Object[] list = this.list;
            return (null != list && index < list.length);
        }
        else
            return false;
    }
    public final V get(int index){
        if (-1 < index){
            Object[] list = this.list;
            if (null != list && index < list.length){
                return (V)list[index];
            }
        }
        throw new java.lang.ArrayIndexOutOfBoundsException(String.valueOf(index));
    }
    public final V set(int index, V value){
        if (-1 < index){
            Object[] list = this.list;
            if (null != list && index < list.length){
                list[index] = value;
                return value;
            }
        }
        throw new java.lang.ArrayIndexOutOfBoundsException(String.valueOf(index));
    }
    public final java.util.Iterator<V> iterator(){
        return new ArrayIterator<V>(this.list);
    }
    public List<V> clone(){
        try {
            AbstractListPrimitive<V> clone = (AbstractListPrimitive<V>)super.clone();
            if (null != this.list)
                clone.list = (Object[])this.list.clone();
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
    protected final int indexOf(V instance){
        Object[] list = this.list;
        if (null != list){
            for (int cc = 0, count = list.length; cc < count; cc++){
                Object item = list[cc];
                if (instance == item || item.equals(instance))
                    return cc;
            }
        }
        return -1;
    }
}
