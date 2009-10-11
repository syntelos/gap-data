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

import gap.data.*;
import gap.service.FileManager;
import gap.service.Function;
import gap.service.OD;
import gap.service.od.*;

import java.util.StringTokenizer;

/**
 * A JPL source program from the "jela" engine. 
 * 
 * @author J. Pritchard
 */
public final class JelaProgram
    extends java.io.CharArrayWriter
{
    private final static String[] Imports = {
        "import gap.*;",
        "import gap.data.*;",
        "import gap.service.*;",
        "import gap.util.*;"
    };
    protected final static String[] Lines(Tool tool){
        if (null != tool){
            String text = gap.Strings.TextToString(tool.getFunctionBody(true));
            if (null != text){
                StringTokenizer strtok = new StringTokenizer(text,"\r\n");
                int count = strtok.countTokens();
                if (0 != count){
                    int cc = 0;
                    String[] re = new String[count];
                    while (strtok.hasMoreTokens()){
                        re[cc++] = strtok.nextToken();
                    }
                    return re;
                }
            }
        }
        return null;
    }

    public final String packageName, className, fullClassName;

    private LineNumbered lined;

    private int indent;


    public JelaProgram(Resource resource, Tool tool){
        super();
        String[] lines = Lines(tool);
        if (null != resource && null != lines){
            this.packageName = FileManager.DerivePackage(resource);
            this.className = Function.DeriveName(tool);
            this.fullClassName = (this.packageName+'.'+this.className);

            this.iprintln("package "+this.packageName+";");
            this.iprintln();
            for (String imp : Imports){
                this.iprintln(imp);
            }
            this.iprintln();
            this.iprintln("public final class "+this.className);
            this.iprintln("    extends Function");
            this.iprintln("{");
            this.iprintln();
            this.iopen();
            this.iprintln("public "+this.className+"(Servlet instance, Request request, Response response, Resource resource, Tool tool)");
            this.iprintln("        throws java.io.IOException, gap.jbx.Function.MethodNotFound");
            this.iprintln("{");
            this.iprintln("    super(instance,request,response,resource,tool);");
            this.iprintln("}");
            this.iprintln();
            this.iprintln("public java.lang.Object invoke()");
            this.iprintln("        throws gap.jbx.Function.InvokeErrorTarget, gap.jbx.Function.InvokeErrorNotInitialized");
            this.iprintln("{");
            this.iopen();
            this.iprintln();
            boolean re = true;
            for (String stmt : lines){
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
        }
        else 
            throw new IllegalArgumentException();
    }


    public String getSource(){
        return this.toString();
    }
    public String getSourceLineNumbered(){
        LineNumbered lined = this.lined;
        if (null == lined){
            lined = new LineNumbered(this.getSource());
            this.lined = lined;
        }
        return lined.toString();
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
