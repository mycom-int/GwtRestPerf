package org.restgwtperftest.client.inject;

import org.restgwtperftest.client.ApplicationContext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules({ BaseModule.class, Module.class })
public interface Injector extends Ginjector
{
   Injector INSTANCE = GWT.create(Injector.class);


   /** To be used only in the entry point. Otherwise, just inject the context. */
   ApplicationContext getApplication();

}
