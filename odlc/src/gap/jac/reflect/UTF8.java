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
package gap.jac.reflect;

/** It is necessary to use a "bootstrap" UTF-8 encoder for encoding
    constant pool entries because the character set converters rely on
    Class.newInstance(). */

class UTF8 {
    // This encoder is not quite correct.  It does not handle surrogate pairs.
    static byte[] encode(String str) {
        int len = str.length();
        byte[] res = new byte[utf8Length(str)];
        int utf8Idx = 0;
        try {
            for (int i = 0; i < len; i++) {
                int c = str.charAt(i) & 0xFFFF;
                if (c >= 0x0001 && c <= 0x007F) {
                    res[utf8Idx++] = (byte) c;
                } else if (c == 0x0000 ||
                           (c >= 0x0080 && c <= 0x07FF)) {
                    res[utf8Idx++] = (byte) (0xC0 + (c >> 6));
                    res[utf8Idx++] = (byte) (0x80 + (c & 0x3F));
                } else {
                    res[utf8Idx++] = (byte) (0xE0 + (c >> 12));
                    res[utf8Idx++] = (byte) (0x80 + ((c >> 6) & 0x3F));
                    res[utf8Idx++] = (byte) (0x80 + (c & 0x3F));
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InternalError
                ("Bug in gap.jac.reflect bootstrap UTF-8 encoder");
        }
        return res;
    }

    private static int utf8Length(String str) {
        int len = str.length();
        int utf8Len = 0;
        for (int i = 0; i < len; i++) {
            int c = str.charAt(i) & 0xFFFF;
            if (c >= 0x0001 && c <= 0x007F) {
                utf8Len += 1;
            } else if (c == 0x0000 ||
                       (c >= 0x0080 && c <= 0x07FF)) {
                utf8Len += 2;
            } else {
                utf8Len += 3;
            }
        }
        return utf8Len;
    }
}
