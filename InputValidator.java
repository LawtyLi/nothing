import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class InputValidator { //    validate the input
    public static int validateGameChoice() {
        Scanner scanner = new Scanner(System.in);
        int gameChoice;

        while (true) {
            System.out.println("Select a game: ");
            if (scanner.hasNextInt()) {
                gameChoice = scanner.nextInt();

                if (gameChoice == 1 || gameChoice == 2 || gameChoice == 3 || gameChoice == 4) {
                    return gameChoice;
                } else {
                    System.out.println("No corresponding game available at the moment.");
                }
            } else {
                String input = scanner.nextLine();

                if ("quit".equalsIgnoreCase(input)) {
                    System.out.println("Exiting the game...");
                    System.exit(0);
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }
        }
    }
    public static String enterUniqueTeamName(Scanner scanner, Set<String> usedTeamNames, int teamNumber, List<Team> teams) {
        String teamName;
        do {
            System.out.print("Enter the name of the team" + (teamNumber + 1 + teams.size()) + ": ");
            teamName = scanner.nextLine().trim();

            if ("bot".equalsIgnoreCase(teamName)) {
                System.out.println("You cannot use the robot's name.");
            } else if ("quit".equalsIgnoreCase(teamName)) {
                boolean validChoice = false;
                do {
                    System.out.print("Do you want to exit the game or set the team name to 'quit'? (Enter 'e' for exit/'c' for continue): ");
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
        } while (!isValidTeamName1(teamName) || usedTeamNames.contains(teamName) || !isValidTeamName2(teamName));
        usedTeamNames.add(teamName);
        return teamName;
    }
    public static boolean validateNumOfTeams(String input, List<Team> teams) {
        // Check if user wants to quit
        if ("quit".equalsIgnoreCase(input)) {
            System.out.println("Exiting the game...");
            System.exit(0);
        }
        // it is a positive integer
        if (!isPositiveInteger(input)) {
            return false;
        }
        int numOfNewTeams = Integer.parseInt(input);
        // check if the total number of teams (existing + new) is between 1 and 5
        if (teams.size() + numOfNewTeams > 5 || teams.size() + numOfNewTeams < 1) {
            System.out.println("The total number of teams (existing + new) must be between 1 and 5.");
            return false;
        }
        return true;
    }
    public static boolean isColorAlreadySelected(String colour, List<Team> teams) {   // check whether the color has been selected
        return teams.stream()
                .anyMatch(team -> team.getColour() != null && team.getColour().getName().equalsIgnoreCase(colour));
    }
    public static int ForBoardPosition(Board board, int n){
        Scanner scanner = new Scanner(System.in);
        int position;
        while(true){
            System.out.println("Enter a number to choose your board(1-"+n*n+"):");
            String input = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            try {
                position = Integer.parseInt(input);

                if(position >= 1 && position <= n*n){
                    // find the num corresponding to the position
                    int x = 2 * ((position - 1) / board.getSize());
                    int y = (4 * ((position - 1) % board.getSize())) + 1;
                    if(board.getTiles()[x][y].getPiece().equals("X") ||
                            board.getTiles()[x][y].getPiece().equals("O")){
                        System.out.println("This position has been taken. Please choose another one.");
                    }
                    else{
                        return position;
                    }

                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
    public static boolean isValidColor(String colour) {
        if(colour.equalsIgnoreCase("quit")){
            System.out.println("Exiting the game...");
            System.exit(0);
        }
        return colour.equals("red") || colour.equals("blue") || colour.equals("green") || colour.equals("yellow") || colour.equals("purple");
    }
    public static boolean isPositiveInteger(String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 1;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return false;
        }
    }
    public static String enterUniqueTeamNameForStart(Scanner scanner, Set<String> usedTeamNames, int teamNumber) {
        String teamName;
        do {
            System.out.print("Enter the name of the team" + (teamNumber + 1) + ": ");
            teamName = scanner.nextLine().trim();

            if ("bot".equalsIgnoreCase(teamName)) {
                System.out.println("You cannot use the robot's name.");
            } else if ("quit".equalsIgnoreCase(teamName)) {
                boolean validChoice = false;
                do {
                    System.out.print("Do you want to exit the game or set the team name to 'quit'? (Enter 'e' for exit/'c' for continue): ");
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
        } while (!isValidTeamName1(teamName) || usedTeamNames.contains(teamName) || !isValidTeamName2(teamName));
        usedTeamNames.add(teamName);
        return teamName;
    }
    public static boolean validateNumberInRange(String input, int minValue, int maxValue) {
        try {
            int number = Integer.parseInt(input);
            return number >= minValue && number <= maxValue;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean validateNumOfPlayers(String input) {
        return isPositiveInteger(input) && Integer.parseInt(input) <= 5;
    }
    public static int getIntInput(String message) {
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
    public static int getValidPlayerChoice(int maxChoice) {
        int choice;
        do {
            choice = InputValidator.getIntInput("Enter the number of the player you choose: ") - 1;

            if (choice >= 0 && choice < maxChoice) {
                return choice;
            } else {
                System.out.println("Invalid player selection. Please choose a valid player number.");
            }
        } while (true);
    }
    public static String selectColor(String piece_type) {
        Set<String> chosenColors = new HashSet<>();
        String[] availableColors = {"red", "blue", "green", "yellow", "purple"};
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the color for " + piece_type + " (red, blue, green, yellow, purple):");
            String color = scanner.nextLine().toLowerCase();
            // Check for the "quit" input
            if ("quit".equalsIgnoreCase(color)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            if (InputValidator.containsIgnoreCase(availableColors, color) && !chosenColors.contains(color)) {
                chosenColors.add(color);
                return color;
            } else {
                System.out.println("Invalid color or color already chosen. Please choose a different color.");
            }
        }
    }
    public static String selectColor(String piece_type, String otherPlayerColor) {
        Set<String> chosenColors = new HashSet<>();
        String[] availableColors = {"red", "blue", "green", "yellow", "purple"};
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the color for " + piece_type + " (red, blue, green, yellow, purple):");
            String color = scanner.nextLine().toLowerCase();
            if ("quit".equalsIgnoreCase(color)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            if (containsIgnoreCase(availableColors, color) && !color.equalsIgnoreCase(otherPlayerColor) && !chosenColors.contains(color)) {
                chosenColors.add(color);
                return color;
            } else {
                System.out.println("Invalid color or color already chosen. Please choose a different color.");
            }
        }
    }
    public static boolean containsIgnoreCase(String[] array, String value) {
        for (String item : array) {
            if (item.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
    public static char getYesOrNoInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                return 'y';
            } else if (input.equals("n")) {
                return 'n';
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n':");
            }
        }
    }
    public static String getPlayerPieceChoice(Scanner scanner) {
        while (true) {
            String pieceInput = scanner.nextLine().trim().toUpperCase();
            if ("quit".equalsIgnoreCase(pieceInput)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            if (pieceInput.length() == 1 && (pieceInput.charAt(0) == 'X' || pieceInput.charAt(0) == 'O')) {
                return pieceInput;
            } else {
                System.out.println("Invalid input. Please enter 'X' or 'O'.");
            }
        }
    }
    private static boolean isValidTeamName1(String teamName) {

        return !teamName.isEmpty(); // team name cannot be empty
    }
    private static boolean isValidTeamName2(String teamName) {

        return !teamName.trim().isEmpty(); // team name cannot be all spaces
    }
}
