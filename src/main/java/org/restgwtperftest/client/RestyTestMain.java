package org.restgwtperftest.client;

import org.fusesource.restygwt.client.Defaults;
import org.restgwtperftest.client.inject.Injector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class RestyTestMain implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Defaults.setServiceRoot(GWT.getHostPageBaseURL());

		GWT.setUncaughtExceptionHandler(NovaExceptionHandler.get());

		Injector.INSTANCE.getApplication();// .getHistoryHandler().handleCurrentHistory();

		RootPanel.get("h1Title").getElement().setInnerText("Hello World!!!");
		RootPanel root = RootPanel.get("root");
		root.add(Injector.INSTANCE.getRootView());		
	}
}
