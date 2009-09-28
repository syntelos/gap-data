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
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * 
 * @author jdp
 */
public class ServletCountDownOutputStream
    extends java.io.FilterOutputStream
{

    private int count;


    public ServletCountDownOutputStream(ServletResponse rep)
        throws IOException
    {
        this(rep.getOutputStream());
    }
    public ServletCountDownOutputStream(ServletOutputStream out){
        super(out);
    }


    public int getCount(){
        return this.count;
    }
    @Override
    public void write(int b) throws IOException {
        this.count += 1;
        this.out.write(b);
    }
    @Override
    public void write(byte[] bary, int ofs, int len) throws IOException {
        this.count += len;
        this.out.write(bary,ofs,len);
    }
}
