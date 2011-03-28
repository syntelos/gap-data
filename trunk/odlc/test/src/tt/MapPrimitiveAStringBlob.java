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


import gap.*;
import gap.data.*;
import gap.service.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.*;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated primitive map.
 */
@Generated(value={"gap.service.OD","MapPrimitive.java"},date="2011-03-28T13:04:06.822Z")
public final class MapPrimitiveAStringBlob
    extends gap.util.AbstractMapPrimitive<String,Blob>
    implements gap.data.Map.Primitive<String,Blob>
{

    private final static long serialVersionUID = 1;

    public final static String ParentTypeName = "A";

    public final static String ChildTypeName = "Blob";

    public final static Class ParentTypeClass = A.class;

    public final static Class ChildTypeClass = Blob.class;

    public final static gap.Primitive MapKeyType = gap.Primitive.String;

    public final static gap.Primitive MapValueType = gap.Primitive.Blob;




    protected transient A parent;


    public MapPrimitiveAStringBlob(A parent) {
        this();
        if (null != parent){
            this.parent = parent;
            this.setValueClassAncestorKey();
        }
        else
            throw new IllegalArgumentException();
    }
    public MapPrimitiveAStringBlob(){
        super();
    }


    public void destroy(){
        this.parent = null;
        super.destroy();
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
        }
    }
    public gap.Primitive getMapKeyType(){
        return MapKeyType;
    }
    public gap.Primitive getType(){
        return MapValueType;
    }
    public Blob get(String key){
        int index = this.index.get(key);
        if (-1 != index)
            return (Blob)this.list[index];
        else
            return null;
    }
    public Map.Primitive<String,Blob> put(String key, Blob value){
        int index = this.index.get(key);
        if (-1 != index)
            this.list[index] = value;
        else {
            index = super.append(value);
            this.index.put(key,index);
        }
        return this;
    }
    public Blob remove(String key){
        int index = this.index.get(key);
        if (-1 != index){
            this.index.drop(key,index);
            Blob value = (Blob)this.list[index];
            super.remove(index);
            return value;
        }
        else
            return null;
    }
}
