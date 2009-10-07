/*
 * Copyright (c) 2009 John Pritchard, JBXML Project Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jbxml;

import javax.xml.stream.Location;

/**
 * A first class member of the bean graph receives essential graph
 * events.  Implementors are graph branch nodes.  Non implementors are
 * graph leaf nodes (property values).
 * 
 * @author jdp
 */
public interface Component {

    /**
     * An object class implementing this interface has this event
     * called on each child during parsing.
     */
    public interface Parent
        extends Component
    {
        /**
         * Called from the child's WTKX Start Element after the
         * child's set bean.
         */
        public void startElement(Component child);
    }

    /**
     * An object class implementing this interface receives non
     * whitespace text.
     */
    public interface AddText 
        extends Component
    {

        public void addElementText(Location loc, String characters);
    }

    public interface Root 
        extends Component
    {
        /**
         * Called under processor "read object url" usage.
         */
        public void setDocumentBase(java.net.URL url);
        /**
         * 
         */
        public java.net.URL getInputURL(String path)
            throws java.net.MalformedURLException;
        /**
         * 
         */
        public java.io.InputStream getInputStream(String path)
            throws java.io.IOException;
    }
    public interface Child 
        extends Component
    {

        public void setParent(Component parent);
    }

    public Reflector getReflect();

    public Component getParent();

    /**
     * Called from Start Element
     */
    public void startElement(Reflector bean);

    /**
     * Called from End Element.  This is a practical graph node
     * initialization.  The parent node is not complete, but this node
     * and its children are complete.
     */
    public void endElement();
}
