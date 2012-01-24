/*
 * Copyright (C) 2011 Miami-Dade County.
 * Copyright (C) 2012 John Pritchard, Gap Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Note: this file incorporates source code from 3d party entities. Such code 
 * is copyrighted by those entities as indicated below.
 */
package json;

import gap.Primitive;
import gap.Objects;
import gap.Strings;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <h3><a href="http://www.json.org">JSON</a> network format</h3>
 *
 * <p> A JSON entity can be one of the following: an object, array,
 * string, number, boolean, or null.  These are indicated by the
 * following type methods: {@link #isObject()}, {@link
 * #isArray()},{@link #isNumber()},{@link #isBoolean()},{@link
 * #isString()}, and {@link #isNull()}.  </p>
 * 
 * <h3>Value</h3>
 * 
 * <p> The underlying value of an instance of <code>Json</code> is
 * available from the {@link #getValue()} method, or one of the
 * <code>asXXX</code> methods.  For example, {@link #asBoolean()} or
 * {@link #asString()}.  </p>
 * 
 * <h3>Copy</h3>
 * 
 * <p> The method {@link #asMap()} performs a deep copy of the
 * underlying map, unwrapping every nested Json entity to its Java
 * representation.  Conversely the method {@link #asJsonMap()} simply
 * returns the map reference.  The methods {@link #asList()} and
 * {@link #asJsonList()} function analogously.  </p>
 * 
 * <h3>Structure</h3>
 * 
 * <p> Set object properties with {@link #set(String, Object)}.
 * </p>
 *
 * <p> The {@link #add(Object)} method works on arrays, as {@link
 * #delAt(int)}. </p>
 * 
 * <p> The {@link #delAt(String)} method works on objects. </p>
 *
 * <p> The {@link #remove(Object)} method works on arrays or
 * objects. </p>
 *
 * <p> To return the removed, use {@link #atDel(int)} or {@link
 * #atDel(String)}.  </p>
 * 
 * <p> To add properties to an object in bulk, or append a sequence of
 * elements to array, use {@link #with(Json)}.  Called on an object,
 * this method copies the argument object properties into itself.
 * Called on an array, this method copies the members of the argument
 * array into itself.  </p>
 * 
 * <p> The {@link #at(int)} method returns the array element at the
 * specified index, and the {@link #at(String)} method does the same
 * for a property of an object instance. The method {@link #at(String,
 * Object)} will create an object property with a default value if it
 * doesn't exist already.  </p>
 * 
 * <p> The enclosing entity can be accessed with {@link #up()} method.
 * </p>
 *
 * 
 * @author Borislav Iordanov
 * @author John Pritchard
 * @version Gap-Data
 */
public abstract class Json
{
    public static Json Decode(String json) { 

        return (Json)new Reader().read(json); 
    }
    public static String Encode(Json json){ 

        return json.toString();
    }
    public static Json Wrap(Object anything) 
    {
        if (anything == null)
            return NullJson.Instance;
        else if (anything instanceof Json)
            return (Json)anything;

        else if (anything instanceof String)
            return new StringJson((String)anything);

        else if (anything instanceof Boolean)
            return new BooleanJson((Boolean)anything);

        else if (anything instanceof Number)
            return new NumberJson((Number)anything);

        else if (anything instanceof Collection<?>)
            {
                Json L = new ArrayJson();
                for (Object x : (Collection<?>)anything)
                    L.add(Json.Wrap(x));
                return L;
            }
        else if (anything instanceof Map<?,?>)
            {
                Json O = new ObjectJson();
                for (Map.Entry<?,?> x : ((Map<?,?>)anything).entrySet())
                    O.set(x.getKey().toString(), Json.Wrap(x.getValue()));
                return O;
            }
        else if (anything.getClass().isArray())
            {
                Class<?> comp = anything.getClass().getComponentType();
                if (!comp.isPrimitive())
                    return new ArrayJson((Object[])anything);
                else {
                    Json A = new ArrayJson();
                    if (boolean.class == comp)
                        for (boolean b : (boolean[])anything) A.add(b);
                    else if (byte.class == comp)
                        for (byte b : (byte[])anything) A.add(b);
                    else if (char.class == comp)
                        for (char b : (char[])anything) A.add(b);
                    else if (short.class == comp)
                        for (short b : (short[])anything) A.add(b);
                    else if (int.class == comp)
                        for (int b : (int[])anything) A.add(b);
                    else if (long.class == comp)
                        for (long b : (long[])anything) A.add(b);
                    else if (float.class == comp)
                        for (float b : (float[])anything) A.add(b);
                    else if (double.class == comp)
                        for (double b : (double[])anything) A.add(b);
                    else
                        throw new IllegalArgumentException("Unrecognized array component type "+comp.getName());

                    return A;
                }
            }
        else
            throw new IllegalArgumentException(anything.getClass().getName());
    }
		


    Json enclosing = null;

	
    protected Json() {
        super();
    }
    protected Json(Json enclosing) { 
        super();
        this.enclosing = enclosing;
    }
	
    /**
     * <p>Explicitly set the parent of this element. The parent is presumably an array
     * or an object. Normally, there's no need to call this method as the parent is
     * automatically set by the framework. You may need to call it however, if you implement
     * your own {@link Factory} with your own implementations of the Json types.
     * </p>
     *  
     * @param enclosing The parent element.
     */
    public void attachTo(Json enclosing) { this.enclosing = enclosing; }
	
    /**
     * <p>Return the <code>Json</code> entity, if any, enclosing this 
     * <code>Json</code>. The returned value can be <code>null</code> or
     * a <code>Json</code> object or list, but not one of the primitive types.</p>
     */
    public final Json up() { return enclosing; }
	
    /**
     * <p>Return a clone (a duplicate) of this <code>Json</code> entity. Note that cloning
     * is deep if array and objects. Primitives are also cloned, even though their values are immutable
     * because the new enclosing entity (the result of the {@link #up()} method) may be different.
     * since they are immutable.</p>
     */
    public Json dup() { return this; }
	
    /**
     * <p>Return the <code>Json</code> element at the specified index of this
     * <code>Json</code> array. This method applies only to Json arrays.
     * </p>
     * 
     * @param index The index of the desired element.
     */
    public Json at(int index) { throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Return the specified property of a <code>Json</code> object or <code>null</code>
     * if there's no such property. This method applies only to Json objects.  
     * </p>
     */
    public Json at(String property)	{ throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Return the specified property of a <code>Json</code> object if it exists.
     * If it doesn't, then create a new property with value the <code>def</code> 
     * parameter and return that parameter. 
     * </p>
     * 
     * @param property The property to return.
     * @param def The default value to set and return in case the property doesn't exist.
     */
    public final Json at(String property, Json def)	
    {
        Json x = at(property);
        if (x == null)
            {
                set(property, def);
                return def;
            }
        else
            return x; 
    }	
	
    /**
     * <p>
     * Return the specified property of a <code>Json</code> object if it exists.
     * If it doesn't, then create a new property with value the <code>def</code> 
     * parameter and return that parameter. 
     * </p>
     * 
     * @param property The property to return.
     * @param def The default value to set and return in case the property doesn't exist.
     */
    public final Json at(String property, Object def)
    {
        return at(property, Json.Wrap(def));
    }
	
    /**
     * <p>
     * Return true if this <code>Json</code> object has the specified property
     * and false otherwise. 
     * </p>
     * 
     * @param property The name of the property.
     */
    public boolean has(String property)	{ throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Return <code>true</code> if and only if this <code>Json</code> object has a property with
     * the specified value. In particular, if the object has no such property <code>false</code> is returned. 
     * </p>
     * 
     * @param property The property name.
     * @param value The value to compare with. Comparison is done via the equals method. 
     * If the value is not an instance of <code>Json</code>, it is first converted to
     * such an instance. 
     * @return
     */
    public boolean is(String property, Object value) { throw new UnsupportedOperationException(); }

    /**
     * <p>
     * Return <code>true</code> if and only if this <code>Json</code> array has an element with
     * the specified value at the specified index. In particular, if the array has no element at
     * this index, <code>false</code> is returned. 
     * </p>
     * 
     * @param property The property name.
     * @param value The value to compare with. Comparison is done via the equals method. 
     * If the value is not an instance of <code>Json</code>, it is first converted to
     * such an instance. 
     * @return
     */
    public boolean is(int index, Object value) { throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Add the specified <code>Json</code> element to this array. 
     * </p>
     * 
     * @return this
     */
    public Json add(Json el) { throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Add an arbitrary Java object to this <code>Json</code> array. The object
     * is first converted to a <code>Json</code> instance by calling the static
     * {@link #Wrap} method.
     * </p>
     * 
     * @param anything Any Java object that can be converted to a Json instance.
     * @return this
     */
    public final Json add(Object anything) { return add(Json.Wrap(anything)); }
	
    /**
     * <p>
     * Remove the specified property from a <code>Json</code> object and return
     * that property.
     * </p>
     * 
     * @param property The property to be removed.
     * @return The property value or <code>null</code> if the object didn't have such
     * a property to begin with.
     */
    public Json atDel(String property)	{ throw new UnsupportedOperationException(); }

    /**
     * <p>
     * Remove the element at the specified index from a <code>Json</code> array and return
     * that element.
     * </p>
     * 
     * @param index The index of the element to delete.
     * @return The element value.
     */
    public Json atDel(int index)	{ throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Delete the specified property from a <code>Json</code> object.
     * </p>
     * 
     * @param property The property to be removed.
     * @return this
     */
    public Json delAt(String property)	{ throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Remove the element at the specified index from a <code>Json</code> array.
     * </p>
     * 
     * @param index The index of the element to delete.
     * @return this
     */
    public Json delAt(int index) { throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Remove the specified element from a <code>Json</code> array.
     * </p>
     * 
     * @param el The element to delete.
     * @return this
     */
    public Json remove(Json el)	{ throw new UnsupportedOperationException(); }
	
    /**
     * <p>
     * Remove the specified Java object (converted to a Json instance) 
     * from a <code>Json</code> array. This is equivalent to 
     * <code>remove({@link #Wrap(anything)})</code>.
     * </p>
     * 
     * @param anything The object to delete.
     * @return this
     */
    public final Json remove(Object anything) { return remove(Json.Wrap(anything)); }
	
    /**
     * <p>
     * Set a <code>Json</code> objects's property.
     * </p>
     * 
     * @param property The property name.
     * @param value The value of the property.
     * @return this
     */
    public Json set(String property, Json value) { throw new UnsupportedOperationException();	}
	
    /**
     * <p>
     * Set a <code>Json</code> objects's property.
     * </p>
     * 
     * @param property The property name.
     * @param value The value of the property, converted to a <code>Json</code> representation
     * with {@link #Wrap}.
     * @return this
     */
    public final Json set(String property, Object value) { return set(property, Json.Wrap(value)); }
	
    /**
     * <p>
     * Combine this object or array with the passed in object or array. The types of 
     * <code>this</code> and the <code>object</code> argument must match. If both are
     * <code>Json</code> objects, all properties of the parameter are added to <code>this</code>.
     * If both are arrays, all elements of the parameter are appended to <code>this</code> 
     * </p>
     * @param object The object or array whose properties or elements must be added to this
     * Json object or array.
     * @return this
     */
    public Json with(Json object) { throw new UnsupportedOperationException(); }
	
    /**
     * <p>Return the underlying value of this <code>Json</code> entity. The actual value will 
     * be a Java Boolean, String, Number, Map, List or null. For complex entities (objects
     * or arrays), the method will perform a deep copy and extra underlying values recursively 
     * for all nested elements.</p>
     */
    public Object getValue() { throw new UnsupportedOperationException(); }

    public Object getValue(Class clas){
        Object value = this.getValue();
        if (null == value || clas.isAssignableFrom(value.getClass()))
            return value;
        else {
            final Primitive primitive = Primitive.For(clas);
            if (null != primitive){
                if (value instanceof String)
                    return Strings.FromString(primitive, (String)value);
                else
                    return Objects.From(primitive, value);
            }
            else
                throw new IllegalArgumentException("Conversion from "+value.getClass().getName()+" to "+clas.getName());
        }
    }
    /**
     * <p>Return the boolean value of a boolean <code>Json</code> instance. Call
     * {@link #isBoolean()} first if you're not sure this instance is indeed a
     * boolean.</p>
     */
    public boolean asBoolean() { throw new UnsupportedOperationException(); }
	
    /**
     * <p>Return the string value of a string <code>Json</code> instance. Call
     * {@link #isString()} first if you're not sure this instance is indeed a
     * string.</p>
     */
    public String asString() { throw new UnsupportedOperationException(); }
	
    /**
     * <p>Return the integer value of a number <code>Json</code> instance. Call
     * {@link #isNumber()} first if you're not sure this instance is indeed a
     * number.</p>
     */
    public int asInteger() { throw new UnsupportedOperationException(); }

    /**
     * <p>Return the float value of a float <code>Json</code> instance. Call
     * {@link #isNumber()} first if you're not sure this instance is indeed a
     * number.</p>
     */
    public float asFloat() { throw new UnsupportedOperationException(); }

    /**
     * <p>Return the double value of a number <code>Json</code> instance. Call
     * {@link #isNumber()} first if you're not sure this instance is indeed a
     * number.</p>
     */
    public double asDouble() { throw new UnsupportedOperationException(); }

    /**
     * <p>Return the long value of a number <code>Json</code> instance. Call
     * {@link #isNumber()} first if you're not sure this instance is indeed a
     * number.</p>
     */
    public long asLong() { throw new UnsupportedOperationException(); }

    /**
     * <p>Return the short value of a number <code>Json</code> instance. Call
     * {@link #isNumber()} first if you're not sure this instance is indeed a
     * number.</p>
     */
    public short asShort() { throw new UnsupportedOperationException(); }

    /**
     * <p>Return the byte value of a number <code>Json</code> instance. Call
     * {@link #isNumber()} first if you're not sure this instance is indeed a
     * number.</p>
     */	
    public byte asByte() { throw new UnsupportedOperationException(); }

    /**
     * <p>Return the first character of a string <code>Json</code> instance. Call
     * {@link #isString()} first if you're not sure this instance is indeed a
     * string.</p>
     */	
    public char asChar() { throw new UnsupportedOperationException(); }		

    /**
     * <p>Return a map of the properties of an object <code>Json</code> instance. The map
     * is a clone of the object and can be modified safely without affecting it. Call
     * {@link #isObject()} first if you're not sure this instance is indeed a
     * <code>Json</code> object.</p>
     */	
    public Map<String, Object> asMap() { throw new UnsupportedOperationException(); }
	
    /**
     * <p>Return the underlying map of properties of a <code>Json</code> object. The returned
     * map is the actual object representation so any modifications to it are modifications
     * of the <code>Json</code> object itself. Call
     * {@link #isObject()} first if you're not sure this instance is indeed a
     * <code>Json</code> object.
     * </p>
     */
    public Map<String, Json> asJsonMap() { throw new UnsupportedOperationException(); }
	
    /**
     * <p>Return a list of the elements of a <code>Json</code> array. The list is a clone
     * of the array and can be modified safely without affecting it. Call
     * {@link #isArray()} first if you're not sure this instance is indeed a
     * <code>Json</code> array.
     * </p>  
     */
    public List<Object> asList()  { throw new UnsupportedOperationException(); }
	
    /**
     * <p>Return the underlying {@link List} representation of a <code>Json</code> array.
     * The returned list is the actual array representation so any modifications to it 
     * are modifications of the <code>Json</code> array itself. Call
     * {@link #isArray()} first if you're not sure this instance is indeed a
     * <code>Json</code> array.
     * </p>
     */
    public List<Json> asJsonList() { throw new UnsupportedOperationException(); }

    /**
     * <p>Return <code>true</code> if this is a <code>Json</code> null entity 
     * and <code>false</code> otherwise.
     * </p> 
     */
    public boolean isNull() { return false; }
    /**
     * <p>Return <code>true</code> if this is a <code>Json</code> string entity 
     * and <code>false</code> otherwise.
     * </p> 
     */
    public boolean isString() { return false; }	
    /**
     * <p>Return <code>true</code> if this is a <code>Json</code> number entity 
     * and <code>false</code> otherwise.
     * </p> 
     */
    public boolean isNumber() { return false; }	
    /**
     * <p>Return <code>true</code> if this is a <code>Json</code> boolean entity 
     * and <code>false</code> otherwise.
     * </p> 
     */
    public boolean isBoolean() { return false;	}	
    /**
     * <p>Return <code>true</code> if this is a <code>Json</code> array (i.e. list) entity 
     * and <code>false</code> otherwise.
     * </p> 
     */
    public boolean isArray() { return false; }	
    /**
     * <p>Return <code>true</code> if this is a <code>Json</code> object entity 
     * and <code>false</code> otherwise.
     * </p> 
     */
    public boolean isObject(){ return false; }	
    /**
     * <p>Return <code>true</code> if this is a <code>Json</code> primitive entity 
     * (one of string, number or boolean) and <code>false</code> otherwise.
     * </p> 
     */
    public boolean isPrimitive() { return isString() || isNumber() || isBoolean(); }

    public final String toString()
    {
        return toString(0);
    }
    public abstract String toString(final int d);

}