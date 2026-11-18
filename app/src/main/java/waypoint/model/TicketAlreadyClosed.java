
package waypoint.model;

public class TicketAlreadyClosed extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TicketAlreadyClosed() {
        super("Ticket is already closed");
    }
}