package org.acme.rest.client;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.smallrye.mutiny.Uni;

@Path("/extensions")
@RegisterRestClient(configKey = "extensions-api")
@ClientHeaderParam(name = "X-Client-Source", value = "inklusport")
public interface ExtensionsService {

    @GET
    Set<Extension> getById(@RestQuery String id);

    @GET
    CompletionStage<Set<Extension>> getByIdAsync(@RestQuery String id);

    @GET
    Uni<Set<Extension>> getByIdAsUni(@RestQuery String id);

    @ClientExceptionMapper 
    static RuntimeException toException(Response response) {
        if (response.getStatus() == 500) {
            return new RuntimeException(
                "La API externa respondió con HTTP 500"
            );
        }

        return null;
    }
}
