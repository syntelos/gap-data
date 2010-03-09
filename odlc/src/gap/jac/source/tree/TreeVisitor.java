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

/**
 * A visitor of trees, in the style of the visitor design pattern.
 * Classes implementing this interface are used to operate
 * on a tree when the kind of tree is unknown at compile time.
 * When a visitor is passed to an tree's {@link Tree#accept
 * accept} method, the <tt>visit<i>XYZ</i></tt> method most applicable
 * to that tree is invoked.
 *
 * <p> Classes implementing this interface may or may not throw a
 * {@code NullPointerException} if the additional parameter {@code p}
 * is {@code null}; see documentation of the implementing class for
 * details.
 *
 * <p> <b>WARNING:</b> It is possible that methods will be added to
 * this interface to accommodate new, currently unknown, language
 * structures added to future versions of the Java&trade; programming
 * language.  Therefore, visitor classes directly implementing this
 * interface may be source incompatible with future versions of the
 * platform.
 *
 * @param <R> the return type of this visitor's methods.  Use {@link
 *            Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter.
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 *
 * @since 1.6
 */
public interface TreeVisitor<R,P> {
    R visitAnnotation(AnnotationTree node, P p);
    R visitMethodInvocation(MethodInvocationTree node, P p);
    R visitAssert(AssertTree node, P p);
    R visitAssignment(AssignmentTree node, P p);
    R visitCompoundAssignment(CompoundAssignmentTree node, P p);
    R visitBinary(BinaryTree node, P p);
    R visitBlock(BlockTree node, P p);
    R visitBreak(BreakTree node, P p);
    R visitCase(CaseTree node, P p);
    R visitCatch(CatchTree node, P p);
    R visitClass(ClassTree node, P p);
    R visitConditionalExpression(ConditionalExpressionTree node, P p);
    R visitContinue(ContinueTree node, P p);
    R visitDoWhileLoop(DoWhileLoopTree node, P p);
    R visitErroneous(ErroneousTree node, P p);
    R visitExpressionStatement(ExpressionStatementTree node, P p);
    R visitEnhancedForLoop(EnhancedForLoopTree node, P p);
    R visitForLoop(ForLoopTree node, P p);
    R visitIdentifier(IdentifierTree node, P p);
    R visitIf(IfTree node, P p);
    R visitImport(ImportTree node, P p);
    R visitArrayAccess(ArrayAccessTree node, P p);
    R visitLabeledStatement(LabeledStatementTree node, P p);
    R visitLiteral(LiteralTree node, P p);
    R visitMethod(MethodTree node, P p);
    R visitModifiers(ModifiersTree node, P p);
    R visitNewArray(NewArrayTree node, P p);
    R visitNewClass(NewClassTree node, P p);
    R visitParenthesized(ParenthesizedTree node, P p);
    R visitReturn(ReturnTree node, P p);
    R visitMemberSelect(MemberSelectTree node, P p);
    R visitEmptyStatement(EmptyStatementTree node, P p);
    R visitSwitch(SwitchTree node, P p);
    R visitSynchronized(SynchronizedTree node, P p);
    R visitThrow(ThrowTree node, P p);
    R visitCompilationUnit(CompilationUnitTree node, P p);
    R visitTry(TryTree node, P p);
    R visitParameterizedType(ParameterizedTypeTree node, P p);
    R visitArrayType(ArrayTypeTree node, P p);
    R visitTypeCast(TypeCastTree node, P p);
    R visitPrimitiveType(PrimitiveTypeTree node, P p);
    R visitTypeParameter(TypeParameterTree node, P p);
    R visitInstanceOf(InstanceOfTree node, P p);
    R visitUnary(UnaryTree node, P p);
    R visitVariable(VariableTree node, P p);
    R visitWhileLoop(WhileLoopTree node, P p);
    R visitWildcard(WildcardTree node, P p);
    R visitOther(Tree node, P p);
}
