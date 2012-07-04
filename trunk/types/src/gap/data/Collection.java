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

import gap.util.Page;

import json.Json;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/**
 * Base class common to {@link List} and {@link Map}.
 * 
 * @author jdp
 */
public interface Collection<V>
    extends java.lang.Iterable<V>,
            java.lang.Comparable<Collection<V>>,
            java.io.Serializable,
            java.lang.Cloneable,
            DataInheritance.Notation
{
    /**
     * Set comparisons for comparable interface return value.
     */
    public final static class Compares {
        /**
         * less-than
         */
        public final static int Intersects = -1;
        /**
         * greater-than
         */
        public final static int NoIntersection = 1;
        /**
         * equal
         */
        public final static int Equivalent = 0;
    }

    /**
     * As a field type, "V" is a member of {@link gap.Primitive}.
     * This kind of list is an entity property value.
     */
    public interface PrimitiveC<V>
        extends Collection<V>
    {
        public int size();

        public boolean contains(V instance);

        public boolean containsNot(V instance);
    }

    /**
     * As a field type, "V" is a member of {@link gap.data.BigTable}.
     * This kind of list is an entity group from the containing
     * instance key.
     */
    public interface ShortC<V>
        extends Collection<V>
    {
        public Query getQuery();

        public Page getPage();

        public boolean hitEnd();

        public V fetch(Filter filter);
    }

    /**
     * As a field type, "V" is a member of {@link gap.data.BigTable}.
     * This kind of list is a collection of top level entities.
     */
    public interface LongC<V>
        extends Collection<V>
    {
        public Query getQuery();

        public Page getPage();

        public boolean hitEnd();

        /**
         * Get value from datastore without adding into the list buffer,
         * and without disturbing the relationship between the page and
         * the buffer.
         */
        public V fetch(Filter filter);
    }


    public Key getValueClassAncestorKey();
    /**
     * @return False when get key returns null
     */
    public boolean hasValueClassAncestorKey();
    /**
     * @return False for no key or incomplete key
     */
    public boolean isCompleteValueClassAncestorKey();

    public boolean isEmpty();

    public boolean isNotEmpty();

    public void init();

    public void destroy();

    /**
     * The argument type will be analogous to this type (list or map).
     * 
     * Generally, deletion is not translated into JSON update, where
     * it would be more than intended in some cases.  The partial
     * update of complex objects should be implemented.  A null or
     * empty collection may be a placeholder, not a deletion.
     * Deletion should be reserved for more explicit operations.
     * 
     * A non empty collection must be interpreted for its deletions
     * and additions, but a null or empty collection may be ignored as
     * having no effect on the state of storage.
     */
    public boolean fromJson(Json json);

    public Collection<V> clone();
}
