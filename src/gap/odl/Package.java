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
package gap.odl;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 
 * @see Class
 * @author jdp
 */
public final class Package
    extends Object
    implements gap.service.od.PackageDescriptor
{

    public final String name;

    public Package(Reader reader)
        throws IOException, Syntax
    {
        super();
        String packageName = null, className = null;
        for (String line : reader){
            line = line.trim();
            if (line.startsWith("#") || 0 == line.length())
                continue;
            else if (line.startsWith("package")){
                StringTokenizer strtok = new StringTokenizer(line," \t;");
                if (2 == strtok.countTokens()){
                    strtok.nextToken();
                    this.name = strtok.nextToken();
                    return;
                }
                else
                    throw new Syntax("Malformed ODL package statement '"+line+"'.");
            }
            else 
                throw new Jump(reader,line);
        }
        throw new Syntax("Missing ODL package statement.");
    }

    public String getName(){
        return this.name;
    }
}
