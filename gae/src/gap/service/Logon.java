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

import gap.data.List;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateName;

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
        return LTL.get().isLoggedIn();
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


    public final String ns;
    public final Principal principal;
    public final UserService service;
    public final User serviceUser;
    public final boolean serviceAdmin;
    public final String serviceLogon, requestUrl;

    private Person person;
    private String loginUrl, logoutUrl;


    public Logon(String ns, Principal principal, String uri, UserService service){
        super();
        this.ns = ns;
        this.principal = principal;
        this.requestUrl = uri;
        this.service = service;
        if (null == principal){
            this.loginUrl = service.createLoginURL(uri);
            this.serviceUser = null;
            this.serviceAdmin = false;
            this.serviceLogon = null;
        }
        else {
            this.logoutUrl = service.createLogoutURL(uri);
            this.serviceAdmin = service.isUserAdmin();

            User guser = service.getCurrentUser();
            this.serviceUser = guser;

            String email = guser.getEmail();
            this.serviceLogon = email;

            try {
                /*
                 * Ensure that every login enters the system, so that
                 * other users' processes can work with this user.
                 */
                this.person = Person.GetCreateLong(email);
            }
            catch (Exception any){
                LogRecord rec = new LogRecord(Level.SEVERE,"error");
                rec.setThrown(any);
                Log.log(rec);
            }
        }
    }


    public boolean hasNamespace(){
        return (null != this.ns);
    }
    public String getNamespace(){
        return this.ns;
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
    /*
     * From gap.Request "logon" is aliased to "person".
     */
    public boolean hasVariable(TemplateName name){
	if (null != this.person){
	    if (name.has(1))
		return this.person.hasVariable(name);
	    else
		return true;
	}
	else
            return super.hasVariable(name);
    }
    public String getVariable(TemplateName name){
	if (name.is(0))
	    return this.serviceLogon;
	else if (null != this.person)
	    return this.person.getVariable(name);
	else
	    return super.getVariable(name);
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){
	if (null != this.person)
	    return this.person.getSection(name);
	else
            return super.getSection(name);
    }
}
