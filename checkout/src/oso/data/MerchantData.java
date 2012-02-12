/*
 * Gap Data
 * Copyright (C) 2009 John Pritchard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package oso.data;


import gap.*;
import gap.data.*;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;
import gap.util.*;

import json.Json;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.BlobKey;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated bean data binding.
 *
 * @see Merchant
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2012-02-12T22:03:18.272Z")
public abstract class MerchantData
    extends gap.data.BigTable
    implements DataInheritance<Merchant>
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("Merchant","oso.data","Merchant","/merchants");

    public final static String ClassName = "Merchant";

    public final static String DefaultSortBy = "identifier";


    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Merchant.class);
    }
    /**
     * @see gap.data.Kind#pathto()
     */
    public final static String PathTo(){
        return KIND.pathto();
    }
    public final static String PathTo(String subpath){
        return KIND.pathto(subpath);
    }

    /**
     * Long instance key without parent key
     */
    public final static Key KeyLongIdFor(String identifier){
        String id = Merchant.IdFor( identifier);
        return KeyLongFor(id);
    }
    /**
     * Used by gap.data.Kind
     *
     * Calls {@link #KeyLongIdFor}
     */
    public final static Key KeyIdFor(Object... args){
        return Merchant.KeyLongIdFor((String)args[0]);
    }
    /**
     * Used by setId
     *
     * Calls {@link #KeyLongFor}
     */
    public final static Key KeyFor(Object... args){
        return Merchant.KeyLongFor( (String)args[0]);
    }
    /**
     * Identifier for unique fields
     */
    public final static String IdFor(String identifier){
        if (null != identifier){
            String identifierString = identifier;
            return gap.data.Hash.For(identifierString);
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup
     */
    public final static Merchant ForLongIdentifier(String identifier){
        if (null != identifier){
            Key key = Merchant.KeyLongIdFor( identifier);
            Merchant instance = (Merchant)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = Merchant.CreateQueryFor(key);
                return (Merchant)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup or create
     */
    public static Merchant GetCreateLong(String identifier){
        return GetCreateLongIdentifier( identifier);
    }
    /**
     * Instance lookup or create
     */
    public final static Merchant GetCreateLongIdentifier(String identifier){
        Merchant merchant = Merchant.ForLongIdentifier( identifier);
        if (null == merchant){
            merchant = new Merchant( identifier);
            merchant = (Merchant)gap.data.Store.PutClass(merchant);
        }
        return merchant;
    }


    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static Merchant ForLongId(String id){
        if (null != id){
            Key key = Merchant.KeyLongFor(id);
            Merchant instance = (Merchant)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = Merchant.CreateQueryFor(key);
                return (Merchant)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Used by gap.data.Kind
     */
    public final static Merchant Get(Key key){
        if (null != key){
            Merchant instance = (Merchant)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = Merchant.CreateQueryFor(key);
                return (Merchant)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Key GetKey(Key key){
        if (null != key){
            Query q = Merchant.CreateQueryFor(key);
            return gap.data.Store.Query1Key(q);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Merchant FromObject(Object value){
        if (null == value)
            return null;
        else if (value instanceof Merchant)
            return (Merchant)value;
        else if (value instanceof Key)
            return Get( (Key)value);
        else if (value instanceof String){
            /*
             * TODO: ilarg: not key.enc; Key For ID.
             */
            Key key = gap.Strings.KeyFromString( (String)value);
            return Get(key);
        }
        else
            throw new IllegalArgumentException(value.getClass().getName());
    }


    /**
     * Anonymous random key cannot be mapped to network identifier
     * @see Merchant#IdFor
     *
     * Test for uniqueness and iterate under collisions.
     */
    public final static Key NewRandomKeyLong(){
        /*
         * Source matter for data local uniqueness
         */
        long matter = (gap.data.Hash.Djb64(ClassName) ^ (serialVersionUID<<3) ^ serialVersionUID);
        /*
         * Random matter for network global uniqueness
         */
        java.util.Random random = new java.util.Random();
        do {
            matter ^= random.nextLong();
            String idString = gap.data.Hash.Hex(matter);
            Key key = KeyFactory.createKey(KIND.getName(),idString);
            if (null == GetKey(key))
                return key;
        }
        while (true);
    }
    /**
     * Drop the instance from memcache and datastore.
     */
    public final static void Delete(Merchant instance){
        if (null != instance){

            Delete(instance.getKey());
        }
    }
    /**
     * Drop the instance from memcache and datastore.
     */
    public final static void Delete(Key instanceKey){
        if (null != instanceKey){

            gap.data.Store.Delete(instanceKey);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Merchant instance){
        if (null != instance){

            gap.data.Store.Clean(instance.getKey());
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Merchant instance){
        if (null != instance){

            gap.data.Store.PutClass(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Merchant instance){
        if (null != instance){

            gap.data.Store.PutClass(instance);
        }
    }
    /**
     * Default sort
     */
    public final static Query CreateQueryFor(){
        return new Query(KIND.getName()).addSort(DefaultSortBy);
    }
    /**
     * Default sort
     */
    public final static Query CreateQueryFor(Key key){
        return new Query(KIND.getName(),key).addSort(DefaultSortBy);
    }
    /**
     * Filter ops
     */
    public final static Query CreateQueryFor(Filter filter){
        Query query = new Query(KIND.getName());
        return filter.update(query);
    }
    
    public final static Merchant Query1(Query query){
        if (null != query)
            return (Merchant)gap.data.Store.Query1Class(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator<Merchant> ListPage(Page page){

        return Merchant.QueryN(Merchant.CreateQueryFor(),page);
    }
    public final static BigTableIterator<Merchant> QueryN(Query query, Page page){
        if (null != query && null != page)
            return gap.data.Store.QueryNClass(query,page);
        else
            throw new IllegalArgumentException();
    }
    public final static Key QueryKey1(Query query){
        if (null != query)
            return gap.data.Store.Query1Key(query);
        else
            throw new IllegalArgumentException();
    }
    public final static List.Primitive<Key> QueryNKey(Query query, Page page){
        if (null != query)
            return gap.data.Store.QueryNKey(query,page);
        else
            throw new IllegalArgumentException();
    }
    public final static List.Primitive<Key> QueryNKey(Query query){
        if (null != query)
            return gap.data.Store.QueryNKey(query);
        else
            throw new IllegalArgumentException();
    }

    /**
     * Persistent fields' binding for {@link Merchant}
     */
    public static enum Field
        implements gap.data.Field<Merchant.Field>
    {
        InheritFromKey("inheritFromKey",Type.Primitive),
        Key("key",Type.Primitive),
        Id("id",Type.Primitive),
        Identifier("identifier",Type.Primitive),
        MerchantId("merchantId",Type.Primitive),
        MerchantKey("merchantKey",Type.Primitive),
        Currency("currency",Type.Primitive),
        Test("test",Type.Primitive);

        private final static lxl.Map<String,Field> FieldName = new lxl.Map<String,Field>();
        public static final String[] AllNames;
        static {
            Field[] allFields = Field.values();
            int count = allFields.length;
            String[] names = new String[count];
            for (int cc = 0; cc < count; cc++) {
                Field field = allFields[cc];
                String fieldName = field.getFieldName();
                names[cc] = fieldName;
                FieldName.put(fieldName,field);
            }
            AllNames = names;
        }
        public static Field getField(String name) {
            return FieldName.get(name);
        }
        public static Field For(String name) {
            Field field = FieldName.get(name);
            if (null == field)
                try {
                    return Field.valueOf(name);
                }
                catch (IllegalArgumentException notFound){
                    return null;
                }
            else
                return field;
        }
        /**
         * Field statistics are maintained for persistent fields exclusively
         */
        public final static class Statistics
            extends gap.data.Field.Statistics<Merchant.Field>
        {
            public Statistics(){
                super(Merchant.Field.class);
            }
        }
        /**
         * Dynamic binding operator for field data type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static Object Get(Field field, Merchant instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Identifier:
                return instance.getIdentifier(mayInherit);
            case MerchantId:
                return instance.getMerchantId(mayInherit);
            case MerchantKey:
                return instance.getMerchantKey(mayInherit);
            case Currency:
                return instance.getCurrency(mayInherit);
            case Test:
                return instance.getTest(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Merchant");
            }
        }
        /**
         * Dynamic binding operator for field data type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static boolean Set(Field field, Merchant instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case Identifier:
                return instance.setIdentifier(gap.Objects.StringFromObject(value));
            case MerchantId:
                return instance.setMerchantId(gap.Objects.StringFromObject(value));
            case MerchantKey:
                return instance.setMerchantKey(gap.Objects.StringFromObject(value));
            case Currency:
                return instance.setCurrency(gap.Objects.StringFromObject(value));
            case Test:
                return instance.setTest(gap.Objects.BooleanFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in Merchant");
            }
        }
        /**
         * Dynamic binding operator for field storage type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static java.io.Serializable Storage(Field field, Merchant instance){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Identifier:
                return instance.getIdentifier(MayNotInherit);
            case MerchantId:
                return instance.getMerchantId(MayNotInherit);
            case MerchantKey:
                return instance.getMerchantKey(MayNotInherit);
            case Currency:
                return instance.getCurrency(MayNotInherit);
            case Test:
                return instance.getTest(MayNotInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Merchant");
            }
        }
        /**
         * Dynamic binding operator for field storage type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static void Storage(Field field, Merchant instance, java.io.Serializable value){
            switch(field){
            case InheritFromKey:
                instance.setInheritFromKey( (Key)value);
                return;
            case Key:
                instance.setKey( (Key)value);
                return;
            case Id:
                instance.setId( (String)value);
                return;
            case Identifier:
                instance.setIdentifier( (String)value);
                return;
            case MerchantId:
                instance.setMerchantId( (String)value);
                return;
            case MerchantKey:
                instance.setMerchantKey( (String)value);
                return;
            case Currency:
                instance.setCurrency( (String)value);
                return;
            case Test:
                instance.setTest( (Boolean)value);
                return;
            default:
                throw new IllegalArgumentException(field.toString()+" in Merchant");
            }
        }


        public final static class List
            extends gap.util.ArrayList<Merchant.Field>
        {
            public List(){
                super();
            }
            public List(Field[] fields){
                super(fields);
            }
            public List(Iterable<Field> fields){
                super();
                for (Field field : fields)
                    this.add(field);
            }
        }


        private final String fieldName;

        private final Type fieldType;

        private final boolean fieldTypePrimitive, fieldTypeBigTable, fieldTypeCollection;

        private final boolean fieldNameKeyOrId;


        Field(String fieldName, Type fieldType){
            if (null != fieldName && null != fieldType){
                this.fieldName = fieldName;
                this.fieldType = fieldType;
                this.fieldNameKeyOrId = BigTable.IsKeyOrId(fieldName);
                /*
                 * Using a switch here causes a null pointer
                 * initializing the switch map.
                 */
                if (Type.Primitive == fieldType){
                    this.fieldTypePrimitive = true;
                    this.fieldTypeBigTable = false;
                    this.fieldTypeCollection = false;
                }
                else if (Type.BigTable == fieldType){
                    this.fieldTypePrimitive = false;
                    this.fieldTypeBigTable = true;
                    this.fieldTypeCollection = false;
                }
                else if (Type.Collection == fieldType){
                    this.fieldTypePrimitive = false;
                    this.fieldTypeBigTable = false;
                    this.fieldTypeCollection = true;
                }
                else if (Type.PrimitiveCollection == fieldType){
                    this.fieldTypePrimitive = true;
                    this.fieldTypeBigTable = false;
                    this.fieldTypeCollection = true;
                }
                else
                    throw new IllegalStateException("Unimplemented field type "+fieldType);
            }
            else
                throw new IllegalStateException();
        }


        public String getFieldName(){
            return this.fieldName;
        }
        public Type getFieldType(){
            return this.fieldType;
        }
        public boolean isFieldTypePrimitive(){
            return this.fieldTypePrimitive;
        }
        public boolean isNotFieldTypePrimitive(){
            return (!this.fieldTypePrimitive);
        }
        public boolean isFieldTypeBigTable(){
            return this.fieldTypeBigTable;
        }
        public boolean isNotFieldTypeBigTable(){
            return (!this.fieldTypeBigTable);
        }
        public boolean isFieldTypeCollection(){
            return this.fieldTypeCollection;
        }
        public boolean isNotFieldTypeCollection(){
            return (!this.fieldTypeCollection);
        }
        public boolean isFieldNameKeyOrId(){
            return this.fieldNameKeyOrId;
        }
        public boolean isNotFieldNameKeyOrId(){
            return (!this.fieldNameKeyOrId);
        }
        public String toString(){
            return this.fieldName;
        }
    }

    private transient Merchant.Field.Statistics fieldStatistics = new Merchant.Field.Statistics();

    private transient Merchant inheritFrom;


    private String identifier;
    private String merchantId;
    private String merchantKey;
    private String currency;
    private Boolean test;




    protected MerchantData() {
        super();
    }
    protected MerchantData(String identifier) {
        super();
        this.setIdentifier(identifier);
        {
            final String id = Merchant.IdFor(identifier);
            final Key key = Merchant.KeyLongFor(id);
            this.setKey(key);
        }
    }


    private Merchant.Field.Statistics fieldStatistics(){
        Merchant.Field.Statistics fieldStatistics = this.fieldStatistics;
        if (null == fieldStatistics){
            fieldStatistics = new Merchant.Field.Statistics();
            this.fieldStatistics = fieldStatistics;
        }
        return fieldStatistics;
    }
    public void destroy(){
        this.inheritFrom = null;
        this.identifier = null;
        this.merchantId = null;
        this.merchantKey = null;
        this.currency = null;
        this.test = null;
    }
    public final String getId(){

        String id = Merchant.IdFor(KIND.name, this.key);
        if (null != id)
            return id;
        else
            return Merchant.IdFor(this.identifier);
    }
    public final boolean setId(String id){
        if (null == id){
            if (null != this.key){
                this.key = null;
                return true;
            }
            else
                return false;
        }
        else if (null == this.key){
            this.key = Merchant.KeyLongFor(id);
            return true;
        }
        else
            return false;
    }
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final Merchant getInheritFrom(){
        Merchant inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Merchant.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(Merchant ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){

            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(Merchant ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){

            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean hasIdentifier(boolean mayInherit){
        return (null != this.getIdentifier(mayInherit));
    }
    public final boolean hasNotIdentifier(boolean mayInherit){
        return (null == this.getIdentifier(mayInherit));
    }
    public final boolean dropIdentifier(){
        if (null != this.identifier){
            this.fieldStatistics().markDirty(Merchant.Field.Identifier);
            this.identifier = null;
            return true;
        }
        else
            return false;
    }
    public final String getIdentifier(){
        return this.identifier;
    }
    public final String getIdentifier(boolean mayInherit){
        return this.getIdentifier();
    }
    public final boolean setIdentifier(String identifier){
        if (IsNotEqual(this.identifier,identifier)){
            this.fieldStatistics().markDirty(Merchant.Field.Identifier);
            this.identifier = identifier;
            return true;
        }
        else
            return false;
    }
    public final boolean hasMerchantId(boolean mayInherit){
        return (null != this.getMerchantId(mayInherit));
    }
    public final boolean hasNotMerchantId(boolean mayInherit){
        return (null == this.getMerchantId(mayInherit));
    }
    public final boolean dropMerchantId(){
        if (null != this.merchantId){
            this.fieldStatistics().markDirty(Merchant.Field.MerchantId);
            this.merchantId = null;
            return true;
        }
        else
            return false;
    }
    public final String getMerchantId(){
        return this.getMerchantId(Notation.MayInherit);
    }
    public final String getMerchantId(boolean mayInherit){
        if (mayInherit){
            String merchantId = this.merchantId;
            if (null == merchantId && this.hasInheritFrom()){
                Merchant inheritFrom = this.getInheritFrom();
                return inheritFrom.getMerchantId(Notation.MayInherit);
            }
            return merchantId;
        }
        else
            return this.merchantId;
    }
    public final boolean setMerchantId(String merchantId, boolean withInheritance){
        if (IsNotEqual(this.merchantId,this.getMerchantId(withInheritance))){
            this.fieldStatistics().markDirty(Merchant.Field.MerchantId);
            this.merchantId = merchantId;
            return true;
        }
        else
            return false;
    }
    public final boolean setMerchantId(String merchantId){
        if (IsNotEqual(this.merchantId,merchantId)){
            this.fieldStatistics().markDirty(Merchant.Field.MerchantId);
            this.merchantId = merchantId;
            return true;
        }
        else
            return false;
    }
    public final boolean hasMerchantKey(boolean mayInherit){
        return (null != this.getMerchantKey(mayInherit));
    }
    public final boolean hasNotMerchantKey(boolean mayInherit){
        return (null == this.getMerchantKey(mayInherit));
    }
    public final boolean dropMerchantKey(){
        if (null != this.merchantKey){
            this.fieldStatistics().markDirty(Merchant.Field.MerchantKey);
            this.merchantKey = null;
            return true;
        }
        else
            return false;
    }
    public final String getMerchantKey(){
        return this.getMerchantKey(Notation.MayInherit);
    }
    public final String getMerchantKey(boolean mayInherit){
        if (mayInherit){
            String merchantKey = this.merchantKey;
            if (null == merchantKey && this.hasInheritFrom()){
                Merchant inheritFrom = this.getInheritFrom();
                return inheritFrom.getMerchantKey(Notation.MayInherit);
            }
            return merchantKey;
        }
        else
            return this.merchantKey;
    }
    public final boolean setMerchantKey(String merchantKey, boolean withInheritance){
        if (IsNotEqual(this.merchantKey,this.getMerchantKey(withInheritance))){
            this.fieldStatistics().markDirty(Merchant.Field.MerchantKey);
            this.merchantKey = merchantKey;
            return true;
        }
        else
            return false;
    }
    public final boolean setMerchantKey(String merchantKey){
        if (IsNotEqual(this.merchantKey,merchantKey)){
            this.fieldStatistics().markDirty(Merchant.Field.MerchantKey);
            this.merchantKey = merchantKey;
            return true;
        }
        else
            return false;
    }
    public final boolean hasCurrency(boolean mayInherit){
        return (null != this.getCurrency(mayInherit));
    }
    public final boolean hasNotCurrency(boolean mayInherit){
        return (null == this.getCurrency(mayInherit));
    }
    public final boolean dropCurrency(){
        if (null != this.currency){
            this.fieldStatistics().markDirty(Merchant.Field.Currency);
            this.currency = null;
            return true;
        }
        else
            return false;
    }
    public final String getCurrency(){
        return this.getCurrency(Notation.MayInherit);
    }
    public final String getCurrency(boolean mayInherit){
        if (mayInherit){
            String currency = this.currency;
            if (null == currency && this.hasInheritFrom()){
                Merchant inheritFrom = this.getInheritFrom();
                return inheritFrom.getCurrency(Notation.MayInherit);
            }
            return currency;
        }
        else
            return this.currency;
    }
    public final boolean setCurrency(String currency, boolean withInheritance){
        if (IsNotEqual(this.currency,this.getCurrency(withInheritance))){
            this.fieldStatistics().markDirty(Merchant.Field.Currency);
            this.currency = currency;
            return true;
        }
        else
            return false;
    }
    public final boolean setCurrency(String currency){
        if (IsNotEqual(this.currency,currency)){
            this.fieldStatistics().markDirty(Merchant.Field.Currency);
            this.currency = currency;
            return true;
        }
        else
            return false;
    }
    public final boolean hasTest(boolean mayInherit){
        return (null != this.getTest(mayInherit));
    }
    public final boolean hasNotTest(boolean mayInherit){
        return (null == this.getTest(mayInherit));
    }
    public final boolean dropTest(){
        if (null != this.test){
            this.fieldStatistics().markDirty(Merchant.Field.Test);
            this.test = null;
            return true;
        }
        else
            return false;
    }
    public final Boolean getTest(){
        return this.getTest(Notation.MayInherit);
    }
    public final Boolean getTest(boolean mayInherit){
        if (mayInherit){
            Boolean test = this.test;
            if (null == test && this.hasInheritFrom()){
                Merchant inheritFrom = this.getInheritFrom();
                return inheritFrom.getTest(Notation.MayInherit);
            }
            return test;
        }
        else
            return this.test;
    }
    public final boolean setTest(Boolean test, boolean withInheritance){
        if (IsNotEqual(this.test,this.getTest(withInheritance))){
            this.fieldStatistics().markDirty(Merchant.Field.Test);
            this.test = test;
            return true;
        }
        else
            return false;
    }
    public final boolean setTest(Boolean test){
        if (IsNotEqual(this.test,test)){
            this.fieldStatistics().markDirty(Merchant.Field.Test);
            this.test = test;
            return true;
        }
        else
            return false;
    }
    public Json toJsonIdentifier(){
        String identifier = this.getIdentifier();
        return Json.Wrap( identifier);
    }
    public boolean fromJsonIdentifier(Json json){
        if (null == json)
            return false;
        else
            return this.setIdentifier((String)json.getValue(String.class));
    }
    public Json toJsonMerchantId(){
        String merchantId = this.getMerchantId();
        return Json.Wrap( merchantId);
    }
    public boolean fromJsonMerchantId(Json json){
        if (null == json)
            return false;
        else
            return this.setMerchantId((String)json.getValue(String.class));
    }
    public Json toJsonMerchantKey(){
        String merchantKey = this.getMerchantKey();
        return Json.Wrap( merchantKey);
    }
    public boolean fromJsonMerchantKey(Json json){
        if (null == json)
            return false;
        else
            return this.setMerchantKey((String)json.getValue(String.class));
    }
    public Json toJsonCurrency(){
        String currency = this.getCurrency();
        return Json.Wrap( currency);
    }
    public boolean fromJsonCurrency(Json json){
        if (null == json)
            return false;
        else
            return this.setCurrency((String)json.getValue(String.class));
    }
    public Json toJsonTest(){
        Boolean test = this.getTest();
        return Json.Wrap( test);
    }
    public boolean fromJsonTest(Json json){
        if (null == json)
            return false;
        else
            return this.setTest((Boolean)json.getValue(Boolean.class));
    }
    /*
     * Data binding supports
     */
    public final Kind getClassKind(){
        return KIND;
    }
    public final String getClassName(){
        return ClassName;
    }
    public final gap.data.List<gap.data.Field> getClassFields(){
        gap.data.List re = new Merchant.Field.List(Field.values());
        /*
         * Compiler has a type astigmatism (parameterized interface gap.data.Field)
         */
        return (gap.data.List<gap.data.Field>)re;
    }
    public final gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public Json toJson(){
        Json json = new json.ObjectJson();
        Json identifier = this.toJsonIdentifier();
        if (null != identifier)
            json.set("identifier",identifier);
        Json merchantId = this.toJsonMerchantId();
        if (null != merchantId)
            json.set("merchantId",merchantId);
        Json merchantKey = this.toJsonMerchantKey();
        if (null != merchantKey)
            json.set("merchantKey",merchantKey);
        Json currency = this.toJsonCurrency();
        if (null != currency)
            json.set("currency",currency);
        Json test = this.toJsonTest();
        if (null != test)
            json.set("test",test);
        return json;
    }
    public boolean fromJson(Json json){
        boolean modified = false;
        modified = (this.fromJsonMerchantId(json.at("merchantId")) || modified);
        modified = (this.fromJsonMerchantKey(json.at("merchantKey")) || modified);
        modified = (this.fromJsonCurrency(json.at("currency")) || modified);
        modified = (this.fromJsonTest(json.at("test")) || modified);
        return modified;
    }
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        String merchantIdRequest = req.getParameter("merchantId");
        if (null != merchantIdRequest && 0 < merchantIdRequest.length()){
            try {
                String merchantId = merchantIdRequest;
                if (this.setMerchantId(merchantId)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"merchantId",merchantIdRequest,exc);
            }
        }
        String merchantKeyRequest = req.getParameter("merchantKey");
        if (null != merchantKeyRequest && 0 < merchantKeyRequest.length()){
            try {
                String merchantKey = merchantKeyRequest;
                if (this.setMerchantKey(merchantKey)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"merchantKey",merchantKeyRequest,exc);
            }
        }
        String currencyRequest = req.getParameter("currency");
        if (null != currencyRequest && 0 < currencyRequest.length()){
            try {
                String currency = currencyRequest;
                if (this.setCurrency(currency)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"currency",currencyRequest,exc);
            }
        }
        String testRequest = req.getParameter("test");
        if (null != testRequest && 0 < testRequest.length()){
            try {
                Boolean test = gap.Strings.BooleanFromString(testRequest);
                if (this.setTest(test)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"test",testRequest,exc);
            }
        }
        return change;
    }
    public final boolean updateFrom(BigTable proto){
        return this.updateFrom( (Merchant)proto);
    }
    public final boolean updateFrom(Merchant proto){
        boolean mayInherit = (!this.hasInheritFromKey());
        boolean change = false;
        String merchantId = proto.getMerchantId(mayInherit);
        if (null != merchantId && this.setMerchantId(merchantId)){
            change = true;
        }
        String merchantKey = proto.getMerchantKey(mayInherit);
        if (null != merchantKey && this.setMerchantKey(merchantKey)){
            change = true;
        }
        String currency = proto.getCurrency(mayInherit);
        if (null != currency && this.setCurrency(currency)){
            change = true;
        }
        Boolean test = proto.getTest(mayInherit);
        if (null != test && this.setTest(test)){
            change = true;
        }
        return change;
    }
    public java.io.Serializable valueStorage(gap.data.Field field){

        return Field.Storage( (Field)field, (Merchant)this);
    }
    public void defineStorage(gap.data.Field field, java.io.Serializable value){

        Field.Storage( (Field)field, (Merchant)this, value);
    }
    public final Merchant markClean(){

        this.fieldStatistics().markClean();
        return (Merchant)this;
    }
    public final Merchant markDirty(){

        this.fieldStatistics().markDirty();
        return (Merchant)this;
    }
    public final Merchant markDirty(gap.data.Field field){

        this.fieldStatistics().markDirty(field);
        return (Merchant)this;
    }
    public final Merchant markDirty(java.io.Serializable instance){
        if (instance == this.identifier){
            gap.data.Field field = Merchant.Field.Identifier;
            return this.markDirty(field);
        }
        else if (instance == this.merchantId){
            gap.data.Field field = Merchant.Field.MerchantId;
            return this.markDirty(field);
        }
        else if (instance == this.merchantKey){
            gap.data.Field field = Merchant.Field.MerchantKey;
            return this.markDirty(field);
        }
        else if (instance == this.currency){
            gap.data.Field field = Merchant.Field.Currency;
            return this.markDirty(field);
        }
        else if (instance == this.test){
            gap.data.Field field = Merchant.Field.Test;
            return this.markDirty(field);
        }
        else if (null != instance)
            throw new IllegalArgumentException(instance.getClass().getName());
        else
            throw new IllegalArgumentException();
    }
    public final Iterable<gap.data.Field> listClean(){

        return this.fieldStatistics().listClean();
    }
    public final Iterable<gap.data.Field> listDirty(){

        return this.fieldStatistics().listDirty();
    }
    public final boolean isClean(){

        return this.fieldStatistics().isClean();
    }
    public final boolean isDirty(){

        return this.fieldStatistics().isDirty();
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return Merchant.ClassDescriptorFor();
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = Merchant.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0)){
                    String id = this.getId();
                    return (null != id);
                }
                else
                    return false;
            case Identifier:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasIdentifier(true);
                }
            case MerchantId:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasMerchantId(true);
                }
            case MerchantKey:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasMerchantKey(true);
                }
            case Currency:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasCurrency(true);
                }
            case Test:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Boolean (EXISTS && TRUE)
                     */
                    return (this.hasTest(true) && this.getTest(true));
                }
            default:
                break;
            }
        }
        return super.hasVariable(name);
    }
    public String getVariable(TemplateName name){
        Field field = Merchant.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0))
                    return this.getId();
                else
                    return null;
            case Identifier:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getIdentifier(true);
            case MerchantId:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getMerchantId(true);
            case MerchantKey:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getMerchantKey(true);
            case Currency:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getCurrency(true);
            case Test:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.BooleanToString(this.getTest(true));
            default:
                break;
            }
        }
        return super.getVariable(name);
    }
    public void setVariable(TemplateName name, String value){
        Field field = Merchant.Field.For(name.getTerm());
        if (null != field){
            if (name.has(1)){
                switch (field){
                case Id:
                    throw new UnsupportedOperationException(field.name());
                case Identifier:
                    throw new IllegalStateException(field.name());
                case MerchantId:
                    throw new IllegalStateException(field.name());
                case MerchantKey:
                    throw new IllegalStateException(field.name());
                case Currency:
                    throw new IllegalStateException(field.name());
                case Test:
                    throw new IllegalStateException(field.name());
                default:
                    throw new IllegalStateException(field.name());
                }
            }
            else
                Field.Set(field,((Merchant)this),value);
        }
        else {
            super.setVariable(name,value);
        }
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Merchant.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Identifier:
                return null;
            case MerchantId:
                return null;
            case MerchantKey:
                return null;
            case Currency:
                return null;
            case Test:
                return null;
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getSection(name);
        }
    }
    public Merchant clone(){
        return (Merchant)super.clone();
    }
    public String pathto(){
        return PathTo(this.getId());
    }
    public String pathto(String subpath){
        StringBuilder string = new StringBuilder();
        string.append(this.getId());
        if (null != subpath){
            if (0 == subpath.length() || '/' != subpath.charAt(0))
                string.append('/');
            string.append(subpath);
        }
        return PathTo(string.toString());
    }
}
