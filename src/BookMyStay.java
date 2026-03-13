import java.util.LinkedList;
import java.util.Queue;

/**
 * UC5: Fair request handling using Queue.
 */
class RequestV5 {
    String guest;
    String type;
    public RequestV5(String g, String t) { this.guest = g; this.type = t; }
}

public class BookMyStay {
    public static void main(String[] args) {
        Queue<RequestV5> queue = new LinkedList<>();
        queue.add(new RequestV5("Alice", "Single"));
        queue.add(new RequestV5("Bob", "Suite"));

        System.out.println("Requests in Queue: " + queue.size());
        System.out.println("First in line: " + queue.peek().guest);
    }
}
