
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-10T17:23:49.587Z",comments="gap.data")
public final class Tool
    extends gap.data.BigTable
    implements DataInheritance<Tool>,
               LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Tool";

    public final static String ClassName = "Tool";

    public final static String DefaultSortBy = "name";

    static {
        Register(Tool.class);
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


    public final static Tool ForLongName(Key ancestor, String name){
        if (null != name){
            Key key = KeyLongIdFor(ancestor, name);
            Tool instance = (Tool)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Tool)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Tool GetCreateLong(Key ancestor, String name){
        Tool tool = ForLongName(ancestor, name);
        if (null == tool){
            tool = new Tool(ancestor, name);
            tool = (Tool)gap.data.Store.Put(tool);
        }
        return tool;
    }



    public final static Key KeyLongFor(Key ancestor, String id){
        return KeyFactory.createKey(KIND,id);
    }


    public final static Tool ForLongId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyLongFor(ancestor,id);
            Tool instance = (Tool)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Tool)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Tool Get(Key key){
        if (null != key){
            Tool instance = (Tool)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Tool)gap.data.Store.Query1(q);
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
    public final static void Delete(Tool instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Tool instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Tool instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Tool instance){
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
    public final static Tool Query1(Query query){
        if (null != query)
            return (Tool)gap.data.Store.Query1(query);
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
     * Persistent fields' binding for {@link Tool}
     */
    public static enum Field
        implements gap.data.Field
    {
        InheritFromKey("inheritFromKey"),
        Key("key"),
        Id("id"),
        Name("name"),
        LastModified("lastModified"),
        HeadXtm("headXtm"),
        OverlayXtm("overlayXtm"),
        FormXtm("formXtm"),
        TitleHiGraphicUri("titleHiGraphicUri"),
        TitleLoGraphicUri("titleLoGraphicUri"),
        ButtonHiGraphicUri("buttonHiGraphicUri"),
        ButtonLoGraphicUri("buttonLoGraphicUri"),
        ButtonOffGraphicUri("buttonOffGraphicUri"),
        MethodName("methodName"),
        MethodBody("methodBody"),
        MethodClassfileJvm("methodClassfileJvm");


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
        public static Object Get(Field field, Tool instance, boolean mayInherit){
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
            case HeadXtm:
                return instance.getHeadXtm(mayInherit);
            case OverlayXtm:
                return instance.getOverlayXtm(mayInherit);
            case FormXtm:
                return instance.getFormXtm(mayInherit);
            case TitleHiGraphicUri:
                return instance.getTitleHiGraphicUri(mayInherit);
            case TitleLoGraphicUri:
                return instance.getTitleLoGraphicUri(mayInherit);
            case ButtonHiGraphicUri:
                return instance.getButtonHiGraphicUri(mayInherit);
            case ButtonLoGraphicUri:
                return instance.getButtonLoGraphicUri(mayInherit);
            case ButtonOffGraphicUri:
                return instance.getButtonOffGraphicUri(mayInherit);
            case MethodName:
                return instance.getMethodName(mayInherit);
            case MethodBody:
                return instance.getMethodBody(mayInherit);
            case MethodClassfileJvm:
                return instance.getMethodClassfileJvm(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Tool");
            }
        }
        public static boolean Set(Field field, Tool instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey( (Key)value);
            case Key:
                return instance.setKey( (Key)value);
            case Id:
                return instance.setId( (String)value);
            case Name:
                return instance.setName( (String)value);
            case LastModified:
                return instance.setLastModified( (Long)value);
            case HeadXtm:
                return instance.setHeadXtm( (String)value);
            case OverlayXtm:
                return instance.setOverlayXtm( (String)value);
            case FormXtm:
                return instance.setFormXtm( (String)value);
            case TitleHiGraphicUri:
                return instance.setTitleHiGraphicUri( (String)value);
            case TitleLoGraphicUri:
                return instance.setTitleLoGraphicUri( (String)value);
            case ButtonHiGraphicUri:
                return instance.setButtonHiGraphicUri( (String)value);
            case ButtonLoGraphicUri:
                return instance.setButtonLoGraphicUri( (String)value);
            case ButtonOffGraphicUri:
                return instance.setButtonOffGraphicUri( (String)value);
            case MethodName:
                return instance.setMethodName( (String)value);
            case MethodBody:
                return instance.setMethodBody( (Text)value);
            case MethodClassfileJvm:
                return instance.setMethodClassfileJvm( (Blob)value);
            default:
                throw new IllegalArgumentException(field.toString()+" in Tool");
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

    private volatile transient Tool inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String name;    // *hash-unique
    private volatile Long lastModified;    
    private volatile String headXtm;    
    private volatile String overlayXtm;    
    private volatile String formXtm;    
    private volatile String titleHiGraphicUri;    
    private volatile String titleLoGraphicUri;    
    private volatile String buttonHiGraphicUri;    
    private volatile String buttonLoGraphicUri;    
    private volatile String buttonOffGraphicUri;    
    private volatile String methodName;    
    private volatile Text methodBody;    
    private volatile Blob methodClassfileJvm;    






    private volatile transient Key parentKey; 


    public Tool() {
        super();
    }
    public Tool(Key child) {
        super();
        this.setKey(child);
    }
    public Tool(Key ancestor, String name) {
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
        this.headXtm = null;
        this.overlayXtm = null;
        this.formXtm = null;
        this.titleHiGraphicUri = null;
        this.titleLoGraphicUri = null;
        this.buttonHiGraphicUri = null;
        this.buttonLoGraphicUri = null;
        this.buttonOffGraphicUri = null;
        this.methodName = null;
        this.methodBody = null;
        this.methodClassfileJvm = null;
    }
    public boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public Tool getInheritFrom(){
        Tool inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Tool.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public boolean setInheritFrom(Tool ancestor){
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
                Tool inheritFrom = this.getInheritFrom();
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

    public boolean hasHeadXtm(boolean mayInherit){
        return (null != this.getHeadXtm(mayInherit));
    }
    public boolean hasNotHeadXtm(boolean mayInherit){
        return (null == this.getHeadXtm(mayInherit));
    }
    public boolean dropHeadXtm(){
        if (null != this.headXtm){
            this.headXtm = null;
            return true;
        }
        else
            return false;
    }
    public String getHeadXtm(boolean mayInherit){
        if (mayInherit){
            String headXtm = this.headXtm;
            if (null == headXtm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getHeadXtm(true);
            }
            return headXtm;
        }
        else
            return this.headXtm;
    }
    public boolean setHeadXtm(String headXtm, boolean withInheritance){
        if (IsNotEqual(this.headXtm,this.getHeadXtm(withInheritance))){
            this.headXtm = headXtm;
            return true;
        }
        else
            return false;
    }
    public boolean setHeadXtm(String headXtm){
        if (IsNotEqual(this.headXtm,headXtm)){
            this.headXtm = headXtm;
            return true;
        }
        else
            return false;
    }

    public boolean hasOverlayXtm(boolean mayInherit){
        return (null != this.getOverlayXtm(mayInherit));
    }
    public boolean hasNotOverlayXtm(boolean mayInherit){
        return (null == this.getOverlayXtm(mayInherit));
    }
    public boolean dropOverlayXtm(){
        if (null != this.overlayXtm){
            this.overlayXtm = null;
            return true;
        }
        else
            return false;
    }
    public String getOverlayXtm(boolean mayInherit){
        if (mayInherit){
            String overlayXtm = this.overlayXtm;
            if (null == overlayXtm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getOverlayXtm(true);
            }
            return overlayXtm;
        }
        else
            return this.overlayXtm;
    }
    public boolean setOverlayXtm(String overlayXtm, boolean withInheritance){
        if (IsNotEqual(this.overlayXtm,this.getOverlayXtm(withInheritance))){
            this.overlayXtm = overlayXtm;
            return true;
        }
        else
            return false;
    }
    public boolean setOverlayXtm(String overlayXtm){
        if (IsNotEqual(this.overlayXtm,overlayXtm)){
            this.overlayXtm = overlayXtm;
            return true;
        }
        else
            return false;
    }

    public boolean hasFormXtm(boolean mayInherit){
        return (null != this.getFormXtm(mayInherit));
    }
    public boolean hasNotFormXtm(boolean mayInherit){
        return (null == this.getFormXtm(mayInherit));
    }
    public boolean dropFormXtm(){
        if (null != this.formXtm){
            this.formXtm = null;
            return true;
        }
        else
            return false;
    }
    public String getFormXtm(boolean mayInherit){
        if (mayInherit){
            String formXtm = this.formXtm;
            if (null == formXtm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFormXtm(true);
            }
            return formXtm;
        }
        else
            return this.formXtm;
    }
    public boolean setFormXtm(String formXtm, boolean withInheritance){
        if (IsNotEqual(this.formXtm,this.getFormXtm(withInheritance))){
            this.formXtm = formXtm;
            return true;
        }
        else
            return false;
    }
    public boolean setFormXtm(String formXtm){
        if (IsNotEqual(this.formXtm,formXtm)){
            this.formXtm = formXtm;
            return true;
        }
        else
            return false;
    }

    public boolean hasTitleHiGraphicUri(boolean mayInherit){
        return (null != this.getTitleHiGraphicUri(mayInherit));
    }
    public boolean hasNotTitleHiGraphicUri(boolean mayInherit){
        return (null == this.getTitleHiGraphicUri(mayInherit));
    }
    public boolean dropTitleHiGraphicUri(){
        if (null != this.titleHiGraphicUri){
            this.titleHiGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getTitleHiGraphicUri(boolean mayInherit){
        if (mayInherit){
            String titleHiGraphicUri = this.titleHiGraphicUri;
            if (null == titleHiGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTitleHiGraphicUri(true);
            }
            return titleHiGraphicUri;
        }
        else
            return this.titleHiGraphicUri;
    }
    public boolean setTitleHiGraphicUri(String titleHiGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.titleHiGraphicUri,this.getTitleHiGraphicUri(withInheritance))){
            this.titleHiGraphicUri = titleHiGraphicUri;
            return true;
        }
        else
            return false;
    }
    public boolean setTitleHiGraphicUri(String titleHiGraphicUri){
        if (IsNotEqual(this.titleHiGraphicUri,titleHiGraphicUri)){
            this.titleHiGraphicUri = titleHiGraphicUri;
            return true;
        }
        else
            return false;
    }

    public boolean hasTitleLoGraphicUri(boolean mayInherit){
        return (null != this.getTitleLoGraphicUri(mayInherit));
    }
    public boolean hasNotTitleLoGraphicUri(boolean mayInherit){
        return (null == this.getTitleLoGraphicUri(mayInherit));
    }
    public boolean dropTitleLoGraphicUri(){
        if (null != this.titleLoGraphicUri){
            this.titleLoGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getTitleLoGraphicUri(boolean mayInherit){
        if (mayInherit){
            String titleLoGraphicUri = this.titleLoGraphicUri;
            if (null == titleLoGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTitleLoGraphicUri(true);
            }
            return titleLoGraphicUri;
        }
        else
            return this.titleLoGraphicUri;
    }
    public boolean setTitleLoGraphicUri(String titleLoGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.titleLoGraphicUri,this.getTitleLoGraphicUri(withInheritance))){
            this.titleLoGraphicUri = titleLoGraphicUri;
            return true;
        }
        else
            return false;
    }
    public boolean setTitleLoGraphicUri(String titleLoGraphicUri){
        if (IsNotEqual(this.titleLoGraphicUri,titleLoGraphicUri)){
            this.titleLoGraphicUri = titleLoGraphicUri;
            return true;
        }
        else
            return false;
    }

    public boolean hasButtonHiGraphicUri(boolean mayInherit){
        return (null != this.getButtonHiGraphicUri(mayInherit));
    }
    public boolean hasNotButtonHiGraphicUri(boolean mayInherit){
        return (null == this.getButtonHiGraphicUri(mayInherit));
    }
    public boolean dropButtonHiGraphicUri(){
        if (null != this.buttonHiGraphicUri){
            this.buttonHiGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getButtonHiGraphicUri(boolean mayInherit){
        if (mayInherit){
            String buttonHiGraphicUri = this.buttonHiGraphicUri;
            if (null == buttonHiGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getButtonHiGraphicUri(true);
            }
            return buttonHiGraphicUri;
        }
        else
            return this.buttonHiGraphicUri;
    }
    public boolean setButtonHiGraphicUri(String buttonHiGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.buttonHiGraphicUri,this.getButtonHiGraphicUri(withInheritance))){
            this.buttonHiGraphicUri = buttonHiGraphicUri;
            return true;
        }
        else
            return false;
    }
    public boolean setButtonHiGraphicUri(String buttonHiGraphicUri){
        if (IsNotEqual(this.buttonHiGraphicUri,buttonHiGraphicUri)){
            this.buttonHiGraphicUri = buttonHiGraphicUri;
            return true;
        }
        else
            return false;
    }

    public boolean hasButtonLoGraphicUri(boolean mayInherit){
        return (null != this.getButtonLoGraphicUri(mayInherit));
    }
    public boolean hasNotButtonLoGraphicUri(boolean mayInherit){
        return (null == this.getButtonLoGraphicUri(mayInherit));
    }
    public boolean dropButtonLoGraphicUri(){
        if (null != this.buttonLoGraphicUri){
            this.buttonLoGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getButtonLoGraphicUri(boolean mayInherit){
        if (mayInherit){
            String buttonLoGraphicUri = this.buttonLoGraphicUri;
            if (null == buttonLoGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getButtonLoGraphicUri(true);
            }
            return buttonLoGraphicUri;
        }
        else
            return this.buttonLoGraphicUri;
    }
    public boolean setButtonLoGraphicUri(String buttonLoGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.buttonLoGraphicUri,this.getButtonLoGraphicUri(withInheritance))){
            this.buttonLoGraphicUri = buttonLoGraphicUri;
            return true;
        }
        else
            return false;
    }
    public boolean setButtonLoGraphicUri(String buttonLoGraphicUri){
        if (IsNotEqual(this.buttonLoGraphicUri,buttonLoGraphicUri)){
            this.buttonLoGraphicUri = buttonLoGraphicUri;
            return true;
        }
        else
            return false;
    }

    public boolean hasButtonOffGraphicUri(boolean mayInherit){
        return (null != this.getButtonOffGraphicUri(mayInherit));
    }
    public boolean hasNotButtonOffGraphicUri(boolean mayInherit){
        return (null == this.getButtonOffGraphicUri(mayInherit));
    }
    public boolean dropButtonOffGraphicUri(){
        if (null != this.buttonOffGraphicUri){
            this.buttonOffGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getButtonOffGraphicUri(boolean mayInherit){
        if (mayInherit){
            String buttonOffGraphicUri = this.buttonOffGraphicUri;
            if (null == buttonOffGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getButtonOffGraphicUri(true);
            }
            return buttonOffGraphicUri;
        }
        else
            return this.buttonOffGraphicUri;
    }
    public boolean setButtonOffGraphicUri(String buttonOffGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.buttonOffGraphicUri,this.getButtonOffGraphicUri(withInheritance))){
            this.buttonOffGraphicUri = buttonOffGraphicUri;
            return true;
        }
        else
            return false;
    }
    public boolean setButtonOffGraphicUri(String buttonOffGraphicUri){
        if (IsNotEqual(this.buttonOffGraphicUri,buttonOffGraphicUri)){
            this.buttonOffGraphicUri = buttonOffGraphicUri;
            return true;
        }
        else
            return false;
    }

    public boolean hasMethodName(boolean mayInherit){
        return (null != this.getMethodName(mayInherit));
    }
    public boolean hasNotMethodName(boolean mayInherit){
        return (null == this.getMethodName(mayInherit));
    }
    public boolean dropMethodName(){
        if (null != this.methodName){
            this.methodName = null;
            return true;
        }
        else
            return false;
    }
    public String getMethodName(boolean mayInherit){
        if (mayInherit){
            String methodName = this.methodName;
            if (null == methodName && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getMethodName(true);
            }
            return methodName;
        }
        else
            return this.methodName;
    }
    public boolean setMethodName(String methodName, boolean withInheritance){
        if (IsNotEqual(this.methodName,this.getMethodName(withInheritance))){
            this.methodName = methodName;
            return true;
        }
        else
            return false;
    }
    public boolean setMethodName(String methodName){
        if (IsNotEqual(this.methodName,methodName)){
            this.methodName = methodName;
            return true;
        }
        else
            return false;
    }

    public boolean hasMethodBody(boolean mayInherit){
        return (null != this.getMethodBody(mayInherit));
    }
    public boolean hasNotMethodBody(boolean mayInherit){
        return (null == this.getMethodBody(mayInherit));
    }
    public boolean dropMethodBody(){
        if (null != this.methodBody){
            this.methodBody = null;
            return true;
        }
        else
            return false;
    }
    public Text getMethodBody(boolean mayInherit){
        if (mayInherit){
            Text methodBody = this.methodBody;
            if (null == methodBody && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getMethodBody(true);
            }
            return methodBody;
        }
        else
            return this.methodBody;
    }
    public boolean setMethodBody(Text methodBody, boolean withInheritance){
        if (IsNotEqual(this.methodBody,this.getMethodBody(withInheritance))){
            this.methodBody = methodBody;
            return true;
        }
        else
            return false;
    }
    public boolean setMethodBody(Text methodBody){
        if (IsNotEqual(this.methodBody,methodBody)){
            this.methodBody = methodBody;
            return true;
        }
        else
            return false;
    }

    public boolean hasMethodClassfileJvm(boolean mayInherit){
        return (null != this.getMethodClassfileJvm(mayInherit));
    }
    public boolean hasNotMethodClassfileJvm(boolean mayInherit){
        return (null == this.getMethodClassfileJvm(mayInherit));
    }
    public boolean dropMethodClassfileJvm(){
        if (null != this.methodClassfileJvm){
            this.methodClassfileJvm = null;
            return true;
        }
        else
            return false;
    }
    public Blob getMethodClassfileJvm(boolean mayInherit){
        if (mayInherit){
            Blob methodClassfileJvm = this.methodClassfileJvm;
            if (null == methodClassfileJvm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getMethodClassfileJvm(true);
            }
            return methodClassfileJvm;
        }
        else
            return this.methodClassfileJvm;
    }
    public boolean setMethodClassfileJvm(Blob methodClassfileJvm, boolean withInheritance){
        if (IsNotEqual(this.methodClassfileJvm,this.getMethodClassfileJvm(withInheritance))){
            this.methodClassfileJvm = methodClassfileJvm;
            return true;
        }
        else
            return false;
    }
    public boolean setMethodClassfileJvm(Blob methodClassfileJvm){
        if (IsNotEqual(this.methodClassfileJvm,methodClassfileJvm)){
            this.methodClassfileJvm = methodClassfileJvm;
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
                            data.putVariable(field.name(),gap.data.validate.Tool.ToString(field,value));
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
                    data.putVariable(field.name(),gap.data.validate.Tool.ToString(field,value));
            }
        }
        return top;
    }
    public boolean updateFrom(Request req){
        boolean change = false;
        return change;
    }
    public boolean updateFrom(BigTable proto){
        return this.updateFrom( (Tool)proto);
    }
    public boolean updateFrom(Tool proto){
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
