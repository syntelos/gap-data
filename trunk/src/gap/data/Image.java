
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-11T14:20:35.534Z",comments="gap.data")
public final class Image
    extends gap.data.BigTable
    implements DataInheritance<Image>,
               LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Image";

    public final static String ClassName = "Image";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Image.class);
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorForParent(){
        return ClassDescriptorFor(Resource.class);
    }

    static {
        Register(Image.class);
    }



    public final static Key KeyLongIdFor(Key ancestor, String base, String name){
        String id = IdFor(ancestor, base,  name);
        return KeyLongFor(ancestor,id);
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


    public final static Image GetCreateLong(Key ancestor, String base, String name){
        Image image = ForLongBaseName(ancestor, base, name);
        if (null == image){
            image = new Image(ancestor, base, name);
            image = (Image)gap.data.Store.Put(image);
        }
        return image;
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
    public final static Query CreateQueryFor(Key key){
        return new Query(KIND,key);
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
     * Persistent fields' binding for {@link Image}
     */
    public static enum Field
        implements gap.data.Field
    {
        InheritFromKey("inheritFromKey"),
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
        public static Object Get(Field field, Image instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey(mayInherit);
            case Id:
                return instance.getId(mayInherit);
            case Base:
                return instance.getBase(mayInherit);
            case Name:
                return instance.getName(mayInherit);
            case LastModified:
                return instance.getLastModified(mayInherit);
            case ContentType:
                return instance.getContentType(mayInherit);
            case Bytes:
                return instance.getBytes(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Image");
            }
        }
        public static boolean Set(Field field, Image instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey( (Key)value);
            case Key:
                return instance.setKey( (Key)value);
            case Id:
                return instance.setId( (String)value);
            case Base:
                return instance.setBase( (String)value);
            case Name:
                return instance.setName( (String)value);
            case LastModified:
                return instance.setLastModified( (Long)value);
            case ContentType:
                return instance.setContentType( (String)value);
            case Bytes:
                return instance.setBytes( (Blob)value);
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

    private volatile transient Image inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String base;    // *hash-unique
    private volatile String name;    // *hash-unique
    private volatile Long lastModified;    
    private volatile String contentType;    
    private volatile Blob bytes;    






    private volatile transient Key parentKey; 


    public Image() {
        super();
    }
    public Image(Key child) {
        super();
        this.setKey(child);
    }
    public Image(Key ancestor, String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor(ancestor,  base, name);
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
        this.base = null;
        this.name = null;
        this.lastModified = null;
        this.contentType = null;
        this.bytes = null;
    }
    public boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public Image getInheritFrom(){
        Image inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Image.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public boolean setInheritFrom(Image ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
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
    public boolean setKey(Key key){
        if (IsNotEqual(this.key,key)){
            this.key = key;
            return true;
        }
        else
            return false;
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
    public boolean setId(String id){
        if (IsNotEqual(this.id,id)){
            this.id = id;
            return true;
        }
        else
            return false;
    }

    public boolean hasBase(boolean mayInherit){
        return (null != this.getBase(mayInherit));
    }
    public boolean hasNotBase(boolean mayInherit){
        return (null == this.getBase(mayInherit));
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
    public String getBase(boolean ignore){
        return this.base;
    }
    public boolean setBase(String base){
        if (IsNotEqual(this.base,base)){
            this.base = base;
            return true;
        }
        else
            return false;
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
    public boolean setName(String name){
        if (IsNotEqual(this.name,name)){
            this.name = name;
            return true;
        }
        else
            return false;
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
                Image inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getLastModified(true);
            }
            return lastModified;
        }
        else
            return this.lastModified;
    }
    public boolean setLastModified(Long lastModified, boolean withInheritance){
        if (IsNotEqual(this.lastModified,this.getLastModified(withInheritance))){
            this.lastModified = lastModified;
            return true;
        }
        else
            return false;
    }
    public boolean setLastModified(Long lastModified){
        if (IsNotEqual(this.lastModified,lastModified)){
            this.lastModified = lastModified;
            return true;
        }
        else
            return false;
    }

    public boolean hasContentType(boolean mayInherit){
        return (null != this.getContentType(mayInherit));
    }
    public boolean hasNotContentType(boolean mayInherit){
        return (null == this.getContentType(mayInherit));
    }
    public boolean dropContentType(){
        if (null != this.contentType){
            this.contentType = null;
            return true;
        }
        else
            return false;
    }
    public String getContentType(boolean mayInherit){
        if (mayInherit){
            String contentType = this.contentType;
            if (null == contentType && this.hasInheritFrom()){
                Image inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getContentType(true);
            }
            return contentType;
        }
        else
            return this.contentType;
    }
    public boolean setContentType(String contentType, boolean withInheritance){
        if (IsNotEqual(this.contentType,this.getContentType(withInheritance))){
            this.contentType = contentType;
            return true;
        }
        else
            return false;
    }
    public boolean setContentType(String contentType){
        if (IsNotEqual(this.contentType,contentType)){
            this.contentType = contentType;
            return true;
        }
        else
            return false;
    }

    public boolean hasBytes(boolean mayInherit){
        return (null != this.getBytes(mayInherit));
    }
    public boolean hasNotBytes(boolean mayInherit){
        return (null == this.getBytes(mayInherit));
    }
    public boolean dropBytes(){
        if (null != this.bytes){
            this.bytes = null;
            return true;
        }
        else
            return false;
    }
    public Blob getBytes(boolean mayInherit){
        if (mayInherit){
            Blob bytes = this.bytes;
            if (null == bytes && this.hasInheritFrom()){
                Image inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getBytes(true);
            }
            return bytes;
        }
        else
            return this.bytes;
    }
    public boolean setBytes(Blob bytes, boolean withInheritance){
        if (IsNotEqual(this.bytes,this.getBytes(withInheritance))){
            this.bytes = bytes;
            return true;
        }
        else
            return false;
    }
    public boolean setBytes(Blob bytes){
        if (IsNotEqual(this.bytes,bytes)){
            this.bytes = bytes;
            return true;
        }
        else
            return false;
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
            java.lang.Object value = Field.Get(field,this,true);
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
    public boolean updateFrom(Request req){
        boolean change = false;
        return change;
    }
    public boolean updateFrom(BigTable proto){
        return this.updateFrom( (Image)proto);
    }
    public boolean updateFrom(Image proto){
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
    public gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
    public gap.service.od.ClassDescriptor getClassDescriptorForParent(){
        return ClassDescriptorForParent();
    }
}
