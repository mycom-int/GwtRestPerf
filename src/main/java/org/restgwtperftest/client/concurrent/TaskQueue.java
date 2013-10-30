package org.restgwtperftest.client.concurrent;

import java.util.LinkedList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class TaskQueue implements RepeatingCommand {
	private final LinkedList<Task> queue;
	private final Scheduler scheduler;
	private int periodMillis;
	private boolean started;
	public TaskQueue() {
		this(300, Scheduler.get());
	}

	public TaskQueue(int periodMillis, Scheduler scheduler) {
		this.periodMillis = periodMillis;
		queue = new LinkedList<Task>();
		this.scheduler = scheduler;
		started = false;
	}

	public void add(Task task) {
		queue.add(task);
		start(periodMillis);
	}

	public void start(int periodMillis) {
		if(!started)
		{
			started = true;
			scheduler.scheduleFixedDelay(this, periodMillis);
		}
	}

	@Override
	public boolean execute() {
		long millis = System.currentTimeMillis();
		try{
			if (!queue.isEmpty()) {
				Task w = queue.remove();
				System.out.println("task: "+ w);
					// TODO add additional safety guard if RestGWT doesn't impl any
					// timeout mechanism. ||
					// timestamp > 10000 ms, force trigger failure.
				w.run();
				return true;
			}
			started = false;
			return false;			
		}
		finally
		{
			long l = System.currentTimeMillis() - millis;
			System.out.println("task time consumption: "+l+" ms");
		}
	}

	public interface Task {
		void run();
	}
}
