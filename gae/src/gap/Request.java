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

import json.Json;

import com.google.appengine.api.capabilities.Capability;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;

import java.nio.charset.Charset;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by {@link gap.service.Servlet}.
 * 
 * @author jdp
 */
public class Request 
    extends gap.hapax.AbstractData
    implements HttpServletRequest,
               DataInheritance.Notation,
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

        Nil, Form, Multipart, Json, Xml;

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
        body, ns, hostname, method, protocol, path, accept, fileManager, parameters, userReference, 
            logon, logonUrl, logonText, contentType, isAdmin, isMember, isOAuth, isNotAdmin, isNotMember, isNotOAuth, version, viewer;

        public static Field For(String name){
            try {
                return Field.valueOf(name);
            }
            catch (IllegalArgumentException exc){
                return null;
            }
        }
    }
    public final static boolean IsCron(HttpServletRequest q){
        String value = q.getHeader("X-AppEngine-Cron");
        return (null != value && "true".equals(value));
    }


    public final String ns, hostname;
    public final HttpServletRequest request;
    public final Method method;
    public final Protocol protocol;
    public final Path path;
    public final Accept accept;
    public final FileManager fileManager;
    public final Parameters parameters;
    public final String userReference;
    public final Logon logon;
    public final String logonUrl, logonText;
    public final ContentType contentType;
    public final boolean isAdmin, isMember, isOAuth, isNotOAuth, isCron;
    private String bodyString;
    private Json bodyJson;
    private Person person;
    private boolean personEnabled = true;


    public Request(String ns, HttpServletRequest req, Method method, Protocol protocol, Path path, 
                   Accept accept, FileManager fm, Logon logon, String uri, Parameters parameters)
    {
        super();
        this.ns = ns;
        this.hostname = req.getHeader("Host");
        this.request = req;
        this.method = method;
        this.protocol = protocol;
        this.path = path;
        this.accept = accept;
        this.fileManager = fm;
        this.parameters = parameters;
        this.userReference = uri;
        this.logon = logon;
        this.contentType = ContentType.For(req);

        this.isAdmin = logon.serviceAdmin;
        this.isMember = logon.serviceMember;
        this.isOAuth = logon.serviceOAuth;
        this.isNotOAuth = (!logon.serviceOAuth);
        this.isCron = IsCron(req);

        if (logon.serviceMember){
            this.logonUrl = logon.getLogoutURL();
            this.logonText = logon.serviceLogon;
        }
        else {
            this.logonUrl = logon.getLoginURL();
            this.logonText = "Sign-in";
        }
        /* TODO
         * 
         * Prefer a pull on page.* and sort.* instead of the following
         * push (on *).
         * 
         * As of this writing, parameters.page.* and parameters.sort.*
         * are not hooked up.
         * 
         * Paging and Sorting should be hooked up via Store and
         * BeanData.xtm Query methods on 'Request' or 'Parameters'.
         */
        this.parameters.dictionaryInto(this);

        RTL.set(this);
    }


    /**
     * @return May be null
     */
    public final String getLogonId(){
        return this.logon.serviceLogon;
    }
    /**
     * @return May be null
     */
    public final String getAppsDomain(){
        return this.ns;
    }
    /**
     * @return May be null
     */
    public final String getUserDomain(){
        return this.logon.getUserDomain();
    }
    /**
     * @return May be null
     */
    public final String getUserEmail(){
        return this.logon.getUserEmail();
    }
    /**
     * @return May be null
     */
    public final String getUserNick(){
        return this.logon.getUserNick();
    }
    /**
     * @return Never null, one of the login or logout url for current
     * action link href.
     */
    public final String getLogonUrl(){
        return this.logonUrl;
    }
    /**
     * @return Never null, one of the login ID or text "Sign-in" for
     * current action link text.
     */
    public final String getLogonText(){
        return this.logonText;
    }
    public final boolean hasNamespace(){
        return (null != this.ns);
    }
    public final String getNamespace(){
        return this.ns;
    }
    public final boolean hasHostname(){
        return (null != this.hostname);
    }
    public final String getHostname(){
        return this.hostname;
    }
    public boolean isViewerEnabled(){

        return this.personEnabled;
    }
    public Request setViewerEnabled(){

        this.personEnabled = true;

        return this;
    }
    public Request setViewerDisabled(){

        this.personEnabled = false;

        return this;
    }
    public boolean hasViewer(){

        if (null != this.person)
            return true;
        else if (this.personEnabled)
            return this.hasViewerLogon();
        else
            return false;
    }
    public Person getViewer(){

        if (null != this.person)
            return this.person;
        else if (this.personEnabled)
            return this.getViewerLogon();
        else
            return null;
    }
    protected boolean hasViewerLogon(){

        return (this.logon.serviceMember && this.isAvailableStorageRead());
    }
    protected Person getViewerLogon(){

        if (null == this.person && this.logon.serviceMember){

            if (this.isAvailableStorageRead()){

                this.person = Person.GetCreateLongLogonId(this.logon.serviceLogon);
            }
        }
        return this.person;
    }
    public Request setViewer(Person person){
        if (null != this.person)
            throw new IllegalStateException();
        else {
            this.person = person;
            return this;
        }
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
    /** 
     * @return Template for request servlet sub path excluding servlet
     * path.
     */
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
        return this.path.hasComponent(idx);
    }
    public final String getPath(int idx){
        return this.path.getComponent(idx);
    }
    public final String getTail(){
        return this.path.getTail();
    }
    public final boolean hasSource(){
        return this.path.hasSource();
    }
    public final boolean hasNotSource(){
        return this.path.hasNotSource();
    }
    public final boolean isSourceTail(){
        return this.path.isSourceTail();
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
    public final boolean isGroupTail(){
        return this.path.isGroupTail();
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
    public final boolean isItemTail(){
        return this.path.isItemTail();
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

            return this.getParameter(name,valueAry);
        else
            return null;
    }
    /**
     * Compose a list of parameters for two equivalent syntaces:
     * multiple instances of the parameter name, and each instance of
     * the parameter name with a value list defined by a separator
     * character (string).
     * 
     * For example with separator character <code>'|'</code>, the
     * following query string expressions produce equivalent lists of
     * parameter values.
     * 
     * <pre>
     * ?s=1&amp;s=2&amp;s=3
     * ?s=1|2&amp;s=3
     * ?s=1|2|3
     * </pre>
     * 
     * @param name Parameter name
     * @param sep Optional parameter value internal separator
     */
    public final String[] getParameters(String name, String sep){
        String[] list = this.parameters.get(name);
        if (null != list && 0 != list.length){
            String[] nlist = null;
            for (String p: list){

                String[] plist = this.separateParameter(p,sep);

                nlist = Strings.Cat(nlist,plist);
            }
            return nlist;
        }
        else
            return null;
    }
    /**
     * Dereference value array, call {@link #isParameterClean} or
     * {@link #cleanParameter}.  Called from {@link #getParameter} on
     * non-empty value arrays.
     * 
     * This process is defined here to rewrite CRLF to LF, permitting
     * simple programming over text fields.  
     * 
     * Admittedly expensive, this should rather be in
     * "getTextParameter" methods.  Although who would remember that
     * those methods exist, and use them consistently.  That's
     * precisely the kind of corner case feature that I forget about,
     * rediscover two years later, and then drop as too obscure.
     * 
     * It seems best to rely on universalized text.  As defined here,
     * all CR, TAB, FF, BEL, and NUL are converted to LF in keeping
     * with a "structured HTML" / "parametric data" conception of
     * text.
     * 
     * TODO: Preserve scan info for cleaning process, as via Jauk
     * match.  The return type from isParameterClean changes with the
     * arguments to cleanParameter.
     */
    public String getParameter(String name, String[] valueAry){

        final String value = valueAry[0];
        if (this.isParameterClean(value))
            return value;
        else 
            return this.cleanParameter(value);
    }
    /**
     * @return False on finding a character value less than 0x20 and not LF
     */
    public boolean isParameterClean(String value){
        final char[] cary = value.toCharArray();
        final int count = cary.length;
        for (int cc = 0; cc < count; cc++){
            char ch = cary[cc];
            if (ch < 0x20 && '\n' != ch)
                return false;
        }
        return true;
    }
    /**
     * @return Replace CR, TAB, FF, BEL, and NUL with LF
     */
    public String cleanParameter(String value){
        final StringTokenizer strtok = new StringTokenizer(value,"\0\b\t\n\f\r",true);
        final StringBuilder strbuf = new StringBuilder();
        final int count = strtok.countTokens();
        for (int cc = 0; cc < count; cc++){

            final String tok = strtok.nextToken();

            if (1 != tok.length()){

                strbuf.append(tok);
            }
            else if (0 != strbuf.length() && '\n' == tok.charAt(0)){

                strbuf.append('\n');
            }
        }
        return strbuf.toString();
    }
    public String[] separateParameter(String value, String sep){
        final StringTokenizer strtok = new StringTokenizer(value,sep);
        final int count = strtok.countTokens();
        if (1 == count)
            return new String[]{value};
        else {
            String[] list = new String[count];

            for (int cc = 0; cc < count; cc++){

                list[cc] = strtok.nextToken();
            }
            return list;
        }
    }
    public final String getBodyString(){
        String body = this.bodyString;
        if (null == body){
            try {
                alto.io.u.Bbuf buf = new alto.io.u.Bbuf(this.getInputStream());
                body = buf.toString();
                this.bodyString = body;
            }
            catch (Exception drop){

                this.bodyString = "";
            }
        }
        return body;
    }
    public final Json getBodyJson(){
        Json body = this.bodyJson;
        if (null == body){
            try {
                body = (new json.Reader().read(this.getBodyString()));
                this.bodyJson = body;
            }
            catch (Exception drop){

                this.bodyJson = new json.NullJson();
            }
        }
        return body;
    }
    public final Selection getSelection(Kind kind){
        HttpSession session = this.request.getSession();
        Selection selection = (Selection)session.getValue(Selection.SessionKey(kind));
        if (null == selection){
            selection = new Selection(kind);
            session.putValue(selection.sessionKey,selection);
        }
        return selection;
    }

    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case body:
                return (null != this.getBodyString());
            case ns:
            case hostname:
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
                if (this.isMember){
                    if (name.has(1) && (this.hasViewer()))
                        this.getViewer().hasVariable(new TemplateName(name));
                    else
                        return true;
                }
                else
                    return false;

            case logonUrl:
                return true;
            case logonText:
                return true;
            case contentType:
                return (name.is(0) && null != this.contentType);
            case isAdmin:
                return (name.is(0) && this.isAdmin);
            case isMember:
                return (name.is(0) && this.isMember);
            case isNotAdmin:
                return (name.is(0) && this.isAdmin);
            case isNotMember:
                return (name.is(0) && this.isMember);
            case isOAuth:
                return (name.is(0) && this.isOAuth);
            case isNotOAuth:
                return (name.is(0) && this.isOAuth);
            case version:
                if (name.has(1))
                    return gap.Version.HasVariable(new TemplateName(name));
                else
                    return true;
            case viewer:
                if (this.isMember){
                    if (name.has(1) && (this.hasViewer()))
                        this.getViewer().hasVariable(new TemplateName(name));
                    else
                        return true;
                }
                else
                    return false;

            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.hasVariable(name);
        }
    }
    public String getVariable(TemplateName name){
        Field field = Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case body:
                if (name.is(0))
                    return this.getBodyString();
                else
                    return null;
            case ns:
                if (name.is(0))
                    return this.ns;
                else
                    return null;
            case hostname:
                if (name.is(0))
                    return this.hostname;
                else
                    return null;
            case method:
                if (name.is(0))
                    return this.method.name;
                else
                    return null;
            case protocol:
                if (name.is(0))
                    return this.protocol.name;
                else
                    return null;
            case path:
                if (name.is(0))
                    return this.path.getFull();
                else
                    return null;
            case accept:
                if (name.is(0))
                    return this.getHeader("Accept");
                else
                    return null;
            case fileManager:
                return null;
            case parameters:
                if (name.has(1))
                    return this.parameters.getVariable(new TemplateName(name));
                else
                    return null;
            case userReference:
                return this.userReference;
            case logon:
                if (this.isMember){
                    if (name.has(1) && (this.hasViewer()))
                        this.getViewer().getVariable(new TemplateName(name));
                    else
                        return this.logon.serviceLogon;
                }
                else
                    return null;

            case logonUrl:
                return this.logonUrl;
            case logonText:
                return this.logonText;
            case contentType:
                if (name.is(0) && null != this.contentType)
                    return this.contentType.name();
                else
                    return null;
            case isAdmin:
                if (name.is(0) && this.isAdmin)
                    return "Admin";
                else
                    return null;
            case isMember:
                if (name.is(0) && this.isMember)
                    return "Member";
                else
                    return null;
            case isNotAdmin:
                if (name.is(0) && this.isAdmin)
                    return null;
                else
                    return "NotAdmin";
            case isNotMember:
                if (name.is(0) && this.isMember)
                    return null;
                else
                    return "NotMember";

            case isOAuth:
                if (name.is(0) && this.isOAuth)
                    return "OAuth";
                else
                    return null;
            case isNotOAuth:
                if (name.is(0) && this.isOAuth)
                    return null;
                else
                    return "NotOAuth";

            case version:
                if (name.has(1))
                    return gap.Version.GetVariable(new TemplateName(name));
                else
                    return gap.Version.Short;
            case viewer:
                if (this.isMember){
                    if (name.has(1) && (this.hasViewer()))
                        this.getViewer().getVariable(new TemplateName(name));
                    else
                        return this.logon.serviceLogon;
                }
                else
                    return null;

            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getVariable(name);
        }
    }
    public void setVariable(String name, String value){

        super.setVariable(new TemplateName(name),value);
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case ns:
            case hostname:
            case method:
            case protocol:
            case path:
            case accept:
            case fileManager:
                return EmptySection;
            case parameters:
                return this.parameters.getSection(new TemplateName(name));
            case userReference:
                return EmptySection;
            case logon:
                if (this.isMember){
                    if (this.hasViewer()){
                        if (name.has(1))
                            return this.getViewer().getSection(new TemplateName(name));
                        else {
                            List.Short<TemplateDataDictionary> section = new gap.util.ArrayList();
                            section.add(this.getViewer());
                            return section;
                        }
                    }
                    else {
                        List.Short<TemplateDataDictionary> section = new gap.util.ArrayList();
                        /*
                         */
                        return section;
                    }
                }
                else
                    return null;

            case logonUrl:
            case logonText:
            case contentType:
                return EmptySection;
            case isAdmin:
                if (this.isAdmin)
                    return EmptySection;
                else
                    return null;
            case isNotAdmin:
                if (this.isAdmin)
                    return null;
                else
                    return EmptySection;
            case isMember:
                if (this.isMember)
                    return EmptySection;
                else
                    return null;
            case isNotMember:
                if (this.isMember)
                    return null;
                else
                    return EmptySection;
            case version:
                return EmptySection;

            case viewer:
                if (this.isMember){
                    if (this.hasViewer()){
                        if (name.has(1))
                            return this.getViewer().getSection(new TemplateName(name));
                        else {
                            List.Short<TemplateDataDictionary> section = new gap.util.ArrayList();
                            section.add(this.getViewer());
                            return section;
                        }
                    }
                    else {
                        List.Short<TemplateDataDictionary> section = new gap.util.ArrayList();
                        /*
                         */
                        return section;
                    }
                }
                else
                    return null;

            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getSection(name);
        }
    }

    /*
     * Servlet Request
     */
    public Object getAttribute(String name) {
        return this.request.getAttribute(name);
    }
    public java.util.Enumeration getAttributeNames() {
        return this.request.getAttributeNames();
    }    
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }
    public void setCharacterEncoding(String enc) throws java.io.UnsupportedEncodingException {
        this.request.setCharacterEncoding(enc);
    }
    public int getContentLength() {
        return this.request.getContentLength();
    }
    public boolean isContentTypeNil(){
        return (ContentType.Nil == this.contentType);
    }
    public boolean isContentTypeForm(){
        return (ContentType.Form == this.contentType);
    }
    public boolean isContentTypeMultipart(){
        return (ContentType.Multipart == this.contentType);
    }
    public boolean isContentTypeJson(){
        return (ContentType.Json == this.contentType);
    }
    public boolean isContentTypeXml(){
        return (ContentType.Xml == this.contentType);
    }
    public String getContentType() {
        return this.request.getContentType();
    }
    public javax.servlet.ServletInputStream getInputStream() throws java.io.IOException {
        return this.request.getInputStream();
    }
    public java.util.Map getParameterMap() {
        return this.request.getParameterMap();
    }
    public java.util.Enumeration getParameterNames() {
        return this.request.getParameterNames();
    }
    public String[] getParameterValues(String name) {
        return this.request.getParameterValues(name);
    }
    public String getProtocol() {
        return this.request.getProtocol();
    }
    public String getScheme() {
        return this.request.getScheme();
    }
    public String getServerName() {
        return this.request.getServerName();
    }
    public int getServerPort() {
        return this.request.getServerPort();
    }
    public java.io.BufferedReader getReader() throws java.io.IOException {
        return this.request.getReader();
    }
    public String getRemoteAddr() {
        return this.request.getRemoteAddr();
    }
    public String getRemoteHost() {
        return this.request.getRemoteHost();
    }
    public void setAttribute(String name, Object o) {
        this.request.setAttribute(name, o);
    }
    public void removeAttribute(String name) {
        this.request.removeAttribute(name);
    }
    public java.util.Locale getLocale() {
        return this.request.getLocale();
    }
    public java.util.Enumeration getLocales() {
        return this.request.getLocales();
    }
    public boolean isSecure() {
        return this.request.isSecure();
    }
    public javax.servlet.RequestDispatcher getRequestDispatcher(String path) {
        return this.request.getRequestDispatcher(path);
    }
    public String getRealPath(String path) {
        return this.request.getRealPath(path);
    }
    public int getRemotePort(){
        return this.request.getRemotePort();
    }
    public String getLocalName(){
        return this.request.getLocalName();
    }
    public String getLocalAddr(){
        return this.request.getLocalAddr();
    }
    public int getLocalPort(){
        return this.request.getLocalPort();
    }

    /*
     * HTTP Servlet Request
     */
    public String getAuthType() {
        return this.request.getAuthType();
    }
    public javax.servlet.http.Cookie[] getCookies() {
        return this.request.getCookies();
    }
    public long getDateHeader(String name) {
        return this.request.getDateHeader(name);
    }
    public String getHeader(String name) {
        return this.request.getHeader(name);
    }
    public java.util.Enumeration getHeaders(String name) {
        return this.request.getHeaders(name);
    }
    public java.util.Enumeration getHeaderNames() {
        return this.request.getHeaderNames();
    }
    public int getIntHeader(String name) {
        return this.request.getIntHeader(name);
    }
    public String getMethod() {
        return this.request.getMethod();
    }
    public String getPathInfo() {
        return this.request.getPathInfo();
    }
    public String getPathTranslated() {
        return this.request.getPathTranslated();
    }
    public String getContextPath() {
        return this.request.getContextPath();
    }
    public String getQueryString() {
        return this.request.getQueryString();
    }
    public String getRemoteUser() {
        return this.request.getRemoteUser();
    }
    public boolean isUserInRole(String role) {
        return this.request.isUserInRole(role);
    }
    public java.security.Principal getUserPrincipal() {
        return this.request.getUserPrincipal();
    }
    public String getRequestedSessionId() {
        return this.request.getRequestedSessionId();
    }
    public String getRequestURI() {
        return this.request.getRequestURI();
    }
    public StringBuffer getRequestURL() {
        return this.request.getRequestURL();
    }
    public String getServletPath() {
        return this.request.getServletPath();
    }
    public javax.servlet.http.HttpSession getSession(boolean create) {
        return this.request.getSession(create);
    }
    public javax.servlet.http.HttpSession getSession() {
        return this.request.getSession();
    }
    public boolean isRequestedSessionIdValid() {
        return this.request.isRequestedSessionIdValid();
    }
    public boolean isRequestedSessionIdFromCookie() {
        return this.request.isRequestedSessionIdFromCookie();
    }
    public boolean isRequestedSessionIdFromURL() {
        return this.request.isRequestedSessionIdFromURL();
    }
    public boolean isRequestedSessionIdFromUrl() {
        return this.request.isRequestedSessionIdFromUrl();
    }

    /*
     * Capabilities
     */
    public final boolean isAvailableStorageRead(){
        return gap.service.Servlet.IsAvailableStorageRead();
    }
    public final boolean isAvailableStorageWrite(){
        return gap.service.Servlet.IsAvailableStorageWrite();
    }
    public final boolean isAvailableBlobstore(){
        return gap.service.Servlet.IsAvailableBlobstore();
    }
    public final boolean isAvailableDatastore(){
        return gap.service.Servlet.IsAvailableDatastore();
    }
    public final boolean isAvailableDatastoreWrite(){
        return gap.service.Servlet.IsAvailableDatastoreWrite();
    }
    public final boolean isAvailableImages(){
        return gap.service.Servlet.IsAvailableImages();
    }
    public final boolean isAvailableMail(){
        return gap.service.Servlet.IsAvailableMail();
    }
    public final boolean isAvailableProspectiveSearch(){
        return gap.service.Servlet.IsAvailableProspectiveSearch();
    }
    public final boolean isAvailableMemcache(){
        return gap.service.Servlet.IsAvailableMemcache();
    }
    public final boolean isAvailableTaskqueue(){
        return gap.service.Servlet.IsAvailableTaskqueue();
    }
    public final boolean isAvailableUrlFetch(){
        return gap.service.Servlet.IsAvailableUrlFetch();
    }
    public final boolean isAvailableXmpp(){
        return gap.service.Servlet.IsAvailableXmpp();
    }
    /**
     * @return Mail service when available, otherwise null
     */
    public final MailService getMailService(){
        if (this.isAvailableMail())
            return MailServiceFactory.getMailService();
        else
            return null;
    }
}
