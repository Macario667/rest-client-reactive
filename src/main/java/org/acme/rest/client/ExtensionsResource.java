
package org.acme.rest.client;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

@Path("/extension")
public class ExtensionsResource {

    @Inject
    @RestClient
    ExtensionsService extensionsService;

    @GET
    @Path("/id/{id}")
    @Blocking
    public Set<Extension> id(@PathParam("id") String id) {
        return extensionsService.getById(id);
    }

    @GET
    @Path("/id-async/{id}")
    public CompletionStage<Set<Extension>> idAsync(
            @PathParam("id") String id) {
        return extensionsService.getByIdAsync(id);
    }

    @GET
    @Path("/id-uni/{id}")
    public Uni<Set<Extension>> idUni(
            @PathParam("id") String id) {
        return extensionsService.getByIdAsUni(id);
    }
}
