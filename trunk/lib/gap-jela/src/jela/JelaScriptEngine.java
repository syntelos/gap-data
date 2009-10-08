/*
 * Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. 
 * Copyright (C) 2009 John Pritchard and the Jela Project. All rights reserved. 
 * Use is subject to license terms.
 *
 * Redistribution and use in source and binary forms, with or without modification, are 
 * permitted provided that the following conditions are met: Redistributions of source code 
 * must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of 
 * conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution. Neither the name of the Sun Microsystems nor the names of 
 * is contributors may be used to endorse or promote products derived from this software 
 * without specific prior written permission. 

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER 
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package jela;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.script.*;

/**
 * Script engine for the "jela" programming language.
 * 
 * @author A. Sundararajan
 * @author J. Pritchard
 */
public final class JelaScriptEngine
    extends AbstractScriptEngine 
    implements Compilable
{
    private final static String ReadFully(Reader reader) throws ScriptException { 
        char[] arr = new char[0x200]; 
        StringBuilder buf = new StringBuilder();
        int numChars;
        try {
            while (0 < (numChars = reader.read(arr, 0, 0x200))){
                buf.append(arr, 0, numChars);
            }
        } catch (IOException exp) {
            throw new ScriptException(exp);
        }
        return buf.toString();
    }


    private final JelaScriptEngineFactory factory;

    private String identifier;


    JelaScriptEngine(JelaScriptEngineFactory factory) {
        super();
        this.factory = factory;
    }



    public String getIdentifier(){
        return this.identifier;
    }
    public void setContext(ScriptContext cx){
        super.setContext(cx);
        if (cx instanceof JelaContextId)
            this.identifier = ((JelaContextId)cx).getId();
    }
    public CompiledScript compile(String script)
        throws ScriptException
    {
        JelaProgram prog = new JelaProgram(this,script);
        return prog.compile();
    }
    public ScriptEngineFactory getFactory() {
        return this.factory;
    }
    public CompiledScript compile(Reader reader) throws ScriptException {        
        return this.compile(ReadFully(reader));
    }
    public Object eval(String str, ScriptContext ctx) 
        throws ScriptException
    {
        throw new UnsupportedOperationException();
    }
    public Object eval(Reader reader, ScriptContext ctx)
        throws ScriptException
    {
        throw new UnsupportedOperationException();
    }
    public Bindings createBindings() {
        return new SimpleBindings();
    }
}
