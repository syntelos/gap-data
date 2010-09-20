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
 * Short and long list base class.  Instances are generated via {@link
 * gap.service.OD}.
 * 
 * @author jdp
 */
public abstract class AbstractMap<K extends Comparable,V extends BigTable>
    extends AbstractList<V>
    implements Map<K,V>
{

    protected transient Index index;


    protected AbstractMap(){
        super();
    }


    protected void buffering(Page page){
        this.index = new Index(page);
    }
    public Map<K,V> clone(){
        AbstractMap<K,V> clone = (AbstractMap<K,V>)super.clone();
        if (null != this.index)
            clone.index = (Index)this.index.clone();
        return clone;
    }
}
