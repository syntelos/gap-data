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

import java.lang.reflect.Method;

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


    public static Kind Create(String name, String pkg, String clan, String path){
        Kind kind = ByKind.get(name);
        if (null == kind){
            kind = new Kind(name,pkg,clan,path);
            int idx = ByKind.put2(name,kind);
            ByPath.put(kind.pathName,idx);
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
    public static Kind ForPath(String path){
        if (null != path){
            int kind = ByPath.get(path);
            if (-1 != kind)
                return ByKind.get(kind);
        }
        return null;
    }
    /** 
     * @param path Relative path expression (as in the HTTP request
     * line) having only path component.
     * @return No leading or trailing path separator ('/'), first
     * token from path delimited sequence.
     */
    public static String PathName(String path){
        if (null == path || 0 == path.length())
            return null;
        else {
            return (new java.util.StringTokenizer(path,"/")).nextToken();
        }
    }


    public final String name, packageName, className, fullClassName, pathName;

    public final int hashCode;

    private Class<? extends TableClass> tableClass;

    private Method tableFunctionGet, tableFunctionKeyIdFor;


    private Kind(String name, String packageName, String className, String path){
        super();
        if (null != name && null != packageName && null != className && null != path){
            this.name = name;
            this.hashCode = name.hashCode();
            this.packageName = packageName;
            this.className = className;
            this.fullClassName = packageName+'.'+className;
            this.pathName = PathName(path);
        }
        else
            throw new IllegalArgumentException();
    }


    public String getName(){
        return this.name;
    }
    public String pathto(String subpath){
        StringBuilder string = new StringBuilder();
        string.append('/');
        string.append(this.pathName);
        string.append('/');

        if (null != subpath)
            string.append(subpath);

        return string.toString();
    }
    public Class<? extends TableClass> getTableClass(){
        Class<? extends TableClass> tableClass = this.tableClass;
        if (null == tableClass){
            try {
                tableClass = (Class<? extends TableClass>)Class.forName(this.fullClassName);
                this.tableClass = tableClass;
            }
            catch (ClassNotFoundException exc){
                throw new IllegalStateException(this.name,exc);
            }
        }
        return tableClass;
    }
    public Method getTableGetter(){
        Method tableFunctionGet = this.tableFunctionGet;
        if (null == tableFunctionGet){
            Class<? extends TableClass> table = this.getTableClass();
            try {
                tableFunctionGet = table.getMethod("Get",Key.class);
                this.tableFunctionGet = tableFunctionGet;
            }
            catch (Exception any){
                throw new IllegalStateException(this.fullClassName,any);
            }
        }
        return tableFunctionGet;
    }
    public TableClass get(Key key){
        Method getter = this.getTableGetter();
        try {
            return (TableClass)getter.invoke(null,key);
        }
        catch (Exception any){
            throw new IllegalStateException(this.fullClassName+'#'+getter.getName(),any);
        }
    }
    public Method getTableKeyIdForFunction(){
        Method tableFunctionKeyIdFor = this.tableFunctionKeyIdFor;
        if (null == tableFunctionKeyIdFor){
            Class<? extends TableClass> table = this.getTableClass();
            try {
                for (Method method : table.getMethods()){
                    if (method.getName().equals("KeyIdFor")){

                        tableFunctionKeyIdFor = method;
                        this.tableFunctionKeyIdFor = tableFunctionKeyIdFor;
                        return tableFunctionKeyIdFor;
                    }
                }
            }
            catch (Exception any){
                throw new IllegalStateException(this.fullClassName,any);
            }
        }
        return tableFunctionKeyIdFor;
    }
    public Key keyIdFor(Object... args){
        Method getter = this.getTableKeyIdForFunction();
        try {
            Object[] argv = new Object[]{args};
            return (Key)getter.invoke(null,argv);
        }
        catch (Exception any){
            throw new IllegalArgumentException(this.fullClassName+'#'+getter.getName(),any);
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