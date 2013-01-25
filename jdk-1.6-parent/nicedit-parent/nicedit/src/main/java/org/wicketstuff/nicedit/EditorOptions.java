package org.wicketstuff.nicedit;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.wicket.util.string.Strings;

/**
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class EditorOptions {
    private Boolean fullPanel;
    private LinkedHashSet<Button> buttonList;
    private String iconsPath;
    private Integer maxHeight;
    private String externalCSS;
    private String uploadURI;

    public boolean getFullPanel() {
        return fullPanel;
    }

    public EditorOptions setFullPanel(boolean fullPanel) {
        this.fullPanel = fullPanel;
        return this;
    }

    public Set<Button> getButtonList() {
        return new LinkedHashSet<Button>(buttonList);
    }

    public EditorOptions addToButtonList(Button button) {
        if (buttonList != null) {
            buttonList.add(button);
        }
        return this;
    }

    public EditorOptions addToButtonList(Collection<Button> buttonList) {
        if (this.buttonList != null) {
            this.buttonList.addAll(buttonList);
        }
        return this;
    }

    public void setButtonList(Collection<Button> buttonList) {
        this.buttonList = new LinkedHashSet<Button>(buttonList);
    }

    public String getIconsPath() {
        return iconsPath;
    }

    public EditorOptions setIconsPath(String iconsPath) {
        this.iconsPath = iconsPath;
        return this;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public EditorOptions setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public String getExternalCSS() {
        return externalCSS;
    }

    public EditorOptions setExternalCSS(String externalCSS) {
        this.externalCSS = externalCSS;
        return this;
    }

    public String getUploadURI() {
        return uploadURI;
    }

    public EditorOptions setUploadURI(String uploadURI) {
        this.uploadURI = uploadURI;
        return this;
    }

    public String buildEditorConfigJs() {
        String config = "{";
        if (fullPanel != null) {
            config = config + "fullPanel : " + fullPanel + ", ";
        }
        if (buttonList != null) {
            config = config + "buttonList : ";
            String buttons = "[";
            for (Button button : buttonList) {
                buttons = buttons + button + ", ";
            }
            buttons = stripSeparatorAtEnd(buttons, ", ") + "]";
            config = config + buttons + ", ";
        }
        if (!Strings.isEmpty(iconsPath)) {
            config = config + "iconsPath : '" + iconsPath + "', ";
        }
        if (maxHeight != null) {
            config = config + "maxHeight : " + maxHeight + ", ";
        }
        if (!Strings.isEmpty(externalCSS)) {
            config = config + "externalCSS : '" + externalCSS + "', ";
        }
        if (!Strings.isEmpty(uploadURI)) {
            config = config + "uploadURI : '" + uploadURI + "', ";
        }

        return stripSeparatorAtEnd(config, ", ") + "}";
    }

    private String stripSeparatorAtEnd(String source, String separator) {
        int cut = source.lastIndexOf(separator);
        if (cut > -1) {
            source = source.substring(0, cut);
        }
        return source;
    }
}
