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


/**
 * TODO: describe gap.jac.OptionName
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 */
public enum OptionName {
    G("-g"),
    G_NONE("-g:none"),
    G_CUSTOM("-g:"),
    XLINT("-Xlint"),
    XLINT_CUSTOM("-Xlint:"),
    NOWARN("-nowarn"),
    VERBOSE("-verbose"),
    DEPRECATION("-deprecation"),
    CLASSPATH("-classpath"),
    CP("-cp"),
    SOURCEPATH("-sourcepath"),
    BOOTCLASSPATH("-bootclasspath"),
    XBOOTCLASSPATH_PREPEND("-Xbootclasspath/p:"),
    XBOOTCLASSPATH_APPEND("-Xbootclasspath/a:"),
    XBOOTCLASSPATH("-Xbootclasspath:"),
    EXTDIRS("-extdirs"),
    DJAVA_EXT_DIRS("-Djava.ext.dirs="),
    ENDORSEDDIRS("-endorseddirs"),
    DJAVA_ENDORSED_DIRS("-Djava.endorsed.dirs="),
    PROC("-proc:"),
    PROCESSOR("-processor"),
    PROCESSORPATH("-processorpath"),
    D("-d"),
    S("-s"),
    IMPLICIT("-implicit:"),
    ENCODING("-encoding"),
    SOURCE("-source"),
    TARGET("-target"),
    VERSION("-version"),
    FULLVERSION("-fullversion"),
    HELP("-help"),
    A("-A"),
    X("-X"),
    J("-J"),
    MOREINFO("-moreinfo"),
    WERROR("-Werror"),
    COMPLEXINFERENCE("-complexinference"),
    PROMPT("-prompt"),
    DOE("-doe"),
    PRINTSOURCE("-printsource"),
    WARNUNCHECKED("-warnunchecked"),
    XMAXERRS("-Xmaxerrs"),
    XMAXWARNS("-Xmaxwarns"),
    XSTDOUT("-Xstdout"),
    XPRINT("-Xprint"),
    XPRINTROUNDS("-XprintRounds"),
    XPRINTPROCESSORINFO("-XprintProcessorInfo"),
    XPREFER("-Xprefer:"),
    O("-O"),
    XJCOV("-Xjcov"),
    XD("-XD"),
    SOURCEFILE("sourcefile");

    public final String optionName;

    OptionName(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public String toString() {
        return optionName;
    }

}
