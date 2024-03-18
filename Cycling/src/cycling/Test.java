import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        CyclingPortalImpl portal = new CyclingPortalImpl();
        
        try {
            int raceId = portal.createRace("Tour de Test", "A test race");
            int stageId = portal.addStageToRace(raceId, "Stage1", "First stage", 120.0, LocalDateTime.now(), StageType.FLAT);
            int stageId2 = portal.addStageToRace(raceId, "Stage2", "First stage", 120.0, LocalDateTime.now(), StageType.HIGH_MOUNTAIN);
            int teamId = portal.createTeam("firstTeam", null);
            int riderId = portal.createRider(teamId, "joe", 2005);
            int riderId2 = portal.createRider(teamId, "holly", 2005);
            int riderId3 = portal.createRider(teamId, "din", 2004);

            
            
            portal.addCategorizedClimbToStage(stageId, 60.9, CheckpointType.HC, 6.5, 15.1);
            portal.addIntermediateSprintToStage(stageId2, 110);
            portal.concludeStagePreparation(stageId);
            portal.concludeStagePreparation(stageId2);

    
            
            LocalTime[] localTimes = new LocalTime[3];

        // Initialize elements of the array
            localTimes[0] = LocalTime.of(9, 0, 0, 0);   // 09:30:00
            localTimes[1] = LocalTime.of(11, 0, 0, 0);
            localTimes[2] = LocalTime.of(15, 0,0, 0); // 12

            LocalTime[] localTimes2 = new LocalTime[3];

        // Initialize elements of the array
            localTimes2[0] = LocalTime.of(9, 0, 0,0);   // 09:30:00
            localTimes2[1] = LocalTime.of(10, 1, 0, 0);
            localTimes2[2] = LocalTime.of(11, 0,0,5000000); // 12:15:30

            LocalTime [] localTimes3 = new LocalTime[3];

            localTimes3[0] = LocalTime.of(9, 0, 0,0);   // 09:30:00
            localTimes3[1] = LocalTime.of(9, 30, 0, 0);
            localTimes3[2] = LocalTime.of(16, 0,1,0); // 12:15:30


            LocalTime[] localTimesStage21 = new LocalTime[3];

        // Initialize elements of the array
            localTimesStage21[0] = LocalTime.of(9, 0, 0, 0);   // 09:30:00
            localTimesStage21[1] = LocalTime.of(9, 20,0, 0);
            localTimesStage21[2] = LocalTime.of(9, 30,0, 0); // 12

            LocalTime[] localTimesStage22 = new LocalTime[3];

        // Initialize elements of the array
            localTimesStage22[0] = LocalTime.of(9, 0, 0,0);   // 09:30:00
            localTimesStage22[1] = LocalTime.of(10, 0, 0, 0);
            localTimesStage22[2] = LocalTime.of(12, 0, 0, 0);

            LocalTime [] localTimesStage23 = new LocalTime[3];

            localTimesStage23[0] = LocalTime.of(9, 0, 0,0);   // 09:30:00
            localTimesStage23[1] = LocalTime.of(9, 28, 0, 0);
            localTimesStage23[2] = LocalTime.of(9, 29, 0, 0);
         // 12:15:30

            // for the first stage
            portal.registerRiderResultsInStage(stageId, riderId, localTimes);
            portal.registerRiderResultsInStage(stageId, riderId2, localTimes2);
            portal.registerRiderResultsInStage(stageId, riderId3, localTimes3);
            // 
            
            // for the second stage
            
            portal.registerRiderResultsInStage(stageId2, riderId, localTimesStage21);
            portal.registerRiderResultsInStage(stageId2, riderId2, localTimesStage22);
            portal.registerRiderResultsInStage(stageId2, riderId3, localTimesStage23);

            
            System.out.println("Results for first rider "  + Arrays.toString(portal.getRiderResultsInStage(stageId, riderId)));
            System.out.println("Results for second rider "  + Arrays.toString(portal.getRiderResultsInStage(stageId, riderId2)));
            System.out.println("Results for third rider "  + Arrays.toString(portal.getRiderResultsInStage(stageId, riderId3)));
            System.out.println("Adjusted elapsed time for the first rider " + portal.getRiderAdjustedElapsedTimeInStage(stageId, riderId));
            System.out.println("Adjusted elapsed time for the second rider " + portal.getRiderAdjustedElapsedTimeInStage(stageId, riderId2));
            System.out.println("Adjusted elapsed time for the third rider " + portal.getRiderAdjustedElapsedTimeInStage(stageId, riderId3));
            System.out.println("The rank in stage by elapsed time is " + Arrays.toString(portal.getRidersRankInStage(stageId)));

            System.out.println("The adjusted elapsed times in stage are " + Arrays.toString(portal.getRankedAdjustedElapsedTimesInStage(stageId)));
            System.out.println("The points in stage. They are the same as the rankOfRidersInStage" + Arrays.toString(portal.getRidersPointsInStage(stageId)));
            System.out.println("The mountain points for the riders are " + Arrays.toString(portal.getRidersMountainPointsInStage(stageId)));

            //NOW FOR STAGE 2
            System.out.println("\n----------------------------------------------------------------------------------------------------\n");
            System.out.println("Results for first rider on the 2nd stage"  + Arrays.toString(portal.getRiderResultsInStage(stageId2, riderId)));
            System.out.println("Results for second rider on the 2nd stage"  + Arrays.toString(portal.getRiderResultsInStage(stageId2, riderId2)));
            System.out.println("Results for third rider on the 2nd stage "  + Arrays.toString(portal.getRiderResultsInStage(stageId2, riderId3)));
            System.out.println("Adjusted elapsed time for the first rider " + portal.getRiderAdjustedElapsedTimeInStage(stageId2, riderId));
            System.out.println("Adjusted elapsed time for the second rider " + portal.getRiderAdjustedElapsedTimeInStage(stageId2, riderId2));
            System.out.println("Adjusted elapsed time for the third rider " + portal.getRiderAdjustedElapsedTimeInStage(stageId2, riderId3));
            System.out.println("The rank in stage by elapsed time is " + Arrays.toString(portal.getRidersRankInStage(stageId2)));
            System.out.println("The adjusted elapsed times in stage are " + Arrays.toString(portal.getRankedAdjustedElapsedTimesInStage(stageId2)));
            System.out.println("The points in stage. They are the same as the rankOfRidersInStage" + Arrays.toString(portal.getRidersPointsInStage(stageId2)));

            System.out.println("\n----------------------------------------\n");
            System.out.println("The General Ranknig Classification is " + Arrays.toString(portal.getRidersGeneralClassificationRank(raceId)));
            System.out.println("\n----------------------------------------\n");
            System.out.println("This is the total elapsed times in race" + Arrays.toString(portal.getGeneralClassificationTimesInRace(raceId)));

            portal.saveCyclingPortal("Serialized.txt");
            portal.loadCyclingPortal("Serialized.txt");
        } catch (IDNotRecognisedException e) {
            // TODO: handle exception
            System.out.println("IDNotRecognisedException");
        } catch (IllegalArgumentException e){
            System.out.println("IllegalArgumentException");
        }  catch (InvalidStageStateException e) {
            System.out.println("InvalidStageStateException");
        } catch(InvalidCheckpointTimesException e) {
            System.out.println("InvalidCheckpointTimesException");
        } catch(DuplicatedResultException e) {
            System.out.println("DuplicatedResultException");
        } catch(InvalidNameException e) {
            System.out.println("InvalidNameException");
        } catch(IllegalNameException e) {
            System.out.println("IllegalNameException");
        }catch(InvalidLengthException e) {
            System.out.println("InvalidLengthException");
        } catch (Exception e) {
            System.out.println("Exception");
        }
        
            
            /** 
            // Test valid result registration
            try {
                portal.registerRiderResultsInStage(stageId, riderId, LocalTime.of(8, 0), LocalTime.of(9, 30), LocalTime.of(10, 45));
                System.out.println("Result registered successfully.");
            } catch (Exception e) {
                System.out.println("Failed to register result: " + e.getMessage());
            }

            // Test invalid stage ID
            try {
                portal.registerRiderResultsInStage(999, riderId, LocalTime.of(8, 0), LocalTime.of(9, 30), LocalTime.of(10, 45));
                System.out.println("Result registered successfully.");
            } catch (Exception e) {
                System.out.println("Failed to register result: " + e.getMessage());
            }

            // Test invalid rider ID
            try {
                portal.registerRiderResultsInStage(stageId, 999, LocalTime.of(8, 0), LocalTime.of(9, 30), LocalTime.of(10, 45));
                System.out.println("Result registered successfully.");
            } catch (Exception e) {
                System.out.println("Failed to register result: " + e.getMessage());
            }

            // Test duplicated result
            try {
                portal.registerRiderResultsInStage(stageId, riderId, LocalTime.of(8, 0), LocalTime.of(9, 30), LocalTime.of(10, 45));
                System.out.println("Result registered successfully.");
            } catch (Exception e) {
                System.out.println("Failed to register result: " + e.getMessage());
            }

            // Test invalid checkpoint times length
            try {
                portal.registerRiderResultsInStage(stageId, riderId, LocalTime.of(8, 0), LocalTime.of(9, 30));
                System.out.println("Result registered successfully.");
            } catch (Exception e) {
                System.out.println("Failed to register result: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("An error occurred during the test setup: " + e.getMessage());
        }
        */
    

}}
