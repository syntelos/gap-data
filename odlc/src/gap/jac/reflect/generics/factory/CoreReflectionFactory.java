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
package gap.jac.reflect.generics.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;


import gap.jac.reflect.generics.reflectiveObjects.*;
import gap.jac.reflect.generics.scope.Scope;
import gap.jac.reflect.generics.tree.FieldTypeSignature;


/**
 * Factory for reflective generic type objects for use by
 * core reflection (java.lang.reflect).
 */
public class CoreReflectionFactory implements GenericsFactory {
    private GenericDeclaration decl;
    private Scope scope;

    private CoreReflectionFactory(GenericDeclaration d, Scope s) {
        decl = d;
        scope = s;
    }

    private GenericDeclaration getDecl(){ return decl;}

    private Scope getScope(){ return scope;}


    private ClassLoader getDeclsLoader() {
        if (decl instanceof Class) {return ((Class) decl).getClassLoader();}
        if (decl instanceof Method) {
            return ((Method) decl).getDeclaringClass().getClassLoader();
        }
        assert decl instanceof Constructor : "Constructor expected";
        return ((Constructor) decl).getDeclaringClass().getClassLoader();

    }

    /**
     * Factory for this class. Returns an instance of
     * <tt>CoreReflectionFactory</tt> for the declaration and scope
     * provided.
     * This factory will produce reflective objects of the appropriate
     * kind. Classes produced will be those that would be loaded by the
     * defining class loader of the declaration <tt>d</tt> (if <tt>d</tt>
     * is a type declaration, or by the defining loader of the declaring
     * class of <tt>d</tt>  otherwise.
     * <p> Type variables will be created or lookup as necessary in the
     * scope <tt> s</tt>.
     * @param d - the generic declaration (class, interface, method or
     * constructor) that thsi factory services
     * @param s  the scope in which the factory will allocate and search for
     * type variables
     * @return an instance of <tt>CoreReflectionFactory</tt>
     */
    public static CoreReflectionFactory make(GenericDeclaration d, Scope s) {
        return new CoreReflectionFactory(d, s);
    }

    public TypeVariable<?> makeTypeVariable(String name,
                                            FieldTypeSignature[] bounds){
        return TypeVariableImpl.make(getDecl(), name, bounds, this);
    }

    public WildcardType makeWildcard(FieldTypeSignature[] ubs,
                                     FieldTypeSignature[] lbs) {
        return WildcardTypeImpl.make(ubs, lbs, this);
    }

    public ParameterizedType makeParameterizedType(Type declaration,
                                                   Type[] typeArgs,
                                                   Type owner) {
        return ParameterizedTypeImpl.make((Class<?>) declaration,
                                          typeArgs, owner);
    }

    public TypeVariable<?> findTypeVariable(String name){
        return getScope().lookup(name);
    }

    public Type makeNamedType(String name){
        try {return Class.forName(name, false, // don't initialize
                                  getDeclsLoader());}
        catch (ClassNotFoundException c) {
            throw new TypeNotPresentException(name, c);
        }
    }

    public Type makeArrayType(Type componentType){
        return GenericArrayTypeImpl.make(componentType);
    }

    public Type makeByte(){return byte.class;}
    public Type makeBool(){return boolean.class;}
    public Type makeShort(){return short.class;}
    public Type makeChar(){return char.class;}
    public Type makeInt(){return int.class;}
    public Type makeLong(){return long.class;}
    public Type makeFloat(){return float.class;}
    public Type makeDouble(){return double.class;}

    public Type makeVoid(){return void.class;}
}
