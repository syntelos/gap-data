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

import oso.data.Person;

import hapax.TemplateDictionary;
import hapax.TemplateException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

/**
 * Using the term "logon" as generic of its states "logged- in" or
 * "logged- out".  When principal is null, the state is "logged- out".
 * 
 * <h3>Templates</h3>
 * 
 * Defines the following template dictionary properties
 * 
 * <pre>
 * logon_url
 * logon_class
 * logon_identifier
 * </pre>
 * 
 * Manages the visibility of the following template sections
 * 
 * <pre>
 * with_logon
 * without_logon
 * with_site_admin
 * without_site_admin
 * </pre>
 * 
 * @author jdp
 */
public final class Logon
    extends Object
{
    private final static ThreadLocal<Logon> LTL = new ThreadLocal<Logon>();

    /**
     * Package protected
     */
    static Logon Enter(Logon logon){
        if (null != LTL.get())
            throw new java.security.AccessControlException("Incorrect reentry.");
        else {
            LTL.set(logon);
            return logon;
        }
    }
    static void Exit(){
        LTL.set(null);
    }
    /**
     * @return User of the current thread.
     */
    public static Logon Get(){
        return LTL.get();
    }
    public static boolean IsPublic(){
        return true;
    }
    public static boolean IsMember(){
        return LTL.get().isLoggedIn();
    }
    public static boolean IsPartner(){

        return false;//(TODO)
    }
    public static boolean IsAdmin(){
        return LTL.get().serviceAdmin;
    }

    /**
     * Subclass usage should be qualified as
     * <code>"Logon.Log"</code> or
     * <code>"gap.service.Logon.Log"</code>.
     */
    protected final static Logger Log = Logger.getLogger("Logon");


    public final Principal principal;
    public final UserService service;
    public final User serviceUser;
    public final boolean serviceAdmin;
    public final String serviceLogon, requestUrl;
    public final TemplateDictionary dict;

    private Person person;
    private String loginUrl, logoutUrl;


    public Logon(Principal principal, String uri, TemplateDictionary dict, UserService service){
        super();
        this.principal = principal;
        this.requestUrl = uri;
        this.dict = dict;
        this.service = service;

        if (null == principal){
            String loginUrl = service.createLoginURL(uri);
            this.loginUrl = loginUrl;
            this.serviceUser = null;
            this.serviceAdmin = false;
            this.serviceLogon = null;


            dict.showSection("without_site_admin");

            TemplateDictionary logon = dict.showSection("logon").get(0);
            logon.putVariable("logon_url",loginUrl);
            logon.putVariable("logon_url_text","Sign-in");
            logon.putVariable("logon_class","logon off");

            logon.showSection("without_login");
        }
        else {
            String logoutUrl = service.createLogoutURL(uri);
            this.logoutUrl = logoutUrl;

            dict.putVariable("logon_url",logoutUrl);

            User guser = service.getCurrentUser();
            this.serviceUser = guser;

            this.serviceAdmin = service.isUserAdmin();

            String email = guser.getEmail();
            this.serviceLogon = email;

            dict.putVariable("logon_url_text",email);
            dict.putVariable("logon_identifier",email);


            if (this.serviceAdmin){
                dict.showSection("with_site_admin");
            }
            else {
                dict.showSection("without_site_admin");
            }

            TemplateDictionary logon = dict.showSection("logon").get(0);

            logon.putVariable("logon_class","logon on");

            logon.showSection("with_login");
            try {
                /*
                 * Ensure that every login enters the system, so that
                 * other users' processes can work with this user.
                 */
                this.person = Person.GetCreate(email);
            }
            catch (Exception any){
                LogRecord rec = new LogRecord(Level.SEVERE,"error");
                rec.setThrown(any);
                Log.log(rec);
            }
        }
    }


    public boolean hasPerson(){
        return (null != this.person);
    }
    public Person getPerson(){
        return this.person;
    }
    public String getLogonId(){
        return this.serviceLogon;
    }
    public String getUserId(){
        Person person = this.person;
        if (null != person)
            return person.getId();
        else 
            return null;
    }
    public boolean isLoggedIn(){
        return (null != this.principal);
    }
    public boolean isLoggedOut(){
        return (null == this.principal);
    }
    public String getLoginURL(){
        String loginUrl = this.loginUrl;
        if (null == loginUrl){
            loginUrl = this.service.createLoginURL(this.requestUrl);
            this.loginUrl = loginUrl;
        }
        return loginUrl;
    }
    public String getLogoutURL(){
        String logoutUrl = this.logoutUrl;
        if (null == logoutUrl){
            logoutUrl = this.service.createLogoutURL(this.requestUrl);
            this.logoutUrl = logoutUrl;
        }
        return logoutUrl;
    }
}
