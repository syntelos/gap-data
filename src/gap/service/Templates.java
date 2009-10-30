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
import gap.hapax.*;
import gap.jac.tools.JavaFileManager.Location;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import javax.servlet.ServletResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

/**
 * Used by {@link FileManager}.
 * 
 * @author jdp
 */
public final class Templates
    extends Object
    implements DataInheritance.Notation,
               TemplateLoader
{

    private static File TemplatesLocation = new File("WEB-INF/templates");

    public final static void SInit(File dir){
        if (TemplatesLocation.isDirectory())
            throw new IllegalStateException();
        else {
            File validate = new File(dir,"WEB-INF/templates");
            if (validate.isDirectory())
                TemplatesLocation = validate;
            else
                throw new IllegalArgumentException(dir.getPath());
        }
    }

    private final static String TemplatesFilenameSuffix = ".xtm";
    private final static int TemplatesFilenameSuffixLen = TemplatesFilenameSuffix.length();

    private final static Templates Instance = new Templates();

    public static TemplateRenderer GetTemplate(Request request)
        throws TemplateException
    {
        return Instance.getTemplate(request);
    }
    public static TemplateRenderer GetTemplate(Resource resource)
        throws TemplateException
    {
        return Instance.getTemplate(resource);
    }
    public static TemplateRenderer GetTemplate(TemplateName name)
        throws TemplateException
    {
        return Instance.getTemplate(name);
    }
    public static TemplateRenderer GetTemplate(String name)
        throws TemplateException
    {
        return Instance.getTemplate(new TemplateName(name));
    }
    private static File TemplateFile(String name)
        throws TemplateException
    {
        if (!name.endsWith(TemplatesFilenameSuffix))
            name += TemplatesFilenameSuffix;

        File file = new File(TemplatesLocation,name);
        if (file.isFile())
            return file;
        else
            return null;
    }
    public static void CreateTools(Resource resource){
        Key resourceKey = resource.getKey();
        Resource index_html = Resource.ForLongBaseName("","index.html");
        if (null != index_html && index_html.hasTools(MayInherit)){
            for (Tool indexTool: index_html.getTools(MayInherit)){
                String name = indexTool.getName();
                Tool resourceTool = new Tool(resourceKey,name);
                resourceTool.inheritFrom(indexTool);
                resourceTool.save();
            }
        }
        else {
            for (Tool indexTool: Tools.Default.getTools()){
                String name = indexTool.getName();
                Tool resourceTool = new Tool(resourceKey,name);
                resourceTool.updateFrom(indexTool);
                resourceTool.save();
            }
        }
    }
    public static String ReadToString(File file)
        throws IOException
    {
        Reader reader = new FileReader(file);
        try {
            StringBuilder string = new StringBuilder();
            char[] buf = new char[0x200];
            int read;
            while (0 < (read = reader.read(buf,0,0x200))){
                string.append(buf,0,read);
            }
            return string.toString();
        }
        finally {
            reader.close();
        }
    }


    private Templates(){
        super();
    }


    public TemplateRenderer getTemplate(TemplateName name)
        throws TemplateException
    {
        Request request = Request.Get();
        if (null != request){
            Resource resource = request.resource;
            if (null != resource){
                gap.data.Template template = resource.getTemplates(name.getName());
                if (null != template)
                    return new TemplateRenderer(this,template);
            }
        }
        File templateFile = TemplateFile(name.getSource());
        if (null != templateFile){
            Resource resource = Resource.GetCreateLong(name.getBase(),name.getName());
            CreateTools(resource);
            Template template = new Template(resource.getKey(),name.getName());
            try {
                template.setTemplateSourceHapax(gap.Strings.TextFromString(ReadToString(templateFile)));
                template.setLastModified(templateFile.lastModified());
                template.save();
                return new TemplateRenderer(this,template);
            }
            catch (IOException exc){
                throw new TemplateException(templateFile.getPath(),exc);
            }
        }
        else
            return null;
    }
    private TemplateRenderer getTemplate(Request request)
        throws TemplateException
    {
        Resource resource = request.resource;
        if (null == resource){
            Path path = request.path;
            File templateFile = TemplateFile(path.getSub());
            if (null != templateFile){
                resource = Resource.GetCreateLong(path.getBase(),path.getName());
                CreateTools(resource);
                Template template = new Template(resource.getKey(),path.getName());
                try {
                    template.setTemplateSourceHapax(gap.Strings.TextFromString(ReadToString(templateFile)));
                    template.setLastModified(templateFile.lastModified());
                    template.save();
                    return new TemplateRenderer(this,template);
                }
                catch (IOException exc){
                    throw new TemplateException(templateFile.getPath(),exc);
                }
            }
            else
                return null;
        }
        else
            return this.getTemplate(resource);
    }
    private TemplateRenderer getTemplate(Resource resource)
        throws TemplateException
    {
        if (null == resource)
            return null;
        else {
            gap.data.Template template = resource.getTemplates(resource.getName());
            if (null != template)
                return new TemplateRenderer(this,template);

            File templateFile = TemplateFile(TemplateName.Cat(resource.getBase(),resource.getName()));
            if (null != templateFile){
                CreateTools(resource);
                template = new Template(resource.getKey(),resource.getName());
                try {
                    template.setTemplateSourceHapax(gap.Strings.TextFromString(ReadToString(templateFile)));
                    template.setLastModified(templateFile.lastModified());
                    template.save();
                    return new TemplateRenderer(this,template);
                }
                catch (IOException exc){
                    throw new TemplateException(templateFile.getPath(),exc);
                }
            }
            else
                return null;
        }
    }
}
