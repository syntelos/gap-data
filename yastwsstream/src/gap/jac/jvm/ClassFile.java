/*
 * Copyright 1999-2006 Sun Microsystems, Inc.  All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * "CLASSPATH" EXCEPTION TO THE GPL
 * 
 * Certain source files distributed by Sun Microsystems, Inc.  are subject to
 * the following clarification and special exception to the GPL, but only where
 * Sun has expressly included in the particular source file's header the words
 * "Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the LICENSE file that accompanied this code."
 * 
 *   Linking this library statically or dynamically with other modules is making
 *   a combined work based on this library.  Thus, the terms and conditions of
 *   the GNU General Public License cover the whole combination.
 * 
 *   As a special exception, the copyright holders of this library give you
 *   permission to link this library with independent modules to produce an
 *   executable, regardless of the license terms of these independent modules,
 *   and to copy and distribute the resulting executable under terms of your
 *   choice, provided that you also meet, for each linked independent module,
 *   the terms and conditions of the license of that module.  An independent
 *   module is a module which is not derived from or based on this library.  If
 *   you modify this library, you may extend this exception to your version of
 *   the library, but you are not obligated to do so.  If you do not wish to do
 *   so, delete this exception statement from your version.
 */
package gap.jac.jvm;

import gap.jac.code.*;
import gap.jac.util.*;

/** A JVM class file.
 *
 *  <p>Generic Java classfiles have one additional attribute for classes,
 *  methods and fields:
 *  <pre>
 *   "Signature" (u4 attr-length, u2 signature-index)
 *  </pre>
 *
 *  <p>A signature gives the full Java type of a method or field. When
 *  used as a class attribute, it indicates type parameters, followed
 *  by supertype, followed by all interfaces.
 *  <pre>
 *     methodOrFieldSignature ::= type
 *     classSignature         ::= [ typeparams ] supertype { interfacetype }
 *  </pre>
 *  <p>The type syntax in signatures is extended as follows:
 *  <pre>
 *     type       ::= ... | classtype | methodtype | typevar
 *     classtype  ::= classsig { '.' classsig }
 *     classig    ::= 'L' name [typeargs] ';'
 *     methodtype ::= [ typeparams ] '(' { type } ')' type
 *     typevar    ::= 'T' name ';'
 *     typeargs   ::= '<' type { type } '>'
 *     typeparams ::= '<' typeparam { typeparam } '>'
 *     typeparam  ::= name ':' type
 *  </pre>
 *  <p>This class defines constants used in class files as well
 *  as routines to convert between internal ``.'' and external ``/''
 *  separators in class names.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b> */
public class ClassFile {

    public final static int JAVA_MAGIC = 0xCAFEBABE;

    // see Target
    public final static int CONSTANT_Utf8 = 1;
    public final static int CONSTANT_Unicode = 2;
    public final static int CONSTANT_Integer = 3;
    public final static int CONSTANT_Float = 4;
    public final static int CONSTANT_Long = 5;
    public final static int CONSTANT_Double = 6;
    public final static int CONSTANT_Class = 7;
    public final static int CONSTANT_String = 8;
    public final static int CONSTANT_Fieldref = 9;
    public final static int CONSTANT_Methodref = 10;
    public final static int CONSTANT_InterfaceMethodref = 11;
    public final static int CONSTANT_NameandType = 12;

    public final static int MAX_PARAMETERS = 0xff;
    public final static int MAX_DIMENSIONS = 0xff;
    public final static int MAX_CODE = 0xffff;
    public final static int MAX_LOCALS = 0xffff;
    public final static int MAX_STACK = 0xffff;


/************************************************************************
 * String Translation Routines
 ***********************************************************************/

    /** Return internal representation of buf[offset..offset+len-1],
     *  converting '/' to '.'.
     */
    public static byte[] internalize(byte[] buf, int offset, int len) {
        byte[] translated = new byte[len];
        for (int j = 0; j < len; j++) {
            byte b = buf[offset + j];
            if (b == '/') translated[j] = (byte) '.';
            else translated[j] = b;
        }
        return translated;
    }

    /** Return internal representation of given name,
     *  converting '/' to '.'.
     */
    public static byte[] internalize(Name name) {
        return internalize(name.table.names, name.index, name.len);
    }

    /** Return external representation of buf[offset..offset+len-1],
     *  converting '.' to '/'.
     */
    public static byte[] externalize(byte[] buf, int offset, int len) {
        byte[] translated = new byte[len];
        for (int j = 0; j < len; j++) {
            byte b = buf[offset + j];
            if (b == '.') translated[j] = (byte) '/';
            else translated[j] = b;
        }
        return translated;
    }

    /** Return external representation of given name,
     *  converting '/' to '.'.
     */
    public static byte[] externalize(Name name) {
        return externalize(name.table.names, name.index, name.len);
    }

/************************************************************************
 * Name-and-type
 ***********************************************************************/

    /** A class for the name-and-type signature of a method or field.
     */
    public static class NameAndType {
        Name name;
        Type type;

        NameAndType(Name name, Type type) {
            this.name = name;
            this.type = type;
        }

        public boolean equals(Object other) {
            return
                other instanceof NameAndType &&
                name == ((NameAndType) other).name &&
                type.equals(((NameAndType) other).type);
        }

        public int hashCode() {
            return name.hashCode() * type.hashCode();
        }
    }
}
