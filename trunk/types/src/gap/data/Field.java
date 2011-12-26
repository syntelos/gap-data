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

    /**
     * Field iterator
     */
    public final static class Iterator<E extends Enum>
        extends Object
        implements java.lang.Iterable<Field<E>>,
                   java.util.Iterator<Field<E>>
    {

        private final Field[] list;

        private final int length;

        private int index;


        public Iterator(){
            super();
            this.list = null;
            this.length = 0;
        }
        public Iterator(Field[] list){
            super();
            this.list = list;
            if (null == list)
                this.length = 0;
            else 
                this.length = list.length;
        }
        public Iterator(Field[] list, int ofs, int len){
            super();
            if (null == list || 0 == len || (0 == ofs && len == list.length)){
                this.list = list;
                this.length = len;
            }
            else {
                Field[] copier = new Field[len];
                System.arraycopy(list,ofs,copier,0,len);
                this.list = copier;
                this.length = len;
            }
        }


        public boolean hasNext(){
            while (this.index < this.length){

                if (null != this.list[this.index])
                    return true;
                else
                    this.index++;
            }
            return false;
        }
        public Field<E> next(){
            while (this.index < this.length){

                Field<E> item = (Field<E>)this.list[this.index++];
                if (null != item)
                    return item;
            }
            throw new java.util.NoSuchElementException();
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public java.util.Iterator<Field<E>> iterator(){
            return this;
        }
    }
    /**
     * Field statistics are maintained for persistent fields exclusively
     */
    public static class Statistics<E extends Enum>
        extends Object
    {
        private final Field[] constants;

        private final int count;

        private boolean isClean;

        private Field[] clean, dirty;


        public Statistics(Class<E> fclas){
            super();
            this.constants = (Field[])fclas.getEnumConstants();
            this.count = this.constants.length;
            this.markClean();
        }


        public final void markClean(){
            this.isClean = true;
            this.clean = this.constants.clone();
            this.dirty = new Field[this.count];
        }
        public final void markDirty(){
            this.isClean = false;
            this.clean = new Field[this.count];
            this.dirty = this.constants.clone();
        }
        public final void markDirty(Field<E> field){
            this.isClean = false;
            final int fx = field.ordinal();
            this.dirty[fx] = field;
            this.clean[fx] = null;
        }
        public final boolean isClean(){
            return this.isClean;
        }
        public final boolean isDirty(){
            return (!this.isClean);
        }
        public final Iterable<Field> listClean(){
            return (new Field.Iterator(this.clean));
        }
        public final Iterable<Field> listDirty(){
            return (new Field.Iterator(this.dirty));
        }
    }
}
