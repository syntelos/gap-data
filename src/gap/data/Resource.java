/*
 * Gap Data
 * Copyright (C) 2009 John Pritchard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package gap.data;


import gap.*;
import gap.data.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated data bean
 */
@Generated(value={"gap.service.OD","Bean.java"},date="2010-01-28T21:41:10.444Z")
public final class Resource
    extends gap.data.BigTable
    implements DataInheritance<Resource>,
               LastModified,
               HasName
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("Resource","gap.data","Resource");

    public final static String ClassName = "Resource";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Resource.class);
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
        return KeyFactory.createKey(KIND.getName(),id);
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
            gap.data.Store.DeleteCollection(KIND,new Query(key));
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
        return new Query(KIND.getName());
    }
    public final static Query CreateQueryFor(Key key){
        return new Query(KIND.getName(),key);
    }
    public final static Query CreateQueryFor(Filter filter){
        Query query = new Query(KIND.getName());
        return filter.update(query);
    }
    
    
    public final static Resource Query1(Query query){
        if (null != query)
            return (Resource)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator QueryN(Query query, Page page){
        if (null != query && null != page)
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
    public final static List.Primitive<Key> QueryKeyN(Query query, Page page){
        if (null != query)
            return gap.data.Store.QueryKeyN(query,page);
        else
            throw new IllegalArgumentException();
    }

    /**
     * Persistent fields' binding for {@link Resource}
     */
    public static enum Field
        implements gap.data.Field<Field>
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
        ServletClassfileJvm("servletClassfileJvm");


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
        public static Field For(String name) {
            Field field = FieldName.get(name);
            if (null == field)
                return Field.valueOf(name);
            else
                return field;
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


    private volatile Map.Long<String,Partner> partners;
    private volatile List.Long<Account> accounts;
    private volatile Map.Short<String,Image> images;
    private volatile Map.Short<String,Template> templates;
    private volatile Map.Short<String,Tool> tools;






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
        this.inheritFrom = null;
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
        Map.Long<String,Partner> partners = this.partners;
        if (null != partners){
            this.partners = null;
            partners.destroy();
        }
        List.Long<Account> accounts = this.accounts;
        if (null != accounts){
            this.accounts = null;
            accounts.destroy();
        }
        Map.Short<String,Image> images = this.images;
        if (null != images){
            this.images = null;
            images.destroy();
        }
        Map.Short<String,Template> templates = this.templates;
        if (null != templates){
            this.templates = null;
            templates.destroy();
        }
        Map.Short<String,Tool> tools = this.tools;
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
    public boolean inheritFrom(Resource ancestor){
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
                    return inheritFrom.getLastModified(MayInherit);
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
                    return inheritFrom.getTag(MayInherit);
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
                    return inheritFrom.getServletClassname(MayInherit);
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
                    return inheritFrom.getServletSourceJava(MayInherit);
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
                    return inheritFrom.getServletClassfileJvm(MayInherit);
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

    public boolean hasPartners(boolean mayInherit){
        return (this.getPartners(mayInherit).isNotEmpty());
    }
    public boolean hasNotPartners(boolean mayInherit){
        return (this.getPartners(mayInherit).isEmpty());
    }
    public boolean dropPartners(){
        Map.Long<String,Partner> partners = this.partners;
        if (null != partners){
            this.partners = null;
            partners.destroy();
            return true;
        }
        else
            return false;
    }
    public Map.Long<String,Partner> getPartners(boolean mayInherit){
        Map.Long<String,Partner> partners = this.partners;
        if (null == partners){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    partners = inheritFrom.getPartners(MayInherit);
                    if (null != partners)
                        return partners;
                }
            }
            partners = new MapLongResourceStringPartner(this);
            this.partners = partners;
            partners.init();
        }
        return partners;
    }
    public void setPartners(Map.Long<String,Partner> partners){
        this.partners = partners;
    }
    public boolean isEmptyPartners(){
        Map.Long<String,Partner> collection = this.partners;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyPartners(){
        Map.Long<String,Partner> collection = this.partners;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Partner fetchPartners(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Long<String,Partner> collection = this.getPartners(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public Partner getPartners(String partnersName){
        if (null != partnersName){
            Map.Long<String,Partner> map = this.getPartners(MayInherit);
            if (null != map){
                Partner value = map.get(partnersName);
                if (null != value)
                    return value;
                else if (map.hitEnd()){
                    Filter filter = new Filter("Partner").add(Partner.Field.For("name"),Filter.Op.eq,name);
                    return map.fetch(filter);
                }
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasAccounts(boolean mayInherit){
        return (this.getAccounts(mayInherit).isNotEmpty());
    }
    public boolean hasNotAccounts(boolean mayInherit){
        return (this.getAccounts(mayInherit).isEmpty());
    }
    public boolean dropAccounts(){
        List.Long<Account> accounts = this.accounts;
        if (null != accounts){
            this.accounts = null;
            accounts.destroy();
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
                    accounts = inheritFrom.getAccounts(MayInherit);
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
    public Account fetchAccounts(Filter filter){
        if (null != filter && KIND == filter.kind){
            List.Long<Account> collection = this.getAccounts(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public Account getAccounts(gap.data.ListFilter<Account> filter){
        if (null != filter){
            List.Long<Account> list = this.getAccounts(MayInherit);
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
        return (this.getImages(mayInherit).isNotEmpty());
    }
    public boolean hasNotImages(boolean mayInherit){
        return (this.getImages(mayInherit).isEmpty());
    }
    public boolean dropImages(){
        Map.Short<String,Image> images = this.images;
        if (null != images){
            this.images = null;
            images.destroy();
            return true;
        }
        else
            return false;
    }
    public Map.Short<String,Image> getImages(boolean mayInherit){
        Map.Short<String,Image> images = this.images;
        if (null == images){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    images = inheritFrom.getImages(MayInherit);
                    if (null != images)
                        return images;
                }
            }
            images = new MapShortResourceStringImage(this);
            this.images = images;
            images.init();
        }
        return images;
    }
    public void setImages(Map.Short<String,Image> images){
        this.images = images;
    }
    public boolean isEmptyImages(){
        Map.Short<String,Image> collection = this.images;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyImages(){
        Map.Short<String,Image> collection = this.images;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Image fetchImages(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Short<String,Image> collection = this.getImages(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public Image getImages(String imagesName){
        if (null != imagesName){
            Map.Short<String,Image> map = this.getImages(MayInherit);
            if (null != map){
                Image value = map.get(imagesName);
                if (null != value)
                    return value;
                else if (map.hitEnd()){
                    Filter filter = new Filter("Image").add(Image.Field.For("name"),Filter.Op.eq,name);
                    return map.fetch(filter);
                }
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasTemplates(boolean mayInherit){
        return (this.getTemplates(mayInherit).isNotEmpty());
    }
    public boolean hasNotTemplates(boolean mayInherit){
        return (this.getTemplates(mayInherit).isEmpty());
    }
    public boolean dropTemplates(){
        Map.Short<String,Template> templates = this.templates;
        if (null != templates){
            this.templates = null;
            templates.destroy();
            return true;
        }
        else
            return false;
    }
    public Map.Short<String,Template> getTemplates(boolean mayInherit){
        Map.Short<String,Template> templates = this.templates;
        if (null == templates){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    templates = inheritFrom.getTemplates(MayInherit);
                    if (null != templates)
                        return templates;
                }
            }
            templates = new MapShortResourceStringTemplate(this);
            this.templates = templates;
            templates.init();
        }
        return templates;
    }
    public void setTemplates(Map.Short<String,Template> templates){
        this.templates = templates;
    }
    public boolean isEmptyTemplates(){
        Map.Short<String,Template> collection = this.templates;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyTemplates(){
        Map.Short<String,Template> collection = this.templates;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Template fetchTemplates(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Short<String,Template> collection = this.getTemplates(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public Template getTemplates(String templatesName){
        if (null != templatesName){
            Map.Short<String,Template> map = this.getTemplates(MayInherit);
            if (null != map){
                Template value = map.get(templatesName);
                if (null != value)
                    return value;
                else if (map.hitEnd()){
                    Filter filter = new Filter("Template").add(Template.Field.For("name"),Filter.Op.eq,name);
                    return map.fetch(filter);
                }
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }

    public boolean hasTools(boolean mayInherit){
        return (this.getTools(mayInherit).isNotEmpty());
    }
    public boolean hasNotTools(boolean mayInherit){
        return (this.getTools(mayInherit).isEmpty());
    }
    public boolean dropTools(){
        Map.Short<String,Tool> tools = this.tools;
        if (null != tools){
            this.tools = null;
            tools.destroy();
            return true;
        }
        else
            return false;
    }
    public Map.Short<String,Tool> getTools(boolean mayInherit){
        Map.Short<String,Tool> tools = this.tools;
        if (null == tools){
            if (mayInherit && this.hasInheritFrom()){
                Resource inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    tools = inheritFrom.getTools(MayInherit);
                    if (null != tools)
                        return tools;
                }
            }
            tools = new MapShortResourceStringTool(this);
            this.tools = tools;
            tools.init();
        }
        return tools;
    }
    public void setTools(Map.Short<String,Tool> tools){
        this.tools = tools;
    }
    public boolean isEmptyTools(){
        Map.Short<String,Tool> collection = this.tools;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public boolean isNotEmptyTools(){
        Map.Short<String,Tool> collection = this.tools;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public Tool fetchTools(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Short<String,Tool> collection = this.getTools(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public Tool getTools(String toolsName){
        if (null != toolsName){
            Map.Short<String,Tool> map = this.getTools(MayInherit);
            if (null != map){
                Tool value = map.get(toolsName);
                if (null != value)
                    return value;
                else if (map.hitEnd()){
                    Filter filter = new Filter("Tool").add(Tool.Field.For("name"),Filter.Op.eq,name);
                    return map.fetch(filter);
                }
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }



    /*
     * Data binding supports
     */
    public Kind getClassKind(){
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

    public boolean updateFrom(Request req) throws ValidationError {
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
