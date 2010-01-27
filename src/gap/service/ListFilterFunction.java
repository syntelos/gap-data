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

import gap.*;
import gap.data.*;
import gap.util.*;

import gap.jbx.Function;
import gap.jbx.Registry;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.script.Bindings;
import javax.script.ScriptException;

/**
 * ListFilter code function.
 * 
 * @author jdp
 */
public class ListFilterFunction<V extends BigTable>
    extends gap.jbx.Function
    implements gap.data.DataInheritance.Notation,
               gap.data.ListFilter<V>
{

    private final static java.util.List<String> JelaImports = new java.util.ArrayList<String>();

    static {
        JelaImports.add("import gap.*;");
        JelaImports.add("import gap.data.*;");
        JelaImports.add("import gap.service.*;");
        JelaImports.add("import gap.util.*;");
    }

    public final static class Type 
        extends gap.jbx.Function.Type
    {
        private final static Class Return = java.lang.Boolean.class;
        private final static Class[] Parameters = { BigTable.class};

        public Type(String name){
            super(name,Return,Parameters);
        }
    }
    public final static String DeriveName(String name){

        return OD.Camel(name);
    }

    /**
     * Constructor for compiled function.
     */
    public final static class Constructor 
        extends Object
    {
        protected final static Class[] Parameters = {
            List.class 
        };
        protected final static int ParametersLen = Parameters.length;

        public final Class<gap.service.ListFilterFunction> target;
        public final java.lang.reflect.Constructor ctor;
        public final boolean isValid;

        public Constructor(Class<gap.service.ListFilterFunction> target)
            throws ScriptException
        {
            super();
            if (null != target){
                this.target = target;
                
                java.lang.reflect.Constructor[] list = target.getConstructors();
                for (java.lang.reflect.Constructor c : list){
                    if (java.lang.reflect.Modifier.isPublic(c.getModifiers())){
                        Class[] params = c.getParameterTypes();
                        if (ParametersLen == params.length){
                            this.ctor = c;
                            this.isValid = true;
                            return;
                        }
                    }
                }
                this.ctor = null;
                this.isValid = false;
            }
            else
                throw new ScriptException("Missing class.");
        }

        public boolean isValid(){
            return this.isValid;
        }
        public ListFilterFunction create(Resource resource, String name)
            throws ScriptException
        {
            if (this.isValid){
                java.lang.reflect.Constructor<gap.service.ListFilterFunction> ctor = this.ctor;
                if (null != ctor){
                    try {
                        return ctor.newInstance(resource, name);
                    }
                    catch (InstantiationException exc){
                        throw new ScriptException(exc);
                    }
                    catch (IllegalAccessException exc){
                        throw new ScriptException(exc);
                    }
                    catch (java.lang.reflect.InvocationTargetException exc){
                        Throwable thrown = exc.getCause();
                        if (thrown instanceof Exception){
                            Exception exc2 = (Exception)thrown;
                            throw new ScriptException(exc2);
                        }
                        else
                            throw new ScriptException(exc);
                    }
                }
            }
            return null;
        }
    }


    protected final AbstractList<V> list;


    public ListFilterFunction(AbstractList list)
        throws java.io.IOException, gap.jbx.Function.MethodNotFound
    {
        super(new Type(tool), Target(tool));
        this.list = list;

        this.setLanguage("jela");
        //         this.setScript(gap.Strings.TextToString(tool.getFunctionBody(true)));
        //         this.init(servlet,servlet.getFunctionList());
    }

    public void destroy(){
        this.instance = null;
    }
    public Object invoke()
        throws Function.InvokeErrorTarget,
               Function.InvokeErrorNotInitialized
    {
        if (this.hasMethod())
            return this.invoke(this.list);
        else {
            try {
                this.filter(this.list);
            }
            catch (java.io.IOException exc){
            }
            return null;
        }
    }
    public void init()
        throws Function.MethodNotFound
    {
    }
    public boolean accept(V item){

        throw new IllegalStateException("Undefined function");
    }
    public gap.data.List<V> filter() throws java.io.IOException {
        gap.data.List<V> filtered = this.list.clone();
        filtered.clearBuffer();
        for (V item: this.list){
            if (this.accept(item))
                filtered.add(item);
        }
        return filtered;
    }
}
