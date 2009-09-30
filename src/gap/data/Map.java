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
    extends Iterable<V>
{

    public interface Short<K,V>
        extends Map<K,V>
    {
        public Key getAncestor();

        public Map<K,V> add(V value);

        public Map<K,V> remove(K key);
    }

    public interface Long<K,V>
        extends Map<K,V>
    {
        public Query getQuery();

        public int getStartIndex();

        public int getCount();
    }


    public String getValueClassKeyFieldName();

    public int size();

    public boolean isEmpty();

    public boolean isNotEmpty();

    public V get(K key);
}
