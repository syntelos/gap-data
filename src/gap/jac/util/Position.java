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
package gap.jac.util;

import java.util.BitSet;
import static gap.jac.util.LayoutCharacters.*;

/** A class that defines source code positions as simple character
 *  offsets from the beginning of the file. The first character
 *  is at position 0.
 *
 *  Support is also provided for (line,column) coordinates, but tab
 *  expansion is optional and no Unicode excape translation is considered.
 *  The first character is at location (1,1).
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class Position {
    public static final int NOPOS        = -1;

    public static final int FIRSTPOS     = 0;
    public static final int FIRSTLINE    = 1;
    public static final int FIRSTCOLUMN  = 1;

    public static final int LINESHIFT    = 10;
    public static final int MAXCOLUMN    = (1<<LINESHIFT) - 1;
    public static final int MAXLINE      = (1<<(Integer.SIZE-LINESHIFT)) - 1;

    public static final int MAXPOS       = Integer.MAX_VALUE;

    /**
     * This is class is not supposed to be instantiated.
     */
    private Position() {}

    /** A two-way map between line/column numbers and positions,
     *  derived from a scan done at creation time.  Tab expansion is
     *  optionally supported via a character map.  Text content
     *  is not retained.
     *<p>
     *  Notes:  The first character position FIRSTPOS is at
     *  (FIRSTLINE,FIRSTCOLUMN).  No account is taken of Unicode escapes.
     *
     * @param   src         Source characters
     * @param   max         Number of characters to read
     * @param   expandTabs  If true, expand tabs when calculating columns
     */
    public static LineMap makeLineMap(char[] src, int max, boolean expandTabs) {
        LineMapImpl lineMap = expandTabs ?
            new LineTabMapImpl(max) : new LineMapImpl();
        lineMap.build(src, max);
        return lineMap;
    }

    /** Encode line and column numbers in an integer as:
     *  line-number << LINESHIFT + column-number
     *  {@link Position.NOPOS represents an undefined position.
     *
     * @param  line  number of line (first is 1)
     * @param  col   number of character on line (first is 1)
     * @return       an encoded position or {@link Position.NOPOS
     *               if the line or column number is too big to
     *               represent in the encoded format
     * @throws IllegalArgumentException if line or col is less than 1
     */
    public static int encodePosition(int line, int col) {
        if (line < 1)
            throw new IllegalArgumentException("line must be greater than 0");
        if (col < 1)
            throw new IllegalArgumentException("column must be greater than 0");

        if (line > MAXLINE || col > MAXCOLUMN) {
            return NOPOS;
        }
        return (line << LINESHIFT) + col;
    }

    public static interface LineMap extends gap.jac.source.tree.LineMap {
        /** Find the start position of a line.
         *
         * @param line number of line (first is 1)
         * @return     position of first character in line
         * @throws  ArrayIndexOutOfBoundsException
         *           if <tt>lineNumber < 1</tt>
         *           if <tt>lineNumber > no. of lines</tt>
         */
        int getStartPosition(int line);

        /** Find the position corresponding to a (line,column).
         *
         * @param   line    number of line (first is 1)
         * @param   column  number of character on line (first is 1)
         *
         * @return  position of character
         * @throws  ArrayIndexOutOfBoundsException
         *           if <tt>line < 1</tt>
         *           if <tt>line > no. of lines</tt>
         */
        int getPosition(int line, int column);

        /** Find the line containing a position; a line termination
         * character is on the line it terminates.
         *
         * @param   pos  character offset of the position
         * @return the line number on which pos occurs (first line is 1)
         */
        int getLineNumber(int pos);

        /** Find the column for a character position.
         *  Note:  this method does not handle tab expansion.
         *  If tab expansion is needed, use a LineTabMap instead.
         *
         * @param  pos   character offset of the position
         * @return       the column number at which pos occurs
         */
        int getColumnNumber(int pos);
    }

    static class LineMapImpl implements LineMap {
        protected int[] startPosition; // start position of each line

        protected LineMapImpl() {}

        protected void build(char[] src, int max) {
            int c = 0;
            int i = 0;
            int[] linebuf = new int[max];
            while (i < max) {
                linebuf[c++] = i;
                do {
                    char ch = src[i];
                    if (ch == '\r' || ch == '\n') {
                        if (ch == '\r' && (i+1) < max && src[i+1] == '\n')
                            i += 2;
                        else
                            ++i;
                        break;
                    }
                    else if (ch == '\t')
                        setTabPosition(i);
                } while (++i < max);
            }
            this.startPosition = new int[c];
            System.arraycopy(linebuf, 0, startPosition, 0, c);
        }

        public int getStartPosition(int line) {
            return startPosition[line - FIRSTLINE];
        }

        public long getStartPosition(long line) {
            return getStartPosition(longToInt(line));
        }

        public int getPosition(int line, int column) {
            return startPosition[line - FIRSTLINE] + column - FIRSTCOLUMN;
        }

        public long getPosition(long line, long column) {
            return getPosition(longToInt(line), longToInt(column));
        }

        // Cache of last line number lookup
        private int lastPosition = Position.FIRSTPOS;
        private int lastLine = Position.FIRSTLINE;

        public int getLineNumber(int pos) {
            if (pos == lastPosition) {
                return lastLine;
            }
            lastPosition = pos;

            int low = 0;
            int high = startPosition.length-1;
            while (low <= high) {
                int mid = (low + high) >> 1;
                int midVal = startPosition[mid];

                if (midVal < pos)
                    low = mid + 1;
                else if (midVal > pos)
                    high = mid - 1;
                else {
                    lastLine = mid + 1; // pos is at beginning of this line
                    return lastLine;
                }
            }
            lastLine = low;
            return lastLine;  // pos is on this line
        }

        public long getLineNumber(long pos) {
            return getLineNumber(longToInt(pos));
        }

        public int getColumnNumber(int pos) {
            return pos - startPosition[getLineNumber(pos) - FIRSTLINE] + FIRSTCOLUMN;
        }

        public long getColumnNumber(long pos) {
            return getColumnNumber(longToInt(pos));
        }

        private static int longToInt(long longValue) {
            int intValue = (int)longValue;
            if (intValue != longValue)
                throw new IndexOutOfBoundsException();
            return intValue;
        }

        protected void setTabPosition(int offset) {}
    }

    /**
     * A LineMap that handles tab expansion correctly.  The cost is
     * an additional bit per character in the source array.
     */
    public static class LineTabMapImpl extends LineMapImpl {
        private BitSet tabMap;       // bits set for tab positions.

        public LineTabMapImpl(int max) {
            super();
            tabMap = new BitSet(max);
        }

        protected void setTabPosition(int offset) {
            tabMap.set(offset);
        }

        public int getColumnNumber(int pos) {
            int lineStart = startPosition[getLineNumber(pos) - FIRSTLINE];
            int column = 0;
            for (int bp = lineStart; bp < pos; bp++) {
                if (tabMap.get(bp))
                    column = (column / TabInc * TabInc) + TabInc;
                else
                    column++;
            }
            return column + FIRSTCOLUMN;
        }

        public int getPosition(int line, int column) {
            int pos = startPosition[line - FIRSTLINE];
            column -= FIRSTCOLUMN;
            int col = 0;
            while (col < column) {
                pos++;
                if (tabMap.get(pos))
                    col = (col / TabInc * TabInc) + TabInc;
                else
                    col++;
            }
            return pos;
        }
    }
}