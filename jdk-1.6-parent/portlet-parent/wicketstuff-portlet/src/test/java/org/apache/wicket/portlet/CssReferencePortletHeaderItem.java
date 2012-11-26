package org.apache.wicket.portlet;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;

import java.util.Arrays;

/**
 * HeaderItem implementation that ensures correct URL rendering for CSS resource references
 * both in portal and standalone deployment.
 * <p/>
 * This is needed because of regression introduced by https://issues.apache.org/jira/browse/WICKET-4645
 * <p/>
 * Problem is that current wicket-core code uses
 * {@link org.apache.wicket.request.cycle.RequestCycle#urlFor(org.apache.wicket.request.resource.ResourceReference, org.apache.wicket.request.mapper.parameter.PageParameters)}
 * to render URL string, which from Wicket 6.1.0 ignores absolute URLs
 * 
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class CssReferencePortletHeaderItem extends CssReferenceHeaderItem {
    public CssReferencePortletHeaderItem(ResourceReference reference) {
        this(reference, null, null, null);
    }

    /**
     * @param reference resource reference pointing to the CSS resource
     * @param pageParameters the parameters for this CSS resource reference
     * @param media the media type for this CSS ("print", "screen", etc.)
     * @param condition the condition to use for Internet Explorer conditional comments. E.g. "IE 7".
     */
    public CssReferencePortletHeaderItem(ResourceReference reference, PageParameters pageParameters, String media,
            String condition) {
        super(reference, pageParameters, media, condition);
    }

    @Override
    public Iterable<?> getRenderTokens() {
        return Arrays.asList("css-" + Strings.stripJSessionId(getUrl()) + "-" + getMedia());
    }

    @Override
    public void render(Response response) {
        internalRenderCSSReference(response, getUrl(), getMedia(), getCondition());
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
