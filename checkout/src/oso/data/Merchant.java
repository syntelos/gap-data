
package oso.data;


import gap.*;
import gap.data.*;
import gap.util.*;

import json.Json;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.*;

import java.util.Date;

/**
 * Generated once (user) bean.
 * This source file will not be overwritten unless deleted,
 * so it can be edited for extensions.
 *
 * @see MerchantData
 */
public final class Merchant
    extends MerchantData
{

    public Merchant() {
        super();
    }
    public Merchant(String identifier) {
        super( identifier);
    }
    public Merchant(Json json){
        super();
        String identifier = (String)json.getValue("identifier",String.class);
        
        if (null != identifier){
            Key key = Merchant.KeyLongIdFor( identifier);
            this.setIdentifier(identifier);
            this.setKey(key);
            this.fillFrom(gap.data.Store.Get(key));
            this.markClean();
            /*
             */
            this.fromJson(json);
            if (this.isDirty())
                this.save();
        }
        else {
            throw new IllegalArgumentException("Unable to create new");
        }
    }

    public void onread(){
    }
    public void onwrite(){
    }
    public java.io.Serializable valueOf(gap.data.Field field, boolean mayInherit){
        return (java.io.Serializable)Field.Get((Field)field,this,mayInherit);
    }
    public void define(gap.data.Field field, java.io.Serializable value){
        Field.Set((Field)field,this,value);
    }
    public void drop(){
        Delete(this);
    }
    public void clean(){
        Clean(this);
    }
    public void save(){
        Save(this);
    }
    public void store(){
        Store(this);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonIdentifier(){
        return super.toJsonIdentifier();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonIdentifier(Json json){
        return super.fromJsonIdentifier(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonMerchantId(){
        return super.toJsonMerchantId();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonMerchantId(Json json){
        return super.fromJsonMerchantId(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonMerchantKey(){
        return super.toJsonMerchantKey();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonMerchantKey(Json json){
        return super.fromJsonMerchantKey(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonCurrency(){
        return super.toJsonCurrency();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonCurrency(Json json){
        return super.fromJsonCurrency(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonTest(){
        return super.toJsonTest();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonTest(Json json){
        return super.fromJsonTest(json);
    }
}
