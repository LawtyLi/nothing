public class OCGameFactory implements GameFactory{
    private final GameManager gameManager;
    private final GameUIManager gameUIManager;

    public OCGameFactory(GameManager gameManager, GameUIManager gameUIManager) {
        this.gameManager = gameManager;
        this.gameUIManager = gameUIManager;
    }
    @Override
    public Game createGame() {
        gameUIManager.AnotherStart();
        return new Order_and_Chaos(gameManager);
    }
}
