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
package gap.odl;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * 
 * @see Class
 * @author jdp
 */
public final class Child
    extends Object
    implements gap.data.HasName
{

    public final String name;


    public Child(String name)
        throws IOException, Syntax
    {
        super();
        if (null != name){
            this.name = name;
        }
        else 
            throw new Jump();
    }


    public String getName(){
        return this.name;
    }
}
