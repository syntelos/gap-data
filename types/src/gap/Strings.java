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
package gap;

import alto.io.u.B64;

import static gap.Primitive.* ;

import jauk.Re;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.KeyFactory;

import java.lang.reflect.Method;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Random;
import java.util.StringTokenizer;


/**
 * String conversions for {@link Primitive} types.
 * 
 * @author jdp
 */
public abstract class Strings {

    public final static java.lang.Object FromString(Primitive type, java.lang.String string){
        if (null == string)
            return null;
        else {
            switch(type){
            case String:
                return string;
            case Boolean:
                return BooleanFromString(string);
            case Byte:
                return ByteFromString(string);
            case Character:
                return CharacterFromString(string);
            case Short:
                return ShortFromString(string);
            case Integer:
                return IntegerFromString(string);
            case Long:
                return LongFromString(string);
            case Float:
                return FloatFromString(string);
            case Double:
                return DoubleFromString(string);
            case Date:
                return DateFromString(string);
            case BigInteger:
                return BigIntegerFromString(string);
            case BigDecimal:
                return BigDecimalFromString(string);
            case Category:
                return CategoryFromString(string);
            case Email:
                return EmailFromString(string);
            case GeoPt:
                return GeoPtFromString(string);
            case Key:
                return KeyFromString(string);
            case Link:
                return LinkFromString(string);
            case PhoneNumber:
                return PhoneNumberFromString(string);
            case PostalAddress:
                return PostalAddressFromString(string);
            case Rating:
                return RatingFromString(string);
            case Text:
                return TextFromString(string);
            case Serializable:
                return SerializableFromString(string);
            default:
                throw new java.lang.IllegalStateException("Unrecognized type "+type.name());
            }
        }
    }
    public final static java.lang.String ToString(Primitive type, java.lang.Object instance){
        if (null == instance)
            return null;
        else {
            switch(type){
            case String:
                return (java.lang.String)instance;
            case Boolean:
                return BooleanToString((java.lang.Boolean)instance);
            case Byte:
                return ByteToString((java.lang.Byte)instance);
            case Character:
                return CharacterToString((java.lang.Character)instance);
            case Short:
                return ShortToString((java.lang.Short)instance);
            case Integer:
                return IntegerToString((java.lang.Integer)instance);
            case Long:
                return LongToString((java.lang.Long)instance);
            case Float:
                return FloatToString((java.lang.Float)instance);
            case Double:
                return DoubleToString((java.lang.Double)instance);
            case Date:
                return DateToString((java.util.Date)instance);
            case Enum:
                return EnumToString((java.lang.Enum)instance);
            case BigInteger:
                return BigIntegerToString((java.math.BigInteger)instance);
            case BigDecimal:
                return BigDecimalToString((java.math.BigDecimal)instance);
            case Category:
                return CategoryToString((com.google.appengine.api.datastore.Category)instance);
            case Email:
                return EmailToString((com.google.appengine.api.datastore.Email)instance);
            case GeoPt:
                return GeoPtToString((com.google.appengine.api.datastore.GeoPt)instance);
            case Key:
		if (instance instanceof com.google.appengine.api.datastore.Key)
		    return KeyToString((com.google.appengine.api.datastore.Key)instance);
		else if (instance instanceof gap.data.TableClass)
		    return KeyToString((gap.data.TableClass)instance);
		else
		    throw new java.lang.IllegalStateException("Unrecognized type for key "+instance.getClass().getName());
            case Link:
                return LinkToString((com.google.appengine.api.datastore.Link)instance);
            case PhoneNumber:
                return PhoneNumberToString((com.google.appengine.api.datastore.PhoneNumber)instance);
            case PostalAddress:
                return PostalAddressToString((com.google.appengine.api.datastore.PostalAddress)instance);
            case Rating:
                return RatingToString((com.google.appengine.api.datastore.Rating)instance);
            case Text:
                return TextToString((com.google.appengine.api.datastore.Text)instance);
            case Serializable:
                return SerializableToString((java.io.Serializable)instance);
            default:
                throw new java.lang.IllegalStateException("Unrecognized type "+type.name());
            }
        }
    }

    public final static java.lang.String StringFromString(java.lang.String string){
        if (null != string)
            return string;
        else
            return null;
    }
    public final static java.lang.String StringToString(java.lang.String instance){
        if (null != instance)
            return (java.lang.String)instance;
        else
            return null;
    }
    /*
     * Java native values
     */
    public final static Re BooleanRE = new Re("(\"true\"|\"false\")");
    /*
     * HTML checkbox values
     */
    public final static Re BooleanRE2 = new Re("(\"on\"|\"off\")");

    public final static java.lang.Boolean BooleanFromString(java.lang.String string){
        if (null != string){
            if (BooleanRE.matches(string))
                return new Boolean(string);
            else if (BooleanRE2.matches(string)){
                if ("on".equals(string))
                    return java.lang.Boolean.TRUE;
                else
                    return java.lang.Boolean.FALSE;
            }
            else
                throw new IllegalArgumentException(string);
        }
        else
            return null;
    }
    public final static java.lang.String BooleanToString(java.lang.Boolean instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }

    public final static java.lang.Byte ByteFromString(java.lang.String string){
        if (null != string)
            return java.lang.Byte.decode(string);
        else
            return null;
    }
    public final static java.lang.String ByteToString(java.lang.Byte instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Character CharacterFromString(java.lang.String string){
        if (null != string && 0 < string.length())
            return new java.lang.Character(string.charAt(0));
        else
            return null;
    }
    public final static java.lang.String CharacterToString(java.lang.Character instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Short ShortFromString(java.lang.String string){
        if (null != string)
            return java.lang.Short.decode(string);
        else
            return null;
    }
    public final static java.lang.String ShortToString(java.lang.Short instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Integer IntegerFromString(java.lang.String string){
        if (null != string)
            return java.lang.Integer.decode(string);
        else
            return null;
    }
    public final static java.lang.String IntegerToString(java.lang.Integer instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Long LongFromString(java.lang.String string){
        if (null != string)
            return java.lang.Long.decode(string);
        else
            return null;
    }
    public final static java.lang.String LongToString(java.lang.Long instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Float FloatFromString(java.lang.String string){
        if (null != string)
            return new java.lang.Float(string);
        else
            return null;
    }
    public final static java.lang.String FloatToString(java.lang.Float instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.lang.Double DoubleFromString(java.lang.String string){
        if (null != string)
            return new java.lang.Double(string);
        else
            return null;
    }
    public final static java.lang.String DoubleToString(java.lang.Double instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static java.util.Date DateFromString(java.lang.String string){
        if (null != string)
            return gap.Date.ParseRFC1123(string);
        else
            return null;
    }
    public final static java.lang.String DateToString(java.util.Date instance){
        if (null != instance)
            return gap.Date.FormatRFC1123(instance);
        else
            return null;
    }
    public final static java.lang.Enum EnumFromString(java.lang.String string){
        if (null != string){
            int parse = string.indexOf('#');
            if (-1 < parse){
                try {
                    Class clas = Class.forName(string.substring(0,parse));
                    Method valueOf = clas.getMethod("valueOf",String.class);
                    return (java.lang.Enum)valueOf.invoke(null,string.substring(parse+1));
                }
                catch (Exception any){
                    throw new IllegalArgumentException(string,any);
                }
            }
            else
                throw new IllegalArgumentException(string);
        }
        else
            return null;
    }
    public final static java.lang.String EnumToString(java.lang.Enum instance){
        if (null != instance)
            return instance.getClass().getName()+'#'+instance.name();
        else
            return null;
    }
    public final static java.math.BigInteger BigIntegerFromString(java.lang.String string){
        if (null != string)
            return new java.math.BigInteger(alto.io.u.Hex.decode(string));
        else
            return null;
    }
    public final static java.lang.String BigIntegerToString(java.math.BigInteger instance){
        if (null != instance)
            return alto.io.u.Hex.encode(instance.toByteArray());
        else
            return null;
    }
    public final static java.math.BigDecimal BigDecimalFromString(java.lang.String string){
        if (null != string)
            return new java.math.BigDecimal(string);
        else
            return null;
    }
    public final static java.lang.String BigDecimalToString(java.math.BigDecimal instance){
        if (null != instance)
            return instance.toString();
        else
            return null;
    }
    public final static com.google.appengine.api.datastore.Blob BlobFromString(java.lang.String string){
        if (null != string){
            byte[] bytes = alto.io.u.B64.decode(string);
            return new com.google.appengine.api.datastore.Blob(bytes);
        }
        else
            return null;
    }
    public final static java.lang.String BlobToString(com.google.appengine.api.datastore.Blob instance){
        if (null != instance)
            return alto.io.u.B64.encodeBytes(instance.getBytes());
        else
            return null;
    }
    public final static com.google.appengine.api.datastore.Category CategoryFromString(java.lang.String string){
        if (null != string)
            return new com.google.appengine.api.datastore.Category(string);
        else
            return null;
    }
    public final static java.lang.String CategoryToString(com.google.appengine.api.datastore.Category instance){
        if (null != instance)
            return instance.getCategory();
        else
            return null;
    }

    public final static Re EmailRE = new Re("<EmailAddress>");

    public final static com.google.appengine.api.datastore.Email EmailFromString(java.lang.String string){
        if (null != string){
            if (EmailRE.matches(string))
                return new com.google.appengine.api.datastore.Email(string);
            else
                throw new IllegalArgumentException(string);
        }
        else
            return null;
    }
    public final static java.lang.String EmailToString(com.google.appengine.api.datastore.Email instance){
        if (null != instance)
            return instance.getEmail();
        else
            return null;
    }
    public final static com.google.appengine.api.datastore.GeoPt GeoPtFromString(java.lang.String string){
        if (null != string){
            java.util.StringTokenizer strtok = new java.util.StringTokenizer(string,",: ");
            if (2 == strtok.countTokens()){
                float lat = java.lang.Float.parseFloat(strtok.nextToken());
                float lon = java.lang.Float.parseFloat(strtok.nextToken());
                return new com.google.appengine.api.datastore.GeoPt(lat,lon);
            }
        }
        return null;
    }
    public final static java.lang.String GeoPtToString(com.google.appengine.api.datastore.GeoPt instance){
        if (null != instance){
            java.lang.StringBuilder strbuf = new java.lang.StringBuilder();
            strbuf.append(instance.getLatitude());
            strbuf.append(',');
            strbuf.append(instance.getLongitude());
            return strbuf.toString();
        }
        else
            return null;
    }
    public final static com.google.appengine.api.datastore.Key KeyFromString(java.lang.String string){
        if (null != string){

            Strings.KeyParser parser = new Strings.KeyParser(string);

            return parser.key;
        }
        else
            return null;
    }
    public final static java.lang.String KeyToString(gap.data.TableClass table){
	if (null != table)
	    return KeyToString(table.getKey());
	else
	    return null;
    }
    public final static java.lang.String KeyToString(com.google.appengine.api.datastore.Key key){
        if (null != key){
            if (key.isComplete()){
                java.lang.String s = KeyToStringBuilder(key).toString();
                if (0 < s.length())
                    return s;
            }
            throw new IllegalArgumentException("Incomplete key ("+key+")");
        }
        else
            return null;
    }

    public final static Re LinkRE = new Re("<LinkUrl>");

    public final static com.google.appengine.api.datastore.Link LinkFromString(java.lang.String string){
        if (null != string){
            if (LinkRE.matches(string))
                return new com.google.appengine.api.datastore.Link(string);
            else {
                String check = "http://"+string;
                if (LinkRE.matches(check))
                    return new com.google.appengine.api.datastore.Link(check);
                else
                    throw new IllegalArgumentException(string);
            }
        }
        else
            return null;
    }
    public final static java.lang.String LinkToString(com.google.appengine.api.datastore.Link instance){
        if (null != instance)
            return instance.getValue();
        else
            return null;
    }

    public final static Re PhoneNumberRE = new Re("<TelephoneNumber>");

    public final static com.google.appengine.api.datastore.PhoneNumber PhoneNumberFromString(java.lang.String string){
        if (null != string){
            if (PhoneNumberRE.matches(string))
                return new com.google.appengine.api.datastore.PhoneNumber(string);
            else
                throw new IllegalArgumentException(string);
        }
        else
            return null;
    }
    public final static java.lang.String PhoneNumberToString(com.google.appengine.api.datastore.PhoneNumber instance){
        if (null != instance)
            return instance.getNumber();
        else
            return null;
    }
    public final static com.google.appengine.api.datastore.PostalAddress PostalAddressFromString(java.lang.String string){
        if (null != string)
            return new com.google.appengine.api.datastore.PostalAddress(string);
        else
            return null;
    }
    public final static java.lang.String PostalAddressToString(com.google.appengine.api.datastore.PostalAddress instance){
        if (null != instance)
            return instance.getAddress();
        else
            return null;
    }
    public final static com.google.appengine.api.datastore.Rating RatingFromString(java.lang.String string){
        if (null != string)
            return new com.google.appengine.api.datastore.Rating(java.lang.Integer.parseInt(string));
        else
            return null;
    }
    public final static java.lang.String RatingToString(com.google.appengine.api.datastore.Rating instance){
        if (null != instance)
            return java.lang.String.valueOf(instance.getRating());
        else
            return null;
    }
    public final static com.google.appengine.api.datastore.Text TextFromString(java.lang.String string){
        if (null != string)
            return new com.google.appengine.api.datastore.Text(string);
        else
            return null;
    }
    public final static java.lang.String TextToString(com.google.appengine.api.datastore.Text instance){
        if (null != instance)
            return instance.getValue();
        else
            return null;
    }
    public final static java.io.Serializable SerializableFromString(java.lang.String string){
        if (null != string){
            byte[] blob = B64.decode(string);
            if (null != blob){
                try {
                    ByteArrayInputStream buffer = new ByteArrayInputStream(blob);
                    ObjectInputStream in = new ObjectInputStream(buffer);
                    return (java.io.Serializable)in.readObject();
                }
                catch (java.lang.ClassNotFoundException exc){
                    throw new java.lang.RuntimeException(exc);
                }
                catch (java.io.IOException exc){
                    throw new java.lang.RuntimeException(exc);
                }
            }
        }
        return null;
    }
    public final static java.lang.String SerializableToString(java.io.Serializable instance){
        if (null != instance){
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(buffer);
                out.writeObject(instance);
                return B64.encodeBytes(buffer.toByteArray());
            }
            catch (java.io.IOException exc){
                throw new java.lang.RuntimeException(exc);
            }
        }
        else
            return null;
    }
    public final static java.lang.String[] Add(java.lang.String[] list, Primitive type, java.lang.Object instance){
	return Add(list,ToString(type,instance));
    }
    public final static java.lang.String[] Add(java.lang.String[] list, java.lang.String item){
	if (null == item)
	    return list;
	else if (null == list)
	    return new java.lang.String[]{item};
	else {
	    int len = list.length;
	    java.lang.String[] copier = new java.lang.String[len+1];
	    java.lang.System.arraycopy(list,0,copier,0,len);
	    copier[len] = item;
	    return copier;
	}
    }
    public final static java.lang.String[] Cat(java.lang.String[] a, java.lang.String[] b){
	if (null == a)
	    return b;
	else if (null == b)
	    return a;
	else {
	    final int alen = a.length;
	    final int blen = b.length;
	    java.lang.String[] copier = new java.lang.String[alen+blen];
	    java.lang.System.arraycopy(a,0,copier,0,alen);
	    java.lang.System.arraycopy(b,0,copier,alen,blen);
	    return copier;
	}
    }
    private final static int RandomIdentifierOctets = 12;

    public final static java.lang.String RandomIdentifier(){
        byte[] bits = new byte[RandomIdentifierOctets];
        {
            new Random().nextBytes(bits);
        }
        return RandomIdentifierPathclean(B64.encodeBytes(bits));
    }
    private final static java.lang.String RandomIdentifierPathclean(java.lang.String r){
        char[] cary = r.toCharArray();
        final int count = cary.length;
        boolean change = false;
        for (int cc = 0; cc < count; cc++){
            switch (cary[cc]){
            case '/':
                change = true;
                cary[cc] = 'A';
                break;
            case '+':
                change = true;
                cary[cc] = 'B';
                break;
            case '\r':
                change = true;
                cary[cc] = 'C';
                break;
            case '\n':
                change = true;
                cary[cc] = 'D';
                break;
            case '=':
                change = true;
                cary[cc] = 'E';
                break;
            default:
                break;
            }
        }
        if (change)
            return new java.lang.String(cary,0,count);
        else
            return r;
    }
    public final static java.lang.StringBuilder KeyToStringBuilder(com.google.appengine.api.datastore.Key key){
        if (null != key)

            return KeyToStringBuilder(new java.lang.StringBuilder(),key);
        else
            throw new IllegalArgumentException();
    }
    private final static java.lang.StringBuilder KeyToStringBuilder(java.lang.StringBuilder strbuf, com.google.appengine.api.datastore.Key k){

        final com.google.appengine.api.datastore.Key p = k.getParent();
        if (null != p && (!KEQ(p,k)))
            KeyToStringBuilder(strbuf,p);

        final java.lang.String n = k.getName();

        if (null != n && 0 != n.length()){
            strbuf.append('/');
            strbuf.append(k.getKind());
            strbuf.append(':');
            strbuf.append(n);
        }
        return strbuf;
    }
    /**
     * @return Key node equivalence
     */
    private final static boolean KEQ(com.google.appengine.api.datastore.Key a, com.google.appengine.api.datastore.Key b){
        if (a == b)
            return true;
        else if (a.getKind().equals(b.getKind())){
            long ai = a.getId();
            long bi = b.getId();
            if (ai == bi){
                java.lang.String an = a.getName();
                java.lang.String bn = b.getName();
                if (null == an)
                    return (null == bn);
                else if (null == bn)
                    return false;
                else
                    return an.equals(bn);
            }
        }
        return false;
    }
    /**
     * Parse output of KeyToString into components and DataStore Key.
     */
    public final static class KeyParser {

        public final static class Component {


            public final java.lang.String component, kind, name;


            public Component(java.lang.String component){
                super();
                final int cidx = component.indexOf(':');
                if (0 < cidx){
                    this.component = component;
                    this.kind = component.substring(0,cidx);
                    this.name = component.substring(cidx+1);
                }
                else
                    throw new IllegalArgumentException(component);
            }


            public final static Component[] Add(Component[] list, Component item){
                if (null == item)
                    return list;
                else if (null == list)
                    return new Component[]{item};
                else {
                    int len = list.length;
                    Component[] copier = new Component[len+1];
                    java.lang.System.arraycopy(list,0,copier,0,len);
                    copier[len] = item;
                    return copier;
                }
            }
        }


        public final java.lang.String string;

        public final Component[] components;

        public final com.google.appengine.api.datastore.Key key;


        public KeyParser(java.lang.String string){
            super();
            if (null != string && 3 < string.length() && '/' == string.charAt(0)){
                this.string = string;
                final StringTokenizer strtok = new StringTokenizer(string,"/");
                final int count = strtok.countTokens();
                {
                    KeyParser.Component[] components = new KeyParser.Component[count];
                    com.google.appengine.api.datastore.Key key = null;
                    for (int cidx = 0; cidx < count; cidx++){

                        KeyParser.Component c = new KeyParser.Component(strtok.nextToken());
                        components[cidx] = c;
                        if (null == key)
                            key = KeyFactory.createKey(c.kind,c.name);
                        else
                            key = KeyFactory.createKey(key,c.kind,c.name);
                    }
                    this.components = components;
                    this.key = key;
                }
            }
            else
                throw new IllegalArgumentException(string);
        }
    }
}
