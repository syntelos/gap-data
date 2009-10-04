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
public interface ClassDescriptor
    extends HasName
{

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
         * @see HasName
         */
        public List<Object> getInterfaces();
    }

    /**
     * Optional field 'kind' is typically the class name.  It
     * identifies the datastore table that the data bean class is
     * associated to.
     */
    public interface Kind
        extends ClassDescriptor
    {
        public boolean hasKind();

        public String getKind();
    }

    /**
     * Optional field 'sortBy' for the class default sort ordering
     * field name.
     */
    public interface SortBy
        extends ClassDescriptor
    {
        public boolean hasSortBy();

        public String getSortBy();
    }

    /**
     * The class relation position as parent or child.  None is
     * generally equivalent to parent in that there's no relation to
     * another class as parent.
     */
    public interface Relation 
        extends ClassDescriptor
    {
        public enum Type {
            None, Parent, Child;
        }

        public boolean hasRelation();

        public Type getRelation();
    }

    /**
     * Class name (not qualified with {@link Package} name).
     */
    public String getName();

    /**
     * Instance fields of the data bean class.
     */
    public boolean hasFields();

    public List<FieldDescriptor> getFields();

    /**
     * Methods of the data bean class bind into a template (bean, bean
     * servlet, etc) by name.
     */
    public boolean hasMethods();

    public List<MethodDescriptor> getMethods();

    /**
     * field 'definitionClassName' is employed by the OD service.
     */
    public boolean hasDefinitionClassName();

    public String getDefinitionClassName();

    public void setDefinitionClassName(String name);
}
