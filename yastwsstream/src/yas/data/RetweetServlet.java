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
package yas.data;


import gap.*;
import gap.data.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.labs.taskqueue.*;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;

/**
 * Favorites process is task driven.
 *
 * @see Retweet
 */
public final class RetweetServlet
    extends TaskServlet
{

    public RetweetServlet() {
        super();
    }


    protected void doPost(Request req, Response rep)
        throws ServletException, IOException
    {
        if (req.hasPath(0)){
            if ("history".equals(req.getPath(0))){
                /*
                 * Manual invocation to initialize source history
                 */

            }
            else
                this.undefined(req,rep);
        }
        else {
            UpdateQueueFavorites();
            /*
             * Automated invocation to read recent source history
             */

        }
    }
}
