
package gap.data;


import gap.data.*;
import gap.util.*;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import javax.annotation.Generated;

/**
 * Data bean generated from "gap.data".
 */
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-07T22:08:20.977Z",comments="gap.data")
public final class Image
    extends gap.data.BigTable
    implements LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Image";

    public final static String ClassName = "Image";

    public final static String DefaultSortBy = "name";

    static {
        Register(Image.class);
    }


    public final static Key KeyLongIdFor(String base, String name){
        String id = IdFor( base,  name);
        return KeyLongFor(id);
    }
    public final static Key KeyLongIdFor(Key ancestor, String base, String name){
        String id = IdFor(ancestor, base,  name);
        return KeyLongFor(ancestor,id);
    }
    public final static Key KeyShortIdFor(Key ancestor, String base, String name){
        String id = IdFor(ancestor, base,  name);
        return KeyShortFor(ancestor,id);
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
    public final static Image ForLongBaseName(Key ancestor, String base, String name){
        if (null != base && null != name){
            Key key = KeyLongIdFor(ancestor, base, name);
            Image instance = (Image)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Image)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Image ForShortBaseName(Key ancestor, String base, String name){
        if (null != base && null != name){
            Key key = KeyShortIdFor(ancestor, base, name);
            Image instance = (Image)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Image)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Image ForLongBaseName(String base, String name){
        if (null != base && null != name){
            Key key = KeyLongIdFor( base, name);
            Image instance = (Image)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Image)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Image GetCreateLong(String base, String name){
        Image image = ForLongBaseName( base, name);
        if (null == image){
            image = new Image( base, name);
            image = (Image)gap.data.Store.Put(image);
        }
        return image;
    }
    public final static Image GetCreateLong(Key ancestor, String base, String name){
        Image image = ForLongBaseName(ancestor, base, name);
        if (null == image){
            image = new Image(ancestor,false, base, name);
            image = (Image)gap.data.Store.Put(image);
        }
        return image;
    }
    public final static Image GetCreateShort(Key ancestor, String base, String name){
        Image image = ForShortBaseName(ancestor, base, name);
        if (null == image){
            image = new Image(ancestor,true, base, name);
            image = (Image)gap.data.Store.Put(image);
        }
        return image;
    }


    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND,id);
    }
    public final static Key KeyShortFor(Key ancestor, String id){
        return KeyFactory.createKey(ancestor,KIND,id);
    }
    public final static Key KeyLongFor(Key ancestor, String id){
        return KeyFactory.createKey(KIND,id);
    }
    public final static Image ForLongId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyLongFor(ancestor,id);
            Image instance = (Image)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Image)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Image ForShortId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyShortFor(ancestor,id);
            Image instance = (Image)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Image)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Image ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
            Image instance = (Image)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Image)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static Image Get(Key key){
        if (null != key){
            Image instance = (Image)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Image)gap.data.Store.Query1(q);
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
    public final static Key NewRandomKeyShort(Key ancestor){
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
                Key key = KeyFactory.createKey(ancestor,KIND,idString);
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
    public final static void Delete(Image instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Image instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Image instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Image instance){
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
    public final static Image Query1(Query query){
        if (null != query)
            return (Image)gap.data.Store.Query1(query);
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
        ContentType("contentType"),
        Bytes("bytes");


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
        public static Object Get(Field field, Image instance){
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
            case ContentType:
                return instance.getContentType();
            case Bytes:
                return instance.getBytes();
            default:
                throw new IllegalArgumentException(field.toString()+" in Image");
            }
        }
        public static void Set(Field field, Image instance, Object value){
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
            case ContentType:
                instance.setContentType( (String)value);
                return;
            case Bytes:
                instance.setBytes( (Blob)value);
                return;
            default:
                throw new IllegalArgumentException(field.toString()+" in Image");
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
    private volatile String contentType;    
    private volatile Blob bytes;    




    public Image() {
        super();
    }
    public Image(Key child) {
        super();
        this.setKey(child);
    }
    public Image(String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor( base, name);
        this.setId(id);
        Key key = KeyLongFor(id);
        this.setKey(key);
    }
    public Image(Key ancestor, boolean isShort, String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor(ancestor,  base, name);
        this.setId(id);
        Key key;
        if (isShort)
            key = KeyShortFor(ancestor,id);
        else
            key = KeyLongFor(ancestor,id);
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

        this.base = null;

        this.name = null;

        this.lastModified = null;

        this.contentType = null;

        this.bytes = null;

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

    public boolean hasContentType(){
        return (null != this.contentType);
    }
    public boolean hasNotContentType(){
        return (null == this.contentType);
    }
    public boolean dropContentType(){
        if (null != this.contentType){
            this.contentType = null;
            return true;
        }
        else
            return false;
    }
    public String getContentType(){
        return this.contentType;
    }
    public void setContentType(String contentType){
        this.contentType = contentType;
    }

    public boolean hasBytes(){
        return (null != this.bytes);
    }
    public boolean hasNotBytes(){
        return (null == this.bytes);
    }
    public boolean dropBytes(){
        if (null != this.bytes){
            this.bytes = null;
            return true;
        }
        else
            return false;
    }
    public Blob getBytes(){
        return this.bytes;
    }
    public void setBytes(Blob bytes){
        this.bytes = bytes;
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
                            data.putVariable(field.name(),gap.data.validate.Image.ToString(field,value));
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
                    data.putVariable(field.name(),gap.data.validate.Image.ToString(field,value));
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