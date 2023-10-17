public class InteractiveGameFactory implements GameFactory { // This class is used to create a game instance.
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
                return new TTTGameFactory(gameManager, gameUIManager).createGame();  // Using the factory to create the game instance.
            case 2:
                return new OCGameFactory(gameManager, gameUIManager).createGame();  // Using the factory to create the game instance.
            case 3:
                return new reTTTGameFactory(gameManager, gameUIManager).createGame();  // Using the factory to create the game instance.
            case 4:
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
        System.out.println("* 1. TTT                                             *");
        System.out.println("* 2. OC                                              *");
        System.out.println("* 3. SuperTTT                                        *");
        System.out.println("* 4. Quoridor                                        *");
        System.out.println("******************************************************");
    }
}
