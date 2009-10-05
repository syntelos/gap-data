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

import gap.data.ResourceDescriptor;
import gap.jac.tools.JavaFileManager.Location;

import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;
import hapax.TemplateLoader;
import hapax.TemplateLoaderContext;

import com.google.appengine.api.datastore.Text;

import javax.servlet.ServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Used by {@link FileManager}.
 * 
 * @author jdp
 */
public final class Templates
    extends hapax.TemplateCache
{
    /**
     * Default data dictionary variables.
     */
    private static TemplateDictionary TemplateDictionaryDefault = TemplateDictionary.create();
    static {
        TemplateDictionaryDefault.putVariable("gap_version_short",gap.Version.Short);
        TemplateDictionaryDefault.putVariable("gap_version_long",gap.Version.Long);
    }
    /**
     * Templates file cache location
     */
    public final static String TemplatesLocation = "WEB-INF/templates";
    public final static int TemplatesLocationLen = TemplatesLocation.length();
    /**
     * Shared templates file cache instance
     */
    private final static Templates Instance = new Templates();


    public static void Render(String name, TemplateDictionary td, PrintWriter writer)
        throws IOException, TemplateException
    {
        if (null != Instance){

            Template template = Instance.getTemplate(name);

            if (null == td)
                td = TemplateDictionaryDefault.clone();

            template.render(td,writer);
        }
        else
            throw new IllegalStateException();
    }
    public static void Render(String name, TemplateDictionary td, ServletResponse rep)
        throws IOException, TemplateException
    {
        rep.setCharacterEncoding("UTF-8");
        Render(name,td,rep.getWriter());
    }
    public static void Render(String name, PrintWriter writer)
        throws IOException, TemplateException
    {
        Render(name,TemplateDictionaryDefault.clone(),writer);
    }
    public static void Render(String name, ServletResponse rep)
        throws IOException, TemplateException
    {
        rep.setCharacterEncoding("UTF-8");
        Render(name,rep.getWriter());
    }
    public static TemplateDictionary CreateDictionary(){
        return TemplateDictionaryDefault.clone();
    }
    public static Template GetTemplate(String name)
        throws TemplateException
    {
        if (null != Instance)
            return Instance.getTemplate(name);
        else
            throw new IllegalStateException();
    }
    public static File GetTemplatesLocation(){
        return Instance.getTemplatesLocation();
    }
    public static Template GetTemplate(TemplateLoaderContext context, ResourceDescriptor resource,
                                       String path)
        throws TemplateException
    {
        return Instance.getTemplate(context,resource,path);
    }
    static TemplateLoaderContext CreateTemplateLoaderContext(FileManager fm, Location fmLocation){

        return new TemplateLoaderContext(Instance,fmLocation.getName());
    }
    static String Clean(String path){

        if (null != path){

            int idx = path.indexOf(Templates.TemplatesLocation);
            if (-1 != idx)
                return path.substring(idx+Templates.TemplatesLocationLen+1);
            else 
                return path;
        }
        else
            return null;
    }


    private final File templatesLocation;


    private Templates(){
        super(TemplatesLocation);
        this.templatesLocation = new File(TemplatesLocation);
    }


    public File getTemplatesLocation(){
        return this.templatesLocation;
    }
    public Template getTemplate(TemplateLoaderContext context, ResourceDescriptor resource, String path)
        throws TemplateException
    {
        if (null != resource){
            String source = gap.Strings.TextToString(resource.getTemplateSourceHapax());
            if (null != source)
                return new Template(source,context);
        }
        return null;
    }
}
