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
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated data bean
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-01-31T13:10:30.419Z")
public abstract class PersonData
    extends gap.data.BigTable
    implements DataInheritance<Person>
{

    private final static long serialVersionUID = 2;

    public final static Kind KIND = Kind.Create("Person","oso.data","Person","/people");

    public final static String ClassName = "Person";

    public final static String DefaultSortBy = "logonId";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Person.class);
    }




    public final static Key KeyLongIdFor(String logonId){
        String id = IdFor( logonId);
        return KeyLongFor(id);
    }


    public final static String IdFor(String logonId){
        if (null != logonId){
            String logonIdString = logonId;
            return gap.data.Hash.For(logonIdString);
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Person ForLongLogonId(String logonId){
        if (null != logonId){
            Key key = KeyLongIdFor( logonId);
            Person instance = (Person)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Person)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Person GetCreateLong(String logonId){
        Person person = ForLongLogonId( logonId);
        if (null == person){
            person = new Person( logonId);
            person = (Person)gap.data.Store.Put(person);
        }
        return person;
    }



    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static Person ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
            Person instance = (Person)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Person)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Person Get(Key key){
        if (null != key){
            Person instance = (Person)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Person)gap.data.Store.Query1(q);
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



    /**
     * Drop the instance and any children of its key from the world,
     * memcache and store.
     */
    public final static void Delete(Person instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Person instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Person instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Person instance){
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
    
    
    public final static Person Query1(Query query){
        if (null != query)
            return (Person)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator QueryN(Query query, Page page){
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
     * Persistent fields' binding for {@link Person}
     */
    public static enum Field
        implements gap.data.Field<Field>
    {
        InheritFromKey("inheritFromKey"),
        Key("key"),
        Id("id"),
        LogonId("logonId");


        private final static java.util.Map<String,Field> FieldName = new java.util.HashMap<String,Field>();
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
        public static Object Get(Field field, Person instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey(mayInherit);
            case Id:
                return instance.getId(mayInherit);
            case LogonId:
                return instance.getLogonId(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Person");
            }
        }
        public static boolean Set(Field field, Person instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case LogonId:
                return instance.setLogonId(gap.Objects.StringFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in Person");
            }
        }


        private final String fieldName;


        Field(String fieldName){
            this.fieldName = fieldName;
        }


        public String getFieldName(){
            return this.fieldName;
        }
        public String toString(){
            return this.fieldName;
        }
    }

    private volatile transient Person inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String logonId;    // *hash-unique








    public PersonData() {
        super();
    }
    public PersonData(String logonId) {
        super();
        this.setLogonId(logonId);
        String id = IdFor( logonId);
        this.setId(id);
        Key key = KeyLongFor(id);
        this.setKey(key);
    }



    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.key = null;
        this.id = null;
        this.logonId = null;
    }
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final Person getInheritFrom(){
        Person inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Person.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(Person ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(Person ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }


    public final boolean hasKey(boolean mayInherit){
        return (null != this.getKey(mayInherit));
    }
    public final boolean hasNotKey(boolean mayInherit){
        return (null == this.getKey(mayInherit));
    }
    public final boolean dropKey(){
        if (null != this.key){
            this.key = null;
            return true;
        }
        else
            return false;
    }
    public final Key getKey(){
        return this.key;
    }
    public final Key getKey(boolean ignore){
        return this.key;
    }
    public final boolean setKey(Key key){
        if (IsNotEqual(this.key,key)){
            this.key = key;
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
    public final String getId(boolean ignore){
        return this.id;
    }
    public final boolean setId(String id){
        if (IsNotEqual(this.id,id)){
            this.id = id;
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
        return this.logonId;
    }
    public final String getLogonId(boolean ignore){
        return this.logonId;
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
    public final String getClassFieldKeyName(){
        return "key";
    }
    public final List<gap.data.Field> getClassFields(){
        return (new gap.data.Field.List(Field.values()));
    }
    public final gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        return change;
    }
    public final boolean updateFrom(BigTable proto){
        return this.updateFrom( (Person)proto);
    }
    public final boolean updateFrom(Person proto){
        boolean change = false;
        return change;
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
}