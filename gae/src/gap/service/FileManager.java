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

import gap.data.Resource;
import gap.jac.tools.FileObject;
import gap.jac.tools.JavaFileObject;
import gap.jac.tools.JavaFileObject.Kind;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.JavaFileManager.Location;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.ToolProvider;
import gap.service.jac.Input;
import gap.service.jac.Output;
import gap.util.AbstractList;

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
        Servlet servlet = null;

        return servlet;
    }
    public Servlet getServlet(Resource desc){

        String servletClassName = desc.getJavaClassName(true);
        if (null != servletClassName){
            try {
                Class<gap.service.Servlet> jclass;
                try {
                    jclass = (Class<gap.service.Servlet>)Class.forName(servletClassName);
                }
                catch (ClassNotFoundException not){

                    jclass = (Class<gap.service.Servlet>)this.define(desc);
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
    public Class define(Resource desc){
        String classname = desc.getJavaClassName(true);
        if (null != classname){
            Blob classfile = desc.getJavaClassBinary(true);
            if (null != classfile)
                return (Class<gap.service.Servlet>)this.define(classname,classfile.getBytes());
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
    /*
     */
    public final static URI ToUri(String name) {
        StringBuilder strbuf = new StringBuilder();
        strbuf.append("mfm:///");
        strbuf.append(name.replace('.', '/'));

        return URI.create(strbuf.toString());
    }
    public final static String DerivePackage(Resource resource){
        String fullname = resource.getJavaClassName(true);
        if (null != fullname){
            int idx = fullname.lastIndexOf('.');
            if (-1 != idx){
                return fullname.substring(0,idx);
            }
        }
        throw new IllegalStateException(fullname);
    }
    public final static String DeriveName(Resource resource){
        String fullname = resource.getJavaClassName(true);
        if (null != fullname){
            int idx = fullname.lastIndexOf('.');
            if (-1 != idx){
                return fullname.substring(idx+1);
            }
        }
        return null;
    }
    public final static String DerivePackage(AbstractList list){
        return OD.Decamel(list.getChildTypeName());
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
