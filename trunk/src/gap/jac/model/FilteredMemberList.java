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
package gap.jac.model;

import gap.jac.util.*;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import gap.jac.code.Scope;
import gap.jac.code.Symbol;

import static gap.jac.code.Flags.*;

/**
 * Utility to construct a view of a symbol's members,
 * filtering out unwanted elements such as synthetic ones.
 * This view is most efficiently accessed through its iterator() method.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 * you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
public class FilteredMemberList extends AbstractList<Symbol> {

    private final Scope scope;

    public FilteredMemberList(Scope scope) {
        this.scope = scope;
    }

    public int size() {
        int cnt = 0;
        for (Scope.Entry e = scope.elems; e != null; e = e.sibling) {
            if (!unwanted(e.sym))
                cnt++;
        }
        return cnt;
    }

    public Symbol get(int index) {
        for (Scope.Entry e = scope.elems; e != null; e = e.sibling) {
            if (!unwanted(e.sym) && (index-- == 0))
                return e.sym;
        }
        throw new IndexOutOfBoundsException();
    }

    // A more efficient implementation than AbstractList's.
    public Iterator<Symbol> iterator() {
        return new Iterator<Symbol>() {

            /** The next entry to examine, or null if none. */
            private Scope.Entry nextEntry = scope.elems;

            private boolean hasNextForSure = false;

            public boolean hasNext() {
                if (hasNextForSure) {
                    return true;
                }
                while (nextEntry != null && unwanted(nextEntry.sym)) {
                    nextEntry = nextEntry.sibling;
                }
                hasNextForSure = (nextEntry != null);
                return hasNextForSure;
            }

            public Symbol next() {
                if (hasNext()) {
                    Symbol result = nextEntry.sym;
                    nextEntry = nextEntry.sibling;
                    hasNextForSure = false;
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Tests whether this is a symbol that should never be seen by
     * clients, such as a synthetic class.  Returns true for null.
     */
    private static boolean unwanted(Symbol s) {
        return s == null  ||  (s.flags() & SYNTHETIC) != 0;
    }
}