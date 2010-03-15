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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * 
 * 
 * @author jdp
 */
public final class Templates
    extends lxl.Map<File,Template>
    implements gap.data.DataInheritance.Notation,
               gap.hapax.TemplateLoader
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

    public static TemplateRenderer GetTemplate(TemplateName name)
        throws TemplateException
    {
        return Instance.getTemplate(name);
    }
    public static TemplateRenderer GetTemplate(String pathname)
        throws TemplateException
    {
        return Instance.getTemplate(pathname);
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
    private TemplateRenderer getTemplate(String path)
        throws TemplateException
    {
        File templateFile = TemplateFile(path);
        if (null != templateFile){
            Template template = this.get(templateFile);
            if (null == template){
                template = new Template(path);
                try {
                    template.setTemplateSourceHapax(gap.Strings.TextFromString(ReadToString(templateFile)));
                    template.setLastModified(templateFile.lastModified());
                    this.put(templateFile,template);

                    return new TemplateRenderer(this,template);
                }
                catch (IOException exc){
                    throw new TemplateException(templateFile.getPath(),exc);
                }
            }
            else
                return new TemplateRenderer(this,template);
        }
        else
            return null;
    }

}
