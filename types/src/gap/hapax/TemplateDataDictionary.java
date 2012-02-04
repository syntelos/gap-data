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
     * Interface to support name dereferencing 
     * @see TemplateName
     */
    public interface Sections
        extends TemplateDataDictionary
    {
        public lxl.Map<String,List.Short<TemplateDataDictionary>> getSections();
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
     * @return First most immediate ancestor.
     */
    public void setParent(TemplateDataDictionary p);
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
