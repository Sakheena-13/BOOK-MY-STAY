import java.util.HashMap;
import java.util.Map;

/**
 * UC9: Custom Exception for Domain-specific errors.
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * UC9: Room Validator and Inventory Guard.
 */
class BookingValidator {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
    }

    /**
     * Validates both input and system state before allowing a booking.
     * Demonstrates Fail-Fast Design.
     */
    public void validateRequest(String guest, String type) throws InvalidBookingException {
        // 1. Validate Input (Input Validation)
        if (guest == null || guest.trim().isEmpty()) {
            throw new InvalidBookingException("Validation Failed: Guest name cannot be empty.");
        }

        // 2. Validate Room Type (Guarding System State)
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Validation Failed: Room type '" + type + "' does not exist.");
        }

        // 3. Validate Availability (Preventing Negative State)
        if (inventory.get(type) <= 0) {
            throw new InvalidBookingException("Validation Failed: No availability for '" + type + "'.");
        }

        System.out.println("Validation Passed for: " + guest);
    }
}

/**
 * Main class to demonstrate Graceful Failure Handling.
 * @version 9.0
 */
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("Book My Stay - System v9.0");
        System.out.println("Goal: Error Handling & State Validation\n");

        BookingValidator validator = new BookingValidator();
        validator.addInventory("Single", 1); // Only 1 room available

        // Array of test cases including valid and invalid inputs
        String[][] testRequests = {
                {"Alice", "Single"},      // Valid
                {"", "Single"},           // Invalid: Empty Name
                {"Bob", "Penthouse"},     // Invalid: Non-existent Room
                {"Charlie", "Single"}     // Invalid: Out of Stock (Fail-Fast)
        };

        for (String[] req : testRequests) {
            try {
                String guest = req[0];
                String type = req[1];

                validator.validateRequest(guest, type);

                // If validation passes, simulate the booking
                System.out.println("SUCCESS: Processed booking for " + guest + "\n");

                // Update state after success
                validator.addInventory(type, 0);

            } catch (InvalidBookingException e) {
                // Graceful Failure Handling
                System.err.println("ERROR: " + e.getMessage());
                System.err.println("System status: Stable. Continuing to next request...\n");
            }
        }

        System.out.println("All requests processed. System remains in a consistent state.");
    }
}
