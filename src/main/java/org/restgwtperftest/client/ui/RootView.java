package org.restgwtperftest.client.ui;

import javax.annotation.Nullable;

import org.restgwtperftest.client.cases.RequestBuilderCase;
import org.restgwtperftest.client.cases.RestyGWTQueryCase;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class RootView extends Composite {
	@UiTemplate("RootView.ui.xml")
	public interface Binder extends UiBinder<Widget, RootView> {
	}

	@Nullable
	@UiField
	VerticalPanel testCasePanel;

	@Inject
	public RootView(Binder uiBinder, 
			CaseView.Factory fac,
			RestyGWTQueryCase restyCase,
			RequestBuilderCase reqBuilderCase
			) {
		initWidget(uiBinder.createAndBindUi(this));
		testCasePanel.add(fac.create(restyCase));
		testCasePanel.add(fac.create(reqBuilderCase));
	}
}
