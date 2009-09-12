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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * These functions are used by {@link BigTable} for the storage
 * translation of unindexed types in the google datastore.
 * 
 * The serialization protocol supports data bean fields of all
 * serializable types including the google datastore data types
 * (e.g. Blob).
 * 
 * @author jdp
 */
public final class Serialize
    extends java.lang.Object
{
    /**
     * @see BigTable#fillFrom
     */
    public final static java.io.Serializable From(Field field, Object blob){
        return From(field, (Blob)blob);
    }
    public final static java.io.Serializable From(Field field, Blob blob){
        if (null != blob){
            try {
                ByteArrayInputStream buffer = new ByteArrayInputStream(blob.getBytes());
                ObjectInputStream in = new ObjectInputStream(buffer);
                return (java.io.Serializable)in.readObject();
            }
            catch (java.lang.ClassNotFoundException exc){
                throw new java.lang.RuntimeException(field.getFieldName(),exc);
            }
            catch (java.io.IOException exc){
                throw new java.lang.RuntimeException(field.getFieldName(),exc);
            }
        }
        else
            return null;
    }
    /**
     * @see BigTable#fillTo
     */
    public final static Blob To(Field field, java.io.Serializable object){
        if (null != object){
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(buffer);
                out.writeObject(object);
                return new Blob(buffer.toByteArray());
            }
            catch (java.io.IOException exc){
                throw new java.lang.RuntimeException(field.getFieldName(),exc);
            }
        }
        else
            return null;
    }

}
