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
package gap.service;

import gap.data.List;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;

import com.google.appengine.api.datastore.Query;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * 
 * @author jdp
 */
public final class Parameters
    extends gap.hapax.AbstractData
    implements TemplateDataDictionary
{
    public final static class Special {
        /**
         * count={count} Requests page size for paged collection. If
         * no parameter is specified the container can choose how many
         * items in the collection should be returned. However, the
         * container SHOULD support a large default count value so
         * that all items can be returned by default.
         * 
         * startIndex={startIndex}	Index into a paged collection.
         */
        public final static class Page 
            extends gap.util.Page
        {
            public final static int GetStartIndex(Map<String,String[]> parameters){
                try {
                    String[] value = parameters.get(StartIndex);
                    if (null != value && 0 < value.length){
                        int startIndex = Integer.parseInt(value[0]);
                        if (0 < startIndex)
                            return startIndex;
                    }
                }
                catch (NumberFormatException exc){
                }
                return 0;
            }
            public final static int GetCount(Map<String,String[]> parameters, int page){
                try {
                    String[] value = parameters.get(Count);
                    if (null != value && 0 < value.length){
                        int count = Integer.parseInt(value[0]);
                        if (0 < count)
                            return count;
                    }
                }
                catch (NumberFormatException exc){
                }
                return page;
            }

            public Page(Map<String,String[]> parameters, int page){
                super(GetStartIndex(parameters),GetCount(parameters,page));
            }
        }
        /**
         * filterBy={fieldname} For a collection, return entries
         * filtered by the given field.
         * 
         * filterOp={operation} The operation to use when filtering a
         * collection, defaults to "contains". Valid values are
         * contains, equals, startsWith, and present.
         * 
         * filterValue={value} The value to use when filtering a
         * collection. For example,
         * filterBy=name&amp;filterOp=startsWith&amp;filterValue=John will
         * return all items whose name field starts with John. Johnny
         * and John Doe would both be included.)
         * 
         */
        public final static class Filter {
            public final static String FilterName = "filterBy";
            public final static String FilterValue = "filterValue";
            public final static String FilterOp = "filterOp";

            public enum Op {
                contains, equals, startsWith, present, lt, le, gt, ge, eq;
            }


            public final String name, value;

            public final Op op;


            public Filter(Map<String,String[]> parameters){
                super();
                String[] value;
                value = parameters.get(FilterName);
                if (null != value && 0 < value.length)
                    this.name = value[0];
                else
                    this.name = null;

                Op op = null;

                value = parameters.get(FilterOp);
                if (null != value && 0 < value.length){
                    op = Op.valueOf(value[0]);
                    if (null == op)
                        this.op = Op.contains;
                    else
                        this.op = op;
                }
                else
                    this.op = Op.contains;
                

                value = parameters.get(FilterValue);
                if (null != value && 0 < value.length)
                    this.value = value[0];
                else
                    this.value = null;
            }

            public boolean isEmpty(){
                return (!this.isNotEmpty());
            }
            public boolean isNotEmpty(){
                return (null != this.name && null != this.op && null != this.value);
            }
        }
        /**
         * format={format}	Format desired; one of (atom, json, xml); default is json if not provided
         */
        public final static class Format {

            public final static String Format = "format";

            private final static int Atom = 1, Json = 0, Xml = 2;


            public final String name;

            private final int type;


            public Format(Map<String,String[]> parameters){
                super();
                String[] value = parameters.get(Format);
                if (null == value || 0 == value.length){
                    this.name = "json";
                    this.type = Json;
                }
                else {
                    this.name = value[0];
                    if ("atom".equalsIgnoreCase(this.name))
                        this.type = Atom;
                    else if ("json".equalsIgnoreCase(this.name))
                        this.type = Json;
                    else if ("xml".equalsIgnoreCase(this.name))
                        this.type = Xml;
                    else
                        this.type = Json;
                }
            }

            public boolean isAtom(){
                return (Atom == this.type);
            }
            public boolean isXml(){
                return (Xml == this.type);
            }
            public boolean isJson(){
                return (Json == this.type);
            }
        }
        /**
         * fields={-join|,|field} List of fields to include in
         * representation or in the members of a collection. If no
         * fields are provided it is up to the container to decide
         * which fields to return. However, the set MUST include the
         * minimum set of fields. For people this is id, name, and
         * thumbnailUrl. For activities this is id and title. @all is
         * accepted to indicate returning all available fields.
         */
        public final static class Fields {
            public final static String Fields = "fields";
            public final static String All = "@all";


            public final boolean all, defaults;
            public final String[] list;

            public Fields(Map<String,String[]> parameters){
                super();
                String[] value = parameters.get(Fields);
                if (null != value){
                    StringBuilder string = new StringBuilder();
                    for (String v : value){
                        if (0 < string.length())
                            string.append(',');
                        string.append(v);
                    }
                    boolean all = false, defaults = false;
                    String[] list = null;
                    StringTokenizer strtok = new StringTokenizer(string.toString(),",| ");
                    int count = strtok.countTokens();
                    switch (count){
                    case 0:
                        defaults = true;
                        break;
                    case 1:
                        String one = strtok.nextToken();
                        list = new String[]{one};
                        all = (All.equals(one));
                        if (all)
                            list = null;
                        break;
                    default:
                        list = new String[count];
                        for (int cc = 0; cc < count; cc++){
                            String field = strtok.nextToken();
                            list[cc] = field;
                            if (All.equals(field)){
                                all = true;
                                list = null;
                                break;
                            }
                        }
                        break;
                    }
                    this.all = all;
                    this.defaults = defaults;
                    this.list = list;
                }
                else {
                    this.all = false;
                    this.defaults = true;
                    this.list = null;
                }
            }

            public boolean isEmpty(){
                return (null == this.list);
            }
            public boolean isNotEmpty(){
                return (null != this.list);
            }
        }
        /**
         * networkDistance={networkDistance} Modifies group-relative
         * requests (@friends, etc.) to include the transitive closure
         * of all friends up to {networkDistance} links away. MAY NOT
         * be honored by the container.
         */
        public final static class NetworkDistance {

            public final static String NetworkDistance = "networkDistance";



            public NetworkDistance(Map<String,String[]> parameters){
                super();
            }
        }
        /**
         * sortBy={fieldname} For a collection, return entries sorted
         * by the given field.
         * 
         * sortOrder={order} Can either be "ascending" or
         * "descending", defaults to ascending. Used to sort objects
         * in a collection.
         */
        public final static class Sort {
            public final static String SortBy = "sortBy";
            public final static String SortOrder = "sortOrder";

            public final static class TemplateNames {
                public final static TemplateName SortBy = new TemplateName(Sort.SortBy);
                public final static TemplateName SortOrder = new TemplateName(Sort.SortOrder);
            }


            public final String sortBy;

            public final Query.SortDirection sortOrder;


            public Sort(Map<String,String[]> parameters, String defaultSortBy){
                super();
                String[] sortBy = parameters.get(SortBy);
                if (null != sortBy && 0 != sortBy.length)
                    this.sortBy = sortBy[0];
                else
                    this.sortBy = defaultSortBy;

                Query.SortDirection sortOrder = null;

                String[] sortOrderP = parameters.get(SortOrder);
                if (null != sortOrderP && 0 != sortOrderP.length){
                    sortOrder = Query.SortDirection.valueOf(sortOrderP[0].toUpperCase());

                    if (null == sortOrder)
                        this.sortOrder = Query.SortDirection.DESCENDING;
                    else
                        this.sortOrder = sortOrder;
                }
                else
                    this.sortOrder = Query.SortDirection.DESCENDING;
            }


            public void dictionaryInto(TemplateDataDictionary dict){
                String sortBy = this.sortBy;
                if (null != sortBy)
                    dict.setVariable(TemplateNames.SortBy,sortBy);

                dict.setVariable(TemplateNames.SortOrder,this.sortOrder.name().toLowerCase());
            }
        }
        /**
         * updatedSince={xsdDateTime} When specified the container
         * should only return items whose updated date is equal to or
         * more recent then the specified value.
         */
        public final static class Since {

            public final static String UpdatedSince = "updatedSince";

            public Since(Map<String,String[]> parameters){
                super();
            }
        }
    }

    public final Map<String,String[]> parameters;
    public final int size;
    public final Special.Page page;
    public final Special.Filter filter;
    public final Special.Format format;
    public final Special.Fields fields;
    public final Special.NetworkDistance networkDistance;
    public final Special.Sort sort;
    public final Special.Since since;


    public Parameters(HttpServletRequest req, int page, Class<? extends gap.data.BigTable> table){
        super();
        Map<String,String[]> parameters = (Map<String,String[]>)req.getParameterMap();
        this.parameters = parameters;
        this.size = parameters.size();
        this.page = new Special.Page(parameters, page);
        this.filter = new Special.Filter(parameters);
        this.format = new Special.Format(parameters);
        this.fields = new Special.Fields(parameters);
        this.networkDistance = new Special.NetworkDistance(parameters);
        this.sort = new Special.Sort(parameters, OD.ClassSortBy(table));
        this.since = new Special.Since(parameters);
    }


    public void dictionaryInto(TemplateDataDictionary dict){

        this.page.dictionaryInto(dict);
        this.sort.dictionaryInto(dict);
    }
    public boolean isEmpty(){
        return (0 == this.size);
    }
    public boolean isNotEmpty(){
        return (0 != this.size);
    }
    public int size(){
        return this.size;
    }
    public String[] get(String name){
        return this.parameters.get(name);
    }
    public String valueOf(String name){
        String[] value = this.parameters.get(name);
        if (null != value && 0 < value.length){
            if (1 == value.length)
                return value[0];
            else {
                StringBuilder string = new StringBuilder();
                for (String v : value){
                    if (0 < string.length())
                        string.append(',');
                    string.append(v);
                }
                return string.toString();
            }
        }
        else
            return null;
    }
    public boolean isFieldsAll(){
        return this.fields.defaults;
    }
    public boolean isFieldsDefault(){
        return this.fields.all;
    }
    public boolean hasFields(){
        return this.fields.isNotEmpty();
    }
    public String[] getFields(){
        return this.fields.list;
    }
}
