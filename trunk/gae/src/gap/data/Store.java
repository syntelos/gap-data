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

import gap.service.Logon;
import gap.util.Page;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import javax.servlet.ServletContext;

import java.util.Iterator;

/**
 * Memcache - Datastore for Entities and Classes
 */
public final class Store
    extends java.lang.Object
{
    /**
     * Namespace
     */
    protected final static class N
        extends java.lang.Object
    {
        private final static ThreadLocal<String> NTL = new ThreadLocal<String>();

        protected static void Enter(String ns){

            if (null == ns){

                if (null != NTL.get()){

                    NTL.set(null);
                }   
            }
            else if (null == NTL.get()){

                NTL.set(ns);
            }
            /*
             * Valid reentry is possible in some error handling schemes
             */
        }
        protected static void Exit(){
            NTL.remove();
        }
        public static String Get(){
            return NTL.get();
        }
    }
    /**
     * Datastore
     */
    protected final static class P 
        extends java.lang.Object
    {
        private final static ThreadLocal<DatastoreService> PTL = new ThreadLocal<DatastoreService>();


        protected static void Enter(String ns){
            DatastoreService ds = PTL.get();
            if (null == ds){
                ds = DatastoreServiceFactory.getDatastoreService();
                PTL.set(ds);
            }
            /*
             * Valid reentry is possible in some error handling schemes
             */
        }
        protected static void Exit(){
            PTL.remove();
        }
        protected static DatastoreService Get(){
            return PTL.get();
        }
        protected static Entity Get(Key key){
            try {
                return Get().get(key);
            }
            catch (com.google.appengine.api.datastore.EntityNotFoundException notFound){
                return null;
            }
        }
        protected static Key Put(Entity entity){

            return Get().put(entity);
        }
        protected static void Delete(Key key){
            if (null != key){
                if (BigTable.IsAdmin(key.getKind())){

                    if (!Logon.IsAdmin())
                        throw new AdminAccessException(key.getKind());
                }
                Get().delete(new Key[]{key});
            }
        }
        /**
         * Defines query for keys only
         * @see BigTableIterator
         */
        protected static <T extends BigTable> BigTableIterator<T> QueryNClass(Query query, Page page){

            query.setKeysOnly();

            final PreparedQuery stmt = Get().prepare(query);

            return new BigTableIterator(stmt,page);
        }
        /**
         * Defines query for keys only
         * @see BigTableIterator
         */
        protected static <T extends BigTable> BigTableIterator<T> QueryNClass(Query query){

            query.setKeysOnly();

            final PreparedQuery stmt = Get().prepare(query);

            return new BigTableIterator(stmt);
        }
        protected static Key Query1(Query query){
            if (BigTable.IsAdmin(query.getKind())){

                if (!Logon.IsAdmin())
                    throw new AdminAccessException();
            }
            query.setKeysOnly();

            DatastoreService ds = Get();
            try {
                PreparedQuery stmt = ds.prepare(query);
                Entity entity = stmt.asSingleEntity();
                if (null == entity)
                    return null;
                else 
                    return entity.getKey();
            }
            catch (com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException exc){

                PreparedQuery stmt = ds.prepare(query);
                Key highKey = null;
                long highId = 0;
                for (Entity ent : stmt.asIterable()){
                    Key key = ent.getKey();
                    long keyId = key.getId();
                    if (keyId > highId){
                        highId = keyId;
                        highKey = key;
                    }
                }
                return highKey;
            }
        }
        protected static List.Primitive<Key> QueryN(Query query, Page page){
            if (BigTable.IsAdmin(query.getKind())){

                if (!Logon.IsAdmin())
                    throw new AdminAccessException();
            }
            query.setKeysOnly();

            DatastoreService ds = Get();
            PreparedQuery stmt = ds.prepare(query);

            Iterable<Entity> it = stmt.asIterable(page.createFetchOptions());

            List.Primitive<Key> list = new gap.util.ListPrimitiveKey(query.getKind());

            for (Entity entity : it){

                list.add(entity.getKey());
            }
            return list;
        }
        protected static List.Primitive<Key> QueryN(Query query){
            if (BigTable.IsAdmin(query.getKind())){

                if (!Logon.IsAdmin())
                    throw new AdminAccessException();
            }
            query.setKeysOnly();

            DatastoreService ds = Get();
            PreparedQuery stmt = ds.prepare(query);

            Iterable<Entity> it = stmt.asIterable();

            List.Primitive<Key> list = new gap.util.ListPrimitiveKey(query.getKind());

            for (Entity entity : it){

                list.add(entity.getKey());
            }
            return list;
        }
    }
    /**
     * Memcache
     */
    protected final static class C
        extends java.lang.Object
    {
        private final static ThreadLocal<MemcacheService> PTL = new ThreadLocal<MemcacheService>();


        protected static void Enter(String ns){
            MemcacheService ds = PTL.get();
            if (null == ds){
                ds = MemcacheServiceFactory.getMemcacheService();
                PTL.set(ds);
            }
            /*
             * Valid reentry is possible in some error handling schemes
             */
        }
        protected static void Exit(){
            PTL.remove();
        }
        protected static MemcacheService Get(){
            return PTL.get();
        }
        protected static Entity Get(Key key){
            try {
                final String ck = BigTable.ToString(key);
                final MemcacheService mc = Store.C.Get();
                try {
                    return (Entity)mc.get(ck);
                }
                catch (ClassCastException exc){
                    /*
                     * Code change conversion
                     */
                    mc.delete(key);
                    return null;
                }
                catch (com.google.appengine.api.memcache.InvalidValueException serialization){
                    mc.delete(key);
                    return null;
                }
            }
            catch (IllegalArgumentException incompleteKey){

                return null;
            }
        }
        protected static void Put(Entity entity){
            final Key key = entity.getKey();

            final String ck = BigTable.ToString(key);

            Get().put(ck,entity);
        }
        protected static void Delete(Key key){

            if (null != key){
                try {
                    final String ck = BigTable.ToString(key);

                    Get().delete(ck);
                }
                catch (IllegalArgumentException incompleteKey){
                }
            }
        }
    }


    /**
     * Test enter without namespace (default application namespace)
     */
    public static void Test(){
        Store.Enter(null);
    }
    /**
     * Servlet init enter without namespace (default application namespace)
     */
    public static void EnterInit(){
        Store.Enter(null);
    }
    /**
     * Servlet service enter with namespace
     */
    public static void Enter(String ns){
        N.Enter(ns);
        P.Enter(ns);
        C.Enter(ns);
    }
    /**
     * Servlet exit
     */
    public static void Exit(){
        P.Exit();
        C.Exit();
    }
    public static String NS(){

        return N.Get();
    }
    public static <T extends BigTable> T GetClass(Key key){
        if (key.isComplete()){
            Entity entity = C.Get(key);
            if (null != entity){
                BigTable table = BigTable.From(entity);
                if (table instanceof AdminReadWrite){

                    if (!Logon.IsAdmin())
                        throw new AdminAccessException(table.getClassKind());
                }
                table.setFromMemcache();
                table.onread();
                return (T)table;
            }
            else {
                entity = P.Get(key);
                if (null != entity){
                    /*
                     * Optimistic cache
                     */
                    C.Put(entity);

                    BigTable table = BigTable.From(entity);
                    if (table instanceof AdminReadWrite){

                        if (!Logon.IsAdmin())
                            throw new AdminAccessException(table.getClassKind());
                    }
                    table.setFromDatastore();
                    table.onread();
                    return (T)table;
                }
                else
                    return null;
            }
        }
        else
            throw new IllegalArgumentException("Incomplete key '"+key+"'.");
    }
    public static Entity Get(Key key){
        if (key.isComplete()){
            Entity entity = C.Get(key);
            if (null != entity)
                return entity;
            else 
                return P.Get(key);
        }
        else
            throw new IllegalArgumentException("Incomplete key '"+key+"'.");
    }
    public static <T extends BigTable> List<T> GetClass(Iterable<Key> keys){

        gap.util.ArrayList<T> list = new gap.util.ArrayList();

        for (Key key : keys){

            Entity entity = C.Get(key);
            if (null != entity){
                BigTable table = BigTable.From(entity);
                if (table instanceof AdminReadWrite){

                    if (!Logon.IsAdmin())
                        throw new AdminAccessException(table.getClassKind());
                }
                table.setFromMemcache();
                table.onread();
                list.add( (T)table);
            }
            else {
                entity = P.Get(key);
                if (null != entity){
                    /*
                     * Optimistic cache
                     */
                    C.Put(entity);

                    BigTable table = BigTable.From(entity);
                    if (table instanceof AdminReadWrite){

                        if (!Logon.IsAdmin())
                            throw new AdminAccessException(table.getClassKind());
                    }
                    table.setFromDatastore();
                    table.onread();
                    list.add( (T)table);
                }
            }
        }
        return list;
    }
    public static <T extends BigTable> T Query1Class(Query q){
        Key key = Store.P.Query1(q);
        if (null != key)
            return Store.GetClass(key);
        else
            return null;
    }
    public static <T extends BigTable> BigTableIterator<T> QueryNClass(Query q, Page p){

        if (null == p)
            return Store.P.QueryNClass(q);
        else
            return Store.P.QueryNClass(q,p);
    }
    public static <T extends BigTable> BigTableIterator<T> QueryNClass(Query q){

        return Store.P.QueryNClass(q);
    }
    public static Key Query1Key(Query q){

        return Store.P.Query1(q);
    }
    public static List.Primitive<Key> QueryNKey(Query q, Page p){

        if (null == p)
            return Store.P.QueryN(q);
        else
            return Store.P.QueryN(q,p);
    }
    public static List.Primitive<Key> QueryNKey(Query q){

        return Store.P.QueryN(q);
    }
    public static <T extends BigTable> T PutClass(T table){
        /*
         */
        if (table instanceof AdminReadWrite){

            if (!Logon.IsAdmin())
                throw new AdminAccessException(table.getClassKind());
        }
        else if (table instanceof LastModified){
            ((LastModified)table).setLastModified(System.currentTimeMillis());
        }

        table.onwrite();

        Key key = table.getKey();

        final Lock lock = Lock.Accept(key);
        if (lock.accept()){
            try {
                /*
                 * Current clean
                 */
                Entity entity = Store.Get(key);

                if (null == entity){

                    entity = new Entity(key);

                    table.markDirty();
                    /*
                     * Copy all
                     */
                    table.fillTo(entity);
                }
                else {
                    /*
                     * Write dirty (push changes local dirty)
                     */
                    table.fillTo(entity);
                    /*
                     * Read clean (pull changes not local dirty)
                     */
                    table.fillFrom(entity);
                }
                table.markClean();

                key = P.Put(entity);
                /*
                 * Copy write to cache
                 */
                C.Put(entity);

                table.setKey(key);

                return table;
            }
            finally {
                lock.exit();
            }
        }
        else
            throw new IllegalArgumentException("Failed to acquire write lock");
    }
    public static void Delete(Key key){
        final Lock lock = Lock.Accept(key);
        if (lock.accept()){
            try {
                C.Delete(key);
                P.Delete(key);
            }
            finally {
                lock.exit();
            }
        }
        else
            throw new IllegalArgumentException("Failed to acquire write lock");
    }
    public static void Clean(Key key){
        C.Delete(key);
    }
    public static void DeleteCollection(Kind access, Query query){
        if (BigTable.IsAdmin(access)){

            if (!Logon.IsAdmin())
                throw new AdminAccessException(access);
        }
        query.setKeysOnly();

        final DatastoreService ds = Store.P.Get();
        final MemcacheService mc = Store.C.Get();

        PreparedQuery stmt = ds.prepare(query);

        Iterable<Entity> list = stmt.asIterable();

        for (Entity ent : list){
            final Key key = ent.getKey();
            final Lock lock = new Lock(key);
            if (lock.enter()){
                try {
                    mc.delete(BigTable.ToString(key));

                    ds.delete(key);
                }
                catch (RuntimeException any){
                }
                finally {
                    lock.exit();
                }
            }
        }
    }


    private Store(){
        super();
    }
}
