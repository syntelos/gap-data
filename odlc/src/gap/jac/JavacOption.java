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
package gap.jac;

import gap.jac.util.Log;
import gap.jac.util.Options;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

/**
 * TODO: describe gap.jac.JavacOption
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 */
public interface JavacOption {

    OptionKind getKind();

    /** Does this option take a (separate) operand?
     *  @return true if this option takes a separate operand
     */
    boolean hasArg();

    /** Does argument string match option pattern?
     *  @param arg   the command line argument string
     *  @return true if {@code arg} matches this option
     */
    boolean matches(String arg);

    /** Process an option with an argument.
     *  @param options the accumulated set of analyzed options
     *  @param option  the option to be processed
     *  @param arg     the arg for the option to be processed
     *  @return true if an error was detected
     */
    boolean process(Options options, String option, String arg);

    /** Process the option with no argument.
     *  @param options the accumulated set of analyzed options
     *  @param option  the option to be processed
     *  @return true if an error was detected
     */
    boolean process(Options options, String option);

    OptionName getName();

    enum OptionKind {
        NORMAL,
        EXTENDED,
        HIDDEN,
    }

    enum ChoiceKind {
        ONEOF,
        ANYOF
    }

    /** This class represents an option recognized by the main program
     */
    static class Option implements JavacOption {

        /** Option string.
         */
        OptionName name;

        /** Documentation key for arguments.
         */
        String argsNameKey;

        /** Documentation key for description.
         */
        String descrKey;

        /** Suffix option (-foo=bar or -foo:bar)
         */
        boolean hasSuffix;

        /** The kind of choices for this option, if any.
         */
        ChoiceKind choiceKind;

        /** The choices for this option, if any.
         */
        Collection<String> choices;

        Option(OptionName name, String argsNameKey, String descrKey) {
            this.name = name;
            this.argsNameKey = argsNameKey;
            this.descrKey = descrKey;
            char lastChar = name.optionName.charAt(name.optionName.length()-1);
            hasSuffix = lastChar == ':' || lastChar == '=';
        }

        Option(OptionName name, String descrKey) {
            this(name, null, descrKey);
        }

        Option(OptionName name, String descrKey, ChoiceKind choiceKind, String... choices) {
            this(name, descrKey, choiceKind, Arrays.asList(choices));
        }

        Option(OptionName name, String descrKey, ChoiceKind choiceKind, Collection<String> choices) {
            this(name, null, descrKey);
            if (choiceKind == null || choices == null)
                throw new NullPointerException();
            this.choiceKind = choiceKind;
            this.choices = choices;
        }

        @Override
        public String toString() {
            return name.optionName;
        }

        public boolean hasArg() {
            return argsNameKey != null && !hasSuffix;
        }

        public boolean matches(String option) {
            if (!hasSuffix)
                return option.equals(name.optionName);

            if (!option.startsWith(name.optionName))
                return false;

            if (choices != null) {
                String arg = option.substring(name.optionName.length());
                if (choiceKind == ChoiceKind.ONEOF)
                    return choices.contains(arg);
                else {
                    for (String a: arg.split(",+")) {
                        if (!choices.contains(a))
                            return false;
                    }
                }
            }

            return true;
        }

        /** Print a line of documentation describing this option, if standard.
         * @param out the stream to which to write the documentation
         */
        void help(PrintWriter out) {
            String s = "  " + helpSynopsis();
            out.print(s);
            for (int j = Math.min(s.length(), 28); j < 29; j++) out.print(" ");
            Log.printLines(out, Main.getLocalizedString(descrKey));
        }

        String helpSynopsis() {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            if (argsNameKey == null) {
                if (choices != null) {
                    String sep = "{";
                    for (String c: choices) {
                        sb.append(sep);
                        sb.append(c);
                        sep = ",";
                    }
                    sb.append("}");
                }
            } else {
                if (!hasSuffix)
                    sb.append(" ");
                sb.append(Main.getLocalizedString(argsNameKey));
            }

            return sb.toString();
        }

        /** Print a line of documentation describing this option, if non-standard.
         *  @param out the stream to which to write the documentation
         */
        void xhelp(PrintWriter out) {}

        /** Process the option (with arg). Return true if error detected.
         */
        public boolean process(Options options, String option, String arg) {
            if (options != null) {
                if (choices != null) {
                    if (choiceKind == ChoiceKind.ONEOF) {
                        // some clients like to see just one of option+choice set
                        for (String c: choices)
                            options.remove(option + c);
                        String opt = option + arg;
                        options.put(opt, opt);
                        // some clients like to see option (without trailing ":")
                        // set to arg
                        String nm = option.substring(0, option.length() - 1);
                        options.put(nm, arg);
                    } else {
                        // set option+word for each word in arg
                        for (String a: arg.split(",+")) {
                            String opt = option + a;
                            options.put(opt, opt);
                        }
                    }
                }
                options.put(option, arg);
            }
            return false;
        }

        /** Process the option (without arg). Return true if error detected.
         */
        public boolean process(Options options, String option) {
            if (hasSuffix)
                return process(options, name.optionName, option.substring(name.optionName.length()));
            else
                return process(options, option, option);
        }

        public OptionKind getKind() { return OptionKind.NORMAL; }

        public OptionName getName() { return name; }
    };

    /** A nonstandard or extended (-X) option
     */
    static class XOption extends Option {
        XOption(OptionName name, String argsNameKey, String descrKey) {
            super(name, argsNameKey, descrKey);
        }
        XOption(OptionName name, String descrKey) {
            this(name, null, descrKey);
        }
        XOption(OptionName name, String descrKey, ChoiceKind kind, String... choices) {
            super(name, descrKey, kind, choices);
        }
        XOption(OptionName name, String descrKey, ChoiceKind kind, Collection<String> choices) {
            super(name, descrKey, kind, choices);
        }
        @Override
        void help(PrintWriter out) {}
        @Override
        void xhelp(PrintWriter out) { super.help(out); }
        @Override
        public OptionKind getKind() { return OptionKind.EXTENDED; }
    };

    /** A hidden (implementor) option
     */
    static class HiddenOption extends Option {
        HiddenOption(OptionName name) {
            super(name, null, null);
        }
        HiddenOption(OptionName name, String argsNameKey) {
            super(name, argsNameKey, null);
        }
        @Override
        void help(PrintWriter out) {}
        @Override
        void xhelp(PrintWriter out) {}
        @Override
        public OptionKind getKind() { return OptionKind.HIDDEN; }
    };

}
