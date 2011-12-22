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
package gap.odl;

import gap.hapax.TemplateName;

/**
 * This file is automatically generated by the build scripts.  
 * @since 1.1
 */
public final class Version {
    /**
     * Major version number.
     */
    public final static int Major = 1;
    /**
     * Minor version number.
     */
    public final static int Minor = 2;
    /**
     * HTTP Product Token in <code>" Major '.' Minor "</code>.
     */
    public final static java.lang.String Short = Major+"."+Minor;
    /**
     * Build sequence number.
     */
    public final static int Build = 6;
    /**
     * HTTP Product Token plus comment build date and time.  
     * As in <code>" Major '.' Minor '.' Build "</code>.
     */
    public final static java.lang.String Long = Short+'.'+Build;

    public final static java.lang.String Name = "gap-data-types";


    public final static java.lang.String ToStringLong(String name){
        return (name+'/'+Long);
    }
    public final static java.lang.String ToStringShort(String name){
        return (name+'/'+Short);
    }
    public final static void PrintlnLong(String name){
        PrintlnLong(name,System.err);
    }
    public final static void PrintlnLong(java.io.PrintStream out){
        PrintlnLong("Syntelos",out);
    }
    public final static void PrintlnLong(String name, java.io.PrintStream out){
        out.println(ToStringLong(name));
    }
    public final static void PrintlnShort(String name){
        PrintlnShort(name,System.err);
    }
    public final static void PrintlnShort(java.io.PrintStream out){
        PrintlnShort("Syntelos",out);
    }
    public final static void PrintlnShort(String name, java.io.PrintStream out){
        out.println(ToStringShort(name));
    }
    private final static lxl.Index<String> Index = new lxl.Index<String>();
    static {
        Index.put("long",0);
        Index.put("short",1);
        Index.put("number",2);
        Index.put("name",3);

    }
    public final static boolean HasVariable(TemplateName name){
        if (name.is(0)){
            switch(Index.get(name.getName())){
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
            }
        }
        else
            return false;
    }
    public final static String GetVariable(TemplateName name){
        if (name.is(0)){
            switch(Index.get(name.getName())){
            case 0:
                return Version.Long;
            case 1:
                return Version.Short;
            case 2:
                return Version.Short;
            case 3:
                return Version.Name;

            default:
                return null;
            }
        }
        else
            return null;
    }
}
