
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-06T04:21:46.421Z",comments="gap.data")
public final class Template
    extends gap.data.BigTable
    implements LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Template";

    public final static String ClassName = "Template";

    public final static String DefaultSortBy = "name";

    static {
        Register(Template.class);
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
    public final static Template ForBaseName(Key ancestor, String base, String name){
        if (null != base && null != name){
            Key key = KeyIdFor(ancestor, base, name);
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Template ForBaseName(String base, String name){
        if (null != base && null != name){
            Key key = KeyIdFor( base, name);
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Template GetCreate(String base, String name){
        Template template = ForBaseName( base, name);
        if (null == template){
            template = new Template( base, name);
            template = (Template)gap.data.Store.Put(template);
        }
        return template;
    }
    public final static Template GetCreate(Key ancestor, String base, String name){
        Template template = ForBaseName(ancestor, base, name);
        if (null == template){
            template = new Template(ancestor, base, name);
            template = (Template)gap.data.Store.Put(template);
        }
        return template;
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
    public final static Template ForId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyFor(ancestor,id);
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Template ForId(String id){
        if (null != id){
            Key key = KeyFor(id);
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static Template Get(Key key){
        if (null != key){
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
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
    public final static void Delete(Template instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Template instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Template instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Template instance){
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
    public final static Template Query1(Query query){
        if (null != query)
            return (Template)gap.data.Store.Query1(query);
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
        Name("name"),
        LastModified("lastModified"),
        TemplateSourceHapax("templateSourceHapax"),
        TemplateContentType("templateContentType");


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
        public static Object Get(Field field, Template instance){
            switch(field){

            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Base:
                return instance.getBase();
            case Name:
                return instance.getName();
            case LastModified:
                return instance.getLastModified();
            case TemplateSourceHapax:
                return instance.getTemplateSourceHapax();
            case TemplateContentType:
                return instance.getTemplateContentType();
            default:
                throw new IllegalArgumentException(field.toString()+" in Template");
            }
        }
        public static void Set(Field field, Template instance, Object value){
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
            case LastModified:
                instance.setLastModified( (Long)value);
                return;
            case TemplateSourceHapax:
                instance.setTemplateSourceHapax( (Text)value);
                return;
            case TemplateContentType:
                instance.setTemplateContentType( (String)value);
                return;
            default:
                throw new IllegalArgumentException(field.toString()+" in Template");
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
    private volatile Long lastModified;    
    private volatile Text templateSourceHapax;    
    private volatile String templateContentType;    




    public Template() {
        super();
    }
    public Template(Key child) {
        super();
        this.setKey(child);
    }
    public Template(String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor( base, name);
        this.setId(id);
        Key key = KeyFor(id);
        this.setKey(key);
    }
    public Template(Key ancestor, String base, String name) {
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

    public boolean hasLastModified(){
        return (null != this.lastModified);
    }
    public boolean hasNotLastModified(){
        return (null == this.lastModified);
    }
    public boolean dropLastModified(){
        if (null != this.lastModified){
            this.lastModified = null;
            return true;
        }
        else
            return false;
    }
    public Long getLastModified(){
        return this.lastModified;
    }
    public void setLastModified(Long lastModified){
        this.lastModified = lastModified;
    }

    public boolean hasTemplateSourceHapax(){
        return (null != this.templateSourceHapax);
    }
    public boolean hasNotTemplateSourceHapax(){
        return (null == this.templateSourceHapax);
    }
    public boolean dropTemplateSourceHapax(){
        if (null != this.templateSourceHapax){
            this.templateSourceHapax = null;
            return true;
        }
        else
            return false;
    }
    public Text getTemplateSourceHapax(){
        return this.templateSourceHapax;
    }
    public void setTemplateSourceHapax(Text templateSourceHapax){
        this.templateSourceHapax = templateSourceHapax;
    }

    public boolean hasTemplateContentType(){
        return (null != this.templateContentType);
    }
    public boolean hasNotTemplateContentType(){
        return (null == this.templateContentType);
    }
    public boolean dropTemplateContentType(){
        if (null != this.templateContentType){
            this.templateContentType = null;
            return true;
        }
        else
            return false;
    }
    public String getTemplateContentType(){
        return this.templateContentType;
    }
    public void setTemplateContentType(String templateContentType){
        this.templateContentType = templateContentType;
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
    public TemplateDictionary dictionaryInto(gap.service.Parameters params, TemplateDictionary dict){
        if (null != params && params.hasFields()){
            TemplateDictionary data = dict.addSection(ClassName);
            for (String name: params.getFields()){
                Field field = Field.getField(name);
                if (null != field){
                    java.lang.Object value = Field.Get(field,this);
                    if (null != value){
                        data.putVariable(field.toString(),value.toString());
                    }
                }
            }
            return dict;
        }
        else
            return this.dictionaryInto(dict);
    }
    public TemplateDictionary dictionaryInto(TemplateDictionary dict){
        TemplateDictionary data = dict.addSection(ClassName);
        for (Field field : Field.values()){
            java.lang.Object value = Field.Get(field,this);
            if (null != value){
                data.putVariable(field.toString(),value.toString());
            }
        }
        return dict;
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
