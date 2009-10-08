/*
 * Copyright (c) 2009 John Pritchard, WTKX Project Group
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

import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author jdp
 */
public class Constructor {

    private final static java.util.Hashtable<Class,Constructor> cache = 
        new java.util.Hashtable<Class,Constructor>();

    public final static Constructor Cache(Class target){
        if (null != target){
            Constructor ctor = (Constructor)cache.get(target);
            if (null != ctor)
                return ctor;
            else {
                ctor = new Constructor(target);
                cache.put(target,ctor);
                return ctor;
            }
        }
        else
            return null;
    }


    public final Class target;
    public final java.lang.reflect.Constructor ctor;
    public final boolean isValid;

    public Constructor(Class target){
        super();
        this.target = target;
        java.lang.reflect.Constructor[] list = target.getConstructors();
        for (java.lang.reflect.Constructor c : list){
            if (Modifier.isPublic(c.getModifiers())){
                Class[] params = c.getParameterTypes();
                switch (params.length){
                case 0:
                    if (Value.class.isAssignableFrom(target)){
                        this.ctor = null;
                        this.isValid = true;
                        return;
                    }
                    else
                        break;
                case 1:
                    this.ctor = c;
                    this.isValid = true;
                    return;
                }
            }
        }
        this.ctor = null;
        this.isValid = false;
    }

    public boolean isValid(){
        return this.isValid;
    }
    public Object create(Object arg){
        if (this.isValid){
            java.lang.reflect.Constructor ctor = this.ctor;
            if (null != ctor){
                try {
                    return ctor.newInstance(arg);
                }
                catch (InstantiationException exc){
                    throw new RuntimeException(exc);
                }
                catch (IllegalAccessException exc){
                    throw new RuntimeException(exc);
                }
                catch (InvocationTargetException exc){
                    throw new RuntimeException(exc.getCause());
                }
            }
            else {
                try {
                    Value value = (Value)this.target.newInstance();
                    value.fromString(arg.toString());
                    return value;
                }
                catch (InstantiationException exc){
                    throw new RuntimeException(exc);
                }
                catch (IllegalAccessException exc){
                    throw new RuntimeException(exc);
                }
            }
        }
        else
            throw new IllegalStateException();
    }
}
