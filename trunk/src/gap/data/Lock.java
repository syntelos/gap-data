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
 * A memcache based shared system lock.  
 * 
 * Correct usage is to enter the lock immediately preceding the top of
 * a try block, and then to exit the lock in the finally clause at the
 * tail of the try block.  Of course, the try block contains the
 * critical (protected) region.
 * 
 * The lock expires in thirty seconds, because all appengine
 * procedures timeout in thirty seconds.
 * 
 * @author jdp
 */
public final class Lock 
    extends Object
    implements java.io.Serializable
{
    private final static long serialVersionUID = 1L;

    private final static MemcacheService.SetPolicy CAS = MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT;
    private final static Expiration EXP = Expiration.byDeltaSeconds(30);
    private final static String ASSERT = "locked";


    public final Key key;

    public final String string;

    public final int hashCode;


    public Lock(Key key){
        super();
        if (null != key){
            this.key = key;
            this.string = "lock:///"+gap.Strings.KeyToString(this.key);
            this.hashCode = gap.data.Hash.Djb32(this.string);
        }
        else
            throw new IllegalArgumentException();
    }


    public boolean enter(long expiration)
        throws java.lang.InterruptedException
    {
        long end = (System.currentTimeMillis()+expiration);
        do {
            if (this.enter())
                return true;
            else {
                long waitfor = (expiration / 3);
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
        if (null != mc)
            return mc.put(this.string,ASSERT,EXP,CAS);
        else
            throw new IllegalStateException("Memcache service not available.");
    }
    public void exit(){
        MemcacheService mc = Store.C.Get();
        if (null != mc)
            mc.delete(this.string);
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
