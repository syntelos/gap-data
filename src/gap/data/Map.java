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
 * The map key for a mapped value is the value of a field of the
 * mapped value.
 * 
 * Keys are {@link gap.Primitive primitive} types.
 * 
 * @author jdp
 */
public interface Map<K,V>
    extends Collection<V>
{
    public static enum Type {
        MapPrimitive("Map.Primitive",gap.data.Map.Primitive.class),
        MapShort("Map.Short",gap.data.Map.Short.class),
        MapLong("Map.Long",gap.data.Map.Long.class);


        public final String dotName;
        public final Class type;

        private Type(String dotName, Class impl){
            this.dotName = dotName;
            this.type = impl;
        }



        public final static boolean Is(String name){
            return (null != Map.Type.For(name));
        }
        public final static boolean Is(Class type){
            return (null != ClassMap.get(type));
        }
        private final static java.util.Map<Class,Map.Type> ClassMap = new java.util.HashMap<Class,Map.Type>();
        static {
            for (Map.Type type : Map.Type.values()){
                ClassMap.put(type.type,type);
            }
        }
        public final static Map.Type For(Class type){
            return ClassMap.get(type);
        }
        private final static java.util.Map<String,Map.Type> DotNameMap = new java.util.HashMap<String,Map.Type>();
        static {
            for (Map.Type type : Map.Type.values()){
                DotNameMap.put(type.dotName,type);
            }
        }
        public final static Map.Type For(String name){
            name = gap.service.OD.CleanTypeName(name);
            Map.Type type = DotNameMap.get(name);
            if (null != type)
                return type;
            else {
                try {
                    return Map.Type.valueOf(name);
                }
                catch (IllegalArgumentException exc){
                    return null;
                }
            }
        }
    }

    /**
     * Keys and values are both primitive types.
     */
    public interface Primitive<K,V>
        extends Map<K,V>, Collection.PrimitiveC<V>
    {
        public Map<K,V> add(K key);

        public Map<K,V> remove(K key);
    }

    public interface Short<K,V>
        extends Map<K,V>, Collection.ShortC<V>
    {
        public gap.Primitive getMapKeyType();

        public String getMapKeyFieldName();
    }

    public interface Long<K,V>
        extends Map<K,V>, Collection.LongC<V>
    {
        public gap.Primitive getMapKeyType();

        public String getMapKeyFieldName();
    }

    public V get(K key);
}
