import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Stage implements Serializable{
    private static int ID = 1;
    private int stageID;
    private int raceID;
    private String name; 
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;
    private String stageState = "initialized";

    public int getStageId() {
        return stageID;
    }
    public int getRaceId() {
        return raceID;
    }
    public double getLength() {
        return length;
    }
    public String getState() {
        return stageState; 
    }
    public StageType getStageType() {
        return type;
    }
    Stage(int raceID, String name, String description, double length, LocalDateTime startTime, StageType type, ArrayList<Race> races, ArrayList<Stage> stages) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException{
        if (Race.checkID(raceID, races) == false) {
            throw new IDNotRecognisedException();
        } else if (Stage.checkStageName(name, stages) == true) {
            throw new IllegalNameException();
        } else if (name == null || name.length() > 30 || name.contains(" ")) {
            throw new InvalidNameException();
        } else if (length < 5) {
            throw new InvalidLengthException();
        } else {
            this.name = name;
            this.description = description;
            this.length = length;
            this.startTime = startTime;
            this.type = type;
            this.raceID = raceID;
            stageID = ID;
            ID++;
        } 
    }

    public static boolean checkID (int stageId, ArrayList<Stage>stages ) {
        boolean found = false;
        for (Stage stage : stages) {
            if (stage.stageID == stageId) { 
                found = true;
                break;
            }
        }
        return found;
    }
    public static boolean checkStageName(String name, ArrayList<Stage> stages) {
        for (Stage stage : stages) {
            if (stage.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    public static int[] getStagesInOrder(ArrayList<Stage> stages, int raceId) {
        //get the stages of the race
        ArrayList<Stage> stagesInRace = new ArrayList<Stage>();
        for (int j = 0; j < stages.size(); j++) {
            if (stages.get(j).raceID == raceId) {
                stagesInRace.add(stages.get(j));
            }
        }
        // sort the stages based on startTime using a comparator
        Collections.sort(stagesInRace, Comparator.comparing(Stage::getStartTime));

        //return the ids in order 
        int[] stageIDs = new int[stagesInRace.size()];
        for (int i = 0; i < stagesInRace.size(); i++) {
            stageIDs[i] = stagesInRace.get(i).stageID;
    }
        return stageIDs;
    }
    public void setState(String state) {
        this.stageState = state;

    }
    public static String getState(ArrayList<Stage> stages, int stageId) {
        String state = null; 
        for (Stage stage : stages) {
            if (stage.stageID == stageId) {
                state = stage.stageState;
                break;
            }
        }
        return state;
    }
    public static StageType getStageType(int stageId, ArrayList<Stage> stages) {
        StageType typeOfStage = null; 
        for (Stage stage : stages) {
            if (stage.stageID == stageId) {
                typeOfStage = stage.type;
                break;
            }
        }
        return typeOfStage;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
 }
