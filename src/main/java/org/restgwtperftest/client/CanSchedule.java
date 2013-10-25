package org.restgwtperftest.client;

import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public interface CanSchedule
{
   void scheduleFixedPeriod(int periodMilli, RepeatingCommand command);
}
