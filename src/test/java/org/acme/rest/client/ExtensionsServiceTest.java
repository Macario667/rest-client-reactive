package org.acme.rest.client;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionsServiceTest {

    @Test
    void shouldMapInternalServerError() {
        Response response = Response.status(500).build();

        RuntimeException exception =
                ExtensionsService.toException(response);

        assertNotNull(exception);
        assertEquals(
                "La API externa respondió con HTTP 500",
                exception.getMessage()
        );

        response.close();
    }

    @Test
    void shouldIgnoreOtherHttpStatuses() {
        Response response = Response.status(404).build();

        RuntimeException exception =
                ExtensionsService.toException(response);

        assertNull(exception);

        response.close();
    }
}