package org.restgwtperftest.client.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.restgwtperftest.shared.model.BigBean;
import org.restgwtperftest.shared.model.ServiceConsts;

@Path(ServiceConsts.SERVICE_PATH_CONTEXT_PREFIX+ServiceConsts.SERVICE_PATH_V1_SLOW)
public interface SimpleRestServiceClient extends RestService {
	@GET
	@Path(ServiceConsts.RELATIVE_PATH_HELLO)
	void sayHello(MethodCallback<String> callback);

	@GET
	@Path("/{amount}")
	void get(@PathParam("amount") int amount,
			MethodCallback<List<BigBean>> callback);
}
