
package oso.data.servlet;


import oso.data.*;

import gap.*;
import gap.data.* ;
import gap.service.* ;
import gap.util.* ;

import hapax.TemplateDictionary;
import hapax.TemplateException;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

import javax.annotation.Generated;

/**
 * Data binding methods.
 */
@Generated(value={"gap.service.OD","odl/bean-servlet.xtm"},date="2009-10-12T10:36:02.193Z",comments="oso.data")
public class Person
    extends gap.servlet.Site
{
    public final static Class<oso.data.Person> BigTableClass = oso.data.Person.class;


    public Person(){
        super();
    }


    protected Parameters createParameters(Request req){
        return new Parameters(req,20,BigTableClass);
    }
    protected TemplateDictionary doGetDefine(Request req, Response rep){

        TemplateDictionary top = req.getTop();

        if (this.canRead(req)){

            if (req.hasPath(1)){

                oso.data.Person instance = oso.data.Person.ForLongId(req.getPath(1));

                if (null != instance){

                    instance.dictionaryInto(top);

                    return top;
                }
            }
            else {
                req.parameters.dictionaryInto(top);
                com.google.appengine.api.datastore.FetchOptions page = req.parameters.page.createFetchOptions();

                com.google.appengine.api.datastore.Query query = oso.data.Person.CreateQueryFor();

                List<oso.data.Person> list = (List<oso.data.Person>)oso.data.Person.QueryN(query,page);

                for (oso.data.Person instance : list){

                    instance.dictionaryInto(top);
                }
                return top;
            }
        }
        return null;
    }

}
