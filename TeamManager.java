import java.util.*;

public class TeamManager {
    private final Set<String> usedTeamNames = new HashSet<>();
    private final Set<String> usedPlayerName = new HashSet<>();
    private final List<Team> teams = new ArrayList<>();

    public void createTeams() {
        Scanner scanner = new Scanner(System.in);
        int numberOfTeams = getNumberOfTeams(scanner);

        for (int i = 0; i < numberOfTeams; i++) {
            String teamName = teams.size() == 0 ?
                    InputValidator.enterUniqueTeamName(scanner, usedTeamNames, i, teams) :
                    InputValidator.enterUniqueTeamNameForStart(scanner, usedTeamNames, i);

            Team team = new Team(teamName);

            team.setColour(enterTeamColor(scanner, teams));

            teams.add(team);
            int numberOfPlayers = getNumberOfPlayersForTeam(scanner, numberOfTeams, teamName);
            addPlayersToTeam(scanner, numberOfPlayers, team);
        }
    }
    public Team selectTeam(String prompt) {
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
    public Team getPlayerTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }
    public List<Team> getTeams() {
        return teams;
    }

    public Set<String> getUsedTeamNames() {
        return usedTeamNames;
    }

    public Set<String> getUsedPlayerName() {
        return usedPlayerName;
    }
// ... any other team related methods ...
}