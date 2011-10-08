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

import com.google.appengine.api.channel.*;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * Configure to <code>"/_ah/channel/*"</code> with channel presence
 * service enabled (in <code>appengine-web.xml</code>) for channel
 * presence events.
 */
public class ChannelPresenceServlet
    extends gap.service.Servlet
{


    public ChannelPresenceServlet() {
        super();
    }



    protected void doConnected(Request req, Response rep, ChannelService svc, ChannelPresence presence)
        throws ServletException, IOException
    {
    }
    protected void doDisconnected(Request req, Response rep, ChannelService svc, ChannelPresence presence)
        throws ServletException, IOException
    {
    }
    @Override
    protected final void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
	/*
	 * GAE protects the URL this should be configured to.
	 */
        switch(Directive.For(req.path.getComponent(1))){

        case channel:
            switch(Directive.Presence.For(req.path.getComponent(2))){
            case connected:{
                final ChannelService xs = ChannelServiceFactory.getChannelService();
                final ChannelPresence p = xs.parsePresence(req);
                this.doConnected(req,rep,xs,p);
            }
                break;
            case disconnected:{
                final ChannelService xs = ChannelServiceFactory.getChannelService();
                final ChannelPresence p = xs.parsePresence(req);
                this.doDisconnected(req,rep,xs,p);
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
	channel, none, unrecognized;

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

	public enum Presence {
	    connected, disconnected, none, unrecognized;

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
    }

}
