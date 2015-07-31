# Introduction #

The Object Description Language (ODL) is a data model driving [JPL](http://books.google.com/books?id=Ww1B9O_yVGsC&lpg=PP1&ots=Sd4I8gQelB&dq=Java%20Programming%20Language&pg=PP1#v=onepage&q=&f=false) source code generation from Hapax3 templates.

The GAP ODL process employs a [Hapax Template](http://code.google.com/p/hapax2/wiki/Language) for transforming the ODL model into Java Programming Language (JPL) sourcecode.

An example ODL class is the [Hapax3 Template](http://code.google.com/p/gap-data/source/browse/trunk/odl/gap/hapax/Template.odl).

# Structure #

An ODL source code unit has a highly defined structure in plain text format.
```
 package pkg.name
 path path-name

 class ClassName 
   [implements InterfaceName]*
 {
   [field-class] FieldType fieldName
 }
```

## Field classes ##

Fields optionally have a field-class prefix from one of

| `*unique` | Required component of the instance natural identifier |
|:----------|:------------------------------------------------------|
| `*transient` | Not persistent to Memcache or Datastore (enum types)  |
| `*child`  | [Field](http://code.google.com/p/gap-data/source/browse/trunk/src/gap/odl/Field.java) [Relation](http://code.google.com/p/gap-data/source/browse/trunk/src/gap/service/od/FieldDescriptor.java) class, see [BeanData.java.xtm](http://code.google.com/p/gap-data/source/browse/trunk/web/WEB-INF/templates/BeanData.java.xtm) |
| `*sortby` | This field is the "default sort by" in listing the collection of instances of this class  |

## Field types ##

Field types include
```
 gap.data.BigTable<ODL>
 gap.Primitives
 gap.data collections, List and Map
```
as handled in [OD](http://code.google.com/p/gap-data/source/browse/trunk/src/gap/service/OD.java), and supported in [BeanData.java.xtm](http://code.google.com/p/gap-data/source/browse/trunk/web/WEB-INF/templates/BeanData.java.xtm).

# Lists #

A [list](http://code.google.com/p/gap-data/source/browse/trunk/src/gap/data/List.java) is defined in ODL as a field having type List Short or Long of Component.

For example in class [gap.hapax.Template](http://code.google.com/p/gap-data/source/browse/trunk/odl/gap/hapax/Template.odl)
```
   List.Short<TemplateNode> templateTargetHapax
```