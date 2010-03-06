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
package gap.jac.tree;

import gap.jac.util.*;
import gap.jac.tree.JCTree.*;

/** A subclass of Tree.Visitor, this class defines
 *  a general tree scanner pattern. Translation proceeds recursively in
 *  left-to-right order down a tree. There is one visitor method in this class
 *  for every possible kind of tree node.  To obtain a specific
 *  scanner, it suffices to override those visitor methods which
 *  do some interesting work. The scanner class itself takes care of all
 *  navigational aspects.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class TreeScanner extends Visitor {

    /** Visitor method: Scan a single node.
     */
    public void scan(JCTree tree) {
        if(tree!=null) tree.accept(this);
    }

    /** Visitor method: scan a list of nodes.
     */
    public void scan(List<? extends JCTree> trees) {
        if (trees != null)
        for (List<? extends JCTree> l = trees; l.nonEmpty(); l = l.tail)
            scan(l.head);
    }


/* ***************************************************************************
 * Visitor methods
 ****************************************************************************/

    public void visitTopLevel(JCCompilationUnit tree) {
        scan(tree.packageAnnotations);
        scan(tree.pid);
        scan(tree.defs);
    }

    public void visitImport(JCImport tree) {
        scan(tree.qualid);
    }

    public void visitClassDef(JCClassDecl tree) {
        scan(tree.mods);
        scan(tree.typarams);
        scan(tree.extending);
        scan(tree.implementing);
        scan(tree.defs);
    }

    public void visitMethodDef(JCMethodDecl tree) {
        scan(tree.mods);
        scan(tree.restype);
        scan(tree.typarams);
        scan(tree.params);
        scan(tree.thrown);
        scan(tree.body);
    }

    public void visitVarDef(JCVariableDecl tree) {
        scan(tree.mods);
        scan(tree.vartype);
        scan(tree.init);
    }

    public void visitSkip(JCSkip tree) {
    }

    public void visitBlock(JCBlock tree) {
        scan(tree.stats);
    }

    public void visitDoLoop(JCDoWhileLoop tree) {
        scan(tree.body);
        scan(tree.cond);
    }

    public void visitWhileLoop(JCWhileLoop tree) {
        scan(tree.cond);
        scan(tree.body);
    }

    public void visitForLoop(JCForLoop tree) {
        scan(tree.init);
        scan(tree.cond);
        scan(tree.step);
        scan(tree.body);
    }

    public void visitForeachLoop(JCEnhancedForLoop tree) {
        scan(tree.var);
        scan(tree.expr);
        scan(tree.body);
    }

    public void visitLabelled(JCLabeledStatement tree) {
        scan(tree.body);
    }

    public void visitSwitch(JCSwitch tree) {
        scan(tree.selector);
        scan(tree.cases);
    }

    public void visitCase(JCCase tree) {
        scan(tree.pat);
        scan(tree.stats);
    }

    public void visitSynchronized(JCSynchronized tree) {
        scan(tree.lock);
        scan(tree.body);
    }

    public void visitTry(JCTry tree) {
        scan(tree.body);
        scan(tree.catchers);
        scan(tree.finalizer);
    }

    public void visitCatch(JCCatch tree) {
        scan(tree.param);
        scan(tree.body);
    }

    public void visitConditional(JCConditional tree) {
        scan(tree.cond);
        scan(tree.truepart);
        scan(tree.falsepart);
    }

    public void visitIf(JCIf tree) {
        scan(tree.cond);
        scan(tree.thenpart);
        scan(tree.elsepart);
    }

    public void visitExec(JCExpressionStatement tree) {
        scan(tree.expr);
    }

    public void visitBreak(JCBreak tree) {
    }

    public void visitContinue(JCContinue tree) {
    }

    public void visitReturn(JCReturn tree) {
        scan(tree.expr);
    }

    public void visitThrow(JCThrow tree) {
        scan(tree.expr);
    }

    public void visitAssert(JCAssert tree) {
        scan(tree.cond);
        scan(tree.detail);
    }

    public void visitApply(JCMethodInvocation tree) {
        scan(tree.meth);
        scan(tree.args);
    }

    public void visitNewClass(JCNewClass tree) {
        scan(tree.encl);
        scan(tree.clazz);
        scan(tree.args);
        scan(tree.def);
    }

    public void visitNewArray(JCNewArray tree) {
        scan(tree.elemtype);
        scan(tree.dims);
        scan(tree.elems);
    }

    public void visitParens(JCParens tree) {
        scan(tree.expr);
    }

    public void visitAssign(JCAssign tree) {
        scan(tree.lhs);
        scan(tree.rhs);
    }

    public void visitAssignop(JCAssignOp tree) {
        scan(tree.lhs);
        scan(tree.rhs);
    }

    public void visitUnary(JCUnary tree) {
        scan(tree.arg);
    }

    public void visitBinary(JCBinary tree) {
        scan(tree.lhs);
        scan(tree.rhs);
    }

    public void visitTypeCast(JCTypeCast tree) {
        scan(tree.clazz);
        scan(tree.expr);
    }

    public void visitTypeTest(JCInstanceOf tree) {
        scan(tree.expr);
        scan(tree.clazz);
    }

    public void visitIndexed(JCArrayAccess tree) {
        scan(tree.indexed);
        scan(tree.index);
    }

    public void visitSelect(JCFieldAccess tree) {
        scan(tree.selected);
    }

    public void visitIdent(JCIdent tree) {
    }

    public void visitLiteral(JCLiteral tree) {
    }

    public void visitTypeIdent(JCPrimitiveTypeTree tree) {
    }

    public void visitTypeArray(JCArrayTypeTree tree) {
        scan(tree.elemtype);
    }

    public void visitTypeApply(JCTypeApply tree) {
        scan(tree.clazz);
        scan(tree.arguments);
    }

    public void visitTypeParameter(JCTypeParameter tree) {
        scan(tree.bounds);
    }

    @Override
    public void visitWildcard(JCWildcard tree) {
        scan(tree.kind);
        if (tree.inner != null)
            scan(tree.inner);
    }

    @Override
    public void visitTypeBoundKind(TypeBoundKind that) {
    }

    public void visitModifiers(JCModifiers tree) {
        scan(tree.annotations);
    }

    public void visitAnnotation(JCAnnotation tree) {
        scan(tree.annotationType);
        scan(tree.args);
    }

    public void visitErroneous(JCErroneous tree) {
    }

    public void visitLetExpr(LetExpr tree) {
        scan(tree.defs);
        scan(tree.expr);
    }

    public void visitTree(JCTree tree) {
        assert false;
    }
}
