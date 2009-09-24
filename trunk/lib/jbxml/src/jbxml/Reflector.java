/*
 * Copyright (c) 2008 VMware, Inc.
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Java reflection as bean properties via the {@link Dictionary}
 * interface.  
 * 
 * The "bean" convention represents as bean property named 'foo' via
 * the methods 'getFoo' and 'setFoo' on the Java object class.  The
 * type of 'foo' is the type of the value returned by 'getFoo'.
 *
 * @author gbrown
 * @author jdp
 */
public class Reflector
    implements Dictionary<String, Object>, Iterable<String> 
{

    public static final String GET_PREFIX = "get";
    public static final String IS_PREFIX = "is";
    public static final String SET_PREFIX = "set";


    private class PropertyIterator implements Iterator<String> {
        private Method[] methods = null;

        int i = 0;
        private String nextProperty = null;

        public PropertyIterator() {
            Class<?> type = bean.getClass();
            methods = type.getMethods();
            nextProperty();
        }

        public boolean hasNext() {
            return (nextProperty != null);
        }

        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            String nextProperty = this.nextProperty;
            nextProperty();

            return nextProperty;
        }

        private void nextProperty() {
            nextProperty = null;

            while (i < methods.length
                && nextProperty == null) {
                Method method = methods[i++];

                if (method.getParameterTypes().length == 0
                    && (method.getModifiers() & Modifier.STATIC) == 0) {
                    String methodName = method.getName();

                    String prefix = null;
                    if (methodName.startsWith(GET_PREFIX)) {
                        prefix = GET_PREFIX;
                    } else {
                        if (methodName.startsWith(IS_PREFIX)) {
                            prefix = IS_PREFIX;
                        }
                    }

                    if (prefix != null) {
                        int propertyOffset = prefix.length();
                        nextProperty = Character.toLowerCase(methodName.charAt(propertyOffset))
                            + methodName.substring(propertyOffset + 1);
                    }

                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private Object bean;

    public final boolean isComponent;

    private Map<String,Relative> relatives;


    public Reflector(Object bean) {
        super();
        if (null != bean){
            this.bean = bean;
            this.isComponent = (bean instanceof Component);
        }
        else
            throw new IllegalArgumentException();
    }


    public void destroy(){
        this.bean = null;
        Map<String,Relative> relatives = this.relatives;
        if (null != relatives){
            this.relatives = null;
            relatives.clear();
        }
    }
    public boolean hasRelative(String name){
        Map<String,Relative> relatives = this.relatives;
        if (null == relatives)
            return false;
        else
            return (null != relatives.get(name));
    }
    public Number getRelativeValue(String name, Number value){
        Relative relative = this.getRelative(name);
        if (null != relative){
            try {
                return relative.floatValue(value);
            }
            catch (RuntimeException x2){
            }
        }
        return null;
    }
    public Relative getRelative(String name){
        Map<String,Relative> relatives = this.relatives;
        if (null == relatives)
            return null;
        else
            return relatives.get(name);
    }
    public Relative setRelative(String name, String value){
        Map<String,Relative> relatives = this.relatives;
        if (null == relatives){
            relatives = new HashMap<String,Relative>();
            this.relatives = relatives;
        }
        Relative rel = relatives.get(name);
        if (null != rel){
            if (null != value)
                rel.fromString(value);
            return rel;
        }
        else if (this.bean instanceof Component){
            rel = new Relative( (Component)this.bean, name, value);
            relatives.put(name,rel);
            return rel;
        }
        else
            return null;
    }
    public void dropRelative(String name){
        Map<String,Relative> relatives = this.relatives;
        if (null != relatives)
            relatives.remove(name);
    }
    /**
     * @return May be null
     */
    public Iterator<String> relatives(){
        Map<String,Relative> relatives = this.relatives;
        if (null != relatives)
            return relatives.keySet().iterator();
        else
            return null;
    }

    public Object getBean() {
    	return bean;
    }
    public Object get(Object key) {
        if (null != key){
            Method getterMethod = this.getGetterMethod( (String)key);
            if (getterMethod != null) {
                try {
                    return getterMethod.invoke(bean, new Object[] {});
                }
                catch (IllegalAccessException exception) {
                    return null;
                }
                catch(InvocationTargetException exception) {
                }
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }
    public Object put(String key, Object value) {
        if (null != key) {

            Class lvalueType = null, rvalueType = ((null != value)?(value.getClass()):(null));

            Method getterMethod = this.getGetterMethod(key);

            if (null != getterMethod){
                lvalueType = getterMethod.getReturnType();
            }

            if (null == lvalueType){
                rvalueType = lvalueType;
            }

            if (null == lvalueType && null == rvalueType)
                throw new PropertyNotFoundException("Unable to determine property type for \"" + key + "\" on bean \""+bean.getClass().getName()+"\".  Define a getter method or avoid setting null.");
            else {
                Method setterMethod = this.getSetterMethod(key, rvalueType);
                if (setterMethod == null){
                    setterMethod = this.getSetterMethod(key, lvalueType);
                    if (null == setterMethod)
                        throw new PropertyNotFoundException("Setter method for property \"" +lvalueType.getName() +' '+ key + "\" not found on bean \""+bean.getClass().getName()+"\".");
                    else {
                        if (lvalueType.isAssignableFrom(rvalueType)){
                            try {
                                setterMethod.invoke(bean, new Object[] {value});
                            }
                            catch(IllegalAccessException exception) {
                                throw new IllegalArgumentException(exception);
                            } 
                            catch(InvocationTargetException exc) {
                
                                Throwable cause = exc.getCause();
                                if (this.isComponent && value instanceof String && cause instanceof NumberFormatException){
                                    try {
                                        Relative rel = this.setRelative(key,(String)value);
                                        if (rel.byPixel()){
                                            this.dropRelative(key);
                                            try {
                                                setterMethod.invoke(bean, new Object[] {rel.floatValue()});
                                            }
                                            catch(IllegalAccessException exc2){
                                            } 
                                            catch(InvocationTargetException exc2){
                                            }
                                        }
                                    }
                                    catch (RuntimeException not){
                                    }
                                }
                                throw new IllegalArgumentException(cause);
                            }
                        }
                        else if (Resolver.IsCollection(lvalueType) && Resolver.IsNotCollection(rvalueType)){
                            int ct = Resolver.TypeofCollection(lvalueType);
                            switch (ct){
                            case Resolver.TypeCollectionList:{
                                java.util.List list = (java.util.List)this.get(key);
                                if (null == list){
                                    list = (java.util.List)Resolver.NewCollection(ct);
                                    try {
                                        setterMethod.invoke(bean, new Object[] {list});
                                    }
                                    catch(IllegalAccessException exc){
                                        throw new IllegalArgumentException(exc);
                                    } 
                                    catch(InvocationTargetException exc){
                                        Throwable cause = exc.getCause();
                                        throw new IllegalArgumentException(cause);
                                    }
                                }
                                list.add(value);
                                return list;
                            }
                            case Resolver.TypeCollectionQueue:{
                                java.util.Queue queue = (java.util.Queue)this.get(key);
                                if (null == queue){
                                    queue = (java.util.Queue)Resolver.NewCollection(ct);
                                    try {
                                        setterMethod.invoke(bean, new Object[] {queue});
                                    }
                                    catch(IllegalAccessException exc){
                                        throw new IllegalArgumentException(exc);
                                    } 
                                    catch(InvocationTargetException exc){
                                        Throwable cause = exc.getCause();
                                        throw new IllegalArgumentException(cause);
                                    }
                                }
                                queue.add(value);
                                return queue;
                            }
                            case Resolver.TypeCollectionSet:{
                                java.util.Set set = (java.util.Set)this.get(key);
                                if (null == set){
                                    set = (java.util.Set)Resolver.NewCollection(ct);
                                    try {
                                        setterMethod.invoke(bean, new Object[] {set});
                                    }
                                    catch(IllegalAccessException exc){
                                        throw new IllegalArgumentException(exc);
                                    } 
                                    catch(InvocationTargetException exc){
                                        Throwable cause = exc.getCause();
                                        throw new IllegalArgumentException(cause);
                                    }
                                }
                                set.add(value);
                                return set;
                            }
                            case Resolver.TypeCollectionMap:{
                                java.util.Map map = (java.util.Map)this.get(key);
                                if (null == map){
                                    map = (java.util.Map)Resolver.NewCollection(ct);
                                    try {
                                        setterMethod.invoke(bean, new Object[] {map});
                                    }
                                    catch(IllegalAccessException exc){
                                        throw new IllegalArgumentException(exc);
                                    } 
                                    catch(InvocationTargetException exc){
                                        Throwable cause = exc.getCause();
                                        throw new IllegalArgumentException(cause);
                                    }
                                }
                                map.put(key,value);
                                return map;
                            }
                            default:
                                throw new IllegalStateException();
                            }
                        }
                        else 
                            throw new PropertyNotFoundException("Setter method for property \"" + key + "\" in class \""+rvalueType.getName()+"\" not found on bean \""+bean.getClass().getName()+"\".");
                    }
                }
                else {
                    try {
                        setterMethod.invoke(bean, new Object[] {value});
                    }
                    catch(IllegalAccessException exception) {
                        throw new IllegalArgumentException(exception);
                    } 
                    catch(InvocationTargetException exc) {
                
                        Throwable cause = exc.getCause();
                        if (this.isComponent && value instanceof String && cause instanceof NumberFormatException){
                            try {
                                Relative rel = this.setRelative(key,(String)value);
                                if (rel.byPixel()){
                                    this.dropRelative(key);
                                    try {
                                        setterMethod.invoke(bean, new Object[] {rel.floatValue()});
                                    }
                                    catch(IllegalAccessException exc2){
                                    } 
                                    catch(InvocationTargetException exc2){
                                    }
                                }
                            }
                            catch (RuntimeException not){
                            }
                        }
                        throw new IllegalArgumentException(cause);
                    }
                }
            }
            return null;
        }
        else
            throw new IllegalArgumentException();
    }
    public boolean isReadOnly(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null.");
        }

        return (getSetterMethod(key, getType(key)) == null);
    }
    public Class<?> getType(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null.");
        }

        Method getterMethod = getGetterMethod(key);

        return (getterMethod == null) ? null : getterMethod.getReturnType();
    }
    private Method getGetterMethod(String key) {
        Class<?> type = bean.getClass();

        key = Resolver.ClassCamel(key);

        Method method = null;
        try {
            method = type.getMethod(GET_PREFIX + key, new Class<?>[] {});
        }
        catch(NoSuchMethodException exception) {
        }

        if (method == null) {
            try {
                method = type.getMethod(IS_PREFIX + key, new Class<?>[] {});
            }
            catch(NoSuchMethodException exception) {
            }
        }

        return method;
    }
    private Method getSetterMethod(String key, Class<?> valueType) {
        Class<?> type = this.bean.getClass();
        Method method = null;

        if (valueType != null) {

            final String methodName = SET_PREFIX + Resolver.ClassCamel(key);

            try {
                method = type.getMethod(methodName, new Class<?>[] {valueType});
            }
            catch(NoSuchMethodException exception) {

            }

            if (method == null) {
                Class<?> superType = valueType.getSuperclass();
                method = getSetterMethod(key, superType);
            }

            if (method == null) {
                try {
                    Field primitiveTypeField = valueType.getField("TYPE");
                    Class<?> primitiveValueType = (Class<?>)primitiveTypeField.get(this);

                    try {
                        method = type.getMethod(methodName, new Class<?>[] {primitiveValueType});
                    }
                    catch(NoSuchMethodException exception) {
                    }
                }
                catch(NoSuchFieldException exception) {
                }
                catch(IllegalAccessException exception) {
                }
            }

            if (method == null) {

                Class<?>[] interfaces = valueType.getInterfaces();

                int i = 0, n = interfaces.length;
                while (method == null && i < n) {
                    Class<?> interfaceType = interfaces[i++];
                    method = getSetterMethod(key, interfaceType);
                }
            }
        }

        return method;
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }
    public boolean containsKey(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null.");
        }

        return (this.getGetterMethod( (String)key) != null);
    }
    public boolean isEmpty() {
        return !iterator().hasNext();
    }
    public Iterator<String> iterator() {
        return new PropertyIterator();
    }

    public void applyAttributes(Resolver io, Dictionary<String, ?> attributes){

        for (String name : attributes){

            Class type = this.getType(name);
            if (null != type){
                Object value = attributes.get(name);

                if (value instanceof String){
                    value = io.resolve( (String)value, type);
                }

                if (null != value)
                    try {
                        this.put(name,value);
                    }
                    catch (PropertyNotFoundException exc){
                    }
            }
        }
    }
}
