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

/**
 * Application version service expects classes implementing {@link
 * Application$Version Application.Version}.
 * 
 * @author jdp
 */
public final class Application
    extends gap.util.Services
{
    /**
     * Interface accepted in classes mentioned in the application
     * services file.
     * 
     * Implementors are instantiated with a public, simple (no-arg)
     * constructor.
     */
    public interface Version {
        /**
         * @return For example "cpi"
         * @see #getVersionString()
         */
        public String getVersionName();
        /**
         * @return For example "3"
         * @see #getVersionString()
         */
        public int getVersionMajor();
        /**
         * @return For example "1"
         * @see #getVersionString()
         */
        public int getVersionMinor();
        /**
         * @return For example "1"
         * @see #getVersionString()
         */
        public int getVersionBuild();
        /**
         * Version token is: name, slash, major, dot, minor, dot
         * build.
         * 
         * @return For example "cpi/3.1.1"
         */
        public String getVersionString();
    }



    public Application(){
        super(Application.class);
    }

}
