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
package gap.jac.tools;

import java.util.Locale;

/**
 * Interface for diagnostics from tools.  A diagnostic usually reports
 * a problem at a specific position in a source file.  However, not
 * all diagnostics are associated with a position or a file.
 *
 * <p>A position is a zero-based character offset from the beginning of
 * a file.  Negative values (except {@link #NOPOS}) are not valid
 * positions.
 *
 * <p>Line and column numbers begin at 1.  Negative values (except
 * {@link #NOPOS}) and 0 are not valid line or column numbers.
 *
 * @param <S> the type of source object used by this diagnostic
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface Diagnostic<S> {

    /**
     * Kinds of diagnostics, for example, error or warning.
     */
    enum Kind {
        /**
         * Problem which prevents the tool's normal completion.
         */
        ERROR,
        /**
         * Problem which does not usually prevent the tool from
         * completing normally.
         */
        WARNING,
        /**
         * Problem similar to a warning, but is mandated by the tool's
         * specification.  For example, the Java&trade; Language
         * Specification, 3rd Ed. mandates warnings on certain
         * unchecked operations and the use of deprecated methods.
         */
        MANDATORY_WARNING,
        /**
         * Informative message from the tool.
         */
        NOTE,
        /**
         * Diagnostic which does not fit within the other kinds.
         */
        OTHER,
    }

    /**
     * Used to signal that no position is available.
     */
    public final static long NOPOS = -1;

    /**
     * Gets the kind of this diagnostic, for example, error or
     * warning.
     * @return the kind of this diagnostic
     */
    Kind getKind();

    /**
     * Gets the source object associated with this diagnostic.
     *
     * @return the source object associated with this diagnostic.
     * {@code null} if no source object is associated with the
     * diagnostic.
     */
    S getSource();

    /**
     * Gets a character offset from the beginning of the source object
     * associated with this diagnostic that indicates the location of
     * the problem.  In addition, the following must be true:
     *
     * <p>{@code getStartPostion() <= getPosition()}
     * <p>{@code getPosition() <= getEndPosition()}
     *
     * @return character offset from beginning of source; {@link
     * #NOPOS} if {@link #getSource()} would return {@code null} or if
     * no location is suitable
     */
    long getPosition();

    /**
     * Gets the character offset from the beginning of the file
     * associated with this diagnostic that indicates the start of the
     * problem.
     *
     * @return offset from beginning of file; {@link #NOPOS} if and
     * only if {@link #getPosition()} returns {@link #NOPOS}
     */
    long getStartPosition();

    /**
     * Gets the character offset from the beginning of the file
     * associated with this diagnostic that indicates the end of the
     * problem.
     *
     * @return offset from beginning of file; {@link #NOPOS} if and
     * only if {@link #getPosition()} returns {@link #NOPOS}
     */
    long getEndPosition();

    /**
     * Gets the line number of the character offset returned by
     * {@linkplain #getPosition()}.
     *
     * @return a line number or {@link #NOPOS} if and only if {@link
     * #getPosition()} returns {@link #NOPOS}
     */
    long getLineNumber();

    /**
     * Gets the column number of the character offset returned by
     * {@linkplain #getPosition()}.
     *
     * @return a column number or {@link #NOPOS} if and only if {@link
     * #getPosition()} returns {@link #NOPOS}
     */
    long getColumnNumber();

    /**
     * Gets a diagnostic code indicating the type of diagnostic.  The
     * code is implementation-dependent and might be {@code null}.
     *
     * @return a diagnostic code
     */
    String getCode();

    /**
     * Gets a localized message for the given locale.  The actual
     * message is implementation-dependent.  If the locale is {@code
     * null} use the default locale.
     *
     * @param locale a locale; might be {@code null}
     * @return a localized message
     */
    String getMessage(Locale locale);
}
