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
package yas.data;


import gap.*;
import gap.data.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.util.Date;

/**
 * Generated once (user) bean.
 * This source file will not be overwritten unless deleted,
 * so it can be edited for extensions.
 *
 * @see SourceData
 */
public final class Source
    extends SourceData
{
    private final static Page SourcesPage = new Page(0,99);
    private final static Query SourcesQuery = Source.CreateQueryFor();
    /**
     * List sources
     */
    public final static BigTableIterator<Source> Sources(){
        return Source.QueryN(SourcesQuery,SourcesPage);
    }

    public final static Source SourceFor(lxl.Map<String,Source> cache, String twid){
        Source source = cache.get(twid);
        if (null == source){
            source = Source.ForLongTwitterId(twid);
            if (null != source)
                cache.put(twid,source);
        }
        return source;
    }


    public Source() {
        super();
    }
    public Source(String twitterId) {
        super( twitterId);
    }



    public void onread(){
    }
    public void onwrite(){
    }
    public java.io.Serializable valueOf(gap.data.Field field, boolean mayInherit){
        return (java.io.Serializable)Field.Get((Field)field,this,mayInherit);
    }
    public void define(gap.data.Field field, java.io.Serializable value){
        Field.Set((Field)field,this,value);
    }
    public void drop(){
        Delete(this);
    }
    public void clean(){
        Clean(this);
    }
    public void save(){
        Save(this);
    }
    public void store(){
        Store(this);
    }
}