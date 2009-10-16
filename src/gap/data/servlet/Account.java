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
package gap.data.servlet;


import gap.data.*;

import gap.*;
import gap.data.* ;
import gap.service.* ;
import gap.util.* ;

import hapax.TemplateDictionary;
import hapax.TemplateException;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

import javax.annotation.Generated;

/**
 * Generated data bean service methods.
 */
@Generated(value={"gap.service.OD","odl/bean-servlet.xtm"},date="2009-10-15T10:18:14.353Z")
public class Account
    extends gap.servlet.Site
{
    public final static Class<gap.data.Account> BigTableClass = gap.data.Account.class;


    public Account(){
        super();
    }


    protected Parameters createParameters(Request req){
        return new Parameters(req,20,BigTableClass);
    }
    protected TemplateDictionary doGetDefine(Request req, Response rep){

        TemplateDictionary top = req.getTop();


        if (req.hasPath(1) && req.hasPath(2)){

            Key parentKey = gap.data.Resource.KeyLongFor(req.getPath(1));

            gap.data.Account instance = gap.data.Account.ForLongId(parentKey,req.getPath(2));

            if (null != instance){

                instance.dictionaryInto(top);

                return top;
            }
        }
        else {
            req.parameters.dictionaryInto(top);

            com.google.appengine.api.datastore.Query query = gap.data.Account.CreateQueryFor();

            BigTableIterator<gap.data.Account> list = (BigTableIterator<gap.data.Account>)gap.data.Account.QueryN(query,req.parameters.page);

            for (gap.data.Account instance : list){

                instance.dictionaryInto(top);
            }
            return top;
        }
        return null;
    }

}