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
package gap.jac.util;

import java.io.*;

/** A byte buffer is a flexible array which grows when elements are
 *  appended. There are also methods to append names to byte buffers
 *  and to convert byte buffers to names.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class ByteBuffer {

    /** An array holding the bytes in this buffer; can be grown.
     */
    public byte[] elems;

    /** The current number of defined bytes in this buffer.
     */
    public int length;

    /** Create a new byte buffer.
     */
    public ByteBuffer() {
        this(64);
    }

    /** Create a new byte buffer with an initial elements array
     *  of given size.
     */
    public ByteBuffer(int initialSize) {
        elems = new byte[initialSize];
        length = 0;
    }

    private void copy(int size) {
        byte[] newelems = new byte[size];
        System.arraycopy(elems, 0, newelems, 0, elems.length);
        elems = newelems;
    }

    /** Append byte to this buffer.
     */
    public void appendByte(int b) {
        if (length >= elems.length) copy(elems.length * 2);
        elems[length++] = (byte)b;
    }

    /** Append `len' bytes from byte array,
     *  starting at given `start' offset.
     */
    public void appendBytes(byte[] bs, int start, int len) {
        while (length + len > elems.length) copy(elems.length * 2);
        System.arraycopy(bs, start, elems, length, len);
        length += len;
    }

    /** Append all bytes from given byte array.
     */
    public void appendBytes(byte[] bs) {
        appendBytes(bs, 0, bs.length);
    }

    /** Append a character as a two byte number.
     */
    public void appendChar(int x) {
        while (length + 1 >= elems.length) copy(elems.length * 2);
        elems[length  ] = (byte)((x >>  8) & 0xFF);
        elems[length+1] = (byte)((x      ) & 0xFF);
        length = length + 2;
    }

    /** Append an integer as a four byte number.
     */
    public void appendInt(int x) {
        while (length + 3 >= elems.length) copy(elems.length * 2);
        elems[length  ] = (byte)((x >> 24) & 0xFF);
        elems[length+1] = (byte)((x >> 16) & 0xFF);
        elems[length+2] = (byte)((x >>  8) & 0xFF);
        elems[length+3] = (byte)((x      ) & 0xFF);
        length = length + 4;
    }

    /** Append a long as an eight byte number.
     */
    public void appendLong(long x) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(8);
        DataOutputStream bufout = new DataOutputStream(buffer);
        try {
            bufout.writeLong(x);
            appendBytes(buffer.toByteArray(), 0, 8);
        } catch (IOException e) {
            throw new AssertionError("write");
        }
    }

    /** Append a float as a four byte number.
     */
    public void appendFloat(float x) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(4);
        DataOutputStream bufout = new DataOutputStream(buffer);
        try {
            bufout.writeFloat(x);
            appendBytes(buffer.toByteArray(), 0, 4);
        } catch (IOException e) {
            throw new AssertionError("write");
        }
    }

    /** Append a double as a eight byte number.
     */
    public void appendDouble(double x) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(8);
        DataOutputStream bufout = new DataOutputStream(buffer);
        try {
            bufout.writeDouble(x);
            appendBytes(buffer.toByteArray(), 0, 8);
        } catch (IOException e) {
            throw new AssertionError("write");
        }
    }

    /** Append a name.
     */
    public void appendName(Name name) {
        appendBytes(name.table.names, name.index, name.len);
    }

    /** Reset to zero length.
     */
    public void reset() {
        length = 0;
    }

    /** Convert contents to name.
     */
    public Name toName(Name.Table names) {
        return names.fromUtf(elems, 0, length);
    }
}
