
package gap.data.servlet;


import gap.data.*;

import gap.Request;
import gap.Response;
import gap.data.* ;

import gap.service.Accept;
import gap.service.Error;
import gap.service.Logon;
import gap.service.Path;
import gap.service.Parameters;
import gap.service.Templates;

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
@Generated(value={"gap.service.OD","odl/bean-servlet.xtm"},date="2009-10-07T01:15:04.219Z",comments="gap.data")
public class Partner
    extends gap.servlet.Site
{
    public final static Class<? extends BigTable> BigTableClass = gap.data.Partner.class;


    public Partner(){
        super();
    }


    protected Parameters createParameters(Request req){
        return new Parameters(req,20,BigTableClass);
    }
    protected TemplateDictionary doGetDefine(Request req, Response rep){

        TemplateDictionary top = req.getTop();

        if (this.canRead(req)){

            String id = req.getPath(0);
            if (null != id){

                gap.data.Partner instance = gap.data.Partner.ForId(id);

                if (null != instance){

                    instance.dictionaryInto(top);

                    return top;
                }
                else {
                    return null;
                }
            }
            else {
                req.parameters.dictionaryInto(top);

                com.google.appengine.api.datastore.Query query = gap.data.Partner.CreateQueryFor();

                com.google.appengine.api.datastore.FetchOptions page = req.parameters.page.createFetchOptions();

                List<gap.data.Partner> list = (List<gap.data.Partner>)gap.data.Partner.QueryN(query,page);

                for (gap.data.Partner instance : list){

                    instance.dictionaryInto(top);
                }
            }
        }
        return top;
    }

}
