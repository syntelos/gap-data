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
