import java.util.*;

public abstract class Game {
    protected Board board;
    private final GameManager gameManager;
    protected TeamManager teamManager;
    protected PlayerManager playerManager;
    protected abstract void execute();
    protected abstract void Finishgame_test();

    public Game(GameManager gameManager){
        this.gameManager = gameManager;
        this.teamManager = gameManager.getTeamManager();
        this.playerManager = gameManager.getPlayerManager();
        execute();
    }
    protected GameManager getGameManager() {
        return gameManager;
    }

}
