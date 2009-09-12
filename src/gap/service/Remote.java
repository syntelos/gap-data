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

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import java.util.logging.Logger;

/**
 * 
 */
public final class Remote
    extends java.lang.Object
{
    public final static Logger Log = Logger.getLogger(Remote.class.getName());

    private final static ThreadLocal<URLFetchService> UFS = new ThreadLocal<URLFetchService>();

    public static void Init(){
        Log.info("[done]");
    }
    static void Enter(){
        URLFetchService ufs = UFS.get();
        if (null == ufs){
            ufs = URLFetchServiceFactory.getURLFetchService();
            UFS.set(ufs);
        }
        else
            /*
             * A thread calling into enter twice has a bug: a
             * premature exit will be incorrect.
             */
            throw new IllegalStateException();
    }
    static void Exit(){
        URLFetchService ufs = UFS.get();
        if (null != ufs)
            UFS.set(null);
    }
    public static URLFetchService Get(){
        return UFS.get();
    }
    public static HTTPResponse Fetch(HTTPRequest req)
        throws java.io.IOException
    {
        return UFS.get().fetch(req);
    }
    public static HTTPResponse Fetch(java.net.URL url)
        throws java.io.IOException
    {
        return UFS.get().fetch(url);
    }
}
