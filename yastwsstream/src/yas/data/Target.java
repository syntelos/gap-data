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
package yas.data;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.urlfetch.HTTPHeader;

/**
 * Generated once (user) bean.
 * This source file will not be overwritten unless deleted,
 * so it can be edited for extensions.
 *
 * @see TargetData
 */
public final class Target
    extends TargetData
{

    private final static Query TargetsQuery = Target.CreateQueryFor();
    /**
     * Retrieve target
     */
    public final static Target Instance(){
        return Target.Query1(TargetsQuery);
    }


    private volatile transient String referer;
    private volatile transient HTTPHeader authBasic;


    public Target() {
        super();
    }
    public Target(String twitterId) {
        super( twitterId);
    }


    public String getReferer(){
        String referer = this.referer;
        if (null == referer){
            String twitterId = this.getTwitterId();
            if (null != twitterId){
                referer = "http://twitter.com/"+twitterId;
                this.referer = referer;
            }
        }
        return referer;
    }
    public HTTPHeader getAuthenticationBasic(){
        HTTPHeader authBasic = this.authBasic;
        if (null == authBasic){
            String uid = this.getTwitterId();
            String pas = this.getTwitterPass();
            if (null != uid && null != pas){
                String code = Encode(uid+':'+pas);
                authBasic = new HTTPHeader("Authentication","Basic "+code);
                this.authBasic = authBasic;
            }
            else
                throw new IllegalStateException("Target not configured");
        }
        return authBasic;
    }
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

    private final static String Encode(String string){
        try {
            byte[] in = string.getBytes("US-ASCII");
            byte[] out = alto.io.u.B64.encode(in);
            return new String(out,"US-ASCII");
        }
        catch (java.io.IOException exc){
            throw new InternalError();
        }
    }
}
