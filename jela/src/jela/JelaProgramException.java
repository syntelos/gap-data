/*
 * Copyright (c) 2009 John Pritchard and the Jela Project Group
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
package jela;

/**
 * Error in compiling a generated JPL source program from the "jela" engine. 
 * 
 * @author J. Pritchard
 */
public final class JelaProgramException
    extends javax.script.ScriptException
{

    private JelaProgramCompiler jela;

    public JelaProgramException(JelaProgramCompiler jela){
        super('\n'+jela.getErrors());
        this.jela = jela;
    }


    public String getClassName(){
        return this.jela.getClassName();
    }
    public String getErrors(){
        return this.jela.getErrors();
    }
    public String getSource(){
        return this.jela.getSource();
    }
    public String getSourceLineNumbered(){
        return this.jela.getSourceLineNumbered();
    }
}
