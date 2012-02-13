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

package oso.data;


import gap.Request;
import gap.Response;
import gap.data.List;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateException;
import gap.hapax.TemplateName;
import gap.hapax.TemplateRenderer;
import gap.service.Templates;

import oso.data.Person;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * Merchant editor
 *
 * @see Merchant
 */
public abstract class MerchantServlet
    extends gap.service.Servlet
{

    public MerchantServlet() {
        super();
    }


}
