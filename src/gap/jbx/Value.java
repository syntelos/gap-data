/*
 * Gap Data
 * Copyright (C) 2009 John Pritchard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package gap.jbx;

/**
 * WTKX rvalue string.  
 * 
 * The value returned by 'toString' is parsed by 'fromString'.  This
 * string may not contain the characters ':' or ';'.  
 * 
 * These strings are compatible with CSS right hand side values.
 * 
 * @see wtkx.io.CSS
 * @see http://www.w3.org/TR/css-style-attr
 * 
 * @author jdp
 */
public interface Value {
    /**
     * For CSS named groups including pseudo elements.
     */
    public interface Via {
        /**
         * A get or create operation may return null, as for 'name'
         * unrecognized.  
         * 
         * When the rvalue is in class {@link Value}, then a
         * recognized name should always return a value -- as for
         * parsing.
         * 
         * Otherwise, when the rvalue is a number or color or font --
         * for example -- then null is appropriate when the class has
         * no reasonable state or context for the representation of
         * the named value.
         */
        public Object forName(String name);
    }

    public void fromString(String value);

    public String toString();
}
