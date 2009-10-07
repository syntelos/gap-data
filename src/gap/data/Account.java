
package gap.data;


import gap.data.*;
import gap.service.Templates;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * Data bean generated from "gap.data".
 */
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-07T01:15:04.442Z",comments="gap.data")
public final class Account
    extends gap.data.BigTable
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Account";

    public final static String ClassName = "Account";

    public final static String DefaultSortBy = "name";

    static {
        Register(Account.class);
    }


    public final static Key KeyIdFor(String base, String name){
        String id = IdFor( base,  name);
        return KeyFor(id);
    }
    public final static Key KeyIdFor(Key ancestor, String base, String name){
        String id = IdFor(ancestor, base,  name);
        return KeyFor(ancestor,id);
    }
    public final static String IdFor(Key ancestor, String base, String name){
        if (ancestor.isComplete() && null != base && null != name){
            String baseString = base;
            String nameString = name;
            return gap.data.Hash.For(ToString(ancestor)+'/'+baseString+'/'+nameString);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static String IdFor(String base, String name){
        if (null != base && null != name){
            String baseString = base;
            String nameString = name;
            return gap.data.Hash.For(baseString+'/'+nameString);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Account ForBaseName(Key ancestor, String base, String name){
        if (null != base && null != name){
            Key key = KeyIdFor(ancestor, base, name);
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Account ForBaseName(String base, String name){
        if (null != base && null != name){
            Key key = KeyIdFor( base, name);
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Account GetCreate(String base, String name){
        Account account = ForBaseName( base, name);
        if (null == account){
            account = new Account( base, name);
            account = (Account)gap.data.Store.Put(account);
        }
        return account;
    }
    public final static Account GetCreate(Key ancestor, String base, String name){
        Account account = ForBaseName(ancestor, base, name);
        if (null == account){
            account = new Account(ancestor, base, name);
            account = (Account)gap.data.Store.Put(account);
        }
        return account;
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
    public final static Account ForId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyFor(ancestor,id);
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Account ForId(String id){
        if (null != id){
            Key key = KeyFor(id);
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static Account Get(Key key){
        if (null != key){
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
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
    public final static void Delete(Account instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Account instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Account instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Account instance){
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
    public final static Account Query1(Query query){
        if (null != query)
            return (Account)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static List QueryN(Query query, FetchOptions page){
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
    public final static List<Key> QueryKeyN(Query query, FetchOptions page){
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
        Base("base"),
        Name("name");


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
        public static Object Get(Field field, Account instance){
            switch(field){

            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Base:
                return instance.getBase();
            case Name:
                return instance.getName();
            default:
                throw new IllegalArgumentException(field.toString()+" in Account");
            }
        }
        public static void Set(Field field, Account instance, Object value){
            switch(field){

            case Key:
                instance.setKey( (Key)value);
                return;
            case Id:
                instance.setId( (String)value);
                return;
            case Base:
                instance.setBase( (String)value);
                return;
            case Name:
                instance.setName( (String)value);
                return;
            default:
                throw new IllegalArgumentException(field.toString()+" in Account");
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
    private volatile String base;    // *hash-unique
    private volatile String name;    // *hash-unique




    public Account() {
        super();
    }
    public Account(Key child) {
        super();
        this.setKey(child);
    }
    public Account(String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor( base, name);
        this.setId(id);
        Key key = KeyFor(id);
        this.setKey(key);
    }
    public Account(Key ancestor, String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor(ancestor,  base, name);
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

    public boolean hasBase(){
        return (null != this.base);
    }
    public boolean hasNotBase(){
        return (null == this.base);
    }
    public boolean dropBase(){
        if (null != this.base){
            this.base = null;
            return true;
        }
        else
            return false;
    }
    public String getBase(){
        return this.base;
    }
    public void setBase(String base){
        this.base = base;
    }

    public boolean hasName(){
        return (null != this.name);
    }
    public boolean hasNotName(){
        return (null == this.name);
    }
    public boolean dropName(){
        if (null != this.name){
            this.name = null;
            return true;
        }
        else
            return false;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
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
    public List<gap.data.Field> getClassFields(){
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
    public TemplateDictionary dictionaryInto(gap.service.Parameters params, TemplateDictionary top){
        if (null != params && params.hasFields()){
            TemplateDictionary data = top.addSection(ClassName);
            for (String name: params.getFields()){
                Field field = Field.getField(name);
                if (null != field){
                    java.lang.Object value = Field.Get(field,this);
                    if (null != value){
                        if (value instanceof DictionaryInto){
                            DictionaryInto dvalue = (DictionaryInto)value;
                            TemplateDictionary dsection = data.addSection(field.name());
                            dvalue.dictionaryInto(dsection);
                        }
                        else
                            data.putVariable(field.name(),gap.data.validate.Account.ToString(field,value));
                    }
                }
            }
            return top;
        }
        else
            return this.dictionaryInto(top);
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary top){
        TemplateDictionary data = top.addSection(ClassName);
        for (Field field : Field.values()){
            java.lang.Object value = Field.Get(field,this);
            if (null != value){
                if (value instanceof DictionaryInto){
                    DictionaryInto dvalue = (DictionaryInto)value;
                    TemplateDictionary dsection = data.addSection(field.name());
                    dvalue.dictionaryInto(dsection);
                }
                else
                    data.putVariable(field.name(),gap.data.validate.Account.ToString(field,value));
            }
        }
        return top;
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
