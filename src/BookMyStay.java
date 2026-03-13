import java.util.ArrayList;
import java.util.List;

/**
 * UseCase8BookingHistoryReport demonstrates historical tracking and reporting.
 * It uses a List to provide operational visibility into confirmed bookings.
 *
 * @version 8.0
 */
public class BookMyStay {

    /**
     * Represents a confirmed booking record.
     */
    static class ConfirmedBooking {
        private String reservationId;
        private String guestName;
        private String roomType;
        private double totalCost;

        public ConfirmedBooking(String id, String name, String type, double cost) {
            this.reservationId = id;
            this.guestName = name;
            this.roomType = type;
            this.totalCost = cost;
        }

        @Override
        public String toString() {
            return String.format("ID: %-6s | Guest: %-10s | Room: %-12s | Cost: $%.2f",
                    reservationId, guestName, roomType, totalCost);
        }
    }

    /**
     * BookingHistory maintains the long-lived record of all confirmed bookings.
     */
    static class BookingHistory {
        private List<ConfirmedBooking> records = new ArrayList<>();

        public void addRecord(ConfirmedBooking booking) {
            records.add(booking);
        }

        public List<ConfirmedBooking> getAllRecords() {
            // Returns a copy to ensure reporting doesn't modify stored data
            return new ArrayList<>(records);
        }
    }

    /**
     * BookingReportService generates summaries from the history data.
     */
    static class BookingReportService {
        public void generateSummaryReport(BookingHistory history) {
            List<ConfirmedBooking> data = history.getAllRecords();
            double totalRevenue = 0;

            System.out.println("--- Administrative Booking Report ---");
            if (data.isEmpty()) {
                System.out.println("No records found.");
                return;
            }

            for (ConfirmedBooking b : data) {
                System.out.println(b);
                totalRevenue += b.totalCost;
            }

            System.out.println("-------------------------------------");
            System.out.println("Total Bookings: " + data.size());
            System.out.printf("Total Revenue:  $%.2f%n", totalRevenue);
            System.out.println("-------------------------------------");
        }
    }

    public static void main(String[] args) {
        System.out.println("Book My Stay - System v8.0");
        System.out.println("Goal: Historical Tracking & Operational Reporting\n");

        // 1. Initialize History and Reporting Service
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // 2. Simulate successful confirmations being added to history
        // (In a real app, these come from the Allocation Service in UC6)
        history.addRecord(new ConfirmedBooking("R-101", "Alice", "Suite Room", 420.0));
        history.addRecord(new ConfirmedBooking("R-102", "Bob", "Single Room", 100.0));
        history.addRecord(new ConfirmedBooking("R-103", "Diana", "Single Room", 120.0));

        // 3. Admin requests a report
        reportService.generateSummaryReport(history);

        System.out.println("\nAudit trail complete. Data is preserved for administrative review.");
    }
}
