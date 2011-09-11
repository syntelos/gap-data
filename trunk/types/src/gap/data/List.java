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
 * A list has features of a set, it should never contain an element
 * twice.  
 * 
 * Short and long lists are defined by parent (containing) class and
 * key, list type, and child class (list class parameter type).  A
 * parent or containing class cannot have two lists of the same class.
 * 
 * @author jdp
 */
public interface List<V>
    extends Collection<V>
{
    public static enum Type {
        ListPrimitive("List.Primitive",gap.data.List.Primitive.class),
        ListShort("List.Short",gap.data.List.Short.class),
        ListLong("List.Long",gap.data.List.Long.class);


        public final String dotName;
        public final Class type;

        private Type(String dotName, Class impl){
            this.dotName = dotName;
            this.type = impl;
        }



        public final static boolean Is(String name){
            return (null != List.Type.For(name));
        }
        public final static boolean Is(Class type){
            return (null != ClassMap.get(type));
        }
        private final static java.util.Map<Class,List.Type> ClassMap = new java.util.HashMap<Class,List.Type>();
        static {
            for (List.Type type : List.Type.values()){
                ClassMap.put(type.type,type);
            }
        }
        public final static List.Type For(Class type){
            return ClassMap.get(type);
        }
        private final static java.util.Map<String,List.Type> DotNameMap = new java.util.HashMap<String,List.Type>();
        static {
            for (List.Type type : List.Type.values()){
                DotNameMap.put(type.dotName,type);
            }
        }
        public final static List.Type For(String name){
            name = gap.service.Classes.CleanTypeName(name);
            List.Type type = DotNameMap.get(name);
            if (null != type)
                return type;
            else {
                try {
                    return List.Type.valueOf(name);
                }
                catch (IllegalArgumentException exc){
                    return null;
                }
            }
        }
    }

    /**
     * This list is a serialized object (value blob)
     */
    public interface Primitive<V>
        extends List<V>, Collection.PrimitiveC<V>
    {
        /**
         * Add to list
         */
        public List.Primitive<V> add(V instance);
        /**
         * Remove from list
         */
        public List.Primitive<V> remove(V instance);

        public List.Primitive<V> clear();
    }

    /**
     * This list is a buffer for a query result
     */
    public interface Short<V>
        extends List<V>, Collection.ShortC<V>
    {
        public void drop();

        /**
         * Add to buffer, no persistent effect 
         */
        public List.Short<V> add(V instance);

        /**
         * @return False on failure to lock collection parent.
         */
        public boolean save(long timeout)
            throws java.lang.InterruptedException;

        public List.Short<V> clone();
        /**
         * In memory operation
         */
        public int indexOf(V value);
        /**
         * Remove from buffer, no persistent effect
         */
        public V remove(V value);
        /**
         * Inclusive head
         * @return First count members of buffer, or array of size zero
         */
        public Iterable<V> nhead(int count);
        /**
         * Inclusive tail
         * @return Last count members of buffer, or array of size zero
         */
        public Iterable<V> ntail(int count);
        /**
         * Exclusive head
         * @return First (size-count) members of buffer, or array of size zero
         */
        public Iterable<V> xhead(int count);
        /**
         * Exclusive tail
         * @return Last (size-count) members of buffer, or array of size zero
         */
        public Iterable<V> xtail(int count);
    }

    /**
     * This list is a buffer for a query result
     */
    public interface Long<V>
        extends List<V>, Collection.LongC<V>
    {
    }

    /**
     * Size in memory list buffer.
     * 
     * @see Collection#hitEnd()
     */
    public int size();
    /**
     * Index in memory list buffer.
     * 
     * @see Collection#hitEnd()
     */
    public V get(int index);
    /**
     * For list buffer operations as in deep cloning.
     * 
     * @return Previous value
     */
    public V set(int index, V value);

    public List<V> clone();

}
