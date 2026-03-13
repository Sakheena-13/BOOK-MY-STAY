import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UseCase7AddOnServiceSelection demonstrates how to extend a booking
 * with optional services using a Map of Lists.
 *
 * @version 7.0
 */
public class BookMyStay {

    /**
     * Represents an optional service (e.g., Breakfast, WiFi).
     */
    static class Service {
        private String name;
        private double cost;

        public Service(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }

        public String getName() { return name; }
        public double getCost() { return cost; }

        @Override
        public String toString() {
            return name + " ($" + cost + ")";
        }
    }

    /**
     * Manages the association between Reservation IDs and selected Services.
     */
    static class AddOnServiceManager {
        // Map: Key = Reservation ID, Value = List of selected services
        private Map<String, List<Service>> reservationServices = new HashMap<>();

        /**
         * Attaches a service to a specific reservation.
         */
        public void addServiceToReservation(String reservationId, Service service) {
            // computeIfAbsent ensures a list exists for the ID before adding
            reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
            System.out.println("Added " + service.getName() + " to Reservation: " + reservationId);
        }

        /**
         * Calculates the total cost of all add-ons for a reservation.
         */
        public double calculateTotalAddOnCost(String reservationId) {
            List<Service> services = reservationServices.get(reservationId);
            if (services == null) return 0.0;

            double total = 0;
            for (Service s : services) {
                total += s.getCost();
            }
            return total;
        }

        /**
         * Displays all services for a reservation.
         */
        public void displayServices(String reservationId) {
            List<Service> services = reservationServices.get(reservationId);
            System.out.println("Services for " + reservationId + ": " +
                    (services != null ? services : "None selected"));
        }
    }

    public static void main(String[] args) {
        System.out.println("Book My Stay - System v7.0");
        System.out.println("Goal: Optional Add-On Service Management\n");

        // 1. Initialize Manager and available services
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        Service breakfast = new Service("Buffet Breakfast", 20.0);
        Service spa = new Service("Spa Treatment", 50.0);
        Service wifi = new Service("High-Speed WiFi", 10.0);

        // 2. Simulate selecting services for specific Reservation IDs (from UC6)
        String resId1 = "R-101";
        String resId2 = "R-102";

        System.out.println("--- Selecting Services ---");
        serviceManager.addServiceToReservation(resId1, breakfast);
        serviceManager.addServiceToReservation(resId1, spa);
        serviceManager.addServiceToReservation(resId2, wifi);

        // 3. Display Results and Costs
        System.out.println("\n--- Add-On Summaries ---");
        serviceManager.displayServices(resId1);
        System.out.println("Total Extra Cost for " + resId1 + ": $" + serviceManager.calculateTotalAddOnCost(resId1));

        System.out.println();
        serviceManager.displayServices(resId2);
        System.out.println("Total Extra Cost for " + resId2 + ": $" + serviceManager.calculateTotalAddOnCost(resId2));

        System.out.println("\nCore booking state remains untouched. Extensibility achieved.");
    }
}
