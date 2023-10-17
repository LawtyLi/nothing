import java.util.*;

public abstract class Game implements Retry{ // the game itself
    protected Board board;
    protected enum Retry{RETRY, nRETRY} // enum for retry
    protected Retry retry = Retry.nRETRY;
    private  GameManager gameManager;
    protected TeamManager teamManager;
    protected PlayerManager playerManager;
    protected abstract void execute();

    public Game(GameManager gameManager){
        this.gameManager = gameManager;
        this.teamManager = gameManager.getTeamManager();
        this.playerManager = gameManager.getPlayerManager();
        execute();
    }
    public Game(){}
    protected GameManager getGameManager() {
        return gameManager;
    }

}
