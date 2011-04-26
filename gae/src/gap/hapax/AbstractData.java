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
    protected final static List.Short<TemplateDataDictionary> EmptySection = new ArrayList<TemplateDataDictionary>();

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
    public AbstractData clone(){
        try {
            return (AbstractData)super.clone();
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new InternalError();
        }
    }
    public AbstractData clone(TemplateDataDictionary parent){
        try {
            AbstractData clone = (AbstractData)super.clone();
            clone.parent = parent;
            return clone;
        }
        catch (java.lang.CloneNotSupportedException exc){
            throw new InternalError();
        }
    }
    public TemplateDataDictionary getParent(){
        return this.parent;
    }
    public void setParent(TemplateDataDictionary p){
        this.parent = p;
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
            section = name.dereferenceC(sections);
        }

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

                    if (null == sections){
                        sections = new lxl.Map<String,List.Short<TemplateDataDictionary>>();
                        this.sections = sections;
                    }
                    name.reference(sections,section);
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

        lxl.Map<String,List.Short<TemplateDataDictionary>> sections = this.sections;
        if (null == sections){
            sections = new lxl.Map<String,List.Short<TemplateDataDictionary>>();
            this.sections = sections;
        }

        List.Short<TemplateDataDictionary> section = name.dereferenceT(sections);
        if (null != section){
            if (name.is(0))
                return section;
            else {
                TemplateDataDictionary data = name.dereferenceT(this,section);
                if (null != data)
                    return data.showSection(new TemplateName(name));
            }
        }

        TemplateDataDictionary data = new AbstractData(this);

        section = name.reference(sections,data);

        if (name.is(0))
            return section;
        else
            return data.showSection(new TemplateName(name));
    }
    public TemplateDataDictionary addSection(TemplateName name){

        TemplateDataDictionary data = new AbstractData(this);

        return this.addSection(name,data);
    }
    public TemplateDataDictionary addSection(TemplateName name, TemplateDataDictionary data){
        if (null == name || null == data)
            throw new IllegalArgumentException();
        else {
            lxl.Map<String,List.Short<TemplateDataDictionary>> sections = this.sections;
            if (null == sections){
                sections = new lxl.Map<String,List.Short<TemplateDataDictionary>>();
                this.sections = sections;
            }

            name.reference(sections,data);
            try {
                data.setParent(this);
            }
            catch (RuntimeException exc){
            }

            if (name.is(0))
                return data;
            else
                return data.addSection(new TemplateName(name));
        }
    }

}
