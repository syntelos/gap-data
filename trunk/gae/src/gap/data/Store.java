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
 * 
 */
public final class Store
    extends java.lang.Object
{

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
        protected static BigTable Get(Key key){
            try {
                Entity entity = Get().get(key);
                if (null != entity){
                    BigTable gdo = BigTable.From(entity);
                    if (gdo instanceof AdminReadWrite){

                        if (!Logon.IsAdmin())
                            throw new AdminAccessException(gdo.getClassKind());
                    }
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
        protected static gap.util.ArrayList<BigTable> Get(java.lang.Iterable<Key> keys){
            gap.util.ArrayList<BigTable> list = new gap.util.ArrayList<BigTable>();
            java.util.Map <Key,Entity> map = Get().get(keys);
            for (Entity entity : map.values()){
                BigTable gdo = BigTable.From(entity);
                if (gdo instanceof AdminReadWrite){

                    if (!Logon.IsAdmin())
                        throw new AdminAccessException(gdo.getClassKind());
                }
                gdo.setFromDatastore();
                gdo.onread();
                list.add(gdo);
            }
            return list;
        }
        protected static BigTable Put(BigTable table){
            if (table instanceof AdminReadWrite){

                if (!Logon.IsAdmin())
                    throw new AdminAccessException(table.getClassKind());
            }
            Entity entity = table.fillToDatastoreEntity();
            Key key = Get().put(entity);
            return table.setFromDatastore(key);
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
        protected static BigTable Query1(Query query){
            DatastoreService ds = Get();
            try {
                PreparedQuery stmt = ds.prepare(query);
                Entity entity = stmt.asSingleEntity();
                if (null == entity)
                    return null;
                else {
                    BigTable gdo = BigTable.From(entity);
                    if (gdo instanceof AdminReadWrite){

                        if (!Logon.IsAdmin())
                            throw new AdminAccessException(gdo.getClassKind());
                    }
                    gdo.setFromDatastore();
                    gdo.onread();
                    return gdo;
                }
            }
            catch (com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException exc){
                query.setKeysOnly();
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
                try {
                    Entity entity = ds.get(highKey);
                    if (null == entity)
                        return null;
                    else {
                        BigTable gdo = BigTable.From(entity);
                        if (gdo instanceof AdminReadWrite){

                            if (!Logon.IsAdmin())
                                throw new AdminAccessException(gdo.getClassKind());
                        }
                        gdo.setFromDatastore();
                        gdo.onread();
                        return gdo;
                    }
                }
                catch (com.google.appengine.api.datastore.EntityNotFoundException err){
                    return null;
                }
            }
        }
        protected static BigTableIterator QueryN(Query query, Page page){
            DatastoreService ds = Get();
            PreparedQuery stmt = ds.prepare(query);

            return new BigTableIterator(stmt,page);
        }
        protected static Key QueryKey1(Query query){
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
        protected static List.Primitive<Key> QueryKeyN(Query query, Page page){
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
        protected static BigTable Get(Key key){
            try {
                String ck = BigTable.ToString(key);
                MemcacheService mc = Store.C.Get();
                try {
                    BigTable gdo = (BigTable)mc.get(ck);
                    if (null != gdo){
                        if (gdo instanceof AdminReadWrite){

                            if (!Logon.IsAdmin())
                                throw new AdminAccessException(gdo.getClassKind());
                        }
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
            catch (IllegalArgumentException incompleteKey){

                return null;
            }
        }
        protected static gap.util.ArrayList<BigTable> Get(java.lang.Iterable<Key> keys){
            MemcacheService mc = Store.C.Get();
            DatastoreService ds = Store.P.Get();
            gap.util.ArrayList<BigTable> list = new gap.util.ArrayList<BigTable>();
            for (Key key : keys){

                BigTable gdo = null;

                try {                
                    String ck = BigTable.ToString(key);
                    try {
                        gdo = (BigTable)mc.get(ck);
                    }
                    catch (com.google.appengine.api.memcache.InvalidValueException serialization){

                        mc.delete(ck);
                    }
                }
                catch (IllegalArgumentException incompleteKey){
                }

                if (null != gdo){
                    if (gdo instanceof AdminReadWrite){

                        if (!Logon.IsAdmin())
                            throw new AdminAccessException(gdo.getClassKind());
                    }
                    gdo.setFromMemcache();
                    gdo.onread();
                    list.add(gdo);
                }
                else {
                    try {
                        gdo = BigTable.From(ds.get(key));
                        if (null != gdo){
                            if (gdo instanceof AdminReadWrite){

                                if (!Logon.IsAdmin())
                                    throw new AdminAccessException(gdo.getClassKind());
                            }
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

            if (table instanceof AdminReadWrite){

                if (!Logon.IsAdmin())
                    throw new AdminAccessException(table.getClassKind());
            }
            try {
                String ck = BigTable.ToString(key);

                Get().put(ck,table);
            }
            catch (IllegalArgumentException incompleteKey){
            }
            return table;
        }
        protected static void Delete(Key key){
            if (null != key){
                if (BigTable.IsAdmin(key.getKind())){

                    if (!Logon.IsAdmin())
                        throw new AdminAccessException(key.getKind());
                }
                try {
                    String ck = BigTable.ToString(key);

                    Get().delete(ck);
                }
                catch (IllegalArgumentException incompleteKey){
                }
            }
        }
    }


    /**
     * Servlet enter
     */
    public static void Enter(String ns){
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
    public static BigTable Get(Key key){
        if (key.isComplete()){
            BigTable table = C.Get(key);
            if (null != table)
                return table;
            else {
                table = P.Get(key);
                if (null != table){
                    /*
                     * Optimistic cache
                     */
                    return C.Put(table.getKey(),table);
                }
                else
                    return null;
            }
        }
        else
            throw new IllegalArgumentException("Incomplete key '"+key+"'.");
    }
    public static gap.util.ArrayList<BigTable> Get(Iterable<Key> keys){

        return C.Get(keys);
    }
    public static BigTable Query1(Query q){
        return Store.P.Query1(q);
    }
    public static BigTableIterator QueryN(Query q, Page p){
        return Store.P.QueryN(q,p);
    }
    public static Key QueryKey1(Query q){
        return Store.P.QueryKey1(q);
    }
    public static List.Primitive<Key> QueryKeyN(Query q, Page p){
        return Store.P.QueryKeyN(q,p);
    }
    public static BigTable Put(BigTable table){

        if (table instanceof LastModified){
            ((LastModified)table).setLastModified(System.currentTimeMillis());
        }

        table.onwrite();

        table = P.Put(table);

        Key key = table.getKey();

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
    public static void DeleteCollection(Kind access, Query query){
        if (BigTable.IsAdmin(access)){

            if (!Logon.IsAdmin())
                throw new AdminAccessException(access);
        }
        query.setKeysOnly();

        DatastoreService ds = Store.P.Get();
        MemcacheService mc = Store.C.Get();

        PreparedQuery stmt = ds.prepare(query);

        Iterable<Entity> list = stmt.asIterable();

        for (Entity ent : list){
            Key key = ent.getKey();
            try {
                String ck = BigTable.ToString(key);
                mc.delete(ck);
            }
            catch (IllegalArgumentException incompleteKey){
            }
            ds.delete(key);
        }
    }

    protected Store(){
        super();
    }

}
