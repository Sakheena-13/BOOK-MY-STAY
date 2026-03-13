import java.util.*;

/**
 * UseCase10BookingCancellation demonstrates state reversal and rollback.
 * It uses a Stack to track released room IDs and ensures inventory consistency.
 *
 * @version 10.0
 */
public class BookMyStay {

    /**
     * Managed Inventory Service with restoration capabilities.
     */
    static class InventoryService {
        private Map<String, Integer> inventory = new HashMap<>();

        public void addStock(String type, int count) { inventory.put(type, count); }
        public int getStock(String type) { return inventory.getOrDefault(type, 0); }

        public void incrementStock(String type) {
            inventory.put(type, getStock(type) + 1);
        }
    }

    /**
     * Cancellation Service uses a Stack for LIFO Rollback.
     */
    static class CancellationService {
        private InventoryService inventoryService;
        private Map<String, String> activeBookings; // ID -> RoomType
        private Stack<String> releasedRoomIds = new Stack<>(); // LIFO Rollback structure

        public CancellationService(InventoryService inventoryService, Map<String, String> activeBookings) {
            this.inventoryService = inventoryService;
            this.activeBookings = activeBookings;
        }

        /**
         * Performs a controlled rollback of a specific booking.
         */
        public void cancelBooking(String reservationId) {
            System.out.println("Processing Cancellation for: " + reservationId);

            // 1. Validate Reservation Existence
            if (!activeBookings.containsKey(reservationId)) {
                System.err.println("REJECTED: Reservation ID " + reservationId + " not found or already cancelled.");
                return;
            }

            // 2. State Reversal - Retrieve Room Type
            String roomType = activeBookings.get(reservationId);

            // 3. Rollback Structure - Push to Stack
            releasedRoomIds.push(reservationId);

            // 4. Inventory Restoration
            inventoryService.incrementStock(roomType);

            // 5. Remove from active bookings
            activeBookings.remove(reservationId);

            System.out.println("SUCCESS: " + reservationId + " cancelled. " + roomType + " inventory restored.");
            System.out.println("Current Rollback Stack: " + releasedRoomIds + "\n");
        }
    }

    public static void main(String[] args) {
        System.out.println("Book My Stay - System v10.0");
        System.out.println("Goal: Booking Cancellation & Inventory Rollback (Stack/LIFO)\n");

        // 1. Initial Setup
        InventoryService inventory = new InventoryService();
        inventory.addStock("Suite", 0); // Currently sold out

        // Simulate active bookings in the system
        Map<String, String> activeBookings = new HashMap<>();
        activeBookings.put("R-101", "Suite");
        activeBookings.put("R-102", "Single");

        CancellationService cancellationService = new CancellationService(inventory, activeBookings);

        // 2. Perform Valid Cancellation
        System.out.println("Initial Suite Inventory: " + inventory.getStock("Suite"));
        cancellationService.cancelBooking("R-101");
        System.out.println("Suite Inventory after rollback: " + inventory.getStock("Suite") + "\n");

        // 3. Perform Invalid Cancellation (Non-existent ID)
        cancellationService.cancelBooking("R-999");

        // 4. Perform Duplicate Cancellation
        cancellationService.cancelBooking("R-101");

        System.out.println("System state remains consistent after all operations.");
    }
}
