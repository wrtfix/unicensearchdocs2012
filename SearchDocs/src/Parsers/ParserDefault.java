/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Parsers;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Author: Arturo Beltran
 */


import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class ParserDefault implements Parser {

        private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.application("hello"));
        public static final String HELLO_MIME_TYPE = "application/hello";

        public Set<MediaType> getSupportedTypes(ParseContext context) {
                return SUPPORTED_TYPES;
        }

        public void parse(
                        InputStream stream, ContentHandler handler,
                        Metadata metadata, ParseContext context)
                        throws IOException, SAXException, TikaException {

                metadata.set(Metadata.CONTENT_TYPE, HELLO_MIME_TYPE);
                metadata.set("Hello", "World");

                XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
                xhtml.startDocument();
                xhtml.endDocument();
        }

        /**
         * @deprecated This method will be removed in Apache Tika 1.0.
         */
        public void parse(
                        InputStream stream, ContentHandler handler, Metadata metadata)
                        throws IOException, SAXException, TikaException {
                parse(stream, handler, metadata, new ParseContext());
        }
}
