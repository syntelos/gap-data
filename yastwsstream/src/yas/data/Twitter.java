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
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;


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
    extends yusu.Twitter
{
    private final static double DeadlineSeconds = 6;
    private final static FetchOptions Options = FetchOptions.Builder.disallowTruncate().setDeadline(DeadlineSeconds);
    private final static HTTPMethod Get = HTTPMethod.GET;
    private final static HTTPMethod Post = HTTPMethod.POST;
    private final static HTTPHeader UA = new HTTPHeader("User-Agent","Gap-Data/"+gap.Version.Short);

    /**
     * 
     */
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
    /**
     * 
     */
    public final static class IO {
        private final static HTTPHeader ContentTypeUrlEnc = new HTTPHeader("Content-Type","application/x-www-form-urlencoded");

        public final static HTTPResponse Get(String referrer, URL url)
            throws IOException
        {
            HTTPRequest request = new HTTPRequest(url,Get,Options);
            request.addHeader(UA);
            if (null != referrer)
                request.addHeader(new HTTPHeader("Referer",referrer));
            URLFetchService service = URLFetchServiceFactory.getURLFetchService();
            return service.fetch(request);
        }
        public final static HTTPResponse Post(String referrer, URL url, String body)
            throws IOException
        {
            HTTPRequest request = new HTTPRequest(url,Post,Options);
            request.addHeader(UA);

            if (null != referrer)
                request.addHeader(new HTTPHeader("Referer",referrer));

            if (null != body){
                request.addHeader(ContentTypeUrlEnc);
                byte[] content = body.getBytes("US-ASCII");
                request.setPayload(content);
                /*
                 * Redundant? 
                 */
                request.addHeader(new HTTPHeader("Content-Length",String.valueOf(content.length)));
            }
            URLFetchService service = URLFetchServiceFactory.getURLFetchService();
            return service.fetch(request);
        }
    }
    /**
     * 
     */
    public final static class Command {
        private final static URL PostUrl;
        static {
            try {
                PostUrl = new URL("http://api.twitter.com/1/statuses/update.xml");
            }
            catch (IOException exc){
                throw new InternalError();
            }
        }

        public final static Feed Search(Target target)
            throws IOException, XMLStreamException
        {
            String referrer = target.getReferer();
            String targetId = target.getTwitterId();
            URL searchUrl = new URL("http://search.twitter.com/search.atom?q=%23"+targetId+"&rpp=100&page=1");
            return (new Feed(referrer,searchUrl));
        }

        public final static boolean Reply(Target target, Feed.Data data, String status)
            throws IOException
        {
            String referrer = target.getReferer();
            StringBuilder body = new StringBuilder();
            body.append("status=");
            body.append(java.net.URLEncoder.encode(status,"US-ASCII"));
            body.append("&in_reply_to_status_id=");
            body.append(data.guid);
            HTTPResponse response = Twitter.IO.Post(referrer,PostUrl,body.toString());
            return (2 == (response.getResponseCode()/100));
        }
    }



    public Twitter(Target target){
        super(target.getTwitterKey(),target.getTwitterSecret());
    }
}
