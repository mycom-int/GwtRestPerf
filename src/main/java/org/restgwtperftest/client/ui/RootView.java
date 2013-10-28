package org.restgwtperftest.client.ui;

import javax.annotation.Nullable;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RootView extends Composite {
	@UiTemplate("RootView.ui.xml")
	public interface Binder extends UiBinder<Widget, RootView> {
	}

	@Nullable
	@UiField
	FlowPanel restyGWTTestPanel;

	@Inject
	public RootView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
