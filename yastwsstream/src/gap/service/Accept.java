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

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * 
 * @author jdp
 */
public final class Accept
    extends java.lang.Object
{
    public final static class Component
        extends java.lang.Object
    {
        public final static class Parameter
            extends java.lang.Object
        {
            public final String name, value;

            public Parameter(String string){
                super();
                StringTokenizer strtok = new StringTokenizer(string,"= ");
                this.name = strtok.nextToken();
                this.value = strtok.nextToken();
            }

            public Integer intValue(){
                try {
                    return new Integer(this.value);
                }
                catch (Exception exc){
                    return null;
                }
            }
            public Float floatValue(){
                try {
                    return new Float(this.value);
                }
                catch (Exception exc){
                    return null;
                }
            }
        }


        public final String name;

        private final Map<String,Parameter> parameters;

        public Component(String string){
            this(string,true);
        }
        public Component(String string, boolean exc){
            super();
            if (null != string){
                StringTokenizer strtok = new StringTokenizer(string,";");
                int count = strtok.countTokens();
                if (strtok.hasMoreTokens()){
                    this.name = strtok.nextToken().trim();
                    if (strtok.hasMoreTokens()){
                        this.parameters = new java.util.HashMap<String,Parameter>();
                        do {
                            try {
                                Parameter p = new Parameter(strtok.nextToken());
                                this.parameters.put(p.name,p);
                            }
                            catch (Exception skip){
                            }
                        }
                        while (strtok.hasMoreTokens());
                    }
                    else
                        this.parameters = null;
                }
                else if (exc)
                    throw new IllegalArgumentException(string);
                else {
                    this.name = null;
                    this.parameters = null;
                }
            }
            else if (exc)
                throw new IllegalArgumentException(string);
            else {
                this.name = null;
                this.parameters = null;
            }
        }

        public Parameter getParameter(String name){
            Map<String,Parameter> parameters = this.parameters;
            if (null != parameters)
                return parameters.get(name);
            else
                return null;
        }
        public boolean equals(Component that){
            if (null != this.name && null != that.name)
                return this.name.equals(that.name);
            else
                return false;
        }
    }


    private final static Map<String,Component> components = new java.util.HashMap<String,Component>();

    private final Component requestBodyContentType;


    public Accept(HttpServletRequest req){
        super();
        this.requestBodyContentType = new Component(req.getContentType(),false);
        String accept = req.getHeader("Accept");
        if (null != accept){
            Map<String,Component> components = this.components;
            StringTokenizer strtok = new StringTokenizer(accept,",");
            while (strtok.hasMoreTokens()){
                try {
                    Component c = new Component(strtok.nextToken());
                    components.put(c.name,c);
                }
                catch (Exception skip){
                }
            }
        }
    }


    public boolean isEmpty(){
        return (0 == this.components.size());
    }
    public boolean isNotEmpty(){
        return (0 != this.components.size());
    }
    public int size(){
        return this.components.size();
    }
    public Component get(String name){
        if (null != name)
            return this.components.get(name);
        else
            return null;
    }
    public Component match(String name){
        if (null != name){
            Component c = this.components.get(name);
            if (null != c)
                return c;
            else {
                SearchType search = new SearchType(name);

                c = this.components.get(search.prefix);
                if (null != c)
                    return c;

                c = this.components.get(search.suffix);
                if (null != c)
                    return c;
            }
        }
        return null;
    }
    public boolean accept(String name){
        return (null != this.match(name));
    }
    public boolean isContentType(String name){
        Component requestType = this.requestBodyContentType;
        Component argumentType = new Component(name,false);
        return (requestType.equals(argumentType));
    }

    public static class SearchType 
        extends Object
    {
        public final String name, prefix, suffix;

        public SearchType(String type){
            super();
            if (null != type){
                this.name = type;
                int idx = type.indexOf('/');
                if (0 < idx && idx < (type.length()-1)){
                    this.prefix = type.substring(0,idx)+"/*";
                    this.suffix = "*/"+type.substring(idx+1);
                }
                else
                    throw new IllegalArgumentException();
            }
            else
                throw new IllegalArgumentException();
        }
    }
}
