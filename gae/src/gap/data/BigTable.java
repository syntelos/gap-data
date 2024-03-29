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

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DataTypeUtils;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.ShortBlob;

import java.io.File;
import java.math.BigInteger;
import java.math.BigDecimal;

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
    extends gap.hapax.AbstractData
    implements gap.data.TableClass,
               RequestCreateUpdate
{
    /**
     * Initialize data bean classes, called in the class
     * initialization of {@link gap.service.Servlet}.  Causes the
     * population of {@link Kind}.
     */
    public final static gap.util.Services Services = (new gap.util.Services(BigTable.class)).init();
    /**
     * 
     */
    public static Class<? extends BigTable> Find(Kind kind){
        if (null != kind)
            return (Class<? extends BigTable>)kind.getTableClass();
        else
            throw new IllegalArgumentException();
    }
    public static Class<? extends BigTable> Find(String kindName){
        return Find(Kind.For(kindName));
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(String kind){
        throw new UnsupportedOperationException();
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(Class<? extends BigTable> clas){
        throw new UnsupportedOperationException();
    }
    public final static boolean IsAdmin(Kind kind){        
        if (null != kind){
            try {
                return IsAdmin(Find(kind));
            }
            catch (IllegalStateException exc){
                return false;
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static boolean IsAdmin(String kind){
        if (null != kind){
            try {
                return IsAdmin(Find(kind));
            }
            catch (IllegalStateException exc){
                return false;
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static boolean IsAdmin(Class kind){
        if (null != kind)
            return (AdminReadWrite.class.isAssignableFrom(kind));
        else
            throw new IllegalArgumentException();
    }
    public final static String IdFor(String kind, Key key){

        while (null != key && kind.equals(key.getKind())){
            String id = key.getName();
            if (null != id)
                return id;
            else
                key = key.getParent();
        }
        return null;
    }
    /**
     * An independent "key to string" ensures that any upstream code
     * changes won't kill our database.
     */
    public final static String ToString(Key key){

        return gap.Strings.KeyToString(key);
    }
    public final static String ToString(Key a, Key b){
        try {
            return gap.Strings.KeyToString(a);
        }
        catch (IllegalArgumentException incomplete){

            return gap.Strings.KeyToString(b);
        }
    }
    public final static boolean Equals(Key a, Key b){
        if (null == a)
            return (null == b);
        else if (null == b)
            return false;
        else if (a.getKind().equals(b.getKind())){
            String na = a.getName();
            String nb = b.getName();
            if (null != na && null != nb && na.equals(nb)){
                return Equals(a.getParent(),b.getParent());
            }
        }
        return false;
    }
    /**
     * @return True for Key(/Kind:ID)
     */
    private final static boolean IsKeyId(String k, Key y){
        return (null != y && null == y.getName() && 0L != y.getId() && k.equals(y.getKind()));
    }
    /**
     * @return True for Key(/Kind:NAME)
     */
    private final static boolean IsKeyName(String k, Key y){
        return (null != y && null != y.getName() && 0L == y.getId() && k.equals(y.getKind()));
    }
    /**
     * Merge Key(/Kind:name) and Key(/Kind:Id) 
     * 
     * @param t Kind and Name information
     * @param y Key and Id information
     * 
     * @return Key(/Kind:Name/Kind:Id)
     */
    private final static Key Merge(BigTable t, Key y){

        final String k = t.getClassKind().name;

        if (IsKeyId(k,y)){

            final Key p = y.getParent();

            if (!IsKeyName(k,p)){

                final long id = y.getId();

                final String name = t.getId();

                if (null != name)
                    return KeyFactory.createKey(p,k,name).getChild(k,id);
            }
        }
        return y;
    }
    public final static BigTable From(Entity entity){
        if (null != entity){
            String kind = entity.getKind();
            if (null != kind){
                Class table = Find(kind);
                return From(table,entity);
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
            instance.markClean();
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

    protected volatile Key inheritFromKey;

    protected volatile Key key;

    private transient volatile Lock lock;


    protected BigTable(){
        super();
    }


    public final boolean hasId(){
        return (null != this.key);
    }
    public final boolean hasNotId(){
        return (null == this.key);
    }
    /**
     * Data store reference to this instance.
     */
    public final boolean hasKey(){
        return (null != this.key);
    }
    public final boolean hasNotKey(){
        return (null == this.key);
    }
    public final boolean dropKey(){
        if (null != this.key){
            this.key = null;
            return true;
        }
        else
            return false;
    }
    /**
     * @return Data store reference to this instance.
     */
    public final Key getKey(){
        return this.key;
    }
    /**
     * @param key Data store reference to this instance.
     */
    public final boolean setKey(Key key){
        if (IsNotEqual(this.key,key)){
            this.key = Merge(this,key);
            return IsNotEqual(this.key,key);
        }
        else
            return false;
    }
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
        this.setKey(with);
        return this;
    }

    /*
     * Data binding supports
     */

    public final String getClassKindPath(){
        return this.getClassKind().pathName;
    }
    /**
     * A global- cloud- system lock associated with this instance may
     * be employed for any suitable purpose.
     * 
     * Operations over individual datastore entities have atomicity
     * while operations over multiple datastore entities do not.  When
     * atomicity is required over multiple entities for the coherency
     * of the datastore, a shared system lock may be the correct
     * solution.
     */
    public final Lock getLock(){
        Lock lock = this.lock;
        if (null == lock){
            lock = new Lock(this.key);
            this.lock = lock;
        }
        return lock;
    }

    public final void define(String fieldName, java.io.Serializable value){
        Field field = this.getClassFieldByName(fieldName);
        if (null != field)
            this.define(field,value);
        else
            throw new IllegalArgumentException("Unknown field name '"+fieldName+"'.");
    }

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
    public boolean inheritFrom(Key key){
        if (IsNotEqual(this.inheritFromKey,key)){
            this.inheritFromKey = key;
            return true;
        }
        else
            return false;
    }

    public final Entity fillTo(Entity entity){

        for (Field field: this.listDirty()){

            final String fieldName = field.getFieldName();

            if (field.isNotFieldNameKeyOrId()){

                java.io.Serializable value = this.valueStorage(field);
                if (null != value){

                    if (IsIndexed(value))
                        entity.setProperty(fieldName, value);
                    else
                        entity.setUnindexedProperty(fieldName, value);
                }
                else
                    entity.removeProperty(fieldName);
            }
            else
                entity.removeProperty(fieldName); // (do not store key into entity)
        }
        return entity;
    }
    public final Entity fillFrom(Entity entity){

        if (null != entity){

            for (Field field: this.getClassFields()){

                java.io.Serializable object = (java.io.Serializable)entity.getProperty(field.getFieldName());

                this.defineStorage(field,object);
            }
            this.setKey(entity.getKey());
        }
        return entity;
    }
    /**
     * @return Key string
     */
    public String toString(){
        String s = gap.Strings.KeyToString(this.getKey());
        if (null != s)
            return s;
        else
            return "/"+this.getClassKind()+":<?>";
    }
    /**
     * @return To string hash code
     */
    public int hashCode(){
        return this.toString().hashCode();
    }
    /**
     * @return To string equivalence
     */
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (null == that)
            return false;
        else
            return this.toString().equals(that.toString());
    }
    /*
     */
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
        if (a == b)
            return true;
        else if (null == a || null == b)
            return false;
        else
            return (a.equals(b));
    }
    public final static boolean IsNotEqual(Object a, Object b){
        if (a == b)
            return false;
        else if (null == a || null == b)
            return true;
        else
            return (!a.equals(b));
    }
    public final static boolean IsNotKeyOrId(String name){
        return (!IsKeyOrId(name));
    }
    public final static boolean IsKeyOrId(String name){
        switch (name.charAt(0)){
        case 'i':
            return (2 == name.length() && 'd' == name.charAt(1));
        case 'k':
            return (3 == name.length() && 'e' == name.charAt(1) && 'y' == name.charAt(2));
        default:
            return false;
        }
    }
}
