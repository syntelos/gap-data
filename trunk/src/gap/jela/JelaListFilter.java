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
import gap.service.ListFilterFunction;
import gap.util.AbstractList;

/**
 * 
 * 
 * @author J. Pritchard
 */
public final class JelaListFilter
    extends JelaProgram
{

    public final String packageName, className, fullClassName;

    private LineNumbered lined;


    public JelaListFilter(AbstractList list, String name){
        super();
        String[] lines = Lines(tool);
        if (null != resource && null != lines){
            this.packageName = FileManager.DerivePackage(list);
            this.className = ListFilterFunction.DeriveName(name);
            this.fullClassName = (this.packageName+'.'+this.className);

            this.iprintln("package "+this.packageName+";");
            this.iprintln();
            for (String imp : Imports){
                this.iprintln(imp);
            }
            this.iprintln();
            this.iprintln("public final class "+this.className);
            this.iprintln("    extends ListFilterFunction");
            this.iprintln("{");
            this.iopen();
            this.iprintln("public final static String Name = \""+name+"\";");
            this.iprintln();
            this.iprintln();
            this.iprintln("public "+this.className+"(AbstractList list)");
            this.iprintln("        throws java.io.IOException, gap.jbx.Function.MethodNotFound");
            this.iprintln("{");
            this.iprintln("    super(list,Name);");
            this.iprintln("}");
            this.iprintln();
            this.iprintln("public boolean accept()");
            this.iprintln("    throws java.io.IOException");
            this.iprintln("{");
            this.iopen();
            this.iprintln();

            for (String stmt : lines){

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
            this.iclose();
            this.iprintln("}");
            this.iprintln();
            this.iclose();
            this.iprintln("}");
        }
        else 
            throw new IllegalArgumentException();
    }


    public String getPackageName(){
        return this.packageName;
    }
    public String getClassName(){
        return this.className;
    }
    public String getFullClassName(){
        return this.fullClassName;
    }
}
