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
package gap.odl;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 
 * @see Class
 * @author jdp
 */
public final class Reader
    extends Object
    implements java.lang.Iterable<String>,
               java.util.Iterator<String>
{

    private BufferedReader in;

    private String next, unext;


    public Reader(java.io.File file)
        throws IOException
    {
        this(new java.io.FileReader(file));
    }
    public Reader(java.io.Reader reader)
        throws IOException
    {
        super();
        if (reader instanceof BufferedReader)
            this.in = (BufferedReader)reader;
        else
            this.in = new BufferedReader(reader);

        this.next = this.in.readLine();

        if (null == this.next)
            this.in.close();
    }


    public boolean hasNext(){
        return (null != this.next || null != this.unext);
    }
    public String next(){
        String s = this.unext;
        if (null != s){
            this.unext = null;
            return s;
        }
        s = this.next;
        if (null != s){
            try {
                String n = this.in.readLine();
                if (null != n)
                    this.next = n.trim();
                else {
                    this.in.close();
                    this.next = null;
                }
            }
            catch (IOException exc){
                this.next = null;
            }
            return s;
        }
        else
            throw new java.util.NoSuchElementException();
    }
    public void remove(){
        throw new UnsupportedOperationException();
    }
    public void unread(String line){
        if (null == this.unext)
            this.unext = line;
        else
            throw new IllegalStateException();
    }
    public java.util.Iterator<String> iterator(){
        return this;
    }
    public void close(){
        try {
            this.in.close();
        }
        catch (IOException exc){
        }
    }
}
