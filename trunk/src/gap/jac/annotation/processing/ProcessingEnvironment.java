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
package gap.jac.annotation.processing;

import java.util.Map;
import java.util.List;
import java.util.Locale;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.File;

/**
 * An annotation processing tool framework will {@linkplain
 * Processor#init provide an annotation processor with an object
 * implementing this interface} so the processor can use facilities
 * provided by the framework to write new files, report error
 * messages, and find other utilities.
 *
 * <p>Third parties may wish to provide value-add wrappers around the
 * facility objects from this interface, for example a {@code Filer}
 * extension that allows multiple processors to coordinate writing out
 * a single source file.  To enable this, for processors running in a
 * context where their side effects via the API could be visible to
 * each other, the tool infrastructure must provide corresponding
 * facility objects that are {@code .equals}, {@code Filer}s that are
 * {@code .equals}, and so on.  In addition, the tool invocation must
 * be able to be configured such that from the perspective of the
 * running annotation processors, at least the chosen subset of helper
 * classes are viewed as being loaded by the same class loader.
 * (Since the facility objects manage shared state, the implementation
 * of a wrapper class must know whether or not the same base facility
 * object has been wrapped before.)
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface ProcessingEnvironment {
    /**
     * Returns the processor-specific options passed to the annotation
     * processing tool.  Options are returned in the form of a map from
     * option name to option value.  For an option with no value, the
     * corresponding value in the map is {@code null}.
     *
     * <p>See documentation of the particular tool infrastructure
     * being used for details on how to pass in processor-specific
     * options.  For example, a command-line implementation may
     * distinguish processor-specific options by prefixing them with a
     * known string like {@code "-A"}; other tool implementations may
     * follow different conventions or provide alternative mechanisms.
     * A given implementation may also provide implementation-specific
     * ways of finding options passed to the tool in addition to the
     * processor-specific options.
     *
     * @return the processor-specific options passed to the tool
     */
    Map<String,String> getOptions();

    /**
     * Returns the messager used to report errors, warnings, and other
     * notices.
     *
     * @return the messager
     */
    Messager getMessager();

    /**
     * Returns the filer used to create new source, class, or auxiliary
     * files.
     *
     * @return the filer
     */
    Filer getFiler();

    /**
     * Returns an implementation of some utility methods for
     * operating on elements
     *
     * @return element utilities
     */
    Elements getElementUtils();

    /**
     * Returns an implementation of some utility methods for
     * operating on types.
     *
     * @return type utilities
     */
    Types getTypeUtils();

    /**
     * Returns the source version that any generated {@linkplain
     * Filer#createSourceFile source} and {@linkplain
     * Filer#createClassFile class} files should conform to.
     *
     * @return the source version to which generated source and class
     *         files should conform to
     * @see Processor#getSupportedSourceVersion
     */
    SourceVersion getSourceVersion();

    /**
     * Returns the current locale or {@code null} if no locale is in
     * effect.  The locale can be be used to provide localized
     * {@linkplain Messager messages}.
     *
     * @return the current locale or {@code null} if no locale is in
     * effect
     */
    Locale getLocale();
}
