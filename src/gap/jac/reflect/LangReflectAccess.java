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

import java.lang.reflect.*;

/** An interface which gives privileged packages Java-level access to
    internals of java.lang.reflect. */

public interface LangReflectAccess {
    /** Creates a new java.lang.reflect.Field. Access checks as per
        java.lang.reflect.AccessibleObject are not overridden. */
    public Field newField(Class declaringClass,
                          String name,
                          Class type,
                          int modifiers,
                          int slot,
                          String signature,
                          byte[] annotations);

    /** Creates a new java.lang.reflect.Method. Access checks as per
      java.lang.reflect.AccessibleObject are not overridden. */
    public Method newMethod(Class declaringClass,
                            String name,
                            Class[] parameterTypes,
                            Class returnType,
                            Class[] checkedExceptions,
                            int modifiers,
                            int slot,
                            String signature,
                            byte[] annotations,
                            byte[] parameterAnnotations,
                            byte[] annotationDefault);

    /** Creates a new java.lang.reflect.Constructor. Access checks as
      per java.lang.reflect.AccessibleObject are not overridden. */
    public <T> Constructor<T> newConstructor(Class<T> declaringClass,
                                             Class[] parameterTypes,
                                             Class[] checkedExceptions,
                                             int modifiers,
                                             int slot,
                                             String signature,
                                             byte[] annotations,
                                             byte[] parameterAnnotations);

    /** Gets the MethodAccessor object for a java.lang.reflect.Method */
    public MethodAccessor getMethodAccessor(Method m);

    /** Sets the MethodAccessor object for a java.lang.reflect.Method */
    public void setMethodAccessor(Method m, MethodAccessor accessor);

    /** Gets the ConstructorAccessor object for a
        java.lang.reflect.Constructor */
    public ConstructorAccessor getConstructorAccessor(Constructor c);

    /** Sets the ConstructorAccessor object for a
        java.lang.reflect.Constructor */
    public void setConstructorAccessor(Constructor c,
                                       ConstructorAccessor accessor);

    /** Gets the "slot" field from a Constructor (used for serialization) */
    public int getConstructorSlot(Constructor c);

    /** Gets the "signature" field from a Constructor (used for serialization) */
    public String getConstructorSignature(Constructor c);

    /** Gets the "annotations" field from a Constructor (used for serialization) */
    public byte[] getConstructorAnnotations(Constructor c);

    /** Gets the "parameterAnnotations" field from a Constructor (used for serialization) */
    public byte[] getConstructorParameterAnnotations(Constructor c);

    //
    // Copying routines, needed to quickly fabricate new Field,
    // Method, and Constructor objects from templates
    //

    /** Makes a "child" copy of a Method */
    public Method      copyMethod(Method arg);

    /** Makes a "child" copy of a Field */
    public Field       copyField(Field arg);

    /** Makes a "child" copy of a Constructor */
    public <T> Constructor<T> copyConstructor(Constructor<T> arg);
}
