
package org.acme.rest.client;

import java.io.IOException;
import java.util.UUID;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;

public class InkluSportRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext context)
            throws IOException {

        context.getHeaders().putSingle(
            "X-Correlation-ID",
            UUID.randomUUID().toString()
        );
    }
}
