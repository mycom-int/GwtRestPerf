package org.restgwtperftest.service.config;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrentResourceConfig extends ResourceConfig
{
   private static Logger logger = LoggerFactory.getLogger(CurrentResourceConfig.class);

   @Inject
   public CurrentResourceConfig(ServiceLocator serviceLocator)
   {
      logger.info("Registering injectables ....");

      GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
      GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
      guiceBridge.bridgeGuiceInjector(ServletConfig.getInjectorInstance());
   }
}
