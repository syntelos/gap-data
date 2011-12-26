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
 */
public interface TableClass
    extends java.io.Serializable,
            DataInheritance.Notation
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
     * KEY is the transient reference to this instance in the data
     * store
     */
    public Key getKey();

    public boolean setKey(Key key);

    public void onread();

    public void onwrite();

    public void destroy();

    public boolean isFromDatastore();

    public boolean isFromMemcache();

    public Kind getClassKind();

    public String getClassName();

    public String getClassKindPath();

    public List<Field> getClassFields();

    public Field getClassFieldByName(String name);

    public java.io.Serializable valueOf(Field field, boolean mayInherit);

    public void define(Field field, java.io.Serializable value);

    public void define(String fieldName, java.io.Serializable value);

    public Entity fillTo(Entity entity);

    public Entity fillFrom(Entity entity);

    public void drop();

    public void clean();

    public void save();

    public void store();

    public boolean hasInheritFromKey();

    public boolean hasNotInheritFromKey();

    public Key getInheritFromKey();

    public boolean setInheritFromKey(Key key);

    public ClassDescriptor getClassDescriptorFor();

}
