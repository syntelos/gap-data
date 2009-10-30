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

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

/**
 * Generated data bean
 */
@Generated(value={"gap.service.OD","bean"},date="2009-10-30T07:25:16.477Z")
public final class TemplateNode
    extends gap.data.BigTable
    implements DataInheritance<TemplateNode>
{

    private final static long serialVersionUID = 1;

    public final static Kind KIND = Kind.Create("TemplateNode","gap.data","TemplateNode");

    public final static String ClassName = "TemplateNode";

    public final static String DefaultSortBy = "offset";

    public final static gap.service.od.ClassDescriptor ClassDescriptorFor(){
        return ClassDescriptorFor(TemplateNode.class);
    }
    public final static gap.service.od.ClassDescriptor ClassDescriptorForParent(){
        return ClassDescriptorFor(Template.class);
    }




    public final static Key KeyShortIdFor(Key ancestor, String nodeType, Integer lineNumber, Text nodeContent){
        String id = IdFor(ancestor, nodeType,  lineNumber,  nodeContent);
        return KeyShortFor(ancestor,id);
    }


    public final static String IdFor(Key ancestor, String nodeType, Integer lineNumber, Text nodeContent){
        if (ancestor.isComplete() && null != nodeType && null != lineNumber && null != nodeContent){
            String nodeTypeString = nodeType;
            String lineNumberString = gap.Strings.IntegerToString(lineNumber);
            String nodeContentString = gap.Strings.TextToString(nodeContent);
            return gap.data.Hash.For(ToString(ancestor)+'/'+nodeTypeString+'/'+lineNumberString+'/'+nodeContentString);
        }
        else
            throw new IllegalArgumentException();
    }


    public final static TemplateNode ForShortNodeTypeLineNumberNodeContent(Key ancestor, String nodeType, Integer lineNumber, Text nodeContent){
        if (null != nodeType && null != lineNumber && null != nodeContent){
            Key key = KeyShortIdFor(ancestor, nodeType, lineNumber, nodeContent);
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


    public final static TemplateNode GetCreateShort(Key ancestor, String nodeType, Integer lineNumber, Text nodeContent){
        TemplateNode templateNode = ForShortNodeTypeLineNumberNodeContent(ancestor, nodeType, lineNumber, nodeContent);
        if (null == templateNode){
            templateNode = new TemplateNode(ancestor, nodeType, lineNumber, nodeContent);
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
    public final static void Delete(TemplateNode instance){
        if (null != instance){
            Key key = instance.getKey();
            gap.data.Store.DeleteCollection(KIND,new Query(key));
            gap.data.Store.Delete(key);
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
    public final static TemplateNode Query1(Query query){
        if (null != query)
            return (TemplateNode)gap.data.Store.Query1(query);
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
     * Persistent fields' binding for {@link TemplateNode}
     */
    public static enum Field
        implements gap.data.Field<Field>
    {
        InheritFromKey("inheritFromKey"),
        ParentKey("parentKey"),
        Key("key"),
        Id("id"),
        NodeType("nodeType"),
        LineNumber("lineNumber"),
        NodeContent("nodeContent"),
        Offset("offset"),
        OffsetCloseRelative("offsetCloseRelative");


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
        public static Object Get(Field field, TemplateNode instance, boolean mayInherit){
            switch(field){
            case InheritFromKey:
                return instance.getInheritFromKey();
            case ParentKey:
                return instance.getParentKey();
            case Key:
                return instance.getKey(mayInherit);
            case Id:
                return instance.getId(mayInherit);
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
        public static boolean Set(Field field, TemplateNode instance, Object value){
            switch(field){
            case InheritFromKey:
                return instance.setInheritFromKey( (Key)value);
            case ParentKey:
                return instance.setParentKey( (Key)value);
            case Key:
                return instance.setKey( (Key)value);
            case Id:
                return instance.setId( (String)value);
            case NodeType:
                return instance.setNodeType( (String)value);
            case LineNumber:
                return instance.setLineNumber( (Integer)value);
            case NodeContent:
                return instance.setNodeContent( (Text)value);
            case Offset:
                return instance.setOffset( (Integer)value);
            case OffsetCloseRelative:
                return instance.setOffsetCloseRelative( (Integer)value);
            default:
                throw new IllegalArgumentException(field.toString()+" in TemplateNode");
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

    private volatile transient TemplateNode inheritFrom;


    private volatile Key key;    
    private volatile String id;    // *unique
    private volatile String nodeType;    // *hash-unique
    private volatile Integer lineNumber;    // *hash-unique
    private volatile Text nodeContent;    // *hash-unique
    private volatile Integer offset;    
    private volatile Integer offsetCloseRelative;    






    private volatile Key parentKey;
    private volatile transient Template parent;


    public TemplateNode() {
        super();
    }
    public TemplateNode(Key ancestor, String nodeType, Integer lineNumber, Text nodeContent) {
        super();
        this.setNodeType(nodeType);
        this.setLineNumber(lineNumber);
        this.setNodeContent(nodeContent);
        this.parentKey = ancestor;
        String id = IdFor(ancestor,  nodeType, lineNumber, nodeContent);
        this.setId(id);
        Key key = KeyShortFor(ancestor,id);
        this.setKey(key);
    }



    public void onread(){

    }
    public void onwrite(){

    }
    public void destroy(){
        this.inheritFrom = null;
        this.datastoreEntity = null;
        this.key = null;
        this.id = null;
        this.nodeType = null;
        this.lineNumber = null;
        this.nodeContent = null;
        this.offset = null;
        this.offsetCloseRelative = null;
        this.parent = null;
    }
    public boolean hasInheritFrom(){
        return (null != this.inheritFrom || null != this.inheritFromKey);
    }
    public boolean hasNotInheritFrom(){
        return (null == this.inheritFrom && null == this.inheritFromKey);
    }
    public TemplateNode getInheritFrom(){
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
    public boolean setInheritFrom(TemplateNode ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }
    public boolean inheritFrom(TemplateNode ancestor){
        if (IsNotEqual(this.inheritFrom,ancestor)){
            this.inheritFrom = ancestor;
            if (null != ancestor)
                this.inheritFromKey = ancestor.getKey();
            return true;
        }
        else
            return false;
    }

    public boolean hasParentKey(){
        return (null != this.parentKey);
    }
    public boolean hasNotParentKey(){
        return (null == this.parentKey);
    }
    public Key getParentKey(){
        return this.parentKey;
    }
    public boolean setParentKey(Key ancestor){
        if (IsNotEqual(this.parentKey,ancestor)){
            this.parentKey = ancestor;
            return true;
        }
        else
            return false;
    }
    public boolean hasParent(){
        return (null != this.parent || null != this.parentKey);
    }
    public boolean hasNotParent(){
        return (null == this.parent && null == this.parentKey);
    }
    public Template getParent(){
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
    public boolean setParent(Template ancestor){
        if (IsNotEqual(this.parent,ancestor)){
            this.parent = ancestor;
            if (null != ancestor)
                this.parentKey = ancestor.getClassFieldKeyValue();
            return true;
        }
        else
            return false;
    }


    public boolean hasKey(boolean mayInherit){
        return (null != this.getKey(mayInherit));
    }
    public boolean hasNotKey(boolean mayInherit){
        return (null == this.getKey(mayInherit));
    }
    public boolean dropKey(){
        if (null != this.key){
            this.key = null;
            return true;
        }
        else
            return false;
    }
    public Key getKey(){
        return this.key;
    }
    public Key getKey(boolean ignore){
        return this.key;
    }
    public boolean setKey(Key key){
        if (IsNotEqual(this.key,key)){
            this.key = key;
            return true;
        }
        else
            return false;
    }

    public boolean hasId(boolean mayInherit){
        return (null != this.getId(mayInherit));
    }
    public boolean hasNotId(boolean mayInherit){
        return (null == this.getId(mayInherit));
    }
    public boolean dropId(){
        if (null != this.id){
            this.id = null;
            return true;
        }
        else
            return false;
    }
    public String getId(){
        return this.id;
    }
    public String getId(boolean ignore){
        return this.id;
    }
    public boolean setId(String id){
        if (IsNotEqual(this.id,id)){
            this.id = id;
            return true;
        }
        else
            return false;
    }

    public boolean hasNodeType(boolean mayInherit){
        return (null != this.getNodeType(mayInherit));
    }
    public boolean hasNotNodeType(boolean mayInherit){
        return (null == this.getNodeType(mayInherit));
    }
    public boolean dropNodeType(){
        if (null != this.nodeType){
            this.nodeType = null;
            return true;
        }
        else
            return false;
    }
    public String getNodeType(){
        return this.nodeType;
    }
    public String getNodeType(boolean ignore){
        return this.nodeType;
    }
    public boolean setNodeType(String nodeType){
        if (IsNotEqual(this.nodeType,nodeType)){
            this.nodeType = nodeType;
            return true;
        }
        else
            return false;
    }

    public boolean hasLineNumber(boolean mayInherit){
        return (null != this.getLineNumber(mayInherit));
    }
    public boolean hasNotLineNumber(boolean mayInherit){
        return (null == this.getLineNumber(mayInherit));
    }
    public boolean dropLineNumber(){
        if (null != this.lineNumber){
            this.lineNumber = null;
            return true;
        }
        else
            return false;
    }
    public Integer getLineNumber(){
        return this.lineNumber;
    }
    public Integer getLineNumber(boolean ignore){
        return this.lineNumber;
    }
    public boolean setLineNumber(Integer lineNumber){
        if (IsNotEqual(this.lineNumber,lineNumber)){
            this.lineNumber = lineNumber;
            return true;
        }
        else
            return false;
    }

    public boolean hasNodeContent(boolean mayInherit){
        return (null != this.getNodeContent(mayInherit));
    }
    public boolean hasNotNodeContent(boolean mayInherit){
        return (null == this.getNodeContent(mayInherit));
    }
    public boolean dropNodeContent(){
        if (null != this.nodeContent){
            this.nodeContent = null;
            return true;
        }
        else
            return false;
    }
    public Text getNodeContent(){
        return this.nodeContent;
    }
    public Text getNodeContent(boolean ignore){
        return this.nodeContent;
    }
    public boolean setNodeContent(Text nodeContent){
        if (IsNotEqual(this.nodeContent,nodeContent)){
            this.nodeContent = nodeContent;
            return true;
        }
        else
            return false;
    }

    public boolean hasOffset(boolean mayInherit){
        return (null != this.getOffset(mayInherit));
    }
    public boolean hasNotOffset(boolean mayInherit){
        return (null == this.getOffset(mayInherit));
    }
    public boolean dropOffset(){
        if (null != this.offset){
            this.offset = null;
            return true;
        }
        else
            return false;
    }
    public Integer getOffset(boolean mayInherit){
        if (mayInherit){
            Integer offset = this.offset;
            if (null == offset && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getOffset(MayInherit);
            }
            return offset;
        }
        else
            return this.offset;
    }
    public boolean setOffset(Integer offset, boolean withInheritance){
        if (IsNotEqual(this.offset,this.getOffset(withInheritance))){
            this.offset = offset;
            return true;
        }
        else
            return false;
    }
    public boolean setOffset(Integer offset){
        if (IsNotEqual(this.offset,offset)){
            this.offset = offset;
            return true;
        }
        else
            return false;
    }

    public boolean hasOffsetCloseRelative(boolean mayInherit){
        return (null != this.getOffsetCloseRelative(mayInherit));
    }
    public boolean hasNotOffsetCloseRelative(boolean mayInherit){
        return (null == this.getOffsetCloseRelative(mayInherit));
    }
    public boolean dropOffsetCloseRelative(){
        if (null != this.offsetCloseRelative){
            this.offsetCloseRelative = null;
            return true;
        }
        else
            return false;
    }
    public Integer getOffsetCloseRelative(boolean mayInherit){
        if (mayInherit){
            Integer offsetCloseRelative = this.offsetCloseRelative;
            if (null == offsetCloseRelative && this.hasInheritFrom()){
                TemplateNode inheritFrom = this.getInheritFrom();
                if (null != inheritFrom)
                    return inheritFrom.getOffsetCloseRelative(MayInherit);
            }
            return offsetCloseRelative;
        }
        else
            return this.offsetCloseRelative;
    }
    public boolean setOffsetCloseRelative(Integer offsetCloseRelative, boolean withInheritance){
        if (IsNotEqual(this.offsetCloseRelative,this.getOffsetCloseRelative(withInheritance))){
            this.offsetCloseRelative = offsetCloseRelative;
            return true;
        }
        else
            return false;
    }
    public boolean setOffsetCloseRelative(Integer offsetCloseRelative){
        if (IsNotEqual(this.offsetCloseRelative,offsetCloseRelative)){
            this.offsetCloseRelative = offsetCloseRelative;
            return true;
        }
        else
            return false;
    }



    /*
     * Data binding supports
     */
    public Kind getClassKind(){
        return KIND;
    }
    public String getClassName(){
        return ClassName;
    }
    public String getClassFieldUnique(){
        return "id";
    }
    public String getClassFieldKeyName(){
        return "key";
    }
    public List<gap.data.Field> getClassFields(){
        return (new gap.data.Field.List(Field.values()));
    }
    public gap.data.Field getClassFieldByName(String name){
        return Field.getField(name);
    }
    public java.io.Serializable valueOf(gap.data.Field field, boolean mayInherit){
        return (java.io.Serializable)Field.Get((Field)field,this,mayInherit);
    }
    public void define(gap.data.Field field, java.io.Serializable value){
        Field.Set((Field)field,this,value);
    }

    public boolean updateFrom(Request req) throws ValidationError {
        boolean change = false;
        return change;
    }
    public boolean updateFrom(BigTable proto){
        return this.updateFrom( (TemplateNode)proto);
    }
    public boolean updateFrom(TemplateNode proto){
        boolean change = false;
        return change;
    }
    public void drop(){
        Delete(this);
    }
    public void clean(){
        Clean(this);
    }
    public void save(){
        Save(this);
    }
    public void store(){
        Store(this);
    }
    public gap.service.od.ClassDescriptor getClassDescriptorFor(){
        return ClassDescriptorFor(this.getClass());
    }
    public gap.service.od.ClassDescriptor getClassDescriptorForParent(){
        return ClassDescriptorForParent();
    }
}
