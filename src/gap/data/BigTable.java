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

import gap.service.Parameters;

import hapax.TemplateDictionary;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DataTypeUtils;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.ShortBlob;

/**
 * The gap data object handled by the {@link Store} API for memcache
 * and datastore.  All datastore objects are cached via the normal
 * operation of the {@link Store} API.
 * 
 * Subclasses are data beans tightly integrated with the appengine
 * datastore.  All non transient fields are serializable object types,
 * and must fully support null values in all cases.
 * 
 * The persistent fields of a big table are serialized as a java bean
 * into memcache, and translated into an entity into the datastore.
 * 
 * This class provides the infrastructure for these integrations.
 * 
 * The serialization protocol supports data bean fields of all
 * serializable types including the google datastore data types
 * (e.g. Blob).  See {@link Serialize}.
 * 
 * The java lang data types Boolean, String, Byte, Short, Integer,
 * Long, Float and Double with the java util Date are available to
 * queries, while all other field types are not.
 * 
 * 
 * @see oso.data.Person
 * @author jdp
 */
public abstract class BigTable
    extends java.lang.Object
    implements java.io.Serializable,
               DictionaryInto,
               RequestCreateUpdate
{
    public final static gap.util.Services Services = (new gap.util.Services(BigTable.class)).init();

    protected final static java.util.Set<String> Imports = new java.util.HashSet<String>();

    protected final static void Register(Class dc){
        String pkg = dc.getPackage().getName();
        if (null != pkg)
            Imports.add(pkg);
        else
            throw new IllegalStateException(dc.getName());
    }
    public static Class Find(String kind)
        throws ClassNotFoundException
    {
        for (String pkg : Imports){
            String classname = (pkg+'.'+kind);
            try {
                return Class.forName(classname);
            }
            catch (ClassNotFoundException exc){
            }
        }
        throw new ClassNotFoundException(kind);
    }
    public final static boolean IsAdmin(String kind){
        if (null != kind){
            try {
                return IsAdmin(Find(kind));
            }
            catch (ClassNotFoundException exc){
                return false;
            }
        }
        else
            return false;
    }
    public final static boolean IsAdmin(Class kind){
        if (null != kind)
            return (AdminReadWrite.class.isAssignableFrom(kind));
        else
            return false;
    }

    /**
     * An independent "key to string" ensures that any upstream code
     * changes won't kill our database.
     */
    public final static String ToString(Key key){
        StringBuilder strbuf = ToStringBuilder(key);
        return strbuf.toString();
    }
    public final static StringBuilder ToStringBuilder(Key key){
        if (null != key){
            StringBuilder strbuf = new StringBuilder();
            ToString(key,strbuf,key);
            return strbuf;
        }
        else
            throw new IllegalArgumentException();
    }
    private final static void ToString(Key key, StringBuilder strbuf, Key k){

        Key p = k.getParent();
        if (null != p)
            ToString(key,strbuf,p);

        strbuf.append('/');
        strbuf.append(k.getKind());
        strbuf.append(':');
        String n = k.getName();
        if (null != n && 0 != n.length())
            strbuf.append(n);
        else {
            long num = k.getId();
            if (0L != num)
                strbuf.append(String.valueOf(num));
            else
                throw new IllegalArgumentException("Key is incomplete '"+key+"'.");
        }
    }
    public final static BigTable From(Entity entity){
        if (null != entity){
            String kind = entity.getKind();
            if (null != kind){
                try {
                    Class table = Find(kind);
                    return From(table,entity);
                }
                catch (java.lang.ClassNotFoundException exc){
                    throw new java.lang.RuntimeException(kind,exc);
                }
            }
            else
                throw new java.lang.IllegalArgumentException("Entity missing kind for key '"+entity.getKey()+"'");
        }
        else
            return null;
    }
    public final static BigTable From(Class table, Entity entity){
        try {
            BigTable instance = (BigTable)table.newInstance();
            instance.fillFrom(entity);
            return instance;
        }
        catch (java.lang.InstantiationException exc){
            throw new java.lang.RuntimeException(table.getName(),exc);
        }
        catch (java.lang.IllegalAccessException exc){
            throw new java.lang.RuntimeException(table.getName(),exc);
        }
    }

    private transient volatile boolean fromDatastore = false;

    protected transient volatile Entity datastoreEntity;

    protected volatile Key inheritFromKey;


    protected BigTable(){
        super();
    }


    /**
     * Called by the Store layer after retrieving an instance object
     * from the datastore or memcache.  Subclasses should ensure that
     * their state is consistent, as for example to upgrade features
     * across package versions.
     * 
     * The fromDatastore field value is defined before this
     * method is called.
     */
    public abstract void onread();
    /**
     * Called once by the Store layer before storing an instance
     * object to the datastore or memcache.  Subclasses may perform
     * operations updating their state.  Called after {@link
     * LastModified} and before storage.
     */
    public abstract void onwrite();

    public abstract void destroy();

    /**
     * The transient field "from datastore" employed by the {@link
     * Store}.  The value "true" is represented as "from datastore",
     * and false as "from memcache".  The init state of this value is
     * "false".
     * 
     * An object is retrieved from the datastore or memcache.  The
     * current state of this field is defined before a call to the
     * "init" method.
     */
    public final boolean isFromDatastore() {
        return this.fromDatastore;
    }
    public final boolean isFromMemcache() {
        return (!this.fromDatastore);
    }
    public final BigTable setFromDatastore() {
        this.fromDatastore = true;
        return this;
    }
    public final BigTable setFromMemcache() {
        this.fromDatastore = false;
        return this;
    }
    public final BigTable setFromDatastore(Key with) {
        this.fromDatastore = true;
        this.defineKey(with);
        return this;
    }

    /*
     * Data binding supports
     */
    /**
     * @return A class static value naming the datastore class name,
     * e.g., "Person".
     */
    public abstract String getClassKind();
    /**
     * @return The class static unqualified class name.
     */
    public abstract String getClassName();
    /**
     * A static value naming the field employed for instance lookups,
     * as from web interfaces.
     */
    public abstract String getClassFieldUnique();
    /**
     * A static value naming the identity field having type 'Key'.
     */
    public abstract String getClassFieldKeyName();

    public final Key getClassFieldKeyValue(){
        return (Key)this.valueOf(this.getClassKeyField(),false);
    }
    public final Field getClassKeyField(){
        return (this.getClassFieldByName(this.getClassFieldKeyName()));
    }
    /**
     * A static value enumerating the persistent (not transient)
     * fields of this class.
     */
    public abstract List<Field> getClassFields();

    public abstract Field getClassFieldByName(String name);

    public abstract java.io.Serializable valueOf(Field field, boolean mayInherit);

    public abstract void define(Field field, java.io.Serializable value);

    public final void define(String fieldName, java.io.Serializable value){
        Field field = this.getClassFieldByName(fieldName);
        if (null != field)
            this.define(field,value);
        else
            throw new IllegalArgumentException("Unknown field name '"+fieldName+"'.");
    }
    public final void clearDatastoreEntity(){
        this.datastoreEntity = null;        
    }
    public final Entity getDatastoreEntity(){
        Entity datastoreEntity = this.datastoreEntity;
        if (null == datastoreEntity){
            String kind = this.getClassKind();
            if (null != kind){
                Key key = this.getClassFieldKeyValue();
                if (null != key){
                    /*
                     * Entity for object from memcache
                     * 
                     * Key.name identity code needs to be aware that
                     * the datastore key can have multiple key.id's
                     * for a key.name.
                     */
                    try {
                        datastoreEntity = Store.P.Get().get(key);
                        this.defineKeyFrom(datastoreEntity);
                    }
                    catch (com.google.appengine.api.datastore.EntityNotFoundException exc){
                        datastoreEntity = new Entity(kind,key);
                    }
                }
                else
                    throw new IllegalStateException("Missing key field value.");

                this.datastoreEntity = datastoreEntity;
            }
            else
                throw new IllegalStateException("Missing class kind.");
        }
        return datastoreEntity;
    }
    public final Entity fillToDatastoreEntity(){
        return this.fillTo(this.getDatastoreEntity());
    }
    public final Entity fillFromDatastoreEntity(Entity entity){
        return (this.datastoreEntity = this.fillFrom(entity));
    }
    /**
     * Delete from the world, completely.
     */
    public abstract void drop();
    /**
     * Drop from memcache, exclusively.
     */
    public abstract void clean();
    /**
     * Clean and store.
     */
    public abstract void save();
    /**
     * Write to store.
     */
    public abstract void store();

    /**
     * Install possibly filtered set.
     */
    public abstract TemplateDictionary dictionaryInto(Parameters params, TemplateDictionary dict);

    public boolean hasInheritFromKey(){
        return (null != this.inheritFromKey);
    }
    public boolean hasNotInheritFromKey(){
        return (null == this.inheritFromKey);
    }
    public Key getInheritFromKey(){
        return this.inheritFromKey;
    }
    public boolean setInheritFromKey(Key key){
        if (IsNotEqual(this.inheritFromKey,key)){
            this.inheritFromKey = key;
            return true;
        }
        else
            return false;
    }
    protected final Entity fillTo(Entity entity){

        for (Field field: this.getClassFields()){
            String fieldName = field.getFieldName();
            java.io.Serializable value = this.valueOf(field,false);
            if (null != value){

                if (IsIndexed(value))
                    entity.setProperty(fieldName, value);
                else 
                    entity.setUnindexedProperty(fieldName, value);
            }
            else if (null != entity.getProperty(fieldName))
                entity.removeProperty(fieldName);
        }
        return entity;
    }

    protected final Entity fillFrom(Entity entity){

        for (Field field: this.getClassFields()){

            java.io.Serializable object = (java.io.Serializable)entity.getProperty(field.getFieldName());

            this.define(field,object);
        }
        this.defineKeyFrom(entity);
        return entity;
    }

    protected final void defineKeyFrom(Entity entity){
        this.define(this.getClassKeyField(),entity.getKey());
    }

    protected final void defineKey(Key key){
        this.define(this.getClassKeyField(),key);
    }


    public final static boolean IsUnindexed(java.lang.Class jclass){
        if (null == jclass)
            return false;
        else
            return (!DataTypeUtils.isSupportedType(jclass));
    }
    public final static boolean IsIndexed(java.lang.Class jclass){
        if (null == jclass)
            return false;
        else
            return DataTypeUtils.isSupportedType(jclass);
    }
    protected final static boolean IsUnindexed(java.io.Serializable value){
        if (null == value)
            return false;
        else 
            return IsUnindexed(value.getClass());
    }
    protected final static boolean IsIndexed(java.io.Serializable value){
        if (null == value)
            return false;
        else 
            return IsIndexed(value.getClass());
    }
    public final static boolean IsEqual(Object a, Object b){
        if (null == a)
            return (null == b);
        else if (null == b)
            return false;
        else
            return (a.equals(b));
    }
    public final static boolean IsNotEqual(Object a, Object b){
        if (null == a)
            return (null != b);
        else if (null == b)
            return true;
        else
            return (!a.equals(b));
    }
}
