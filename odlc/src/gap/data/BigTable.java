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

import gap.service.Classes;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DataTypeUtils;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.ShortBlob;

import java.io.File;

/**
 * Mockup class for compiler types.
 * 
 * @author jdp
 */
public abstract class BigTable
    extends gap.hapax.AbstractData
    implements gap.data.TableClass
{

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(String kind){
        throw new UnsupportedOperationException();
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(Class<? extends BigTable> clas){
        if (null != clas){
            String path = "odl/"+clas.getName().replace('.','/')+".odl";
            File file = new File(path);
            if (file.isFile()){
                try {
                    return Classes.For(file);
                }
                catch (java.io.IOException exc){
                    throw new IllegalArgumentException(clas.getName(),exc);
                }
            }
            else
                throw new IllegalArgumentException(clas.getName());
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * An independent "key to string" ensures that any upstream code
     * changes won't kill our database.
     */
    public final static String ToString(Key key){
        StringBuilder strbuf = ToStringBuilder(key);
        return strbuf.toString();
    }
    public final static StringBuilder ToStringBuilder(Key key){
        if (null != key){
            StringBuilder strbuf = new StringBuilder();
            ToString(key,strbuf,key);
            return strbuf;
        }
        else
            throw new IllegalArgumentException();
    }
    private final static void ToString(Key key, StringBuilder strbuf, Key k){

        Key p = k.getParent();
        if (null != p)
            ToString(key,strbuf,p);

        strbuf.append('/');
        strbuf.append(k.getKind());
        strbuf.append(':');
        String n = k.getName();
        if (null != n && 0 != n.length())
            strbuf.append(n);
        else {
            long num = k.getId();
            if (0L != num)
                strbuf.append(String.valueOf(num));
            else
                throw new IllegalArgumentException("Key is incomplete '"+key+"'.");
        }
    }
    public final static boolean IsUnindexed(java.lang.Class jclass){
        if (null == jclass)
            return false;
        else
            return (!DataTypeUtils.isSupportedType(jclass));
    }
    public final static boolean IsIndexed(java.lang.Class jclass){
        if (null == jclass)
            return false;
        else
            return DataTypeUtils.isSupportedType(jclass);
    }
    protected final static boolean IsUnindexed(java.io.Serializable value){
        if (null == value)
            return false;
        else 
            return IsUnindexed(value.getClass());
    }
    protected final static boolean IsIndexed(java.io.Serializable value){
        if (null == value)
            return false;
        else 
            return IsIndexed(value.getClass());
    }
    public final static boolean IsEqual(Object a, Object b){
        if (a == b)
            return true;
        else if (null == a)
            return (null == b);
        else if (null == b)
            return false;
        else
            return (a.equals(b));
    }
    public final static boolean IsNotEqual(Object a, Object b){
        if (a == b)
            return false;
        else if (null == a)
            return (null != b);
        else if (null == b)
            return true;
        else
            return (!a.equals(b));
    }


    protected BigTable(){
        super();
    }



    public abstract boolean hasKey();

    public abstract boolean hasNotKey();

    public abstract boolean dropKey();

    public abstract Key getKey();

    public abstract boolean setKey(Key key);

    public abstract void onread();

    public abstract void onwrite();

    public abstract void destroy();

    public abstract boolean isFromDatastore();

    public abstract boolean isFromMemcache();

    public abstract BigTable setFromDatastore();

    public abstract BigTable setFromMemcache();

    public abstract BigTable setFromDatastore(Key with);

    public abstract Kind getClassKind();

    public abstract String getClassName();

    public abstract String getClassKindPath();

    public abstract String getClassFieldUnique();

    public abstract Field getClassKeyField();

    public abstract Lock getLock();

    public abstract List<Field> getClassFields();

    public abstract Field getClassFieldByName(String name);

    public abstract java.io.Serializable valueOf(Field field, boolean mayInherit);

    public abstract void define(Field field, java.io.Serializable value);

    public abstract void define(String fieldName, java.io.Serializable value);

    public abstract void clearDatastoreEntity();

    public abstract Entity getDatastoreEntity();

    public abstract Entity fillToDatastoreEntity();

    public abstract Entity fillFromDatastoreEntity(Entity entity);

    public abstract void drop();

    public abstract void clean();

    public abstract void save();

    public abstract void store();

    public abstract boolean hasInheritFromKey();

    public abstract boolean hasNotInheritFromKey();

    public abstract Key getInheritFromKey();

    public abstract boolean setInheritFromKey(Key key);

    public abstract boolean inheritFrom(Key key);

    public abstract gap.service.od.ClassDescriptor getClassDescriptorFor();

}
