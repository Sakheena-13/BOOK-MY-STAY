/**
 * UC2: Object modeling using inheritance.
 */
abstract class RoomV2 {
    private String type;
    private double price;
    public RoomV2(String type, double price) { this.type = type; this.price = price; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public abstract String getFeatures();
}

class SingleRoomV2 extends RoomV2 {
    public SingleRoomV2() { super("Single", 100.0); }
    @Override public String getFeatures() { return "1 Bed"; }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomV2 single = new SingleRoomV2();
        int singleAvailable = 5; // Static availability variable
        System.out.println("Room: " + single.getType() + " | Price: $" + single.getPrice() + " | Left: " + singleAvailable);
    }
}
