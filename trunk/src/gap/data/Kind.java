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

    private final static lxl.Map<String,Kind> ByKind = new lxl.Map<String,Kind>(64);

    private final static lxl.Index<String> ByPath = new lxl.Index<String>(64);


    public static Kind Create(String name, String pkg, String clan){
        return Create(name,pkg,clan,"/anon");
    }
    public static Kind Create(String name, String pkg, String clan, String path){
        Kind kind = ByKind.get(name);
        if (null == kind){
            kind = new Kind(name,pkg,clan,path);
            int idx = ByKind.put2(name,kind);
            ByPath.put(kind.path,idx);
        }
        else if ((!kind.className.equals(clan))||(!kind.packageName.equals(pkg))){

            throw new IllegalArgumentException("Kind name conflict in '"+name+"' from '"+pkg+'.'+clan+"' with '"+kind.fullClassName+"'.");
        }
        return kind;
    }
    public static boolean Has(String name){
        return ByKind.containsKey(name);
    }
    public static Kind For(String name){
        return ByKind.get(name);
    }
    public static Kind For(gap.service.Path path){
        String string = path.full;
        int kind = ByPath.get(string);
        if (-1 == kind){
            do {
                int idx = string.lastIndexOf('/');
                string = string.substring(0,idx);
                if (0 < string.length()){
                    kind = ByPath.get(string);
                    if (-1 != kind)
                        return ByKind.get(kind);
                }
                else
                    return null;
            }
            while (true);
        }
        return ByKind.get(kind);
    }
    /** 
     * @param path Relative path expression (as in the HTTP request
     * line) having only path component.
     * @return Leading path separator, no trailing path separator ('/')
     */
    public static String PathNormal(String path){
        if (null == path || 0 == path.length())
            return null;
        else {
            if ('/' != path.charAt(0))
                path = "/"+path;

            int term = (path.length()-1);

            if (0 < term && '/' == path.charAt(term))
                path = path.substring(0,term);

            return path;
        }
    }


    public final String name, packageName, className, fullClassName, path;

    public final int hashCode;


    private Kind(String name, String packageName, String className, String path){
        super();
        if (null != name && null != packageName && null != className && null != path){
            this.name = name;
            this.hashCode = name.hashCode();
            this.packageName = packageName;
            this.className = className;
            this.fullClassName = packageName+'.'+className;
            this.path = PathNormal(path);
        }
        else
            throw new IllegalArgumentException();
    }


    public String getName(){
        return this.name;
    }
    public Class<? extends BigTable> getTableClass()
        throws java.lang.ClassNotFoundException
    {
        try {
            return (Class<? extends BigTable>)Class.forName(this.fullClassName);
        }
        catch (ClassNotFoundException exc){
            throw new ClassNotFoundException(this.name,exc);
        }
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