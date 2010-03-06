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
package gap.jbx;

/**
 * Interface representing a set of key/value pairs.
 *
 * @author gbrown
 * @author jdp
 */
public interface Dictionary<K, V>
    extends Iterable<K>
{

    public class Utils<K,V>
        extends java.util.HashMap<K,V>
        implements Dictionary<K,V>
    {
        public Utils(){
            super();
        }

        public java.util.Iterator<K> iterator(){
            return this.keySet().iterator();
        }
    }


    /**
     * Retrieves the value for the given key.
     *
     * @param key
     * The key whose value is to be returned.
     *
     * @return
     * The value corresponding to <tt>key</tt>, or null if the key does not
     * exist. Will also return null if the key refers to a null value.
     * Use <tt>containsKey()</tt> to distinguish between these two cases.
     */
    public V get(Object key);

    /**
     * Sets the value of the given key, creating a new entry or replacing the
     * existing value.
     *
     * @param key
     * The key whose value is to be set.
     *
     * @param value
     * The value to be associated with the given key.
     */
    public V put(K key, V value);

    /**
     * Removes a key/value pair from the map.  This method should
     * throw an instance of {@link PropertyNotFoundException} if it is
     * unable to perform the requested operation due to an
     * unrecognized name.
     *
     * @param key
     * The key whose mapping is to be removed.
     *
     * @return
     * The value that was removed.
     */
    public V remove(Object key);

    /**
     * Tests the existence of a key in the dictionary.
     *
     * @param key
     * The key whose presence in the dictionary is to be tested.
     *
     * @return
     * <tt>true</tt> if the key exists in the dictionary; <tt>false</tt>,
     * otherwise.
     */
    public boolean containsKey(Object key);

    /**
     * Tests the emptiness of the dictionary.
     *
     * @return
     * <tt>true</tt> if the dictionary contains no keys; <tt>false</tt>,
     * otherwise.
     */
    public boolean isEmpty();
}
