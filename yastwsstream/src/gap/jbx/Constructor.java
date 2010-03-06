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
