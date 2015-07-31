# System #

The set of [App Engine](http://code.google.com/p/appengine) application instances on one application Memcache and Datastore.

# Lock #

The [Gap Data Lock](http://code.google.com/p/gap-data/source/browse/trunk/src/gap/data/Lock.java) is a system level thread mutex. Appengine will kill off requests into overdue locks.
```
 Lock lock = new Lock("statistic-live-count");
 if (lock.enter(999)){
   try {
    Long count = Statistic.Live.Count();
    this.write(count.toString());
   }
   finally {
    lock.exit();
   }
 }
 else 
  this.error("Lock unavailable");
```
