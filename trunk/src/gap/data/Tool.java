
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-09T09:20:30.440Z",comments="gap.data")
public final class Tool
    extends gap.data.BigTable
    implements LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Tool";

    public final static String ClassName = "Tool";

    public final static String DefaultSortBy = "name";

    static {
        Register(Tool.class);
    }


    public final static Key KeyLongIdFor(String name){
        String id = IdFor( name);
        return KeyLongFor(id);
    }
    public final static Key KeyLongIdFor(Key ancestor, String name){
        String id = IdFor(ancestor, name);
        return KeyLongFor(ancestor,id);
    }
    public final static Key KeyShortIdFor(Key ancestor, String name){
        String id = IdFor(ancestor, name);
        return KeyShortFor(ancestor,id);
    }
    public final static String IdFor(Key ancestor, String name){
        if (ancestor.isComplete() && null != name){
            String nameString = name;
            return gap.data.Hash.For(ToString(ancestor)+'/'+nameString);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static String IdFor(String name){
        if (null != name){
            String nameString = name;
            return gap.data.Hash.For(nameString);
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
    public final static Tool ForShortName(Key ancestor, String name){
        if (null != name){
            Key key = KeyShortIdFor(ancestor, name);
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
    public final static Tool ForLongName(String name){
        if (null != name){
            Key key = KeyLongIdFor( name);
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


    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND,id);
    }
    public final static Key KeyShortFor(Key ancestor, String id){
        return KeyFactory.createKey(ancestor,KIND,id);
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
    public final static Tool ForShortId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyShortFor(ancestor,id);
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
    public final static Tool ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
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
    public final static Query CreateQueryFor(Key ancestor){
        return new Query(KIND,ancestor);
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
     * 
     */
    public static enum Field
        implements gap.data.Field
    {

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
        public static Object Get(Field field, Tool instance){
            switch(field){

            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Name:
                return instance.getName();
            case LastModified:
                return instance.getLastModified();
            case HeadXtm:
                return instance.getHeadXtm();
            case OverlayXtm:
                return instance.getOverlayXtm();
            case FormXtm:
                return instance.getFormXtm();
            case TitleHiGraphicUri:
                return instance.getTitleHiGraphicUri();
            case TitleLoGraphicUri:
                return instance.getTitleLoGraphicUri();
            case ButtonHiGraphicUri:
                return instance.getButtonHiGraphicUri();
            case ButtonLoGraphicUri:
                return instance.getButtonLoGraphicUri();
            case ButtonOffGraphicUri:
                return instance.getButtonOffGraphicUri();
            case MethodName:
                return instance.getMethodName();
            case MethodBody:
                return instance.getMethodBody();
            case MethodClassfileJvm:
                return instance.getMethodClassfileJvm();
            default:
                throw new IllegalArgumentException(field.toString()+" in Tool");
            }
        }
        public static void Set(Field field, Tool instance, Object value){
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

    public boolean hasHeadXtm(){
        return (null != this.headXtm);
    }
    public boolean hasNotHeadXtm(){
        return (null == this.headXtm);
    }
    public boolean dropHeadXtm(){
        if (null != this.headXtm){
            this.headXtm = null;
            return true;
        }
        else
            return false;
    }
    public String getHeadXtm(){
        return this.headXtm;
    }
    public void setHeadXtm(String headXtm){
        this.headXtm = headXtm;
    }

    public boolean hasOverlayXtm(){
        return (null != this.overlayXtm);
    }
    public boolean hasNotOverlayXtm(){
        return (null == this.overlayXtm);
    }
    public boolean dropOverlayXtm(){
        if (null != this.overlayXtm){
            this.overlayXtm = null;
            return true;
        }
        else
            return false;
    }
    public String getOverlayXtm(){
        return this.overlayXtm;
    }
    public void setOverlayXtm(String overlayXtm){
        this.overlayXtm = overlayXtm;
    }

    public boolean hasFormXtm(){
        return (null != this.formXtm);
    }
    public boolean hasNotFormXtm(){
        return (null == this.formXtm);
    }
    public boolean dropFormXtm(){
        if (null != this.formXtm){
            this.formXtm = null;
            return true;
        }
        else
            return false;
    }
    public String getFormXtm(){
        return this.formXtm;
    }
    public void setFormXtm(String formXtm){
        this.formXtm = formXtm;
    }

    public boolean hasTitleHiGraphicUri(){
        return (null != this.titleHiGraphicUri);
    }
    public boolean hasNotTitleHiGraphicUri(){
        return (null == this.titleHiGraphicUri);
    }
    public boolean dropTitleHiGraphicUri(){
        if (null != this.titleHiGraphicUri){
            this.titleHiGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getTitleHiGraphicUri(){
        return this.titleHiGraphicUri;
    }
    public void setTitleHiGraphicUri(String titleHiGraphicUri){
        this.titleHiGraphicUri = titleHiGraphicUri;
    }

    public boolean hasTitleLoGraphicUri(){
        return (null != this.titleLoGraphicUri);
    }
    public boolean hasNotTitleLoGraphicUri(){
        return (null == this.titleLoGraphicUri);
    }
    public boolean dropTitleLoGraphicUri(){
        if (null != this.titleLoGraphicUri){
            this.titleLoGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getTitleLoGraphicUri(){
        return this.titleLoGraphicUri;
    }
    public void setTitleLoGraphicUri(String titleLoGraphicUri){
        this.titleLoGraphicUri = titleLoGraphicUri;
    }

    public boolean hasButtonHiGraphicUri(){
        return (null != this.buttonHiGraphicUri);
    }
    public boolean hasNotButtonHiGraphicUri(){
        return (null == this.buttonHiGraphicUri);
    }
    public boolean dropButtonHiGraphicUri(){
        if (null != this.buttonHiGraphicUri){
            this.buttonHiGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getButtonHiGraphicUri(){
        return this.buttonHiGraphicUri;
    }
    public void setButtonHiGraphicUri(String buttonHiGraphicUri){
        this.buttonHiGraphicUri = buttonHiGraphicUri;
    }

    public boolean hasButtonLoGraphicUri(){
        return (null != this.buttonLoGraphicUri);
    }
    public boolean hasNotButtonLoGraphicUri(){
        return (null == this.buttonLoGraphicUri);
    }
    public boolean dropButtonLoGraphicUri(){
        if (null != this.buttonLoGraphicUri){
            this.buttonLoGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getButtonLoGraphicUri(){
        return this.buttonLoGraphicUri;
    }
    public void setButtonLoGraphicUri(String buttonLoGraphicUri){
        this.buttonLoGraphicUri = buttonLoGraphicUri;
    }

    public boolean hasButtonOffGraphicUri(){
        return (null != this.buttonOffGraphicUri);
    }
    public boolean hasNotButtonOffGraphicUri(){
        return (null == this.buttonOffGraphicUri);
    }
    public boolean dropButtonOffGraphicUri(){
        if (null != this.buttonOffGraphicUri){
            this.buttonOffGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getButtonOffGraphicUri(){
        return this.buttonOffGraphicUri;
    }
    public void setButtonOffGraphicUri(String buttonOffGraphicUri){
        this.buttonOffGraphicUri = buttonOffGraphicUri;
    }

    public boolean hasMethodName(){
        return (null != this.methodName);
    }
    public boolean hasNotMethodName(){
        return (null == this.methodName);
    }
    public boolean dropMethodName(){
        if (null != this.methodName){
            this.methodName = null;
            return true;
        }
        else
            return false;
    }
    public String getMethodName(){
        return this.methodName;
    }
    public void setMethodName(String methodName){
        this.methodName = methodName;
    }

    public boolean hasMethodBody(){
        return (null != this.methodBody);
    }
    public boolean hasNotMethodBody(){
        return (null == this.methodBody);
    }
    public boolean dropMethodBody(){
        if (null != this.methodBody){
            this.methodBody = null;
            return true;
        }
        else
            return false;
    }
    public Text getMethodBody(){
        return this.methodBody;
    }
    public void setMethodBody(Text methodBody){
        this.methodBody = methodBody;
    }

    public boolean hasMethodClassfileJvm(){
        return (null != this.methodClassfileJvm);
    }
    public boolean hasNotMethodClassfileJvm(){
        return (null == this.methodClassfileJvm);
    }
    public boolean dropMethodClassfileJvm(){
        if (null != this.methodClassfileJvm){
            this.methodClassfileJvm = null;
            return true;
        }
        else
            return false;
    }
    public Blob getMethodClassfileJvm(){
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
            java.lang.Object value = Field.Get(field,this);
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

        Key key = Strings.KeyFromString(req.getParameter("key"));
        if (IsNotEqual(key,this.getKey())){
            this.setKey(key);
            change = true;
        }

        String id = Strings.StringFromString(req.getParameter("id"));
        if (IsNotEqual(id,this.getId())){
            this.setId(id);
            change = true;
        }

        String name = Strings.StringFromString(req.getParameter("name"));
        if (IsNotEqual(name,this.getName())){
            this.setName(name);
            change = true;
        }

        Long lastModified = Strings.LongFromString(req.getParameter("lastModified"));
        if (IsNotEqual(lastModified,this.getLastModified())){
            this.setLastModified(lastModified);
            change = true;
        }

        String headXtm = Strings.StringFromString(req.getParameter("headXtm"));
        if (IsNotEqual(headXtm,this.getHeadXtm())){
            this.setHeadXtm(headXtm);
            change = true;
        }

        String overlayXtm = Strings.StringFromString(req.getParameter("overlayXtm"));
        if (IsNotEqual(overlayXtm,this.getOverlayXtm())){
            this.setOverlayXtm(overlayXtm);
            change = true;
        }

        String formXtm = Strings.StringFromString(req.getParameter("formXtm"));
        if (IsNotEqual(formXtm,this.getFormXtm())){
            this.setFormXtm(formXtm);
            change = true;
        }

        String titleHiGraphicUri = Strings.StringFromString(req.getParameter("titleHiGraphicUri"));
        if (IsNotEqual(titleHiGraphicUri,this.getTitleHiGraphicUri())){
            this.setTitleHiGraphicUri(titleHiGraphicUri);
            change = true;
        }

        String titleLoGraphicUri = Strings.StringFromString(req.getParameter("titleLoGraphicUri"));
        if (IsNotEqual(titleLoGraphicUri,this.getTitleLoGraphicUri())){
            this.setTitleLoGraphicUri(titleLoGraphicUri);
            change = true;
        }

        String buttonHiGraphicUri = Strings.StringFromString(req.getParameter("buttonHiGraphicUri"));
        if (IsNotEqual(buttonHiGraphicUri,this.getButtonHiGraphicUri())){
            this.setButtonHiGraphicUri(buttonHiGraphicUri);
            change = true;
        }

        String buttonLoGraphicUri = Strings.StringFromString(req.getParameter("buttonLoGraphicUri"));
        if (IsNotEqual(buttonLoGraphicUri,this.getButtonLoGraphicUri())){
            this.setButtonLoGraphicUri(buttonLoGraphicUri);
            change = true;
        }

        String buttonOffGraphicUri = Strings.StringFromString(req.getParameter("buttonOffGraphicUri"));
        if (IsNotEqual(buttonOffGraphicUri,this.getButtonOffGraphicUri())){
            this.setButtonOffGraphicUri(buttonOffGraphicUri);
            change = true;
        }

        String methodName = Strings.StringFromString(req.getParameter("methodName"));
        if (IsNotEqual(methodName,this.getMethodName())){
            this.setMethodName(methodName);
            change = true;
        }

        Text methodBody = Strings.TextFromString(req.getParameter("methodBody"));
        if (IsNotEqual(methodBody,this.getMethodBody())){
            this.setMethodBody(methodBody);
            change = true;
        }

        Blob methodClassfileJvm = Strings.BlobFromString(req.getParameter("methodClassfileJvm"));
        if (IsNotEqual(methodClassfileJvm,this.getMethodClassfileJvm())){
            this.setMethodClassfileJvm(methodClassfileJvm);
            change = true;
        }

        return change;
    }
    public boolean updateFrom(BigTable proto){
        return this.updateFrom( (Tool)proto);
    }
    public boolean updateFrom(Tool proto){
        boolean change = false;

        Key key = proto.getKey();
        if (null != key && IsNotEqual(key,this.getKey())){
            this.setKey(key);
            change = true;
        }

        Long lastModified = proto.getLastModified();
        if (null != lastModified && IsNotEqual(lastModified,this.getLastModified())){
            this.setLastModified(lastModified);
            change = true;
        }

        String headXtm = proto.getHeadXtm();
        if (null != headXtm && IsNotEqual(headXtm,this.getHeadXtm())){
            this.setHeadXtm(headXtm);
            change = true;
        }

        String overlayXtm = proto.getOverlayXtm();
        if (null != overlayXtm && IsNotEqual(overlayXtm,this.getOverlayXtm())){
            this.setOverlayXtm(overlayXtm);
            change = true;
        }

        String formXtm = proto.getFormXtm();
        if (null != formXtm && IsNotEqual(formXtm,this.getFormXtm())){
            this.setFormXtm(formXtm);
            change = true;
        }

        String titleHiGraphicUri = proto.getTitleHiGraphicUri();
        if (null != titleHiGraphicUri && IsNotEqual(titleHiGraphicUri,this.getTitleHiGraphicUri())){
            this.setTitleHiGraphicUri(titleHiGraphicUri);
            change = true;
        }

        String titleLoGraphicUri = proto.getTitleLoGraphicUri();
        if (null != titleLoGraphicUri && IsNotEqual(titleLoGraphicUri,this.getTitleLoGraphicUri())){
            this.setTitleLoGraphicUri(titleLoGraphicUri);
            change = true;
        }

        String buttonHiGraphicUri = proto.getButtonHiGraphicUri();
        if (null != buttonHiGraphicUri && IsNotEqual(buttonHiGraphicUri,this.getButtonHiGraphicUri())){
            this.setButtonHiGraphicUri(buttonHiGraphicUri);
            change = true;
        }

        String buttonLoGraphicUri = proto.getButtonLoGraphicUri();
        if (null != buttonLoGraphicUri && IsNotEqual(buttonLoGraphicUri,this.getButtonLoGraphicUri())){
            this.setButtonLoGraphicUri(buttonLoGraphicUri);
            change = true;
        }

        String buttonOffGraphicUri = proto.getButtonOffGraphicUri();
        if (null != buttonOffGraphicUri && IsNotEqual(buttonOffGraphicUri,this.getButtonOffGraphicUri())){
            this.setButtonOffGraphicUri(buttonOffGraphicUri);
            change = true;
        }

        String methodName = proto.getMethodName();
        if (null != methodName && IsNotEqual(methodName,this.getMethodName())){
            this.setMethodName(methodName);
            change = true;
        }

        Text methodBody = proto.getMethodBody();
        if (null != methodBody && IsNotEqual(methodBody,this.getMethodBody())){
            this.setMethodBody(methodBody);
            change = true;
        }

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
