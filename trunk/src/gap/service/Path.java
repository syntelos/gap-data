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

import gap.data.Kind;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * A request path may be a string of data kinds and identifiers, as in
 * the following examples.
 * 
 * <pre>
 * /people/fadbbce97c0ca0ae
 * /people/fadbbce97c0ca0ae/action/171dfd44aae48eae
 * </pre>
 * 
 * The first example references a data bean having kind "Person" from
 * path name "people".
 * 
 * The second example may be a short or long reference via path name
 * "action" to a related item.
 * 
 * If this relation is short, then that item is only accessible via
 * this path.  However if this relation is long, then the following
 * example would be equivalent.
 * 
 * <pre>
 * /action/171dfd44aae48eae
 * </pre>
 * 
 * @author jdp
 */
public final class Path
    extends java.lang.Object
    implements java.lang.Iterable<Path.Component>
{
    public final static class Special {
        public final static String Me = "@me";
        public final static String Self = "@self";
        public final static String Friends = "@friends";
        public final static String SupportedFields = "@supportedFields";
    }
    public final static class Component {

        public final String term;

        public final boolean identifier;

        public final Kind kind;


        public Component(String term){
            super();
            this.term = term;
            Kind kind = Kind.ForPath(term);
            if (null != kind){
                this.identifier = false;
                this.kind = kind;
            }
            else {
                boolean identifier = false;
                try {
                    long id = gap.data.Hash.Hex(term);
                    identifier = true;
                }
                catch (Exception exc){
                }
                this.identifier = identifier;
                this.kind = null;
            }
        }
    }
    public final static class Iterator
        extends Object
        implements java.util.Iterator<Path.Component>
    {
        private final Component[] list;
        private final int length;
        private int index;


        public Iterator(Path path){
            super();
            this.list = path.components;
            this.length = path.size;
        }


        public boolean hasNext(){
            return (this.index < this.length);
        }
        public Component next(){
            if (this.index < this.length)
                return this.list[this.index++];
            else
                throw new java.util.NoSuchElementException();
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
    }


    public final String servlet, full, sub;
    public final Component[] components;
    public final int size;


    public Path(HttpServletRequest req){
        super();
        String servlet = req.getServletPath();
        this.servlet = Clean(servlet);
        String path = req.getPathInfo();
        this.sub = path;
        if (null != path){
            this.full = (servlet+path);
            if ("/".equals(path)){
                this.components = null;
                this.size = 0;
            }
            else {
                StringTokenizer strtok = new StringTokenizer(path,"/");
                int len = strtok.countTokens();
                Component[] components = new Component[len];
                for (int cc = 0; cc < len; cc++){
                    components[cc] = new Component(strtok.nextToken());
                }
                this.components = components;
                this.size = len;
            }
        }
        else {
            this.full = servlet;
            this.components = null;
            this.size = 0;
        }
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
    /**
     * @return Subcomponent for index 
     */
    public boolean hasIdentifier(int idx){
        Component[] components = this.components;
        return (null != components && idx < components.length && components[idx].identifier);
    }
    /**
     * @return Subcomponent for index or null for not found.
     */
    public String getIdentifier(int idx){
        Component[] components = this.components;
        if (null != components && idx < components.length){
            Component component = components[idx];
            if (component.identifier)
                return component.term;
        }
        return null;
    }
    /**
     * @return Subcomponent for index 
     */
    public boolean hasKind(int idx){
        Component[] components = this.components;
        return (null != components && idx < components.length && null != components[idx].kind);
    }
    /**
     * @return Subcomponent for index or null for not found.
     */
    public Kind getKind(int idx){
        Component[] components = this.components;
        if (null != components && idx < components.length)
            return components[idx].kind;
        else
            return null;
    }
    /**
     * @return Subcomponent for index 
     */
    public boolean hasComponent(int idx){
        Component[] components = this.components;
        return (null != components && idx < components.length);
    }
    /**
     * @return Subcomponent for index or null for not found.
     */
    public String getComponent(int idx){
        Component[] components = this.components;
        if (null != components && idx < components.length)
            return components[idx].term;
        else
            return null;
    }
    /**
     * @return Subcomponent for index zero equals string (case sensitive).
     */
    public boolean isSub0(String string){
        String sub0 = this.getComponent(0);
        return (null != sub0 && sub0.equals(string));
    }
    /**
     * @return Subcomponent for index one equals string (case sensitive).
     */
    public boolean isSub1(String string){
        String sub1 = this.getComponent(1);
        return (null != sub1 && sub1.equals(string));
    }
    /**
     * @return Subcomponent for index two equals string (case sensitive).
     */
    public boolean isSub2(String string){
        String sub2 = this.getComponent(2);
        return (null != sub2 && sub2.equals(string));
    }
    public boolean isSub3(String string){
        String sub3 = this.getComponent(3);
        return (null != sub3 && sub3.equals(string));
    }
    public boolean isSub4(String string){
        String sub4 = this.getComponent(4);
        return (null != sub4 && sub4.equals(string));
    }
    public boolean isSub5(String string){
        String sub5 = this.getComponent(5);
        return (null != sub5 && sub5.equals(string));
    }
    public boolean isServlet(String string){
        String servlet = this.servlet;
        return (null != servlet && servlet.equals(string));
    }
    public String getServlet(){
        return this.servlet;
    }
    public String getFull(){
        return this.full;
    }
    public String getSub(){
        return this.sub;
    }
    public String getSource(){
        return this.getComponent(0);
    }
    public boolean hasSource(){
        return (0 < this.size);
    }
    public boolean hasNotSource(){
        return (1 > this.size);
    }
    public String getGroup(){
        return this.getComponent(1);
    }
    public boolean hasGroup(){
        return (1 < this.size);
    }
    public boolean hasNotGroup(){
        return (2 > this.size);
    }
    public String getItem(){
        return this.getComponent(2);
    }
    public boolean hasItem(){
        return (2 < this.size);
    }
    public boolean hasNotItem(){
        return (3 > this.size);
    }
    public boolean hasNotBase(){
        return (2 > this.size);
    }
    public boolean hasBase(){
        return (1 < this.size);
    }
    public String getBase(){
        StringBuilder strbuf = new StringBuilder();
        Component components[] = this.components;
        String el;
        if (null != components){
            for (int cc = 0, count = components.length, term = (count-1); cc < term; cc++){
                el = components[cc].term;
                if (0 != strbuf.length())
                    strbuf.append('/');
                strbuf.append(el);
            }
        }
        return strbuf.toString();
    }
    public boolean hasNotName(){
        return (1 > this.size);
    }
    public boolean hasName(){
        return (0 < this.size);
    }
    public String getName(){
        String name = this.getComponent(this.size-1);
        if (null != name)
            return name;
        else
            return "";
    }
    public boolean isMe(){
        return Special.Me.equals(this.getSource());
    }
    public boolean isSupportedFields(){
        return (1 == this.size && Special.SupportedFields.equals(this.getSource()));
    }
    public boolean isSelf(){
        return (2 == this.size && Special.Self.equals(this.getGroup()));
    }
    public boolean isFriends(){
        return (3 == this.size && Special.Friends.equals(this.getItem()));
    }
    public java.util.Iterator<Path.Component> iterator(){
        return new Iterator(this);
    }
    public String toString(){
        return this.full;
    }
    public int hashCode(){
        return this.full.hashCode();
    }
    public boolean equals(Object that){
        if (that == this)
            return true;
        else if (null == that)
            return false;
        else 
            return this.full.equals(that.toString());
    }

    public final static String Clean(String string){
        if (null == string || 0 == string.length())
            return "";
        else if ('/' == string.charAt(0))
            return string.substring(1);
        else
            return string;
    }
}
