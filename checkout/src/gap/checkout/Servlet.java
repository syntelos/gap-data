/*******************************************************************************
 * Copyright (C) 2007 Google Inc.
 * Copyright (C) 2012 John Pritchard, Gap Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package gap.checkout;

import com.google.checkout.sdk.commands.CheckoutException;
import com.google.checkout.sdk.domain.ItemId;
import com.google.checkout.sdk.util.HttpUrlException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Various XML utilities.
 *
 * @version 1.1 - ksim - March 6th, 2007 - Added functions regarding streaming
 * @version 1.2 - ksim - March 10th, 2007 - Added functions regarding DOM
 *          manipulation
 * @version 2.0 - jdp - 12 Feb 2012 - conversion to servlet in gap-data
 */

public class Servlet
    extends gap.service.Servlet
{

    public static final String SEND_AND_RECEIVE_DEBUGGING_STRING =
        "Response Code:\t{0}\n"
        + "With Url:\t[ {1} ]\n"
        + "Request:\t[ {2} ]\n"
        + "Response:\t[ {3} ]";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String XML_MIME_TYPE = "application/xml";


    private static final SimpleDateFormat SDF = new SimpleDateFormat(
                                                                     "yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private static final JAXBContext Context = Servlet.MakeJaxbContext();

    private static JAXBContext MakeJaxbContext() {
        try {
            return JAXBContext.newInstance("com.google.checkout.sdk.domain");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> JAXBElement<T> FromXML(InputStream is) throws JAXBException {
        return (JAXBElement<T>)Context.createUnmarshaller().unmarshal(is);
    }

    public static void ToXML(JAXBElement<?> jaxbElement, OutputStream os) {
        JAXB.marshal(jaxbElement, os);
    }

    @SuppressWarnings("unchecked")
    public static <T> JAXBElement<T> FromXML(String xmlString)
        throws JAXBException
    {
        return (JAXBElement<T>)Context.createUnmarshaller().unmarshal(new StringReader(xmlString));
    }

    public static String ToXML(JAXBElement<?> jaxbElement) {
        StringWriter stringWriter = new StringWriter();
        JAXB.marshal(jaxbElement, stringWriter);
        return stringWriter.toString();
    }

    /**
     * @param socket An opened connection to a listening checkout service with all
     *    headers already set. It must not have been otherwise used or processed.
     *    It will always be disconnected when this method returns.
     * @param xmlString The data to send across the wire.
     * @return The string as returned by the server
     * @throws HttpUrlException If any operation fails
     */
    public static String PostXML(HttpURLConnection socket, String xmlString)
        throws HttpUrlException
    {
        try {
            Exception underlying = null;
            try {
                Writer writer = new OutputStreamWriter(socket.getOutputStream());
                writer.write(xmlString);
                writer.close();
                if (socket.getResponseCode() == 200) {
                    String slurp = Slurp(socket.getInputStream());

                    Log.log(Level.INFO, "Received from:\t{0}\nContent:\t{1}",
                            new Object[]{socket.getURL(), slurp});

                    return slurp;
                }
            } 
            catch (IOException e) {
                underlying = e;
            }
            throw MakeUrlException(socket, xmlString, underlying);
        }
        finally {
            socket.disconnect();
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V PostJAXB( HttpURLConnection socket, JAXBElement<?> jaxbElement)
        throws CheckoutException
    {
        try {
            Exception underlying = null;
            try {
                ToXML(jaxbElement, socket.getOutputStream());
                if (socket.getResponseCode() == 200) {
                    JAXBElement<V> response = FromXML(socket.getInputStream());

                    Log.log(Level.INFO, SEND_AND_RECEIVE_DEBUGGING_STRING,
                            new Object[]{200, socket.getURL(), jaxbElement.getValue(), response.getValue()});

                    return response.getValue();
                }
            }
            catch (IOException e) {
                underlying = e;
            }
            catch (JAXBException e) {
                underlying = e;
            }
            throw MakeUrlException(socket, jaxbElement.getValue(), underlying);
        }
        finally {
            socket.disconnect();
        }
    }

    public static String Slurp(InputStream from) throws IOException {
        if (from == null)
            return "";
        else {
            final char[] buffer = new char[1024];
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(from, "UTF-8");
            int read;
            do {
                read = in.read(buffer, 0, buffer.length);
                if (read>0) {
                    out.append(buffer, 0, read);
                }
            }
            while (read>=0);

            return out.toString();
        }
    }

    public static String GetDateString(XMLGregorianCalendar date) {
        if (date == null) {
            return "null";
        }
        else {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            SDF.setTimeZone(tz);
            String ret = SDF.format(date);

            return ret.substring(0, ret.length() - 5) + "Z";
        }
    }

    /**
     * Detects whether the "notification" received is actually a ping indicating
     * Google Checkout must be contacted to fetch the notification.
     * This library can operate in serial number request mode, where the client
     * doesn't need a certificate to authenticate themselves; instead, Google
     * Checkout doesn't post the entire notification but simply indicates that
     * one should be fetched.
     * @return True if a notification should be fetched rather than parsed.
     */
    public static boolean IsSerialNumberRequest(HttpServletRequest request) {
        String mimeType = request.getHeader(CONTENT_TYPE);
        if (mimeType == null || mimeType.isEmpty()) {
            return true;
        }
        else
            return !mimeType.toLowerCase().contains(XML_MIME_TYPE);
    }

    public static ItemId MakeItemId(String itemId) {
        ItemId id = new ItemId();
        id.setMerchantItemId(itemId);
        return id;
    }

    public static BigDecimal Normalize(BigDecimal value) {
        value = value.stripTrailingZeros();
        if (value.scale() < 0) {
            value = value.setScale(0);
        }
        return value;
    }

    private static HttpUrlException MakeUrlException(HttpURLConnection hurlc,
                                                     Object triedToSend,
                                                     Throwable possibleUnderlying)
    {
        URL url = hurlc.getURL();
        int responseCode;
        String slurp;
        try {
            slurp = Servlet.Slurp(hurlc.getErrorStream());
        }
        catch (IOException e) {
            if (possibleUnderlying != null) {
                Log.log(Level.SEVERE,
                        "Masking exception while processing communication error from checkout:", e);
            }
            else {
                possibleUnderlying = e;
            }
            slurp = "<Error reading cause of error>";
        }
        try {
            responseCode = hurlc.getResponseCode();
        }
        catch (IOException e) {
            if (possibleUnderlying != null) {
                Log.log(Level.SEVERE,
                        "Masking exception while processing communication error from checkout:", e);
            }
            else {
                possibleUnderlying = e;
            }
            responseCode = Integer.MAX_VALUE;
        }
        return new HttpUrlException(url, responseCode,
                                    triedToSend, slurp, possibleUnderlying);
    }
}
