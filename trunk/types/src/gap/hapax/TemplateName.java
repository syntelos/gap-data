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

import gap.data.HasName;
import gap.data.List;

import java.util.StringTokenizer;

/**
 * <h3>Syntax</h3>
 * 
 * A name is a string that identifies a section or variable in a
 * template data dictionary.  A name is employed in templates within
 * the '{{' and '}}' brackets.  It is parsed by instances of this
 * class for special data dictionary referencing and dereferencing
 * features.
 * 
 * <h4>Path</h4>
 * 
 * A section or variable name parsed into a path - list of components
 * delimited by solidus '/' (slash).
 * 
 * <h4>Index</h4>
 * 
 * A path component accepts an optional index suffix enclosed in
 * square brackets for names identifying lists -- for index from head
 * or tail.  A negative list index is added to list size for index
 * from tail.  For these indeces negative one refers to the last
 * element of the list.
 * 
 * <h4>Complement</h4>
 * 
 * A path is boolean - complemented with a '~' (tilde) prefix to any
 * of its components.  The complement operator is intended for the
 * first component, and carries through path interpretation.
 * 
 * Therefore the first component complemented is necessarily
 * equivalent to all components (or any component) complemented.
 * 
 * Complement is employed exclusively for template sections, for
 * "exists" and "not exists" sections.  The "variable", "add", and
 * "show" methods ignore the complementarity (polarity) of the name.
 * 
 * <h3>Usage</h3>
 * 
 * The user implements path interpretation (demonstrated in {@link
 * gap.hapax.TemplateDataDictionary$Abstract} and {@link
 * gap.util.AbstractData}) using the dereference and reference
 * methods.
 * 
 * 
 * @see TemplateDataDictionary$Abstract
 * @see AbstractData
 * @author jdp
 */
public final class TemplateName 
    extends Object
    implements HasName, Iterable<TemplateName.Component>
{

    /**
     * A name component has name, and an optional index
     * 
     * <pre><i>name</i> '['</code> <i>index</i> <code>']'</pre>.
     * 
     * A negative list index is added to list size.
     * 
     * @author jdp
     */
    public final static class Component 
        extends Object
        implements Comparable<Component>
    {

        public final String source, term;

        public final int index;

        public final boolean complement;

        /**
         * Copies syntactically cleaned information to "this.source"
         * except any complement operator.  See also TemplateName
         * constructors and Cat function.
         */
        public Component(boolean complement, String source){
            super();
            StringTokenizer strtok = new StringTokenizer(source,"][");
            switch (strtok.countTokens()){
            case 1:{
                String term = strtok.nextToken().trim();
                boolean thisComplement = ('~' == term.charAt(0));
                if (thisComplement)
                    this.term = term.substring(1);
                else
                    this.term = term;

                this.complement = (complement || thisComplement);
                this.index = 0;
                this.source = this.term;
                break;
            }
            case 2:{
                String term = strtok.nextToken().trim();
                boolean thisComplement = ('~' == term.charAt(0));
                if (thisComplement)
                    this.term = term.substring(1);
                else
                    this.term = term;

                String arg = strtok.nextToken().trim();
                int index;
                try {
                    index = Integer.decode(arg);

                    source = (this.term+'['+arg+']');
                }
                catch (NumberFormatException exc){
                    throw new IllegalArgumentException(source,exc);
                }
                this.complement = (complement || thisComplement);
                this.index = index;
                this.source = source;
                break;
            }
            default:
                throw new IllegalArgumentException(source);
            }
        }


        public List.Short<TemplateDataDictionary> dereferenceC(lxl.Map<String,List.Short<TemplateDataDictionary>> dict)
        {
            List.Short<TemplateDataDictionary> section = dict.get(this.term);
            if (this.complement){

                if (null == section)
                    return TemplateDataDictionary.Abstract.EmptySection.clone();
                else
                    return null;
            }
            else
                return section;
        }
        public TemplateDataDictionary dereferenceC(TemplateDataDictionary p, 
                                                  List<TemplateDataDictionary> list)
        {
            int idx = this.index;
            if (-1 < idx){
                /*
                 * Zero-Positive values index from the start of the list
                 */
                TemplateDataDictionary dict = list.get(idx);
                if (this.complement){

                    if (null == dict)
                        return TemplateDataDictionary.Abstract.EmptyDictionary.clone(p);
                    else
                        return null;
                }
                else
                    return dict;
            }
            else {
                /*
                 * Negative values index from the end of the list
                 */
                idx += list.size();
                if (-1 < idx){
                    TemplateDataDictionary dict = list.get(idx);

                    if (this.complement){
                        if (null == dict)
                            return TemplateDataDictionary.Abstract.EmptyDictionary.clone(p);
                        else
                            return null;
                    }
                    else
                        return dict;
                }
                else
                    throw new ArrayIndexOutOfBoundsException("In '"+this.source+"' at list size '"+list.size()+"'.");
            }
        }

        public List.Short<TemplateDataDictionary> dereferenceT(lxl.Map<String,List.Short<TemplateDataDictionary>> dict)
        {
            return dict.get(this.term);
        }
        public TemplateDataDictionary dereferenceT(TemplateDataDictionary p, 
                                                   List<TemplateDataDictionary> list)
        {
            int idx = this.index;
            if (-1 < idx){
                /*
                 * Zero-Positive values index from the start of the list
                 */
                return list.get(idx);
            }
            else {
                /*
                 * Negative values index from the end of the list
                 */
                idx += list.size();
                if (-1 < idx)
                    return list.get(idx);
                else
                    throw new ArrayIndexOutOfBoundsException("In '"+this.source+"' at list size '"+list.size()+"'.");
            }
        }
        public List.Short<TemplateDataDictionary> reference(lxl.Map<String,List.Short<TemplateDataDictionary>> dict, 
                                                            TemplateDataDictionary data)
        {
            List.Short<TemplateDataDictionary> section = this.dereferenceT(dict);
            if (null == section){
                section = TemplateDataDictionary.Abstract.Add(section,data);
                dict.put(this.term,section);
            }
            else
                section = TemplateDataDictionary.Abstract.Add(section,data);

            return section;
        }
        public List.Short<TemplateDataDictionary> reference(lxl.Map<String,List.Short<TemplateDataDictionary>> dict, 
                                                            List.Short<TemplateDataDictionary> list)
        {
            dict.put(this.term,list);

            return list;
        }
        public int hashCode(){
            return this.source.hashCode();
        }
        public String toString(){
            return this.source;
        }
        public boolean equals(Object that){
            if (this == that)
                return true;
            else if (null == that)
                return false;
            else
                return this.source.equals(that.toString());
        }
        public int compareTo(Component that){
            if (this == that)
                return 0;
            else if (null == that)
                return 1;
            else if (0 != this.index || 0 != that.index){
                int comp = this.term.compareTo(that.term);
                if (0 != comp)
                    return comp;
                else if (this.index != that.index){
                    if (this.index < that.index)
                        return -1;
                    else
                        return 1;
                }
                else
                    return 0;
            }
            else
                return this.term.compareTo(that.term);
        }
    }
    /**
     * TemplateName path iterator.
     * 
     * @author jdp
     */
    public final class Iterator
        extends Object
        implements java.util.Iterator<Component>
    {
        private final Component[] list;
        private final int count;
        private int index;


        public Iterator(Component[] list){
            super();
            this.list = list;
            this.count = list.length;
        }

        public boolean hasNext(){
            return (this.index < this.count);
        }
        public Component next(){
            int index = this.index;
            if (index < this.count){
                Component next = this.list[index];
                this.index++;
                return next;
            }
            else
                throw new java.util.NoSuchElementException(String.valueOf(index));
        }
        public void remove(){
            throw new java.lang.UnsupportedOperationException();
        }
    }


    public final TemplateName from;

    public final String source;

    public final Component path[];

    public final int count;

    public final boolean complement;


    public TemplateName(String base, String name){
        this(Cat(base,name));
    }
    public TemplateName(String source){
        super();
        this.from = null;
        if (null != source){
            boolean complement = false;

            StringBuilder strbuf = new StringBuilder();
            StringTokenizer strtok = new StringTokenizer(source,"/");
            int count = strtok.countTokens();
            Component[] path = new Component[count];
            for (int cc = 0; cc < count; cc++){
                Component el = new Component(complement,strtok.nextToken());
                complement = (complement || el.complement);
                path[cc] = el;
                if (0 != cc)
                    strbuf.append('/');
                strbuf.append(el.source);
            }
            /*
             */
            if (complement)
                strbuf.insert(0,'~');

            this.source = strbuf.toString();
            this.path = path;
            this.count = count;
            this.complement = complement;
        }
        else
            throw new IllegalArgumentException();
    }
    public TemplateName(TemplateName prefix, String suffix){
        this(Cat(prefix,suffix));
    }
    public TemplateName(TemplateName shift){
        super();
        if (null != shift){
            this.from = shift;
            this.source = shift.source;
            int count = (shift.count-1);
            if (0 < count){
                this.count = count;
                Component[] path = new Component[count];
                System.arraycopy(shift.path,1,path,0,count);
                this.path = path;
            }
            else {
                this.count = 0;
                this.path = new Component[0];
            }
            this.complement = shift.complement;
        }
        else
            throw new IllegalArgumentException();
    }


    public String getSource(){
        return this.source;
    }
    public boolean isComplement(){
        return this.complement;
    }
    public boolean isIdentity(){
        return (1 == this.count);
    }
    public int size(){
        return this.count;
    }
    public boolean has(int idx){
        return (-1 < idx && idx < this.count);
    }
    public boolean hasNot(int idx){
        return (idx >= this.count);
    }
    public boolean is(int idx){
        return (idx == (this.count-1));
    }
    public Component get(int idx){
        if (-1 < idx && idx < this.count)
            return this.path[idx];
        else
            return null;
    }
    /**
     * @return Head
     */
    public Component first(){
        return this.get(0);
    }
    /**
     * @return Not first
     */
    public Component last(){
        int idx = (this.count-1);
        if (0 != idx)
            return this.get(idx);
        else
            return null;
    }
    /**
     * @return Last or first
     */
    public Component tail(){
        return this.get(this.count-1);
    }
    /**
     * @return A negative index is added to list size.
     */
    public int getIndex(int idx){
        if (-1 < idx && idx < this.count)
            return this.path[idx].index;
        else
            throw new IllegalArgumentException(String.valueOf(idx));
    }
    public boolean hasNotBase(){
        return (2 > this.count);
    }
    public boolean hasBase(){
        return (1 < this.count);
    }
    /**
     * @return Base prefix, related to name
     * @see #getName()
     */
    public String getBase(){

        return this.getBaseBuffer().toString();
    }
    public StringBuilder getBaseBuffer(){
        StringBuilder strbuf = new StringBuilder();

        final Component path[] = this.path;
        final int count = this.count, term = (count-1);

        for (int cc = 0; cc < term; cc++){
            Component el = path[cc];
            if (0 != strbuf.length())
                strbuf.append('/');
            strbuf.append(el.source);
        }
        /*
         */
        if (this.complement){
            if (0 < term)
                strbuf.insert(0,'~');
        }
        return strbuf;
    }
    public boolean hasNotName(){
        return (1 > this.count);
    }
    public boolean hasName(){
        return (0 < this.count);
    }
    /**
     * @return Tail term (component name), related to base
     * @see #getBase()
     */
    public String getName(){
        if (0 < this.count){
            Component[] components = this.path;
            return components[components.length-1].term;
        }
        else
            return "";
    }
    /**
     * @return First term (component name) at the current path depth
     */
    public String getTerm(){
        if (0 < this.count){
            Component[] components = this.path;
            return components[0].term;
        }
        else
            return "";
    }
    /**
     * Use polarity
     */
    public TemplateDataDictionary dereferenceC(TemplateDataDictionary p, List<TemplateDataDictionary> list){

        return this.first().dereferenceC(p,list);
    }
    public List.Short<TemplateDataDictionary> dereferenceC(lxl.Map<String,List.Short<TemplateDataDictionary>> map){

        return this.first().dereferenceC(map);
    }
    /**
     * Ignore polarity
     */
    public TemplateDataDictionary dereferenceT(TemplateDataDictionary p, List<TemplateDataDictionary> list){

        return this.first().dereferenceT(p,list);
    }
    public List.Short<TemplateDataDictionary> dereferenceT(lxl.Map<String,List.Short<TemplateDataDictionary>> map){

        return this.first().dereferenceT(map);
    }
    /**
     * Ignore polarity
     */
    public List.Short<TemplateDataDictionary> reference(lxl.Map<String,List.Short<TemplateDataDictionary>> map, 
                                                        TemplateDataDictionary data)
    {
        return this.first().reference(map, data);
    }
    public List.Short<TemplateDataDictionary> reference(lxl.Map<String,List.Short<TemplateDataDictionary>> map,
                                                        List.Short<TemplateDataDictionary> list)
    {
        return this.first().reference(map, list);
    }
    public int hashCode(){
        return this.source.hashCode();
    }
    public String toString(){
        return this.source;
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (null == that)
            return false;
        else
            return this.source.equals(that.toString());
    }
    public java.util.Iterator<Component> iterator(){
        return new Iterator(this.path);
    }

    /**
     * Path cat: "a+'/'+b"
     */
    public final static String Cat(String base, String name){
        if (null == base || 0 == base.length())
            return name;
        else if (null == name || 0 == name.length())
            return base;
        else if ('/' == base.charAt(base.length()-1) || '/' == name.charAt(0))
            return base+name;
        else
            return base+'/'+name;
    }
    /**
     * Name cat: "base(a)+'/'+(tail(a)+b)+index(a)"
     */
    public final static String Cat(TemplateName prefix, String suffix){
        if (null == prefix)
            return suffix;
        else {
            Component tail = prefix.tail();
            if (0 != tail.index){
                StringBuilder strbuf;
                if (prefix.hasBase()){
                    strbuf = prefix.getBaseBuffer();
                    strbuf.append('/');
                }
                else
                    strbuf = new StringBuilder();

                strbuf.append(tail.term);
                strbuf.append(suffix);
                strbuf.append('[');
                strbuf.append(tail.index);
                strbuf.append(']');
                return strbuf.toString();
            }
            else
                return (prefix+suffix);
        }
    }
}
