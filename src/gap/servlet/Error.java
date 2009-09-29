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
package gap.servlet;

import gap.service.Accept;
import gap.service.Logon;
import gap.service.Path;

import hapax.TemplateDictionary;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.LogRecord;

/**
 * Bound to path <code>'/errors/*'</code>
 */
public class Error
    extends gap.service.Servlet
{


    public Error(){
        super();
    }


    @Override
    protected TemplateDictionary doGetDefine(Path path, Accept accept, Logon logon){

        TemplateDictionary top = logon.dict;

        top.setVariable("logon","div.logon.html");

        return top;
    }
}
