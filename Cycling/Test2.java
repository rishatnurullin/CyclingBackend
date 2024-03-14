import java.time.LocalDateTime;
import java.time.LocalTime;

public class Test2 {
    public static void main(String[] args) {
        LocalTime time1 = LocalTime.of(10, 30, 0);
        LocalTime time2 = LocalTime.of(1, 15, 0);

        // Convert LocalTime to LocalDateTime by setting the date component to a default value
        LocalDateTime dateTime1 = LocalDateTime.of(1, 1, 1, time1.getHour(), time1.getMinute(), time1.getSecond());
        LocalDateTime dateTime2 = LocalDateTime.of(1, 1, 1, time2.getHour(), time2.getMinute(), time2.getSecond());

        // Add LocalTime variables by converting them to LocalDateTime and adding them
        LocalDateTime sumDateTime = dateTime1.plusHours(dateTime2.getHour())
                                            .plusMinutes(dateTime2.getMinute())
                                            .plusSeconds(dateTime2.getSecond());

        // Extract the time component from the sum
        LocalTime sumTime = LocalTime.of(sumDateTime.getHour(), sumDateTime.getMinute(), sumDateTime.getSecond());

        System.out.println("Sum of times: " + sumTime);
    }
}
