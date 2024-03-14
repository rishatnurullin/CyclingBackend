//package cycling;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Collections;
//import javax.naming.InvalidNameException;
import java.util.HashMap;
import java.util.Map;


public class CyclingPortalImpl {

  ArrayList<Rider> riders;
  ArrayList<Team> teams;
  ArrayList<Race> races;
  ArrayList<Stage> stages;
  ArrayList<Checkpoint> checkpoints;
  ArrayList<Result> results;

  // Constructor of Cycling Portal Impl
  public CyclingPortalImpl() {
    // initializing emptry ArrayLists
    this.races = new ArrayList<Race>();
    this.teams = new ArrayList<Team>();
    this.stages = new ArrayList<Stage>();
    this.riders = new ArrayList<Rider>();
	  this.checkpoints = new ArrayList<Checkpoint>();
    this.results = new ArrayList<Result>();
  }

  public int[] getRaceIds() {
    // checks if the race ArrayList is empty
    if (races.size() == 0) {
      return new int[0];
    }
    int[] raceIDArray = new int[races.size()];
    for (int i = 0; i < races.size(); i++) {
      raceIDArray[i] = races.get(i).objectID;
    }
    return raceIDArray;
  }

  /**
   * The method creates a staged race in the platform with the given name and
   * description.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param name        Race's name.
   * @param description Race's description (can be null).
   * @throws IllegalNameException If the name already exists in the platform.
   * @throws InvalidNameException If the name is null, empty, has more than 30
   *                              characters, or has white spaces.
   * @return the unique ID of the created race.
   *
   */
  int createRace(String name, String description)
    throws InvalidNameException, IllegalNameException {
      //call Race constuctor
      Race race = new Race(name, description, races);
      //add new race to races ArrayList
      races.add(race);
      return race.objectID;
  }

  /**
   * Get the details from a race.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param raceId The ID of the race being queried.
   * @return Any formatted string containing the race ID, name, description, the
   *         number of stages, and the total length (i.e., the sum of all stages'
   *         length).
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   */
  String viewRaceDetails(int raceId) throws IDNotRecognisedException {
    //check if raceId exists
    if (Race.checkID(raceId, races) == true) {
      String answer = Race.getRaceDetails(raceId, races, stages);
      return answer;
    } else {
      throw new IDNotRecognisedException("Race ID inputted does not exist");
    }
  }

  /**
   * The method removes the race and all its related information, i.e., stages,
   * checkpoints, and results.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param raceId The ID of the race to be removed.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   */

  void removeRaceById(int raceId) throws IDNotRecognisedException {
    // check of raceId exists
    
    if (Race.checkID(raceId, races) == true) {

      //remove stages with the raceId
      stages.removeIf(stage -> stage.raceID == raceId);

    // Remove checkpoints with the raceId
      checkpoints.removeIf(checkpoint -> checkpoint.raceID == raceId);

    // Remove results with the raceId
      results.removeIf(result -> result.raceID == raceId);
      // remove the race with the raceId
      races.removeIf(race -> race.objectID == raceId);
      
      // delete checkponts, stages, results of the stage here  !! do it before deleting the race
    } else {
      throw new IDNotRecognisedException("Race ID inputted does not exist");
    }
  }

  /**
   * The method queries the number of stages created for a race.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param raceId The ID of the race being queried.
   * @return The number of stages created for the race.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   */

  int getNumberOfStages(int raceId) throws IDNotRecognisedException {
    if (!Race.checkID(raceId, races)) {
      throw new IDNotRecognisedException("Race ID inputted does not exist");
    }
    int count = 0;
    for (Stage stage : stages) {
        if (stage.raceID == raceId) {
            count++;
        }
    }
    return count;
  }

  /**
   * Creates a new stage and adds it to the race.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param raceId      The race which the stage will be added to.
   * @param stageName   An identifier name for the stage.
   * @param description A descriptive text for the stage.
   * @param length      Stage length in kilometres.
   * @param startTime   The date and time in which the stage will be raced. It
   *                    cannot be null.
   * @param type        The type of the stage. This is used to determine the
   *                    amount of points given to the winner.
   * @return the unique ID of the stage.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   * @throws IllegalNameException     If the name already exists in the platform.
   * @throws InvalidNameException     If the name is null, empty, has more than 30
   *                              	characters, or has white spaces.
   * @throws InvalidLengthException   If the length is less than 5km.
   */
  int addStageToRace(
    int raceId,
    String stageName,
    String description,
    double length,
    LocalDateTime startTime,
    StageType type
  )
    throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
      // call Stage constructor 
    Stage stage = new Stage(
      raceId,
      stageName,
      description,
      length,
      startTime,
      type,
      races,
      stages
    );
    // add the new stage object to stages ArrayList
    stages.add(stage);
    return stage.stageID;
  }

  /**
   * Retrieves the list of stage IDs of a race.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param raceId The ID of the race being queried.
   * @return An array of stage IDs ordered (from first to last) by their sequence in the
   *         race or an empty array if none exists.
   * @throws IDNotRecognisedException If the ID does not match to any race in the
   *                                  system.
   */

  int[] getRaceStages(int raceId) throws IDNotRecognisedException {
    // check if raceId exists
    if (Race.checkID(raceId, races) == false) {
      throw new IDNotRecognisedException();
    } else {
      return Stage.getStagesInOrder(stages, raceId);
    }
  }

  /**
   * Gets the length of a stage in a race, in kilometres.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage being queried.
   * @return The stage's length.
   * @throws IDNotRecognisedException If the ID does not match to any stage in the
   *                                  system.
   */
  double getStageLength(int stageId) throws IDNotRecognisedException {
    for (Stage stage : stages) {
      if (stage.stageID == stageId) {
          return stage.length;
      }
  }
    throw new IDNotRecognisedException();
  }

  /**
   * Removes a stage and all its related data, i.e., checkpoints and results.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage being removed.
   * @throws IDNotRecognisedException If the ID does not match to any stage in the
   *                                  system.
   */
  void removeStageById(int stageId) throws IDNotRecognisedException {
    // Check if stageId exists
    if (Stage.checkID(stageId, stages) == false) {
      throw new IDNotRecognisedException();
  }

    // Remove checkpoints of the stage
    checkpoints.removeIf(checkpoint -> checkpoint.stageID == stageId);

    // Remove results of the stage
    results.removeIf(result -> result.stageId == stageId);

    // Remove the stage
    stages.removeIf(stage -> stage.stageID == stageId);
  }

  /**
   * Adds a climb checkpoint to a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId         The ID of the stage to which the climb checkpoint is
   *                        being added.
   * @param location        The kilometre location where the climb finishes within
   *                        the stage.
   * @param type            The category of the climb - {@link CheckpointType#C4},
   *                        {@link CheckpointType#C3}, {@link CheckpointType#C2},
   *                        {@link CheckpointType#C1}, or {@link CheckpointType#HC}.
   * @param averageGradient The average gradient for the climb.
   * @param length          The length of the climb in kilometre.
   * @return The ID of the checkpoint created.
   * @throws IDNotRecognisedException   If the ID does not match to any stage in
   *                                    the system.
   * @throws InvalidLocationException   If the location is out of bounds of the
   *                                    stage length.
   * @throws InvalidStageStateException If the stage is "waiting for results".
   * @throws InvalidStageTypeException  Time-trial stages cannot contain any
   *                                    checkpoint.
   */
  int addCategorizedClimbToStage(
    int stageId,
    Double location,
    CheckpointType type,
    Double averageGradient,
    Double length
  )
    throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		Checkpoint climbCheckpoint = new Checkpoint(stageId, location, averageGradient, length, type, stages);
		checkpoints.add(climbCheckpoint);
		return climbCheckpoint.checkpointId;

	}

  /**
   * Adds an intermediate sprint to a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId  The ID of the stage to which the intermediate sprint checkpoint
   *                 is being added.
   * @param location The kilometre location where the intermediate sprint finishes
   *                 within the stage.
   * @return The ID of the checkpoint created.
   * @throws IDNotRecognisedException   If the ID does not match to any stage in
   *                                    the system.
   * @throws InvalidLocationException   If the location is out of bounds of the
   *                                    stage length.
   * @throws InvalidStageStateException If the stage is "waiting for results".
   * @throws InvalidStageTypeException  Time-trial stages cannot contain any
   *                                    checkpoint.
   */
  int addIntermediateSprintToStage(int stageId, double location)
    throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		Checkpoint checkpoint = new Checkpoint(stageId, location, 0, 0, CheckpointType.SPRINT, stages);
    checkpoints.add(checkpoint);
    return checkpoint.checkpointId;
	}

  /**
   * Removes a checkpoint from a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param checkpointId The ID of the checkpoint to be removed.
   * @throws IDNotRecognisedException   If the ID does not match to any checkpoint in
   *                                    the system.
   * @throws InvalidStageStateException If the stage is "waiting for results".
   */
  void removeCheckpoint(int checkpointId)
    throws IDNotRecognisedException, InvalidStageStateException {
      //check if checkpointId exists
      if (Checkpoint.checkID(checkpointId, checkpoints) == true) {
        String state = null;
        for (Checkpoint checkpoint : checkpoints) {
          if (checkpoint.checkpointId == checkpointId) {
            // retrive the state of the stage
            state = Stage.getState(stages, checkpoint.stageID);
            if (state.equals("waiting for results")) {
              throw new InvalidStageStateException();
            } else {
              // remove the checkpoint from checkpoints ArrayList
              checkpoints.remove(checkpoint);
            }
            break;
          }
        }
      } else {
        throw new IDNotRecognisedException();
      }
	}

  /**
   * Concludes the preparation of a stage. After conclusion, the stage's state
   * should be "waiting for results".
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage to be concluded.
   * @throws IDNotRecognisedException   If the ID does not match to any stage in
   *                                    the system.
   * @throws InvalidStageStateException If the stage is "waiting for results".
   */
  void concludeStagePreparation(int stageId)
    throws IDNotRecognisedException, InvalidStageStateException {
      //check if stageId exists
      if (Stage.checkID(stageId, stages) == false) {
        throw new IDNotRecognisedException();
      } else {
        // check if the state of the stage is eligible
        if (Stage.getState(stages, stageId) == "waiting for results") {
          throw new InvalidStageStateException();
        }
        else {
          //change the state of the stage
          for (Stage stage : stages) {
            if (stage.stageID == stageId) {
              stage.setState("waiting for results");
              break;
            }
          }
            }
          }
        }

  /**
   * Retrieves the list of checkpoint (mountains and sprints) IDs of a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage being queried.
   * @return The list of checkpoint IDs ordered (from first to last) by their location in the
   *         stage.
   * @throws IDNotRecognisedException If the ID does not match to any stage in the
   *                                  system.
   */
  int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
    //check if stageId exists
	if (Stage.checkID(stageId, stages) == false) {
		throw new IDNotRecognisedException();
	} else {
    // sort and return an array of checkpoint ids
    return Checkpoint.getCheckpointsInOrder(checkpoints, stageId);
	}
  }

  /**
   * Creates a team with name and description.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param name        The identifier name of the team.
   * @param description A description of the team.
   * @return The ID of the created team.
   * @throws IllegalNameException If the name already exists in the platform.
   * @throws InvalidNameException If the name is null, empty, has more than 30
   *                              characters, or has white spaces.
   */
  int createTeam(String name, String description)
    throws IllegalNameException, InvalidNameException {
      //call Team constructor
    Team team = new Team(name, description, teams);
    // add team to teams ArrayList
    teams.add(team);
    return team.teamID;
  }

  /**
   * Removes a team from the system.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param teamId The ID of the team to be removed.
   * @throws IDNotRecognisedException If the ID does not match to any team in the
   *                                  system.
   */
  void removeTeam(int teamId) throws IDNotRecognisedException {
    // Check if teamId exists
    if (!Team.checkID(teamId, teams)) {
      throw new IDNotRecognisedException();
    }

    // Remove riders in the team and store their riderIds
    ArrayList<Integer> riderIdsToRemove = new ArrayList<>();
    for (Rider rider : riders) {
      if (rider.teamID == teamId) {
          riderIdsToRemove.add(rider.riderID);
      }
    }
    riders.removeIf(rider -> rider.teamID == teamId);

    // Remove the team
    teams.removeIf(team -> team.teamID == teamId);

    // Remove the results using riderIds
    results.removeIf(result -> riderIdsToRemove.contains(result.riderId));
  }

  /**
   * Get the list of teams' IDs in the system.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @return The list of IDs from the teams in the system. An empty list if there
   *         are no teams in the system.
   *
   */
  int[] getTeams() {
    int[] teamIDArray = new int[teams.size()];
    // loop over teams and store each teamID in teamIDArray
    for (int i = 0; i < teams.size(); i++) {
      teamIDArray[i] = teams.get(i).teamID;
    }
    return teamIDArray;
  }

  /**
   * Get the riders of a team.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param teamId The ID of the team being queried.
   * @return A list with riders' ID.
   * @throws IDNotRecognisedException If the ID does not match to any team in the
   *                                  system.
   */
  int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
    // store the riderIds of the team in listOfRiderID
    ArrayList<Integer> listOfRiderID = new ArrayList<Integer>();
    for (Rider rider : riders) {
      if (rider.teamID == teamId) {
        listOfRiderID.add(rider.riderID);
      }
    }
    //conver listOfRiderID into int[]
    int[] finalList = new int[listOfRiderID.size()];
    for (int i = 0; i < listOfRiderID.size(); i++) {
      finalList[i] = listOfRiderID.get(i);
    }
    return finalList;
  }

  /**
   * Creates a rider.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param teamID      The ID rider's team.
   * @param name        The name of the rider.
   * @param yearOfBirth The year of birth of the rider.
   * @return The ID of the rider in the system.
   * @throws IDNotRecognisedException If the ID does not match to any team in the
   *                                  system.
   * @throws IllegalArgumentException If the name of the rider is null or empty,
   *                                  or the year of birth is less than 1900.
   */
  int createRider(int teamID, String name, int yearOfBirth)
    throws IDNotRecognisedException, IllegalArgumentException {
      // call the Rider constructor
    Rider rider = new Rider(name, yearOfBirth, teamID, teams);
    // add the new rider to riders
    riders.add(rider);
    return rider.riderID;
  }

  /**
   * Removes a rider from the system. When a rider is removed from the platform,
   * all of its results should be also removed. Race results must be updated.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param riderId The ID of the rider to be removed.
   * @throws IDNotRecognisedException If the ID does not match to any rider in the
   *                                  system.
   */
  void removeRider(int riderId) throws IDNotRecognisedException {
    // check if riderId exists
    if (Rider.checkID(riderId, riders) == false) {
      throw new IDNotRecognisedException();
    } else {
      riders.removeIf(rider -> rider.riderID == riderId);
      // remove the results with the riderId
      results.removeIf(result -> result.riderId == riderId);
    }
  }

  /**
   * Record the times of a rider in a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId     The ID of the stage the result refers to.
   * @param riderId     The ID of the rider.
   * @param checkpointTimes An array of times at which the rider reached each of the
   *                    checkpoints of the stage, including the start time and the
   *                    finish line.
   * @throws IDNotRecognisedException    If the ID does not match to any rider or
   *                                     stage in the system.
   * @throws DuplicatedResultException   Thrown if the rider has already a result
   *                                     for the stage. Each rider can have only
   *                                     one result per stage.
   * @throws InvalidCheckpointTimesException Thrown if the length of checkpointTimes is
   *                                     not equal to n+2, where n is the number
   *                                     of checkpoints in the stage; +2 represents
   *                                     the start time and the finish time of the
   *                                     stage.
   * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
   *                                     results". Results can only be added to a
   *                                     stage while it is "waiting for results".
   */
  void registerRiderResultsInStage(
    int stageId,
    int riderId,
    LocalTime... checkpointTimes
  )
    throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {

      // Check if stageId and riderId exist
      if (!Stage.checkID(stageId, stages) || !Rider.checkID(riderId, riders)) {
        throw new IDNotRecognisedException();
    }

  // Check if the result already exists
      for (Result result : results) {
          if (result.riderId == riderId && result.stageId == stageId) {
              throw new DuplicatedResultException();
          }
      }

  // Find the stage and validate stage state

      int countOfCheckpoints = 0;
      for (Stage stage : stages) {
          if (stage.stageID == stageId) {
              if (!stage.stageState.equals("waiting for results")) {
                  throw new InvalidStageStateException();
              }
              countOfCheckpoints = this.getStageCheckpoints(stageId).length;
              break;
          }
      }

      // Check if checkpointTimes has the correct length
      if (checkpointTimes.length != countOfCheckpoints + 2) {
        throw new InvalidCheckpointTimesException();
      }

      // Register the result
      Result result = new Result(stageId, riderId, checkpointTimes, stages);
      results.add(result);
    }

  /**
   * Get the times of a rider in a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any exceptions are
   * thrown.
   *
   * @param stageId The ID of the stage the result refers to.
   * @param riderId The ID of the rider.
   * @return The array of times at which the rider reached each of the checkpoints
   *         of the stage and the total elapsed time. The elapsed time is the
   *         difference between the finish time and the start time. Return an
   *         empty array if there is no result registered for the rider in the
   *         stage. Assume the total elapsed time of a stage never exceeds 24h
   *         and, therefore, can be represented by a LocalTime variable. There is
   *         no need to check for this condition or raise any exception.
   * @throws IDNotRecognisedException If the ID does not match to any rider or
   *                                  stage in the system.
   */
  LocalTime[] getRiderResultsInStage(int stageId, int riderId)
    throws IDNotRecognisedException {
      // check if stageId and riderId exist
      if (Stage.checkID(stageId, stages) == false || Rider.checkID(riderId, riders) == false) {
        throw new IDNotRecognisedException();
      } else {
        // assuming the start time is first and finish time is last in checkpointTimes attribute of Result
        for (Result result : results) {
          //find the needed result
          if (result.riderId == riderId & result.stageId == stageId) {
            int length = result.checkpointTimes.length;
            LocalTime[] arrayOfTimes = new LocalTime[length - 1];
            // calculate the duration between start and finish times
            Duration totalDuration = Duration.between(result.checkpointTimes[0],result.checkpointTimes[length-1]);
            // convert it into a LocalTime variable
            LocalTime totalFinalTime = LocalTime.of((int) totalDuration.toHours(), totalDuration.toMinutesPart(), totalDuration.toSecondsPart(), totalDuration.toNanosPart());
            //store it as the alst element
            arrayOfTimes[arrayOfTimes.length-1] = totalFinalTime;
            //copy all the checkpoint times to arrayOfTimes
            for (int i = 1; i < result.checkpointTimes.length-1; i++) {
              arrayOfTimes[i-1] = result.checkpointTimes[i];
            }
            return arrayOfTimes;
          }
        }
        // output an empty array if no result is found
        return new LocalTime[0];
      }
    }

  /**
   * For the general classification, the aggregated time is based on the adjusted
   * elapsed time, not the real elapsed time. Adjustments are made to take into
   * account groups of riders finishing very close together, e.g., the peloton. If
   * a rider has a finishing time less than one second slower than the
   * previous rider, then their adjusted elapsed time is the smallest of both. For
   * instance, a stage with 200 riders finishing "together" (i.e., less than 1
   * second between consecutive riders), the adjusted elapsed time of all riders
   * should be the same as the first of all these riders, even if the real gap
   * between the 200th and the 1st rider is much bigger than 1 second. There is no
   * adjustments on elapsed time on time-trials.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage the result refers to.
   * @param riderId The ID of the rider.
   * @return The adjusted elapsed time for the rider in the stage. Return null if
   * 		  there is no result registered for the rider in the stage.
   * @throws IDNotRecognisedException   If the ID does not match to any rider or
   *                                    stage in the system.
   */
  LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId)
    throws IDNotRecognisedException {
      if (Stage.checkID(stageId, stages) == false) {
        throw new IDNotRecognisedException();
      } else if (Rider.checkID(riderId, riders) == false) {
        System.out.println("unrecogrnised rider");
        throw new IDNotRecognisedException();
      }
      else {
        // sort results
        //store the elapsed times here
        ArrayList<LocalTime> sortedResultsForStage = new ArrayList<LocalTime>();
        //store riderIds who participated in  the stage
        int [] sortedRiderIdsInStage = this.getRidersRankInStage(stageId);
        int indexOfRiderId = 0;
        for (int i = 0; i < sortedRiderIdsInStage.length; i++) {
          int length = this.getRiderResultsInStage(stageId,sortedRiderIdsInStage[i]).length;
          LocalTime elapsedTime = this.getRiderResultsInStage(stageId,sortedRiderIdsInStage[i])[length -1];
          sortedResultsForStage.add(elapsedTime);
          if (sortedRiderIdsInStage[i] == riderId) {
            indexOfRiderId = i;
          }
        }
        // calculating the adjusted elapsed time
        LocalTime adjustedElapsedTime = sortedResultsForStage.get(indexOfRiderId);
        for (int i = -1; i > (indexOfRiderId*-1)-1;i--) {
          Duration duration = Duration.between(sortedResultsForStage.get(indexOfRiderId+i), adjustedElapsedTime);
          if (duration.getSeconds() < 1) {
            adjustedElapsedTime = sortedResultsForStage.get(indexOfRiderId+i);
          } else {
            break;
          }
        }
        return adjustedElapsedTime;
      }
    }

  /**
   * Removes the stage results from the rider.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage the result refers to.
   * @param riderId The ID of the rider.
   * @throws IDNotRecognisedException If the ID does not match to any rider or
   *                                  stage in the system.
   */
  void deleteRiderResultsInStage(int stageId, int riderId)
    throws IDNotRecognisedException {

      //check if stageId and riderId exist
      if (!Stage.checkID(stageId, stages) || !Rider.checkID(riderId, riders)) {
        throw new IDNotRecognisedException();
    }
      // Remove results for the specified rider and stage
      results.removeIf(result -> result.riderId == riderId && result.stageId == stageId);
    }

  /**
   * Get the riders finished position in a a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage being queried.
   * @return A list of riders ID sorted by their elapsed time. An empty list if
   *         there is no result for the stage.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */
  int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
    if ( Stage.checkID(stageId, stages) == false) {
      throw new IDNotRecognisedException();
    } else {
      // create an emptty int array to store thcheck 
      int count = 0;
      ArrayList<Integer> ridersInStage = new ArrayList<Integer>();
      ArrayList<LocalTime> timeElapsedOfRiders = new ArrayList<LocalTime>();
      for (Result result :results) {
        if (result.stageId == stageId) {
          count++;
          ridersInStage.add(result.riderId);
          int length = this.getRiderResultsInStage(stageId, result.riderId).length;
          LocalTime timeElapsed = this.getRiderResultsInStage(stageId, result.riderId)[length-1];
          timeElapsedOfRiders.add(timeElapsed);
        }
      }
      if (count == 0) {
        return new int[0];
      }
      boolean sorted = false;
      while (sorted == false) {
        sorted = true;
        for (int i = 0; i < ridersInStage.size()-1; i++){
          if (timeElapsedOfRiders.get(i).isAfter(timeElapsedOfRiders.get(i+1))) {
            sorted = false;
            LocalTime temp = timeElapsedOfRiders.get(i);
            timeElapsedOfRiders.set(i,timeElapsedOfRiders.get(i+1));
            timeElapsedOfRiders.set(i+1,temp);
  
            int tempIndex = ridersInStage.get(i);
            ridersInStage.set(i, ridersInStage.get(i+1));
            ridersInStage.set(i+1, tempIndex);
          }
  
        }
      } 
    int [] finalArray = new int[ridersInStage.size()];
    for (int j = 0; j < ridersInStage.size(); j++) {
      finalArray[j] = ridersInStage.get(j);
     } 
     return finalArray;
    }
  }

  /**
   * Get the adjusted elapsed times of riders in a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any exceptions are
   * thrown.
   *
   * @param stageId The ID of the stage being queried.
   * @return The ranked list of adjusted elapsed times sorted by their finish
   *         time. An empty list if there is no result for the stage. These times
   *         should match the riders returned by
   *         {@link #getRidersRankInStage(int)}. Assume the total elapsed time of
   *         in a stage never exceeds 24h and, therefore, can be represented by a
   *         LocalTime variable. There is no need to check for this condition or
   *         raise any exception.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */
  LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId)
    throws IDNotRecognisedException{
      if (Stage.checkID(stageId, stages) == false) {
        throw new IDNotRecognisedException();
      } else {
        // how to check if there are no results for the stage
        int count = 0;
        for (Result result : results) {
          if (result.stageId == stageId) {
            count++;
          } 
        }
        if (count != 0) {
          int[] ridersInStageRanked = this.getRidersRankInStage(stageId);

          ArrayList<LocalTime> rankedAdjustedTimes = new ArrayList<LocalTime>();
          for (int riderId : ridersInStageRanked) {
            rankedAdjustedTimes.add(this.getRiderAdjustedElapsedTimeInStage(stageId, riderId));
          }
          LocalTime[] finalArrayOfTimes = new LocalTime[rankedAdjustedTimes.size()];
          for (int i = 0; i < rankedAdjustedTimes.size(); i++) {
            finalArrayOfTimes[i] = rankedAdjustedTimes.get(i);
          } 
          return finalArrayOfTimes;
        
        } else {
          return new LocalTime[0];
        }
        
      }
      
    }

  /**
   * Get the number of points obtained by each rider in a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage being queried.
   * @return The ranked list of points each riders received in the stage, sorted
   *         by their elapsed time. An empty list if there is no result for the
   *         stage. These points should match the riders returned by
   *         {@link #getRidersRankInStage(int)}.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */
  int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException{
    //check if the stageId exists
    if (!Stage.checkID(stageId, stages)) {
        throw new IDNotRecognisedException("Stage ID not recognized.");
    }
    
    // point distributions for differnt stages
    Map<StageType, int[]> pointsDistributionMap = new HashMap<>();
    pointsDistributionMap.put(StageType.FLAT, new int[]{50,30,20,18,16,14,12,10,8,7,6,5,4,3,2});
    pointsDistributionMap.put(StageType.HIGH_MOUNTAIN, new int[]{20,17,15,13,11,10,9,8,7,6,5,4,3,2,1});
    pointsDistributionMap.put(StageType.MEDIUM_MOUNTAIN, new int[]{30,25,22,19,17,15,13,11,9,7,6,5,4,3,2});
    pointsDistributionMap.put(StageType.TT, new int[]{20,17,15,13,11,10,9,8,7,6,5,4,3,2,1});
    int[] sprintPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};

    // Get the sorted rider IDs for this stage
    int[] rankedRiderIds = this.getRidersRankInStage(stageId);
    
    //this array will store the final points for the rac 
    int[] finalPointsArray = new int[rankedRiderIds.length];
    Arrays.fill(finalPointsArray, 0); // Initialize points to 0
    
    // Assign points based on stage type
    StageType stageType = Stage.getStageType(stageId, stages);
    int[] pointDistribution = pointsDistributionMap.getOrDefault(stageType, new int[0]);
    
    // Assign points to riders based on their rank and the points distribution for the stage
    for (int i = 0; i < rankedRiderIds.length && i < pointDistribution.length; i++) {
        finalPointsArray[i] = pointDistribution[i];
    }
    ArrayList<Integer> rankedRiderIdsInStage = new ArrayList<>();
    for (int i = 0; i < this.getRidersRankInStage(stageId).length; i++) {
      rankedRiderIdsInStage.add(this.getRidersRankInStage(stageId)[i]);
    }
    // Process sprints if applicable
    if (stageType != StageType.TT) { 

        // Assuming getStageCheckpoints returns an ordered list of checkpoints IDs for the stage
      int[] checkpointIdsInStage = this.getStageCheckpoints(stageId);
      for (int k = 0; k < checkpointIdsInStage.length; k++) {

          

          //check that the checkpoint is a sprint
        if (Checkpoint.getCheckPointType(checkpoints, checkpointIdsInStage[k]) == CheckpointType.SPRINT) {

            //another sorted list of riderIds in the stage
          ArrayList<Integer> sortedIDsforCheckpoint = rankedRiderIdsInStage;

            //get the times for that checkpoint
          ArrayList<LocalTime> timesForCheckPoint = new ArrayList<>();
          for (Integer riderId : rankedRiderIdsInStage) {
            LocalTime time = this.getRiderResultsInStage(stageId, riderId)[k];
            timesForCheckPoint.add(time);
          }
            
          boolean sorted = false;
          while (sorted == false) {
            sorted = true;
            for (int i = 0; i < sortedIDsforCheckpoint.size()-1; i++){
              if (timesForCheckPoint.get(i).isAfter(timesForCheckPoint.get(i+1))) {
                sorted = false;
                LocalTime temp = timesForCheckPoint.get(i);
                timesForCheckPoint.set(i,timesForCheckPoint.get(i+1));
                timesForCheckPoint.set(i+1,temp);
      
                int tempIndex = sortedIDsforCheckpoint.get(i);
                sortedIDsforCheckpoint.set(i, sortedIDsforCheckpoint.get(i+1));
                sortedIDsforCheckpoint.set(i+1, tempIndex);
              }
            }
            }
          for (int b = 0; b < sprintPoints.length && b < sortedIDsforCheckpoint.size(); b++) {
            int pointToAdd= sprintPoints[b];
            int indexOfRider = rankedRiderIdsInStage.indexOf(sortedIDsforCheckpoint.get(b));
            finalPointsArray[indexOfRider] += pointToAdd;
          }
          }
        }
    }
    return finalPointsArray;
  }

  /**
   * Get the number of mountain points obtained by each rider in a stage.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param stageId The ID of the stage being queried.
   * @return The ranked list of mountain points each riders received in the stage,
   *         sorted by their finish time. An empty list if there is no result for
   *         the stage. These points should match the riders returned by
   *         {@link #getRidersRankInStage(int)}.
   * @throws IDNotRecognisedException If the ID does not match any stage in the
   *                                  system.
   */
  int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException{
    
    //CHECK IF THE ID IS VALID
    if (!Stage.checkID(stageId, stages)) {
      throw new IDNotRecognisedException();
    }
    ///////////////////////////////////////////

    // SET UP THE POINTS FOR THE DIFFERNT CLIMBS
    Map<CheckpointType, int[]> pointsDistributionMap = new HashMap<>();
    pointsDistributionMap.put(CheckpointType.C4, new int[]{1});
    pointsDistributionMap.put(CheckpointType.C3, new int[]{2,1});
    pointsDistributionMap.put(CheckpointType.C2, new int[]{5,3,2,1});
    pointsDistributionMap.put(CheckpointType.C1, new int[]{10,8,6,4,2,1});
    pointsDistributionMap.put(CheckpointType.HC, new int[]{20,15,12,10,8,6,4,2});
    ///////////////////////////////////////////////////////////////////////////////////
    

    //CREATE AN ARRAYLIST OF RANKED RIDER IDS IN THE STAGE
    ArrayList<Integer> rankedRiderIdsInStage = new ArrayList<>();
    for (int i = 0; i < this.getRidersRankInStage(stageId).length; i++) {
      rankedRiderIdsInStage.add(this.getRidersRankInStage(stageId)[i]);
    }
    /////////////////////////////////////////////////////////////////////

    //CREATE THE ARRAY THAT WILL STORE THE MOUNTAIN POINTS
    int[] finalPointsArray = new int[rankedRiderIdsInStage.size()];
    Arrays.fill(finalPointsArray, 0); // Initialize points to 0
    //////////////////////////////////////////////////////////////////


    //LOOPING OVER THE CHECKPOINTS IN THE STAGE
    int[] checkpointIdsInStage = this.getStageCheckpoints(stageId);
    for (int k = 0; k < checkpointIdsInStage.length; k++) {
      
      //CHECK IF THE CHECKPOINT IS NOT A SPRINT
      if (Checkpoint.getCheckPointType(checkpoints,checkpointIdsInStage[k]) != CheckpointType.SPRINT) {
        //STORES THE RANKINGS OF IDS FOR THIS EXACT CHECKPOINT
        ArrayList<Integer> sortedIDsforCheckpoint = new ArrayList<>();
        for (int i : rankedRiderIdsInStage) {
          sortedIDsforCheckpoint.add(i);
        }

        //GET WHAT TYPE OF CLIMB IT IS
        CheckpointType checkpointType = Checkpoint.getCheckPointType(checkpoints, checkpointIdsInStage[k]);
        int[] pointDistribution = pointsDistributionMap.getOrDefault(checkpointType, new int[0]);
        ///////////////////////////////////////////////////


        //GET THE RAW TIMES FOR THAT CHECKPOINT FOR EACH RIDER. THE TIMES ARE ORDERED BY 
        ArrayList<LocalTime> timesForCheckPoint = new ArrayList<>();
        for (Integer riderId : rankedRiderIdsInStage) {
          LocalTime time = this.getRiderResultsInStage(stageId, riderId)[k];
          timesForCheckPoint.add(time);
        }
        /////////////////////////////////////////////////////////////////////

        //SORT THE timesForCheckPoint ARRAY AND sortedIDsforCheckpoint AT THE SAME TIME
        boolean sorted = false;
        while (sorted == false) {
          sorted = true;
          for (int i = 0; i < sortedIDsforCheckpoint.size()-1; i++){
            if (timesForCheckPoint.get(i).isAfter(timesForCheckPoint.get(i+1))) {
              sorted = false;
              LocalTime temp = timesForCheckPoint.get(i);
              timesForCheckPoint.set(i,timesForCheckPoint.get(i+1));
              timesForCheckPoint.set(i+1,temp);
    
              int tempIndex = sortedIDsforCheckpoint.get(i);
              sortedIDsforCheckpoint.set(i, sortedIDsforCheckpoint.get(i+1));
              sortedIDsforCheckpoint.set(i+1, tempIndex);
            }
          }
          }
          /////////////////////////////////////////////////////////////////////////

          
          for (int b = 0; b < pointDistribution.length && b < sortedIDsforCheckpoint.size(); b++) {
            int pointToAdd = pointDistribution[b];
            int indexOfRider = rankedRiderIdsInStage.indexOf(sortedIDsforCheckpoint.get(b));
            finalPointsArray[indexOfRider] += pointToAdd;
          }
      }
    }
    return finalPointsArray;
    }
  /**
	 * The method removes the race and all its related information, i.e., stages,
	 * checkpoints, and results.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name The name of the race to be removed.
	 * @throws NameNotRecognisedException If the name does not match to any race in
	 *                                    the system.
	 */
	void removeRaceByName(String name) throws NameNotRecognisedException {
    if (Race.checkRaceName(name, races) == false) {
      throw new NameNotRecognisedException();
    } else {
      ArrayList<Race> raceToRemove = new ArrayList<Race>();
      for (Race race : races) {
        if (race.name.equals(name)) {
          raceToRemove.add(race);
          break;
        } 
      }
      races.removeAll(raceToRemove);
    }}
    /**
	 * Get the general classification rank of riders in a race.
	 * <p>
	 * The state of this CyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return A ranked list of riders' IDs sorted ascending by the sum of their
	 *         adjusted elapsed times in all stages of the race. That is, the first
	 *         in this list is the winner (least time). An empty list if there is no
	 *         result for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
    int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
      // total adjusted elapsed time.
      
      if (Race.checkID(raceId, races) == false) {
        throw new IDNotRecognisedException(); 
      }
        
      // get the Ids of riders from a race
      int stageId = 0;
      for (Stage stage : stages) {
        if (stage.raceID == raceId) {
          stageId = stage.stageID; 
        }
      }
      
      //assuming all the riders that particiapte in a stage of a race, participate in all the other
      // get the array List of rider Ids
      ArrayList<Integer> riderIdsInRace = new ArrayList<Integer>();
        for (int i = 0; i < this.getRidersRankInStage(stageId).length; i++) {
          riderIdsInRace.add(this.getRidersRankInStage(stageId)[i]);
        }
      ///////////////////////////////////////////////////////////
      
      
      ArrayList<LocalTime> totalAdjElapsedTimeRiders = new ArrayList<>();
      //LOOP OVER THE RIDER IDS IN THE RACE
      for (int i = 0; i < riderIdsInRace.size(); i++) {
        int rideId = riderIdsInRace.get(i);
        //THIS WILL HOLD THE OVERALL ADJUSTED ELAPSED TIME FOR A RIDER
        LocalTime sum = LocalTime.of(0, 0, 0);
        int [] stageIds = this.getRaceStages(raceId);
        
        // LOOP OVER THE STAGES IN RACE
        for (int j = 0; j < stageIds.length; j++) {
          System.out.println(Arrays.toString(this.getRidersPointsInStage(stageId)));
          int stageIdCurrent  = stageIds[j];
          
          //get the adjusted elapsed time in stage FOR THE RIDER
          LocalTime AdjustedTimeForStage = this.getRiderAdjustedElapsedTimeInStage(stageIdCurrent, rideId);

          //add it to the sum
          LocalDateTime dateTime1 = LocalDateTime.of(1, 1, 1, AdjustedTimeForStage.getHour(), AdjustedTimeForStage.getMinute(), AdjustedTimeForStage.getSecond());
          LocalDateTime dateTime2 = LocalDateTime.of(1, 1, 1, sum.getHour(), sum.getMinute(), sum.getSecond());

          // Add LocalTime variables by converting them to LocalDateTime and adding them
          LocalDateTime sumDateTime = dateTime1.plusHours(dateTime2.getHour())
                                            .plusMinutes(dateTime2.getMinute())
                                            .plusSeconds(dateTime2.getSecond());

        // Extract the time component from the sum
          LocalTime sumTime = LocalTime.of(sumDateTime.getHour(), sumDateTime.getMinute(), sumDateTime.getSecond());
          sum = sumTime;
        }
        totalAdjElapsedTimeRiders.add(sum);
        //NOW WE HAVE THE TOTAL ADJUSTED ELAPSED TIME FOR ALL RIDERS
      }
      //SORT BOTH ARRAY LISTS
      boolean sorted = false;
      while (sorted == false) {
        sorted = true;
        for (int i = 0; i < totalAdjElapsedTimeRiders.size()-1; i++){
          if (totalAdjElapsedTimeRiders.get(i).isAfter(totalAdjElapsedTimeRiders.get(i+1))) {
            sorted = false;
            LocalTime temp = totalAdjElapsedTimeRiders.get(i);
            totalAdjElapsedTimeRiders.set(i,totalAdjElapsedTimeRiders.get(i+1));
            totalAdjElapsedTimeRiders.set(i+1,temp);
  
            int tempIndex = riderIdsInRace.get(i);
            riderIdsInRace.set(i, riderIdsInRace.get(i+1));
            riderIdsInRace.set(i+1, tempIndex);
          }
        }
        }
        for (Race race : races) {
          if (race.objectID == raceId) {
            race.adjustedElapsedTimes = totalAdjElapsedTimeRiders;
          }
        }
      //CONVERT THE ARRAY LIST INTO A ARRAY
      int []generalClassificationRank = new int[riderIdsInRace.size()];
      for (int i = 0; i < generalClassificationRank.length; i++) {

        generalClassificationRank[i] = riderIdsInRace.get(i);
      }
      return generalClassificationRank;
      
    }

  /**
   * Method empties this MiniCyclingPortal of its contents and resets all
   * internal counters.
   */
  
  void eraseCyclingPortal() {
    this.races = new ArrayList<Race>();
    this.riders = new ArrayList<Rider>();
    this.teams = new ArrayList<Team>();
    this.stages = new ArrayList<Stage>();
    this.checkpoints = new ArrayList<Checkpoint>();
  }

  LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
    if (Race.checkID(raceId, races) == false) {
      throw new IDNotRecognisedException();
    }
    Race race = Race.findRaceById(races, raceId);
    return race.adjustedElapsedTimes.toArray(new LocalTime[0]);
  }
  /**
   * Method saves this MiniCyclingPortal contents into a serialised file,
   * with the filename given in the argument.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param filename Location of the file to be saved.
   * @throws IOException If there is a problem experienced when trying to save the
   *                     store contents to the file.
   */
  //void saveCyclingPortal(String filename) throws IOException;

  /**
   * Method should load and replace this MiniCyclingPortal contents with the
   * serialised contents stored in the file given in the argument.
   * <p>
   * The state of this MiniCyclingPortal must be unchanged if any
   * exceptions are thrown.
   *
   * @param filename Location of the file to be loaded.
   * @throws IOException            If there is a problem experienced when trying
   *                                to load the store contents from the file.
   * @throws ClassNotFoundException If required class files cannot be found when
   *                                loading.
   */
  //void loadCyclingPortal(String filename)
    //throws IOException, ClassNotFoundException;
}
