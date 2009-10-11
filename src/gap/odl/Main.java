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

import alto.io.u.Find;

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
    private final static java.util.Map<String,Class> Classes = new java.util.HashMap<String,Class>();

    public final static String ClassName(File odl){
        String name = odl.getName();
        int idx = name.lastIndexOf('.');
        if (-1 != idx)
            return name.substring(0,idx);
        else
            return name;
    }
    public final static Class ClassDescriptorFor(File odl)
        throws IOException, Syntax
    {
        String name = ClassName(odl);
        Class desc;
        synchronized(Classes){
            desc = Classes.get(name);
        }
        if (null == desc){
            Reader odlReader = new Reader(odl);
            try {
                desc = (new Class(odlReader));
            }
            finally {
                odlReader.close();
            }
            synchronized(Classes){
                Classes.put(name,desc);
            }
        }
        return desc;
    }
    /**
     * Generate bean, validate, servlet and list classes.
     */
    public final static List<File> ProcessFiles(File beanXtm, File odl, File beanJava, File beans, File servlets)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        try {
            List<File> products = new java.util.ArrayList<File>();
            Class odlClass = ClassDescriptorFor(odl);
            Package pack = odlClass.pack;
            List<ImportDescriptor> imports = odlClass.imports;

            File javaDir = beanJava.getParentFile();
            File odlDir = beanXtm.getParentFile();

            String parentClassName = OD.ClassName(odlClass);
            /*
             * Bean
             */
            {
                PrintWriter out = new PrintWriter(new FileWriter(beanJava));
                try {
                    OD.GenerateBeanSource(beanXtm, pack, imports, odlClass, out);
                    products.add(beanJava);
                }
                finally {
                    out.close();
                }
                if (null != beans){
                    out = new PrintWriter(new FileWriter(beans,true));
                    try {
                        out.println(pack.getName()+'.'+parentClassName);
                    }
                    finally {
                        out.close();
                    }
                }
            }
            /*
             * Validate
             */
            {
                File validateXtm = new File(odlDir,"bean-validate.xtm");
                File validateFile = new File(javaDir,"validate/"+parentClassName+".java");
                PrintWriter out = new PrintWriter(new FileWriter(validateFile));
                try {
                    OD.GenerateBeanSource(validateXtm, pack, imports, odlClass, out);
                    products.add(validateFile);
                }
                finally {
                    out.close();
                }
            }
            /*
             * Servlet
             */
            {
                File servletXtm = new File(odlDir,"bean-servlet.xtm");
                File servletFile = new File(javaDir,"servlet/"+parentClassName+".java");
                PrintWriter out = new PrintWriter(new FileWriter(servletFile));
                try {
                    OD.GenerateBeanSource(servletXtm, pack, imports, odlClass, out);
                    products.add(servletFile);
                }
                finally {
                    out.close();
                }
                if (null != servlets){
                    out = new PrintWriter(new FileWriter(servlets,true));
                    try {
                        out.println(pack.getName()+".servlet."+parentClassName);
                    }
                    finally {
                        out.close();
                    }
                }
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
                                    File listFile = new File(javaDir,listClassName+".java");
                                    PrintWriter out = new PrintWriter(new FileWriter(listFile));
                                    try {
                                        OD.GenerateListSource(listShortXtm, pack, imports, odlClass, field,
                                                              parentClassName, childClassName, listClassName, listType,
                                                              out);
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
                                    File listFile = new File(javaDir,listClassName+".java");
                                    PrintWriter out = new PrintWriter(new FileWriter(listFile));
                                    try {
                                        OD.GenerateListSource(listLongXtm, pack, imports, odlClass, field,
                                                              parentClassName, childClassName, listClassName, listType, 
                                                              out);
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
        catch (Syntax syntax){

            throw new Syntax("In '"+odl+"'",syntax);
        }
    }
    /**
     * Run on directories
     * @return List of target products
     */
    public final static List<File> ProcessDirectories(File xtmFile, File odlDir, File srcDir, File beans, File servlets)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        List<File> products = new java.util.ArrayList<File>();

        Find odlFiles = new Find(ODLFiles,odlDir);
        for (File odlFile: odlFiles){
            File srcFile = SrcFile(odlDir,srcDir,odlFile);
            try {
                List<File> files = Main.ProcessFiles(xtmFile,odlFile,srcFile,beans,servlets);

                products.addAll(files);
            }
            catch (ODStateException ODStateException){
                throw new ODStateException("In '"+odlFile+"'",ODStateException);
            }
        }
        return products;
    }

    public final static void usage(java.io.PrintStream out){
        out.println("Usage");
        out.println();
        out.println("  gap.odl.Main odl src");
        out.println();
        out.println("Description");
        out.println();
        out.println("     This process is for generating java output in the Gap ODL");
        out.println("     framework.  It will generate bean, validation, servlet and list");
        out.println("     types for the input ODL packages.");
        out.println();
    }

    public final static void main(String[] argv){
        if (2 == argv.length){

            File odl = new File(argv[0]);
            File src = new File(argv[1]);
            if (odl.isDirectory() && src.isDirectory()){

                File beanXtm = new File(odl,"bean.xtm");
                File servlets = new File(src,"META-INF/services/gap.service.Servlet");
                File beans = new File(src,"META-INF/services/gap.data.BigTable");

                if (beanXtm.isFile()){

                    if (servlets.isFile())
                        servlets.delete();
                    else if (!servlets.getParentFile().isDirectory()){
                        System.err.println("Error, directory not found: '"+servlets.getPath()+"'.");
                        System.exit(1);
                        return;
                    }

                    if (beans.isFile())
                        beans.delete();
                    else if (!beans.getParentFile().isDirectory()){
                        System.err.println("Error, directory not found: '"+beans.getPath()+"'.");
                        System.exit(1);
                        return;
                    }

                    try {
                        System.out.println("Template: "+beanXtm.getPath());
                        System.out.println("Source: "+odl.getPath());
                        System.out.println("Target: "+src.getPath());
                        List<File> products = Main.ProcessDirectories(beanXtm,odl,src,beans,servlets);
                        for (File product : products){
                            System.out.println("Product: "+product.getPath());
                        }
                        System.out.println("Product: "+servlets.getPath());
                        System.out.println("Product: "+beans.getPath());
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
                    System.err.println("Error, file not found: '"+beanXtm.getPath()+"'.");
                    System.exit(1);
                    return;
                }
            }
            else {
                if (!odl.isDirectory()){
                    System.err.println("Error, directory not found: '"+odl.getPath()+"'.");

                    if (!src.isDirectory())
                        System.err.println("Error, directory not found: '"+src.getPath()+"'.");

                    System.exit(1);
                    return;
                }
                else {
                    System.err.println("Error, directory not found: '"+src.getPath()+"'.");
                    System.exit(1);
                    return;
                }
            }
        }
        else {
            usage(System.err);
            System.exit(1);
            return;
        }
    }

    public final static class FileFilter
        extends Object
        implements java.io.FileFilter
    {

        public FileFilter(){
            super();
        }

        public boolean accept(File file){
            if (file.isFile())
                return (file.getName().endsWith(".odl"));
            else
                return (!file.getName().equals(".svn"));
        }
    }
    public final static FileFilter ODLFiles = new FileFilter();

    public final static File SrcFile(File odlDir, File srcDir, File odlFile){
        String odlDirPath = odlDir.getPath();
        String odlFilePath = odlFile.getPath();
        String fileLocal = odlFilePath.substring(odlDirPath.length(),odlFilePath.length()-4);
        while ('/' == fileLocal.charAt(0))
            fileLocal = fileLocal.substring(1);
        return new File(srcDir, fileLocal+".java");
    }
}
