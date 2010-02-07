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
 * Tool code function.
 * 
 * @author jdp
 */
public class ToolFunction
    extends gap.jbx.Function
    implements gap.data.DataInheritance.Notation
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
        private final static Class Return = java.lang.Void.class;
        private final static Class[] Parameters = { gap.Request.class, gap.Response.class};

        /**
         * A request method "POST" for tool "create" produces type
         * name (origin caller) "doPostCreate" for the {@link Method
         * known methods}.
         */
        public final static String Name(Tool tool){
            return Method.Get().binding+OD.Camel(tool.getName());
        }

        public Type(Tool tool){
            super(Name(tool),Return,Parameters);
        }
    }
    public static String Target(Tool tool){
        if (null == tool)
            throw new IllegalArgumentException();

        else if (tool.hasJavaMethodName(true))
            return tool.getJavaMethodName(true);
        else
            return gap.service.ToolFunction.Type.Name(tool);
    }
    public final static String DeriveName(Tool tool){
        String name = FileManager.DeriveName(tool);
        if (null == name)
            return OD.Camel(Target(tool));
        else
            return name;
    }

    /**
     * Constructor for compiled function.
     */
    public final static class Constructor 
        extends Object
    {
        protected final static Class[] Parameters = {
            Servlet.class, Request.class, Response.class, Resource.class, Tool.class 
        };
        protected final static int ParametersLen = Parameters.length;

        public final Class<gap.service.ToolFunction> target;
        public final java.lang.reflect.Constructor ctor;
        public final boolean isValid;

        public Constructor(Class<gap.service.ToolFunction> target)
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
        public ToolFunction create(Servlet instance, Request request, Response response, Resource resource, Tool tool)
            throws ScriptException
        {
            if (this.isValid){
                java.lang.reflect.Constructor<gap.service.ToolFunction> ctor = this.ctor;
                if (null != ctor){
                    try {
                        return ctor.newInstance(instance, request, response, resource, tool);
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


    protected final Request request;
    protected final Response response;
    protected final Resource resource;
    protected final Tool tool;
    protected final BufferedReader in;
    protected final PrintWriter out;


    public ToolFunction(Servlet instance, Request request, Response response, Resource resource, Tool tool)
        throws java.io.IOException, gap.jbx.Function.MethodNotFound
    {
        super(new Type(tool), Target(tool));
        this.request = request;
        this.response = response;
        this.resource = resource;
        this.tool = tool;
        this.in = request.getReader();
        this.out = response.getWriter();

        this.setLanguage("jela");
        this.setScript(gap.Strings.TextToString(tool.getJavaMethodSource(true)));
        this.init(instance,instance.getFunctionList());
    }

    public void destroy(){
        this.instance = null;
    }
    public Object invoke()
        throws Function.InvokeErrorTarget,
               Function.InvokeErrorNotInitialized
    {
        if (this.hasMethod())
            return this.invoke(this.request, this.response, this.resource, this.tool);
        else {
            try {
                this.service();
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
    protected void service() throws java.io.IOException {
        throw new IllegalStateException("Undefined function");
    }
}
