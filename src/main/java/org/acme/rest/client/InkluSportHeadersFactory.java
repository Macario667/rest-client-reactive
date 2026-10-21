package org.acme.rest.client;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

public class InkluSportHeadersFactory implements ClientHeadersFactory {

    @Override
    public MultivaluedMap<String, String> update(
            MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {

        MultivaluedMap<String, String> headers =
                new MultivaluedHashMap<>();

        headers.putAll(clientOutgoingHeaders);

        headers.putSingle(
                "X-Client-Application",
                "inklusport-api"
        );

        return headers;
    }
}
