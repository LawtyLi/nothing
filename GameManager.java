import java.util.Arrays;
import java.util.Scanner;

public class GameManager {
    private final TeamManager teamManager;
    private final PlayerManager playerManager;
    private GameFactory gameFactory;
    private final GameUIManager gameUIManager;

    public GameManager() {
        this.teamManager = new TeamManager();
        this.playerManager = new PlayerManager();
        this.gameUIManager = new GameUIManager(this, teamManager);
        this.gameFactory = new InteractiveGameFactory(this, gameUIManager);

    }
    public void startNewGame() {
        gameFactory.createGame();
    }
    public Player[] init_team(){         // Initialize the team
        // Select two players from two teams (by player name
        Team team1;
        Team team2;
        while (true) {
            // Select two players from two teams (by team name)
            team1 = teamManager.selectTeam("Select Team 1 (by team name or 'bot'): ");
            team2 = teamManager.selectTeam("Select Team 2 (by team name or 'bot'): ");

            if (team1.equals(team2) && team1.getPlayersLength() == 1) {
                System.out.println("You cannot choose a team with only one player to play against itself. Please choose another team.");
            } else {
                break;
            }
        }
        Player player1 = playerManager.selectPlayerFromTeam(team1, 1);
        Player player2 = playerManager.selectPlayerFromTeam(team2, 2);
        return new Player[]{player1, player2};
    }

    public void setGameFactory(GameFactory gameFactory) {this.gameFactory = gameFactory;}
    public TeamManager getTeamManager() {return teamManager;}
    public GameUIManager getGameUIManager() {return gameUIManager;}
    public PlayerManager getPlayerManager() {return playerManager;}
}
