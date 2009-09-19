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

import gap.jac.util.*;
import gap.jac.util.Position.LineMap;

/**
 * The lexical analyzer maps an input stream consisting of ASCII
 * characters and Unicode escapes into a token sequence.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
public interface Lexer {

    /**
     * Has a @deprecated been encountered in last doc comment?
     * This needs to be reset by client with resetDeprecatedFlag.
     */
    boolean deprecatedFlag();

    void resetDeprecatedFlag();

    /**
     * Returns the documentation string of the current token.
     */
    String docComment();

    /**
     * Return the last character position of the current token.
     */
    int endPos();

    /**
     * Return the position where a lexical error occurred;
     */
    int errPos();

    /**
     * Set the position where a lexical error occurred;
     */
    void errPos(int pos);

    /**
     * Build a map for translating between line numbers and
     * positions in the input.
     *
     * @return a LineMap
     */
    LineMap getLineMap();

    /**
     * Returns a copy of the input buffer, up to its inputLength.
     * Unicode escape sequences are not translated.
     */
    char[] getRawCharacters();

    /**
     * Returns a copy of a character array subset of the input buffer.
     * The returned array begins at the <code>beginIndex</code> and
     * extends to the character at index <code>endIndex - 1</code>.
     * Thus the length of the substring is <code>endIndex-beginIndex</code>.
     * This behavior is like
     * <code>String.substring(beginIndex, endIndex)</code>.
     * Unicode escape sequences are not translated.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex the ending index, exclusive.
     * @throws IndexOutOfBounds if either offset is outside of the
     *         array bounds
     */
    char[] getRawCharacters(int beginIndex, int endIndex);

    /**
     * Return the name of an identifier or token for the current token.
     */
    Name name();

    /**
     * Read token.
     */
    void nextToken();

    /**
     * Return the current token's position: a 0-based
     *  offset from beginning of the raw input stream
     *  (before unicode translation)
     */
    int pos();

    /**
     * Return the last character position of the previous token.
     */
    int prevEndPos();

    /**
     * Return the radix of a numeric literal token.
     */
    int radix();

    /**
     * The value of a literal token, recorded as a string.
     *  For integers, leading 0x and 'l' suffixes are suppressed.
     */
    String stringVal();

    /**
     * Return the current token, set by nextToken().
     */
    Token token();

    /**
     * Sets the current token.
     */
    void token(Token token);
}
