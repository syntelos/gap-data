/*******************************************************************************
 * Copyright (C) 2009 Google Inc.
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
package com.google.checkout.sdk.commands;

import com.google.checkout.sdk.commands.EnvironmentInterface.CommandType;
import com.google.checkout.sdk.domain.Money;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.notifications.BaseNotificationDispatcher;
import com.google.checkout.sdk.notifications.Notification;
import com.google.checkout.sdk.notifications.NotificationHandler;
import com.google.checkout.sdk.util.Base64Coder;
import com.google.checkout.sdk.util.Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBElement;

/**
 * The integration between a merchant's system and Google
 * Checkout. The implementor holds configuration parameters including
 * the Merchant Id and Merchant Key, and knows how to formulate and
 * post Google Checkout API commands using these values.
 *
 */
public interface ApiContext {

    /**
     * @return The merchantId as passed in the constructor.
     */
    public String getMerchantId();

    /**
     * @return The merchantKey as passed in the constructor.
     */
    public String getMerchantKey();

    /**
     * @return The currency in which this merchant operates, such as USD or GBP.
     */
    public String getCurrencyCode();

    public EnvironmentInterface getEnvironment();

    /**
     * @return A helper object for posting server-to-server shopping carts to
     *    Google Checkout given this configuration. Buyers can then purchase these
     *    shopping carts; see the documentation on {@link #handleNotification}
     *    for how to discover when this has occurred.
     */
    public CartPoster cartPoster();

    /**
     * @param googleOrderNumber The order number of the order to modify.
     *    This can be extracted via {@link OrderSummary#getGoogleOrderNumber()}
     *    or {@link Notification#getGoogleOrderNumber()} invoked on a notification.
     * @return A helper object for postpurchase processing Google Checkout orders
     *    for this configuration.
     */
    public OrderCommands orderCommands(String googleOrderNumber);

    /**
     * @param googleOrderNumber The order number of the order to modify.
     * @return A helper object for postpurchase processing Google Checkout orders
     *    for this configuration. The object will (when possible) avoid sending
     *    email about changed states to the buyer.
     */
    public OrderCommands noEmailsOrderCommands(String googleOrderNumber);

    /**
     * @return A helper object for fetching notifications and events for this
     *    configuration.
     */
    public ReportsRequester reportsRequester();


    /**
     * <p>Uses the given notification dispatcher to handle a notification POST for
     * Checkout.  Can handle XML, HTML and serial-number notifications.</p>
     *
     * <p>To handle notifications, extend the {@link BaseNotificationDispatcher}
     * class with your business-specific logic.  Then, in a {@code Servlet} that's
     * handling the {@code POST} from Checkout, pass in a new instance of your
     * child class to this method:
     * <code>
     * public class MyServlet extends HttpServlet {
     *   public void doPost(HttpServletRequest request, HttpServletResponse response) {
     *     apiContext.handleNotification(
     *          new MyNotificationDispatcher(request, response));
     *   }
     * }
     * </code>
     * </p>
     * <p>This will rethrow any exceptions that occur during the transaction or
     * business logic, but will not rethrow exceptions that occur while responding
     * to Checkout notifications with an acknowledgment message.</p>
     * @param notificationDispatcher A new instance of an object which handles
     *    business logic and possibly transactional logic in response to
     *    Google Checkout notifications.
     * @throws CheckoutException if the {@code notificationDispatcher} methods
     *    throw an exception. Importantly, this method will not throw any
     *    exceptions at all if there is an error while responding to Google
     *    Checkout after handling the notification.
     */
    public void handleNotification(BaseNotificationDispatcher notificationDispatcher)
        throws CheckoutException;

    /**
     * @return The contents of the Authorization header line for a correctly
     *    authenticated Google Checkout command. These take the form
     *    <code>Basic SomeBase64Encoded_Stuff</code>,
     *    with SomeBase64Encoded_Stuff being
     *    <code>Base64Coder.encode(merchantId + ":" + merchantKey)</code>
     */
    public String getHttpAuth();

    /**
     * @param auth An authorization header.
     * @return If {@link #getHttpAuth} would return the equivalent value.
     */
    public boolean isValidAuth(String auth);

    /**
     * @param value The amount of Money in question; 1.0 is 1 unit of whichever
     *    currency {@link #getMerchantCurrencyCode()} reveals.
     *    {@code makeMoney(new BigDecimal("0.01"))} represents 1 US cent, for
     *    instance.
     * @return A Money object of the correct {@code value}.
     */
    public Money makeMoney(BigDecimal value);

    /**
     * Creates a Money object for use in other commands. This is a helper for
     * tests: real, production systems should not use {@code double} values for
     * money, because they're naturally imprecise and can have confusing effects.
     * Consider using {@link #makeMoney(BigDecimal)} instead.
     * @param value The amount of Money in question; 1.0 is 1 unit of whichever
     *    currency {@link #getMerchantCurrencyCode()} reveals.
     *    {@code makeMoney(0.01)} represents 1 US cent, for instance.
     * @return A Money object of the correct {@code value}.
     */
    public Money makeMoney(double value);

    /**
     * Post order processing XML to the appropriate URL.
     * @param jaxbDomainObject The domain object for the command to run.
     * @return The parsed response object.
     * @throws CheckoutException If underlying I/O operations throw or an unsuccessful
     *    response is given.
     */
    public Object postCommand(CommandType command, JAXBElement<?> jaxbDomainObject)
        throws CheckoutException;

    /**
     * Post order processing XML to the appropriate URL.
     * @param commandXml The xml string for the command to run.
     * @return The response xml.
     * @throws CheckoutException If underlying I/O operations throw or an
     *     unsuccessful response is given.
     */
    public String postCommand(CommandType command, String commandXml) 
        throws CheckoutException ;

    /**
     * Opens and configures a connection to the appropriate URL with basic auth
     * set up for this merchant. Depending on the URL you pass it, it may or may
     * not give you an {@link HttpsURLConnection}.
     * @param toUrl A url to which to connect.
     * @return The HttpURLConnection.
     * @throws CheckoutException If the underlying operations throw.
     */
    public HttpURLConnection makeConnection(String toUrl) 
        throws CheckoutException;

    /**
     * Does the actual act of opening a connection. Particularly for
     * testing, this method may be overridden to not use real URL objects, but
     * instead some testing object or behavior. If you override this,
     * {@link #makeConnection} will still prepare headers, so your mock objects
     * will have to be relatively fully featured.
     * @param toUrl The URL to open a connection against.
     * @return An {@code HttpURLConnection} pointing at {@code toUrl} with no
     *    other actions taken (such as headers, configurations, etc).
     * @throws CheckoutException if underlying operation throws IOException.
     */
    public HttpURLConnection openHttpConnection(String toUrl) 
        throws CheckoutException;
}
