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
package gap.jac.reflect.generics.repository;

import gap.jac.reflect.generics.factory.GenericsFactory;
import gap.jac.reflect.generics.tree.Tree;
import gap.jac.reflect.generics.visitor.Reifier;


/**
 * Abstract superclass for representing the generic type information for
 * a reflective entity.
 * The code is not dependent on a particular reflective implementation.
 * It is designed to be used unchanged by at least core reflection and JDI.
 */
public abstract class AbstractRepository<T extends Tree> {

    // A factory used to produce reflective objects. Provided when the
    //repository is created. Will vary across implementations.
    private GenericsFactory factory;

    private T tree; // the AST for the generic type info

    //accessors
    private GenericsFactory getFactory() { return factory;}

    /**
     * Accessor for <tt>tree</tt>.
     * @return the cached AST this repository holds
     */
    protected T getTree(){ return tree;}

    /**
     * Returns a <tt>Reifier</tt> used to convert parts of the
     * AST into reflective objects.
     * @return  a <tt>Reifier</tt> used to convert parts of the
     * AST into reflective objects
     */
    protected Reifier getReifier(){return Reifier.make(getFactory());}

    /**
     * Constructor. Should only be used by subclasses. Concrete subclasses
     * should make their constructors private and provide public factory
     * methods.
     * @param rawSig - the generic signature of the reflective object
     * that this repository is servicing
     * @param f - a factory that will provide instances of reflective
     * objects when this repository converts its AST
     */
    protected AbstractRepository(String rawSig, GenericsFactory f) {
        tree = parse(rawSig);
        factory = f;
    }

    /**
     * Returns the AST for the genric type info of this entity.
     * @param s - a string representing the generic signature of this
     * entity
     * @return the AST for the generic type info of this entity.
     */
    protected abstract T parse(String s);
}
