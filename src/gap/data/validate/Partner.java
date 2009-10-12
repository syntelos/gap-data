
package gap.data.validate;


import gap.data.*;
import static gap.data.Partner.Field.*;

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
 * Generated data bean string I/O functions.
 */
@Generated(value={"gap.service.OD","odl/bean-validate.xtm"},date="2009-10-12T23:24:38.545Z")
public abstract class Partner
    extends gap.Strings
{
    public final static Object FromString(gap.data.Partner.Field field, String string){
        switch(field){
        default:
            throw new IllegalStateException(field.name());
        }
    }
    public final static String ToString(gap.data.Partner.Field field, Object value){
        switch(field){
        default:
            throw new IllegalStateException(field.name());
        }
    }
}
