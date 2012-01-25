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

import java.util.Collections;
import java.util.List;

/**
 * 
 */
public class BooleanJson
    extends Json
{
    private boolean val;


    public BooleanJson() {
        super();
    }
    public BooleanJson(Json e) {
        super(e);
    }
    public BooleanJson(Boolean val, Json e) { 
        super(e); 
        this.val = val;
    }
    public BooleanJson(Boolean val) { 
        super(); 
        this.val = val;
    }

		
    public Json dup() { return new BooleanJson(val, null); }		
    public boolean asBoolean() { return val; }		
    public boolean isBoolean() { return true;	}		
    public String toString(int d) { 
        Boolean val = this.val;
        if (null == val)
            return "null";
        else if (val)
            return "true";
        else
            return "false"; 
    }
		
    @SuppressWarnings("unchecked")
    public List<Object> asList() { return (List<Object>)(List<?>)Collections.singletonList(val); }
    public int hashCode() { return val ? 1 : 0; }
    public boolean equals(Object x)
    {
        return x instanceof BooleanJson && ((BooleanJson)x).val == val;
    }		
}
