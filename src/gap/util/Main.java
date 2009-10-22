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
package gap.util;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

import java.io.File;
import java.util.Map;

/**
 * Command line application context stub for appengine.
 */
public class Main
    extends Object
    implements ApiProxy.Environment 
{
    private static Main Instance;
    public final static Main Install(){
        if (null == Instance)
            Instance = new Main();
        return Instance;
    }
    public final static Main Install(File dir){
        if (null == Instance)
            Instance = new Main(dir);
        return Instance;
    }


    protected final Map<String,Object> attributes = new java.util.HashMap<String,Object>();

    protected final File dir;


    protected Main(){
        this(new File("war"));
    }
    protected Main(File dir){
        super();
        if (null != dir && dir.isDirectory()){
            this.dir = dir;
            ApiProxy.setEnvironmentForCurrentThread(this);
            ApiProxyLocalImpl proxy = new ApiProxyLocalImpl(dir){};

            ApiProxy.setDelegate(proxy);

            proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
        }
        else if (null == dir)
            throw new IllegalArgumentException();
        else 
            throw new IllegalArgumentException("Directory not found '"+dir.getPath()+"'.");
    }


    public void destroy(){
        ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
        LocalDatastoreService datastoreService = (LocalDatastoreService) proxy.getService("datastore_v3");
        datastoreService.clearProfiles();
    }
    public File getAppDirectory(){
        return this.dir;
    }
    public String getAppId() {
        return gap.Version.Target;
    }
    public String getVersionId() {
        return gap.Version.Long;
    }
    public String getEmail() {
        throw new UnsupportedOperationException();
    }
    public boolean isLoggedIn() {
        throw new UnsupportedOperationException();
    }
    public boolean isAdmin() {
        throw new UnsupportedOperationException();
    }
    public String getAuthDomain() {
        throw new UnsupportedOperationException();
    }
    public String getRequestNamespace() {
        return "";
    }
    public Map<String,Object> getAttributes() {
        return this.attributes;
    }
    public Object getAttribute(String name){
        return this.attributes.get(name);
    }
    public Main setAttribute(String name, Object value){
        this.attributes.put(name,value);
        return this;
    }
}
