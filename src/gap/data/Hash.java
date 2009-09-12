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
package gap.data;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Produce abstract identifiers from strings like email addresses.
 * 
 * @author jdp
 */
public final class Hash
    extends java.lang.Object
{
    public final static Charset UTF8 = Charset.forName("UTF-8");

    private final static char[] HexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * @param string Any
     * @return Hex of hash of string
     */
    public final static String For(String string){
        if (null == string || 0 == string.length())
            return "00";
        else {
            long value = Djb64(string.getBytes(UTF8));
            return Hex(Long(value));
        }
    }
    public final static String ForBoolean(List<Boolean> list){
        StringBuilder string = new StringBuilder();
        for (Boolean item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForString(List<String> list){
        StringBuilder string = new StringBuilder();
        for (String item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForByte(List<Byte> list){
        StringBuilder string = new StringBuilder();
        for (Byte item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForShort(List<Short> list){
        StringBuilder string = new StringBuilder();
        for (Short item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForInteger(List<Integer> list){
        StringBuilder string = new StringBuilder();
        for (Integer item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForLong(List<Long> list){
        StringBuilder string = new StringBuilder();
        for (Long item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForFloat(List<Float> list){
        StringBuilder string = new StringBuilder();
        for (Float item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForDouble(List<Double> list){
        StringBuilder string = new StringBuilder();
        for (Double item : list){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    public final static String ForDate(List<Date> list){
        StringBuilder string = new StringBuilder();
        for (Date item : list){
            if (0 != string.length())
                string.append('/');
            string.append(gap.Date.FormatISO8601(item));
        }
        return For(string.toString());
    }
    public final static String ForStringString(Map<String,String> map){
        StringBuilder string = new StringBuilder();
        for (String item : map.values()){
            if (0 != string.length())
                string.append('/');
            string.append(item);
        }
        return For(string.toString());
    }
    /**
     * Daniel Bernstein's later function.
     * @see http://www.cse.yorku.ca/~oz/hash.html
     */
    public final static long Djb64 ( byte[] b){
        if ( null == b)
            return 0L;
        else {
            long hash = 5381;
            int c;
            for (int cc = 0, count = b.length; cc < count; cc++){
                c = b[cc] & 0xff;
                hash = ((hash << 5) + hash) ^ c;
            }
            return hash;
        }
    }
    public final static byte[] Long( long num){
        byte[] ret = new byte[8];
        //
        ret[0] = (byte)((num>>>56)&0xff);
        ret[1] = (byte)((num>>>48)&0xff);
        ret[2] = (byte)((num>>>40)&0xff);
        ret[3] = (byte)((num>>>32)&0xff);
        ret[4] = (byte)((num>>>24)&0xff);
        ret[5] = (byte)((num>>>16)&0xff);
        ret[6] = (byte)((num>>> 8)&0xff);
        ret[7] = (byte)((num>>> 0)&0xff);

        return ret;
    }
    public final static String Hex ( byte[] buffer){
        if ( null == buffer)
            return null;
        else {
            int len = buffer.length;
            char[] cary = new char[(len*2)];
            int val, ca = 0;
            int leadingzero = 0;
            for ( int cc = 0; cc < len; cc++){

                val = (buffer[cc]&0xff);

                if (cc == leadingzero && 0 == val){
                    leadingzero += 1;
                }
                else {
                    cary[ca++] = (HexChars[(val>>>4)&0xf]);
                    cary[ca++] = (HexChars[ val&0xf]);
                }
            }
            if (0 < leadingzero){
                int trim = (leadingzero*2);
                int nlen = (cary.length - trim);
                if (0 < nlen){
                    char[] copier = new char[nlen];
                    System.arraycopy(cary,0,copier,0,nlen);
                    return new java.lang.String(copier);
                }
                else
                    return "00";//(consistent with value '1' encoded as '01')
            }
            else
                return new java.lang.String(cary);
        }
    }

}
