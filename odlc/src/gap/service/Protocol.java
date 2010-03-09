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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import java.util.StringTokenizer;

/**
 * 
 * 
 * @author jdp
 */
public final class Protocol
    extends java.lang.Object
{
    private final static java.lang.ThreadLocal<Protocol> MTL = new java.lang.ThreadLocal<Protocol>();

    public final static Protocol Get(){
        return MTL.get();
    }

    /**
     * Standard protocol names
     */
    public final static class Names {

        public final static String HTTP10 = "HTTP/1.0";
        public final static String HTTP11 = "HTTP/1.1";
    }
    /**
     * Conventional protocol type constants
     */
    public final static int UNKNOWN = 0;
    public final static int HTTP10 = 1;
    public final static int HTTP11 = 2;

    /**
     * Standard protocol instances
     */
    public final static class Values {

        public final static Protocol HTTP10 = new Protocol(Protocol.Names.HTTP10,Protocol.HTTP10);
        public final static Protocol HTTP11 = new Protocol(Protocol.Names.HTTP11,Protocol.HTTP11);
    }


    public final String name;

    public final int type;


    private Protocol(String name, int type){
        super();
        this.name = name;
        this.type = type;
    }


    public int hashCode(){
        return this.name.hashCode();
    }
    public boolean equals(java.lang.Object that){
        if (this == that)
            return true;
        else if (that instanceof Protocol)
            return (this.name.equals(((Protocol)that).name));
        else
            return false;
    }
    public String toString(){
        switch (this.type){
        case UNKNOWN:
            return name;
        case HTTP10:
            return Names.HTTP10;
        case HTTP11:
            return Names.HTTP11;
        default:
            throw new IllegalStateException();
        }
    }

    /*
     */
    private final static java.util.Map<String,Protocol> Map = new java.util.HashMap<String,Protocol>();
    static {
        Map.put(Names.HTTP10,Values.HTTP10);
        Map.put(Names.HTTP11,Values.HTTP11);
    }
    private final static Protocol Lookup(String name){
        Protocol value = Map.get(name);
        if (null != value)
            return value;
        else
            return new Protocol(name,Protocol.UNKNOWN);
    }
    final static Protocol Enter(HttpServletRequest req){

        Protocol protocol = Protocol.Lookup(req.getProtocol());

        MTL.set(protocol);

        return protocol;
    }
    final static void Exit(){

        MTL.remove();
    }
}
