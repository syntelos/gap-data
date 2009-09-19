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

import gap.jac.code.Type;

import static gap.jac.code.TypeTags.*;

/**
 * Utilities for operating on constant values.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 * you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
public class Constants {

    /**
     * Converts a constant in internal representation (in which
     * boolean, char, byte, short, and int are each represented by an
     * Integer) into standard representation.  Other values (including
     * null) are returned unchanged.
     */
    public static Object decode(Object value, Type type) {
        if (value instanceof Integer) {
            int i = (Integer) value;
            switch (type.tag) {
            case BOOLEAN:  return i != 0;
            case CHAR:     return (char) i;
            case BYTE:     return (byte) i;
            case SHORT:    return (short) i;
            }
        }
        return value;
    }

    /**
     * Returns a string representation of a constant value (given in
     * internal representation), quoted and formatted as in Java source.
     */
    public static String format(Object value, Type type) {
        value = decode(value, type);
        switch (type.tag) {
        case BYTE:      return formatByte((Byte) value);
        case LONG:      return formatLong((Long) value);
        case FLOAT:     return formatFloat((Float) value);
        case DOUBLE:    return formatDouble((Double) value);
        case CHAR:      return formatChar((Character) value);
        }
        if (value instanceof String)
            return formatString((String) value);
        return value + "";
    }

    /**
     * Returns a string representation of a constant value (given in
     * standard wrapped representation), quoted and formatted as in
     * Java source.
     */
    public static String format(Object value) {
        if (value instanceof Byte)      return formatByte((Byte) value);
        if (value instanceof Long)      return formatLong((Long) value);
        if (value instanceof Float)     return formatFloat((Float) value);
        if (value instanceof Double)    return formatDouble((Double) value);
        if (value instanceof Character) return formatChar((Character) value);
        if (value instanceof String)    return formatString((String) value);
        return value + "";
    }

    private static String formatByte(byte b) {
        return String.format("0x%02x", b);
    }

    private static String formatLong(long lng) {
        return lng + "L";
    }

    private static String formatFloat(float f) {
        if (Float.isNaN(f))
            return "0.0f/0.0f";
        else if (Float.isInfinite(f))
            return (f < 0) ? "-1.0f/0.0f" : "1.0f/0.0f";
        else
            return f + "f";
    }

    private static String formatDouble(double d) {
        if (Double.isNaN(d))
            return "0.0/0.0";
        else if (Double.isInfinite(d))
            return (d < 0) ? "-1.0/0.0" : "1.0/0.0";
        else
            return d + "";
    }

    private static String formatChar(char c) {
        return '\'' + Convert.quote(c) + '\'';
    }

    private static String formatString(String s) {
        return '"' + Convert.quote(s) + '"';
    }
}
