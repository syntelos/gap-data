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

import gap.hapax.Template;
import gap.hapax.TemplateException;
import gap.hapax.TemplateName;
import gap.hapax.TemplateRenderer;

import com.google.appengine.api.datastore.Text;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * 
 * 
 * @author jdp
 */
public final class Templates
    extends lxl.Map<Resource,Template>
    implements gap.data.DataInheritance.Notation,
               gap.hapax.TemplateLoader
{

    /**
     * Tempaltes are read from disk in the ASCII transparent UTF-8
     * character encoding.
     */
    public final static Charset UTF8 = Charset.forName("UTF-8");

    private final static String ResourcePrefix = "/xtm/";
    private final static String ResourceSuffix = ".xtm";
    private final static File ResourceDir = new File("xtm");


    private final static Templates Instance = new Templates();

    public static TemplateRenderer GetTemplate(TemplateName name)
        throws IOException, TemplateException
    {
        return Instance.getTemplate(name);
    }
    public static TemplateRenderer GetTemplate(String pathname)
        throws IOException, TemplateException
    {
        return Instance.getTemplate(pathname);
    }

    public static String ReadToString(Resource resource)
        throws IOException
    {
        InputStream file = resource.openStream();
        if (null != file){
            Reader reader = new java.io.InputStreamReader(file,UTF8);
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
        else
            throw new java.io.FileNotFoundException(resource.getPath());
    }


    private Templates(){
        super();
    }


    public TemplateRenderer getTemplate(TemplateName name)
        throws IOException, TemplateException
    {
        return this.getTemplate(name.getSource());
    }
    private TemplateRenderer getTemplate(String path)
        throws IOException, TemplateException
    {
        Resource templateResource = new Resource(path,ResourcePrefix,ResourceSuffix);
        Template template = this.get(templateResource);
        if (null == template){
            template = new Template(path);
            Resource templateFileResource = new Resource(ResourceDir,templateResource);
            try {
                template.setTemplateSourceHapax(gap.Strings.TextFromString(ReadToString(templateFileResource)));
                template.setLastModified(templateFileResource.getLastModified());
                this.put(templateResource,template);

                return new TemplateRenderer(this,template);
            }
            catch (IOException tryresource){

                template.setTemplateSourceHapax(gap.Strings.TextFromString(ReadToString(templateResource)));
                template.setLastModified(templateResource.getLastModified());
                this.put(templateResource,template);

                return new TemplateRenderer(this,template);
            }
        }
        else
            return new TemplateRenderer(this,template);
    }

}
