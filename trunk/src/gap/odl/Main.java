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

import gap.service.OD;
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
     * Run on files
     */
    public final static void Process(File xtm, File odl, PrintWriter out)
        throws IOException, TemplateException, Syntax, ODStateException
    {
        Reader odlReader = new Reader(odl);
        Class odlClass = new Class(odlReader);

        OD.GenerateSource(xtm, odlClass.pack, odlClass.imports, odlClass, out);
    }
    /**
     * Run on directories
     * @return List of target products
     */
    public final static List<File> Process(File xtmFile, File odlDir, File outDir)
        throws IOException, TemplateException, Syntax, ODStateException
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
                    catch (ODStateException ODStateException){
                        throw new ODStateException("In '"+odlFile+"'",ODStateException);
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
