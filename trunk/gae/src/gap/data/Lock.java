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
 * Application cloud system mutex based on the memcache increment
 * operation.
 * 
 * <h3>Reentrant</h3>
 * 
 * <p> Each lock instance operates as if it is a unique representative
 * of the current thread (or an "accepted child").  Therefore using
 * this class requires the maintenance of this agreement between
 * instances and callers.</p>
 * 
 * <p> A static "Get" function is provided to get a reference to any
 * existing lock in the request-response thread.</p> 
 * 
 * <p> The implementation employs a dead reckoning algorithm over
 * Memcache, and depends on the simple usage pattern.  A lock instance
 * object is created or accepted and its enter/accept and exit methods
 * called. </p> 
 * 
 * <p> Other usage will not perform as desired.  For example, creating
 * a lock instance in order to test another instance in the same
 * thread would function as if in another thread.</p>
 * 
 * <h3>Lock child</h3>
 * 
 * <p> Employ the {@link Lock#Accept Lock.Accept} static function to
 * create a lock, and the {@link #accept accept} instance method to
 * enter the lock.  </p>
 * 
 * <p> This pattern implements the execution path leaf consumer of
 * locking, as for example in the {@link Store} class.  The Store
 * class is an execution path leaf as the bottom of the execution path
 * -- calling only the AppEngine DataStore and Memcache APIs (aside
 * from the data bean methods). </p>
 * 
 * <p> There are many possible applications of locking in the
 * Application and Framework outside of or external to the Store
 * class.  From calling execution branches external to Store, a Lock
 * Parent may be entered within the scope of the Store.  In order to
 * avoid deadlocks, the Store should not simply create a lock to
 * protect its algorithms from races -- rather it should accept a
 * lock.  </p>
 * 
 * <h3>Lock parent</h3>
 * 
 * <p> Employ a constructor and "enter" method pattern to create a
 * lock that must never be found in the scope of an entered lock.
 * </p>
 * 
 * <p> The enter method will throw a deadlock warning exception when
 * this requirement is violated.  Normal coverage testing will reveal
 * these application design conflicts. </p>
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

    public final static String Prefix = "lock://";

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
    /**
     * Requesting a lock within a lock
     */
    public final static class DeadlockWarning
        extends java.lang.RuntimeException
    {
        public final String existing, requesting;


        public DeadlockWarning(Lock existing, Lock requesting){
            this(existing.string,requesting.string);
        }
        private DeadlockWarning(String existing, String requesting){
            super(String.format("Requesting lock '%s' within lock '%s'",requesting,existing));
            this.existing = existing;
            this.requesting = requesting;
        }
    }


    private final static ThreadLocal<Lock> TL = new ThreadLocal<Lock>();

    private static boolean Enter(Lock lock){
        Lock test = Lock.TL.get();
        if (test == lock)
            return true;
        else if (null == test){
            Lock.TL.set(lock);
            return true;
        }
        else
            throw new DeadlockWarning(test,lock);
    }
    private static void Exit(){
        Lock.TL.remove();
    }
    /**
     * @return Current lock for this thread
     */
    public static Lock Get(){
        return Lock.TL.get();
    }
    /**
     * @return This thread has entered a lock
     */
    public static boolean In(){
        return (null != Lock.TL.get());
    }
    /**
     * @return This thread has not entered a lock
     */
    public static boolean Not(){
        return (null == Lock.TL.get());
    }
    /**
     * Get or create a lock.  An existing lock is returned when found,
     * otherwise a new lock is created from the argument key.  This
     * logic assumes that any existing lock is semantically
     * appropriate to the user (e.g. parent of argument key).
     * 
     * The returned lock should have the accept and exit methods
     * employed in place of the enter and exit methods.  The returned
     * lock will not exit a pre-existing lock.
     */
    public static Lock Accept(Key key){
        Lock accept = Lock.TL.get();
        if (null != accept)
            return new Lock(accept);
        else
            return new Lock(key);
    }


    public final String string;

    public final int hashCode;

    public final boolean child;

    private transient Long entry;



    public Lock(Key key){
        this(BigTable.ToString(key));
    }
    public Lock(String guid){
        super();
        if (null != guid && 0 < guid.length()){

            if (guid.startsWith(Lock.Prefix))

                throw new IllegalArgumentException(String.format("Unintended application '%s'",guid));
            else
                this.string = Lock.Prefix+guid;

            this.hashCode = gap.data.Hash.Djb32(this.string);
            this.child = false;
        }
        else
            throw new IllegalArgumentException();
    }
    /**
     * Create a child that will not touch the MC atom
     */
    private Lock(Lock accept){
        super();
        this.string = accept.string;
        this.hashCode = accept.hashCode;
        this.child = true;
        this.entry = accept.entry;
    }


    /**
     * Enter this lock or accept 
     * 
     * @return Success gaining mutex access
     */
    public boolean accept(){
        if (Lock.In())
            return true;
        else
            return this.enter();
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

        if (null == this.entry){
            try {
                return this.enter(ENTER);
            }
            catch (java.lang.InterruptedException exc){

                throw new Lock.InterruptedException(exc);
            }
        }
        else
            return this.entered();
    }
    public boolean enter(long timeout)
        throws java.lang.InterruptedException
    {
        if (this.child)
            return this.entered();
        else {
            final long end = (System.currentTimeMillis()+timeout);
            final MemcacheService mc = Store.C.Get();
            do {
                if (this.test(mc))
                    /*
                     */
                    return Lock.Enter(this);
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
    }
    public void exit(){
        if (!this.child){
            MemcacheService mc = Store.C.Get();
            if (null != mc)
                this.exit(mc);
        }
    }
    private void exit(MemcacheService mc){
        if (null != this.entry){
            try {
                mc.increment(this.string,DEC);
            }
            finally {
                this.entry = null;
                /*
                 */
                Lock.Exit();
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
            /*
             */
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
