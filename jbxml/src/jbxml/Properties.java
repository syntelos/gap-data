/*
 * Copyright (c) 2009 VMware, Inc.
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * An implementation of the {@link Dictionary} interface over the java
 * built in hash map.
 *
 * @author jdp
 */
public final class Properties
    extends java.util.HashMap<String,String>
    implements Dictionary<String, String>
{


    private final Dictionary<String, String> defaults;


    public Properties(){
        super();
        this.defaults = null;
    }
    public Properties(Dictionary<String, String> defaults){
        super();
        this.defaults = defaults;
    }

    public String get(Object key) {
        String value = super.get(key);
        if (null != value)
            return value;
        else {
            Dictionary<String, String> defaults = this.defaults;
            if (null != defaults){
                return defaults.get(key);
            }
            return null;
        }
    }
    public boolean containsKey(Object key) {
        if (super.containsKey(key))
            return true;
        else {
            Dictionary<String, String> defaults = this.defaults;
            if (null != defaults){
                return defaults.containsKey(key);
            } else {
                return false;
            }
        }
    }
    public void load(InputStream in) throws IOException {
        java.util.Properties loader = new java.util.Properties();
        loader.load(in);
        java.util.Enumeration keys = loader.keys();
        String name, value;
        while(keys.hasMoreElements()){
            name = (String)keys.nextElement();
            value = (String)loader.get(name);
            this.put(name,value);
        }
    }
    public void load(String filename) throws IOException {
        InputStream in = new FileInputStream(filename);
        try {
            this.load(in);
        }
        finally {
            in.close();
        }
    }
    public java.util.Iterator<String> iterator(){
        return this.keySet().iterator();
    }
}
