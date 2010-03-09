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
 * 
 * @author jdp
 */
public abstract class AbstractMapPrimitive<K,V>
    extends AbstractListPrimitive<V>
    implements Map.Primitive<K,V>
{

    protected transient Index index = new Index(23);


    protected AbstractMapPrimitive(){
        super();
    }


    public AbstractMapPrimitive clone(){
        AbstractMapPrimitive clone = (AbstractMapPrimitive)super.clone();
        if (null != this.index)
            clone.index = this.index.clone();
        return clone;
    }
}
