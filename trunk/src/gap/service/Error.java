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
package gap.service;

import hapax.TemplateDictionary;
import hapax.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.Reader;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;


/**
 * Compile java source to binary.
 * 
 * @author jdp
 */
public class Error {

    public final static class Attribute {
        /**
         * Defines the HTTP error code, if applicable, as an int
         * object. If the servlet throws an exception not related to
         * HTTP, the status code is usually set to 500 (Internal
         * Server Error).
         */
        public final static java.lang.String Status = "javax.servlet.error.status_code";
        /**
         * Returns the exception or error message.
         */
        public final static java.lang.String Message = "javax.servlet.error.message";
        /**
         * Defines the type of exception.
         */
        public final static java.lang.String Type = "javax.servlet.error.exception_type";
        /**
         * Defines the actual exception thrown. You can use the
         * printStackTrace method to view the stack trace of the
         * exception.
         */
        public final static java.lang.String Exception = "javax.servlet.error.exception";
        /**
         *  Defines the request URI prior to the exception being thrown.
         */
        public final static java.lang.String URI = "javax.servlet.error.request_uri";


        public final static class Value {

            public final static Object Status(HttpServletRequest req){
                return req.getAttribute(Error.Attribute.Status);
            }
            public final static Object Message(HttpServletRequest req){
                return req.getAttribute(Error.Attribute.Message);
            }
            public final static Object Type(HttpServletRequest req){
                return req.getAttribute(Error.Attribute.Type);
            }
            public final static Throwable Exception(HttpServletRequest req){
                return (Throwable)req.getAttribute(Error.Attribute.Exception);
            }
            public final static Object URI(HttpServletRequest req){
                return req.getAttribute(Error.Attribute.URI);
            }
            public final static void Setup(HttpServletRequest req, Path path, int status, java.lang.String msg, Throwable thrown){

                req.setAttribute(Error.Attribute.Status,java.lang.String.valueOf(status));
                req.setAttribute(Error.Attribute.Message,msg);
                req.setAttribute(Error.Attribute.Type,thrown.getClass().getName());
                req.setAttribute(Error.Attribute.Exception,thrown);
                req.setAttribute(Error.Attribute.URI,path.getFull());
            }
        }

        public final static class ToString {

            public final static java.lang.String Exception(HttpServletRequest req){
                return Exception(req,null);
            }
            public final static java.lang.String Exception(HttpServletRequest req, Throwable any){
                Throwable value = Attribute.Value.Exception(req);
                if (null == value)
                    value = any;
                if (null != value){
                    java.io.CharArrayWriter buffer = new java.io.CharArrayWriter();
                    java.io.PrintWriter err = new java.io.PrintWriter(buffer);
                    value.printStackTrace(err);
                    return buffer.toString();
                }
                else
                    return "";
            }
            public final static java.lang.String Status(HttpServletRequest req){
                Object value = Attribute.Value.Status(req);
                if (null != value)
                    return value.toString();
                else
                    return "";
            }
            public final static java.lang.String Status(HttpServletRequest req, Throwable any){
                Object value = Attribute.Value.Status(req);
                if (null != value)
                    return value.toString();
                else if (null != any)
                    return "500";
                else
                    return "";
            }
            public final static java.lang.String Message(HttpServletRequest req){
                Object value = Attribute.Value.Message(req);
                if (null != value)
                    return value.toString();
                else
                    return "";
            }
            public final static java.lang.String Message(HttpServletRequest req, Throwable any){
                Object value = Attribute.Value.Message(req);
                if (null != value)
                    return value.toString();
                else if (null != any)
                    return any.getMessage();
                else
                    return "";
            }
            public final static java.lang.String Type(HttpServletRequest req){
                Object value = Attribute.Value.Type(req);
                if (null != value)
                    return value.toString();
                else
                    return "";
            }
            public final static java.lang.String Type(HttpServletRequest req, Throwable any){
                Object value = Attribute.Value.Type(req);
                if (null != value)
                    return value.toString();
                else if (null != any)
                    return any.getClass().getName();
                else
                    return "";
            }
            public final static java.lang.String URI(HttpServletRequest req){
                Object value = Attribute.Value.URI(req);
                if (null != value)
                    return value.toString();
                else
                    return "";
            }
            public final static java.lang.String URI(HttpServletRequest req, Throwable any){
                Object value = Attribute.Value.URI(req);
                if (null != value)
                    return value.toString();
                else
                    return "";
            }
        }
    }
}
