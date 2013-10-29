package org.restgwtperftest.client.cases;

import java.util.List;

import javax.annotation.Nullable;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.restgwtperftest.client.api.SimpleRestServiceClient;
import org.restgwtperftest.shared.model.BigBean;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.inject.Inject;

public class RestyGWTQueryCase implements Case{
	@Inject
	@Nullable
	private SimpleRestServiceClient simpleClient;

	@Override
	public void findBigBeans(final int threadAmount, final int beanAmount,final int schPeriod) {
		for (int i = threadAmount; i >0; i--) {
			final int idx = i;
			Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
				@Override
				public boolean execute() {
					System.out.println(">>> RestGWT getAllBigBeans["+idx+"] "
							+ "threadAmount:"+threadAmount+", beanAmount:"+beanAmount);
					simpleClient.get(
							beanAmount,
							new MethodCallback<List<BigBean>>() {
								@Override
								public void onSuccess(Method method,
										List<BigBean> beans) {
									System.out.println("success getAllBigBeans idx["+idx
											+ "]: " + beans.size());
								}
								@Override
								public void onFailure(Method method,
										Throwable exception) {
									System.out.println("ERROR in thread["+idx+"], "+exception);
								}
							});
					return false;
				}
			}, 300);
		}
	}
	@Override
	public void doLongOperation(int threadAmount, final int sleepMillis, int schPeriod) {
		for (int i = threadAmount; i >0; i--) {
			final int idx = i;
			Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
				@Override
				public boolean execute() {
					System.out.println(">>> RestGWT: doLongOp["+idx+"] "
							+ "sleepMillis:"+sleepMillis);
					simpleClient.doLongOperation(sleepMillis, new MethodCallback<Void>(){
						@Override
						public void onSuccess(Method method, Void response) {
							System.out.println("success doLongOp idx["+idx
									+ "] ");
						}
						@Override
						public void onFailure(Method method, Throwable exception) {
							System.out.println("ERROR in thread["+idx+"], "+exception);
						}
					});
					return false;
				}
			}, 300);
		}		
	}
	@Override
	public String getName() {
		return "Using RestyGWT client";
	}


}
