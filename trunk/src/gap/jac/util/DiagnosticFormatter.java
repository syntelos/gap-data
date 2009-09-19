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
package gap.jac.util;

import gap.jac.tools.JavaFileObject;

import gap.jac.file.JavacFileManager;
import gap.jac.util.JCDiagnostic.DiagnosticSource;
import gap.jac.util.JCDiagnostic.DiagnosticType;

/**
 * A formatter for diagnostic messages.
 * The formatter will format a diagnostic according to one of two format strings, depending on whether
 * or not the source name and position are set. The format is a printf-like string,
 * with the following special characters:
 * <ul>
 * <li>%b: the base of the source name, or "-" if not set
 * <li>%f: the source name, or "-" if not set
 * <li>%l: the line number of the diagnostic, derived from the character offset if set, or "-" otherwise
 * <li>%c: the column number of the diagnostic, derived from the character offset if set, or "-" otherwise
 * <li>%o: the character offset of the diagnostic if set, or "-" otherwise
 * <li>%p: the prefix for the diagnostic, derived from the diagnostic type
 * <li>%t: the prefix as it normally appears in standard diagnostics. In this case, no prefix is
 *        shown if the type is ERROR and if a source name is set
 * <li>%m: the text or the diagnostic, including any appropriate arguments
 * </ul>
 */
public class DiagnosticFormatter {
    /**
     * A format string to be used for diagnostics with a given position.
     */
    protected String posFormat;

    /**
     * A format string to be used for diagnostics regarding classfiles
     */
    protected String classFormat = DEFAULT_CLASS_FORMAT;

    /**
     * A format string to be used for diagnostics without a given position.
     */
    protected String noPosFormat;

    /**
     * A value to indicate whether to output the i18n key and args, instead of
     * the derived l10n message.
     */
    protected boolean raw;

    /** The context key for the formatter. */
    protected static final Context.Key<DiagnosticFormatter> formatterKey =
        new Context.Key<DiagnosticFormatter>();

    /** Get the DiagnosticFormatter instance for this context. */
    public static DiagnosticFormatter instance(Context context) {
        DiagnosticFormatter instance = context.get(formatterKey);
        if (instance == null)
            instance = new DiagnosticFormatter(context);
        return instance;
    }

    /**
     * Create a formatter based on the supplied options.
     */
    protected DiagnosticFormatter(Context context) {
        Options options = Options.instance(context);
        raw = options.get("rawDiagnostics") != null;
        String fmt = options.get("diags");
        if (fmt != null) {
            int sep = fmt.indexOf('|');
            if (sep == -1)
                posFormat = noPosFormat = fmt;
            else {
                posFormat = fmt.substring(0, sep);
                noPosFormat = fmt.substring(sep + 1);
            }
        }
        else {
            posFormat = DEFAULT_POS_FORMAT;
            noPosFormat = DEFAULT_NO_POS_FORMAT;
        }
    }

    public static final String DEFAULT_POS_FORMAT = "%f:%l: %t%m";
    public static final String DEFAULT_CLASS_FORMAT = "%f: %t%m";
    public static final String DEFAULT_NO_POS_FORMAT = "%p%m";

    public DiagnosticFormatter() {
        posFormat = DEFAULT_POS_FORMAT;
        noPosFormat = DEFAULT_NO_POS_FORMAT;
        raw = false;
    }

    public DiagnosticFormatter(String pos, String noPos) {
        posFormat = pos;
        noPosFormat = noPos;
        raw = false;
    }

    String format(JCDiagnostic d) {
        return (raw ? format_raw(d) : format_std(d));
    }

    private String format_raw(JCDiagnostic d) {
        DiagnosticSource source = d.getDiagnosticSource();
        int position = d.getIntPosition();

        StringBuilder sb = new StringBuilder();
        if (position == Position.NOPOS)
            sb.append("-");
        else {
            sb.append(source.getName() + ":" + source.getLineNumber(position) + ":" + source.getColumnNumber(position) + ":");
        }
        sb.append(" ");
        sb.append(d.getCode());
        String sep = ": ";
        for (Object arg: d.getArgs()) {
            sb.append(sep);
            if (arg instanceof JCDiagnostic) {
                sb.append('(');
                sb.append(format_raw((JCDiagnostic) arg));
                sb.append(')');
            }
            else if (arg instanceof JavaFileObject)
                sb.append(JavacFileManager.getJavacBaseFileName((JavaFileObject) arg));
            else
                sb.append(arg);
            sep = ", ";
        }
        return sb.toString();
    }

    private String format_std(JCDiagnostic d) {
        DiagnosticSource source = d.getDiagnosticSource();
        DiagnosticType type = d.getType();
        int position = d.getIntPosition();


        String format = noPosFormat;
        if (source != null) {
            if (position != Position.NOPOS) {
                format = posFormat;
            } else if (source.getFile() != null &&
                       source.getFile().getKind() == JavaFileObject.Kind.CLASS) {
                format = classFormat;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);
            if (c == '%' && i < format.length() - 1) {
                c = format.charAt(++i);
                switch (c) {
                case 'b':
                    sb.append(source == null ? "-" : source.getName());
                    break;

                case 'e':
                    sb.append(position == Position.NOPOS ? "-" : String.valueOf(d.getEndPosition()));
                    break;

                case 'f':
                    sb.append(source == null ? "-" : d.getSourceName());
                    break;

                case 'l':
                    sb.append(position == Position.NOPOS ? "-" : String.valueOf(d.getLineNumber()));
                    break;

                case 'c':
                    sb.append(position == Position.NOPOS ? "-" : String.valueOf(d.getColumnNumber()));
                    break;

                case 'o':
                    sb.append(position == Position.NOPOS ? "-" : String.valueOf(position));
                    break;

                case 'p':
                    sb.append(d.getPrefix());
                    break;

                case 's':
                    sb.append(position == Position.NOPOS ? "-" : String.valueOf(d.getStartPosition()));
                    break;

                case 't': {
                    boolean usePrefix;
                    switch (type) {
                    case FRAGMENT:
                        usePrefix = false;
                        break;

                    case ERROR:
                        usePrefix = (position == Position.NOPOS);
                        break;

                    default:
                        usePrefix = true;
                    }

                    if (usePrefix)
                        sb.append(d.getPrefix());
                    break;
                }

                case 'm':
                    sb.append(d.getMessage(null));
                    break;

                case '_':
                    sb.append(' ');
                    break;

                case '%':
                    sb.append('%');
                    break;

                default:
                    sb.append(c);
                    break;
                }
            }
            else
                sb.append(c);
        }
        return sb.toString();
    }
}
