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

import gap.data.Kind;
import gap.data.List;
import gap.data.TableClass;

import com.google.appengine.api.datastore.Key;

/**
 * An ordered list with scan for index, or an unordered set with a
 * sorted index.
 * 
 * @see gap.service.Selection
 * @author jdp
 */
public class ListPrimitiveKey
    extends AbstractListPrimitive<Key>
{
    private final static long serialVersionUID = 5567372790939261769L;


    public final static boolean KeySet = false, KeyList = true;


    public final String kindName;

    private volatile transient Kind kind;

    public final boolean ordered, nordered;


    public ListPrimitiveKey(String kind){
        this(Kind.For(kind));
    }
    public ListPrimitiveKey(Kind kind){
        this(kind,false);
    }
    public ListPrimitiveKey(Kind kind, boolean ordered){
        this(null,kind,ordered);
    }
    public ListPrimitiveKey(gap.data.BigTable ancestor, Kind kind, boolean ordered){
        super(ancestor);
        this.ordered = ordered;
        this.nordered = (!ordered);
        if (null != kind){
            this.kind = kind;
            this.kindName = kind.name;
        }
        else
            throw new IllegalArgumentException();
    }


    public final gap.Primitive getType(){
        return gap.Primitive.Key;
    }
    public ListPrimitiveKey clone(){
        return (ListPrimitiveKey)super.clone();
    }
    public Kind getKind(){
        Kind kind = this.kind;
        if (null == kind){
            kind = Kind.For(this.kindName);
            this.kind = kind;
        }
        return kind;
    }
    public final Key keyIdFor(Object... args){
        return this.getKind().keyIdFor(args);
    }
    public final Key add(Object... args){
        Key key = this.keyIdFor(args);
        this.add(key);
        return key;
    }
    public final TableClass getTable(int idx){
        Key key = this.get(idx);
        return this.getKind().get(key);
    }
    public final TableClass getTable(Key key){
        return this.getKind().get(key);
    }
    @Override
    protected final List.Primitive<Key> added(){

        if (this.nordered)
            java.util.Arrays.sort(this.list);

        return this;
    }
    @Override
    protected final int appended(int idx){

        if (this.nordered)
            java.util.Arrays.sort(this.list);

        return idx;
    }
    @Override
    protected final Key updated(Key value){

        if (this.nordered)
            java.util.Arrays.sort(this.list);

        return value;
    }
    @Override
    public final int indexOf(Key instance){

        if (this.ordered)
            return super.indexOf(instance);
        else if (null != this.list)
            return java.util.Arrays.binarySearch(this.list,instance);
        else
            return -1;
    }
}
