/*******************************************************************************
 * Copyright (C) 2012 John Pritchard, Gap Data
 *
 * Portions Copyright (C) 2009 Google Inc.
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

package oso.data;


import gap.*;
import gap.data.*;
import gap.util.*;

import json.Json;

import com.google.checkout.sdk.commands.CartPoster;
import com.google.checkout.sdk.commands.CheckoutException;
import com.google.checkout.sdk.commands.EnvironmentInterface;
import com.google.checkout.sdk.commands.EnvironmentInterface.CommandType;
import com.google.checkout.sdk.commands.OrderCommands;
import com.google.checkout.sdk.commands.OrderCommandsImpl;
import com.google.checkout.sdk.commands.ReportsRequester;
import com.google.checkout.sdk.domain.Money;
import com.google.checkout.sdk.domain.OrderSummary;
import com.google.checkout.sdk.notifications.NotificationDispatcher;
import com.google.checkout.sdk.notifications.Notification;
import com.google.checkout.sdk.notifications.NotificationHandler;
import com.google.checkout.sdk.util.Base64Coder;
import com.google.checkout.sdk.util.Utils;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.blobstore.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBElement;

/**
 * Implementor of Api Context
 * 
 * @see MerchantServlet
 * @see gap.checkout.Servlet
 */
public final class Merchant
    extends MerchantData
    implements com.google.checkout.sdk.commands.ApiContext
{
    public final static String DefaultCurrencyCode = "USD";


    protected final static Lock InstanceLockAccept(){
        return Lock.Accept(Merchant.KIND);
    }
    /**
     * For applications having one merchant, exclusively.
     */
    public final static Merchant Instance(){
        Lock lock = Merchant.InstanceLockAccept();
        if (lock.accept()){
            try {
                Merchant merchant = Merchant.Query1(Merchant.CreateQueryFor());
                if (null == merchant){

                    merchant = new Merchant(gap.Strings.RandomIdentifier());
                    merchant.setCurrencyCode(Merchant.DefaultCurrencyCode);
                    merchant.setTest(Boolean.TRUE);
                    merchant.save();
                }
                return merchant;
            }
            finally {
                lock.exit();
            }
        }
        else
            throw new IllegalStateException("Unable to enter instance lock");
    }



    private transient EnvironmentInterface environment;


    public Merchant() {
        super();
    }
    public Merchant(String identifier) {
        super( identifier);
    }
    public Merchant(Json json){
        super();
        String identifier = (String)json.getValue("identifier",String.class);
        
        if (null != identifier){
            Key key = Merchant.KeyLongIdFor( identifier);
            this.setIdentifier(identifier);
            this.setKey(key);
            this.fillFrom(gap.data.Store.Get(key));
            this.markClean();
            /*
             */
            this.fromJson(json);
            if (this.isDirty())
                this.save();
        }
        else {
            throw new IllegalArgumentException("Unable to create new");
        }
    }


    public EnvironmentInterface getEnvironment(){
        return this.environment;
    }
    public void setEnvironment(EnvironmentInterface environment){
        this.environment = environment;
    }
    public boolean isTest(){
        Boolean test = this.getTest();
        if (null == test || test)
            return true;
        else
            return false;
    }

    /*
     * Begin block sourced from original ApiContext class
     */
    public CartPoster cartPoster() {
        return new CartPoster(this);
    }
    public OrderCommands orderCommands(String googleOrderNumber) {

        return new OrderCommandsImpl(this, googleOrderNumber);
    }
    public OrderCommands noEmailsOrderCommands(String googleOrderNumber) {

        return new OrderCommandsImpl(this, googleOrderNumber, false);
    }
    public ReportsRequester reportsRequester() {
        return new ReportsRequester(this);
    }
    public void handleNotification(NotificationDispatcher notificationDispatcher)
        throws CheckoutException
    {
        new NotificationHandler(this).handleNotification(notificationDispatcher);
    }
    public String getHttpAuth() {
        return "Basic " + Base64Coder.encode(this.getMerchantId()+':'+this.getMerchantKey());
    }
    public boolean isValidAuth(String auth) {
        return this.getHttpAuth().equals(auth);
    }
    public Money makeMoney(BigDecimal value) {
        Money money = new Money();
        money.setCurrency(this.getCurrencyCode());
        money.setValue(Utils.Normalize(value));

        return money;
    }
    public Money makeMoney(double value) {
        return makeMoney(BigDecimal.valueOf(value));
    }
    public Object postCommand(CommandType command, JAXBElement<?> jaxbDomainObject)
        throws CheckoutException
    {
        HttpURLConnection connection = makeConnection(environment.getUrl(command, this.getMerchantId()));
        return Utils.PostJAXB(connection, jaxbDomainObject);
    }
    public String postCommand(CommandType command, String commandXml) 
        throws CheckoutException
    {
        HttpURLConnection connection = makeConnection(environment.getUrl(command, this.getMerchantId()));
        return Utils.PostXML(connection, commandXml);
    }
    public HttpURLConnection makeConnection(String toUrl)
        throws CheckoutException
    {
        HttpURLConnection connection = openHttpConnection(toUrl);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setReadTimeout(0);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Authorization", getHttpAuth());
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new CheckoutException(e);
        }
        return connection;
    }
    public HttpURLConnection openHttpConnection(String toUrl)
        throws CheckoutException
    {
        URL url;
        try {
            url = new URL(toUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new CheckoutException(e);
        }
    }
    /*
     * End block 
     */

    public void onread(){
    }
    public void onwrite(){
    }
    public java.io.Serializable valueOf(gap.data.Field field, boolean mayInherit){
        return (java.io.Serializable)Field.Get((Field)field,this,mayInherit);
    }
    public void define(gap.data.Field field, java.io.Serializable value){
        Field.Set((Field)field,this,value);
    }
    public void drop(){
        Delete(this);
    }
    public void clean(){
        Clean(this);
    }
    public void save(){
        Save(this);
    }
    public void store(){
        Store(this);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonIdentifier(){
        return super.toJsonIdentifier();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonIdentifier(Json json){
        return super.fromJsonIdentifier(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonMerchantId(){
        return super.toJsonMerchantId();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonMerchantId(Json json){
        return super.fromJsonMerchantId(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonMerchantKey(){
        return super.toJsonMerchantKey();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonMerchantKey(Json json){
        return super.fromJsonMerchantKey(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonCurrencyCode(){
        return super.toJsonCurrencyCode();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonCurrencyCode(Json json){
        return super.fromJsonCurrencyCode(json);
    }
    /**
     * To disable json access via 'toJson', redefine this method to "return null".
     */
    @Override
    public Json toJsonTest(){
        return super.toJsonTest();
    }
    /**
     * To disable json access via 'fromJson', redefine this method to "return false".
     */
    @Override
    public boolean fromJsonTest(Json json){
        return super.fromJsonTest(json);
    }
}
