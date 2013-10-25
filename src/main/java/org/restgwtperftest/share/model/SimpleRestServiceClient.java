package org.restgwtperftest.share.model;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;


@Path("api/v1/simple")
public interface SimpleRestServiceClient extends RestService
{
   @POST
   void get(String simpleId, MethodCallback<BigBean> callback);

   @GET
   @Path("/{id}")
   void post(@PathParam("id") String simpleId, MethodCallback<BigBean> callback);
}
