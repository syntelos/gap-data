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
package gap.util;

import java.io.PrintStream;

/**
 * Index elements of a list by a key for each element.  List elements
 * are represented by their integer index into the list, and their key
 * objects.
 * 
 * @author jdp
 */
public final class Index<K extends java.lang.Comparable>
    extends lxl.Index<K>
{


    public Index(int size){
        super(size);
    }
    public Index(Page page){
        this(page.count);
    }

}
