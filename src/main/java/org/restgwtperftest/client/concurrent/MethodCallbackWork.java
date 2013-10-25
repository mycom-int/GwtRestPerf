package org.restgwtperftest.client.concurrent;

import java.util.ArrayList;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;


public abstract class MethodCallbackWork<T> implements Work<T>, MethodCallback<T>
{
   private ArrayList<MethodCallback<T>> callbacks;
   private boolean done = false;
   private String id;
   private boolean started = false;

   public String getId()
   {
      return id;
   }
   public void execute()
   {
      if (!started)
      {
         started = true;
         run();
      }
   }
   protected abstract void run();
   
   public MethodCallbackWork(String id)
   {
      this.id = id;
      callbacks = new ArrayList<MethodCallback<T>>();
   }

   public void add(MethodCallback<T> callback)
   {
      callbacks.add(callback);
   }

   @Override
   public void onFailure(Method method, Throwable exception)
   {
      done = true;
      for (MethodCallback<T> callback : callbacks)
      {
         callback.onFailure(method, exception);
      }
   }

   @Override
   public void onSuccess(Method method, T response)
   {
      done = true;
      for (MethodCallback<T> callback : callbacks)
      {
         callback.onSuccess(method, response);
      }
   }

   public boolean isDone()
   {
      return done;
   }
}
