import java.util.*;

public abstract class Game {
    protected Board board;
    protected Player[] selectedPlayers = new Player[2];
    protected List<Team> teams;
    private int game_id;
    private final Set<String> usedPlayerName = new HashSet<>();
    private final Set<String> usedTeamNames = new HashSet<>();

    public static void StartGame(){
        new ChooseGame(InputValidator.validateGameChoice());
    }
    public Game(){};
    protected abstract void execute();

    protected void createTeams() {
        Scanner scanner = new Scanner(System.in);
        int numberOfTeams = getNumberOfTeams(scanner);

        for (int i = 0; i < numberOfTeams; i++) {
            String teamName = teams.size() == 0 ?
                    InputValidator.enterUniqueTeamName(scanner, usedTeamNames, i, teams) :
                    InputValidator.enterUniqueTeamNameForStart(scanner, usedTeamNames, i);

            Team team = new Team(teamName);

            if (game_id == 1 || game_id == 3) {
                team.setColour(enterTeamColor(scanner, teams));
            }
            teams.add(team);
            int numberOfPlayers = getNumberOfPlayersForTeam(scanner, numberOfTeams, teamName);
            addPlayersToTeam(scanner, numberOfPlayers, team);
        }
    }
    protected void init_team(){         // Initialize the team
        // Select two players from two teams (by player name
        Team team1;
        Team team2;
        while (true) {
            // Select two players from two teams (by team name)
            team1 = selectTeam("Select Team 1 (by team name or 'bot'): ");
            team2 = selectTeam("Select Team 2 (by team name or 'bot'): ");

            if (team1.equals(team2) && team1.getPlayersLength() == 1) {
                System.out.println("You cannot choose a team with only one player to play against itself. Please choose another team.");
            } else {
                break;
            }
        }
        Player player1;
        Player player2;
        if ("bot".equalsIgnoreCase(team1.getName())) {
            player1 = Player.BOT;
            selectedPlayers[0] = player1;
            player2 = selectPlayerFromTeam(team2, 2);
            selectedPlayers[1] = player2;
        } else if ("bot".equalsIgnoreCase(team2.getName())) {
            player2 = Player.BOT;
            selectedPlayers[1] = player2;
            player1 = selectPlayerFromTeam(team1, 1);
            selectedPlayers[0] = player1;
        } else {
            player1 = selectPlayerFromTeam(team1, 1);
            selectedPlayers[0] = player1;
            player2 = selectPlayerFromTeam(team2, 2);
            selectedPlayers[1] = player2;
        }

        if(game_id == 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            // If player1 is BOT, only ask player2 for their piece choice.
//            String player2Piece;
//            if (player1 == Player.BOT) {
//                System.out.print(player2.getName() + ", choose your piece (X or O): ");
//                player2Piece = InputValidator.getPlayerPieceChoice(scanner);
//                String botPiece = (player2Piece.equalsIgnoreCase("X")) ? "O" : "X";
//                player1.setPiece(new Piece(botPiece));
//                player2.setPiece(new Piece(player2Piece));
//            } else {
//                System.out.print(player1.getName() + ", choose your piece (X or O): ");
//                String player1Piece = InputValidator.getPlayerPieceChoice(scanner);
//                player2Piece = (player1Piece.equalsIgnoreCase("X")) ? "O" : "X";
//                System.out.println(player2.getName() + ", your piece is " + player2Piece + ".");
//                player1.setPiece(new Piece(player1Piece));
//                player2.setPiece(new Piece(player2Piece));
//            }
        }
        else if(game_id == 2 || game_id == 3){
            System.out.println();
        }
    }
    protected Team selectTeam(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String teamName = scanner.nextLine().trim().toLowerCase();

            if ("quit".equalsIgnoreCase(teamName)) {
                boolean isQuitTeamExists = teams.stream().anyMatch(team -> "quit".equalsIgnoreCase(team.getName()));
                if (isQuitTeamExists) {
                    System.out.print("Do you want to exit the game or select the 'quit' team? (Enter 'e' for exit/'c' for continue): ");
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if ("e".equalsIgnoreCase(choice)) {
                        System.out.println("Exiting the game...");
                        System.exit(0);
                    } else if ("c".equalsIgnoreCase(choice)) {
                        return teams.stream().filter(team -> "quit".equalsIgnoreCase(team.getName())).findFirst().orElse(null);
                    } else {
                        System.out.println("Invalid choice. Please enter again.");
                    }
                } else {
                    System.out.println("Exiting the game...");
                    System.exit(0);
                }
            } else if ("bot".equalsIgnoreCase(teamName)) {
                if (teams.contains(Team.BOT_TEAM)) {
                    System.out.println("BOT team has already been selected. Please choose a different team.");
                } else {
                    teams.add(Team.BOT_TEAM);
                    return Team.BOT_TEAM;  // 直接返回BOT_TEAM常量
                }
            } else {
                for (Team team : teams) {
                    if (team.getName().equalsIgnoreCase(teamName)) {
                        return team;
                    }
                }
                System.out.println("Team not found. Please enter a valid team name.");
            }
        }
    }
    protected Player selectPlayerFromTeam(Team team, int j) {
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
    private int getNumberOfTeams(Scanner scanner) {
        String input;
        do {
            System.out.print("Enter the number of teams (1-5): ");
            input = scanner.nextLine();
        } while (!InputValidator.validateNumOfTeams(input, teams));
        return Integer.parseInt(input);
    }
    private static Colour enterTeamColor(Scanner scanner, List<Team> existingTeams) {
        Colour teamColour;
        do {
            System.out.print("Enter the team color (red, blue, green, yellow, or purple): ");
            String teamColor = scanner.nextLine().toLowerCase();
            if (InputValidator.isValidColor(teamColor) && !InputValidator.isColorAlreadySelected(teamColor, existingTeams)) {
                teamColour = new Colour(teamColor);
                return teamColour;
            }
        } while (true);
    }

    private int getNumberOfPlayersForTeam(Scanner scanner, int numberOfTeams, String teamName) {
        while (true) {
            System.out.printf("Enter the number of players for Team %s ", teamName);
            if (numberOfTeams == 1 && teams.size() == 1) {
                System.out.print("(2-5): ");
            } else {
                System.out.print("(1-5): ");
            }
            String input = scanner.nextLine().trim().toLowerCase();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            if (numberOfTeams == 1 && teams.size() == 1) {
                if (InputValidator.validateNumberInRange(input, 2, 5)) {
                    return Integer.parseInt(input);
                } else {
                    System.out.println("Invalid input. Please enter a number between 2 and 5.");
                }
            } else {
                if (InputValidator.validateNumOfPlayers(input)) {
                    return Integer.parseInt(input);
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                }
            }
        }
    }
    private void addPlayersToTeam(Scanner scanner, int numberOfPlayers, Team team) {
        for (int j = 0; j < numberOfPlayers; j++) {
            String playerName;
            do {
                System.out.printf("Enter the name of Player %d for Team %s: ", j + 1, team.getName());
                playerName = scanner.nextLine().trim();

                if ("quit".equalsIgnoreCase(playerName)) {
                    boolean validChoice = false;
                    do {
                        System.out.print("Do you want to exit the game or select the 'quit' team? (Enter 'e' for exit/'c' for continue): ");
                        String choice = scanner.nextLine().trim().toLowerCase();

                        if ("e".equalsIgnoreCase(choice)) {
                            System.out.println("Exiting the game...");
                            System.exit(0);
                        } else if ("c".equalsIgnoreCase(choice)) {
                            validChoice = true;  // Mark the choice as valid
                            break;
                        } else {
                            System.out.println("Invalid choice. Please enter 'e' for exit or 'c' for continue.");
                        }
                    } while (!validChoice);  // Keep looping until a valid choice is made
                }

                if ("bot".equalsIgnoreCase(playerName)) {
                    System.out.println("The name 'bot' is reserved for the computer player. Please choose a different name.");
                    continue;
                }

                if (!usedPlayerName.contains(playerName)) {
                    break;
                } else {
                    System.out.println("Player name already exists. Please enter a different name.");
                }
            } while (true);
            Player player = new Player(playerName);
            team.addPlayer(player);
            usedPlayerName.add(playerName);
        }
    }
    protected Team getPlayerTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }
    protected boolean test_input_finishgame(Scanner scanner) {
        String input = scanner.nextLine().trim();
        // Check for "quit" input
        if ("quit".equalsIgnoreCase(input)) {
            System.out.println("Exiting the game...");
            System.out.println("BTW, you can enter 3 to quit the game too");
            System.exit(0);
        }
        // If not "quit", check if it's an integer
        try {
            int n = Integer.parseInt(input);
            if (n == 1) {
                AnotherStart();
                return true;
            } else if (n == 2) {
                Game.StartGame();
                return true;
            } else if (n == 3) {
                System.exit(0);
                return true;
            } else {
                System.out.println("We seem do not have this option! Try again!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("We seem do not have this option! Try again!");
            return false;
        }
    }
    protected void AnotherStart(){
        Arrays.fill(selectedPlayers, null);
        if(this.teams != null && !this.teams.isEmpty()){
            teams.removeIf(team -> "bot".equalsIgnoreCase(team.getName()));
            System.out.println(" ");
            System.out.println("Now you can choose \n" +
                    "1.Keep the current team.\n" +
                    "2.Continue adding new teams.\n" +
                    "3.Reset the team. ");
            do{
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine().trim();

                // Check for "quit" input
                if ("quit".equalsIgnoreCase(input)) {
                    System.out.println("Exiting the game...");
                    System.exit(0);
                }

                try {
                    int n = Integer.parseInt(input);
                    if(n == 1){
                        System.out.println(" ");
                        System.out.println("You choose to keep the current team.");
                        System.out.println(" ");
                        int counter = 1;
                        for (Team team : teams) {
                            System.out.println(counter + ". " + team.getName());
                            counter++;
                        }
                        init_team();
                        execute();
                        break;
                    }
                    else if(n == 2){
                        System.out.println(" ");
                        System.out.println("You choose to continue adding new teams.");
                        System.out.println(" ");
                        createTeams();
                        init_team();
                        execute();
                        break;
                    }
                    else if(n == 3){
                        System.out.println(" ");
                        System.out.println("You choose to reset the team.");
                        System.out.println(" ");
                        this.teams = new ArrayList<>();
                        usedPlayerName.clear();
                        usedTeamNames.clear();
                        createTeams();
                        init_team();
                        execute();
                        break;
                    }
                    else{
                        System.out.println(" ");
                        System.out.println("We seem do not have this option! Try again!");
                        System.out.println(" ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" ");
                    System.out.println("We seem do not have this option! Try again!");
                    System.out.println(" ");
                }
            } while (true);
        }
        else {
            this.teams = new ArrayList<>();
            createTeams();
            init_team();
            execute();
        }
    }
    public void setGame_id(int game_id) {this.game_id = game_id;}
}
