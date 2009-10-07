package jbxml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

/**
 * DOM element processor.
 * 
 * @author jdp
 */
public final class Element  {

    public final static Class NilClass = null;

    public enum Type {
        INSTANCE,
        UNDEFINED,
        READ_ONLY_PROPERTY,
        WRITABLE_PROPERTY
    }

    public final Element parent;
    public final String ns, tagName;
    public final String id, pkg, cn, fqcn;
    public final boolean hasId, hasStaticValue, isComponent, isComponentParent, isComponentChild;
    public final Class clas;
    public final Type type;
    public final List<Attribute> attributes;

    private Resolver io;
    private Object value;
    private Reflector valueBean;

    public Element(Element parent, Resolver io, XMLStreamReader reader)
        throws XMLStreamException
    {
        super();
        this.io = io;
        this.parent = parent;
        this.ns = reader.getNamespaceURI();
        this.tagName = reader.getLocalName();
        this.pkg = io.packageName(this.ns);
        this.cn = io.camelName(this.tagName);
        this.fqcn = io.fqcn(this.pkg,this.cn);
        /*
         */
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        String id = null;

        for (int cc = 0, count = reader.getAttributeCount(); cc < count; cc++) {
            Attribute att = new Attribute(this,io,reader,cc);
            if (att.isId)
                id = att.value;
            attributes.add(att);
        }
        this.attributes = attributes;
        /*
         */
        this.id = id;
        this.hasId = (null != id);
        /*
         */
        boolean isComponent = false, isComponentParent = false, isComponentChild = false;
        Class clas = null;
        if (null != this.fqcn){
            try {
                clas = Class.forName(this.fqcn);
                Object value;
                if (this.hasId){
                    ReferenceTo pointer = ReferenceTo.Cache(clas);
                    if (pointer.isValid)
                        value = pointer.dereference(id);
                    else
                        value = clas.newInstance();
                }
                else
                    value = clas.newInstance();

                this.value = value;
                this.valueBean = new Reflector(this.value);
                isComponent = (value instanceof Component);
                if (isComponent){
                    isComponentParent = (value instanceof Component.Parent);
                    isComponentChild = (value instanceof Component.Child);
                    Component c = (Component)value;
                    c.startElement(this.valueBean);
                    if (null != parent){
                        parent.child(c);
                    }
                    else
                        io.startRoot(c);
                }
            }
            catch (Exception exc){
            }
        }
        this.isComponent = isComponent;
        this.isComponentParent = isComponentParent;
        this.isComponentChild = isComponentChild;
        this.clas = clas;
        /*
         */
        if (null != clas){
            if (null != this.value){
                /*
                 * Has value from 'fqcn' (from element name and namespace).
                 */
                this.type = Type.INSTANCE;
                this.hasStaticValue = true;
                /*
                 */
                if (null != parent && parent.isValueCollection()){
                    try {
                        parent.setValue(this.value);
                    }
                    catch (Exception ignore){
                    }
                }
            }
            else {
                this.type = Type.UNDEFINED;
                this.hasStaticValue = false;
            }
        }
        else if (null != parent){
            Reflector bean = parent.valueBean;
            if (null != bean){
                clas = bean.getType(this.tagName);
                if (null != clas){
                    if (bean.isReadOnly(this.tagName)){
                        this.type = Type.READ_ONLY_PROPERTY;
                        this.hasStaticValue = true;
                        this.value = bean.get(this.tagName);
                        if (null != this.value)
                            this.valueBean = new Reflector(this.value);
                        else
                            this.valueBean = bean;
                    }
                    else {
                        this.type = Type.WRITABLE_PROPERTY;
                        this.valueBean = bean;
                        this.hasStaticValue = false;
                    }
                }
                else {
                    this.type = Type.UNDEFINED;
                    this.valueBean = bean;
                    this.hasStaticValue = false;
                }
            }
            else {
                this.type = Type.UNDEFINED;
                this.hasStaticValue = false;
            }
        }
        else {
            this.type = Type.UNDEFINED;
            this.hasStaticValue = false;
        }
    }

    public boolean isInstance(){
        return (Type.INSTANCE == this.type || Type.WRITABLE_PROPERTY == this.type);
    }
    public boolean isUndefined(){
        return (Type.UNDEFINED == this.type);
    }
    public boolean isPropertyReadonly(){
        return (Type.READ_ONLY_PROPERTY == this.type);
    }
    public boolean isPropertyWritable(){
        return (Type.WRITABLE_PROPERTY == this.type);
    }
    public boolean isValueDictionary(){
        if (Type.READ_ONLY_PROPERTY == this.type)
            return (this.value instanceof Dictionary);
        else
            return false;
    }
    public boolean isValueList(){
        if (Type.READ_ONLY_PROPERTY == this.type)
            return (this.value instanceof List);
        else
            return false;
    }
    public boolean isValueCollection(){
        if (Type.READ_ONLY_PROPERTY == this.type)
            return (this.value instanceof List)||(this.value instanceof Dictionary);
        else
            return false;
    }
    public Object getValue(){

        Object value = this.value;

        if (null != value)
            return value;

        else if (this.hasBeanOverValue()){

            Reflector bean = this.valueBean;

            if (null != bean)

                return bean.get(this.tagName);
        }
        return null;
    }
    public boolean maySetValue2(Element instance){
        return (Type.INSTANCE == instance.type && Type.INSTANCE == this.type);
    }
    public boolean setValue2(Element instance){
        try {
            this.valueBean.put(instance.tagName,instance.getValue());
            return true;
        }
        catch (RuntimeException notSupported){
            return false;
        }
    }
    public boolean maySetValue(){
        if (Type.INSTANCE == this.type || Type.WRITABLE_PROPERTY == this.type)

            return this.hasBeanOverValue();

        else if (Type.READ_ONLY_PROPERTY == this.type)

            return (this.value instanceof List)||(this.value instanceof Dictionary);
        else
            return false;
    }
    public boolean setValue(Object value){
        if (null != value){
            if (Type.INSTANCE == this.type){
                this.getBeanOverValue().put(this.tagName,value);
                return true;
            }
            else if (Type.WRITABLE_PROPERTY == this.type){
                this.getBeanOverValue().put(this.tagName,value);
                return true;
            }
            else if (Type.READ_ONLY_PROPERTY == this.type){
                Object thisValue = this.value;
                if (thisValue instanceof List){
                    List thisList = (List)thisValue;
                    value = this.io.resolve(value,thisList);
                    thisList.add(value);
                    return true;
                }
                else if (thisValue instanceof Dictionary){
                    Dictionary thisDict = (Dictionary)thisValue;
                    value = this.io.resolve(value,thisDict);
                    thisDict.put(this.tagName,value);
                    return true;
                }
            }
        }
        return false;
    }
    public void addText(Location loc, String text){
        if (this.isComponent && this.value instanceof Component.AddText){
            Component.AddText component = (Component.AddText)this.value;
            component.addElementText(loc, text);
        }
        else if (this.maySetValue())
            this.setValue(text);
    }
    public boolean hasBeanOverValue(){
        if (Type.INSTANCE == this.type || Type.READ_ONLY_PROPERTY == this.type)
            return ((null != this.parent)&&(null != parent.getBeanOnValue()));
        else if (Type.WRITABLE_PROPERTY == this.type)
            return (null != this.valueBean);
        else
            return false;
    }
    public Reflector getBeanOverValue(){
        if (Type.INSTANCE == this.type || Type.READ_ONLY_PROPERTY == this.type)
            return parent.getBeanOnValue();
        else if (Type.WRITABLE_PROPERTY == this.type)
            return this.valueBean;
        else
            return null;
    }
    public boolean hasBeanOnValue(){
        return (Type.INSTANCE == this.type || Type.READ_ONLY_PROPERTY == this.type);
    }
    public Reflector getBeanOnValue(){
        if (Type.INSTANCE == this.type || Type.READ_ONLY_PROPERTY == this.type)
            return this.valueBean;
        else
            return null;
    }
    public void applyAttributes(Resolver io){
        if (Type.INSTANCE == this.type){

            Reflector bean = this.valueBean;

            for (Attribute att : this.attributes){

                Object value = io.resolve(att.value,bean.getType(att.localName));

                if (null != value){
                    try {
                        bean.put(att.localName,value);
                    }
                    catch (PropertyNotFoundException exc){
                    }
                }
            }
        }
        else if (this.value instanceof Dictionary){

            Dictionary dict = (Dictionary)this.value;

            for (Attribute att : this.attributes){

                Object value = io.resolve(att.value,NilClass);

                if (null != value){
                    try {
                        dict.put(att.localName,value);
                    }
                    catch (PropertyNotFoundException exc){
                    }
                }
            }
        }
    }
    public void child(Component rvalue){
        if (this.isComponent){
            Object lvalue = this.value;
            if (lvalue instanceof Component.Parent){
                ((Component.Parent)lvalue).startElement(rvalue);
            }
        }
    }
    public void end(){
        if (Type.INSTANCE == this.type){
            Object value = this.value;
            if (value instanceof Component)
                ((Component)value).endElement();
        }
    }
}
