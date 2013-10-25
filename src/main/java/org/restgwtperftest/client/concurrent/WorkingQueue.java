package org.restgwtperftest.client.concurrent;

import java.util.HashMap;
import java.util.LinkedList;

import javax.annotation.CheckForNull;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class WorkingQueue<T extends Work<?>> implements RepeatingCommand
{
   private LinkedList<T> queue = new LinkedList<T>();
   private HashMap<String, T> works = new HashMap<String, T>();
   private boolean isWorking = false;
   private boolean cancel = false;

   public void start(int periodMillis)
   {
      if (!isWorking)
      {
         isWorking = true;
         Scheduler.get().scheduleFixedPeriod(this, periodMillis);
      }
   }

   @Override
   public boolean execute()
   {
      if (!queue.isEmpty())
      {
         Work<?> w = queue.peek();
         if (w.isDone())
         {
            queue.remove();
            works.remove(w.getId());
         }
         else
         {
            //TODO add additional safety guard if RestGWT doesn't impl any timeout mechanism. ||
            //timestamp > 10000 ms, force trigger failure.
            w.execute();
         }
         return true;
      }
      isWorking = false;
      return false;
   }

   public void add(T work)
   {
      queue.add(work);
      works.put(work.getId(), work);
   }

   @CheckForNull
   public T get(String id)
   {
      return works.get(id);
   }

   public boolean isCancel()
   {
      return cancel;
   }

   public void setCancel(boolean cancel)
   {
      this.cancel = cancel;
   }
   public String toString()
   {
      return "WorkingQueue size:"+queue.size();
   }
}
