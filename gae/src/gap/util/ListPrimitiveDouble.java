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

import json.ArrayJson;
import json.Json;

/**

 * 
 * @author jdp
 */
public final class ListPrimitiveDouble
    extends AbstractListPrimitive<Double>
{
    private final static long serialVersionUID = 1L;


    public ListPrimitiveDouble(){
        super();
    }
    public ListPrimitiveDouble(gap.data.BigTable ancestor){
        super(ancestor);
    }

    public final gap.Primitive getType(){
        return gap.Primitive.Double;
    }
    public ListPrimitiveDouble clone(){
        return (ListPrimitiveDouble)super.clone();
    }
    public boolean fromJson(Json json){
        boolean mod = false;
        if (json instanceof ArrayJson){

            mod = this.isNotEmpty();

            this.clear();

            final ArrayJson array = (ArrayJson)json;
            for (Json j: array){

                Double v = j.getValue(Double.class);

                if (null != v){

                    this.add(v);

                    mod = true;
                }
            }
        }
        return mod;
    }
}
