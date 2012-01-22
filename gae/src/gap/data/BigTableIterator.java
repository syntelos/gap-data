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
import gap.util.Page;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;

import java.util.Iterator;

/**
 * @see Store#QueryN
 * @author jdp
 */
public final class BigTableIterator<V extends BigTable>
    extends Object
    implements Iterator<V>,
               Iterable<V>
{
    public final int gross;

    public final boolean hitEnd;

    private final Iterator<Entity> ds;


    /**
     * Query for keys only
     */
    BigTableIterator(PreparedQuery stmt, Page page){
        super();
        
        this.gross = stmt.countEntities();

        this.hitEnd = (this.gross > page.count);

        Iterable<Entity> ds = stmt.asIterable(page.createFetchOptions());

        this.ds = ds.iterator();
    }
    /**
     * Query for keys only
     */
    BigTableIterator(PreparedQuery stmt){
        super();
        
        this.gross = stmt.countEntities();

        this.hitEnd = false;

        Iterable<Entity> ds = stmt.asIterable();

        this.ds = ds.iterator();
    }


    public int getGross(){
        return this.gross;
    }
    public boolean hitEnd(){
        return this.hitEnd;
    }
    public boolean hasNext(){
        return this.ds.hasNext();
    }
    public V next(){

        Entity entity = this.ds.next();

        return (V)Store.GetClass(entity.getKey());
    }
    public void remove(){
        throw new java.lang.UnsupportedOperationException();
    }
    public Iterator<V> iterator(){
        return this;
    }
}
