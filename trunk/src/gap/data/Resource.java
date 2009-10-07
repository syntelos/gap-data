
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
@Generated(value={"gap.service.OD","odl/bean.xtm"},date="2009-10-07T22:08:20.519Z",comments="gap.data")
public final class Resource
    extends gap.data.BigTable
    implements LastModified
{

    private final static long serialVersionUID = 1;

    public final static String KIND = "Resource";

    public final static String ClassName = "Resource";

    public final static String DefaultSortBy = "name";

    static {
        Register(Resource.class);
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
    public final static Resource ForLongBaseName(Key ancestor, String base, String name){
        if (null != base && null != name){
            Key key = KeyLongIdFor(ancestor, base, name);
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
    public final static Resource ForShortBaseName(Key ancestor, String base, String name){
        if (null != base && null != name){
            Key key = KeyShortIdFor(ancestor, base, name);
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
    public final static Resource GetCreateLong(Key ancestor, String base, String name){
        Resource resource = ForLongBaseName(ancestor, base, name);
        if (null == resource){
            resource = new Resource(ancestor,false, base, name);
            resource = (Resource)gap.data.Store.Put(resource);
        }
        return resource;
    }
    public final static Resource GetCreateShort(Key ancestor, String base, String name){
        Resource resource = ForShortBaseName(ancestor, base, name);
        if (null == resource){
            resource = new Resource(ancestor,true, base, name);
            resource = (Resource)gap.data.Store.Put(resource);
        }
        return resource;
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
    public final static Resource ForLongId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyLongFor(ancestor,id);
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
    public final static Resource ForShortId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyShortFor(ancestor,id);
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
    public final static Query CreateQueryFor(Key ancestor){
        return new Query(KIND,ancestor);
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
        Tag("tag"),
        ServletClassname("servletClassname"),
        ServletSourceJava("servletSourceJava"),
        ServletClassfileJvm("servletClassfileJvm"),
        TemplateSourceHapax("templateSourceHapax"),
        TemplateContentType("templateContentType"),
        Partners("partners"),
        Accounts("accounts"),
        Images("images"),
        Templates("templates"),
        Tools("tools");


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
        public static Object Get(Field field, Resource instance){
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
            case Tag:
                return instance.getTag();
            case ServletClassname:
                return instance.getServletClassname();
            case ServletSourceJava:
                return instance.getServletSourceJava();
            case ServletClassfileJvm:
                return instance.getServletClassfileJvm();
            case TemplateSourceHapax:
                return instance.getTemplateSourceHapax();
            case TemplateContentType:
                return instance.getTemplateContentType();
            case Partners:
                return instance.getPartners();
            case Accounts:
                return instance.getAccounts();
            case Images:
                return instance.getImages();
            case Templates:
                return instance.getTemplates();
            case Tools:
                return instance.getTools();
            default:
                throw new IllegalArgumentException(field.toString()+" in Resource");
            }
        }
        public static void Set(Field field, Resource instance, Object value){
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
            case Tag:
                instance.setTag( (Category)value);
                return;
            case ServletClassname:
                instance.setServletClassname( (String)value);
                return;
            case ServletSourceJava:
                instance.setServletSourceJava( (Text)value);
                return;
            case ServletClassfileJvm:
                instance.setServletClassfileJvm( (Blob)value);
                return;
            case TemplateSourceHapax:
                instance.setTemplateSourceHapax( (Text)value);
                return;
            case TemplateContentType:
                instance.setTemplateContentType( (String)value);
                return;
            case Partners:
                instance.setPartners( (List.Long<Partner>)value);
                return;
            case Accounts:
                instance.setAccounts( (List.Long<Account>)value);
                return;
            case Images:
                instance.setImages( (List.Long<Image>)value);
                return;
            case Templates:
                instance.setTemplates( (List.Long<Template>)value);
                return;
            case Tools:
                instance.setTools( (List.Long<Tool>)value);
                return;
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
    private volatile List.Long<Image> images;    
    private volatile List.Long<Template> templates;    
    private volatile List.Long<Tool> tools;    




    public Resource() {
        super();
    }
    public Resource(Key child) {
        super();
        this.setKey(child);
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
    public Resource(Key ancestor, boolean isShort, String base, String name) {
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
        List.Long<Image> images = this.images;
        if (null != images){
            this.images = null;
            images.destroy();
        }
        List.Long<Template> templates = this.templates;
        if (null != templates){
            this.templates = null;
            templates.destroy();
        }
        List.Long<Tool> tools = this.tools;
        if (null != tools){
            this.tools = null;
            tools.destroy();
        }
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

    public boolean hasTag(){
        return (null != this.tag);
    }
    public boolean hasNotTag(){
        return (null == this.tag);
    }
    public boolean dropTag(){
        if (null != this.tag){
            this.tag = null;
            return true;
        }
        else
            return false;
    }
    public Category getTag(){
        return this.tag;
    }
    public void setTag(Category tag){
        this.tag = tag;
    }

    public boolean hasServletClassname(){
        return (null != this.servletClassname);
    }
    public boolean hasNotServletClassname(){
        return (null == this.servletClassname);
    }
    public boolean dropServletClassname(){
        if (null != this.servletClassname){
            this.servletClassname = null;
            return true;
        }
        else
            return false;
    }
    public String getServletClassname(){
        return this.servletClassname;
    }
    public void setServletClassname(String servletClassname){
        this.servletClassname = servletClassname;
    }

    public boolean hasServletSourceJava(){
        return (null != this.servletSourceJava);
    }
    public boolean hasNotServletSourceJava(){
        return (null == this.servletSourceJava);
    }
    public boolean dropServletSourceJava(){
        if (null != this.servletSourceJava){
            this.servletSourceJava = null;
            return true;
        }
        else
            return false;
    }
    public Text getServletSourceJava(){
        return this.servletSourceJava;
    }
    public void setServletSourceJava(Text servletSourceJava){
        this.servletSourceJava = servletSourceJava;
    }

    public boolean hasServletClassfileJvm(){
        return (null != this.servletClassfileJvm);
    }
    public boolean hasNotServletClassfileJvm(){
        return (null == this.servletClassfileJvm);
    }
    public boolean dropServletClassfileJvm(){
        if (null != this.servletClassfileJvm){
            this.servletClassfileJvm = null;
            return true;
        }
        else
            return false;
    }
    public Blob getServletClassfileJvm(){
        return this.servletClassfileJvm;
    }
    public void setServletClassfileJvm(Blob servletClassfileJvm){
        this.servletClassfileJvm = servletClassfileJvm;
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

    public boolean hasPartners(){
        return (null != this.partners);
    }
    public boolean hasNotPartners(){
        return (null == this.partners);
    }
    public boolean dropPartners(){
        if (null != this.partners){
            this.partners = null;
            return true;
        }
        else
            return false;
    }
    public List.Long<Partner> getPartners(){
        List.Long<Partner> partners = this.partners;
        if (null == partners){
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
        List.Long<Partner> list = this.partners;
        if (null != list)
            return list.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyPartners(){
        List.Long<Partner> list = this.partners;
        if (null != list)
            return (!list.isEmpty());
        else
            return false;
    }
    public Partner getPartners(gap.data.ListFilter<Partner> filter){
        if (null != filter){
            List.Long<Partner> list = this.getPartners();
            for (Partner item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasAccounts(){
        return (null != this.accounts);
    }
    public boolean hasNotAccounts(){
        return (null == this.accounts);
    }
    public boolean dropAccounts(){
        if (null != this.accounts){
            this.accounts = null;
            return true;
        }
        else
            return false;
    }
    public List.Long<Account> getAccounts(){
        List.Long<Account> accounts = this.accounts;
        if (null == accounts){
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
        List.Long<Account> list = this.accounts;
        if (null != list)
            return list.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyAccounts(){
        List.Long<Account> list = this.accounts;
        if (null != list)
            return (!list.isEmpty());
        else
            return false;
    }
    public Account getAccounts(gap.data.ListFilter<Account> filter){
        if (null != filter){
            List.Long<Account> list = this.getAccounts();
            for (Account item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasImages(){
        return (null != this.images);
    }
    public boolean hasNotImages(){
        return (null == this.images);
    }
    public boolean dropImages(){
        if (null != this.images){
            this.images = null;
            return true;
        }
        else
            return false;
    }
    public List.Long<Image> getImages(){
        List.Long<Image> images = this.images;
        if (null == images){
            images = new ListLongResourceImage(this);
            this.images = images;
            images.init();
        }
        return images;
    }
    public void setImages(List.Long<Image> images){
        this.images = images;
    }
    public boolean isEmptyImages(){
        List.Long<Image> list = this.images;
        if (null != list)
            return list.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyImages(){
        List.Long<Image> list = this.images;
        if (null != list)
            return (!list.isEmpty());
        else
            return false;
    }
    public Image getImages(gap.data.ListFilter<Image> filter){
        if (null != filter){
            List.Long<Image> list = this.getImages();
            for (Image item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasTemplates(){
        return (null != this.templates);
    }
    public boolean hasNotTemplates(){
        return (null == this.templates);
    }
    public boolean dropTemplates(){
        if (null != this.templates){
            this.templates = null;
            return true;
        }
        else
            return false;
    }
    public List.Long<Template> getTemplates(){
        List.Long<Template> templates = this.templates;
        if (null == templates){
            templates = new ListLongResourceTemplate(this);
            this.templates = templates;
            templates.init();
        }
        return templates;
    }
    public void setTemplates(List.Long<Template> templates){
        this.templates = templates;
    }
    public boolean isEmptyTemplates(){
        List.Long<Template> list = this.templates;
        if (null != list)
            return list.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyTemplates(){
        List.Long<Template> list = this.templates;
        if (null != list)
            return (!list.isEmpty());
        else
            return false;
    }
    public Template getTemplates(gap.data.ListFilter<Template> filter){
        if (null != filter){
            List.Long<Template> list = this.getTemplates();
            for (Template item : list){
                if (filter.accept(item))
                    return item;
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasTools(){
        return (null != this.tools);
    }
    public boolean hasNotTools(){
        return (null == this.tools);
    }
    public boolean dropTools(){
        if (null != this.tools){
            this.tools = null;
            return true;
        }
        else
            return false;
    }
    public List.Long<Tool> getTools(){
        List.Long<Tool> tools = this.tools;
        if (null == tools){
            tools = new ListLongResourceTool(this);
            this.tools = tools;
            tools.init();
        }
        return tools;
    }
    public void setTools(List.Long<Tool> tools){
        this.tools = tools;
    }
    public boolean isEmptyTools(){
        List.Long<Tool> list = this.tools;
        if (null != list)
            return list.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyTools(){
        List.Long<Tool> list = this.tools;
        if (null != list)
            return (!list.isEmpty());
        else
            return false;
    }
    public Tool getTools(gap.data.ListFilter<Tool> filter){
        if (null != filter){
            List.Long<Tool> list = this.getTools();
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
            java.lang.Object value = Field.Get(field,this);
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
