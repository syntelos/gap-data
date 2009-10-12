
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
@Generated(value={"gap.service.OD","odl/bean-servlet.xtm"},date="2009-10-11T17:31:49.948Z",comments="gap.data")
public class Template
    extends gap.servlet.Site
{
    public final static Class<gap.data.Template> BigTableClass = gap.data.Template.class;


    public Template(){
        super();
    }


    protected Parameters createParameters(Request req){
        return new Parameters(req,20,BigTableClass);
    }
    protected TemplateDictionary doGetDefine(Request req, Response rep){

        TemplateDictionary top = req.getTop();

        if (this.canRead(req)){

            if (req.hasPath(1) && req.hasPath(3)){

                Key parentKey = gap.data.Resource.KeyLongFor(req.getPath(1));

                gap.data.Template instance = gap.data.Template.ForShortId(parentKey,req.getPath(1));

                if (null != instance){

                    instance.dictionaryInto(top);

                    return top;
                }
            }
            else if (req.hasPath(1)){
                req.parameters.dictionaryInto(top);
                com.google.appengine.api.datastore.FetchOptions page = req.parameters.page.createFetchOptions();

                Key parentKey = gap.data.Resource.KeyLongFor(req.getPath(1));

                com.google.appengine.api.datastore.Query query = gap.data.Template.CreateQueryFor(parentKey);

                List<gap.data.Template> list = (List<gap.data.Template>)gap.data.Template.QueryN(query,page);

                for (gap.data.Template instance : list){

                    instance.dictionaryInto(top);
                }
                return top;
            }
        }
        return null;
    }

}