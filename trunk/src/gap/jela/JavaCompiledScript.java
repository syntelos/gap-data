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
package gap.jela;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Jela programs are generated as subclasses of this class.  The
 * abstract eval method defined here is their scope of interpretation.
 * 
 * @author J. Pritchard
 */
public abstract class JavaCompiledScript 
    extends CompiledScript 
{

    /**
     * Constructor for compiled program.
     * 
     * @author J. Pritchard
     */
    public final static class Constructor 
        extends Object
    {
        public final Class target;
        public final java.lang.reflect.Constructor ctor;
        public final boolean isValid;

        public Constructor(Class target)
            throws ScriptException
        {
            super();
            if (null != target){
                this.target = target;
                if (JavaCompiledScript.class.isAssignableFrom(target)){
                    java.lang.reflect.Constructor[] list = target.getConstructors();
                    for (java.lang.reflect.Constructor c : list){
                        if (java.lang.reflect.Modifier.isPublic(c.getModifiers())){
                            Class[] params = c.getParameterTypes();
                            if (1 == params.length){
                                if (JelaScriptEngine.class.equals(params[0])){
                                    this.ctor = c;
                                    this.isValid = true;
                                    return;
                                }
                            }
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
        public JavaCompiledScript create(JelaScriptEngine arg)
            throws ScriptException
        {
            if (this.isValid){
                java.lang.reflect.Constructor ctor = this.ctor;
                if (null != ctor){
                    try {
                        return (JavaCompiledScript)ctor.newInstance(arg);
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
            throw new ScriptException("Failed to construct an instance of "+this.target.getName());
        }
    }


    private JelaScriptEngine engine;


    protected JavaCompiledScript(JelaScriptEngine engine){
        super();
        this.engine = engine;
    }


    /**
     * This is the scope of interpretation for jela programs.
     */
    public abstract Object eval(ScriptContext context, Bindings global, Bindings local)
        throws ScriptException;

    public final ScriptEngine getEngine() {
        return this.engine;
    }
    public final Object eval(ScriptContext ctx) 
        throws ScriptException 
    {
        return this.eval(ctx,ctx.getBindings(ScriptContext.GLOBAL_SCOPE),ctx.getBindings(ScriptContext.ENGINE_SCOPE));
    }
    public final Object eval(Bindings local)
        throws ScriptException
    {
        return this.eval(getEngine().getContext(),local);
    }
    private final Object eval(ScriptContext ctx, Bindings local) 
        throws ScriptException 
    {
        return this.eval(ctx,ctx.getBindings(ScriptContext.GLOBAL_SCOPE),local);
    }

    public final void println(){
        this.println(null);
    }
    public final void println(String s){
        ScriptContext cx = this.engine.getContext();
        if (null != cx){
            Writer wri = cx.getWriter();
            if (null != wri){
                try {
                    if (null != s)
                        wri.append(s);
                    wri.append('\n');
                    wri.flush();
                }
                catch (java.io.IOException exc){
                    throw new RuntimeException(exc);
                }
            }
            else if (null != s)
                System.out.println(s);
            else
                System.out.println();
        }
        else if (null != s)
            System.out.println(s);
        else
            System.out.println();
    }
    public final void printf(String s, Object... args){
        ScriptContext cx = this.engine.getContext();
        if (null != cx){
            Writer wri = cx.getWriter();
            if (null != wri){
                PrintWriter pw;
                if (wri instanceof PrintWriter)
                    pw = (PrintWriter)wri;
                else {
                    pw = new PrintWriter(wri);
                    cx.setWriter(pw);
                }
                if (null != s){
                    pw.printf(s,args);
                    pw.flush();
                }
            }
            else if (null != s)
                System.out.printf(s,args);
        }
        else if (null != s)
            System.out.printf(s,args);
    }
    public String readLine() throws java.io.IOException {
        ScriptContext cx = this.engine.getContext();
        if (null != cx){
            Reader red = cx.getReader();
            if (null != red){
                BufferedReader brd;
                if (red instanceof BufferedReader)
                    brd = (BufferedReader)red;
                else {
                    brd = new BufferedReader(red);
                    cx.setReader(brd);
                }
                return brd.readLine();
            }
        }
        BufferedReader brd = new BufferedReader(new InputStreamReader(System.in));
        return brd.readLine();
    }
    public final static String[] wraptext(String s, int width){
        String[] re = null;
        while (width < s.length()){
            int idx = width;
            for (; 0 < idx; idx--){
                if (' ' == s.charAt(idx))
                    break;
            }
            if (0 == idx)
                idx = width;
            {
                String a = s.substring(0,idx).trim();
                String b = s.substring(idx+1).trim();
                if (null == re)
                    re = new String[]{a};
                else {
                    int len = re.length;
                    String[] copier = new String[len+1];
                    System.arraycopy(re,0,copier,0,len);
                    copier[len] = a;
                    re = copier;
                }
                s = b;
            }
        }
        if (0 < s.length()){
            if (null == re)
                re = new String[]{s};
            else {
                int len = re.length;
                String[] copier = new String[len+1];
                System.arraycopy(re,0,copier,0,len);
                copier[len] = s;
                re = copier;
            }
        }
        return re;
    }
    public final static void exit(){
        System.exit(0);
    }
    public final static void exit(int rc){
        System.exit(rc);
    }
}
