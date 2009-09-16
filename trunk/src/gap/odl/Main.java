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

import hapax.Template;
import hapax.TemplateCache;
import hapax.TemplateDictionary;
import hapax.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

/**
 * This package will generate the Java source code for a class modeled
 * in {@link Class ODL}.
 * 
 * This command line tool requires three file arguments in the
 * following positional order: <i>in templ xtm</i>; <i>in model
 * odl</i>; and <i>out source java</i>.
 * 
 * @see Class
 * @author jdp
 */
public final class Main
    extends java.lang.Object
{
    /**
     * Run on files
     */
    public final static void Process(File xtm, File odl, PrintWriter out)
        throws IOException, TemplateException, Syntax
    {
        TemplateCache loader = new TemplateCache(xtm.getParent());
        Template template = loader.getTemplate(xtm.getPath());
        TemplateDictionary data = new TemplateDictionary();
        Reader odlReader = new Reader(odl);
        Class odlClass = new Class(odlReader);

        /*
         * Tool globals
         */
        data.putVariable("odl_gen_xtm_src",xtm.getPath());
        data.putVariable("odl_gen_odl_src",odl.getPath());
        data.putVariable("odl_gen_timestamp",(gap.Date.FormatISO8601(System.currentTimeMillis())));

        /*
         * Class globals
         */
        data.putVariable("package_name",odlClass.pack.name);
        data.putVariable("class_name",odlClass.name);
        data.putVariable("class_nameDecamel",odlClass.nameDecamel);
        data.putVariable("class_version",odlClass.version);

        /*
         * Imports
         */
        if (odlClass.hasImports()){
            for (Import imp : odlClass.imports){
                TemplateDictionary imports = data.addSection("import");
                if (imp.isPackage())
                    imports.putVariable("import_spec",imp.packageSpec);
                else
                    imports.putVariable("import_spec",imp.className);
            }
        }
        else
            data.hideSection("import");

        if (odlClass.hasFields()){
            Field unique = null, key = null;
            List<Field> uniqueHash = null;
            /*
             * Fields & data
             */
            for (Field field : odlClass.fields){
                if (field.persistent){
                    TemplateDictionary dataField = data.addSection("pfield");
                    dataField.putVariable("field_class",field.typeName);
                    dataField.putVariable("field_name",field.name);
                    dataField.putVariable("field_nameCamel",field.nameCamel);

                    if (field.hash){
                        if (field.isTypeClassIndexed()){

                            TemplateDictionary field_is = dataField.addSection("field_is_hash_unique");
                            field_is.putVariable("data_model","*hash-unique");

                            if (field.isTypeClassString()){
                                dataField.putVariable("field_to_string_prefix","");
                                dataField.putVariable("field_to_string_suffix","");
                            }
                            else if (field.isTypeClassDate()){
                                dataField.putVariable("field_to_string_prefix","oso.Date.FormatISO8601(");
                                dataField.putVariable("field_to_string_suffix",")");
                            }
                            else {
                                dataField.putVariable("field_to_string_prefix","");
                                dataField.putVariable("field_to_string_suffix",".toString()");
                            }

                            if (null == uniqueHash){
                                uniqueHash = new java.util.ArrayList<Field>();
                            }
                            uniqueHash.add(field);
                        }
                        else if (field.isTypeClassList()){
                            if (field.hasTypeParameters()){
                                String typeComponent = field.getTypeParameter1();

                                TemplateDictionary field_is = dataField.addSection("field_is_hash_unique");
                                field_is.putVariable("data_model","*hash-unique");

                                dataField.putVariable("field_to_string_prefix","gap.data.Hash.For"+typeComponent+"(");
                                dataField.putVariable("field_to_string_suffix",")");

                                TemplateDictionary field_is_list = dataField.addSection("field_is_list");
                                field_is_list.putVariable("field_list_component",typeComponent);

                                if (null == uniqueHash){
                                    uniqueHash = new java.util.ArrayList<Field>();
                                }
                                uniqueHash.add(field);
                            }
                            else
                                throw new Syntax("Field '"+field.name+"' type list missing type parameter.");
                        }
                        else
                            throw new Syntax("Model '*hash-unique' field type is not indexable or list of indexable '"+field.typeClass+"'.");
                    }
                    else if (field.unique){
                        if (field.isTypeClassString()){

                            TemplateDictionary field_is = dataField.addSection("field_is_unique");
                            field_is.putVariable("data_model","*unique");


                            if (null == unique)
                                unique = field;
                            else
                                throw new Syntax("Model has more than one '*unique' field, '"+unique.name+"' and '"+field.name+"'.");
                        }
                        else
                            throw new Syntax("Model '*unique' field type is not string '"+field.typeClass+"'.");
                    }
                    else if (field.isTypeClassList()){
                        if (field.hasTypeParameters()){
                            TemplateDictionary field_is_list = dataField.addSection("field_is_list");
                            String typeComponent = field.getTypeParameter1();
                            field_is_list.putVariable("field_list_component",typeComponent);
                        }
                        else
                            throw new Syntax("Field '"+field.name+"' type list missing type parameter.");
                    }

                    if (field.key){
                        if (null == key)
                            key = field;
                    }
                }
                else if (field.relation){

                    if (field.hasTypeClass() && field.isNotTypeClassBigTable())
                        throw new Syntax("Relation field '"+field.name+"' is not a subclass of 'gap.data.BigTable'.");

                    TemplateDictionary dataField = data.addSection("rfield");

                    dataField.putVariable("field_class",field.typeName);
                    dataField.putVariable("field_name",field.name);
                    dataField.putVariable("field_nameCamel",field.nameCamel);
                }
                else {

                    TemplateDictionary dataField = data.addSection("tfield");
                    {
                        TemplateDictionary field_is = dataField.addSection("field_is_transient");
                        field_is.putVariable("data_model","*transient");
                    }
                    dataField.putVariable("field_class",field.typeName);
                    dataField.putVariable("field_name",field.name);
                    dataField.putVariable("field_nameCamel",field.nameCamel);
                }
            }
            /*
             * Fields: 'key'
             */
            if (null != key){
                data.putVariable("field_key_name",key.name);
                data.putVariable("field_key_nameCamel",key.nameCamel);
                data.putVariable("field_key_class",key.typeName);
            }
            else
                throw new Syntax("Model requires a field having type 'com.google.appengine.api.datastore.Key'.");
            /*
             * Fields: 'unique', and 'hash_unique'
             */
            if (null != unique){
                if (null != uniqueHash){
                    TemplateDictionary dataField;

                    TemplateDictionary dataFieldH = data.addSection("field_hash_unique");

                    for (Field field : uniqueHash){
                        dataField = dataFieldH.addSection("field");
                        dataField.putVariable("field_class",field.typeName);
                        dataField.putVariable("field_name",field.name);
                        dataField.putVariable("field_nameCamel",field.nameCamel);
                    }

                    dataField = data.addSection("field_unique");
                    dataField.putVariable("field_class",unique.typeName);
                    dataField.putVariable("field_name",unique.name);
                    dataField.putVariable("field_nameCamel",unique.nameCamel);
                }
                else
                    throw new Syntax("Model requires one or more fields qualified as '*hash-unique'.");
            }
            else if (null != uniqueHash)
                throw new Syntax("Model requires field qualified as '*unique'.");
            else
                throw new Syntax("Model requires fields qualified as '*unique' and '*hash-unique'.");

            /*
             * Global field 'unique'
             */
            data.putVariable("field_unique_name",unique.name);
            data.putVariable("field_unique_nameCamel",unique.nameCamel);
            data.putVariable("field_unique_class",unique.typeName);
        }
        else
            throw new Syntax("Model requires fields.");

        /*
         * Run template
         */
        template.render(data,out); 
    }
    /**
     * Run on directories
     * @return List of target products
     */
    public final static List<File> Process(File xtmFile, File odlDir, File outDir)
        throws IOException, TemplateException, Syntax
    {
        List<File> products = new java.util.ArrayList<File>();
        String xtmName = xtmFile.getName();
        if (xtmName.endsWith(".xtm")){
            String outFext = xtmName.substring(0,xtmName.length()-4);
            String[] odlList = odlDir.list();
            for (String odlName : odlList){
                if (odlName.endsWith(".odl")){
                    File odlFile = new File(odlDir,odlName);
                    File outFile = new File(outDir,odlName.substring(0,odlName.length()-3)+outFext);

                    FileWriter out = new FileWriter(outFile);
                    try {
                        PrintWriter outPrinter = new PrintWriter(out);
                    
                        Main.Process(xtmFile,odlFile,outPrinter);

                        products.add(outFile);
                    }
                    catch (Syntax syntax){
                        throw new Syntax("In '"+odlFile+"'",syntax);
                    }
                    finally {
                        out.close();
                    }
                }
            }
            return products;
        }
        else
            throw new IllegalArgumentException(xtmName);
    }

    public final static void usage(java.io.PrintStream out){
        out.println("Usage");
        out.println();
        out.println("  gap.odl.Main template.xtm class.odl file.out");
        out.println();
        out.println("Description");
        out.println();
        out.println("     Generate 'file.out' from 'class.odl' via 'template.xtm'.");
        out.println();
        out.println("Usage");
        out.println();
        out.println("  gap.odl.Main lang.xtm odl/source/dir out/target/dir");
        out.println();
        out.println("Description");
        out.println();
        out.println("     For the template named with the target filename extension,");
        out.println("     generate a collection of targets given a collection of sources.");
        out.println("     The template file must have the '.xtm' filename extension.");
        out.println();
    }

    public final static void main(String[] argv){
        if (3 == argv.length){
            File xtmFile = new File(argv[0]);
            File odlFile = new File(argv[1]);
            File outFile = new File(argv[2]);
            if (xtmFile.isFile() && odlFile.isFile()){
                try {
                    FileWriter out = new FileWriter(outFile);
                    try {
                        PrintWriter outPrinter = new PrintWriter(out);
                    
                        Main.Process(xtmFile,odlFile,outPrinter);
                    }
                    finally {
                        out.close();
                    }
                    System.exit(0);
                    return;
                }
                catch (Exception exc){
                    exc.printStackTrace();
                    System.exit(1);
                    return;
                }
            }
            else if (xtmFile.isFile() && odlFile.isDirectory()){
                if (!outFile.isDirectory()){
                    if (!outFile.mkdirs()){
                        System.err.println("Error, directory not found: '"+outFile.getPath()+"'.");
                        System.exit(1);
                        return;
                    }
                }

                try {
                    System.out.println("Template: "+xtmFile.getPath());
                    System.out.println("Source: "+odlFile.getPath());
                    System.out.println("Target: "+outFile.getPath());
                    List<File> products = Main.Process(xtmFile,odlFile,outFile);
                    for (File product : products){
                        System.out.println("Product: "+product.getPath());
                    }
                    System.exit(0);
                    return;
                }
                catch (Exception exc){
                    exc.printStackTrace();
                    System.exit(1);
                    return;
                }
            }
            else {
                if (!xtmFile.isFile()){
                    System.err.println("Error, file not found: '"+xtmFile.getPath()+"'.");

                    if (!odlFile.isFile())
                        System.err.println("Error, file not found: '"+odlFile.getPath()+"'.");

                    System.exit(1);
                    return;
                }
                else {
                    System.err.println("Error, file not found: '"+odlFile.getPath()+"'.");
                    System.exit(1);
                    return;
                }
            }
        }
        else {
            usage(System.err);
            System.exit(1);
        }
    }

}
