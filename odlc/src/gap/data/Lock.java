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
package gap.data;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.Expiration;

/**
 * A memcache based shared system lock.  The lock may employ a
 * datastore key for an association with an instance, or any string
 * for a GUID.  The lock, like the GUID, is unique across all
 * processes running the application within the global appengine
 * network system.
 * 
 * Correct usage is to enter the lock immediately preceding the top of
 * a try block, and then to exit the lock in the finally clause at the
 * tail of the try block.  Of course, the try block contains the
 * critical (protected) region.
 * 
 * The operation of this lock employs the declared atomicity of the
 * increment API in the memcache service.
 * 
 * @author jdp
 */
public final class Lock 
    extends Object
    implements java.io.Serializable,
               gap.util.Millis
{
    private final static long serialVersionUID = 1L;

    private final static MemcacheService.SetPolicy IFNOT = MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT;
    private final static Expiration EXP = Expiration.byDeltaMillis( (int)(3*Hours));
    private final static Long COND = 0L;
    private final static Long INC = 1L;
    private final static Long DEC = -1L;



    public final String string;

    public final int hashCode;

    private transient boolean entered;


    public Lock(Key key){
        this(gap.Strings.KeyToString(key));
    }
    public Lock(String guid){
        if (null != guid){
            this.string = "lock:///"+guid;
            this.hashCode = gap.data.Hash.Djb32(this.string);
        }
        else
            throw new IllegalArgumentException();
    }


    public boolean enter(long timeout)
        throws java.lang.InterruptedException
    {
        long end = (System.currentTimeMillis()+timeout);
        do {
            if (this.enter())
                return true;
            else {
                long waitfor = (timeout / 3);
                if (0 < waitfor)
                    Thread.sleep(waitfor);
                else
                    Thread.sleep(33);
            }
        }
        while (System.currentTimeMillis() < end);
        return false;
    }
    public boolean enter(){
        MemcacheService mc = Store.C.Get();
        if (null != mc){

            Long entry = mc.increment(this.string,INC);

            if (null == entry){

                mc.put(this.string,COND,EXP,IFNOT);

                entry = mc.increment(this.string,INC);
            }
            return (this.entered = (INC.longValue() == entry.longValue()));
        }
        else
            throw new IllegalStateException("Memcache service not available.");
    }
    public void exit(){
        MemcacheService mc = Store.C.Get();
        if (null != mc){
            if (this.entered)
                mc.increment(this.string,DEC);
            else
                throw new IllegalStateException("Lock not entered.");
        }
        else
            throw new IllegalStateException("Memcache service not available.");
    }
    public int hashCode(){
        return this.hashCode;
    }
    public String toString(){
        return this.string;
    }
    public boolean equals(Object that){
        if (this == that)
            return true;
        else if (null == that)
            return false;
        else
            return this.string.equals(that.toString());
    }
}
