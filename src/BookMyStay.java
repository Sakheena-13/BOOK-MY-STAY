import java.io.*;
import java.util.*;

/**
 * UC12: Data Persistence & System Recovery.
 * Demonstrates Serialization and Deserialization to maintain state across restarts.
 */
public class BookMyStay {

    // The file where our "Database" will be stored
    private static final String STORAGE_FILE = "hotel_state.ser";

    /**
     * Represents the entire System State to be saved.
     * Must implement Serializable to be written to a file.
     */
    static class HotelState implements Serializable {
        private static final long serialVersionUID = 1L;
        Map<String, Integer> inventory = new HashMap<>();
        List<String> bookingHistory = new ArrayList<>();

        public void display() {
            System.out.println("--- Restored System State ---");
            System.out.println("Inventory: " + inventory);
            System.out.println("History Count: " + bookingHistory.size());
            for(String record : bookingHistory) System.out.println(" - " + record);
            System.out.println("-----------------------------");
        }
    }

    /**
     * Persistence Service to handle File I/O operations.
     */
    static class PersistenceService {
        public void saveState(HotelState state) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
                oos.writeObject(state);
                System.out.println("SUCCESS: System state serialized to " + STORAGE_FILE);
            } catch (IOException e) {
                System.err.println("ERROR: Failed to save state: " + e.getMessage());
            }
        }

        public HotelState loadState() {
            File file = new File(STORAGE_FILE);
            if (!file.exists()) {
                System.out.println("No persistence file found. Starting with fresh state.");
                return new HotelState();
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
                System.out.println("RECOVERY: Loading state from " + STORAGE_FILE + "...");
                return (HotelState) ois.readObject();
            } catch (Exception e) {
                System.err.println("ERROR: Corrupted state file. Starting fresh. " + e.getMessage());
                return new HotelState();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Book My Stay - System v12.0");
        System.out.println("Goal: Persistence & System Recovery\n");

        PersistenceService persistence = new PersistenceService();

        // 1. SYSTEM STARTUP (Recovery Phase)
        HotelState currentState = persistence.loadState();
        currentState.display();

        // 2. SYSTEM OPERATION (Simulation)
        System.out.println("\nSimulating a new booking...");
        String newGuest = "Guest_" + System.currentTimeMillis() % 1000;
        currentState.inventory.put("Single", currentState.inventory.getOrDefault("Single", 10) - 1);
        currentState.bookingHistory.add("Guest: " + newGuest + " | Room: Single");

        // 3. SYSTEM SHUTDOWN (Persistence Phase)
        System.out.println("Preparing for shutdown...");
        persistence.saveState(currentState);

        System.out.println("\nApplication Terminated. RUN AGAIN to see the recovered state!");
    }
}
