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

import gap.service.od.ClassDescriptor;
import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;
import gap.service.od.MethodDescriptor;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

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
 * /# gap odl 'class Bar'
 *  #/
 * package foo;
 * import java.io.*;
 * class Bar version 2
 *   implements AnInterface
 *   implements AndAnotherInterface
 * {
 *   *unique String uniqueId;
 *   *hash-unique String uniqueName;
 *   Serializable fieldValue;
 *   List<String> aChangeSinceVersionOne;
 * }
 * </pre>
 * 
 * In this example, the value of 'uniqueId' is the gap data hash of
 * 'uniqueName'.
 * 
 * <pre>
 * /# gap odl 'class Whiz'
 *  #/
 * package gee;
 * 
 * child Whiz version 1
 *   parent foo.Bar
 *   implements SomeInterface
 * {
 *   *unique String uniqueId;
 *   *hash-unique String uniqueName;
 * }
 * </pre>
 * 
 * This example shows the child class declaration.  The parent must be
 * declared before any interface, and on another line following the
 * class name and version.
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
    implements ClassDescriptor.Version,
               ClassDescriptor.Implements,
               ClassDescriptor.Relation
{
    public final static Pattern Open = Pattern.compile("^(class|parent|child).*");
    public final static Pattern Open2 = Pattern.compile("\\s*\\{",Pattern.MULTILINE);


    public final Package pack;

    public final Parent parent;

    public final String name, nameDecamel;

    public final Relation.Type relation;

    public final String version;

    public final List<ImportDescriptor> imports = new java.util.ArrayList<ImportDescriptor>();

    private Comment comment;

    public final List<Object> interfaces = new java.util.ArrayList<Object>();

    public final List<FieldDescriptor> fields = new java.util.ArrayList<FieldDescriptor>();

    public final List<MethodDescriptor> methods = new java.util.ArrayList<MethodDescriptor>();

    private String definitionClassName;


    public Class(Reader reader)
        throws IOException, Syntax
    {
        super();
        Package pack = null;
        String spec = null, name = null;
        Import imp = null;
        Field field = null;
        Method method = null;
        long version = 1L;
        Parent parent = null;

        while (true){
            try {
                if (null == pack)
                    pack = new Package(reader);
                else if (null == name){
                    try {
                        imp = new Import(reader);
                        this.imports.add(imp);
                    }
                    catch (Jump local){
                        this.comment = reader.comment();
                        String line = reader.getNext(Open);
                        if (null != line){
                            StringTokenizer strtok = new StringTokenizer(line, " \t\r\n{");
                            switch (strtok.countTokens()){
                            case 2:
                                spec = strtok.nextToken();
                                name = strtok.nextToken();
                                break;
                            case 4:
                                spec = strtok.nextToken();
                                name = strtok.nextToken();
                                if ("version".equals(strtok.nextToken()))
                                    version = Long.parseLong(strtok.nextToken());
                                else
                                    throw new Syntax("Malformed ODL class declaration in '"+line+"'.");
                                break;
                            default:
                                throw new Syntax("Malformed ODL class declaration in '"+line+"'.");
                            }
                            if (!line.endsWith("{")){
                                try {
                                    parent = new Parent(reader);
                                }
                                catch (Jump to){
                                }
                                try {
                                    while (true){
                                        Interface inf = new Interface(reader, pack, this.imports);
                                        this.interfaces.add(inf);
                                    }
                                }
                                catch (Jump to){
                                }
                                String open2 = reader.getNext(Open2);
                            }
                        }
                        else
                            throw new Syntax("ODL class declaration not found.");
                    }
                }
                else {
                    try {
                        method = new Method(reader);
                        this.methods.add(method);
                    }
                    catch (Jump to){

                        field = new Field(reader);
                        this.fields.add(field);
                    }
                }
            }
            catch (Jump terminal){
                break;
            }
        }

        if (null != pack)
            this.pack = pack;
        else
            throw new Syntax("Syntax error missing package.");

        this.parent = parent;

        if (null != spec && null != name){

            if ("parent".equals(spec))
                this.relation = Relation.Type.Parent;
            else if ("child".equals(spec))
                this.relation = Relation.Type.Child;
            else
                this.relation = Relation.Type.None;

            this.name = name;
            this.nameDecamel = Decamel(name);
        }
        else
            throw new Syntax("Syntax error missing class declaration.");

        this.version = String.valueOf(version);

        return;
    }


    public String getName(){
        return this.name;
    }
    public boolean hasRelation(){
        return true;
    }
    public Relation.Type getRelation(){
        return this.relation;
    }
    public boolean hasVersion(){
        return (null != this.version);
    }
    public Long getVersion(){
        return new Long(this.version);
    }
    public boolean hasImports(){
        return (!this.imports.isEmpty());
    }
    public boolean hasComment(){
        return (null != this.comment);
    }
    public Comment getComment(){
        return this.comment;
    }
    public boolean hasFields(){
        return (!this.fields.isEmpty());
    }
    public List<FieldDescriptor> getFields(){
        return this.fields;
    }
    public boolean hasMethods(){
        return (!this.methods.isEmpty());
    }
    public List<MethodDescriptor> getMethods(){
        return this.methods;
    }

    public boolean hasDefinitionClassName(){
        return (null != this.definitionClassName);
    }
    public String getDefinitionClassName(){
        return this.definitionClassName;
    }
    public void setDefinitionClassName(String name){
        this.definitionClassName = name;
    }

    public boolean hasInterfaces(){
        return (!this.interfaces.isEmpty());
    }
    public List<Object> getInterfaces(){
        return this.interfaces;
    }
    public boolean hasParent(){
        return (null != this.parent);
    }
    public String getParent(){
        if (null != this.parent)
            return this.parent.getName();
        else
            return null;
    }

    public final static String Decamel(String string){
        if (1 < string.length())
            return (string.substring(0,1).toLowerCase()+string.substring(1));
        else
            return string.toLowerCase();
    }
}
