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

import java.util.Date;

/**
 * Tools layer under servlets in this package includes access control
 * policy function, {@link #IsAdmin IsAdmin}.
 */
public abstract class YasServlet
    extends gap.service.Servlet
{

    protected final static boolean IsAdmin(Request req, Target target){
        if (req.isLoggedIn()){
            if (null == target || null == target.getLogonId())
                return req.isAdmin;
            else
                return (req.getLogonId().equalsIgnoreCase(target.getLogonId()));
        }
        else
            return false;
    }

    public YasServlet() {
        super();
    }

}
