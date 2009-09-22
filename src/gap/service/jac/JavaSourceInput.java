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
package gap.service.jac;

import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.CharBuffer;
import gap.jac.tools.JavaFileObject.Kind;

/**
 * 
 * 
 * @author jdp
 */
public final class JavaSourceInput
    extends gap.jac.tools.SimpleJavaFileObject
{

    private Reader in;


    public JavaSourceInput(URI uri, Reader in) {
        super(uri, Kind.SOURCE);
        this.in = in;
    }


    public Reader openReader() {
        return this.in;
    }
    public String toString(){
        return this.toUri().toString();
    }
}
