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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/**

 * 
 * @author jdp
 */
public interface Map<K,V>
    extends Collection<V>
{

    public interface Short<K,V>
        extends Map<K,V>, Collection.Short<V>
    {
        public Map<K,V> add(K key);

        public Map<K,V> remove(K key);
    }

    public interface Long<K,V>
        extends Map<K,V>, Collection.Long<V>
    {
    }

    public V get(K key);
}
