/**
 * 
 */
package org.restgwtperftest.client;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;

public class ApplicationContextImpl implements ApplicationContext {

	@Inject
	public ApplicationContextImpl() {
	}

	@Override
	public EventBus getMainEventBus() {
		return null;
	}

}
