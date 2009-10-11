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
package gap.data;

import gap.Request;
import gap.Response;
import gap.service.Method;
import gap.service.OD;
import gap.service.Servlet;

import gap.jbx.Registry;

import javax.script.Bindings;

/**
 * A gap data function binds tool methods.
 * 
 * @author jdp
 */
public final class Function
    extends gap.jbx.Function
{

    private final static java.util.List<String> JelaImports = new java.util.ArrayList<String>();
    private final static java.util.List<String> JelaDeclarations = new java.util.ArrayList<String>();
    static {
        JelaImports.add("import gap.*;");
        JelaImports.add("import gap.data.*;");
        JelaImports.add("import gap.service.*;");
        JelaImports.add("import gap.util.*;");

        JelaDeclarations.add("Request req = (Request)local.get(\"req\");");
        JelaDeclarations.add("Response rep = (Response)local.get(\"rep\");");
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

        else if (tool.hasMethodName(true))
            return tool.getMethodName(true);
        else
            return gap.data.Function.Type.Name(tool);
    }



    public Function(Servlet instance, Request request, Response response, Resource resource, Tool tool)
        throws gap.jbx.Function.MethodNotFound
    {
        super(new Type(tool), Target(tool));
        this.instance = instance;

        java.util.List<String> imports = new java.util.ArrayList<String>(JelaImports);
        this.setImportStatements(imports);
        java.util.List<String> declarations = new java.util.ArrayList<String>(JelaDeclarations);
        this.setDeclarationStatements(declarations);
        this.setLanguage("jela");
        this.setScript(gap.Strings.TextToString(tool.getMethodBody(true)));
        if (this.isScript()){
            Registry bindings = new Registry();
            bindings.put("req",request);
            bindings.put("rep",response);
            this.init(bindings);
        }
        else {
            this.init(instance,instance.getFunctionList());
        }
    }

    public void destroy(){
        this.instance = null;
    }
    public void init(Bindings local)
        throws gap.jbx.Function.MethodNotFound
    {
        this.setBindings(local,ENGINE_SCOPE);
        super.init();
    }
}
