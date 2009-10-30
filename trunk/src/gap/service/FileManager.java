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
package gap.service;

import gap.*;
import gap.data.*;
import gap.jela.JelaFunction;
import gap.util.*;
import gap.service.jac.*;

import gap.jac.tools.FileObject;
import gap.jac.tools.JavaCompiler;
import gap.jac.tools.JavaFileObject;
import gap.jac.tools.JavaFileObject.Kind;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.JavaFileManager.Location;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.ToolProvider;

import com.google.appengine.api.datastore.Blob;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

/**
 * Instantiated per request for infinite many locations and stateful
 * process handling.
 * 
 * @see Compiler
 * @author jdp
 */
public class FileManager
    extends java.lang.ClassLoader
    implements gap.jac.tools.JavaFileManager
{
    public final static Charset UTF8 = Charset.forName("UTF-8");

    protected final static Logger Log = Logger.getLogger(FileManager.class.getName());


    public final static FileManager Get(){
        ClassLoader cxl = Thread.currentThread().getContextClassLoader();
        if (cxl instanceof FileManager)
            return (FileManager)cxl;
        else
            return null;
    }
    public final static void Exit(){
        Thread current = Thread.currentThread();
        ClassLoader cxl = current.getContextClassLoader();
        if (cxl instanceof FileManager){
            ClassLoader parent = cxl.getParent();
            current.setContextClassLoader(parent);
        }
    }



    public static class StringLocation
        extends java.lang.Object
        implements Location
    {
        
        private final String name;

        private final boolean isOutput;


        public StringLocation(String name){
            this(name,true);
        }
        public StringLocation(String name, boolean canOutput){
            super();
            if (null != name){
                this.name = name;
                this.isOutput = canOutput;
            }
            else {
                this.name = "";
                this.isOutput = canOutput;
            }
        }


        public String getName(){
            return this.name;
        }
        public boolean isRoot(){
            return (0 == name.length());
        }
        public boolean isOutputLocation(){
            return this.isOutput;
        }
    }
    /**
     * Complete compilation error results.
     */
    public final static class CompileError
        extends java.lang.IllegalStateException
    {
        public final String classname;
        /**
         * {@link gap.data.Resource} or {@link gap.data.Tool}
         */
        public final BigTable descriptor;
        public final String errors;


        CompileError(String classname, BigTable desc, StringWriter err){
            super(classname);
            this.classname = classname;
            this.descriptor = desc;
            this.errors = err.toString();
        }
    }


    public final Location location;

    public final Map<URI,Output> output = new java.util.HashMap<URI,Output>();

    public final Map<URI,Input> input = new java.util.HashMap<URI,Input>();

    /**
     * Define thread context class loader
     */
    public FileManager(String location){
        this(Thread.currentThread(),(new StringLocation(location)));
    }
    public FileManager(String location, boolean canOutput){
        this(Thread.currentThread(),(new StringLocation(location,canOutput)));
    }
    private FileManager(Thread current, Location location){
        this(current,current.getContextClassLoader(),location);
    }
    private FileManager(Thread current, ClassLoader context, Location location){
        super(context);
        if (null != location){
            this.location = location;
            current.setContextClassLoader(this);
        }
        else
            throw new IllegalArgumentException();
    }



    public Servlet getServlet(Path path){

        String base = "";
        String name = null;
        if (path.hasBase()){
            if (path.hasName()){
                base = path.getBase();
                name = path.getName();
            }
            else {
                name = path.getBase();
            }
        }
        else {
            name = "";
        }

        Servlet servlet = null;

        Resource desc = Resource.ForLongBaseName(base,name);
        if (null != desc)
            servlet = this.getServlet(desc);

        return servlet;
    }
    public Servlet getServlet(Resource desc){

        String servletClassName = desc.getServletClassname(true);
        if (null != servletClassName){
            try {
                Class<gap.service.Servlet> jclass;
                try {
                    jclass = (Class<gap.service.Servlet>)Class.forName(servletClassName);
                }
                catch (ClassNotFoundException not){

                    jclass = this.define(desc);
                }
                if (null != jclass){
                    Servlet servlet = jclass.newInstance();
                    servlet.init(Servlet.Config);
                    return servlet;
                }
                else
                    return null;
            }
            catch (Exception any){
                LogRecord rec = new LogRecord(Level.SEVERE,"error");
                rec.setThrown(any);
                FileManager.Log.log(rec);
                return null;
            }
        }
        else
            return null;
    }
    public boolean compile(Resource desc)
        throws java.io.IOException,
               FileManager.CompileError
    {
        String className = desc.getServletClassname(true);
        String sourceText = gap.Strings.TextToString(desc.getServletSourceJava(true));

        if (null != className && null != sourceText){

            Reader in = new java.io.StringReader(sourceText); 
            StringWriter err = new StringWriter();
            ByteArrayOutputStream bin = new ByteArrayOutputStream();

            URI uri = FileManager.ToUri(className);

            List<JavaFileObject> units = new java.util.ArrayList<JavaFileObject>(1);
            {
                units.add(new Input(uri,Kind.SOURCE,in));
            }

            this.output.put(uri,new Output(uri,Kind.CLASS,bin));

            JavaCompiler tool  = new gap.jac.api.JavacTool();
            try {
                gap.jac.tools.JavaCompiler.CompilationTask task =
                    tool.getTask(err, this, null, Options, null, units);

                if (task.call()){
                    desc.setServletClassfileJvm(new Blob(bin.toByteArray()));
                    desc.save();
                    return true;
                }
                else
                    throw new CompileError(className,desc,err);
            }
            finally {
                tool.destroy();
            }
        }
        else 
            return false;
    }

    public Class<gap.service.Servlet> define(Resource desc){
        String classname = desc.getServletClassname(true);
        if (null != classname){
            Blob classfile = desc.getServletClassfileJvm(true);
            if (null != classfile)
                return (Class<gap.service.Servlet>)this.define(classname,classfile.getBytes());
        }
        return null;
    }

    public Function getFunction(Servlet instance, Request request, Response response, Resource resource, Tool desc){

        String functionClassName = desc.getFunctionClassname(true);
        if (null != functionClassName){
            try {
                Class jclass;
                try {
                    jclass = Class.forName(functionClassName);
                }
                catch (ClassNotFoundException not){

                    jclass = this.define(desc);
                }
                if (null != jclass){
                    Function.Constructor ctor = new Function.Constructor(jclass);

                    return ctor.create(instance, request, response, resource, desc);
                }
                else
                    return null;
            }
            catch (Exception any){
                LogRecord rec = new LogRecord(Level.SEVERE,"error");
                rec.setThrown(any);
                FileManager.Log.log(rec);
                return null;
            }
        }
        else
            return null;
    }

    public boolean compile(Resource resource, Tool desc)
        throws java.io.IOException,
               FileManager.CompileError
    {
        String className = desc.getFunctionClassname(true);
        String sourceText = gap.Strings.TextToString(desc.getFunctionSourceJava(true));
        if (null == sourceText){
            JelaFunction prog = new JelaFunction(resource,desc);
            sourceText = prog.getSource();
            desc.setFunctionSourceJava(Strings.TextFromString(sourceText));
            desc.setFunctionClassname(prog.fullClassName);
        }

        if (null != className && null != sourceText){

            Reader in = new java.io.StringReader(sourceText); 
            StringWriter err = new StringWriter();
            ByteArrayOutputStream bin = new ByteArrayOutputStream();

            URI uri = FileManager.ToUri(className);

            List<JavaFileObject> units = new java.util.ArrayList<JavaFileObject>(1);
            {
                units.add(new Input(uri,Kind.SOURCE,in));
            }

            this.output.put(uri,new Output(uri,Kind.CLASS,bin));

            JavaCompiler tool  = new gap.jac.api.JavacTool();
            try {
                gap.jac.tools.JavaCompiler.CompilationTask task =
                    tool.getTask(err, this, null, Options, null, units);

                if (task.call()){
                    desc.setFunctionClassfileJvm(new Blob(bin.toByteArray()));
                    desc.save();
                    return true;
                }
                else
                    throw new CompileError(className,desc,err);
            }
            finally {
                tool.destroy();
            }
        }
        else 
            return false;
    }

    public Class<gap.service.Function> define(Tool desc){
        String classname = desc.getFunctionClassname(true);
        if (null != classname){
            Blob classfile = desc.getFunctionClassfileJvm(true);
            if (null != classfile)
                return (Class<gap.service.Function>)this.define(classname,classfile.getBytes());
        }
        return null;
    }
    protected Class define(String className, byte[] b){
        if (null != b)
            return super.defineClass(className,b,0,b.length);
        else
            return null;
    }
    /*
     */
    public int isSupportedOption(String option){
        return -1;
    }
    public ClassLoader getClassLoader(Location location){
        return this;
    }
    public Iterable<JavaFileObject> list(Location location,
                                         String packageName,
                                         Set<Kind> kinds,
                                         boolean recurse)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }
    public String inferBinaryName(Location location, JavaFileObject file){

        URI uri = file.toUri();

        String path = uri.getRawPath();

        if (!path.endsWith(".class")){
            int idx = path.lastIndexOf('.');
            if (-1 != idx)
                return (path.substring(0,idx)+".class");
        }
        return path;
    }
    public boolean isSameFile(FileObject a, FileObject b){

        return (a.equals(b));
    }
    public boolean handleOption(String current, Iterator<String> remaining){

        return false;
    }
    public boolean hasLocation(Location location){
        return true;
    }
    public boolean hasLocation(){
        return true;
    }
    public String getLocation(){
        return this.location.getName();
    }

    public JavaFileObject getJavaFileForInput(Location location,
                                              String className,
                                              Kind kind)
        throws IOException
    {
        return this.input.get(ToUri(className));
    }
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className,
                                               Kind kind,
                                               FileObject sibling)
        throws IOException
    {
        return this.output.get(ToUri(className));
    }

    public FileObject getFileForInput(Location location,
                               String packageName,
                               String relativeName)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public FileObject getFileForOutput(Location location,
                                       String packageName,
                                       String relativeName,
                                       FileObject sibling)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public void flush() throws IOException {
    }
    public void close() throws IOException {
    }

    public String toString(){
        Location location = this.location;
        if (null != location)
            return location.getName();
        else
            return "";
    }

    public final static URI ToUri(String name) {
        StringBuilder strbuf = new StringBuilder();
        strbuf.append("mfm:///");
        strbuf.append(name.replace('.', '/'));

        return URI.create(strbuf.toString());
    }


    public final static Resource GetResource(Path path){

        return Resource.ForLongBaseName(path.getBase(),path.getName());
    }
    public final static Resource GetCreateResource(Path path){

        return Resource.GetCreateLong(path.getBase(),path.getName());
    }
    public final static Tool GetTool(Resource resource, Method method, String name){
        if (null != resource){
            if (null != name)
                return resource.getTools(name);
            else
                return resource.getTools(method.lower);
        }
        else
            return null;
    }
    public final static String DerivePackage(Resource resource){
        String base = resource.getBase();
        String name = resource.getName();
        if (null != base && 0 != base.length()){
            if (0 != name.length())
                return (OD.Decamel(base)+'/'+OD.Decamel(name));
            else
                throw new IllegalStateException();
        }
        else if (0 != name.length())
            return OD.Decamel(name);
        else
            throw new IllegalStateException();
    }

    public final static String GetPath(Resource desc){
        String base = desc.getBase();
        String name = desc.getName();
        return GetPath(base,name);
    }
    public final static String GetPath(String base, String name){
        if (null != name){
            if (null == base)
                return name;
            else
                return base+'/'+name;
        }
        else if (null == base)
            return null;
        else
            return base;
    }
    public final static String GetPath(Path path){
        String base = null;
        String name = null;
        if (path.hasBase()){
            if (path.hasName()){
                base = path.getBase();
                name = path.getName();
            }
            else {
                name = path.getBase();
            }
        }
        return GetPath(base,name);
    }


    private final static List<String> Options;
    static {
        List<String> list = new java.util.ArrayList<String>();
        list.add("-g");
        list.add("-Xlint:none");
        list.add("-encoding");
        list.add("UTF-8");
        list.add("-source");
        list.add("1.6");
        list.add("-target");
        list.add("1.6");
        Options = Collections.unmodifiableList(list);
    }

}
