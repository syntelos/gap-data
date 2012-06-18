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
package gap.odl;

import jauk.Pattern;

import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;

import com.google.appengine.api.datastore.Key;

import java.lang.reflect.TypeVariable;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Normal field types are either {@link gap.Primitive primitives} or
 * {@link gap.data.Collection collections}.  Transient fields and
 * relation declarations may be {@link gap.data.BigTable data bean}
 * types.
 * 
 * @see Class
 * @author jdp
 */
public final class Field
    extends Object
    implements FieldDescriptor.Persistence,
               FieldDescriptor.Uniqueness,
               FieldDescriptor.Relation,
               FieldDescriptor.DefaultSortBy,
               FieldDescriptor.Enumerated
{
    public final static Pattern Statement = new jauk.Re("<_>*[^;]*;<Newline>");
    /**
     * Field qualifiers
     */
    public enum Qualifier {
        /**
         * <pre>
         * *child
         * </pre>
         * Short relation for a Child Class
         */
        Child("*child"),
        /**
         * <pre>
         * *unique
         * </pre>
         * Component of required Object Instance Identity
         */
        Unique("*unique"),
        /**
         * <pre>
         * *transient
         * </pre>
         * A Field that is not persistent is not stored in the Data
         * Store or Memcache.
         */
        Transient("*transient"),
        /**
         * <pre>
         * *enum
         * </pre>
         * The field Object Class is a Java Enum
         */
        Enumerated("*enum"),
        /**
         * <pre>
         * *table
         * </pre>
         * Employ table class interface type, storing Key not Abstract
         * Identifier.  This is for a field that is defined with a
         * Java Class that is an interface extending {@link
         * gap.data.TableClass}.  Because the Data Store KIND is a
         * variable in this expression, the Data Store Key is stored
         * rather than the Gap Data conventional Abstract Identifier.
         * 
         * This is an advanced usage in that Data Store Keys in Gap
         * Data may refer to a specific version of an Object Instance
         * in the Data Store.
         */
        TableClass("*table"),
        /**
         * <pre>
         * *sortBy
         * </pre>
         * This field is the default sortBy -- otherwise the first
         * unique would be the default sortBy in Data Store Queries.
         */
        DefaultSortBy("*sortby");


        public final String syntax;

        private Qualifier(String syntax){
            this.syntax = syntax;
        }


        private final static lxl.Map<String,Qualifier> Syntax = new lxl.Map<String,Qualifier>();
        static {
            for (Qualifier qualifier: Qualifier.values()){
                Syntax.put(qualifier.syntax,qualifier);
            }
        }
        public static Qualifier For(String syntax){
            if (null == syntax || 0 == syntax.length())
                return null;
            else {
                Qualifier qualifier = Syntax.get(syntax);
                if (null != qualifier)
                    return qualifier;
                else {
                    try {
                        return valueOf(syntax);
                    }
                    catch (IllegalArgumentException exc){
                        return null;
                    }
                }
            }
        }
        public static Qualifier[] Add(Qualifier[] list, Qualifier item){
            if (null == item)
                return list;
            else if (null == list)
                return new Qualifier[]{item};
            else {
                int len = list.length;
                Qualifier[] copier = new Qualifier[len+1];
                System.arraycopy(list,0,copier,0,len);
                copier[len] = item;
                return copier;
            }
        }
    }
    /**
     * Get Type Equivalents
     */
    private final static lxl.Map<String,String> GetTypeEq = new lxl.Map<String,String>();
    static {
        GetTypeEq.put("bool","Boolean");
        GetTypeEq.put("boolean","Boolean");
        GetTypeEq.put("byte","Byte");
        GetTypeEq.put("short","Short");
        GetTypeEq.put("int","Integer");
        GetTypeEq.put("integer","Integer");
        GetTypeEq.put("long","Long");
        GetTypeEq.put("float","Float");
        GetTypeEq.put("double","Double");
        GetTypeEq.put("string","String");
        GetTypeEq.put("key","Key");
        GetTypeEq.put("enum","Enum"); //(for completeness)
        GetTypeEq.put("date","Date");
        GetTypeEq.put("serializable","Serializable");
        GetTypeEq.put("java.io.serializable","Serializable");
        GetTypeEq.put("java.io.Serializable","Serializable");
        GetTypeEq.put("object","Serializable");
        GetTypeEq.put("java.lang.object","Serializable");
        GetTypeEq.put("java.lang.Object","Serializable");
    }
    /**
     * Map type equivalents, <i>e.g. "int" = "Integer", or "bool" =
     * "Boolean"</i>.
     * @see gap.Primitive#For(java.lang.String)
     */
    public final static String GetType(String type){
        String equiv = GetTypeEq.get(type);
        if (null != equiv)
            return equiv;
        else
            return type;
    }


    private Comment comment;

    public final String typeName, name;

    public final Persistence.Type persistence;

    public final boolean unique, enumerated, tableclass;

    public final Relation.Type relational;

    public final boolean isDefaultSortBy;


    public Field(Reader reader, Class clas, Package pack)
        throws IOException, Syntax
    {
        super();
        this.comment = reader.comment();
        String line = reader.next(Statement);
        if (null != line){
            boolean isDefaultSortBy = false;
            String typeName = null, name = null;
            Persistence.Type persistence = null;
            boolean unique = false, enumerated = false, tableclass = false;
            Relation.Type relational = null;

            StringTokenizer strtok = new StringTokenizer(line," \t\r\n;");

            while (strtok.hasMoreTokens()){
                String token = strtok.nextToken();
                Qualifier qualifier = Qualifier.For(token);
                if (null != qualifier){
                    if (null != typeName)
                        throw new Syntax("Malformed ODL field statement '"+line+"'.");
                    else {
                        switch (qualifier){
                        case Child:
                            if (unique)
                                throw new Syntax("Malformed ODL field statement '"+line+"'.");
                            else {
                                persistence = Persistence.Type.Transient;
                                relational = Relation.Type.Child;
                            }
                            break;
                        case Unique:
                            if (unique)
                                throw new Syntax("Malformed ODL field statement '"+line+"'.");
                            else {
                                unique = true;
                                persistence = Persistence.Type.Persistent;
                                relational = Relation.Type.None;
                            }
                            break;
                        case Transient:
                            if (unique)
                                throw new Syntax("Malformed ODL field statement '"+line+"'.");
                            else {
                                persistence = Persistence.Type.Transient;
                                relational = Relation.Type.None;
                            }
                            break;
                        case Enumerated:
                            enumerated = true;
                            break;
                        case TableClass:
                            tableclass = true;
                            break;
                        case DefaultSortBy:
                            isDefaultSortBy = true;
                            break;
                        }
                    }
                }
                else if (null == typeName){
                    typeName = token;
                }
                else if (null == name){
                    name = token;
                }
                else
                    throw new Syntax("Malformed ODL field statement '"+line+"'.");
            }
            if (null == typeName)
                throw new Syntax("Malformed ODL field statement '"+line+"'.");
            else if (null == name)
                name = Class.Decamel(typeName);

            this.typeName = Field.GetType(typeName);
            this.name = name;
            this.unique = unique;
            this.enumerated = enumerated;
            this.persistence = persistence;
            this.relational = relational;
            this.tableclass = tableclass;
            this.isDefaultSortBy = isDefaultSortBy;
            /*
             * Rather than scan later, fit this here with an
             * appropriate semantics for "touch"
             */
            if (tableclass){

                gap.service.Classes.TouchTableClass(pack, clas, this);
            }
        }
        else
            throw new Jump(this.comment);
    }


    public String getName(){
        return this.name;
    }
    public Object getType(){
        return this.typeName;
    }
    public boolean hasPersistence(){
        return (null != this.persistence);
    }
    public Persistence.Type getPersistence(){
        return this.persistence;
    }
    public boolean isUnique(){
        return this.unique;
    }
    public boolean isEnumerated(){
        return this.enumerated;
    }
    public boolean hasRelation(){
        return (null != this.relational);
    }
    public Relation.Type getRelation(){
        return this.relational;
    }
    public boolean hasComment(){
        return (null != this.comment);
    }
    public Comment getComment(){
        return this.comment;
    }
    public boolean isDefaultSortBy(){
        return this.isDefaultSortBy;
    }
}
