
package gap.data.servlet;


import gap.data.*;

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
@Generated(value={"gap.service.OD","odl/bean-servlet.xtm"},date="2009-10-12T10:36:02.832Z",comments="gap.data")
public class Partner
    extends gap.servlet.Site
{
    public final static Class<gap.data.Partner> BigTableClass = gap.data.Partner.class;


    public Partner(){
        super();
    }


    protected Parameters createParameters(Request req){
        return new Parameters(req,20,BigTableClass);
    }
    protected TemplateDictionary doGetDefine(Request req, Response rep){

        TemplateDictionary top = req.getTop();

        if (this.canRead(req)){

            if (req.hasPath(1) && req.hasPath(2)){

                Key parentKey = gap.data.Resource.KeyLongFor(req.getPath(1));

                gap.data.Partner instance = gap.data.Partner.ForLongId(parentKey,req.getPath(2));

                if (null != instance){

                    instance.dictionaryInto(top);

                    return top;
                }
            }
            else {
                req.parameters.dictionaryInto(top);
                com.google.appengine.api.datastore.FetchOptions page = req.parameters.page.createFetchOptions();

                com.google.appengine.api.datastore.Query query = gap.data.Partner.CreateQueryFor();

                List<gap.data.Partner> list = (List<gap.data.Partner>)gap.data.Partner.QueryN(query,page);

                for (gap.data.Partner instance : list){

                    instance.dictionaryInto(top);
                }
                return top;
            }
        }
        return null;
    }

}
