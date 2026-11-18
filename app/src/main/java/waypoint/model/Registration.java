
package waypoint.model;

import java.util.Objects;
import java.util.Locale;

/** VALUE OBJECT. Validates and normalises registration numbers. */
public record Registration(String value) {

    // Compact constructor
    public Registration {
        // 1. Check for null
        Objects.requireNonNull(value, "Registration cannot be null");

        // 2. Remove extra spaces
        value = value.trim();

        // 3. Convert to uppercase
        value = value.toUpperCase(Locale.ROOT);

        // 4. Validate registration format
        if (!value.matches("[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}")) {
            throw new IllegalArgumentException(
                "Invalid registration number: " + value
            );
        }
    }
}