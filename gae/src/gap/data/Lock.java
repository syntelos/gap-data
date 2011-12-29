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
 * An appengine application cloud system mutex lock.  The design
 * depends on the uniqueness of a memcache increment subject over the
 * appengine application memcache cloud.
 * 
 * The lock may employ a datastore key for an association with an
 * instance, or any string for a GUID.
 * 
 * A try block contains a critical or protected region.  Enter the
 * lock immediately preceding the top of the try block, and then exit
 * the lock in the finally clause.
 * 
 * The design and implementation of this lock is based on the memcache
 * increment operation.  While the memcache cloud is consistent over
 * the application runtime cloud, this lock is intended to protect a
 * region of code in terms of mutually exclusive entry.
 * 
 * The memcache increment subject is incremented to value one when the
 * lock is entered, and decremented to value zero when the lock exits.
 * 
 * @author jdp
 */
public final class Lock 
    extends Object
    implements java.io.Serializable,
               gap.util.Millis
{
    /**
     * Version two changes key format from version one.
     */
    private final static long serialVersionUID = 2L;
    /**
     */
    private final static MemcacheService.SetPolicy IFNOT = MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT;
    /**
     * Lifespan of Lock object in Memcache is protecting some minutes,
     * so it's more than some minutes.
     */
    private final static Expiration EXP = Expiration.byDeltaMillis( (int)(1*Hours));
    /**
     * Entry timeout is request timeout (30 seconds) minus two seconds
     * (normal request processing time).
     */
    private final static long ENTER = ((30-2)*Seconds);
    /**
     */
    private final static long TOF = 33L;
    /**
     */
    private final static Long COND = 0L;
    private final static Long INC = 1L;
    private final static Long DEC = -1L;

    /**
     * Thrown by enter on thread interrupt.  Caller should return
     * immediately from the current thread.
     */
    public final static class InterruptedException
        extends java.lang.RuntimeException
    {
        public InterruptedException(java.lang.InterruptedException exc){
            super(exc);
        }
    }



    public final String string;

    public final int hashCode;

    private transient Long entry;



    public Lock(Key key){
        this(BigTable.ToString(key));
    }
    public Lock(String guid){
        if (null != guid && 0 < guid.length()){
            this.string = "lock://"+guid;
            this.hashCode = gap.data.Hash.Djb32(this.string);
        }
        else
            throw new IllegalArgumentException();
    }


    /**
     * Enter using a default lock request timeout.
     * 
     * Default lock request timeout is request timeout (30 seconds)
     * minus two seconds (normal request processing time).
     * 
     * @return Success gaining mutex access
     */
    public boolean enter(){
        try {
            return this.enter(ENTER);
        }
        catch (java.lang.InterruptedException exc){

            throw new Lock.InterruptedException(exc);
        }
    }
    public boolean enter(long timeout)
        throws java.lang.InterruptedException
    {
        final long end = (System.currentTimeMillis()+timeout);
        final MemcacheService mc = Store.C.Get();
        do {
            if (this.test(mc))
                return true;
            else {
                long waitfor = (timeout / TOF);
                if (0 < waitfor)
                    Thread.sleep(waitfor);
                else
                    Thread.sleep(TOF);
            }
        }
        while (System.currentTimeMillis() < end);
        return false;
    }
    public void exit(){
        MemcacheService mc = Store.C.Get();
        if (null != mc)
            this.exit(mc);
        else
            throw new IllegalStateException("Memcache service not available.");
    }
    private void exit(MemcacheService mc){
        if (null != this.entry){
            try {
                mc.increment(this.string,DEC);
            }
            finally {
                this.entry = null;
            }
        }
    }
    private boolean entered(){
        Long entry = this.entry;
        return (null != entry && INC == entry);
    }
    private boolean test(MemcacheService mc){
        if (null != mc){

            this.entry = mc.increment(this.string,INC);

            if (null == this.entry){

                mc.put(this.string,COND,EXP,IFNOT);

                this.entry = mc.increment(this.string,INC);
            }
            if (this.entered())
                return true;
            else {
                this.exit(mc);
                return false;
            }
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
