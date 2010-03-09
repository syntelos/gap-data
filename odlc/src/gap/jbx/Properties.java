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
package gap.jbx;

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
