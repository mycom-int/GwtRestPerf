package org.restgwtperftest.client.inject;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class BaseModule extends AbstractGinModule
{

   @Override
   protected void configure()
   {
      bind(EventBus.class).to(SimpleEventBus.class); // Not Singleton, we can have private event buses
//
//      install(new GinFactoryModuleBuilder().build(SimplePanelRegionView.Factory.class));
//
//      // Root UI configuration.
//      bind(RootView.class).to(RootViewImpl.class);
//
//      bind(NovaResources.class).in(Singleton.class);
//      bind(PlaceHistoryMapper.class).to(NovaPlaceHistoryMapper.class);
//
//      // Clients
//      bind(HomePageClient.class).in(Singleton.class);
//      bind(SecurityClient.class).in(Singleton.class);
//      bind(MeasurementJobInstanceClient.class).in(Singleton.class);
   }

}
