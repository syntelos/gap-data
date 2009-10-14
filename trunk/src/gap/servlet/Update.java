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
package gap.servlet;

import gap.*;

import javax.servlet.ServletException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

/**
 * Bound to path <code>'/update'</code>
 */
public class Update
    extends Site
{
    private static boolean Once = true;

    public Update(){
        super();
    }


    @Override
    protected void doGet(Request req, Response rep)
        throws ServletException, IOException
    {
        try {
            this.doGetDefine(req,rep);

            if (Once){
                if (this.installResourceIndex()){
                    if (this.installResourceErrors()){
                        Once = false;
                        this.error(req,rep,200,"Installation Performed.");
                    }
                    else 
                        this.error(req,rep,500,"Error installing 'errors'.");
                }
                else {
                    Once = false;
                    this.error(req,rep,200,"Previously installed.");
                }
            }
            else
                this.error(req,rep,200,"Installation Complete.");
        }
        catch (hapax.TemplateException exc){
            LogRecord rec = new LogRecord(Level.SEVERE,"init");
            rec.setThrown(exc);
            Log.log(rec);
            this.error(req,rep,500,"Internal error.",exc);
        }
        catch (IOException exc){
            LogRecord rec = new LogRecord(Level.SEVERE,"init");
            rec.setThrown(exc);
            Log.log(rec);
            this.error(req,rep,500,"Internal error.",exc);
        }
    }
}
