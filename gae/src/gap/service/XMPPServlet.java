/*
 * Gap Data
 * Copyright (C) 2011 John Pritchard
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
import gap.util.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.xmpp.*;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;

/**
 * Configure to <code>"/_ah/xmpp/*"</code>.
 */
public class XMPPServlet
    extends gap.service.Servlet
{


    public XMPPServlet() {
        super();
    }


    protected void doChat(Request req, Response rep, XMPPService xs, Message msg)
        throws ServletException, IOException
    {
    }
    protected void doSubscribe(Request req, Response rep, XMPPService xs, Subscription sub)
        throws ServletException, IOException
    {
    }
    protected void doSubscribed(Request req, Response rep, XMPPService xs, Subscription sub)
        throws ServletException, IOException
    {
    }
    protected void doUnsubscribe(Request req, Response rep, XMPPService xs, Subscription sub)
        throws ServletException, IOException
    {
    }
    protected void doUnsubscribed(Request req, Response rep, XMPPService xs, Subscription sub)
        throws ServletException, IOException
    {
    }
    protected void doAvailable(Request req, Response rep, XMPPService xs, Presence pre)
        throws ServletException, IOException
    {
    }
    protected void doUnavailable(Request req, Response rep, XMPPService xs, Presence pre)
        throws ServletException, IOException
    {
    }
    protected void doProbe(Request req, Response rep, XMPPService xs, Presence pre)
        throws ServletException, IOException
    {
    }
    @Override
        protected final void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
	/*
	 * GAE protects the URL this should be configured to, and will
	 * employ admin status on access.  Re-enforcing this here is a
	 * problem as under test there is no user.
	 */
        switch(Directive.For(req.path.getComponent(0))){
        case message:
            switch(Directive.Message.For(req.path.getComponent(1))){
            case chat:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Message m = xs.parseMessage(req);
                this.doChat(req,rep,xs,m);
            }
                break;
            case none:
            case unrecognized:
                this.error(req,rep,403,"Unrecognized request path");
            }
            break;
        case subscription:
            switch(Directive.Subscription.For(req.path.getComponent(1))){
            case subscribe:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Subscription s = xs.parseSubscription(req);
                this.doSubscribe(req,rep,xs,s);
            }
                break;
            case subscribed:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Subscription s = xs.parseSubscription(req);
                this.doSubscribed(req,rep,xs,s);
            }
                break;
            case unsubscribe:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Subscription s = xs.parseSubscription(req);
                this.doUnsubscribe(req,rep,xs,s);
            }
                break;
            case unsubscribed:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Subscription s = xs.parseSubscription(req);
                this.doUnsubscribed(req,rep,xs,s);
            }
                break;
            case none:
            case unrecognized:
                this.error(req,rep,403,"Unrecognized request path");
            }
            break;
        case presence:
            switch(Directive.Presence.For(req.path.getComponent(1))){
            case available:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Presence p = xs.parsePresence(req);
                this.doAvailable(req,rep,xs,p);
            }
                break;
            case unavailable:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Presence p = xs.parsePresence(req);
                this.doUnavailable(req,rep,xs,p);
            }
                break;
            case probe:{
                final XMPPService xs = XMPPServiceFactory.getXMPPService();
                final Presence p = xs.parsePresence(req);
                this.doProbe(req,rep,xs,p);
            }
                break;
            case none:
            case unrecognized:
                this.error(req,rep,403,"Unrecognized request path");
            }
            break;
        case none:
        case unrecognized:
            this.error(req,rep,403,"Unrecognized request path");
        }

    }

    /**
     * Lookup maps for fixed path elements
     */
    public enum Directive {
	message, presence, subscription, none, unrecognized;

	public final static Directive For(String name){
	    if (null == name)
		return none;
	    else {
		try {
		    return Directive.valueOf(name);
		}
		catch (RuntimeException exc){
		    return unrecognized;
		}
	    }
	}

	public enum Message {
	    chat, none, unrecognized;

	    public final static Message For(String name){
		if (null == name)
		    return none;
		else {
		    try {
			return Message.valueOf(name);
		    }
		    catch (RuntimeException exc){
			return unrecognized;
		    }
		}
	    }
	}
	public enum Presence {
	    available, unavailable, probe, none, unrecognized;

	    public final static Presence For(String name){
		if (null == name)
		    return none;
		else {
		    try {
			return Presence.valueOf(name);
		    }
		    catch (RuntimeException exc){
			return unrecognized;
		    }
		}
	    }
	}
	public enum Subscription {
	    subscribe, subscribed, unsubscribe, unsubscribed, none, unrecognized;

	    public final static Subscription For(String name){
		if (null == name)
		    return none;
		else {
		    try {
			return Subscription.valueOf(name);
		    }
		    catch (RuntimeException exc){
			return unrecognized;
		    }
		}
	    }
	}
    }
}
