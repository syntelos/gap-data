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

import gap.data.List;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.oauth.OAuthServiceFailureException;

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
 * 
 * @author jdp
 */
public final class Logon
    extends gap.hapax.AbstractData
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
        LTL.remove();
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
        return LTL.get().serviceMember;
    }
    public static boolean IsAdmin(){
        return LTL.get().serviceAdmin;
    }

    /**
     * Subclass usage should be qualified as
     * <code>"Logon.Log"</code> or
     * <code>"gap.service.Logon.Log"</code>.
     */
    protected final static Logger Log = Logger.getLogger(Logon.class.getName());

    public static enum Field {
        ns, person, loginUrl, logoutUrl, logon, admin, member;

        public static Field For(String name){
            try {
                return Field.valueOf(name);
            }
            catch (IllegalArgumentException exc){
                return null;
            }
        }
    }

    public final static OAuthService OAuth = OAuthServiceFactory.getOAuthService();


    public final String ns;
    public final Principal principal;
    public final UserService service;
    public final User serviceUser;
    public final boolean serviceAdmin, serviceMember, serviceOAuth;
    public final String serviceLogon, requestUrl, oauthConsumer;

    private String loginUrl, logoutUrl;


    public Logon(String ns, Principal principal, String uri, UserService service){
        super();
        this.ns = ns;
        this.principal = principal;
        this.requestUrl = uri;
        this.service = service;
        /*
         * Sublimate authentication for application programming
         */
        User guser = null;
        boolean isAdmin = false, isOAuth = false;
        String oauthConsumer;

        try {
            oauthConsumer = OAuth.getOAuthConsumerKey();
            guser = OAuth.getCurrentUser();
            isAdmin = OAuth.isUserAdmin();
            isOAuth = true;
        }
        catch (OAuthRequestException exc){

            oauthConsumer = null;
        }
        catch (OAuthServiceFailureException exc){

            oauthConsumer = null;
        }
        this.oauthConsumer = oauthConsumer;

        this.serviceOAuth = isOAuth;
        /*
         */
        if (isOAuth){

            Logon.Log.log(Level.INFO,String.format("OAuth '%s'",oauthConsumer));
        }
        else {
            try {
                guser = service.getCurrentUser();
                if (null != guser)
                    isAdmin = service.isUserAdmin();
            }
            catch (IllegalStateException nologin){
            }
        }
        /*
         */
        if (null == guser){
            this.loginUrl = service.createLoginURL(uri);
            this.serviceUser = null;
            this.serviceAdmin = false;
            this.serviceLogon = null;
            this.serviceMember = false;
        }
        else {
            this.logoutUrl = service.createLogoutURL(uri);
            this.serviceAdmin = isAdmin;
            this.serviceMember = true;
            this.serviceUser = guser;

            final String email = guser.getEmail();

            this.serviceLogon = email;

            Logon.Log.log(Level.INFO,String.format("Logon '%s'",email));
        }
    }


    public boolean hasNamespace(){
        return (null != this.ns);
    }
    public String getNamespace(){
        return this.ns;
    }
    public String getLogonId(){
        return this.serviceLogon;
    }
    public String getUserEmail(){
        User user = this.serviceUser;
        if (null != user)
            return user.getEmail();
        else 
            return null;
    }
    public String getUserDomain(){
        User user = this.serviceUser;
        if (null != user)
            return user.getAuthDomain();
        else 
            return null;
    }
    public String getUserNick(){
        User user = this.serviceUser;
        if (null != user)
            return user.getNickname();
        else 
            return null;
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
