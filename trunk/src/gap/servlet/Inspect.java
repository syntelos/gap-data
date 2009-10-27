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

import gap.*;
import gap.util.*;

import javax.servlet.ServletException;

import java.io.IOException;

/**
 * Bound to path <code>'/inspect/*'</code> for syntax highlighted java code display.
 */
public class Inspect
    extends Site
{

    @Override
    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        String json = Gson.ToJson(req);
        rep.println(json);
        rep.setContentTypeJson();
        return;
    }
}