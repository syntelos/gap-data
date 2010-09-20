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

import com.google.appengine.api.datastore.Text;

/**
 * 
 *
 */
public final class TemplateNode
    extends Object
{


    private volatile String nodeType;
    private volatile Integer lineNumber;
    private volatile Text nodeContent;    
    private volatile Integer offset;    
    private volatile Integer offsetCloseRelative;    



    public TemplateNode() {
        super();
    }
    public TemplateNode(String nodeType, Integer lineNumber, Text nodeContent) {
        super();
        this.setNodeType(nodeType);
        this.setLineNumber(lineNumber);
        this.setNodeContent(nodeContent);
    }



    public final boolean hasNodeType(){
        return (null != this.nodeType);
    }
    public final boolean hasNotNodeType(){
        return (null == this.nodeType);
    }
    public final String getNodeType(){
        return this.nodeType;
    }
    public final void setNodeType(String nodeType){

        this.nodeType = nodeType;
    }
    public final boolean hasLineNumber(){
        return (null != this.lineNumber);
    }
    public final boolean hasNotLineNumber(){
        return (null == this.lineNumber);
    }
    public final Integer getLineNumber(){
        return this.lineNumber;
    }
    public final void setLineNumber(Integer lineNumber){
        this.lineNumber = lineNumber;
    }
    public final boolean hasNodeContent(){
        return (null != this.nodeContent);
    }
    public final boolean hasNotNodeContent(){
        return (null == this.nodeContent);
    }
    public final Text getNodeContent(){
        return this.nodeContent;
    }
    public final void setNodeContent(Text nodeContent){
        this.nodeContent = nodeContent;
    }
    public final boolean hasOffset(){
        return (null != this.offset);
    }
    public final boolean hasNotOffset(){
        return (null == this.offset);
    }
    public final Integer getOffset(){
        return this.offset;
    }
    public final void setOffset(Integer offset){
        this.offset = offset;
    }
    public final boolean hasOffsetCloseRelative(){
        return (null != this.offsetCloseRelative);
    }
    public final boolean hasNotOffsetCloseRelative(){
        return (null == this.offsetCloseRelative);
    }
    public final Integer getOffsetCloseRelative(){
        return this.offsetCloseRelative;
    }
    public final void setOffsetCloseRelative(Integer offsetCloseRelative){
        this.offsetCloseRelative = offsetCloseRelative;
    }
}
