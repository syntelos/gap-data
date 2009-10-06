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

import gap.service.Logon;

import com.google.appengine.api.datastore.Entity;

import java.util.Iterator;

/**
 * Implemented by an Enum for the fields of a persistent data class.
 * 
 * @see oso.data.Activity
 * @see oso.data.Message
 * @see oso.data.MessageCollection
 * @see oso.data.Person
 * @author jdp
 */
public final class BigTableIterator
    extends Object
    implements Iterator<BigTable>,
               Iterable<BigTable>
{

    private final Iterator<Entity> ds;


    BigTableIterator(Iterable<Entity> ds){
        super();
        this.ds = ds.iterator();
    }

    public boolean hasNext(){
        return this.ds.hasNext();
    }
    public BigTable next(){
        Entity entity = this.ds.next();
        BigTable gdo = BigTable.From(entity);
        if (gdo instanceof AdminReadWrite){

            if (!Logon.IsAdmin())
                throw new AdminAccessException();
        }
        gdo.setFromDatastore();
        gdo.onread();
        return gdo;
    }
    public void remove(){
        throw new java.lang.UnsupportedOperationException();
    }
    public Iterator<BigTable> iterator(){
        return this;
    }
}
