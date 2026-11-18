
package waypoint.model;

import java.util.*;
import java.math.BigDecimal;

/** VALUE OBJECT. Exact money. */
public final class Money implements Comparable<Money> {

    // Fields
    private final long paise;
    private final Currency currency;

    // Private constructor
    private Money(long paise, Currency currency) {
        this.paise = paise;
        this.currency = Objects.requireNonNull(
            currency, "Currency cannot be null"
        );
    }

    // Factory method: create Money using minor units (paise)
    public static Money ofMinor(long paise, Currency currency) {
        return new Money(paise, currency);
    }

    // Factory method: create Money using rupees
    public static Money rupees(long rupees) {
        return new Money(
            Math.multiplyExact(rupees, 100L),
            Currency.getInstance("INR")
        );
    }

    // Addition
    public Money plus(Money other) {
        checkCurrency(other);
        return new Money(
            Math.addExact(this.paise, other.paise),
            this.currency
        );
    }

    // Subtraction
    public Money minus(Money other) {
        checkCurrency(other);
        return new Money(
            Math.subtractExact(this.paise, other.paise),
            this.currency
        );
    }

    // Multiplication
    public Money times(int multiplier) {
        return new Money(
            Math.multiplyExact(this.paise, multiplier),
            this.currency
        );
    }

    // Check whether amount is negative
    public boolean isNegative() {
        return paise < 0;
    }

    // Compare two Money objects
    @Override
    public int compareTo(Money other) {
        checkCurrency(other);
        return Long.compare(this.paise, other.paise);
    }

    // Allocate money equally among n shares
    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                "Number of shares must be positive"
            );
        }

        List<Money> shares = new ArrayList<>();

        long quotient = paise / n;
        long remainder = paise % n;

        for (int i = 0; i < n; i++) {
            long share = quotient;

            if (i < Math.abs(remainder)) {
                share += (remainder > 0) ? 1 : -1;
            }

            shares.add(new Money(share, currency));
        }

        return Collections.unmodifiableList(shares);
    }

    // Check currency compatibility
    private void checkCurrency(Money other) {
        Objects.requireNonNull(other, "Money cannot be null");

        if (!this.currency.equals(other.currency)) {
            throw new CurrencyMismatch();
        }
    }

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Money)) {
            return false;
        }

        Money other = (Money) obj;

        return this.paise == other.paise
            && this.currency.equals(other.currency);
    }

    // HashCode method
    @Override
    public int hashCode() {
        return Objects.hash(paise, currency);
    }

    // Convert Money into readable format
    @Override
    public String toString() {
        BigDecimal amount = BigDecimal.valueOf(paise, 2);

        return currency.getCurrencyCode() + " " + amount.toPlainString();
    }
}