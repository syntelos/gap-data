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


import gap.*;
import gap.data.*;
import gap.util.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.labs.taskqueue.*;

import java.util.Date;

/**
 * Tools layer under the task processes {@link CommandServlet} and
 * {@link RetweetServlet}.
 * 
 * For perpetual tasking, each task creates a new task of the same
 * kind.  The queue execution rate is managed for the performance of
 * each queue.
 *
 * @see CommandServlet
 */
public abstract class TaskServlet
    extends gap.service.Servlet
{
    public final static com.google.appengine.api.datastore.Transaction TxNil = null;
    /**
     * Get queue
     */
    public final static Queue GetQueueFavorites(){
        return QueueFactory.getQueue("favorites");
    }
    public final static Queue GetQueueCommands(){
        return QueueFactory.getQueue("commands");
    }
    /**
     * Add task to queue
     */
    public final static TaskHandle UpdateQueueFavorites(){
        Queue queue = GetQueueCommands();
        TaskOptions topt = TaskOptions.Builder.url("/retweet");
        topt = topt.method(TaskOptions.Method.POST);
        return queue.add(TxNil,topt);
    }
    public final static TaskHandle UpdateQueueCommands(){
        Queue queue = GetQueueCommands();
        TaskOptions topt = TaskOptions.Builder.url("/command");
        topt = topt.method(TaskOptions.Method.POST);
        return queue.add(TxNil,topt);
    }
    private final static Page SourcesPage = new Page(0,99);
    private final static Query SourcesQuery = Source.CreateQueryFor();
    /**
     * List sources
     */
    public final static BigTableIterator<Source> Sources(){
        return Source.QueryN(SourcesQuery,SourcesPage);
    }

    public final static Source SourceFor(lxl.Map<String,Source> cache, String twid){
        Source source = cache.get(twid);
        if (null == source){
            source = Source.ForLongTwitterId(twid);
            if (null != source)
                cache.put(twid,source);
        }
        return source;
    }


    public TaskServlet() {
        super();
    }

}
