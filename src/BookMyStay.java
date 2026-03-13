import java.util.*;

/**
 * UC6: Final allocation with Set for unique IDs.
 */
public class BookMyStay {
    public static void main(String[] args) {
        Set<String> allocatedIds = new HashSet<>();
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 1);

        String guest = "Alice";
        String type = "Single";

        if (inventory.get(type) > 0) {
            String id = "R101";
            allocatedIds.add(id); // Set prevents double-booking
            inventory.put(type, inventory.get(type) - 1); // Atomic update
            System.out.println("Confirmed: " + guest + " assigned to " + id);
        }
    }
}
