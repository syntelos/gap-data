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
public final class Interface
    extends Object
{

    public final String typeName;

    public final java.lang.Class typeClass;


    public Interface(Reader reader, Package pkg, List<ImportDescriptor> imports)
        throws IOException, Syntax
    {
        super();
        String interfaceName = null, className = null;
        for (String line : reader){
            line = line.trim();
            if (line.startsWith("#") || 0 == line.length())
                continue;
            else if (line.startsWith("{"))
                throw new Jump(reader);
            else {
                StringTokenizer strtok = new StringTokenizer(line," \t;");
                switch (strtok.countTokens()){

                case 2:

                    String s = strtok.nextToken();
                    if ("implements".equals(s)){

                        this.typeName = strtok.nextToken();

                        this.typeClass = Import.Find(pkg,imports,this.typeName);

                        if (null == typeClass)
                            throw new Syntax("Unrecognized class at 'implements' expression, '"+line+"'.");

                        else if (!this.typeClass.isInterface())
                            throw new Syntax("Invalid class is not an interface, '"+line+"'.");

                        else
                            return;
                    }
                    else {
                        throw new Syntax("Unrecognized input at 'implements type' expression, '"+line+"'.");
                    }

                default:
                    throw new Syntax("Malformed ODL implements statement '"+line+"'.");
                }
            }
        }
        throw new Syntax("Missing class open '{' not found.");
    }


    public Object getType(){
        return this.typeName;
    }
    public boolean hasTypeClass(){
        return (null != this.typeClass);
    }
    public java.lang.Class getTypeClass(){
        return this.typeClass;
    }
    public String toString(){
        return this.typeName;
    }
}
