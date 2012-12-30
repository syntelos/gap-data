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
package gap.util;

import gap.service.od.ClassDescriptor;
import gap.service.od.FieldDescriptor;
import gap.service.od.PackageDescriptor;
import gap.service.od.MethodDescriptor;

/**
 * Short descriptor for abstract table classes expressed as interfaces
 * member of {@link gap.data.TableClass}.
 */
public final class ClassNameDescriptor
    extends Object
    implements ClassDescriptor.WithPackage
{

    public final class ClassNamePackageDescriptor
        extends Object
        implements PackageDescriptor
    {

        public final String name;

        public ClassNamePackageDescriptor(String name){
            super();
            this.name = name;
        }

        public String getName(){
            return this.name;
        }
        public String toString(){
            return this.name;
        }
        public int hashCode(){
            return this.name.hashCode();
        }
        public boolean equals(Object that){
            if (this == that)
                return true;
            else if (null == that)
                return false;
            else 
                return this.toString().equals(that.toString());
        }
    }


    public final ClassName name;

    private String definitionClassName;


    public ClassNameDescriptor(ClassName name){
        super();
        if (null != name && name.qualified)
            this.name = name;
        else
            throw new IllegalArgumentException(name.toString());
    }


    public boolean hasPackage(){

        return true;
    }
    public PackageDescriptor getPackage(){

        return new ClassNamePackageDescriptor(this.name.pkg);
    }
    public String getPackageClassName(){

        return this.name.name;
    }
    public String getName(){

        return this.name.unqualified;
    }
    public boolean hasFields(){
        return false;
    }
    public lxl.List<FieldDescriptor> getFields(){

        return new lxl.ArrayList();
    }
    public boolean isFieldShort(FieldDescriptor field){
        return false;
    }
    public boolean hasMethods(){
        return false;
    }
    public lxl.List<MethodDescriptor> getMethods(){

        return new lxl.ArrayList();
    }
    public boolean hasDefinitionClassName(){

        return true;
    }
    public String getDefinitionClassName(){

        if (null != this.definitionClassName)
            return this.definitionClassName;
        else
            return this.name.name;
    }
    public void setDefinitionClassName(String cn){

        this.definitionClassName = cn;
    }
    public String toString(){
        return this.name.toString();
    }
    public int hashCode(){
        return this.name.hashCode();
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (null != that)
            return this.name.equals(that.toString());
        else
            return false;
    }
}
