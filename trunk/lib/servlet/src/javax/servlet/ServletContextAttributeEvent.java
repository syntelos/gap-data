

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * Portions Copyright Apache Software Foundation.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.servlet;


	/** 
	* This is the event class for notifications about changes to the attributes of the
	*  servlet context of a web application.
	* @see ServletContextAttributeListener
	 * @since	v 2.3
	*/

public class ServletContextAttributeEvent extends ServletContextEvent { 
	private String name;
	private Object value;

	/** Construct a ServletContextAttributeEvent from the given context for the
	** given attribute name and attribute value. 
	*/
	public ServletContextAttributeEvent(ServletContext source, String name, Object value) {
	    super(source);
	    this.name = name;
	    this.value = value;
	}
	
	/**
	* Return the name of the attribute that changed on the ServletContext.
	*
	*/
	public String getName() {
		return this.name;
	}
	
	/**
	* Returns the value of the attribute that has been added, removed, or replaced.
	* If the attribute was added, this is the value of the attribute. If the attribute was
	* removed, this is the value of the removed attribute. If the attribute was replaced, this
	* is the old value of the attribute.
	*
	*/
	
	public Object getValue() {
	    return this.value;   
	}
}

