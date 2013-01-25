package org.wicketstuff.nicedit.examples.ui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 3143951867526267574L;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new BookmarkablePageLink<BasicExamplePage>("basicExampleLink", BasicExamplePage.class));
        add(new BookmarkablePageLink<BasicExamplePage>("styledExampleLink", StyledExamplePage.class));
        add(new BookmarkablePageLink<BasicExamplePage>("enhancedExampleLink", StyledExamplePage.class));
    }
}
