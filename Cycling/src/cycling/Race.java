import java.time.LocalTime;
import java.util.ArrayList;
import java.time.Duration;

public class Race {
    static int ID = 1;
    int objectID;
    String name;
    String description;
    ArrayList<LocalTime> adjustedElapsedTimes;
    
    
    //Constructor to create Races
    //Checks if a race with the same name already exists or is invalid
    //If all good creates a race with a unique name, description and ID 
    public Race(String name, String description, ArrayList<Race> races) throws IllegalNameException,InvalidNameException {
        if (races.size() == 0) {
            this.name = name;
            this.description = description;
            objectID = ID;
            ID++;
            adjustedElapsedTimes = new ArrayList<>();
        }
        else if (Race.checkRaceName(name, races) == true) {
            throw new IllegalNameException();
        } else if (name == null || name.length() > 30 || name.contains(" ")) {
            throw new InvalidNameException();
        } else {
            this.name = name;
            this.description = description;
            objectID = ID;
            ID++;
            adjustedElapsedTimes = new ArrayList<>();
        }
    }
    // static; checks if the iID of the race exists in an Array List of races
    public static boolean checkID (int raceId, ArrayList<Race> races) {
        boolean found = false;
        for (Race race : races) {
            if (race.objectID == raceId) { 
                found = true;
                break;
            }
        }
        return found;
    }
    // static; returns a formated string about the race
    public static String getRaceDetails(int raceId, ArrayList<Race> races, ArrayList<Stage> stages) {
        String name = null;
        String description = null;
        int numStages = 0;
        int totalLength = 0;

        for (Race race : races) {
            if (race.objectID == raceId) {
                name = race.name;
                description = race.description;
				double length = 0;
				for (Stage stage : stages) {
					if (stage.raceID == raceId) {
						numStages++;
						length = length + stage.length;
					}
				}
                break;
                
            }
        }
        return "Name: " + name + "; Description: " + description + "; Number of stages: " + numStages +"; Total Length: " + totalLength; 
    }
    // static; checks if the provided name is free
    public static boolean checkRaceName(String name, ArrayList<Race> races) {
        boolean found = false;
        // loops  over Array List of races
        for (Race race : races) {
            // checks if the name matches
            if (race.name.equals(name)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static Race findRaceById(ArrayList<Race> races, int raceId) {
        for (Race race : races) {
            if (race.objectID == raceId) {
                return race;
            }
        }
        return null;
    }
     public static void main(String[] args) {
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 30);

        // Calculate duration
        Duration duration = Duration.between(startTime, endTime);

        // Print duration in hours, minutes, and seconds
        System.out.println("Duration: " + duration.toHours() + " hours, " +
                duration.toMinutesPart() + " minutes, " +
                duration.toSecondsPart() + " seconds");

        
    }

}
