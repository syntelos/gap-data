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
package gap.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 */
public final class Stats
    extends Object
{
    private final static ThreadLocal<Stats> STL = new ThreadLocal<Stats>();

    public static Stats Enter(HttpServletRequest req){
        Stats stats = STL.get();
        if (null == stats){
            stats = new Stats(req);
            STL.set(stats);
        }
        return stats;
    }
    public static Stats Get(){
        return STL.get();
    }
    public static void SetBytesDown(int count){
        Stats stats = STL.get();
        stats.setBytesDown(count);
    }
    public static void Exit(Stats stats, Logon logon){
            /*
        Person person = logon.getPerson();
        person.addStatsTimeDelta(stats.getTimeDelta());
        person.addStatsBytesUp(stats.getBytesUp());
        person.addStatsBytesDown(stats.getBytesDown());
        person.save();
             */
        STL.set(null);
    }


    public final long timeStart;

    private long timeMark;

    public final int bytesUp;

    private int bytesDown;


    private Stats(HttpServletRequest req){
        super();
        this.timeStart = System.currentTimeMillis();
        this.bytesUp = req.getContentLength();
    }


    public void mark(){
        this.timeMark = System.currentTimeMillis();
    }
    public long getTimeDelta(){
        long e = this.timeMark;
        long s = this.timeStart;
        if (e > s)
            return (e - s);
        else {
            e = System.currentTimeMillis();
            this.timeMark = e;
            return (e - s);
        }
    }
    void setBytesDown(int down){
        this.bytesDown = down;
    }
    public int getBytesUp(){
        return this.bytesUp;
    }
    public int getBytesDown(){
        return this.bytesDown;
    }
}
