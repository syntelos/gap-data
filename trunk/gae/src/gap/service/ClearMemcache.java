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

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import java.util.logging.Logger;

/**
 * A servlet context listener to optionally clear memcache at
 * application initialization time for changes to serialized objects.
 */
public final class ClearMemcache
    extends Object
    implements javax.servlet.ServletContextListener
{
    public final static String ScxParamClearMemcache = "gap.service.ClearMemcache";

    public final static Logger Log = Logger.getLogger(ClearMemcache.class.getName());


    public ClearMemcache(){
        super();
    }


    private void clearMemcache(ServletContext scx){
        String paramValue = scx.getInitParameter(ScxParamClearMemcache);
        if (null != paramValue && "true".equals(paramValue.trim())){
            MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
            memcache.clearAll();
            Log.info("[cleared]");
        }
        else
            Log.info("[not cleared]");
    }
    public void contextInitialized (ServletContextEvent evt){
        ServletContext scx = evt.getServletContext();
        this.clearMemcache(scx);
    }
    public void contextDestroyed (ServletContextEvent evt){
    }
}
