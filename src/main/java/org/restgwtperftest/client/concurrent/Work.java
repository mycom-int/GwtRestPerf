package org.restgwtperftest.client.concurrent;

import org.fusesource.restygwt.client.MethodCallback;

public interface Work<T> {
	void execute();

	public String getId();

	public boolean isDone();

	public void add(MethodCallback<T> callback);
}
