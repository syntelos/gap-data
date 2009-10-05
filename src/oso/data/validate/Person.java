
package oso.data.validate;


import oso.data.*;
import static oso.data.Person.Field.*;

import gap.service.Accept;
import gap.service.Error;
import gap.service.Logon;
import gap.service.Path;
import gap.service.Parameters;
import gap.service.Templates;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import javax.annotation.Generated;

/**
 * Data validation functions.
 */
@Generated(value={"gap.service.OD","odl/bean-validate.xtm"},date="2009-10-05T20:55:25.122Z",comments="oso.data")
public abstract class Person
    extends gap.Strings
{
    public final static Object FromString(oso.data.Person.Field field, String string){
        switch(field){
          case Key:
            return KeyFromString(string);
          case Id:
            return StringFromString(string);
          case LogonId:
            return StringFromString(string);
        default:
            throw new IllegalStateException(field.name());
        }
    }
    public final static String ToString(oso.data.Person.Field field, Object value){
        switch(field){
          case Key:
            return KeyToString( (Key)value);
          case Id:
            return StringToString( (String)value);
          case LogonId:
            return StringToString( (String)value);
        default:
            throw new IllegalStateException(field.name());
        }
    }
}
