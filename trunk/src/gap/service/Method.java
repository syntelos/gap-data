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
public final class Method
    extends java.lang.Object
{
    public final static class Names {

        public final static String GET = "GET";
        public final static String HEAD = "HEAD";
        public final static String POST = "POST";
        public final static String PUT = "PUT";
        public final static String DELETE = "DELETE";
        public final static String OPTIONS = "OPTIONS";
        public final static String TRACE = "TRACE";
    }

    public final static int UNKNOWN = 0;
    public final static int GET = 1;
    public final static int HEAD = 2;
    public final static int POST = 3;
    public final static int PUT = 4;
    public final static int DELETE = 5;
    public final static int OPTIONS = 6;
    public final static int TRACE = 7;

    private final static java.util.Map<String,Integer> Map = new java.util.HashMap<String,Integer>();
    static {
        Map.put(Names.GET,GET);
        Map.put(Names.HEAD,HEAD);
        Map.put(Names.POST,POST);
        Map.put(Names.PUT,PUT);
        Map.put(Names.DELETE,DELETE);
        Map.put(Names.OPTIONS,OPTIONS);
        Map.put(Names.TRACE,TRACE);
    }
    public final static int Lookup(String name){
        Integer value = Map.get(name);
        if (null != value)
            return value.intValue();
        else
            return UNKNOWN;
    }
    public final static int Lookup(HttpServletRequest req){
        return Method.Lookup(req.getMethod());
    }
    public final static int Lookup(ServletRequest req){
        return Method.Lookup((HttpServletRequest)req);
    }


    public final int type;


    public Method(HttpServletRequest req){
        super();
        this.type = Method.Lookup(req.getMethod());
    }
    public Method(String name){
        super();
        this.type = Method.Lookup(name);
    }


    public int hashCode(){
        return this.type;
    }
    public String toString(){
        switch (this.type){
        case UNKNOWN:
            return "UNKNOWN";
        case GET:
            return "GET";
        case HEAD:
            return "HEAD";
        case POST:
            return "POST";
        case PUT:
            return "PUT";
        case DELETE:
            return "DELETE";
        case OPTIONS:
            return "OPTIONS";
        case TRACE:
            return "TRACE";
        default:
            throw new IllegalStateException();
        }
    }
    public boolean equals(java.lang.Object that){
        if (this == that)
            return true;
        else if (that instanceof Method)
            return (this.hashCode() == that.hashCode());
        else
            return false;
    }
}
