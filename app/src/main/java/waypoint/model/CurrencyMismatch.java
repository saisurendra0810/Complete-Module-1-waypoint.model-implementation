package waypoint.model;

public final class CurrencyMismatch extends RuntimeException {

    public CurrencyMismatch() {
        super("Currencies must be the same");
    }
}