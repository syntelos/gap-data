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


import gap.data.*;
import gap.service.Templates;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated long list.
 */
@Generated(value={"gap.service.OD","odl/list-long.xtm"},date="2009-10-16T09:23:18.638Z")
public final class ListLongResourcePartner
    extends gap.util.AbstractList<Partner>
    implements gap.data.List.Long<Partner>
{

    private final static long serialVersionUID = 1;

    public final static String AncestorKeyFieldName = "key";

    public final static String ParentTypeName = "Resource";

    public final static String ChildTypeName = "Partner";


    protected transient Resource parent;


    public ListLongResourcePartner(Resource parent) {
        super();
        if (null != parent){
            this.parent = parent;
            this.ancestorKeyFieldName = AncestorKeyFieldName;
            this.query = Partner.CreateQueryFor();
        }
        else
            throw new IllegalArgumentException();
    }
    public ListLongResourcePartner(){
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
    public TemplateDictionary dictionaryInto(TemplateDictionary top){

        for (Partner value: this){

            value.dictionaryInto(top);
        }
        return top;
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary top, DictionaryInto.DataFilter filter){

        for (Partner value: this){

            value.dictionaryInto(top,filter);
        }
        return top;
    }
    public Partner fetch(Filter filter){
        Query query = Partner.CreateQueryFor(filter);
        return Partner.Query1(query);
    }
}