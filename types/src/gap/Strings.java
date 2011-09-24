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

import static gap.Primitive.* ;

import jauk.Re;

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
        if (null != string)
            return com.google.appengine.api.datastore.KeyFactory.stringToKey(string);
        else
            return null;
    }
    public final static java.lang.String KeyToString(gap.data.TableClass table){
	if (null != table)
	    return KeyToString(table.getKey());
	else
	    return null;
    }
    public final static java.lang.String KeyToString(com.google.appengine.api.datastore.Key instance){
        if (null != instance)
            return com.google.appengine.api.datastore.KeyFactory.keyToString(instance);
        else
            return null;
    }

    public final static Re LinkRE = new Re("<LinkUrl>");

    public final static com.google.appengine.api.datastore.Link LinkFromString(java.lang.String string){
        if (null != string){
            if (LinkRE.matches(string))
                return new com.google.appengine.api.datastore.Link(string);
            else
                throw new IllegalArgumentException(string);
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

}
