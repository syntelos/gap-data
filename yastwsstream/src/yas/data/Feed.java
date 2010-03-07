package yas.data;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.StringTokenizer;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Consume an atom feed for a set of twitter guids.
 */
public class Feed
    extends lxl.ArrayList<Feed.Data>
    implements javax.xml.stream.XMLStreamConstants
{
    private final static double DeadlineSeconds = 6;
    private final static FetchOptions Options = FetchOptions.Builder.disallowTruncate().setDeadline(DeadlineSeconds);
    private final static HTTPMethod Get = HTTPMethod.GET;
    private final static HTTPHeader UA = new HTTPHeader("User-Agent","Gap-Data/"+gap.Version.Short);

    private final static XMLInputFactory InputFactory = XMLInputFactory.newInstance();

    /**
     * 
     */
    public final static class Data 
        extends Object
    {
        public final String content, author, guid;

        public Data(String content, String author, String guid){
            super();
            this.content = content;
            this.author = author;
            this.guid = guid;
        }
    }


    public Feed(String referrer, URL url)
        throws IOException, XMLStreamException
    {
        super();
        HTTPRequest request = new HTTPRequest(url,Get,Options);
        request.addHeader(UA);
        if (null != referrer)
            request.addHeader(new HTTPHeader("Referer",referrer));
        URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        HTTPResponse response = service.fetch(request);
        InputStream xmlInput = new java.io.ByteArrayInputStream(response.getContent());
        XMLStreamReader reader = InputFactory.createXMLStreamReader(xmlInput);
        boolean inEntries = false;
        String entryContent = null;
        while (true){
            switch (reader.next()){
            case START_ELEMENT:
                String localName = reader.getLocalName();
                if (inEntries){
                    if ("title".equals(localName)){

                        entryContent = reader.getElementText();
                    }
                    else if ("link".equals(localName)
                        && "text/html".equals(reader.getAttributeValue(null,"type")))
                    {
                        String href = reader.getAttributeValue(null,"href");
                        if (null != href){
                            StringTokenizer strtok = new StringTokenizer(href,":/");
                            try {
                                if ("http".equals(strtok.nextToken()) &&
                                    "twitter.com".equals(strtok.nextToken()))
                                {
                                    String author = strtok.nextToken();
                                    if ("statuses".equals(strtok.nextToken())){
                                        String guid = strtok.nextToken();
                                        this.add(new Data(entryContent,author,guid));
                                    }
                                }
                            }
                            catch (java.util.NoSuchElementException ignore){
                            }
                        }
                    }
                }
                else if ("entry".equals(localName)){
                    inEntries = true;
                }
                break;
            case END_DOCUMENT:
                return;
            default:
                break;
            }
        }
    }
}
