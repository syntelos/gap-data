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
import gap.hapax.TemplateName;
import gap.hapax.TemplateRenderer;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateException;
import gap.service.Templates;
import gap.service.od.ClassDescriptor;
import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;
import gap.service.od.MethodDescriptor;
import gap.service.od.ODStateException;
import gap.service.od.PackageDescriptor;

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
    public final static class TemplateNames {
        public final static TemplateName Args = new TemplateName("args");
        public final static TemplateName Body = new TemplateName("body");
        public final static TemplateName DataModel = new TemplateName("data_model");
        public final static TemplateName Excs = new TemplateName("excs");
        public final static TemplateName Field = new TemplateName("field");
        public final static TemplateName FieldClass = new TemplateName("field_class");
        public final static TemplateName FieldClassClean = new TemplateName("field_classClean");
        public final static TemplateName FieldClassCleanClean = new TemplateName("field_classCleanClean");
        public final static TemplateName FieldImplClassName = new TemplateName("field_impl_class_name");
        public final static TemplateName FieldIsCollectionLongOrShort = new TemplateName("field_is_collection_long_or_short");
        public final static TemplateName FieldIsHashUnique = new TemplateName("field_is_hash_unique");
        public final static TemplateName FieldIsInheritable = new TemplateName("field_is_inheritable");
        public final static TemplateName FieldIsKey = new TemplateName("field_is_key");
        public final static TemplateName FieldIsList = new TemplateName("field_is_list");
        public final static TemplateName FieldIsListLong = new TemplateName("field_is_list_long");
        public final static TemplateName FieldIsListLongOrShort = new TemplateName("field_is_list_long_or_short");
        public final static TemplateName FieldIsListPrimitive = new TemplateName("field_is_list_primitive");
        public final static TemplateName FieldIsListShort = new TemplateName("field_is_list_short");
        public final static TemplateName FieldIsMap = new TemplateName("field_is_map");
        public final static TemplateName FieldIsMapLong = new TemplateName("field_is_map_long");
        public final static TemplateName FieldIsMapLongOrShort = new TemplateName("field_is_map_long_or_short");
        public final static TemplateName FieldIsMapPrimitive = new TemplateName("field_is_map_primitive");
        public final static TemplateName FieldIsMapShort = new TemplateName("field_is_map_short");
        public final static TemplateName FieldIsNotCollectionLongOrShort = new TemplateName("field_is_not_collection_long_or_short");
        public final static TemplateName FieldIsNotHashUnique = new TemplateName("field_is_not_hash_unique");
        public final static TemplateName FieldIsNotInheritable = new TemplateName("field_is_not_inheritable");
        public final static TemplateName FieldIsNotKey = new TemplateName("field_is_not_key");
        public final static TemplateName FieldIsNotList = new TemplateName("field_is_not_list");
        public final static TemplateName FieldIsNotListLong = new TemplateName("field_is_not_list_long");
        public final static TemplateName FieldIsNotListLongOrShort = new TemplateName("field_is_not_list_long_or_short");
        public final static TemplateName FieldIsNotListPrimitive = new TemplateName("field_is_not_list_primitive");
        public final static TemplateName FieldIsNotListShort = new TemplateName("field_is_not_list_short");
        public final static TemplateName FieldIsNotMap = new TemplateName("field_is_not_map");
        public final static TemplateName FieldIsNotMapLong = new TemplateName("field_is_not_map_long");
        public final static TemplateName FieldIsNotMapLongOrShort = new TemplateName("field_is_not_map_long_or_short");
        public final static TemplateName FieldIsNotMapPrimitive = new TemplateName("field_is_not_map_primitive");
        public final static TemplateName FieldIsNotMapShort = new TemplateName("field_is_not_map_short");
        public final static TemplateName FieldIsNotUnique = new TemplateName("field_is_not_unique");
        public final static TemplateName FieldIsTransient = new TemplateName("field_is_transient");
        public final static TemplateName FieldIsUnique = new TemplateName("field_is_unique");
        public final static TemplateName FieldListComponent = new TemplateName("field_list_component");
        public final static TemplateName FieldListComponentKind = new TemplateName("field_list_component_kind");
        public final static TemplateName FieldListComponentNamed = new TemplateName("field_list_component_named");
        public final static TemplateName FieldMapComponent = new TemplateName("field_map_component");
        public final static TemplateName FieldMapComponentFrom = new TemplateName("field_map_component_from");
        public final static TemplateName FieldMapComponentFromName = new TemplateName("field_map_component_from_name");
        public final static TemplateName FieldMapComponentFromNameCamel = new TemplateName("field_map_component_from_nameCamel");
        public final static TemplateName FieldMapComponentKind = new TemplateName("field_map_component_kind");
        public final static TemplateName FieldMapComponentNamed = new TemplateName("field_map_component_named");
        public final static TemplateName FieldMapComponentTo = new TemplateName("field_map_component_to");
        public final static TemplateName FieldName = new TemplateName("field_name");
        public final static TemplateName FieldNameCamel = new TemplateName("field_nameCamel");
        public final static TemplateName FieldToStringPrefix = new TemplateName("field_to_string_prefix");
        public final static TemplateName FieldToStringSuffix = new TemplateName("field_to_string_suffix");
        public final static TemplateName ImportSpec = new TemplateName("import_spec");
        public final static TemplateName InterfaceClass = new TemplateName("interface_class");
        public final static TemplateName ListClassName = new TemplateName("list_class_name");
        public final static TemplateName MapClassName = new TemplateName("map_class_name");
        public final static TemplateName MapKeyFieldName = new TemplateName("map_key_field_name");
        public final static TemplateName MapKeyFieldNameCamel = new TemplateName("map_key_field_nameCamel");
        public final static TemplateName MapKeyType = new TemplateName("map_key_type");
        public final static TemplateName MethodArguments = new TemplateName("method_arguments");
        public final static TemplateName MethodBody = new TemplateName("method_body");
        public final static TemplateName MethodName = new TemplateName("method_name");
        public final static TemplateName MethodType = new TemplateName("method_type");
        public final static TemplateName Name = new TemplateName("name");
        public final static TemplateName NameCamel = new TemplateName("nameCamel");
        public final static TemplateName NameDecamel = new TemplateName("nameDecamel");
        public final static TemplateName OdlGenClass = new TemplateName("odl_gen_class");
        public final static TemplateName OdlGenTimestamp = new TemplateName("odl_gen_timestamp");
        public final static TemplateName OdlGenXtmSrc = new TemplateName("odl_gen_xtm_src");
        public final static TemplateName PrefixChild = new TemplateName("child_");
        public final static TemplateName PrefixParent = new TemplateName("parent_");
        public final static TemplateName Primitives = new TemplateName("primitives");
        public final static TemplateName Type = new TemplateName("type");

    }

    public final static void GenerateBeanSource(TemplateName xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
                                                ClassDescriptor cd, PrintWriter out)
        throws ODStateException, IOException, TemplateException
    {
        if (null != xtm && null != pkg && null != imports && null != cd && null != out){

            TemplateRenderer template = Templates.GetTemplate(xtm);
            TemplateDataDictionary top = new gap.hapax.AbstractData();

            DefineDescription(xtm,top);

            DefinePrimitives(top);

            DefineClass(pkg,cd,imports,top);

            try {
                template.render(top,out); 
            }
            catch (TemplateException exc){
                throw new TemplateException("In template '"+xtm.source+"'.",exc);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static void GenerateListSource(TemplateName xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
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
            TemplateRenderer template = Templates.GetTemplate(xtm);
            TemplateDataDictionary top = new gap.hapax.AbstractData();

            DefineDescription(xtm,top);

            DefinePrimitives(top);

            DefineClass(pkg,parent,imports,top,TemplateNames.PrefixParent);

            ClassDescriptor child = gap.odl.Main.ClassDescriptorFor(childClassName);
            if (null != child){

                DefineClass(pkg,child,imports,top,TemplateNames.PrefixChild);

                top.setVariable(TemplateNames.ListClassName,listClassName);

                try {
                    template.render(top,out); 
                }
                catch (TemplateException exc){
                    throw new TemplateException("In template '"+xtm.source+"'.",exc);
                }
            }
            else 
                throw new TemplateException("Child class descriptor not found for name '"+childClassName+"'.");
        }
        else
            throw new IllegalArgumentException();
    }

    public final static void GenerateMapSource(TemplateName xtm, PackageDescriptor pkg, List<ImportDescriptor> imports,
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
            TemplateRenderer template = Templates.GetTemplate(xtm);
            TemplateDataDictionary top = new gap.hapax.AbstractData();

            DefineDescription(xtm,top);

            DefinePrimitives(top);

            DefineClass(pkg,parent,imports,top,TemplateNames.PrefixParent);

            ClassDescriptor child = gap.odl.Main.ClassDescriptorFor(mapChild.childValueClassName);
            if (null != child){

                DefineClass(pkg,child,imports,top,TemplateNames.PrefixChild);

                top.setVariable(TemplateNames.MapClassName,mapClassName);
                top.setVariable(TemplateNames.MapKeyType,mapChild.childKeyFieldType);
                top.setVariable(TemplateNames.MapKeyFieldName,mapChild.childKeyFieldName);
                top.setVariable(TemplateNames.MapKeyFieldNameCamel,Camel(mapChild.childKeyFieldName));

                try {
                    template.render(top,out); 
                }
                catch (TemplateException exc){
                    throw new TemplateException("In template '"+xtm.source+"'.",exc);
                }
            }
            else 
                throw new TemplateException("Child class descriptor not found for name '"+mapChild.childValueClassName+"'.");
        }
        else
            throw new IllegalArgumentException();
    }

    public final static void DefineDescription(TemplateName xtm, TemplateDataDictionary top){

        top.setVariable(TemplateNames.OdlGenClass,"gap.service.OD");
        top.setVariable(TemplateNames.OdlGenXtmSrc,xtm.source);
        top.setVariable(TemplateNames.OdlGenTimestamp,(gap.Date.FormatISO8601(System.currentTimeMillis())));
    }
    public final static void DefinePrimitives(TemplateDataDictionary top){

        TemplateDataDictionary primitives = top.addSection(TemplateNames.Primitives);
        for (Primitive type : Primitive.values()){
            TemplateDataDictionary primitive = primitives.addSection(TemplateNames.Type);
            String type_name = type.name();
            primitive.setVariable(TemplateNames.Name,type_name);
            primitive.setVariable(TemplateNames.NameCamel,type_name);
            primitive.setVariable(TemplateNames.NameDecamel,Decamel(type_name));
            primitive.addSection(new TemplateName(type_name));
        }
    }
    public final static void DefineClass(PackageDescriptor pkg, ClassDescriptor cd, List<ImportDescriptor> imports, TemplateDataDictionary top)
        throws ODStateException, IOException
    {
        DefineClass(pkg,cd,imports,top,null);
    }
    public final static void DefineClass(PackageDescriptor pkg, ClassDescriptor cd, List<ImportDescriptor> imports, TemplateDataDictionary top, TemplateName prefix)
        throws ODStateException, IOException
    {
        String packageName = PackageName(pkg);
        String className = ClassName(cd);
        String classNameDecamel = Decamel(className);
        String classVersion = ClassVersion(cd);
        String classKind = ClassKind(cd);
        String classPath = ClassPath(cd);
        String defaultSortBy = null, defaultSortByOpt = null;

        /*
         * Class globals
         */
        top.setVariable(new TemplateName(prefix,"package_name"), packageName);
        top.setVariable(new TemplateName(prefix,"class_name"), className);
        top.setVariable(new TemplateName(prefix,"class_nameDecamel"), classNameDecamel);
        top.setVariable(new TemplateName(prefix,"class_version"),classVersion);
        top.setVariable(new TemplateName(prefix,"class_kind"), classKind);
        top.setVariable(new TemplateName(prefix,"class_path"), classPath);

        ClassDescriptor.Relation.Type classRelation = null;
        String classRelationParent = null;
        ClassDescriptor parent = null;
        if (cd instanceof ClassDescriptor.Relation){
            ClassDescriptor.Relation cdr = (ClassDescriptor.Relation)cd;
            classRelation = cdr.getRelation();
            classRelationParent = cdr.getParent();
        }
        if (null == classRelation || ClassDescriptor.Relation.Type.None.equals(classRelation)){
            top.addSection(new TemplateName(prefix,"class_re_none"));
            top.addSection(new TemplateName(prefix,"class_re_not_parent"));
            top.addSection(new TemplateName(prefix,"class_re_not_child"));
            top.addSection(new TemplateName(prefix,"class_re_not_childgroup"));
            top.addSection(new TemplateName(prefix,"class_re_not_child_or_group"));
        }
        else if (ClassDescriptor.Relation.Type.Parent.equals(classRelation)){
            top.addSection(new TemplateName(prefix,"class_re_not_none"));
            top.addSection(new TemplateName(prefix,"class_re_parent"));
            top.addSection(new TemplateName(prefix,"class_re_not_child"));
            top.addSection(new TemplateName(prefix,"class_re_not_childgroup"));
            top.addSection(new TemplateName(prefix,"class_re_not_child_or_group"));
        }
        else if (ClassDescriptor.Relation.Type.Child.equals(classRelation)){
            top.addSection(new TemplateName(prefix,"class_re_not_none"));
            top.addSection(new TemplateName(prefix,"class_re_not_parent"));
            top.addSection(new TemplateName(prefix,"class_re_not_childgroup"));
            top.addSection(new TemplateName(prefix,"class_re_child_or_group"));

            TemplateDataDictionary child = top.addSection(new TemplateName(prefix,"class_re_child"));

            if (null == classRelationParent)
                throw new ODStateException(cd,"The object data model requires a parent class name.");
            else {

                top.setVariable(new TemplateName(prefix,"parent_class_name"),classRelationParent);

                parent = gap.odl.Main.ClassDescriptorFor(classRelationParent);
                if (null == parent)
                    throw new ODStateException(cd,"Parent class not found.");
            }
        }
        else if (ClassDescriptor.Relation.Type.ChildGroup.equals(classRelation)){
            top.addSection(new TemplateName(prefix,"class_re_not_none"));
            top.addSection(new TemplateName(prefix,"class_re_not_parent"));
            top.addSection(new TemplateName(prefix,"class_re_not_child"));
            top.addSection(new TemplateName(prefix,"class_re_child_or_group"));

            top.addSection(new TemplateName(prefix,"class_re_childgroup"));
            if (null == classRelationParent)
                throw new ODStateException(cd,"The object data model requires a parent class name.");
            else {

                top.setVariable(new TemplateName(prefix,"parent_class_name"),classRelationParent);

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
            TemplateDataDictionary imd = top.addSection(new TemplateName(prefix,"import"));
            if (imp.hasPackageSpec())
                imd.setVariable(TemplateNames.ImportSpec,imp.getPackageSpec());
            else if (imp.hasClassName())
                imd.setVariable(TemplateNames.ImportSpec,imp.getClassName());
        }

        String[] interfaces = ClassImplements(cd);
        for (String inf : interfaces){
            TemplateDataDictionary ind = top.addSection(new TemplateName(prefix,"implements"));
            ind.setVariable(TemplateNames.InterfaceClass,inf);
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
                TemplateDataDictionary dataField = null;
                boolean isPersistent = false;
                boolean isInheritable = true;
                boolean isRelation = false;
                boolean isCollection = false;
                boolean isTransient = false;

                if (IsFieldDefaultSortBy(field))
                    defaultSortBy = fieldName;

                /*
                 * Create 'dataField' section
                 */
                if (IsFieldPersistent(field,fieldTypeClass)){
                    isPersistent = true;

                    dataField = top.addSection(new TemplateName(prefix,"pfield"));

                    /*
                     * Populate 'pfield' section
                     */
                    if (IsFieldHashUnique(field)){
                        isInheritable = false;

                        defaultSortByOpt = fieldName;

                        dataField.addSection(TemplateNames.FieldIsNotUnique);
                        dataField.addSection(TemplateNames.FieldIsNotInheritable);

                        TemplateDataDictionary field_is = dataField.addSection(TemplateNames.FieldIsHashUnique);

                        field_is.setVariable(TemplateNames.DataModel,"*hash-unique");

                        /*
                         * Global section 'field_hash_unique'
                         */
                        TemplateDataDictionary topDataFieldH = top.showSection(new TemplateName(prefix,"field_hash_unique")).get(0);

                        TemplateDataDictionary topDataFieldHF = topDataFieldH.addSection(TemplateNames.Field);

                        topDataFieldHF.setVariable(TemplateNames.FieldName,fieldName);
                        topDataFieldHF.setVariable(TemplateNames.FieldNameCamel,fieldNameCamel);
                        topDataFieldHF.setVariable(TemplateNames.FieldClass,fieldType);
                        topDataFieldHF.setVariable(TemplateNames.FieldClassClean,fieldTypeClean);
                        topDataFieldHF.setVariable(TemplateNames.FieldClassCleanClean,fieldTypeCleanClean);
                        if (IsTypeClassString(fieldTypeClass)){
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringPrefix,"");
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringSuffix,"");
                        }
                        else {
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings."+fieldTypeCleanClean+"ToString(");
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringSuffix,")");
                        }
                    }
                    else if (IsFieldUnique(field)){
                        isInheritable = false;

                        dataField.addSection(TemplateNames.FieldIsNotHashUnique);
                        dataField.addSection(TemplateNames.FieldIsNotInheritable);

                        TemplateDataDictionary field_is = dataField.addSection(TemplateNames.FieldIsUnique);

                        if (null == unique){

                            unique = field;

                            field_is.setVariable(TemplateNames.DataModel,"*unique");

                            /*
                             * Global section 'field_unique'
                             */
                            TemplateDataDictionary topDataFieldU = top.showSection(new TemplateName(prefix,"field_unique")).get(0);

                            topDataFieldU.setVariable(TemplateNames.FieldName,fieldName);
                            topDataFieldU.setVariable(TemplateNames.FieldNameCamel,fieldNameCamel);
                            topDataFieldU.setVariable(TemplateNames.FieldClass,fieldType);
                            topDataFieldU.setVariable(TemplateNames.FieldClassClean,fieldTypeClean);
                            topDataFieldU.setVariable(TemplateNames.FieldClassCleanClean,fieldTypeCleanClean);

                            /*
                             * Global field 'unique' references
                             */
                            top.setVariable(new TemplateName(prefix,"field_unique_name"),fieldName);
                            top.setVariable(new TemplateName(prefix,"field_unique_nameCamel"),fieldNameCamel);
                            top.setVariable(new TemplateName(prefix,"field_unique_class"),fieldType);
                            top.setVariable(new TemplateName(prefix,"field_unique_classClean"),fieldTypeClean);
                        }
                        else
                            throw new ODStateException(field,"Model has more than one '*unique' field, '"+unique.getName()+"' and '"+fieldName+"'.");
                    }
                    else {
                        dataField.addSection(TemplateNames.FieldIsNotUnique);
                        dataField.addSection(TemplateNames.FieldIsNotHashUnique);
                    }
                }
                else if (IsTypeClassCollection(fieldTypeClass)){
                    isCollection = true;

                    dataField = top.addSection(new TemplateName(prefix,"cfield"));
                    /*
                     * Populate 'cfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsNotUnique);
                    dataField.addSection(TemplateNames.FieldIsNotHashUnique);
                }
                else if (IsFieldRelation(field)){
                    isRelation = true;

                    dataField = top.addSection(new TemplateName(prefix,"rfield"));
                    /*
                     * Populate 'rfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsNotUnique);
                    dataField.addSection(TemplateNames.FieldIsNotHashUnique);

                    if ((!IsTypeClassKey(fieldTypeClass)) && null != fieldTypeClass && IsNotTypeClassBigTable(fieldTypeClass))
                        throw new ODStateException(field,"Relation field '"+fieldName+"' is not a subclass of 'gap.data.BigTable'.");

                }
                else {
                    isTransient = true;

                    dataField = top.addSection(new TemplateName(prefix,"tfield"));
                    /*
                     * Populate 'tfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsNotUnique);
                    dataField.addSection(TemplateNames.FieldIsNotHashUnique);
                    {
                        TemplateDataDictionary field_is = dataField.addSection(TemplateNames.FieldIsTransient);
                        field_is.setVariable(TemplateNames.DataModel,"*transient");
                    }
                }

                /*
                 * Populate 'dataField' name and type information
                 */
                dataField.setVariable(TemplateNames.FieldName,fieldName);
                dataField.setVariable(TemplateNames.FieldNameCamel,fieldNameCamel);
                dataField.setVariable(TemplateNames.FieldClass,fieldType);
                dataField.setVariable(TemplateNames.FieldClassClean,fieldTypeClean);
                dataField.setVariable(TemplateNames.FieldClassCleanClean,fieldTypeCleanClean);

                if (IsTypeClassKey(fieldTypeClass)){
                    isInheritable = false;

                    dataField.addSection(TemplateNames.FieldIsNotInheritable);

                    TemplateDataDictionary field_is = dataField.addSection(TemplateNames.FieldIsKey);

                    if (null == key){
                        key = field;

                        top.setVariable(new TemplateName(prefix,"field_key_name"),fieldName);
                        top.setVariable(new TemplateName(prefix,"field_key_nameCamel"),fieldNameCamel);
                        top.setVariable(new TemplateName(prefix,"field_key_class"),fieldType);
                        top.setVariable(new TemplateName(prefix,"field_key_classClean"),fieldTypeClean);
                    }
                }
                else if (IsTypeClassList(fieldTypeClass)){

                    dataField.addSection(TemplateNames.FieldIsNotMap);

                    if (1 == fieldTypeParameters.length){

                        dataField.addSection(TemplateNames.FieldIsList);

                        gap.data.List.Type listType = gap.data.List.Type.For(fieldTypeClean);
                        switch(listType){
                        case ListPrimitive:
                            dataField.addSection(TemplateNames.FieldIsListPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotCollectionLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotListLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotListLong);
                            dataField.addSection(TemplateNames.FieldIsNotListShort);
                            break;
                        case ListShort:
                            dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                            dataField.addSection(TemplateNames.FieldIsCollectionLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsListLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotListLong);
                            dataField.addSection(TemplateNames.FieldIsListShort);
                            break;
                        case ListLong:
                            dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                            dataField.addSection(TemplateNames.FieldIsCollectionLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsListLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsListLong);
                            dataField.addSection(TemplateNames.FieldIsNotListShort);
                            break;
                        }

                        String typeComponent = fieldTypeParameters[0];
                        dataField.setVariable(TemplateNames.FieldListComponent,typeComponent);

                        if (IsTypeOf(typeComponent,"HasName"))
                            dataField.addSection(TemplateNames.FieldListComponentNamed);

                        ClassDescriptor component = gap.odl.Main.ClassDescriptorFor(typeComponent);
                        if (null != component){
                            String componentKind = ClassKind(component);
                            if (null != componentKind)
                                dataField.setVariable(TemplateNames.FieldListComponentKind,componentKind);
                        }

                        dataField.setVariable(TemplateNames.FieldImplClassName,ListClassName(fieldTypeClean,className,typeComponent));
                    }
                    else
                        throw new ODStateException(field,"Field '"+fieldName+"' type list missing type parameter.");
                }
                else if (IsTypeClassMap(fieldTypeClass)){

                    dataField.addSection(TemplateNames.FieldIsNotList);

                    if (2 == fieldTypeParameters.length){

                        dataField.addSection(TemplateNames.FieldIsMap);

                        OD.MapChild mapChild = new OD.MapChild(field);
                        switch(mapChild.mapType){
                        case MapPrimitive:
                            dataField.addSection(TemplateNames.FieldIsMapPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotCollectionLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotMapLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotMapLong);
                            dataField.addSection(TemplateNames.FieldIsNotMapShort);
                            break;
                        case MapShort:
                            dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
                            dataField.addSection(TemplateNames.FieldIsCollectionLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsMapLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotMapLong);
                            dataField.addSection(TemplateNames.FieldIsMapShort);
                            break;
                        case MapLong:
                            dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
                            dataField.addSection(TemplateNames.FieldIsCollectionLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsMapLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsMapLong);
                            dataField.addSection(TemplateNames.FieldIsNotMapShort);
                            break;
                        }


                        String typeComponentFrom = mapChild.childKeyFieldType;
                        String typeComponentFromName = mapChild.childKeyFieldName;
                        String typeComponentTo = mapChild.childValueClassName;
                        dataField.setVariable(TemplateNames.FieldMapComponentFrom,typeComponentFrom);
                        dataField.setVariable(TemplateNames.FieldMapComponentFromName,typeComponentFromName);
                        dataField.setVariable(TemplateNames.FieldMapComponentFromNameCamel,Camel(typeComponentFromName));
                        dataField.setVariable(TemplateNames.FieldMapComponentTo,typeComponentTo);
                        dataField.setVariable(TemplateNames.FieldMapComponent,typeComponentTo);

                        if (IsTypeOf(typeComponentTo,"HasName"))
                            dataField.addSection(TemplateNames.FieldMapComponentNamed);

                        ClassDescriptor componentTo = gap.odl.Main.ClassDescriptorFor(typeComponentTo);
                        if (null != componentTo){
                            String componentToKind = ClassKind(componentTo);
                            if (null != componentToKind)
                                dataField.setVariable(TemplateNames.FieldMapComponentKind,componentToKind);
                        }

                        dataField.setVariable(TemplateNames.FieldImplClassName,MapClassName(fieldTypeClean,className,typeComponentFrom,typeComponentTo));
                    }
                    else
                        throw new ODStateException(field,"Field '"+fieldName+"' type map missing type parameter.");
                }
                else {
                    dataField.addSection(TemplateNames.FieldIsNotKey);
                    if (isPersistent && isInheritable)
                        dataField.addSection(TemplateNames.FieldIsInheritable);
                }
            }
        }

        if (null != defaultSortBy)
            top.setVariable(new TemplateName(prefix,"class_defaultSortBy"), defaultSortBy);
        else
            top.setVariable(new TemplateName(prefix,"class_defaultSortBy"), defaultSortByOpt);

        /*
         * Methods
         */
        if (cd.hasMethods()){

            for (MethodDescriptor method: cd.getMethods()){

                String method_name = method.getName();
                if (null != method_name){
                    String method_body = gap.Strings.TextToString(method.getBody());
                    if (null != method_body){

                        TemplateDataDictionary methods = top.addSection(new TemplateName(prefix,"method"));
                        methods.setVariable(TemplateNames.MethodName,method_name);
                        methods.setVariable(TemplateNames.MethodBody,method_body);

                        TemplateDataDictionary mb = top.addSection(new TemplateName(prefix,"method_"+method_name+"_with_body"));
                        mb.setVariable(TemplateNames.Body,method_body);

                        String method_type = ToString(method.getType());
                        if (null != method_type){
                            TemplateDataDictionary ma = top.addSection(new TemplateName(prefix,"method_"+method_name+"_with_type"));
                            ma.setVariable(TemplateNames.Type,method_type);
                            methods.setVariable(TemplateNames.MethodType,method_type);
                        }
                        else
                            top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_type"));

                        if (method instanceof MethodDescriptor.Arguments){
                            MethodDescriptor.Arguments ma = (MethodDescriptor.Arguments)method;
                            if (ma.hasArguments()){
                                String method_arguments = ma.getArguments();
                                if (null != method_arguments){
                                    TemplateDataDictionary td = top.addSection(new TemplateName(prefix,"method_"+method_name+"_with_args"));
                                    td.setVariable(TemplateNames.Args,method_arguments);
                                    methods.setVariable(TemplateNames.MethodArguments,method_arguments);
                                }
                                else
                                    top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_args"));
                            }
                            else
                                top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_args"));
                        }
                        else
                            top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_args"));

                        if (method instanceof MethodDescriptor.Exceptions){
                            MethodDescriptor.Exceptions ma = (MethodDescriptor.Exceptions)method;
                            if (ma.hasExceptions()){
                                String method_exceptions = ma.getExceptions();
                                if (null != method_exceptions){
                                    TemplateDataDictionary td = top.addSection(new TemplateName(prefix,"method_"+method_name+"_with_excs"));
                                    td.setVariable(TemplateNames.Excs,method_exceptions);
                                    methods.setVariable(new TemplateName(prefix,"method_exceptions"),method_exceptions);
                                }
                                else
                                    top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_excs"));
                            }
                            else
                                top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_excs"));
                        }
                        else
                            top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_excs"));
                    }
                    else {
                        top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_body"));
                    }
                }
            }
        }

        /*
         * Current template model requires 'key'.
         */
        if (null != key)
            return;
        else
            throw new ODStateException(cd,"Model requires a field having type 'com.google.appengine.api.datastore.Key'.");

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
