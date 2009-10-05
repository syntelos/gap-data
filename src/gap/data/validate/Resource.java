
package gap.data.validate;


import gap.data.*;
import static gap.data.Resource.Field.*;

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
@Generated(value={"gap.service.OD","odl/bean-validate.xtm"},date="2009-10-05T20:55:30.580Z",comments="gap.data")
public abstract class Resource
    extends gap.Strings
{
    public final static Object FromString(gap.data.Resource.Field field, String string){
        switch(field){
          case Key:
            return KeyFromString(string);
          case Id:
            return StringFromString(string);
          case Base:
            return StringFromString(string);
          case Name:
            return StringFromString(string);
          case Tag:
            return CategoryFromString(string);
          case ServletClassname:
            return StringFromString(string);
          case ServletSourceJava:
            return TextFromString(string);
          case ServletClassfileJvm:
            return BlobFromString(string);
          case TemplateSourceHapax:
            return TextFromString(string);
        default:
            throw new IllegalStateException(field.name());
        }
    }
    public final static String ToString(gap.data.Resource.Field field, Object value){
        switch(field){
          case Key:
            return KeyToString( (Key)value);
          case Id:
            return StringToString( (String)value);
          case Base:
            return StringToString( (String)value);
          case Name:
            return StringToString( (String)value);
          case Tag:
            return CategoryToString( (Category)value);
          case ServletClassname:
            return StringToString( (String)value);
          case ServletSourceJava:
            return TextToString( (Text)value);
          case ServletClassfileJvm:
            return BlobToString( (Blob)value);
          case TemplateSourceHapax:
            return TextToString( (Text)value);
        default:
            throw new IllegalStateException(field.name());
        }
    }
}