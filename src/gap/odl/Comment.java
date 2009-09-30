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

import gap.service.od.ImportDescriptor;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @see Class
 * @author jdp
 */
public final class Comment
    extends Object
{
    public final static Pattern Line = Pattern.compile("\\s*#\\s*.*");
    public final static Pattern MultiLine = Pattern.compile("\\s\\/\\*.*\\*\\/",Pattern.MULTILINE);


    public final String text;


    public Comment(Reader reader)
        throws IOException, Syntax
    {
        super();
        String text = reader.getNext(Line);
        if (null != text)
            this.text = text;
        else {
            text = reader.getNext(MultiLine);
            if (null != text)
                this.text = text;
            else
                throw new Jump();
        }
    }

}
