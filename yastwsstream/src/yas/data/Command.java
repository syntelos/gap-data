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

import com.google.appengine.api.datastore.Key;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * 
 *
 * @see CommandData
 */
public final class Command
    extends CommandData
{
    public enum Identifier {
        help, stats, sources, source, drop;
    }

    /**
     * Parser features not persistent
     */
    public transient final Identifier identifier;
    public transient final String name;


    /**
     * Parse new receipt
     */
    public Command(Key key, Feed.Data data){
        super();
        this.setId(data.guid);
        this.setKey(key);
        StringTokenizer strtok = new StringTokenizer(data.content," \t\r\n");
        switch (strtok.countTokens()){
        case 1:
            this.identifier = Identifier.valueOf(strtok.nextToken());
            this.name = null;
            break;
        case 2:
        default:
            this.identifier = Identifier.valueOf(strtok.nextToken());
            this.name = Twitter.Name.Clean(strtok.nextToken());
            break;
        }
        switch (this.identifier){
        case help:
        case stats:
        case sources:
            break;
        case source:
        case drop:
            if (null == this.name)
                throw new IllegalStateException();
            else
                break;
        default:
            break;
        }
    }
    public Command() {
        super();
        this.identifier = null;
        this.name = null;
    }


    public void onread(){
    }
    public void onwrite(){
    }
    public java.io.Serializable valueOf(gap.data.Field field, boolean mayInherit){
        return (java.io.Serializable)Field.Get((Field)field,this,mayInherit);
    }
    public void define(gap.data.Field field, java.io.Serializable value){
        Field.Set((Field)field,this,value);
    }
    public void drop(){
        Delete(this);
    }
    public void clean(){
        Clean(this);
    }
    public void save(){
        Save(this);
    }
    public void store(){
        Store(this);
    }
}
