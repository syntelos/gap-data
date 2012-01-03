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
package tt;


import gap.*;
import gap.data.*;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;
import gap.util.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.BlobKey;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated bean data binding.
 *
 * @see A
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2012-01-03T19:10:55.132Z")
public abstract class AData
    extends gap.data.BigTable
    implements DataInheritance<A>
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("A","tt","A","/a");

    public final static String ClassName = "A";

    public final static String DefaultSortBy = "name";


    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(A.class);
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
    public final static Key KeyLongIdFor(String name){
        String id = A.IdFor( name);
        return KeyLongFor(id);
    }
    /**
     * Used by gap.data.Kind
     *
     * Calls {@link #KeyLongIdFor}
     */
    public final static Key KeyIdFor(Object... args){
        return A.KeyLongIdFor((String)args[0]);
    }
    /**
     * Used by setId
     *
     * Calls {@link #KeyLongFor}
     */
    public final static Key KeyFor(Object... args){
        return A.KeyLongFor( (String)args[0]);
    }
    /**
     * Identifier for unique fields
     */
    public final static String IdFor(String name){
        if (null != name){
            String nameString = name;
            return gap.data.Hash.For(nameString);
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup
     */
    public final static A ForLongName(String name){
        if (null != name){
            Key key = A.KeyLongIdFor( name);
            A instance = (A)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = A.CreateQueryFor(key);
                return (A)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup or create
     */
    public static A GetCreateLong(String name){
        return GetCreateLongName( name);
    }
    /**
     * Instance lookup or create
     */
    public final static A GetCreateLongName(String name){
        A a = A.ForLongName( name);
        if (null == a){
            a = new A( name);
            a = (A)gap.data.Store.PutClass(a);
        }
        return a;
    }


    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static A ForLongId(String id){
        if (null != id){
            Key key = A.KeyLongFor(id);
            A instance = (A)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = A.CreateQueryFor(key);
                return (A)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Used by gap.data.Kind
     */
    public final static A Get(Key key){
        if (null != key){
            A instance = (A)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = A.CreateQueryFor(key);
                return (A)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Key GetKey(Key key){
        if (null != key){
            Query q = A.CreateQueryFor(key);
            return gap.data.Store.Query1Key(q);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static A FromObject(Object value){
        if (null == value)
            return null;
        else if (value instanceof A)
            return (A)value;
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
     * @see A#IdFor
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
    public final static void Delete(A instance){
        if (null != instance){

            Delete(instance.getKey());
        }
    }
    /**
     * Drop the instance from memcache and datastore.
     */
    public final static void Delete(Key instanceKey){
        if (null != instanceKey){

            gap.data.Store.DeleteKey(instanceKey);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(A instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.CleanKey(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(A instance){
        if (null != instance){
            gap.data.Store.PutClass(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(A instance){
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
    
    public final static A Query1(Query query){
        if (null != query)
            return (A)gap.data.Store.Query1Class(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator<A> QueryN(Query query, Page page){
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
     * Persistent fields' binding for {@link A}
     */
    public static enum Field
        implements gap.data.Field<A.Field>
    {
        InheritFromKey("inheritFromKey",Type.Primitive),
        Key("key",Type.Primitive),
        Id("id",Type.Primitive),
        Name("name",Type.Primitive),
        Children("children",Type.Collection),
        Content("content",Type.PrimitiveCollection);

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
            extends gap.data.Field.Statistics<A.Field>
        {
            public Statistics(){
                super(A.Field.class);
            }
        }
        /**
         * Dynamic binding operator for field data type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static Object Get(Field field, A instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Name:
                return instance.getName(mayInherit);
            case Children:
                return instance.getChildren(mayInherit);
            case Content:
                return instance.getContent(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in A");
            }
        }
        /**
         * Dynamic binding operator for field data type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static boolean Set(Field field, A instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case Name:
                return instance.setName(gap.Objects.StringFromObject(value));
            case Children:
                return instance.setChildren((List.Short<B>)value);
            case Content:
                return instance.setContent((Map.Primitive<String,Blob>)value);
            default:
                throw new IllegalArgumentException(field.toString()+" in A");
            }
        }
        /**
         * Dynamic binding operator for field storage type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static java.io.Serializable Storage(Field field, A instance){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Name:
                return instance.getName(MayNotInherit);
            case Children:{
                return null;
            }
            case Content:{
                Map.Primitive<String,Blob> content = instance.getContent(MayNotInherit);
                if (null != content)
                    return Serialize.To(field,content);
                else
                    return null;
            }
            default:
                throw new IllegalArgumentException(field.toString()+" in A");
            }
        }
        /**
         * Dynamic binding operator for field storage type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static void Storage(Field field, A instance, java.io.Serializable value){
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
            case Name:
                instance.setName( (String)value);
                return;
            case Children:{
                return;
            }
            case Content:{
                Map.Primitive<String,Blob> _content = null;
                if (null != value){
                    _content = (Map.Primitive<String,Blob>)Serialize.From(field,value);
                }
                instance.setContent( _content);
                return;
            }
            default:
                throw new IllegalArgumentException(field.toString()+" in A");
            }
        }


        public final static class List
            extends gap.util.ArrayList<A.Field>
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

    private final A.Field.Statistics fieldStatistics = new A.Field.Statistics();

    private transient A inheritFrom;


    private String name;
    private transient List.Short<B> children;
    private Map.Primitive<String,Blob> content;




    protected AData() {
        super();
    }
    protected AData(String name) {
        super();
        this.setName(name);
        {
            final String id = A.IdFor(name);
            final Key key = A.KeyLongFor(id);
            this.setKey(key);
        }
    }


    public void destroy(){
        this.inheritFrom = null;
        this.name = null;
        List.Short<B> children = this.children;
        if (null != children){
            this.children = null;
            children.destroy();
        }
        Map.Primitive<String,Blob> content = this.content;
        if (null != content){
            this.content = null;
            content.destroy();
        }
    }
    public final String getId(){

        String id = A.IdFor(KIND.name, this.key);
        if (null != id)
            return id;
        else
            return A.IdFor(this.name);
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
            this.key = A.KeyLongFor(id);
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
    public final A getInheritFrom(){
        A inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = A.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(A ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){

            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(A ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){

            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean hasName(boolean mayInherit){
        return (null != this.getName(mayInherit));
    }
    public final boolean hasNotName(boolean mayInherit){
        return (null == this.getName(mayInherit));
    }
    public final boolean dropName(){
        if (null != this.name){
            this.fieldStatistics.markDirty(Field.Name);
            this.name = null;
            return true;
        }
        else
            return false;
    }
    public final String getName(){
        return this.name;
    }
    public final String getName(boolean mayInherit){
        return this.getName();
    }
    public final boolean setName(String name){
        if (IsNotEqual(this.name,name)){
            this.fieldStatistics.markDirty(Field.Name);
            this.name = name;
            return true;
        }
        else
            return false;
    }
    public final boolean hasChildren(boolean mayInherit){
        return (this.getChildren(mayInherit).isNotEmpty());
    }
    public final boolean hasNotChildren(boolean mayInherit){
        return (this.getChildren(mayInherit).isEmpty());
    }
    public final boolean dropChildren(){
        List.Short<B> children = this.children;
        if (null != children){
            this.children = null;
            children.destroy();
            return true;
        }
        else
            return false;
    }
    public final List.Short<B> getChildren(){
        return this.getChildren(Notation.MayInherit);
    }
    public final List.Short<B> getChildren(boolean mayInherit){
        List.Short<B> children = this.children;
        if (null == children){
            if (mayInherit && this.hasInheritFrom()){
                A inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    children = inheritFrom.getChildren(Notation.MayInherit);
                    if (null != children)
                        return children;
                }
            }
            /*
             * Collection type coersion
             */
            {
                Object tmp = new ListAB((A)this);
                children = (List.Short<B>)tmp;
            }
            this.children = children;
            children.init();
        }
        return children;
    }
    public final boolean setChildren(List.Short<B> children){
        if (IsNotEqual(this.children,children)){

            this.children = children;
            return true;
        }
        else
            return false;
    }
    public final boolean isEmptyChildren(){
        List.Short<B> collection = this.children;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyChildren(){
        List.Short<B> collection = this.children;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final B fetchChildren(Filter filter){
        if (null != filter && KIND == filter.kind){
            List.Short<B> collection = this.getChildren(Notation.MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final B getChildren(gap.data.ListFilter<B> filter){
        if (null != filter){
            List.Short<B> list = this.getChildren(Notation.MayInherit);
            for (B item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }
    public final boolean hasContent(boolean mayInherit){
        return (this.getContent(mayInherit).isNotEmpty());
    }
    public final boolean hasNotContent(boolean mayInherit){
        return (this.getContent(mayInherit).isEmpty());
    }
    public final boolean dropContent(){
        Map.Primitive<String,Blob> content = this.content;
        if (null != content){
            this.content = null;
            content.destroy();
            return true;
        }
        else
            return false;
    }
    public final Map.Primitive<String,Blob> getContent(){
        return this.getContent(Notation.MayInherit);
    }
    public final Map.Primitive<String,Blob> getContent(boolean mayInherit){
        Map.Primitive<String,Blob> content = this.content;
        if (null == content){
            if (mayInherit && this.hasInheritFrom()){
                A inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    content = inheritFrom.getContent(Notation.MayInherit);
                    if (null != content)
                        return content;
                }
            }
            /*
             * Collection type coersion
             */
            {
                Object tmp = new MapPrimitiveAStringBlob((A)this);
                content = (Map.Primitive<String,Blob>)tmp;
            }
            this.content = content;
            content.init();
        }
        return content;
    }
    public final boolean setContent(Map.Primitive<String,Blob> content){
        if (IsNotEqual(this.content,content)){

            this.content = content;
            return true;
        }
        else
            return false;
    }
    public final boolean isEmptyContent(){
        Map.Primitive<String,Blob> collection = this.content;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyContent(){
        Map.Primitive<String,Blob> collection = this.content;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final Blob getContent(String contentName){
        if (null != contentName){
            Map.Primitive<String,Blob> map = this.getContent(Notation.MayInherit);
            if (null != map)
                return map.get(contentName);
            else
                return null;
        }
        else
            throw new IllegalArgumentException();
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
        gap.data.List re = new A.Field.List(Field.values());
        /*
         * Compiler has a type astigmatism (parameterized interface gap.data.Field)
         */
        return (gap.data.List<gap.data.Field>)re;
    }
    public final gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        return change;
    }
    public final boolean updateFrom(BigTable proto){
        return this.updateFrom( (A)proto);
    }
    public final boolean updateFrom(A proto){
        boolean mayInherit = (!this.hasInheritFromKey());
        boolean change = false;
        return change;
    }
    public java.io.Serializable valueStorage(gap.data.Field field){

        return Field.Storage( (Field)field, (A)this);
    }
    public void defineStorage(gap.data.Field field, java.io.Serializable value){

        Field.Storage( (Field)field, (A)this, value);
    }
    public final A markClean(){

        this.fieldStatistics.markClean();
        return (A)this;
    }
    public final A markDirty(){

        this.fieldStatistics.markDirty();
        return (A)this;
    }
    public final Iterable<gap.data.Field> listClean(){

        return this.fieldStatistics.listClean();
    }
    public final Iterable<gap.data.Field> listDirty(){

        return this.fieldStatistics.listDirty();
    }
    public final boolean isClean(){

        return this.fieldStatistics.isClean();
    }
    public final boolean isDirty(){

        return this.fieldStatistics.isDirty();
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return A.ClassDescriptorFor();
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = A.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0)){
                    String id = this.getId();
                    return (null != id);
                }
                else
                    return false;
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasName(true);
                }
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.hasVariable(name);
        }
    }
    public String getVariable(TemplateName name){
        Field field = A.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0))
                    return this.getId();
                else
                    return null;
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getName(true);
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getVariable(name);
        }
    }
    public void setVariable(TemplateName name, String value){
        Field field = A.Field.For(name.getTerm());
        if (null != field){
            if (name.has(1)){
                switch (field){
                case Id:
                    throw new UnsupportedOperationException(field.name());
                case Name:
                    throw new IllegalStateException(field.name());
                default:
                    throw new IllegalStateException(field.name());
                }
            }
            else
                Field.Set(field,((A)this),value);
        }
        else {
            super.setVariable(name,value);
        }
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){
        Field field = A.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Name:
                return null;
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getSection(name);
        }
    }
    public A clone(){
        return (A)super.clone();
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
