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

import gap.data.TemplateDescriptor;
import gap.data.ServletDescriptor;
import gap.service.jac.JavaClassOutput;
import gap.service.jac.JavaSourceInput;

import gap.jac.tools.FileObject;
import gap.jac.tools.JavaCompiler;
import gap.jac.tools.JavaFileObject;
import gap.jac.tools.JavaFileObject.Kind;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.JavaFileManager.Location;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.ToolProvider;

import hapax.Template;
import hapax.TemplateException;
import hapax.TemplateLoaderContext;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public final Map<URI,JavaClassOutput> output = new java.util.HashMap<URI,JavaClassOutput>();

    public final Map<URI,JavaSourceInput> input = new java.util.HashMap<URI,JavaSourceInput>();

    public final Map<String,Template> templates = new java.util.HashMap<String,Template>();

    public final TemplateLoaderContext templatesContext;

    /**
     * Use parent class loader
     */
    public FileManager(ClassLoader parent, String location){
        this(parent, (new StringLocation(location)));
    }
    public FileManager(ClassLoader parent, String location, boolean canOutput){
        this(parent, (new StringLocation(location,canOutput)));
    }
    public FileManager(ClassLoader parent, Location location){
        super(parent);
        if (null != location){
            this.location = location;
            this.templatesContext = Templates.CreateTemplateLoaderContext(this,location);
        }
        else
            throw new IllegalArgumentException();
    }
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
            this.templatesContext = Templates.CreateTemplateLoaderContext(this,location);
        }
        else
            throw new IllegalArgumentException();
    }



    public String getTemplatePath(TemplateDescriptor templateD){
//         String name = templateD.getName();
//         String base = templateD.getBase();
//         if (null != name){
//             if (null == base)
//                 return name;
//             else
//                 return base+'/'+name;
//         }
//         else if (null == base)
//             return null;
//         else
//             return base;
        return null;
    }
    public Template getTemplate(String path)
        throws TemplateException
    {

        if (null != path){

            Template template = this.templates.get(path);

            if (null == template)
                template = Templates.GetTemplate(path);

            return template;
        }
        else
            return null;
    }
    public Template getTemplate(TemplateDescriptor templateD)
        throws TemplateException
    {

        String path = this.getTemplatePath(templateD);
        if (null != path){
            Template template = this.templates.get(path);
            if (null == template){
                template = Templates.GetTemplate(this.templatesContext,templateD,path);
                if (null == template)
                    template = Templates.GetTemplate(path);
                else 
                    this.templates.put(path,template);
            }
            return template;
        }
        else
            return null;
    }
    public Servlet getServlet(ServletDescriptor servletD){

//         String servletClassName = servletD.getServletClassName();
//         if (null != servletClassName){
//             Class jclass = this.findClass(servletClassName);
//             if (null == jclass){
//                 jclass = this.define(servletD);
//             }
//             return jclass;
//         }
//         else
        return null;
    }
    public boolean compile(ServletDescriptor servletD)
        throws java.io.IOException
    {
        String className = null;//servletD.getServletClassName()
        String sourceText = null;//Unwrap(servletD.getServletSource());

        if (null != className && null != sourceText){

            Reader in = new java.io.StringReader(sourceText); 
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            ByteArrayOutputStream bin = new ByteArrayOutputStream();

            URI uri = FileManager.ToUri(className);

            List<JavaFileObject> units = new java.util.ArrayList<JavaFileObject>(1);
            {
                units.add(new JavaSourceInput(uri,in));
            }

            this.output.put(uri,new JavaClassOutput(uri,bin));

            JavaCompiler tool  = new gap.jac.api.JavacTool();
            try {
                gap.jac.tools.JavaCompiler.CompilationTask task =
                    tool.getTask((new OutputStreamWriter(err,UTF8)), this, null, Options, null, units);

                if (task.call()){
                    //servletD.setServletTargetBinary(bin.toByteArray());
                    return true;
                }
                else
                    return false;//throw new CompileError(servletD,err);
            }
            finally {
                tool.destroy();
            }
        }
        else 
            return false;
    }

    public Class define(ServletDescriptor servletD){
        //return this.define(servletD.getServletClassName(),servletD.getServletTargetBinary());
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

    public int hashCode(){
        return this.location.getName().hashCode();
    }
    public String toString(){
        return this.location.getName();
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (that instanceof FileManager)
            return (this.location.getName().equals( ((FileManager)that).location.getName()));
        else
            return false;
    }
    public final static URI ToUri(String name) {
        StringBuilder strbuf = new StringBuilder();
        strbuf.append("mfm:///");
        strbuf.append(name.replace('.', '/'));

        return URI.create(strbuf.toString());
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
