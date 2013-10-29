package org.restgwtperftest.service.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.enunciate.modules.jersey.ExternallyManagedLifecycle;
import org.restgwtperftest.shared.model.BigBean;
import org.restgwtperftest.shared.model.ServiceConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(ServiceConsts.SERVICE_PATH_V1_SLOW)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ExternallyManagedLifecycle
public class SlowTestService {
	   private static final Logger logger = LoggerFactory.getLogger(SlowTestService.class);
	   @GET
	   @Path(ServiceConsts.RELATIVE_PATH_HELLO)
	   public String getHello()
	   {
	      return "Hello world!";
	   }
	   @GET
	   @Path(ServiceConsts.RELATIVE_PATH_LONG_OPERATION+"/{sleep}")
	   public void doLongOperation(@PathParam("sleep")int sleepMillis)
	   {
		   try {
			Thread.sleep(sleepMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	   }
	   
	   @GET
	   @Path("/{amount}")
	   public List<BigBean> findAll(@PathParam("amount")int amount)
	   {
		   ArrayList<BigBean> arr = new ArrayList<BigBean>();
		   BigBean bean;
		   for(int i=amount;i>0;i--)
		   {
			   bean = new BigBean(UUID.randomUUID().toString());
			   arr.add(bean);
		   }
		   return arr;
	   }

	   
}
