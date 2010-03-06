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
package gap.jac.api;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import gap.jac.tools.JavaFileObject.Kind;
import gap.jac.tools.*;

/**
 * Wraps all calls to a given file manager.  Subclasses of this class
 * might override some of these methods and might also provide
 * additional fields and methods.
 *
 * <p>This class might be moved to {@link gap.jac.tools} in a future
 * release.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 *
 * @param <M> the type of file manager wrapped to by this object
 *
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public class WrappingJavaFileManager<M extends JavaFileManager> extends ForwardingJavaFileManager<M> {

    /**
     * Creates a new instance of WrappingJavaFileManager.
     * @param fileManager file manager to be wrapped
     */
    protected WrappingJavaFileManager(M fileManager) {
        super(fileManager);
    }

    /**
     * This implementation returns the given file object.  Subclasses
     * may override this behavior.
     *
     * @param fileObject a file object
     */
    protected FileObject wrap(FileObject fileObject) {
        return fileObject;
    }

    /**
     * This implementation forwards to {@link #wrap(FileObject)}.
     * Subclasses may override this behavior.
     *
     * @param fileObject a file object
     * @throws ClassCastException if the file object returned from the
     * forwarded call is not a subtype of {@linkplain JavaFileObject}
     */
    protected JavaFileObject wrap(JavaFileObject fileObject) {
        return (JavaFileObject)wrap((FileObject)fileObject);
    }

    /**
     * This implementation returns the given file object.  Subclasses
     * may override this behavior.
     *
     * @param fileObject a file object
     */
    protected FileObject unwrap(FileObject fileObject) {
        return fileObject;
    }

    /**
     * This implementation forwards to {@link #unwrap(FileObject)}.
     * Subclasses may override this behavior.
     *
     * @param fileObject a file object
     * @throws ClassCastException if the file object returned from the
     * forwarded call is not a subtype of {@linkplain JavaFileObject}
     */
    protected JavaFileObject unwrap(JavaFileObject fileObject) {
        return (JavaFileObject)unwrap((FileObject)fileObject);
    }

    /**
     * This implementation maps the given list of file objects by
     * calling wrap on each.  Subclasses may override this behavior.
     *
     * @param fileObjects a list of file objects
     * @return the mapping
     */
    protected Iterable<JavaFileObject> wrap(Iterable<JavaFileObject> fileObjects) {
        List<JavaFileObject> mapped = new ArrayList<JavaFileObject>();
        for (JavaFileObject fileObject : fileObjects)
            mapped.add(wrap(fileObject));
        return Collections.unmodifiableList(mapped);
    }

    /**
     * This implementation returns the given URI.  Subclasses may
     * override this behavior.
     *
     * @param uri a URI
     */
    protected URI unwrap(URI uri) {
        return uri;
    }

    /**
     * @throws IllegalStateException {@inheritDoc}
     */
    public Iterable<JavaFileObject> list(Location location,
                                         String packageName,
                                         Set<Kind> kinds,
                                         boolean recurse)
        throws IOException
    {
        return wrap(super.list(location, packageName, kinds, recurse));
    }

    /**
     * @throws IllegalStateException {@inheritDoc}
     */
    public String inferBinaryName(Location location, JavaFileObject file) {
        return super.inferBinaryName(location, unwrap(file));
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IllegalStateException {@inheritDoc}
     */
    public JavaFileObject getJavaFileForInput(Location location,
                                              String className,
                                              Kind kind)
        throws IOException
    {
        return wrap(super.getJavaFileForInput(location, className, kind));
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IllegalStateException {@inheritDoc}
     */
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className,
                                               Kind kind,
                                               FileObject sibling)
        throws IOException
    {
        return wrap(super.getJavaFileForOutput(location, className, kind, unwrap(sibling)));
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalStateException {@inheritDoc}
     */
    public FileObject getFileForInput(Location location,
                                      String packageName,
                                      String relativeName)
        throws IOException
    {
        return wrap(super.getFileForInput(location, packageName, relativeName));
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalStateException {@inheritDoc}
     */
    public FileObject getFileForOutput(Location location,
                                       String packageName,
                                       String relativeName,
                                       FileObject sibling)
        throws IOException
    {
        return wrap(super.getFileForOutput(location,
                                           packageName,
                                           relativeName,
                                           unwrap(sibling)));
    }

}
