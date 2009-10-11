
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-11T16:44:56.844Z",comments="gap.data")
public final class Resource
    extends gap.data.BigTable
    implements DataInheritance<Resource>,
               LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Resource";

    public final static String ClassName = "Resource";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Resource.class);
    }

    static {
        Register(Resource.class);
    }



    public final static Key KeyLongIdFor(String base, String name){
        String id = IdFor( base,  name);
        return KeyLongFor(id);
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


    public final static Resource ForLongBaseName(String base, String name){
        if (null != base && null != name){
            Key key = KeyLongIdFor( base, name);
            Resource instance = (Resource)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Resource)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Resource GetCreateLong(String base, String name){
        Resource resource = ForLongBaseName( base, name);
        if (null == resource){
            resource = new Resource( base, name);
            resource = (Resource)gap.data.Store.Put(resource);
        }
        return resource;
    }



    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND,id);
    }


    public final static Resource ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
            Resource instance = (Resource)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Resource)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Resource Get(Key key){
        if (null != key){
            Resource instance = (Resource)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Resource)gap.data.Store.Query1(q);
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
     * Drop the instance and any children of its key from the world,
     * memcache and store.
     */
    public final static void Delete(Resource instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Resource instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Resource instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Resource instance){
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
    public final static Resource Query1(Query query){
        if (null != query)
            return (Resource)gap.data.Store.Query1(query);
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
     * Persistent fields' binding for {@link Resource}
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
        Tag("tag"),
        ServletClassname("servletClassname"),
        ServletSourceJava("servletSourceJava"),
        ServletClassfileJvm("servletClassfileJvm"),
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
        public static Object Get(Field field, Resource instance, boolean mayInherit){
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
            case Tag:
                return instance.getTag(mayInherit);
            case ServletClassname:
                return instance.getServletClassname(mayInherit);
            case ServletSourceJava:
                return instance.getServletSourceJava(mayInherit);
            case ServletClassfileJvm:
                return instance.getServletClassfileJvm(mayInherit);
            case TemplateSourceHapax:
                return instance.getTemplateSourceHapax(mayInherit);
            case TemplateContentType:
                return instance.getTemplateContentType(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Resource");
            }
        }
        public static boolean Set(Field field, Resource instance, Object value){
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
            case Tag:
                return instance.setTag( (Category)value);
            case ServletClassname:
                return instance.setServletClassname( (String)value);
            case ServletSourceJava:
                return instance.setServletSourceJava( (Text)value);
            case ServletClassfileJvm:
                return instance.setServletClassfileJvm( (Blob)value);
            case TemplateSourceHapax:
                return instance.setTemplateSourceHapax( (Text)value);
            case TemplateContentType:
                return instance.setTemplateContentType( (String)value);
            default:
                throw new IllegalArgumentException(field.toString()+" in Resource");
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

    private volatile transient Resource inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String base;    // *hash-unique
    private volatile String name;    // *hash-unique
    private volatile Long lastModified;    
    private volatile Category tag;    
    private volatile String servletClassname;    
    private volatile Text servletSourceJava;    
    private volatile Blob servletClassfileJvm;    
    private volatile Text templateSourceHapax;    
    private volatile String templateContentType;    


    private volatile List.Long<Partner> partners;
    private volatile List.Long<Account> accounts;
    private volatile List.Short<Image> images;
    private volatile List.Short<Template> templates;
    private volatile List.Short<Tool> tools;






    public Resource() {
        super();
    }
    public Resource(String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor( base, name);
        this.setId(id);
        Key key = KeyLongFor(id);
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
        this.tag = null;
        this.servletClassname = null;
        this.servletSourceJava = null;
        this.servletClassfileJvm = null;
        this.templateSourceHapax = null;
        this.templateContentType = null;
        List.Long<Partner> partners = this.partners;
        if (null != partners){
            this.partners = null;
            partners.destroy();
        }
        List.Long<Account> accounts = this.accounts;
        if (null != accounts){
            this.accounts = null;
            accounts.destroy();
        }
        List.Short<Image> images = this.images;
        if (null != images){
            this.images = null;
            images.destroy();
        }
        List.Short<Template> templates = this.templates;
        if (null != templates){
            this.templates = null;
            templates.destroy();
        }
        List.Short<Tool> tools = this.tools;
        if (null != tools){
            this.tools = null;
            tools.destroy();
        }
    }
    public boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public Resource getInheritFrom(){
        Resource inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Resource.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public boolean setInheritFrom(Resource ancestor){
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
                Resource inheritFrom = this.getInheritFrom();
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

    public boolean hasTag(boolean mayInherit){
        return (null != this.getTag(mayInherit));
    }
    public boolean hasNotTag(boolean mayInherit){
        return (null == this.getTag(mayInherit));
    }
    public boolean dropTag(){
        if (null != this.tag){
            this.tag = null;
            return true;
        }
        else
            return false;
    }
    public Category getTag(boolean mayInherit){
        if (mayInherit){
            Category tag = this.tag;
            if (null == tag && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTag(true);
            }
            return tag;
        }
        else
            return this.tag;
    }
    public boolean setTag(Category tag, boolean withInheritance){
        if (IsNotEqual(this.tag,this.getTag(withInheritance))){
            this.tag = tag;
            return true;
        }
        else
            return false;
    }
    public boolean setTag(Category tag){
        if (IsNotEqual(this.tag,tag)){
            this.tag = tag;
            return true;
        }
        else
            return false;
    }

    public boolean hasServletClassname(boolean mayInherit){
        return (null != this.getServletClassname(mayInherit));
    }
    public boolean hasNotServletClassname(boolean mayInherit){
        return (null == this.getServletClassname(mayInherit));
    }
    public boolean dropServletClassname(){
        if (null != this.servletClassname){
            this.servletClassname = null;
            return true;
        }
        else
            return false;
    }
    public String getServletClassname(boolean mayInherit){
        if (mayInherit){
            String servletClassname = this.servletClassname;
            if (null == servletClassname && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getServletClassname(true);
            }
            return servletClassname;
        }
        else
            return this.servletClassname;
    }
    public boolean setServletClassname(String servletClassname, boolean withInheritance){
        if (IsNotEqual(this.servletClassname,this.getServletClassname(withInheritance))){
            this.servletClassname = servletClassname;
            return true;
        }
        else
            return false;
    }
    public boolean setServletClassname(String servletClassname){
        if (IsNotEqual(this.servletClassname,servletClassname)){
            this.servletClassname = servletClassname;
            return true;
        }
        else
            return false;
    }

    public boolean hasServletSourceJava(boolean mayInherit){
        return (null != this.getServletSourceJava(mayInherit));
    }
    public boolean hasNotServletSourceJava(boolean mayInherit){
        return (null == this.getServletSourceJava(mayInherit));
    }
    public boolean dropServletSourceJava(){
        if (null != this.servletSourceJava){
            this.servletSourceJava = null;
            return true;
        }
        else
            return false;
    }
    public Text getServletSourceJava(boolean mayInherit){
        if (mayInherit){
            Text servletSourceJava = this.servletSourceJava;
            if (null == servletSourceJava && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getServletSourceJava(true);
            }
            return servletSourceJava;
        }
        else
            return this.servletSourceJava;
    }
    public boolean setServletSourceJava(Text servletSourceJava, boolean withInheritance){
        if (IsNotEqual(this.servletSourceJava,this.getServletSourceJava(withInheritance))){
            this.servletSourceJava = servletSourceJava;
            return true;
        }
        else
            return false;
    }
    public boolean setServletSourceJava(Text servletSourceJava){
        if (IsNotEqual(this.servletSourceJava,servletSourceJava)){
            this.servletSourceJava = servletSourceJava;
            return true;
        }
        else
            return false;
    }

    public boolean hasServletClassfileJvm(boolean mayInherit){
        return (null != this.getServletClassfileJvm(mayInherit));
    }
    public boolean hasNotServletClassfileJvm(boolean mayInherit){
        return (null == this.getServletClassfileJvm(mayInherit));
    }
    public boolean dropServletClassfileJvm(){
        if (null != this.servletClassfileJvm){
            this.servletClassfileJvm = null;
            return true;
        }
        else
            return false;
    }
    public Blob getServletClassfileJvm(boolean mayInherit){
        if (mayInherit){
            Blob servletClassfileJvm = this.servletClassfileJvm;
            if (null == servletClassfileJvm && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getServletClassfileJvm(true);
            }
            return servletClassfileJvm;
        }
        else
            return this.servletClassfileJvm;
    }
    public boolean setServletClassfileJvm(Blob servletClassfileJvm, boolean withInheritance){
        if (IsNotEqual(this.servletClassfileJvm,this.getServletClassfileJvm(withInheritance))){
            this.servletClassfileJvm = servletClassfileJvm;
            return true;
        }
        else
            return false;
    }
    public boolean setServletClassfileJvm(Blob servletClassfileJvm){
        if (IsNotEqual(this.servletClassfileJvm,servletClassfileJvm)){
            this.servletClassfileJvm = servletClassfileJvm;
            return true;
        }
        else
            return false;
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
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTemplateSourceHapax(true);
            }
            return templateSourceHapax;
        }
        else
            return this.templateSourceHapax;
    }
    public boolean setTemplateSourceHapax(Text templateSourceHapax, boolean withInheritance){
        if (IsNotEqual(this.templateSourceHapax,this.getTemplateSourceHapax(withInheritance))){
            this.templateSourceHapax = templateSourceHapax;
            return true;
        }
        else
            return false;
    }
    public boolean setTemplateSourceHapax(Text templateSourceHapax){
        if (IsNotEqual(this.templateSourceHapax,templateSourceHapax)){
            this.templateSourceHapax = templateSourceHapax;
            return true;
        }
        else
            return false;
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
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTemplateContentType(true);
            }
            return templateContentType;
        }
        else
            return this.templateContentType;
    }
    public boolean setTemplateContentType(String templateContentType, boolean withInheritance){
        if (IsNotEqual(this.templateContentType,this.getTemplateContentType(withInheritance))){
            this.templateContentType = templateContentType;
            return true;
        }
        else
            return false;
    }
    public boolean setTemplateContentType(String templateContentType){
        if (IsNotEqual(this.templateContentType,templateContentType)){
            this.templateContentType = templateContentType;
            return true;
        }
        else
            return false;
    }

    public boolean hasPartners(boolean mayInherit){
        return (null != this.getPartners(mayInherit));
    }
    public boolean hasNotPartners(boolean mayInherit){
        return (null == this.getPartners(mayInherit));
    }
    public boolean dropPartners(){
        if (null != this.partners){
            this.partners = null;
            return true;
        }
        else
            return false;
    }
    public List.Long<Partner> getPartners(boolean mayInherit){
        List.Long<Partner> partners = this.partners;
        if (null == partners){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    partners = inheritFrom.getPartners(true);
                    if (null != partners)
                        return partners;
                }
            }
            partners = new ListLongResourcePartner(this);
            this.partners = partners;
            partners.init();
        }
        return partners;
    }
    public void setPartners(List.Long<Partner> partners){
        this.partners = partners;
    }
    public boolean isEmptyPartners(){
        List.Long<Partner> collection = this.partners;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyPartners(){
        List.Long<Partner> collection = this.partners;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Partner getPartners(gap.data.ListFilter<Partner> filter){
        if (null != filter){
            List.Long<Partner> list = this.getPartners(true);
            for (Partner item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasAccounts(boolean mayInherit){
        return (null != this.getAccounts(mayInherit));
    }
    public boolean hasNotAccounts(boolean mayInherit){
        return (null == this.getAccounts(mayInherit));
    }
    public boolean dropAccounts(){
        if (null != this.accounts){
            this.accounts = null;
            return true;
        }
        else
            return false;
    }
    public List.Long<Account> getAccounts(boolean mayInherit){
        List.Long<Account> accounts = this.accounts;
        if (null == accounts){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    accounts = inheritFrom.getAccounts(true);
                    if (null != accounts)
                        return accounts;
                }
            }
            accounts = new ListLongResourceAccount(this);
            this.accounts = accounts;
            accounts.init();
        }
        return accounts;
    }
    public void setAccounts(List.Long<Account> accounts){
        this.accounts = accounts;
    }
    public boolean isEmptyAccounts(){
        List.Long<Account> collection = this.accounts;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyAccounts(){
        List.Long<Account> collection = this.accounts;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Account getAccounts(gap.data.ListFilter<Account> filter){
        if (null != filter){
            List.Long<Account> list = this.getAccounts(true);
            for (Account item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasImages(boolean mayInherit){
        return (null != this.getImages(mayInherit));
    }
    public boolean hasNotImages(boolean mayInherit){
        return (null == this.getImages(mayInherit));
    }
    public boolean dropImages(){
        if (null != this.images){
            this.images = null;
            return true;
        }
        else
            return false;
    }
    public List.Short<Image> getImages(boolean mayInherit){
        List.Short<Image> images = this.images;
        if (null == images){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    images = inheritFrom.getImages(true);
                    if (null != images)
                        return images;
                }
            }
            images = new ListShortResourceImage(this);
            this.images = images;
            images.init();
        }
        return images;
    }
    public void setImages(List.Short<Image> images){
        this.images = images;
    }
    public boolean isEmptyImages(){
        List.Short<Image> collection = this.images;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyImages(){
        List.Short<Image> collection = this.images;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Image getImages(gap.data.ListFilter<Image> filter){
        if (null != filter){
            List.Short<Image> list = this.getImages(true);
            for (Image item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasTemplates(boolean mayInherit){
        return (null != this.getTemplates(mayInherit));
    }
    public boolean hasNotTemplates(boolean mayInherit){
        return (null == this.getTemplates(mayInherit));
    }
    public boolean dropTemplates(){
        if (null != this.templates){
            this.templates = null;
            return true;
        }
        else
            return false;
    }
    public List.Short<Template> getTemplates(boolean mayInherit){
        List.Short<Template> templates = this.templates;
        if (null == templates){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    templates = inheritFrom.getTemplates(true);
                    if (null != templates)
                        return templates;
                }
            }
            templates = new ListShortResourceTemplate(this);
            this.templates = templates;
            templates.init();
        }
        return templates;
    }
    public void setTemplates(List.Short<Template> templates){
        this.templates = templates;
    }
    public boolean isEmptyTemplates(){
        List.Short<Template> collection = this.templates;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyTemplates(){
        List.Short<Template> collection = this.templates;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Template getTemplates(gap.data.ListFilter<Template> filter){
        if (null != filter){
            List.Short<Template> list = this.getTemplates(true);
            for (Template item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasTools(boolean mayInherit){
        return (null != this.getTools(mayInherit));
    }
    public boolean hasNotTools(boolean mayInherit){
        return (null == this.getTools(mayInherit));
    }
    public boolean dropTools(){
        if (null != this.tools){
            this.tools = null;
            return true;
        }
        else
            return false;
    }
    public List.Short<Tool> getTools(boolean mayInherit){
        List.Short<Tool> tools = this.tools;
        if (null == tools){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    tools = inheritFrom.getTools(true);
                    if (null != tools)
                        return tools;
                }
            }
            tools = new ListShortResourceTool(this);
            this.tools = tools;
            tools.init();
        }
        return tools;
    }
    public void setTools(List.Short<Tool> tools){
        this.tools = tools;
    }
    public boolean isEmptyTools(){
        List.Short<Tool> collection = this.tools;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyTools(){
        List.Short<Tool> collection = this.tools;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Tool getTools(gap.data.ListFilter<Tool> filter){
        if (null != filter){
            List.Short<Tool> list = this.getTools(true);
            for (Tool item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
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
                            data.putVariable(field.name(),gap.data.validate.Resource.ToString(field,value));
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
                    data.putVariable(field.name(),gap.data.validate.Resource.ToString(field,value));
            }
        }
        return top;
    }
    public boolean updateFrom(Request req){
        boolean change = false;
        return change;
    }
    public boolean updateFrom(BigTable proto){
        return this.updateFrom( (Resource)proto);
    }
    public boolean updateFrom(Resource proto){
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
}
