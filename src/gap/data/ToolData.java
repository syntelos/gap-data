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
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-01-31T10:17:15.062Z")
public abstract class ToolData
    extends gap.data.BigTable
    implements DataInheritance<Tool>,
               LastModified,
               HasName
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("Tool","gap.data","Tool","/tools");

    public final static String ClassName = "Tool";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Tool.class);
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorForParent(){
        return ClassDescriptorFor(Resource.class);
    }




    public final static Key KeyShortIdFor(Key ancestor, String name){
        String id = IdFor(ancestor, name);
        return KeyShortFor(ancestor,id);
    }


    public final static String IdFor(Key ancestor, String name){
        if (ancestor.isComplete() && null != name){
            String nameString = name;
            return gap.data.Hash.For(ToString(ancestor)+'/'+nameString);
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Tool ForShortName(Key ancestor, String name){
        if (null != name){
            Key key = KeyShortIdFor(ancestor, name);
            Tool instance = (Tool)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Tool)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Tool GetCreateShort(Key ancestor, String name){
        Tool tool = ForShortName(ancestor, name);
        if (null == tool){
            tool = new Tool(ancestor, name);
            tool = (Tool)gap.data.Store.Put(tool);
        }
        return tool;
    }



    public final static Key KeyShortFor(Key ancestor, String id){
        return KeyFactory.createKey(ancestor,KIND.getName(),id);
    }


    public final static Tool ForShortId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyShortFor(ancestor,id);
            Tool instance = (Tool)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Tool)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Tool Get(Key key){
        if (null != key){
            Tool instance = (Tool)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Tool)gap.data.Store.Query1(q);
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
                Key key = KeyFactory.createKey(ancestor,KIND.getName(),idString);
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
    public final static void Delete(Tool instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Tool instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Tool instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Tool instance){
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
    
    
    public final static Query CreateQueryFor(Key ancestor, Filter filter){
        Query query = new Query(KIND.getName(),ancestor);
        return filter.update(query);
    }
    public final static Tool Query1(Query query){
        if (null != query)
            return (Tool)gap.data.Store.Query1(query);
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
     * Persistent fields' binding for {@link Tool}
     */
    public static enum Field
        implements gap.data.Field<Field>
    {
        InheritFromKey("inheritFromKey"),
        ParentKey("parentKey"),
        Key("key"),
        Id("id"),
        Name("name"),
        LastModified("lastModified"),
        HeadXtm("headXtm"),
        OverlayXtm("overlayXtm"),
        FormXtm("formXtm"),
        TitleHiGraphicUri("titleHiGraphicUri"),
        TitleLoGraphicUri("titleLoGraphicUri"),
        ButtonHiGraphicUri("buttonHiGraphicUri"),
        ButtonLoGraphicUri("buttonLoGraphicUri"),
        ButtonOffGraphicUri("buttonOffGraphicUri"),
        FunctionMethodname("functionMethodname"),
        FunctionClassname("functionClassname"),
        FunctionBody("functionBody"),
        FunctionSourceJava("functionSourceJava"),
        FunctionClassfileJvm("functionClassfileJvm");


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
        public static Object Get(Field field, Tool instance, boolean mayInherit){
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
            case LastModified:
                return instance.getLastModified(mayInherit);
            case HeadXtm:
                return instance.getHeadXtm(mayInherit);
            case OverlayXtm:
                return instance.getOverlayXtm(mayInherit);
            case FormXtm:
                return instance.getFormXtm(mayInherit);
            case TitleHiGraphicUri:
                return instance.getTitleHiGraphicUri(mayInherit);
            case TitleLoGraphicUri:
                return instance.getTitleLoGraphicUri(mayInherit);
            case ButtonHiGraphicUri:
                return instance.getButtonHiGraphicUri(mayInherit);
            case ButtonLoGraphicUri:
                return instance.getButtonLoGraphicUri(mayInherit);
            case ButtonOffGraphicUri:
                return instance.getButtonOffGraphicUri(mayInherit);
            case FunctionMethodname:
                return instance.getFunctionMethodname(mayInherit);
            case FunctionClassname:
                return instance.getFunctionClassname(mayInherit);
            case FunctionBody:
                return instance.getFunctionBody(mayInherit);
            case FunctionSourceJava:
                return instance.getFunctionSourceJava(mayInherit);
            case FunctionClassfileJvm:
                return instance.getFunctionClassfileJvm(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Tool");
            }
        }
        public static boolean Set(Field field, Tool instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case ParentKey:
                return instance.setParentKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case Name:
                return instance.setName(gap.Objects.StringFromObject(value));
            case LastModified:
                return instance.setLastModified(gap.Objects.LongFromObject(value));
            case HeadXtm:
                return instance.setHeadXtm(gap.Objects.StringFromObject(value));
            case OverlayXtm:
                return instance.setOverlayXtm(gap.Objects.StringFromObject(value));
            case FormXtm:
                return instance.setFormXtm(gap.Objects.StringFromObject(value));
            case TitleHiGraphicUri:
                return instance.setTitleHiGraphicUri(gap.Objects.StringFromObject(value));
            case TitleLoGraphicUri:
                return instance.setTitleLoGraphicUri(gap.Objects.StringFromObject(value));
            case ButtonHiGraphicUri:
                return instance.setButtonHiGraphicUri(gap.Objects.StringFromObject(value));
            case ButtonLoGraphicUri:
                return instance.setButtonLoGraphicUri(gap.Objects.StringFromObject(value));
            case ButtonOffGraphicUri:
                return instance.setButtonOffGraphicUri(gap.Objects.StringFromObject(value));
            case FunctionMethodname:
                return instance.setFunctionMethodname(gap.Objects.StringFromObject(value));
            case FunctionClassname:
                return instance.setFunctionClassname(gap.Objects.StringFromObject(value));
            case FunctionBody:
                return instance.setFunctionBody(gap.Objects.TextFromObject(value));
            case FunctionSourceJava:
                return instance.setFunctionSourceJava(gap.Objects.TextFromObject(value));
            case FunctionClassfileJvm:
                return instance.setFunctionClassfileJvm(gap.Objects.BlobFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in Tool");
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

    private volatile transient Tool inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String name;    // *hash-unique
    private volatile Long lastModified;    
    private volatile String headXtm;    
    private volatile String overlayXtm;    
    private volatile String formXtm;    
    private volatile String titleHiGraphicUri;    
    private volatile String titleLoGraphicUri;    
    private volatile String buttonHiGraphicUri;    
    private volatile String buttonLoGraphicUri;    
    private volatile String buttonOffGraphicUri;    
    private volatile String functionMethodname;    
    private volatile String functionClassname;    
    private volatile Text functionBody;    
    private volatile Text functionSourceJava;    
    private volatile Blob functionClassfileJvm;    






    private volatile Key parentKey;
    private volatile transient Resource parent;


    public ToolData() {
        super();
    }
    public ToolData(Key ancestor, String name) {
        super();
        this.setName(name);
        this.parentKey = ancestor;
        String id = IdFor(ancestor,  name);
        this.setId(id);
        Key key = KeyShortFor(ancestor,id);
        this.setKey(key);
    }



    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.key = null;
        this.id = null;
        this.name = null;
        this.lastModified = null;
        this.headXtm = null;
        this.overlayXtm = null;
        this.formXtm = null;
        this.titleHiGraphicUri = null;
        this.titleLoGraphicUri = null;
        this.buttonHiGraphicUri = null;
        this.buttonLoGraphicUri = null;
        this.buttonOffGraphicUri = null;
        this.functionMethodname = null;
        this.functionClassname = null;
        this.functionBody = null;
        this.functionSourceJava = null;
        this.functionClassfileJvm = null;
        this.parent = null;
    }
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final Tool getInheritFrom(){
        Tool inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Tool.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(Tool ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(Tool ancestor){
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
                Tool inheritFrom = this.getInheritFrom();
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

    public final boolean hasHeadXtm(boolean mayInherit){
        return (null != this.getHeadXtm(mayInherit));
    }
    public final boolean hasNotHeadXtm(boolean mayInherit){
        return (null == this.getHeadXtm(mayInherit));
    }
    public final boolean dropHeadXtm(){
        if (null != this.headXtm){
            this.headXtm = null;
            return true;
        }
        else
            return false;
    }
    public final String getHeadXtm(boolean mayInherit){
        if (mayInherit){
            String headXtm = this.headXtm;
            if (null == headXtm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getHeadXtm(MayInherit);
            }
            return headXtm;
        }
        else
            return this.headXtm;
    }
    public final boolean setHeadXtm(String headXtm, boolean withInheritance){
        if (IsNotEqual(this.headXtm,this.getHeadXtm(withInheritance))){
            this.headXtm = headXtm;
            return true;
        }
        else
            return false;
    }
    public final boolean setHeadXtm(String headXtm){
        if (IsNotEqual(this.headXtm,headXtm)){
            this.headXtm = headXtm;
            return true;
        }
        else
            return false;
    }

    public final boolean hasOverlayXtm(boolean mayInherit){
        return (null != this.getOverlayXtm(mayInherit));
    }
    public final boolean hasNotOverlayXtm(boolean mayInherit){
        return (null == this.getOverlayXtm(mayInherit));
    }
    public final boolean dropOverlayXtm(){
        if (null != this.overlayXtm){
            this.overlayXtm = null;
            return true;
        }
        else
            return false;
    }
    public final String getOverlayXtm(boolean mayInherit){
        if (mayInherit){
            String overlayXtm = this.overlayXtm;
            if (null == overlayXtm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getOverlayXtm(MayInherit);
            }
            return overlayXtm;
        }
        else
            return this.overlayXtm;
    }
    public final boolean setOverlayXtm(String overlayXtm, boolean withInheritance){
        if (IsNotEqual(this.overlayXtm,this.getOverlayXtm(withInheritance))){
            this.overlayXtm = overlayXtm;
            return true;
        }
        else
            return false;
    }
    public final boolean setOverlayXtm(String overlayXtm){
        if (IsNotEqual(this.overlayXtm,overlayXtm)){
            this.overlayXtm = overlayXtm;
            return true;
        }
        else
            return false;
    }

    public final boolean hasFormXtm(boolean mayInherit){
        return (null != this.getFormXtm(mayInherit));
    }
    public final boolean hasNotFormXtm(boolean mayInherit){
        return (null == this.getFormXtm(mayInherit));
    }
    public final boolean dropFormXtm(){
        if (null != this.formXtm){
            this.formXtm = null;
            return true;
        }
        else
            return false;
    }
    public final String getFormXtm(boolean mayInherit){
        if (mayInherit){
            String formXtm = this.formXtm;
            if (null == formXtm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFormXtm(MayInherit);
            }
            return formXtm;
        }
        else
            return this.formXtm;
    }
    public final boolean setFormXtm(String formXtm, boolean withInheritance){
        if (IsNotEqual(this.formXtm,this.getFormXtm(withInheritance))){
            this.formXtm = formXtm;
            return true;
        }
        else
            return false;
    }
    public final boolean setFormXtm(String formXtm){
        if (IsNotEqual(this.formXtm,formXtm)){
            this.formXtm = formXtm;
            return true;
        }
        else
            return false;
    }

    public final boolean hasTitleHiGraphicUri(boolean mayInherit){
        return (null != this.getTitleHiGraphicUri(mayInherit));
    }
    public final boolean hasNotTitleHiGraphicUri(boolean mayInherit){
        return (null == this.getTitleHiGraphicUri(mayInherit));
    }
    public final boolean dropTitleHiGraphicUri(){
        if (null != this.titleHiGraphicUri){
            this.titleHiGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public final String getTitleHiGraphicUri(boolean mayInherit){
        if (mayInherit){
            String titleHiGraphicUri = this.titleHiGraphicUri;
            if (null == titleHiGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTitleHiGraphicUri(MayInherit);
            }
            return titleHiGraphicUri;
        }
        else
            return this.titleHiGraphicUri;
    }
    public final boolean setTitleHiGraphicUri(String titleHiGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.titleHiGraphicUri,this.getTitleHiGraphicUri(withInheritance))){
            this.titleHiGraphicUri = titleHiGraphicUri;
            return true;
        }
        else
            return false;
    }
    public final boolean setTitleHiGraphicUri(String titleHiGraphicUri){
        if (IsNotEqual(this.titleHiGraphicUri,titleHiGraphicUri)){
            this.titleHiGraphicUri = titleHiGraphicUri;
            return true;
        }
        else
            return false;
    }

    public final boolean hasTitleLoGraphicUri(boolean mayInherit){
        return (null != this.getTitleLoGraphicUri(mayInherit));
    }
    public final boolean hasNotTitleLoGraphicUri(boolean mayInherit){
        return (null == this.getTitleLoGraphicUri(mayInherit));
    }
    public final boolean dropTitleLoGraphicUri(){
        if (null != this.titleLoGraphicUri){
            this.titleLoGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public final String getTitleLoGraphicUri(boolean mayInherit){
        if (mayInherit){
            String titleLoGraphicUri = this.titleLoGraphicUri;
            if (null == titleLoGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTitleLoGraphicUri(MayInherit);
            }
            return titleLoGraphicUri;
        }
        else
            return this.titleLoGraphicUri;
    }
    public final boolean setTitleLoGraphicUri(String titleLoGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.titleLoGraphicUri,this.getTitleLoGraphicUri(withInheritance))){
            this.titleLoGraphicUri = titleLoGraphicUri;
            return true;
        }
        else
            return false;
    }
    public final boolean setTitleLoGraphicUri(String titleLoGraphicUri){
        if (IsNotEqual(this.titleLoGraphicUri,titleLoGraphicUri)){
            this.titleLoGraphicUri = titleLoGraphicUri;
            return true;
        }
        else
            return false;
    }

    public final boolean hasButtonHiGraphicUri(boolean mayInherit){
        return (null != this.getButtonHiGraphicUri(mayInherit));
    }
    public final boolean hasNotButtonHiGraphicUri(boolean mayInherit){
        return (null == this.getButtonHiGraphicUri(mayInherit));
    }
    public final boolean dropButtonHiGraphicUri(){
        if (null != this.buttonHiGraphicUri){
            this.buttonHiGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public final String getButtonHiGraphicUri(boolean mayInherit){
        if (mayInherit){
            String buttonHiGraphicUri = this.buttonHiGraphicUri;
            if (null == buttonHiGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getButtonHiGraphicUri(MayInherit);
            }
            return buttonHiGraphicUri;
        }
        else
            return this.buttonHiGraphicUri;
    }
    public final boolean setButtonHiGraphicUri(String buttonHiGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.buttonHiGraphicUri,this.getButtonHiGraphicUri(withInheritance))){
            this.buttonHiGraphicUri = buttonHiGraphicUri;
            return true;
        }
        else
            return false;
    }
    public final boolean setButtonHiGraphicUri(String buttonHiGraphicUri){
        if (IsNotEqual(this.buttonHiGraphicUri,buttonHiGraphicUri)){
            this.buttonHiGraphicUri = buttonHiGraphicUri;
            return true;
        }
        else
            return false;
    }

    public final boolean hasButtonLoGraphicUri(boolean mayInherit){
        return (null != this.getButtonLoGraphicUri(mayInherit));
    }
    public final boolean hasNotButtonLoGraphicUri(boolean mayInherit){
        return (null == this.getButtonLoGraphicUri(mayInherit));
    }
    public final boolean dropButtonLoGraphicUri(){
        if (null != this.buttonLoGraphicUri){
            this.buttonLoGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public final String getButtonLoGraphicUri(boolean mayInherit){
        if (mayInherit){
            String buttonLoGraphicUri = this.buttonLoGraphicUri;
            if (null == buttonLoGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getButtonLoGraphicUri(MayInherit);
            }
            return buttonLoGraphicUri;
        }
        else
            return this.buttonLoGraphicUri;
    }
    public final boolean setButtonLoGraphicUri(String buttonLoGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.buttonLoGraphicUri,this.getButtonLoGraphicUri(withInheritance))){
            this.buttonLoGraphicUri = buttonLoGraphicUri;
            return true;
        }
        else
            return false;
    }
    public final boolean setButtonLoGraphicUri(String buttonLoGraphicUri){
        if (IsNotEqual(this.buttonLoGraphicUri,buttonLoGraphicUri)){
            this.buttonLoGraphicUri = buttonLoGraphicUri;
            return true;
        }
        else
            return false;
    }

    public final boolean hasButtonOffGraphicUri(boolean mayInherit){
        return (null != this.getButtonOffGraphicUri(mayInherit));
    }
    public final boolean hasNotButtonOffGraphicUri(boolean mayInherit){
        return (null == this.getButtonOffGraphicUri(mayInherit));
    }
    public final boolean dropButtonOffGraphicUri(){
        if (null != this.buttonOffGraphicUri){
            this.buttonOffGraphicUri = null;
            return true;
        }
        else
            return false;
    }
    public final String getButtonOffGraphicUri(boolean mayInherit){
        if (mayInherit){
            String buttonOffGraphicUri = this.buttonOffGraphicUri;
            if (null == buttonOffGraphicUri && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getButtonOffGraphicUri(MayInherit);
            }
            return buttonOffGraphicUri;
        }
        else
            return this.buttonOffGraphicUri;
    }
    public final boolean setButtonOffGraphicUri(String buttonOffGraphicUri, boolean withInheritance){
        if (IsNotEqual(this.buttonOffGraphicUri,this.getButtonOffGraphicUri(withInheritance))){
            this.buttonOffGraphicUri = buttonOffGraphicUri;
            return true;
        }
        else
            return false;
    }
    public final boolean setButtonOffGraphicUri(String buttonOffGraphicUri){
        if (IsNotEqual(this.buttonOffGraphicUri,buttonOffGraphicUri)){
            this.buttonOffGraphicUri = buttonOffGraphicUri;
            return true;
        }
        else
            return false;
    }

    public final boolean hasFunctionMethodname(boolean mayInherit){
        return (null != this.getFunctionMethodname(mayInherit));
    }
    public final boolean hasNotFunctionMethodname(boolean mayInherit){
        return (null == this.getFunctionMethodname(mayInherit));
    }
    public final boolean dropFunctionMethodname(){
        if (null != this.functionMethodname){
            this.functionMethodname = null;
            return true;
        }
        else
            return false;
    }
    public final String getFunctionMethodname(boolean mayInherit){
        if (mayInherit){
            String functionMethodname = this.functionMethodname;
            if (null == functionMethodname && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionMethodname(MayInherit);
            }
            return functionMethodname;
        }
        else
            return this.functionMethodname;
    }
    public final boolean setFunctionMethodname(String functionMethodname, boolean withInheritance){
        if (IsNotEqual(this.functionMethodname,this.getFunctionMethodname(withInheritance))){
            this.functionMethodname = functionMethodname;
            return true;
        }
        else
            return false;
    }
    public final boolean setFunctionMethodname(String functionMethodname){
        if (IsNotEqual(this.functionMethodname,functionMethodname)){
            this.functionMethodname = functionMethodname;
            return true;
        }
        else
            return false;
    }

    public final boolean hasFunctionClassname(boolean mayInherit){
        return (null != this.getFunctionClassname(mayInherit));
    }
    public final boolean hasNotFunctionClassname(boolean mayInherit){
        return (null == this.getFunctionClassname(mayInherit));
    }
    public final boolean dropFunctionClassname(){
        if (null != this.functionClassname){
            this.functionClassname = null;
            return true;
        }
        else
            return false;
    }
    public final String getFunctionClassname(boolean mayInherit){
        if (mayInherit){
            String functionClassname = this.functionClassname;
            if (null == functionClassname && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionClassname(MayInherit);
            }
            return functionClassname;
        }
        else
            return this.functionClassname;
    }
    public final boolean setFunctionClassname(String functionClassname, boolean withInheritance){
        if (IsNotEqual(this.functionClassname,this.getFunctionClassname(withInheritance))){
            this.functionClassname = functionClassname;
            return true;
        }
        else
            return false;
    }
    public final boolean setFunctionClassname(String functionClassname){
        if (IsNotEqual(this.functionClassname,functionClassname)){
            this.functionClassname = functionClassname;
            return true;
        }
        else
            return false;
    }

    public final boolean hasFunctionBody(boolean mayInherit){
        return (null != this.getFunctionBody(mayInherit));
    }
    public final boolean hasNotFunctionBody(boolean mayInherit){
        return (null == this.getFunctionBody(mayInherit));
    }
    public final boolean dropFunctionBody(){
        if (null != this.functionBody){
            this.functionBody = null;
            return true;
        }
        else
            return false;
    }
    public final Text getFunctionBody(boolean mayInherit){
        if (mayInherit){
            Text functionBody = this.functionBody;
            if (null == functionBody && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionBody(MayInherit);
            }
            return functionBody;
        }
        else
            return this.functionBody;
    }
    public final boolean setFunctionBody(Text functionBody, boolean withInheritance){
        if (IsNotEqual(this.functionBody,this.getFunctionBody(withInheritance))){
            this.functionBody = functionBody;
            return true;
        }
        else
            return false;
    }
    public final boolean setFunctionBody(Text functionBody){
        if (IsNotEqual(this.functionBody,functionBody)){
            this.functionBody = functionBody;
            return true;
        }
        else
            return false;
    }

    public final boolean hasFunctionSourceJava(boolean mayInherit){
        return (null != this.getFunctionSourceJava(mayInherit));
    }
    public final boolean hasNotFunctionSourceJava(boolean mayInherit){
        return (null == this.getFunctionSourceJava(mayInherit));
    }
    public final boolean dropFunctionSourceJava(){
        if (null != this.functionSourceJava){
            this.functionSourceJava = null;
            return true;
        }
        else
            return false;
    }
    public final Text getFunctionSourceJava(boolean mayInherit){
        if (mayInherit){
            Text functionSourceJava = this.functionSourceJava;
            if (null == functionSourceJava && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionSourceJava(MayInherit);
            }
            return functionSourceJava;
        }
        else
            return this.functionSourceJava;
    }
    public final boolean setFunctionSourceJava(Text functionSourceJava, boolean withInheritance){
        if (IsNotEqual(this.functionSourceJava,this.getFunctionSourceJava(withInheritance))){
            this.functionSourceJava = functionSourceJava;
            return true;
        }
        else
            return false;
    }
    public final boolean setFunctionSourceJava(Text functionSourceJava){
        if (IsNotEqual(this.functionSourceJava,functionSourceJava)){
            this.functionSourceJava = functionSourceJava;
            return true;
        }
        else
            return false;
    }

    public final boolean hasFunctionClassfileJvm(boolean mayInherit){
        return (null != this.getFunctionClassfileJvm(mayInherit));
    }
    public final boolean hasNotFunctionClassfileJvm(boolean mayInherit){
        return (null == this.getFunctionClassfileJvm(mayInherit));
    }
    public final boolean dropFunctionClassfileJvm(){
        if (null != this.functionClassfileJvm){
            this.functionClassfileJvm = null;
            return true;
        }
        else
            return false;
    }
    public final Blob getFunctionClassfileJvm(boolean mayInherit){
        if (mayInherit){
            Blob functionClassfileJvm = this.functionClassfileJvm;
            if (null == functionClassfileJvm && this.hasInheritFrom()){
                Tool inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getFunctionClassfileJvm(MayInherit);
            }
            return functionClassfileJvm;
        }
        else
            return this.functionClassfileJvm;
    }
    public final boolean setFunctionClassfileJvm(Blob functionClassfileJvm, boolean withInheritance){
        if (IsNotEqual(this.functionClassfileJvm,this.getFunctionClassfileJvm(withInheritance))){
            this.functionClassfileJvm = functionClassfileJvm;
            return true;
        }
        else
            return false;
    }
    public final boolean setFunctionClassfileJvm(Blob functionClassfileJvm){
        if (IsNotEqual(this.functionClassfileJvm,functionClassfileJvm)){
            this.functionClassfileJvm = functionClassfileJvm;
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
        return this.updateFrom( (Tool)proto);
    }
    public final boolean updateFrom(Tool proto){
        boolean change = false;
        return change;
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorForParent(){
        return ClassDescriptorForParent();
    }
}
