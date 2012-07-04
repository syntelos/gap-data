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
public final class ListPrimitiveEnum
    extends AbstractListPrimitive<Enum>
{
    private final static long serialVersionUID = -8730744897200958281L;


    public ListPrimitiveEnum(){
        super();
    }
    public ListPrimitiveEnum(gap.data.BigTable ancestor){
        super(ancestor);
    }


    public final gap.Primitive getType(){
        return gap.Primitive.Enum;
    }
    public ListPrimitiveEnum clone(){
        return (ListPrimitiveEnum)super.clone();
    }
    public boolean fromJson(Json json){
        boolean mod = false;
        if (json instanceof ArrayJson){

            mod = this.isNotEmpty();

            this.clear();

            final ArrayJson array = (ArrayJson)json;
            for (Json j: array){

                Enum v = j.getValue(Enum.class);

                if (null != v){

                    this.add(v);

                    mod = true;
                }
            }
        }
        return mod;
    }
}
