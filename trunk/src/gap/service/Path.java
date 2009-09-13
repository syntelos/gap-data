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
    /**
     * Placeholder organization for the html tabs
     */
    public final static class Tabs {
        /**
         * These are sections in the main and sidebar templates, as
         * well tabs.  
         * 
         * The one exception is "errors", which is not a tab but is a
         * special section in the main template employed by the error
         * servlet to display error messages in html in the browser.
         */
        public final static class Source {

            public final static String Unknown = null;
            public final static String Gap = "";
            public final static String People = "people";
            public final static String Groups = "groups";
            public final static String Projects = "projects";
            public final static String Activities = "activities";
            public final static String Messages = "messages";
            public final static String Images = "images";
            public final static String Administer = "administer";
            public final static String Errors = "errors";

            private final static String[] List = {
                Unknown, Gap, People, Groups, Projects, Activities, Messages, Images, Administer, Errors
            };

            public final static String For(int position){
                if (-1 < position && position < List.length)
                    return List[position];
                else
                    return Unknown;
            }
        }
        public final static class Const {

            public final static int Unknown = 0;
            public final static int Gap = 1;
            public final static int People = 2;
            public final static int Groups = 3;
            public final static int Projects = 4;
            public final static int Activities = 5;
            public final static int Messages = 6;
            public final static int Images = 7;
            public final static int Administer = 8;
            public final static int Errors = 9;
        }
        public final static class CSS {

            public final static String Active = "tab active";
            public final static String Inactive = "tab inactive";

            public final static String ClassFor(int status, int position){
                if (status == position)
                    return CSS.Active;
                else
                    return CSS.Inactive;
            }
            public final static class Name {

                public final static String Unknown = "tab_unknown_class";
                public final static String Gap = "tab_gap_class";
                public final static String People = "tab_people_class";
                public final static String Groups = "tab_groups_class";
                public final static String Projects = "tab_projects_class";
                public final static String Activities = "tab_activities_class";
                public final static String Messages = "tab_messages_class";
                public final static String Images = "tab_images_class";
                public final static String Administer = "tab_administer_class";
                public final static String Errors = "tab_errors_class";

                private final static String[] List = {
                    Unknown, Gap, People, Groups, Projects, Activities, Messages, Images, Administer, Errors
                };

                public final static String For(int position){
                    if (-1 < position && position < List.length)
                        return List[position];
                    else
                        return Unknown;
                }

                public final static int Count(){
                    return List.length;
                }
            }
        }
        private final static java.util.Map<String,Integer> Map = new java.util.HashMap<String,Integer>();
        static {
            Map.put(Source.Gap,Const.Gap);
            Map.put(Source.People,Const.People);
            Map.put(Source.Groups,Const.Groups);
            Map.put(Source.Projects,Const.Projects);
            Map.put(Source.Activities,Const.Activities);
            Map.put(Source.Messages,Const.Messages);
            Map.put(Source.Images,Const.Images);
            Map.put(Source.Administer,Const.Administer);
            Map.put(Source.Errors,Const.Errors);
        }
        public final static int StatusFor(Path path){
            String source = path.getServlet();
            if (null == source)
                return Const.Gap;
            else {
                Integer id = Map.get(source);
                if (null != id)
                    return id.intValue();
                else
                    return Const.Gap;
            }
        }
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
        int status = Tabs.StatusFor(this);
        int position = 1, count = Tabs.CSS.Name.Count();
        for (; position < count; position++){
            if (1 < position){
                String source = Tabs.Source.For(position);
                if (position == status)
                    dict.showSection(source);
                else
                    dict.hideSection(source);
            }
            String cssClassName = Tabs.CSS.Name.For(position);
            String cssClassValue = Tabs.CSS.ClassFor(status,position);
            if (null != cssClassName && null != cssClassValue)
                dict.putVariable(cssClassName,cssClassValue);
            else
                throw new IllegalStateException("[BUG] status="+status+", position="+position);
        }
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
