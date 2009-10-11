
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
@Generated(value={"gap.service.OD","odl/bean-validate.xtm"},date="2009-10-11T13:24:01.617Z",comments="gap.data")
public abstract class Resource
    extends gap.Strings
{
    public final static Object FromString(gap.data.Resource.Field field, String string){
        switch(field){
        default:
            throw new IllegalStateException(field.name());
        }
    }
    public final static String ToString(gap.data.Resource.Field field, Object value){
        switch(field){
        default:
            throw new IllegalStateException(field.name());
        }
    }
}
