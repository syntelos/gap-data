
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-10T16:46:29.102Z",comments="gap.data")
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
        public static void Set(Field field, Tool instance, Object value){
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
            case HeadXtm:
                instance.setHeadXtm( (String)value);
                return;
            case OverlayXtm:
                instance.setOverlayXtm( (String)value);
                return;
            case FormXtm:
                instance.setFormXtm( (String)value);
                return;
            case TitleHiGraphicUri:
                instance.setTitleHiGraphicUri( (String)value);
                return;
            case TitleLoGraphicUri:
                instance.setTitleLoGraphicUri( (String)value);
                return;
            case ButtonHiGraphicUri:
                instance.setButtonHiGraphicUri( (String)value);
                return;
            case ButtonLoGraphicUri:
                instance.setButtonLoGraphicUri( (String)value);
                return;
            case ButtonOffGraphicUri:
                instance.setButtonOffGraphicUri( (String)value);
                return;
            case MethodName:
                instance.setMethodName( (String)value);
                return;
            case MethodBody:
                instance.setMethodBody( (Text)value);
                return;
            case MethodClassfileJvm:
                instance.setMethodClassfileJvm( (Blob)value);
                return;
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
    public void setInheritFrom(Tool ancestor){
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
                Tool inheritFrom = this.getInheritFrom();
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
    public void setHeadXtm(String headXtm){
        this.headXtm = headXtm;
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
    public void setOverlayXtm(String overlayXtm){
        this.overlayXtm = overlayXtm;
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
    public void setFormXtm(String formXtm){
        this.formXtm = formXtm;
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
    public void setTitleHiGraphicUri(String titleHiGraphicUri){
        this.titleHiGraphicUri = titleHiGraphicUri;
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
    public void setTitleLoGraphicUri(String titleLoGraphicUri){
        this.titleLoGraphicUri = titleLoGraphicUri;
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
    public void setButtonHiGraphicUri(String buttonHiGraphicUri){
        this.buttonHiGraphicUri = buttonHiGraphicUri;
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
    public void setButtonLoGraphicUri(String buttonLoGraphicUri){
        this.buttonLoGraphicUri = buttonLoGraphicUri;
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
    public void setButtonOffGraphicUri(String buttonOffGraphicUri){
        this.buttonOffGraphicUri = buttonOffGraphicUri;
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
    public void setMethodName(String methodName){
        this.methodName = methodName;
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
    public void setMethodBody(Text methodBody){
        this.methodBody = methodBody;
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
    public void setMethodClassfileJvm(Blob methodClassfileJvm){
        this.methodClassfileJvm = methodClassfileJvm;
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
