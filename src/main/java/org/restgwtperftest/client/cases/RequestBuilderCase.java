package org.restgwtperftest.client.cases;

import org.fusesource.restygwt.client.ObjectEncoderDecoder;
import org.restgwtperftest.shared.model.ServiceConsts;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class RequestBuilderCase implements Case {

	@Override
	public String getName() {
		return "Using GWT Request Builder ";
	}

	@Override
	public void findBigBeans(final int threadAmount, final int beanAmount,
			final int schPeriod) {
		for (int i = threadAmount; i > 0; i--) {
			Scheduler.get().scheduleFixedPeriod(
					new QueryFindAllBigBeans(i, beanAmount), 300);
		}
	}

	@Override
	public void doLongOperation(int threadAmount, int sleepMillis, int schPeriod) {
		for (int i = threadAmount; i > 0; i--) {
			Scheduler.get().scheduleFixedPeriod(
					new QueryLongOperation(i, sleepMillis), 300);
		}
	}

	class QueryFindAllBigBeans extends RestQuery {

		private int beanAmount;

		public QueryFindAllBigBeans(int idx, int beanAmount) {
			super(idx);
			this.beanAmount = beanAmount;
		}
		public boolean execute(){
			System.out.println(">>> RequestBuilder QueryBigBeans  [" + idx + "] beanAmount:"+beanAmount);
			return super.execute();
		}
		@Override
		public void onResponseReceived(Request request, Response response) {
			JSONArray value = (JSONArray) JSONParser.parseStrict(response.getText());
			System.out.println("success Loaded idx[" + idx + "]: array size:"
					+ value.size());
			ObjectEncoderDecoder endeCoder = new ObjectEncoderDecoder();
			endeCoder.decode(value );
		}
		@Override
		protected String getRequestURL() {
			String uri = ServiceConsts.SERVICE_PATH_CONTEXT_PREFIX
					+ ServiceConsts.SERVICE_PATH_V1_SLOW + "/" + beanAmount;
			return uri;
		}
	}
	class QueryLongOperation extends RestQuery {

		private int sleepMillis;

		public QueryLongOperation(int idx, int sleepMillis) {
			super(idx);
			this.sleepMillis = sleepMillis;
		}
		public boolean execute(){
			System.out.println(">>> RequestBuilder doLongOperation  [" + idx + "] sleepMillis:"+sleepMillis);
			return super.execute();
		}
		@Override
		public void onResponseReceived(Request request, Response response) {
			System.out.println("success Loaded idx[" + idx + "]: content length:"
					+ response.getText().length());
		}
		@Override
		protected String getRequestURL() {
			String uri = ServiceConsts.SERVICE_PATH_CONTEXT_PREFIX
					+ ServiceConsts.RELATIVE_PATH_LONG_OPERATION + "/" + sleepMillis;
			return uri;
		}
	}
}

abstract class RestQuery implements RequestCallback, RepeatingCommand {
	protected int idx;

	public RestQuery(int idx) {
		this.idx = idx;
	}

	@Override
	public boolean execute() {
		try {
			RequestBuilder reqBuilder = new RequestBuilder(RequestBuilder.GET,
					getRequestURL());
			reqBuilder.setHeader("Content-Type", "application/json");
			reqBuilder.setCallback(this);
			reqBuilder.send();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	abstract protected String getRequestURL();


	@Override
	public void onError(Request request, Throwable exception) {
		System.out.println("ERROR in thread[" + idx + "], " + exception);
	}
}