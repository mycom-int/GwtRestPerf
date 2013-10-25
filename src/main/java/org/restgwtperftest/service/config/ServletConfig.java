package org.restgwtperftest.service.config;

import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ServletConfig extends GuiceServletContextListener
{
	   private static Injector INJECTOR;
	   private PatchIssue288 patch = new PatchIssue288();

	   public static Injector getInjectorInstance()
	   {
	      return INJECTOR;
	   }

	   public static void createInjectorInstance()
	   {
	      INJECTOR = Guice.createInjector(
	         new RestServiceModule());
	   }

	   public static void finalizeInjectorInstance()
	   {
	      INJECTOR = null;
	   }

	   @Override
	   protected Injector getInjector()
	   {
	      return getInjectorInstance();
	   }

	   @Override
	   public void contextInitialized(ServletContextEvent servletContextEvent)
	   {
	      createInjectorInstance();
	      super.contextInitialized(servletContextEvent);
	   }

	   @Override
	   public void contextDestroyed(ServletContextEvent servletContextEvent)
	   {
	      try
	      {
	         super.contextDestroyed(servletContextEvent);
	      }
	      finally
	      {
	         patch.contextDestroyed(servletContextEvent);
	         finalizeInjectorInstance(); // force GC.
	      }
	   }

	}
