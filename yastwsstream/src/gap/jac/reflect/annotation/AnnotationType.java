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
package gap.jac.reflect.annotation;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Represents an annotation type at run time.  Used to type-check annotations
 * and apply member defaults.
 *
 * @author  Josh Bloch
 * @since   1.5
 */
public class AnnotationType {

    private volatile static AnnotationType JavaLangClass;

    /**
     * Member name -> type mapping. Note that primitive types
     * are represented by the class objects for the corresponding wrapper
     * types.  This matches the return value that must be used for a
     * dynamic proxy, allowing for a simple isInstance test.
     */
    private final Map<String, Class> memberTypes = new HashMap<String,Class>();

    /**
     * Member name -> default value mapping.
     */
    private final Map<String, Object> memberDefaults =
        new HashMap<String, Object>();

    /**
     * Member name -> Method object mapping. This (and its assoicated
     * accessor) are used only to generate AnnotationTypeMismatchExceptions.
     */
    private final Map<String, Method> members = new HashMap<String, Method>();

    /**
     * The retention policy for this annotation type.
     */
    private RetentionPolicy retention = RetentionPolicy.RUNTIME;;

    /**
     * Whether this annotation type is inherited.
     */
    private boolean inherited = false;

    /**
     * Returns an AnnotationType instance for the specified annotation type.
     *
     * @throw IllegalArgumentException if the specified class object for
     *     does not represent a valid annotation type
     */
    public static synchronized AnnotationType getInstance(
        Class annotationClass)
    {

        return (new AnnotationType((Class<?>) annotationClass));
    }

    /**
     * Sole constructor.
     *
     * @param annotationClass the class object for the annotation type
     * @throw IllegalArgumentException if the specified class object for
     *     does not represent a valid annotation type
     */
    private AnnotationType(final Class<?> annotationClass) {
        if (!annotationClass.isAnnotation())
            throw new IllegalArgumentException("Not an annotation type");

        Method[] methods =
            AccessController.doPrivileged(new PrivilegedAction<Method[]>() {
                public Method[] run() {
                    // Initialize memberTypes and defaultValues
                    return annotationClass.getDeclaredMethods();
                }
            });


        for (Method method :  methods) {
            if (method.getParameterTypes().length != 0)
                throw new IllegalArgumentException(method + " has params");
            String name = method.getName();
            Class type = method.getReturnType();
            memberTypes.put(name, invocationHandlerReturnType(type));
            members.put(name, method);

            Object defaultValue = method.getDefaultValue();
            if (defaultValue != null)
                memberDefaults.put(name, defaultValue);

            members.put(name, method);
        }



        // Initialize retention, & inherited fields.  Special treatment
        // of the corresponding annotation types breaks infinite recursion.
        if (annotationClass != Retention.class &&
            annotationClass != Inherited.class) {
            Retention ret = annotationClass.getAnnotation(Retention.class);
            retention = (ret == null ? RetentionPolicy.CLASS : ret.value());
            inherited = annotationClass.isAnnotationPresent(Inherited.class);
        }
    }

    /**
     * Returns the type that must be returned by the invocation handler
     * of a dynamic proxy in order to have the dynamic proxy return
     * the specified type (which is assumed to be a legal member type
     * for an annotation).
     */
    public static Class invocationHandlerReturnType(Class type) {
        // Translate primitives to wrappers
        if (type == byte.class)
            return Byte.class;
        if (type == char.class)
            return Character.class;
        if (type == double.class)
            return Double.class;
        if (type == float.class)
            return Float.class;
        if (type == int.class)
            return Integer.class;
        if (type == long.class)
            return Long.class;
        if (type == short.class)
            return Short.class;
        if (type == boolean.class)
            return Boolean.class;

        // Otherwise, just return declared type
        return type;
    }

    /**
     * Returns member types for this annotation type
     * (member name -> type mapping).
     */
    public Map<String, Class> memberTypes() {
        return memberTypes;
    }

    /**
     * Returns members of this annotation type
     * (member name -> associated Method object mapping).
     */
    public Map<String, Method> members() {
        return members;
    }

    /**
     * Returns the default values for this annotation type
     * (Member name -> default value mapping).
     */
    public Map<String, Object> memberDefaults() {
        return memberDefaults;
    }

    /**
     * Returns the retention policy for this annotation type.
     */
    public RetentionPolicy retention() {
        return retention;
    }

    /**
     * Returns true if this this annotation type is inherited.
     */
    public boolean isInherited() {
        return inherited;
    }

    /**
     * For debugging.
     */
    public String toString() {
        StringBuffer s = new StringBuffer("Annotation Type:" + "\n");
        s.append("   Member types: " + memberTypes + "\n");
        s.append("   Member defaults: " + memberDefaults + "\n");
        s.append("   Retention policy: " + retention + "\n");
        s.append("   Inherited: " + inherited);
        return s.toString();
    }
}
