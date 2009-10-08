/*
 * Copyright (c) 2008 VMware, Inc.
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

/**
 * Thrown when a caller attempts to set the value of a non-existent bean
 * property.
 *
 * @author gbrown
 * @author jdp
 */
public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException() {
        this(null, null);
    }

    public PropertyNotFoundException(String message) {
        this(message, null);
    }

    public PropertyNotFoundException(Throwable cause) {
        this(null, cause);
    }

    public PropertyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
