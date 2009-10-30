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

import java.util.StringTokenizer;

/**
 * A JPL source program from the "jela" engine. 
 * 
 * @author J. Pritchard
 */
public abstract class JelaProgram
    extends java.io.CharArrayWriter
{
    protected final static String[] Imports = {
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


    private LineNumbered lined;

    private int indent;


    protected JelaProgram(){
        super();
    }


    public abstract String getPackageName();

    public abstract String getClassName();

    public abstract String getFullClassName();

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

    protected void iopen(){
        this.indent += 1;
    }
    protected void iclose(){
        if (0 < this.indent)
            this.indent -= 1;
    }
    protected void iprintln(String string){
        super.append(I[this.indent % I.length]);
        super.append(string);
        super.append('\n');
    }
    protected void iprintln(){
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
