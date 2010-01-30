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
import gap.hapax.*;
import gap.service.*;

import javax.servlet.http.HttpServletRequest;

import java.nio.charset.Charset;

/**
 * Created by {@link gap.service.Servlet}.
 * 
 * @author jdp
 */
public class Request 
    extends javax.servlet.http.HttpServletRequestWrapper
    implements DataInheritance.Notation,
               TemplateDataDictionary
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
        RTL.remove();
    }

    /**
     * Request content type
     */
    public static enum ContentType {
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
    public static enum Field {
        method, protocol, path, accept, fileManager, parameters, userReference, 
            logon, contentType, isAdmin, isPartner, isMember, resource, tool;

        public static Field For(String name){
            try {
                return Field.valueOf(name);
            }
            catch (IllegalArgumentException exc){
                return null;
            }
        }
    }


    public final Method method;
    public final Protocol protocol;
    public final Path path;
    public final Kind kind;
    public final Accept accept;
    public final FileManager fileManager;
    public final Parameters parameters;
    public final String userReference;
    public final Logon logon;
    public final ContentType contentType;
    public final boolean isAdmin, isPartner, isMember;
    public Resource resource;
    public Tool tool;

    private TemplateDataDictionary parent;
    private java.util.Map<String,String> variables;
    private java.util.Map<String,List<TemplateDataDictionary>> sections;


    public Request(HttpServletRequest req, Method method, Protocol protocol, Path path, 
                   Accept accept, FileManager fm, Logon logon, String uri, Parameters parameters)
    {
        super(req);
        this.method = method;
        this.protocol = protocol;
        this.path = path;
        this.kind = Kind.For(path);
        this.accept = accept;
        this.fileManager = fm;
        this.parameters = parameters;
        this.userReference = uri;
        this.logon = logon;
        this.contentType = ContentType.For(req);
        this.resource = FileManager.GetResource(path);
        this.isAdmin = logon.serviceAdmin;

        String logonId = logon.serviceLogon;

        this.isMember = (null != logonId);

        if (null != this.resource){
            this.tool = FileManager.GetTool(this.resource,method,req.getParameter("op"));

            if (logon.serviceAdmin)
                this.isPartner = true;
            else {
                if (null != logonId)
                    this.isPartner = (null != resource.getPartners(logonId));
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


    public Resource getResource(){
        return this.resource;
    }
    public boolean hasTool(){
        return (null != this.tool);
    }
    public Tool getTool(){
        return this.tool;
    }
    public Map<String,Tool> getTools(){
        Resource resource = this.resource;
        if (null != resource)
            return resource.getTools(MayInherit);
        else
            return null;
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
    public final boolean acceptHtml(){
        return this.accept.accept("text/html");
    }
    public final boolean acceptJson(){
        return (this.accept.accept("text/json")
                ||this.accept.accept("application/json"));
    }
    public final boolean acceptXml(){
        return (this.accept.accept("text/xml")
                ||this.accept.accept("application/xml"));
    }
    public final boolean accept(String name){
        return this.accept.accept(name);
    }
    public final TemplateRenderer getTemplate()
        throws TemplateException
    {
        return Templates.GetTemplate(this);
    }
    public final Servlet getServlet(){
        return this.fileManager.getServlet(this.path);
    }
    public final Servlet getServlet(Path path){
        return this.fileManager.getServlet(path);
    }
    public final ToolFunction getToolFunction(Servlet instance, Request request, Response response, Resource resource, Tool tool){
        return this.fileManager.getToolFunction(instance, request, response, resource, tool);
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

    public void renderComplete(){
        this.parent = null;
        java.util.Map<String,String> variables = this.variables;
        if (null != variables)
            variables.clear();
        java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;
        if (null != sections){
            for (List<TemplateDataDictionary> list: sections.values()){
                for (TemplateDataDictionary item: list){
                    item.renderComplete();
                }
            }
            sections.clear();
        }
    }
    public TemplateDataDictionary clone(){
        try {
            return (TemplateDataDictionary)super.clone();
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new java.lang.Error(exc);
        }
    }
    public TemplateDataDictionary clone(TemplateDataDictionary parent){
        try {
            Request clone = (Request)super.clone();
            clone.parent = parent;
            return clone;
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new java.lang.Error(exc);
        }
    }
    public TemplateDataDictionary getParent(){
        return this.parent;
    }
    public boolean hasVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case method:
            case protocol:
            case path:
            case accept:
            case fileManager:
                return name.is(0);
            case parameters:
                if (name.has(1))
                    return this.parameters.hasVariable(new TemplateName(name));
                else
                    return true;
            case userReference:
                return name.is(0);
            case logon:
                if (name.has(1))
                    return this.logon.hasVariable(new TemplateName(name));
                else
                    return true;
            case contentType:
            case isAdmin:
            case isPartner:
            case isMember:
                return name.is(0);
            case resource:
                if (name.has(1) && null != this.resource)
                    return this.resource.hasVariable(new TemplateName(name));
                else
                    return name.is(0);
            case tool:
                if (name.has(1) && null != this.tool)
                    return this.tool.hasVariable(new TemplateName(name));
                else
                    return name.is(0);
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            java.util.Map<String,String> variables = this.variables;
            if (null != variables && variables.containsKey(name.getName())){
                return true;
            }
            TemplateDataDictionary parent = this.parent;
            if (null != parent){
                return parent.hasVariable(name);
            }
            return false;
        }
    }
    public String getVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case method:
                if (name.is(0))
                    return this.method.name;
                else
                    return "";
            case protocol:
                if (name.is(0))
                    return this.protocol.name;
                else
                    return "";
            case path:
                if (name.is(0))
                    return this.path.getFull();
                else
                    return "";
            case accept:
                if (name.is(0))
                    return this.getHeader("Accept");
                else
                    return "";
            case fileManager:
                return "";
            case parameters:
                if (name.has(1))
                    return this.parameters.getVariable(new TemplateName(name));
                else
                    return "";
            case userReference:
                return this.userReference;
            case logon:
                if (name.has(1))
                    return this.logon.getVariable(new TemplateName(name));
                else
                    return "";
            case contentType:
                if (name.is(0) && null != this.contentType)
                    return this.contentType.name();
                else
                    return "";
            case isAdmin:
                if (name.is(0) && this.isMember)
                    return "Admin";
                else
                    return "";
            case isPartner:
                if (name.is(0) && this.isPartner)
                    return "Partner";
                else
                    return "";
            case isMember:
                if (name.is(0) && this.isMember)
                    return "Member";
                else
                    return "";
            case resource:
                if (name.has(1) && null != this.resource)
                    return this.resource.getVariable(new TemplateName(name));
                else
                    return "";
            case tool:
                if (name.has(1) && null != this.tool)
                    return this.tool.getVariable(new TemplateName(name));
                else
                    return "";
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            java.util.Map<String,String> variables = this.variables;
            if (null != variables){
                String value = variables.get(name.getName());
                if (null != value)
                    return value;
            }
            TemplateDataDictionary parent = this.parent;
            if (null != parent){
                return parent.getVariable(name);
            }
            return null;
        }
    }
    public void setVariable(TemplateName name, String value){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case method:
            case protocol:
            case path:
            case accept:
            case fileManager:
            case parameters:
            case userReference:
            case logon:
            case contentType:
            case isAdmin:
            case isPartner:
            case isMember:
                throw new IllegalArgumentException(field.name());

            case resource:{
                Path resourceName = new Path(value);
                Resource resource = Resource.ForLongBaseName(resourceName.getBase(),resourceName.getName());
                if (null != resource)
                    this.resource = resource;
                return;
            }
            case tool:
                if (null != this.resource){
                    Path toolName = new Path(value);
                    if (toolName.hasBase()){
                        Path resourceName = new Path(toolName.getBase());
                        Resource resource = Resource.ForLongBaseName(resourceName.getBase(),resourceName.getName());
                        if (null != resource)
                            this.resource = resource;
                    }
                    this.tool = this.resource.getTools(toolName.getName());
                    return;
                }
                else
                    throw new IllegalStateException(name.source);

            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            java.util.Map<String,String> variables = this.variables;
            if (null == variables){
                variables = new java.util.HashMap<String,String>();
                this.variables = variables;
            }
            variables.put(name.getName(),value);
        }
    }
    public List<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case method:
            case protocol:
            case path:
            case accept:
            case fileManager:
                return null;
            case parameters:
                return this.parameters.getSection(new TemplateName(name));
            case userReference:
                return null;
            case logon:
                return this.logon.getSection(new TemplateName(name));
            case contentType:
            case isAdmin:
            case isPartner:
            case isMember:
                return null;
            case resource:
                if (null != this.resource)
                    return this.resource.getSection(new TemplateName(name));
                else
                    return null;
            case tool:
                if (null != this.tool)
                    return this.tool.getSection(new TemplateName(name));
                else
                    return null;
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;
            List<TemplateDataDictionary> section = null;
            if (null != sections){
                section = sections.get(name.getComponent(0));
            }
            if (null == section){
                /*
                 * Inherit
                 */
                TemplateDataDictionary parent = this.parent;
                if (null != parent){
                    section = parent.getSection(name);
                    if (null != section){

                        section = AbstractData.SectionClone(this,section);

                        sections.put(name.getComponent(0),section);
                    }
                }
                if (null == section){
                    /*
                     * Synthetic
                     */
                    if (this.hasVariable(name))
                        section = this.showSection(name);
                    else
                        return null;
                }
            }
            /*
             * Section name resolution
             */
            if (name.is(0))
                return section;
            else {
                TemplateDataDictionary sectionData = name.dereference(0,section);
                return sectionData.getSection(new TemplateName(name));
            }
        }
    }
    public List<TemplateDataDictionary> showSection(TemplateName name){

        java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;
        if (null == sections){
            sections = new java.util.HashMap<String,List<TemplateDataDictionary>>();
            this.sections = sections;

            TemplateDataDictionary newSection = new AbstractData(this);
            List<TemplateDataDictionary> newSectionList = AbstractData.Add(null,newSection);
            sections.put(name.getComponent(0),newSectionList);
            if (name.is(0))
                return newSectionList;
            else
                return newSection.showSection(new TemplateName(name));
        }
        else {
            List<TemplateDataDictionary> section = sections.get(name.getComponent(0));
            if (null != section){
                if (name.is(0))
                    return section;
                else {
                    TemplateDataDictionary sectionData = name.dereference(0,section);
                    return sectionData.showSection(new TemplateName(name));
                }
            }
            else {
                TemplateDataDictionary newSection = new AbstractData(this);
                section = AbstractData.Add(section,newSection);
                this.sections.put(name.getComponent(0),section);
                if (name.is(0))
                    return section;
                else
                    return newSection.showSection(new TemplateName(name));
            }
        }
    }
    public TemplateDataDictionary addSection(TemplateName name){

        TemplateDataDictionary newSection = new AbstractData(this);

        java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;
        if (null == sections){
            sections = new java.util.HashMap<String,List<TemplateDataDictionary>>();
            this.sections = sections;
            List<TemplateDataDictionary> section = AbstractData.Add(null,newSection);
            sections.put(name.getComponent(0),section);
        }
        else {
            List<TemplateDataDictionary> section = sections.get(name.getComponent(0));
            if (null == section){
                section = AbstractData.Add(section,newSection);
                sections.put(name.getComponent(0),section);
            }
            else
                section = AbstractData.Add(section,newSection);
        }
        if (name.is(0))
            return newSection;
        else
            return newSection.addSection(new TemplateName(name));
    }
}
