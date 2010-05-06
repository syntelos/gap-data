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

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated bean data binding.
 *
 * @see A
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-05-05T20:00:35.049Z")
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




    public final static Key KeyLongIdFor(String name){
        String id = IdFor( name);
        return KeyLongFor(id);
    }


    public final static String IdFor(String name){
        if (null != name){
            String nameString = name;
            return gap.data.Hash.For(nameString);
        }
        else
            throw new IllegalArgumentException();
    }


    public final static A ForLongName(String name){
        if (null != name){
            Key key = KeyLongIdFor( name);
            A instance = (A)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (A)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static A GetCreateLong(String name){
        A a = ForLongName( name);
        if (null == a){
            a = new A( name);
            a = (A)gap.data.Store.Put(a);
        }
        return a;
    }



    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static A ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
            A instance = (A)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (A)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static A Get(Key key){
        if (null != key){
            A instance = (A)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (A)gap.data.Store.Query1(q);
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
     * Drop the instance and any children of its key from the world,
     * memcache and store.
     */
    public final static void Delete(A instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(A instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(A instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(A instance){
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
    
    
    public final static A Query1(Query query){
        if (null != query)
            return (A)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator<A> QueryN(Query query, Page page){
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
        Content("content",Type.Collection);

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

    private volatile transient A inheritFrom;


    private volatile String name;
    private volatile List.Short<B> children;
    private volatile Map.Primitive<String,Blob> content;




    protected AData() {
        super();
    }
    protected AData(String name) {
        super();
        this.setName(name);
        String id = IdFor( name);
        Key key = KeyLongFor(id);
        this.setKey(key);
    }



    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
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
            children = new ListShortAB((A)this);
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
            content = new MapPrimitiveAStringBlob((A)this);
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
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor();
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasName(true);
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
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            if (name.has(1)){
                switch (field){
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
        Field field = Field.For(name.getComponent(0));
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
}