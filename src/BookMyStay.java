import java.util.HashMap;
import java.util.Map;

/**
 * UC3: Replacing variables with HashMap.
 */
class RoomInventoryV3 {
    private Map<String, Integer> inventory = new HashMap<>();
    public void setStock(String type, int count) { inventory.put(type, count); }
    public int getStock(String type) { return inventory.getOrDefault(type, 0); }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventoryV3 inv = new RoomInventoryV3();
        inv.setStock("Single", 10);
        System.out.println("Inventory Initialized. Single Rooms: " + inv.getStock("Single"));
    }
}
