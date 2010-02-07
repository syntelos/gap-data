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
package gap.hapax;


import gap.*;
import gap.data.*;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;
import gap.util.*;

import com.google.appengine.api.datastore.*;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated bean data binding.
 * 
 * @see Template
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2010-02-07T17:49:19.106Z")
public abstract class TemplateData
    extends gap.data.BigTable
    implements DataInheritance<Template>,
               gap.data.LastModified,
               gap.data.HasName
{

    private final static long serialVersionUID = 3;

    public final static Kind KIND = Kind.Create("Template","gap.hapax","Template","/templates");

    public final static String ClassName = "Template";

    public final static String DefaultSortBy = "name";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Template.class);
    }




    public final static Key KeyLongIdFor(String name){
        String id = IdFor( name);
        return KeyLongFor(id);
    }


    public final static String IdFor(String name){
        if (null != name){
            String nameString = name;
            return gap.data.Hash.For(nameString);
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Template ForLongName(String name){
        if (null != name){
            Key key = KeyLongIdFor( name);
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Template GetCreateLong(String name){
        Template template = ForLongName( name);
        if (null == template){
            template = new Template( name);
            template = (Template)gap.data.Store.Put(template);
        }
        return template;
    }



    public final static Key KeyLongFor(String id){
        return KeyFactory.createKey(KIND.getName(),id);
    }


    public final static Template ForLongId(String id){
        if (null != id){
            Key key = KeyLongFor(id);
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }


    public final static Template Get(Key key){
        if (null != key){
            Template instance = (Template)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (Template)gap.data.Store.Query1(q);
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
    public final static Template FromObject(Object value){
        if (null == value)
            return null;
        else if (value instanceof Template)
            return (Template)value;
        else if (value instanceof Key)
            return Get( (Key)value);
        else if (value instanceof String){
            /*
             * TODO: ilarg: not key.enc; Key For ID.
             */
            Key key = gap.Strings.KeyFromString( (String)value);
            return Get(key);
        }
        else
            throw new IllegalArgumentException(value.getClass().getName());
    }



    /**
     * Drop the instance and any children of its key from the world,
     * memcache and store.
     */
    public final static void Delete(Template instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(Template instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(Template instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(Template instance){
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
    
    
    public final static Template Query1(Query query){
        if (null != query)
            return (Template)gap.data.Store.Query1(query);
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
     * Persistent fields' binding for {@link Template}
     */
    public static enum Field
        implements gap.data.Field<Field>
    {
        InheritFromKey("inheritFromKey"),
        Key("key"),
        Id("id"),
        Name("name"),
        LastModified("lastModified"),
        TemplateSourceHapax("templateSourceHapax");


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
        public static Object Get(Field field, Template instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId(mayInherit);
            case Name:
                return instance.getName(mayInherit);
            case LastModified:
                return instance.getLastModified(mayInherit);
            case TemplateSourceHapax:
                return instance.getTemplateSourceHapax(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Template");
            }
        }
        public static boolean Set(Field field, Template instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case Name:
                return instance.setName(gap.Objects.StringFromObject(value));
            case LastModified:
                return instance.setLastModified(gap.Objects.LongFromObject(value));
            case TemplateSourceHapax:
                return instance.setTemplateSourceHapax(gap.Objects.TextFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in Template");
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

    private volatile transient Template inheritFrom;


    private volatile String id;    // *unique
    private volatile String name;    // *hash-unique
    private volatile Long lastModified;    
    private volatile Text templateSourceHapax;    
    private volatile List.Short<TemplateNode> templateTargetHapax;




    protected TemplateData() {
        super();
    }
    protected TemplateData(String name) {
        super();
        this.setName(name);
        String id = IdFor( name);
        this.setId(id);
        Key key = KeyLongFor(id);
        this.setKey(key);
    }



    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.id = null;
        this.name = null;
        this.lastModified = null;
        this.templateSourceHapax = null;
        List.Short<TemplateNode> templateTargetHapax = this.templateTargetHapax;
        if (null != templateTargetHapax){
            this.templateTargetHapax = null;
            templateTargetHapax.destroy();
        }
    }
    public final boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public final boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public final Template getInheritFrom(){
        Template inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = Template.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(Template ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(Template ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
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
                Template inheritFrom = this.getInheritFrom();
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
    public final boolean hasTemplateSourceHapax(boolean mayInherit){
        return (null != this.getTemplateSourceHapax(mayInherit));
    }
    public final boolean hasNotTemplateSourceHapax(boolean mayInherit){
        return (null == this.getTemplateSourceHapax(mayInherit));
    }
    public final boolean dropTemplateSourceHapax(){
        if (null != this.templateSourceHapax){
            this.templateSourceHapax = null;
            return true;
        }
        else
            return false;
    }
    public final Text getTemplateSourceHapax(boolean mayInherit){
        if (mayInherit){
            Text templateSourceHapax = this.templateSourceHapax;
            if (null == templateSourceHapax && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getTemplateSourceHapax(MayInherit);
            }
            return templateSourceHapax;
        }
        else
            return this.templateSourceHapax;
    }
    public final boolean setTemplateSourceHapax(Text templateSourceHapax, boolean withInheritance){
        if (IsNotEqual(this.templateSourceHapax,this.getTemplateSourceHapax(withInheritance))){
            this.templateSourceHapax = templateSourceHapax;
            return true;
        }
        else
            return false;
    }
    public final boolean setTemplateSourceHapax(Text templateSourceHapax){
        if (IsNotEqual(this.templateSourceHapax,templateSourceHapax)){
            this.templateSourceHapax = templateSourceHapax;
            return true;
        }
        else
            return false;
    }
    public final boolean hasTemplateTargetHapax(boolean mayInherit){
        return (this.getTemplateTargetHapax(mayInherit).isNotEmpty());
    }
    public final boolean hasNotTemplateTargetHapax(boolean mayInherit){
        return (this.getTemplateTargetHapax(mayInherit).isEmpty());
    }
    public final boolean dropTemplateTargetHapax(){
        List.Short<TemplateNode> templateTargetHapax = this.templateTargetHapax;
        if (null != templateTargetHapax){
            this.templateTargetHapax = null;
            templateTargetHapax.destroy();
            return true;
        }
        else
            return false;
    }
    public final List.Short<TemplateNode> getTemplateTargetHapax(boolean mayInherit){
        List.Short<TemplateNode> templateTargetHapax = this.templateTargetHapax;
        if (null == templateTargetHapax){
            if (mayInherit && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    templateTargetHapax = inheritFrom.getTemplateTargetHapax(MayInherit);
                    if (null != templateTargetHapax)
                        return templateTargetHapax;
                }
            }
            templateTargetHapax = new ListShortTemplateTemplateNode((Template)this);
            this.templateTargetHapax = templateTargetHapax;
            templateTargetHapax.init();
        }
        return templateTargetHapax;
    }
    public final void setTemplateTargetHapax(List.Short<TemplateNode> templateTargetHapax){
        this.templateTargetHapax = templateTargetHapax;
    }
    public final boolean isEmptyTemplateTargetHapax(){
        List.Short<TemplateNode> collection = this.templateTargetHapax;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyTemplateTargetHapax(){
        List.Short<TemplateNode> collection = this.templateTargetHapax;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
    public final TemplateNode fetchTemplateTargetHapax(Filter filter){
        if (null != filter && KIND == filter.kind){
            List.Short<TemplateNode> collection = this.getTemplateTargetHapax(MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final TemplateNode getTemplateTargetHapax(gap.data.ListFilter<TemplateNode> filter){
        if (null != filter){
            List.Short<TemplateNode> list = this.getTemplateTargetHapax(MayInherit);
            for (TemplateNode item : list){
                if (filter.accept(item))
                    return item;
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
        return this.updateFrom( (Template)proto);
    }
    public final boolean updateFrom(Template proto){
        boolean change = false;
        return change;
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Id:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasId(true);
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasName(true);
            case LastModified:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasLastModified(true);
            case TemplateSourceHapax:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.hasTemplateSourceHapax(true);
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
            case Id:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getId(true);
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getName(true);
            case LastModified:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.LongToString(this.getLastModified(true));
            case TemplateSourceHapax:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.TextToString(this.getTemplateSourceHapax(true));
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getVariable(name);
        }
    }
    public void setVariable(TemplateName name, String value){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            if (name.has(1)){
                switch (field){
                case Id:
                    throw new IllegalStateException(field.name());
                case Name:
                    throw new IllegalStateException(field.name());
                case LastModified:
                    throw new IllegalStateException(field.name());
                case TemplateSourceHapax:
                    throw new IllegalStateException(field.name());
                default:
                    throw new IllegalStateException(field.name());
                }
            }
            else
                Field.Set(field,((Template)this),value);
        }
        else {
            super.setVariable(name,value);
        }
    }
    public List<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Field.For(name.getComponent(0));
        if (null != field){
            switch (field){
            case Id:
                return null;
            case Name:
                return null;
            case LastModified:
                return null;
            case TemplateSourceHapax:
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
