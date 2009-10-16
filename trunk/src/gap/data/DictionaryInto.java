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
package gap.data;

import hapax.TemplateDictionary;

/**
 * Implemented by table and collection classes.
 */
public interface DictionaryInto {

    public interface DataFilter {
        /**
         * @return Field name or null to reject.
         * @see Field#getFieldName()
         */
        public String acceptAs(BigTable instance, Kind instanceKind, Field field);
    }

    /**
     * Install all with inheritance.
     */
    public TemplateDictionary dictionaryInto(TemplateDictionary dict);

    /**
     * Install with inheritance as directed by filter.
     */
    public TemplateDictionary dictionaryInto(TemplateDictionary dict, DataFilter filter);

}
