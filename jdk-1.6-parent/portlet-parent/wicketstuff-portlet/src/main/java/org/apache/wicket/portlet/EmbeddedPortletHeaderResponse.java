/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.portlet;

import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.internal.HeaderResponse;
import org.apache.wicket.request.Response;
import org.apache.wicket.response.StringResponse;

/**
 * Portlet behaviour override of the {@link HeaderResponse} implementation, responsible for writing
 * header contributions from portlets to the body of the response, as opposed to the head.
 *
 * @author Ate Douma
 * @see HeaderResponse
 */
public class EmbeddedPortletHeaderResponse extends HeaderResponse {
    private final Response realResponse;
    private final StringResponse bufferedResponse;

    public EmbeddedPortletHeaderResponse(Response realResponse) {
        this.realResponse = realResponse;
        this.bufferedResponse = new StringResponse();
    }

    @Override
    public void render(HeaderItem headerItem) {
        if (!isClosed()) {
            if (headerItem instanceof CssUrlReferenceHeaderItem) {
                CssUrlReferenceHeaderItem cssUrlReferenceHeaderItem = (CssUrlReferenceHeaderItem) headerItem;
                String media = cssUrlReferenceHeaderItem.getMedia();
                String url = cssUrlReferenceHeaderItem.getUrl();

                CssRenderToken token = new CssRenderToken(url, media);
                if (!wasRendered(token)) {
                    // The CSS reference has not been written to the response yet
                    getResponse().write("<script type=\"text/javascript\">");
                    getResponse().write("var elem=document.createElement(\"link\");");
                    getResponse().write("elem.setAttribute(\"rel\",\"stylesheet\");");
                    getResponse().write("elem.setAttribute(\"type\",\"text/css\");");
                    getResponse().write("elem.setAttribute(\"href\",\"" + url + "\");");
                    if (media != null) {
                        getResponse().write("elem.setAttribute(\"media\",\"" + media + "\");");
                    }
                    getResponse().write("document.getElementsByTagName(\"head\")[0].appendChild(elem);");
                    getResponse().write("</script>");
                    getResponse().write("\n");
                    markRendered(token);
                }
            } else {
                super.render(headerItem);
            }
        }
    }

    @Override
    public void close() {
        super.close();
        // Automatically add <head> if necessary
        CharSequence output = bufferedResponse.getBuffer();
        if (output.length() > 0) {
            if (output.charAt(0) == '\r') {
                for (int i = 2; i < output.length(); i += 2) {
                    char ch = output.charAt(i);
                    if (ch != '\r') {
                        output = output.subSequence(i - 2, output.length());
                        break;
                    }
                }
            } else if (output.charAt(0) == '\n') {
                for (int i = 1; i < output.length(); i++) {
                    char ch = output.charAt(i);
                    if (ch != '\n') {
                        output = output.subSequence(i - 1, output.length());
                        break;
                    }
                }
            }
        }
        if (output.length() > 0) {
            realResponse.write("<span id=\"" + ThreadPortletContext.getNamespace() +
                    "_embedded_head\" style=\"display:none\">");
            realResponse.write(output);
            realResponse.write("</span>");
        }
        bufferedResponse.reset();
    }

    @Override
    protected Response getRealResponse() {
        return bufferedResponse;
    }

    private static class CssRenderToken {
        private final String url;
        private final String media;

        private CssRenderToken(String url, String media) {
            if (url == null) {
                throw new IllegalArgumentException("Url can't be null.");
            }
            this.url = url;
            this.media = media;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CssRenderToken that = (CssRenderToken) o;
            return url.equals(that.url) && (media != null ? media.equals(that.media) : that.media == null);
        }

        @Override
        public int hashCode() {
            return 31 * url.hashCode() + (media != null ? media.hashCode() : 0);
        }
    }
}
