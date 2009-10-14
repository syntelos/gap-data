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
package gap;

import oso.data.*;
import gap.data.*;
import gap.service.*;

import hapax.Template;
import hapax.TemplateDictionary;
import hapax.TemplateException;

import javax.servlet.http.HttpServletRequest;

import java.nio.charset.Charset;

/**
 * Created by {@link gap.service.Servlet}.
 * 
 * @author jdp
 */
public class Request 
    extends javax.servlet.http.HttpServletRequestWrapper
    implements DataInheritance.Notation
{
    /**
     * Universal charset
     */
    public final static Charset UTF8 = Charset.forName("UTF-8");

    private final static java.lang.ThreadLocal<Request> RTL = new java.lang.ThreadLocal<Request>();

    public final static Request Get(){
        return RTL.get();
    }
    public final static void Exit(){
        RTL.set(null);
    }

    /**
     * Request content type
     */
    public enum ContentType {
        Nil,
        Form,
        Multipart,
        Json,
        Xml;

        private final static java.util.Map<String,ContentType> Map = new java.util.HashMap<String,ContentType>();
        static {
            Map.put("application/x-www-form-urlencoded",Form);
            Map.put("multipart/form-data",Multipart);
            Map.put("application/json",Json);
            Map.put("text/json",Json);
            Map.put("application/xml",Xml);
            Map.put("text/xml",Xml);
        }
        private final static java.util.regex.Pattern Tail = java.util.regex.Pattern.compile("[; ]");

        public final static ContentType For(HttpServletRequest req){
            return ContentType.For(req.getContentType());
        }
        public final static ContentType For(String mimetype){
            if (null == mimetype)
                return ContentType.Nil;
            else {
                ContentType value = Map.get(mimetype);
                if (null != value)
                    return value;
                else {
                    String[] re = Tail.split(mimetype,0);
                    if (null != re && 0 != re.length){
                        value = Map.get(re[0]);
                        if (null != value)
                            return value;
                    }
                    return ContentType.Nil;
                }
            }
        }
    }


    public final Method method;
    public final Protocol protocol;
    public final Path path;
    public final Accept accept;
    public final FileManager fileManager;
    public final Parameters parameters;
    public final String userReference;
    public final TemplateDictionary top;
    public final Logon logon;
    public final ContentType contentType;
    public final boolean isPartner;
    public Resource resource;
    public Tool tool;


    public Request(HttpServletRequest req, Method method, Protocol protocol, Path path, 
                   Accept accept, FileManager fm, Logon logon, String uri, TemplateDictionary top, 
                   Parameters parameters)
    {
        super(req);
        this.method = method;
        this.protocol = protocol;
        this.path = path;
        this.accept = accept;
        this.fileManager = fm;
        this.parameters = parameters;
        this.userReference = uri;
        this.top = top;
        this.logon = logon;
        this.contentType = ContentType.For(req);
        this.resource = FileManager.GetResource(path);
        if (null != this.resource){
            this.tool = FileManager.GetTool(this.resource,method,req.getParameter("op"));

            if (logon.serviceAdmin)
                this.isPartner = true;
            else {
                String logonId = logon.serviceLogon;
                if (null != logonId)
                    this.isPartner = (null != resource.getPartnersByName(logonId));
                else
                    this.isPartner = false;
            }
        }
        else {
            this.tool = null;
            this.isPartner = false;
        }

        RTL.set(this);
    }


    public boolean hasResource(){
        return (null != this.resource);
    }
    public Resource getResource(){
        return this.resource;
    }
    public boolean hasTool(){
        return (null != this.tool);
    }
    public Tool getTool(){
        return this.tool;
    }
    public List<Tool> getTools(){
        Resource resource = this.resource;
        if (null != resource)
            return resource.getTools(MayInherit);
        else
            return null;
    }
    public final hapax.TemplateDictionary getTop(){
        return logon.dict;
    }
    public final boolean isAdmin(){
        return this.logon.serviceAdmin;
    }
    public final String getLogonId(){
        return this.logon.serviceLogon;
    }
    public final String getUserId(){
        return this.logon.getUserId();
    }
    public final boolean isLoggedIn(){
        return this.logon.isLoggedIn();
    }
    public final boolean isLoggedOut(){
        return this.logon.isLoggedOut();
    }
    public final boolean hasViewer(){
        return this.logon.hasPerson();
    }
    public final Person getViewer(){
        return this.logon.getPerson();
    }
    public final boolean accept(String name){
        return this.accept.accept(name);
    }
    public final hapax.Template getTemplate()
        throws TemplateException
    {
        return Templates.GetTemplate(this.resource);
    }
    public final Servlet getServlet(){
        return this.fileManager.getServlet(this.path);
    }
    public final Servlet getServlet(Path path){
        return this.fileManager.getServlet(path);
    }
    public final Function getFunction(Servlet instance, Request request, Response response, Resource resource, Tool tool){
        return this.fileManager.getFunction(instance, request, response, resource, tool);
    }
    public final boolean isPath(String string){
        return this.path.equals(string);
    }
    public final String getPathFull(){
        return this.path.getFull();
    }
    public final String getPathFullClean(){
        return Path.Clean(this.path.getFull());
    }
    public final boolean hasPath(int idx){
        return this.path.has(idx);
    }
    public final String getPath(int idx){
        return this.path.get(idx);
    }
    public final boolean hasSource(){
        return this.path.hasSource();
    }
    public final boolean hasNotSource(){
        return this.path.hasNotSource();
    }
    public final String getSource(){
        return this.path.getSource();
    }
    public final boolean hasGroup(){
        return this.path.hasGroup();
    }
    public final boolean hasNotGroup(){
        return this.path.hasNotGroup();
    }
    public final String getGroup(){
        return this.path.getGroup();
    }
    public final boolean hasItem(){
        return this.path.hasItem();
    }
    public final boolean hasNotItem(){
        return this.path.hasNotItem();
    }
    public final String getItem(){
        return this.path.getItem();
    }
    public final boolean hasBase(){
        return this.path.hasBase();
    }
    public final boolean hasNotBase(){
        return this.path.hasNotBase();
    }
    public final String getBase(){
        return this.path.getBase();
    }
    public final boolean hasName(){
        return this.path.hasName();
    }
    public final boolean hasNotName(){
        return this.path.hasNotName();
    }
    public final String getName(){
        return this.path.getName();
    }
    public final String getParameter(String name){
        String[] valueAry = this.parameters.get(name);
        if (null != valueAry && 0 != valueAry.length)
            return valueAry[0];
        else
            return null;
    }
}
