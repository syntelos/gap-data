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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class ObjectJson
    extends Json
{
    private Map<String, Json> object = new HashMap<String, Json>();

		
    public ObjectJson() {
        super();
    }
    public ObjectJson(Json e) { 
        super(e);
    }
    /**
     * @param args Sequence of name-value pairs 
     */
    public ObjectJson(Object... args) {
        super();

        if (args.length % 2 != 0)
            throw new IllegalArgumentException("Odd number of arguments for sequence of name-value pairs.");
        else {
            for (int i = 0; i < args.length; i++)
                this.set(args[i].toString(), args[++i]);
        }
    }
    public ObjectJson(Map map){
        super();

        for (Map.Entry entry : (Set<Map.Entry>)map.entrySet()){

            this.set( (String)entry.getKey(), entry.getValue());
        }
    }


    public Json dup() 
    { 
        ObjectJson j = new ObjectJson();
        for (Map.Entry<String, Json> e : object.entrySet())
            {
                Json v = e.getValue().dup();
                v.enclosing = j;
                j.object.put(e.getKey(), v);
            }
        return j;
    }
		
    public boolean has(String property)
    {
        return object.containsKey(property);
    }
		
    public boolean is(String property, Object value) 
    { 
        Json p = object.get(property);
        if (p == null)
            return false;
        else {
            Object po = p.getValue();
            if (null == po)
                return false;
            else
                return po.equals(value);
        }
    }		
		
    public Json at(String property)
    {
        return object.get(property);
    }

    public Json with(Json x)
    {
        if (!x.isObject())
            throw new UnsupportedOperationException();
        object.putAll(((ObjectJson)x).object);
        return this;
    }
    public Object getValue(String property){

        Json wrap = object.get(property);
        if (null != wrap)
            return wrap.getValue();
        else
            return null;
    }
    public Object getValue(String property, Class clas){

        Json wrap = object.get(property);
        if (null != wrap)
            return wrap.getValue(clas);
        else
            return null;
    }
    public Json set(String property, Json el)
    {
        el.enclosing = this;
        object.put(property, el);
        return this;
    }

    public Json atDel(String property) 
    {
        Json el = object.remove(property);
        if (el != null)
            el.enclosing = null;
        return el;
    }
		
    public Json delAt(String property) 
    {
        Json el = object.remove(property);
        if (el != null)
            el.enclosing = null;
        return this;
    }
		
    public Object getValue() { return asMap(); }
    public boolean isObject() { return true; }
    public Map<String, Object> asMap() 
    {
        HashMap<String, Object> m = new HashMap<String, Object>();
        for (Map.Entry<String, Json> e : object.entrySet())
            m.put(e.getKey(), e.getValue().getValue());
        return m; 
    }
    @Override
    public Map<String, Json> asJsonMap() { return object; }
		
    public String toString(final int d)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (Iterator<Map.Entry<String, Json>> i = object.entrySet().iterator(); i.hasNext(); )
            {
                Map.Entry<String, Json> x  = i.next();

                for (int dd = 0; dd < d; dd++){
                    sb.append(' ');
                }
                sb.append('"');
                sb.append(Escaper.Plain.escapeJsonString(x.getKey()));
                sb.append('"');
                sb.append(":");
                sb.append(x.getValue().toString());
                if (i.hasNext())
                    sb.append(',');
                sb.append('\n');
            }
        sb.append('}');
        return sb.toString();
    }
    public int hashCode() { 
        int h = 0;
        for (String key: object.keySet()){

            h ^= key.hashCode();
        }
        return h;
    }
    public boolean equals(Object x)
    {			
        return (x instanceof ObjectJson) && ((ObjectJson)x).object.equals(object); 
    }				
}
