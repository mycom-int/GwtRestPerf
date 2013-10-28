package org.restgwtperftest.client.cases;

import org.restgwtperftest.shared.model.ServiceConsts;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public class RequestBuilderCase implements Case {

	@Override
	public String getName() {
		return "Using GWT Request Builder ";
	}

	@Override
	public void findBigBeans(final int threadAmount, final int beanAmount,
			final int schPeriod) {
		for (int i = threadAmount; i > 0; i--) {
			Scheduler.get().scheduleFixedPeriod(new TestRestCall(i, threadAmount, beanAmount), 300);
		}
	}

	private static class TestRestCall implements RequestCallback,
			RepeatingCommand {
		private final int threadAmount; 
		private final int beanAmount;
		private int idx;
		public TestRestCall(int idx, int threadAmount, int beanAmount) {
			this.idx = idx;
			this.threadAmount = threadAmount;
			this.beanAmount = beanAmount;
		}

		@Override
		public boolean execute() {
			try {
				System.out.println(">>> start Service Call[" + idx + "] "
						+ "threadAmount:" + threadAmount + ", beanAmount:"
						+ beanAmount);
				String uri = ServiceConsts.SERVICE_PATH_CONTEXT_PREFIX+ServiceConsts.SERVICE_PATH_V1_SLOW+"/"+beanAmount;
				
				RequestBuilder reqBuilder = new RequestBuilder(
					RequestBuilder.GET, uri);
				reqBuilder.setHeader("Content-Type", "application/json");
				reqBuilder.setCallback(this);
				reqBuilder.send();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			System.out.println("success Loaded idx["+idx
					+ "]: ");
		}

		@Override
		public void onError(Request request, Throwable exception) {
			System.out.println("ERROR in thread["+idx+"], "+exception);
		}
	}

}
