package org.wicketstuff.nicedit;

import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class NicEditor extends Behavior {

    private String editorId;
    private String editorPanelId;
    private Set<String> editorInstanceIds;
    private EditorOptions editorOptions;

    /**
     * Similar as {@link #NicEditor(String, EditorOptions)} but will try to construct editor on component this behaviour
     * is attached to. Also uses default editor settings.
     */
    public NicEditor() {
        editorOptions = getDefaultEditorOptions();
    }

    /**
     * @param editorId id of element that will be used as editor with panel
     * @param editorOptions if null, default values will be used
     */
    public NicEditor(String editorId, EditorOptions editorOptions) {
        this.editorId = editorId;
        this.editorOptions = editorOptions == null ? getDefaultEditorOptions() : editorOptions;
    }

    /**
     * @param editorPanelId id of element for editor control panel
     * @param editorInstanceIds list of ids of elements for editors themselves
     * @param editorOptions if null, default values will be used
     */
    public NicEditor(String editorPanelId, Set<String> editorInstanceIds, EditorOptions editorOptions) {
        this.editorPanelId = editorPanelId;
        this.editorInstanceIds = editorInstanceIds;
        this.editorOptions = editorOptions == null ? getDefaultEditorOptions() : editorOptions;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
        if (Form.class.isAssignableFrom(component.getClass())) {
            if (tag.getAttribute("onsubmit") != null) {
                tag.put("onsubmit", tag.getAttribute("onsubmit") + ";" + getEditorContentSaveJS());
            } else {
                tag.put("onsubmit", getEditorContentSaveJS());
            }
        }
    }

    protected EditorOptions getDefaultEditorOptions() {
        return new EditorOptions()
                .setIconsPath(RequestCycle.get().urlFor(ResourceReferences.NICEDITOR_ICONS, null).toString());
    }

    private String getEditorContentSaveJS() {
        return "for (var i = 0; i < nicEditors.editors.length; i++) { nicEditors.editors[i].nicInstances[0].saveContent(); }; return true;";
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        response.render(JavaScriptHeaderItem.forScript(getEditorInitJS(component), "wicket-nicedit"));
    }

    protected String getEditorInitJS(Component component) {
        String initScript = "var currentEditor = new nicEditor(" + editorOptions.buildEditorConfigJs() + ");\n";
        if (editorId != null) {
            initScript = initScript + "currentEditor.panelInstance('" + editorId + "');\n";
        } else if (editorPanelId == null) {
            initScript = initScript + "currentEditor.panelInstance('" + component.getMarkupId() + "');\n";
        } else {
            initScript = initScript + "currentEditor.setPanel('" + editorPanelId + "');\n";
            for (String editorInstanceId : editorInstanceIds) {
                initScript = initScript + " currentEditor.addInstance('" + editorInstanceId + "');\n";
            }
        }
        return initScript;
    }
}
