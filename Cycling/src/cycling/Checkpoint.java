import java.util.ArrayList;
import java.util.Comparator;


public class Checkpoint {
  static int count = 1;
  int checkpointId;
  int stageID;
  double location;
  double averageGradient = 0;
  CheckpointType type;
  double length;
  int raceID;

  public Checkpoint(
    int stageID,
    double location,
    double averageGradient,
    double length,
    CheckpointType type,
    ArrayList<Stage> stages
  )
    throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
    if (Stage.checkID(stageID, stages) == true) {
      double stageLength = 0;
      String state = null;
      StageType stageType = null;
      int raceID = 0;
      for (Stage stage : stages) {
        if (stage.stageID == stageID) {
          stageLength = stage.length;
          state = stage.stageState;
          stageType = stage.type;
          raceID = stage.raceID;
        }
      }
      if (location < stageLength) {
        if (state.equals("waiting for results") == false) {
          if (stageType != StageType.TT) {
            //add the climb
            this.type = type;
            this.stageID = stageID;
            this.location = location;
            checkpointId = count;
            count++;
            this.raceID = raceID;
            if (type == CheckpointType.SPRINT) {
              this.averageGradient = 0;
              this.length = 0;
            } else {
              this.averageGradient = averageGradient;
              this.length = length;
            }
          } else {
            throw new InvalidStageTypeException();
          }
        } else {
          throw new InvalidStageStateException();
        }
      } else {
        throw new InvalidLocationException();
      }
    } else {
      throw new IDNotRecognisedException();
    }
  }
  public static boolean checkID (int checkpointId, ArrayList<Checkpoint>checkpoints) {
    boolean found = false;
    for (Checkpoint checkpoint : checkpoints) {
        if (checkpoint.checkpointId == checkpointId) { 
            found = true;
            break;
        }
    }
    return found;
}
public static int[] getCheckpointsInOrder(ArrayList<Checkpoint> checkpoints, int stageId) {
  ArrayList<Checkpoint> checkpointsInStage = new ArrayList<Checkpoint>();
  for (Checkpoint checkpoint : checkpoints) {
      if (checkpoint.stageID == stageId) {
          checkpointsInStage.add(checkpoint);
      }
  }
  checkpointsInStage.sort(Comparator.comparingDouble(Checkpoint::getLocation));
  int[] checkpointIDs = new int[checkpoints.size()];
    // Update checkpoint IDs array
  for (int i = 0; i < checkpointsInStage.size(); i++) {
      checkpointIDs[i] = checkpointsInStage.get(i).checkpointId;
    }


  return checkpointIDs;
}

public double getLocation(){
  return location;
}
public static CheckpointType getCheckPointType(ArrayList<Checkpoint> checkpoints, int checkpointID) {
  CheckpointType typeOfCheckpoint = null; 
  for (Checkpoint checkpoint : checkpoints) {
    if (checkpoint.checkpointId == checkpointID) {
      typeOfCheckpoint = checkpoint.type;
      break;
    }
  }
  return typeOfCheckpoint;
}
}
