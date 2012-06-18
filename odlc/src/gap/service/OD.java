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
import gap.data.Collection;
import gap.data.HasName;
import static gap.data.List.Type.*;
import static gap.data.Map.Type.*;
import gap.data.TableClass;
import gap.hapax.TemplateName;
import gap.hapax.TemplateRenderer;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateException;
import gap.service.od.ClassDescriptor;
import gap.service.od.FieldDescriptor;
import gap.service.od.ImportDescriptor;
import gap.service.od.MethodDescriptor;
import gap.service.od.ODStateException;
import gap.service.od.PackageDescriptor;
import gap.util.ClassName;
import gap.util.Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import lxl.List;
import java.util.StringTokenizer;


/**
 * Object data model for generating JPL source from description.
 * 
 * @author jdp
 */
public final class OD
    extends gap.service.Classes
{
    public final static class TemplateNames {
        /*
         * Field properties
         */
        public final static TemplateName Field = new TemplateName("field");

        public final static TemplateName FieldName = new TemplateName("field_name");
        public final static TemplateName FieldNameCamel = new TemplateName("field_nameCamel");

        public final static TemplateName FieldClass = new TemplateName("field_class");
        public final static TemplateName FieldClassClean = new TemplateName("field_classClean");
        public final static TemplateName FieldClassCleanClean = new TemplateName("field_classCleanClean");
        public final static TemplateName FieldImplClassName = new TemplateName("field_impl_class_name");
        /*
         * Field kind booleans
         */
        public final static TemplateName FieldIsUnique = new TemplateName("field_is_unique");
        public final static TemplateName FieldIsNotUnique = new TemplateName("field_is_not_unique");

        public final static TemplateName FieldIsTransient = new TemplateName("field_is_transient");
        public final static TemplateName FieldIsNotTransient = new TemplateName("field_is_not_transient");

        public final static TemplateName FieldIsRelation = new TemplateName("field_is_relation");
        public final static TemplateName FieldIsNotRelation = new TemplateName("field_is_not_relation");

        public final static TemplateName FieldIsCollection = new TemplateName("field_is_collection");
        public final static TemplateName FieldIsCollectionNotPrimitive = new TemplateName("field_is_collection_not_primitive");
        public final static TemplateName FieldIsNotCollection = new TemplateName("field_is_not_collection");
        /*
         */
        public final static TemplateName FieldIsInheritable = new TemplateName("field_is_inheritable");
        public final static TemplateName FieldIsNotInheritable = new TemplateName("field_is_not_inheritable");
        /*
         * Field type booleans
         */
        public final static TemplateName FieldIsPrimitive = new TemplateName("field_is_primitive");
        public final static TemplateName FieldIsNotPrimitive = new TemplateName("field_is_not_primitive");

        public final static TemplateName FieldIsKey = new TemplateName("field_is_key");
        public final static TemplateName FieldIsNotKey = new TemplateName("field_is_not_key");

        public final static TemplateName FieldIsEnum = new TemplateName("field_is_enum");
        public final static TemplateName FieldIsNotEnum = new TemplateName("field_is_not_enum");

        public final static TemplateName FieldIsBoolean = new TemplateName("field_is_boolean");
        public final static TemplateName FieldIsNotBoolean = new TemplateName("field_is_not_boolean");

        public final static TemplateName FieldIsNumber = new TemplateName("field_is_number");
        public final static TemplateName FieldIsNotNumber = new TemplateName("field_is_not_number");

        public final static TemplateName FieldIsCharacter = new TemplateName("field_is_character");
        public final static TemplateName FieldIsNotCharacter = new TemplateName("field_is_not_character");

        public final static TemplateName FieldIsBigInteger = new TemplateName("field_is_biginteger");
        public final static TemplateName FieldIsNotBigInteger = new TemplateName("field_is_not_biginteger");

        public final static TemplateName FieldIsBigDecimal = new TemplateName("field_is_bigdecimal");
        public final static TemplateName FieldIsNotBigDecimal = new TemplateName("field_is_not_bigdecimal");

        public final static TemplateName FieldIsSerializable = new TemplateName("field_is_serializable");
        public final static TemplateName FieldIsNotSerializable = new TemplateName("field_is_not_serializable");

        public final static TemplateName FieldIsBigTable = new TemplateName("field_is_bigTable");
        public final static TemplateName FieldIsNotBigTable = new TemplateName("field_is_not_bigTable");

        public final static TemplateName FieldIsBigTableInterface = new TemplateName("field_is_bigTableInterface");
        public final static TemplateName FieldIsNotBigTableInterface = new TemplateName("field_is_not_bigTableInterface");

        public final static TemplateName FieldIsLong = new TemplateName("field_is_long");
        public final static TemplateName FieldIsShort = new TemplateName("field_is_short");
        /*
         * Field type collection booleans
         */
        public final static TemplateName FieldIsPrimitiveCollection = new TemplateName("field_is_primitive_collection");
        public final static TemplateName FieldIsNotPrimitiveCollection = new TemplateName("field_is_not_primitive_collection");

        public final static TemplateName FieldIsList = new TemplateName("field_is_list");
        public final static TemplateName FieldIsNotList = new TemplateName("field_is_not_list");

        public final static TemplateName FieldIsListLong = new TemplateName("field_is_list_long");
        public final static TemplateName FieldIsNotListLong = new TemplateName("field_is_not_list_long");

        public final static TemplateName FieldIsListLongOrShort = new TemplateName("field_is_list_long_or_short");
        public final static TemplateName FieldIsNotListLongOrShort = new TemplateName("field_is_not_list_long_or_short");

        public final static TemplateName FieldIsListPrimitive = new TemplateName("field_is_list_primitive");
        public final static TemplateName FieldIsNotListPrimitive = new TemplateName("field_is_not_list_primitive");

        public final static TemplateName FieldIsListShort = new TemplateName("field_is_list_short");
        public final static TemplateName FieldIsNotListShort = new TemplateName("field_is_not_list_short");
        /*
         * Field type collection map booleans
         */
        public final static TemplateName FieldIsMap = new TemplateName("field_is_map");
        public final static TemplateName FieldIsNotMap = new TemplateName("field_is_not_map");

        public final static TemplateName FieldIsMapLong = new TemplateName("field_is_map_long");
        public final static TemplateName FieldIsNotMapLong = new TemplateName("field_is_not_map_long");

        public final static TemplateName FieldIsMapLongOrShort = new TemplateName("field_is_map_long_or_short");
        public final static TemplateName FieldIsNotMapLongOrShort = new TemplateName("field_is_not_map_long_or_short");

        public final static TemplateName FieldIsMapPrimitive = new TemplateName("field_is_map_primitive");
        public final static TemplateName FieldIsNotMapPrimitive = new TemplateName("field_is_not_map_primitive");

        public final static TemplateName FieldIsMapShort = new TemplateName("field_is_map_short");
        public final static TemplateName FieldIsNotMapShort = new TemplateName("field_is_not_map_short");
        /*
         * Field type collection properties
         */
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
        /*
         * Field operators
         */
        public final static TemplateName FieldToStringPrefix = new TemplateName("field_to_string_prefix");
        public final static TemplateName FieldToStringSuffix = new TemplateName("field_to_string_suffix");
        public final static TemplateName FieldFromStringPrefix = new TemplateName("field_from_string_prefix");
        public final static TemplateName FieldFromStringSuffix = new TemplateName("field_from_string_suffix");
        public final static TemplateName FieldFromObjectPrefix = new TemplateName("field_from_object_prefix");
        public final static TemplateName FieldFromObjectSuffix = new TemplateName("field_from_object_suffix");

        public final static TemplateName FieldNumberValue = new TemplateName("field_numberValue");
        /*
         * Field operators for type collection
         */
        public final static TemplateName ListClassName = new TemplateName("list_class_name");
        public final static TemplateName ListType = new TemplateName("list_type");
        public final static TemplateName MapClassName = new TemplateName("map_class_name");
        public final static TemplateName MapKeyFieldName = new TemplateName("map_key_field_name");
        public final static TemplateName MapKeyFieldNameCamel = new TemplateName("map_key_field_nameCamel");
        public final static TemplateName MapKeyType = new TemplateName("map_key_type");
        public final static TemplateName MapPValueType = new TemplateName("map_pvalue_type");

        public final static TemplateName MethodArguments = new TemplateName("method_arguments");
        public final static TemplateName MethodBody = new TemplateName("method_body");
        public final static TemplateName MethodName = new TemplateName("method_name");
        public final static TemplateName MethodType = new TemplateName("method_type");
        public final static TemplateName MethodExceptions = new TemplateName("method_excs");
        /*
         * Class properties
         */
        public final static TemplateName ImportSpec = new TemplateName("import_spec");
        public final static TemplateName InterfaceClass = new TemplateName("interface_class");
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

        public final static TemplateName WebXmlSection = new TemplateName("servlet");
        public final static TemplateName WebXmlSectionName = new TemplateName("name");
        public final static TemplateName WebXmlSectionClass = new TemplateName("class_name");
        public final static TemplateName WebXmlSectionUrl = new TemplateName("url_pattern");
        public final static TemplateName WebXmlSectionLoad = new TemplateName("load_on");
    }

    public final static void GenerateBeanSource(TemplateName xtm, PackageDescriptor pkg, lxl.List<ImportDescriptor> imports,
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

    public final static boolean GenerateServletSource(TemplateName xtm, PackageDescriptor pkg, lxl.List<ImportDescriptor> imports,
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

                return true;
            }
            catch (TemplateException exc){
                throw new TemplateException("In template '"+xtm.source+"'.",exc);
            }
        }
        else
            throw new IllegalArgumentException();
    }
    public final static void GenerateWebXml(TemplateName xtm, Services servlets, File webXml)
        throws ODStateException, IOException, TemplateException
    {
        if (null != xtm && null != servlets && null != webXml){

            TemplateRenderer template = Templates.GetTemplate(xtm);
            TemplateDataDictionary top = new gap.hapax.AbstractData();
            TemplateDataDictionary servlet;

            for (ClassName servletClassName : servlets){
                ClassDescriptor cd = Classes.ForServlet(servletClassName.getName());
                if (null != cd){
                    servlet = top.addSection(TemplateNames.WebXmlSection);
                    servlet.setVariable(TemplateNames.WebXmlSectionName,cd.getName());
                    servlet.setVariable(TemplateNames.WebXmlSectionClass,servletClassName.getName());
                    servlet.setVariable(TemplateNames.WebXmlSectionUrl,WebXmlPathStar(cd));
                    servlet.setVariable(TemplateNames.WebXmlSectionLoad,"-1");
                }
                else
                    throw new IllegalStateException("Missing class descriptor for '"+servletClassName+"'");
            }

            PrintWriter out = new PrintWriter(new FileWriter(webXml));
            try {
                template.render(top,out);

                return;
            }
            catch (TemplateException exc){
                throw new TemplateException("In template '"+xtm.source+"'.",exc);
            }
            finally {
                out.close();
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static void GenerateListSource(TemplateName xtm, PackageDescriptor pkg, lxl.List<ImportDescriptor> imports,
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

            DefineImports(top,imports);

            DefineClass(pkg,parent,imports,top,TemplateNames.PrefixParent);

            Class childClass = FieldClass(PackageName(pkg),childClassName,imports);

            switch(listType){
            case ListPrimitive:{
                Primitive child = Primitive.For(childClassName,childClass);
                if (null != child){
                    DefineClass(PackageFor(child,childClass),child,ImportsFor(child,parent,imports),top,TemplateNames.PrefixChild);

                    top.setVariable(TemplateNames.ListClassName,listClassName);
                    top.setVariable(TemplateNames.ListType,"Primitive");

                    try {
                        template.render(top,out); 
                    }
                    catch (TemplateException exc){
                        throw new TemplateException("In template '"+xtm.source+"'.",exc);
                    }
                }
                else
                    throw new TemplateException("Primitive type not found for name '"+childClassName+"'.");
  
            }
                break;
            case ListShort:
            case ListLong:{
                ClassDescriptor child = Classes.For(childClassName);
                if (null != child){

                    DefineClass(PackageFor(child,pkg),child,ImportsFor(child,imports),top,TemplateNames.PrefixChild);

                    top.setVariable(TemplateNames.ListClassName,listClassName);
                    if (gap.data.List.Type.ListShort == listType)
                        top.setVariable(TemplateNames.ListType,"Short");
                    else
                        top.setVariable(TemplateNames.ListType,"Long");

                    try {
                        template.render(top,out); 
                    }
                    catch (TemplateException exc){
                        throw new TemplateException("In template '"+xtm.source+"'.",exc);
                    }
                }
                else if (null != childClass){

                    DefineClass(pkg,childClass,imports,top,TemplateNames.PrefixChild);

                    top.setVariable(TemplateNames.ListClassName,listClassName);
                    if (gap.data.List.Type.ListShort == listType)
                        top.setVariable(TemplateNames.ListType,"Short");
                    else
                        top.setVariable(TemplateNames.ListType,"Long");

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
                break;
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public final static void GenerateMapSource(TemplateName xtm, PackageDescriptor pkg, lxl.List<ImportDescriptor> imports,
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

            DefineImports(top,imports);

            DefineClass(pkg,parent,imports,top,TemplateNames.PrefixParent);

            Class childClass = FieldClass(PackageName(pkg),mapChild.childValueClassName,imports);

            switch (mapType){
            case MapPrimitive:{
                Primitive child = Primitive.For(mapChild.childValueClassName,childClass);
                if (null != child){
                    DefineClass(PackageFor(child,childClass),child,ImportsFor(child,parent,imports),top,TemplateNames.PrefixChild);

                    top.setVariable(TemplateNames.MapClassName,mapClassName);
                    top.setVariable(TemplateNames.MapKeyType,mapChild.childKeyFieldType);
                    top.setVariable(TemplateNames.MapPValueType,child.local);

                    try {
                        template.render(top,out); 
                    }
                    catch (TemplateException exc){
                        throw new TemplateException("In template '"+xtm.source+"'.",exc);
                    }
                }
                else
                    throw new TemplateException("Primitive type not found for name '"+mapChild.childValueClassName+"'.");
            }
                break;
            case MapShort: //(fall-through)
            case MapLong:{
                ClassDescriptor child = Classes.For(mapChild.childValueClassName);
                if (null != child){

                    DefineClass(PackageFor(child,pkg),child,ImportsFor(child,imports),top,TemplateNames.PrefixChild);

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
                else if (null != childClass){

                    DefineClass(pkg,childClass,imports,top,TemplateNames.PrefixChild);

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
                break;
            }
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
    public final static void DefineImports(TemplateDataDictionary top, lxl.List<ImportDescriptor> imports){

        DefineImports(top,imports,null);
    }
    public final static void DefineImports(TemplateDataDictionary top, lxl.List<ImportDescriptor> imports, TemplateName prefix){

        for (ImportDescriptor imp : imports){
            TemplateDataDictionary imd = top.addSection(new TemplateName(prefix,"import"));
            if (imp.hasPackageSpec())
                imd.setVariable(TemplateNames.ImportSpec,imp.getPackageSpec());
            else if (imp.hasClassName())
                imd.setVariable(TemplateNames.ImportSpec,imp.getClassName());
        }
    }
    /**
     * Used for the primitive children of collection types.
     */
    public final static void DefineClass(PackageDescriptor pkg, Primitive pd, lxl.List<ImportDescriptor> imports, TemplateDataDictionary top, TemplateName prefix)
        throws ODStateException, IOException
    {
        String packageName = PackageName(pd);
        String className = ClassName(pd);
        String classNameDecamel = Decamel(className);
        /*
         * Class globals
         */
        top.setVariable(new TemplateName(prefix,"package_name"), packageName);
        top.setVariable(new TemplateName(prefix,"class_name"), className);
        top.setVariable(new TemplateName(prefix,"class_nameDecamel"), classNameDecamel);
    }
    /**
     * User class
     */
    public final static void DefineClass(PackageDescriptor pkg, Class uclass, lxl.List<ImportDescriptor> imports, TemplateDataDictionary top, TemplateName prefix)
        throws ODStateException, IOException
    {
        String packageName = PackageName(uclass);
        String className = ClassName(uclass);
        String classNameDecamel = Decamel(className);
        /*
         * Class globals
         */
        top.setVariable(new TemplateName(prefix,"package_name"), packageName);
        top.setVariable(new TemplateName(prefix,"class_name"), className);
        top.setVariable(new TemplateName(prefix,"class_nameDecamel"), classNameDecamel);
    }
    public final static void DefineClass(PackageDescriptor pkg, ClassDescriptor cd, lxl.List<ImportDescriptor> imports, TemplateDataDictionary top)
        throws ODStateException, IOException
    {
        DefineClass(pkg,cd,imports,top,null);
    }
    public final static void DefineClass(PackageDescriptor pkg, ClassDescriptor cd, lxl.List<ImportDescriptor> imports, TemplateDataDictionary top, TemplateName prefix)
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

            top.addSection(new TemplateName(prefix,"class_re_not_parent"));
            top.addSection(new TemplateName(prefix,"class_re_not_child"));

        }
        else if (ClassDescriptor.Relation.Type.Parent.equals(classRelation)){

            top.addSection(new TemplateName(prefix,"class_re_parent"));
            top.addSection(new TemplateName(prefix,"class_re_not_child"));

        }
        else if (ClassDescriptor.Relation.Type.Child.equals(classRelation)){

            top.addSection(new TemplateName(prefix,"class_re_not_parent"));


            TemplateDataDictionary child = top.addSection(new TemplateName(prefix,"class_re_child"));

            if (null == classRelationParent)
                throw new ODStateException(cd,"The object data model requires a parent class name.");
            else {

                top.setVariable(new TemplateName(prefix,"parent_class_name"),classRelationParent);

                parent = Classes.For(classRelationParent);
                if (null == parent)
                    throw new ODStateException(cd,String.format("Parent class '%s' of '%s' not found.",classRelationParent,className));
            }
        }
        else
            throw new IllegalStateException("Unrecognized class relation "+classRelation.name());

        cd.setDefinitionClassName(packageName+'.'+className);

        /*
         * Imports
         */
        DefineImports(top,imports,prefix);

        /*
         * Interfaces
         */
        String[] interfaces = ClassImplements(cd);
        for (String inf : interfaces){
            TemplateDataDictionary ind = top.addSection(new TemplateName(prefix,"implements"));
            ind.setVariable(TemplateNames.InterfaceClass,inf);
        }

        /*
         * Fields
         */
        FieldDescriptor key = null;

        TemplateDataDictionary field_unique = null;

        if (cd.hasFields()){


            for (FieldDescriptor field : cd.getFields()){

                final String fieldName = field.getName();

                if (IsFieldNameIllegal(fieldName))
                    throw new ODStateException(field,String.format("Reserved field name '%s'",fieldName));

                final String fieldNameCamel = Camel(fieldName);
                final String fieldType = OD.MapChild.Type(ToString(field.getType()));
                final String fieldTypeClean = CleanTypeName(fieldType);
                final String fieldTypeCleanClean = CleanCleanTypeName(fieldType);
                final ClassDescriptor fieldTypeClassDescriptor = Classes.For(fieldType);
                final Class fieldTypeClass = FieldClass(packageName,fieldType,imports);
                final boolean isBigTable = IsTypeClassBigTable(fieldTypeClassDescriptor,fieldTypeClass);
                final boolean isBigTableInterface;
                if (isBigTable)
                    isBigTableInterface = IsTypeClassBigTableInterface(fieldTypeClassDescriptor);
                else
                    isBigTableInterface = false;

                final boolean isCollection = IsTypeClassCollection(field,fieldTypeClass);

                final boolean isEnumerated = IsFieldEnumerated(field,fieldTypeClassDescriptor,fieldTypeClass);
                final Primitive fieldTypePrimitive = Primitive.For(fieldTypeClass,isEnumerated,isBigTable,isCollection);
                final String[] fieldTypeParameters = FieldTypeParameters(ToString(field.getType()));

                final gap.data.List.Type listType;
                if (IsTypeClassList(fieldTypeClass))
                    listType = gap.data.List.Type.For(fieldTypeClean);
                else
                    listType = null;

                final OD.MapChild mapChild;
                if (IsTypeClassMap(fieldTypeClass))
                    mapChild = new OD.MapChild(field);
                else
                    mapChild = null;

                final boolean isNumber = (null != fieldTypePrimitive && fieldTypePrimitive.isNumber());

                TemplateDataDictionary dataField = null;
                boolean isPersistent = false;
                boolean isInheritable = true;
                boolean isRelation = false;
                boolean isTransient = false;
                boolean isUnique = false;



                if (IsFieldDefaultSortBy(field))
                    defaultSortBy = fieldName;

                /*
                 * Create dataField section with field kind booleans
                 */
                if (IsFieldPersistent(field,fieldTypeClass)){
                    isPersistent = true;

                    dataField = top.addSection(new TemplateName(prefix,"pfield"));
                    top.addSection(new TemplateName(prefix,"field"), dataField);

                    /*
                     * Populate 'pfield' section
                     */
                    if (IsFieldUnique(field)){
                        if (isCollection)
			    throw new ODStateException(field,"Unique field '"+fieldName+"' of collection type.");
                        else {
                            isUnique = true;

                            isInheritable = false;

                            defaultSortByOpt = fieldName;

                            dataField.addSection(TemplateNames.FieldIsUnique);
                            dataField.addSection(TemplateNames.FieldIsNotCollection);
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                            /*
                             * Global section 'field_unique'
                             */
                            if (null == field_unique){

                                top.addSection(new TemplateName(prefix,"field_unique"), dataField);
                            }
                        }
                    }
                    else {
                        dataField.addSection(TemplateNames.FieldIsNotUnique);

                        if (isCollection){
                            dataField.addSection(TemplateNames.FieldIsCollection);
                            if (null != listType){

                                if (ListPrimitive == listType){
                                    dataField.addSection(TemplateNames.FieldIsPrimitiveCollection);
                                }
                                else {
                                    dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                                    dataField.addSection(TemplateNames.FieldIsCollectionNotPrimitive);
                                }
                            }
                            else if (null != mapChild){

                                if (MapPrimitive == mapChild.mapType){
                                    dataField.addSection(TemplateNames.FieldIsPrimitiveCollection);
                                }
                                else {
                                    dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                                    dataField.addSection(TemplateNames.FieldIsCollectionNotPrimitive);
                                }
                            }
                            else
                                throw new ODStateException(field,"Collection field '"+fieldName+"' is neither list nor map.");
                        }
                        else {
                            dataField.addSection(TemplateNames.FieldIsNotCollection);
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                        }
                    }
                    dataField.addSection(TemplateNames.FieldIsNotTransient);
                    dataField.addSection(TemplateNames.FieldIsNotRelation);
                }
                else if (isCollection){

                    dataField = top.addSection(new TemplateName(prefix,"cfield"));
                    top.addSection(new TemplateName(prefix,"field"), dataField);
                    /*
                     * Populate 'cfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsCollection);
                    dataField.addSection(TemplateNames.FieldIsNotUnique);
                    dataField.addSection(TemplateNames.FieldIsNotTransient);
                    dataField.addSection(TemplateNames.FieldIsNotRelation);

                    if (null != listType){

                        if (ListPrimitive == listType){
                            dataField.addSection(TemplateNames.FieldIsPrimitiveCollection);
                        }
                        else {
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                            dataField.addSection(TemplateNames.FieldIsCollectionNotPrimitive);
                        }
                    }
                    else if (null != mapChild){

                        if (MapPrimitive == mapChild.mapType){
                            dataField.addSection(TemplateNames.FieldIsPrimitiveCollection);
                        }
                        else {
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                            dataField.addSection(TemplateNames.FieldIsCollectionNotPrimitive);
                        }
                    }
                    else
                        throw new ODStateException(field,"Collection field '"+fieldName+"' is neither list nor map.");
                }
                else if (IsFieldRelation(field)){
                    isRelation = true;

                    dataField = top.addSection(new TemplateName(prefix,"rfield"));
                    top.addSection(new TemplateName(prefix,"field"), dataField);
                    /*
                     * Populate 'rfield' section
                     */
                    if ((!IsTypeClassKey(fieldTypeClass)) && null != fieldTypeClass && IsNotTypeClassBigTable(fieldTypeClass))
                        throw new ODStateException(field,"Relation field '"+fieldName+"' is not a subclass of 'gap.data.BigTable'.");
                    else {
                        dataField.addSection(TemplateNames.FieldIsNotCollection);
                        dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                        dataField.addSection(TemplateNames.FieldIsNotUnique);
                        dataField.addSection(TemplateNames.FieldIsNotTransient);
                        dataField.addSection(TemplateNames.FieldIsNotRelation);
                    }
                }
                else {
                    isTransient = true;

                    dataField = top.addSection(new TemplateName(prefix,"tfield"));
                    top.addSection(new TemplateName(prefix,"field"), dataField);
                    /*
                     * Populate 'tfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsNotCollection);
                    dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                    dataField.addSection(TemplateNames.FieldIsNotUnique);
                    dataField.addSection(TemplateNames.FieldIsTransient);
                    dataField.addSection(TemplateNames.FieldIsNotRelation);
                }

                /*
                 * Field common properties
                 */
                dataField.setVariable(TemplateNames.FieldName,fieldName);
                dataField.setVariable(TemplateNames.FieldNameCamel,fieldNameCamel);
                dataField.setVariable(TemplateNames.FieldClass,fieldType);
                dataField.setVariable(TemplateNames.FieldClassClean,fieldTypeClean);
                dataField.setVariable(TemplateNames.FieldClassCleanClean,fieldTypeCleanClean);

                /*
                 * I/O functions ToString, FromString, FromObject
                 * and Is/IsNot selectors
                 */
                if (null != fieldTypePrimitive){

                    dataField.addSection(TemplateNames.FieldIsPrimitive);
                    dataField.addSection(TemplateNames.FieldIsNotBigTable);
                    dataField.addSection(TemplateNames.FieldIsNotBigTableInterface);

                    dataField.addSection(TemplateNames.FieldIsNotMap);
                    dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
                    dataField.addSection(TemplateNames.FieldIsNotMapLong);
                    dataField.addSection(TemplateNames.FieldIsNotMapLongOrShort);
                    dataField.addSection(TemplateNames.FieldIsNotMapShort);
                    dataField.addSection(TemplateNames.FieldIsNotList);
                    dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                    dataField.addSection(TemplateNames.FieldIsNotListLongOrShort);
                    dataField.addSection(TemplateNames.FieldIsNotListLong);
                    dataField.addSection(TemplateNames.FieldIsNotListShort);

                    if (isNumber){
                        dataField.addSection(TemplateNames.FieldIsNumber);
                        switch(fieldTypePrimitive){
                        case Short:
                            dataField.setVariable(TemplateNames.FieldNumberValue,"shortValue");
                            break;
                        case Integer:
                            dataField.setVariable(TemplateNames.FieldNumberValue,"intValue");
                            break;
                        case Long:
                            dataField.setVariable(TemplateNames.FieldNumberValue,"longValue");
                            break;
                        case Float:
                            dataField.setVariable(TemplateNames.FieldNumberValue,"floatValue");
                            break;
                        case Double:
                            dataField.setVariable(TemplateNames.FieldNumberValue,"doubleValue");
                            break;
                        default:
                            throw new ODStateException(field,"Number field '"+fieldName+"' type not recognized.");
                        }
                    }
                    else
                        dataField.addSection(TemplateNames.FieldIsNotNumber);

                    switch(fieldTypePrimitive){
                    case Key:
                        isInheritable = false;
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.KeyToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"gap.Strings.KeyFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects.KeyFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    case Enum:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.EnumToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"("+fieldType+")gap.Strings.EnumFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"("+fieldType+")gap.Objects.EnumFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    case Boolean:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.BooleanToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"gap.Strings.BooleanFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects.BooleanFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    case Character:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.CharacterToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"gap.Strings.CharacterFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects.CharacterFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    case String:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,"");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,"");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects.StringFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    case BigInteger:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.BigIntegerToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"gap.Strings.BigIntegerFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects.BigIntegerFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    case BigDecimal:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.BigDecimalToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"gap.Strings.BigDecimalFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects.BigDecimalFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    case Serializable:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.SerializableToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"("+fieldType+")gap.Strings.SerializableFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"("+fieldType+")gap.Objects.SerializableFromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsSerializable);
                        break;
                    default:
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings."+fieldTypeCleanClean+"ToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"gap.Strings."+fieldTypeCleanClean+"FromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects."+fieldTypeCleanClean+"FromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");

                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);
                        break;
                    }
                }
                else {

                    if (isBigTable){
                        dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.KeyToString(");
                        dataField.setVariable(TemplateNames.FieldToStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromStringPrefix,"gap.Strings.KeyFromString(");
                        dataField.setVariable(TemplateNames.FieldFromStringSuffix,")");

                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,FullClassName(fieldTypeClassDescriptor,fieldTypeClass)+".FromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");
                        /*
                         */
                        dataField.addSection(TemplateNames.FieldIsBigTable);
                        if (isBigTableInterface)
                            dataField.addSection(TemplateNames.FieldIsBigTableInterface);
                        else
                            dataField.addSection(TemplateNames.FieldIsNotBigTableInterface);

                        if (IsFieldShort(cd,field,fieldTypeClassDescriptor))
                            dataField.addSection(TemplateNames.FieldIsShort);
                        else
                            dataField.addSection(TemplateNames.FieldIsLong);

                        dataField.addSection(TemplateNames.FieldIsNotPrimitive);
                        dataField.addSection(TemplateNames.FieldIsNotKey);
                        dataField.addSection(TemplateNames.FieldIsNotEnum);
                        dataField.addSection(TemplateNames.FieldIsNotBoolean);
                        dataField.addSection(TemplateNames.FieldIsNotNumber);
                        dataField.addSection(TemplateNames.FieldIsNotCharacter);
                        dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                        dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                        dataField.addSection(TemplateNames.FieldIsNotSerializable);

                        dataField.addSection(TemplateNames.FieldIsNotMap);
                        dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
                        dataField.addSection(TemplateNames.FieldIsNotMapLong);
                        dataField.addSection(TemplateNames.FieldIsNotMapLongOrShort);
                        dataField.addSection(TemplateNames.FieldIsNotMapShort);
                        dataField.addSection(TemplateNames.FieldIsNotList);
                        dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                        dataField.addSection(TemplateNames.FieldIsNotListLongOrShort);
                        dataField.addSection(TemplateNames.FieldIsNotListLong);
                        dataField.addSection(TemplateNames.FieldIsNotListShort);
                    }
                    else {
                        dataField.addSection(TemplateNames.FieldIsNotBigTable);
                        dataField.addSection(TemplateNames.FieldIsNotBigTableInterface);
                        /*
                         */
                        if (null != listType){
                            dataField.addSection(TemplateNames.FieldIsNotMap);

                            dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"("+fieldType+')');
                            dataField.setVariable(TemplateNames.FieldFromObjectSuffix,"");

                            dataField.addSection(TemplateNames.FieldIsNotPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotKey);
                            dataField.addSection(TemplateNames.FieldIsNotEnum);
                            dataField.addSection(TemplateNames.FieldIsNotBoolean);
                            dataField.addSection(TemplateNames.FieldIsNotNumber);
                            dataField.addSection(TemplateNames.FieldIsNotCharacter);
                            dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                            dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                            dataField.addSection(TemplateNames.FieldIsNotSerializable);

                            dataField.addSection(TemplateNames.FieldIsNotMap);
                            dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotMapLong);
                            dataField.addSection(TemplateNames.FieldIsNotMapLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotMapShort);

                            if (1 == fieldTypeParameters.length){

                                dataField.addSection(TemplateNames.FieldIsList);

                                switch(listType){
                                case ListPrimitive:
                                    dataField.addSection(TemplateNames.FieldIsListPrimitive);
                                    dataField.addSection(TemplateNames.FieldIsNotListLongOrShort);
                                    dataField.addSection(TemplateNames.FieldIsNotListLong);
                                    dataField.addSection(TemplateNames.FieldIsNotListShort);
                                    break;
                                case ListShort:
                                    dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                                    dataField.addSection(TemplateNames.FieldIsListLongOrShort);
                                    dataField.addSection(TemplateNames.FieldIsNotListLong);
                                    dataField.addSection(TemplateNames.FieldIsListShort);
                                    break;
                                case ListLong:
                                    dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                                    dataField.addSection(TemplateNames.FieldIsListLongOrShort);
                                    dataField.addSection(TemplateNames.FieldIsListLong);
                                    dataField.addSection(TemplateNames.FieldIsNotListShort);
                                    break;
                                default:
                                    throw new ODStateException(field,"List field '"+fieldName+"' has list type '"+listType+"'.");
                                }

                                String typeParameter = fieldTypeParameters[0];
                                dataField.setVariable(TemplateNames.FieldListComponent,typeParameter);

                                if (IsTypeOf(typeParameter,"HasName"))
                                    dataField.addSection(TemplateNames.FieldListComponentNamed);

                                ClassDescriptor component = Classes.For(typeParameter);
                                if (null != component){
                                    String componentKind = ClassKind(component);
                                    if (null != componentKind)
                                        dataField.setVariable(TemplateNames.FieldListComponentKind,componentKind);

                                    dataField.setVariable(TemplateNames.FieldImplClassName,ListImplClassName(fieldTypeClean,className,component));
                                }
                                else if (ListPrimitive == listType){

                                    Class componentClass = FieldClass(pkg,typeParameter,imports);
                                    if (null != componentClass){
                                        Primitive componentPrimitive = Primitive.For(componentClass);

                                        dataField.setVariable(TemplateNames.FieldImplClassName,ListPrimitiveClassName(componentPrimitive));
                                    }
                                    else
                                        throw new ODStateException(field,"In field '"+fieldType+" "+fieldName+"', require pre-compilation for class analysis of component type '"+typeParameter+"'.");
                                }
                                else
                                    throw new ODStateException(field,"In field '"+fieldType+" "+fieldName+"', expecting List.Primitive for component type '"+typeParameter+"'.");
                            }
                            else
                                throw new ODStateException(field,"Field '"+fieldName+"' type list missing type parameter.");
                        }
                        else if (null != mapChild){

                            dataField.addSection(TemplateNames.FieldIsNotList);

                            dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"("+fieldType+')');
                            dataField.setVariable(TemplateNames.FieldFromObjectSuffix,"");

                            dataField.addSection(TemplateNames.FieldIsNotPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotKey);
                            dataField.addSection(TemplateNames.FieldIsNotEnum);
                            dataField.addSection(TemplateNames.FieldIsNotBoolean);
                            dataField.addSection(TemplateNames.FieldIsNotNumber);
                            dataField.addSection(TemplateNames.FieldIsNotCharacter);
                            dataField.addSection(TemplateNames.FieldIsNotBigInteger);
                            dataField.addSection(TemplateNames.FieldIsNotBigDecimal);
                            dataField.addSection(TemplateNames.FieldIsNotSerializable);

                            dataField.addSection(TemplateNames.FieldIsNotList);
                            dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotListLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotListLong);
                            dataField.addSection(TemplateNames.FieldIsNotListShort);

                            if (2 == fieldTypeParameters.length){

                                dataField.addSection(TemplateNames.FieldIsMap);

                                switch(mapChild.mapType){
                                case MapPrimitive:
                                    dataField.addSection(TemplateNames.FieldIsMapPrimitive);
                                    dataField.addSection(TemplateNames.FieldIsNotMapLongOrShort);
                                    dataField.addSection(TemplateNames.FieldIsNotMapLong);
                                    dataField.addSection(TemplateNames.FieldIsNotMapShort);
                                    break;
                                case MapShort:

                                    dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
                                    dataField.addSection(TemplateNames.FieldIsMapLongOrShort);
                                    dataField.addSection(TemplateNames.FieldIsNotMapLong);
                                    dataField.addSection(TemplateNames.FieldIsMapShort);
                                    break;
                                case MapLong:
                                    dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
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

                                ClassDescriptor componentTo = Classes.For(typeComponentTo);
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
                            throw new ODStateException(field,"Invalid table or collection type of field '"+fieldType+" "+fieldName+"'.");
                        }
                    }
                }

                /*
                 * Field inheritance
                 */
                if (isPersistent && isInheritable)
                    dataField.addSection(TemplateNames.FieldIsInheritable);
                else
                    dataField.addSection(TemplateNames.FieldIsNotInheritable);
            }
        }

        if (null == field_unique)
            top.showSection(new TemplateName(prefix,"not_field_unique"));


        if (null != defaultSortBy)
            top.setVariable(new TemplateName(prefix,"class_defaultSortBy"), defaultSortBy);
        else if (null != defaultSortByOpt)
            top.setVariable(new TemplateName(prefix,"class_defaultSortBy"), defaultSortByOpt);
        else
            throw new ODStateException(cd,"The object data model requires a default sort by.");

        /*
         * Methods
         */
        if (cd.hasMethods()){

            for (MethodDescriptor method: cd.getMethods()){

                String method_name = method.getName();
                if (null != method_name){
                    String method_body = gap.Strings.TextToString(method.getBody());
                    if (null != method_body){

                        TemplateDataDictionary methodData = top.addSection(new TemplateName(prefix,"method"));
                        methodData.setVariable(TemplateNames.MethodName,method_name);
                        methodData.setVariable(TemplateNames.MethodBody,method_body);

                        TemplateDataDictionary mb = top.addSection(new TemplateName(prefix,"method_"+method_name+"_with_body"));
                        mb.setVariable(TemplateNames.MethodBody,method_body);

                        String method_type = ToString(method.getType());
                        if (null != method_type){
                            TemplateDataDictionary ma = top.addSection(new TemplateName(prefix,"method_"+method_name+"_with_type"));
                            ma.setVariable(TemplateNames.Type,method_type);
                            methodData.setVariable(TemplateNames.MethodType,method_type);
                        }
                        else
                            top.showSection(new TemplateName(prefix,"method_"+method_name+"_without_type"));

                        if (method instanceof MethodDescriptor.Arguments){
                            MethodDescriptor.Arguments ma = (MethodDescriptor.Arguments)method;
                            if (ma.hasArguments()){
                                String method_arguments = ma.getArguments();
                                if (null != method_arguments){
                                    top.addSection(new TemplateName(prefix,"method_"+method_name+"_with_args"));

                                    methodData.setVariable(TemplateNames.MethodArguments,method_arguments);
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
                                    td.setVariable(TemplateNames.MethodExceptions,method_exceptions);
                                    methodData.setVariable(new TemplateName(prefix,"method_exceptions"),method_exceptions);
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
    }

}
