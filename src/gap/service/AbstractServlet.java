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
import oso.data.Person;

import hapax.TemplateDictionary;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;


public abstract class AbstractServlet
    extends javax.servlet.http.HttpServlet
{



    public AbstractServlet(){
        super();
    }


    protected final Logon logon(HttpServletRequest req, Accept accept){
        Logon logon = Logon.Get();
        if (null != logon)
            return logon;
        else {
            Principal principal = req.getUserPrincipal();
            String uri = this.getUri(accept,req);
            TemplateDictionary dict = Templates.CreateDictionary();
            return this.logon(principal,uri,dict);
        }
    }
    private final Logon logon(Principal principal, String uri, TemplateDictionary dict){
        UserService service = UserServiceFactory.getUserService();
        Logon logon = new Logon(principal,uri,dict,service);
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
    protected void serviceEnter(Method method, Protocol protocol, HttpServletRequest req, HttpServletResponse rep){
        Store.Enter();
        Remote.Enter();
        XMessaging.Enter();
    }
    protected void serviceExit(Method method, Protocol protocol, HttpServletRequest req, HttpServletResponse rep){
        Store.Exit();
        Remote.Exit();
        Logon.Exit();
        XMessaging.Exit();
        Method.Exit();
    }
    @Override
    protected final void service(HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException
    {
        Method method = Method.Enter(req);
        Protocol protocol = Protocol.Enter(req);
        this.serviceEnter(method,protocol,req,rep);
        Path path = new Path(req);
        Accept accept = new Accept(req);
        Logon logon = null;
        try {
            logon = this.logon(req,accept);

            switch (method.type){
            case Method.GET:{
                long lastModified = this.getLastModified(req);
                if (0 < lastModified){

                    long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                    if (0 < ifModifiedSince){

                        lastModified /= 1000;
                        lastModified *= 1000;

                        if (ifModifiedSince < lastModified){

                            MaybeSetLastModified(rep, lastModified);

                            this.doGet(path,accept,logon,req,rep);
                        }
                        else 
                            rep.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    }
                    else
                        this.doGet(path,accept,logon,req,rep);
                }
                else
                    this.doGet(path,accept,logon,req,rep);
                return;
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
                        else 
                            rep.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    }
                }
                this.doHead(path,accept,logon,req,rep);
                return;
            }
            case Method.POST:
                this.doPost(path,accept,logon,req,rep);
                return;
            case Method.PUT:
                this.doPut(path,accept,logon,req,rep);
                return;
            case Method.DELETE:
                this.doDelete(path,accept,logon,req,rep);
                return;
            case Method.OPTIONS:
                this.doOptions(path,accept,logon,req,rep);
                return;
            case Method.TRACE:
                this.doTrace(path,accept,logon,req,rep);
                return;
            default:
                this.doMethod(path,accept,logon,method,req,rep);
                return;
            }
        }
        finally {
            this.serviceExit(method,protocol,req,rep);
        }

    }
    protected void doGet(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doHead(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void doPost(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep)
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
    protected void error(Path path, Accept accept, Logon logon, HttpServletRequest req, HttpServletResponse rep, int status, String statusMessage)
        throws IOException, ServletException
    {
        this.undefined(path,accept,logon,Method.Get(),req,rep);
    }
    protected void undefined(Path path, Accept accept, Logon logon, Method method, HttpServletRequest req, HttpServletResponse rep)
        throws ServletException, IOException
    {
        rep.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Method '"+method+"' not implemented");
    }
    protected void render(Path path, Accept accept, Logon logon, TemplateDictionary top, HttpServletResponse rep)
        throws IOException, ServletException
    {
        
    }
    protected void redirectToItem(Path path, Accept accept, Logon logon, Method method, String id, HttpServletResponse rep)
        throws IOException, ServletException
    {

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
}
