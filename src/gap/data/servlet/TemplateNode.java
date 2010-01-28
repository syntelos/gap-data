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
package gap.data.servlet;


import gap.data.*;

import gap.*;
import gap.data.* ;
import gap.service.* ;
import gap.util.* ;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

import javax.annotation.Generated;

/**
 * Generated data bean service methods.
 */
@Generated(value={"gap.service.OD","bean-servlet"},date="2010-01-28T13:44:28.361Z")
public class TemplateNode
    extends gap.servlet.Site
{
    public final static Class<gap.data.TemplateNode> BigTableClass = gap.data.TemplateNode.class;


    public TemplateNode(){
        super();
    }


    protected Parameters createParameters(Request req){
        return new Parameters(req,20,BigTableClass);
    }
}
