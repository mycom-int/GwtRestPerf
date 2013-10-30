package org.restgwtperftest.client;

import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;
import org.restgwtperftest.client.concurrent.TaskQueue;
import org.restgwtperftest.client.concurrent.TaskQueue.Task;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

public class QueuedResponseDispatcher implements Dispatcher {
	public static final QueuedResponseDispatcher INSTANCE = new QueuedResponseDispatcher();
	
	private TaskQueue taskQueue = new TaskQueue();
	@Override
	public Request send(Method method, RequestBuilder builder)
			throws RequestException {
		RequestCallback callback ;
//		callback = new QueuedRequestCallbackWrapper(builder.getCallback(), taskQueue);
		callback = new DeferredRequestCallbackWrapper(builder.getCallback(), Scheduler.get());
		//TODO wrap callback with delayed process
		Request req = builder.sendRequest(builder.getRequestData(), callback);
		return req;
	}
}
class QueuedRequestCallbackWrapper implements RequestCallback
{
	private RequestCallback callback;
	private TaskQueue taskQueue;
	
	public QueuedRequestCallbackWrapper(RequestCallback callback, TaskQueue taskQueue) {
		this.callback = callback;
		this.taskQueue = taskQueue;
	}

	@Override
	public void onResponseReceived(final Request request, final Response response) {
		System.out.println(">>>>>>>>>>>>>>>>>>> ResponseQueuedDispatcher::onResponseReceived, taskQueue"+taskQueue);
		taskQueue.add(new Task(){
			@Override
			public void run() {
				callback.onResponseReceived(request, response);				
			}
		});
	}

	@Override
	public void onError(final Request request, final Throwable exception) {
		taskQueue.add(new Task(){
			@Override
			public void run() {
				callback.onError(request, exception);				
			}
		});
	}
}
class DeferredRequestCallbackWrapper implements RequestCallback{
	private RequestCallback callback;
	private Scheduler scheduler;
	public DeferredRequestCallbackWrapper(RequestCallback callback, Scheduler scheduler) {
		this.callback = callback;
		this.scheduler = scheduler;
	}

	@Override
	public void onResponseReceived(final Request request, final Response response) {
		System.out.println(">>>>>>>>>>>>>>>>>>> DeferredRequestCallbackWrapper::onResponseReceived");
		scheduler.scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				callback.onResponseReceived(request, response);				
			}});
	}

	@Override
	public void onError(final Request request, final Throwable exception) {
		scheduler.scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				callback.onError(request, exception);	
			}});
	}
}
