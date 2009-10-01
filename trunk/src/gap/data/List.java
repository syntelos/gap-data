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
 * @author jdp
 */
public interface List<V>
    extends Collection<V>
{
    public enum Type {
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
            List.Type type = DotNameMap.get(name);
            if (null != type)
                return type;
            else
                return List.Type.valueOf(name);
        }
    }

    public interface Primitive<V>
        extends List<V>, Collection.PrimitiveC<V>
    {
        public List<V> add(V instance);

        public List<V> remove(V instance);
    }

    public interface Short<V>
        extends List<V>, Collection.ShortC<V>
    {
        public List<V> add(V instance);

        public List<V> remove(V instance);
    }

    public interface Long<V>
        extends List<V>, Collection.LongC<V>
    {
    }

    public V get(int index);

}
