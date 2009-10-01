
package oso.data;

import gap.data.*;
import gap.service.Templates;

import hapax.TemplateDictionary;


import com.google.appengine.api.datastore.*;

import java.io.Serializable;


import javax.annotation.Generated;

/** 
 * Generated from "oso.data" with "odl/java.xtm".
 *
 */
@Generated(value={"gap.od","odl/java.xtm"},date="2009-09-23T21:54:45.812Z",comments="oso.data")
public final class Person
    extends gap.data.BigTable
{

    private final static long serialVersionUID = 2;

    public final static String KIND = "Person";

    public final static String ClassName = "Person";

    static {
        Register(Person.class);
    }


    public final static Key KeyIdFor(String logonId){
        String id = IdFor( logonId);
        return KeyFor(id);
    }
    public final static Key KeyIdFor(Key ancestor, String logonId){
        String id = IdFor(ancestor, logonId);
        return KeyFor(ancestor,id);
    }
    public final static String IdFor(Key ancestor, String logonId){
        if (ancestor.isComplete() && null != logonId){
            String logonIdString = logonId;
            return gap.data.Hash.For(ToString(ancestor)+'/'+logonIdString);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static String IdFor(String logonId){
        if (null != logonId){
            String logonIdString = logonId;
            return gap.data.Hash.For(logonIdString);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Person ForLogonId(Key ancestor, String logonId){
        if (null != logonId){
            Key key = KeyIdFor(ancestor, logonId);
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
    public final static Person ForLogonId(String logonId){
        if (null != logonId){
            Key key = KeyIdFor( logonId);
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
    public final static Person GetCreate(String logonId){
        Person person = ForLogonId( logonId);
        if (null == person){
            person = new Person( logonId);
            person = (Person)gap.data.Store.Put(person);
        }
        return person;
    }
    public final static Person GetCreate(Key ancestor, String logonId){
        Person person = ForLogonId(ancestor, logonId);
        if (null == person){
            person = new Person(ancestor, logonId);
            person = (Person)gap.data.Store.Put(person);
        }
        return person;
    }


    public final static Key KeyFor(String id){
        return KeyFactory.createKey(KIND,id);
    }
    public final static Key KeyGroupFor(Key ancestor, String id){
        return KeyFactory.createKey(ancestor,KIND,id);
    }
    public final static Key KeyFor(Key ancestor, String id){
        return KeyFactory.createKey(KIND,id);
    }
    public final static Person ForId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyFor(ancestor,id);
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
    public final static Person ForId(String id){
        if (null != id){
            Key key = KeyFor(id);
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
     * Test for uniqueness and iterate under collisions.
     */
    public final static Key NewChildRandomKey(Key parent){
        if (null != parent){
            String source = gap.data.BigTable.ToString(parent);
            long id = gap.data.Hash.Djb64(source);
            java.util.Random random = new java.util.Random();
            do {
                id ^= random.nextLong();
                String idString = gap.data.Hash.Hex(id);
                Key key = KeyFactory.createKey(KIND,idString);
                if (null == GetKey(key))
                    return key;
            }
            while (true);
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
            gap.data.Store.DeleteCollection(new Query(key));
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
    public final static Query CreateQueryFor(){
        return new Query(KIND);
    }
    public final static Query CreateQueryFor(Key ancestor){
        return new Query(KIND,ancestor);
    }
    public final static Person Query1(Query query){
        if (null != query)
            return (Person)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static List.Primitive QueryN(Query query, FetchOptions page){
        if (null != query)
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
    public final static List.Primitive<Key> QueryKeyN(Query query, FetchOptions page){
        if (null != query)
            return gap.data.Store.QueryKeyN(query,page);
        else
            throw new IllegalArgumentException();
    }

    /**
     * 
     */
    public static enum Field
        implements gap.data.Field
    {

        Key("key"),
        Id("id"),
        LogonId("logonId"),
        Roles("roles"),
        Attributes("attributes");


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
        public static Object Get(Field field, Person instance){
            switch(field){

            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case LogonId:
                return instance.getLogonId();
            case Roles:
                return instance.getRoles();
            case Attributes:
                return instance.getAttributes();
            default:
                throw new IllegalArgumentException(field.toString()+" in Person");
            }
        }
        public static void Set(Field field, Person instance, Object value){
            switch(field){

            case Key:
                instance.setKey( (Key)value);
                return;
            case Id:
                instance.setId( (String)value);
                return;
            case LogonId:
                instance.setLogonId( (String)value);
                return;
            case Roles:
                instance.setRoles( (List.Primitive<String>)value);
                return;
            case Attributes:
                instance.setAttributes( (Map<String,Serializable>)value);
                return;
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


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String logonId;    // *hash-unique
    private volatile List.Primitive<String> roles;    
    private volatile Map<String,Serializable> attributes;    




    public Person() {
        super();
    }
    public Person(Key child) {
        super();
        this.setKey(child);
    }
    public Person(String logonId) {
        super();
        this.setLogonId(logonId);
        String id = IdFor( logonId);
        this.setId(id);
        Key key = KeyFor(id);
        this.setKey(key);
    }
    public Person(Key ancestor, String logonId) {
        super();
        this.setLogonId(logonId);
        String id = IdFor(ancestor,  logonId);
        this.setId(id);
        Key key = KeyFor(ancestor,id);
        this.setKey(key);
    }


    public void onread(){
    }
    public void onwrite(){
    }


    public boolean hasKey(){
        return (null != this.key);
    }
    public boolean hasNotKey(){
        return (null == this.key);
    }
    public boolean dropKey(){
        if (null != this.key){
            this.key = null;
            return true;
        }
        else
            return false;
    }
    public Key getKey(){
        return this.key;
    }
    public void setKey(Key key){
        this.key = key;
    }

    public boolean hasId(){
        return (null != this.id);
    }
    public boolean hasNotId(){
        return (null == this.id);
    }
    public boolean dropId(){
        if (null != this.id){
            this.id = null;
            return true;
        }
        else
            return false;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }

    public boolean hasLogonId(){
        return (null != this.logonId);
    }
    public boolean hasNotLogonId(){
        return (null == this.logonId);
    }
    public boolean dropLogonId(){
        if (null != this.logonId){
            this.logonId = null;
            return true;
        }
        else
            return false;
    }
    public String getLogonId(){
        return this.logonId;
    }
    public void setLogonId(String logonId){
        this.logonId = logonId;
    }

    public boolean hasRoles(){
        return (null != this.roles);
    }
    public boolean hasNotRoles(){
        return (null == this.roles);
    }
    public boolean dropRoles(){
        if (null != this.roles){
            this.roles = null;
            return true;
        }
        else
            return false;
    }
    public List.Primitive<String> getRoles(){
        return this.roles;
    }
    public void setRoles(List.Primitive<String> roles){
        this.roles = roles;
    }
    public int sizeRoles(){
        List.Primitive<String> list = this.roles;
        if (null != list)
            return list.size();
        else
            return 0;
    }
    public boolean isEmptyRoles(){
        List.Primitive<String> list = this.roles;
        if (null != list)
            return list.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyRoles(){
        List.Primitive<String> list = this.roles;
        if (null != list)
            return (!list.isEmpty());
        else
            return false;
    }
    public boolean containsRoles(String rolesItem){
        if (null != rolesItem){
            List.Primitive<String> list = this.roles;
            if (null != list)
                return list.contains(rolesItem);
            else
                return false;
        }
        else
            throw new IllegalArgumentException();
    }
    public String getRoles(gap.data.ListFilter<String> filter){
        if (null != filter){
            List.Primitive<String> list = this.roles;
            if (null != list){
                for (String item : list){
                    if (filter.accept(item))
                        return item;
                }
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }
    public void addRoles(String rolesItem){
        if (null != rolesItem){
            List.Primitive<String> list = this.roles;
            if (null == list){
                list = new gap.util.ListPrimitiveString();
                this.roles = list;
            }
            else if (list.contains(rolesItem))
                return;

            list.add(rolesItem);
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasAttributes(){
        return (null != this.attributes);
    }
    public boolean hasNotAttributes(){
        return (null == this.attributes);
    }
    public boolean dropAttributes(){
        if (null != this.attributes){
            this.attributes = null;
            return true;
        }
        else
            return false;
    }
    public Map<String,Serializable> getAttributes(){
        return this.attributes;
    }
    public void setAttributes(Map<String,Serializable> attributes){
        this.attributes = attributes;
    }
    public int sizeAttributes(){
        Map<String,Serializable> map = this.attributes;
        if (null != map)
            return map.size();
        else
            return 0;
    }
    public boolean isEmptyAttributes(){
        Map<String,Serializable> map = this.attributes;
        if (null != map)
            return map.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyAttributes(){
        Map<String,Serializable> map = this.attributes;
        if (null != map)
            return (!map.isEmpty());
        else
            return false;
    }
    public Serializable getAttributes(String attributesKey){
        if (null != attributesKey){
            Map<String,Serializable> map = this.attributes;
            if (null != map)
                return map.get(attributesKey);
            else
                return null;
        }
        else
            throw new IllegalArgumentException();
    }



    /*
     * Data addressing supports
     */

    /*
     * Data binding supports
     */
    public String getClassKind(){
        return KIND;
    }
    public String getClassName(){
        return ClassName;
    }
    public String getClassFieldUnique(){
        return "id";
    }
    public String getClassFieldKeyName(){
        return "key";
    }
    public List.Primitive<gap.data.Field> getClassFields(){
        return (new gap.data.Field.List(Field.values()));
    }
    public gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public java.io.Serializable valueOf(gap.data.Field field){
        return (java.io.Serializable)Field.Get((Field)field,this);
    }
    public void define(gap.data.Field field, java.io.Serializable value){
        Field.Set((Field)field,this,value);
    }
    public int updateFrom(gap.service.Parameters params, Person request){
        /*
         * 
         */
        return 0;
    }
    public TemplateDictionary dictionaryFor(){
        return this.dictionaryInto(Templates.CreateDictionary());
    }
    public TemplateDictionary dictionaryInto(gap.service.Parameters params, TemplateDictionary dict){
        if (null != params && params.hasFields()){

            for (String name: params.getFields()){
                Field field = Field.getField(name);
                if (null != field){
                    java.lang.Object value = Field.Get(field,this);
                    if (null != value){
                        dict.putVariable(field.toString(),value.toString());
                    }
                }
            }
            return dict;
        }
        else
            return this.dictionaryInto(dict);
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary dict){

        for (Field field : Field.values()){
            java.lang.Object value = Field.Get(field,this);
            if (null != value){
                dict.putVariable(field.toString(),value.toString());
            }
        }
        return dict;
    }
    public TemplateDictionary dictionaryFor(gap.service.Parameters params){
        return this.dictionaryInto(params,Templates.CreateDictionary());
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
}
