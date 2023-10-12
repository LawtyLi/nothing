public class InteractiveGameFactory implements GameFactory {
    private final GameManager gameManager;
    private final GameUIManager gameUIManager;

    public InteractiveGameFactory(GameManager gameManager, GameUIManager gameUIManager) {
        this.gameManager = gameManager;
        this.gameUIManager = gameUIManager;
    }

    @Override
    public Game createGame() {
        displayWelcomeMessage();
        int gameChoice = InputValidator.validateGameChoice();
        switch (gameChoice) {
            case 1:
                return new QuoridorGameFactory(gameManager, gameUIManager).createGame();  // Using the factory to create the game instance.
            default:
                throw new IllegalArgumentException("Invalid game choice");
        }
    }
    private static void displayWelcomeMessage() {
        System.out.println("******************************************************");
        System.out.println("*               Welcome to our playroom              *");
        System.out.println("* Choose a game that you wanna play.                 *");
        System.out.println("* We have a list of games for you to choose from.    *");
        System.out.println("* 1. Quoridor                                        *");
        System.out.println("* 2. TBD                                             *");
        System.out.println("* 3. TBD                                             *");
        System.out.println("******************************************************");
    }
}
