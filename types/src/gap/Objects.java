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

/**
 * Object conversions for field types.
 * 
 * @author jdp
 */
public abstract class Objects {

    public final static java.lang.Object ObjectFromObject(java.lang.Object object){
        if (object instanceof java.lang.String){
            String string = (String)object;
            try {
                com.google.appengine.api.datastore.Key key = Strings.KeyFromString(string);
                gap.data.Kind kind = gap.data.Kind.For(key.getKind());
                return kind.get(key);
            }
            catch (Exception any){
            }
        }
        return object;
    }
    public final static java.lang.String StringFromObject(java.lang.Object object){
        if (object instanceof java.lang.String)
            return (java.lang.String)object;
        else if (object instanceof gap.data.HasName)
            return ((gap.data.HasName)object).getName();
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Boolean BooleanFromObject(java.lang.Object object){
        if (object instanceof java.lang.Boolean)
            return (java.lang.Boolean)object;
        else if (object instanceof java.lang.String)
            return Strings.BooleanFromString( (String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Byte ByteFromObject(java.lang.Object object){
        if (object instanceof java.lang.Byte)
            return (java.lang.Byte)object;
        else if (object instanceof java.lang.String)
            return Strings.ByteFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Byte( ((java.lang.Number)object).byteValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Character CharacterFromObject(java.lang.Object object){
        if (object instanceof java.lang.Character)
            return (java.lang.Character)object;
        /*
         * Special storage type is Integer
         */
        else if (object instanceof java.lang.Number)
            return new java.lang.Character( (char)((java.lang.Number)object).intValue());
        else if (object instanceof java.lang.String)
            return Strings.CharacterFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Short ShortFromObject(java.lang.Object object){
        if (object instanceof java.lang.Short)
            return (java.lang.Short)object;
        else if (object instanceof java.lang.String)
            return Strings.ShortFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Short( ((java.lang.Number)object).shortValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Integer IntegerFromObject(java.lang.Object object){
        if (object instanceof java.lang.Integer)
            return (java.lang.Integer)object;
        else if (object instanceof java.lang.String)
            return Strings.IntegerFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Integer( ((java.lang.Number)object).intValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Long LongFromObject(java.lang.Object object){
        if (object instanceof java.lang.Long)
            return (java.lang.Long)object;
        else if (object instanceof java.lang.String)
            return Strings.LongFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Long( ((java.lang.Number)object).longValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Float FloatFromObject(java.lang.Object object){
        if (object instanceof java.lang.Float)
            return (java.lang.Float)object;
        else if (object instanceof java.lang.String)
            return Strings.FloatFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Float( ((java.lang.Number)object).floatValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Double DoubleFromObject(java.lang.Object object){
        if (object instanceof java.lang.Double)
            return (java.lang.Double)object;
        else if (object instanceof java.lang.String)
            return Strings.DoubleFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.lang.Double( ((java.lang.Number)object).doubleValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.util.Date DateFromObject(java.lang.Object object){
        if (object instanceof java.util.Date)
            return (java.util.Date)object;
        else if (object instanceof java.lang.String)
            return Strings.DateFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.util.Date( ((java.lang.Number)object).longValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.lang.Enum EnumFromObject(java.lang.Object object){
        if (object instanceof java.lang.Enum)
            return (java.lang.Enum)object;
        else if (object instanceof java.lang.String)
            return Strings.EnumFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.math.BigInteger BigIntegerFromObject(java.lang.Object object){
        if (object instanceof java.math.BigInteger)
            return (java.math.BigInteger)object;
        /*
         * Special storage type is ShortBlob
         */
        else if (object instanceof com.google.appengine.api.datastore.ShortBlob){
            com.google.appengine.api.datastore.ShortBlob blob = (com.google.appengine.api.datastore.ShortBlob)object;
            return new java.math.BigInteger(1,blob.getBytes());
        }
        else if (object instanceof java.lang.String)
            return Strings.BigIntegerFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.math.BigInteger( java.lang.Long.toString(((java.lang.Number)object).longValue(),16),16);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static java.math.BigDecimal BigDecimalFromObject(java.lang.Object object){
        if (object instanceof java.math.BigDecimal)
            return (java.math.BigDecimal)object;
        /*
         * Special storage type is String
         */
        else if (object instanceof java.lang.String)
            return Strings.BigDecimalFromString( (java.lang.String)object);
        else if (object instanceof java.lang.Number)
            return new java.math.BigDecimal( ((java.lang.Number)object).doubleValue());
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.Blob BlobFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.Blob)
            return (com.google.appengine.api.datastore.Blob)object;
        else if (object instanceof java.lang.String)
            return Strings.BlobFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.Category CategoryFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.Category)
            return (com.google.appengine.api.datastore.Category)object;
        else if (object instanceof java.lang.String)
            return Strings.CategoryFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.Email EmailFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.Email)
            return (com.google.appengine.api.datastore.Email)object;
        else if (object instanceof java.lang.String)
            return Strings.EmailFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.GeoPt GeoPtFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.GeoPt)
            return (com.google.appengine.api.datastore.GeoPt)object;
        else if (object instanceof java.lang.String)
            return Strings.GeoPtFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.Key KeyFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.Key)
            return (com.google.appengine.api.datastore.Key)object;
        else if (object instanceof java.lang.String)
            return Strings.KeyFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.Link LinkFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.Link)
            return (com.google.appengine.api.datastore.Link)object;
        else if (object instanceof java.lang.String)
            return Strings.LinkFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.PhoneNumber PhoneNumberFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.PhoneNumber)
            return (com.google.appengine.api.datastore.PhoneNumber)object;
        else if (object instanceof java.lang.String)
            return Strings.PhoneNumberFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.PostalAddress PostalAddressFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.PostalAddress)
            return (com.google.appengine.api.datastore.PostalAddress)object;
        else if (object instanceof java.lang.String)
            return Strings.PostalAddressFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.Rating RatingFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.Rating)
            return (com.google.appengine.api.datastore.Rating)object;
        else if (object instanceof java.lang.String)
            return Strings.RatingFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
    public final static com.google.appengine.api.datastore.Text TextFromObject(java.lang.Object object){
        if (object instanceof com.google.appengine.api.datastore.Text)
            return (com.google.appengine.api.datastore.Text)object;
        else if (object instanceof java.lang.String)
            return Strings.TextFromString( (java.lang.String)object);
        else if (null == object)
            return null;
        else
            throw new ClassCastException(object.getClass().getName());
    }
}
