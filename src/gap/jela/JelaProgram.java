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

import java.io.File;
import java.io.FileWriter;
import java.io.CharArrayWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;

/**
 * A JPL source program from the "jela" engine. 
 * 
 * <h3>Debugging</h3>
 * 
 * Create a directory named "debug/jela0/gen" under the process working
 * directory to have generated sources deposited there for debugging.
 * Copies will be written as created, when possible.
 * 
 * @author J. Pritchard
 */
public final class JelaProgram
    extends CharArrayWriter
{
    public final static String PackageName = "jela0.gen";
    public final static String ClassName = "JelaProgram";

    private static volatile long NameDefaultCounter = 0L;
    private static final Long NameInitCounter = 0L;
    private final static java.util.Hashtable<String,Long> Namespace = new java.util.Hashtable<String,Long>();
    private final static String NextClassname(String id){
        if (null == id)
            return (ClassName+String.valueOf(NameDefaultCounter++));
        else {
            synchronized(Namespace){
                Long nameCounter = Namespace.get(id);
                if (null == nameCounter){
                    Namespace.put(id,NameInitCounter);
                    return (ClassName+id);
                }
                else {
                    nameCounter += 1L;
                    Namespace.put(id,nameCounter);
                    return (ClassName+id+String.valueOf(nameCounter));
                }
            }
        }
    }
    /**
     * 
     */
    public final static class Structure {

        public final List<String> imports = new ArrayList<String>();
        public final List<String> declarations = new ArrayList<String>();
        public final List<String> lines = new ArrayList<String>();

        Structure(ScriptContext scx, String text){
            super();
            if (scx instanceof JelaContextImports){
                List<String> scxImports = ((JelaContextImports)scx).getImportStatements();
                if (null != scxImports){
                    for (String imp : scxImports){
                        this.imports.add(imp);
                    }
                }
            }
            if (scx instanceof JelaContextDeclarations){
                List<String> scxDeclarations = ((JelaContextDeclarations)scx).getDeclarationStatements();
                if (null != scxDeclarations){
                    for (String imp : scxDeclarations){
                        this.declarations.add(imp);
                    }
                }
            }
            StringTokenizer strtok = new StringTokenizer(text,"\r\n");
            while (strtok.hasMoreTokens()){
                String line = strtok.nextToken().trim();
                if (line.startsWith("import "))
                    this.imports.add(line);
                else
                    this.lines.add(line);
            }
        }
    }


    public final String classname, fqcn, fqfn;

    public final URI filesource, fileclass;

    private Structure structured;

    private JavaSourceInputBuffer source;

    private JelaScriptEngine je;

    private JelaProgramCompiler compiler;

    private int indent;


    public JelaProgram(JelaScriptEngine je, String jela)
        throws ScriptException
    {
        super();
        this.je = je;
        ScriptContext scx = je.getContext();
        this.classname = NextClassname(je.getIdentifier());
        this.fqcn = (JelaProgram.PackageName+'.'+this.classname);
        this.fqfn = this.fqcn.replace('.','/');
        try {
            this.filesource = new URI("mfm:///"+this.fqfn+".java");
            this.fileclass = new URI("mfm:///"+this.fqfn+".class");
        }
        catch (java.net.URISyntaxException exc){
            throw new ScriptException(exc);
        }
        this.compiler = new JelaProgramCompiler(this,scx);
        if (this.compiler.isAlive()){
            this.iprintln("package "+JelaProgram.PackageName+";");
            this.iprintln();
            this.structured = new Structure(scx,jela);
            for (String imp : this.structured.imports){
                this.iprintln(imp);
            }
            this.iprintln();
            this.iprintln("public final class "+this.classname);
            this.iprintln("    extends jela.JavaCompiledScript");
            this.iprintln("{");
            this.iprintln();
            this.iopen();
            this.iprintln("public "+this.classname+"(jela.JelaScriptEngine je){");
            this.iprintln("    super(je);");
            this.iprintln("}");
            this.iprintln();
            this.iprintln("public java.lang.Object eval(javax.script.ScriptContext context, javax.script.Bindings global, javax.script.Bindings local)");
            this.iprintln("    throws javax.script.ScriptException");
            this.iprintln("{");
            this.iopen();
            for (String stmt : this.structured.declarations){

                if (stmt.endsWith("{")){
                    this.iprintln(stmt);
                    this.iopen();
                }
                else if (stmt.endsWith("}")){
                    this.iclose();
                    this.iprintln(stmt);
                }
                else
                    this.iprintln(stmt);
            }
            this.iprintln();
            boolean re = true;
            for (String stmt : this.structured.lines){
                if (stmt.startsWith("return"))
                    re = false;
                if (stmt.endsWith("{")){
                    this.iprintln(stmt);
                    this.iopen();
                }
                else if (stmt.endsWith("}")){
                    this.iclose();
                    this.iprintln(stmt);
                }
                else
                    this.iprintln(stmt);
            }
            if (re){
                this.iprintln();
                this.iprintln("return null;");
            }
            this.iclose();
            this.iprintln("}");
            this.iprintln();
            this.iclose();
            this.iprintln("}");
            this.source = new JavaSourceInputBuffer(this);
            try {
                File file = new File("debug/"+this.fqfn+".java");
                FileWriter debug = new FileWriter(file);
                try {
                    debug.append(this.toString());
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
        else {
            this.append(jela);
            this.source = new JavaSourceInputBuffer(this);
        }
    }


    public CompiledScript compile()
        throws ScriptException
    {
        JelaProgramCompiler pc = new JelaProgramCompiler(this);
        if (pc.isAlive()){
            if (pc.compile())
                return pc.getCompiledScript(this.je);
            else 
                throw new JelaProgramException(pc);
        }
        else 
            return pc.getCompiledScript(this.je);
    }
    public javax.tools.JavaFileObject getInputSource(){
        return this.source;
    }
    public JelaScriptEngine getEngine(){
        return this.je;
    }
    public ScriptContext getContext(){
        return this.je.getContext();
    }

    private void iopen(){
        this.indent += 1;
    }
    private void iclose(){
        this.indent -= 1;
        if (0 > this.indent)
            this.indent = 0;
    }
    private void iprintln(String string){
        super.append(I[this.indent % I.length]);
        super.append(string);
        super.append('\n');
    }
    private void iprintln(){
        super.append('\n');
    }

    private final static String[] I = {
        "",
        "    ",
        "        ",
        "            ",
        "                ",
        "                    ",
        "                        ",
    };
}
