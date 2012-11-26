package org.apache.wicket.portlet;

import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;

import java.util.Arrays;
import java.util.Collections;

/**
 * HeaderItem implementation that ensures correct URL rendering for JavaScript resource references
 * both in portal and standalone deployment.
 * <p/>
 * This is needed because of regression introduced by https://issues.apache.org/jira/browse/WICKET-4645
 * <p/>
 * Problem is that current wicket-core code uses
 * {@link RequestCycle#urlFor(org.apache.wicket.request.resource.ResourceReference, org.apache.wicket.request.mapper.parameter.PageParameters)}
 * to render URL string, which from Wicket 6.1.0 ignores absolute URLs
 * 
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class JavaScriptReferencePortletHeaderItem extends JavaScriptReferenceHeaderItem {
    public JavaScriptReferencePortletHeaderItem(ResourceReference reference) {
        this(reference, null, null, false, null, null);
    }

    /**
     * @param reference resource reference pointing to the javascript resource
     * @param pageParameters the parameters for this Javascript resource reference
     * @param id id that will be used to filter duplicate reference (it's still filtered by URL
     *            too)
     * @param defer specifies that the execution of a script should be deferred (delayed) until after
     *            the page has been loaded.
     * @param charset a non null value specifies the charset attribute of the script tag
     * @param condition the condition to use for Internet Explorer conditional comments. E.g. "IE 7".
     */
    public JavaScriptReferencePortletHeaderItem(ResourceReference reference, PageParameters pageParameters, String id,
            boolean defer, String charset, String condition) {
        super(reference, pageParameters, id, defer, charset, condition);
    }

    @Override
    public Iterable<?> getRenderTokens() {
        String token = "javascript-" + Strings.stripJSessionId(getUrl());
        return Strings.isEmpty(getId()) ? Collections.singletonList(token)
                : Arrays.asList("javascript-" + getId(), token);
    }

    @Override
    public void render(Response response) {
        internalRenderJavaScriptReference(response, getUrl(), getId(), isDefer(), getCharset(), getCondition());
    }

    private String getUrl() {
        // This returns URL from specific mappers
        Url url = RequestCycle.get().mapUrlFor(getReference(), getPageParameters());
        // This ensures that absolute URL returned by PortletRequestMapper is preserved and not relativized
        if (url.isAbsolute()) {
            return url.toString();
        } else {
            return RequestCycle.get().getUrlRenderer().renderUrl(url);
        }
    }
}
