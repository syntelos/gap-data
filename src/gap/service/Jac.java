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

import gap.service.jac.FileManager;

import java.io.OutputStream;
import java.io.Reader;
import java.util.List;


/**
 * Compile java source to binary.
 * 
 * @author jdp
 */
public class Jac
    extends java.lang.ClassLoader
{
    /**
     * Compile JPL sourcecode into output.
     * 
     * @exception java.io.IOException Error writing to output.
     */
    public final static boolean CompileSource(String className, Reader in, OutputStream err, OutputStream bin)
        throws java.io.IOException
    {
        if (null != className && null != in && null != bin){
            FileManager fm = FileManager.Get();
            if (null != fm)
                return fm.compile(className,in,err,bin);
            else
                throw new IllegalStateException();
        }
        else
            throw new IllegalArgumentException();
    }

}
