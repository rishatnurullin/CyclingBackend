import java.util.ArrayList;

public class Rider{
    static int ID =1;
    String name;
    int yearOfBirth;
    int riderID;
    int teamID;

    public Rider(String name, int yearOfBirth, int teamID, ArrayList<Team> teams) throws IDNotRecognisedException {
        if (Team.checkID(teamID, teams) == false) {
            throw new IDNotRecognisedException(); 
        } else if (name == null || yearOfBirth < 1900) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        riderID = ID;
        this.teamID = teamID;
        ID++;

        // retrieve the team with the given teamID
        // add the rider to the team
    }
    public static void addToTeam() {

    }
    public void removeRider(int riderID) {

    }
    public static boolean checkID (int riderId, ArrayList<Rider>riders ) {
        boolean found = false;
        for (Rider rider : riders) {
            if (rider.riderID == riderId) { 
                found = true;
                break;
            }
        }
        return found;
    }
    public static void main(String[] args) {
        
    }
}