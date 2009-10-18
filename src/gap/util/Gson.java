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

import hapax.TemplateDataDictionary;

import com.google.gson.FieldAttributes;
/**
 * @author jdp
 */
public final class Gson
    extends Object
{

    public static class TemplateDictionaryExclude
        extends Object
        implements com.google.gson.ExclusionStrategy
    {
        public TemplateDictionaryExclude(){
            super();
        }

        public boolean shouldSkipField(FieldAttributes field){
            String name = field.getName();
            return ("parent".equals(name));
        }
        public boolean shouldSkipClass(Class clas){
            return false;
        }
    }

    public final static String ToJson(TemplateDataDictionary object){

        com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
            .setExclusionStrategies(new TemplateDictionaryExclude())
            .serializeNulls()
            .setPrettyPrinting()
            .create();

        return gson.toJson(object);
    }
}
