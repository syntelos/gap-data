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
    private final static java.lang.ThreadLocal<Method> MTL = new java.lang.ThreadLocal<Method>();

    public final static Method Get(){
        return MTL.get();
    }

    /**
     * Standard method names
     */
    public final static class Names {

        public final static String GET = "GET";
        public final static String HEAD = "HEAD";
        public final static String POST = "POST";
        public final static String PUT = "PUT";
        public final static String DELETE = "DELETE";
        public final static String OPTIONS = "OPTIONS";
        public final static String TRACE = "TRACE";
    }
    /**
     * Conventional method type constants
     */
    public final static int UNKNOWN = 0;
    public final static int GET = 1;
    public final static int HEAD = 2;
    public final static int POST = 3;
    public final static int PUT = 4;
    public final static int DELETE = 5;
    public final static int OPTIONS = 6;
    public final static int TRACE = 7;

    /**
     * Standard method instances
     */
    public final static class Values {

        public final static Method GET = new Method(Method.Names.GET,Method.GET);
        public final static Method HEAD = new Method(Method.Names.HEAD,Method.HEAD);
        public final static Method POST = new Method(Method.Names.POST,Method.POST);
        public final static Method PUT = new Method(Method.Names.PUT,Method.PUT);
        public final static Method DELETE = new Method(Method.Names.DELETE,Method.DELETE);
        public final static Method OPTIONS = new Method(Method.Names.OPTIONS,Method.OPTIONS);
        public final static Method TRACE = new Method(Method.Names.TRACE,Method.TRACE);
    }


    public final String name;

    public final int type;


    private Method(String name, int type){
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
        else if (that instanceof Method)
            return (this.name.equals(((Method)that).name));
        else
            return false;
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

    /*
     */
    private final static java.util.Map<String,Method> Map = new java.util.HashMap<String,Method>();
    static {
        Map.put(Names.GET,Values.GET);
        Map.put(Names.HEAD,Values.HEAD);
        Map.put(Names.POST,Values.POST);
        Map.put(Names.PUT,Values.PUT);
        Map.put(Names.DELETE,Values.DELETE);
        Map.put(Names.OPTIONS,Values.OPTIONS);
        Map.put(Names.TRACE,Values.TRACE);
    }
    private final static Method Lookup(String name){
        Method value = Map.get(name);
        if (null != value)
            return value;
        else
            return new Method(name,Method.UNKNOWN);
    }
    final static Method Enter(HttpServletRequest req){

        Method method = Method.Lookup(req.getMethod());

        MTL.set(method);

        return method;
    }
    final static void Exit(){

        MTL.set(null);
    }
}
