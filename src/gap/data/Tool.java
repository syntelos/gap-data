
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-07T01:15:04.636Z",comments="gap.data")
public final class Tool
    extends gap.data.BigTable
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Tool";

    public final static String ClassName = "Tool";

    public final static String DefaultSortBy = "name";

    static {
        Register(Tool.class);
    }


    public final static Key KeyIdFor(String name){
        String id = IdFor( name);
        return KeyFor(id);
    }
    public final static Key KeyIdFor(Key ancestor, String name){
        String id = IdFor(ancestor, name);
        return KeyFor(ancestor,id);
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
    public final static Tool ForName(Key ancestor, String name){
        if (null != name){
            Key key = KeyIdFor(ancestor, name);
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
    public final static Tool ForName(String name){
        if (null != name){
            Key key = KeyIdFor( name);
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
    public final static Tool GetCreate(String name){
        Tool tool = ForName( name);
        if (null == tool){
            tool = new Tool( name);
            tool = (Tool)gap.data.Store.Put(tool);
        }
        return tool;
    }
    public final static Tool GetCreate(Key ancestor, String name){
        Tool tool = ForName(ancestor, name);
        if (null == tool){
            tool = new Tool(ancestor, name);
            tool = (Tool)gap.data.Store.Put(tool);
        }
        return tool;
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
    public final static Tool ForId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyFor(ancestor,id);
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
    public final static Tool ForId(String id){
        if (null != id){
            Key key = KeyFor(id);
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
        HeadXtm("headXtm"),
        OverlayXtm("overlayXtm"),
        FormXtm("formXtm"),
        TitleGraphicUri("titleGraphicUri"),
        ButtonGraphicUri("buttonGraphicUri"),
        ButtonOffGraphicUri("buttonOffGraphicUri");


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
            case HeadXtm:
                return instance.getHeadXtm();
            case OverlayXtm:
                return instance.getOverlayXtm();
            case FormXtm:
                return instance.getFormXtm();
            case TitleGraphicUri:
                return instance.getTitleGraphicUri();
            case ButtonGraphicUri:
                return instance.getButtonGraphicUri();
            case ButtonOffGraphicUri:
                return instance.getButtonOffGraphicUri();
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
            case HeadXtm:
                instance.setHeadXtm( (String)value);
                return;
            case OverlayXtm:
                instance.setOverlayXtm( (String)value);
                return;
            case FormXtm:
                instance.setFormXtm( (String)value);
                return;
            case TitleGraphicUri:
                instance.setTitleGraphicUri( (String)value);
                return;
            case ButtonGraphicUri:
                instance.setButtonGraphicUri( (String)value);
                return;
            case ButtonOffGraphicUri:
                instance.setButtonOffGraphicUri( (String)value);
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
    private volatile String headXtm;    
    private volatile String overlayXtm;    
    private volatile String formXtm;    
    private volatile String titleGraphicUri;    
    private volatile String buttonGraphicUri;    
    private volatile String buttonOffGraphicUri;    




    public Tool() {
        super();
    }
    public Tool(Key child) {
        super();
        this.setKey(child);
    }
    public Tool(String name) {
        super();
        this.setName(name);
        String id = IdFor( name);
        this.setId(id);
        Key key = KeyFor(id);
        this.setKey(key);
    }
    public Tool(Key ancestor, String name) {
        super();
        this.setName(name);
        String id = IdFor(ancestor,  name);
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

    public boolean hasTitleGraphicUri(){
        return (null != this.titleGraphicUri);
    }
    public boolean hasNotTitleGraphicUri(){
        return (null == this.titleGraphicUri);
    }
    public boolean dropTitleGraphicUri(){
        if (null != this.titleGraphicUri){
            this.titleGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getTitleGraphicUri(){
        return this.titleGraphicUri;
    }
    public void setTitleGraphicUri(String titleGraphicUri){
        this.titleGraphicUri = titleGraphicUri;
    }

    public boolean hasButtonGraphicUri(){
        return (null != this.buttonGraphicUri);
    }
    public boolean hasNotButtonGraphicUri(){
        return (null == this.buttonGraphicUri);
    }
    public boolean dropButtonGraphicUri(){
        if (null != this.buttonGraphicUri){
            this.buttonGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public String getButtonGraphicUri(){
        return this.buttonGraphicUri;
    }
    public void setButtonGraphicUri(String buttonGraphicUri){
        this.buttonGraphicUri = buttonGraphicUri;
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
