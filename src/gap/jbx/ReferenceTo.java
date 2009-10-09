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
