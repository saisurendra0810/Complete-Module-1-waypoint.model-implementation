
package waypoint.model;

import java.util.Objects;

/** ENTITY. Identity = id. Invariant: a spot cannot be assigned while occupied. */
public final class Spot {

    private final SpotId id;
    private VehicleId occupant;   // the ONLY mutable field

    // Constructor
    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "Spot ID cannot be null");
    }

    // Assign a vehicle to the spot
    public boolean tryAssign(VehicleId v) {
        Objects.requireNonNull(v, "Vehicle ID cannot be null");

        if (occupant != null) {
            return false;
        }

        occupant = v;
        return true;
    }

    // Release the spot
    public void release(VehicleId v) {
        Objects.requireNonNull(v, "Vehicle ID cannot be null");

        if (v.equals(occupant)) {
            occupant = null;
        }
    }

    // Check whether the spot is free
    public boolean isFree() {
        return occupant == null;
    }

    // Compare spots using ID alone
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Spot)) {
            return false;
        }

        Spot other = (Spot) obj;

        return this.id.equals(other.id);
    }

    // Generate hash code using ID alone
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}