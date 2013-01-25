package org.wicketstuff.nicedit;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * Contains various resource references
 * 
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class ResourceReferences {

    private ResourceReferences() {
    }

    /**
     * NicEdit javascript file will be downloaded by browser from remote location
     */
    public static final ResourceReference LATEST_NICEDIT_JS =
            new UrlResourceReference(Url.parse("http://js.nicedit.com/nicEdit-latest.js"));
    /**
     * Bundled NicEdit javascript source, version 0.9 r24
     */
    public static final ResourceReference NICEDIT_STANDARD_JS_SRC =
            new JavaScriptResourceReference(ResourceReferences.class, "nicEdit_src.js");
    /**
     * Bundled NicEdit compressed javascript, version 0.9 r24
     */
    public static final ResourceReference NICEDIT_STANDARD_JS_MIN =
            new JavaScriptResourceReference(ResourceReferences.class, "nicEdit_min.js");
    /**
     * Bundled NicEdit editor icons, version 0.9 r24
     */
    public static final ResourceReference NICEDITOR_ICONS =
            new PackageResourceReference(ResourceReferences.class, "nicEditorIcons.gif");
}
