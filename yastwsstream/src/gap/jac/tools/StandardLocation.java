/*
 * Copyright 1999-2006 Sun Microsystems, Inc.  All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * "CLASSPATH" EXCEPTION TO THE GPL
 * 
 * Certain source files distributed by Sun Microsystems, Inc.  are subject to
 * the following clarification and special exception to the GPL, but only where
 * Sun has expressly included in the particular source file's header the words
 * "Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the LICENSE file that accompanied this code."
 * 
 *   Linking this library statically or dynamically with other modules is making
 *   a combined work based on this library.  Thus, the terms and conditions of
 *   the GNU General Public License cover the whole combination.
 * 
 *   As a special exception, the copyright holders of this library give you
 *   permission to link this library with independent modules to produce an
 *   executable, regardless of the license terms of these independent modules,
 *   and to copy and distribute the resulting executable under terms of your
 *   choice, provided that you also meet, for each linked independent module,
 *   the terms and conditions of the license of that module.  An independent
 *   module is a module which is not derived from or based on this library.  If
 *   you modify this library, you may extend this exception to your version of
 *   the library, but you are not obligated to do so.  If you do not wish to do
 *   so, delete this exception statement from your version.
 */
package gap.jac.tools;

import gap.jac.tools.JavaFileManager.Location;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * Standard locations of file objects.
 *
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public enum StandardLocation implements Location {

    /**
     * Location of new class files.
     */
    CLASS_OUTPUT,

    /**
     * Location of new source files.
     */
    SOURCE_OUTPUT,

    /**
     * Location to search for user class files.
     */
    CLASS_PATH,

    /**
     * Location to search for existing source files.
     */
    SOURCE_PATH,

    /**
     * Location to search for annotation processors.
     */
    ANNOTATION_PROCESSOR_PATH,

    /**
     * Location to search for platform classes.  Sometimes called
     * the boot class path.
     */
    PLATFORM_CLASS_PATH;

    /**
     * Gets a location object with the given name.  The following
     * property must hold: {@code locationFor(x) ==
     * locationFor(y)} if and only if {@code x.equals(y)}.
     * The returned location will be an output location if and only if
     * name ends with {@code "_OUTPUT"}.
     *
     * @param name a name
     * @return a location
     */
    public static Location locationFor(final String name) {
        if (locations.isEmpty()) {
            // can't use valueOf which throws IllegalArgumentException
            for (Location location : values())
                locations.putIfAbsent(location.getName(), location);
        }
        locations.putIfAbsent(name.toString(/* null-check */), new Location() {
                public String getName() { return name; }
                public boolean isOutputLocation() { return name.endsWith("_OUTPUT"); }
            });
        return locations.get(name);
    }
    //where
        private static ConcurrentMap<String,Location> locations
            = new ConcurrentHashMap<String,Location>();

    public String getName() { return name(); }

    public boolean isOutputLocation() {
        return this == CLASS_OUTPUT || this == SOURCE_OUTPUT;
    }
}
