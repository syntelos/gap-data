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
package gap.data;


import gap.*;
import gap.data.*;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated long map.
 */
@Generated(value={"gap.service.OD","map-long"},date="2009-10-30T07:25:06.111Z")
public final class MapLongResourceStringPartner
    extends gap.util.AbstractMap<String,Partner>
    implements gap.data.Map.Long<String,Partner>
{

    private final static long serialVersionUID = 1;

    public final static String AncestorKeyFieldName = "key";

    public final static String ParentTypeName = "Resource";

    public final static String ChildTypeName = "Partner";

    public final static gap.Primitive MapKeyType = gap.Primitive.String;

    public final static String MapKeyFieldName = "name";

    public final static Partner.Field MapKeyField = Partner.Field.Name;


    protected transient Resource parent;


    public MapLongResourceStringPartner(Resource parent) {
        super();
        if (null != parent){
            this.parent = parent;
            this.ancestorKeyFieldName = AncestorKeyFieldName;
            this.query = Partner.CreateQueryFor();
        }
        else
            throw new IllegalArgumentException();
    }
    public MapLongResourceStringPartner(){
        super();
    }


    public void destroy(){
        this.parent = null;
        this.clearBuffer();
    }
    public Resource getParent(){
        Resource parent = this.parent;
        if (null == parent){
            Key parentKey = this.ancestorKey;
            if (null != parentKey){
                parent = Resource.Get(parentKey);
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
    public void setValueClassAncestorKey(){
        this.ancestorKey = this.getParent().getClassFieldKeyValue();
    }
    public Partner fetch(Filter filter){
        Query query = Partner.CreateQueryFor(filter);
        return Partner.Query1(query);
    }
    public gap.Primitive getMapKeyType(){
        return MapKeyType;
    }
    public String getMapKeyFieldName(){
        return MapKeyFieldName;
    }
    protected void buffered(BigTable instance, int index){
        Comparable field = (Comparable)instance.valueOf(MapKeyField,MayNotInherit);
        this.index.add(field,index);
    }
    public Partner get(String mapKey){
        int index = this.index.get(mapKey);
        if (-1 != index)
            return (Partner)this.buffer[index];
        else
            return null;
    }
}
