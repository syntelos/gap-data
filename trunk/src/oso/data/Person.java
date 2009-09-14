
package oso.data;

import gap.data.ListFilter;
import gap.data.Store;
import gap.service.Templates;

import hapax.TemplateDictionary;


import com.google.appengine.api.datastore.*;

import java.util.*;


import javax.annotation.Generated;

/** 
 * Generated from "odl/oso/data/Person.odl" with "odl/java.xtm".
 * 
 */
@Generated(value={"gap.odl.Main","odl/java.xtm"},date="2009-09-14T21:24:34.397Z",comments="odl/oso/data/Person.odl")
public final class Person
    extends gap.data.BigTable
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Person";

    static {
        Register(Person.class);
    }


    public final static Key KeyIdFor(String logonId){
        String id = IdFor( logonId);
        return KeyFactory.createKey(KIND,id);
    }
    public final static Key KeyIdFor(Key ancestor, String logonId){
        String id = IdFor(ancestor, logonId);
        return KeyFactory.createKey(ancestor,KIND,id);
    }
    public final static Query CreateQueryFor(){
        return new Query(KIND);
    }
    public final static Query CreateQueryFor(Key ancestor){
        return new Query(KIND,ancestor);
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
            String id = IdFor(ancestor,  logonId);
            Person person = (Person)Store.C.Get(id);
            if (null != person)
                return person;
            else {
                Query query = new Query("Person",ancestor);
                query.addFilter("logonId",Query.FilterOperator.EQUAL,logonId);
                person = (Person)Store.P.Query1(Person.class,query);
                if (null != person)
                    Store.C.Put(id,person);
                return person;
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Person ForLogonId(String logonId){
        if (null != logonId){
            String id = IdFor( logonId);
            Person person = (Person)Store.C.Get(id);
            if (null != person)
                return person;
            else {
                Query query = new Query("Person");
                query.addFilter("logonId",Query.FilterOperator.EQUAL,logonId);
                person = (Person)Store.P.Query1(Person.class,query);
                if (null != person)
                    Store.C.Put(id,person);
                return person;
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Person GetCreate(String logonId){
        Person person = ForLogonId( logonId);
        if (null == person){
            person = new Person( logonId);
            person = (Person)Store.P.Put(person);
            Store.C.Put(person.getId(),person);
        }
        return person;
    }


    public final static Key KeyFor(String id){
        return KeyFactory.createKey(KIND,id);
    }
    public final static Key KeyFor(Key ancestor, String id){
        return KeyFactory.createKey(ancestor,KIND,id);
    }
    public final static Person ForId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            String ckey = (ToString(ancestor)+'/'+id);
            Person person = (Person)Store.C.Get(ckey);
            if (null != person)
                return person;
            else {
                Query query = new Query("Person",ancestor);
                query.addFilter("id",Query.FilterOperator.EQUAL,id);
                person = (Person)Store.P.Query1(Person.class,query);
                if (null != person)
                    Store.C.Put(ckey,person);
                return person;
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Person ForId(String id){
        if (null != id){
            Person person = (Person)Store.C.Get(id);
            if (null != person)
                return person;
            else {
                Query query = new Query("Person");
                query.addFilter("id",Query.FilterOperator.EQUAL,id);
                person = (Person)Store.P.Query1(Person.class,query);
                if (null != person)
                    Store.C.Put(id,person);
                return person;
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static Person Query1(Query query){
        if (null != query)
            return (Person)Store.P.Query1(Person.class,query);
        else
            throw new IllegalArgumentException();
    }
    public final static List QueryN(Query query, FetchOptions page){
        if (null != query)
            return Store.P.QueryN(Person.class,query,page);
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
        Roles("roles");


        private final static Map<String,Field> FieldName = new java.util.HashMap<String,Field>();
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
                instance.setRoles( (List<String>)value);
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
    private volatile List<String> roles;    




    public Person() {
        super();
    }
    public Person(String logonId) {
        super();
        this.setLogonId(logonId);
        String id = IdFor( logonId);
        this.setId(id);
        Key key = KeyFactory.createKey(KIND,id);
        this.setKey(key);
    }
    public Person(Key ancestor, String logonId) {
        super();
        this.setLogonId(logonId);
        String id = IdFor(ancestor,  logonId);
        this.setId(id);
        Key key = KeyFactory.createKey(ancestor,KIND,id);
        this.setKey(key);
    }


    public void init(){
    }


    public boolean hasKey(){
        return (null != this.key);
    }
    public boolean hasNotKey(){
        return (null == this.key);
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
    public List<String> getRoles(){
        return this.roles;
    }
    public void setRoles(List<String> roles){
        this.roles = roles;
    }
    public boolean isEmptyRoles(){
        List<String> list = this.roles;
        if (null != list)
            return list.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyRoles(){
        List<String> list = this.roles;
        if (null != list)
            return (!list.isEmpty());
        else
            return false;
    }
    public boolean containsRoles(String rolesItem){
        if (null != rolesItem){
            List<String> list = this.roles;
            if (null != list)
                return list.contains(rolesItem);
            else
                return false;
        }
        else
            throw new IllegalArgumentException();
    }
    public String getRoles(ListFilter<String> filter){
        if (null != filter){
            List<String> list = this.roles;
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
            List<String> list = this.roles;
            if (null == list){
                list = new java.util.ArrayList<String>();
                this.roles = list;
            }
            else if (list.contains(rolesItem))
                return;

            list.add(rolesItem);
        }
        else
            throw new IllegalArgumentException();
    }
    public void removeRoles(String rolesItem){
        if (null != rolesItem){
            List<String> list = this.roles;
            if (null != list)
                list.remove(rolesItem);
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
    public String getClassFieldUnique(){
        return "id";
    }
    public String getClassFieldKeyName(){
        return "key";
    }
    public List<gap.data.Field> getClassFields(){
        List<gap.data.Field> list = new java.util.ArrayList<gap.data.Field>();
        for (gap.data.Field field : Field.values())
            list.add(field);
        return list;
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
    public int updateFrom(gap.service.Query query, Person request){
        /*
         * 
         */
        return 0;
    }
    public TemplateDictionary dictionaryFor(){
        return this.dictionaryInto(Templates.CreateDictionary());
    }
    public TemplateDictionary dictionaryInto(gap.service.Query query, TemplateDictionary dict){
        if (null != query && query.hasFields()){

            for (String name: query.getFields()){
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
    public TemplateDictionary dictionaryFor(gap.service.Query query){
        return this.dictionaryInto(Templates.CreateDictionary());
    }

}
