
package gap.data;


import gap.data.*;
import gap.service.Templates;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * Data bean generated from "gap.data".
 */
@Generated(value={"gap.service.OD","odl/list-long.xtm"},date="2009-10-07T18:58:40.293Z",comments="gap.data")
public final class ListLongResourcePartner
    extends gap.util.AbstractList<Partner>
    implements gap.data.List.Long<Partner>
{

    private final static long serialVersionUID = 1;

    public final static String AncestorKeyFieldName = "key";

    public final static String ParentTypeName = "Resource";

    public final static String ChildTypeName = "Partner";


    protected transient Resource parent;


    public ListLongResourcePartner(Resource parent) {
        super();
        if (null != parent){
            this.parent = parent;
            this.ancestorKeyFieldName = ListLongResourcePartner.AncestorKeyFieldName;
            this.query = Resource.CreateQueryFor();
        }
        else
            throw new IllegalArgumentException();
    }
    public ListLongResourcePartner(){
        super();
    }


    public void destroy(){
        this.parent = null;
        this.clearBuffer();
    }
    public Resource getParent(){
        Resource parent = this.parent;
        if (null == parent){
            Key parentKey = this.ancestorKey;
            if (null != parentKey){
                parent = Resource.Get(parentKey);
                if (null != parent)
                    this.parent = parent;
                else
                    throw new IllegalStateException("Missing parent, user employed wrong constructor (pre store).");
            }
            else
                throw new IllegalStateException("Missing ancestor key.");
        }
        return parent;
    }
    public void setValueClassAncestorKey(){
        this.ancestorKey = this.getParent().getClassFieldKeyValue();
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary top){

        for (Partner value: this){

            value.dictionaryInto(top);
        }
        return top;
    }
}
