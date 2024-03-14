import java.util.ArrayList;
public class Team {
    static int ID = 1;
    int teamID;
    String name;
    String description;

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
