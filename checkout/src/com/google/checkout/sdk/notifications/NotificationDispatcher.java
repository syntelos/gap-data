/*******************************************************************************
 * Copyright (C) 2009 Google Inc.
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
package com.google.checkout.sdk.notifications;

import com.google.checkout.sdk.commands.CheckoutException;

import com.google.checkout.sdk.domain.AuthorizationAmountNotification;
import com.google.checkout.sdk.domain.ChargeAmountNotification;
import com.google.checkout.sdk.domain.ChargebackAmountNotification;
import com.google.checkout.sdk.domain.NewOrderNotification;
import com.google.checkout.sdk.domain.OrderStateChangeNotification;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.domain.RefundAmountNotification;
import com.google.checkout.sdk.domain.RiskInformationNotification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> Instances of this class are request-response events for
 * notifications.  Notifications are Google Checkout API Callbacks,
 * and are an optional component of application implementation. </p>
 * 
 * <p> Notification events are processed via the {@link
 * com.google.checkout.sdk.commands.ApiContext ApiContext} into the
 * methods defined this interface. </p>
 *
 * <h3>On Notification Methods</h3>
 *
 * <p>Define application logic for handling a particular notification
 * through the definition of the on notification methods. </p>
 *
 * <h3>On All Notifications Method</h3>
 *
 * <p> The {@link #onAllNotifications} method is invoked before more
 * specific on notification methods.</p>
 *
 * <h3>Transaction Logic Methods</h3>
 * <ol>
 *  <li>{@link #startTransaction}</li>
 *  <li>{@link #rollBackTransaction}</li>
 *  <li>{@link #commitTransaction}</li>
 * </ol>
 * 
 * <p> Define the transaction methods {@link #startTransaction},
 * {@link #rollBackTransaction}, and {@link #commitTransaction} to act
 * on any (optional) data transaction interface.  In this case, ensure
 * that {@link #commitTransaction} is executed before any
 * acknowledgment is sent back to Google Checkout.</p>
 *
 * <h3>Notification Logic Methods</h3>
 * <ol>
 *  <li>{@link #hasAlreadyHandled}</li>
 *  <li>{@link #rememberSerialNumber}</li>
 * </ol>
 * 
 * <p> Define the notification logic methods to ensure that the
 * notification event is handled once, uniquely.</p>
 * 
 * <h3>Usage</h3>
 * 
 * <p> Define a servlet receiving notifications, and dispatch the
 * notifications via an instance of this class.  </p>
 * 
 * <p> In the following example, the definition of this class is
 * represented by a class named "LocalNotificationDispatcher".
 * 
 * <pre>
 *
 * public class LocalNotificationDispatcher
 *     implements NotificationDispatcher
 * {
 *   ...
 * }
 *
 * public class NotificationServlet
 *     extends gap.checkout.Servlet
 * {
 * 
 *   @Override
 *   protected void doPost(HttpServletRequest q, HttpServletResponse p) {
 * 
 *       this.getMerchant(q,p).handleNotification(new LocalNotificationDispatcher(q, p));
 *   }
 * }
 * </pre>
 * </p>
 * 
 * @see com.google.checkout.sdk.commands.ApiContext
 * @see https://checkout.google.com/sell/settings?section=Integration
 */
public interface NotificationDispatcher {

    /*
     * The following methods must be defined
     */

    /**
     * @return The current request
     */
    public HttpServletRequest getRequest();
    /**
     * @return The current response
     */
    public HttpServletResponse getResponse();

    /**
     * With a read lock, determine whether this notification has already been
     * handled.
     * @param serialNumber The serial number of this notification, which is
     *    unique to this order/notification pair, but stable across each attempt
     *    to deliver the notification.
     * @param orderSummary The state of the order whose notification is currently
     *    being handled.
     * @param notification The parsed JAXB object of the notification itself.
     * @return True if this notification has already been successfully handled and
     *    committed; otherwise false.
     */
    public boolean hasAlreadyHandled(String serialNumber,
                                     OrderSummary orderSummary,
                                     Notification notification)
        throws CheckoutException;


    /**
     * Save away the serialNumber so that if hasAlreadyHandled is called with this
     * serial number in the future, it will return "true".
     * @param serialNumber The serial number of this notification, which is
     *    unique to this order/notification pair, but stable across each attempt
     *    to deliver the notification.
     * @param orderSummary The state of the order whose notification is currently
     *    being handled.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void rememberSerialNumber(String serialNumber,
                                     OrderSummary orderSummary,
                                     Notification notification)
        throws CheckoutException;

    /*
     * The following methods are optionally defined
     */

    /**
     * Start a new database transaction for handling this notification.
     * @param serialNumber The serial number of this notification, which is
     *    unique to this order/notification pair, but stable across each attempt
     *    to deliver the notification.
     * @param orderSummary The state of the order whose notification is currently
     *    being handled.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void startTransaction(String serialNumber,
                                    OrderSummary orderSummary,
                                    Notification notification)
        throws CheckoutException;

    /**
     * The notification was successfully handled, so commit the current
     * transaction.
     * @param serialNumber The serial number of this notification, which is
     *    unique to this order/notification pair, but stable across each attempt
     *    to deliver the notification.
     * @param orderSummary The state of the order whose notification is currently
     *    being handled.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void commitTransaction(String serialNumber,
                                     OrderSummary orderSummary,
                                     Notification notification)
        throws CheckoutException;

    /**
     * An error occurred while handling the notification, so roll back the current
     * transaction.
     * @param serialNumber The serial number of this notification, which is
     *    unique to this order/notification pair, but stable across each attempt
     *    to deliver the notification.
     * @param orderSummary The state of the order whose notification is currently
     *    being handled.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void rollBackTransaction(String serialNumber,
                                       OrderSummary orderSummary,
                                       Notification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters any new notification from Checkout.  This method will be called
     * before the specific onFooNotification() methods.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onAllNotifications(OrderSummary orderSummary,
                                      Notification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled RefundAmountNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onRefundAmountNotification(OrderSummary orderSummary,
                                              RefundAmountNotification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled RiskInformationNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onRiskInformationNotification(OrderSummary orderSummary,
                                                 RiskInformationNotification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled OrderStateChangeNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onOrderStateChangeNotification(OrderSummary orderSummary,
                                                  OrderStateChangeNotification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled NewOrderNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onNewOrderNotification(OrderSummary orderSummary,
                                          NewOrderNotification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled ChargebackAmountNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onChargebackAmountNotification(OrderSummary orderSummary,
                                                  ChargebackAmountNotification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled ChargeAmountNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onChargeAmountNotification(OrderSummary orderSummary,
                                              ChargeAmountNotification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled AuthorizationAmountNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onAuthorizationAmountNotification(OrderSummary orderSummary,
                                                     AuthorizationAmountNotification notification)
        throws CheckoutException;

    /**
     * Should be overridden with the behavior that should happen when your system
     * encounters a not-yet-handled RefundAmountNotification.
     * @param orderSummary The parsed OrderSummary object from the notification.
     * @param notification The parsed JAXB object of the notification itself.
     */
    public void onRiskAmountNotification(OrderSummary orderSummary,
                                            RefundAmountNotification notification)
        throws CheckoutException;

}
