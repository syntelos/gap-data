/*******************************************************************************
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

import gap.Request;
import gap.Response;

import oso.data.Merchant;

import com.google.checkout.sdk.commands.Environment;

/**
 * Checkout servlet defined for an application having one merchant.
 * 
 * 
 */
public abstract class Servlet
    extends gap.service.Servlet
{


    public Servlet(){
        super();
    }


    /**
     * This method is responsible for calling set environment on the
     * merchant instance object before returning it.
     * 
     * This method has been defined, here, for an application having
     * one merchant.
     * 
     * @return Merchant instance with checkout environment
     */
    public Merchant getMerchant(Request q, Response p){
        /*
         * For applications having one merchant, exclusively.
         */
        Merchant merchant = Merchant.Instance();
        if (merchant.isTest())
            merchant.setEnvironment(this.getCheckoutTest());
        else
            merchant.setEnvironment(this.getCheckoutProduction());

        return merchant;
    }

    protected final Environment getCheckoutProduction(){
        return Environment.PRODUCTION;
    }
    protected final Environment getCheckoutTest(){
        return Environment.SANDBOX;
    }
}
