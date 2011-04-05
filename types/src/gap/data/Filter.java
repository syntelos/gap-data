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

import com.google.appengine.api.datastore.Query;

/**
 * A serializable set of data query operations, including relations
 * and sorting.
 * 
 * 
 * A filter is constructed for a data type.  A set of terms is added
 * to the filter, each identifying a field and operator and value.
 * 
 */
public final class Filter
    extends Object
    implements java.io.Serializable,
               java.lang.Comparable<Filter>,
	       java.lang.Cloneable,
               java.lang.Iterable<Filter.Term>
{
    private final static long serialVersionUID = 1;
    /**
     * Data query operators include relational and sorting operations.
     */
    public static enum Op {
        /**
         * Relate by term field and value
         */
        lt(Query.FilterOperator.LESS_THAN),
        le(Query.FilterOperator.LESS_THAN_OR_EQUAL),
        eq(Query.FilterOperator.EQUAL),
        ge(Query.FilterOperator.GREATER_THAN_OR_EQUAL),
        gt(Query.FilterOperator.GREATER_THAN),
        /**
         * Sort ascending on term field
         */
        asc(null),
        /**
         * Sort descending in term field
         */
        dsc(null);


        private final static long serialVersionUID = 1;


        /**
         * Null for 'asc' and 'dsc' sorting.
         */
        public final Query.FilterOperator rel;


        private Op(Query.FilterOperator op){
            this.rel = op;
        }


        public boolean isRelation(){
            return (null != this.rel);
        }
        public boolean isNotRelation(){
            return (null == this.rel);
        }
        public boolean isSort(){
            return (null == this.rel);
        }
        public boolean isNotSort(){
            return (null != this.rel);
        }
    }
    /**
     * A data query term includes a data type field, operator and
     * optional value.  The relational operators employ a value, while
     * the sort operators do not employ a term value.
     */
    public final static class Term 
        extends Object
        implements java.io.Serializable, 
                   java.lang.Comparable<Filter.Term>
    {
        private final static long serialVersionUID = 1;

        public final static class Iterator
            extends Object
            implements java.util.Iterator<Term>
        {
            private final Term[] list;
            private final int count;
            private int index;

            Iterator(Term[] list){
                super();
                if (null == list){
                    this.list = null;
                    this.count = 0;
                }
                else {
                    this.list = list;
                    this.count = list.length;
                }
            }

            public boolean hasNext(){
                return (this.index < this.count);
            }
            public Term next(){
                if (this.index < this.count)
                    return this.list[this.index++];
                else
                    throw new java.util.NoSuchElementException(String.valueOf(this.index));
            }
            public void remove(){
                throw new java.lang.UnsupportedOperationException();
            }
        }

        public final Field field;
        public final Op op;
        public final Object value;


        /**
         * Convenient for relational operators
         */
        public Term(Field field, Op op, Object value){
            super();
            if (null != field && null != op){
                this.field = field;
                this.op = op;
                this.value = value;
            }
            else
                throw new IllegalArgumentException();
        }
        /**
         * Convenient for sort ('asc' and 'dsc') operators
         */
        public Term(Field field, Op op){
            super();
            if (null != field && null != op){
                this.field = field;
                this.op = op;
                this.value = null;
            }
            else
                throw new IllegalArgumentException();
        }


        public int compareTo(Term term){
            if (null == term)
                return 1;
            else {
                int comp = this.field.compareTo(term.field);
                if (0 == comp){
                    comp = this.op.compareTo(term.op);
                    if (0 == comp){
                        Object thisValue = this.value;
                        Object thatValue = term.value;
                        if (null == thisValue){
                            if (null == thatValue)
                                return 0;
                            else
                                return -1;
                        }
                        else if (null == thatValue)
                            return 1;
                        else if (thisValue instanceof Comparable)
                            return ((Comparable)thisValue).compareTo(thatValue);

                        else if (thisValue.equals(thatValue))
                            return 0;
                        else
                            return (thisValue.toString().compareTo(thatValue.toString()));
                    }
                }
                return comp;
            }
        }
    }


    public final Kind kind;

    private Filter.Term[] list;


    public Filter(Kind kind){
        super();
        if (null != kind)
            this.kind = kind;
        else
            throw new IllegalArgumentException();
    }
    public Filter(String kindName){
        this(Kind.For(kindName));
    }


    public Filter clone(){
	try {
	    Filter clone = (Filter)super.clone();
	    if (null != clone.list)
		clone.list = clone.list.clone();

	    return clone;
	}
	catch (CloneNotSupportedException exc){
	    throw new InternalError();
	}
    }
    public Kind getKind(){
        return this.kind;
    }
    public int size(){
        Filter.Term[] list = this.list;
        if (null == list)
            return 0;
        else
            return list.length;
    }
    public Filter.Term get(int idx){
        if (-1 < idx){
            Filter.Term[] list = this.list;
            if (null != list && idx < list.length)
                return list[idx];
        }
        throw new ArrayIndexOutOfBoundsException(String.valueOf(idx));
    }
    public Filter add(Filter.Term term){
        if (null != term){
            Filter.Term[] list = this.list;
            if (null == list)
                this.list = new Filter.Term[]{term};
            else {
                int len = list.length;
                Filter.Term[] copier = new Filter.Term[len+1];
                System.arraycopy(list,0,copier,0,len);
                copier[len] = term;
                java.util.Arrays.sort(copier);
                this.list = copier;
            }
            return this;
        }
        else
            throw new IllegalArgumentException();
    }

    public Filter add(Field field, Op op, Object value){
        return this.add(new Term(field,op,value));
    }
    public Query update(Query query){
        Filter.Term[] list = this.list;
        if (null != list){
            for (Filter.Term term : list){
                switch (term.op){
                case asc:
                    query.addSort(term.field.name(),Query.SortDirection.ASCENDING);
                    break;
                case dsc:
                    query.addSort(term.field.name(),Query.SortDirection.DESCENDING);
                    break;
                default:
                    query.addFilter(term.field.name(),term.op.rel,term.value);
                    break;
                }
            }
        }
        return query;
    }
    public java.util.Iterator<Filter.Term> iterator(){
        return new Filter.Term.Iterator(this.list);
    }
    public int compareTo(Filter filter){
        if (null == filter)
            return 1;
        else {
            int comp = this.kind.compareTo(filter.kind);
            if (0 == comp){
                int fz = filter.size();
                int tz = this.size();
                if (fz == tz){
                    for (int cc = 0; cc < tz; cc++){
                        comp = this.list[cc].compareTo(filter.list[cc]);
                        if (0 != comp)
                            return comp;
                    }
                    return 0;
                }
                else if (fz > tz)
                    return -1;
                else
                    return 1;
            }
            else
                return comp;
        }
    }
}
