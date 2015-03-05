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
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

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
    /**
     * Called from main programs with working directory
     */
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
    /**
     * Tempaltes are read from disk in the ASCII transparent UTF-8
     * character encoding.
     */
    public final static Charset UTF8 = Charset.forName("UTF-8");

    private final static String TemplatesFilenameSuffix = ".xtm";
    private final static int TemplatesFilenameSuffixLen = TemplatesFilenameSuffix.length();

    private final static Templates Instance = new Templates();

    /**
     * Template for servlet path info (path not including servlet
     * mapping), placing servlets in the role of alternative views on
     * one space of data and templates.
     */
    public static TemplateRenderer GetTemplate(Request request)
        throws TemplateException
    {
        return Instance.getTemplate(request);
    }
    public static TemplateRenderer GetTemplate(TemplateName name)
        throws TemplateException
    {
        return Instance.getTemplate(name);
    }
    public static TemplateRenderer GetRenderer(Template template)
        throws TemplateException
    {
        return Instance.getRenderer(template);
    }
    public static TemplateRenderer GetTemplate(String pathname)
        throws TemplateException
    {
        return Instance.getTemplate(pathname);
    }
    private static File TemplateFile(String name)
        throws TemplateException
    {
        try {
            if (!name.endsWith(TemplatesFilenameSuffix))
                name += TemplatesFilenameSuffix;

            File file = new File(TemplatesLocation,name);
            if (file.isFile()){

                return file;
            }
        }
        catch (Exception ignore){
        }
        return null;
    }
    public static String ReadToString(File file)
        throws IOException
    {
        Reader reader = new java.io.InputStreamReader(new java.io.FileInputStream(file),UTF8);
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
        return this.getTemplate(name.getSource());
    }
    public TemplateRenderer getRenderer(Template template)
        throws TemplateException
    {
        if (null != template)
            return new TemplateRendererImpl(this,template);
	else
	    return null;
    }
    /** 
     * @return Template for request servlet sub path excluding servlet
     * path.
     */
    private TemplateRenderer getTemplate(Request request)
        throws TemplateException
    {
        return this.getTemplate(request.path.sub);
    }
    private TemplateRenderer getTemplate(String path)
        throws TemplateException
    {
        Template template = Template.ForLongName(path);
        if (null != template)
            return new TemplateRendererImpl(this,template);
        else {
            File templateFile = TemplateFile(path);
            if (null != templateFile){
                template = new Template(path);
                try {
                    template.setTemplateSourceHapax(gap.Strings.TextFromString(ReadToString(templateFile)));
                    template.setLastModified(templateFile.lastModified());
                    template.save();
                    return new TemplateRendererImpl(this,template);
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
