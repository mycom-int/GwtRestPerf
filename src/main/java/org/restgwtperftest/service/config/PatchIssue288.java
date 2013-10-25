package org.restgwtperftest.service.config;

import java.lang.ref.PhantomReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copied/pasted from the net, at:
//  http://code.google.com/p/google-guice/issues/detail?id=288
// This must be called from the ServletConfig.
public class PatchIssue288
{
   private static final Logger logger = LoggerFactory.getLogger(PatchIssue288.class);

   public PatchIssue288()
   {

   }

   public void contextDestroyed(ServletContextEvent event)
   {
      Thread[] threads = getThreads();

      String[] guicePrefixes = { "com.google.inject.internal.", // google guice 2.0
         "com.google.inject.internal.util.$" // google guice 3.0
      };
      for (String guicePrefix : guicePrefixes)
      {
         cancelThreads(guicePrefix, threads);
      }
   }

   private void cancelThreads(String guicePrefix, Thread[] threads)
   {
      for (Thread thread : threads)
      {
         if (thread == null)
            continue;
         cancelOneThread(guicePrefix, thread);
      }
   }

   private void cancelOneThread(String guicePrefix, Thread thread)
   {
      sendShutdownNotify(guicePrefix, thread);
      cancelExpirationTimer(guicePrefix, thread);
   }

   private void sendShutdownNotify(String guicePrefix, Thread thread)
   {
      try
      {
         doSendShutdownNotify(guicePrefix, thread);
      }
      catch (Exception e)
      {
         logger.warn(thread.getClass().getName() + " couldn't be notified to shutdown !", e);
      }
   }

   private void cancelExpirationTimer(String guicePrefix, Thread thread)
   {
      try
      {
         doCancelExpirationTimer(guicePrefix, thread);
      }
      catch (Exception e)
      {
         logger.warn(thread.getClass().getName() + " couldn't be cancelled !", e);
      }
   }

   private void doCancelExpirationTimer(String guicePrefix, Thread thread) throws Exception
   {
      if (thread.getClass().getName().equals("java.util.TimerThread"))
      {
         Class<?> expirationTimerClass = Class.forName(guicePrefix + "ExpirationTimer");
         if (expirationTimerClass != null)
         {
            cancelExpirationTimer(expirationTimerClass, thread);
         }
      }
   }

   private void cancelExpirationTimer(Class<?> expirationTimerClass, Thread thread) throws Exception
   {
      Timer instance = (Timer) getFieldInstance(null, expirationTimerClass, "instance");
      Thread instanceThread = (Thread) getFieldInstance(instance, instance.getClass(), "thread");

      // Check that the thread is our instance
      if (instanceThread == thread)
      {
         instance.cancel();
         logger.info(expirationTimerClass.getName() + " successfully cancelled.");
      }
   }

   private void doSendShutdownNotify(String guicePrefix, Thread thread) throws Exception
   {
      if (thread.getClass().getName().equals(guicePrefix + "Finalizer"))
      {
         Class<?> mapMakerClass = Class.forName(guicePrefix + "MapMaker");

         Class<?>[] classes = mapMakerClass.getDeclaredClasses();
         for (Class<?> clazz : classes)
         {
            if (clazz.getName().equals(guicePrefix + "MapMaker$QueueHolder"))
            {
               sendShutdownNotify(clazz, thread);
            }
         }
      }
   }

   private void sendShutdownNotify(Class<?> clazz, Thread thread) throws Exception
   {
      Object finalizableReferenceQueue = getFieldInstance(null, clazz, "queue");
      Object referenceQueue = getFieldInstance(finalizableReferenceQueue, finalizableReferenceQueue.getClass(), "queue");
      Object finalizerQueue = getFieldInstance(thread, thread.getClass(), "queue");

      // Check that the thread is our instance
      if (referenceQueue == finalizerQueue)
      {
         sendShutdownNotify(thread, finalizerQueue);
      }
   }

   private void sendShutdownNotify(Thread thread, Object finalizerQueue) throws Exception
   {
      PhantomReference<Class<?>> frqReference = (PhantomReference<Class<?>>) getFieldInstance(thread, thread.getClass(), "frqReference");

      // Notify the finalizer it can stop
      Method enqueue = finalizerQueue.getClass().getDeclaredMethod("enqueue", new Class[] { java.lang.ref.Reference.class });
      enqueue.setAccessible(true);
      enqueue.invoke(finalizerQueue, new Object[] { frqReference });
      logger.info(thread.getClass().getName() + " successfully notified to shutdown.");
   }

   private Object getFieldInstance(Object instance, Class<?> clazz, String name) throws Exception
   {
      Field field = clazz.getDeclaredField(name);
      field.setAccessible(true);
      return field.get(instance);
   }

   private ThreadGroup getToplevelThreadGroup()
   {
      // Get the current thread group
      ThreadGroup tg = Thread.currentThread().getThreadGroup();
      // Find the root thread group
      while (tg.getParent() != null)
      {
         tg = tg.getParent();
      }
      return tg;
   }

   /* Code from apache tomcat 6.0.32 */
   private Thread[] getThreads()
   {
      ThreadGroup tg = getToplevelThreadGroup();

      int threadCountGuess = tg.activeCount() + 50;
      return enumerateThreads(tg, threadCountGuess);
   }

   private Thread[] enumerateThreads(ThreadGroup tgParent, int threadCountGuess)
   {
      Thread[] threads = new Thread[threadCountGuess];
      int threadCountActual = tgParent.enumerate(threads);
      // Make sure we don't miss any threads
      while (threadCountActual == threadCountGuess)
      {
         threadCountGuess *= 2;
         threads = new Thread[threadCountGuess];
         // Note tg.enumerate(Thread[]) silently ignores any threads that
         // can't fit into the array
         threadCountActual = tgParent.enumerate(threads);
      }
      return threads;
   }

}
