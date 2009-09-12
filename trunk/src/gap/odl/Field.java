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

import com.google.appengine.api.datastore.Key;

import java.lang.reflect.TypeVariable;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @see Class
 * @author jdp
 */
public final class Field
    extends Object
{

    public final String typeName, typeParameters[], name, qualifier, nameCamel;

    public final java.lang.Class typeClass;

    public final boolean indexed, unique, hash, persistent, key;


    public Field(Reader reader, Package pkg, List<Import> imports)
        throws IOException, Syntax
    {
        super();
        String fieldName = null, className = null;
        for (String line : reader){
            line = line.trim();
            if (line.startsWith("#") || 0 == line.length())
                continue;
            else if (line.startsWith("}"))
                throw new Jump.EOF(reader);
            else {
                StringTokenizer strtok = new StringTokenizer(line," \t;");
                switch (strtok.countTokens()){

                case 2:
                    this.qualifier = null;
                    this.typeName = strtok.nextToken();
                    this.typeParameters = SimpleTypeParameters(this.typeName);
                    this.name = strtok.nextToken();
                    this.nameCamel = Camel(this.name);
                    this.typeClass = Import.Find(pkg,imports,this.typeName);
                    this.indexed = gap.data.BigTable.IsIndexed(this.typeClass);
                    this.unique = false;
                    this.hash = false;
                    this.persistent = true;
                    this.key = (null != this.typeClass && Key.class.equals(this.typeClass));
                    return;

                case 3:
                    this.qualifier = strtok.nextToken();
                    this.typeName = strtok.nextToken();
                    this.typeParameters = SimpleTypeParameters(this.typeName);
                    this.name = strtok.nextToken();
                    this.nameCamel = Camel(this.name);
                    this.typeClass = Import.Find(pkg,imports,this.typeName);
                    this.indexed = gap.data.BigTable.IsIndexed(this.typeClass);
                    this.key = (null != this.typeClass && Key.class.equals(this.typeClass));
                    if (null != this.qualifier){
                        if ("*unique".equalsIgnoreCase(this.qualifier)){
                            if (this.indexed){
                                this.unique = true;
                                this.hash = false;
                                this.persistent = true;
                            }
                            else
                                throw new Syntax("Unique field is not an indexed type '"+line+"'.");
                        }
                        else if ("*hash-unique".equalsIgnoreCase(this.qualifier)){
                            this.unique = true;
                            this.hash = true;
                            this.persistent = true;
                        }
                        else if ("*transient".equalsIgnoreCase(this.qualifier)){
                            this.unique = false;
                            this.hash = false;
                            this.persistent = false;
                        }
                        else 
                            throw new Syntax("Unrecognized field field qualifier in '"+line+"'.");
                    }
                    else {
                        this.unique = false;
                        this.hash = false;
                        this.persistent = true;
                    }

                    if (this.key && (!this.persistent))
                        throw new Syntax("Key field is not persistent '"+line+"'.");

                    return;

                default:
                    throw new Syntax("Malformed ODL field statement '"+line+"'.");
                }
            }
        }
        throw new Syntax("Missing class close '}' not found.");
    }


    public boolean hasTypeClass(){
        return (null != this.typeClass);
    }
    public java.lang.Class getTypeClass(){
        return this.typeClass;
    }
    public boolean hasTypeParameters(){
        return (0 != this.typeParameters.length);
    }
    public String[] getTypeParameters(){
        return this.typeParameters;
    }
    public String getTypeParameter1(){
        String[] parameters = this.typeParameters;
        if (null != parameters && 0 != parameters.length)
            return parameters[0];
        else
            return "";
    }
    public String getTypeParameter2(){
        String[] parameters = this.typeParameters;
        if (null != parameters && 1 < parameters.length)
            return parameters[1];
        else
            return "";
    }
    public boolean isTypeClassIndexed(){
        return this.indexed;
    }
    public boolean isTypeClassString(){
        java.lang.Class typeClass = this.typeClass;
        if (null != typeClass)
            return java.lang.String.class.equals(typeClass);
        else
            return false;
    }
    public boolean isTypeClassDate(){
        java.lang.Class typeClass = this.typeClass;
        if (null != typeClass)
            return java.util.Date.class.equals(typeClass);
        else
            return false;
    }
    public boolean isTypeClassCollection(){
        java.lang.Class typeClass = this.typeClass;
        if (null != typeClass)
            return java.util.Collection.class.isAssignableFrom(typeClass);
        else
            return false;
    }
    public boolean isTypeClassList(){
        java.lang.Class typeClass = this.typeClass;
        if (null != typeClass)
            return java.util.List.class.isAssignableFrom(typeClass);
        else
            return false;
    }
    public boolean isTypeClassListOfString(){
        java.lang.Class typeClass = this.typeClass;
        if (null != typeClass){
            if (java.util.List.class.isAssignableFrom(typeClass)){
                TypeVariable<java.lang.Class>[] parameters = typeClass.getTypeParameters();
                if (null != parameters && 0 != parameters.length){
                    java.lang.Class parameterClass = parameters[0].getGenericDeclaration();
                    return java.lang.String.class.equals(parameterClass);
                }
            }
        }
        return false;
    }
    public boolean isTypeClassMap(){
        java.lang.Class typeClass = this.typeClass;
        if (null != typeClass)
            return java.util.Map.class.isAssignableFrom(typeClass);
        else
            return false;
    }

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

    final static String[] SimpleTypeParameters(String typeName){
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

}
