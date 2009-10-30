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
@Generated(value={"gap.service.OD","bean"},date="2009-10-30T07:25:17.779Z")
public final class Partner
    extends gap.data.BigTable
    implements DataInheritance<Partner>,
               HasName
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("Partner","gap.data","Partner");

    public final static String ClassName = "Partner";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Partner.class);
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorForParent(){
        return ClassDescriptorFor(Resource.class);
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


    public final static Partner ForLongName(Key ancestor, String name){
        if (null != name){
            Key key = KeyLongIdFor(ancestor, name);
            Partner instance = (Partner)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Partner)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Partner GetCreateLong(Key ancestor, String name){
        Partner partner = ForLongName(ancestor, name);
        if (null == partner){
            partner = new Partner(ancestor, name);
            partner = (Partner)gap.data.Store.Put(partner);
        }
        return partner;
    }



    public final static Key KeyLongFor(Key ancestor, String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static Partner ForLongId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyLongFor(ancestor,id);
            Partner instance = (Partner)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Partner)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Partner Get(Key key){
        if (null != key){
            Partner instance = (Partner)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Partner)gap.data.Store.Query1(q);
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
    public final static void Delete(Partner instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Partner instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Partner instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Partner instance){
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
    
    public final static Partner Query1(Query query){
        if (null != query)
            return (Partner)gap.data.Store.Query1(query);
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
     * Persistent fields' binding for {@link Partner}
     */
    public static enum Field
        implements gap.data.Field<Field>
    {
        InheritFromKey("inheritFromKey"),
        ParentKey("parentKey"),
        Key("key"),
        Id("id"),
        Name("name");


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
        public static Object Get(Field field, Partner instance, boolean mayInherit){
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
            default:
                throw new IllegalArgumentException(field.toString()+" in Partner");
            }
        }
        public static boolean Set(Field field, Partner instance, Object value){
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
            default:
                throw new IllegalArgumentException(field.toString()+" in Partner");
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

    private volatile transient Partner inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String name;    // *hash-unique






    private volatile Key parentKey;
    private volatile transient Resource parent;


    public Partner() {
        super();
    }
    public Partner(Key ancestor, String name) {
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
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.key = null;
        this.id = null;
        this.name = null;
        this.parent = null;
    }
    public boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public Partner getInheritFrom(){
        Partner inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Partner.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public boolean setInheritFrom(Partner ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public boolean inheritFrom(Partner ancestor){
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
        return this.updateFrom( (Partner)proto);
    }
    public boolean updateFrom(Partner proto){
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
