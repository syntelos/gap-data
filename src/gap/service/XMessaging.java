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

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

import java.util.logging.Logger;

/**
 * 
 */
public final class XMessaging
    extends java.lang.Object
{
    public final static Logger Log = Logger.getLogger(XMessaging.class.getName());

    private final static ThreadLocal<XMPPService> XMS = new ThreadLocal<XMPPService>();

    public static void Init(){
        Log.info("[done]");
    }
    static void Enter(){
        XMPPService xms = XMS.get();
        if (null == xms){
            xms = XMPPServiceFactory.getXMPPService();
            XMS.set(xms);
        }
        else
            /*
             * A thread calling into enter twice has a bug: a
             * premature exit will be incorrect.
             */
            throw new IllegalStateException();
    }
    static void Exit(){
        XMPPService xms = XMS.get();
        if (null != xms)
            XMS.set(null);
    }
    public static XMPPService Get(){
        return XMS.get();
    }

}
