/*
 * Gap Data
 * Copyright (C) 2009 John Pritchard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package gap.jbx;

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
