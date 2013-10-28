package org.restgwtperftest.client.inject;

import org.restgwtperftest.client.ApplicationContext;
import org.restgwtperftest.client.ApplicationContextImpl;
import org.restgwtperftest.client.ui.CaseView;
import org.restgwtperftest.client.ui.RootView;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

public class Module extends AbstractGinModule
{
   @Override
   protected void configure()
   {
	   bind(ApplicationContext.class).to(ApplicationContextImpl.class).in(Singleton.class);
	   bind(RootView.class);
	   install(new GinFactoryModuleBuilder().build(CaseView.Factory.class));
   }


}
