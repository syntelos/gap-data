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

import gap.data.Store;
import gap.data.ServletDescriptor;
import gap.data.TemplateDescriptor;
import gap.util.DevNullOutputStream;
import gap.util.ServletCountDownOutputStream;
import gap.util.HttpServletResponseOutput;

import hapax.Template;
import hapax.TemplateException;
import hapax.TemplateDictionary;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
{
    /**
     * Universal charset
     */
    public final static Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Defined operations available via POST method.
     */
    public enum Op {
        Create, Update, Goto, Delete, Export, Import;
    }
    /**
     * Subclass usage should be qualified as
     * <code>"Servlet.Log"</code> or
     * <code>"gap.service.Servlet.Log"</code>.
     */
    protected final static Logger Log = Logger.getLogger("Servlet");
    /**
     * One servlet config in the classloader scope.
     */
    protected volatile static ServletConfig Config;


    public Servlet(){
        super();
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
        Stats stats = Stats.Enter(req);
        Method method = Method.Enter(req);
        Protocol protocol = Protocol.Enter(req);
        this.serviceEnter();
        Path path = new Path(req);
        Accept accept = new Accept(req);
        FileManager fm = new FileManager(path.get(0));
        Logon logon = null;
        try {
            logon = this.logon(path,accept,fm,req);

            this.service(method,protocol,path,accept,fm,logon,req,rep);
        }
        catch (Exception any){
            LogRecord rec = new LogRecord(Level.SEVERE,"error");
            rec.setThrown(any);
            Log.log(rec);
            this.error(path,accept,logon,req,rep,500,"Internal error.",any);
        }
        finally {
            Stats.Exit(stats,logon);
            this.serviceExit();
        }
    }
    /**
     * This is marked final for performance, and thinking it's
     * unlikely anyone would want to override it.  
     */
    protected final void service(Method method, Protocol protocol, 
                                 Path path, Accept accept, FileManager fm, Logon logon,
                                 HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException, TemplateException
    {

        Servlet servlet = fm.getServlet(path);

        /*
         */
        switch (method.type){
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
                servlet.doGet(path,accept,logon,req,rep);
                return;
            }
            else if (accept.accept("text/html") && path.hasNotSource()){
                rep.sendRedirect("index.html");
                return;
            }
            else {
                this.doGet(path,accept,logon,req,rep);
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
            final DevNullOutputStream buffer = new DevNullOutputStream();
            final PrintWriter out = new PrintWriter(new OutputStreamWriter(buffer,UTF8));

            HttpServletResponseOutput wrapper = new HttpServletResponseOutput(rep,buffer,out);

            if (null != servlet)
                servlet.doGet(path,accept,logon,req,wrapper);
            else if (accept.accept("text/html") && path.hasNotSource()){
                rep.sendRedirect("index.html");
                return;
            }
            else 
                this.doGet(path,accept,logon,req,wrapper);


            int contentLength = buffer.getCount();
            if (0 < contentLength)
                rep.setContentLength(contentLength);

            return;
        }
        case Method.POST:
            if (null != servlet){
                servlet.doPost(path,accept,logon,req,rep);
                return;
            }
            else {
                this.doPost(path,accept,logon,req,rep);
                return;
            }
        case Method.PUT:
            if (null != servlet){
                servlet.doPut(path,accept,logon,req,rep);
                return;
            }
            else {
                this.doPut(path,accept,logon,req,rep);
                return;
            }
        case Method.DELETE:
            if (null != servlet){
                servlet.doDelete(path,accept,logon,req,rep);
                return;
            }
            else {
                this.doDelete(path,accept,logon,req,rep);
                return;
            }
        case Method.OPTIONS:
            if (null != servlet){
                servlet.doOptions(path,accept,logon,req,rep);
                return;
            }
            else {
                this.doOptions(path,accept,logon,req,rep);
                return;
            }
        case Method.TRACE:
            if (null != servlet){
                servlet.doTrace(path,accept,logon,req,rep);
                return;
            }
            else {
                this.doTrace(path,accept,logon,req,rep);
                return;
            }
        default:
            if (null != servlet){
                servlet.doMethod(path,accept,logon,method,req,rep);
                return;
            }
            else {
                this.doMethod(path,accept,logon,method,req,rep);
                return;
            }
        }
    }

    protected boolean canCreate(Path path, Accept accept, Logon logon){
        return (logon.serviceAdmin);
    }
    protected boolean canUpdate(Path path, Accept accept, Logon logon){
        return (logon.serviceAdmin);
    }
    protected boolean canGoto(Path path, Accept accept, Logon logon){
        return (logon.serviceAdmin);
    }
    protected boolean canDelete(Path path, Accept accept, Logon logon){
        return (logon.serviceAdmin);
    }
    protected boolean canExport(Path path, Accept accept, Logon logon){
        return (logon.serviceAdmin);
    }
    protected boolean canImport(Path path, Accept accept, Logon logon){
        return (logon.serviceAdmin);
    }

    protected TemplateDictionary doGetDefine(Path path, Accept accept, Logon logon){
        return logon.dict;
    }
    protected Template doGetTemplate(Path path, Accept accept, Logon logon, FileManager fm)
        throws TemplateException
    {
        return null;
    }
    protected void doGet(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        TemplateDictionary top = this.doGetDefine(path,accept,logon);
        try {
            Template template = this.doGetTemplate(path,accept,logon,FileManager.Get());
            if (null != template){
                this.render(path,accept,logon,template,top,rep);
                return ;
            }
        }
        catch (TemplateException exc){
            LogRecord rec = new LogRecord(Level.SEVERE,"error");
            rec.setThrown(exc);
            Servlet.Log.log(rec);
        }
        this.error(path,accept,logon,req,rep);
    }
    protected void doPost(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        if (logon.serviceAdmin){
            Parameters parameters = new Parameters(req);
            String opStrary[] = parameters.get("op");
            String opString = (null != opStrary && 0 != opStrary.length)?(opStrary[0]):(null);
            String formatStrary[] = parameters.get("format");
            String formatString = (null != formatStrary && 0 != formatStrary.length)?(formatStrary[0]):(null);
            if (null != opString && null != formatString){
                Op op = Op.valueOf(opString);
                if (null != op){
                    boolean formatServlet = ("servlet".equals(formatString));
                    boolean formatTemplate = ("template".equals(formatString));
                    switch (op){
                    case Create:{
                        if (formatServlet)
                            this.doPostCreateServlet(path,accept,logon,parameters,req,rep);

                        else if (formatTemplate)
                            this.doPostCreateTemplate(path,accept,logon,parameters,req,rep);
                        else
                            this.doPostCreate(path,accept,logon,parameters,req,rep);
                        return;
                    }
                    case Update:{
                        if (formatServlet)
                            this.doPostUpdateServlet(path,accept,logon,parameters,req,rep);

                        else if (formatTemplate)
                            this.doPostUpdateTemplate(path,accept,logon,parameters,req,rep);
                        else
                            this.doPostUpdate(path,accept,logon,parameters,req,rep);
                        return;
                    }
                    case Goto:{
                        if (formatServlet)
                            this.doPostGotoServlet(path,accept,logon,parameters,req,rep);

                        else if (formatTemplate)
                            this.doPostGotoTemplate(path,accept,logon,parameters,req,rep);
                        else
                            this.doPostGoto(path,accept,logon,parameters,req,rep);
                        return;
                    }
                    case Delete:
                        if (formatServlet)
                            this.doPostDeleteServlet(path,accept,logon,parameters,req,rep);

                        else if (formatTemplate)
                            this.doPostDeleteTemplate(path,accept,logon,parameters,req,rep);
                        else
                            this.doPostDelete(path,accept,logon,parameters,req,rep);
                        return;

                    case Export:
                        if (formatServlet)
                            this.doPostExportServlet(path,accept,logon,parameters,req,rep);

                        else if (formatTemplate)
                            this.doPostExportTemplate(path,accept,logon,parameters,req,rep);
                        else
                            this.doPostExport(path,accept,logon,parameters,req,rep);
                        return;

                    case Import:
                        if (formatServlet)
                            this.doPostImportServlet(path,accept,logon,parameters,req,rep);

                        else if (formatTemplate)
                            this.doPostImportTemplate(path,accept,logon,parameters,req,rep);
                        else
                            this.doPostImport(path,accept,logon,parameters,req,rep);
                        return;
                    default:
                        this.undefined(path,accept,logon,Method.Get(),req,rep);
                        return;
                    }
                }
                else
                    this.error(path,accept,logon,req,rep,400,"Unrecognized value for parameter 'op': '"+opString+"'.");
            }
            else 
                this.error(path,accept,logon,req,rep,400,"Missing parameter 'op' or 'format'.");
        }
        else 
            this.error(path,accept,logon,req,rep,403,"Access not granted");
    }
    /*
     */
    protected void doPostCreateServlet(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        String requestType = req.getContentType();
        if ("application/x-www-form-urlencoded".equals(requestType)){
            /*
             * Form edit
             */
        }
        else if ("multipart/form-data".equals(requestType)){
            /*
             * File upload
             */
        }
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostCreateTemplate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        String requestType = req.getContentType();
        if ("application/x-www-form-urlencoded".equals(requestType)){
            /*
             * Form edit
             */
        }
        else if ("multipart/form-data".equals(requestType)){
            /*
             * File upload
             */
        }
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostCreate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostUpdateServlet(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        String requestType = req.getContentType();
        if ("application/x-www-form-urlencoded".equals(requestType)){
            /*
             * Form edit
             */
        }
        else if ("multipart/form-data".equals(requestType)){
            /*
             * File upload
             */
        }
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostUpdateTemplate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        String requestType = req.getContentType();
        if ("application/x-www-form-urlencoded".equals(requestType)){
            /*
             * Form edit
             */
        }
        else if ("multipart/form-data".equals(requestType)){
            /*
             * File upload
             */
        }
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostUpdate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostGotoServlet(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostGotoTemplate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostGoto(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostDeleteServlet(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostDeleteTemplate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostDelete(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostExportServlet(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostExportTemplate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostExport(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostImportServlet(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostImportTemplate(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        /*
         */
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPostImport(Path path, Accept accept, Logon logon, Parameters parameters,
                                HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPut(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doDelete(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doOptions(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doTrace(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doMethod(Path path, Accept accept, Logon logon, Method method, HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException
    {
        this.undefined(path,accept,logon,method,req,rep);
    }
    protected void error(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException
    {
        this.error(path, accept, logon, req, rep, 0, null, null);
    }
    protected void error(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep, int status, String statusMessage)
        throws IOException, ServletException
    {
        this.error(path, accept, logon, req, rep, status, statusMessage, null);
    }
    protected void error(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep, int status, String statusMessage, Throwable any)
        throws IOException, ServletException
    {

        TemplateDictionary dict;
        if (null != logon)
            dict = logon.dict;
        else
            dict = Templates.CreateDictionary();

        rep.resetBuffer();

        String from = "error";

        TemplateDictionary error = dict.addSection(from);

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
        error.putVariable("error_message",errors_message);
        error.putVariable("error_exception",errors_exception);
        error.putVariable("error_status",errors_status);

        error.putVariable("error_type",errors_type);
        error.putVariable("error_uri",errors_uri);


        String top = null;
        /*
         */
        if (accept.accept("text/html"))
            top = "errors.html";

        else if (accept.accept("application/json")){
            error.putVariable("error_exception_json",QuoteJson(errors_exception));
            error.putVariable("error_message_json",QuoteJson(errors_message));
            top = "errors.json";
        }
        else if (accept.accept("text/xml"))
            top = "errors.xml";

        else if (accept.accept("application/xml"))
            top = "errors.xml";

        /*
         */
        if (null != top){
            try {
                Template template = FileManager.Get().getTemplate(top);
                if (null != template)
                    this.render(path, accept, logon, template, error, rep);
            }
            catch (TemplateException exc){
                LogRecord rec = new LogRecord(Level.SEVERE,"error");
                rec.setThrown(exc);
                Log.log(rec);
            }
        }
    }
    protected void undefined(Path path, Accept accept, Logon logon, Method method, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.error(path,accept,logon,req,rep,HttpServletResponse.SC_NOT_IMPLEMENTED, "Method '"+method+"' not implemented");
    }
    protected void render(Path path, Accept accept, Logon logon, String templateName, HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException
    {
        try {
            Template template = FileManager.Get().getTemplate(templateName);
            if (null != template)
                this.render(path, accept, logon, template, logon.dict, rep);
            else
                this.error(path,accept,logon,req,rep,404,"Not found.");
        }
        catch (TemplateException exc){
            LogRecord rec = new LogRecord(Level.SEVERE,"error");
            rec.setThrown(exc);
            Log.log(rec);
            this.error(path,accept,logon,req,rep,500,"Internal error.",exc);
        }
    }
    protected void render(Path path, Accept accept, Logon logon, Template template, TemplateDictionary top, HttpServletResponse rep)
        throws IOException, ServletException, TemplateException
    {
        rep.setCharacterEncoding("UTF-8");
        ServletCountDownOutputStream counter = new ServletCountDownOutputStream(rep);
        PrintWriter out = (new PrintWriter(new OutputStreamWriter(counter,UTF8)));
        this.render(path,accept,logon,template,top,rep,out);
        //this.render(path,accept,logon,template,top,rep,rep.getWriter());
        out.flush();
        Stats.SetBytesDown(counter.getCount());
    }
    private void render(Path path, Accept accept, Logon logon, Template template, TemplateDictionary top, HttpServletResponse rep, PrintWriter out)
        throws IOException, ServletException, TemplateException
    {

        template.render(top,out);

        if (accept.accept("text/html"))
            rep.setContentType("text/html;charset=utf-8");

        else if (accept.accept("application/json"))
            rep.setContentType("application/json");

        else if (accept.accept("text/xml"))
            rep.setContentType("text/xml");

        else if (accept.accept("application/xml"))
            rep.setContentType("application/xml");
    }
    protected void redirectToItem(Path path, Accept accept, Logon logon, Method method, String id, HttpServletResponse rep)
        throws IOException, ServletException
    {
        if (logon.requestUrl.endsWith(id))

            rep.sendRedirect(logon.requestUrl);

        else if (path.hasBase()){
            String base = path.getBase();

            rep.sendRedirect("/"+base+'/'+id);
        }
        else
            rep.sendRedirect("/"+id);
    }
    protected Logon logon(Path path, Accept accept, FileManager fm, HttpServletRequest req){
        Logon logon = Logon.Get();
        if (null != logon)
            return logon;
        else {
            Principal principal = req.getUserPrincipal();
            String uri = this.getUri(accept,req);
            TemplateDictionary dict = Templates.CreateDictionary();

            return this.logonSimple(principal,uri,dict);
        }
    }
    protected Logon logonSimple(Principal principal, String reqUri, TemplateDictionary dict){

        Logon logon = new Logon(principal,reqUri,dict,UserServiceFactory.getUserService());

        return Logon.Enter(logon);
    }

    protected final String getUri(Accept accept, HttpServletRequest req){
        String uri = req.getParameter("uri");
        if (null != uri)
            return uri;
        else if (accept.accept("text/html"))
            return req.getRequestURI();
        else {
            uri = req.getHeader("Referer");
            if (null != uri)
                return uri;
            else
                return req.getRequestURI();
        }
    }
    protected void serviceEnter(){
        Store.Enter();
        Remote.Enter();
        XMessaging.Enter();
    }
    protected void serviceExit(){
        Store.Exit();
        Remote.Exit();
        Logon.Exit();
        XMessaging.Exit();
        Method.Exit();
        FileManager.Exit();
    }
    /*
     */
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
