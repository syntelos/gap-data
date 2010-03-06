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
package gap.jac.file;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import gap.jac.tools.JavaFileObject;

import gap.jac.file.JavacFileManager.Archive;
import gap.jac.util.List;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;

public class ZipArchive implements Archive {

    public ZipArchive(JavacFileManager fm, ZipFile zdir) throws IOException {
        this.fileManager = fm;
        this.zdir = zdir;
        this.map = new HashMap<String,List<String>>();
        for (Enumeration<? extends ZipEntry> e = zdir.entries(); e.hasMoreElements(); ) {
            ZipEntry entry;
            try {
                entry = e.nextElement();
            } catch (InternalError ex) {
                IOException io = new IOException();
                io.initCause(ex); // convenience constructors added in Mustang :-(
                throw io;
            }
            addZipEntry(entry);
        }
    }

    void addZipEntry(ZipEntry entry) {
        String name = entry.getName();
        int i = name.lastIndexOf('/');
        String dirname = name.substring(0, i+1);
        String basename = name.substring(i+1);
        if (basename.length() == 0)
            return;
        List<String> list = map.get(dirname);
        if (list == null)
            list = List.nil();
        list = list.prepend(basename);
        map.put(dirname, list);
    }

    public boolean contains(String name) {
        int i = name.lastIndexOf('/');
        String dirname = name.substring(0, i+1);
        String basename = name.substring(i+1);
        if (basename.length() == 0)
            return false;
        List<String> list = map.get(dirname);
        return (list != null && list.contains(basename));
    }

    public List<String> getFiles(String subdirectory) {
        return map.get(subdirectory);
    }

    public JavaFileObject getFileObject(String subdirectory, String file) {
        ZipEntry ze = zdir.getEntry(subdirectory + file);
        return new ZipFileObject(this, file, ze);
    }

    public Set<String> getSubdirectories() {
        return map.keySet();
    }

    public void close() throws IOException {
        zdir.close();
    }

    protected JavacFileManager fileManager;
    protected final Map<String,List<String>> map;
    protected final ZipFile zdir;

    /**
     * A subclass of JavaFileObject representing zip entries.
     */
    public static class ZipFileObject extends BaseFileObject {

        private String name;
        ZipArchive zarch;
        ZipEntry entry;

        public ZipFileObject(ZipArchive zarch, String name, ZipEntry entry) {
            super(zarch.fileManager);
            this.zarch = zarch;
            this.name = name;
            this.entry = entry;
        }

        public InputStream openInputStream() throws IOException {
            return zarch.zdir.getInputStream(entry);
        }

        public OutputStream openOutputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        protected CharsetDecoder getDecoder(boolean ignoreEncodingErrors) {
            return fileManager.getDecoder(fileManager.getEncodingName(), ignoreEncodingErrors);
        }

        public Writer openWriter() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public String getName() {
            return name;
        }

        public boolean isNameCompatible(String cn, JavaFileObject.Kind k) {
            cn.getClass();
            // null check
            if (k == Kind.OTHER && getKind() != k) {
                return false;
            }
            return name.equals(cn + k.extension);
        }

        @Deprecated
        public String getPath() {
            return zarch.zdir.getName() + "(" + entry + ")";
        }

        public long getLastModified() {
            return entry.getTime();
        }

        public boolean delete() {
            throw new UnsupportedOperationException();
        }

        public CharBuffer getCharContent(boolean ignoreEncodingErrors) throws IOException {
            CharBuffer cb = fileManager.getCachedContent(this);
            if (cb == null) {
                InputStream in = zarch.zdir.getInputStream(entry);
                try {
                    ByteBuffer bb = fileManager.makeByteBuffer(in);
                    JavaFileObject prev = fileManager.log.useSource(this);
                    try {
                        cb = fileManager.decode(bb, ignoreEncodingErrors);
                    } finally {
                        fileManager.log.useSource(prev);
                    }
                    fileManager.recycleByteBuffer(bb);
                    if (!ignoreEncodingErrors) {
                        fileManager.cache(this, cb);
                    }
                } finally {
                    in.close();
                }
            }
            return cb;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ZipFileObject)) {
                return false;
            }
            ZipFileObject o = (ZipFileObject) other;
            return zarch.zdir.equals(o.zarch.zdir) || name.equals(o.name);
        }

        @Override
        public int hashCode() {
            return zarch.zdir.hashCode() + name.hashCode();
        }

        public String getZipName() {
            return zarch.zdir.getName();
        }

        public String getZipEntryName() {
            return entry.getName();
        }

        public URI toUri() {
            String zipName = new File(getZipName()).toURI().normalize().getPath();
            String entryName = getZipEntryName();
            return URI.create("jar:" + zipName + "!" + entryName);
        }

        @Override
        protected String inferBinaryName(Iterable<? extends File> path) {
            String entryName = getZipEntryName();
            if (zarch instanceof SymbolArchive) {
                String prefix = ((SymbolArchive) zarch).prefix;
                if (entryName.startsWith(prefix))
                    entryName = entryName.substring(prefix.length());
            }
            return removeExtension(entryName).replace('/', '.');
        }
    }

}
