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
package gap.jac.processing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Utility class to determine if a service can be found on the
 * path that might be used to create a class loader.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 *
 */
// based on sun.misc.Service
class ServiceProxy {
    static class ServiceConfigurationError extends Error {
        static final long serialVersionUID = 7732091036771098303L;

        ServiceConfigurationError(String msg) {
            super(msg);
        }
    }

    private static final String prefix = "META-INF/services/";

    private static void fail(Class service, String msg)
            throws ServiceConfigurationError {
        throw new ServiceConfigurationError(service.getName() + ": " + msg);
    }

    private static void fail(Class service, URL u, int line, String msg)
            throws ServiceConfigurationError {
        fail(service, u + ":" + line + ": " + msg);
    }

    /**
     * Parse the content of the given URL as a provider-configuration file.
     *
     * @param  service
     *         The service class for which providers are being sought;
     *         used to construct error detail strings
     *
     * @param  url
     *         The URL naming the configuration file to be parsed
     *
     * @return true if the name of a service is found
     *
     * @throws ServiceConfigurationError
     *         If an I/O error occurs while reading from the given URL, or
     *         if a configuration-file format error is detected
     */
    private static boolean parse(Class service, URL u) throws ServiceConfigurationError {
        InputStream in = null;
        BufferedReader r = null;
        try {
            in = u.openStream();
            r = new BufferedReader(new InputStreamReader(in, "utf-8"));
            int lc = 1;
            String ln;
            while ((ln = r.readLine()) != null) {
                int ci = ln.indexOf('#');
                if (ci >= 0) ln = ln.substring(0, ci);
                ln = ln.trim();
                int n = ln.length();
                if (n != 0) {
                    if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0))
                        fail(service, u, lc, "Illegal configuration-file syntax");
                    int cp = ln.codePointAt(0);
                    if (!Character.isJavaIdentifierStart(cp))
                        fail(service, u, lc, "Illegal provider-class name: " + ln);
                    for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                        cp = ln.codePointAt(i);
                        if (!Character.isJavaIdentifierPart(cp) && (cp != '.'))
                            fail(service, u, lc, "Illegal provider-class name: " + ln);
                    }
                    return true;
                }
            }
        } catch (FileNotFoundException x) {
            return false;
        } catch (IOException x) {
            fail(service, ": " + x);
        } finally {
            try {
                if (r != null) r.close();
            } catch (IOException y) {
                fail(service, ": " + y);
            }
            try {
                if (in != null) in.close();
            } catch (IOException y) {
                fail(service, ": " + y);
            }
        }
        return false;
    }

    /**
     * Return true if a description for at least one service is found in the
     * service configuration files in the given URLs.
     */
    public static boolean hasService(Class<?> service, URL[] urls)
            throws ServiceConfigurationError {
        for (URL url: urls) {
            try {
                String fullName = prefix + service.getName();
                URL u = new URL(url, fullName);
                boolean found = parse(service, u);
                if (found)
                    return true;
            } catch (MalformedURLException e) {
                // should not happen; ignore it if it does
            }
        }
        return false;
    }
}
