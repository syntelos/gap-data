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

import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;
import hapax.TemplateLoader;

import javax.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 */
public final class Templates
    extends hapax.TemplateCache
{
    public final static String TemplatesLocation = "WEB-INF/templates";

    private static TemplateDictionary TemplateDictionaryDefault = TemplateDictionary.create();
    static {
        TemplateDictionaryDefault.putVariable("gap_version_short",gap.Version.Short);
        TemplateDictionaryDefault.putVariable("gap_version_long",gap.Version.Long);
    }
    private static Templates Instance = new Templates();


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


    private Templates(){
        super(TemplatesLocation);
    }

}
