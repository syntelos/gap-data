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
 * @see TemplateNode
 */
@Generated(value={"gap.service.OD","BeanData.java"},date="2011-12-22T17:30:21.763Z")
public abstract class TemplateNodeData
    extends gap.data.BigTable
    implements DataInheritance<TemplateNode>
{

    private final static long serialVersionUID = 4;

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
    public final static Key KeyShortIdFor(Key ancestor, String nodeType, Integer lineNumber){
        String id = IdFor( nodeType,  lineNumber);
        return KeyShortFor(ancestor,id);
    }
    /**
     * Used by gap.data.Kind
     *
     * Calls {@link #KeyShortIdFor}
     */
    public final static Key KeyIdFor(Object... args){
        return KeyShortIdFor( (Key)args[0], (String)args[1], (Integer)args[2]);
    }
    /**
     * Used by setId
     *
     * Calls {@link #KeyShortFor}
     */
    public final static Key KeyFor(Object... args){
        return KeyShortFor( (Key)args[0], (String)args[1]);
    }
    /**
     * Identifier for unique fields
     */
    public final static String IdFor(String nodeType, Integer lineNumber){
        if (null != nodeType && null != lineNumber){
            String nodeTypeString = nodeType;
            String lineNumberString = gap.Strings.IntegerToString(lineNumber);
            return gap.data.Hash.For(nodeTypeString+'/'+lineNumberString);
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup from parent key
     */
    public final static TemplateNode ForShortNodeTypeLineNumber(Key ancestor, String nodeType, Integer lineNumber){
        if (null != nodeType && null != lineNumber){
            Key key = KeyShortIdFor(ancestor, nodeType, lineNumber);
            TemplateNode instance = (TemplateNode)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (TemplateNode)gap.data.Store.Query1(q);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Instance lookup or create from parent key
     */
    public final static TemplateNode GetCreateShort(Key ancestor, String nodeType, Integer lineNumber){
        TemplateNode templateNode = ForShortNodeTypeLineNumber(ancestor, nodeType, lineNumber);
        if (null == templateNode){
            templateNode = new TemplateNode(ancestor, nodeType, lineNumber);
            templateNode = (TemplateNode)gap.data.Store.Put(templateNode);
        }
        return templateNode;
    }


    public final static Key KeyShortFor(Key ancestor, String id){
        return KeyFactory.createKey(ancestor,KIND.getName(),id);
    }


    public final static TemplateNode ForShortId(Key ancestor, String id){
        if (null != ancestor && ancestor.isComplete() && null != id){
            Key key = KeyShortFor(ancestor,id);
            TemplateNode instance = (TemplateNode)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (TemplateNode)gap.data.Store.Query1(q);
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
            TemplateNode instance = (TemplateNode)gap.data.Store.Get(key);
            if (null != instance)
                return instance;
            else {
                Query q = CreateQueryFor(key);
                return (TemplateNode)gap.data.Store.Query1(q);
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
     * Drop the instance from memcache and datastore.
     */
    public final static void Delete(TemplateNode instance){
        if (null != instance){

            gap.data.Store.Delete(instance.getKey());
        }
    }
    /**
     * Drop the instance from memcache, exclusively.
     */
    public final static void Clean(TemplateNode instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.Clean(key);
        }
    }
    /**
     * Store the instance.
     */
    public final static void Save(TemplateNode instance){
        if (null != instance){
            gap.data.Store.Put(instance);
        }
    }
    /**
     * Write the instance to store.
     */
    public final static void Store(TemplateNode instance){
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
    public final static Query CreateQueryFor(Key ancestor, Filter filter){
        Query query = new Query(KIND.getName(),ancestor);
        return filter.update(query);
    }
    public final static TemplateNode Query1(Query query){
        if (null != query)
            return (TemplateNode)gap.data.Store.Query1(query);
        else
            throw new IllegalArgumentException();
    }
    public final static BigTableIterator<TemplateNode> QueryN(Query query, Page page){
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
        LineNumber("lineNumber",Type.Primitive),
        NodeContent("nodeContent",Type.Primitive),
        Offset("offset",Type.Primitive),
        OffsetCloseRelative("offsetCloseRelative",Type.Primitive);

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
            case LineNumber:
                return instance.getLineNumber(mayInherit);
            case NodeContent:
                return instance.getNodeContent(mayInherit);
            case Offset:
                return instance.getOffset(mayInherit);
            case OffsetCloseRelative:
                return instance.getOffsetCloseRelative(mayInherit);
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
            case LineNumber:
                return instance.setLineNumber(gap.Objects.IntegerFromObject(value));
            case NodeContent:
                return instance.setNodeContent(gap.Objects.TextFromObject(value));
            case Offset:
                return instance.setOffset(gap.Objects.IntegerFromObject(value));
            case OffsetCloseRelative:
                return instance.setOffsetCloseRelative(gap.Objects.IntegerFromObject(value));
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
            case LineNumber:{
                return instance.getLineNumber(MayNotInherit);
            }
            case NodeContent:
                return instance.getNodeContent(MayNotInherit);
            case Offset:{
                return instance.getOffset(MayNotInherit);
            }
            case OffsetCloseRelative:{
                return instance.getOffsetCloseRelative(MayNotInherit);
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
            case LineNumber:{

                instance.setLineNumber( (Number)value);
                return;
            }
            case NodeContent:
                instance.setNodeContent( (Text)value);
                return;
            case Offset:{

                instance.setOffset( (Number)value);
                return;
            }
            case OffsetCloseRelative:{

                instance.setOffsetCloseRelative( (Number)value);
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

    private transient TemplateNode inheritFrom;


    private String nodeType;
    private Integer lineNumber;
    private Text nodeContent;
    private Integer offset;
    private Integer offsetCloseRelative;


    private Key parentKey;
    private transient Template parent;


    protected TemplateNodeData() {
        super();
    }
    protected TemplateNodeData(Key ancestor, String nodeType, Integer lineNumber) {
        super();
        this.setNodeType(nodeType);
        this.setLineNumber(lineNumber);
        this.parentKey = ancestor;
        {
            final String id = IdFor(nodeType, lineNumber);
            final Key key = KeyShortFor(ancestor,id);
            this.setKey(key);
        }
    }


    public void destroy(){
        this.inheritFrom = null;
        this.nodeType = null;
        this.lineNumber = null;
        this.nodeContent = null;
        this.offset = null;
        this.offsetCloseRelative = null;
        this.parent = null;
    }
    public final String getId(){

        String id = IdFor(KIND.name, this.key);
        if (null != id)
            return id;
        else
            return IdFor(this.nodeType, this.lineNumber);
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
            this.key = KeyShortFor(this.getParentKey(),id);
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

            this.nodeType = nodeType;
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

            this.lineNumber = null;
            return true;
        }
        else
            return false;
    }
    public final Integer getLineNumber(){
        return this.lineNumber;
    }
    public final Integer getLineNumber(boolean mayInherit){
        return this.getLineNumber();
    }
    public final boolean setLineNumber(Integer lineNumber){
        if (IsNotEqual(this.lineNumber,lineNumber)){

            this.lineNumber = lineNumber;
            return true;
        }
        else
            return false;
    }
    public final boolean setLineNumber(Number lineNumber){
        if (IsNotEqual(this.lineNumber,lineNumber)){

            if (lineNumber instanceof Integer)
                this.lineNumber = (Integer)lineNumber;
            else
                this.lineNumber = new Integer( lineNumber.intValue());
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

            this.nodeContent = nodeContent;
            return true;
        }
        else
            return false;
    }
    public final boolean setNodeContent(Text nodeContent){
        if (IsNotEqual(this.nodeContent,nodeContent)){

            this.nodeContent = nodeContent;
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

            this.offset = null;
            return true;
        }
        else
            return false;
    }
    public final Integer getOffset(){
        return this.getOffset(Notation.MayInherit);
    }
    public final Integer getOffset(boolean mayInherit){
        if (mayInherit){
            Integer offset = this.offset;
            if (null == offset && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                return inheritFrom.getOffset(Notation.MayInherit);
            }
            return offset;
        }
        else
            return this.offset;
    }
    public final boolean setOffset(Integer offset, boolean withInheritance){
        if (IsNotEqual(this.offset,this.getOffset(withInheritance))){

            this.offset = offset;
            return true;
        }
        else
            return false;
    }
    public final boolean setOffset(Integer offset){
        if (IsNotEqual(this.offset,offset)){

            this.offset = offset;
            return true;
        }
        else
            return false;
    }
    public final boolean setOffset(Number offset){
        if (IsNotEqual(this.offset,offset)){

            if (offset instanceof Integer)
                this.offset = (Integer)offset;
            else
                this.offset = new Integer( offset.intValue());
            return true;
        }
        else
            return false;
    }
    public final boolean hasOffsetCloseRelative(boolean mayInherit){
        return (null != this.getOffsetCloseRelative(mayInherit));
    }
    public final boolean hasNotOffsetCloseRelative(boolean mayInherit){
        return (null == this.getOffsetCloseRelative(mayInherit));
    }
    public final boolean dropOffsetCloseRelative(){
        if (null != this.offsetCloseRelative){

            this.offsetCloseRelative = null;
            return true;
        }
        else
            return false;
    }
    public final Integer getOffsetCloseRelative(){
        return this.getOffsetCloseRelative(Notation.MayInherit);
    }
    public final Integer getOffsetCloseRelative(boolean mayInherit){
        if (mayInherit){
            Integer offsetCloseRelative = this.offsetCloseRelative;
            if (null == offsetCloseRelative && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                return inheritFrom.getOffsetCloseRelative(Notation.MayInherit);
            }
            return offsetCloseRelative;
        }
        else
            return this.offsetCloseRelative;
    }
    public final boolean setOffsetCloseRelative(Integer offsetCloseRelative, boolean withInheritance){
        if (IsNotEqual(this.offsetCloseRelative,this.getOffsetCloseRelative(withInheritance))){

            this.offsetCloseRelative = offsetCloseRelative;
            return true;
        }
        else
            return false;
    }
    public final boolean setOffsetCloseRelative(Integer offsetCloseRelative){
        if (IsNotEqual(this.offsetCloseRelative,offsetCloseRelative)){

            this.offsetCloseRelative = offsetCloseRelative;
            return true;
        }
        else
            return false;
    }
    public final boolean setOffsetCloseRelative(Number offsetCloseRelative){
        if (IsNotEqual(this.offsetCloseRelative,offsetCloseRelative)){

            if (offsetCloseRelative instanceof Integer)
                this.offsetCloseRelative = (Integer)offsetCloseRelative;
            else
                this.offsetCloseRelative = new Integer( offsetCloseRelative.intValue());
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
    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
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
        String offsetRequest = req.getParameter("offset");
        if (null != offsetRequest && 0 < offsetRequest.length()){
            try {
                Integer offset = gap.Strings.IntegerFromString(offsetRequest);
                if (this.setOffset(offset)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"offset",offsetRequest,exc);
            }
        }
        String offsetCloseRelativeRequest = req.getParameter("offsetCloseRelative");
        if (null != offsetCloseRelativeRequest && 0 < offsetCloseRelativeRequest.length()){
            try {
                Integer offsetCloseRelative = gap.Strings.IntegerFromString(offsetCloseRelativeRequest);
                if (this.setOffsetCloseRelative(offsetCloseRelative)){
                    change = true;
                }
            }
            catch (RuntimeException exc){
                throw new ValidationError(ClassName,"offsetCloseRelative",offsetCloseRelativeRequest,exc);
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
        Text nodeContent = proto.getNodeContent(mayInherit);
        if (null != nodeContent && this.setNodeContent(nodeContent)){
            change = true;
        }
        Integer offset = proto.getOffset(mayInherit);
        if (null != offset && this.setOffset(offset)){
            change = true;
        }
        Integer offsetCloseRelative = proto.getOffsetCloseRelative(mayInherit);
        if (null != offsetCloseRelative && this.setOffsetCloseRelative(offsetCloseRelative)){
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
    public final gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor();
    }
    public final gap.service.od.ClassDescriptor getClassDescriptorForParent(){
        return ClassDescriptorForParent();
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
            case NodeType:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasNodeType(true);
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
            case NodeContent:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasNodeContent(true);
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
            case OffsetCloseRelative:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else {
                    /*
                     * Synthesize section for Field (EXISTS)
                     */
                    return this.hasOffsetCloseRelative(true);
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
            case NodeType:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return this.getNodeType(true);
            case LineNumber:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.IntegerToString(this.getLineNumber(true));
            case NodeContent:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.TextToString(this.getNodeContent(true));
            case Offset:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.IntegerToString(this.getOffset(true));
            case OffsetCloseRelative:
                if (name.has(1))
                    throw new IllegalStateException(field.name());
                else
                    return gap.Strings.IntegerToString(this.getOffsetCloseRelative(true));
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
                case NodeType:
                    throw new IllegalStateException(field.name());
                case LineNumber:
                    throw new IllegalStateException(field.name());
                case NodeContent:
                    throw new IllegalStateException(field.name());
                case Offset:
                    throw new IllegalStateException(field.name());
                case OffsetCloseRelative:
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
        Field field = Field.For(name.getTerm());
        if (null != field){
            switch (field){
            case NodeType:
                return null;
            case LineNumber:
                return null;
            case NodeContent:
                return null;
            case Offset:
                return null;
            case OffsetCloseRelative:
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
