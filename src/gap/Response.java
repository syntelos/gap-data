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
package gap;

import gap.util.DevNullOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Created by {@link gap.service.Servlet}.
 * 
 * @author jdp
 */
public class Response 
    extends javax.servlet.http.HttpServletResponseWrapper
    implements gap.data.DataInheritance.Notation
{
    /**
     * Universal charset
     */
    public final static Charset UTF8 = Charset.forName("UTF-8");

    private final static java.lang.ThreadLocal<Response> RTL = new java.lang.ThreadLocal<Response>();

    public final static Response Get(){
        return RTL.get();
    }
    public final static void Exit(){
        RTL.set(null);
    }


    private ServletOutputStream out;
    private PrintWriter writer;


    public Response(HttpServletResponse req){
        super(req);
        this.setCharacterEncoding("UTF-8");

        RTL.set(this);
    }


    public PrintWriter openDevNull(){
        this.out = new DevNullOutputStream();
        this.writer = new PrintWriter(new OutputStreamWriter(this.out,UTF8));
        return this.writer;
    }
    public void closeDevNull(){
    }
    public void setContentLength(){

        DevNullOutputStream devNull = (DevNullOutputStream)this.out;
        int contentLength = devNull.getCount();
        if (0 < contentLength)
            this.setContentLength(contentLength);
    }
    public ServletOutputStream getOutputStream() throws IOException {
        ServletOutputStream out = this.out;
        if (null != out)
            return out;
        else
            return super.getOutputStream();
    }
    public PrintWriter getWriter() throws IOException {
        PrintWriter writer = this.writer;
        if (null != writer)
            return writer;
        else
            return super.getWriter();
    }
    
}
