package org.wicketstuff.nicedit.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.nicedit.examples.ui.HomePage;

/**
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class WicketNicEditApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }
}
