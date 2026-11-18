
package waypoint.model;

import java.util.Objects;

/** Distinct ID types so they cannot be swapped accidentally. */

record TicketId(String value) {
    TicketId {
        Objects.requireNonNull(value, "Ticket ID cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Ticket ID cannot be blank");
        }
    }
}

record SpotId(String value) {
    SpotId {
        Objects.requireNonNull(value, "Spot ID cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Spot ID cannot be blank");
        }
    }
}

record VehicleId(String value) {
    VehicleId {
        Objects.requireNonNull(value, "Vehicle ID cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Vehicle ID cannot be blank");
        }
    }
}