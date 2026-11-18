
package waypoint.model;

import java.time.Instant;
import java.util.Objects;

/** ENTITY. Invariants:
 *   I1  entry is always present
 *   I2  exit is null, or not before entry
 *   I3  fare is null, or not negative
 *   I4  fare exists only if exit exists
 *   I5  a closed ticket never reopens
 */
public final class Ticket {

    // Fields
    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;

    private Instant exit;
    private Money fare;

    // Private constructor
    private Ticket(TicketId id, SpotId spotId, Instant entry) {
        this.id = Objects.requireNonNull(id, "Ticket ID cannot be null");
        this.spotId = Objects.requireNonNull(spotId, "Spot ID cannot be null");
        this.entry = Objects.requireNonNull(entry, "Entry time cannot be null");

        this.exit = null;
        this.fare = null;
    }

    // Factory method to create an open ticket
    public static Ticket open(TicketId id, SpotId spotId, Instant entry) {
        return new Ticket(id, spotId, entry);
    }

    // Check whether ticket is open
    public boolean isOpen() {
        return exit == null;
    }

    // Get ticket fare
    public Money fare() {
        return fare;
    }

    // Close the ticket and calculate fare
    public Money close(Instant at, FarePolicy policy) {

        // Reject second close
        if (!isOpen()) {
            throw new TicketAlreadyClosed();
        }

        // Validate exit time
        Objects.requireNonNull(at, "Exit time cannot be null");

        if (at.isBefore(entry)) {
            throw new IllegalArgumentException(
                "Exit time cannot be before entry time"
            );
        }

        Objects.requireNonNull(policy, "Fare policy cannot be null");

        // Calculate fare
        Money calculatedFare = Objects.requireNonNull(
            policy.fareFor(entry, at, spotId),
            "Fare cannot be null"
        );

        // Reject negative fare
        if (calculatedFare.isNegative()) {
            throw new IllegalArgumentException(
                "Fare cannot be negative"
            );
        }

        // Update exit and fare together
        this.exit = at;
        this.fare = calculatedFare;

        return this.fare;
    }
}