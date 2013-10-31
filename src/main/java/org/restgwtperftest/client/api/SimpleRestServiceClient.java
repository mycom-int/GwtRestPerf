package org.restgwtperftest.client.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.restgwtperftest.client.QueuedResponseDispatcher;
import org.restgwtperftest.shared.model.BigBean;
import org.restgwtperftest.shared.model.ServiceConsts;

@Path(ServiceConsts.SERVICE_PATH_CONTEXT_PREFIX+ServiceConsts.SERVICE_PATH_V1_SLOW)
@Options(dispatcher=QueuedResponseDispatcher.class)
public interface SimpleRestServiceClient extends RestService {
	@GET
	@Path(ServiceConsts.RELATIVE_PATH_HELLO)
	void sayHello(MethodCallback<String> callback);

	@GET
	@Path(ServiceConsts.RELATIVE_PATH_LONG_OPERATION+"/{sleep}")
	void doLongOperation(@PathParam("sleep")int sleepMillis, MethodCallback<Void> vOID);

	@GET
	@Path("/{amount}")
	void getAllBeans(@PathParam("amount") int amount,
			MethodCallback<List<BigBean>> callback);
}
