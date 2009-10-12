
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
@Generated(value={"gap.service.OD","odl/bean-servlet.xtm"},date="2009-10-12T10:36:03.571Z",comments="gap.data")
public class Tool
    extends gap.servlet.Site
{
    public final static Class<gap.data.Tool> BigTableClass = gap.data.Tool.class;


    public Tool(){
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

                gap.data.Tool instance = gap.data.Tool.ForShortId(parentKey,req.getPath(2));

                if (null != instance){

                    instance.dictionaryInto(top);

                    return top;
                }
            }
            else if (req.hasPath(1)){
                req.parameters.dictionaryInto(top);
                com.google.appengine.api.datastore.FetchOptions page = req.parameters.page.createFetchOptions();

                Key parentKey = gap.data.Resource.KeyLongFor(req.getPath(1));

                com.google.appengine.api.datastore.Query query = gap.data.Tool.CreateQueryFor(parentKey);

                List<gap.data.Tool> list = (List<gap.data.Tool>)gap.data.Tool.QueryN(query,page);

                for (gap.data.Tool instance : list){

                    instance.dictionaryInto(top);
                }
                return top;
            }
        }
        return null;
    }

}
