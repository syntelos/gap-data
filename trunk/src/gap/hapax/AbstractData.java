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
import gap.util.ArrayList;

/**
 * @author jdp
 */
public class AbstractData
    extends Object
    implements TemplateDataDictionary
{
    protected transient TemplateDataDictionary parent;
    protected transient lxl.Map<String,String> variables;
    protected transient lxl.Map<String,List.Short<TemplateDataDictionary>> sections;


    public AbstractData(){
        super();
    }
    public AbstractData(TemplateDataDictionary parent){
        super();
        this.parent = parent;
    }

    public void renderComplete(){
        this.parent = null;
        lxl.Map<String,String> variables = this.variables;
        if (null != variables)
            variables.clear();
        lxl.Map<String,List.Short<TemplateDataDictionary>> sections = this.sections;
        if (null != sections){
            for (List.Short<TemplateDataDictionary> list: sections.values()){
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

        lxl.Map<String,String> variables = this.variables;
        if (null != variables && variables.containsKey(name.getName())){
            return true;
        }
        TemplateDataDictionary parent = this.parent;
        if (null != parent){
            return parent.hasVariable(name);
        }
        return false;
    }
    public String getVariable(TemplateName name){

        lxl.Map<String,String> variables = this.variables;
        if (null != variables){
            String value = variables.get(name.getName());
            if (null != value)
                return value;
        }
        TemplateDataDictionary parent = this.parent;
        if (null != parent){
            return parent.getVariable(name);
        }
        return null;
    }
    public void setVariable(TemplateName name, String value){

        lxl.Map<String,String> variables = this.variables;
        if (null == variables){
            variables = new lxl.Map<String,String>();
            this.variables = variables;
        }
        variables.put(name.getName(),value);
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){

        lxl.Map<String,List.Short<TemplateDataDictionary>> sections = this.sections;
        List.Short<TemplateDataDictionary> section = null;
        if (null != sections){
            section = sections.get(name.getComponent(0));
        }
        if (null == section){
            /*
             * Inherit
             */
            TemplateDataDictionary parent = this.parent;
            if (null != parent){
                section = parent.getSection(name);
                if (null != section){

                    section = SectionClone(this,section);

                    sections.put(name.getComponent(0),section);
                }
            }
            if (null == section){
                /*
                 * Synthetic
                 */
                if (this.hasVariable(name))
                    section = this.showSection(name);
                else
                    return null;
            }
        }
        /*
         * Section name resolution
         */
        if (name.is(0))
            return section;
        else {
            TemplateDataDictionary sectionData = name.dereference(0,section);
            return sectionData.getSection(new TemplateName(name));
        }
    }
    public List.Short<TemplateDataDictionary> showSection(TemplateName name){

        lxl.Map<String,List.Short<TemplateDataDictionary>> sections = this.sections;
        if (null == sections){
            sections = new lxl.Map<String,List.Short<TemplateDataDictionary>>();
            this.sections = sections;

            TemplateDataDictionary newSection = new AbstractData(this);
            List.Short<TemplateDataDictionary> newSectionList = Add(null,newSection);
            sections.put(name.getComponent(0),newSectionList);
            if (name.is(0))
                return newSectionList;
            else
                return newSection.showSection(new TemplateName(name));
        }
        else {
            List.Short<TemplateDataDictionary> section = sections.get(name.getComponent(0));
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

        return this.addSection(name,newSection);
    }

    public TemplateDataDictionary addSection(TemplateName name, TemplateDataDictionary newSection){
        if (null == name || null == newSection)
            throw new IllegalArgumentException();
        else {
            lxl.Map<String,List.Short<TemplateDataDictionary>> sections = this.sections;
            if (null == sections){
                sections = new lxl.Map<String,List.Short<TemplateDataDictionary>>();
                this.sections = sections;
                List.Short<TemplateDataDictionary> section = Add(null,newSection);
                sections.put(name.getComponent(0),section);
            }
            else {
                List.Short<TemplateDataDictionary> section = sections.get(name.getComponent(0));
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
    }
    public final static List.Short<TemplateDataDictionary> Add(List.Short<TemplateDataDictionary> list, TemplateDataDictionary data){
        if (null == list){
            List.Short<TemplateDataDictionary> newSectionList = new ArrayList<TemplateDataDictionary>();
            newSectionList.add(data);
            return newSectionList;
        }
        else {
            List.Short<TemplateDataDictionary> shortSectionList = (List.Short<TemplateDataDictionary>)list;
            shortSectionList.add(data);
            return shortSectionList;
        }
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
