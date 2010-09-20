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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This core servlet extends the java http servlet with frequently
 * used GAE/GD features.
 * 
 * It also serves the special files "/version.txt" (gap data version)
 * and "/system.txt" (system properties).  System txt service may
 * disabled by an override in a subclass, however version txt service
 * is intended common to all instances.
 */
public class Servlet
    extends javax.servlet.http.HttpServlet
    implements Service,
               gap.data.DataInheritance.Notation
{
    /**
     * Initialize configured servlet classes.
     */
    public final static gap.util.Services Services = (new gap.util.Services(Servlet.class)).init();
    static {
        gap.data.BigTable.Services.init();
    }

    /**
     * Email address handling
     */
    public final static class Email {

        private final static Pattern EMAILADDR = Pattern.compile("[\\w\\-\\.]+@[\\w\\-\\.]+", Pattern.CASE_INSENSITIVE);
        /**
         * @param string Input
         * @return Non null for recognized "valid" email address
         */
        public final static String Clean(String string){
            if (null == string || 0 == string.length())
                return null;
            else {
                Matcher m = EMAILADDR.matcher(string);
                if (m.matches())
                    return string;
                else
                    return null;
            }
        }
        /**
         * @param string Input
         * @return Recognized "valid" email address
         */
        public final static boolean IsValid(String string){
            if (null == string || 0 == string.length())
                return false;
            else {
                Matcher m = EMAILADDR.matcher(string);
                return (m.matches());
            }
        }
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
    /**
     * Error output structure
     */
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


    public String getMimeType(String filename){
        return this.getServletConfig().getServletContext().getMimeType(filename);
    }
    /**
     * @return May be null
     */
    public final String getAppsDomain(){
        String re = com.google.appengine.api.NamespaceManager.getGoogleAppsNamespace();
        if (null == re || 0 == re.length())
            return null;
        else
            return re;
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
        final String ns = this.getAppsDomain();

        Service.Routines.Enter(ns);

        Method method  = Method.Enter(req);
        Protocol protocol = Protocol.Enter(req);
        Path path = new Path(req);
        Accept accept = new Accept(req);

        FileManager fm = new FileManager(path.getComponent(0));

        String uri = req.getParameter("uri");
        if (null == uri){
            uri = req.getRequestURI();
        }
        Request request = null;
        try {
            Logon logon = Logon.Enter(new Logon(ns,req.getUserPrincipal(),uri,UserServiceFactory.getUserService()));

            request = this.createRequest(ns,req,method,protocol,path,accept,fm,logon,uri);

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
    protected boolean serviceSystemTxt(){
        return true;
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
                VersionTxt(out);
                rep.setContentTypeText();
                return;
            }
            else if (req.isPath("/system.txt") && this.serviceSystemTxt()){
                PrintWriter out = rep.getWriter();
                SystemTxt(out);
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
                    VersionTxt(out);
                    rep.setContentTypeText();
                }
                else if (req.isPath("/system.txt") && this.serviceSystemTxt()){
                    PrintWriter out = rep.getWriter();
                    SystemTxt(out);
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
    protected TemplateRenderer getTemplate(Request req)
        throws TemplateException
    {
        return req.getTemplate();
    }
    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.hasNotSource()){

            if (req.acceptHtml())

                rep.sendRedirect("/index.html");
            else
                this.error(req,rep,404,"Not found");
        }
        else 
            this.render(req,rep);
    }
    protected void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
        this.undefined(req,rep);
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
        this.error(req,rep,HttpServletResponse.SC_NOT_IMPLEMENTED, "Method '"+req.getMethod()+"' not implemented");
    }
    protected final void render(Request req, Response rep)
        throws IOException, ServletException, TemplateException
    {

        try {
            TemplateRenderer template = this.getTemplate(req);
            if (null != template){
                this.render(req,rep,template);
            }
            else {
                this.error(req,rep,404,"Not found");
            }
        }
        catch (TemplateException exc){
            LogRecord rec = new LogRecord(Level.SEVERE,req.userReference);
            rec.setThrown(exc);
            Servlet.Log.log(rec);
            this.error(req,rep,500,"Template error",exc);
        }
    }
    protected final void render(Request req, Response rep, String templateName)
        throws IOException, ServletException, TemplateException
    {
        TemplateRenderer template = Templates.GetTemplate(templateName);
        if (null != template)
            this.render(req,rep,template);
        else
            this.error(req,rep,500,"Template not found '"+templateName+"'");
    }
    protected final void render(Request req, Response rep, TemplateRenderer template)
        throws IOException, ServletException, TemplateException
    {
        try {
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
        finally {
            req.renderComplete();
        }
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
        Class<? extends gap.data.TableClass> table = null;
        {
            if (path.hasKind(0))
                table = path.getKind(0).getTableClass();
        }
        return new Parameters(req,Page.DefaultCount,table);
    }
    /**
     * Must not throw an exception.  May only return a null value when
     * the subsequent call to create response will return a null
     * value -- otherwise -- must not return a null value.
     */
    protected Request createRequest(String ns, HttpServletRequest req, Method method, Protocol protocol, Path path, 
                                    Accept accept, FileManager fm, Logon logon, String uri)
    {
        Parameters parameters = this.createParameters(req,path);
        return new Request(ns,req,method,protocol,path,accept,fm,logon,uri,parameters);
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

    protected final static void VersionTxt(PrintWriter out){

        out.printf("%s/%s%n",gap.Version.Name,gap.Version.Long);

    }
    protected final static void SystemTxt(PrintWriter out){
        try {
            for (java.util.Map.Entry ent: System.getProperties().entrySet()){

                String key = (String)ent.getKey();
                String value = (String)ent.getValue();

                out.printf("%s: %s%n",key,value);
            }
        }
        catch (Throwable t){
            out.println();
            t.printStackTrace(out);
        }
    }
}
