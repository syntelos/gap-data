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

/**
 * WTKX rvalue string.  
 * 
 * The value returned by 'toString' is parsed by 'fromString'.  This
 * string may not contain the characters ':' or ';'.  
 * 
 * These strings are compatible with CSS right hand side values.
 * 
 * @see wtkx.io.CSS
 * @see http://www.w3.org/TR/css-style-attr
 * 
 * @author jdp
 */
public interface Value {
    /**
     * For CSS named groups including pseudo elements.
     */
    public interface Via {
        /**
         * A get or create operation may return null, as for 'name'
         * unrecognized.  
         * 
         * When the rvalue is in class {@link Value}, then a
         * recognized name should always return a value -- as for
         * parsing.
         * 
         * Otherwise, when the rvalue is a number or color or font --
         * for example -- then null is appropriate when the class has
         * no reasonable state or context for the representation of
         * the named value.
         */
        public Object forName(String name);
    }

    public void fromString(String value);

    public String toString();
}
