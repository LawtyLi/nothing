import java.util.List;

public class PlayerManager {
    public Player selectPlayerFromTeam(Team team, int j) {
        System.out.println("Select a player"+ j +" from Team " + team.getName() + ": ");
        List<Player> players = team.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).getName());
        }
        Player selectedPlayer;
        do {
            int playerChoice = InputValidator.getValidPlayerChoice(players.size());
            selectedPlayer = players.get(playerChoice);   // get the player

            if (selectedPlayer.isChosen()) {
                System.out.println("Player " + selectedPlayer.getName() + " has already been selected. Please choose another player.");
            } else {
                selectedPlayer.setChosen(true);
                return selectedPlayer;
            }
        } while (true);
    }
}
