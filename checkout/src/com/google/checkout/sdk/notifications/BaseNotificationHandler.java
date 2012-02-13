/*******************************************************************************
 * Copyright (C) 2010 Google Inc.
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
import com.google.checkout.sdk.domain.NotificationAcknowledgment;
import com.google.checkout.sdk.domain.OrderStateChangeNotification;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.domain.RefundAmountNotification;
import com.google.checkout.sdk.domain.RiskInformationNotification;
import static com.google.checkout.sdk.notifications.Notification.DispatchType;
import static com.google.checkout.sdk.notifications.Notification.DispatchType.*;
import com.google.checkout.sdk.util.Utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Superclass for notification handling: contains useful utilities, but no
 * public methods.
 *
 */
public class BaseNotificationHandler {

    protected static final Logger logger = Logger.getLogger("com.google.checkout.sdk");

    /**
     * Calls one of the onFooNotification() methods on the given dispatcher.
     */
    protected void dispatchByType(Notification notification, OrderSummary orderSummary,
                                  BaseNotificationDispatcher dispatcher)
        throws CheckoutException
    {
        final DispatchType type = DispatchType.For(notification);
        switch(type){
        case AuthorizationAmount:
            dispatcher.onAuthorizationAmountNotification(orderSummary, (AuthorizationAmountNotification)notification);
            return;
        case ChargeAmount:
            dispatcher.onChargeAmountNotification(orderSummary, (ChargeAmountNotification)notification);
            return;
        case ChargebackAmount:
            dispatcher.onChargebackAmountNotification(orderSummary, (ChargebackAmountNotification)notification);
            return;
        case NewOrder:
            dispatcher.onNewOrderNotification(orderSummary, (NewOrderNotification)notification);
            return;
        case OrderStateChange:
            dispatcher.onOrderStateChangeNotification(orderSummary, (OrderStateChangeNotification)notification);
            return;
        case RefundAmount:
            dispatcher.onRefundAmountNotification(orderSummary, (RefundAmountNotification)notification);
            return;
        case RiskInformation:
            dispatcher.onRiskInformationNotification(orderSummary, (RiskInformationNotification)notification);
            return;
        case Unknown:
            dispatchUnknownNotification(notification, orderSummary, dispatcher);
            return;
        default:
            throw new IllegalStateException(type.name());
        }
    }

    protected void dispatchUnknownNotification(Notification notification, OrderSummary orderSummary,
                                               BaseNotificationDispatcher dispatcher)
        throws CheckoutException
    {
        throw new CheckoutException("Unrecognized notification type " + notification);
    }

    /**
     * Sends a NotificationAcknowledgment with the given serial number.
     * @throws Exception if the acknowledgment could not be sent.
     */
    protected void sendNotificationAcknowledgment(String serialNumber, HttpServletResponse response,
                                                  Notification notification, HttpServletRequest request)
        throws CheckoutException, IOException
    {
        NotificationAcknowledgment ack = new NotificationAcknowledgment();
        ack.setSerialNumber(serialNumber);

        Utils.ToXML(ack.toJAXB(), response.getOutputStream());

        logger.log(Level.INFO,
                   "Sent response ack:\n" + Utils.SEND_AND_RECEIVE_DEBUGGING_STRING,
                   new Object[]{200, request.getRemoteAddr(), notification, ack});
    }
}
