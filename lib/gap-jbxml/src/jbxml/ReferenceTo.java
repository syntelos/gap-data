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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

/**
 * A bean class having a public static method named "Dereference"
 * (returning a member of the class and taking a string argument)
 * supports the forward referencing of beans by ID (string) when it
 * always returns a non null value.
 * 
 * @author jdp
 */
public class ReferenceTo {

    private final static java.util.Hashtable<Class,ReferenceTo> cache = 
        new java.util.Hashtable<Class,ReferenceTo>();

    public final static ReferenceTo Cache(Class target){
        if (null != target){
            ReferenceTo ctor = (ReferenceTo)cache.get(target);
            if (null != ctor)
                return ctor;
            else {
                ctor = new ReferenceTo(target);
                cache.put(target,ctor);
                return ctor;
            }
        }
        else
            return null;
    }


    public final Class target;
    public final Method method;
    public final boolean isValid;

    public ReferenceTo(Class target){
        super();
        this.target = target;
        Method method = null;
        boolean isValid = false;
        try {
            method = target.getMethod("Dereference",String.class);
            if (Modifier.isPublic(method.getModifiers())){
                isValid = (target.equals(method.getReturnType()));
            }
        }
        catch (NoSuchMethodException exc){
        }
        this.method = method;
        this.isValid = isValid;
    }

    public boolean isValid(){
        return this.isValid;
    }
    public Object dereference(String arg){
        if (this.isValid){
            try {
                return this.method.invoke(null,arg);
            }
            catch (IllegalAccessException exc){
                throw new RuntimeException(exc);
            }
            catch (InvocationTargetException exc){
                throw new RuntimeException(exc.getCause());
            }
        }
        else
            throw new IllegalStateException();
    }
}
