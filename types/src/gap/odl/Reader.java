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

import jauk.Resource;
import jauk.Match;
import jauk.Pattern;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * 
 * @see Class
 * @author jdp
 */
public final class Reader
    extends jauk.Scanner
{

    private Comment comment;


    public Reader(Resource source)
	throws IOException
    {
        super(source);
    }
    public Reader(Readable source)
	throws IOException
    {
        super(source);
    }
    public Reader(InputStream source)
	throws IOException
    {
        super(source);
    }
    public Reader(InputStream source, Charset cs)
	throws IOException
    {
        super(source,cs);
    }
    public Reader(File source)
	throws IOException
    {
        super(source);
    }
    public Reader(File source, Charset cs)
	throws IOException
    {
        super(source,cs);
    }
    public Reader(String source)
	throws IOException
    {
        super(source);
    }
    public Reader(ReadableByteChannel source)
	throws IOException
    {
        super(source);
    }
    public Reader(ReadableByteChannel source, Charset cs)
	throws IOException
    {
        super(source,cs);
    }


    public void comment(Jump jump){

	this.comment = jump.comment;
    }
    public Comment comment()
        throws IOException, Syntax
    {
	Comment comment = this.comment;

	this.comment = null;

	try {
	    return new Comment(this);
	}
	catch (Jump to){

	    return comment;
        }
    }
    public String next(Pattern pattern){

        this.comment = null;

        return super.next(pattern);
    }
}
