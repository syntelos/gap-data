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
        Principal principal = req.getUserPrincipal();
        String uri = this.getUri(accept,req);
        TemplateDictionary dict = Templates.CreateDictionary();
        return this.logon(principal,uri,dict);
    }
    protected final Logon logon(Principal principal, String uri, TemplateDictionary dict){
        UserService service = this.getUserService();
        Logon logon = new Logon(principal,uri,dict,service);
        return Logon.Enter(logon);
    }
    protected final UserService getUserService(){
        return UserServiceFactory.getUserService();
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
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rep)
        throws IOException, ServletException
    {
        Store.Enter();
        Remote.Enter();
        XMessaging.Enter();
        try {
            switch (Method.Lookup(req)){
            case Method.GET:{
                long lastModified = this.getLastModified(req);
                if (0 < lastModified){
                    long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                    if (ifModifiedSince < (lastModified / 1000 * 1000)) {
                        MaybeSetLastModified(rep, lastModified);
                        this.doGet(req, rep);
                    }
                    else {
                        rep.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    }
                }
                else
                    this.doGet(req, rep);
                return;
            }
            case Method.HEAD:{
                long lastModified = this.getLastModified(req);
                if (0 < lastModified){
                    long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                    if (ifModifiedSince < (lastModified / 1000 * 1000))
                        MaybeSetLastModified(rep, lastModified);
                    else 
                        rep.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
                this.doHead(req, rep);
                return;
            }
            case Method.POST:
                this.doPost(req, rep);
                return;
            case Method.PUT:
                this.doPut(req, rep);
                return;
            case Method.DELETE:
                this.doDelete(req, rep);
                return;
            case Method.OPTIONS:
                this.doOptions(req, rep);
                return;
            case Method.TRACE:
                this.doTrace(req, rep);
                return;
            default:
                rep.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Method '"+req.getMethod()+"' not implemented");
                return;
            }
        }
        finally {
            Store.Exit();
            Remote.Exit();
            Logon.Exit();
            XMessaging.Exit();
        }
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
}
