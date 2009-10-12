
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
 * Generated data bean string I/O functions.
 */
@Generated(value={"gap.service.OD","odl/bean-validate.xtm"},date="2009-10-12T23:24:37.795Z")
public abstract class Person
    extends gap.Strings
{
    public final static Object FromString(oso.data.Person.Field field, String string){
        switch(field){
        default:
            throw new IllegalStateException(field.name());
        }
    }
    public final static String ToString(oso.data.Person.Field field, Object value){
        switch(field){
        default:
            throw new IllegalStateException(field.name());
        }
    }
}
