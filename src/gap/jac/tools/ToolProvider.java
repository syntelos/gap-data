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

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.logging.Level;
import static java.util.logging.Level.*;

/**
 * Provides methods for locating tool providers, for example,
 * providers of compilers.  This class complements the
 * functionality of {@link java.util.ServiceLoader}.
 *
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public class ToolProvider {

    private ToolProvider() {}

    private static final String propertyName = "sun.tools.ToolProvider";
    private static final String loggerName   = "gap.jac.tools";

    /*
     * Define the system property "sun.tools.ToolProvider" to enable
     * debugging:
     *
     *     java ... -Dsun.tools.ToolProvider ...
     */
    static <T> T trace(Level level, Object reason) {
        // NOTE: do not make this method private as it affects stack traces
        try {
            if (System.getProperty(propertyName) != null) {
                StackTraceElement[] st = Thread.currentThread().getStackTrace();
                String method = "???";
                String cls = ToolProvider.class.getName();
                if (st.length > 2) {
                    StackTraceElement frame = st[2];
                    method = String.format((Locale)null, "%s(%s:%s)",
                                           frame.getMethodName(),
                                           frame.getFileName(),
                                           frame.getLineNumber());
                    cls = frame.getClassName();
                }
                Logger logger = Logger.getLogger(loggerName);
                if (reason instanceof Throwable) {
                    logger.logp(level, cls, method,
                                reason.getClass().getName(), (Throwable)reason);
                } else {
                    logger.logp(level, cls, method, String.valueOf(reason));
                }
            }
        } catch (SecurityException ex) {
            System.err.format((Locale)null, "%s: %s; %s%n",
                              ToolProvider.class.getName(),
                              reason,
                              ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Gets the Java&trade; programming language compiler provided
     * with this platform.
     * @return the compiler provided with this platform or
     * {@code null} if no compiler is provided
     */
    public static JavaCompiler getSystemJavaCompiler() {
        if (Lazy.compilerClass == null)
            return trace(WARNING, "Lazy.compilerClass == null");
        try {
            return Lazy.compilerClass.newInstance();
        } catch (Throwable e) {
            return trace(WARNING, e);
        }
    }

    /**
     * Returns the class loader for tools provided with this platform.
     * This does not include user-installed tools.  Use the
     * {@linkplain java.util.ServiceLoader service provider mechanism}
     * for locating user installed tools.
     *
     * @return the class loader for tools provided with this platform
     * or {@code null} if no tools are provided
     */
    public static ClassLoader getSystemToolClassLoader() {
        if (Lazy.compilerClass == null)
            return trace(WARNING, "Lazy.compilerClass == null");
        return Lazy.compilerClass.getClassLoader();
    }

    /**
     * This class will not be initialized until one of the above
     * methods are called.  This ensures that searching for the
     * compiler does not affect platform start up.
     */
    static class Lazy  {
        private static final String defaultJavaCompilerName
            = "gap.jac.api.JavacTool";
        private static final String[] defaultToolsLocation
            = { "lib", "tools.jar" };
        static final Class<? extends JavaCompiler> compilerClass;
        static {
            Class<? extends JavaCompiler> c = null;
            try {
                c = findClass().asSubclass(JavaCompiler.class);
            } catch (Throwable t) {
                trace(WARNING, t);
            }
            compilerClass = c;
        }

        private static Class<?> findClass()
            throws MalformedURLException, ClassNotFoundException
        {
            try {
                return enableAsserts(Class.forName(defaultJavaCompilerName, false, null));
            } catch (ClassNotFoundException e) {
                trace(FINE, e);
            }
            File file = new File(System.getProperty("java.home"));
            if (file.getName().equalsIgnoreCase("jre"))
                file = file.getParentFile();
            for (String name : defaultToolsLocation)
                file = new File(file, name);
            URL[] urls = {file.toURI().toURL()};
            trace(FINE, urls[0].toString());
            ClassLoader cl = URLClassLoader.newInstance(urls);
            cl.setPackageAssertionStatus("gap.jac", true);
            return Class.forName(defaultJavaCompilerName, false, cl);
        }

        private static Class<?> enableAsserts(Class<?> cls) {
            try {
                ClassLoader loader = cls.getClassLoader();
                if (loader != null)
                    loader.setPackageAssertionStatus("gap.jac", true);
                else
                    trace(FINE, "loader == null");
            } catch (SecurityException ex) {
                trace(FINE, ex);
            }
            return cls;
        }
    }
}
