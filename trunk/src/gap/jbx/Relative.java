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
package gap.jbx;

/**
 * @author jdp
 */
public class Relative
    extends Number
    implements Value
{
    public enum By {
        pc,
        pt,
        px,
        em;

        public final static By decode(String suffix){
            if (null != suffix && 0 < suffix.length()){
                switch (suffix.charAt(0)){
                case '%':
                    return pc;
                case 'e':
                    switch (suffix.charAt(1)){
                    case 'm':
                        return em;
                    default:
                        throw new IllegalArgumentException(suffix);
                    }
                case 'p':
                    switch (suffix.charAt(1)){
                    case 't':
                        return pt;
                    case 'x':
                        return px;
                    default:
                        throw new IllegalArgumentException(suffix);
                    }
                default:
                    throw new IllegalArgumentException(suffix);
                }
            }
            else
                throw new IllegalArgumentException(suffix);
        }
        public final static String encode(By by){
            if (null == by)
                return null;
            else {
                switch (by){
                case pc:
                    return "%";
                case pt:
                    return "pt";
                case px:
                    return "px";
                case em:
                    return "em";
                default:
                    throw new IllegalStateException(by.toString());
                }
            }
        }
    }


    protected final Component owner;

    protected final String property;

    protected float base;

    protected By by;


    public Relative(Component owner, String property){
        super();
        if (null != owner && null != property){
            this.owner = owner;
            this.property = property;
        }
        else
            throw new IllegalArgumentException();
    }
    public Relative(Component owner, String property, String s){
        this(owner,property);
        if (null != s)
            this.fromString(s);
    }


    public Component getBean(){
        return this.owner;
    }
    public Component getParent(){
        return this.owner.getParent();
    }
    public String getName(){
        return this.property;
    }
    public Object parentValue(){
        Component parent = owner.getParent();
        if (null != parent){
            Reflector parentReflect = parent.getReflect();
            return parentReflect.get(this.property);
        }
        else
            throw new UnsupportedOperationException();
    }
    public float floatValue(){
        if (By.px == this.by)
            return this.base;
        else if (By.pc == this.by){
            Object parentValue = this.parentValue();
            if (parentValue instanceof Number)

                return this.floatValue( (Number)parentValue);

            else if (null != parentValue)
                throw new IllegalArgumentException("Parent value of '"+this.property+"' is not a number ("+parentValue.getClass()+").");
            else
                throw new IllegalArgumentException("Parent value of '"+this.property+"' is null.");
        }
        else
            throw new UnsupportedOperationException();
    }
    public float floatValue(Number parentValue){
        if (By.pc == this.by){
            if (null != parentValue){

                float pvf = ((Number)parentValue).floatValue();

                return ((this.base/100f) * pvf);
            }
            else
                throw new IllegalArgumentException("Parent value of '"+this.property+"' is null.");
        }
        else if (By.px == this.by)
            return this.base;
        else
            throw new UnsupportedOperationException();
    }
    public int intValue(){
        return (int)this.floatValue();
    }
    public int intValue(Number parentValue){
        return (int)this.floatValue(parentValue);
    }
    public long longValue(){
        return this.intValue();
    }
    public long longValue(Number parentValue){
        return this.intValue(parentValue);
    }
    public double doubleValue(){
        return this.floatValue();
    }
    public double doubleValue(Number parentValue){
        return this.floatValue(parentValue);
    }
    

    public float getBase(){
        return this.base;
    }
    public void setBase(float base){
        this.base = base;
    }
    public void setBase(String base){
        if (null != base)
            this.base = Float.parseFloat(base);
        else
            throw new IllegalArgumentException();
    }
    public boolean byPercent(){
        return (By.pc == this.by);
    }
    public boolean byPoint(){
        return (By.pt == this.by);
    }
    public boolean byPixel(){
        return (By.px == this.by);
    }
    public boolean byEm(){
        return (By.em == this.by);
    }
    public boolean isByPercent(){
        return (By.pc == this.by);
    }
    public boolean isByPoint(){
        return (By.pt == this.by);
    }
    public boolean isByPixel(){
        return (By.px == this.by);
    }
    public boolean isByEm(){
        return (By.em == this.by);
    }
    public By getBy(){
        return this.by;
    }
    public void setBy(By by){
        if (null != by)
            this.by = by;
        else
            throw new IllegalArgumentException();
    }
    public void setBy(String by){
        if (null != by)
            this.by = By.decode(by);
        else
            throw new IllegalArgumentException();
    }
    public void fromString(String value){
        if (null != value){
            for (int cc = (value.length()-1); cc > -1; cc--){
                switch (value.charAt(cc)){
                case '%':
                case 'e':
                case 'm':
                case 'p':
                case 't':
                case 'x':
                    break;
                default:
                    cc += 1;
                    String base = value.substring(0,cc);
                    this.base = Float.parseFloat(base);
                    String suffix = value.substring(cc);
                    this.by = By.decode(suffix);
                    return;
                }
            }
        }
        throw new IllegalArgumentException(value);
    }
    public String toString(){
        String base = String.valueOf(this.base);
        String suff = By.encode(this.by);
        if (null != suff)
            return (base+suff);
        else
            return base;
    }
}
