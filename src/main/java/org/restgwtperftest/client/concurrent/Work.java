package org.restgwtperftest.client.concurrent;

import org.fusesource.restygwt.client.MethodCallback;

public interface Work<T> extends Executable
{

   public String getId();

   public boolean isDone();

   public void add(MethodCallback<T> callback);
}
