
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
@Generated(value={"gap.service.OD","odl/list-short.xtm"},date="2009-10-11T17:31:48.740Z",comments="gap.data")
public final class ListShortResourceImage
    extends gap.util.AbstractList<Image>
    implements gap.data.List.Short<Image>
{

    private final static long serialVersionUID = 1;

    public final static String AncestorKeyFieldName = "key";

    public final static String ParentTypeName = "Resource";

    public final static String ChildTypeName = "Image";


    protected transient Resource parent;


    public ListShortResourceImage(Resource parent) {
        super();
        if (null != parent){
            this.parent = parent;
            this.ancestorKeyFieldName = ListShortResourceImage.AncestorKeyFieldName;
            this.setValueClassAncestorKey();
        }
        else
            throw new IllegalArgumentException();
    }
    public ListShortResourceImage(){
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
        Key key = this.getParent().getClassFieldKeyValue();
        if (key != this.ancestorKey){
            this.ancestorKey = key;
            this.query = Resource.CreateQueryFor(this.ancestorKey);
        }
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary top){

        for (Image value: this){

            value.dictionaryInto(top);
        }
        return top;
    }
}
