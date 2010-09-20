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

/** 
 * @see Context
 * @see Servlet
 * @see gap.util.Main
 */
public interface Service {

    public final static class Namespace {
        public final static String People = "http://ns.opensocial.org/2008/opensocial/people";
        public final static String Groups = "http://ns.opensocial.org/2008/opensocial/groups";
        public final static String Activities = "http://ns.opensocial.org/2008/opensocial/activities";
        public final static String AppData = "http://ns.opensocial.org//2008/opensocial/appData";
        public final static String Invalidate = "http://ns.opensocial.org/2008/opensocial/cache/invalidate";
        public final static String Messages = "http://ns.opensocial.org/2008/opensocial/messages";
        public final static String Albums = "http://ns.opensocial.org/2008/opensocial/albums";
        public final static String MediaItems = "http://ns.opensocial.org/2008/opensocial/mediaItems";
    }

    public final static class Routines {

        public final static void Enter(String ns){
            Store.Enter(ns);
            Remote.Enter(ns);
            XMessaging.Enter(ns);
        }
        public final static void Exit(){
            Store.Exit();
            Remote.Exit();
            Logon.Exit();
            XMessaging.Exit();
            Method.Exit();
            FileManager.Exit();
            Request.Exit();
            Response.Exit();
        }
    }

}


