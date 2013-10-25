package org.restgwtperftest.client;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.restgwtperftest.client.inject.Injector;
import org.restgwtperftest.share.model.BigBean;
import org.restgwtperftest.share.model.SimpleRestServiceClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.ui.RootPanel;

public class RestyTestMain implements EntryPoint {
	@Inject
	@Nullable
	private SimpleRestServiceClient simpleClient;

	@Override
	public void onModuleLoad() {
		Defaults.setServiceRoot(GWT.getHostPageBaseURL());

		GWT.setUncaughtExceptionHandler(NovaExceptionHandler.get());

		Injector.INSTANCE.getApplication();// .getHistoryHandler().handleCurrentHistory();

		RootPanel.get("h1Title").getElement().setInnerText("Hello World!!!");
		for (int i = 0; i < 10; i++) {
			Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

				@Override
				public boolean execute() {
					simpleClient.get(
							"3419866788-27",
							new MethodCallback<BigBean>() {
								@Override
								public void onSuccess(Method method,
										BigBean response) {
									System.out.println("success Loaded: "
											+ response);
								}

								@Override
								public void onFailure(Method method,
										Throwable exception) {
									System.out.println();
								}
							});
					return false;
				}
			}, 1000);
		}
	}
}
