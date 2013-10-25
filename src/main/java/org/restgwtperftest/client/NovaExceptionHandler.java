package org.restgwtperftest.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.restgwtperftest.client.ui.widget.ConfirmBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.web.bindery.event.shared.UmbrellaException;

/**
 * Non caught exceptions are handled here.
 */
public class NovaExceptionHandler implements UncaughtExceptionHandler
{
   private static final Logger logger = Logger.getLogger(NovaExceptionHandler.class.getName());

   private static NovaExceptionHandler instanceExceptionHandler = new NovaExceptionHandler();

   public static NovaExceptionHandler get()
   {
      return instanceExceptionHandler;
   }

   private NovaExceptionHandler()
   {
      super();
   }

   @Override
   public void onUncaughtException(Throwable throwable)
   {
      throwable = unwrap(throwable);
      String text = buildText(throwable);

      logger.log(Level.SEVERE, throwable.getClass().getName() + " " + throwable.getMessage());
      if (GWT.isClient())
      {
         ConfirmBox.alert("Error", text);
      }
   }

   private String buildText(Throwable throwable)
   {
      StringBuilder text = new StringBuilder("<div class='errorStack'>Uncaught exception<br/>");
      while (throwable != null)
      {
         text.append(throwable).append("<br/>");
         appendStackTraceElements(throwable, text);
         throwable = throwable.getCause();
         if (throwable != null)
         {
            text.append("Caused by: ").append(throwable);
         }
      }
      text.append("</div>");
      return text.toString();
   }

   private void appendStackTraceElements(Throwable throwable, StringBuilder text)
   {
      StackTraceElement[] stackTraceElements = throwable.getStackTrace();
      text.append("<div class='errorStackDetail'>");
      for (int i = 0; i < stackTraceElements.length; i++)
      {
         text.append("    at ").append(stackTraceElements[i]).append("<br/>");
      }
      text.append("</div>");
   }

   // http://www.summa-tech.com/blog/2012/06/11/7-tips-for-exception-handling-in-gwt/
   public Throwable unwrap(Throwable e)
   {
      if (e instanceof UmbrellaException)
      {
         UmbrellaException ue = (UmbrellaException) e;
         if (ue.getCauses().size() == 1)
            return unwrap(ue.getCauses().iterator().next());
      }
      return e;
   }
}
