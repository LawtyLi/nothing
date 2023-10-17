public class reTTTGameFactory implements GameFactory{
    private final GameManager gameManager;
    private final GameUIManager gameUIManager;

    public reTTTGameFactory(GameManager gameManager, GameUIManager gameUIManager) {
        this.gameManager = gameManager;
        this.gameUIManager = gameUIManager;
    }
    @Override
    public Game createGame() {
        gameUIManager.AnotherStart();
        return new Recursive_TTT(gameManager);
    }
}
