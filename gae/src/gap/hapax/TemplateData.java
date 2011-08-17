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
import com.google.appengine.api.blobstore.BlobKey;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated bean data binding.
 *
 * @see Template
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2011-08-17T18:06:33.233Z")
public abstract class TemplateData
    extends gap.data.BigTable
    implements DataInheritance<Template>,
               gap.data.LastModified,
               gap.data.HasName
{

    private final static long serialVersionUID = 4;

    public final static Kind KIND = Kind.Create("Template","gap.hapax","Template","/templates");

    public final static String ClassName = "Template";

    public final static String DefaultSortBy = "name";


    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(Template.class);
    }
    public final static String PathTo(String subpath){
        return KIND.pathto(subpath);
    }




    public final static Key KeyLongIdFor(String name){
        String id = IdFor( name);
        return KeyLongFor(id);
    }
    /**
     * Used by gap.data.Kind
     *
     * Calls {@link #KeyLongIdFor}
     */
    public final static Key KeyIdFor(Object... args){
        return KeyLongIdFor((String)args[0]);
    }
    /**
     * Used by setId
     *
     * Calls {@link #KeyLongFor}
     */
    public final static Key KeyFor(Object... args){
        return KeyLongFor( (String)args[0]);
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

    /**
     * Used by gap.data.Kind
     */
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
     * Test for uniqueness and iterate under collisions.
     */
    public final static Key NewRandomKeyLong(){
        /*
         * Source matter for data local uniqueness
         */
        long matter = (gap.data.Hash.Djb64(ClassName) ^ (serialVersionUID<<3) ^ serialVersionUID);
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

    /**
     * Drop the instance from memcache and datastore.
     */
    public final static void Delete(Template instance){
        if (null != instance){

            gap.data.Store.Delete(instance.getKey());
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
    public final static BigTableIterator<Template> QueryN(Query query, Page page){
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
        implements gap.data.Field<Template.Field>
    {
        InheritFromKey("inheritFromKey",Type.Primitive),
        Key("key",Type.Primitive),
        Id("id",Type.Primitive),
        Name("name",Type.Primitive),
        LastModified("lastModified",Type.Primitive),
        TemplateSourceHapax("templateSourceHapax",Type.Primitive),
        TemplateTargetHapax("templateTargetHapax",Type.Collection);

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
                try {
                    return Field.valueOf(name);
                }
                catch (IllegalArgumentException notFound){
                    return null;
                }
            else
                return field;
        }
        /**
         * Principal dynamic binding operator (datastore, etc, except serialization)
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static Object Get(Field field, Template instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case Name:
                return instance.getName(mayInherit);
            case LastModified:
                return instance.getLastModified(mayInherit);
            case TemplateSourceHapax:
                return instance.getTemplateSourceHapax(mayInherit);
            case TemplateTargetHapax:
                return instance.getTemplateTargetHapax(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in Template");
            }
        }
        /**
         * Principal dynamic binding operator (datastore, etc, except serialization)
         *
         * Persistent BigTable fields are represented by the string ID.
         */
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
            case TemplateTargetHapax:
                return instance.setTemplateTargetHapax((List.Short<TemplateNode>)value);
            default:
                throw new IllegalArgumentException(field.toString()+" in Template");
            }
        }

        public final static class List
            extends gap.util.ArrayList<Template.Field>
        {
            public List(){
                super();
            }
            public List(Field[] fields){
                super(fields);
            }
            public List(Iterable<Field> fields){
                super();
                for (Field field : fields)
                    this.add(field);
            }
        }


        private final String fieldName;

        private final Type fieldType;

        private final boolean fieldTypePrimitive, fieldTypeBigTable, fieldTypeCollection;

        private final boolean fieldNameKeyOrId;


        Field(String fieldName, Type fieldType){
            if (null != fieldName && null != fieldType){
                this.fieldName = fieldName;
                this.fieldType = fieldType;
                this.fieldNameKeyOrId = BigTable.IsKeyOrId(fieldName);
                /*
                 * Using a switch here causes a null pointer
                 * initializing the switch map.
                 */
                if (Type.Primitive == fieldType){
                    this.fieldTypePrimitive = true;
                    this.fieldTypeBigTable = false;
                    this.fieldTypeCollection = false;
                }
                else if (Type.BigTable == fieldType){
                    this.fieldTypePrimitive = false;
                    this.fieldTypeBigTable = true;
                    this.fieldTypeCollection = false;
                }
                else if (Type.Collection == fieldType){
                    this.fieldTypePrimitive = false;
                    this.fieldTypeBigTable = false;
                    this.fieldTypeCollection = true;
                }
                else if (Type.PrimitiveCollection == fieldType){
                    this.fieldTypePrimitive = true;
                    this.fieldTypeBigTable = false;
                    this.fieldTypeCollection = true;
                }
                else
                    throw new IllegalStateException("Unimplemented field type "+fieldType);
            }
            else
                throw new IllegalStateException();
        }


        public String getFieldName(){
            return this.fieldName;
        }
        public Type getFieldType(){
            return this.fieldType;
        }
        public boolean isFieldTypePrimitive(){
            return this.fieldTypePrimitive;
        }
        public boolean isNotFieldTypePrimitive(){
            return (!this.fieldTypePrimitive);
        }
        public boolean isFieldTypeBigTable(){
            return this.fieldTypeBigTable;
        }
        public boolean isNotFieldTypeBigTable(){
            return (!this.fieldTypeBigTable);
        }
        public boolean isFieldTypeCollection(){
            return this.fieldTypeCollection;
        }
        public boolean isNotFieldTypeCollection(){
            return (!this.fieldTypeCollection);
        }
        public boolean isFieldNameKeyOrId(){
            return this.fieldNameKeyOrId;
        }
        public boolean isNotFieldNameKeyOrId(){
            return (!this.fieldNameKeyOrId);
        }
        public String toString(){
            return this.fieldName;
        }
    }

    private transient Template inheritFrom;


    private String name;
    private Long lastModified;
    private Text templateSourceHapax;
    private transient List.Short<TemplateNode> templateTargetHapax;




    protected TemplateData() {
        super();
    }
    protected TemplateData(String name) {
        super();
        this.setName(name);
        {
            final String id = IdFor(name);
            final Key key = KeyLongFor(id);
            this.setKey(key);
        }
    }



    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.name = null;
        this.lastModified = null;
        this.templateSourceHapax = null;
        List.Short<TemplateNode> templateTargetHapax = this.templateTargetHapax;
        if (null != templateTargetHapax){
            this.templateTargetHapax = null;
            templateTargetHapax.destroy();
        }
    }
    public final String getId(){
        Key key = this.key;
        if (null != key){
            String id = key.getName();
            if (null != id)
                return id;
            else {
                Key pk = key.getParent();
                if (null != pk && KIND.name.equals(pk.getKind())){
                    id = pk.getName();
                    if (null != id)
                        return id;
                    else
                        throw new IllegalStateException("Key missing name"); //(named key structure)
                }
                else
                    throw new IllegalStateException("Key missing name");
            }
        }
        else
            return null;
    }
    public final boolean setId(String id){
        if (null == id){
            if (null != this.key){
                this.key = null;
                return true;
            }
            else
                return false;
        }
        else if (null == this.key){
            this.key = KeyLongFor(id);
            return true;
        }
        else
            return false;
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
            this.dirty = true;
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
            this.dirty = true;
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
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
            this.dirty = true;
            this.name = null;
            return true;
        }
        else
            return false;
    }
    public final String getName(){
        return this.name;
    }
    public final String getName(boolean mayInherit){
        return this.getName();
    }
    public final boolean setName(String name){
        if (IsNotEqual(this.name,name)){
            this.dirty = true;
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
            this.dirty = true;
            this.lastModified = null;
            return true;
        }
        else
            return false;
    }
    public final Long getLastModified(){
        return this.getLastModified(Notation.MayInherit);
    }
    public final Long getLastModified(boolean mayInherit){
        if (mayInherit){
            Long lastModified = this.lastModified;
            if (null == lastModified && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                return inheritFrom.getLastModified(Notation.MayInherit);
            }
            return lastModified;
        }
        else
            return this.lastModified;
    }
    public final boolean setLastModified(Long lastModified, boolean withInheritance){
        if (IsNotEqual(this.lastModified,this.getLastModified(withInheritance))){
            this.dirty = true;
            this.lastModified = lastModified;
            return true;
        }
        else
            return false;
    }
    public final boolean setLastModified(Long lastModified){
        if (IsNotEqual(this.lastModified,lastModified)){
            this.dirty = true;
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
            this.dirty = true;
            this.templateSourceHapax = null;
            return true;
        }
        else
            return false;
    }
    public final Text getTemplateSourceHapax(){
        return this.getTemplateSourceHapax(Notation.MayInherit);
    }
    public final Text getTemplateSourceHapax(boolean mayInherit){
        if (mayInherit){
            Text templateSourceHapax = this.templateSourceHapax;
            if (null == templateSourceHapax && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                return inheritFrom.getTemplateSourceHapax(Notation.MayInherit);
            }
            return templateSourceHapax;
        }
        else
            return this.templateSourceHapax;
    }
    public final boolean setTemplateSourceHapax(Text templateSourceHapax, boolean withInheritance){
        if (IsNotEqual(this.templateSourceHapax,this.getTemplateSourceHapax(withInheritance))){
            this.dirty = true;
            this.templateSourceHapax = templateSourceHapax;
            return true;
        }
        else
            return false;
    }
    public final boolean setTemplateSourceHapax(Text templateSourceHapax){
        if (IsNotEqual(this.templateSourceHapax,templateSourceHapax)){
            this.dirty = true;
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
    public final List.Short<TemplateNode> getTemplateTargetHapax(){
        return this.getTemplateTargetHapax(Notation.MayInherit);
    }
    public final List.Short<TemplateNode> getTemplateTargetHapax(boolean mayInherit){
        List.Short<TemplateNode> templateTargetHapax = this.templateTargetHapax;
        if (null == templateTargetHapax){
            if (mayInherit && this.hasInheritFrom()){
                Template inheritFrom = this.getInheritFrom();
                if (null != inheritFrom){
                    templateTargetHapax = inheritFrom.getTemplateTargetHapax(Notation.MayInherit);
                    if (null != templateTargetHapax)
                        return templateTargetHapax;
                }
            }
            /*
             * Collection type coersion
             */
            {
                Object tmp = new ListShortTemplateTemplateNode((Template)this);
                templateTargetHapax = (List.Short<TemplateNode>)tmp;
            }
            this.templateTargetHapax = templateTargetHapax;
            templateTargetHapax.init();
        }
        return templateTargetHapax;
    }
    public final boolean setTemplateTargetHapax(List.Short<TemplateNode> templateTargetHapax){
        if (IsNotEqual(this.templateTargetHapax,templateTargetHapax)){
            this.dirty = true;
            this.templateTargetHapax = templateTargetHapax;
            return true;
        }
        else
            return false;
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
            List.Short<TemplateNode> collection = this.getTemplateTargetHapax(Notation.MayInherit);
            return collection.fetch(filter);
        }
        else
            throw new IllegalArgumentException();
    }
    public final TemplateNode getTemplateTargetHapax(gap.data.ListFilter<TemplateNode> filter){
        if (null != filter){
            List.Short<TemplateNode> list = this.getTemplateTargetHapax(Notation.MayInherit);
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
    public final gap.data.List<gap.data.Field> getClassFields(){
        gap.data.List re = new Template.Field.List(Field.values());
        /*
         * Compiler has a type astigmatism (parameterized interface gap.data.Field)
         */
        return (gap.data.List<gap.data.Field>)re;
    }
    public final gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        String lastModifiedRequest = req.getParameter("lastModified");
        if (null != lastModifiedRequest && 0 < lastModifiedRequest.length()){
            try {
                Long lastModified = Strings.LongFromString(lastModifiedRequest);
                if (this.setLastModified(lastModified)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"lastModified",lastModifiedRequest,exc);
            }
        }
        String templateSourceHapaxRequest = req.getParameter("templateSourceHapax");
        if (null != templateSourceHapaxRequest && 0 < templateSourceHapaxRequest.length()){
            try {
                Text templateSourceHapax = Strings.TextFromString(templateSourceHapaxRequest);
                if (this.setTemplateSourceHapax(templateSourceHapax)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"templateSourceHapax",templateSourceHapaxRequest,exc);
            }
        }
        return change;
    }
    public final boolean updateFrom(BigTable proto){
        return this.updateFrom( (Template)proto);
    }
    public final boolean updateFrom(Template proto){
        boolean mayInherit = (!this.hasInheritFromKey());
        boolean change = false;
        Long lastModified = proto.getLastModified(mayInherit);
        if (null != lastModified && this.setLastModified(lastModified)){
            change = true;
        }
        Text templateSourceHapax = proto.getTemplateSourceHapax(mayInherit);
        if (null != templateSourceHapax && this.setTemplateSourceHapax(templateSourceHapax)){
            change = true;
        }
        return change;
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor();
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0)){
                    String id = this.getId();
                    return (null != id);
                }
                else
                    return false;
            case Name:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasName(true);
                }
            case LastModified:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasLastModified(true);
                }
            case TemplateSourceHapax:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasTemplateSourceHapax(true);
                }
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.hasVariable(name);
        }
    }
    public String getVariable(TemplateName name){
        Field field = Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0))
                    return this.getId();
                else
                    return null;
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
        Field field = Field.For(name.getTerm());
        if (null != field){
            if (name.has(1)){
                switch (field){
                case Id:
                    throw new UnsupportedOperationException(field.name());
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
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){
        Field field = Field.For(name.getTerm());
        if (null != field){
            switch (field){
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
    public Template clone(){
        return (Template)super.clone();
    }
    public String pathto(){
        return KIND.pathto(this.getId());
    }
    public String pathto(String subpath){
        StringBuilder string = new StringBuilder();
        string.append(this.getId());
        if (null != subpath){
            if (0 == subpath.length() || '/' != subpath.charAt(0))
                string.append('/');
            string.append(subpath);
        }
        return KIND.pathto(string.toString());
    }
}
