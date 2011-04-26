/*
 * Hapax3
 * Copyright (c) 2007 Doug Coker
 * Copyright (c) 2009 John Pritchard
 * 
 * The MIT License
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gap.hapax;

import gap.data.List;

/**
 * The implementation of {@link TemplateDictionary} as consumed by
 * {@link Template} is the specification of this interface.
 * 
 * The nominal implementation is {@link gap.hapax.AbstractData} in the
 * "gae" subproject.
 * 
 * @author jdp
 */
public interface TemplateDataDictionary
    extends java.lang.Cloneable
{
    /**
     * Degenerate case of an in- memory Data Dictionary to implement
     * "complement" in {@link TemplateName}.  This implementation has
     * no variables.
     */
    public static class Abstract
        extends lxl.Map<String,List.Short<TemplateDataDictionary>>
        implements TemplateDataDictionary
    {
        public final static List.Short<TemplateDataDictionary> EmptySection = 
            new gap.util.ArrayList<TemplateDataDictionary>();

        public final static Abstract EmptyDictionary = new Abstract();


        protected TemplateDataDictionary parent;


        public Abstract(){
            super();
        }
        public Abstract(TemplateDataDictionary parent){
            super();
            this.parent = parent;
        }


        public void renderComplete(){
        }
        public Abstract clone(){

            return (Abstract)super.clone();
        }
        public Abstract clone(TemplateDataDictionary parent){
            Abstract clone = this.clone();
            clone.parent = parent;
            return clone;
        }
        public TemplateDataDictionary getParent(){
            return this.parent;
        }
        public boolean hasVariable(TemplateName name){
            if (null != this.parent)
                return this.parent.hasVariable(name);
            else
                return false;
        }
        public String getVariable(TemplateName name){
            if (null != this.parent)
                return this.parent.getVariable(name);
            else
                return null;
        }
        public void setVariable(TemplateName name, String value){

            throw new UnsupportedOperationException();
        }
        public List.Short<TemplateDataDictionary> getSection(TemplateName name){

            List.Short<TemplateDataDictionary> section = name.dereferenceC(this);

            if (null == section){
                /*
                 * Inherit
                 */
                TemplateDataDictionary parent = this.parent;
                if (null != parent){
                    section = parent.getSection(name);
                    /*
                     * parent implements name polarity
                     */
                    if (null != section){

                        section = Abstract.SectionClone(this,section);

                        name.reference(this,section);
                    }
                }
                if (null == section){
                    /*
                     * Synthetic
                     */
                    if (this.hasVariable(name)){

                        if (!name.complement)
                            section = this.showSection(name);
                    }
                    else {
                        if (name.complement)
                            section = this.showSection(name);
                    }
                }
            }
            /*
             * Section name resolution
             */
            if (name.is(0))
                return section;
            else if (null == section)
                return null;
            else {
                TemplateDataDictionary sectionData = name.dereferenceC(this,section);
                if (null != sectionData)
                    return sectionData.getSection(new TemplateName(name));
                else
                    return null;
            }
        }
        public List.Short<TemplateDataDictionary> showSection(TemplateName name){

            List.Short<TemplateDataDictionary> section = name.dereferenceT(this);
            if (null != section){
                if (name.is(0))
                    return section;
                else {
                    TemplateDataDictionary data = name.dereferenceT(this,section);
                    if (null != data)
                        return data.showSection(new TemplateName(name));
                }
            }

            TemplateDataDictionary data = new Abstract(this);

            section = name.reference(this,data);

            if (name.is(0))
                return section;
            else
                return data.showSection(new TemplateName(name));
        }
        public TemplateDataDictionary addSection(TemplateName name){

            return this.addSection(name,new Abstract(this));
        }
        public TemplateDataDictionary addSection(TemplateName name, TemplateDataDictionary data){
            if (null == name || null == data)
                throw new IllegalArgumentException();
            else {

                name.reference(this,data);

                if (name.is(0))
                    return data;
                else
                    return data.addSection(new TemplateName(name));
            }
        }
        public final static List.Short<TemplateDataDictionary> Add(List.Short<TemplateDataDictionary> list, 
                                                                   TemplateDataDictionary data)
        {
            if (null == list){
                list = new gap.util.ArrayList<TemplateDataDictionary>();
            }
            list.add(data);
            return list;
        }
        public final static List.Short<TemplateDataDictionary> SectionClone(TemplateDataDictionary parent, List.Short<TemplateDataDictionary> section){

            List.Short<TemplateDataDictionary> sectionClone = section.clone();

            for (int sectionIndex = 0, sectionCount = sectionClone.size(); sectionIndex < sectionCount; sectionIndex++){
                TemplateDataDictionary sectionItem = sectionClone.get(sectionIndex);
                TemplateDataDictionary sectionItemClone = sectionItem.clone(parent);
                sectionClone.set(sectionIndex,sectionItemClone);
            }

            return sectionClone;
        }
    }


    /**
     * Consumption complete.
     */
    public void renderComplete();
    /**
     * @return Deep clone of dictionary carries parent.
     */
    public TemplateDataDictionary clone();
    /**
     * @return Deep clone replaces parent.
     */
    public TemplateDataDictionary clone(TemplateDataDictionary parent);
    /**
     * @return First most immediate ancestor.
     */
    public TemplateDataDictionary getParent();
    /**
     * @return Get variable would be not null
     */
    public boolean hasVariable(TemplateName name);
    /**
     * @return Variable value from this or parent
     */
    public String getVariable(TemplateName name);
    /**
     * Define variable.
     */
    public void setVariable(TemplateName name, String value);
    /**
     * @return Child, child from ancestry, or synthetic on variable.
     */
    public List.Short<TemplateDataDictionary> getSection(TemplateName name);
    /**
     * @return Existing section, or with no existing section create new.
     */
    public List.Short<TemplateDataDictionary> showSection(TemplateName name);
    /**
     * @return New section.
     */
    public TemplateDataDictionary addSection(TemplateName name);
    /**
     * Intended to permit the reflection of a section element within
     * multiple named sections.
     * 
     * @return Section argument.
     */
    public TemplateDataDictionary addSection(TemplateName name, TemplateDataDictionary section);

}
