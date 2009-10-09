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

public interface ListFilter<V> {

    public final static class ListFilterTool
        extends java.lang.Object
        implements ListFilter<Tool>
    {
        public final String name;

        public ListFilterTool(String name){
            super();
            if (null != name)
                this.name = name;
            else
                throw new IllegalArgumentException();
        }

        public boolean accept(Tool tool){
            return (null != tool && this.name.equals(tool.getName()));
        }
    }
    public final static class ListFilterTemplate
        extends java.lang.Object
        implements ListFilter<Template>
    {
        public final String name;

        public ListFilterTemplate(String name){
            super();
            if (null != name)
                this.name = name;
            else
                throw new IllegalArgumentException();
        }

        public boolean accept(Template template){
            return (null != template && this.name.equals(template.getName()));
        }
    }

    public boolean accept(V item);

}
