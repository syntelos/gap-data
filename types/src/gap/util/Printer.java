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
package gap.util;

/**
 * @author jdp
 */
public class Printer
    extends Object
{
    private int indent;

    private final java.io.PrintStream out;


    public Printer(){
        this(System.err);
    }
    public Printer(java.io.PrintStream out){
        super();
        if (null != out)
            this.out = out;
        else
            throw new IllegalArgumentException();
    }


    public void reset(){
        this.indent = 0;
    }
    public void enter(){
        this.indent += 1;
    }
    public void exit(){
        if (0 < this.indent)
            this.indent -= 1;
    }
    public void println(String string){
        this.out.print(I[this.indent % I.length]);
        this.out.println(string);
    }
    public void println(){
        this.out.println();
    }
    public void close() throws java.io.IOException {
        this.out.close();
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
