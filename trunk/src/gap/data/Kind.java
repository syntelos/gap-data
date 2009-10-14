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

/**
 * Dynamic set of data types according to "kind" name.
 * 
 * <h3>Not Serializable</h3>
 * 
 * As a dynamical enum, deserialization would break the in-memory
 * uniqueness property of instances of this class.
 * 
 * @author jdp
 */
public final class Kind
    extends Object
    implements java.lang.Comparable<Kind>,
               HasName
{

    private final static java.util.Map<String,Kind> ByKind = new java.util.HashMap<String,Kind>();

    public static Kind Create(String kindName, String pkg, String clan){
        Kind kind = ByKind.get(kindName);
        if (null == kind){
            kind = new Kind(kindName,pkg,clan);
            ByKind.put(kindName,kind);
        }
        else if ((!kind.className.equals(clan))||(!kind.packageName.equals(pkg))){

            throw new IllegalArgumentException("Kind name conflict in '"+kindName+"' from '"+pkg+'.'+clan+"' with '"+kind.fullClassName+"'.");
        }
        return kind;
    }
    //  public static Kind Rename(String name, String pkg, String clan){
    //      throw new java.lang.UnsupportedOperationException();
    //  }
    public static boolean Has(String kindName){
        return ByKind.containsKey(kindName);
    }
    public static Kind For(String kindName){
        return ByKind.get(kindName);
    }

    public static java.util.Collection<Kind> Iterable(){
        return ByKind.values();
    }


    public final String name, packageName, className, fullClassName;

    public final int hashCode;


    private Kind(String name, String packageName, String className){
        super();
        if (null != name && null != packageName && null != className){
            this.name = name;
            this.hashCode = name.hashCode();
            this.packageName = packageName;
            this.className = className;
            this.fullClassName = packageName+'.'+className;
        }
        else
            throw new IllegalArgumentException();
    }


    public String getName(){
        return this.name;
    }
    public String toString(){
        return this.name;
    }
    public int hashCode(){
        return this.hashCode;
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (that instanceof Kind)
            return this.name.equals(((Kind)that).name);
        else
            return false;
    }
    public boolean equalsFull(Kind that){
        if (this == that)
            return true;
        else if (null != that)
            return this.fullClassName.equals(that.fullClassName);
        else
            return false;
    }
    public boolean notEqualsFull(Kind that){
        if (this == that)
            return false;
        else if (null != that)
            return (!this.fullClassName.equals(that.fullClassName));
        else
            return true;
    }
    public int compareTo(Kind kind){
        if (null == kind)
            return 1;
        else
            return this.name.compareTo(kind.name);
    }
}