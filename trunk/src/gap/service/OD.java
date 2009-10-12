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

import gap.data.HasName;
import static gap.data.List.Type.*;
import gap.service.od.ClassDescriptor;
import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;
import gap.service.od.MethodDescriptor;
import gap.service.od.ODStateException;
import gap.service.od.PackageDescriptor;

import hapax.Template;
import hapax.TemplateCache;
import hapax.TemplateDictionary;
import hapax.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.StringTokenizer;


/**
 * Object data model for generating JPL source from description.
 * 
 * @author jdp
 */
public class OD
    extends java.lang.Object
{
    /**
     * Generate OD sourcecode into the output writer.
     * 
     * @exception gap.service.od.ODStateException Unable to generate source from description, model incomplete.
     * @exception java.io.IOException Error writing to output.
     * @exception hapax.TemplateException Error processing template.
     */
    public final static void GenerateBeanSource(File xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
                                                ClassDescriptor cd, PrintWriter out)
        throws gap.service.od.ODStateException, java.io.IOException, hapax.TemplateException
    {
        if (null != xtm && null != pkg && null != imports && null != cd && null != out){
            TemplateCache loader = new TemplateCache(xtm.getParent());
            Template template = loader.getTemplate(xtm.getPath());
            TemplateDictionary top = new TemplateDictionary();

            String packageName = PackageName(pkg);

            String className = ClassName(cd);

            String classNameDecamel = Decamel(className);

            String classVersion = ClassVersion(cd);

            String classKind = ClassKind(cd);

            String classPath = ClassPath(cd);

            String defaultSortBy = null;

            /*
             * Tool globals
             */
            top.putVariable("odl_gen_class","gap.service.OD");
            top.putVariable("odl_gen_xtm_src",xtm.getPath());
            top.putVariable("odl_gen_odl_src",packageName);
            top.putVariable("odl_gen_timestamp",(gap.Date.FormatISO8601(System.currentTimeMillis())));

            /*
             * General 
             */
            {
                TemplateDictionary primitives = top.addSection("primitives");
                for (gap.Primitive type : gap.Primitive.values()){
                    TemplateDictionary primitive = primitives.addSection("primitives");
                    String type_name = type.name();
                    primitive.setVariable("type_name",type_name);
                    primitive.setVariable("type_nameCamel",type_name);
                    primitive.setVariable("type_nameDecamel",Decamel(type_name));
                    primitive.addSection(type_name);
                }
            }

            /*
             * Class globals
             */
            top.putVariable("package_name", packageName);
            top.putVariable("class_name", className);
            top.putVariable("class_nameDecamel", classNameDecamel);
            top.putVariable("class_version",classVersion);
            top.putVariable("class_kind", classKind);
            top.putVariable("class_path", classPath);

            ClassDescriptor.Relation.Type classRelation = null;
            String classRelationParent = null;
            ClassDescriptor parent = null;
            if (cd instanceof ClassDescriptor.Relation){
                ClassDescriptor.Relation cdr = (ClassDescriptor.Relation)cd;
                classRelation = cdr.getRelation();
                classRelationParent = cdr.getParent();
            }
            if (null == classRelation || ClassDescriptor.Relation.Type.None.equals(classRelation)){
                top.addSection("class_re_none");
                top.addSection("class_re_not_parent");
                top.addSection("class_re_not_child");
                top.addSection("class_re_not_childgroup");
                top.addSection("class_re_not_child_or_group");
            }
            else if (ClassDescriptor.Relation.Type.Parent.equals(classRelation)){
                top.addSection("class_re_not_none");
                top.addSection("class_re_parent");
                top.addSection("class_re_not_child");
                top.addSection("class_re_not_childgroup");
                top.addSection("class_re_not_child_or_group");
            }
            else if (ClassDescriptor.Relation.Type.Child.equals(classRelation)){
                top.addSection("class_re_not_none");
                top.addSection("class_re_not_parent");
                top.addSection("class_re_not_childgroup");
                top.addSection("class_re_child_or_group");

                TemplateDictionary child = top.addSection("class_re_child");

                if (null == classRelationParent)
                    throw new ODStateException(cd,"The object data model requires a parent class name.");
                else {

                    top.setVariable("parent_class_name",classRelationParent);

                    parent = gap.odl.Main.ClassDescriptorFor(classRelationParent);
                    if (null == parent)
                        throw new ODStateException(cd,"Parent class not found.");
                }
            }
            else if (ClassDescriptor.Relation.Type.ChildGroup.equals(classRelation)){
                top.addSection("class_re_not_none");
                top.addSection("class_re_not_parent");
                top.addSection("class_re_not_child");
                top.addSection("class_re_child_or_group");

                top.addSection("class_re_childgroup");
                if (null == classRelationParent)
                    throw new ODStateException(cd,"The object data model requires a parent class name.");
                else {

                    top.setVariable("parent_class_name",classRelationParent);

                    parent = gap.odl.Main.ClassDescriptorFor(classRelationParent);
                    if (null == parent)
                        throw new ODStateException(cd,"Parent class not found.");
                }
            }
            else
                throw new IllegalStateException("Unrecognized class relation "+classRelation.name());

            cd.setDefinitionClassName(packageName+'.'+className);

            /*
             * Imports
             */
            for (ImportDescriptor imp : imports){
                TemplateDictionary imd = top.addSection("import");
                if (imp.hasPackageSpec())
                    imd.putVariable("import_spec",imp.getPackageSpec());
                else if (imp.hasClassName())
                    imd.putVariable("import_spec",imp.getClassName());
            }

            String[] interfaces = ClassImplements(cd);
            for (String inf : interfaces){
                TemplateDictionary ind = top.addSection("implements");
                ind.putVariable("interface_class",inf);
            }

            /*
             * Fields & data
             */
            FieldDescriptor key = null, unique = null;

            if (cd.hasFields()){

                for (FieldDescriptor field : cd.getFields()){

                    String fieldName = field.getName();
                    String fieldNameCamel = Camel(fieldName);
                    String fieldType = ToString(field.getType());
                    String fieldTypeClean = CleanTypeName(fieldType);
                    String fieldTypeCleanClean = CleanCleanTypeName(fieldType);
                    Class fieldTypeClass = FieldClass(packageName,fieldType,imports);
                    String[] fieldTypeParameters = FieldTypeParameters(fieldType);
                    TemplateDictionary dataField = null;
                    boolean isPersistent = false;
                    boolean isInheritable = true;
                    boolean isRelation = false;
                    boolean isCollection = false;
                    boolean isTransient = false;

                    /*
                     * Create 'dataField' section
                     */
                    if (IsFieldPersistent(field,fieldTypeClass)){
                        isPersistent = true;

                        dataField = top.addSection("pfield");

                        /*
                         * Populate 'pfield' section
                         */
                        if (IsFieldHashUnique(field)){
                            isInheritable = false;

                            dataField.addSection("field_is_not_unique");
                            dataField.addSection("field_is_not_inheritable");

                            TemplateDictionary field_is = dataField.addSection("field_is_hash_unique");

                            field_is.putVariable("data_model","*hash-unique");

                            if (IsTypeClassString(fieldTypeClass)){
                                dataField.putVariable("field_to_string_prefix","");
                                dataField.putVariable("field_to_string_suffix","");
                            }
                            else if (IsTypeClassDate(fieldTypeClass)){
                                dataField.putVariable("field_to_string_prefix","gap.Date.FormatISO8601(");
                                dataField.putVariable("field_to_string_suffix",")");
                            }
                            else {
                                dataField.putVariable("field_to_string_prefix","");
                                dataField.putVariable("field_to_string_suffix",".toString()");
                            }

                            defaultSortBy = fieldName;

                            /*
                             * Global section 'field_hash_unique'
                             */
                            TemplateDictionary topDataFieldH = top.showSection("field_hash_unique").get(0);

                            TemplateDictionary topDataFieldHF = topDataFieldH.addSection("field");

                            topDataFieldHF.putVariable("field_name",fieldName);
                            topDataFieldHF.putVariable("field_nameCamel",fieldNameCamel);
                            topDataFieldHF.putVariable("field_class",fieldType);
                            topDataFieldHF.putVariable("field_classClean",fieldTypeClean);
                            topDataFieldHF.putVariable("field_classCleanClean",fieldTypeCleanClean);
                        }
                        else if (IsFieldUnique(field)){
                            isInheritable = false;

                            dataField.addSection("field_is_not_hash_unique");
                            dataField.addSection("field_is_not_inheritable");

                            TemplateDictionary field_is = dataField.addSection("field_is_unique");

                            if (null == unique){

                                unique = field;

                                field_is.putVariable("data_model","*unique");

                                /*
                                 * Global section 'field_unique'
                                 */
                                TemplateDictionary topDataFieldU = top.showSection("field_unique").get(0);

                                topDataFieldU.putVariable("field_name",fieldName);
                                topDataFieldU.putVariable("field_nameCamel",fieldNameCamel);
                                topDataFieldU.putVariable("field_class",fieldType);
                                topDataFieldU.putVariable("field_classClean",fieldTypeClean);
                                topDataFieldU.putVariable("field_classCleanClean",fieldTypeCleanClean);

                                /*
                                 * Global field 'unique' references
                                 */
                                top.putVariable("field_unique_name",fieldName);
                                top.putVariable("field_unique_nameCamel",fieldNameCamel);
                                top.putVariable("field_unique_class",fieldType);
                                top.putVariable("field_unique_classClean",fieldTypeClean);
                            }
                            else
                                throw new ODStateException(field,"Model has more than one '*unique' field, '"+unique.getName()+"' and '"+fieldName+"'.");
                        }
                        else {
                            dataField.addSection("field_is_not_unique");
                            dataField.addSection("field_is_not_hash_unique");
                        }
                    }
                    else if (IsTypeClassCollection(fieldTypeClass)){
                        isCollection = true;

                        dataField = top.addSection("cfield");
                        /*
                         * Populate 'cfield' section
                         */
                        dataField.addSection("field_is_not_unique");
                        dataField.addSection("field_is_not_hash_unique");
                    }
                    else if (IsFieldRelation(field)){
                        isRelation = true;

                        dataField = top.addSection("rfield");
                        /*
                         * Populate 'rfield' section
                         */
                        dataField.addSection("field_is_not_unique");
                        dataField.addSection("field_is_not_hash_unique");

                        if ((!IsTypeClassKey(fieldTypeClass)) && null != fieldTypeClass && IsNotTypeClassBigTable(fieldTypeClass))
                            throw new ODStateException(field,"Relation field '"+fieldName+"' is not a subclass of 'gap.data.BigTable'.");

                    }
                    else {
                        isTransient = true;

                        dataField = top.addSection("tfield");
                        /*
                         * Populate 'tfield' section
                         */
                        dataField.addSection("field_is_not_unique");
                        dataField.addSection("field_is_not_hash_unique");
                        {
                            TemplateDictionary field_is = dataField.addSection("field_is_transient");
                            field_is.putVariable("data_model","*transient");
                        }
                    }

                    /*
                     * Populate 'dataField' name and type information
                     */
                    dataField.putVariable("field_name",fieldName);
                    dataField.putVariable("field_nameCamel",fieldNameCamel);
                    dataField.putVariable("field_class",fieldType);
                    dataField.putVariable("field_classClean",fieldTypeClean);
                    dataField.putVariable("field_classCleanClean",fieldTypeCleanClean);

                    if (IsTypeClassKey(fieldTypeClass)){
                        isInheritable = false;

                        dataField.addSection("field_is_not_inheritable");

                        TemplateDictionary field_is = dataField.addSection("field_is_key");

                        if (null == key){
                            key = field;

                            top.putVariable("field_key_name",fieldName);
                            top.putVariable("field_key_nameCamel",fieldNameCamel);
                            top.putVariable("field_key_class",fieldType);
                            top.putVariable("field_key_classClean",fieldTypeClean);
                        }
                    }
                    else if (IsTypeClassList(fieldTypeClass)){

                        dataField.addSection("field_is_not_map");

                        if (1 == fieldTypeParameters.length){

                            TemplateDictionary field_is = dataField.addSection("field_is_list");

                            String typeComponent = fieldTypeParameters[0];
                            field_is.putVariable("field_list_component",typeComponent);

                            if (IsTypeOf(typeComponent,"HasName"))
                                field_is.addSection("field_list_component_named");

                            dataField.putVariable("field_impl_class_name",ListClassName(fieldTypeClean,className,typeComponent));
                        }
                        else
                            throw new ODStateException(field,"Field '"+fieldName+"' type list missing type parameter.");
                    }
                    else if (IsTypeClassMap(fieldTypeClass)){

                        dataField.addSection("field_is_not_list");

                        if (2 == fieldTypeParameters.length){

                            TemplateDictionary field_is = dataField.addSection("field_is_map");

                            String typeComponentFrom = fieldTypeParameters[0];
                            String typeComponentTo = fieldTypeParameters[1];
                            field_is.putVariable("field_map_component_from",typeComponentFrom);
                            field_is.putVariable("field_map_component_to",typeComponentTo);
                        }
                        else
                            throw new ODStateException(field,"Field '"+fieldName+"' type map missing type parameter.");
                    }
                    else {
                        dataField.addSection("field_is_not_key");
                        if (isPersistent && isInheritable)
                            dataField.addSection("field_is_inheritable");
                    }
                }
            }

            /*
             * Methods
             */
            if (cd.hasMethods()){

                for (MethodDescriptor method: cd.getMethods()){

                    String method_name = method.getName();
                    if (null != method_name){
                        String method_body = gap.Strings.TextToString(method.getBody());
                        if (null != method_body){

                            TemplateDictionary methods = top.addSection("method");
                            methods.setVariable("method_name",method_name);
                            methods.setVariable("method_body",method_body);

                            TemplateDictionary mb = top.addSection("method_"+method_name+"_with_body");
                            mb.setVariable("body",method_body);

                            String method_type = ToString(method.getType());
                            if (null != method_type){
                                TemplateDictionary ma = top.addSection("method_"+method_name+"_with_type");
                                ma.setVariable("type",method_type);
                                methods.setVariable("method_type",method_type);
                            }
                            else
                                top.showSection("method_"+method_name+"_without_type");

                            if (method instanceof MethodDescriptor.Arguments){
                                MethodDescriptor.Arguments ma = (MethodDescriptor.Arguments)method;
                                if (ma.hasArguments()){
                                    String method_arguments = ma.getArguments();
                                    if (null != method_arguments){
                                        TemplateDictionary td = top.addSection("method_"+method_name+"_with_args");
                                        td.setVariable("args",method_arguments);
                                        methods.setVariable("method_arguments",method_arguments);
                                    }
                                    else
                                        top.showSection("method_"+method_name+"_without_args");
                                }
                                else
                                    top.showSection("method_"+method_name+"_without_args");
                            }
                            else
                                top.showSection("method_"+method_name+"_without_args");

                            if (method instanceof MethodDescriptor.Exceptions){
                                MethodDescriptor.Exceptions ma = (MethodDescriptor.Exceptions)method;
                                if (ma.hasExceptions()){
                                    String method_exceptions = ma.getExceptions();
                                    if (null != method_exceptions){
                                        TemplateDictionary td = top.addSection("method_"+method_name+"_with_excs");
                                        td.setVariable("excs",method_exceptions);
                                        methods.setVariable("method_exceptions",method_exceptions);
                                    }
                                    else
                                        top.showSection("method_"+method_name+"_without_excs");
                                }
                                else
                                    top.showSection("method_"+method_name+"_without_excs");
                            }
                            else
                                top.showSection("method_"+method_name+"_without_excs");
                        }
                        else {
                            top.showSection("method_"+method_name+"_without_body");
                        }
                    }
                }
            }

            /*
             * Current template model requires 'key'.
             */
            if (null != key){

                top.setVariable("class_defaultSortBy", defaultSortBy);

                /*
                 * Run template
                 */
                try {
                    template.render(top,out); 
                }
                catch (TemplateException exc){
                    throw new TemplateException("In "+xtm.getPath(),exc);
                }
            }
            else
                throw new ODStateException(cd,"Model requires a field having type 'com.google.appengine.api.datastore.Key'.");
        }
        else
            throw new IllegalArgumentException();
    }

    public final static void GenerateListSource(File xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
                                                ClassDescriptor parent, FieldDescriptor field, 
                                                String parentClassName, String childClassName, 
                                                String listClassName, gap.data.List.Type listType, 
                                                PrintWriter out)
        throws gap.service.od.ODStateException, java.io.IOException, hapax.TemplateException
    {
        if (null != xtm && null != pkg && null != imports && null != parent && null != field
            && null != parentClassName && null != childClassName && null != listClassName
            && null != listType && null != out)
        {
            TemplateCache loader = new TemplateCache(xtm.getParent());
            Template template = loader.getTemplate(xtm.getPath());
            TemplateDictionary top = new TemplateDictionary();

            String packageName = PackageName(pkg);

            String parentClassVersion = ClassVersion(parent);

            String parentClassKind = ClassKind(parent);

            String parentClassPath = ClassPath(parent);

            FieldDescriptor parentKeyField = KeyField(packageName,parent,imports);

            /*
             * Tool globals
             */
            top.putVariable("odl_gen_class","gap.service.OD");
            top.putVariable("odl_gen_xtm_src",xtm.getPath());
            top.putVariable("odl_gen_odl_src",packageName);
            top.putVariable("odl_gen_timestamp",(gap.Date.FormatISO8601(System.currentTimeMillis())));

            /*
             * General 
             */
            {
                TemplateDictionary primitives = top.addSection("primitives");
                for (gap.Primitive type : gap.Primitive.values()){
                    TemplateDictionary primitive = primitives.addSection("primitives");
                    String type_name = type.name();
                    primitive.setVariable("type_name",type_name);
                    primitive.setVariable("type_nameCamel",type_name);
                    primitive.setVariable("type_nameDecamel",Decamel(type_name));
                    primitive.addSection(type_name);
                }
            }

            /*
             * Class globals
             */
            top.setVariable("package_name",packageName);
            top.setVariable("parent_class_version",parentClassVersion);
            top.setVariable("parent_class_name",parentClassName);
            top.setVariable("parent_class_kind",parentClassKind);
            top.setVariable("parent_class_path",parentClassPath);
            top.setVariable("parent_keyfield_name",parentKeyField.getName());
            top.setVariable("child_class_name",childClassName);
            top.setVariable("list_class_name",listClassName);

            /*
             * Run template
             */
            try {
                template.render(top,out); 
            }
            catch (TemplateException exc){
                throw new TemplateException("In "+xtm.getPath(),exc);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static List<FieldDescriptor> FieldsOfTypeList(PackageDescriptor pkg, ClassDescriptor cd, List<ImportDescriptor> imports){
        List<FieldDescriptor> re = new java.util.ArrayList<FieldDescriptor>();
        if (cd.hasFields()){
            String packageName = OD.PackageName(pkg);
            for (FieldDescriptor field : cd.getFields()){
                String typeName = OD.ToString(field.getType());
                Class typeClass = OD.FieldClass(packageName,typeName,imports);
                if (IsTypeClassList(typeClass)){
                    re.add(field);
                }
            }
        }
        return re;
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
        if (cd.hasPath())
            return cd.getPath();
        else
            throw new ODStateException(cd,"OD Model requires 'path' field of class.");
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
    public final static String ClassSortBy(Class<? extends gap.data.BigTable> table){
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
                List<Object> ili = cim.getInterfaces();
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
    public final static Class FieldClass(PackageDescriptor pkg, FieldDescriptor field, List<ImportDescriptor> imports){
        String packageName = OD.PackageName(pkg);
        String typeName = OD.ToString(field.getType());
        return OD.FieldClass(packageName,typeName,imports);
    }
    public final static Class FieldClass(String pkg, String fieldType, List<ImportDescriptor> imports){
        String cleanTypeName = CleanTypeName(fieldType);
        gap.Primitive primitive = gap.Primitive.For(cleanTypeName);
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
    public final static String ChildClassName(FieldDescriptor field){
        String typeName = OD.ToString(field.getType());
        String[] parameters = FieldTypeParameters(typeName);
        if (null != parameters && 1 == parameters.length)
            return parameters[0];
        else
            return null;
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
            if (ufield.hasUniqueness()){
                FieldDescriptor.Uniqueness.Type utype = ufield.getUniqueness();
                if (FieldDescriptor.Uniqueness.Type.Unique.equals(utype))
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
    public final static boolean IsFieldHashUnique(FieldDescriptor field){
        if (field instanceof FieldDescriptor.Uniqueness){
            FieldDescriptor.Uniqueness ufield = (FieldDescriptor.Uniqueness)field;
            if (ufield.hasUniqueness()){
                FieldDescriptor.Uniqueness.Type utype = ufield.getUniqueness();
                if (FieldDescriptor.Uniqueness.Type.HashUnique.equals(utype))
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
    public final static boolean IsTypeClassKey(java.lang.Class fieldType){
        if (null != fieldType)
            return (com.google.appengine.api.datastore.Key.class.equals(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassIndexed(java.lang.Class fieldType){
        return (gap.data.BigTable.IsIndexed(fieldType));
    }
    public final static boolean IsTypeClassList(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.List.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsTypeClassBigTable(java.lang.Class fieldType){
        if (null != fieldType)
            return (gap.data.BigTable.class.isAssignableFrom(fieldType));
        else
            return false;
    }
    public final static boolean IsNotTypeClassBigTable(java.lang.Class fieldType){
        if (null != fieldType)
            return (!(gap.data.BigTable.class.isAssignableFrom(fieldType)));
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
        String childClassName = ChildClassName(fd);
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
        String childClassName = ChildClassName(fd);
        return ListLongClassName(parentClassName,childClassName);
    }
    public final static String ListLongClassName(String parentClassName, String childClassName){
        if (null != parentClassName && null != childClassName)
            return "ListLong"+parentClassName+childClassName;
        else
            return null;
    }
    public final static FieldDescriptor KeyField(String packageName, ClassDescriptor cd, List<ImportDescriptor> imports){
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
        else
            throw new ODStateException(child,"Unrecognized field type.");
    }
    public final static boolean IsTypeOf(String typeName, String interfaceName){
        if (gap.Primitive.Is(typeName))
            return false;
        else {
            try {
                ClassDescriptor type = gap.odl.Main.ClassDescriptorFor(typeName);
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
}
