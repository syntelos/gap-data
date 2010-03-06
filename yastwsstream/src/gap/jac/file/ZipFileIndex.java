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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

/** This class implements building of index of a zip archive and access to it's context.
 *  It also uses prebuild index if available. It supports invocations where it will
 *  serialize an optimized zip index file to disk.
 *
 *  In oreder to use secondary index file make sure the option "usezipindex" is in the Options object,
 *  when JavacFileManager is invoked. (You can pass "-XDusezipindex" on the command line.
 *
 *  Location where to look for/generate optimized zip index files can be provided using
 *  "-XDcachezipindexdir=<directory>". If this flag is not provided, the dfault location is
 *  the value of the "java.io.tmpdir" system property.
 *
 *  If key "-XDwritezipindexfiles" is specified, there will be new optimized index file
 *  created for each archive, used by the compiler for compilation, at location,
 *  specified by "cachezipindexdir" option.
 *
 * If nonBatchMode option is specified (-XDnonBatchMode) the compiler will use timestamp
 * checking to reindex the zip files if it is needed. In batch mode the timestamps are not checked
 * and the compiler uses the cached indexes.
 */
public class ZipFileIndex {
    private static final String MIN_CHAR = String.valueOf(Character.MIN_VALUE);
    private static final String MAX_CHAR = String.valueOf(Character.MAX_VALUE);

    public final static long NOT_MODIFIED = Long.MIN_VALUE;

    private static Map<File, ZipFileIndex> zipFileIndexCache = new HashMap<File, ZipFileIndex>();
    private static ReentrantLock lock = new ReentrantLock();

    private static boolean NON_BATCH_MODE = System.getProperty("nonBatchMode") != null;// TODO: Use -XD compiler switch for this.

    private Map<String, DirectoryEntry> directories = Collections.<String, DirectoryEntry>emptyMap();
    private Set<String> allDirs = Collections.<String>emptySet();

    // ZipFileIndex data entries
    private File zipFile;
    private long zipFileLastModified = NOT_MODIFIED;
    private RandomAccessFile zipRandomFile;
    private Entry[] entries;

    private boolean readFromIndex = false;
    private File zipIndexFile = null;
    private boolean triedToReadIndex = false;
    final String symbolFilePrefix;
    private int symbolFilePrefixLength = 0;
    private boolean hasPopulatedData = false;
    private long lastReferenceTimeStamp = NOT_MODIFIED;

    private boolean usePreindexedCache = false;
    private String preindexedCacheLocation = null;

    private boolean writeIndex = false;

    /**
     * Returns a list of all ZipFileIndex entries
     *
     * @return A list of ZipFileIndex entries, or an empty list
     */
    public static List<ZipFileIndex> getZipFileIndexes() {
        return getZipFileIndexes(false);
    }

    /**
     * Returns a list of all ZipFileIndex entries
     *
     * @param openedOnly If true it returns a list of only opened ZipFileIndex entries, otherwise
     *                   all ZipFileEntry(s) are included into the list.
     * @return A list of ZipFileIndex entries, or an empty list
     */
    public static List<ZipFileIndex> getZipFileIndexes(boolean openedOnly) {
        List<ZipFileIndex> zipFileIndexes = new ArrayList<ZipFileIndex>();
        lock.lock();
        try {
            zipFileIndexes.addAll(zipFileIndexCache.values());

            if (openedOnly) {
                for(ZipFileIndex elem : zipFileIndexes) {
                    if (!elem.isOpen()) {
                        zipFileIndexes.remove(elem);
                    }
                }
            }
        }
        finally {
            lock.unlock();
        }
        return zipFileIndexes;
    }

    public boolean isOpen() {
        lock.lock();
        try {
            return zipRandomFile != null;
        }
        finally {
            lock.unlock();
        }
    }

    public static ZipFileIndex getZipFileIndex(File zipFile, String symbolFilePrefix, boolean useCache, String cacheLocation, boolean writeIndex) throws IOException {
        ZipFileIndex zi = null;
        lock.lock();
        try {
            zi = getExistingZipIndex(zipFile);

            if (zi == null || (zi != null && zipFile.lastModified() != zi.zipFileLastModified)) {
                zi = new ZipFileIndex(zipFile, symbolFilePrefix, writeIndex,
                        useCache, cacheLocation);
                zipFileIndexCache.put(zipFile, zi);
            }
        }
        finally {
            lock.unlock();
        }
        return zi;
    }

    public static ZipFileIndex getExistingZipIndex(File zipFile) {
        lock.lock();
        try {
            return zipFileIndexCache.get(zipFile);
        }
        finally {
            lock.unlock();
        }
    }

    public static void clearCache() {
        lock.lock();
        try {
            zipFileIndexCache.clear();
        }
        finally {
            lock.unlock();
        }
    }

    public static void clearCache(long timeNotUsed) {
        lock.lock();
        try {
            Iterator<File> cachedFileIterator = zipFileIndexCache.keySet().iterator();
            while (cachedFileIterator.hasNext()) {
                File cachedFile = cachedFileIterator.next();
                ZipFileIndex cachedZipIndex = zipFileIndexCache.get(cachedFile);
                if (cachedZipIndex != null) {
                    long timeToTest = cachedZipIndex.lastReferenceTimeStamp + timeNotUsed;
                    if (timeToTest < cachedZipIndex.lastReferenceTimeStamp || // Overflow...
                            System.currentTimeMillis() > timeToTest) {
                        zipFileIndexCache.remove(cachedFile);
                    }
                }
            }
        }
        finally {
            lock.unlock();
        }
    }

    public static void removeFromCache(File file) {
        lock.lock();
        try {
            zipFileIndexCache.remove(file);
        }
        finally {
            lock.unlock();
        }
    }

    /** Sets already opened list of ZipFileIndexes from an outside client
      * of the compiler. This functionality should be used in a non-batch clients of the compiler.
      */
    public static void setOpenedIndexes(List<ZipFileIndex>indexes) throws IllegalStateException {
        lock.lock();
        try {
            if (zipFileIndexCache.isEmpty()) {
                throw new IllegalStateException("Setting opened indexes should be called only when the ZipFileCache is empty. Call JavacFileManager.flush() before calling this method.");
            }

            for (ZipFileIndex zfi : indexes) {
                zipFileIndexCache.put(zfi.zipFile, zfi);
            }
        }
        finally {
            lock.unlock();
        }
    }

    private ZipFileIndex(File zipFile, String symbolFilePrefix, boolean writeIndex,
            boolean useCache, String cacheLocation) throws IOException {
        this.zipFile = zipFile;
        this.symbolFilePrefix = symbolFilePrefix;
        this.symbolFilePrefixLength = (symbolFilePrefix == null ? 0 :
            symbolFilePrefix.getBytes("UTF-8").length);
        this.writeIndex = writeIndex;
        this.usePreindexedCache = useCache;
        this.preindexedCacheLocation = cacheLocation;

        if (zipFile != null) {
            this.zipFileLastModified = zipFile.lastModified();
        }

        // Validate integrity of the zip file
        checkIndex();
    }

    public String toString() {
        return "ZipFileIndex of file:(" + zipFile + ")";
    }

    // Just in case...
    protected void finalize() {
        closeFile();
    }

    private boolean isUpToDate() {
        if (zipFile != null &&
                ((!NON_BATCH_MODE) || zipFileLastModified == zipFile.lastModified()) &&
                hasPopulatedData) {
            return true;
        }

        return false;
    }

    /**
     * Here we need to make sure that the ZipFileIndex is valid. Check the timestamp of the file and
     * if its the same as the one at the time the index was build we don't need to reopen anything.
     */
    private void checkIndex() throws IOException {
        boolean isUpToDate = true;
        if (!isUpToDate()) {
            closeFile();
            isUpToDate = false;
        }

        if (zipRandomFile != null || isUpToDate) {
            lastReferenceTimeStamp = System.currentTimeMillis();
            return;
        }

        hasPopulatedData = true;

        if (readIndex()) {
            lastReferenceTimeStamp = System.currentTimeMillis();
            return;
        }

        directories = Collections.<String, DirectoryEntry>emptyMap();
        allDirs = Collections.<String>emptySet();

        try {
            openFile();
            long totalLength = zipRandomFile.length();
            ZipDirectory directory = new ZipDirectory(zipRandomFile, 0L, totalLength, this);
            directory.buildIndex();
        } finally {
            if (zipRandomFile != null) {
                closeFile();
            }
        }

        lastReferenceTimeStamp = System.currentTimeMillis();
    }

    private void openFile() throws FileNotFoundException {
        if (zipRandomFile == null && zipFile != null) {
            zipRandomFile = new RandomAccessFile(zipFile, "r");
        }
    }

    private void cleanupState() {
        // Make sure there is a valid but empty index if the file doesn't exist
        entries = Entry.EMPTY_ARRAY;
        directories = Collections.<String, DirectoryEntry>emptyMap();
        zipFileLastModified = NOT_MODIFIED;
        allDirs = Collections.<String>emptySet();
    }

    public void close() {
        lock.lock();
        try {
            writeIndex();
            closeFile();
        }
        finally {
            lock.unlock();
        }
    }

    private void closeFile() {
        if (zipRandomFile != null) {
            try {
                zipRandomFile.close();
            } catch (IOException ex) {
            }
            zipRandomFile = null;
        }
    }

    /**
     * Returns the ZipFileIndexEntry for an absolute path, if there is one.
     */
    Entry getZipIndexEntry(String path) {
        if (File.separatorChar != '/') {
            path = path.replace('/', File.separatorChar);
        }
        lock.lock();
        try {
            checkIndex();
            String lookFor = "";
            int lastSepIndex = path.lastIndexOf(File.separatorChar);
            boolean noSeparator = false;
            if (lastSepIndex == -1) {
                noSeparator = true;
            }

            DirectoryEntry de = directories.get(noSeparator ? "" : path.substring(0, lastSepIndex));

            lookFor = path.substring(noSeparator ? 0 : lastSepIndex + 1);

            return de == null ? null : de.getEntry(lookFor);
        }
        catch (IOException e) {
            return null;
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Returns a javac List of filenames within an absolute path in the ZipFileIndex.
     */
    public gap.jac.util.List<String> getFiles(String path) {
        if (File.separatorChar != '/') {
            path = path.replace('/', File.separatorChar);
        }

        lock.lock();
        try {
            checkIndex();

            DirectoryEntry de = directories.get(path);
            gap.jac.util.List<String> ret = de == null ? null : de.getFiles();

            if (ret == null) {
                return gap.jac.util.List.<String>nil();
            }
            return ret;
        }
        catch (IOException e) {
            return gap.jac.util.List.<String>nil();
        }
        finally {
            lock.unlock();
        }
    }

    public List<String> getAllDirectories(String path) {

        if (File.separatorChar != '/') {
            path = path.replace('/', File.separatorChar);
        }

        lock.lock();
        try {
            checkIndex();
            path = path.intern();

            DirectoryEntry de = directories.get(path);
            gap.jac.util.List<String> ret = de == null ? null : de.getDirectories();

            if (ret == null) {
                return gap.jac.util.List.<String>nil();
            }

            return ret;
        }
        catch (IOException e) {
            return gap.jac.util.List.<String>nil();
        }
        finally {
            lock.unlock();
        }
    }

    public Set<String> getAllDirectories() {
        lock.lock();
        try {
            checkIndex();
            if (allDirs == Collections.EMPTY_SET) {
                Set<String> alldirs = new HashSet<String>();
                Iterator<String> dirsIter = directories.keySet().iterator();
                while (dirsIter.hasNext()) {
                    alldirs.add(new String(dirsIter.next()));
                }

                allDirs = alldirs;
            }

            return allDirs;
        }
        catch (IOException e) {
            return Collections.<String>emptySet();
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Tests if a specific path exists in the zip.  This method will return true
     * for file entries and directories.
     *
     * @param path A path within the zip.
     * @return True if the path is a file or dir, false otherwise.
     */
    public boolean contains(String path) {
        lock.lock();
        try {
            checkIndex();
            return getZipIndexEntry(path) != null;
        }
        catch (IOException e) {
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    public boolean isDirectory(String path) throws IOException {
        lock.lock();
        try {
            // The top level in a zip file is always a directory.
            if (path.length() == 0) {
                lastReferenceTimeStamp = System.currentTimeMillis();
                return true;
            }

            if (File.separatorChar != '/')
                path = path.replace('/', File.separatorChar);
            checkIndex();
            return directories.get(path) != null;
        }
        finally {
            lock.unlock();
        }
    }

    public long getLastModified(String path) throws IOException {
        lock.lock();
        try {
            Entry entry = getZipIndexEntry(path);
            if (entry == null)
                throw new FileNotFoundException();
            return entry.getLastModified();
        }
        finally {
            lock.unlock();
        }
    }

    public int length(String path) throws IOException {
        lock.lock();
        try {
            Entry entry = getZipIndexEntry(path);
            if (entry == null)
                throw new FileNotFoundException();

            if (entry.isDir) {
                return 0;
            }

            byte[] header = getHeader(entry);
            // entry is not compressed?
            if (get2ByteLittleEndian(header, 8) == 0) {
                return entry.compressedSize;
            } else {
                return entry.size;
            }
        }
        finally {
            lock.unlock();
        }
    }

    public byte[] read(String path) throws IOException {
        lock.lock();
        try {
            Entry entry = getZipIndexEntry(path);
            if (entry == null)
                throw new FileNotFoundException(MessageFormat.format("Path not found in ZIP: {0}", path));
            return read(entry);
        }
        finally {
            lock.unlock();
        }
    }

    byte[] read(Entry entry) throws IOException {
        lock.lock();
        try {
            openFile();
            byte[] result = readBytes(entry);
            closeFile();
            return result;
        }
        finally {
            lock.unlock();
        }
    }

    public int read(String path, byte[] buffer) throws IOException {
        lock.lock();
        try {
            Entry entry = getZipIndexEntry(path);
            if (entry == null)
                throw new FileNotFoundException();
            return read(entry, buffer);
        }
        finally {
            lock.unlock();
        }
    }

    int read(Entry entry, byte[] buffer)
            throws IOException {
        lock.lock();
        try {
            int result = readBytes(entry, buffer);
            return result;
        }
        finally {
            lock.unlock();
        }
    }

    private byte[] readBytes(Entry entry) throws IOException {
        byte[] header = getHeader(entry);
        int csize = entry.compressedSize;
        byte[] cbuf = new byte[csize];
        zipRandomFile.skipBytes(get2ByteLittleEndian(header, 26) + get2ByteLittleEndian(header, 28));
        zipRandomFile.readFully(cbuf, 0, csize);

        // is this compressed - offset 8 in the ZipEntry header
        if (get2ByteLittleEndian(header, 8) == 0)
            return cbuf;

        int size = entry.size;
        byte[] buf = new byte[size];
        if (inflate(cbuf, buf) != size)
            throw new ZipException("corrupted zip file");

        return buf;
    }

    /**
     *
     */
    private int readBytes(Entry entry, byte[] buffer) throws IOException {
        byte[] header = getHeader(entry);

        // entry is not compressed?
        if (get2ByteLittleEndian(header, 8) == 0) {
            zipRandomFile.skipBytes(get2ByteLittleEndian(header, 26) + get2ByteLittleEndian(header, 28));
            int offset = 0;
            int size = buffer.length;
            while (offset < size) {
                int count = zipRandomFile.read(buffer, offset, size - offset);
                if (count == -1)
                    break;
                offset += count;
            }
            return entry.size;
        }

        int csize = entry.compressedSize;
        byte[] cbuf = new byte[csize];
        zipRandomFile.skipBytes(get2ByteLittleEndian(header, 26) + get2ByteLittleEndian(header, 28));
        zipRandomFile.readFully(cbuf, 0, csize);

        int count = inflate(cbuf, buffer);
        if (count == -1)
            throw new ZipException("corrupted zip file");

        return entry.size;
    }

    //----------------------------------------------------------------------------
    // Zip utilities
    //----------------------------------------------------------------------------

    private byte[] getHeader(Entry entry) throws IOException {
        zipRandomFile.seek(entry.offset);
        byte[] header = new byte[30];
        zipRandomFile.readFully(header);
        if (get4ByteLittleEndian(header, 0) != 0x04034b50)
            throw new ZipException("corrupted zip file");
        if ((get2ByteLittleEndian(header, 6) & 1) != 0)
            throw new ZipException("encrypted zip file"); // offset 6 in the header of the ZipFileEntry
        return header;
    }

  /*
   * Inflate using the java.util.zip.Inflater class
   */
    private static Inflater inflater;
    private int inflate(byte[] src, byte[] dest) {

        // construct the inflater object or reuse an existing one
        if (inflater == null)
            inflater = new Inflater(true);

        synchronized (inflater) {
            inflater.reset();
            inflater.setInput(src);
            try {
                return inflater.inflate(dest);
            } catch (DataFormatException ex) {
                return -1;
            }
        }
    }

    /**
     * return the two bytes buf[pos], buf[pos+1] as an unsigned integer in little
     * endian format.
     */
    private static int get2ByteLittleEndian(byte[] buf, int pos) {
        return (buf[pos] & 0xFF) + ((buf[pos+1] & 0xFF) << 8);
    }

    /**
     * return the 4 bytes buf[i..i+3] as an integer in little endian format.
     */
    private static int get4ByteLittleEndian(byte[] buf, int pos) {
        return (buf[pos] & 0xFF) + ((buf[pos + 1] & 0xFF) << 8) +
                ((buf[pos + 2] & 0xFF) << 16) + ((buf[pos + 3] & 0xFF) << 24);
    }

    /* ----------------------------------------------------------------------------
     * ZipDirectory
     * ----------------------------------------------------------------------------*/

    private class ZipDirectory {
        private String lastDir;
        private int lastStart;
        private int lastLen;

        byte[] zipDir;
        RandomAccessFile zipRandomFile = null;
        ZipFileIndex zipFileIndex = null;

        public ZipDirectory(RandomAccessFile zipRandomFile, long start, long end, ZipFileIndex index) throws IOException {
            this.zipRandomFile = zipRandomFile;
            this.zipFileIndex = index;

            findCENRecord(start, end);
        }

        /*
         * Reads zip file central directory.
         * For more details see readCEN in zip_util.c from the JDK sources.
         * This is a Java port of that function.
         */
        private void findCENRecord(long start, long end) throws IOException {
            long totalLength = end - start;
            int endbuflen = 1024;
            byte[] endbuf = new byte[endbuflen];
            long endbufend = end - start;

            // There is a variable-length field after the dir offset record. We need to do consequential search.
            while (endbufend >= 22) {
                if (endbufend < endbuflen)
                    endbuflen = (int)endbufend;
                long endbufpos = endbufend - endbuflen;
                zipRandomFile.seek(start + endbufpos);
                zipRandomFile.readFully(endbuf, 0, endbuflen);
                int i = endbuflen - 22;
                while (i >= 0 &&
                        !(endbuf[i] == 0x50 &&
                        endbuf[i + 1] == 0x4b &&
                        endbuf[i + 2] == 0x05 &&
                        endbuf[i + 3] == 0x06 &&
                        endbufpos + i + 22 +
                        get2ByteLittleEndian(endbuf, i + 20) == totalLength)) {
                    i--;
                }

                if (i >= 0) {
                    zipDir = new byte[get4ByteLittleEndian(endbuf, i + 12) + 2];
                    zipDir[0] = endbuf[i + 10];
                    zipDir[1] = endbuf[i + 11];
                    zipRandomFile.seek(start + get4ByteLittleEndian(endbuf, i + 16));
                    zipRandomFile.readFully(zipDir, 2, zipDir.length - 2);
                    return;
                } else {
                    endbufend = endbufpos + 21;
                }
            }
            throw new ZipException("cannot read zip file");
        }
        private void buildIndex() throws IOException {
            int entryCount = get2ByteLittleEndian(zipDir, 0);

            entries = new Entry[entryCount];
            // Add each of the files
            if (entryCount > 0) {
                directories = new HashMap<String, DirectoryEntry>();
                ArrayList<Entry> entryList = new ArrayList<Entry>();
                int pos = 2;
                for (int i = 0; i < entryCount; i++) {
                    pos = readEntry(pos, entryList, directories);
                }

                // Add the accumulated dirs into the same list
                Iterator i = directories.keySet().iterator();
                while (i.hasNext()) {
                    Entry zipFileIndexEntry = new Entry( (String) i.next());
                    zipFileIndexEntry.isDir = true;
                    entryList.add(zipFileIndexEntry);
                }

                entries = entryList.toArray(new Entry[entryList.size()]);
                Arrays.sort(entries);
            } else {
                cleanupState();
            }
        }

        private int readEntry(int pos, List<Entry> entryList,
                Map<String, DirectoryEntry> directories) throws IOException {
            if (get4ByteLittleEndian(zipDir, pos) != 0x02014b50) {
                throw new ZipException("cannot read zip file entry");
            }

            int dirStart = pos + 46;
            int fileStart = dirStart;
            int fileEnd = fileStart + get2ByteLittleEndian(zipDir, pos + 28);

            if (zipFileIndex.symbolFilePrefixLength != 0 &&
                    ((fileEnd - fileStart) >= symbolFilePrefixLength)) {
                dirStart += zipFileIndex.symbolFilePrefixLength;
               fileStart += zipFileIndex.symbolFilePrefixLength;
            }

            // Use the OS's path separator. Keep the position of the last one.
            for (int index = fileStart; index < fileEnd; index++) {
                byte nextByte = zipDir[index];
                if (nextByte == (byte)'\\' || nextByte == (byte)'/') {
                    zipDir[index] = (byte)File.separatorChar;
                    fileStart = index + 1;
                }
            }

            String directory = null;
            if (fileStart == dirStart)
                directory = "";
            else if (lastDir != null && lastLen == fileStart - dirStart - 1) {
                int index = lastLen - 1;
                while (zipDir[lastStart + index] == zipDir[dirStart + index]) {
                    if (index == 0) {
                        directory = lastDir;
                        break;
                    }
                    index--;
                }
            }

            // Sub directories
            if (directory == null) {
                lastStart = dirStart;
                lastLen = fileStart - dirStart - 1;

                directory = new String(zipDir, dirStart, lastLen, "UTF-8").intern();
                lastDir = directory;

                // Enter also all the parent directories
                String tempDirectory = directory;

                while (directories.get(tempDirectory) == null) {
                    directories.put(tempDirectory, new DirectoryEntry(tempDirectory, zipFileIndex));
                    int separator = tempDirectory.lastIndexOf(File.separatorChar);
                    if (separator == -1)
                        break;
                    tempDirectory = tempDirectory.substring(0, separator);
                }
            }
            else {
                directory = directory.intern();
                if (directories.get(directory) == null) {
                    directories.put(directory, new DirectoryEntry(directory, zipFileIndex));
                }
            }

            // For each dir create also a file
            if (fileStart != fileEnd) {
                Entry entry = new Entry(directory,
                        new String(zipDir, fileStart, fileEnd - fileStart, "UTF-8"));

                entry.setNativeTime(get4ByteLittleEndian(zipDir, pos + 12));
                entry.compressedSize = get4ByteLittleEndian(zipDir, pos + 20);
                entry.size = get4ByteLittleEndian(zipDir, pos + 24);
                entry.offset = get4ByteLittleEndian(zipDir, pos + 42);
                entryList.add(entry);
            }

            return pos + 46 +
                    get2ByteLittleEndian(zipDir, pos + 28) +
                    get2ByteLittleEndian(zipDir, pos + 30) +
                    get2ByteLittleEndian(zipDir, pos + 32);
        }
    }

    /**
     * Returns the last modified timestamp of a zip file.
     * @return long
     */
    public long getZipFileLastModified() throws IOException {
        lock.lock();
        try {
            checkIndex();
            return zipFileLastModified;
        }
        finally {
            lock.unlock();
        }
    }

    /** ------------------------------------------------------------------------
     *  DirectoryEntry class
     * -------------------------------------------------------------------------*/

    static class DirectoryEntry {
        private boolean filesInited;
        private boolean directoriesInited;
        private boolean zipFileEntriesInited;
        private boolean entriesInited;

        private long writtenOffsetOffset = 0;

        private String dirName;

        private gap.jac.util.List<String> zipFileEntriesFiles = gap.jac.util.List.<String>nil();
        private gap.jac.util.List<String> zipFileEntriesDirectories = gap.jac.util.List.<String>nil();
        private gap.jac.util.List<Entry>  zipFileEntries = gap.jac.util.List.<Entry>nil();

        private List<Entry> entries = new ArrayList<Entry>();

        private ZipFileIndex zipFileIndex;

        private int numEntries;

        DirectoryEntry(String dirName, ZipFileIndex index) {
        filesInited = false;
            directoriesInited = false;
            entriesInited = false;

            if (File.separatorChar == '/') {
                dirName.replace('\\', '/');
            }
            else {
                dirName.replace('/', '\\');
            }

            this.dirName = dirName.intern();
            this.zipFileIndex = index;
        }

        private gap.jac.util.List<String> getFiles() {
            if (filesInited) {
                return zipFileEntriesFiles;
            }

            initEntries();

            for (Entry e : entries) {
                if (!e.isDir) {
                    zipFileEntriesFiles = zipFileEntriesFiles.append(e.name);
                }
            }
            filesInited = true;
            return zipFileEntriesFiles;
        }

        private gap.jac.util.List<String> getDirectories() {
            if (directoriesInited) {
                return zipFileEntriesFiles;
            }

            initEntries();

            for (Entry e : entries) {
                if (e.isDir) {
                    zipFileEntriesDirectories = zipFileEntriesDirectories.append(e.name);
                }
            }

            directoriesInited = true;

            return zipFileEntriesDirectories;
        }

        private gap.jac.util.List<Entry> getEntries() {
            if (zipFileEntriesInited) {
                return zipFileEntries;
            }

            initEntries();

            zipFileEntries = gap.jac.util.List.nil();
            for (Entry zfie : entries) {
                zipFileEntries = zipFileEntries.append(zfie);
            }

            zipFileEntriesInited = true;

            return zipFileEntries;
        }

        private Entry getEntry(String rootName) {
            initEntries();
            int index = Collections.binarySearch(entries, new Entry(dirName, rootName));
            if (index < 0) {
                return null;
            }

            return entries.get(index);
        }

        private void initEntries() {
            if (entriesInited) {
                return;
            }

            if (!zipFileIndex.readFromIndex) {
                int from = -Arrays.binarySearch(zipFileIndex.entries,
                        new Entry(dirName, ZipFileIndex.MIN_CHAR)) - 1;
                int to = -Arrays.binarySearch(zipFileIndex.entries,
                        new Entry(dirName, MAX_CHAR)) - 1;

                boolean emptyList = false;

                for (int i = from; i < to; i++) {
                    entries.add(zipFileIndex.entries[i]);
                }
            } else {
                File indexFile = zipFileIndex.getIndexFile();
                if (indexFile != null) {
                    RandomAccessFile raf = null;
                    try {
                        raf = new RandomAccessFile(indexFile, "r");
                        raf.seek(writtenOffsetOffset);

                        for (int nFiles = 0; nFiles < numEntries; nFiles++) {
                            // Read the name bytes
                            int zfieNameBytesLen = raf.readInt();
                            byte [] zfieNameBytes = new byte[zfieNameBytesLen];
                            raf.read(zfieNameBytes);
                            String eName = new String(zfieNameBytes, "UTF-8");

                            // Read isDir
                            boolean eIsDir = raf.readByte() == (byte)0 ? false : true;

                            // Read offset of bytes in the real Jar/Zip file
                            int eOffset = raf.readInt();

                            // Read size of the file in the real Jar/Zip file
                            int eSize = raf.readInt();

                            // Read compressed size of the file in the real Jar/Zip file
                            int eCsize = raf.readInt();

                            // Read java time stamp of the file in the real Jar/Zip file
                            long eJavaTimestamp = raf.readLong();

                            Entry rfie = new Entry(dirName, eName);
                            rfie.isDir = eIsDir;
                            rfie.offset = eOffset;
                            rfie.size = eSize;
                            rfie.compressedSize = eCsize;
                            rfie.javatime = eJavaTimestamp;
                            entries.add(rfie);
                        }
                    } catch (Throwable t) {
                        // Do nothing
                    } finally {
                        try {
                            if (raf == null) {
                                raf.close();
                            }
                        } catch (Throwable t) {
                            // Do nothing
                        }
                    }
                }
            }

            entriesInited = true;
        }

        List<Entry> getEntriesAsCollection() {
            initEntries();

            return entries;
        }
    }

    private boolean readIndex() {
        if (triedToReadIndex || !usePreindexedCache) {
            return false;
        }

        boolean ret = false;
        lock.lock();
        try {
            triedToReadIndex = true;
            RandomAccessFile raf = null;
            try {
                File indexFileName = getIndexFile();
                raf = new RandomAccessFile(indexFileName, "r");

                long fileStamp = raf.readLong();
                if (zipFile.lastModified() != fileStamp) {
                    ret = false;
                } else {
                    directories = new HashMap<String, DirectoryEntry>();
                    int numDirs = raf.readInt();
                    for (int nDirs = 0; nDirs < numDirs; nDirs++) {
                        int dirNameBytesLen = raf.readInt();
                        byte [] dirNameBytes = new byte[dirNameBytesLen];
                        raf.read(dirNameBytes);

                        String dirNameStr = new String(dirNameBytes, "UTF-8");
                        DirectoryEntry de = new DirectoryEntry(dirNameStr, this);
                        de.numEntries = raf.readInt();
                        de.writtenOffsetOffset = raf.readLong();
                        directories.put(dirNameStr, de);
                    }
                    ret = true;
                    zipFileLastModified = fileStamp;
                }
            } catch (Throwable t) {
                // Do nothing
            } finally {
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (Throwable tt) {
                        // Do nothing
                    }
                }
            }
            if (ret == true) {
                readFromIndex = true;
            }
        }
        finally {
            lock.unlock();
        }

        return ret;
    }

    private boolean writeIndex() {
        boolean ret = false;
        if (readFromIndex || !usePreindexedCache) {
            return true;
        }

        if (!writeIndex) {
            return true;
        }

        File indexFile = getIndexFile();
        if (indexFile == null) {
            return false;
        }

        RandomAccessFile raf = null;
        long writtenSoFar = 0;
        try {
            raf = new RandomAccessFile(indexFile, "rw");

            raf.writeLong(zipFileLastModified);
            writtenSoFar += 8;


            Iterator<String> iterDirName = directories.keySet().iterator();
            List<DirectoryEntry> directoriesToWrite = new ArrayList<DirectoryEntry>();
            Map<String, Long> offsets = new HashMap<String, Long>();
            raf.writeInt(directories.keySet().size());
            writtenSoFar += 4;

            while(iterDirName.hasNext()) {
                String dirName = iterDirName.next();
                DirectoryEntry dirEntry = directories.get(dirName);

                directoriesToWrite.add(dirEntry);

                // Write the dir name bytes
                byte [] dirNameBytes = dirName.getBytes("UTF-8");
                int dirNameBytesLen = dirNameBytes.length;
                raf.writeInt(dirNameBytesLen);
                writtenSoFar += 4;

                raf.write(dirNameBytes);
                writtenSoFar += dirNameBytesLen;

                // Write the number of files in the dir
                List dirEntries = dirEntry.getEntriesAsCollection();
                raf.writeInt(dirEntries.size());
                writtenSoFar += 4;

                offsets.put(dirName, new Long(writtenSoFar));

                // Write the offset of the file's data in the dir
                dirEntry.writtenOffsetOffset = 0L;
                raf.writeLong(0L);
                writtenSoFar += 8;
            }

            for (DirectoryEntry de : directoriesToWrite) {
                // Fix up the offset in the directory table
                long currFP = raf.getFilePointer();

                long offsetOffset = offsets.get(de.dirName).longValue();
                raf.seek(offsetOffset);
                raf.writeLong(writtenSoFar);

                raf.seek(currFP);

                // Now write each of the files in the DirectoryEntry
                List<Entry> entries = de.getEntriesAsCollection();
                for (Entry zfie : entries) {
                    // Write the name bytes
                    byte [] zfieNameBytes = zfie.name.getBytes("UTF-8");
                    int zfieNameBytesLen = zfieNameBytes.length;
                    raf.writeInt(zfieNameBytesLen);
                    writtenSoFar += 4;
                    raf.write(zfieNameBytes);
                    writtenSoFar += zfieNameBytesLen;

                    // Write isDir
                    raf.writeByte(zfie.isDir ? (byte)1 : (byte)0);
                    writtenSoFar += 1;

                    // Write offset of bytes in the real Jar/Zip file
                    raf.writeInt(zfie.offset);
                    writtenSoFar += 4;

                    // Write size of the file in the real Jar/Zip file
                    raf.writeInt(zfie.size);
                    writtenSoFar += 4;

                    // Write compressed size of the file in the real Jar/Zip file
                    raf.writeInt(zfie.compressedSize);
                    writtenSoFar += 4;

                    // Write java time stamp of the file in the real Jar/Zip file
                    raf.writeLong(zfie.getLastModified());
                    writtenSoFar += 8;
                }
            }
        } catch (Throwable t) {
            // Do nothing
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch(IOException ioe) {
                // Do nothing
            }
        }

        return ret;
    }

    public boolean writeZipIndex() {
        lock.lock();
        try {
            return writeIndex();
        }
        finally {
            lock.unlock();
        }
    }

    private File getIndexFile() {
        if (zipIndexFile == null) {
            if (zipFile == null) {
                return null;
            }

            zipIndexFile = new File((preindexedCacheLocation == null ? "" : preindexedCacheLocation) +
                    zipFile.getName() + ".index");
        }

        return zipIndexFile;
    }

    public File getZipFile() {
        return zipFile;
    }


    static class Entry implements Comparable<Entry> {
        public static final Entry[] EMPTY_ARRAY = {};

        // Directory related
        String dir;
        boolean isDir;

        // File related
        String name;

        int offset;
        int size;
        int compressedSize;
        long javatime;

        private int nativetime;

        public Entry(String path) {
            int separator = path.lastIndexOf(File.separatorChar);
            if (separator == -1) {
                dir = "".intern();
                name = path;
            } else {
                dir = path.substring(0, separator).intern();
                name = path.substring(separator + 1);
            }
        }

        public Entry(String directory, String name) {
            this.dir = directory.intern();
            this.name = name;
        }

        public String getName() {
            if (dir == null || dir.length() == 0) {
                return name;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(dir);
            sb.append(File.separatorChar);
            sb.append(name);
            return sb.toString();
        }

        public String getFileName() {
            return name;
        }

        public long getLastModified() {
            if (javatime == 0) {
                    javatime = dosToJavaTime(nativetime);
            }
            return javatime;
        }

        // From java.util.zip
        private static long dosToJavaTime(int nativetime) {
            // Bootstrap build problems prevent me from using the code directly
            // Convert the raw/native time to a long for now
            return (long)nativetime;
        }

        void setNativeTime(int natTime) {
            nativetime = natTime;
        }

        public boolean isDirectory() {
            return isDir;
        }

        public int compareTo(Entry other) {
            String otherD = other.dir;
            if (dir != otherD) {
                int c = dir.compareTo(otherD);
                if (c != 0)
                    return c;
            }
            return name.compareTo(other.name);
        }


        public String toString() {
            return isDir ? ("Dir:" + dir + " : " + name) :
                (dir + ":" + name);
        }
    }

}
