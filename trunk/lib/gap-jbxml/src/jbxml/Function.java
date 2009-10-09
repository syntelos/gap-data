/*
 * Copyright (c) 2009 John Pritchard, WTKX Project Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jbxml;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.Reader;
import java.io.Writer;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.xml.stream.Location;


/**
 * First class functions for java method and script bindings.  A
 * function is a bean and can be used in any way from jbxml that any
 * other bean can be used.
 * 
 * <h3>Initialization</h3>
 * 
 * Functions must be initialized after the construction of the bean
 * graph by the Processor.  The initialization process requires the
 * Resolver / Processor "in scope".  See Resolver enter and exit.
 * 
 * The function init method is signaled for static method "class
 * camel" naming with a null instance object argument, or an instance
 * method "field camel" naming with a non null instance object
 * argument.  In the static method case, the name of the function is
 * in "class camel" case.  In the instance method case, the name of
 * the method is in "field camel" case.  Otherwise an instance method
 * must have an instance object, but a static method called with an
 * instance object is harmless.
 * 
 * The function init method will throw a method not found exception
 * for any failure to correctly initialize.
 * 
 * <h3>Invocation</h3>
 * 
 * The function invoke method will throw one of the declared
 * exceptions on any failure.
 * 
 * <h3>Intrinsic events' binding</h3>
 * 
 * The function class is used internally within an application, not
 * apparent to the XML layer.
 * 
 * Functions constructed with the arguments' constructor may be used
 * to bind intrinsic events to a known user instance object.
 * 
 * <pre>
 * &lt;el evt="fn"/&gt;
 * </pre>
 * 
 * In this example, the bean named 'El' has an event named 'evt' bound
 * to a method on a user instance object named 'fn'.  A function type
 * is created for the signature of 'evt', a function is created for
 * this type, and initialized to the user instance object.
 * 
 * <h3>Scripts</h3>
 * 
 * The body of the XML function element or the value of the field
 * named script is the source (code) for a "language" defined by the
 * field of the same name.
 * 
 * <pre>
 * &lt;function language="beanshell"&gt;
 *  
 *  // 'beanshell' source
 *  
 * &lt;/function&gt;
 * </pre>
 * 
 * Using the function element child text should produce better error
 * messages having location information including line numbers.
 * 
 * Employs java platform script engines.  See "javax.script" or
 * "scripting.dev.java.net" for more info.  The only default scripting
 * language in the platform runtime is "javascript".
 * 
 * <h3>Java methods</h3>
 * 
 * Dynamic java method binding by type signature.  In this case, the
 * function requires a type.
 * 
 * <pre>
 * &lt;function ...&gt;
 *    &lt;function.type ...&gt;
 *    &lt;/function.type&gt;
 * &lt;/function&gt;
 * </pre>
 * 
 * Method signatures are fixed by the caller (via {@link
 * Function$Type}).  The target method must receive the caller's
 * required parameters.
 * 
 * <h3>For bean graph events binding</h3>
 * 
 * A function may be used to implement dynamic binding of bean methods
 * to other application methods.  In this case, an element attribute
 * represents a bean event interface method.  The attribute value
 * names a method on an application instance object.  
 * 
 * The bean has a function type representing the call signature of its
 * method, and employs the type and list classes defined here to bind
 * to a function on the application instance object.  
 * 
 * A bound function is then initialized to the application instance
 * object.  
 * 
 * Subsequently the bean calls the function object from its interface
 * method to handle these events.
 * 
 * <h3>As a first class bean graph component</h3>
 * 
 * A function may be a component of a bean graph.  In order to be
 * properly initialized, it requires a type field value and child
 * text.
 * 
 * If the function element has chid text it must have a valid value
 * for the "language" attribute field.  A valid value is a subpackage
 * name under "jbxml.gen" -- for example "java".  It is bound to the
 * defined method.
 * 
 * @author jdp
 */
public class Function
    extends Object
    implements Component.Child,
               Component.AddText,
               javax.script.ScriptContext,
               jela.JelaScriptContext
{
    private final static ThreadLocal<Function> TL = new ThreadLocal<Function>();
    /**
     * @return The function in the scope of the current thread.
     */
    public final static Function Get(){
        return TL.get();
    }
    /**
     * @return If the current thread is in the scope of a function.
     */
    public final static boolean In(){
        return (null != TL.get());
    }


    /**
     * Function classes are named arbitrarily in the constructor.  The
     * name should be a valid java method identifier string.  The name
     * should be the runtime calling method's name.
     * 
     * <h3>Bean graph component</h3>
     * 
     * A type may be a component of a bean graph.  In order to be
     * properly initialized, it requires a name.  The return type
     * defaults to void.
     * 
     */
    public static class Type 
        extends Object
        implements Component.Child
    {


        private String id, name;
        private Class returntype;
        private final java.util.List<Class> parameters;
        private Reflector reflect;
        private Component parent;


        public Type(){
            super();
            this.parameters = new java.util.ArrayList<Class>();
        }
        public Type(String name, Class returntype, Class[] parameters) {
            this();
            this.name = name;
            this.returntype = returntype;
            for (Class p : parameters){
                this.parameters.add(p);
            }
        }


        public final String getId(){
            return this.id;
        }
        public final void setId(String id){
            if (null != id)
                this.id = id;
            else
                throw new IllegalArgumentException();
        }
        public final String getName(){
            String name = this.name;
            if (null != name)
                return name;
            else
                throw new IllegalStateException("Incompletely initialized.");
        }
        public final void setName(String name){
            if (null != name)
                this.name = name;
            else
                throw new IllegalArgumentException();
        }
        public final Class getReturnType(){
            Class returntype = this.returntype;
            if (null != returntype)
                return returntype;
            else
                throw new IllegalStateException("Incompletely initialized.");
        }
        public final void setReturnType(Class returntype){
            if (null != returntype)
                this.returntype = returntype;
            else
                throw new IllegalArgumentException();
        }
        public final java.util.List<Class> getParameters(){
            return this.parameters;
        }
        public final boolean accept(Method m){
            Class returntype = this.returntype;
            if (null == returntype)
                throw new IllegalStateException("Incompletely initialized.");
            else if (returntype.equals(m.getReturnType())){
                java.util.List<Class> tp = this.parameters;
                Class[] mp = m.getParameterTypes();
                int count = tp.size();
                if (count == mp.length){
                    Class ca, cb;
                    for (int cc = 0; cc < count; cc++){
                        ca = tp.get(cc);
                        cb = mp[cc];
                        if (!ca.equals(cb))
                            return false;
                    }
                    return true;
                }
            }
            return false;
        }
        public void setParent(Component parent){
            this.parent = parent;
        }
        public Reflector getReflect(){
            return this.reflect;
        }
        public Component getParent(){
            return this.parent;
        }
        public void startElement(Reflector bean){
            this.reflect = bean;
        }
        public void endElement(){
            if (null == this.returntype)
                this.returntype = Void.class;
        }
        public final int hashCode(){
            return this.returntype.hashCode();
        }
        public final String toString(){
            return this.name;
        }
        public boolean equals(Object that){
            if (this == that)
                return true;
            else if (that instanceof Type){
                Type thatType = (Type)that;
                if (this.returntype.equals(thatType.returntype)){
                    return this.parameters.equals(thatType.parameters);
                }
            }
            return false;
        }
    }
    /**
     * Object class methods list for function 'init'.
     */
    public static class List
        extends java.util.ArrayList<Method>
    {
        public List(Class clas){
            super();
            Method[] list = clas.getMethods();
            for (Method m : list){
                this.add(m);
            }
        }
        public List(List copy){
            super(copy);
        }
    }
    public static class Error
        extends RuntimeException
    {
        public Error(Throwable t){
            super(t);
        }
        public Error(String m, Throwable t){
            super(m,t);
        }
        public Error(String m){
            super(m);
        }
    }
    /**
     * May be used externally to represent a function that is not
     * bound.
     */
    public final static class Undefined
        extends Error
    {
        public final Function.Type type;


        public Undefined(Function.Type type){
            super(type.name);
            this.type = type;
        }

        
        public Type getType(){
            return this.type;
        }
        public boolean isType(Type type){
            return (type == this.type);
        }
    }
    public final static class MethodNotFound
        extends Error
    {
        public MethodNotFound(Throwable t){
            super(t);
        }
        public MethodNotFound(String m, Throwable t){
            super(m,t);
        }
        public MethodNotFound(String m){
            super(m);
        }
    }
    public final static class InvokeErrorAccess
        extends Error
    {
        public InvokeErrorAccess(Throwable t){
            super(t);
        }
        public InvokeErrorAccess(String m, Throwable t){
            super(m,t);
        }
        public InvokeErrorAccess(String m){
            super(m);
        }
    }
    public final static class InvokeErrorTarget
        extends Error
    {
        public InvokeErrorTarget(Throwable t){
            super(t);
        }
        public InvokeErrorTarget(String m, Throwable t){
            super(m,t);
        }
        public InvokeErrorTarget(String m){
            super(m);
        }
    }
    public final static class InvokeErrorNotInitialized
        extends Error
    {
        public InvokeErrorNotInitialized(Throwable t){
            super(t);
        }
        public InvokeErrorNotInitialized(String m, Throwable t){
            super(m,t);
        }
        public InvokeErrorNotInitialized(String m){
            super(m);
        }
    }

    private final static java.util.List<Integer> Scopes = new java.util.ArrayList<Integer>();
    static {
        Scopes.add(GLOBAL_SCOPE);
        Scopes.add(ENGINE_SCOPE);
    }


    private Type type;

    private String id, target, language, string;

    private StringBuilder script;

    private int scriptLocLno;

    private String scriptLocSid;

    private Resolver scriptGlobal;

    private Bindings scriptLocal;

    private Writer scriptOut, scriptErr;

    private Reader scriptIn;

    private ScriptEngine scriptEngine;

    private Compilable scriptEngineCompiler;

    private CompiledScript scriptCompiled;

    private boolean scriptInitFailure;

    protected Object instance;

    private Method method;

    private Function tlStack;

    private Reflector reflect;

    private Component parent;

    private java.util.List<String> scriptImports, scriptDeclarations;


    public Function(){
        super();
    }
    public Function(Type type, String target){
        super();
        if (null != type && null != target){
            this.type = type;
            this.target = target;
            this.string = type.toString()+':'+target;
        }
        else
            throw new IllegalArgumentException();
    }


    public final void setParent(Component parent){
        this.parent = parent;
    }
    public final Reflector getReflect(){
        return this.reflect;
    }
    public final Component getParent(){
        return this.parent;
    }
    public final String getId(){
        return this.id;
    }
    public final void setId(String id){
        if (null != id)
            this.id = id;
        else
            throw new IllegalArgumentException();
    }
    public final String getName(){
        Type type = this.type;
        if (null != type)
            return type.name;
        else
            throw new IllegalStateException("Incompletely initialized.");
    }
    public final Type getType(){
        return this.type;
    }
    public final void setType(Type type){
        if (null != type){
            this.type = type;
            if (null != this.target)
                this.string = type.toString()+':'+this.target;
        }
        else
            throw new IllegalArgumentException();
    }
    public final boolean isType(Type type){
        return (null != type && (type == this.type || type.equals(this.type)));
    }
    public final boolean hasTarget(){
        return (null != this.target);
    }
    public final String getTarget(){
        String target = this.target;
        if (null != target)
            return target;
        else
            throw new IllegalStateException("Incompletely initialized.");
    }
    public final void setTarget(String target){
        if (null != target){
            this.target = target;
            if (null != this.type)
                this.string = this.type.toString()+':'+target;
        }
        else
            throw new IllegalArgumentException();
    }
    public final boolean isTarget(String name){
        return (null != this.target && this.target.equals(name));
    }
    public final boolean hasLanguage(){
        return (null != this.language);
    }
    public final boolean hasNotLanguage(){
        return (null == this.language);
    }
    public final String getLanguage(){
        return this.language;
    }
    public final void setLanguage(String language){
        if (null != language)
            this.language = language;
        else
            throw new IllegalArgumentException();
    }
    public final boolean isInitialized(){
        return (null != this.instance && null != this.method)||(null != this.language && null != this.script && null != this.script);
    }
    public final boolean isMethod(){
        return this.isNotScript();
    }
    public final boolean isNotMethod(){
        return this.isScript();
    }
    public final boolean hasMethod(){
        return (null != this.method);
    }
    public final boolean hasNotMethod(){
        return (null == this.method);
    }
    public final Method getMethod(){
        return this.method;
    }
    public final boolean isObject(Object a){
        return (a == this.instance);
    }
    public final boolean isScript(){
        if (this.hasLanguage() && null != this.script)
            return (0 < this.script.length());
        else
            return false;
    }
    public final boolean isNotScript(){
        if (this.hasNotLanguage())
            return true;
        else
            return this.hasNotScript();
    }
    public final boolean hasScript(){
        if (null != this.script)
            return (0 < this.script.length());
        else
            return false;
    }
    public final boolean hasNotScript(){
        if (null == this.script)
            return true;
        else
            return (1 > this.script.length());
    }
    public final String getScript(){
        if (null != this.script && 0 < this.script.length())
            return this.script.toString();
        else
            return null;
    }
    public final void setScript(StringBuilder string){
        this.script = string;
    }
    public final void setScript(String string){
        if (null == string)
            this.script = null;
        else
            this.script = new StringBuilder(string);
    }

    /**
     * Script only init
     */
    public final void init()
        throws Function.MethodNotFound
    {
        if (null != this.language && null != this.script){
            Resolver resolver = Resolver.Get();
            if (null != resolver){
                this.scriptGlobal = resolver;

                if (null == this.scriptLocal)
                    this.scriptLocal = new Registry();

                if (null == this.scriptOut || this.scriptOut instanceof CharArrayWriter)
                    this.scriptOut = new CharArrayWriter();
                if (null == this.scriptErr || this.scriptErr instanceof CharArrayWriter)
                    this.scriptErr = new CharArrayWriter();
                if (null == this.scriptIn || this.scriptIn instanceof CharArrayReader)
                    this.scriptIn = new CharArrayReader(new char[0]);

                this.scriptEngine = resolver.getEngineByName(this.language);
                if (null != this.scriptEngine){
                    this.scriptEngine.setContext(this);
                    this.scriptInitFailure = false;
                    if (this.scriptEngine instanceof Compilable){
                        this.scriptEngineCompiler = (Compilable)this.scriptEngine;
                        try {
                            this.scriptCompiled = this.scriptEngineCompiler.compile(this.script.toString());
                        }
                        catch (ScriptException exc){
                            this.scriptInitFailure = true;
                            throw new MethodNotFound( Create(exc, this.scriptLocSid, this.scriptLocLno),exc);
                        }
                    }
                    else {
                        this.scriptEngineCompiler = null;
                        this.scriptCompiled = null;
                        this.scriptInitFailure = false;
                    }
                }
                else
                    throw new MethodNotFound("Script language not found '"+this.language+"'.");
            }
            else
                throw new MethodNotFound("Resolver not in thread local scope.  See 'Resolver.{enter,exit}'.");
        }
        else if (null == this.language)
            throw new MethodNotFound("Missing 'language'.");
    }
    /**
     * Universal script or method / function init
     * 
     * @param instance Null for static method (named in class camel
     * case), not null for instance method (named in field camel
     * case).
     * @param methods 
     */
    public final void init(Object instance, List methods)
        throws Function.MethodNotFound
    {
        if (null != this.language && null != this.script){
            this.init();
        }
        else {
            if (null != instance){
                String mname = Resolver.FieldCamel(this.target);
                Type type = this.type;

                for (Method method : methods){

                    if (mname.equals(method.getName())){
                    
                        if (type.accept(method)){

                            synchronized(this){

                                this.instance = instance;

                                this.method = method;
                            }
                            return;
                        }
                    }
                }

                throw new MethodNotFound("Instance method named '"+this.target+
                                         "' not found in object '"+instance.getClass().getName()+"'.");
            }
            else {
                String mname = Resolver.ClassCamel(this.target);
                Type type = this.type;

                for (Method method : methods){

                    if (mname.equals(method.getName())){
                    
                        if (type.accept(method)){

                            synchronized(this){

                                this.instance = null;

                                this.method = method;
                            }
                            return;
                        }
                    }
                }

                throw new MethodNotFound("Static method named '"+this.target+
                                         "' not found in object '"+instance.getClass().getName()+"'.");
            }
        }
    }
    /**
     * Universal script or method / function invoke.
     */
    public final Object invoke(Object... args)
        throws Function.InvokeErrorAccess, Function.InvokeErrorTarget,
               Function.InvokeErrorNotInitialized
    {
        Method method = this.method;
        if (null != method){
            Object a = this.instance;
            try {
                this.enter();
                try {
                    return this.method.invoke(a,args);
                }
                finally {
                    this.exit();
                }
            }
            catch (IllegalAccessException exc){
                throw new InvokeErrorAccess(exc);
            }
            catch (InvocationTargetException exc){
                throw new InvokeErrorTarget(exc);
            }
        }
        else 
            return this.invoke();
    }
    /**
     * Script invoke
     */
    public final Object invoke()
        throws Function.InvokeErrorTarget,
               Function.InvokeErrorNotInitialized
    {
        if (null != this.scriptEngine){
            this.enter();
            try {
                if (null != this.scriptCompiled){
                    try {
                        return this.scriptCompiled.eval();
                    }
                    catch (ScriptException exc){
                        throw new InvokeErrorTarget( Create(exc, this.scriptLocSid, this.scriptLocLno),exc);
                    }
                }
                else if (this.scriptInitFailure)
                    throw new InvokeErrorNotInitialized("Not initialized.");
                else {
                    try {
                        return this.scriptEngine.eval(this.script.toString());
                    }
                    catch (ScriptException exc){
                        throw new InvokeErrorTarget( Create(exc, this.scriptLocSid, this.scriptLocLno),exc);
                    }
                }
            }
            finally {
                this.exit();
            }
        }
        else
            return null;
    }
    public final void addElementText(Location loc, String child){

        StringBuilder script = this.script;
        if (null == script){
            script = new StringBuilder();
            this.script = script;
            this.scriptLocLno = loc.getLineNumber();
            this.scriptLocSid = loc.getSystemId();
        }
        script.append(child);
    }
    public void startElement(Reflector bean){
        this.reflect = bean;
    }
    public void endElement(){
    }

    public final void setBindings(Bindings bindings, int scope){
        switch (scope){
        case ENGINE_SCOPE:
            if (this.scriptLocal == bindings)
                return;
            else {
                if (null != this.scriptLocal && (!this.scriptLocal.isEmpty()))
                    bindings.putAll(this.scriptLocal);
                this.scriptLocal = bindings;
                return;
            }

        case GLOBAL_SCOPE:
            if (null != this.scriptGlobal){
                this.scriptGlobal.setBindings(bindings);
                return;
            }
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        default:
            throw new IllegalArgumentException("Unrecognized scope.");
        }
    }
    public final Bindings getBindings(int scope){
        switch (scope){
        case ENGINE_SCOPE:
            if (null != this.scriptLocal)
                return this.scriptLocal;
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        case GLOBAL_SCOPE:
            if (null != this.scriptGlobal)
                return this.scriptGlobal.getBindings(scope);
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        default:
            throw new IllegalArgumentException("Unrecognized scope.");
        }
    }
    public final void setAttribute(String name, Object value, int scope){
        switch (scope){
        case ENGINE_SCOPE:
            if (null != this.scriptLocal){
                this.scriptLocal.put(name,value);
                return;
            }
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        case GLOBAL_SCOPE:
            if (null != this.scriptGlobal){
                this.scriptGlobal.setAttribute(name,value,scope);
                return;
            }
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        default:
            throw new IllegalArgumentException("Unrecognized scope.");
        }
    }
    public final Object getAttribute(String name, int scope){
        switch (scope){
        case ENGINE_SCOPE:
            if (null != this.scriptLocal)
                return this.scriptLocal.get(name);
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        case GLOBAL_SCOPE:
            if (null != this.scriptGlobal)
                return this.scriptGlobal.getAttribute(name,scope);
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        default:
            throw new IllegalArgumentException("Unrecognized scope.");
        }
    }
    public final Object removeAttribute(String name, int scope){
        switch (scope){
        case ENGINE_SCOPE:
            if (null != this.scriptLocal)
                return this.scriptLocal.remove(name);
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        case GLOBAL_SCOPE:
            if (null != this.scriptGlobal)
                return this.scriptGlobal.removeAttribute(name,scope);
            else
                throw new IllegalArgumentException("Uninitialized scope.");
        default:
            throw new IllegalArgumentException("Unrecognized scope.");
        }
    }
    public final Object getAttribute(String name){
        if (null != this.scriptLocal){
            Object re = this.scriptLocal.get(name);
            if (null != re)
                return re;
        }
        if (null != this.scriptGlobal)
            return this.scriptGlobal.getAttribute(name);
        else
            return null;
    }
    public final int getAttributesScope(String name){
        if (null != this.scriptLocal){
            Object re = this.scriptLocal.get(name);
            if (null != re)
                return ENGINE_SCOPE;
        }
        if (null != this.scriptGlobal)
            return this.scriptGlobal.getAttributesScope(name);
        else
            return -1;
    }
    public final java.io.Writer getWriter(){
        return this.scriptOut;
    }
    public final java.io.Writer getErrorWriter(){
        return this.scriptErr;
    }
    public final void setWriter(java.io.Writer w){
        this.scriptOut = w;
    }
    public final void setErrorWriter(java.io.Writer w){
        this.scriptErr = w;
    }
    public final java.io.Reader getReader(){
        return this.scriptIn;
    }
    public final void setReader(java.io.Reader r){
        this.scriptIn = r;
    }
    public final java.util.List<Integer> getScopes(){
        return Scopes;
    }

    public java.util.List<String> getImportStatements(){
        return this.scriptImports;
    }
    public void setImportStatements(java.util.List<String> list){
        this.scriptImports = list;
    }
    public java.util.List<String> getDeclarationStatements(){
        return this.scriptDeclarations;
    }
    public void setDeclarationStatements(java.util.List<String> list){
        this.scriptDeclarations = list;
    }

    public String toString(){
        return this.string;
    }
    public int hashCode(){
        return this.string.hashCode();
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (that instanceof Function)
            return this.string.equals(that.toString());
        else
            return false;
    }

    protected final void enter(){
        this.tlStack = TL.get();
        TL.set(this);
    }
    protected final void exit(){
        TL.remove();
        Function tlStack = this.tlStack;
        if (null != tlStack){
            this.tlStack = null;
            TL.set(tlStack);
        }
    }
    /**
     * Exception message from script compile or eval.
     */
    protected final String Create(ScriptException exc, String loc, int locLno){

        int lno = exc.getLineNumber();
        if (0 < lno){
            if (0 < locLno){
                lno += locLno;

                if (null != loc)
                    return (loc+":"+lno+": "+exc.getMessage());
                else
                    return ("Error in script at source line "+lno+": "+exc.getMessage());
            }
            else if (null != loc)
                return (loc+": in script line "+lno+": "+exc.getMessage());
            else
                return ("Error in script line "+lno+": "+exc.getMessage());
        }
        else if (null != loc){
            if (0 < locLno)
                return (loc+":"+locLno+": "+exc.getMessage());
            else
                return (loc+": "+exc.getMessage());
        }
        else
            return ("Error in script: "+exc.getMessage());
    }
}
