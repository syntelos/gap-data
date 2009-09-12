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

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.ShortBlob;

import java.util.EnumSet;
import java.util.List;

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
 * @see oso.data.Activity
 * @see oso.data.Image
 * @see oso.data.Message
 * @see oso.data.MessageCollection
 * @see oso.data.Person
 * @author jdp
 */
public abstract class BigTable
    extends java.lang.Object
    implements java.io.Serializable
{

    public final static BigTable From(Entity entity){
        if (null != entity){
            String kind = entity.getKind();
            if (null != kind){
                String classname = "oso.data."+kind;
                try {
                    Class table = Class.forName(classname);
                    return From(table,entity);
                }
                catch (java.lang.ClassNotFoundException exc){
                    throw new java.lang.RuntimeException(classname,exc);
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

    private transient volatile Enum[] classFieldsArray;


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
    public abstract void init();

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

    /**
     * A static value naming the datastore class name, e.g., "Person".
     */
    public abstract String getClassKind();
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
        return (Key)this.valueOf(this.getClassKeyField());
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

    public abstract java.io.Serializable valueOf(Field field);

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
                String keyf = this.getClassFieldKeyName();
                if (null != keyf)
                    datastoreEntity = new Entity(kind,keyf);
                else
                    datastoreEntity = new Entity(kind);

                this.datastoreEntity = datastoreEntity;
            }
            else
                throw new IllegalStateException("Missing class kind.");
        }
        return datastoreEntity;
    }
    public final Entity getDatastoreEntity(Key parent){
        Entity datastoreEntity = this.datastoreEntity;
        if (null == datastoreEntity){
            String kind = this.getClassKind();
            if (null != kind){
                String keyf = this.getClassFieldKeyName();
                if (null != keyf)
                    datastoreEntity = new Entity(kind,keyf,parent);
                else
                    datastoreEntity = new Entity(kind,parent);

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
    public final Entity fillToDatastoreEntity(Key parent){
        Entity datastoreEntity = this.getDatastoreEntity(parent);
        return this.fillTo(datastoreEntity);
    }
    public final Entity fillFromDatastoreEntity(Entity entity){
        return (this.datastoreEntity = this.fillFrom(entity));
    }

    protected final Entity fillTo(Entity entity){

        for (Field field: this.getClassFields()){
            String fieldName = field.getFieldName();
            java.io.Serializable value = this.valueOf(field);
            if (null != value){
                /*
                 * The set of types named "indexed" is highly defined,
                 * while the set "datastore" leads to the ambiguity in
                 * the handling of the datastore Blob type as both a
                 * field type and the container of {@link Serialize
                 * Serialize'd} field values.
                 */
                if (IsIndexed(value))
                    entity.setProperty(fieldName, value);
                else if (IsDatastore(value))
                    entity.setUnindexedProperty(fieldName, value);
                else {
                    Blob blob = Serialize.To(field,value);
                    entity.setUnindexedProperty(fieldName, blob);
                }
            }
            else if (null != entity.getProperty(fieldName))
                entity.removeProperty(fieldName);
        }
        return entity;
    }

    protected final Entity fillFrom(Entity entity){

        for (Field field: this.getClassFields()){

            java.io.Serializable object = (java.io.Serializable)entity.getProperty(field.getFieldName());
            if (null != object){
                /*
                 * Resolving types could be done via the reflection of
                 * the return type of the field's getter method.  The
                 * process defined here is not inexpensive, but
                 * correct when successful.
                 */
                if (IsIndexed(object))
                    this.define(field,object);
                else if (object instanceof Blob){
                    try {
                        java.io.Serializable deser = Serialize.From(field,object);
                        this.define(field,deser);
                    }
                    catch (Exception exc){
                        this.define(field,object);
                    }
                }
                else
                    this.define(field,object);
            }
            else
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


    private final static java.util.Set<Class> IndexedTypes = new java.util.HashSet<Class>();
    static {
        IndexedTypes.add(Boolean.class);
        IndexedTypes.add(String.class);
        IndexedTypes.add(Byte.class);
        IndexedTypes.add(Short.class);
        IndexedTypes.add(Integer.class);
        IndexedTypes.add(Long.class);
        IndexedTypes.add(Float.class);
        IndexedTypes.add(Double.class);
        IndexedTypes.add(java.util.Date.class);
    }
    public final static boolean IsUnindexed(java.lang.Class jclass){
        if (null == jclass)
            return false;
        else
            return (!IndexedTypes.contains(jclass));
    }
    public final static boolean IsIndexed(java.lang.Class jclass){
        if (null == jclass)
            return false;
        else
            return (IndexedTypes.contains(jclass));
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

    private final static java.util.Set<Class> DatastoreTypes = new java.util.HashSet<Class>();
    static {
        DatastoreTypes.add(Blob.class);
        DatastoreTypes.add(Key.class);
        DatastoreTypes.add(Link.class);
        DatastoreTypes.add(Text.class);
        DatastoreTypes.add(ShortBlob.class);
    }
    public final static boolean IsDatastore(java.lang.Class jclass){
        if (null == jclass)
            return false;
        else
            return (DatastoreTypes.contains(jclass));
    }
    protected final static boolean IsDatastore(java.io.Serializable value){
        if (null == value)
            return false;
        else 
            return IsDatastore(value.getClass());
    }

}
