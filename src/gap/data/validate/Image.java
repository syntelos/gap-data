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
package gap.data.validate;


import gap.data.*;
import static gap.data.Image.Field.*;

import gap.service.Accept;
import gap.service.Error;
import gap.service.Logon;
import gap.service.Path;
import gap.service.Parameters;
import gap.service.Templates;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated data bean string I/O functions.
 */
@Generated(value={"gap.service.OD","BeanValidate.java"},date="2010-01-30T19:07:59.161Z")
public abstract class Image
    extends gap.Strings
{
    public final static Object FromString(gap.data.Image.Field field, String string){
        switch(field){
          case Key:
            return KeyFromString(string);
          case Id:
            return StringFromString(string);
          case Base:
            return StringFromString(string);
          case Name:
            return StringFromString(string);
          case LastModified:
            return LongFromString(string);
          case ContentType:
            return StringFromString(string);
          case Bytes:
            return BlobFromString(string);
        default:
            return null;
        }
    }
    public final static String ToString(gap.data.Image.Field field, Object value){
        switch(field){
          case Key:
            return KeyToString( (Key)value);
          case Id:
            return StringToString( (String)value);
          case Base:
            return StringToString( (String)value);
          case Name:
            return StringToString( (String)value);
          case LastModified:
            return LongToString( (Long)value);
          case ContentType:
            return StringToString( (String)value);
          case Bytes:
            return BlobToString( (Blob)value);
        default:
            return null;
        }
    }
}
