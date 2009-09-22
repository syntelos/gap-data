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
package gap.service.od;

import java.util.List;

/**
 * 
 * 
 * @author jdp
 */
public interface ClassDescriptor {

    /**
     * Optional field 'version' will default to "one".
     */
    public interface Version
        extends ClassDescriptor
    {
        public boolean hasVersion();

        public Long getVersion();
    }

    /**
     * Optional field 'interfaces'
     */
    public interface Implements
        extends ClassDescriptor
    {
        public boolean hasInterfaces();
        /**
         * @return List of objects for toString
         */
        public List<Object> getInterfaces();
    }

    /**
     * Optional field 'kind'
     */
    public interface Kind
        extends ClassDescriptor
    {
        public boolean hasKind();
        /**
         * @return List of objects for toString
         */
        public String getKind();
    }

    /**
     * field 'name'
     */
    public String getName();

    /**
     * field 'fields'
     */
    public boolean hasFields();

    public List<FieldDescriptor> getFields();

    /**
     * field 'definitionClassName'
     */
    public boolean hasDefinitionClassName();

    public String getDefinitionClassName();

    public void setDefinitionClassName(String name);
}
