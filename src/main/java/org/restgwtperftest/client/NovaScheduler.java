package org.restgwtperftest.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

/**
 * Thin wrapper around the GWT Scheduler which cannot be used in activities / presenters,
 * and cannot be tested easily.
 *
 * The CanSchedule interface allows to create a fake, testable scheduler.
 */
public class NovaScheduler implements CanSchedule
{
   @Override
   public void scheduleFixedPeriod(int periodMilli, RepeatingCommand command)
   {
      Scheduler.get().scheduleFixedPeriod(command, periodMilli);
   }
}
