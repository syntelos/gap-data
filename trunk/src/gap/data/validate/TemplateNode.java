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
import static gap.data.TemplateNode.Field.*;

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
@Generated(value={"gap.service.OD","bean-validate"},date="2009-10-30T07:25:16.611Z")
public abstract class TemplateNode
    extends gap.Strings
{
    public final static Object FromString(gap.data.TemplateNode.Field field, String string){
        switch(field){
          case Key:
            return KeyFromString(string);
          case Id:
            return StringFromString(string);
          case NodeType:
            return StringFromString(string);
          case LineNumber:
            return IntegerFromString(string);
          case NodeContent:
            return TextFromString(string);
          case Offset:
            return IntegerFromString(string);
          case OffsetCloseRelative:
            return IntegerFromString(string);
        default:
            return null;
        }
    }
    public final static String ToString(gap.data.TemplateNode.Field field, Object value){
        switch(field){
          case Key:
            return KeyToString( (Key)value);
          case Id:
            return StringToString( (String)value);
          case NodeType:
            return StringToString( (String)value);
          case LineNumber:
            return IntegerToString( (Integer)value);
          case NodeContent:
            return TextToString( (Text)value);
          case Offset:
            return IntegerToString( (Integer)value);
          case OffsetCloseRelative:
            return IntegerToString( (Integer)value);
        default:
            return null;
        }
    }
}
