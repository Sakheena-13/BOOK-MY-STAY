import java.util.HashMap;
import java.util.Map;

/**
 * UC4: Read-only search logic.
 */
class SearchServiceV4 {
    public void search(Map<String, Integer> inv) {
        System.out.println("--- Available Rooms ---");
        inv.forEach((type, count) -> {
            if (count > 0) System.out.println(type + ": " + count + " available");
        });
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Suite", 0); // Should be filtered out
        new SearchServiceV4().search(inventory);
    }
}
