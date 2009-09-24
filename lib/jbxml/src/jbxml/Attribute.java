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

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;


/**
 * DOM attribute of element.
 * 
 * @author jdp
 */
public final class Attribute {

    public final String namespaceURI;
    public final String prefix;
    public final String localName;
    public final String value;
    public final boolean isId;

    public Attribute(Element el, Resolver io, XMLStreamReader reader, int idx)
        throws XMLStreamException
    {
        super();
        this.namespaceURI = reader.getAttributeNamespace(idx);
        this.prefix = reader.getAttributePrefix(idx);
        this.localName = reader.getAttributeLocalName(idx);
        this.value = reader.getAttributeValue(idx);
        this.isId = io.isId(this.localName);
    }
}
