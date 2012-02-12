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

import json.Json;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.BlobKey;

import java.util.Date;

import javax.annotation.Generated;

/**
 * Generated bean data binding.
 *
 * @see TemplateNode
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2012-02-12T02:50:36.851Z")
public abstract class TemplateNodeData
    extends gap.data.BigTable
    implements DataInheritance<TemplateNode>
{

    private final static long serialVersionUID = 5;

    public final static Kind KIND = Kind.Create("TemplateNode","gap.hapax","TemplateNode","/template-nodes");

    public final static String ClassName = "TemplateNode";

    public final static String DefaultSortBy = "offset";


    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(TemplateNode.class);
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorForParent(){
        return ClassDescriptorFor(Template.class);
    }
    /**
     * @see gap.data.Kind#pathto()
     */
    public final static String PathTo(){
        return KIND.pathto();
    }
    public final static String PathTo(String subpath){
        return KIND.pathto(subpath);
    }

    /**
     * Short instance key from parent key
     */
    public final static Key KeyShortIdFor(Key ancestor, String nodeType, Integer offset){
        String id = TemplateNode.IdFor( nodeType,  offset);
        return TemplateNode.KeyShortFor(ancestor,id);
    }
    /**
     * Used by gap.data.Kind
     *
     * Calls {@link #KeyShortIdFor}
     */
    public final static Key KeyIdFor(Object... args){
        return TemplateNode.KeyShortIdFor( (Key)args[0], (String)args[1], (Integer)args[2]);
    }
    /**
     * Used by setId
     *
     * Calls {@link #KeyShortFor}
     */
    public final static Key KeyFor(Object... args){
        return TemplateNode.KeyShortFor( (Key)args[0], (String)args[1]);
    }
    /**
     * Identifier for unique fields
     */
    public final static String IdFor(String nodeType, Integer offset){
        if (null != nodeType && null != offset){
            String nodeTypeString = nodeType;
            String offsetString = gap.Strings.IntegerToString(offset);
            return gap.data.Hash.For(nodeTypeString+'/'+offsetString);
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup from parent key
     */
    public final static TemplateNode ForShortNodeTypeOffset(Key ancestor, String nodeType, Integer offset){
        if (null != nodeType && null != offset){
            Key key = TemplateNode.KeyShortIdFor(ancestor, nodeType, offset);
            TemplateNode instance = (TemplateNode)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = TemplateNode.CreateQueryFor(key);
                return (TemplateNode)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup or create from parent key
     */
    public final static TemplateNode GetCreateShort(Key ancestor, String nodeType, Integer offset){
        TemplateNode templateNode = TemplateNode.ForShortNodeTypeOffset(ancestor, nodeType, offset);
        if (null == templateNode){
            templateNode = new TemplateNode(ancestor, nodeType, offset);
            templateNode = (TemplateNode)gap.data.Store.PutClass(templateNode);
        }
        return templateNode;
    }


    public final static Key KeyShortFor(Key ancestor, String id){
        return KeyFactory.createKey(ancestor,KIND.getName(),id);
    }


    public final static TemplateNode ForShortId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = TemplateNode.KeyShortFor(ancestor,id);
            TemplateNode instance = (TemplateNode)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = TemplateNode.CreateQueryFor(key);
                return (TemplateNode)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Used by gap.data.Kind
     */
    public final static TemplateNode Get(Key key){
        if (null != key){
            TemplateNode instance = (TemplateNode)gap.data.Store.GetClass(key);
            if (null != instance)
                return instance;
            else {
                Query q = TemplateNode.CreateQueryFor(key);
                return (TemplateNode)gap.data.Store.Query1Class(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static Key GetKey(Key key){
        if (null != key){
            Query q = TemplateNode.CreateQueryFor(key);
            return gap.data.Store.Query1Key(q);
        }
        else
            throw new IllegalArgumentException();
    }
    public final static TemplateNode FromObject(Object value){
        if (null == value)
            return null;
        else if (value instanceof TemplateNode)
            return (TemplateNode)value;
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
     * Anonymous random key cannot be mapped to network identifier
     * @see TemplateNode#IdFor
     *
     * Test for uniqueness and iterate under collisions.
     */
    public final static Key NewRandomKeyShort(Key ancestor){
        if (null != ancestor){
            /*
             * Source matter for data local uniqueness
             */
            long matter = gap.data.Hash.Djb64(gap.data.BigTable.ToString(ancestor));
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
     * Drop all children of the parent
     */
    public final static void Delete(Template parent){
        if (null != parent){

            DeleteChildrenOf(parent.getKey());
        }
    }
    /**
     * Drop all children of the parent
     */
    public final static void DeleteChildrenOf(Key parentKey){
        if (null != parentKey){

             gap.data.Store.DeleteCollection(KIND,TemplateNode.CreateQueryFor(parentKey));
        }
    }
    /**
     * Drop the instance from memcache and datastore.
     */
    public final static void Delete(TemplateNode instance){
        if (null != instance){

            Delete(instance.getKey());
        }
    }
    /**
     * Drop the instance from memcache and datastore.
     */
    public final static void Delete(Key instanceKey){
        if (null != instanceKey){

            gap.data.Store.Delete(instanceKey);
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(TemplateNode instance){
        if (null != instance){

            gap.data.Store.Clean(instance.getKey());
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(TemplateNode instance){
        if (null != instance){

            gap.data.Store.PutClass(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(TemplateNode instance){
        if (null != instance){

            gap.data.Store.PutClass(instance);
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
    public final static Query CreateQueryFor(Key ancestor, Filter filter){
        Query query = new Query(KIND.getName(),ancestor);
        return filter.update(query);
    }
    public final static TemplateNode Query1(Query query){
        if (null != query)
            return (TemplateNode)gap.data.Store.Query1Class(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator<TemplateNode> ListPage(Key ancestor, Page page){

        return TemplateNode.QueryN(TemplateNode.CreateQueryFor(ancestor),page);
    }
    public final static BigTableIterator<TemplateNode> QueryN(Query query, Page page){
        if (null != query && null != page)
            return gap.data.Store.QueryNClass(query,page);
        else
            throw new IllegalArgumentException();
    }
    public final static Key QueryKey1(Query query){
        if (null != query)
            return gap.data.Store.Query1Key(query);
        else
            throw new IllegalArgumentException();
    }
    public final static List.Primitive<Key> QueryNKey(Query query, Page page){
        if (null != query)
            return gap.data.Store.QueryNKey(query,page);
        else
            throw new IllegalArgumentException();
    }
    public final static List.Primitive<Key> QueryNKey(Query query){
        if (null != query)
            return gap.data.Store.QueryNKey(query);
        else
            throw new IllegalArgumentException();
    }

    /**
     * Persistent fields' binding for {@link TemplateNode}
     */
    public static enum Field
        implements gap.data.Field<TemplateNode.Field>
    {
        InheritFromKey("inheritFromKey",Type.Primitive),
        ParentKey("parentKey",Type.Primitive),
        Key("key",Type.Primitive),
        Id("id",Type.Primitive),
        NodeType("nodeType",Type.Primitive),
        Offset("offset",Type.Primitive),
        LineNumber("lineNumber",Type.Primitive),
        Index("index",Type.Primitive),
        NodeContent("nodeContent",Type.Primitive),
        IndexCloseRelative("indexCloseRelative",Type.Primitive);

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
         * Field statistics are maintained for persistent fields exclusively
         */
        public final static class Statistics
            extends gap.data.Field.Statistics<TemplateNode.Field>
        {
            public Statistics(){
                super(TemplateNode.Field.class);
            }
        }
        /**
         * Dynamic binding operator for field data type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static Object Get(Field field, TemplateNode instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case ParentKey:
                return instance.getParentKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case NodeType:
                return instance.getNodeType(mayInherit);
            case Offset:
                return instance.getOffset(mayInherit);
            case LineNumber:
                return instance.getLineNumber(mayInherit);
            case Index:
                return instance.getIndex(mayInherit);
            case NodeContent:
                return instance.getNodeContent(mayInherit);
            case IndexCloseRelative:
                return instance.getIndexCloseRelative(mayInherit);
            default:
                throw new IllegalArgumentException(field.toString()+" in TemplateNode");
            }
        }
        /**
         * Dynamic binding operator for field data type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static boolean Set(Field field, TemplateNode instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey(gap.Objects.KeyFromObject(value));
            case ParentKey:
                return instance.setParentKey(gap.Objects.KeyFromObject(value));
            case Key:
                return instance.setKey(gap.Objects.KeyFromObject(value));
            case Id:
                return instance.setId(gap.Objects.StringFromObject(value));
            case NodeType:
                return instance.setNodeType(gap.Objects.StringFromObject(value));
            case Offset:
                return instance.setOffset(gap.Objects.IntegerFromObject(value));
            case LineNumber:
                return instance.setLineNumber(gap.Objects.IntegerFromObject(value));
            case Index:
                return instance.setIndex(gap.Objects.IntegerFromObject(value));
            case NodeContent:
                return instance.setNodeContent(gap.Objects.TextFromObject(value));
            case IndexCloseRelative:
                return instance.setIndexCloseRelative(gap.Objects.IntegerFromObject(value));
            default:
                throw new IllegalArgumentException(field.toString()+" in TemplateNode");
            }
        }
        /**
         * Dynamic binding operator for field storage type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static java.io.Serializable Storage(Field field, TemplateNode instance){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case ParentKey:
                return instance.getParentKey();
            case Key:
                return instance.getKey();
            case Id:
                return instance.getId();
            case NodeType:
                return instance.getNodeType(MayNotInherit);
            case Offset:{
                return instance.getOffset(MayNotInherit);
            }
            case LineNumber:{
                return instance.getLineNumber(MayNotInherit);
            }
            case Index:{
                return instance.getIndex(MayNotInherit);
            }
            case NodeContent:
                return instance.getNodeContent(MayNotInherit);
            case IndexCloseRelative:{
                return instance.getIndexCloseRelative(MayNotInherit);
            }
            default:
                throw new IllegalArgumentException(field.toString()+" in TemplateNode");
            }
        }
        /**
         * Dynamic binding operator for field storage type
         *
         * Persistent BigTable fields are represented by the string ID.
         */
        public static void Storage(Field field, TemplateNode instance, java.io.Serializable value){
            switch(field){
            case InheritFromKey:
                instance.setInheritFromKey( (Key)value);
                return;
            case ParentKey:
                instance.setParentKey( (Key)value);
                return;
            case Key:
                instance.setKey( (Key)value);
                return;
            case Id:
                instance.setId( (String)value);
                return;
            case NodeType:
                instance.setNodeType( (String)value);
                return;
            case Offset:{

                instance.setOffset( (Number)value);
                return;
            }
            case LineNumber:{

                instance.setLineNumber( (Number)value);
                return;
            }
            case Index:{

                instance.setIndex( (Number)value);
                return;
            }
            case NodeContent:
                instance.setNodeContent( (Text)value);
                return;
            case IndexCloseRelative:{

                instance.setIndexCloseRelative( (Number)value);
                return;
            }
            default:
                throw new IllegalArgumentException(field.toString()+" in TemplateNode");
            }
        }


        public final static class List
            extends gap.util.ArrayList<TemplateNode.Field>
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

    private transient TemplateNode.Field.Statistics fieldStatistics = new TemplateNode.Field.Statistics();

    private transient TemplateNode inheritFrom;


    private String nodeType;
    private Integer offset;
    private Integer lineNumber;
    private Integer index;
    private Text nodeContent;
    private Integer indexCloseRelative;


    private Key parentKey;
    private transient Template parent;


    protected TemplateNodeData() {
        super();
    }
    protected TemplateNodeData(Key ancestor, String nodeType, Integer offset) {
        super();
        this.setNodeType(nodeType);
        this.setOffset(offset);
        this.parentKey = ancestor;
        {
            final String id = TemplateNode.IdFor(nodeType, offset);
            final Key key = TemplateNode.KeyShortFor(ancestor,id);
            this.setKey(key);
        }
    }


    private TemplateNode.Field.Statistics fieldStatistics(){
        TemplateNode.Field.Statistics fieldStatistics = this.fieldStatistics;
        if (null == fieldStatistics){
            fieldStatistics = new TemplateNode.Field.Statistics();
            this.fieldStatistics = fieldStatistics;
        }
        return fieldStatistics;
    }
    public void destroy(){
        this.inheritFrom = null;
        this.nodeType = null;
        this.offset = null;
        this.lineNumber = null;
        this.index = null;
        this.nodeContent = null;
        this.indexCloseRelative = null;
        this.parent = null;
    }
    public final String getId(){

        String id = TemplateNode.IdFor(KIND.name, this.key);
        if (null != id)
            return id;
        else
            return TemplateNode.IdFor(this.nodeType, this.offset);
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
            this.key = TemplateNode.KeyShortFor(this.getParentKey(),id);
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
    public final TemplateNode getInheritFrom(){
        TemplateNode inheritFrom = this.inheritFrom;
        if (null == inheritFrom){
            Key inheritFromKey = this.inheritFromKey;
            if (null != inheritFromKey){
                inheritFrom = TemplateNode.Get(inheritFromKey);
                this.inheritFrom = inheritFrom;
            }
        }
        return inheritFrom;
    }
    public final boolean setInheritFrom(TemplateNode ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){

            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public final boolean inheritFrom(TemplateNode ancestor){
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
    public final Template getParent(){
        Template parent = this.parent;
        if (null == parent){
            Key parentKey = this.parentKey;
            if (null != parentKey){
                parent = Template.Get(parentKey);
                this.parent = parent;
            }
        }
        return parent;
    }
    public final boolean setParent(Template ancestor){
        if (IsNotEqual(this.parent,ancestor)){

            this.parent = ancestor;
            if (null != ancestor)
                this.parentKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }

    public final boolean hasNodeType(boolean mayInherit){
        return (null != this.getNodeType(mayInherit));
    }
    public final boolean hasNotNodeType(boolean mayInherit){
        return (null == this.getNodeType(mayInherit));
    }
    public final boolean dropNodeType(){
        if (null != this.nodeType){
            this.fieldStatistics().markDirty(TemplateNode.Field.NodeType);
            this.nodeType = null;
            return true;
        }
        else
            return false;
    }
    public final String getNodeType(){
        return this.nodeType;
    }
    public final String getNodeType(boolean mayInherit){
        return this.getNodeType();
    }
    public final boolean setNodeType(String nodeType){
        if (IsNotEqual(this.nodeType,nodeType)){
            this.fieldStatistics().markDirty(TemplateNode.Field.NodeType);
            this.nodeType = nodeType;
            return true;
        }
        else
            return false;
    }
    public final boolean hasOffset(boolean mayInherit){
        return (null != this.getOffset(mayInherit));
    }
    public final boolean hasNotOffset(boolean mayInherit){
        return (null == this.getOffset(mayInherit));
    }
    public final boolean dropOffset(){
        if (null != this.offset){
            this.fieldStatistics().markDirty(TemplateNode.Field.Offset);
            this.offset = null;
            return true;
        }
        else
            return false;
    }
    public final Integer getOffset(){
        return this.offset;
    }
    public final Integer getOffset(boolean mayInherit){
        return this.getOffset();
    }
    public final boolean setOffset(Integer offset){
        if (IsNotEqual(this.offset,offset)){
            this.fieldStatistics().markDirty(TemplateNode.Field.Offset);
            this.offset = offset;
            return true;
        }
        else
            return false;
    }
    public final boolean hasLineNumber(boolean mayInherit){
        return (null != this.getLineNumber(mayInherit));
    }
    public final boolean hasNotLineNumber(boolean mayInherit){
        return (null == this.getLineNumber(mayInherit));
    }
    public final boolean dropLineNumber(){
        if (null != this.lineNumber){
            this.fieldStatistics().markDirty(TemplateNode.Field.LineNumber);
            this.lineNumber = null;
            return true;
        }
        else
            return false;
    }
    public final Integer getLineNumber(){
        return this.getLineNumber(Notation.MayInherit);
    }
    public final Integer getLineNumber(boolean mayInherit){
        if (mayInherit){
            Integer lineNumber = this.lineNumber;
            if (null == lineNumber && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                return inheritFrom.getLineNumber(Notation.MayInherit);
            }
            return lineNumber;
        }
        else
            return this.lineNumber;
    }
    public final boolean setLineNumber(Integer lineNumber, boolean withInheritance){
        if (IsNotEqual(this.lineNumber,this.getLineNumber(withInheritance))){
            this.fieldStatistics().markDirty(TemplateNode.Field.LineNumber);
            this.lineNumber = lineNumber;
            return true;
        }
        else
            return false;
    }
    public final boolean setLineNumber(Integer lineNumber){
        if (IsNotEqual(this.lineNumber,lineNumber)){
            this.fieldStatistics().markDirty(TemplateNode.Field.LineNumber);
            this.lineNumber = lineNumber;
            return true;
        }
        else
            return false;
    }
    public final boolean hasIndex(boolean mayInherit){
        return (null != this.getIndex(mayInherit));
    }
    public final boolean hasNotIndex(boolean mayInherit){
        return (null == this.getIndex(mayInherit));
    }
    public final boolean dropIndex(){
        if (null != this.index){
            this.fieldStatistics().markDirty(TemplateNode.Field.Index);
            this.index = null;
            return true;
        }
        else
            return false;
    }
    public final Integer getIndex(){
        return this.getIndex(Notation.MayInherit);
    }
    public final Integer getIndex(boolean mayInherit){
        if (mayInherit){
            Integer index = this.index;
            if (null == index && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                return inheritFrom.getIndex(Notation.MayInherit);
            }
            return index;
        }
        else
            return this.index;
    }
    public final boolean setIndex(Integer index, boolean withInheritance){
        if (IsNotEqual(this.index,this.getIndex(withInheritance))){
            this.fieldStatistics().markDirty(TemplateNode.Field.Index);
            this.index = index;
            return true;
        }
        else
            return false;
    }
    public final boolean setIndex(Integer index){
        if (IsNotEqual(this.index,index)){
            this.fieldStatistics().markDirty(TemplateNode.Field.Index);
            this.index = index;
            return true;
        }
        else
            return false;
    }
    public final boolean hasNodeContent(boolean mayInherit){
        return (null != this.getNodeContent(mayInherit));
    }
    public final boolean hasNotNodeContent(boolean mayInherit){
        return (null == this.getNodeContent(mayInherit));
    }
    public final boolean dropNodeContent(){
        if (null != this.nodeContent){
            this.fieldStatistics().markDirty(TemplateNode.Field.NodeContent);
            this.nodeContent = null;
            return true;
        }
        else
            return false;
    }
    public final Text getNodeContent(){
        return this.getNodeContent(Notation.MayInherit);
    }
    public final Text getNodeContent(boolean mayInherit){
        if (mayInherit){
            Text nodeContent = this.nodeContent;
            if (null == nodeContent && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                return inheritFrom.getNodeContent(Notation.MayInherit);
            }
            return nodeContent;
        }
        else
            return this.nodeContent;
    }
    public final boolean setNodeContent(Text nodeContent, boolean withInheritance){
        if (IsNotEqual(this.nodeContent,this.getNodeContent(withInheritance))){
            this.fieldStatistics().markDirty(TemplateNode.Field.NodeContent);
            this.nodeContent = nodeContent;
            return true;
        }
        else
            return false;
    }
    public final boolean setNodeContent(Text nodeContent){
        if (IsNotEqual(this.nodeContent,nodeContent)){
            this.fieldStatistics().markDirty(TemplateNode.Field.NodeContent);
            this.nodeContent = nodeContent;
            return true;
        }
        else
            return false;
    }
    public final boolean hasIndexCloseRelative(boolean mayInherit){
        return (null != this.getIndexCloseRelative(mayInherit));
    }
    public final boolean hasNotIndexCloseRelative(boolean mayInherit){
        return (null == this.getIndexCloseRelative(mayInherit));
    }
    public final boolean dropIndexCloseRelative(){
        if (null != this.indexCloseRelative){
            this.fieldStatistics().markDirty(TemplateNode.Field.IndexCloseRelative);
            this.indexCloseRelative = null;
            return true;
        }
        else
            return false;
    }
    public final Integer getIndexCloseRelative(){
        return this.getIndexCloseRelative(Notation.MayInherit);
    }
    public final Integer getIndexCloseRelative(boolean mayInherit){
        if (mayInherit){
            Integer indexCloseRelative = this.indexCloseRelative;
            if (null == indexCloseRelative && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                return inheritFrom.getIndexCloseRelative(Notation.MayInherit);
            }
            return indexCloseRelative;
        }
        else
            return this.indexCloseRelative;
    }
    public final boolean setIndexCloseRelative(Integer indexCloseRelative, boolean withInheritance){
        if (IsNotEqual(this.indexCloseRelative,this.getIndexCloseRelative(withInheritance))){
            this.fieldStatistics().markDirty(TemplateNode.Field.IndexCloseRelative);
            this.indexCloseRelative = indexCloseRelative;
            return true;
        }
        else
            return false;
    }
    public final boolean setIndexCloseRelative(Integer indexCloseRelative){
        if (IsNotEqual(this.indexCloseRelative,indexCloseRelative)){
            this.fieldStatistics().markDirty(TemplateNode.Field.IndexCloseRelative);
            this.indexCloseRelative = indexCloseRelative;
            return true;
        }
        else
            return false;
    }
    public Json toJsonNodeType(){
        String nodeType = this.getNodeType();
        return Json.Wrap( nodeType);
    }
    public boolean fromJsonNodeType(Json json){
        if (null == json)
            return false;
        else
            return this.setNodeType((String)json.getValue(String.class));
    }
    public Json toJsonOffset(){
        Integer offset = this.getOffset();
        return Json.Wrap( offset);
    }
    public boolean fromJsonOffset(Json json){
        if (null == json)
            return false;
        else
            return this.setOffset((Integer)json.getValue(Integer.class));
    }
    public final boolean setOffset(Number offset){
        if (IsNotEqual(this.offset,offset)){
            this.fieldStatistics().markDirty(TemplateNode.Field.Offset);
            if (offset instanceof Integer)
                this.offset = (Integer)offset;
            else
                this.offset = new Integer( offset.intValue());
            return true;
        }
        else
            return false;
    }
    public Json toJsonLineNumber(){
        Integer lineNumber = this.getLineNumber();
        return Json.Wrap( lineNumber);
    }
    public boolean fromJsonLineNumber(Json json){
        if (null == json)
            return false;
        else
            return this.setLineNumber((Integer)json.getValue(Integer.class));
    }
    public final boolean setLineNumber(Number lineNumber){
        if (IsNotEqual(this.lineNumber,lineNumber)){
            this.fieldStatistics().markDirty(TemplateNode.Field.LineNumber);
            if (lineNumber instanceof Integer)
                this.lineNumber = (Integer)lineNumber;
            else
                this.lineNumber = new Integer( lineNumber.intValue());
            return true;
        }
        else
            return false;
    }
    public Json toJsonIndex(){
        Integer index = this.getIndex();
        return Json.Wrap( index);
    }
    public boolean fromJsonIndex(Json json){
        if (null == json)
            return false;
        else
            return this.setIndex((Integer)json.getValue(Integer.class));
    }
    public final boolean setIndex(Number index){
        if (IsNotEqual(this.index,index)){
            this.fieldStatistics().markDirty(TemplateNode.Field.Index);
            if (index instanceof Integer)
                this.index = (Integer)index;
            else
                this.index = new Integer( index.intValue());
            return true;
        }
        else
            return false;
    }
    public Json toJsonNodeContent(){
        Text nodeContent = this.getNodeContent();
        return Json.Wrap( nodeContent);
    }
    public boolean fromJsonNodeContent(Json json){
        if (null == json)
            return false;
        else
            return this.setNodeContent((Text)json.getValue(Text.class));
    }
    public Json toJsonIndexCloseRelative(){
        Integer indexCloseRelative = this.getIndexCloseRelative();
        return Json.Wrap( indexCloseRelative);
    }
    public boolean fromJsonIndexCloseRelative(Json json){
        if (null == json)
            return false;
        else
            return this.setIndexCloseRelative((Integer)json.getValue(Integer.class));
    }
    public final boolean setIndexCloseRelative(Number indexCloseRelative){
        if (IsNotEqual(this.indexCloseRelative,indexCloseRelative)){
            this.fieldStatistics().markDirty(TemplateNode.Field.IndexCloseRelative);
            if (indexCloseRelative instanceof Integer)
                this.indexCloseRelative = (Integer)indexCloseRelative;
            else
                this.indexCloseRelative = new Integer( indexCloseRelative.intValue());
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
    public final gap.data.List<gap.data.Field> getClassFields(){
        gap.data.List re = new TemplateNode.Field.List(Field.values());
        /*
         * Compiler has a type astigmatism (parameterized interface gap.data.Field)
         */
        return (gap.data.List<gap.data.Field>)re;
    }
    public final gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public Json toJson(){
        Json json = new json.ObjectJson();
        Json nodeType = this.toJsonNodeType();
        if (null != nodeType)
            json.set("nodeType",nodeType);
        Json offset = this.toJsonOffset();
        if (null != offset)
            json.set("offset",offset);
        Json lineNumber = this.toJsonLineNumber();
        if (null != lineNumber)
            json.set("lineNumber",lineNumber);
        Json index = this.toJsonIndex();
        if (null != index)
            json.set("index",index);
        Json nodeContent = this.toJsonNodeContent();
        if (null != nodeContent)
            json.set("nodeContent",nodeContent);
        Json indexCloseRelative = this.toJsonIndexCloseRelative();
        if (null != indexCloseRelative)
            json.set("indexCloseRelative",indexCloseRelative);
        return json;
    }
    public boolean fromJson(Json json){
        boolean modified = false;
        modified = (this.fromJsonLineNumber(json.at("lineNumber")) || modified);
        modified = (this.fromJsonIndex(json.at("index")) || modified);
        modified = (this.fromJsonNodeContent(json.at("nodeContent")) || modified);
        modified = (this.fromJsonIndexCloseRelative(json.at("indexCloseRelative")) || modified);
        return modified;
    }
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        String lineNumberRequest = req.getParameter("lineNumber");
        if (null != lineNumberRequest && 0 < lineNumberRequest.length()){
            try {
                Integer lineNumber = gap.Strings.IntegerFromString(lineNumberRequest);
                if (this.setLineNumber(lineNumber)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"lineNumber",lineNumberRequest,exc);
            }
        }
        String indexRequest = req.getParameter("index");
        if (null != indexRequest && 0 < indexRequest.length()){
            try {
                Integer index = gap.Strings.IntegerFromString(indexRequest);
                if (this.setIndex(index)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"index",indexRequest,exc);
            }
        }
        String nodeContentRequest = req.getParameter("nodeContent");
        if (null != nodeContentRequest && 0 < nodeContentRequest.length()){
            try {
                Text nodeContent = gap.Strings.TextFromString(nodeContentRequest);
                if (this.setNodeContent(nodeContent)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"nodeContent",nodeContentRequest,exc);
            }
        }
        String indexCloseRelativeRequest = req.getParameter("indexCloseRelative");
        if (null != indexCloseRelativeRequest && 0 < indexCloseRelativeRequest.length()){
            try {
                Integer indexCloseRelative = gap.Strings.IntegerFromString(indexCloseRelativeRequest);
                if (this.setIndexCloseRelative(indexCloseRelative)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"indexCloseRelative",indexCloseRelativeRequest,exc);
            }
        }
        return change;
    }
    public final boolean updateFrom(BigTable proto){
        return this.updateFrom( (TemplateNode)proto);
    }
    public final boolean updateFrom(TemplateNode proto){
        boolean mayInherit = (!this.hasInheritFromKey());
        boolean change = false;
        Integer lineNumber = proto.getLineNumber(mayInherit);
        if (null != lineNumber && this.setLineNumber(lineNumber)){
            change = true;
        }
        Integer index = proto.getIndex(mayInherit);
        if (null != index && this.setIndex(index)){
            change = true;
        }
        Text nodeContent = proto.getNodeContent(mayInherit);
        if (null != nodeContent && this.setNodeContent(nodeContent)){
            change = true;
        }
        Integer indexCloseRelative = proto.getIndexCloseRelative(mayInherit);
        if (null != indexCloseRelative && this.setIndexCloseRelative(indexCloseRelative)){
            change = true;
        }
        return change;
    }
    public java.io.Serializable valueStorage(gap.data.Field field){

        return Field.Storage( (Field)field, (TemplateNode)this);
    }
    public void defineStorage(gap.data.Field field, java.io.Serializable value){

        Field.Storage( (Field)field, (TemplateNode)this, value);
    }
    public final TemplateNode markClean(){

        this.fieldStatistics().markClean();
        return (TemplateNode)this;
    }
    public final TemplateNode markDirty(){

        this.fieldStatistics().markDirty();
        return (TemplateNode)this;
    }
    public final TemplateNode markDirty(gap.data.Field field){

        this.fieldStatistics().markDirty(field);
        return (TemplateNode)this;
    }
    public final TemplateNode markDirty(java.io.Serializable instance){
        if (instance == this.nodeType){
            gap.data.Field field = TemplateNode.Field.NodeType;
            return this.markDirty(field);
        }
        else if (instance == this.offset){
            gap.data.Field field = TemplateNode.Field.Offset;
            return this.markDirty(field);
        }
        else if (instance == this.lineNumber){
            gap.data.Field field = TemplateNode.Field.LineNumber;
            return this.markDirty(field);
        }
        else if (instance == this.index){
            gap.data.Field field = TemplateNode.Field.Index;
            return this.markDirty(field);
        }
        else if (instance == this.nodeContent){
            gap.data.Field field = TemplateNode.Field.NodeContent;
            return this.markDirty(field);
        }
        else if (instance == this.indexCloseRelative){
            gap.data.Field field = TemplateNode.Field.IndexCloseRelative;
            return this.markDirty(field);
        }
        else if (null != instance)
            throw new IllegalArgumentException(instance.getClass().getName());
        else
            throw new IllegalArgumentException();
    }
    public final Iterable<gap.data.Field> listClean(){

        return this.fieldStatistics().listClean();
    }
    public final Iterable<gap.data.Field> listDirty(){

        return this.fieldStatistics().listDirty();
    }
    public final boolean isClean(){

        return this.fieldStatistics().isClean();
    }
    public final boolean isDirty(){

        return this.fieldStatistics().isDirty();
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return TemplateNode.ClassDescriptorFor();
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorForParent(){
        return TemplateNode.ClassDescriptorForParent();
    }
    /*
     * Template Data Dictionary
     */
    public boolean hasVariable(TemplateName name){
        Field field = TemplateNode.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0)){
                    String id = this.getId();
                    return (null != id);
                }
                else
                    return false;
            case NodeType:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasNodeType(true);
                }
            case Offset:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasOffset(true);
                }
            case LineNumber:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasLineNumber(true);
                }
            case Index:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasIndex(true);
                }
            case NodeContent:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasNodeContent(true);
                }
            case IndexCloseRelative:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasIndexCloseRelative(true);
                }
            default:
                break;
            }
        }
        return super.hasVariable(name);
    }
    public String getVariable(TemplateName name){
        Field field = TemplateNode.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case Id:
                if (name.is(0))
                    return this.getId();
                else
                    return null;
            case NodeType:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getNodeType(true);
            case Offset:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.IntegerToString(this.getOffset(true));
            case LineNumber:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.IntegerToString(this.getLineNumber(true));
            case Index:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.IntegerToString(this.getIndex(true));
            case NodeContent:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.TextToString(this.getNodeContent(true));
            case IndexCloseRelative:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.IntegerToString(this.getIndexCloseRelative(true));
            default:
                break;
            }
        }
        return super.getVariable(name);
    }
    public void setVariable(TemplateName name, String value){
        Field field = TemplateNode.Field.For(name.getTerm());
        if (null != field){
            if (name.has(1)){
                switch (field){
                case Id:
                    throw new UnsupportedOperationException(field.name());
                case NodeType:
                    throw new IllegalStateException(field.name());
                case Offset:
                    throw new IllegalStateException(field.name());
                case LineNumber:
                    throw new IllegalStateException(field.name());
                case Index:
                    throw new IllegalStateException(field.name());
                case NodeContent:
                    throw new IllegalStateException(field.name());
                case IndexCloseRelative:
                    throw new IllegalStateException(field.name());
                default:
                    throw new IllegalStateException(field.name());
                }
            }
            else
                Field.Set(field,((TemplateNode)this),value);
        }
        else {
            super.setVariable(name,value);
        }
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){
        Field field = TemplateNode.Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case NodeType:
                return null;
            case Offset:
                return null;
            case LineNumber:
                return null;
            case Index:
                return null;
            case NodeContent:
                return null;
            case IndexCloseRelative:
                return null;
            default:
                throw new IllegalStateException(field.name());
            }
        }
        else {
            return super.getSection(name);
        }
    }
    public TemplateNode clone(){
        return (TemplateNode)super.clone();
    }
    public String pathto(){
        return PathTo(this.getId());
    }
    public String pathto(String subpath){
        StringBuilder string = new StringBuilder();
        string.append(this.getId());
        if (null != subpath){
            if (0 == subpath.length() || '/' != subpath.charAt(0))
                string.append('/');
            string.append(subpath);
        }
        return PathTo(string.toString());
    }
}
