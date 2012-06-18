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

import gap.service.od.ClassDescriptor;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;


/**
 * Marker interface for Big Table classes as defined from ODL.
 * 
 * A Gap Data ODL field may employ interface members of this class
 * with the field qualifier <code>*table</code>.
 */
public interface TableClass
    extends java.io.Serializable,
            DataInheritance.Notation,
            gap.hapax.TemplateDataDictionary,
            json.Builder
{
    /**
     * ID is a propery of KEY
     */
    public boolean hasId();

    public boolean hasNotId();

    public String getId();

    public boolean setId(String id);

    public boolean hasKey();

    public boolean hasNotKey();

    public boolean dropKey();
    /**
     * Data store reference to this instance.
     */
    public Key getKey();

    public boolean setKey(Key key);
    /**
     * Called by the Store layer after retrieving an instance object
     * from the datastore or memcache.  Subclasses should ensure that
     * their state is consistent, as for example to upgrade features
     * across package versions.
     * 
     * The fromDatastore field value is defined before this
     * method is called.
     */
    public void onread();
    /**
     * Called once by the Store layer before storing an instance
     * object to the datastore or memcache.  Subclasses may perform
     * operations updating their state.  Called after {@link
     * LastModified} and before storage.
     */
    public void onwrite();

    public void destroy();
    /**
     * The transient field "from datastore" employed by the Store.
     * The value "true" is represented as "from datastore", and false
     * as "from memcache".  The init state of this value is "false".
     * 
     * An object is retrieved from the datastore or memcache.  The
     * current state of this field is defined before a call to the
     * "init" method.
     */
    public boolean isFromDatastore();

    public boolean isFromMemcache();
    /**
     * @return A class static value naming the datastore class name,
     * e.g., "Person".
     */
    public Kind getClassKind();
    /**
     * @return The class static unqualified class name.
     */
    public String getClassName();

    public String getClassKindPath();
    /**
     * Special data store cloud lock
     */
    public <L> L getLock();
    /**
     * A static value enumerating the persistent (not transient)
     * fields of this class.
     */
    public List<Field> getClassFields();

    public Field getClassFieldByName(String name);
    /**
     * Dynamic binding operator for field data type
     *
     * Persistent BigTable fields are represented by the string ID.
     * Persistent TableClass fields are represented by the Data Store Key.
     */
    public java.io.Serializable valueOf(Field field, boolean mayInherit);
    /**
     * Dynamic binding operator for field storage type
     *
     * Persistent BigTable fields are represented by the string ID.
     * Persistent TableClass fields are represented by the Data Store Key.
     */
    public java.io.Serializable valueStorage(Field field);
    /**
     * Dynamic binding operator for field data type
     *
     * Persistent BigTable fields are represented by the string ID.
     * Persistent TableClass fields are represented by the Data Store Key.
     */
    public void define(Field field, java.io.Serializable value);

    public void define(String fieldName, java.io.Serializable value);
    /**
     * Dynamic binding operator for field storage type
     *
     * Persistent BigTable fields are represented by the string ID.
     * Persistent TableClass fields are represented by the Data Store Key.
     */
    public void defineStorage(Field field, java.io.Serializable value);

    public Entity fillTo(Entity entity);

    public Entity fillFrom(Entity entity);
    /**
     * Delete from the world, completely.
     */
    public void drop();
    /**
     * Drop from memcache, exclusively.
     */
    public void clean();
    /**
     * Clean and store.
     */
    public void save();
    /**
     * Write to store.
     */
    public void store();

    public boolean hasInheritFromKey();

    public boolean hasNotInheritFromKey();

    public Key getInheritFromKey();

    public boolean setInheritFromKey(Key key);

    public ClassDescriptor getClassDescriptorFor();

    public TableClass markClean();

    public TableClass markDirty();

    public TableClass markDirty(Field field);

    public TableClass markDirty(java.io.Serializable instance);

    public Iterable<Field> listClean();

    public Iterable<Field> listDirty();

    public boolean isClean();

    public boolean isDirty();

}
