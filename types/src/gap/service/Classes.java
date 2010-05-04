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
package gap.service;

import gap.Primitive;
import gap.data.HasName;
import gap.data.Kind;
import gap.data.TableClass;
import gap.service.od.ClassDescriptor;
import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;
import gap.service.od.PackageDescriptor;
import gap.service.od.ODStateException;
import gap.util.ClassName;
import gap.util.Resource;

import alto.io.u.Find;

import com.google.appengine.api.datastore.DataTypeUtils;

import java.io.File;
import java.util.StringTokenizer;

/** 
 * Class descriptors service.  A class descriptor describes a data
 * store type.  This code is common between odlc and runtime
 * requirements for class descriptor service.
 */
public class Classes {

    private final static lxl.Map<ClassName,Resource> R = new lxl.Map<ClassName,Resource>();

    private final static lxl.Map<ClassName,ClassDescriptor> C = new lxl.Map<ClassName,ClassDescriptor>();

    public final static Iterable<Resource> Resources(){
        return R.values();
    }
    public final static Iterable<ClassDescriptor> Classes(){
        return C.values();
    }

    public final static ClassDescriptor For(ClassName name)
        throws java.io.IOException, gap.odl.Syntax
    {
        ClassDescriptor cd;
        synchronized(C){
            cd = C.get(name);
        }
        if (null == cd){
            Resource resource = R.get(name);
            if (null != resource){
                gap.odl.Reader rdr = new gap.odl.Reader(resource);
                try {
                    cd = (new gap.odl.Class(rdr));
                }
                finally {
                    rdr.close();
                }
                synchronized(C){
                    C.put(name,cd);
                }
            }
        }
        return cd;
    }
    public final static ClassDescriptor For(File file)
        throws java.io.IOException, gap.odl.Syntax
    {
        Resource resource = new Resource(file);
        ClassName name = new ClassName(file);
        synchronized(R){
            R.put(name,resource);
        }
        ClassDescriptor cd;
        synchronized(C){
            cd = C.get(name);
        }
        if (null == cd){
            gap.odl.Reader rdr = new gap.odl.Reader(resource);
            try {
                cd = (new gap.odl.Class(rdr));
            }
            finally {
                rdr.close();
            }
            synchronized(C){
                C.put(name,cd);
            }
        }
        return cd;
    }
    public final static ClassDescriptor For(HasName named)
        throws java.io.IOException, gap.odl.Syntax
    {
        return Classes.For(named.getName());
    }
    public final static ClassDescriptor For(String name)
        throws java.io.IOException, gap.odl.Syntax
    {
        ClassName cn = new ClassName(name);
        if (cn.qualified)
            cn = new ClassName(cn.unqualified);

        return Classes.For(cn);
    }
    public final static ClassDescriptor For(Class<? extends TableClass> jclass)
        throws java.io.IOException, gap.odl.Syntax
    {
        if (null != jclass){
            String path = "odl/"+jclass.getName().replace('.','/')+".odl";
            return For(new File(path));
        }
        else
            throw new IllegalArgumentException();
    }
    public final static ClassDescriptor ForData(String name)
        throws java.io.IOException, gap.odl.Syntax
    {
        if (null != name && name.endsWith("Data"))
            return Classes.For(name.substring(0,name.length()-4));
        else
            return null;

    }
    public final static ClassDescriptor ForServlet(String name)
        throws java.io.IOException, gap.odl.Syntax
    {
        if (null != name && name.endsWith("Servlet")){
            name = name.substring(0,name.length()-7);
            int idx = name.lastIndexOf('.');
            if (-1 != idx)
                name = name.substring(idx+1);
            return Classes.For(name);
        }
        else
            return null;
    }
    /**
     * @param find Listing of odl files
     * @return Number of files found from listing
     */
    public final static int Resources(Find find){
        int count = 0;
        while (find.hasNext()){
            Resource file = new Resource(find.next());
            ClassName name = new ClassName(file);
            synchronized(R){
                R.put(name,file);
            }
            count += 1;
        }
        return count;
    }
    public final static String Camel(String string){
        if (null != string){
            int strlen = string.length();
            if (0 != strlen){
                if (1 != strlen)
                    return (string.substring(0,1).toUpperCase()+string.substring(1));
                else
                    return string.toUpperCase();
            }
            else
                throw new IllegalArgumentException();
        }
        else
            throw new IllegalArgumentException();
    }
    public final static String Decamel(String string){
        if (1 < string.length())
            return (string.substring(0,1).toLowerCase()+string.substring(1));
        else
            return string.toLowerCase();
    }
    public final static String ClassVersion(ClassDescriptor cd){
        if (cd instanceof ClassDescriptor.Version){
            ClassDescriptor.Version cv = (ClassDescriptor.Version)cd;
            if (cv.hasVersion()){
                return cv.getVersion().toString();
            }
        }
        return "1";
    }
    public final static String ClassKind(ClassDescriptor cd){
        if (cd instanceof ClassDescriptor.Kind){
            ClassDescriptor.Kind cdk = (ClassDescriptor.Kind)cd;
            if (cdk.hasKind())
                return cdk.getKind();
        }
        String definitionClassName = cd.getDefinitionClassName();
        if (null != definitionClassName){
            try {
                Class clas = null;
                try {
                    clas = Class.forName(definitionClassName,true,Thread.currentThread().getContextClassLoader());
                }
                catch (SecurityException retry){
                    clas = Class.forName(definitionClassName);
                }
                java.lang.reflect.Field fieldKind = clas.getField("KIND");
                return (String)fieldKind.get(null);
            }
            catch (Exception exc){
            }
        }
        return cd.getName();
    }
    public final static String ClassPath(ClassDescriptor cd){
        if (cd instanceof ClassDescriptor.Path){
            ClassDescriptor.Path cdp = (ClassDescriptor.Path)cd;
            if (cdp.hasPath())
                return cdp.getPath();
            else
                throw new ODStateException(cd,"OD Model requires 'path' field of class.");
        }
        else
            return null;
    }
    public final static String ClassSortBy(ClassDescriptor cd){
        if (cd instanceof ClassDescriptor.SortBy){
            ClassDescriptor.SortBy cdk = (ClassDescriptor.SortBy)cd;
            if (cdk.hasSortBy())
                return cdk.getSortBy();
        }
        String definitionClassName = cd.getDefinitionClassName();
        if (null != definitionClassName){
            try {
                Class clas = null;
                try {
                    clas = Class.forName(definitionClassName,true,Thread.currentThread().getContextClassLoader());
                }
                catch (SecurityException retry){
                    clas = Class.forName(definitionClassName);
                }
                return ClassSortBy(clas);
            }
            catch (Exception exc){
            }
        }
        return null;
    }
    public final static String ClassSortBy(Class<? extends gap.data.TableClass> table){
        if (null != table){
            try {
                java.lang.reflect.Field fieldDefaultSortBy = table.getField("DefaultSortBy");
                return (String)fieldDefaultSortBy.get(null);
            }
            catch (Exception exc){
            }
        }
        return null;
    }
    public final static String[] ClassImplements(ClassDescriptor cd){
        if (cd instanceof ClassDescriptor.Implements){
            ClassDescriptor.Implements cim = (ClassDescriptor.Implements)cd;
            if (cim.hasInterfaces()){
                lxl.List<Object> ili = cim.getInterfaces();
                int count = ili.size();
                if (0 != count){
                    String[] re = new String[count];
                    for (int cc = 0; cc < count; cc++)
                        re[cc] = ili.get(cc).toString();

                    return re;
                }
            }
        }
        return new String[0];
    }
    public final static Class FieldClass(PackageDescriptor pkg, FieldDescriptor field, lxl.List<ImportDescriptor> imports){
        String packageName = Classes.PackageName(pkg);
        String typeName = Classes.ToString(field.getType());
        return Classes.FieldClass(packageName,typeName,imports);
    }
    public final static Class FieldClass(String pkg, String fieldType, lxl.List<ImportDescriptor> imports){
        String cleanTypeName = CleanTypeName(fieldType);
        Primitive primitive = Primitive.For(cleanTypeName);
        if (null != primitive)
            return primitive.type;
        else {
            gap.data.List.Type listType = gap.data.List.Type.For(cleanTypeName);
            if (null != listType)
                return listType.type;
            else {
                gap.data.Map.Type mapType = gap.data.Map.Type.For(cleanTypeName);
                if (null != mapType)
                    return mapType.type;
                else {
                    try {
                        return java.lang.Class.forName(cleanTypeName);
                    }
                    catch (java.lang.ClassNotFoundException exc){
                    }
                    for (ImportDescriptor imp : imports){
                        java.lang.Class clas = ClassFor(cleanTypeName,imp);
                        if (null != clas)
                            return clas;
                    }
                    try {
                        String classname = pkg+'.'+cleanTypeName;
                        return java.lang.Class.forName(classname);
                    }
                    catch (java.lang.ClassNotFoundException exc){
                    }
                    try {
                        String classname = "java.lang."+cleanTypeName;
                        return java.lang.Class.forName(classname);
                    }
                    catch (java.lang.ClassNotFoundException exc){
                    }
                    return null;
                }
            }
        }
    }
    public final static String ListChildClassName(FieldDescriptor field){
        String typeName = Classes.ToString(field.getType());
        String[] parameters = FieldTypeParameters(typeName);
        if (null != parameters && 1 == parameters.length)
            return parameters[0];
        else
            return null;
    }
    /**
     * Parse map type components.  A map type field declaration
     * requires its key component declared from the child class in
     * 'FieldType:fieldName' format, as in
     * 'Map.Short&lt;String:name,TableClass&gt;' (for field type
     * 'String' name 'name' in child 'TableClass').
     */
    public final static class MapChild
        extends Object
    {
        public final static String ClassName(FieldDescriptor field){
            String typeName = Classes.ToString(field.getType());
            String[] parameters = FieldTypeParameters(typeName);
            if (null != parameters && 2 == parameters.length)
                return parameters[1];
            else
                return null;
        }
        /**
         * Normalize Type string.
         */
        public final static String Type(String type){
            int idx0 = type.indexOf(':');
            if (-1 != idx0){
                int idx1 = type.indexOf(',',idx0);
                if (-1 != idx1){
                    return (type.substring(0,idx0)+type.substring(idx1));
                }
            }
            return type;
        }
        /**
         * Normalize Type parameter string.
         */
        public final static String TypeKey(String type){
            int idx = type.indexOf(':');
            if (-1 != idx)
                return (type.substring(0,idx));
            else
                return type;
        }

        public final String fieldTypeDeclaration, fieldTypeName;
        public final gap.data.Map.Type mapType;
        public final gap.Primitive keyType;
        public final String childKeyFieldType, childKeyFieldName, childValueClassName;

        public MapChild(FieldDescriptor field)
            throws ODStateException
        {
            super();
            String fieldTypeDeclaration = Classes.ToString(field.getType());
            this.fieldTypeDeclaration = fieldTypeDeclaration;
            this.mapType = gap.data.Map.Type.For(fieldTypeDeclaration);
            String[] parameters = FieldTypeParameters(fieldTypeDeclaration);
            if (null != parameters && 2 == parameters.length){
                this.childValueClassName = parameters[1];
                String childKeyField = parameters[0];
                int idx = childKeyField.indexOf(':');
                if (-1 != idx){
                    this.childKeyFieldType = childKeyField.substring(0,idx);
                    try {
                        this.keyType = gap.Primitive.valueOf(this.childKeyFieldType);
                        this.childKeyFieldName = childKeyField.substring(idx+1);
                        this.fieldTypeName = this.mapType.dotName+'<'+this.childKeyFieldType+','+this.childValueClassName+'>';
                    }
                    catch (IllegalArgumentException notPrimitive){
                        throw new ODStateException(field,"Map type parameter key '"+this.childKeyFieldType+"' not primitive.",notPrimitive);
                    }
                }
                else
                    throw new ODStateException(field,"Map type requires key component 'type:fieldName' as in 'Map.Short<String:name,TableClass>'.");
            }
            else
                throw new ODStateException(field,"Map type parameters not found.");
        }
    }
    public final static boolean IsClassRelationPrimary(ClassDescriptor cd){
        if (cd instanceof ClassDescriptor.Relation){
            ClassDescriptor.Relation cdr = (ClassDescriptor.Relation)cd;
            return cdr.isRelationPrimary();
        }
        else
            return true;
    }
    public final static String[] FieldTypeParameters(String typeName){
        int start = typeName.indexOf('<');
        if (-1 != start){
            String parameters = typeName.substring((start+1),(typeName.length()-1)).trim();
            StringTokenizer strtok = new StringTokenizer(parameters,", ");
            int count = strtok.countTokens();
            String[] list = new String[count];
            for (int cc = 0; cc < count; cc++){
                String token = strtok.nextToken();
                if ('<' != token.charAt(0))
                    list[cc] = token;
                else
                    throw new IllegalStateException(typeName);
            }
            return list;
        }
        return new String[0];
    }
    public final static boolean IsFieldPersistent(FieldDescriptor field, Class fieldTypeClass){

        if (IsTypeClassCollection(fieldTypeClass))

            return false;

        else if (IsFieldRelation(field)) /*(very template specific -- will change)
                                     */
            return false;

        else if (IsTypeClassIndexed(fieldTypeClass) && field instanceof FieldDescriptor.Persistence){
            FieldDescriptor.Persistence pfield = (FieldDescriptor.Persistence)field;
            if (pfield.hasPersistence()){
                if (FieldDescriptor.Persistence.Type.Transient.equals(pfield.getPersistence()))
                    return false;
                else
                    return true;
            }
            else
                return true;
        }
        else
            return true;
    }
    public final static boolean IsFieldUnique(FieldDescriptor field){
        if (field instanceof FieldDescriptor.Uniqueness){
            FieldDescriptor.Uniqueness ufield = (FieldDescriptor.Uniqueness)field;
            return (ufield.isUnique());
        }
        else
            return false;
    }
    public final static boolean IsFieldRelation(FieldDescriptor field){
        if (field instanceof FieldDescriptor.Relation){
            FieldDescriptor.Relation rfield = (FieldDescriptor.Relation)field;
            if (rfield.hasRelation()){
                FieldDescriptor.Relation.Type rtype = rfield.getRelation();
                if (FieldDescriptor.Relation.Type.None.equals(rtype))
                    return false;
                else
                    return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    public final static boolean IsFieldRelationChild(FieldDescriptor field){
        if (field instanceof FieldDescriptor.Relation){
            FieldDescriptor.Relation rfield = (FieldDescriptor.Relation)field;
            if (rfield.hasRelation()){
                FieldDescriptor.Relation.Type rtype = rfield.getRelation();
                if (FieldDescriptor.Relation.Type.Child.equals(rtype))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    public final static boolean IsFieldRelationParent(FieldDescriptor field){
        if (field instanceof FieldDescriptor.Relation){
            FieldDescriptor.Relation rfield = (FieldDescriptor.Relation)field;
            if (rfield.hasRelation()){
                FieldDescriptor.Relation.Type rtype = rfield.getRelation();
                if (FieldDescriptor.Relation.Type.Parent.equals(rtype))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    public final static boolean IsFieldDefaultSortBy(FieldDescriptor field){
        if (field instanceof FieldDescriptor.DefaultSortBy){
            FieldDescriptor.DefaultSortBy sfield = (FieldDescriptor.DefaultSortBy)field;
            return sfield.isDefaultSortBy();
        }
        else
            return false;
    }
    public final static boolean IsTypeClassKey(java.lang.Class fieldType){
        if (null != fieldType)
            return (com.google.appengine.api.datastore.Key.class.equals(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassIndexed(java.lang.Class fieldType){
        if (null != fieldType)
            return DataTypeUtils.isSupportedType(fieldType);
        else
            return false;
    }
    public final static boolean IsTypeClassList(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.List.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassBigTable(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.TableClass.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsNotTypeClassBigTable(java.lang.Class fieldType){
        if (null != fieldType)
            return (!(gap.data.TableClass.class.isAssignableFrom(fieldType)));
        else
            return true;
    }
    public final static boolean IsTypeClassMap(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.Map.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassString(java.lang.Class fieldType){
        if (null != fieldType)
            return (java.lang.String.class.equals(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassDate(java.lang.Class fieldType){
        if (null != fieldType)
            return (java.util.Date.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassCollection(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.Collection.class.isAssignableFrom(fieldType));
        else
            return true;
    }
    public final static boolean IsNotTypeClassCollection(java.lang.Class fieldType){
        if (null != fieldType)
            return (!(gap.data.Collection.class.isAssignableFrom(fieldType)));
        else
            return true;
    }
    public final static String CleanTypeName(String name){
        int idx = name.indexOf('<');
        if (-1 != idx)
            return name.substring(0,idx);
        else
            return name;
    }
    public final static String CleanCleanTypeName(String name){
        int idx = name.indexOf('.');
        if (-1 != idx)
            return name.substring(0,idx);
        else
            return name;
    }
    private final static Class ClassFor(String cleanTypeName, ImportDescriptor imp){
        if (imp.hasPackageSpec()){
            String packageSpec = imp.getPackageSpec();
            if (packageSpec.endsWith(".*")){
                packageSpec = packageSpec.substring(0,packageSpec.length()-1);
                String packageClassName = packageSpec+cleanTypeName;
                try {
                    return Class.forName(packageClassName);
                }
                catch (ClassNotFoundException exc){
                    return null;
                }
            }
            else
                throw new ODStateException(imp,"Import descriptor package spec missing dot-star suffix '"+packageSpec+"'.");
        }
        else if (imp.hasClassName()){
            String packageClassName = imp.getClassName();
            if (packageClassName.endsWith("."+cleanTypeName)){
                try {
                    return Class.forName(packageClassName);
                }
                catch (ClassNotFoundException exc){
                    return null;
                }
            }
            else
                return null;
        }
        else
            return null;
    }
    public final static String ToString(Object object){
        if (object instanceof String)
            return (String)object;
        else if (object instanceof HasName)
            return ((HasName)object).getName();
        else 
            return object.toString();
    }
    public final static String PackageName(PackageDescriptor pkg)
        throws ODStateException
    {
        String packageName = pkg.getName();
        if (null == packageName || 0 == packageName.length())
            throw new ODStateException(pkg,"The object data model requires a package name.");

        return Decamel(packageName);
    }
    public final static String ClassName(ClassDescriptor cd)
        throws ODStateException
    {
        String className = cd.getName();
        if (null == className || 0 == className.length())
            throw new ODStateException(cd,"The object data model requires a class name.");
        else
            return Camel(className);
    }
    public final static String ListClassName(String fieldType, String parentClassName, String childClassName){
        gap.data.List.Type listType = gap.data.List.Type.For(fieldType);
        switch(listType){
        case ListPrimitive:
            return ListPrimitiveClassName(childClassName);
        case ListShort:
            return ListShortClassName(parentClassName,childClassName);
        case ListLong:
            return ListLongClassName(parentClassName,childClassName);
        default:
            throw new IllegalStateException("Unrecognized type '"+fieldType+"'.");
        }
    }
    public final static String ListPrimitiveClassName(String childClassName){
        if (null != childClassName)
            return "ListPrimitive"+childClassName;
        else
            return null;
    }
    public final static String ListShortClassName(ClassDescriptor cd, FieldDescriptor fd){
        String parentClassName = ClassName(cd);
        String childClassName = ListChildClassName(fd);
        return ListShortClassName(parentClassName,childClassName);
    }
    public final static String ListShortClassName(String parentClassName, String childClassName){
        if (null != parentClassName && null != childClassName)
            return "ListShort"+parentClassName+childClassName;
        else
            return null;
    }
    public final static String ListLongClassName(ClassDescriptor cd, FieldDescriptor fd){
        String parentClassName = ClassName(cd);
        String childClassName = ListChildClassName(fd);
        return ListLongClassName(parentClassName,childClassName);
    }
    public final static String ListLongClassName(String parentClassName, String childClassName){
        if (null != parentClassName && null != childClassName)
            return "ListLong"+parentClassName+childClassName;
        else
            return null;
    }
    public final static String MapClassName(String fieldType, String parentClassName, String typeComponentFrom, String typeComponentTo){
        gap.data.Map.Type mapType = gap.data.Map.Type.For(fieldType);
        switch(mapType){
        case MapPrimitive:
            return MapPrimitiveClassName(parentClassName,typeComponentFrom,typeComponentTo);
        case MapShort:
            return MapShortClassName(parentClassName,typeComponentFrom,typeComponentTo);
        case MapLong:
            return MapLongClassName(parentClassName,typeComponentFrom,typeComponentTo);
        default:
            throw new IllegalStateException("Unrecognized type '"+fieldType+"'.");
        }
    }
    public final static String MapPrimitiveClassName(String parentClassName, String typeComponentFrom, String typeComponentTo){
        if (null != typeComponentFrom && null != typeComponentTo)
            return "MapPrimitive"+typeComponentFrom+typeComponentTo;
        else
            return null;
    }
    public final static String MapShortClassName(String parentClassName, Classes.MapChild mapChild){
        if (null != parentClassName && null != mapChild)
            return MapShortClassName(parentClassName,mapChild.childKeyFieldType,mapChild.childValueClassName);
        else
            throw new IllegalArgumentException();
    }
    public final static String MapShortClassName(String parentClassName, String typeComponentFrom, String typeComponentTo){
        if (null != parentClassName && null != typeComponentFrom && null != typeComponentTo)
            return "MapShort"+parentClassName+typeComponentFrom+typeComponentTo;
        else
            return null;
    }
    public final static String MapLongClassName(String parentClassName, Classes.MapChild mapChild){
        if (null != parentClassName && null != mapChild)
            return MapLongClassName(parentClassName,mapChild.childKeyFieldType,mapChild.childValueClassName);
        else
            throw new IllegalArgumentException();
    }
    public final static String MapLongClassName(String parentClassName, String typeComponentFrom, String typeComponentTo){
        if (null != parentClassName && null != typeComponentFrom && null != typeComponentTo)
            return "MapLong"+parentClassName+typeComponentFrom+typeComponentTo;
        else
            return null;
    }
    public final static FieldDescriptor KeyField(String packageName, ClassDescriptor cd, lxl.List<ImportDescriptor> imports){
        if (cd.hasFields()){
            for (FieldDescriptor field : cd.getFields()){
                String fieldType = ToString(field.getType());
                Class fieldTypeClass = FieldClass(packageName,fieldType,imports);
                if (IsTypeClassKey(fieldTypeClass)){
                    return field;
                }
            }
        }
        return null;
    }
    public final static FieldDescriptor FindFieldFor(ClassDescriptor parent, ClassDescriptor child){
        if (null != parent && null != child){
            if (parent.hasFields()){
                for (FieldDescriptor parentField : parent.getFields()){
                    String parentFieldType = ToString(parentField.getType());
                    gap.data.List.Type listType = gap.data.List.Type.For(parentFieldType);
                    if (null != listType){
                        String[] parentFieldTypeParameters = FieldTypeParameters(parentFieldType);
                        if (null != parentFieldTypeParameters && 1 == parentFieldTypeParameters.length){
                            String childFieldType = parentFieldTypeParameters[0];
                            if (childFieldType.equals(child.getName()))
                                return parentField;
                        }
                    }
                    else {
                        gap.data.Map.Type mapType = gap.data.Map.Type.For(parentFieldType);
                        if (null != mapType){
                            String[] parentFieldTypeParameters = FieldTypeParameters(parentFieldType);
                            if (null != parentFieldTypeParameters && 2 == parentFieldTypeParameters.length){
                                String childFieldType = parentFieldTypeParameters[1];
                                if (childFieldType.equals(child.getName()))
                                    return parentField;
                            }
                        }
                    }
                }
            }
            throw new ODStateException(child,"Child relation field not found in parent.");
        }
        else
            throw new IllegalArgumentException();
    }
    public final static boolean IsFieldShortIn(ClassDescriptor parent, ClassDescriptor child){
        FieldDescriptor parentField = FindFieldFor(parent,child);
        String parentFieldType = ToString(parentField.getType());
        gap.data.List.Type listType = gap.data.List.Type.For(parentFieldType);
        if (null != listType){
            switch (listType){
            case ListPrimitive:
                return false;
            case ListLong:
                return false;
            case ListShort:
                return true;
            default:
                throw new ODStateException(child,"Unrecognized list type.");
            }
        }
        else {
            gap.data.Map.Type mapType = gap.data.Map.Type.For(parentFieldType);
            if (null != mapType){
                switch (mapType){
                case MapPrimitive:
                    return false;
                case MapLong:
                    return false;
                case MapShort:
                    return true;
                default:
                    throw new ODStateException(child,"Unrecognized map type.");
                }
            }
            else
                throw new ODStateException(child,"Unrecognized field type.");
        }
    }
    public final static lxl.List<FieldDescriptor> FieldsOfTypeList(PackageDescriptor pkg, ClassDescriptor cd, lxl.List<ImportDescriptor> imports){
        lxl.List<FieldDescriptor> re = new lxl.ArrayList<FieldDescriptor>();
        if (cd.hasFields()){
            String packageName = Classes.PackageName(pkg);
            for (FieldDescriptor field : cd.getFields()){
                String typeName = Classes.ToString(field.getType());
                Class typeClass = Classes.FieldClass(packageName,typeName,imports);
                if (IsTypeClassList(typeClass)){
                    re.add(field);
                }
            }
        }
        return re;
    }
    public final static lxl.List<FieldDescriptor> FieldsOfTypeMap(PackageDescriptor pkg, ClassDescriptor cd, lxl.List<ImportDescriptor> imports){
        lxl.List<FieldDescriptor> re = new lxl.ArrayList<FieldDescriptor>();
        if (cd.hasFields()){
            String packageName = Classes.PackageName(pkg);
            for (FieldDescriptor field : cd.getFields()){
                String typeName = Classes.ToString(field.getType());
                Class typeClass = Classes.FieldClass(packageName,typeName,imports);
                if (IsTypeClassMap(typeClass)){
                    re.add(field);
                }
            }
        }
        return re;
    }
    public final static boolean IsTypeOf(String typeName, String interfaceName){
        if (gap.Primitive.Is(typeName))
            return false;
        else {
            try {
                ClassDescriptor type = Classes.For(typeName);
                if (null != type){
                    if (type instanceof ClassDescriptor.Implements){
                        ClassDescriptor.Implements typei = (ClassDescriptor.Implements)type;
                        if (typei.hasInterfaces()){
                            for (Object inf : typei.getInterfaces()){
                                String infName = ToString(inf);
                                if (interfaceName.equals(infName))
                                    return true;
                            }
                        }
                    }
                }
            }
            catch (Exception ignore){
                ignore.printStackTrace();//(only likely at dev time)
            }
            return false;
        }
    }
    public final static String WebXmlPathStar(ClassDescriptor cd){
        String path = ClassPath(cd);
        if (null != path && 0 != path.length()){
            StringBuilder string = new StringBuilder();
            if ('/' != path.charAt(0))
                string.append('/');
            string.append(path);
            string.append("/*");
            return string.toString();
        }
        else
            throw new IllegalStateException("Missing path for class '"+cd.getName()+"'");
    }
}
