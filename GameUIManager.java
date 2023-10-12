import java.util.Arrays;
import java.util.Scanner;

public class GameUIManager {
    protected Player[] selectedPlayers = new Player[2];
    private final GameManager gameManager;
    private final TeamManager teamManager;

    public GameUIManager(GameManager gameManager, TeamManager teamManager) {
        this.gameManager = gameManager;
        this.teamManager = teamManager;
    }
    protected void AnotherStart(){
        Arrays.fill(selectedPlayers, null);
        if(teamManager.getTeams() != null && !teamManager.getTeams().isEmpty()){
            teamManager.getTeams().removeIf(team -> "bot".equalsIgnoreCase(team.getName()));
            System.out.println(" ");
            System.out.println("Now you can choose \n" +
                    "1.Keep the current team.\n" +
                    "2.Continue adding new teams.\n" +
                    "3.Reset the team. ");
            do{
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine().trim();

                // Check for "quit" input
                if ("quit".equalsIgnoreCase(input)) {
                    System.out.println("Exiting the game...");
                    System.exit(0);
                }

                try {
                    int n = Integer.parseInt(input);
                    if(n == 1){
                        System.out.println(" ");
                        System.out.println("You choose to keep the current team.");
                        System.out.println(" ");
                        int counter = 1;
                        for (Team team : teamManager.getTeams()) {
                            System.out.println(counter + ". " + team.getName());
                            counter++;
                        }
                        selectedPlayers = gameManager.init_team();
                        break;
                    }
                    else if(n == 2){
                        System.out.println(" ");
                        System.out.println("You choose to continue adding new teams.");
                        System.out.println(" ");
                        teamManager.createTeams();
                        selectedPlayers = gameManager.init_team();
                        break;
                    }
                    else if(n == 3){
                        System.out.println(" ");
                        System.out.println("You choose to reset the team.");
                        System.out.println(" ");
                        teamManager.getTeams().clear();
                        teamManager.getUsedPlayerName().clear();
                        teamManager.getUsedTeamNames().clear();
                        teamManager.createTeams();
                        selectedPlayers = gameManager.init_team();
                        break;
                    }
                    else{
                        System.out.println(" ");
                        System.out.println("We seem do not have this option! Try again!");
                        System.out.println(" ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" ");
                    System.out.println("We seem do not have this option! Try again!");
                    System.out.println(" ");
                }
            } while (true);
        }
        else {
            teamManager.getTeams().clear();
            teamManager.createTeams();
            selectedPlayers = gameManager.init_team();
        }
    }

    public Player[] getSelectedPlayers() {return selectedPlayers;}
}
