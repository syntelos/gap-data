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
package yas.data;


import gap.*;
import gap.data.*;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated bean data binding.
 *
 * @see Source
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-03-06T14:54:41.518Z")
public abstract class SourceData
    extends gap.data.BigTable
    implements DataInheritance<Source>
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("Source","yas.data","Source","/source");

    public final static String ClassName = "Source";

    public final static String DefaultSortBy = "twitterId";


    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Source.class);
    }




    public final static Key KeyLongIdFor(String twitterId){
        String id = IdFor( twitterId);
        return KeyLongFor(id);
    }


    public final static String IdFor(String twitterId){
        if (null != twitterId){
            String twitterIdString = twitterId;
            return gap.data.Hash.For(twitterIdString);
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Source ForLongTwitterId(String twitterId){
        if (null != twitterId){
            Key key = KeyLongIdFor( twitterId);
            Source instance = (Source)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Source)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Source GetCreateLong(String twitterId){
        Source source = ForLongTwitterId( twitterId);
        if (null == source){
            source = new Source( twitterId);
            source = (Source)gap.data.Store.Put(source);
        }
        return source;
    }



    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static Source ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
            Source instance = (Source)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Source)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Source Get(Key key){
        if (null != key){
            Source instance = (Source)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Source)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Key GetKey(Key key){
        if (null != key){
            Query q = CreateQueryFor(key);
            return gap.data.Store.QueryKey1(q);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Source FromObject(Object value){
        if (null == value)
            return null;
        else if (value instanceof Source)
            return (Source)value;
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
     * Drop the instance and any children of its key from the world,
     * memcache and store.
     */
    public final static void Delete(Source instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Source instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Source instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Source instance){
        if (null != instance){
            gap.data.Store.Put(instance);
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
    
    
    public final static Source Query1(Query query){
        if (null != query)
            return (Source)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator<Source> QueryN(Query query, Page page){
        if (null != query && null != page)
            return gap.data.Store.QueryN(query,page);
        else
            throw new IllegalArgumentException();
    }
    public final static Key QueryKey1(Query query){
        if (null != query)
            return gap.data.Store.QueryKey1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static List.Primitive<Key> QueryKeyN(Query query, Page page){
        if (null != query)
            return gap.data.Store.QueryKeyN(query,page);
        else
            throw new IllegalArgumentException();
    }

    /**
     * Persistent fields' binding for {@link Source}
     */
    public static enum Field
        implements gap.data.Field<Field>
    {
        InheritFromKey("inheritFromKey",Type.Primitive),
        Key("key",Type.Primitive),
        Id("id",Type.Primitive),
        TwitterId("twitterId",Type.Primitive),
        LogonId("logonId",Type.Primitive);

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
                return Field.valueOf(name);
            else
                return field;
        }
        public static Object Get(Field field, Source instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId(mayInherit);
            case TwitterId:
                return instance.getTwitterId(mayInherit);
            case LogonId:
                return instance.getLogonId(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Source");
            }
        }
        public static boolean Set(Field field, Source instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case TwitterId:
                return instance.setTwitterId(gap.Objects.StringFromObject(value));
            case LogonId:
                return instance.setLogonId(gap.Objects.StringFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in Source");
            }
        }


        private final String fieldName;

        private final Type fieldType;

        private final boolean fieldTypePrimitive, fieldTypeBigTable, fieldTypeCollection;


        Field(String fieldName, Type fieldType){
            if (null != fieldName && null != fieldType){
                this.fieldName = fieldName;
                this.fieldType = fieldType;
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
        public String toString(){
            return this.fieldName;
        }
    }

    private volatile transient Source inheritFrom;


    private volatile String id;    // *unique
    private volatile String twitterId;    // *hash-unique
    private volatile String logonId;    




    protected SourceData() {
        super();
    }
    protected SourceData(String twitterId) {
        super();
        this.setTwitterId(twitterId);
        String id = IdFor( twitterId);
        this.setId(id);
        Key key = KeyLongFor(id);
        this.setKey(key);
    }



    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.id = null;
        this.twitterId = null;
        this.logonId = null;
    }
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final Source getInheritFrom(){
        Source inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Source.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(Source ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(Source ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean hasId(boolean mayInherit){
        return (null != this.getId(mayInherit));
    }
    public final boolean hasNotId(boolean mayInherit){
        return (null == this.getId(mayInherit));
    }
    public final boolean dropId(){
        if (null != this.id){
            this.id = null;
            return true;
        }
        else
            return false;
    }
    public final String getId(){
        return this.id;
    }
    public final String getId(boolean mayInherit){
        return this.getId();
    }
    public final boolean setId(String id){
        if (IsNotEqual(this.id,id)){
            this.id = id;
            return true;
        }
        else
            return false;
    }
    public final boolean hasTwitterId(boolean mayInherit){
        return (null != this.getTwitterId(mayInherit));
    }
    public final boolean hasNotTwitterId(boolean mayInherit){
        return (null == this.getTwitterId(mayInherit));
    }
    public final boolean dropTwitterId(){
        if (null != this.twitterId){
            this.twitterId = null;
            return true;
        }
        else
            return false;
    }
    public final String getTwitterId(){
        return this.twitterId;
    }
    public final String getTwitterId(boolean mayInherit){
        return this.getTwitterId();
    }
    public final boolean setTwitterId(String twitterId){
        if (IsNotEqual(this.twitterId,twitterId)){
            this.twitterId = twitterId;
            return true;
        }
        else
            return false;
    }
    public final boolean hasLogonId(boolean mayInherit){
        return (null != this.getLogonId(mayInherit));
    }
    public final boolean hasNotLogonId(boolean mayInherit){
        return (null == this.getLogonId(mayInherit));
    }
    public final boolean dropLogonId(){
        if (null != this.logonId){
            this.logonId = null;
            return true;
        }
        else
            return false;
    }
    public final String getLogonId(){
        return this.getLogonId(MayInherit);
    }
    public final String getLogonId(boolean mayInherit){
        if (mayInherit){
            String logonId = this.logonId;
            if (null == logonId && this.hasInheritFrom()){
                Source inheritFrom = this.getInheritFrom();
                return inheritFrom.getLogonId(MayInherit);
            }
            return logonId;
        }
        else
            return this.logonId;
    }
    public final boolean setLogonId(String logonId, boolean withInheritance){
        if (IsNotEqual(this.logonId,this.getLogonId(withInheritance))){
            this.logonId = logonId;
            return true;
        }
        else
            return false;
    }
    public final boolean setLogonId(String logonId){
        if (IsNotEqual(this.logonId,logonId)){
            this.logonId = logonId;
            return true;
        }
        else
            return false;
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
    public final String getClassFieldUnique(){
        return "id";
    }
    public final List<gap.data.Field> getClassFields(){
        return (new gap.data.Field.List(Field.values()));
    }
    public final gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        String logonIdRequest = req.getParameter("logonId");
        try {
            String logonId = Strings.StringFromString(logonIdRequest);
            if (this.setLogonId(logonId)){
                change = true;
            }
        }
        catch (RuntimeException exc){
            throw new ValidationError(ClassName,"logonId",logonIdRequest,exc);
        }
        return change;
    }
    public final boolean updateFrom(BigTable proto){
        return this.updateFrom( (Source)proto);
    }
    public final boolean updateFrom(Source proto){
        boolean mayInherit = (!this.hasInheritFromKey());
        boolean change = false;
        String logonId = proto.getLogonId(mayInherit);
        if (null != logonId && this.setLogonId(logonId)){
            change = true;
        }
        return change;
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Id:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasId(true);
            case TwitterId:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasTwitterId(true);
            case LogonId:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasLogonId(true);
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.hasVariable(name);
        }
    }
    public String getVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Id:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getId(true);
            case TwitterId:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getTwitterId(true);
            case LogonId:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getLogonId(true);
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getVariable(name);
        }
    }
    public void setVariable(TemplateName name, String value){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            if (name.has(1)){
                switch (field){
                case Id:
                    throw new IllegalStateException(field.name());
                case TwitterId:
                    throw new IllegalStateException(field.name());
                case LogonId:
                    throw new IllegalStateException(field.name());
                default:
                    throw new IllegalStateException(field.name());
                }
            }
            else
                Field.Set(field,((Source)this),value);
        }
        else {
            super.setVariable(name,value);
        }
    }
    public List<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Id:
                return null;
            case TwitterId:
                return null;
            case LogonId:
                return null;
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getSection(name);
        }
    }
}
