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

import gap.*;
import gap.data.*;
import gap.jac.tools.JavaFileManager.Location;

import com.google.appengine.api.datastore.Text;

import javax.servlet.ServletResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Used by {@link FileManager}.
 * 
 * @author jdp
 */
public final class Templates
    extends hapax.TemplateCache
    implements DataInheritance.Notation
{
    /**
     * Default data dictionary variables.
     */
    private static hapax.TemplateDictionary TemplateDictionaryDefault = hapax.TemplateDictionary.create();
    static {
        TemplateDictionaryDefault.putVariable("gap_version_short",gap.Version.Short);
        TemplateDictionaryDefault.putVariable("gap_version_long",gap.Version.Long);
    }
    /**
     * Templates file cache location
     */
    private final static String TemplatesLocation = "WEB-INF/templates";
    private final static int TemplatesLocationLen = TemplatesLocation.length();
    /**
     * Shared templates file cache instance
     */
    private final static Templates Instance = new Templates();


    public static hapax.TemplateDictionary CreateDictionary(){
        return TemplateDictionaryDefault.clone();
    }
    public final static hapax.Template GetTemplate(Resource resource)
        throws hapax.TemplateException
    {
        return Instance.getTemplate(resource);
    }
    static hapax.Template GetTemplate(String name)
        throws hapax.TemplateException
    {
        if (null != Instance)
            return Instance.getTemplate(name);
        else
            throw new IllegalStateException();
    }
    static File TemplateFile(String name)
        throws hapax.TemplateException
    {
        return Instance.templateFile(name);
    }
    static String TemplateSource(File file)
        throws IOException
    {
        return Instance.templateSource(file);
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



    private Templates(){
        super(TemplatesLocation);
    }


    public hapax.Template getTemplate(String name)
        throws hapax.TemplateException
    {
        Request request = Request.Get();
        if (null != request){
            Resource resource = request.resource;
            if (null != resource){
                gap.data.Template templateData = resource.getTemplatesByName(name);
                if (null != templateData)
                    return this.getTemplate(resource,templateData);
                else {
                    resource = Resource.ForLongBaseName(resource.getBase(),name);
                    if (null != resource)
                        return this.getTemplate(resource);
                }
            }
            resource = Resource.ForLongBaseName("",name);
            if (null != resource){
                if (null == request.resource)
                    request.resource = resource;

                return this.getTemplate(resource);
            }
        }
        return null;
    }
    public hapax.Template getTemplate(hapax.TemplateLoader context, String name)
        throws hapax.TemplateException
    {
        return this.getTemplate(name);
    }
    protected File templateFile(String name)
        throws hapax.TemplateException
    {
        return new File(hapax.Path.toFile(TemplatesLocation,name));
    }
    protected String templateSource(File file)
        throws IOException
    {
        FileReader reader = new FileReader(file);
        try {
            return this.readToString(reader);
        }
        finally {
            reader.close();
        }
    }
    protected hapax.Template getTemplate(Resource resource)
        throws hapax.TemplateException
    {
        if (null == resource)
            return null;
        else {
            gap.data.Template templateData = resource.getTemplatesByName(resource.getName());
            return this.getTemplate(resource,templateData);
        }
    }
    private hapax.Template getTemplate(Resource resource, gap.data.Template templateData)
        throws hapax.TemplateException
    {
        if (null != templateData){
            long last = 0;
            if (templateData.hasLastModified(MayInherit))
                last = resource.getLastModified(MayInherit);

            String cachePath = hapax.Path.toFile(FileManager.GetPath(resource),templateData.getName());

            hapax.Template template = this.hitCache(cachePath, last);
            if (null != template)
                return template;
            else {
                hapax.TemplateLoader context = new hapax.TemplateLoader.Context(this, resource.getBase());

                String contents = Strings.TextToString(templateData.getTemplateSourceHapax(MayInherit));

                template = new hapax.Template(last, contents, context);

                synchronized(this.cache){
                    this.cache.put(cachePath,template);
                }
                return template;
            }
        }
        else
            return null;
    }
}
