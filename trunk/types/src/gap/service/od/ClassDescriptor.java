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

/**
 * 
 * 
 * @author jdp
 */
public interface ClassDescriptor
    extends gap.data.HasName
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
        public lxl.List<Object> getInterfaces();
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
     * Optional field 'path' is the collection path root, e.g.,
     * "/people".  It should have a leading slash but no trailing
     * slash, or will be normalized to this form.
     */
    public interface Path
        extends ClassDescriptor
    {
        public boolean hasPath();

        public String getPath();
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
     * The class relation position (itself) as parent or child.  None
     * is generally equivalent to parent in that there's no relation
     * to another class as parent.
     */
    public interface Relation 
        extends ClassDescriptor
    {
        /**
         * The child relation employs the parent key in its key -- it
         * is the short relation.
         */
        public enum Type {
            None, Parent, Child;
        }

        public boolean hasRelation();

        public Type getRelation();
        /**
         * @return Relation is type none or parent
         */
        public boolean isRelationPrimary();
        /**
         * @return Relation is type child 
         */
        public boolean isRelationSecondary();

        /**
         * A child must have a parent.  The parent is a {@link
         * gap.data.BigTable} class name.  It would be an unqualified
         * class name when found in the same package, or when an
         * import expression includes it.
         */
        public boolean hasParent();

        public boolean hasNotParent();

        public String getParent();
    }

    /**
     * 
     */
    public interface WithPackage
        extends ClassDescriptor
    {
        public boolean hasPackage();

        public PackageDescriptor getPackage();

        public String getPackageClassName();
    }

    /**
     * 
     */
    public interface WithImports
        extends ClassDescriptor
    {
        public boolean hasImports();

        public lxl.List<ImportDescriptor> getImports();
    }

    /**
     * Class name (not qualified with {@link Package} name).
     */
    public String getName();

    /**
     * Instance fields of the data bean class.
     */
    public boolean hasFields();

    public lxl.List<FieldDescriptor> getFields();
    /**
     * @return Class knows this field to be short
     */
    public boolean isFieldShort(FieldDescriptor field);

    /**
     * Methods of the data bean class bind into a template (bean, bean
     * servlet, etc) by name.
     */
    public boolean hasMethods();

    public lxl.List<MethodDescriptor> getMethods();

    /**
     * Field 'definitionClassName' is employed by the OD service for
     * the fully qualified class name.
     */
    public boolean hasDefinitionClassName();

    public String getDefinitionClassName();

    public void setDefinitionClassName(String name);
}
