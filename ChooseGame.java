public class ChooseGame {
    public ChooseGame(int game_number) {
        while (true) {
            if (game_number == 1) {
                new Quoridor();
                break;
            }
        }
    }
}