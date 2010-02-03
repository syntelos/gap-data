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
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated data bean
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-02-03T12:48:22.042Z")
public abstract class AccountData
    extends gap.data.BigTable
    implements DataInheritance<Account>
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("Account","gap.data","Account","/accounts");

    public final static String ClassName = "Account";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Account.class);
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorForParent(){
        return ClassDescriptorFor(Resource.class);
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


    public final static Account ForLongBaseName(Key ancestor, String base, String name){
        if (null != base && null != name){
            Key key = KeyLongIdFor(ancestor, base, name);
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Account GetCreateLong(Key ancestor, String base, String name){
        Account account = ForLongBaseName(ancestor, base, name);
        if (null == account){
            account = new Account(ancestor, base, name);
            account = (Account)gap.data.Store.Put(account);
        }
        return account;
    }



    public final static Key KeyLongFor(Key ancestor, String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static Account ForLongId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyLongFor(ancestor,id);
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Account Get(Key key){
        if (null != key){
            Account instance = (Account)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Account)gap.data.Store.Query1(q);
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
                Key key = KeyFactory.createKey(KIND.getName(),idString);
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
    public final static void Delete(Account instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Account instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Account instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Account instance){
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
    
    public final static Account Query1(Query query){
        if (null != query)
            return (Account)gap.data.Store.Query1(query);
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
     * Persistent fields' binding for {@link Account}
     */
    public static enum Field
        implements gap.data.Field<Field>
    {
        InheritFromKey("inheritFromKey"),
        ParentKey("parentKey"),
        Key("key"),
        Id("id"),
        Base("base"),
        Name("name");


        private final static lxl.Map<String,Field> FieldName = new lxl.Map<String,Field>();
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
        public static Object Get(Field field, Account instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case ParentKey:
                return instance.getParentKey();
            case Key:
                return instance.getKey(mayInherit);
            case Id:
                return instance.getId(mayInherit);
            case Base:
                return instance.getBase(mayInherit);
            case Name:
                return instance.getName(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Account");
            }
        }
        public static boolean Set(Field field, Account instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case ParentKey:
                return instance.setParentKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case Base:
                return instance.setBase(gap.Objects.StringFromObject(value));
            case Name:
                return instance.setName(gap.Objects.StringFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in Account");
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

    private volatile transient Account inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String base;    // *hash-unique
    private volatile String name;    // *hash-unique






    private volatile Key parentKey;
    private volatile transient Resource parent;


    protected AccountData() {
        super();
    }
    protected AccountData(Key ancestor, String base, String name) {
        super();
        this.setBase(base);
        this.setName(name);
        this.parentKey = ancestor;
        String id = IdFor(ancestor,  base, name);
        this.setId(id);
        Key key = KeyLongFor(ancestor,id);
        this.setKey(key);
    }



    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.key = null;
        this.id = null;
        this.base = null;
        this.name = null;
        this.parent = null;
    }
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final Account getInheritFrom(){
        Account inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Account.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(Account ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(Account ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean hasParentKey(){
        return (null != this.parentKey);
    }
    public final boolean hasNotParentKey(){
        return (null == this.parentKey);
    }
    public final Key getParentKey(){
        return this.parentKey;
    }
    public final boolean setParentKey(Key ancestor){
        if (IsNotEqual(this.parentKey,ancestor)){
            this.parentKey = ancestor;
            return true;
        }
        else
            return false;
    }
    public final boolean hasParent(){
        return (null != this.parent || null != this.parentKey);
    }
    public final boolean hasNotParent(){
        return (null == this.parent && null == this.parentKey);
    }
    public final Resource getParent(){
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
    public final boolean setParent(Resource ancestor){
        if (IsNotEqual(this.parent,ancestor)){
            this.parent = ancestor;
            if (null != ancestor)
                this.parentKey = ancestor.getClassFieldKeyValue();
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
        return this.updateFrom( (Account)proto);
    }
    public final boolean updateFrom(Account proto){
        boolean change = false;
        return change;
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorForParent(){
        return ClassDescriptorForParent();
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Key:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasKey(true);
            case Id:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasId(true);
            case Base:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasBase(true);
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasName(true);
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.hasVariable(name);
        }
    }
    public String getVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Key:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.KeyToString(this.getKey(true));
            case Id:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getId(true);
            case Base:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getBase(true);
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getName(true);
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getVariable(name);
        }
    }
    public List<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Key:
                return null;
            case Id:
                return null;
            case Base:
                return null;
            case Name:
                return null;
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getSection(name);
        }
    }
}
