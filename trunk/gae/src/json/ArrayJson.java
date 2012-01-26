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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */	
public class ArrayJson
    extends Json
{
    private List<Json> list = new ArrayList<Json>();

		
    public ArrayJson() {
        super();
    }
    public ArrayJson(Json e) {
        super(e);
    }
    public ArrayJson(Object... args) {
        super();
        for (Object x : args){
            this.add(x);
        }
    }
    public ArrayJson(Iterable args) {
        super();
        for (Object x : args){
            this.add(x);
        }
    }
		

    public boolean isNull(){
        return (0 == list.size());
    }
    public Json dup() 
    { 
        ArrayJson j = new ArrayJson();
        for (Json e : list)
            {
                Json v = e.dup();
                v.enclosing = j;
                j.list.add(v);
            }
        return j;
    }
		
    public List<Json> asJsonList() { return list; }
    public List<Object> asList() 
    {
        ArrayList<Object> A = new ArrayList<Object>();
        for (Json x: list)
            A.add(x.getValue());
        return A; 
    }
    public boolean is(int index, Object value) 
    { 
        if (index < 0 || index >= list.size())
            return false;
        else {
            Json p = list.get(index);
            if (null == p)
                return false;
            else {
                Object po = p.getValue();
                if (null == po)
                    return false;
                else
                    return po.equals(value);
            }
        }
    }       		
    public Object getValue() { return asList(); }
    public boolean isArray() { return true; }
    public Json at(int index) { return list.get(index); }
    public Json add(Json el) { list.add(el); el.enclosing = this; return this; }
    public Json remove(Json el) { list.remove(el); el.enclosing = null; return this; }

    public Json with(Json object) 
    {
        if (!object.isArray())
            throw new UnsupportedOperationException();
        // what about "enclosing" here? we don't have a provision where a Json 
        // element belongs to more than one enclosing elements...
        list.addAll(((ArrayJson)object).list);
        return this;
    }
		
    public Json atDel(int index) 
    { 
        Json el = list.remove(index); 
        if (el != null) 
            el.enclosing = null; 
        return el; 
    }
		
    public Json delAt(int index) 
    { 
        Json el = list.remove(index); 
        if (el != null) 
            el.enclosing = null; 
        return this; 
    }
		
    public String toString(final int d)
    {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (Iterator<Json> i = list.iterator(); i.hasNext(); )
            {
                for (int dd = 0; dd < d; dd++){
                    sb.append(' ');
                }
                sb.append(i.next().toString(d+1));
                if (i.hasNext())
                    sb.append(',');
                sb.append('\n');
            }
        sb.append(']');
        return sb.toString();
    }
    public int hashCode() { return list.hashCode(); }
    public boolean equals(Object x)
    {			
        return (x instanceof ArrayJson) && ((ArrayJson)x).list.equals(list); 
    }		
}