package org.restgwtperftest.client.ui;

import java.util.List;

import javax.annotation.Nullable;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.restgwtperftest.client.api.SimpleRestServiceClient;
import org.restgwtperftest.shared.model.BigBean;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

//extract presenter if needed.... 
public class RestyGWTSlowTestView extends Composite {
	@UiTemplate("RestyGWTSlowTestView.ui.xml")
	public interface Binder extends UiBinder<Widget, RestyGWTSlowTestView> {
	}
	@Inject
	@Nullable
	private SimpleRestServiceClient simpleClient;
	
	
	@Nullable
	@UiField
	TextBox threadAmountTxb;
	@Nullable
	@UiField
	TextBox beanAmountTxb;
	@Nullable
	@UiField
	Button startBtn;

	@Inject
	public RestyGWTSlowTestView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		//TODO
	}
	
	public void perform()
	{
		for (int i = 0; i < 10; i++) {
			Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

				@Override
				public boolean execute() {
					simpleClient.get(
							20,
							new MethodCallback<List<BigBean>>() {
								@Override
								public void onSuccess(Method method,
										List<BigBean> beans) {
									System.out.println("success Loaded: "
											+ beans.size());
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
