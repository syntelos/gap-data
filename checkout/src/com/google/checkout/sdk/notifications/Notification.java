/*******************************************************************************
 * Copyright (C) 2010 Google Inc.
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

import com.google.checkout.sdk.domain.OrderSummary;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Common interface implemented by all Google Checkout notifications.
 *
 */
public interface Notification {

    public enum DispatchType {
        Acknowledgment, AuthorizationAmount, ChargeAmount, ChargebackAmount, NewOrder, OrderStateChange, OrderSummary, RefundAmount, RiskInformation, Unknown;

        private final static java.util.Map<String,DispatchType> Map = new java.util.HashMap();
        static {

            Map.put("com.google.checkout.sdk.domain.AuthorizationAmountNotification",DispatchType.AuthorizationAmount);
            Map.put("com.google.checkout.sdk.domain.ChargeAmountNotification",DispatchType.ChargeAmount);
            Map.put("com.google.checkout.sdk.domain.ChargebackAmountNotification",DispatchType.ChargebackAmount);
            Map.put("com.google.checkout.sdk.domain.NewOrderNotification",DispatchType.NewOrder);
            Map.put("com.google.checkout.sdk.domain.NotificationAcknowledgment",DispatchType.Acknowledgment);
            Map.put("com.google.checkout.sdk.domain.OrderStateChangeNotification",DispatchType.OrderStateChange);
            Map.put("com.google.checkout.sdk.domain.OrderSummary",DispatchType.OrderSummary);
            Map.put("com.google.checkout.sdk.domain.RefundAmountNotification",DispatchType.RefundAmount);
            Map.put("com.google.checkout.sdk.domain.RiskInformationNotification",DispatchType.RiskInformation);
        }
        public final static DispatchType For(Notification instance){
            DispatchType type = Map.get(instance.getClass().getName());
            if (null != type)
                return type;
            else
                return DispatchType.Unknown;
        }
    }



    String getGoogleOrderNumber();
    void setGoogleOrderNumber(String googleOrderNumber);

    OrderSummary getOrderSummary();
    void setOrderSummary(OrderSummary orderSummary);

    String getSerialNumber();
    void setSerialNumber(String serialNumber);

    XMLGregorianCalendar getTimestamp();
    void setTimestamp(XMLGregorianCalendar value);
}
