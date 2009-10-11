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
package gap.jela;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import gap.jac.tools.FileObject;
import gap.jac.tools.JavaCompiler;
import gap.jac.tools.JavaFileObject;
import gap.jac.tools.JavaFileObject.Kind;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.JavaFileManager.Location;
import gap.jac.tools.JavaFileManager;
import gap.jac.tools.ToolProvider;
import javax.script.ScriptContext;
import javax.script.ScriptException;

/**
 * Simple interface to Java compiler using JSR 199 Compiler API, and
 * an in memory JavaFileManager.
 * 
 * @author J. Pritchard
 * @since 1.6
 */
public final class JelaProgramCompiler
    extends Object
    implements JavaFileManager
{

    public final boolean alive;

    private final JavaFileManager stdFileManager;

    private final boolean writeClassFiles = false;

    private JelaProgram program;

    private ScriptContext context;

    private JavaCompiler tool;

    private volatile byte[] classfile;

    private CharArrayWriter errors;

    private LineNumbered lined;


    public JelaProgramCompiler(JelaProgram program) {
        this(program,program.getContext());
    }
    public JelaProgramCompiler(JelaProgram program, ScriptContext context) {
        this(program,context,ToolProvider.getSystemJavaCompiler());
    }
    private JelaProgramCompiler(JelaProgram program, ScriptContext context, JavaCompiler compiler){
        this(program,context,compiler,
             ((null != compiler)?(compiler.getStandardFileManager(null, null, null)):(null)));
    }
    private JelaProgramCompiler(JelaProgram program, ScriptContext context, JavaCompiler compiler, 
                               JavaFileManager stdfm)
    {
        super();
        this.stdFileManager = stdfm;
        this.alive = (null != stdfm);
        this.program = program;
        this.context = context;
        this.tool = compiler;
    }


    public boolean isAlive(){
        return this.alive;
    }
    public void completed(byte[] classfile){
        this.classfile = classfile;
        if (this.writeClassFiles){
            try {
                File file = new File("debug/"+this.program.fqfn+".class");
                FileOutputStream debug = new FileOutputStream(file);
                try {
                    debug.write(classfile,0,classfile.length);
                    debug.flush();
                }
                finally {
                    debug.close();
                }
            }
            catch (Throwable t){
                return;
            }
        }
    }
    public String getClassName(){
        return this.program.fqcn;
    }
    public String getSource(){
        return this.program.toString();
    }
    public String getSourceLineNumbered(){
        LineNumbered lined = this.lined;
        if (null == lined){
            lined = new LineNumbered(this.getSource());
            this.lined = lined;
        }
        return lined.toString();
    }
    public String getErrors(){
        CharArrayWriter errors = this.errors;
        if (null != errors)
            return errors.toString();
        else
            return null;
    }
    public byte[] getClassFile(){
        return this.classfile;
    }
    /**
     * @see #alive
     * @see #isAlive()
     */
    public boolean compile() {
        if (alive){
            List<JavaFileObject> units = new ArrayList<JavaFileObject>(1);
            {
                units.add(this.program.getInputSource());
            }
            this.errors = new CharArrayWriter();

            gap.jac.tools.JavaCompiler.CompilationTask task =
                this.tool.getTask(this.errors, this, null, Options, null, units);

            return task.call();
        }
        else
            return false;
    }
    /**
     * If the platform compiler is unavailable, revert to supporting
     * the JRE environment via precompiled binaries from
     * "debug/jela0/gen" already found in the runtime classpath.
     * @see #alive
     * @see #isAlive()
     */
    public JavaCompiledScript getCompiledScript(JelaScriptEngine je)
        throws ScriptException
    {
        if (alive){
            JelaClassLoader jcl = JelaClassLoader.Instance();
            Class clas = jcl.classFor(this);
            if (null != clas){
                JavaCompiledScript.Constructor ctor = new JavaCompiledScript.Constructor(clas);
                return ctor.create(je);
            }
            else 
                throw new JelaProgramException(this);
        }
        else {
            String classname = this.getClassName();
            try {
                Class clas = Class.forName(classname);
                JavaCompiledScript.Constructor ctor = new  JavaCompiledScript.Constructor(clas);
                return ctor.create(je);
            }
            catch (ClassNotFoundException exc){
                throw new ScriptException("The platform compiler is unavailable, require precompiled binaries in class path.");
            }
        }
    }
    /*
     * gap.jac.tools File Manager
     */
    public ClassLoader getClassLoader(Location location) {
        if (this.alive)
            return this.stdFileManager.getClassLoader(location);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public Iterable<JavaFileObject> list(Location location,
                                         String packageName,
                                         Set<Kind> kinds,
                                         boolean recurse)
        throws IOException
    {
        if (this.alive)
            return this.stdFileManager.list(location, packageName, kinds, recurse);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public String inferBinaryName(Location location, JavaFileObject file) {
        if (this.alive)
            return this.stdFileManager.inferBinaryName(location, file);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public boolean isSameFile(FileObject a, FileObject b) {
        if (this.alive)
            return this.stdFileManager.isSameFile(a, b);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public boolean handleOption(String current, Iterator<String> remaining) {
        if (this.alive)
            return this.stdFileManager.handleOption(current, remaining);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }

    public boolean hasLocation(Location location) {
        if (this.alive)
            return this.stdFileManager.hasLocation(location);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }

    public int isSupportedOption(String option) {
        if (this.alive)
            return this.stdFileManager.isSupportedOption(option);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public JavaFileObject getJavaFileForInput(Location location,
                                              String className,
                                              Kind kind)
        throws IOException
    {
        if (this.alive)
            return this.stdFileManager.getJavaFileForInput(location, className, kind);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className,
                                               Kind kind,
                                               FileObject sibling)
        throws IOException
    {
        if (this.alive){
            if (kind == Kind.CLASS)
                return new JavaClassOutputBuffer(this,className);
            else 
                return this.stdFileManager.getJavaFileForOutput(location, className, kind, sibling);
        }
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public FileObject getFileForInput(Location location,
                                      String packageName,
                                      String relativeName)
        throws IOException
    {
        if (this.alive)
            return this.stdFileManager.getFileForInput(location, packageName, relativeName);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public FileObject getFileForOutput(Location location,
                                       String packageName,
                                       String relativeName,
                                       FileObject sibling)
        throws IOException
    {
        if (this.alive)
            return this.stdFileManager.getFileForOutput(location, packageName, relativeName, sibling);
        else
            throw new UnsupportedOperationException("Platform compiler not found");
    }
    public void close() throws IOException {
    }
    public void flush() throws IOException {
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
