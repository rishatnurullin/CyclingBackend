import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class Result {
    int stageId;
    int riderId;
    LocalTime[] checkpointTimes;
    int raceID;

    Result(int stageId, int riderId, LocalTime[]checkpointTimes, ArrayList<Stage> stages) {

        for (Stage stage: stages) {
            if (stage.stageID == stageId){
                this.raceID = stage.raceID;
            }
        }
        this.stageId = stageId;
        this.riderId =riderId;
        this.checkpointTimes = checkpointTimes;
    }


public static void main(String[] args) {
    LocalTime startTime = LocalTime.of(10, 30, 0);
    LocalTime endTime = LocalTime.of(12, 15, 30);

        // Calculating the duration between the two times
    Duration duration = Duration.between(startTime, endTime);
    System.out.println(duration);

    // Retrieving individual components of the duration
    long hours = duration.toHours();
    long minutes = duration.toMinutesPart();
    long seconds = duration.toSecondsPart();
    System.out.println(hours);

    System.out.println("Time elapsed: " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds.");
}
}

