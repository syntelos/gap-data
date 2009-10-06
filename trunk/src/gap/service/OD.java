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
    public final static void GenerateSource(File xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
                                              ClassDescriptor cd, PrintWriter out)
        throws gap.service.od.ODStateException, java.io.IOException, hapax.TemplateException
    {
        if (null != xtm && null != pkg && null != imports && null != cd && null != out){
            TemplateCache loader = new TemplateCache(xtm.getParent());
            Template template = loader.getTemplate(xtm.getPath());
            TemplateDictionary top = new TemplateDictionary();

            String packageName = pkg.getName();
            if (null == packageName || 0 == packageName.length())
                throw new ODStateException(pkg,"The object data model requires a package name.");

            packageName = Decamel(packageName);

            String className = cd.getName();
            if (null == className || 0 == className.length())
                throw new ODStateException(cd,"The object data model requires a class name.");
            else
                className = Camel(className);

            String classNameDecamel = Decamel(className);

            String classVersion = ClassVersion(cd);

            String classKind = ClassKind(cd);

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

            if (cd instanceof ClassDescriptor.Implements){
                ClassDescriptor.Implements cdi = (ClassDescriptor.Implements)cd;
                if (cdi.hasInterfaces()){
                    for (Object inf : cdi.getInterfaces()){
                        TemplateDictionary ind = top.addSection("implements");
                        ind.setVariable("interface",ToString(inf));
                    }
                }
            }

            ClassDescriptor.Relation.Type classRelation = null;
            String classRelationParent = null;
            if (cd instanceof ClassDescriptor.Relation){
                ClassDescriptor.Relation cdr = (ClassDescriptor.Relation)cd;
                classRelation = cdr.getRelation();
                classRelationParent = cdr.getParent();
            }
            if (null == classRelation || ClassDescriptor.Relation.Type.None.equals(classRelation)){
                top.addSection("class_re_none");
            }
            else if (ClassDescriptor.Relation.Type.Parent.equals(classRelation)){
                top.addSection("class_re_parent");
            }
            else if (ClassDescriptor.Relation.Type.Child.equals(classRelation)){
                TemplateDictionary child = top.addSection("class_re_child");
                if (null == classRelationParent)
                    throw new ODStateException(cd,"The object data model requires a parent class name.");
                else
                    child.setVariable("class_parentClass",classRelationParent);
            }
            else if (ClassDescriptor.Relation.Type.ChildGroup.equals(classRelation)){
                TemplateDictionary child = top.addSection("class_re_childgroup");
                if (null == classRelationParent)
                    throw new ODStateException(cd,"The object data model requires a parent class name.");
                else
                    child.setVariable("class_parentClass",classRelationParent);
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

                    if (IsFieldPersistent(field)){

                        TemplateDictionary dataField = top.addSection("pfield");

                        dataField.putVariable("field_name",fieldName);
                        dataField.putVariable("field_nameCamel",fieldNameCamel);
                        dataField.putVariable("field_class",fieldType);
                        dataField.putVariable("field_classClean",fieldTypeClean);
                        dataField.putVariable("field_classCleanClean",fieldTypeCleanClean);

                        if (IsFieldHashUnique(field)){

                            dataField.addSection("field_is_not_unique");

                            TemplateDictionary field_is = dataField.addSection("field_is_hash_unique");

                            if (IsTypeClassKey(fieldTypeClass)){

                                dataField.addSection("field_is_key");
                                dataField.addSection("field_is_not_map");
                                dataField.addSection("field_is_not_list");
                                dataField.addSection("field_is_not_collection");

                                if (null == key){
                                    key = field;

                                    top.putVariable("field_key_name",fieldName);
                                    top.putVariable("field_key_nameCamel",fieldNameCamel);
                                    top.putVariable("field_key_class",fieldType);
                                    top.putVariable("field_key_classClean",fieldTypeClean);
                                }
                            }
                            else if (IsTypeClassIndexed(fieldTypeClass)){

                                dataField.addSection("field_is_not_key");
                                dataField.addSection("field_is_not_map");
                                dataField.addSection("field_is_not_list");
                                dataField.addSection("field_is_not_collection");

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
                            }
                            else if (IsTypeClassList(fieldTypeClass)){

                                dataField.addSection("field_is_not_key");
                                dataField.addSection("field_is_not_map");
                                dataField.addSection("field_is_list");
                                dataField.addSection("field_is_collection");

                                if (1 == fieldTypeParameters.length){
                                    String typeComponent = fieldTypeParameters[0];

                                    field_is.putVariable("data_model","*hash-unique");

                                    dataField.putVariable("field_to_string_prefix","gap.data.Hash.For"+typeComponent+"(");
                                    dataField.putVariable("field_to_string_suffix",")");

                                    TemplateDictionary field_is_list = dataField.addSection("field_is_list");
                                    field_is_list.putVariable("field_list_component",typeComponent);

                                }
                                else
                                    throw new ODStateException(field,"Field '"+fieldName+"' type list missing type parameter.");
                            }
                            else
                                throw new ODStateException(field,"Model '*hash-unique' field type is not indexable or list of indexable '"+fieldTypeClass+"'.");

                            /*
                             * Global section 'field_hash_unique'
                             */
                            TemplateDictionary topDataFieldH = top.showSection("field_hash_unique").get(0);


                            dataField = topDataFieldH.addSection("field");

                            dataField.putVariable("field_name",fieldName);
                            dataField.putVariable("field_nameCamel",fieldNameCamel);
                            dataField.putVariable("field_class",fieldType);
                            dataField.putVariable("field_classClean",fieldTypeClean);
                            dataField.putVariable("field_classCleanClean",fieldTypeCleanClean);
                        }
                        else if (IsFieldUnique(field)){

                            dataField.addSection("field_is_not_hash_unique");

                            TemplateDictionary field_is = dataField.addSection("field_is_unique");

                            if (IsTypeClassKey(fieldTypeClass)){

                                dataField.addSection("field_is_key");
                                dataField.addSection("field_is_not_map");
                                dataField.addSection("field_is_not_list");
                                dataField.addSection("field_is_not_collection");

                                if (null == key){
                                    key = field;

                                    top.putVariable("field_key_name",fieldName);
                                    top.putVariable("field_key_nameCamel",fieldNameCamel);
                                    top.putVariable("field_key_class",fieldType);
                                    top.putVariable("field_key_classClean",fieldTypeClean);
                                }
                            }
                            else if (IsTypeClassString(fieldTypeClass)){

                                dataField.addSection("field_is_not_key");
                                dataField.addSection("field_is_not_map");
                                dataField.addSection("field_is_not_list");
                                dataField.addSection("field_is_not_collection");

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
                            else
                                throw new ODStateException(field,"Model '*unique' field type is not string '"+fieldTypeClass+"'.");
                        }
                        else if (IsTypeClassKey(fieldTypeClass)){

                            dataField.addSection("field_is_not_unique");
                            dataField.addSection("field_is_not_hash_unique");
                            dataField.addSection("field_is_not_map");
                            dataField.addSection("field_is_not_list");
                            dataField.addSection("field_is_not_collection");

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

                            dataField.addSection("field_is_not_key");
                            dataField.addSection("field_is_not_unique");
                            dataField.addSection("field_is_not_hash_unique");
                            dataField.addSection("field_is_not_map");
                            dataField.addSection("field_is_collection");

                            if (1 == fieldTypeParameters.length){

                                TemplateDictionary field_is = dataField.addSection("field_is_list");

                                String typeComponent = fieldTypeParameters[0];
                                field_is.putVariable("field_list_component",typeComponent);
                            }
                            else
                                throw new ODStateException(field,"Field '"+fieldName+"' type list missing type parameter.");
                        }
                        else if (IsTypeClassMap(fieldTypeClass)){

                            dataField.addSection("field_is_not_key");
                            dataField.addSection("field_is_not_unique");
                            dataField.addSection("field_is_not_hash_unique");
                            dataField.addSection("field_is_not_list");
                            dataField.addSection("field_is_collection");

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
                            dataField.addSection("field_is_not_unique");
                            dataField.addSection("field_is_not_hash_unique");
                        }
                    }
                    else if (IsFieldRelation(field)){

                        if (null != fieldTypeClass && IsNotTypeClassBigTable(fieldTypeClass))
                            throw new ODStateException(field,"Relation field '"+fieldName+"' is not a subclass of 'gap.data.BigTable'.");
                        else {
                            TemplateDictionary dataField = top.addSection("rfield");

                            dataField.putVariable("field_name",fieldName);
                            dataField.putVariable("field_nameCamel",fieldNameCamel);
                            dataField.putVariable("field_class",fieldType);
                            dataField.putVariable("field_classClean",fieldTypeClean);
                            dataField.putVariable("field_classCleanClean",fieldTypeCleanClean);
                        }
                    }
                    else {
                        TemplateDictionary dataField = top.addSection("tfield");
                        {
                            TemplateDictionary field_is = dataField.addSection("field_is_transient");
                            field_is.putVariable("data_model","*transient");
                        }

                        dataField.putVariable("field_name",fieldName);
                        dataField.putVariable("field_nameCamel",fieldNameCamel);
                        dataField.putVariable("field_class",fieldType);
                        dataField.putVariable("field_classClean",fieldTypeClean);
                        dataField.putVariable("field_classCleanClean",fieldTypeCleanClean);
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
    public final static boolean IsFieldPersistent(FieldDescriptor field){

        if (IsFieldRelation(field)) /*(very template specific -- will change)
                                     */
            return false;

        else if (field instanceof FieldDescriptor.Persistence){
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
            return (gap.data.List.class.equals(fieldType));
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
    final static String CleanTypeName(String name){
        int idx = name.indexOf('<');
        if (-1 != idx)
            return name.substring(0,idx);
        else
            return name;
    }
    final static String CleanCleanTypeName(String name){
        int idx = name.indexOf('.');
        if (-1 != idx)
            return name.substring(0,idx);
        else
            return name;
    }
    final static Class ClassFor(String cleanTypeName, ImportDescriptor imp){
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
}
