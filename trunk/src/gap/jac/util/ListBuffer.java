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

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** A class for constructing lists by appending elements. Modelled after
 *  java.lang.StringBuffer.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class ListBuffer<A> implements Collection<A> {

    public static <T> ListBuffer<T> lb() {
        return new ListBuffer<T>();
    }

    /** The list of elements of this buffer.
     */
    public List<A> elems;

    /** A pointer pointing to the last, sentinel element of `elems'.
     */
    public List<A> last;

    /** The number of element in this buffer.
     */
    public int count;

    /** Has a list been created from this buffer yet?
     */
    public boolean shared;

    /** Create a new initially empty list buffer.
     */
    public ListBuffer() {
        clear();
    }

    public final void clear() {
        this.elems = new List<A>(null,null);
        this.last = this.elems;
        count = 0;
        shared = false;
    }

    /** Return the number of elements in this buffer.
     */
    public int length() {
        return count;
    }
    public int size() {
        return count;
    }

    /** Is buffer empty?
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /** Is buffer not empty?
     */
    public boolean nonEmpty() {
        return count != 0;
    }

    /** Copy list and sets last.
     */
    private void copy() {
        List<A> p = elems = new List<A>(elems.head, elems.tail);
        while (true) {
            List<A> tail = p.tail;
            if (tail == null) break;
            tail = new List<A>(tail.head, tail.tail);
            p.setTail(tail);
            p = tail;
        }
        last = p;
        shared = false;
    }

    /** Prepend an element to buffer.
     */
    public ListBuffer<A> prepend(A x) {
        elems = elems.prepend(x);
        count++;
        return this;
    }

    /** Append an element to buffer.
     */
    public ListBuffer<A> append(A x) {
        if (shared) copy();
        last.head = x;
        last.setTail(new List<A>(null,null));
        last = last.tail;
        count++;
        return this;
    }

    /** Append all elements in a list to buffer.
     */
    public ListBuffer<A> appendList(List<A> xs) {
        while (xs.nonEmpty()) {
            append(xs.head);
            xs = xs.tail;
        }
        return this;
    }

    /** Append all elements in a list to buffer.
     */
    public ListBuffer<A> appendList(ListBuffer<A> xs) {
        return appendList(xs.toList());
    }

    /** Append all elements in an array to buffer.
     */
    public ListBuffer<A> appendArray(A[] xs) {
        for (int i = 0; i < xs.length; i++) {
            append(xs[i]);
        }
        return this;
    }

    /** Convert buffer to a list of all its elements.
     */
    public List<A> toList() {
        shared = true;
        return elems;
    }

    /** Does the list contain the specified element?
     */
    public boolean contains(Object x) {
        return elems.contains(x);
    }

    /** Convert buffer to an array
     */
    public <T> T[] toArray(T[] vec) {
        return elems.toArray(vec);
    }
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    /** The first element in this buffer.
     */
    public A first() {
        return elems.head;
    }

    /** Remove the first element in this buffer.
     */
    public void remove() {
        if (elems != last) {
            elems = elems.tail;
            count--;
        }
    }

    /** Return first element in this buffer and remove
     */
    public A next() {
        A x = elems.head;
        remove();
        return x;
    }

    /** An enumeration of all elements in this buffer.
     */
    public Iterator<A> iterator() {
        return new Iterator<A>() {
            List<A> elems = ListBuffer.this.elems;
            public boolean hasNext() {
                return elems != last;
            }
            public A next() {
                if (elems == last)
                    throw new NoSuchElementException();
                A elem = elems.head;
                elems = elems.tail;
                return elem;
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean add(A a) {
        throw new UnsupportedOperationException();
    }
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    public boolean addAll(Collection<? extends A> c) {
        throw new UnsupportedOperationException();
    }
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
