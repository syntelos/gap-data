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

import javax.annotation.*;
import gap.jac.tools.Diagnostic;
import javax.lang.model.element.*;

/**
 * A {@code Messager} provides the way for an annotation processor to
 * report error messages, warnings, and other notices.  Elements,
 * annotations, and annotation values can be passed to provide a
 * location hint for the message.  However, such location hints may be
 * unavailable or only approximate.
 *
 * <p>Printing a message with an {@linkplain
 * gap.jac.tools.Diagnostic.Kind#ERROR error kind} will {@linkplain
 * RoundEnvironment#errorRaised raise an error}.
 *
 * <p>Note that the messages &quot;printed&quot; by methods in this
 * interface may or may not appear as textual output to a location
 * like {@link System#out} or {@link System#err}.  Implementations may
 * choose to present this information in a different fashion, such as
 * messages in a window.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see ProcessingEnvironment#getLocale
 * @since 1.6
 */
public interface Messager {
    /**
     * Prints a message of the specified kind.
     *
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg);

    /**
     * Prints a message of the specified kind at the location of the
     * element.
     *
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param e    the element to use as a position hint
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e);

    /**
     * Prints a message of the specified kind at the location of the
     * annotation mirror of the annotated element.
     *
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param e    the annotated element
     * @param a    the annotation to use as a position hint
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a);

    /**
     * Prints a message of the specified kind at the location of the
     * annotation value inside the annotation mirror of the annotated
     * element.
     *
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param e    the annotated element
     * @param a    the annotation containing the annotation value
     * @param v    the annotation value to use as a position hint
     */
    void printMessage(Diagnostic.Kind kind,
                      CharSequence msg,
                      Element e,
                      AnnotationMirror a,
                      AnnotationValue v);
}
