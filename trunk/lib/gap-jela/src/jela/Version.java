/*
 * Copyright (c) 2009 John Pritchard and the Jela Project Group
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
package jela;

public final class Version
    extends Object
{
    public final static String Name = "gap-jela";
    public final static int Major   =  1;
    public final static int Minor   =  0;
    public final static int Build   =  1;


    public final static String Number = String.valueOf(Major)+'.'+String.valueOf(Minor);

    public final static String Full = Name+'-'+Number;

    public final static boolean Release = (0 == Build);

    private Version(){
        super();
    }
}
