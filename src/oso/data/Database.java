
package oso.data;

import gap.data.ListFilter;
import gap.data.Store;
import gap.service.Templates;

import hapax.TemplateDictionary;


import com.google.appengine.api.datastore.*;

import java.util.*;


import javax.annotation.Generated;

/** 
 * Generated from "odl/oso/data/Database.odl" with "odl/java.xtm".
 * 
 */
@Generated(value={"gap.odl.Main","odl/java.xtm"},date="2009-09-12T20:14:13.396Z",comments="odl/oso/data/Database.odl")
public final class Database
    extends gap.data.BigTable
{

    private final static long serialVersionUID = 1;


    public final static String IdFor(String name){
        if (null != name){
            String nameString = name;
            return gap.data.Hash.For(nameString);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Database ForName(String name){
        if (null != name){
            String id = IdFor( name);
            Database database = (Database)Store.C.Get(id);
            if (null != database)
                return database;
            else {
                Query query = new Query("Database");
                query.addFilter("name",Query.FilterOperator.EQUAL,name);
                database = (Database)Store.P.Query1(Database.class,query);
                if (null != database)
                    Store.C.Put(id,database);
                return database;
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Database GetCreate(String name){
        Database database = ForName( name);
        if (null == database){
            database = new Database( name);
            database = (Database)Store.P.Put(database);
            Store.C.Put(database.getId(),database);
        }
        return database;
    }


    public final static Database ForId(String id){
        if (null != id){
            Database database = (Database)Store.C.Get(id);
            if (null != database)
                return database;
            else {
                Query query = new Query("Database");
                query.addFilter("id",Query.FilterOperator.EQUAL,id);
                database = (Database)Store.P.Query1(Database.class,query);
                if (null != database)
                    Store.C.Put(id,database);
                return database;
            }
        }
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
        Name("name");


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
        public static Object Get(Field field, Database instance){
            switch(field){

            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Name:
                return instance.getName();
            default:
                throw new IllegalArgumentException(field.toString()+" in Database");
            }
        }
        public static void Set(Field field, Database instance, Object value){
            switch(field){

            case Key:
                instance.setKey( (Key)value);
                return;
            case Id:
                instance.setId( (String)value);
                return;
            case Name:
                instance.setName( (String)value);
                return;
            default:
                throw new IllegalArgumentException(field.toString()+" in Database");
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
    private volatile String name;    // *hash-unique




    public Database() {
        super();
    }
    public Database(String name) {
        super();
        this.setName(name);
        String id = IdFor( name);
        this.setId(id);
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

    public boolean hasName(){
        return (null != this.name);
    }
    public boolean hasNotName(){
        return (null == this.name);
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }




    /*
     * Data binding supports
     */
    public String getClassKind(){
        return "Database";
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
