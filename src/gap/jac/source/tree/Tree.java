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
 * Common interface for all nodes in an abstract syntax tree.
 *
 * <p><b>WARNING:</b> This interface and its sub-interfaces are
 * subject to change as the Java&trade; programming language evolves.
 * These interfaces are implemented by Sun's Java compiler (javac)
 * and should not be implemented either directly or indirectly by
 * other applications.
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 *
 * @since 1.6
 */
public interface Tree {

    /**
     * Enumerates all kinds of trees.
     */
    public enum Kind {
        /**
         * Used for instances of {@link AnnotationTree}.
         */
        ANNOTATION(AnnotationTree.class),

        /**
         * Used for instances of {@link ArrayAccessTree}.
         */
        ARRAY_ACCESS(ArrayAccessTree.class),

        /**
         * Used for instances of {@link ArrayTypeTree}.
         */
        ARRAY_TYPE(ArrayTypeTree.class),

        /**
         * Used for instances of {@link AssertTree}.
         */
        ASSERT(AssertTree.class),

        /**
         * Used for instances of {@link AssignmentTree}.
         */
        ASSIGNMENT(AssignmentTree.class),

        /**
         * Used for instances of {@link BlockTree}.
         */
        BLOCK(BlockTree.class),

        /**
         * Used for instances of {@link BreakTree}.
         */
        BREAK(BreakTree.class),

        /**
         * Used for instances of {@link CaseTree}.
         */
        CASE(CaseTree.class),

        /**
         * Used for instances of {@link CatchTree}.
         */
        CATCH(CatchTree.class),

        /**
         * Used for instances of {@link ClassTree}.
         */
        CLASS(ClassTree.class),

        /**
         * Used for instances of {@link CompilationUnitTree}.
         */
        COMPILATION_UNIT(CompilationUnitTree.class),

        /**
         * Used for instances of {@link ConditionalExpressionTree}.
         */
        CONDITIONAL_EXPRESSION(ConditionalExpressionTree.class),

        /**
         * Used for instances of {@link ContinueTree}.
         */
        CONTINUE(ContinueTree.class),

        /**
         * Used for instances of {@link DoWhileLoopTree}.
         */
        DO_WHILE_LOOP(DoWhileLoopTree.class),

        /**
         * Used for instances of {@link EnhancedForLoopTree}.
         */
        ENHANCED_FOR_LOOP(EnhancedForLoopTree.class),

        /**
         * Used for instances of {@link ExpressionStatementTree}.
         */
        EXPRESSION_STATEMENT(ExpressionStatementTree.class),

        /**
         * Used for instances of {@link MemberSelectTree}.
         */
        MEMBER_SELECT(MemberSelectTree.class),

        /**
         * Used for instances of {@link ForLoopTree}.
         */
        FOR_LOOP(ForLoopTree.class),

        /**
         * Used for instances of {@link IdentifierTree}.
         */
        IDENTIFIER(IdentifierTree.class),

        /**
         * Used for instances of {@link IfTree}.
         */
        IF(IfTree.class),

        /**
         * Used for instances of {@link ImportTree}.
         */
        IMPORT(ImportTree.class),

        /**
         * Used for instances of {@link InstanceOfTree}.
         */
        INSTANCE_OF(InstanceOfTree.class),

        /**
         * Used for instances of {@link LabeledStatementTree}.
         */
        LABELED_STATEMENT(LabeledStatementTree.class),

        /**
         * Used for instances of {@link MethodTree}.
         */
        METHOD(MethodTree.class),

        /**
         * Used for instances of {@link MethodInvocationTree}.
         */
        METHOD_INVOCATION(MethodInvocationTree.class),

        /**
         * Used for instances of {@link ModifiersTree}.
         */
        MODIFIERS(ModifiersTree.class),

        /**
         * Used for instances of {@link NewArrayTree}.
         */
        NEW_ARRAY(NewArrayTree.class),

        /**
         * Used for instances of {@link NewClassTree}.
         */
        NEW_CLASS(NewClassTree.class),

        /**
         * Used for instances of {@link ParenthesizedTree}.
         */
        PARENTHESIZED(ParenthesizedTree.class),

        /**
         * Used for instances of {@link PrimitiveTypeTree}.
         */
        PRIMITIVE_TYPE(PrimitiveTypeTree.class),

        /**
         * Used for instances of {@link ReturnTree}.
         */
        RETURN(ReturnTree.class),

        /**
         * Used for instances of {@link EmptyStatementTree}.
         */
        EMPTY_STATEMENT(EmptyStatementTree.class),

        /**
         * Used for instances of {@link SwitchTree}.
         */
        SWITCH(SwitchTree.class),

        /**
         * Used for instances of {@link SynchronizedTree}.
         */
        SYNCHRONIZED(SynchronizedTree.class),

        /**
         * Used for instances of {@link ThrowTree}.
         */
        THROW(ThrowTree.class),

        /**
         * Used for instances of {@link TryTree}.
         */
        TRY(TryTree.class),

        /**
         * Used for instances of {@link ParameterizedTypeTree}.
         */
        PARAMETERIZED_TYPE(ParameterizedTypeTree.class),

        /**
         * Used for instances of {@link TypeCastTree}.
         */
        TYPE_CAST(TypeCastTree.class),

        /**
         * Used for instances of {@link TypeParameterTree}.
         */
        TYPE_PARAMETER(TypeParameterTree.class),

        /**
         * Used for instances of {@link VariableTree}.
         */
        VARIABLE(VariableTree.class),

        /**
         * Used for instances of {@link WhileLoopTree}.
         */
        WHILE_LOOP(WhileLoopTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing postfix
         * increment operator {@code ++}.
         */
        POSTFIX_INCREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing postfix
         * decrement operator {@code --}.
         */
        POSTFIX_DECREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing prefix
         * increment operator {@code ++}.
         */
        PREFIX_INCREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing prefix
         * decrement operator {@code --}.
         */
        PREFIX_DECREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing unary plus
         * operator {@code +}.
         */
        UNARY_PLUS(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing unary minus
         * operator {@code -}.
         */
        UNARY_MINUS(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing bitwise
         * complement operator {@code ~}.
         */
        BITWISE_COMPLEMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing logical
         * complement operator {@code !}.
         */
        LOGICAL_COMPLEMENT(UnaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * multiplication {@code *}.
         */
        MULTIPLY(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * division {@code /}.
         */
        DIVIDE(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * remainder {@code %}.
         */
        REMAINDER(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * addition or string concatenation {@code +}.
         */
        PLUS(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * subtraction {@code -}.
         */
        MINUS(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * left shift {@code <<}.
         */
        LEFT_SHIFT(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * right shift {@code >>}.
         */
        RIGHT_SHIFT(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * unsigned right shift {@code >>>}.
         */
        UNSIGNED_RIGHT_SHIFT(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * less-than {@code <}.
         */
        LESS_THAN(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * greater-than {@code >}.
         */
        GREATER_THAN(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * less-than-equal {@code <=}.
         */
        LESS_THAN_EQUAL(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * greater-than-equal {@code >=}.
         */
        GREATER_THAN_EQUAL(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * equal-to {@code ==}.
         */
        EQUAL_TO(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * not-equal-to {@code !=}.
         */
        NOT_EQUAL_TO(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * bitwise and logical "and" {@code &}.
         */
        AND(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * bitwise and logical "xor" {@code ^}.
         */
        XOR(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * bitwise and logical "or" {@code |}.
         */
        OR(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * conditional-and {@code &&}.
         */
        CONDITIONAL_AND(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * conditional-or {@code ||}.
         */
        CONDITIONAL_OR(BinaryTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * multiplication assignment {@code *=}.
         */
        MULTIPLY_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * division assignment {@code /=}.
         */
        DIVIDE_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * remainder assignment {@code %=}.
         */
        REMAINDER_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * addition or string concatenation assignment {@code +=}.
         */
        PLUS_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * subtraction assignment {@code -=}.
         */
        MINUS_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * left shift assignment {@code <<=}.
         */
        LEFT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * right shift assignment {@code >>=}.
         */
        RIGHT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * unsigned right shift assignment {@code >>>=}.
         */
        UNSIGNED_RIGHT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * bitwise and logical "and" assignment {@code &=}.
         */
        AND_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * bitwise and logical "xor" assignment {@code ^=}.
         */
        XOR_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * bitwise and logical "or" assignment {@code |=}.
         */
        OR_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * an integral literal expression of type {@code int}.
         */
        INT_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * an integral literal expression of type {@code long}.
         */
        LONG_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a floating-point literal expression of type {@code float}.
         */
        FLOAT_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a floating-point literal expression of type {@code double}.
         */
        DOUBLE_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a boolean literal expression of type {@code boolean}.
         */
        BOOLEAN_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a character literal expression of type {@code char}.
         */
        CHAR_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a string literal expression of type {@link String}.
         */
        STRING_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * the use of {@code null}.
         */
        NULL_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link WildcardTree} representing
         * an unbounded wildcard type argument.
         */
        UNBOUNDED_WILDCARD(WildcardTree.class),

        /**
         * Used for instances of {@link WildcardTree} representing
         * an extends bounded wildcard type argument.
         */
        EXTENDS_WILDCARD(WildcardTree.class),

        /**
         * Used for instances of {@link WildcardTree} representing
         * a super bounded wildcard type argument.
         */
        SUPER_WILDCARD(WildcardTree.class),

        /**
         * Used for instances of {@link ErroneousTree}.
         */
        ERRONEOUS(ErroneousTree.class),

        /**
         * An implementation-reserved node. This is the not the node
         * you are looking for.
         */
        OTHER(null);


        Kind(Class<? extends Tree> intf) {
            associatedInterface = intf;
        }

        public Class<? extends Tree> asInterface() {
            return associatedInterface;
        }

        private final Class<? extends Tree> associatedInterface;
    }

    /**
     * Gets the kind of this tree.
     *
     * @return the kind of this tree.
     */
    Kind getKind();

    /**
     * Accept method used to implement the visitor pattern.  The
     * visitor pattern is used to implement operations on trees.
     *
     * @param <R> result type of this operation.
     * @param <D> type of additonal data.
     */
    <R,D> R accept(TreeVisitor<R,D> visitor, D data);
}
