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
package gap.jac.parser;

import java.util.Map;
import java.util.HashMap;
import gap.jac.tree.JCTree;
import gap.jac.tree.TreeInfo;
import gap.jac.util.Position;
import gap.jac.util.List;

import static gap.jac.tree.JCTree.*;

/**
 * This class is similar to Parser except that it stores ending
 * positions for the tree nodes.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b></p>
 */
public class EndPosParser extends Parser {

    public EndPosParser(Factory fac, Lexer S, boolean keepDocComments) {
        super(fac, S, keepDocComments);
        this.S = S;
        endPositions = new HashMap<JCTree,Integer>();
    }

    private Lexer S;

    /** A hashtable to store ending positions
     *  of source ranges indexed by the tree nodes.
     *  Defined only if option flag genEndPos is set.
     */
    Map<JCTree, Integer> endPositions;

    /** {@inheritDoc} */
    @Override
    protected void storeEnd(JCTree tree, int endpos) {
        int errorEndPos = getErrorEndPos();
        endPositions.put(tree, errorEndPos > endpos ? errorEndPos : endpos);
    }

    /** {@inheritDoc} */
    @Override
    protected <T extends JCTree> T to(T t) {
        storeEnd(t, S.endPos());
        return t;
    }

    /** {@inheritDoc} */
    @Override
    protected <T extends JCTree> T toP(T t) {
        storeEnd(t, S.prevEndPos());
        return t;
    }

    @Override
    public JCCompilationUnit compilationUnit() {
        JCCompilationUnit t = super.compilationUnit();
        t.endPositions = endPositions;
        return t;
    }

    /** {@inheritDoc} */
    @Override
    JCExpression parExpression() {
        int pos = S.pos();
        JCExpression t = super.parExpression();
        return toP(F.at(pos).Parens(t));
    }

    /** {@inheritDoc} */
    @Override
    public int getEndPos(JCTree tree) {
        return TreeInfo.getEndPos(tree, endPositions);
    }

}
