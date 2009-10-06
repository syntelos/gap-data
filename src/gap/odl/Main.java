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

import static gap.data.List.Type.*;
import gap.service.OD;
import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;
import gap.service.od.ODStateException;

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
     * Generate bean, validate, servlet and list classes.
     */
    public final static List<File> ProcessFiles(File beanXtm, File odl, File beanFile)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        List<File> products = new java.util.ArrayList<File>();

        Reader odlReader = new Reader(odl);
        Class odlClass = new Class(odlReader);
        Package pack = odlClass.pack;
        List<ImportDescriptor> imports = odlClass.imports;

        File outDir = beanFile.getParentFile();
        File odlDir = beanXtm.getParentFile();

        FileWriter out = new FileWriter(beanFile);
        /*
         * Bean
         */
        try {
            OD.GenerateBeanSource(beanXtm, pack, imports, odlClass, (new PrintWriter(out)));
            products.add(beanFile);
        }
        finally {
            out.close();
        }

        String parentClassName = OD.ClassName(odlClass);
        /*
         * Validate
         */
        File validateXtm = new File(odlDir,"bean-validate.xtm");
        File validateFile = new File(outDir,"validate/"+parentClassName+".java");
        out = new FileWriter(validateFile);
        try {
            OD.GenerateBeanSource(validateXtm, pack, imports, odlClass, (new PrintWriter(out)));
            products.add(validateFile);
        }
        finally {
            out.close();
        }
        /*
         * Servlet
         */
        File servletXtm = new File(odlDir,"bean-servlet.xtm");
        File servletFile = new File(outDir,"servlet/"+parentClassName+".java");
        out = new FileWriter(servletFile);
        try {
            OD.GenerateBeanSource(servletXtm, pack, imports, odlClass, (new PrintWriter(out)));
            products.add(servletFile);
        }
        finally {
            out.close();
        }
        /*
         * Lists
         */
        List<FieldDescriptor> fieldsOfList = OD.FieldsOfTypeList(pack, odlClass, imports);
        if (!fieldsOfList.isEmpty()){
            File listShortXtm = new File(odlDir,"list-short.xtm");
            File listLongXtm = new File(odlDir,"list-long.xtm");

            for (FieldDescriptor field: fieldsOfList){
                String typeName = OD.ToString(field.getType());
                gap.data.List.Type listType = gap.data.List.Type.For(typeName);
                if (null != listType){
                    switch(listType){
                    case ListPrimitive:
                        break;
                    case ListShort:
                        if (listShortXtm.isFile()){
                            String childClassName = OD.ChildClassName(field);
                            String listClassName = OD.ListShortClassName(parentClassName,childClassName);
                            if (null != listClassName){
                                File listFile = new File(outDir,listClassName+".java");
                                out = new FileWriter(listFile);
                                try {
                                    OD.GenerateListSource(listShortXtm, pack, imports, odlClass, field,
                                                          parentClassName, childClassName, listClassName, listType,
                                                          (new PrintWriter(out)));
                                    products.add(listFile);
                                }
                                finally {
                                    out.close();
                                }
                            }
                        }
                        break;
                    case ListLong:
                        if (listLongXtm.isFile()){
                            String childClassName = OD.ChildClassName(field);
                            String listClassName = OD.ListLongClassName(parentClassName,childClassName);
                            if (null != listClassName){
                                File listFile = new File(outDir,listClassName+".java");
                                out = new FileWriter(listFile);
                                try {
                                    OD.GenerateListSource(listLongXtm, pack, imports, odlClass, field,
                                                          parentClassName, childClassName, listClassName, listType, 
                                                          (new PrintWriter(out)));
                                    products.add(listFile);
                                }
                                finally {
                                    out.close();
                                }
                            }
                        }
                        break;
                    default:
                        break;
                    }
                }
            }
        }
        return products;
    }
    /**
     * Run on directories
     * @return List of target products
     */
    public final static List<File> ProcessDirectories(File xtmFile, File odlDir, File outDir)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        List<File> products = new java.util.ArrayList<File>();

        String[] odlList = odlDir.list();
        for (String odlName : odlList){
            if (odlName.endsWith(".odl")){
                File odlFile = new File(odlDir,odlName);
                File outFile = new File(outDir,odlName.substring(0,odlName.length()-4)+".java");
                try {
                    List<File> files = Main.ProcessFiles(xtmFile,odlFile,outFile);

                    products.addAll(files);
                }
                catch (ODStateException ODStateException){
                    throw new ODStateException("In '"+odlFile+"'",ODStateException);
                }
            }
        }
        return products;
    }

    public final static void usage(java.io.PrintStream out){
        out.println("Usage");
        out.println();
        out.println("  gap.odl.Main odl/bean.xtm odl/pkg/Class.odl src/pkg/Class.java");
        out.println();
        out.println("  gap.odl.Main odl/bean.xtm odl/package src/package");
        out.println();
        out.println("Description");
        out.println();
        out.println("     This process is for generating java output in the Gap ODL");
        out.println("     framework.  It will generate bean, validation, servlet and list");
        out.println("     types for the input ODL class or package.");
        out.println();
    }

    public final static void main(String[] argv){

        if (3 <= argv.length){

            File xtmFile = new File(argv[0]);
            File odlFile = new File(argv[1]);
            File outFile = new File(argv[2]);
            if (xtmFile.isFile() && odlFile.isFile()){
                try {
                    System.out.println("Template: "+xtmFile.getPath());
                    System.out.println("Source: "+odlFile.getPath());
                    System.out.println("Target: "+outFile.getPath());
                    List<File> products = Main.ProcessFiles(xtmFile,odlFile,outFile);
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
                    List<File> products = Main.ProcessDirectories(xtmFile,odlFile,outFile);
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
