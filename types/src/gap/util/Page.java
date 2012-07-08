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
package gap.util;

import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;

import com.google.appengine.api.datastore.FetchOptions;

/**
 * 
 */
public class Page 
    extends Object
{
    public final static Page Default = new Page();
    public final static int DefaultCount = 20;

    public final static String Count = "count";
    public final static String StartIndex = "startIndex";
    public final static String StartIndexPrev = "startIndexPrev";
    public final static String StartIndexNext = "startIndexNext";

    public final static class TemplateNames {
        public final static TemplateName Count = new TemplateName(Page.Count);
        public final static TemplateName StartIndex = new TemplateName(Page.StartIndex);
        public final static TemplateName StartIndexPrev = new TemplateName(Page.StartIndexPrev);
        public final static TemplateName StartIndexNext = new TemplateName(Page.StartIndexNext);
    }


    public final int count, startIndex, nextIndex, prevIndex;


    public Page(int startIndex, int count){
        super();
        if (-1 < startIndex && 0 < count){
            int nextIndex = (startIndex+count), prevIndex = (startIndex-count);
            this.count = count; 
            this.startIndex = startIndex;
            this.nextIndex = nextIndex;
            if (-1 < prevIndex)
                this.prevIndex = prevIndex;
            else
                this.prevIndex = 0;
        }
        else
            throw new IllegalArgumentException();
    }
    public Page(){
        this(0,DefaultCount);
    }
    public Page(Page p, int count){
        this(p.startIndex,count);
    }


    public final void dictionaryInto(TemplateDataDictionary dict){

        dict.setVariable(TemplateNames.StartIndex,String.valueOf(this.startIndex));
        dict.setVariable(TemplateNames.StartIndexPrev,String.valueOf(this.prevIndex));
        dict.setVariable(TemplateNames.StartIndexNext,String.valueOf(this.nextIndex));
        dict.setVariable(TemplateNames.Count,String.valueOf(this.count));
    }
    public final FetchOptions createFetchOptions(){
        return FetchOptions.Builder.withLimit(this.count).offset(this.startIndex);
    }
}
