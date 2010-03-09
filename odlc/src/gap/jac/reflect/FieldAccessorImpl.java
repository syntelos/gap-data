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

/** Package-private implementation of the FieldAccessor interface
    which has access to all classes and all fields, regardless of
    language restrictions. See MagicAccessorImpl. */

abstract class FieldAccessorImpl extends MagicAccessorImpl
    implements FieldAccessor {
    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract Object get(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract boolean getBoolean(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract byte getByte(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract char getChar(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract short getShort(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract int getInt(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract long getLong(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract float getFloat(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract double getDouble(Object obj)
        throws IllegalArgumentException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void set(Object obj, Object value)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setBoolean(Object obj, boolean z)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setByte(Object obj, byte b)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setChar(Object obj, char c)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setShort(Object obj, short s)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setInt(Object obj, int i)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setLong(Object obj, long l)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setFloat(Object obj, float f)
        throws IllegalArgumentException, IllegalAccessException;

    /** Matches specification in {@link java.lang.reflect.Field} */
    public abstract void setDouble(Object obj, double d)
        throws IllegalArgumentException, IllegalAccessException;
}
