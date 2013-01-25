package org.wicketstuff.nicedit.examples.ui;

import java.util.Collections;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.nicedit.NicEditor;

/**
 * @author msabo (marek.sabo@mgm-tp.com)
 */
public class EnhancedExamplePage extends WebPage {
    private static final long serialVersionUID = 7681942921122080242L;
    private String content = "looper";

    public EnhancedExamplePage() {
        Form<Void> form = new Form<Void>("form");

        final WebMarkupContainer editorControl;
        form.add(editorControl = new WebMarkupContainer("editorControl"));
        final TextArea<String> rte;
        form.add(rte = new TextArea<String>("editor", PropertyModel.<String> of(this, "content")));
        form.add(new NicEditor("editorControl", Collections.singleton("editor"), null));
        form.add(new Button("submitButton", Model.of("Submit")));
        add(form);
        final Label contentLabel;
        add(contentLabel = new Label("contentLabel", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return content;
            }
        }));
        contentLabel.setOutputMarkupId(true);
        contentLabel.add(new AjaxEventBehavior("click") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                contentLabel.setEscapeModelStrings(!contentLabel.getEscapeModelStrings());
                target.add(contentLabel);
            }
        });
    }
}
