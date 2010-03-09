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

import java.lang.annotation.Annotation;
import gap.jac.util.*;
import gap.jac.comp.*;
import gap.jac.tree.JCTree.*;
import gap.jac.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.*;
import java.util.*;

/**
 * Object providing state about a prior round of annotation processing.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
public class JavacRoundEnvironment implements RoundEnvironment {
    // Default equals and hashCode methods are okay.

    private final boolean processingOver;
    private final boolean errorRaised;
    private final ProcessingEnvironment processingEnv;

    // Caller must pass in an immutable set
    private final Set<? extends Element> rootElements;

    JavacRoundEnvironment(boolean processingOver,
                          boolean errorRaised,
                          Set<? extends Element> rootElements,
                          ProcessingEnvironment processingEnv) {
        this.processingOver = processingOver;
        this.errorRaised = errorRaised;
        this.rootElements = rootElements;
        this.processingEnv = processingEnv;
    }

    public String toString() {
        return String.format("[errorRaised=%b, rootElements=%s, processingOver=%b]",
                             errorRaised,
                             rootElements,
                             processingOver);
    }

    public boolean processingOver() {
        return processingOver;
    }

    /**
     * Returns {@code true} if an error was raised in the prior round
     * of processing; returns {@code false} otherwise.
     *
     * @return {@code true} if an error was raised in the prior round
     * of processing; returns {@code false} otherwise.
     */
    public boolean errorRaised() {
        return errorRaised;
    }

    /**
     * Returns the type elements specified by the prior round.
     *
     * @return the types elements specified by the prior round, or an
     * empty set if there were none
     */
    public Set<? extends Element> getRootElements() {
        return rootElements;
    }

    private static final String NOT_AN_ANNOTATION_TYPE =
        "The argument does not represent an annotation type: ";

    /**
     * Returns the elements annotated with the given annotation type.
     * Only type elements <i>included</i> in this round of annotation
     * processing, or declarations of members, parameters, or type
     * parameters declared within those, are returned.  Included type
     * elements are {@linkplain #getSpecifiedTypeElements specified
     * types} and any types nested within them.
     *
     * @param a  annotation type being requested
     * @return the elements annotated with the given annotation type,
     * or an empty set if there are none
     */
    public Set<? extends Element> getElementsAnnotatedWith(TypeElement a) {
        Set<Element> result = Collections.emptySet();
        if (a.getKind() != ElementKind.ANNOTATION_TYPE)
            throw new IllegalArgumentException(NOT_AN_ANNOTATION_TYPE + a);

        DeclaredType annotationTypeElement;
        TypeMirror tm = a.asType();
        if ( tm instanceof DeclaredType )
            annotationTypeElement = (DeclaredType) a.asType();
        else
            throw new AssertionError("Bad implementation type for " + tm);

        ElementScanner6<Set<Element>, DeclaredType> scanner =
            new AnnotationSetScanner(result);

        for (Element element : rootElements)
            result = scanner.scan(element, annotationTypeElement);

        return result;
    }

    // Could be written as a local class inside getElementsAnnotatedWith
    private class AnnotationSetScanner extends
        ElementScanner6<Set<Element>, DeclaredType> {
        // Insertion-order preserving set
        Set<Element> annotatedElements = new LinkedHashSet<Element>();

        AnnotationSetScanner(Set<Element> defaultSet) {
            super(defaultSet);
        }

        @Override
        public Set<Element> scan(Element e, DeclaredType p) {
            java.util.List<? extends AnnotationMirror> annotationMirrors =
                processingEnv.getElementUtils().getAllAnnotationMirrors(e);
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                if (annotationMirror.getAnnotationType().equals(p))
                    annotatedElements.add(e);
            }
            e.accept(this, p);
            return annotatedElements;
        }

    }

    /**
     * {@inheritdoc}
     */
    public Set<? extends Element> getElementsAnnotatedWith(Class<? extends Annotation> a) {
        if (!a.isAnnotation())
            throw new IllegalArgumentException(NOT_AN_ANNOTATION_TYPE + a);
        String name = a.getCanonicalName();
        if (name == null)
            return Collections.emptySet();
        else {
            TypeElement annotationType = processingEnv.getElementUtils().getTypeElement(name);
            if (annotationType == null)
                return Collections.emptySet();
            else
                return getElementsAnnotatedWith(annotationType);
        }
    }
}
