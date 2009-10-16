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
import gap.service.Templates;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated short map.
 */
@Generated(value={"gap.service.OD","odl/map-short.xtm"},date="2009-10-16T11:37:45.888Z")
public final class MapShortResourceStringTemplate
    extends gap.util.AbstractMap<String,Template>
    implements gap.data.Map.Short<String,Template>
{

    private final static long serialVersionUID = 1;

    public final static String AncestorKeyFieldName = "key";

    public final static String ParentTypeName = "Resource";

    public final static String ChildTypeName = "Template";

    public final static gap.Primitive MapKeyType = gap.Primitive.String;

    public final static String MapKeyFieldName = "name";

    public final static Template.Field MapKeyField = Template.Field.Name;


    protected transient Resource parent;


    public MapShortResourceStringTemplate(Resource parent) {
        super();
        if (null != parent){
            this.parent = parent;
            this.ancestorKeyFieldName = AncestorKeyFieldName;
            this.setValueClassAncestorKey();
        }
        else
            throw new IllegalArgumentException();
    }
    public MapShortResourceStringTemplate(){
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
        Key key = this.getParent().getClassFieldKeyValue();
        if (key != this.ancestorKey){
            this.ancestorKey = key;
            this.query = Template.CreateQueryFor(this.ancestorKey);
        }
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary top){

        for (Template value: this){

            value.dictionaryInto(top);
        }
        return top;
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary top, DictionaryInto.DataFilter filter){

        for (Template value: this){

            value.dictionaryInto(top,filter);
        }
        return top;
    }
    public Template fetch(Filter filter){
        Key ancestor = this.ancestorKey;
        if (null != ancestor){
            Query query = Template.CreateQueryFor(ancestor,filter);
            return Template.Query1(query);
        }
        else
            throw new IllegalStateException("Missing ancestor key.");
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
    public Template get(String mapKey){
        int index = this.index.get(mapKey);
        if (-1 != index)
            return (Template)this.buffer[index];
        else
            return null;
    }
}
