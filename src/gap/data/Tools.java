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
 * Default site toolset.
 * 
 * @author jdp
 */
public class Tools
    extends Object
{
    public static enum Set 
        implements HasName
    {
        Update("update-up"),
        Create("create-cr"),
        Goto("goto-gt"),
        Delete("delete-de"),
        Export("export-ex"),
        Import("import-im");


        private final static java.util.Map<String,Set> Lookup = new java.util.HashMap<String,Set>();
        static {
            for (Set set : Set.values()){
                Lookup.put(set.lname,set);
                Lookup.put(set.name(),set);
            }
        }
        private final static String LName(String imgPrefix){
            return imgPrefix.substring(0,imgPrefix.length()-3);
        }
        public final static Set For(String name){
            return Lookup.get(name);
        }
        public final static Set For(Tool tool){
            return For(tool.getName());
        }

        public final String imagePrefix, lname;

        Set(String imgPrefix){
            this.imagePrefix = imgPrefix;
            this.lname = LName(imgPrefix);
        }

        public String getName(){
            return this.lname;
        }
    }

    public final static Tools Default = new Tools();


    private final List.Primitive<Tool> tools = new gap.util.AbstractListPrimitive.Any<Tool>();


    public Tools(){
        super();
        for (Tools.Set toolSet : Tools.Set.values()){
            String lname = toolSet.lname;
            String imagePrefix = toolSet.imagePrefix;
            Tool tool = new Tool();
            tool.setName(lname);
            tool.setOverlayXtm("div.overlay.html");
            tool.setFormXtm("form."+lname+".html");
            tool.setTitleHiGraphicUri("/icons/"+imagePrefix+"-200x50-a00.png");
            tool.setTitleLoGraphicUri("/icons/"+imagePrefix+"-200x50-000.png");
            tool.setButtonHiGraphicUri("/icons/"+imagePrefix+"-b-200x50-a00.png");
            tool.setButtonLoGraphicUri("/icons/"+imagePrefix+"-b-200x50-000.png");
            tool.setButtonOffGraphicUri("/icons/"+imagePrefix+"-b-200x50-aaa.png");
            this.tools.add(tool);
        }
    }


    public List<Tool> getTools(){
        return this.tools;
    }

}
