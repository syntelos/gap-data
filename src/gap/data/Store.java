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

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import javax.servlet.ServletContext;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public final class Store
    extends java.lang.Object
{

    /**
     * BigTable
     */
    protected final static class P 
        extends java.lang.Object
    {
        private final static ThreadLocal<DatastoreService> PTL = new ThreadLocal<DatastoreService>();


        protected static void Enter(){
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
            DatastoreService ds = PTL.get();
            if (null != ds)
                PTL.set(null);
        }
        protected static DatastoreService Get(){
            return PTL.get();
        }
        protected static BigTable Get(Key key){
            try {
                Entity entity = Get().get(key);
                if (null != entity){
                    BigTable gdo = BigTable.From(entity);
                    gdo.setFromDatastore();
                    gdo.onread();
                    return gdo;
                }
                else
                    return null;
            }
            catch (com.google.appengine.api.datastore.EntityNotFoundException notFound){
                return null;
            }
        }
        protected static List<BigTable> Get(java.lang.Iterable<Key> keys){
            List<BigTable> list = new java.util.ArrayList<BigTable>();
            Map <Key,Entity> map = Get().get(keys);
            for (Entity entity : map.values()){
                BigTable gdo = BigTable.From(entity);
                gdo.setFromDatastore();
                gdo.onread();
                list.add(gdo);
            }
            return list;
        }
        protected static BigTable Put(BigTable table){
            Entity entity = table.fillToDatastoreEntity();
            Key key = Get().put(entity);
            return table.setFromDatastore(key);
        }
        protected static void Delete(Key key){
            if (null != key)
                Get().delete(new Key[]{key});
        }
        protected static BigTable Query1(Query query){
            DatastoreService ds = Get();
            PreparedQuery stmt = ds.prepare(query);

            Entity entity = stmt.asSingleEntity();/* This expression requires that a key having name
                                                   * but not id resolve to one id.
                                                   * 
                                                   * Key.name identity code needs to be aware that
                                                   * the datastore key can have multiple key.id's
                                                   * for a key.name.
                                                   */
            if (null == entity)
                return null;
            else {
                BigTable gdo = BigTable.From(entity);
                gdo.setFromDatastore();
                gdo.onread();
                return gdo;
            }
        }
        protected static List<BigTable> QueryN(Query query, FetchOptions page){
            DatastoreService ds = Get();
            PreparedQuery stmt = ds.prepare(query);

            Iterable<Entity> it;
            if (null != page)
                it = stmt.asIterable(page);
            else
                it = stmt.asIterable();

            List<BigTable> list = new java.util.ArrayList<BigTable>();

            for (Entity entity : it){
                BigTable gdo = BigTable.From(entity);
                gdo.setFromDatastore();
                gdo.onread();
                list.add(gdo);
            }
            return list;
        }
        protected static Key QueryKey1(Query query){
            query.setKeysOnly();

            DatastoreService ds = Get();
            PreparedQuery stmt = ds.prepare(query);

            Entity entity = stmt.asSingleEntity();/* This expression requires that a key having name
                                                   * but not id resolve to one id.
                                                   * 
                                                   * Key.name identity code needs to be aware that
                                                   * the datastore key can have multiple key.id's
                                                   * for a key.name.
                                                   */
            if (null == entity)
                return null;
            else 
                return entity.getKey();
        }
        protected static List<Key> QueryKeyN(Query query, FetchOptions page){
            query.setKeysOnly();

            DatastoreService ds = Get();
            PreparedQuery stmt = ds.prepare(query);

            Iterable<Entity> it;
            if (null != page)
                it = stmt.asIterable(page);
            else
                it = stmt.asIterable();

            List<Key> list = new java.util.ArrayList<Key>();

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


        protected static void Enter(){
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
            MemcacheService ds = PTL.get();
            if (null != ds)
                PTL.set(null);
        }
        protected static MemcacheService Get(){
            return PTL.get();
        }
        protected static BigTable Get(Key key){
            String ck = BigTable.ToString(key);
            MemcacheService mc = Store.C.Get();
            try {
                BigTable gdo = (BigTable)mc.get(ck);
                if (null != gdo){
                    gdo.setFromMemcache();
                    gdo.onread();
                    return gdo;
                }
                else 
                    return null;
            }
            catch (com.google.appengine.api.memcache.InvalidValueException serialization){
                mc.delete(key);
                return null;
            }
        }
        protected static List<BigTable> Get(java.lang.Iterable<Key> keys){
            MemcacheService mc = Store.C.Get();
            DatastoreService ds = Store.P.Get();
            List<BigTable> list = new java.util.ArrayList<BigTable>();
            for (Key key : keys){
                
                String ck = BigTable.ToString(key);

                BigTable gdo = null;
                try {
                    gdo = (BigTable)mc.get(ck);
                }
                catch (com.google.appengine.api.memcache.InvalidValueException serialization){

                    mc.delete(ck);
                }

                if (null != gdo){
                    gdo.setFromMemcache();
                    gdo.onread();
                    list.add(gdo);
                }
                else {
                    try {
                        gdo = BigTable.From(ds.get(key));
                        if (null != gdo){
                            gdo.setFromDatastore();
                            gdo.onread();
                            list.add(gdo);
                            mc.put(key,gdo);
                        }
                    }
                    catch (com.google.appengine.api.datastore.EntityNotFoundException exc){
                    }
                }
            }
            return list;
        }
        protected static BigTable Put(Key key, BigTable table){

            String ck = BigTable.ToString(key);

            Get().put(ck,table);

            return table;
        }
        protected static void Delete(Key key){
            if (null != key){

                String ck = BigTable.ToString(key);

                Get().delete(ck);
            }
        }
    }


    /**
     * Servlet enter
     */
    public static void Enter(){
        P.Enter();
        C.Enter();
    }
    /**
     * Servlet exit
     */
    public static void Exit(){
        P.Exit();
        C.Exit();
    }
    public static BigTable Get(Key key){
        if (key.isComplete()){
            BigTable value = C.Get(key);
            if (null != value)
                return value;
            else
                return P.Get(key);
        }
        else
            throw new IllegalArgumentException("Incomplete key '"+key+"'.");
    }
    public static List<BigTable> Get(Iterable<Key> keys){

        return C.Get(keys);
    }
    public static BigTable Query1(Query q){
        return Store.P.Query1(q);
    }
    public static List<BigTable> QueryN(Query q, FetchOptions p){
        return Store.P.QueryN(q,p);
    }
    public static Key QueryKey1(Query q){
        return Store.P.QueryKey1(q);
    }
    public static List<Key> QueryKeyN(Query q, FetchOptions p){
        return Store.P.QueryKeyN(q,p);
    }
    public static BigTable Put(BigTable table){

        if (table instanceof LastModified){
            ((LastModified)table).setLastModified(System.currentTimeMillis());
        }

        table.onwrite();

        table = P.Put(table);

        Key key = table.getClassFieldKeyValue();

        C.Put(key,table);

        return table;
    }
    public static void Delete(Key key){
        C.Delete(key);
        P.Delete(key);
    }
    public static void Clean(Key key){
        C.Delete(key);
    }
    public static void DeleteCollection(Query query){

        query.setKeysOnly();

        DatastoreService ds = Store.P.Get();
        MemcacheService mc = Store.C.Get();

        PreparedQuery stmt = ds.prepare(query);

        Iterable<Entity> list = stmt.asIterable();

        for (Entity ent : list){
            Key key = ent.getKey();
            {
                String ck = BigTable.ToString(key);
                mc.delete(ck);
            }
            ds.delete(key);
        }
    }

    protected Store(){
        super();
    }

}
