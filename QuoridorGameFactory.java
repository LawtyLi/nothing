public class QuoridorGameFactory implements GameFactory {
    private final GameManager gameManager;
    private final GameUIManager gameUIManager;

    public QuoridorGameFactory(GameManager gameManager, GameUIManager gameUIManager) {
        this.gameManager = gameManager;
        this.gameUIManager = gameUIManager;
    }
    @Override
    public Game createGame() {
        gameUIManager.AnotherStart();
        return new Quoridor(gameManager);
    }
}
