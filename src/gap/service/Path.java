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

import hapax.TemplateDictionary;

import javax.servlet.http.HttpServletRequest;

import java.util.StringTokenizer;

/**
 * 
 * 
 * @author jdp
 */
public final class Path
    extends java.lang.Object
{
    public final static class Special {
        public final static String Me = "@me";
        public final static String Self = "@self";
        public final static String Friends = "@friends";
        public final static String SupportedFields = "@supportedFields";
    }

    private final String servlet, full, sub;
    private final String[] components;
    private final int size;


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
                String[] components = new String[len];
                for (int cc = 0; cc < len; cc++){
                    components[cc] = strtok.nextToken();
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
    public Path(String path){
        super();
        this.servlet = "";
        this.sub = path;
        if (null != path){
            this.full = path;
            if ("/".equals(path)){
                this.components = null;
                this.size = 0;
            }
            else {
                StringTokenizer strtok = new StringTokenizer(path,"/?&#;");
                int len = strtok.countTokens();
                String[] components = new String[len];
                for (int cc = 0; cc < len; cc++){
                    components[cc] = strtok.nextToken();
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
     * @return Subcomponent for index or null for not found.
     */
    public String get(int idx){
        String[] components = this.components;
        if (null != components && idx < components.length)
            return components[idx];
        else
            return null;
    }
    /**
     * @return Subcomponent for index zero equals string (case sensitive).
     */
    public boolean isSub0(String string){
        String sub0 = this.get(0);
        return (null != sub0 && sub0.equals(string));
    }
    /**
     * @return Subcomponent for index one equals string (case sensitive).
     */
    public boolean isSub1(String string){
        String sub1 = this.get(1);
        return (null != sub1 && sub1.equals(string));
    }
    /**
     * @return Subcomponent for index two equals string (case sensitive).
     */
    public boolean isSub2(String string){
        String sub2 = this.get(2);
        return (null != sub2 && sub2.equals(string));
    }
    public boolean isSub3(String string){
        String sub3 = this.get(3);
        return (null != sub3 && sub3.equals(string));
    }
    public boolean isSub4(String string){
        String sub4 = this.get(4);
        return (null != sub4 && sub4.equals(string));
    }
    public boolean isSub5(String string){
        String sub5 = this.get(5);
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
        int idx = 0;
        String[] components = this.components;
        if (null != components && idx < components.length)
            return components[idx];
        else
            return null;
    }
    public boolean hasSource(){
        return (0 < this.size);
    }
    public boolean hasNotSource(){
        return (1 > this.size);
    }
    public String getGroup(){
        int idx = 1;
        String[] components = this.components;
        if (null != components && idx < components.length)
            return components[idx];
        else
            return null;
    }
    public boolean hasGroup(){
        return (1 < this.size);
    }
    public boolean hasNotGroup(){
        return (2 > this.size);
    }
    public String getItem(){
        int idx = 2;
        String[] components = this.components;
        if (null != components && idx < components.length)
            return components[idx];
        else
            return null;
    }
    public boolean hasItem(){
        return (2 < this.size);
    }
    public boolean hasNotItem(){
        return (3 > this.size);
    }
    /**
     * The "base" is the path components excepting an optional last
     * component.  One path component is the "base".  Users can employ
     * a base with no name as a name.
     */
    public boolean hasBase(){

        return (0 < this.size);
    }
    public boolean hasNotBase(){

        return (1 > this.size);
    }
    public String getBase(){
        if (2 < this.size){
            StringBuilder strbuf = new StringBuilder();
            for (String component : this.components){
                if (0 != strbuf.length())
                    strbuf.append('/');
                strbuf.append(component);
            }
            return strbuf.toString();
        }
        else if (0 < this.size)
            return this.components[0];
        else
            return null;
    }
    /**
     * The "name" is the last path component after a "base".  
     */
    public boolean hasName(){

        return (1 < this.size);
    }
    public boolean hasNotName(){

        return (2 > this.size);
    }
    public String getName(){
        if (1 < this.size){
            String[] components = this.components;
            return components[components.length-1];
        }
        else
            return null;
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
    public void dictionaryInto(TemplateDictionary dict){
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

    private final static String Clean(String string){
        if (null == string || 0 == string.length())
            return "";
        else if ('/' == string.charAt(0))
            return string.substring(1);
        else
            return string;
    }
}
