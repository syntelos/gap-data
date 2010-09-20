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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

/**
 * An integrated context listener for subclassing.
 * 
 * @author jdp
 */
public class Context
    extends Object
    implements Service,
               javax.servlet.ServletContextListener
{

    protected final static Logger Log = Logger.getLogger(Context.class.getName());



    public Context(){
        super();
    }


    public final void contextInitialized(ServletContextEvent evt){
        ServletContext servletContext = evt.getServletContext();
        this.serviceEnter();
        try {
            this.contextInitialized(servletContext);
        }
        finally {
            this.serviceExit();
        }
    }
    protected void contextInitialized(ServletContext servletContext){
    }
    public final void contextDestroyed(ServletContextEvent evt){
        ServletContext servletContext = evt.getServletContext();
        this.serviceEnter();
        try {
            this.contextDestroyed(servletContext);
        }
        finally {
            this.serviceExit();
        }
    }
    protected void contextDestroyed(ServletContext servletContext){
    }
    protected FileManager serviceEnter(){
        Service.Routines.Enter(this.getAppsDomain());
        return (new FileManager(""));
    }
    protected void serviceExit(){
        Service.Routines.Exit();
    }
    public final String getAppsDomain(){
        String re = com.google.appengine.api.NamespaceManager.getGoogleAppsNamespace();
        if (null == re || 0 == re.length())
            return null;
        else
            return re;
    }
}
