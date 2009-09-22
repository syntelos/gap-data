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

import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;

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
    implements FieldDescriptor.Persistence,
               FieldDescriptor.Uniqueness,
               FieldDescriptor.Relation
{

    public final String typeName, name;

    public final Persistence.Type persistence;

    public final Uniqueness.Type uniqueness;

    public final Relation.Type relational;


    public Field(Reader reader, Package pkg)
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

                    String s = strtok.nextToken();
                    if ("*child".equals(s)){

                        this.uniqueness = Uniqueness.Type.Undefined;
                        this.persistence = Persistence.Type.Transient;
                        this.relational = Relation.Type.Child;

                        this.typeName = strtok.nextToken();
                        this.name = Class.Decamel(this.typeName);
                    }
                    else {
                        this.typeName = s;
                        this.name = strtok.nextToken();
                        this.uniqueness = Uniqueness.Type.Undefined;
                        this.persistence = Persistence.Type.Persistent;
                        this.relational = Relation.Type.None;
                    }
                    return;

                case 3:
                    String qualifier = strtok.nextToken();
                    this.typeName = strtok.nextToken();
                    this.name = strtok.nextToken();

                    if ("*unique".equalsIgnoreCase(qualifier)){

                        this.uniqueness = Uniqueness.Type.Unique;
                        this.persistence = Persistence.Type.Persistent;
                        this.relational = Relation.Type.None;
                    }
                    else if ("*hash-unique".equalsIgnoreCase(qualifier)){

                        this.uniqueness = Uniqueness.Type.HashUnique;
                        this.persistence = Persistence.Type.Persistent;
                        this.relational = Relation.Type.None;
                    }
                    else if ("*transient".equalsIgnoreCase(qualifier)){

                        this.uniqueness = Uniqueness.Type.Undefined;
                        this.persistence = Persistence.Type.Transient;
                        this.relational = Relation.Type.None;
                    }
                    else if ("*child".equalsIgnoreCase(qualifier)){

                        this.uniqueness = Uniqueness.Type.Undefined;
                        this.persistence = Persistence.Type.Transient;
                        this.relational = Relation.Type.Child;
                    }
                    else 
                        throw new Syntax("Unrecognized field qualifier in '"+line+"'.");

                    return;

                default:
                    throw new Syntax("Malformed ODL field statement '"+line+"'.");
                }
            }
        }
        throw new Syntax("Missing class close '}' not found.");
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
    public boolean hasUniqueness(){
        return (null != this.uniqueness);
    }
    public Uniqueness.Type getUniqueness(){
        return this.uniqueness;
    }
    public boolean hasRelation(){
        return (null != this.relational);
    }
    public Relation.Type getRelation(){
        return this.relational;
    }

}
