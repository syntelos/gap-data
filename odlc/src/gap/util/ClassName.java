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

/**
 * Typed string for {@link Services} 
 */
public final class ClassName
    extends Object
    implements java.lang.Comparable<ClassName>
{


    private final String name;


    public ClassName(String name){
        super();
        if (null != name && 0 != name.length())
            this.name = name;
        else
            throw new IllegalArgumentException(name);
    }


    public String getName(){
        return this.name;
    }
    public String toString(){
        return this.name;
    }
    public int hashCode(){
        return this.name.hashCode();
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (null != that)
            return this.name.equals(that.toString());
        else
            return false;
    }
    public int compareTo(ClassName that){
        return this.name.compareTo(that.getName());
    }
}
