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
import static gap.data.List.Type.*;
import static gap.data.Map.Type.*;
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
public final class OD
    extends java.lang.Object
{

    public final static void GenerateBeanSource(File xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
                                                ClassDescriptor cd, PrintWriter out)
        throws ODStateException, IOException, TemplateException
    {
        if (null != xtm && null != pkg && null != imports && null != cd && null != out){
            TemplateCache loader = new TemplateCache(xtm.getParent());
            Template template = loader.getTemplate(xtm.getPath());
            TemplateDictionary top = new TemplateDictionary();

            DefineDescription(xtm,top);

            DefinePrimitives(top);

            DefineClass(pkg,cd,imports,top);

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

    public final static void GenerateListSource(File xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
                                                ClassDescriptor parent, FieldDescriptor field, 
                                                String parentClassName, String childClassName, 
                                                String listClassName, gap.data.List.Type listType, 
                                                PrintWriter out)
        throws ODStateException, IOException, TemplateException
    {
        if (null != xtm && null != pkg && null != imports && null != parent && null != field
            && null != parentClassName && null != childClassName && null != listClassName
            && null != listType && null != out)
        {
            TemplateCache loader = new TemplateCache(xtm.getParent());
            Template template = loader.getTemplate(xtm.getPath());
            TemplateDictionary top = new TemplateDictionary();

            DefineDescription(xtm,top);

            DefinePrimitives(top);

            DefineClass(pkg,parent,imports,top,"parent_");

            ClassDescriptor child = gap.odl.Main.ClassDescriptorFor(childClassName);

            DefineClass(pkg,child,imports,top,"child_");

            top.setVariable("list_class_name",listClassName);

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

    public final static void GenerateMapSource(File xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
                                               ClassDescriptor parent, FieldDescriptor field, 
                                               String parentClassName, String mapClassName, 
                                               gap.data.Map.Type mapType, OD.MapChild mapChild,
                                               PrintWriter out)
        throws ODStateException, IOException, TemplateException
    {
        if (null != xtm && null != pkg && null != imports && null != parent && null != field
            && null != parentClassName && null != mapClassName && null != mapType
            && null != mapChild && null != out)
        {
            TemplateCache loader = new TemplateCache(xtm.getParent());
            Template template = loader.getTemplate(xtm.getPath());
            TemplateDictionary top = new TemplateDictionary();

            DefineDescription(xtm,top);

            DefinePrimitives(top);

            DefineClass(pkg,parent,imports,top,"parent_");

            ClassDescriptor child = gap.odl.Main.ClassDescriptorFor(mapChild.childValueClassName);

            DefineClass(pkg,child,imports,top,"child_");

            top.setVariable("map_class_name",mapClassName);
            top.setVariable("map_key_type",mapChild.childKeyFieldType);
            top.setVariable("map_key_field_name",mapChild.childKeyFieldName);
            top.setVariable("map_key_field_nameCamel",Camel(mapChild.childKeyFieldName));

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

    public final static void DefineDescription(File xtm, TemplateDictionary top){

        top.putVariable("odl_gen_class","gap.service.OD");
        top.putVariable("odl_gen_xtm_src",xtm.getPath());
        top.putVariable("odl_gen_timestamp",(gap.Date.FormatISO8601(System.currentTimeMillis())));
    }
    public final static void DefinePrimitives(TemplateDictionary top){

        TemplateDictionary primitives = top.addSection("primitives");
        for (Primitive type : Primitive.values()){
            TemplateDictionary primitive = primitives.addSection("type");
            String type_name = type.name();
            primitive.setVariable("name",type_name);
            primitive.setVariable("nameCamel",type_name);
            primitive.setVariable("nameDecamel",Decamel(type_name));
            primitive.addSection(type_name);
        }
    }
    public final static void DefineClass(PackageDescriptor pkg, ClassDescriptor cd, List<ImportDescriptor> imports, TemplateDictionary top)
        throws ODStateException, IOException
    {
        DefineClass(pkg,cd,imports,top,null);
    }
    public final static void DefineClass(PackageDescriptor pkg, ClassDescriptor cd, List<ImportDescriptor> imports, TemplateDictionary top, String prefix)
        throws ODStateException, IOException
    {
        String packageName = PackageName(pkg);
        String className = ClassName(cd);
        String classNameDecamel = Decamel(className);
        String classVersion = ClassVersion(cd);
        String classKind = ClassKind(cd);
        String classPath = ClassPath(cd);
        String defaultSortBy = null;

        /*
         * Class globals
         */
        top.putVariable(DefineName(prefix,"package_name"), packageName);
        top.putVariable(DefineName(prefix,"class_name"), className);
        top.putVariable(DefineName(prefix,"class_nameDecamel"), classNameDecamel);
        top.putVariable(DefineName(prefix,"class_version"),classVersion);
        top.putVariable(DefineName(prefix,"class_kind"), classKind);
        top.putVariable(DefineName(prefix,"class_path"), classPath);

        ClassDescriptor.Relation.Type classRelation = null;
        String classRelationParent = null;
        ClassDescriptor parent = null;
        if (cd instanceof ClassDescriptor.Relation){
            ClassDescriptor.Relation cdr = (ClassDescriptor.Relation)cd;
            classRelation = cdr.getRelation();
            classRelationParent = cdr.getParent();
        }
        if (null == classRelation || ClassDescriptor.Relation.Type.None.equals(classRelation)){
            top.addSection(DefineName(prefix,"class_re_none"));
            top.addSection(DefineName(prefix,"class_re_not_parent"));
            top.addSection(DefineName(prefix,"class_re_not_child"));
            top.addSection(DefineName(prefix,"class_re_not_childgroup"));
            top.addSection(DefineName(prefix,"class_re_not_child_or_group"));
        }
        else if (ClassDescriptor.Relation.Type.Parent.equals(classRelation)){
            top.addSection(DefineName(prefix,"class_re_not_none"));
            top.addSection(DefineName(prefix,"class_re_parent"));
            top.addSection(DefineName(prefix,"class_re_not_child"));
            top.addSection(DefineName(prefix,"class_re_not_childgroup"));
            top.addSection(DefineName(prefix,"class_re_not_child_or_group"));
        }
        else if (ClassDescriptor.Relation.Type.Child.equals(classRelation)){
            top.addSection(DefineName(prefix,"class_re_not_none"));
            top.addSection(DefineName(prefix,"class_re_not_parent"));
            top.addSection(DefineName(prefix,"class_re_not_childgroup"));
            top.addSection(DefineName(prefix,"class_re_child_or_group"));

            TemplateDictionary child = top.addSection(DefineName(prefix,"class_re_child"));

            if (null == classRelationParent)
                throw new ODStateException(cd,"The object data model requires a parent class name.");
            else {

                top.setVariable(DefineName(prefix,"parent_class_name"),classRelationParent);

                parent = gap.odl.Main.ClassDescriptorFor(classRelationParent);
                if (null == parent)
                    throw new ODStateException(cd,"Parent class not found.");
            }
        }
        else if (ClassDescriptor.Relation.Type.ChildGroup.equals(classRelation)){
            top.addSection(DefineName(prefix,"class_re_not_none"));
            top.addSection(DefineName(prefix,"class_re_not_parent"));
            top.addSection(DefineName(prefix,"class_re_not_child"));
            top.addSection(DefineName(prefix,"class_re_child_or_group"));

            top.addSection(DefineName(prefix,"class_re_childgroup"));
            if (null == classRelationParent)
                throw new ODStateException(cd,"The object data model requires a parent class name.");
            else {

                top.setVariable(DefineName(prefix,"parent_class_name"),classRelationParent);

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
            TemplateDictionary imd = top.addSection(DefineName(prefix,"import"));
            if (imp.hasPackageSpec())
                imd.putVariable("import_spec",imp.getPackageSpec());
            else if (imp.hasClassName())
                imd.putVariable("import_spec",imp.getClassName());
        }

        String[] interfaces = ClassImplements(cd);
        for (String inf : interfaces){
            TemplateDictionary ind = top.addSection(DefineName(prefix,"implements"));
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
                String fieldType = OD.MapChild.Type(ToString(field.getType()));
                String fieldTypeClean = CleanTypeName(fieldType);
                String fieldTypeCleanClean = CleanCleanTypeName(fieldType);
                Class fieldTypeClass = FieldClass(packageName,fieldType,imports);
                String[] fieldTypeParameters = FieldTypeParameters(ToString(field.getType()));
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

                    dataField = top.addSection(DefineName(prefix,"pfield"));

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
                        TemplateDictionary topDataFieldH = top.showSection(DefineName(prefix,"field_hash_unique")).get(0);

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
                            TemplateDictionary topDataFieldU = top.showSection(DefineName(prefix,"field_unique")).get(0);

                            topDataFieldU.putVariable("field_name",fieldName);
                            topDataFieldU.putVariable("field_nameCamel",fieldNameCamel);
                            topDataFieldU.putVariable("field_class",fieldType);
                            topDataFieldU.putVariable("field_classClean",fieldTypeClean);
                            topDataFieldU.putVariable("field_classCleanClean",fieldTypeCleanClean);

                            /*
                             * Global field 'unique' references
                             */
                            top.putVariable(DefineName(prefix,"field_unique_name"),fieldName);
                            top.putVariable(DefineName(prefix,"field_unique_nameCamel"),fieldNameCamel);
                            top.putVariable(DefineName(prefix,"field_unique_class"),fieldType);
                            top.putVariable(DefineName(prefix,"field_unique_classClean"),fieldTypeClean);
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

                    dataField = top.addSection(DefineName(prefix,"cfield"));
                    /*
                     * Populate 'cfield' section
                     */
                    dataField.addSection("field_is_not_unique");
                    dataField.addSection("field_is_not_hash_unique");
                }
                else if (IsFieldRelation(field)){
                    isRelation = true;

                    dataField = top.addSection(DefineName(prefix,"rfield"));
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

                    dataField = top.addSection(DefineName(prefix,"tfield"));
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

                        top.putVariable(DefineName(prefix,"field_key_name"),fieldName);
                        top.putVariable(DefineName(prefix,"field_key_nameCamel"),fieldNameCamel);
                        top.putVariable(DefineName(prefix,"field_key_class"),fieldType);
                        top.putVariable(DefineName(prefix,"field_key_classClean"),fieldTypeClean);
                    }
                }
                else if (IsTypeClassList(fieldTypeClass)){

                    dataField.addSection("field_is_not_map");

                    if (1 == fieldTypeParameters.length){

                        dataField.addSection("field_is_list");

                        gap.data.List.Type listType = gap.data.List.Type.For(fieldTypeClean);
                        switch(listType){
                        case ListPrimitive:
                            dataField.addSection("field_is_list_primitive");
                            dataField.addSection("field_is_not_collection_long_or_short");
                            dataField.addSection("field_is_not_list_long_or_short");
                            dataField.addSection("field_is_not_list_long");
                            dataField.addSection("field_is_not_list_short");
                            break;
                        case ListShort:
                            dataField.addSection("field_is_not_list_primitive");
                            dataField.addSection("field_is_collection_long_or_short");
                            dataField.addSection("field_is_list_long_or_short");
                            dataField.addSection("field_is_not_list_long");
                            dataField.addSection("field_is_list_short");
                            break;
                        case ListLong:
                            dataField.addSection("field_is_not_list_primitive");
                            dataField.addSection("field_is_collection_long_or_short");
                            dataField.addSection("field_is_list_long_or_short");
                            dataField.addSection("field_is_list_long");
                            dataField.addSection("field_is_not_list_short");
                            break;
                        }

                        String typeComponent = fieldTypeParameters[0];
                        dataField.putVariable("field_list_component",typeComponent);

                        if (IsTypeOf(typeComponent,"HasName"))
                            dataField.addSection("field_list_component_named");

                        ClassDescriptor component = gap.odl.Main.ClassDescriptorFor(typeComponent);
                        if (null != component){
                            String componentKind = ClassKind(component);
                            if (null != componentKind)
                                dataField.putVariable("field_list_component_kind",componentKind);
                        }

                        dataField.putVariable("field_impl_class_name",ListClassName(fieldTypeClean,className,typeComponent));
                    }
                    else
                        throw new ODStateException(field,"Field '"+fieldName+"' type list missing type parameter.");
                }
                else if (IsTypeClassMap(fieldTypeClass)){

                    dataField.addSection("field_is_not_list");

                    if (2 == fieldTypeParameters.length){

                        dataField.addSection("field_is_map");

                        OD.MapChild mapChild = new OD.MapChild(field);
                        switch(mapChild.mapType){
                        case MapPrimitive:
                            dataField.addSection("field_is_map_primitive");
                            dataField.addSection("field_is_not_collection_long_or_short");
                            dataField.addSection("field_is_not_map_long_or_short");
                            dataField.addSection("field_is_not_map_long");
                            dataField.addSection("field_is_not_map_short");
                            break;
                        case MapShort:
                            dataField.addSection("field_is_not_map_primitive");
                            dataField.addSection("field_is_collection_long_or_short");
                            dataField.addSection("field_is_map_long_or_short");
                            dataField.addSection("field_is_not_map_long");
                            dataField.addSection("field_is_map_short");
                            break;
                        case MapLong:
                            dataField.addSection("field_is_not_map_primitive");
                            dataField.addSection("field_is_collection_long_or_short");
                            dataField.addSection("field_is_map_long_or_short");
                            dataField.addSection("field_is_map_long");
                            dataField.addSection("field_is_not_map_short");
                            break;
                        }


                        String typeComponentFrom = mapChild.childKeyFieldType;
                        String typeComponentFromName = mapChild.childKeyFieldName;
                        String typeComponentTo = mapChild.childValueClassName;
                        dataField.putVariable("field_map_component_from",typeComponentFrom);
                        dataField.putVariable("field_map_component_from_name",typeComponentFromName);
                        dataField.putVariable("field_map_component_from_nameCamel",Camel(typeComponentFromName));
                        dataField.putVariable("field_map_component_to",typeComponentTo);
                        dataField.putVariable("field_map_component",typeComponentTo);

                        if (IsTypeOf(typeComponentTo,"HasName"))
                            dataField.addSection("field_map_component_named");

                        ClassDescriptor componentTo = gap.odl.Main.ClassDescriptorFor(typeComponentTo);
                        if (null != componentTo){
                            String componentToKind = ClassKind(componentTo);
                            if (null != componentToKind)
                                dataField.putVariable("field_map_component_kind",componentToKind);
                        }

                        dataField.putVariable("field_impl_class_name",MapClassName(fieldTypeClean,className,typeComponentFrom,typeComponentTo));
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

                        TemplateDictionary methods = top.addSection(DefineName(prefix,"method"));
                        methods.setVariable("method_name",method_name);
                        methods.setVariable("method_body",method_body);

                        TemplateDictionary mb = top.addSection(DefineName(prefix,"method_"+method_name+"_with_body"));
                        mb.setVariable("body",method_body);

                        String method_type = ToString(method.getType());
                        if (null != method_type){
                            TemplateDictionary ma = top.addSection(DefineName(prefix,"method_"+method_name+"_with_type"));
                            ma.setVariable("type",method_type);
                            methods.setVariable("method_type",method_type);
                        }
                        else
                            top.showSection(DefineName(prefix,"method_"+method_name+"_without_type"));

                        if (method instanceof MethodDescriptor.Arguments){
                            MethodDescriptor.Arguments ma = (MethodDescriptor.Arguments)method;
                            if (ma.hasArguments()){
                                String method_arguments = ma.getArguments();
                                if (null != method_arguments){
                                    TemplateDictionary td = top.addSection(DefineName(prefix,"method_"+method_name+"_with_args"));
                                    td.setVariable("args",method_arguments);
                                    methods.setVariable("method_arguments",method_arguments);
                                }
                                else
                                    top.showSection(DefineName(prefix,"method_"+method_name+"_without_args"));
                            }
                            else
                                top.showSection(DefineName(prefix,"method_"+method_name+"_without_args"));
                        }
                        else
                            top.showSection(DefineName(prefix,"method_"+method_name+"_without_args"));

                        if (method instanceof MethodDescriptor.Exceptions){
                            MethodDescriptor.Exceptions ma = (MethodDescriptor.Exceptions)method;
                            if (ma.hasExceptions()){
                                String method_exceptions = ma.getExceptions();
                                if (null != method_exceptions){
                                    TemplateDictionary td = top.addSection(DefineName(prefix,"method_"+method_name+"_with_excs"));
                                    td.setVariable("excs",method_exceptions);
                                    methods.setVariable(DefineName(prefix,"method_exceptions"),method_exceptions);
                                }
                                else
                                    top.showSection(DefineName(prefix,"method_"+method_name+"_without_excs"));
                            }
                            else
                                top.showSection(DefineName(prefix,"method_"+method_name+"_without_excs"));
                        }
                        else
                            top.showSection(DefineName(prefix,"method_"+method_name+"_without_excs"));
                    }
                    else {
                        top.showSection(DefineName(prefix,"method_"+method_name+"_without_body"));
                    }
                }
            }
        }

        /*
         * Current template model requires 'key'.
         */
        if (null != key){

            top.setVariable(DefineName(prefix,"class_defaultSortBy"), defaultSortBy);

            return;
        }
        else
            throw new ODStateException(cd,"Model requires a field having type 'com.google.appengine.api.datastore.Key'.");

    }
    public final static String DefineName(String prefix, String name){
        if (null == prefix || 0 == prefix.length())
            return name;
        else
            return (prefix+name);
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

    public final static List<FieldDescriptor> FieldsOfTypeMap(PackageDescriptor pkg, ClassDescriptor cd, List<ImportDescriptor> imports){
        List<FieldDescriptor> re = new java.util.ArrayList<FieldDescriptor>();
        if (cd.hasFields()){
            String packageName = OD.PackageName(pkg);
            for (FieldDescriptor field : cd.getFields()){
                String typeName = OD.ToString(field.getType());
                Class typeClass = OD.FieldClass(packageName,typeName,imports);
                if (IsTypeClassMap(typeClass)){
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
        String typeName = OD.ToString(field.getType());
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
            String typeName = OD.ToString(field.getType());
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
            String fieldTypeDeclaration = OD.ToString(field.getType());
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
    public final static String MapShortClassName(String parentClassName, OD.MapChild mapChild){
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
    public final static String MapLongClassName(String parentClassName, OD.MapChild mapChild){
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
