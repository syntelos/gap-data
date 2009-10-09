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
 * Future jbxml binding point for generic site toolset XML.
 * 
 * @author jdp
 */
public class Tools
    extends Object
{
    public final static Tools Default = new Tools();


    private final ListShortResourceTool tools = new ListShortResourceTool();


    public Tools(){
        super();
        Tool updateTool = new Tool();
        updateTool.setName("update");
        updateTool.setOverlayXtm("div.overlay.html");
        updateTool.setFormXtm("form.update.html");
        updateTool.setTitleHiGraphicUri("/icons/update-up-200x50-a00.png");
        updateTool.setTitleLoGraphicUri("/icons/update-up-200x50-000.png");
        updateTool.setButtonHiGraphicUri("/icons/update-up-b-200x50-a00.png");
        updateTool.setButtonLoGraphicUri("/icons/update-up-b-200x50-000.png");
        updateTool.setButtonOffGraphicUri("/icons/update-up-b-200x50-aaa.png");
        this.tools.addToBuffer(updateTool);

        Tool createTool = new Tool();
        createTool.setName("create");
        createTool.setOverlayXtm("div.overlay.html");
        createTool.setFormXtm("form.create.html");
        createTool.setTitleHiGraphicUri("/icons/create-cr-200x50-a00.png");
        createTool.setTitleLoGraphicUri("/icons/create-cr-200x50-000.png");
        createTool.setButtonHiGraphicUri("/icons/create-cr-b-200x50-a00.png");
        createTool.setButtonLoGraphicUri("/icons/create-cr-b-200x50-000.png");
        createTool.setButtonOffGraphicUri("/icons/create-cr-b-200x50-aaa.png");
        this.tools.addToBuffer(createTool);

        Tool gotoTool = new Tool();
        gotoTool.setName("goto");
        gotoTool.setOverlayXtm("div.overlay.html");
        gotoTool.setFormXtm("form.goto.html");
        gotoTool.setTitleHiGraphicUri("/icons/goto-gt-200x50-a00.png");
        gotoTool.setTitleLoGraphicUri("/icons/goto-gt-200x50-000.png");
        gotoTool.setButtonHiGraphicUri("/icons/goto-gt-b-200x50-a00.png");
        gotoTool.setButtonLoGraphicUri("/icons/goto-gt-b-200x50-000.png");
        gotoTool.setButtonOffGraphicUri("/icons/goto-gt-b-200x50-aaa.png");
        this.tools.addToBuffer(gotoTool);

        Tool deleteTool = new Tool();
        deleteTool.setName("delete");
        deleteTool.setOverlayXtm("div.overlay.html");
        deleteTool.setFormXtm("form.delete.html");
        deleteTool.setTitleHiGraphicUri("/icons/delete-de-200x50-a00.png");
        deleteTool.setTitleLoGraphicUri("/icons/delete-de-200x50-000.png");
        deleteTool.setButtonHiGraphicUri("/icons/delete-de-b-200x50-a00.png");
        deleteTool.setButtonLoGraphicUri("/icons/delete-de-b-200x50-000.png");
        deleteTool.setButtonOffGraphicUri("/icons/delete-de-b-200x50-aaa.png");
        this.tools.addToBuffer(deleteTool);

        Tool exportTool = new Tool();
        exportTool.setName("export");
        exportTool.setOverlayXtm("div.overlay.html");
        exportTool.setFormXtm("form.export.html");
        exportTool.setTitleHiGraphicUri("/icons/export-ex-200x50-a00.png");
        exportTool.setTitleLoGraphicUri("/icons/export-ex-200x50-000.png");
        exportTool.setButtonHiGraphicUri("/icons/export-ex-b-200x50-a00.png");
        exportTool.setButtonLoGraphicUri("/icons/export-ex-b-200x50-000.png");
        exportTool.setButtonOffGraphicUri("/icons/export-ex-b-200x50-aaa.png");
        this.tools.addToBuffer(exportTool);

        Tool importTool = new Tool();
        importTool.setName("import");
        importTool.setOverlayXtm("div.overlay.html");
        importTool.setFormXtm("form.import.html");
        importTool.setTitleHiGraphicUri("/icons/import-im-200x50-a00.png");
        importTool.setTitleLoGraphicUri("/icons/import-im-200x50-000.png");
        importTool.setButtonHiGraphicUri("/icons/import-im-b-200x50-a00.png");
        importTool.setButtonLoGraphicUri("/icons/import-im-b-200x50-000.png");
        importTool.setButtonOffGraphicUri("/icons/import-im-b-200x50-aaa.png");
        this.tools.addToBuffer(importTool);

    }


    public List.Short<Tool> getTools(){
        return this.tools;
    }

}
