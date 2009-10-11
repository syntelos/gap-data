
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
@Generated(value={"gap.service.OD","odl/bean-servlet.xtm"},date="2009-10-11T13:24:02.104Z",comments="gap.data")
public class Image
    extends gap.servlet.Site
{
    public final static Class<gap.data.Image> BigTableClass = gap.data.Image.class;


    public Image(){
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

                gap.data.Image instance = gap.data.Image.ForLongId(parentKey,req.getPath(1));

                if (null != instance){

                    instance.dictionaryInto(top);

                    return top;
                }
            }
            else {
                req.parameters.dictionaryInto(top);
                com.google.appengine.api.datastore.FetchOptions page = req.parameters.page.createFetchOptions();

                com.google.appengine.api.datastore.Query query = gap.data.Image.CreateQueryFor();

                List<gap.data.Image> list = (List<gap.data.Image>)gap.data.Image.QueryN(query,page);

                for (gap.data.Image instance : list){

                    instance.dictionaryInto(top);
                }
                return top;
            }
        }
        return null;
    }

}
