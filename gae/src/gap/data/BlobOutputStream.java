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
package gap.data;

import com.google.appengine.api.datastore.Blob;

/**
 * Convenient programming pattern to write to a Datastore Blob.
 * 
 * @author jdp
 */
public final class BlobOutputStream
    extends java.io.ByteArrayOutputStream
{

    private BigTable table;
    private Field field;


    public BlobOutputStream(BigTable table, Field field){
        super();
        if (null != table && null != field){
            this.table = table;
            this.field = field;
        }
        else
            throw new IllegalArgumentException();
    }


    public void close()
        throws java.io.IOException
    {
        byte[] data = this.toByteArray();
        Blob blob = new Blob(data);
        this.table.define(this.field,blob);
    }
}
