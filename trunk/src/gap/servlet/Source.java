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
package gap.servlet;

import gap.*;
import gap.hapax.*;
import gap.service.*;

import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

/**
 * Bound to path <code>'/src/*'</code> 
 */
public class Source
    extends Site
{
    public final static class TemplateNames {
        public final static TemplateName FileClass = new TemplateName("file_class");
        public final static TemplateName FileName = new TemplateName("file_name");
        public final static TemplateName FileText = new TemplateName("file_text");
    }

    /**
     * Static content cache for input to hapax.
     */
    protected static class FileCache
        extends java.util.Hashtable<File,FileCache.Content>
    {
        protected static class Content {

            protected final String plain, xml;

            protected Content(String plain){
                super();
                this.plain = plain;
                this.xml = QuoteXml(plain);
            }
        }

        protected final File dir;

        protected FileCache(String dir){
            super();
            this.dir = new File(dir);
            if (!this.dir.isDirectory())
                throw new IllegalStateException("Directory not found '"+dir+"'");
        }

        public String getXml(String name)
            throws IOException
        {
            File file = new File(this.dir,Path.Clean(name));
            Content content = this.get(file);
            if (null == content){
                if (file.isFile()){
                    String string = Templates.ReadToString(file);
                    if (null != string){
                        content = new Content(string);
                        this.put(file,content);
                        return content.xml;
                    }
                }
                return null;
            }
            else
                return content.xml;
        }
        public String getPlain(String name)
            throws IOException
        {
            File file = new File(this.dir,Path.Clean(name));
            Content content = this.get(file);
            if (null == content){
                if (file.isFile()){
                    String string = Templates.ReadToString(file);
                    if (null != string){
                        content = new Content(string);
                        this.put(file,content);
                        return content.plain;
                    }
                }
                return null;
            }
            else
                return content.plain;
        }

    }

    protected final static FileCache Cache = new FileCache("WEB-INF");


    public Source(){
        super();
    }


    protected TemplateDataDictionary doGetDefine(Request req, Response rep){
        String path = req.getPathFull();
        try {
            String content = Cache.getXml(path);
            if (null != content){
                TemplateDataDictionary top = req;
                top.setVariable(TemplateNames.FileName,path);
                top.setVariable(TemplateNames.FileClass,"lang-java");
                top.setVariable(TemplateNames.FileText,content);
                return top;
            }
        }
        catch (IOException exc){
            LogRecord rec = new LogRecord(Level.SEVERE,req.userReference);
            rec.setThrown(exc);
            Servlet.Log.log(rec);
        }
        return null;
    }
    @Override
    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.accept("text/html")){
            TemplateDataDictionary top = this.doGetDefine(req,rep);
            if (null != top){
                try {
                    TemplateRenderer template = Templates.GetTemplate("src.html");
                    if (null != template){
                        this.render(req,rep,template);
                        rep.setContentTypeHtml();
                        return ;
                    }
                    else {
                        this.error(req,rep,500,"Missing source template");
                        return;
                    }
                }
                catch (TemplateException exc){
                    LogRecord rec = new LogRecord(Level.SEVERE,req.userReference);
                    rec.setThrown(exc);
                    Servlet.Log.log(rec);
                    this.error(req,rep,500,"Template error",exc);
                    return;
                }
            }
            else
                this.error(req,rep,404,"Not found.");
        }
        else {
            String path = req.getPathFull();
            try {
                String content = Cache.getPlain(path);
                if (null != content){
                    rep.write(content);
                    rep.setContentTypeText();
                    return ;
                }
                else
                    this.error(req,rep,404,"Not found.");
            }
            catch (IOException exc){
                LogRecord rec = new LogRecord(Level.SEVERE,req.userReference);
                rec.setThrown(exc);
                Servlet.Log.log(rec);
                this.error(req,rep,500,"File error",exc);
                return;
            }
        }
    }
}
