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
package gap.service;

import gap.Primitive;
import gap.data.HasName;
import static gap.data.List.Type.*;
import static gap.data.Map.Type.*;

import java.util.StringTokenizer;

/**
 * Object data model primitives.
 * 
 * @author jdp
 */
public class OD
    extends java.lang.Object
{
    public final static String Camel(String string){
        if (null != string){
            int strlen = string.length();
            if (0 != strlen){
                if (1 != strlen)
                    return (string.substring(0,1).toUpperCase()+string.substring(1));
                else
                    return string.toUpperCase();
            }
            else
                throw new IllegalArgumentException();
        }
        else
            throw new IllegalArgumentException();
    }
    public final static String Decamel(String string){
        if (1 < string.length())
            return (string.substring(0,1).toLowerCase()+string.substring(1));
        else
            return string.toLowerCase();
    }
    public final static String ClassSortBy(Class<? extends gap.data.TableClass> table){
        if (null != table){
            try {
                java.lang.reflect.Field fieldDefaultSortBy = table.getField("DefaultSortBy");
                return (String)fieldDefaultSortBy.get(null);
            }
            catch (Exception exc){
            }
        }
        return null;
    }
    public final static String[] FieldTypeParameters(String typeName){
        int start = typeName.indexOf('<');
        if (-1 != start){
            String parameters = typeName.substring((start+1),(typeName.length()-1)).trim();
            StringTokenizer strtok = new StringTokenizer(parameters,", ");
            int count = strtok.countTokens();
            String[] list = new String[count];
            for (int cc = 0; cc < count; cc++){
                String token = strtok.nextToken();
                if ('<' != token.charAt(0))
                    list[cc] = token;
                else
                    throw new IllegalStateException(typeName);
            }
            return list;
        }
        return new String[0];
    }
    public final static boolean IsTypeClassKey(java.lang.Class fieldType){
        if (null != fieldType)
            return (com.google.appengine.api.datastore.Key.class.equals(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassIndexed(java.lang.Class fieldType){
        return (gap.data.BigTable.IsIndexed(fieldType));
    }
    public final static boolean IsTypeClassList(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.List.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassBigTable(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.BigTable.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsNotTypeClassBigTable(java.lang.Class fieldType){
        if (null != fieldType)
            return (!(gap.data.BigTable.class.isAssignableFrom(fieldType)));
        else
            return true;
    }
    public final static boolean IsTypeClassMap(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.Map.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassString(java.lang.Class fieldType){
        if (null != fieldType)
            return (java.lang.String.class.equals(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassDate(java.lang.Class fieldType){
        if (null != fieldType)
            return (java.util.Date.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassCollection(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.Collection.class.isAssignableFrom(fieldType));
        else
            return true;
    }
    public final static boolean IsNotTypeClassCollection(java.lang.Class fieldType){
        if (null != fieldType)
            return (!(gap.data.Collection.class.isAssignableFrom(fieldType)));
        else
            return true;
    }
    public final static String CleanTypeName(String name){
        int idx = name.indexOf('<');
        if (-1 != idx)
            return name.substring(0,idx);
        else
            return name;
    }
    public final static String CleanCleanTypeName(String name){
        int idx = name.indexOf('.');
        if (-1 != idx)
            return name.substring(0,idx);
        else
            return name;
    }
    public final static String ToString(Object object){
        if (object instanceof String)
            return (String)object;
        else if (object instanceof HasName)
            return ((HasName)object).getName();
        else 
            return object.toString();
    }
    public final static String ListClassName(String fieldType, String parentClassName, String childClassName){
        gap.data.List.Type listType = gap.data.List.Type.For(fieldType);
        switch(listType){
        case ListPrimitive:
            return ListPrimitiveClassName(childClassName);
        case ListShort:
            return ListShortClassName(parentClassName,childClassName);
        case ListLong:
            return ListLongClassName(parentClassName,childClassName);
        default:
            throw new IllegalStateException("Unrecognized type '"+fieldType+"'.");
        }
    }
    public final static String ListPrimitiveClassName(String childClassName){
        if (null != childClassName)
            return "ListPrimitive"+childClassName;
        else
            return null;
    }
    public final static String ListShortClassName(String parentClassName, String childClassName){
        if (null != parentClassName && null != childClassName)
            return "ListShort"+parentClassName+childClassName;
        else
            return null;
    }
    public final static String ListLongClassName(String parentClassName, String childClassName){
        if (null != parentClassName && null != childClassName)
            return "ListLong"+parentClassName+childClassName;
        else
            return null;
    }
    public final static String MapClassName(String fieldType, String parentClassName, String typeComponentFrom, String typeComponentTo){
        gap.data.Map.Type mapType = gap.data.Map.Type.For(fieldType);
        switch(mapType){
        case MapPrimitive:
            return MapPrimitiveClassName(parentClassName,typeComponentFrom,typeComponentTo);
        case MapShort:
            return MapShortClassName(parentClassName,typeComponentFrom,typeComponentTo);
        case MapLong:
            return MapLongClassName(parentClassName,typeComponentFrom,typeComponentTo);
        default:
            throw new IllegalStateException("Unrecognized type '"+fieldType+"'.");
        }
    }
    public final static String MapPrimitiveClassName(String parentClassName, String typeComponentFrom, String typeComponentTo){
        if (null != typeComponentFrom && null != typeComponentTo)
            return "MapPrimitive"+typeComponentFrom+typeComponentTo;
        else
            return null;
    }
    public final static String MapShortClassName(String parentClassName, String typeComponentFrom, String typeComponentTo){
        if (null != parentClassName && null != typeComponentFrom && null != typeComponentTo)
            return "MapShort"+parentClassName+typeComponentFrom+typeComponentTo;
        else
            return null;
    }
    public final static String MapLongClassName(String parentClassName, String typeComponentFrom, String typeComponentTo){
        if (null != parentClassName && null != typeComponentFrom && null != typeComponentTo)
            return "MapLong"+parentClassName+typeComponentFrom+typeComponentTo;
        else
            return null;
    }
}
