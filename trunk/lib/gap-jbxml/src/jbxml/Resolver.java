/*
 * Copyright (c) 2009 John Pritchard, JBXML Project Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jbxml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.util.List;
import java.util.StringTokenizer;

import javax.script.Bindings;

/**
 *
 * @author jdp
 * @since 1.6
 */
public abstract class Resolver
    extends javax.script.ScriptEngineManager
    implements javax.script.ScriptContext
{
    private final static ThreadLocal<Resolver> TL = new ThreadLocal<Resolver>();
    public final static Resolver Get(){
        return TL.get();
    }
    public final static boolean In(){
        return (null != TL.get());
    }


    public static final char URL_PREFIX = '@';
    public static final String URL_PREFIX2 = "url(";
    public static final char RESOURCE_KEY_PREFIX = '%';
    public static final char OBJECT_REFERENCE_PREFIX = '$';

    public final static boolean IsObjectReference(String name){
        if (null != name && 2 < name.length())
            return (OBJECT_REFERENCE_PREFIX == name.charAt(0) && OBJECT_REFERENCE_PREFIX != name.charAt(1));
        else
            return false;
    }

    public static final String ID_ATTRIBUTE = "id";


    /**
     * Upcase the first character in the input string, downcase the
     * remainder of the input string.
     */
    public final static java.lang.String Camel00(java.lang.String name){
        if (null == name)
            return "";
        else {
            int len = name.length();
            if (1 > len)
                return "";
            else if (1 == len)
                return name.toUpperCase();
            else
                return (Character.toUpperCase(name.charAt(0))+name.substring(1).toLowerCase());
        }
    }
    public final static java.lang.String Camel0(java.lang.String name){
        String[] strtok = name.split(".");
        int count = strtok.length;
        if (2 > count)
            return Camel00(name);
        else {
            java.lang.StringBuilder strbuf = new java.lang.StringBuilder();
            for (int cc = 0; cc < count; cc++){
                java.lang.String tok = strtok[cc];
                tok = Camel00(tok);
                if (0 < cc)
                    strbuf.append('.');
                strbuf.append(tok);
            }
            return strbuf.toString();
        }
    }
    /**
     * Convert any string into Class Name "camel" case.  The first
     * character is upcased, and characters following hyphen are
     * upcased and the hyphen dropped.
     */
    public final static java.lang.String ClassCamel(java.lang.String name){
        String[] strtok = name.split("-");
        int count = strtok.length;
        if (2 > count)
            return Camel0(name);
        else {
            java.lang.StringBuilder strbuf = new java.lang.StringBuilder();
            for (int cc = 0; cc < count; cc++){
                java.lang.String tok = strtok[cc];
                tok = Camel0(tok);
                strbuf.append(tok);
            }
            return strbuf.toString();
        }
    }
    /**
     * Convert any string into Field Name "camel" case.  Characters
     * following hyphen are upcased and the hyphen is dropped.
     */
    public final static java.lang.String FieldCamel(java.lang.String name){
        String[] strtok = name.split("-");
        int count = strtok.length;
        if (2 > count)
            return name;
        else {
            java.lang.StringBuilder strbuf = new java.lang.StringBuilder();
            for (int cc = 0; cc < count; cc++){
                java.lang.String tok = strtok[cc];
                if (0 < cc)
                    tok = Camel0(tok);
                strbuf.append(tok);
            }
            return strbuf.toString();
        }
    }
    /**
     * Drop any '/' characters from the tail of the string.  For
     * cleaning XMLNS attribute values before mapping to package
     * names.
     */
    public final static String TailTrim (String string){
        if (null == string)
            return null;
        else {
            int len = string.length();
            if (1 > len)
                return null;
            else {
                int idx = (len-1);
                while (-1 < idx && '/' == string.charAt(idx)){
                    string = string.substring(0,idx);
                    len = string.length();
                    idx = (len-1);
                }
                if (1 > len)
                    return null;
                else
                    return string;
            }
        }
    }
    /**
     * Drop any '/' characters from the head of the string.  For
     * cleaning XMLNS attribute values before mapping to package
     * names.
     */
    public final static String HeadTrim (String string){
        if (null == string)
            return null;
        else {
            int len = string.length();
            if (1 > len)
                return null;
            else {
                while (0 < len && '/' == string.charAt(0)){
                    string = string.substring(1);
                    len = string.length();
                }
                if (1 > len)
                    return null;
                else
                    return string;
            }
        }
    }
    public final static String HeadTailTrim (String string){
        return HeadTrim(TailTrim(string));
    }

    private final static List<Integer> Scopes = new java.util.ArrayList<Integer>();
    static {
        Scopes.add(GLOBAL_SCOPE);
    }


    protected Component.Root root;

    protected Dictionary<String, ?> resources;

    protected URL location;

    private Resolver tlStack;


    protected Resolver(Resolver parent) {
        super();
        if (null != parent){
            this.root = parent.root;
            this.resources = parent.resources;
            this.setBindings(parent.getBindings());
            this.location = parent.location;
        }
        else
            throw new IllegalArgumentException();
    }
    protected Resolver(Component.Root root, Dictionary<String, ?> resources) {
        super();
        if (null != root && null != resources){
            this.root = root;
            this.resources = resources;
            this.setBindings(new Registry());
        }
        else
            throw new IllegalArgumentException();
    }


    public void destroy(){
        this.root = null;
        this.resources = null;
    }
    public final void enter(){
        this.tlStack = TL.get();
        TL.set(this);
    }
    public final void exit(){
        TL.remove();
        Resolver tlStack = this.tlStack;
        if (null != tlStack){
            this.tlStack = null;
            TL.set(tlStack);
        }
    }

    public final void setBindings(Bindings bindings, int scope){
        if (GLOBAL_SCOPE == scope){
            Bindings global = this.getBindings();
            if (global == bindings)
                return;
            else {
                if (!global.isEmpty())
                    bindings.putAll(global);
                this.setBindings(bindings);
            }
        }
        else
            throw new IllegalArgumentException("Unrecognized scope.");
    }
    public final Bindings getBindings(int scope){
        if (GLOBAL_SCOPE == scope)
            return this.getBindings();
        else
            throw new IllegalArgumentException("Unrecognized scope.");
    }
    public final void setAttribute(String name, Object value, int scope){
        if (GLOBAL_SCOPE == scope)
            this.put(name,value);
        else
            throw new IllegalArgumentException("Unrecognized scope.");
    }
    public final Object getAttribute(String name, int scope){
        if (GLOBAL_SCOPE == scope)
            return this.get(name);
        else
            throw new IllegalArgumentException("Unrecognized scope.");
    }
    public final Object removeAttribute(String name, int scope){
        if (GLOBAL_SCOPE == scope){
            Bindings global = this.getBindings();
            return global.remove(name);
        }
        else
            throw new IllegalArgumentException("Unrecognized scope.");
    }
    public final Object getAttribute(String name){
        return this.get(name);
    }
    public final int getAttributesScope(String name){
        if (null != this.get(name))
            return GLOBAL_SCOPE;
        else
            return -1;
    }
    public java.io.Writer getWriter(){
        throw new UnsupportedOperationException();
    }
    public java.io.Writer getErrorWriter(){
        throw new UnsupportedOperationException();
    }
    public void setWriter(java.io.Writer w){
        throw new UnsupportedOperationException();
    }
    public void setErrorWriter(java.io.Writer w){
        throw new UnsupportedOperationException();
    }
    public java.io.Reader getReader(){
        throw new UnsupportedOperationException();
    }
    public void setReader(java.io.Reader r){
        throw new UnsupportedOperationException();
    }
    public final List<Integer> getScopes(){
        return Scopes;
    }
    public final Object getObjectById(String id) {
        return this.get(id);
    }
    public final void setObjectById(String id, Object value){
        this.put(id,value);
    }
    public final Object removeObjectById(String id){
        Bindings global = this.getBindings();
        return global.remove(id);
    }
    public final Object readObject(String resourceName)
        throws IOException
    {
        if (URL_PREFIX == resourceName.charAt(0))
            resourceName = resourceName.substring(1);

        else if (resourceName.startsWith(URL_PREFIX2)){

            resourceName = resourceName.substring(4,resourceName.length()-1).trim();
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL location = classLoader.getResource(resourceName);
        if (null == location){
            location = this.location;
            if (null == location)
                return this.readObject(root.getInputStream(resourceName));
            else {
                location = new URL(location,resourceName);
                return this.readObject(location);
            }
        }
        else
            return this.readObject(location);
    }
    public final Object readObject(URL location) throws IOException {
        if (null == location)
            return null;
        else {
            this.location = location;
            InputStream in = location.openStream();
            try {
                return this.readObject(in);
            }
            finally {
                in.close();
            }
        }
    }
    /**
     * This method does not close the input argument stream.
     */
    public abstract Object readObject(InputStream inputStream)
        throws IOException;


    /**
     * Called in {@link Processor} from {@link Element} to set the
     * root of the bean graph so that Root and Properties are
     * available during construction.
     */
    public void startRoot(Component c){
        if (c instanceof Component.Child)
            ((Component.Child)c).setParent(this.root);
    }
    public final Dictionary<String, ?> getResources() {
        return this.resources;
    }
    public final String packageName(String string){
        String trimmed = HeadTailTrim(string);
        Object lookup = this.resources.get(trimmed);
        if (lookup instanceof String)
            return (String)lookup;
        else {
            try {
                /*
                 * Handle one little case, "ns:string",
                 * and let applications map any others.
                 */
                URI uri = new URI(trimmed);
                String path = uri.getSchemeSpecificPart();
                if (null != path){
                    return path.replace('/','.');
                }
            }
            catch (Exception exc){
            }
            return trimmed.replace('/','.');
        }
    }
    public final String camelName(String string){
        string = ClassCamel(string);
        if (null != string)
            return string.replace('.', '$');
        else
            return null;
    }
    public final String fqcn(String p, String c){
        if (null != p && null != c)
            return p+'.'+c;
        else
            return null;
    }
    public final InputStream getInputStream(String path)
        throws IOException
    {
        return this.root.getInputStream(path);
    }
    public final boolean isId(String name){
        return (ID_ATTRIBUTE.equals(name));
    }
    public final Object resolve(Object value, List collection){
        Class type = TypeComponentOf(collection);
        if (type.isAssignableFrom(value.getClass()))
            return value;
        else if (value instanceof String)
            return this.resolve( (String)value, type);
        else if (null != value)
            throw new IllegalStateException("Unexpected "+value.getClass());
        else
            return null;
    }
    public final Object resolve(Object value, Dictionary collection){
        Class type = TypeComponentOf(collection);
        if (type.isAssignableFrom(value.getClass()))
            return value;
        else if (value instanceof String)
            return this.resolve( (String)value, type);
        else if (null != value)
            throw new IllegalStateException("Unexpected "+value.getClass());
        else
            return null;
    }
    public final Object resolve(String attributeValue, Class type){
        if (null == attributeValue || 1 > attributeValue.length())
            return null;
        else if (IsCollection(type)){
            int ct = TypeofCollection(type);
            java.lang.Class component = TypeComponentOf(type);
            switch (ct){
            case TypeCollectionList:{
                java.util.List list = (java.util.List)NewCollection(ct);
                String[] input = CollectionParseList(attributeValue);
                for (String term : input){
                    Object value = this.resolve(term,component);
                    if (null != value)
                        list.add(value);
                }
                return list;
            }
            case TypeCollectionQueue:{
                java.util.Queue queue = (java.util.Queue)NewCollection(ct);
                String[] input = CollectionParseList(attributeValue);
                for (String term : input){
                    Object value = this.resolve(term,component);
                    if (null != value)
                        queue.add(value);
                }
                return queue;
            }
            case TypeCollectionSet:{
                java.util.Set set = (java.util.Set)NewCollection(ct);
                String[] input = CollectionParseList(attributeValue);
                for (String term : input){
                    Object value = this.resolve(term,component);
                    if (null != value)
                        set.add(value);
                }
                return set;
            }
            case TypeCollectionMap:{
                java.util.Map map = (java.util.Map)NewCollection(ct);
                String[][] input = CollectionParseMap(attributeValue);
                for (String[] pair : input){
                    String name = pair[0];
                    String term = pair[1];
                    Object value = this.resolve(term,component);
                    if (null != value)
                        map.put(name,value);
                }
                return map;
            }
            default:
                break;
            }
        }

        attributeValue = attributeValue.trim();
        int tc = Typeof(type);
        switch(tc){
        case TypeBoolean:
            if ("true".equalsIgnoreCase(attributeValue))
                return Boolean.TRUE;
            else
                return Boolean.FALSE;

        case TypeCharacter:
            if (1 == attributeValue.length())
                return new Character(attributeValue.charAt(0));
            else {
                try {
                    return new Character((char)(Integer.decode(attributeValue).intValue()));
                }
                catch (NumberFormatException exception) {
                    return new Character(attributeValue.charAt(0));
                }
            }
        case TypeClass:
            try {
                return Class.forName(attributeValue);
            }
            catch (ClassNotFoundException exc){
                return attributeValue;
            }
        case TypeByte:
            try {
                return new Byte(Integer.decode(attributeValue).byteValue());
            } catch (NumberFormatException exception) {
                return attributeValue;
            }
        case TypeShort:
            try {
                return new Short(Integer.decode(attributeValue).shortValue());
            } catch (NumberFormatException exception) {
                return attributeValue;
            }
        case TypeInteger:
            try {
                return Integer.decode(attributeValue);
            } catch (NumberFormatException exception) {
                return attributeValue;
            }
        case TypeLong:
            try {
                return Long.decode(attributeValue);
            } catch (NumberFormatException exception) {
                return attributeValue;
            }
        case TypeFloat:
            try {
                return new Float(attributeValue);
            } catch (NumberFormatException exception) {
                return attributeValue;
            }
        case TypeDouble:
            try {
                return new Double(attributeValue);
            } catch (NumberFormatException exception) {
                return attributeValue;
            }

        default:

            switch (attributeValue.charAt(0)){

            case URL_PREFIX:

                if (attributeValue.length() > 1) {

                    if (attributeValue.charAt(1) == URL_PREFIX)
                        return attributeValue.substring(1);

                    else {
                        if (location == null) 
                            try {
                                return this.root.getInputURL(attributeValue.substring(1));
                            }
                            catch (MalformedURLException exc){
                                throw new RuntimeException(exc);
                            }
                        else
                            try {
                                return new URL(location, attributeValue.substring(1));
                            }
                            catch (MalformedURLException exc){
                                throw new RuntimeException(exc);
                            }
                    }
                }
                break;

            case 'u':
                if (attributeValue.startsWith(URL_PREFIX2)){

                    String path = attributeValue.substring(4,attributeValue.length()-1).trim();

                    if (location == null) 
                        try {
                            return this.root.getInputURL(path);
                        }
                        catch (MalformedURLException exc){
                            throw new RuntimeException(exc);
                        }
                    else
                        try {
                            return new URL(location, path);
                        }
                        catch (MalformedURLException exc){
                            throw new RuntimeException(exc);
                        }
                }
                break;

            case RESOURCE_KEY_PREFIX:

                if (attributeValue.length() > 1) {

                    if (attributeValue.charAt(1) == RESOURCE_KEY_PREFIX)

                        return attributeValue.substring(1);
                    else 
                        return this.resources.get(attributeValue.substring(1));
                }
                break;

            case OBJECT_REFERENCE_PREFIX:

                if (attributeValue.length() > 1) {

                    if (attributeValue.charAt(1) == OBJECT_REFERENCE_PREFIX) 

                        return attributeValue.substring(1);
                    else {
                        String id = attributeValue.substring(1);
                        Object re = this.getAttribute(id);
                        if (null != re)
                            return re;
                        else {
                            ReferenceTo pointer = ReferenceTo.Cache(type);
                            if (pointer.isValid){
                                return pointer.dereference(id);
                            }
                        }
                    }
                }
                break;

            default:
                break;
            }
            if (TypeString != tc){
                Constructor ctor = Constructor.Cache(type);
                if (null != ctor && ctor.isValid)
                    return ctor.create(attributeValue);
            }
            break;
        }
        return attributeValue;
    }
    /*
     * Resolve TypeOf Switch
     */
    protected final static int TypeBoolean = 1;
    protected final static int TypeCharacter = 2;
    protected final static int TypeClass = 3;
    protected final static int TypeByte = 4;
    protected final static int TypeShort = 5;
    protected final static int TypeInteger = 6;
    protected final static int TypeLong = 7;
    protected final static int TypeFloat = 8;
    protected final static int TypeDouble = 9;
    protected final static int TypeString = 10;
    protected final static java.util.Hashtable<Class,Integer> TypePrimitives = new java.util.Hashtable<Class,Integer>();
    static {
        TypePrimitives.put(Boolean.class,TypeBoolean);
        TypePrimitives.put(Boolean.TYPE,TypeBoolean);
        TypePrimitives.put(Character.class,TypeCharacter);
        TypePrimitives.put(Character.TYPE,TypeCharacter);
        TypePrimitives.put(Class.class,TypeClass);
        TypePrimitives.put(Byte.class,TypeByte);
        TypePrimitives.put(Byte.TYPE,TypeByte);
        TypePrimitives.put(Short.class,TypeShort);
        TypePrimitives.put(Short.TYPE,TypeShort);
        TypePrimitives.put(Integer.class,TypeInteger);
        TypePrimitives.put(Integer.TYPE,TypeInteger);
        TypePrimitives.put(Long.class,TypeLong);
        TypePrimitives.put(Long.TYPE,TypeLong);
        TypePrimitives.put(Float.class,TypeFloat);
        TypePrimitives.put(Float.TYPE,TypeFloat);
        TypePrimitives.put(Double.class,TypeDouble);
        TypePrimitives.put(Double.TYPE,TypeDouble);
        TypePrimitives.put(String.class,TypeString);
    }
    protected final static int Typeof(Class clas){
        if (null == clas)
            return 0;
        else {
            Integer type = TypePrimitives.get(clas);
            if (null == type)
                return 0;
            else
                return type.intValue();
        }
    }

    protected final static Class CollectionClass = java.util.Collection.class;
    protected final static Class CollectionMapClass = java.util.Map.class;

    protected final static boolean IsCollection(Class type){
        return (CollectionClass.isAssignableFrom(type)||CollectionMapClass.isAssignableFrom(type));
    }
    protected final static boolean IsNotCollection(Class type){
        return ((!CollectionClass.isAssignableFrom(type))&&(!CollectionMapClass.isAssignableFrom(type)));
    }
    /*
     * Resolve CollectionOf Switch
     */
    protected final static int TypeCollectionList = 1;
    protected final static int TypeCollectionQueue = 2;
    protected final static int TypeCollectionSet = 3;
    protected final static int TypeCollectionMap = 4;

    protected final static java.util.Hashtable<Class,Integer> TypeCollections = new java.util.Hashtable<Class,Integer>();
    static {
        TypeCollections.put(java.util.List.class,TypeCollectionList);
        TypeCollections.put(java.util.Queue.class,TypeCollectionQueue);
        TypeCollections.put(java.util.Set.class,TypeCollectionSet);
        TypeCollections.put(java.util.Map.class,TypeCollectionMap);
    }
    protected final static int TypeofCollection(Class clas){
        if (null == clas)
            return 0;
        else if (clas.isInterface()){
            Integer type = TypeCollections.get(clas);
            if (null == type)
                return 0;
            else
                return type.intValue();
        }
        else if (CollectionClass.isAssignableFrom(clas)){
            for (Class inf : clas.getInterfaces()){
                Integer type = TypeCollections.get(clas);
                if (null != type)
                    return type.intValue();
            }
            return 0;
        }
        else if (CollectionMapClass.isAssignableFrom(clas))
            return TypeCollectionMap;
        else 
            return 0;
    }
    protected final static Object NewCollection(int type){
        switch (type){
        case TypeCollectionList:
            return new java.util.ArrayList();
        case TypeCollectionQueue:
            return new java.util.PriorityQueue();
        case TypeCollectionSet:
            return new java.util.HashSet();
        case TypeCollectionMap:
            return new java.util.HashMap();
        default:
            return null;
        }
    }
    protected String[] CollectionParseList(String string){
        StringTokenizer strtok = new StringTokenizer(string,",; \r\n");
        String[] re = new String[strtok.countTokens()];
        for (int cc = 0, count = re.length; cc < count; cc++){
            re[cc] = strtok.nextToken();
        }
        return re;
    }
    protected String[][] CollectionParseMap(String string){
        String[] list = CollectionParseList(string);
        String[][] re = null;
        for (String term : list){
            String name, value;
            StringTokenizer strtok = new StringTokenizer(string,"=:");
            switch (strtok.countTokens()){
            case 2:
                name = strtok.nextToken();
                value = strtok.nextToken();
                break;
            case 1:
                name = strtok.nextToken();
                value = "";
                break;
            default:
                throw new IllegalArgumentException(string);
            }

            if (null == re)
                re = new String[][]{{name,value}};
            else {
                int len = re.length;
                String[][] copier = new String[len+1][];
                System.arraycopy(re,0,copier,0,len);
                copier[len] = new String[]{name,value};
                re = copier;
            }
        }
        return re;
    }

    /**
     * @param collection For a (collection container or array) class
     * declared as <code>Name&lt;A&gt;</code>, return
     * <code>A.class</code>, or for a class declared as
     * <code>Name&lt;A,B&gt;</code> return <code>B.class</code>.
     * Otherwise return <code>java.lang.Object.class</code> (not
     * specific).
     */
    public final static Class TypeComponentOf(Object collection){
        if (collection instanceof Class){
            Class jclass = (Class)collection;
            if (jclass.isArray())
                return jclass.getComponentType();
            else if (IsCollection(jclass)){
                java.lang.reflect.TypeVariable[] generic = jclass.getTypeParameters();
                switch (generic.length){
                case 1:
                    return (Class)(generic[0].getBounds()[0]);
                case 2:
                    return (Class)(generic[1].getBounds()[0]);
                default:
                    return Object.class;
                }
            }
            else
                return jclass;
        }
        else if (null != collection){
            Class jclass = collection.getClass();
            if (jclass.isArray())
                return jclass.getComponentType();
            else if (IsCollection(jclass)){
                java.lang.reflect.TypeVariable[] generic = jclass.getTypeParameters();
                switch (generic.length){
                case 1:
                    return (Class)(generic[0].getBounds()[0]);
                case 2:
                    return (Class)(generic[1].getBounds()[0]);
                default:
                    return Object.class;
                }
            }
            else
                return jclass;
        }
        else
            return Object.class;
    }
}
