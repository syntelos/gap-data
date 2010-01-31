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
import gap.util.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

/**
 * This core servlet is extended in {@link gap.servlet.Error} and
 * {@link gap.servlet.Site}.
 */
public class Servlet
    extends javax.servlet.http.HttpServlet
    implements Service,
               gap.data.DataInheritance.Notation
{
    public final static gap.util.Services Services = (new gap.util.Services(Servlet.class)).init();
    static {
        gap.data.BigTable.Services.init();
    }

    /**
     * Universal charset
     */
    public final static Charset UTF8 = Charset.forName("UTF-8");

    protected final static Logger Log = Logger.getLogger(Servlet.class.getName());
    /**
     * One servlet config in the classloader scope.
     */
    protected volatile static ServletConfig Config;


    private volatile gap.jbx.Function.List functionList;

    public final static class TemplateNames {
        public final static TemplateName Error = new TemplateName("error");
        public final static TemplateName ErrorException = new TemplateName("error_exception");
        public final static TemplateName ErrorExceptionJson = new TemplateName("error_exception_json");
        public final static TemplateName ErrorMessage = new TemplateName("error_message");
        public final static TemplateName ErrorMessageJson = new TemplateName("error_message_json");
        public final static TemplateName ErrorStatus = new TemplateName("error_status");
        public final static TemplateName ErrorType = new TemplateName("error_type");
        public final static TemplateName ErrorUri = new TemplateName("error_uri");
    }



    public Servlet(){
        super();
    }


    public final gap.jbx.Function.List getFunctionList(){
        gap.jbx.Function.List functionList = this.functionList;
        if (null == functionList){
            functionList = new gap.jbx.Function.List(this.getClass());
            this.functionList = functionList;
        }
        return functionList;
    }
    @Override
    public final void init(ServletConfig config) throws ServletException {

        if (null == Config)
            Config = config;

        super.init(config);
    }
    /**
     * This method injects store enter and exit, and defines an
     * efficient servlet processor for this package.
     */
    @Override
    public final void service(ServletRequest req, ServletResponse rep)
        throws IOException, ServletException
    {
        this.service( (HttpServletRequest)req, (HttpServletResponse)rep);
    }
    /**
     * This is marked final for performance, and for the integrity of
     * the stats process.
     */
    @Override
    protected final void service(HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException
    {
        Service.Routines.Enter();
        Method method  = Method.Enter(req);
        Protocol protocol = Protocol.Enter(req);
        Path path = new Path(req);
        Accept accept = new Accept(req);
        FileManager fm = new FileManager(path.get(0));
        String uri = req.getParameter("uri");
        if (null == uri){
            uri = req.getRequestURI();
        }
        Request request = null;
        try {
            Logon logon = Logon.Enter(new Logon(req.getUserPrincipal(),uri,UserServiceFactory.getUserService()));

            request = this.createRequest(req,method,protocol,path,accept,fm,logon,uri);

            Response response = this.createResponse(request,rep);
            if (null != response)
                this.service(request, response);
        }
        catch (Exception any){
            LogRecord rec = new LogRecord(Level.SEVERE,"error");
            rec.setThrown(any);
            Log.log(rec);
            if (null != request)
                this.error(request,rep,500,"Internal error",any);
            else
                this.error(req,rep,500,"Internal error",any);
        }
        finally {
            Service.Routines.Exit();
        }
    }
    /**
     * This is marked final for performance, and thinking it's
     * unlikely anyone would want to override it.  
     */
    protected final void service(Request req, Response rep)
        throws IOException, ServletException, TemplateException
    {
        Servlet servlet = req.getServlet();

        switch (req.method.type){
        case Method.GET:{
            long lastModified = this.getLastModified(req);
            if (0 < lastModified){

                long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                if (0 < ifModifiedSince){

                    lastModified /= 1000;
                    lastModified *= 1000;

                    if (ifModifiedSince < lastModified)

                        MaybeSetLastModified(rep, lastModified);

                    else {
                        rep.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                        return;
                    }
                }
            }
            if (null != servlet){
                servlet.doGet(req,rep);
                return;
            }
            else if (req.isPath("/version.txt")){
                PrintWriter out = rep.getWriter();
                out.println(gap.Version.Target+' '+gap.Version.Name+' '+gap.Version.Long);
                rep.setContentTypeText();
                return;
            }
            else {
                this.doGet(req,rep);
                return;
            }
        }
        case Method.HEAD:{
            long lastModified = this.getLastModified(req);
            if (0 < lastModified){

                long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                if (0 < ifModifiedSince){

                    lastModified /= 1000;
                    lastModified *= 1000;

                    if (ifModifiedSince < lastModified)
                        MaybeSetLastModified(rep, lastModified);
                    else {
                        rep.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                        return;
                    }
                }
            }
            rep.openDevNull();
            try {
                if (null != servlet)
                    servlet.doGet(req,rep);
                else if (req.isPath("/version.txt")){
                    PrintWriter out = rep.getWriter();
                    out.println(gap.Version.Name+' '+gap.Version.Target+' '+gap.Version.Long);
                    rep.setContentTypeText();
                }
                else 
                    this.doGet(req,rep);
            }
            finally {
                rep.setContentLength();
            }
            return;
        }
        case Method.POST:
            if (null != servlet)
                servlet.doPost(req,rep);
            else 
                this.doPost(req,rep);
            return;

        case Method.PUT:
            if (null != servlet)
                servlet.doPut(req,rep);
            else 
                this.doPut(req,rep);
            return;

        case Method.DELETE:
            if (null != servlet)
                servlet.doDelete(req,rep);
            else 
                this.doDelete(req,rep);
            return;

        case Method.OPTIONS:
            if (null != servlet)
                servlet.doOptions(req,rep);
            else 
                this.doOptions(req,rep);
            return;

        case Method.TRACE:
            if (null != servlet)
                servlet.doTrace(req,rep);
            else 
                this.doTrace(req,rep);
            return;

        default:
            if (null != servlet)
                servlet.doMethod(req,rep);
            else 
                this.doMethod(req,rep);
            return;
        }
    }

    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.hasNotSource()){
            if (req.acceptHtml()){
                rep.sendRedirect("/index.html");
                return;
            }
            else
                this.error(req,rep,404,"Not found");
        }
        else {
            try {
                TemplateRenderer template = req.getTemplate();
                if (null != template){
                    this.render(req,rep,template);
                    return ;
                }
            }
            catch (TemplateException exc){
                LogRecord rec = new LogRecord(Level.SEVERE,req.userReference);
                rec.setThrown(exc);
                Servlet.Log.log(rec);
                this.error(req,rep,500,"Template error",exc);
                return;
            }
            this.error(req,rep,404,"Not found");
        }
    }
    protected void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
        Resource resource = req.resource;
        if (null != resource){
            String op = req.getParameter("op");
            if (null == op)
                this.error(req,rep,400,"Missing request parameter 'op'");

            else if ("create".equals(op)){

                this.error(req,rep,400,"Not available to create");
            }
            else {
                Tool tool = resource.getTools(op);
                if (null != tool){
                    ToolFunction function = req.getToolFunction(this,req,rep,resource,tool);
                    if (null != function)
                        function.invoke();
                    else
                        this.error(req,rep,400,"Operator function not found");
                }
                else
                    this.error(req,rep,400,"Unrecognized request parameter 'op'");
            }
        }
        else {
            String op = req.getParameter("op");
            if (null == op)
                this.error(req,rep,400,"Missing request parameter 'op'");

            else if ("create".equals(op)){
                if (req.isMember){

                    resource = FileManager.GetCreateResource(req.path);

                    if (resource.updateFrom(req))
                        resource.save();

                    Templates.CreateTools(resource);

                    this.redirectToItem(req,rep,resource.getId());
                }
                else
                    this.error(req,rep,403,"Access not granted");
            }
            else
                this.error(req,rep,400,"Unrecognized request parameter 'op' not 'create'");
        }
    }
    protected void doPut(Request req, Response rep)
        throws ServletException, IOException
    {
        this.undefined(req,rep);
    }
    protected void doDelete(Request req, Response rep)
        throws ServletException, IOException
    {
        this.undefined(req,rep);
    }
    protected void doOptions(Request req, Response rep)
        throws ServletException, IOException
    {
        this.undefined(req,rep);
    }
    protected void doTrace(Request req, Response rep)
        throws ServletException, IOException
    {
        this.undefined(req,rep);
    }
    protected void doMethod(Request req, Response rep)
        throws IOException, ServletException
    {
        this.undefined(req,rep);
    }
    protected final void error(HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException
    {
        this.error(req, rep, 0, null, null);
    }
    protected final void error(HttpServletRequest req, HttpServletResponse rep, int status, String statusMessage)
        throws IOException, ServletException
    {
        this.error(req, rep, status, statusMessage, null);
    }
    protected final void error(HttpServletRequest req, HttpServletResponse rep, int status, String statusMessage, Throwable any)
        throws IOException, ServletException
    {
        if (req instanceof Request && rep instanceof Response){
            Request request = (Request)req;
            TemplateDataDictionary top = request;

            rep.resetBuffer();

            TemplateDataDictionary error = top.addSection(TemplateNames.Error);

            String errors_exception = Error.Attribute.ToString.Exception(req,any);
            String errors_status, errors_message;
            String errors_type = Error.Attribute.ToString.Type(req,any);
            String errors_uri = Error.Attribute.ToString.URI(req,any);
            if (0 < status){
                rep.setStatus(status,statusMessage);

                errors_status = String.valueOf(status);
                errors_message = statusMessage;
            }
            else {
                errors_status = Error.Attribute.ToString.Status(req,any);
                errors_message = Error.Attribute.ToString.Message(req,any);
            }
            error.setVariable(TemplateNames.ErrorMessage,errors_message);
            error.setVariable(TemplateNames.ErrorException,errors_exception);
            error.setVariable(TemplateNames.ErrorStatus,errors_status);

            error.setVariable(TemplateNames.ErrorType,errors_type);
            error.setVariable(TemplateNames.ErrorUri,errors_uri);

            String templateName = null;

            if (request.accept("text/html"))
                templateName = "errors.html";

            else if (request.accept("application/json")){
                error.setVariable(TemplateNames.ErrorExceptionJson,QuoteJson(errors_exception));
                error.setVariable(TemplateNames.ErrorMessageJson,QuoteJson(errors_message));
                templateName = "errors.json";
            }
            else if (request.accept("text/xml"))
                templateName = "errors.xml";

            else if (request.accept("application/xml"))
                templateName = "errors.xml";

            if (null != templateName){
                try {
                    TemplateRenderer template = Templates.GetTemplate(templateName);
                    if (null != template)
                        this.render(request, ((Response)rep), template);
                }
                catch (TemplateException exc){
                    LogRecord rec = new LogRecord(Level.SEVERE,"error");
                    rec.setThrown(exc);
                    Log.log(rec);
                }
            }
        }
        else if (0 < status)
            rep.setStatus(status,statusMessage);
    }
    protected final void undefined(Request req, Response rep)
        throws ServletException, IOException
    {
        this.error(req,rep,HttpServletResponse.SC_NOT_IMPLEMENTED, "Method '"+Method.Get()+"' not implemented");
    }
    protected final void render(Request req, Response rep, TemplateRenderer template)
        throws IOException, ServletException, TemplateException
    {
        template.render(req,rep.getWriter());

        if (req.accept("text/html"))
            rep.setContentType("text/html;charset=utf-8");

        else if (req.accept("application/json"))
            rep.setContentType("application/json");

        else if (req.accept("text/xml"))
            rep.setContentType("text/xml");

        else if (req.accept("application/xml"))
            rep.setContentType("application/xml");
    }
    protected void redirectToItem(Request req, Response rep, String id)
        throws IOException, ServletException
    {
        String path = '/'+id;
        if (req.userReference.endsWith(path))

            rep.sendRedirect(req.userReference);

        else if (req.hasBase()){
            String base = req.getBase();

            rep.sendRedirect("/"+base+path);
        }
        else
            rep.sendRedirect(path);
    }
    protected Parameters createParameters(HttpServletRequest req, Path path){
        Class<? extends gap.data.BigTable> table = null;
        Kind kind = Kind.For(path);
        if (null != kind){
            try {
                table = kind.getTableClass();
            }
            catch (ClassNotFoundException exc){
            }
        }
        return new Parameters(req,Page.DefaultCount,table);
    }
    /**
     * Must not throw an exception.  May only return a null value when
     * the subsequent call to create response will return a null
     * value -- otherwise -- must not return a null value.
     */
    protected Request createRequest(HttpServletRequest req, Method method, Protocol protocol, Path path, Accept accept,
                                    FileManager fm, Logon logon, String uri)
    {
        Parameters parameters = this.createParameters(req,path);
        return new Request(req,method,protocol,path,accept,fm,logon,uri,parameters);
    }
    /**
     * May return null to halt further processing.
     */
    protected Response createResponse(Request req, HttpServletResponse rep){
        return new Response(rep);
    }

    /*
     */
    protected final static String Safe(String string){
        if (null == string)
            return "";
        else
            return string;
    }


    protected final static String HEADER_IFMODSINCE = "If-Modified-Since";
    protected final static String HEADER_LASTMOD = "Last-Modified";
    
    protected final static void MaybeSetLastModified(HttpServletResponse rep,
                                                     long lastModified)
    {
        if (rep.containsHeader(HEADER_LASTMOD))
            return;
        else if (0 <= lastModified)
            rep.setDateHeader(HEADER_LASTMOD, lastModified);
    }
    /**
     */
    protected final static String QuoteXml(String string){
        if (null == string)
            return null;
        else {
            StringBuilder re = new StringBuilder();
            char[] cary = string.toCharArray();
            for (int cc = 0, count = cary.length; cc < count; cc++){
                char ch = cary[cc];
                switch (ch){
                case '<':
                    re.append("&lt;");
                    break;
                case '&':
                    re.append("&amp;");
                    break;
                case '>':
                    re.append("&gt;");
                    break;
                default:
                    re.append(ch);
                    break;
                }
            }
            return re.toString();
        }
    }
    /**
     */
    protected final static String QuoteJson(String string){
        if (null == string)
            return null;
        else {
            StringBuilder re = new StringBuilder();
            char[] cary = string.toCharArray();
            for (int cc = 0, count = cary.length; cc < count; cc++){
                char ch = cary[cc];
                switch (ch){
                case '\b':
                    re.append("\\b");
                    break;
                case '\r':
                    re.append("\\r");
                    break;
                case '\n':
                    re.append("\\n");
                    break;
                case '\t':
                    re.append("\\t");
                    break;
                case '\f':
                    re.append("\\f");
                    break;
                case '"':
                    re.append("\\\"");
                    break;
                case '\\':
                    re.append("\\\\");
                    break;
                case '/':
                    re.append("\\/");
                    break;
                case 0x2028:
                case 0x2029:
                    break;
                default:
                    int code = ch;
                    if ((0x20 > code)||(0x7e < code && 0xa0 > code))
                        break;
                    else if (0x7e < code){

                        re.append("\\u")
                            .append(HEX[(code >>> 12) & 0xf])
                            .append(HEX[(code >>> 8) & 0xf])
                            .append(HEX[(code >>> 4) & 0xf])
                            .append(HEX[code & 0xf]);
                        break;
                    }
                    else {
                        re.append(ch);
                        break;
                    }
                }
            }
            return re.toString();
        }
    }
    private static final char[] HEX = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

}
