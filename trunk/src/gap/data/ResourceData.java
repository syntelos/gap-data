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

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated data bean
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-01-31T13:10:16.653Z")
public abstract class ResourceData
    extends gap.data.BigTable
    implements DataInheritance<Resource>,
               LastModified,
               HasName
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("Resource","gap.data","Resource","/resources");

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
    /**
     * Default sort
     */
    public final static Query CreateQueryFor(){
        return new Query(KIND.getName()).addSort(DefaultSortBy);
    }
    /**
     * Default sort
     */
    public final static Query CreateQueryFor(Key key){
        return new Query(KIND.getName(),key).addSort(DefaultSortBy);
    }
    /**
     * Filter ops
     */
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
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case Base:
                return instance.setBase(gap.Objects.StringFromObject(value));
            case Name:
                return instance.setName(gap.Objects.StringFromObject(value));
            case LastModified:
                return instance.setLastModified(gap.Objects.LongFromObject(value));
            case Tag:
                return instance.setTag(gap.Objects.CategoryFromObject(value));
            case ServletClassname:
                return instance.setServletClassname(gap.Objects.StringFromObject(value));
            case ServletSourceJava:
                return instance.setServletSourceJava(gap.Objects.TextFromObject(value));
            case ServletClassfileJvm:
                return instance.setServletClassfileJvm(gap.Objects.BlobFromObject(value));
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






    public ResourceData() {
        super();
    }
    public ResourceData(String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        String id = IdFor( base, name);
        this.setId(id);
        Key key = KeyLongFor(id);
        this.setKey(key);
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
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final Resource getInheritFrom(){
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
    public final boolean setInheritFrom(Resource ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(Resource ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }


    public final boolean hasKey(boolean mayInherit){
        return (null != this.getKey(mayInherit));
    }
    public final boolean hasNotKey(boolean mayInherit){
        return (null == this.getKey(mayInherit));
    }
    public final boolean dropKey(){
        if (null != this.key){
            this.key = null;
            return true;
        }
        else
            return false;
    }
    public final Key getKey(){
        return this.key;
    }
    public final Key getKey(boolean ignore){
        return this.key;
    }
    public final boolean setKey(Key key){
        if (IsNotEqual(this.key,key)){
            this.key = key;
            return true;
        }
        else
            return false;
    }

    public final boolean hasId(boolean mayInherit){
        return (null != this.getId(mayInherit));
    }
    public final boolean hasNotId(boolean mayInherit){
        return (null == this.getId(mayInherit));
    }
    public final boolean dropId(){
        if (null != this.id){
            this.id = null;
            return true;
        }
        else
            return false;
    }
    public final String getId(){
        return this.id;
    }
    public final String getId(boolean ignore){
        return this.id;
    }
    public final boolean setId(String id){
        if (IsNotEqual(this.id,id)){
            this.id = id;
            return true;
        }
        else
            return false;
    }

    public final boolean hasBase(boolean mayInherit){
        return (null != this.getBase(mayInherit));
    }
    public final boolean hasNotBase(boolean mayInherit){
        return (null == this.getBase(mayInherit));
    }
    public final boolean dropBase(){
        if (null != this.base){
            this.base = null;
            return true;
        }
        else
            return false;
    }
    public final String getBase(){
        return this.base;
    }
    public final String getBase(boolean ignore){
        return this.base;
    }
    public final boolean setBase(String base){
        if (IsNotEqual(this.base,base)){
            this.base = base;
            return true;
        }
        else
            return false;
    }

    public final boolean hasName(boolean mayInherit){
        return (null != this.getName(mayInherit));
    }
    public final boolean hasNotName(boolean mayInherit){
        return (null == this.getName(mayInherit));
    }
    public final boolean dropName(){
        if (null != this.name){
            this.name = null;
            return true;
        }
        else
            return false;
    }
    public final String getName(){
        return this.name;
    }
    public final String getName(boolean ignore){
        return this.name;
    }
    public final boolean setName(String name){
        if (IsNotEqual(this.name,name)){
            this.name = name;
            return true;
        }
        else
            return false;
    }

    public final boolean hasLastModified(boolean mayInherit){
        return (null != this.getLastModified(mayInherit));
    }
    public final boolean hasNotLastModified(boolean mayInherit){
        return (null == this.getLastModified(mayInherit));
    }
    public final boolean dropLastModified(){
        if (null != this.lastModified){
            this.lastModified = null;
            return true;
        }
        else
            return false;
    }
    public final Long getLastModified(boolean mayInherit){
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
    public final boolean setLastModified(Long lastModified, boolean withInheritance){
        if (IsNotEqual(this.lastModified,this.getLastModified(withInheritance))){
            this.lastModified = lastModified;
            return true;
        }
        else
            return false;
    }
    public final boolean setLastModified(Long lastModified){
        if (IsNotEqual(this.lastModified,lastModified)){
            this.lastModified = lastModified;
            return true;
        }
        else
            return false;
    }

    public final boolean hasTag(boolean mayInherit){
        return (null != this.getTag(mayInherit));
    }
    public final boolean hasNotTag(boolean mayInherit){
        return (null == this.getTag(mayInherit));
    }
    public final boolean dropTag(){
        if (null != this.tag){
            this.tag = null;
            return true;
        }
        else
            return false;
    }
    public final Category getTag(boolean mayInherit){
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
    public final boolean setTag(Category tag, boolean withInheritance){
        if (IsNotEqual(this.tag,this.getTag(withInheritance))){
            this.tag = tag;
            return true;
        }
        else
            return false;
    }
    public final boolean setTag(Category tag){
        if (IsNotEqual(this.tag,tag)){
            this.tag = tag;
            return true;
        }
        else
            return false;
    }

    public final boolean hasServletClassname(boolean mayInherit){
        return (null != this.getServletClassname(mayInherit));
    }
    public final boolean hasNotServletClassname(boolean mayInherit){
        return (null == this.getServletClassname(mayInherit));
    }
    public final boolean dropServletClassname(){
        if (null != this.servletClassname){
            this.servletClassname = null;
            return true;
        }
        else
            return false;
    }
    public final String getServletClassname(boolean mayInherit){
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
    public final boolean setServletClassname(String servletClassname, boolean withInheritance){
        if (IsNotEqual(this.servletClassname,this.getServletClassname(withInheritance))){
            this.servletClassname = servletClassname;
            return true;
        }
        else
            return false;
    }
    public final boolean setServletClassname(String servletClassname){
        if (IsNotEqual(this.servletClassname,servletClassname)){
            this.servletClassname = servletClassname;
            return true;
        }
        else
            return false;
    }

    public final boolean hasServletSourceJava(boolean mayInherit){
        return (null != this.getServletSourceJava(mayInherit));
    }
    public final boolean hasNotServletSourceJava(boolean mayInherit){
        return (null == this.getServletSourceJava(mayInherit));
    }
    public final boolean dropServletSourceJava(){
        if (null != this.servletSourceJava){
            this.servletSourceJava = null;
            return true;
        }
        else
            return false;
    }
    public final Text getServletSourceJava(boolean mayInherit){
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
    public final boolean setServletSourceJava(Text servletSourceJava, boolean withInheritance){
        if (IsNotEqual(this.servletSourceJava,this.getServletSourceJava(withInheritance))){
            this.servletSourceJava = servletSourceJava;
            return true;
        }
        else
            return false;
    }
    public final boolean setServletSourceJava(Text servletSourceJava){
        if (IsNotEqual(this.servletSourceJava,servletSourceJava)){
            this.servletSourceJava = servletSourceJava;
            return true;
        }
        else
            return false;
    }

    public final boolean hasServletClassfileJvm(boolean mayInherit){
        return (null != this.getServletClassfileJvm(mayInherit));
    }
    public final boolean hasNotServletClassfileJvm(boolean mayInherit){
        return (null == this.getServletClassfileJvm(mayInherit));
    }
    public final boolean dropServletClassfileJvm(){
        if (null != this.servletClassfileJvm){
            this.servletClassfileJvm = null;
            return true;
        }
        else
            return false;
    }
    public final Blob getServletClassfileJvm(boolean mayInherit){
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
    public final boolean setServletClassfileJvm(Blob servletClassfileJvm, boolean withInheritance){
        if (IsNotEqual(this.servletClassfileJvm,this.getServletClassfileJvm(withInheritance))){
            this.servletClassfileJvm = servletClassfileJvm;
            return true;
        }
        else
            return false;
    }
    public final boolean setServletClassfileJvm(Blob servletClassfileJvm){
        if (IsNotEqual(this.servletClassfileJvm,servletClassfileJvm)){
            this.servletClassfileJvm = servletClassfileJvm;
            return true;
        }
        else
            return false;
    }

    public final boolean hasPartners(boolean mayInherit){
        return (this.getPartners(mayInherit).isNotEmpty());
    }
    public final boolean hasNotPartners(boolean mayInherit){
        return (this.getPartners(mayInherit).isEmpty());
    }
    public final boolean dropPartners(){
        Map.Long<String,Partner> partners = this.partners;
        if (null != partners){
            this.partners = null;
            partners.destroy();
            return true;
        }
        else
            return false;
    }
    public final Map.Long<String,Partner> getPartners(boolean mayInherit){
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
            partners = new MapLongResourceStringPartner((Resource)this);
            this.partners = partners;
            partners.init();
        }
        return partners;
    }
    public final void setPartners(Map.Long<String,Partner> partners){
        this.partners = partners;
    }
    public final boolean isEmptyPartners(){
        Map.Long<String,Partner> collection = this.partners;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyPartners(){
        Map.Long<String,Partner> collection = this.partners;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final Partner fetchPartners(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Long<String,Partner> collection = this.getPartners(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final Partner getPartners(String partnersName){
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

    public final boolean hasAccounts(boolean mayInherit){
        return (this.getAccounts(mayInherit).isNotEmpty());
    }
    public final boolean hasNotAccounts(boolean mayInherit){
        return (this.getAccounts(mayInherit).isEmpty());
    }
    public final boolean dropAccounts(){
        List.Long<Account> accounts = this.accounts;
        if (null != accounts){
            this.accounts = null;
            accounts.destroy();
            return true;
        }
        else
            return false;
    }
    public final List.Long<Account> getAccounts(boolean mayInherit){
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
            accounts = new ListLongResourceAccount((Resource)this);
            this.accounts = accounts;
            accounts.init();
        }
        return accounts;
    }
    public final void setAccounts(List.Long<Account> accounts){
        this.accounts = accounts;
    }
    public final boolean isEmptyAccounts(){
        List.Long<Account> collection = this.accounts;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyAccounts(){
        List.Long<Account> collection = this.accounts;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final Account fetchAccounts(Filter filter){
        if (null != filter && KIND == filter.kind){
            List.Long<Account> collection = this.getAccounts(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final Account getAccounts(gap.data.ListFilter<Account> filter){
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

    public final boolean hasImages(boolean mayInherit){
        return (this.getImages(mayInherit).isNotEmpty());
    }
    public final boolean hasNotImages(boolean mayInherit){
        return (this.getImages(mayInherit).isEmpty());
    }
    public final boolean dropImages(){
        Map.Short<String,Image> images = this.images;
        if (null != images){
            this.images = null;
            images.destroy();
            return true;
        }
        else
            return false;
    }
    public final Map.Short<String,Image> getImages(boolean mayInherit){
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
            images = new MapShortResourceStringImage((Resource)this);
            this.images = images;
            images.init();
        }
        return images;
    }
    public final void setImages(Map.Short<String,Image> images){
        this.images = images;
    }
    public final boolean isEmptyImages(){
        Map.Short<String,Image> collection = this.images;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyImages(){
        Map.Short<String,Image> collection = this.images;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final Image fetchImages(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Short<String,Image> collection = this.getImages(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final Image getImages(String imagesName){
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

    public final boolean hasTemplates(boolean mayInherit){
        return (this.getTemplates(mayInherit).isNotEmpty());
    }
    public final boolean hasNotTemplates(boolean mayInherit){
        return (this.getTemplates(mayInherit).isEmpty());
    }
    public final boolean dropTemplates(){
        Map.Short<String,Template> templates = this.templates;
        if (null != templates){
            this.templates = null;
            templates.destroy();
            return true;
        }
        else
            return false;
    }
    public final Map.Short<String,Template> getTemplates(boolean mayInherit){
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
            templates = new MapShortResourceStringTemplate((Resource)this);
            this.templates = templates;
            templates.init();
        }
        return templates;
    }
    public final void setTemplates(Map.Short<String,Template> templates){
        this.templates = templates;
    }
    public final boolean isEmptyTemplates(){
        Map.Short<String,Template> collection = this.templates;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyTemplates(){
        Map.Short<String,Template> collection = this.templates;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final Template fetchTemplates(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Short<String,Template> collection = this.getTemplates(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final Template getTemplates(String templatesName){
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

    public final boolean hasTools(boolean mayInherit){
        return (this.getTools(mayInherit).isNotEmpty());
    }
    public final boolean hasNotTools(boolean mayInherit){
        return (this.getTools(mayInherit).isEmpty());
    }
    public final boolean dropTools(){
        Map.Short<String,Tool> tools = this.tools;
        if (null != tools){
            this.tools = null;
            tools.destroy();
            return true;
        }
        else
            return false;
    }
    public final Map.Short<String,Tool> getTools(boolean mayInherit){
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
            tools = new MapShortResourceStringTool((Resource)this);
            this.tools = tools;
            tools.init();
        }
        return tools;
    }
    public final void setTools(Map.Short<String,Tool> tools){
        this.tools = tools;
    }
    public final boolean isEmptyTools(){
        Map.Short<String,Tool> collection = this.tools;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyTools(){
        Map.Short<String,Tool> collection = this.tools;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final Tool fetchTools(Filter filter){
        if (null != filter && KIND == filter.kind){
            Map.Short<String,Tool> collection = this.getTools(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final Tool getTools(String toolsName){
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
    public final Kind getClassKind(){
        return KIND;
    }
    public final String getClassName(){
        return ClassName;
    }
    public final String getClassFieldUnique(){
        return "id";
    }
    public final String getClassFieldKeyName(){
        return "key";
    }
    public final List<gap.data.Field> getClassFields(){
        return (new gap.data.Field.List(Field.values()));
    }
    public final gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        return change;
    }
    public final boolean updateFrom(BigTable proto){
        return this.updateFrom( (Resource)proto);
    }
    public final boolean updateFrom(Resource proto){
        boolean change = false;
        return change;
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
}
