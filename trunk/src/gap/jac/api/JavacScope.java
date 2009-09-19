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
package gap.jac.api;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Iterator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import gap.jac.tools.JavaFileObject;

import gap.jac.source.tree.Tree;
import gap.jac.source.util.SourcePositions;
import gap.jac.source.util.TreePath;
import gap.jac.source.util.Trees;
import gap.jac.code.Scope;
import gap.jac.code.Symbol.ClassSymbol;
import gap.jac.comp.Attr;
import gap.jac.comp.AttrContext;
import gap.jac.comp.Enter;
import gap.jac.comp.Env;
import gap.jac.comp.MemberEnter;
import gap.jac.comp.Resolve;
import gap.jac.tree.JCTree.JCClassDecl;
import gap.jac.tree.JCTree.JCCompilationUnit;
import gap.jac.tree.JCTree.JCExpression;
import gap.jac.tree.JCTree.JCMethodDecl;
import gap.jac.tree.JCTree.JCVariableDecl;
import gap.jac.tree.JCTree;
import gap.jac.tree.TreeCopier;
import gap.jac.tree.TreeInfo;
import gap.jac.tree.TreeMaker;
import gap.jac.util.Context;
import gap.jac.util.List;
import gap.jac.util.Log;

import static gap.jac.source.tree.Tree.Kind.*;


/**
 * Provides an implementation of Scope.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 *
 * @author Jonathan Gibbons;
 */
public class JavacScope implements gap.jac.source.tree.Scope {
    protected final Env<AttrContext> env;

    /** Creates a new instance of JavacScope */
    JavacScope(Env<AttrContext> env) {
        env.getClass(); // null-check
        this.env = env;
    }

    public JavacScope getEnclosingScope() {
        if (env.outer != null && env.outer != env)
            return  new JavacScope(env.outer);
        else {
            // synthesize an outermost "star-import" scope
            return new JavacScope(env) {
                public boolean isStarImportScope() {
                    return true;
                }
                public JavacScope getEnclosingScope() {
                    return null;
                }
                public Iterable<? extends Element> getLocalElements() {
                    return env.toplevel.starImportScope.getElements();
                }
            };
        }
    }

    public TypeElement getEnclosingClass() {
        // hide the dummy class that javac uses to enclose the top level declarations
        return (env.outer == null || env.outer == env ? null : env.enclClass.sym);
    }

    public ExecutableElement getEnclosingMethod() {
        return (env.enclMethod == null ? null : env.enclMethod.sym);
    }

    public Iterable<? extends Element> getLocalElements() {
        return env.info.getLocalElements();
    }

    public Env<AttrContext> getEnv() {
        return env;
    }

    public boolean isStarImportScope() {
        return false;
    }

    public boolean equals(Object other) {
        if (other instanceof JavacScope) {
            JavacScope s = (JavacScope) other;
            return (env.equals(s.env)
                && isStarImportScope() == s.isStarImportScope());
        } else
            return false;
    }

    public int hashCode() {
        return env.hashCode() + (isStarImportScope() ? 1 : 0);
    }

    public String toString() {
        return "JavacScope[env=" + env + ",starImport=" + isStarImportScope() + "]";
    }
}
