
package gap.data;


import gap.*;
import gap.data.*;
import gap.util.*;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * Data bean generated from "gap.data".
 */
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-10T16:46:29.316Z",comments="gap.data")
public final class Template
    extends gap.data.BigTable
    implements DataInheritance<Template>,
               LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Template";

    public final static String ClassName = "Template";

    public final static String DefaultSortBy = "name";

    static {
        Register(Template.class);
    }



    public final static Key KeyLongIdFor(Key ancestor, String name){
        String id = IdFor(ancestor, name);
        return KeyLongFor(ancestor,id);
    }


    public final static String IdFor(Key ancestor, String name){
        if (ancestor.isComplete() && null != name){
            String nameString = name;
            return gap.data.Hash.For(ToString(ancestor)+'/'+nameString);
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Template ForLongName(Key ancestor, String name){
        if (null != name){
            Key key = KeyLongIdFor(ancestor, name);
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


    public final static Template GetCreateLong(Key ancestor, String name){
        Template template = ForLongName(ancestor, name);
        if (null == template){
            template = new Template(ancestor, name);
            template = (Template)gap.data.Store.Put(template);
        }
        return template;
    }



    public final static Key KeyLongFor(Key ancestor, String id){
        return KeyFactory.createKey(KIND,id);
    }


    public final static Template ForLongId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyLongFor(ancestor,id);
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
    public final static Key NewRandomKeyLong(Key ancestor){
        if (null != ancestor){
            /*
             * Source matter for data local uniqueness
             */
            String source = gap.data.BigTable.ToString(ancestor);
            long matter = gap.data.Hash.Djb64(source);
            /*
             * Random matter for network global uniqueness
             */
            java.util.Random random = new java.util.Random();
            do {
                matter ^= random.nextLong();
                String idString = gap.data.Hash.Hex(matter);
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
    public final static Query CreateQueryFor(Key key){
        return new Query(KIND,key);
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
     * Persistent fields' binding for {@link Template}
     */
    public static enum Field
        implements gap.data.Field
    {
        InheritFromKey("inheritFromKey"),
        Key("key"),
        Id("id"),
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
        public static Object Get(Field field, Template instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey(mayInherit);
            case Id:
                return instance.getId(mayInherit);
            case Name:
                return instance.getName(mayInherit);
            case LastModified:
                return instance.getLastModified(mayInherit);
            case TemplateSourceHapax:
                return instance.getTemplateSourceHapax(mayInherit);
            case TemplateContentType:
                return instance.getTemplateContentType(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Template");
            }
        }
        public static void Set(Field field, Template instance, Object value){
            switch(field){
            case InheritFromKey:
                instance.setInheritFromKey( (Key)value);
            case Key:
                instance.setKey( (Key)value);
                return;
            case Id:
                instance.setId( (String)value);
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

    private volatile transient Template inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String name;    // *hash-unique
    private volatile Long lastModified;    
    private volatile Text templateSourceHapax;    
    private volatile String templateContentType;    






    private volatile transient Key parentKey; 


    public Template() {
        super();
    }
    public Template(Key child) {
        super();
        this.setKey(child);
    }
    public Template(Key ancestor, String name) {
        super();
        this.setName(name);
        String id = IdFor(ancestor,  name);
        this.setId(id);
        Key key = KeyLongFor(ancestor,id);
        this.setKey(key);
    }



    public void onread(){

    }
    public void onwrite(){

    }
    public void destroy(){
        this.datastoreEntity = null;
        this.key = null;
        this.id = null;
        this.name = null;
        this.lastModified = null;
        this.templateSourceHapax = null;
        this.templateContentType = null;
    }
    public boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public Template getInheritFrom(){
        Template inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Template.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public void setInheritFrom(Template ancestor){
        this.inheritFrom = ancestor;
        if (null != ancestor)
            this.inheritFromKey = ancestor.getKey();
    }

    public boolean hasKey(boolean mayInherit){
        return (null != this.getKey(mayInherit));
    }
    public boolean hasNotKey(boolean mayInherit){
        return (null == this.getKey(mayInherit));
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
    public Key getKey(boolean ignore){
        return this.key;
    }
    public void setKey(Key key){
        this.key = key;
    }

    public boolean hasId(boolean mayInherit){
        return (null != this.getId(mayInherit));
    }
    public boolean hasNotId(boolean mayInherit){
        return (null == this.getId(mayInherit));
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
    public String getId(boolean ignore){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }

    public boolean hasName(boolean mayInherit){
        return (null != this.getName(mayInherit));
    }
    public boolean hasNotName(boolean mayInherit){
        return (null == this.getName(mayInherit));
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
    public String getName(boolean ignore){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean hasLastModified(boolean mayInherit){
        return (null != this.getLastModified(mayInherit));
    }
    public boolean hasNotLastModified(boolean mayInherit){
        return (null == this.getLastModified(mayInherit));
    }
    public boolean dropLastModified(){
        if (null != this.lastModified){
            this.lastModified = null;
            return true;
        }
        else
            return false;
    }
    public Long getLastModified(boolean mayInherit){
        if (mayInherit){
            Long lastModified = this.lastModified;
            if (null == lastModified && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getLastModified(true);
            }
            return lastModified;
        }
        else
            return this.lastModified;
    }
    public void setLastModified(Long lastModified){
        this.lastModified = lastModified;
    }

    public boolean hasTemplateSourceHapax(boolean mayInherit){
        return (null != this.getTemplateSourceHapax(mayInherit));
    }
    public boolean hasNotTemplateSourceHapax(boolean mayInherit){
        return (null == this.getTemplateSourceHapax(mayInherit));
    }
    public boolean dropTemplateSourceHapax(){
        if (null != this.templateSourceHapax){
            this.templateSourceHapax = null;
            return true;
        }
        else
            return false;
    }
    public Text getTemplateSourceHapax(boolean mayInherit){
        if (mayInherit){
            Text templateSourceHapax = this.templateSourceHapax;
            if (null == templateSourceHapax && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTemplateSourceHapax(true);
            }
            return templateSourceHapax;
        }
        else
            return this.templateSourceHapax;
    }
    public void setTemplateSourceHapax(Text templateSourceHapax){
        this.templateSourceHapax = templateSourceHapax;
    }

    public boolean hasTemplateContentType(boolean mayInherit){
        return (null != this.getTemplateContentType(mayInherit));
    }
    public boolean hasNotTemplateContentType(boolean mayInherit){
        return (null == this.getTemplateContentType(mayInherit));
    }
    public boolean dropTemplateContentType(){
        if (null != this.templateContentType){
            this.templateContentType = null;
            return true;
        }
        else
            return false;
    }
    public String getTemplateContentType(boolean mayInherit){
        if (mayInherit){
            String templateContentType = this.templateContentType;
            if (null == templateContentType && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTemplateContentType(true);
            }
            return templateContentType;
        }
        else
            return this.templateContentType;
    }
    public void setTemplateContentType(String templateContentType){
        this.templateContentType = templateContentType;
    }



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
    public java.io.Serializable valueOf(gap.data.Field field, boolean mayInherit){
        return (java.io.Serializable)Field.Get((Field)field,this,mayInherit);
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
                    java.lang.Object value = Field.Get(field,this,true);
                    if (null != value){
                        if (value instanceof DictionaryInto){
                            DictionaryInto dvalue = (DictionaryInto)value;
                            TemplateDictionary dsection = data.addSection(field.name());
                            dvalue.dictionaryInto(dsection);
                        }
                        else
                            data.putVariable(field.name(),gap.data.validate.Template.ToString(field,value));
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
            java.lang.Object value = Field.Get(field,this,true);
            if (null != value){
                if (value instanceof DictionaryInto){
                    DictionaryInto dvalue = (DictionaryInto)value;
                    TemplateDictionary dsection = data.addSection(field.name());
                    dvalue.dictionaryInto(dsection);
                }
                else
                    data.putVariable(field.name(),gap.data.validate.Template.ToString(field,value));
            }
        }
        return top;
    }
    public boolean updateFrom(Request req){
        boolean change = false;
        return change;
    }
    public boolean updateFrom(BigTable proto){
        return this.updateFrom( (Template)proto);
    }
    public boolean updateFrom(Template proto){
        boolean change = false;
        return change;
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
