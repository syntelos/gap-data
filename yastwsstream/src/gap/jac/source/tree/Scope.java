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
package gap.jac.source.tree;

import gap.jac.source.tree.Tree;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

/**
 * Interface for determining locally available program elements, such as
 * local variables and imports.
 * Upon creation, a Scope is associated with a given program position;
 * for example, a {@linkplain Tree tree node}. This position may be used to
 * infer an enclosing method and/or class.
 *
 * <p>A Scope does not itself contain the details of the elements corresponding
 * to the parameters, methods and fields of the methods and classes containing
 * its position. However, these elements can be determined from the enclosing
 * elements.
 *
 * <p>Scopes may be contained in an enclosing scope. The outermost scope contains
 * those elements available via "star import" declarations; the scope within that
 * contains the top level elements of the compilation unit, including any named
 * imports.
 *
 * @since 1.6
 */
public interface Scope {
    /**
     * Returns the enclosing scope.
     */
    public Scope getEnclosingScope();

    /**
     * Returns the innermost type element containing the position of this scope
     */
    public TypeElement getEnclosingClass();

    /**
     * Returns the innermost executable element containing the position of this scope.
     */
    public ExecutableElement getEnclosingMethod();

    /**
     * Returns the elements directly contained in this scope.
     */
    public Iterable<? extends Element> getLocalElements();
}
