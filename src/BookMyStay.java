import java.util.*;

/**
 * UC11: Concurrent Booking Simulation.
 * Demonstrates Thread Safety and Synchronized Access to shared resources.
 *
 * @version 11.0
 */
public class BookMyStay {

    /**
     * Shared Resource: Inventory.
     * Methods are 'synchronized' to prevent Race Conditions.
     */
    static class ThreadSafeInventory {
        private int availableRooms = 2; // Only 2 rooms for multiple guests

        // Critical Section: Only one thread can check and decrement at a time
        public synchronized boolean bookRoom(String guestName) {
            if (availableRooms > 0) {
                System.out.println("[THREAD: " + Thread.currentThread().getName() +
                        "] Processing for: " + guestName);

                // Simulate processing delay to increase chance of a race condition
                try { Thread.sleep(100); } catch (InterruptedException e) {}

                availableRooms--;
                System.out.println("CONFIRMED: " + guestName + " got a room. Left: " + availableRooms);
                return true;
            } else {
                System.out.println("FAILED: No rooms left for " + guestName);
                return false;
            }
        }

        public int getRemaining() { return availableRooms; }
    }

    /**
     * Represents a Guest acting as an independent Thread.
     */
    static class GuestRequest implements Runnable {
        private String guestName;
        private ThreadSafeInventory inventory;

        public GuestRequest(String name, ThreadSafeInventory inv) {
            this.guestName = name;
            this.inventory = inv;
        }

        @Override
        public void run() {
            inventory.bookRoom(guestName);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Book My Stay - System v11.0");
        System.out.println("Goal: Thread Safety & Concurrent Booking Simulation\n");

        ThreadSafeInventory sharedInventory = new ThreadSafeInventory();

        // 1. Create multiple guest threads attempting to book simultaneously
        Thread t1 = new Thread(new GuestRequest("Alice", sharedInventory), "Thread-Alice");
        Thread t2 = new Thread(new GuestRequest("Bob", sharedInventory), "Thread-Bob");
        Thread t3 = new Thread(new GuestRequest("Charlie", sharedInventory), "Thread-Charlie");
        Thread t4 = new Thread(new GuestRequest("Diana", sharedInventory), "Thread-Diana");

        // 2. Start all threads at roughly the same time (Concurrent Execution)
        System.out.println("Starting concurrent booking requests...");
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // 3. Wait for all threads to finish
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        // 4. Final Consistency Check
        System.out.println("\nFinal System State:");
        System.out.println("Remaining Rooms: " + sharedInventory.getRemaining());
        if (sharedInventory.getRemaining() < 0) {
            System.err.println("CRITICAL ERROR: Oversold inventory (Race Condition occurred)!");
        } else {
            System.out.println("SUCCESS: System state is consistent.");
        }
    }
}
