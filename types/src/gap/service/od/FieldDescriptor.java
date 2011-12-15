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
 * A defined field must have both type and name.
 * 
 * @author jdp
 */
public interface FieldDescriptor
    extends gap.data.HasName
{
    /**
     * Default sort field.  Only one field in a class description may
     * return true for the default sort-by field.  Naturally, the type
     * of this field must be a {@link gap.Primitive primitive}.
     */
    public interface DefaultSortBy
        extends FieldDescriptor
    {
        /**
         * @return True for the default sort field, only one per
         * class.
         */
        public boolean isDefaultSortBy();
    }

    /**
     * Symbolic enumerated types
     */
    public interface Enumerated
        extends FieldDescriptor
    {
        /**
         * @return True for field type is a subclass of {@link java.lang.Enum}
         */
        public boolean isEnumerated();
    }

    /**
     * Fields are persistent by default.
     */
    public interface Persistence 
        extends FieldDescriptor
    {
        public enum Type {
            Persistent, Transient;
        }

        public boolean hasPersistence();

        public Type getPersistence();
    }

    /**
     * A type class may have zero or more "unique" fields.
     * 
     * The set of unique fields' values constitutes the identity of an
     * instance object.
     */
    public interface Uniqueness 
        extends FieldDescriptor
    {
        /**
         * @return True for unique fields, false for not unique or
         * unknown.
         */
        public boolean isUnique();
    }
    /**
     * 
     */
    public interface Relation 
        extends FieldDescriptor
    {
        /**
         * The child (lang) relation employs the parent key in its key
         * -- it is the "short" (data) relation.
         */
        public enum Type {
            None, Parent, Child;
        }

        public boolean hasRelation();

        public Type getRelation();
    }


    /**
     * @return An object for toString
     * @see gap.data.HasName
     * @see gap.Objects#StringFromObject(java.lang.Object)
     */
    public Object getType();

    public String getName();

}
