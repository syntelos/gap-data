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
 * @see B
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-05-05T20:00:35.633Z")
public abstract class BData
    extends gap.data.BigTable
    implements DataInheritance<B>
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("B","tt","B","/b");

    public final static String ClassName = "B";

    public final static String DefaultSortBy = "";


    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(B.class);
    }




    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static B ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
            B instance = (B)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (B)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static B Get(Key key){
        if (null != key){
            B instance = (B)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (B)gap.data.Store.Query1(q);
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
    public final static B FromObject(Object value){
        if (null == value)
            return null;
        else if (value instanceof B)
            return (B)value;
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
    public final static void Delete(B instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(B instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(B instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(B instance){
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
    
    
    public final static B Query1(Query query){
        if (null != query)
            return (B)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator<B> QueryN(Query query, Page page){
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
     * Persistent fields' binding for {@link B}
     */
    public static enum Field
        implements gap.data.Field<B.Field>
    {
        InheritFromKey("inheritFromKey",Type.Primitive),
        Key("key",Type.Primitive),
        Id("id",Type.Primitive);

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
        public static Object Get(Field field, B instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            default:
                throw new IllegalArgumentException(field.toString()+" in B");
            }
        }
        public static boolean Set(Field field, B instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in B");
            }
        }

        public final static class List
            extends gap.util.ArrayList<B.Field>
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

    private volatile transient B inheritFrom;






    protected BData() {
        super();
    }


    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
    }
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final B getInheritFrom(){
        B inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = B.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(B ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(B ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
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
    public final gap.data.List<gap.data.Field> getClassFields(){
        gap.data.List re = new B.Field.List(Field.values());
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
        return this.updateFrom( (B)proto);
    }
    public final boolean updateFrom(B proto){
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
                default:
                    throw new IllegalStateException(field.name());
                }
            }
            else
                Field.Set(field,((B)this),value);
        }
        else {
            super.setVariable(name,value);
        }
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getSection(name);
        }
    }
}
