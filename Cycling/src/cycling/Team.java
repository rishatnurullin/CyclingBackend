import java.io.Serializable;
import java.util.ArrayList;
public class Team implements Serializable{
    private static int ID = 1;
    private int teamID;
    private String name;
    private String description;

    public int getTeamId() {
        return teamID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Team(String name, String description, ArrayList<Team> teams) throws IllegalNameException,InvalidNameException {
        if (Team.checkTeamName(name, teams) == true) {
            throw new IllegalNameException();
        } else if (name == null || name.length() > 30 || name.contains(" ")) {
            throw new InvalidNameException();
        }
        this.name = name;
        this.description = description;
        this.teamID = ID;
        ID++;
    }
    public static boolean checkTeamName(String name, ArrayList<Team> teams) {
        boolean nameTaken = false;
        for (Team team : teams) {
            if (team.name.equals(name)) {
                nameTaken = true;
                break;
            }
        }
        return nameTaken;
    }
    public static boolean checkID (int teamId, ArrayList<Team> teams) {
        boolean found = false;
        for (Team team : teams) {
            if (team.teamID == teamId) { 
                found = true;
                break;
            }
        }
        return found;
    }
}
