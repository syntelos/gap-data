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


/**
 * Implemented by an Enum for the fields of a persistent data class.
 * 
 * @see oso.data.Person
 * @author jdp
 */
public interface Field<E extends Enum>
    extends java.lang.Comparable<E>
{

    public static enum Type {
        Primitive, BigTable, Collection, PrimitiveCollection;
    }

    /**
     * Upcase name (camel name)
     */
    public String name();
    /**
     * Runtime compiled constant value for field.
     */
    public int ordinal();
    /**
     * Downcase name (decamel name)
     */
    public String getFieldName();

    public Type getFieldType();

    public boolean isFieldTypePrimitive();

    public boolean isNotFieldTypePrimitive();

    public boolean isFieldTypeBigTable();

    public boolean isNotFieldTypeBigTable();

    public boolean isFieldTypeCollection();

    public boolean isNotFieldTypeCollection();

    public boolean isFieldNameKeyOrId();

    public boolean isNotFieldNameKeyOrId();

}
