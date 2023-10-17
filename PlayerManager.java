import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerManager { // the player manager
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
    protected Player selectPlayerFromTeam(Team team, Player currentPlayer, int j) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a player"+ (j+1) +" from Team " + team.getName() + ": ");
        List<Player> players = team.getPlayers();
        List<Player> availablePlayers = new ArrayList<>();

        for (Player player : players) {
            if (!player.equals(currentPlayer)) { // Exclude the current player
                availablePlayers.add(player);
            }
        }

        for (int i = 0; i < availablePlayers.size(); i++) {
            System.out.println((i + 1) + ". " + availablePlayers.get(i).getName());
        }

        int playerChoice;
        do {
            playerChoice = getIntInput("Enter the number of the player you choose: ");

            if (playerChoice >= 1 && playerChoice <= availablePlayers.size()) {
                Player selectedPlayer = availablePlayers.get(playerChoice - 1); // get the player from the available players list

                if (selectedPlayer.isChosen()) {
                    System.out.println("Player " + selectedPlayer.getName() + " has already been selected. Please choose another player.");
                } else {
                    selectedPlayer.setChosen(true);
                    return selectedPlayer;
                }
            } else {
                System.out.println("Invalid player selection. Please choose a valid player number.");
            }
        } while (true);
    }
    protected int getIntInput(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(message);
            String inputStr = scanner.nextLine().trim().toLowerCase();

            if ("quit".equalsIgnoreCase(inputStr)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            try {
                int input = Integer.parseInt(inputStr);
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
