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

import gap.util.ArrayList;
import gap.data.List;

import com.google.appengine.api.datastore.Text;

/**
 * 
 *
 */
public final class Template
    extends Object
    implements gap.data.LastModified,
               gap.data.HasName
{

    private volatile String name;
    private volatile Long lastModified;    
    private volatile Text templateSourceHapax;    
    private volatile List<TemplateNode> templateTargetHapax;




    public Template() {
        super();
    }
    public Template(String name) {
        super();
        this.setName(name);
    }



    public final boolean hasName(){
        return (null != this.name);
    }
    public final boolean hasNotName(){
        return (null == this.name);
    }
    public final String getName(){
        return this.name;
    }
    public final void setName(String name){
        this.name = name;
    }
    public final boolean hasLastModified(){
        return (null != this.lastModified);
    }
    public final boolean hasNotLastModified(){
        return (null == this.lastModified);
    }
    public final Long getLastModified(){
        return this.lastModified;
    }
    public final void setLastModified(Long lastModified){
        this.lastModified = lastModified;
    }
    public final boolean hasTemplateSourceHapax(){
        return (null != this.templateSourceHapax);
    }
    public final boolean hasNotTemplateSourceHapax(){
        return (null == this.templateSourceHapax);
    }
    public final Text getTemplateSourceHapax(){
        return templateSourceHapax;
    }
    public final void setTemplateSourceHapax(Text templateSourceHapax){
        this.templateSourceHapax = templateSourceHapax;
    }
    public final boolean hasTemplateTargetHapax(){
        return (this.getTemplateTargetHapax().isNotEmpty());
    }
    public final boolean hasNotTemplateTargetHapax(){
        return (this.getTemplateTargetHapax().isEmpty());
    }
    public final List<TemplateNode> getTemplateTargetHapax(){
        List<TemplateNode> templateTargetHapax = this.templateTargetHapax;
        if (null == templateTargetHapax){
            templateTargetHapax = new ArrayList<TemplateNode>();
            this.templateTargetHapax = templateTargetHapax;
        }
        return templateTargetHapax;
    }
    public final void setTemplateTargetHapax(List<TemplateNode> templateTargetHapax){
        this.templateTargetHapax = templateTargetHapax;
    }
    public final boolean isEmptyTemplateTargetHapax(){
        List<TemplateNode> collection = this.templateTargetHapax;
        if (null != collection)
            return collection.isEmpty();
        else
            return true;
    }
    public final boolean isNotEmptyTemplateTargetHapax(){
        List<TemplateNode> collection = this.templateTargetHapax;
        if (null != collection)
            return (!collection.isEmpty());
        else
            return false;
    }
}
