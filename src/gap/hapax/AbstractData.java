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
package gap.hapax;

import gap.data.List;

/**
 * @author jdp
 */
public class AbstractData
    extends Object
    implements TemplateDataDictionary
{
    protected transient TemplateDataDictionary parent;
    protected transient java.util.Map<String,String> variables;
    protected transient java.util.Map<String,List<TemplateDataDictionary>> sections;


    public AbstractData(){
        super();
    }
    public AbstractData(TemplateDataDictionary parent){
        super();
        this.parent = parent;
    }

    public void renderComplete(){
        this.parent = null;
        java.util.Map<String,String> variables = this.variables;
        if (null != variables)
            variables.clear();
        java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;
        if (null != sections){
            for (List<TemplateDataDictionary> list: sections.values()){
                for (TemplateDataDictionary item: list){
                    item.renderComplete();
                }
            }
            sections.clear();
        }
    }
    public TemplateDataDictionary clone(){
        try {
            return (TemplateDataDictionary)super.clone();
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new java.lang.Error(exc);
        }
    }
    public TemplateDataDictionary clone(TemplateDataDictionary parent){
        try {
            AbstractData clone = (AbstractData)super.clone();
            clone.parent = parent;
            return clone;
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new java.lang.Error(exc);
        }
    }
    public TemplateDataDictionary getParent(){
        return this.parent;
    }
    public boolean hasVariable(TemplateName name){

        java.util.Map<String,String> variables = this.variables;
        if (null != variables){
            if (variables.containsKey(name.getName()))
                return true;
            else {
                TemplateDataDictionary parent = this.parent;
                if (null != parent)
                    return parent.hasVariable(name);
            }
        }
        return false;
    }
    public String getVariable(TemplateName name){

        java.util.Map<String,String> variables = this.variables;
        if (null != variables){
            String value = variables.get(name.getName());
            if (null != value)
                return value;
            else {
                TemplateDataDictionary parent = this.parent;
                if (null != parent)
                    return parent.getVariable(name);
            }
        }
        return null;
    }
    public void setVariable(TemplateName name, String value){

        java.util.Map<String,String> variables = this.variables;
        if (null == variables){
            variables = new java.util.HashMap<String,String>();
            this.variables = variables;
        }
        variables.put(name.getName(),value);
    }
    public List<TemplateDataDictionary> getSection(TemplateName name){

        java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;

        if (null != sections && sections.containsKey(name.getComponent(0))){

            List<TemplateDataDictionary> section = sections.get(name.getComponent(0));
            if (name.is(0))
                return section;
            else {
                TemplateDataDictionary sectionData = name.dereference(0,section);
                return sectionData.getSection(new TemplateName(name));
            }
        }
        else {
            /*
             * Inherit
             */
            TemplateDataDictionary parent = this.parent;
            if (null != parent){
                List<TemplateDataDictionary> section = parent.getSection(name);
                if (null != section)
                    return section;
            }
            /*
             * Synthetic
             */
            if (this.hasVariable(name))
                return this.showSection(name);
            else
                return null;
        }
    }
    public List<TemplateDataDictionary> showSection(TemplateName name){

        java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;
        if (null == sections){
            sections = new java.util.HashMap<String,List<TemplateDataDictionary>>();
            this.sections = sections;

            TemplateDataDictionary newSection = new AbstractData(this);
            List<TemplateDataDictionary> newSectionList = Add(null,newSection);
            sections.put(name.getComponent(0),newSectionList);
            if (name.is(0))
                return newSectionList;
            else
                return newSection.showSection(new TemplateName(name));
        }
        else {
            List<TemplateDataDictionary> section = sections.get(name.getComponent(0));
            if (null != section){
                if (name.is(0))
                    return section;
                else {
                    TemplateDataDictionary sectionData = name.dereference(0,section);
                    return sectionData.showSection(new TemplateName(name));
                }
            }
            else {
                TemplateDataDictionary newSection = new AbstractData(this);
                section = Add(section,newSection);
                this.sections.put(name.getComponent(0),section);
                if (name.is(0))
                    return section;
                else
                    return newSection.showSection(new TemplateName(name));
            }
        }
    }
    public TemplateDataDictionary addSection(TemplateName name){

        TemplateDataDictionary newSection = new AbstractData(this);

        java.util.Map<String,List<TemplateDataDictionary>> sections = this.sections;
        if (null == sections){
            sections = new java.util.HashMap<String,List<TemplateDataDictionary>>();
            this.sections = sections;
            List<TemplateDataDictionary> section = Add(null,newSection);
            sections.put(name.getComponent(0),section);
        }
        else {
            List<TemplateDataDictionary> section = sections.get(name.getComponent(0));
            if (null == section){
                section = Add(section,newSection);
                sections.put(name.getComponent(0),section);
            }
            else
                section = Add(section,newSection);
        }
        if (name.is(0))
            return newSection;
        else
            return newSection.addSection(new TemplateName(name));
    }

    public final static List<TemplateDataDictionary> Add(List<TemplateDataDictionary> list, TemplateDataDictionary data){
        if (null == list){
            List.Primitive<TemplateDataDictionary> newSectionList = new gap.util.AbstractListPrimitive.Any<TemplateDataDictionary>();
            newSectionList.add(data);
            return newSectionList;
        }
        else if (list instanceof List.Primitive){
            List.Primitive<TemplateDataDictionary> primitiveSectionList = (List.Primitive<TemplateDataDictionary>)list;
            primitiveSectionList.add(data);
            return primitiveSectionList;
        }
        else if (list instanceof List.Short){
            List.Short<TemplateDataDictionary> shortSectionList = (List.Short<TemplateDataDictionary>)list;
            shortSectionList.add(data);
            return shortSectionList;
        }
        else
            return list;
    }
}
