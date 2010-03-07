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
package yas.data;

import com.google.appengine.api.datastore.Key;

import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLStreamException;

/**
 * 
 *
 */
public final class Twitter
    extends Object
{

    public final static class Name {

        private final static lxl.Index<String> Reserved = new lxl.Index<String>(17);
        static {
            final int RSRV = 0;
            Reserved.put("twitter",RSRV);
            Reserved.put("lists",RSRV);
            Reserved.put("retweet",RSRV);

            Reserved.put("retweets",RSRV);
            Reserved.put("following",RSRV);
            Reserved.put("followings",RSRV);

            Reserved.put("follower",RSRV);
            Reserved.put("followers",RSRV);
            Reserved.put("with_friend",RSRV);

            Reserved.put("with_friends",RSRV);
            Reserved.put("statuses",RSRV);
            Reserved.put("status",RSRV);

            Reserved.put("activity",RSRV);
            Reserved.put("favourites",RSRV);
            Reserved.put("favourite",RSRV);

            Reserved.put("favorite",RSRV);
            Reserved.put("favorites",RSRV);
        }
        private final static String NotReserved(String string){
            if (-1 == Reserved.get(string))
                return string;
            else
                return null;
        }
        private final static Pattern USERNAME = Pattern.compile("[@\uFF20]?[a-z0-9_]{1,20}", Pattern.CASE_INSENSITIVE);

        public final static String Clean(String string){
            if (null == string || 0 == string.length())
                return null;
            else {
                Matcher m = USERNAME.matcher(string);
                if (m.matches()){
                    switch (string.charAt(0)){
                    case '@':
                    case '\uFF20':
                        string = string.substring(1);
                        if (0 == string.length())
                            return null;
                        else
                            return NotReserved(string);
                    default:
                        return NotReserved(string);
                    }
                }
                else
                    return null;
            }
        }
    }

    public final static class Command {

        public final static Feed Search(String targetId)
            throws IOException, XMLStreamException
        {
            String referrer = "http://twitter.com/"+targetId;
            URL searchUrl = new URL("http://search.twitter.com/search.atom?q=%23"+targetId+"&rpp=100&page=1");
            return (new Feed(referrer,searchUrl));
        }

        public final static void Reply(Feed.Data command, String msg)
            throws IOException, XMLStreamException
        {

        }
    }
}
