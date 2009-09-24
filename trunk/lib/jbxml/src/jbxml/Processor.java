/*
 * Copyright (c) 2009 VMware, Inc.
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

/**
 * Loads an object bean graph from a XML document.  
 *
 * @author gbrown
 * @author jdp
 * @since 1.6
 */
public class Processor
    extends Resolver
{

    public Processor(Component.Root root, Dictionary<String, ?> resources) {
        super(root,resources);
    }
    public Processor(Resolver parent) {
        super(parent);
    }

    /**
     * This method does not close the input argument stream.
     */
    public Object readObject(InputStream inputStream)
        throws IOException
    {
        if (null == inputStream)
            return null;
        else {

            this.getBindings().clear();

            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty("javax.xml.stream.isCoalescing", true);

            Object rvalue = null;
            this.enter();
            try {
                XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

                Element element = null;

                while (reader.hasNext()) {
                    switch (reader.next()) {

                    case XMLStreamConstants.CHARACTERS:

                        if (!reader.isWhiteSpace()) {

                            Location loc = reader.getLocation();

                            String text = reader.getText();

                            if (text.length() > 0) {

                                element.addText(loc,text);
                            }
                        }
                        break;

                    case XMLStreamConstants.START_ELEMENT:

                        element = new Element(element,this,reader);

                        if (element.hasId && element.hasStaticValue){
                            Object value = element.getValue();
                            Object test = this.get(element.id);
                            if (null == test)
                                this.put(element.id, value);
                            else if (test != value){
                                Class valueClass = value.getClass();
                                Class testClass = test.getClass();
                                if (valueClass.equals(testClass))
                                    throw new PropertyFormatException("Collision in global identifier \""+element.id+"\" in two instances of bean class \""+testClass.getName()+"\".");
                                else
                                    throw new PropertyFormatException("Collision in global identifier \""+element.id+"\" in bean classes \""+testClass.getName()+"\" and \""+valueClass.getName()+"\".");
                            }
                        }
                        break;


                    case XMLStreamConstants.END_ELEMENT:

                        element.applyAttributes(this);
                        /*
                         * Not done previously, state fixed at element end.
                         */
                        Element parent = element.parent;
                        if (null != parent){
                            if (parent.isComponentParent){
                                try {
                                    if (parent.maySetValue())

                                        parent.setValue(element.getValue());

                                    else if (parent.maySetValue2(element))

                                        parent.setValue2(element);
                                }
                                catch (PropertyNotFoundException ignore){
                                }
                            }
                            else {
                                if (parent.maySetValue())

                                    parent.setValue(element.getValue());

                                else if (parent.maySetValue2(element))

                                    parent.setValue2(element);
                            }
                        }
                        /*
                         */
                        element.end();

                        /*
                         * If this is the top of the stack, return
                         * this element's value; otherwise, move up
                         * the stack
                         */
                        if (element.parent == null)

                            rvalue = element.getValue();
                        else 
                            element = element.parent;

                        break;
                    }
                }
                reader.close();
            }
            catch (XMLStreamException exception) {
                if (null != this.location)
                    throw new IOException(this.location.toExternalForm(),exception);
                else
                    throw new IOException(exception);
            }
            finally {
                this.exit();
            }

            if (null != this.location && rvalue instanceof Component.Root)
                ((Component.Root)rvalue).setDocumentBase(this.location);

            return rvalue;
        }
    }

}
