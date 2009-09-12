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

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <h3>Purpose</h3>
 * 
 * To model the persistent fields of a class for integration with
 * {@link gap.data.BigTable}.  This package will generate the Java
 * source code for the class subclassing {@link gap.data.BigTable}.
 * 
 * <h3>Syntax</h3>
 * 
 * <ul>
 * 
 * <li> Prefix line comment starting with '#'.  (No suffix line
 * comments).  </li>
 * 
 * <li> Package statement from JPL.  Optionally terminated by
 * semicolon. </li>
 * 
 * <li> Import expression from JPL, but employing '$' inner class name
 * delimeters.  Optionally terminated by semicolon. </li>
 * 
 * <li> Class expression: opening declaration "'class' Classname '{' "
 * on one line.  A list of fields, one per line.  A closing
 * declaration '}' on a line.
 *  
 * The class declaration line can optionally override the default
 * class serialized version number of one with the expression "'class'
 * Classname 'version' number '{'" for number in base ten requiring no
 * more than 64 bits storage. </li>
 * 
 * <li> Each field is a class name, space, field name.  Optionally
 * terminated by semicolon.
 *
 * A few primary unique fields may be qualified with one of the
 * keywords <code>"*hash-unique"</code> or <code>"*unique"</code>.
 * These fields have type String.
 * 
 * The latter causes the field to be employed as a primary lookup
 * identifier, and the former causes the hash of the field to be
 * employed as a primary lookup identifier.
 * 
 * Each class is expected to have one (and only one) of each.  The
 * value of the hash of the <code>"*hash-unique"</code> field is
 * assumed identical to the value of the <code>"*unique"</code>
 * field. </li>
 * 
 * </ul>
 * 
 * <h3>Example</h3>
 * 
 * <pre>
 * # gap odl 'class Bar'
 * #
 * package foo
 * import java.io.*
 * class Bar version 2 {
 *   *unique String uniqueId
 *   *hash-unique String uniqueName
 *   Serializable fieldValue
 *   List<String> aChangeSinceVersionOne
 * }
 * </pre>
 * 
 * In this example, the value of 'uniqueId' is the gap data hash of
 * 'uniqueName'.
 * 
 * <h3>Notes</h3>
 * 
 * Field types must resolve at runtime to serializable types.  It's
 * poor style, but not incorrect, to employ the the java lang Object
 * type.  Better style would be to employ the Serializable type,
 * instead.
 * 
 * 
 * @author jdp
 */
public final class Class
    extends Object
{

    public final Package pack;

    public final String name, nameDecamel;

    public final String version;

    public final List<Import> imports = new java.util.ArrayList<Import>();

    public final List<Field> fields = new java.util.ArrayList<Field>();


    public Class(Reader reader)
        throws IOException, Syntax
    {
        super();
        Package pack = null;
        String name = null;
        Import imp = null;
        Field field = null;
        long version = 1L;

        while (reader.hasNext()){
            try {
                if (null == pack)
                    pack = new Package(reader);
                else if (null == name){
                    try {
                        imp = new Import(reader);
                        this.imports.add(imp);
                    }
                    catch (Jump local){
                        String line = reader.next();
                        if (line.startsWith("#") || 0 == line.length())
                            continue;
                        else if (line.startsWith("class ") && line.endsWith("{")){
                            StringTokenizer strtok = new StringTokenizer(line, " \t{");
                            switch (strtok.countTokens()){
                            case 2:
                                strtok.nextToken();
                                name = strtok.nextToken();
                                break;
                            case 4:
                                strtok.nextToken();
                                name = strtok.nextToken();
                                if ("version".equals(strtok.nextToken()))
                                    version = Long.parseLong(strtok.nextToken());
                                else
                                    throw new Syntax("Malformed ODL class declaration in '"+line+"'.");
                                break;
                            default:
                                throw new Syntax("Malformed ODL class declaration in '"+line+"'.");
                            }
                        }
                        else
                            throw new Syntax("ODL class declaration not found.");
                    }
                }
                else {
                    field = new Field(reader, pack, this.imports);
                    this.fields.add(field);
                }
            }
            catch (Jump global){
                continue;
            }
            catch (Jump.EOF eof){
                break;
            }
        }

        if (null != pack)
            this.pack = pack;
        else
            throw new Syntax("Syntax error missing package.");

        if (null != name){
            this.name = name;
            this.nameDecamel = Decamel(name);
        }
        else
            throw new Syntax("Syntax error missing class declaration.");

        this.version = String.valueOf(version);

        return;
    }


    public boolean hasImports(){
        return (!this.imports.isEmpty());
    }
    public boolean hasFields(){
        return (!this.fields.isEmpty());
    }
    public final static String Decamel(String string){
        if (1 < string.length())
            return (string.substring(0,1).toLowerCase()+string.substring(1));
        else
            return string.toLowerCase();
    }
}
