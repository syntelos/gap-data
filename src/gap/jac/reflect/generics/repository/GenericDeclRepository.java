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

import java.lang.reflect.TypeVariable;
import gap.jac.reflect.generics.factory.GenericsFactory;
import gap.jac.reflect.generics.tree.FormalTypeParameter;
import gap.jac.reflect.generics.tree.Signature;
import gap.jac.reflect.generics.visitor.Reifier;



/**
 * This class represents the generic type information for a generic
 * declaration.
 * The code is not dependent on a particular reflective implementation.
 * It is designed to be used unchanged by at least core reflection and JDI.
 */
public abstract class GenericDeclRepository<S extends Signature>
    extends AbstractRepository<S> {

    private TypeVariable[] typeParams; // caches the formal type parameters

    protected GenericDeclRepository(String rawSig, GenericsFactory f) {
        super(rawSig, f);
    }

    // public API
 /*
 * When queried for a particular piece of type information, the
 * general pattern is to consult the corresponding cached value.
 * If the corresponding field is non-null, it is returned.
 * If not, it is created lazily. This is done by selecting the appropriate
 * part of the tree and transforming it into a reflective object
 * using a visitor.
 * a visitor, which is created by feeding it the factory
 * with which the repository was created.
 */

    /**
     * Return the formal type parameters of this generic declaration.
     * @return the formal type parameters of this generic declaration
     */
    public TypeVariable/*<?>*/[] getTypeParameters(){
        if (typeParams == null) { // lazily initialize type parameters
            // first, extract type parameter subtree(s) from AST
            FormalTypeParameter[] ftps = getTree().getFormalTypeParameters();
            // create array to store reified subtree(s)
            TypeVariable[] tps = new TypeVariable[ftps.length];
            // reify all subtrees
            for (int i = 0; i < ftps.length; i++) {
                Reifier r = getReifier(); // obtain visitor
                ftps[i].accept(r); // reify subtree
                // extract result from visitor and store it
                tps[i] = (TypeVariable<?>) r.getResult();
            }
            typeParams = tps; // cache overall result
        }
        return typeParams.clone(); // return cached result
    }
}