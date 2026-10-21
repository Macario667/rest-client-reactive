package org.acme.rest.client;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.quarkus.rest.client.reactive.jackson.ClientObjectMapper;
import io.smallrye.mutiny.Uni;

@Path("/extensions")
@RegisterRestClient(configKey = "extensions-api")
@RegisterClientHeaders(InkluSportHeadersFactory.class)
@RegisterProvider(InkluSportRequestFilter.class)
@ClientHeaderParam(name = "X-Client-Source", value = "inklusport")
public interface ExtensionsService {

    @GET
    Set<Extension> getById(@RestQuery String id);

    @GET
    CompletionStage<Set<Extension>> getByIdAsync(@RestQuery String id);

    @GET
    Uni<Set<Extension>> getByIdAsUni(@RestQuery String id);

    @GET
    Set<Extension> getByIdWithHeaders(
            @RestQuery String id,
            @HeaderParam ("X-Request-Source") String requestSourceString);

    @GET
    Response getByIdWithFullResponse(@RestQuery String id);
    
    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        if (response.getStatus() == 404) {
            return new jakarta.ws.rs.NotFoundException(
                "La extensión solicitada no existe"
            );
        }

        if (response.getStatus() == 500) {
            return new RuntimeException(
                "La API externa respondió con HTTP 500"
            );
        }

        return null;
    }

    @ClientObjectMapper
    static ObjectMapper objectMapper(ObjectMapper defaultMapper) {
        return defaultMapper.copy()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
