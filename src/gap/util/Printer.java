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
    extends java.io.FilterWriter
{
    private int indent;


    public Printer(){
        this(System.console().writer());
    }
    public Printer(java.io.Writer out){
        super(out);
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
        try {
            super.append(I[this.indent % I.length]);
            super.append(string);
            super.append('\n');
        }
        catch (java.io.IOException exc){
            throw new java.lang.RuntimeException(exc);
        }
    }
    public void println(){
        try {
            super.append('\n');
        }
        catch (java.io.IOException exc){
            throw new java.lang.RuntimeException(exc);
        }
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
