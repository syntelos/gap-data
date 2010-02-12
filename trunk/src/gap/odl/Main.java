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
import gap.hapax.TemplateException;
import gap.hapax.TemplateName;
import gap.service.OD;
import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;
import gap.service.od.ODStateException;

import alto.io.u.Find;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    public final static class TemplateNames {
        public final static TemplateName BeanUser = new TemplateName("BeanUser.java");
        public final static TemplateName BeanData = new TemplateName("BeanData.java");
        public final static TemplateName BeanServlet = new TemplateName("BeanServlet.java");
        public final static TemplateName ListLong = new TemplateName("ListLong.java");
        public final static TemplateName ListShort = new TemplateName("ListShort.java");
        public final static TemplateName MapLong = new TemplateName("MapLong.java");
        public final static TemplateName MapShort = new TemplateName("MapShort.java");
        public final static TemplateName WebXml = new TemplateName("web.xml");
    }

    private final static lxl.Map<String,Class> Classes = new lxl.Map<String,Class>();
    private final static lxl.Map<String,File> Files = new lxl.Map<String,File>();

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
    public final static Class ClassDescriptorFor(gap.data.HasName clas)
        throws IOException, Syntax
    {
        return ClassDescriptorFor(clas.getName());
    }
    /**
     * @param name Class base name, typically the big table KIND name.
     */
    public final static Class ClassDescriptorFor(String name)
        throws IOException, Syntax
    {
        Class desc;
        synchronized(Classes){
            desc = Classes.get(name);
        }
        if (null == desc){
            if (Files.isEmpty()){
                SInit();
            }
            File file = Files.get(name);
            if (null != file){
                Reader odlReader = new Reader(file);
                try {
                    desc = (new Class(odlReader));
                }
                catch (Syntax exc){
                    throw new Syntax("In '"+file+"'.",exc);
                }
                finally {
                    odlReader.close();
                }
                synchronized(Classes){
                    Classes.put(name,desc);
                }
            }
        }
        return desc;
    }
    public final static Class ClassDescriptorForData(String name)
        throws IOException, Syntax
    {
        if (null != name && name.endsWith("Data"))
            return ClassDescriptorFor(name.substring(0,name.length()-4));
        else
            return null;
    }
    public final static Class ClassDescriptorForServlet(String name)
        throws IOException, Syntax
    {
        if (null != name && name.endsWith("Servlet"))
            return ClassDescriptorFor(name.substring(0,name.length()-7));
        else
            return null;
    }
    /**
     * Generate bean, validate, servlet and list classes.
     */
    public final static lxl.List<File> ProcessFiles(File odl, File src, File beans, File servlets)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        try {
            lxl.List<File> products = new lxl.ArrayList<File>();
            Class clas = ClassDescriptorFor(odl);
            Package pack = clas.pack;
            lxl.List<ImportDescriptor> imports = clas.imports;

            String parentClassName = OD.ClassName(clas);
            String packageName = OD.PackageName(pack);
            File packagePath = new File(src,packageName.replace('.','/'));
            /*
             * Bean Data
             */
            {
                File beanJava = new File(packagePath,parentClassName+"Data.java");
                PrintWriter out = new PrintWriter(new FileWriter(beanJava));
                try {
                    OD.GenerateBeanSource(TemplateNames.BeanData, pack, imports, clas, out);
                    products.add(beanJava);
                }
                finally {
                    out.close();
                }
            }
            /*
             * Bean User
             */
            {
                File beanJava = new File(packagePath,parentClassName+".java");
                if (!beanJava.exists()){
                    PrintWriter out = new PrintWriter(new FileWriter(beanJava));
                    try {
                        OD.GenerateBeanSource(TemplateNames.BeanUser, pack, imports, clas, out);
                        products.add(beanJava);
                    }
                    finally {
                        out.close();
                    }
                }
            }
            /*
             * Bean Servlet
             */
            {
                File servletJava = new File(packagePath,parentClassName+"Servlet.java");
                if (!servletJava.exists()){
                    PrintWriter out = new PrintWriter(new FileWriter(servletJava));
                    try {
                        if (OD.GenerateServletSource(TemplateNames.BeanServlet, pack, imports, clas, out)){
                            products.add(servletJava);
                            /*
                             * Services Record
                             */
                            if (null != servlets){
                                PrintWriter services = new PrintWriter(new FileWriter(servlets,true));
                                try {
                                    services.println(pack.getName()+'.'+parentClassName+"Servlet");
                                }
                                finally {
                                    services.close();
                                }
                            }
                        }
                    }
                    finally {
                        out.close();
                    }
                }
            }
            /*
             * Services Record
             */
            if (null != beans){
                PrintWriter services = new PrintWriter(new FileWriter(beans,true));
                try {
                    services.println(pack.getName()+'.'+parentClassName);
                }
                finally {
                    services.close();
                }
            }
            /*
             * Lists
             */
            lxl.List<FieldDescriptor> fieldsOfList = OD.FieldsOfTypeList(pack, clas, imports);

            if (!fieldsOfList.isEmpty()){

                for (FieldDescriptor field: fieldsOfList){
                    String typeName = OD.ToString(field.getType());
                    gap.data.List.Type listType = gap.data.List.Type.For(typeName);
                    if (null != listType){
                        switch(listType){
                        case ListPrimitive:
                            break;
                        case ListShort:{
                                String childClassName = OD.ListChildClassName(field);
                                String listClassName = OD.ListShortClassName(parentClassName,childClassName);
                                if (null != listClassName){
                                    File listFile = new File(packagePath,listClassName+".java");
                                    PrintWriter out = new PrintWriter(new FileWriter(listFile));
                                    try {
                                        OD.GenerateListSource(TemplateNames.ListShort, pack, imports, clas, field,
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
                        case ListLong:{
                                String childClassName = OD.ListChildClassName(field);
                                String listClassName = OD.ListLongClassName(parentClassName,childClassName);
                                if (null != listClassName){
                                    File listFile = new File(packagePath,listClassName+".java");
                                    PrintWriter out = new PrintWriter(new FileWriter(listFile));
                                    try {
                                        OD.GenerateListSource(TemplateNames.ListLong, pack, imports, clas, field,
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
            /*
             * Maps
             */
            lxl.List<FieldDescriptor> fieldsOfMap = OD.FieldsOfTypeMap(pack, clas, imports);
            if (!fieldsOfMap.isEmpty()){

                for (FieldDescriptor field: fieldsOfMap){
                    String typeName = OD.ToString(field.getType());
                    gap.data.Map.Type mapType = gap.data.Map.Type.For(typeName);
                    if (null != mapType){
                        switch(mapType){
                        case MapPrimitive:
                            break;
                        case MapShort:{
                                OD.MapChild mapChild = new OD.MapChild(field);
                                String mapClassName = OD.MapShortClassName(parentClassName,mapChild);
                                if (null != mapClassName){
                                    File mapFile = new File(packagePath,mapClassName+".java");
                                    PrintWriter out = new PrintWriter(new FileWriter(mapFile));
                                    try {
                                        OD.GenerateMapSource(TemplateNames.MapShort, pack, imports, clas, field,
                                                             parentClassName, mapClassName, mapType,
                                                             mapChild, out);
                                        products.add(mapFile);
                                    }
                                    finally {
                                        out.close();
                                    }
                                }
                            }
                            break;
                        case MapLong:{
                                OD.MapChild mapChild = new OD.MapChild(field);
                                String mapClassName = OD.MapLongClassName(parentClassName,mapChild);
                                if (null != mapClassName){
                                    File mapFile = new File(packagePath,mapClassName+".java");
                                    PrintWriter out = new PrintWriter(new FileWriter(mapFile));
                                    try {
                                        OD.GenerateMapSource(TemplateNames.MapLong, pack, imports, clas, field,
                                                             parentClassName, mapClassName, mapType, 
                                                             mapChild, out);
                                        products.add(mapFile);
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
    public final static lxl.List<File> ProcessDirectories(File odlDir, File src, File beans, File servlets)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        lxl.List<File> products = new lxl.ArrayList<File>();

        SInit();

        for (File odlFile: Files.values()){
            try {
                lxl.List<File> files = Main.ProcessFiles(odlFile,src,beans,servlets);

                products.addAll(files);
            }
            catch (ODStateException ODStateException){
                throw new ODStateException("In '"+odlFile+"'",ODStateException);
            }
        }
        return products;
    }
    /**
     * Generate 'web.xml' from servlets service file plus defaults.
     * @return List of target products
     */
    public final static File ProcessServlets(File servletsService)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        lxl.List<String> servlets = gap.util.Services.Read(servletsService);

        File webXml = new File("ver/web/WEB-INF/web.xml");
        PrintWriter out = new PrintWriter(new FileWriter(webXml));
        try {
            OD.GenerateWebXml(TemplateNames.WebXml,servlets,out);
        }
        catch (ODStateException ODStateException){
            throw new ODStateException("In '"+servletsService+"'",ODStateException);
        }
        finally {
            out.close();
        }
        return webXml;
    }


    public final static void usage(java.io.PrintStream out){
        out.println("Usage");
        out.println();
        out.println("  gap.odl.Main ");
        out.println();
        out.println("Description");
        out.println();
        out.println("     This process is for generating java output in the Gap ODL");
        out.println("     framework.  It will generate bean, validation, servlet and list");
        out.println("     types for the input ODL packages.");
        out.println();
    }

    public final static void main(String[] argv){
        if (0 == argv.length){

            File odl = new File("odl");
            File src = new File("src");
            if (odl.isDirectory() && src.isDirectory()){

                File beans = new File(src,"META-INF/services/gap.data.BigTable");
                if (!DropTouch(beans)){
                    if (!beans.getParentFile().isDirectory()){
                        System.err.println("Error, parent directory not found: '"+beans.getPath()+"'.");
                        System.exit(1);
                        return;
                    }
                    else {
                        System.err.println("Error, file not writeable: '"+beans.getPath()+"'.");
                        System.exit(1);
                        return;
                    }
                }

                File servlets = new File(src,"META-INF/services/gap.service.Servlet");
                if (!DropTouch(servlets)){
                    if (!servlets.getParentFile().isDirectory()){
                        System.err.println("Error, parent directory not found: '"+servlets.getPath()+"'.");
                        System.exit(1);
                        return;
                    }
                    else {
                        System.err.println("Error, file not writeable: '"+servlets.getPath()+"'.");
                        System.exit(1);
                        return;
                    }
                }

                gap.util.Main appenv = gap.util.Main.Install();
                int rc = 0;
                try {
                    System.out.println("Source: "+odl.getPath());
                    System.out.println("Target: "+src.getPath());
                    lxl.List<File> products = Main.ProcessDirectories(odl,src,beans,servlets);
                    for (File product : products){
                        System.out.println("Product: "+product.getPath());
                    }
                    System.out.println("Product: "+beans.getPath());
                    System.out.println("Product: "+servlets.getPath());
                    File webXml = Main.ProcessServlets(servlets);
                    System.out.println("Product: "+webXml.getPath());
                }
                catch (Throwable thrown){
                    thrown.printStackTrace();
                    rc = 1;
                }
                finally {
                    appenv.destroy();
                    System.exit(rc);
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

    public final static class FindClass
        extends Object
        implements java.io.FileFilter
    {
        private final String name;

        public FindClass(String name){
            super();
            this.name = name+".odl";
        }

        public boolean accept(File file){
            if (file.isFile())
                return (this.name.equals(file.getName()));
            else
                return (!file.getName().equals(".svn"));
        }
    }


    static void SInit(){
        if (Files.isEmpty()){
            File odlDir = new File("odl");
            Find find = new Find(ODLFiles,odlDir);
            while (find.hasNext()){
                File odl = find.next();
                String name = ClassName(odl);
                Files.put(name,odl);
            }
        }
    }
    /**
     * File content cleared for appending.
     */
    private final static boolean DropTouch(File file){
        try {
            new java.io.FileOutputStream(file).close();
            return true;
        }
        catch (Exception err){
            return false;
        }
    }
}
