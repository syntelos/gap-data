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
        public final static TemplateName Args = new TemplateName("args");
        public final static TemplateName Body = new TemplateName("body");
        public final static TemplateName DataModel = new TemplateName("data_model");
        public final static TemplateName Excs = new TemplateName("excs");
        public final static TemplateName Field = new TemplateName("field");
        public final static TemplateName FieldClass = new TemplateName("field_class");
        public final static TemplateName FieldClassClean = new TemplateName("field_classClean");
        public final static TemplateName FieldClassCleanClean = new TemplateName("field_classCleanClean");
        public final static TemplateName FieldImplClassName = new TemplateName("field_impl_class_name");
        public final static TemplateName FieldIsCollection = new TemplateName("field_is_collection");
        public final static TemplateName FieldIsPrimitiveCollection = new TemplateName("field_is_primitive_collection");
        public final static TemplateName FieldIsNotPrimitiveCollection = new TemplateName("field_is_not_primitive_collection");

        public final static TemplateName FieldIsInheritable = new TemplateName("field_is_inheritable");
        public final static TemplateName FieldIsKey = new TemplateName("field_is_key");
        public final static TemplateName FieldIsList = new TemplateName("field_is_list");
        public final static TemplateName FieldIsPrimitive = new TemplateName("field_is_primitive");
        public final static TemplateName FieldIsNotPrimitive = new TemplateName("field_is_not_primitive");
        public final static TemplateName FieldIsBigTable = new TemplateName("field_is_bigTable");
        public final static TemplateName FieldIsNotBigTable = new TemplateName("field_is_not_bigTable");
        public final static TemplateName FieldIsListLong = new TemplateName("field_is_list_long");
        public final static TemplateName FieldIsListLongOrShort = new TemplateName("field_is_list_long_or_short");
        public final static TemplateName FieldIsListPrimitive = new TemplateName("field_is_list_primitive");
        public final static TemplateName FieldIsListShort = new TemplateName("field_is_list_short");
        public final static TemplateName FieldIsMap = new TemplateName("field_is_map");
        public final static TemplateName FieldIsMapLong = new TemplateName("field_is_map_long");
        public final static TemplateName FieldIsMapLongOrShort = new TemplateName("field_is_map_long_or_short");
        public final static TemplateName FieldIsMapPrimitive = new TemplateName("field_is_map_primitive");
        public final static TemplateName FieldIsMapShort = new TemplateName("field_is_map_short");
        public final static TemplateName FieldIsNotCollection = new TemplateName("field_is_not_collection");

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
        public final static TemplateName FieldFromObjectPrefix = new TemplateName("field_from_object_prefix");
        public final static TemplateName FieldFromObjectSuffix = new TemplateName("field_from_object_suffix");
        public final static TemplateName ImportSpec = new TemplateName("import_spec");
        public final static TemplateName InterfaceClass = new TemplateName("interface_class");
        public final static TemplateName ListClassName = new TemplateName("list_class_name");
        public final static TemplateName MapClassName = new TemplateName("map_class_name");
        public final static TemplateName MapKeyFieldName = new TemplateName("map_key_field_name");
        public final static TemplateName MapKeyFieldNameCamel = new TemplateName("map_key_field_nameCamel");
        public final static TemplateName MapKeyType = new TemplateName("map_key_type");
        public final static TemplateName MapPValueType = new TemplateName("map_pvalue_type");
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

            if (IsClassRelationPrimary(cd)){

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
                return false;
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

                parent = Classes.For(classRelationParent);
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

                parent = Classes.For(classRelationParent);
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
         * Fields & data
         */
        FieldDescriptor key = null;

        if (cd.hasFields()){

            for (FieldDescriptor field : cd.getFields()){

                String fieldName = field.getName();
                String fieldNameCamel = Camel(fieldName);
                String fieldType = OD.MapChild.Type(ToString(field.getType()));
                String fieldTypeClean = CleanTypeName(fieldType);
                String fieldTypeCleanClean = CleanCleanTypeName(fieldType);
                ClassDescriptor fieldTypeClassDescriptor = Classes.For(fieldType);
                Class fieldTypeClass = FieldClass(packageName,fieldType,imports);
                Primitive fieldTypePrimitive = Primitive.For(fieldTypeClass);
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
                    top.addSection(new TemplateName(prefix,"field"), dataField);

                    /*
                     * Populate 'pfield' section
                     */
                    if (IsFieldUnique(field)){
                        isInheritable = false;

                        defaultSortByOpt = fieldName;

                        dataField.addSection(TemplateNames.FieldIsNotInheritable);

                        TemplateDataDictionary field_is = dataField.addSection(TemplateNames.FieldIsUnique);

                        field_is.setVariable(TemplateNames.DataModel,"*unique");

                        /*
                         * Global section 'field_unique'
                         */
                        TemplateDataDictionary topDataFieldH = top.showSection(new TemplateName(prefix,"field_unique")).get(0);

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
			else if (IsTypeClassBigTable(field,fieldTypeClass)){
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.KeyToString(");
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringSuffix,")");
			}
                        else if (IsTypeClassCollection(field,fieldTypeClass)){
			    throw new ODStateException(field,"Unique field '"+fieldName+"' of type collection.");
			}
                        else {
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings."+fieldTypeCleanClean+"ToString(");
                            topDataFieldHF.setVariable(TemplateNames.FieldToStringSuffix,")");
                        }
                    }
                    else {
                        dataField.addSection(TemplateNames.FieldIsNotUnique);
                    }
                }
                else if (IsTypeClassCollection(field,fieldTypeClass)){
                    isCollection = true;

                    dataField = top.addSection(new TemplateName(prefix,"cfield"));
                    top.addSection(new TemplateName(prefix,"field"), dataField);
                    /*
                     * Populate 'cfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsNotUnique);
                }
                else if (IsFieldRelation(field)){
                    isRelation = true;

                    dataField = top.addSection(new TemplateName(prefix,"rfield"));
                    top.addSection(new TemplateName(prefix,"field"), dataField);
                    /*
                     * Populate 'rfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsNotUnique);

                    if ((!IsTypeClassKey(fieldTypeClass)) && null != fieldTypeClass && IsNotTypeClassBigTable(fieldTypeClass))
                        throw new ODStateException(field,"Relation field '"+fieldName+"' is not a subclass of 'gap.data.BigTable'.");

                }
                else {
                    isTransient = true;

                    dataField = top.addSection(new TemplateName(prefix,"tfield"));
                    top.addSection(new TemplateName(prefix,"field"), dataField);
                    /*
                     * Populate 'tfield' section
                     */
                    dataField.addSection(TemplateNames.FieldIsNotUnique);
                    {
                        TemplateDataDictionary field_is = dataField.addSection(TemplateNames.FieldIsTransient);
                        field_is.setVariable(TemplateNames.DataModel,"*transient");
                    }
                }

                if (isCollection)
                    dataField.addSection(TemplateNames.FieldIsCollection);
                else
                    dataField.addSection(TemplateNames.FieldIsNotCollection);

                /*
                 * Populate 'dataField' name and type information
                 */
                dataField.setVariable(TemplateNames.FieldName,fieldName);
                dataField.setVariable(TemplateNames.FieldNameCamel,fieldNameCamel);
                dataField.setVariable(TemplateNames.FieldClass,fieldType);
                dataField.setVariable(TemplateNames.FieldClassClean,fieldTypeClean);
                dataField.setVariable(TemplateNames.FieldClassCleanClean,fieldTypeCleanClean);
                if (IsTypeClassString(fieldTypeClass)){
                    dataField.setVariable(TemplateNames.FieldToStringPrefix,"");
                    dataField.setVariable(TemplateNames.FieldToStringSuffix,"");
                }
		else if (IsTypeClassBigTable(field,fieldTypeClass)){
		    dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings.KeyToString(");
		    dataField.setVariable(TemplateNames.FieldToStringSuffix,")");
		}
		else if (IsTypeClassCollection(field,fieldTypeClass)){
		    /*
		     * [TODO] Is the missing case encountered?
		     */
		    //throw new ODStateException(field,"Field '"+fieldName+"' of type collection.");
		}
                else {
                    dataField.setVariable(TemplateNames.FieldToStringPrefix,"gap.Strings."+fieldTypeCleanClean+"ToString(");
                    dataField.setVariable(TemplateNames.FieldToStringSuffix,")");
                }
                if (null != fieldTypePrimitive){
                    dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"gap.Objects."+fieldTypeCleanClean+"FromObject(");
                    dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");
                    dataField.addSection(TemplateNames.FieldIsPrimitive);
                    dataField.addSection(TemplateNames.FieldIsNotBigTable);
                }
                else {
                    dataField.addSection(TemplateNames.FieldIsNotPrimitive);
                    if (IsTypeClassBigTable(fieldTypeClassDescriptor,fieldTypeClass)){
                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,FullClassName(fieldTypeClassDescriptor,fieldTypeClass)+".FromObject(");
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,")");
                        dataField.addSection(TemplateNames.FieldIsBigTable);
                    }
                    else {
                        dataField.setVariable(TemplateNames.FieldFromObjectPrefix,"("+fieldType+')');
                        dataField.setVariable(TemplateNames.FieldFromObjectSuffix,"");
                        dataField.addSection(TemplateNames.FieldIsNotBigTable);
                    }
                }

                if (IsTypeClassKey(fieldTypeClass)){
                    isInheritable = false;

                    dataField.addSection(TemplateNames.FieldIsNotInheritable);

                    TemplateDataDictionary field_is = dataField.addSection(TemplateNames.FieldIsKey);

                }
                else if (IsTypeClassList(fieldTypeClass)){

                    dataField.addSection(TemplateNames.FieldIsNotMap);

                    if (1 == fieldTypeParameters.length){

                        dataField.addSection(TemplateNames.FieldIsList);

                        gap.data.List.Type listType = gap.data.List.Type.For(fieldTypeClean);
                        switch(listType){
                        case ListPrimitive:
                            dataField.addSection(TemplateNames.FieldIsPrimitiveCollection);
                            dataField.addSection(TemplateNames.FieldIsListPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotListLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotListLong);
                            dataField.addSection(TemplateNames.FieldIsNotListShort);
                            break;
                        case ListShort:
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                            dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                            dataField.addSection(TemplateNames.FieldIsListLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotListLong);
                            dataField.addSection(TemplateNames.FieldIsListShort);
                            break;
                        case ListLong:
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                            dataField.addSection(TemplateNames.FieldIsNotListPrimitive);
                            dataField.addSection(TemplateNames.FieldIsListLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsListLong);
                            dataField.addSection(TemplateNames.FieldIsNotListShort);
                            break;
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

                            dataField.setVariable(TemplateNames.FieldImplClassName,ListClassName(fieldTypeClean,className,component));
                        }
                        else {
                            Class componentClass = FieldClass(pkg,typeParameter,imports);
                            if (null != componentClass){
                                Primitive primitive = Primitive.For(componentClass);
                                if (null != primitive)
                                    dataField.setVariable(TemplateNames.FieldImplClassName,ListClassName(fieldTypeClean,className,primitive));
                                else
                                    throw new ODStateException(field,"Field '"+fieldName+"' in '"+listType.name()+"' expected primitive component type '"+typeParameter+"'.");
                            }
                            else
                                throw new ODStateException(field,"Field '"+fieldName+"' in '"+listType.name()+"' unable to resolve class of component type '"+typeParameter+"'.");
                        }
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
                            dataField.addSection(TemplateNames.FieldIsPrimitiveCollection);
                            dataField.addSection(TemplateNames.FieldIsMapPrimitive);
                            dataField.addSection(TemplateNames.FieldIsNotMapLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotMapLong);
                            dataField.addSection(TemplateNames.FieldIsNotMapShort);
                            break;
                        case MapShort:
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
                            dataField.addSection(TemplateNames.FieldIsNotMapPrimitive);
                            dataField.addSection(TemplateNames.FieldIsMapLongOrShort);
                            dataField.addSection(TemplateNames.FieldIsNotMapLong);
                            dataField.addSection(TemplateNames.FieldIsMapShort);
                            break;
                        case MapLong:
                            dataField.addSection(TemplateNames.FieldIsNotPrimitiveCollection);
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
    }

}
