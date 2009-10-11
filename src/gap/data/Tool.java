
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-11T16:44:57.807Z",comments="gap.data")
public final class Tool
    extends gap.data.BigTable
    implements DataInheritance<Tool>,
               LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Tool";

    public final static String ClassName = "Tool";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Tool.class);
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorForParent(){
        return ClassDescriptorFor(Resource.class);
    }

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
        ParentKey("parentKey"),
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
        FunctionMethodname("functionMethodname"),
        FunctionClassname("functionClassname"),
        FunctionBody("functionBody"),
        FunctionSourceJava("functionSourceJava"),
        FunctionClassfileJvm("functionClassfileJvm");


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
            case ParentKey:
                return instance.getParentKey();
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
            case FunctionMethodname:
                return instance.getFunctionMethodname(mayInherit);
            case FunctionClassname:
                return instance.getFunctionClassname(mayInherit);
            case FunctionBody:
                return instance.getFunctionBody(mayInherit);
            case FunctionSourceJava:
                return instance.getFunctionSourceJava(mayInherit);
            case FunctionClassfileJvm:
                return instance.getFunctionClassfileJvm(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Tool");
            }
        }
        public static boolean Set(Field field, Tool instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey( (Key)value);
            case ParentKey:
                return instance.setParentKey( (Key)value);
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
            case FunctionMethodname:
                return instance.setFunctionMethodname( (String)value);
            case FunctionClassname:
                return instance.setFunctionClassname( (String)value);
            case FunctionBody:
                return instance.setFunctionBody( (Text)value);
            case FunctionSourceJava:
                return instance.setFunctionSourceJava( (Text)value);
            case FunctionClassfileJvm:
                return instance.setFunctionClassfileJvm( (Blob)value);
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
    private volatile String functionMethodname;    
    private volatile String functionClassname;    
    private volatile Text functionBody;    
    private volatile Text functionSourceJava;    
    private volatile Blob functionClassfileJvm;    






    private volatile Key parentKey;
    private volatile transient Resource parent;


    public Tool() {
        super();
    }
    public Tool(Key ancestor, String name) {
        super();
        this.setName(name);
        this.parentKey = ancestor;
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
        this.functionMethodname = null;
        this.functionClassname = null;
        this.functionBody = null;
        this.functionSourceJava = null;
        this.functionClassfileJvm = null;
        this.parent = null;
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

    public boolean hasParentKey(){
        return (null != this.parentKey);
    }
    public boolean hasNotParentKey(){
        return (null == this.parentKey);
    }
    public Key getParentKey(){
        return this.parentKey;
    }
    public boolean setParentKey(Key ancestor){
        if (IsNotEqual(this.parentKey,ancestor)){
            this.parentKey = ancestor;
            return true;
        }
        else
            return false;
    }
    public boolean hasParent(){
        return (null != this.parent || null != this.parentKey);
    }
    public boolean hasNotParent(){
        return (null == this.parent && null == this.parentKey);
    }
    public Resource getParent(){
        Resource parent = this.parent;
        if (null == parent){
            Key parentKey = this.parentKey;
            if (null != parentKey){
                parent = Resource.Get(parentKey);
                this.parent = parent;
            }
        }
        return parent;
    }
    public boolean setParent(Resource ancestor){
        if (IsNotEqual(this.parent,ancestor)){
            this.parent = ancestor;
            if (null != ancestor)
                this.parentKey = ancestor.getClassFieldKeyValue();
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

    public boolean hasFunctionMethodname(boolean mayInherit){
        return (null != this.getFunctionMethodname(mayInherit));
    }
    public boolean hasNotFunctionMethodname(boolean mayInherit){
        return (null == this.getFunctionMethodname(mayInherit));
    }
    public boolean dropFunctionMethodname(){
        if (null != this.functionMethodname){
            this.functionMethodname = null;
            return true;
        }
        else
            return false;
    }
    public String getFunctionMethodname(boolean mayInherit){
        if (mayInherit){
            String functionMethodname = this.functionMethodname;
            if (null == functionMethodname && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionMethodname(true);
            }
            return functionMethodname;
        }
        else
            return this.functionMethodname;
    }
    public boolean setFunctionMethodname(String functionMethodname, boolean withInheritance){
        if (IsNotEqual(this.functionMethodname,this.getFunctionMethodname(withInheritance))){
            this.functionMethodname = functionMethodname;
            return true;
        }
        else
            return false;
    }
    public boolean setFunctionMethodname(String functionMethodname){
        if (IsNotEqual(this.functionMethodname,functionMethodname)){
            this.functionMethodname = functionMethodname;
            return true;
        }
        else
            return false;
    }

    public boolean hasFunctionClassname(boolean mayInherit){
        return (null != this.getFunctionClassname(mayInherit));
    }
    public boolean hasNotFunctionClassname(boolean mayInherit){
        return (null == this.getFunctionClassname(mayInherit));
    }
    public boolean dropFunctionClassname(){
        if (null != this.functionClassname){
            this.functionClassname = null;
            return true;
        }
        else
            return false;
    }
    public String getFunctionClassname(boolean mayInherit){
        if (mayInherit){
            String functionClassname = this.functionClassname;
            if (null == functionClassname && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionClassname(true);
            }
            return functionClassname;
        }
        else
            return this.functionClassname;
    }
    public boolean setFunctionClassname(String functionClassname, boolean withInheritance){
        if (IsNotEqual(this.functionClassname,this.getFunctionClassname(withInheritance))){
            this.functionClassname = functionClassname;
            return true;
        }
        else
            return false;
    }
    public boolean setFunctionClassname(String functionClassname){
        if (IsNotEqual(this.functionClassname,functionClassname)){
            this.functionClassname = functionClassname;
            return true;
        }
        else
            return false;
    }

    public boolean hasFunctionBody(boolean mayInherit){
        return (null != this.getFunctionBody(mayInherit));
    }
    public boolean hasNotFunctionBody(boolean mayInherit){
        return (null == this.getFunctionBody(mayInherit));
    }
    public boolean dropFunctionBody(){
        if (null != this.functionBody){
            this.functionBody = null;
            return true;
        }
        else
            return false;
    }
    public Text getFunctionBody(boolean mayInherit){
        if (mayInherit){
            Text functionBody = this.functionBody;
            if (null == functionBody && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionBody(true);
            }
            return functionBody;
        }
        else
            return this.functionBody;
    }
    public boolean setFunctionBody(Text functionBody, boolean withInheritance){
        if (IsNotEqual(this.functionBody,this.getFunctionBody(withInheritance))){
            this.functionBody = functionBody;
            return true;
        }
        else
            return false;
    }
    public boolean setFunctionBody(Text functionBody){
        if (IsNotEqual(this.functionBody,functionBody)){
            this.functionBody = functionBody;
            return true;
        }
        else
            return false;
    }

    public boolean hasFunctionSourceJava(boolean mayInherit){
        return (null != this.getFunctionSourceJava(mayInherit));
    }
    public boolean hasNotFunctionSourceJava(boolean mayInherit){
        return (null == this.getFunctionSourceJava(mayInherit));
    }
    public boolean dropFunctionSourceJava(){
        if (null != this.functionSourceJava){
            this.functionSourceJava = null;
            return true;
        }
        else
            return false;
    }
    public Text getFunctionSourceJava(boolean mayInherit){
        if (mayInherit){
            Text functionSourceJava = this.functionSourceJava;
            if (null == functionSourceJava && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionSourceJava(true);
            }
            return functionSourceJava;
        }
        else
            return this.functionSourceJava;
    }
    public boolean setFunctionSourceJava(Text functionSourceJava, boolean withInheritance){
        if (IsNotEqual(this.functionSourceJava,this.getFunctionSourceJava(withInheritance))){
            this.functionSourceJava = functionSourceJava;
            return true;
        }
        else
            return false;
    }
    public boolean setFunctionSourceJava(Text functionSourceJava){
        if (IsNotEqual(this.functionSourceJava,functionSourceJava)){
            this.functionSourceJava = functionSourceJava;
            return true;
        }
        else
            return false;
    }

    public boolean hasFunctionClassfileJvm(boolean mayInherit){
        return (null != this.getFunctionClassfileJvm(mayInherit));
    }
    public boolean hasNotFunctionClassfileJvm(boolean mayInherit){
        return (null == this.getFunctionClassfileJvm(mayInherit));
    }
    public boolean dropFunctionClassfileJvm(){
        if (null != this.functionClassfileJvm){
            this.functionClassfileJvm = null;
            return true;
        }
        else
            return false;
    }
    public Blob getFunctionClassfileJvm(boolean mayInherit){
        if (mayInherit){
            Blob functionClassfileJvm = this.functionClassfileJvm;
            if (null == functionClassfileJvm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionClassfileJvm(true);
            }
            return functionClassfileJvm;
        }
        else
            return this.functionClassfileJvm;
    }
    public boolean setFunctionClassfileJvm(Blob functionClassfileJvm, boolean withInheritance){
        if (IsNotEqual(this.functionClassfileJvm,this.getFunctionClassfileJvm(withInheritance))){
            this.functionClassfileJvm = functionClassfileJvm;
            return true;
        }
        else
            return false;
    }
    public boolean setFunctionClassfileJvm(Blob functionClassfileJvm){
        if (IsNotEqual(this.functionClassfileJvm,functionClassfileJvm)){
            this.functionClassfileJvm = functionClassfileJvm;
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
    public gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
    public gap.service.od.ClassDescriptor getClassDescriptorForParent(){
        return ClassDescriptorForParent();
    }
}
