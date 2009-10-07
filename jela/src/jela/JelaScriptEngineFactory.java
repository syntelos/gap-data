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

import javax.script.*;
import java.util.*;

/**
 * This is script engine factory for "Java" script engine.
 * 
 * @author A. Sundararajan
 * @author J. Pritchard
 */
public final class JelaScriptEngineFactory 
    implements ScriptEngineFactory 
{
    private final static List<String> Names;
    private final static List<String> Extensions;
    private final static List<String> MimeTypes;
    private final static Map<String,String> Parameters;
    static {
        List<String> list;

        list = new ArrayList<String>(1);
        list.add(Version.Name);
        Names = Collections.unmodifiableList(list);
        Extensions = Names;

        list = new ArrayList<String>(0);
        MimeTypes = Collections.unmodifiableList(list);

        Map<String,String> map;

        map = new HashMap<String,String>();
        map.put(ScriptEngine.ENGINE,Version.Name);
        map.put(ScriptEngine.ENGINE_VERSION,Version.Number);
        map.put(ScriptEngine.NAME,Version.Name);
        map.put(ScriptEngine.LANGUAGE,Version.Name);
        map.put(ScriptEngine.LANGUAGE_VERSION,Version.Number);
        map.put("THREADING","MULTITHREADED");
        Parameters = Collections.unmodifiableMap(map);
    }


    public JelaScriptEngineFactory(){
        super();
    }


    public ScriptEngine getScriptEngine() {
        return (new JelaScriptEngine(this));
    }
    public String getEngineName() { 
        return Version.Name;
    }
    public String getEngineVersion() {
        return Version.Number;
    }
    public String getLanguageName() {
        return Version.Name;
    }
    public String getLanguageVersion() {
        return Version.Number;
    }
    public List<String> getExtensions() {
        return Extensions;
    }
    public List<String> getMimeTypes() {
        return MimeTypes;
    }
    public List<String> getNames() {
        return Names;
    }
    public String getParameter(String key) {
        return Parameters.get(key);
    } 
    public String getMethodCallSyntax(String obj, String m, String... args) {
        throw new UnsupportedOperationException();
    }
    public String getOutputStatement(String toDisplay) {
        throw new UnsupportedOperationException();
    }
    public String getProgram(String... statements) {
        throw new UnsupportedOperationException();
    }
}
