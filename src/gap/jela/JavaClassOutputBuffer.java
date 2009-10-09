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
/*
 * Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. 
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
package gap.jela;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.JavaFileObject.Kind;

/**
 * A file object used to represent Java byte code coming from the compiler.
 * 
 * @author A. Sundararajan
 * @author J. Pritchard
 */
public final class JavaClassOutputBuffer
    extends javax.tools.SimpleJavaFileObject
{
    private JelaProgramCompiler compiler;
    private String name;
    
    public JavaClassOutputBuffer(JelaProgramCompiler compiler, String classname) { 
        super(ToURI(classname), Kind.CLASS);
        this.compiler = compiler;
        this.name = classname;
    }

    public OutputStream openOutputStream() {
        return new java.io.ByteArrayOutputStream(){
            public void close() throws IOException {
                compiler.completed(this.toByteArray());
            }
        };
    }

    public final static URI ToURI(String name) {
        final StringBuilder newUri = new StringBuilder();
        newUri.append("mfm:///");
        newUri.append(name.replace('.', '/'));
        newUri.append(".class");
        return URI.create(newUri.toString());
    }
}
