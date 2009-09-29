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
package gap.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author jdp
 */
public class HttpServletResponseOutput
    extends javax.servlet.http.HttpServletResponseWrapper
{

    private ServletOutputStream out;

    private PrintWriter wri;


    public HttpServletResponseOutput(HttpServletResponse rep, ServletOutputStream out, PrintWriter wri)
        throws IOException
    {
        super(rep);
        this.out = out;
        this.wri = wri;
    }


    public ServletOutputStream getOutputStream() throws IOException {
        return this.out;
    } 
    public PrintWriter getWriter() throws IOException {
        return this.wri;
    }
}
