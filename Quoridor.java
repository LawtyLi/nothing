import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Quoridor extends Game implements WinRelated{ // the game class
    private List<Wall> placedWalls;
    public Quoridor(GameManager gameManager){
        super(gameManager);
    }
    @Override
    protected void execute() {  // the main logic of the game
        int currentPlayerIndex = 0;
        this.placedWalls = new ArrayList<>();
        System.out.println("Now input your board size of this round(7 or 9 or 11) ");
        board = new Board('Q'); // create a new board
        choose_piece(); // choose the piece
        board.show_board(board.getTiles());
        System.out.println();
        while(true){
            if(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getNumOfWalls() == 0){ // if the player has no walls left
                System.out.println("Player" + (currentPlayerIndex+1) + " " + getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() +
                        " move :");
            }else{
                System.out.println("Player" + (currentPlayerIndex+1) + " " + getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() +
                        " move or place a wall :");
            }
            validMoveOrWall(currentPlayerIndex);  // execute the move or place a wall
            if(is_win() == 1){
                after_win(0);
                break;
            }else if(is_win() == 2){
                after_win(1);
                break;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
        }
    }
    private int is_win() {
        if (getGameManager().getGameUIManager().getSelectedPlayers()[0].getX() == 2* board.getSize()-2) {
            return 1;
        } else if (getGameManager().getGameUIManager().getSelectedPlayers()[1].getX() == 0) {
            return 2;
        }
        return 0;
    }
    public void choose_piece() {
        Scanner scanner = new Scanner(System.in);
        String firstPlayerPiece = null;

        for (int i = 0; i < 2; i++) {
            while (true) {
                System.out.print("Player " + (i + 1) + ", please choose a letter as your piece (or type 'quit' to exit): ");
                String input = scanner.nextLine().trim();
                if ("quit".equalsIgnoreCase(input)) {
                    System.out.println("Exiting the game...");
                    System.exit(0);  // Terminate the program
                }
                if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                    // 如果是第二个玩家，检查选择的棋子是否与第一个玩家相同
                    if (i == 1 && input.equalsIgnoreCase(firstPlayerPiece)) {
                        System.out.println("This piece is already chosen by Player 1. Please choose a different letter.");
                        continue;
                    }
                    getGameManager().getGameUIManager().getSelectedPlayers()[i].setPiece(new Piece(input.toUpperCase()));
                    // 如果是第一个玩家，记录其选择的棋子以供后续比较
                    if (i == 0) {
                        firstPlayerPiece = input;
                    }
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a single letter.");
                }
            }
        }
        setPieceToInitialPosition(getGameManager().getGameUIManager().getSelectedPlayers()[0], 0, 2* board.getSize()-1);
        setPieceToInitialPosition(getGameManager().getGameUIManager().getSelectedPlayers()[1], 2* board.getSize()-2, 2* board.getSize()-1);
        getGameManager().getGameUIManager().getSelectedPlayers()[0].setX(0);
        getGameManager().getGameUIManager().getSelectedPlayers()[0].setY(2* board.getSize()-1);
        getGameManager().getGameUIManager().getSelectedPlayers()[1].setX(2* board.getSize()-2);
        getGameManager().getGameUIManager().getSelectedPlayers()[1].setY(2* board.getSize()-1);
    }

    public void setPieceToInitialPosition(Player player, int x, int y) {
        Colour colour = new Colour(teamManager.getPlayerTeam(player).getColour().getName());
        String pieceSymbol = player.getPiece().getSymbol();
        board.setTile(x, y, pieceSymbol, colour);
    }
    public void validMoveOrWall(int currentPlayerIndex) { // execute the move or place a wall
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter 'm' to move or 'p' to place a wall (or 'quit' to exit): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                System.out.println("Exiting game...");
                System.exit(0);
            } else if (input.equals("m")) {
                // 调用移动玩家的逻辑
                Tile chosenTile = move(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]);
                if(chosenTile == null) { // If user wants to return
                    continue;  // Restart the loop to let the user choose again
                }
                break;
            } else if (input.equals("p")) {

                if (getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getNumOfWalls() == 0) {
                    System.out.println("You have no walls left. Please make a valid choice.");
                } else {
                    // 调用放置墙的逻辑
                    String check = placeWall(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]);
                    if(check.equals("retry")) { // If user wants to return
                        continue;  // Restart the loop to let the user choose again
                    }
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter 'm' to move or 'p' to place a wall.");
            }
        }
    }
    public Tile move(Player currentPlayer) {
        List<Tile> validMoves = findValidMoves(currentPlayer);
        displayValidMovesOnBoard(validMoves);
        Tile chosenTile = getUserMoveChoice(validMoves);
        clearDisplayedMoves(validMoves);
        if(chosenTile == null) { // If user wants to return
            return null;
        }
        executeMoveChoice(currentPlayer, chosenTile);
        return chosenTile;
    }

    public String placeWall(Player currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        boolean isWallPlaced = false;
        String wallOrientation = null;
        String[] wallCoordinates = null;
        while (!isWallPlaced ) {
            // get wall orientation
            while (true) {
                wallOrientation = getWallOrientation(scanner);
                if(wallOrientation == "retry") return wallOrientation;
                if (wallOrientation == null) continue;
                // use wall orientation to get wall coordinates
                while (true) {
                    wallCoordinates = getWallCoordinates(scanner, wallOrientation, board);
                    if (wallCoordinates == null) break; // if user wants to return

                    if (placeAndShowWall(wallOrientation, wallCoordinates, board, currentPlayer, placedWalls)) {
                        isWallPlaced = true;
                        break;  // if wall is placed successfully, break out of the loop
                    } else {
                        // if placeAndShowWall return false，then continue getWallCoordinates
                        continue;
                    }
                }
                if (isWallPlaced) {
                    currentPlayer.setNumOfWalls(currentPlayer.getNumOfWalls() - 1);
                    System.out.println("Player" + (getGameManager().getGameUIManager().getSelectedPlayers()[0] == currentPlayer ? "1" : "2") + " " + currentPlayer.getName() + " has " + currentPlayer.getNumOfWalls() + " walls left.");
                    System.out.println();
                    break;
                }
            }
        }
        return wallOrientation;
    }
    private String[] getWallCoordinates(Scanner scanner, String wallOrientation, Board board) {
        while (true) {
            System.out.print(wallOrientation.equals("1") ? "Enter wall coordinates in the format “row column1 column2” like '4 5 6'(or 'r' to return): " : "Enter wall coordinates in the format “column row1 row2” like '4 5 6'(or 'r' to return): ");
            String input = scanner.nextLine().trim();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Exiting the game...");
                System.exit(0);  // Terminate the program
            }
            String[] wallCoordinates = input.split(" ");
            if (wallCoordinates.length == 1 && wallCoordinates[0].equalsIgnoreCase("r")) {
                System.out.println("Returning to choose wall orientation.");
                return null;
            } else if (!isValidCoordinates(wallCoordinates)) {
                System.out.println("Invalid input format. Please try again.");
            } else {
                if (processWallCoordinates(wallCoordinates, wallOrientation, board)) {
                    return wallCoordinates;
                }
            }
        }
    }
    private boolean placeAndShowWall(String wallOrientation, String[] wallCoordinates, Board board, Player currentPlayer, List<Wall> placedWalls) {
        int xStart, xEnd, yStart, yEnd, middleX, middleY;
        int[] coordinates = new int[4];
        if (isValidWallPlacement(wallCoordinates, wallOrientation, board, coordinates)) {
            xStart = coordinates[0];
            xEnd = coordinates[1];
            yStart = coordinates[2];
            yEnd = coordinates[3];
            middleX = (xEnd + xStart) / 2;
            middleY = (yEnd + yStart) / 2;
        } else {
            return false;
        }
        // Here, place the wall on the board.
        String colourName = teamManager.getPlayerTeam(currentPlayer).getColour().getName();
        Colour wallColour = new Colour(colourName);
        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
                if (!((wallOrientation.equals("1") && j == middleY) || (wallOrientation.equals("2") && i == middleX))) {
                    board.getTiles()[i][j].setColour(wallColour);
                }
            }
        }
        // store the wall in the list of placed walls
        Wall.Direction direction = (wallOrientation.equals("1")) ? Wall.Direction.HORIZONTAL : Wall.Direction.VERTICAL;
        Wall wall = new Wall(wallColour, direction, new Wall.Coordinate(xStart, yStart));
        placedWalls.add(wall);
        board.show_board(board.getTiles());
        return true;
    }

    private boolean canReach(Tile start, Tile end, Set<Tile> visited) {
        if (start.equals(end)) {
            return true;
        }
        visited.add(start);
        List<Tile> validMoves = findValidMovesFromTile(start);
        for (Tile adjacent : validMoves) {
            if (!visited.contains(adjacent) && canReach(adjacent, end, visited)) {
                return true;
            }
        }
        return false;
    }

    private List<Tile> findValidMovesFromTile(Tile tile) {
        // create a temporary player to find valid moves
        Player tempPlayer = new Player("", tile.getX(), tile.getY(), new Piece("P"));
        return findValidMoves(tempPlayer);
    }

    private boolean isValidCoordinates(String[] wallCoordinates) {
        if(wallCoordinates.length < 3) { // Ensure there are at least three elements
            return false;
        }
        try {
            int coord1 = Integer.parseInt(wallCoordinates[0]);
            int coord2 = Integer.parseInt(wallCoordinates[1]);
            int coord3 = Integer.parseInt(wallCoordinates[2]);

            // Check if the coordinates are within the valid range
            boolean validRange = (coord1 >= 1 && coord1 <= board.getSize()-1) && (coord2 >= 1 && coord2 <= board.getSize()) && (coord3 >= 1 && coord3 <= board.getSize());

            // Check if the difference between coord2 and coord3 is 1
            boolean validDifference = Math.abs(coord2 - coord3) == 1;

            return validRange && validDifference;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private List<Tile> findValidMoves(Player currentPlayer) {
        int x = currentPlayer.getX();
        int y = currentPlayer.getY();
        List<Tile> validMoves = new ArrayList<>();
        // up
        if (x - 2 >= 0) {
            // 1. no wall, no opponent piece
            if (board.getTiles()[x-1][y].getColour().getName().equals("white")
                    && !board.getTiles()[x-2][y].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))) {
                validMoves.add(board.getTiles()[x-2][y]);
            }
            // 2. no wall, but there is opponent piece, and there is no wall behind the opponent piece
            else if (x - 4 >= 0 && board.getTiles()[x-2][y].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && board.getTiles()[x-3][y].getColour().getName().equals("white")) {
                validMoves.add(board.getTiles()[x-4][y]);
            }
            // 3. no wall, but there is opponent piece, and there is wall behind the opponent piece
            else if (x - 4 >= 0 && board.getTiles()[x-2][y].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && !board.getTiles()[x-3][y].getColour().getName().equals("white")) {
                if (y - 4 >= 0 && board.getTiles()[x-2][y-2].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x-2][y-4]);
                if (y + 4 < board.getTiles()[0].length && board.getTiles()[x-2][y+2].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x-2][y+4]);
            }
        }
        // down
        if (x + 2 < board.getTiles().length) {
            if (board.getTiles()[x+1][y].getColour().getName().equals("white")
                    && !board.getTiles()[x+2][y].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))) {
                validMoves.add(board.getTiles()[x+2][y]);
            }
            else if (x + 4 < board.getTiles().length && board.getTiles()[x+2][y].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && board.getTiles()[x+3][y].getColour().getName().equals("white")) {
                validMoves.add(board.getTiles()[x+4][y]);
            }
            else if (x + 4 < board.getTiles().length && board.getTiles()[x+2][y].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && !board.getTiles()[x+3][y].getColour().getName().equals("white")) {
                if (y - 4 >= 0 && board.getTiles()[x+2][y-2].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x+2][y-4]);
                if (y + 4 < board.getTiles()[0].length && board.getTiles()[x+2][y+2].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x+2][y+4]);
            }
        }
        // left
        if (y - 4 >= 0) {
            if (board.getTiles()[x][y-2].getColour().getName().equals("white")
                    && !board.getTiles()[x][y-4].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))) {
                validMoves.add(board.getTiles()[x][y-4]);
            }
            else if (y - 8 >= 0 && board.getTiles()[x][y-4].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && board.getTiles()[x][y-6].getColour().getName().equals("white")) {
                validMoves.add(board.getTiles()[x][y-8]);
            }
            else if (y - 8 >= 0 && board.getTiles()[x][y-4].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && !board.getTiles()[x][y-6].getColour().getName().equals("white")) {
                if (x - 2 >= 0 && board.getTiles()[x-1][y-4].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x-2][y-4]);
                if (x + 2 < board.getTiles().length && board.getTiles()[x+1][y-4].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x+2][y-4]);
            }
        }
        // right
        if (y + 4 < board.getTiles()[0].length) {
            if (board.getTiles()[x][y+2].getColour().getName().equals("white")
                    && !board.getTiles()[x][y+4].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))) {
                validMoves.add(board.getTiles()[x][y+4]);
            }
            else if (y + 8 < board.getTiles()[0].length && board.getTiles()[x][y+4].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && board.getTiles()[x][y+6].getColour().getName().equals("white")) {
                validMoves.add(board.getTiles()[x][y+8]);
            }
            else if (y + 8 < board.getTiles()[0].length && board.getTiles()[x][y+4].getPiece().getSymbol().equals(opponentPieceSymbol(currentPlayer))
                    && !board.getTiles()[x][y+6].getColour().getName().equals("white")) {
                if (x - 2 >= 0 && board.getTiles()[x-1][y+4].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x-2][y+4]);
                if (x + 2 < board.getTiles().length && board.getTiles()[x+1][y+4].getColour().getName().equals("white")) validMoves.add(board.getTiles()[x+2][y+4]);
            }
        }

        return validMoves.stream()
                .sorted(Comparator.comparingInt(Tile::getX).thenComparing(Tile::getY))
                .collect(Collectors.toList());
    }
    private boolean isValidWallPlacement(String[] wallCoordinates, String wallOrientation, Board board, int[] coordinates) {
        int coord1 = Integer.parseInt(wallCoordinates[0]);
        int minCoord = Integer.parseInt(wallCoordinates[1]);
        int maxCoord = Integer.parseInt(wallCoordinates[2]);

        int coord2 = Math.min(minCoord, maxCoord);
        int coord3 = Math.max(minCoord, maxCoord);

        int xStart, xEnd, yStart, yEnd;

        if (wallOrientation.equals("1")) {  // Horizontal wall
            xStart = xEnd = 2 * coord1 - 1;
            yStart = 4 * coord2 - 4;
            yEnd = 4 * coord3 - 2;
        } else {  // Vertical wall
            xStart = 2 * coord2 - 2;
            xEnd = 2 * coord3 - 2;
            yStart = yEnd = 4 * coord1 - 1;
        }

        Tile startTile = board.getTiles()[xStart][yStart];
        Tile endTile = board.getTiles()[xEnd][yEnd];

        if (!startTile.getColour().getName().equals("white") || !endTile.getColour().getName().equals("white")) {
            System.out.println("A wall already exists at the given coordinates. Please choose another location.");
            return false;
        }
        int middleX = (xEnd + xStart) / 2;
        int middleY = (yEnd + yStart) / 2;
        // Temporarily place the wall for checking.
        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
                if (!((wallOrientation.equals("1") && j == middleY) || (wallOrientation.equals("2") && i == middleX))) {
                    board.getTiles()[i][j].setColour(new Colour("cyan")); // Use a temporary colour for validation
                }
            }
        }
        Tile outside, inside, outside1, inside1;
        if (wallOrientation.equals("1")) {
            outside = board.getTiles()[xStart-1][yStart+1];
            outside1 = board.getTiles()[xStart-1][yEnd-1];   // 上面的两个格子
            inside = board.getTiles()[xStart+1][yStart+1];
            inside1 = board.getTiles()[xStart+1][yEnd-1];   // 下面的两个格子

        } else {
            outside = board.getTiles()[xStart][yStart-2];
            outside1 = board.getTiles()[xEnd][yStart-2];   // 左边的两个格子
            inside = board.getTiles()[xStart][yStart+2];
            inside1 = board.getTiles()[xEnd][yStart+2];   // 右边的两个格子
        }
        if (!canReach(outside, inside, new HashSet<>()) && !canReach(outside1, inside1, new HashSet<>())) {
            System.out.println("This wall forms an enclosed space. Choose another location.");
            for (int i = xStart; i <= xEnd; i++) {
                for (int j = yStart; j <= yEnd; j++) {
                    if (board.getTiles()[i][j].getColour().getName().equals("cyan")) {
                        board.getTiles()[i][j].setColour(new Colour("white"));  // Restore to default
                    }
                }
            }
            return false;
        }
        // Update the returned coordinates.
        coordinates[0] = xStart;
        coordinates[1] = xEnd;
        coordinates[2] = yStart;
        coordinates[3] = yEnd;
        return true;
    }
    private String getWallOrientation(Scanner scanner){
        System.out.print("Enter 1 for horizontal wall or 2 for vertical wall or 'r' to return to choose move or place the wall: ");
        String wallOrientation = scanner.nextLine();
        if ("quit".equalsIgnoreCase(wallOrientation)) {
            System.out.println("Exiting the game...");
            System.exit(0);  // Terminate the program
        }
        if ("r".equalsIgnoreCase(wallOrientation)) {
            return "retry"; // Return null to signify that the user wants to return to the previous menu.
        }
        if (wallOrientation.equals("1")) {
            // Print the board suitable for horizontal wall placement
            generateHorizontalWallView();
            return wallOrientation;
        } else if (wallOrientation.equals("2")) {
            // Print the board suitable for vertical wall placement
            generateVerticalWallView();
            return wallOrientation;
        } else {
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
        return null;
    }
    private boolean processWallCoordinates(String[] wallCoordinates, String wallOrientation, Board board) {
        // Convert input into coordinates
        int coord1 = Integer.parseInt(wallCoordinates[0]);
        int minCoord = Integer.parseInt(wallCoordinates[1]);
        int maxCoord = Integer.parseInt(wallCoordinates[2]);

        int coord2 = Math.min(minCoord, maxCoord);
        int coord3 = Math.max(minCoord, maxCoord);
        int xStart, xEnd, yStart, yEnd;

        if (wallOrientation.equals("1")) {  // Horizontal wall
            xStart = xEnd = 2 * coord1 - 1;
            yStart = 4 * coord2 - 4;
            yEnd = 4 * coord3 - 2;
        } else {  // Vertical wall
            xStart = 2 * coord2 - 2;
            xEnd = 2 * coord3 - 2;
            yStart = yEnd = 4 * coord1 - 1;
        }
        Tile startTile = board.getTiles()[xStart][yStart];
        Tile endTile = board.getTiles()[xEnd][yEnd];

        if (!startTile.getColour().getName().equals("white") || !endTile.getColour().getName().equals("white")) {
            System.out.println("A wall already exists at the given coordinates. Please choose another location.");
            return false;
        }
        return true;
    }
    private void displayValidMovesOnBoard(List<Tile> sortedValidMoves) {
        int counter = 1;
        for (Tile tile : sortedValidMoves) {
            board.setTile(tile.getX(), tile.getY(), String.valueOf(counter++), new Colour("white"));
        }
        board.show_board(board.getTiles());
    }
    private static Tile getUserMoveChoice(List<Tile> sortedValidMoves) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Please choose a number corresponding to a valid move: ");
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Exiting the game...");
                System.exit(0);  // Terminate the program
            }
            if ("r".equalsIgnoreCase(input)) {
                return null;  // Return null if the player chooses to return
            }
            if (!input.matches("\\d+")) { // if input is not a number
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            int chosenMove = Integer.parseInt(input);
            if (chosenMove > 0 && chosenMove <= sortedValidMoves.size()) {
                return sortedValidMoves.get(chosenMove - 1);
            } else {
                System.out.println("Invalid move choice. Please choose a number from the displayed options.");
            }
        }
    }
    private void executeMoveChoice(Player currentPlayer, Tile chosenTile) {
        int x = currentPlayer.getX();
        int y = currentPlayer.getY();

        currentPlayer.setX(chosenTile.getX());
        currentPlayer.setY(chosenTile.getY());

        board.setTile(chosenTile.getX(), chosenTile.getY(), currentPlayer.getPiece().getSymbol(), teamManager.getPlayerTeam(currentPlayer).getColour());
        board.setTile(x, y, " ", new Colour("white"));
        board.show_board(board.getTiles());
    }
    private void clearDisplayedMoves(List<Tile> displayedMoves) {
        for (Tile tile : displayedMoves) {
            tile.getPiece().setSymbol(" ");
        }
    }
    private String opponentPieceSymbol(Player currentPlayer) {
        return (currentPlayer == getGameManager().getGameUIManager().getSelectedPlayers()[0]) ? getGameManager().getGameUIManager().getSelectedPlayers()[1].getPiece().getSymbol() : getGameManager().getGameUIManager().getSelectedPlayers()[0].getPiece().getSymbol();
    }
    public void copyBoard(Tile[][] source, Tile[][] destination) {
        int sourceRows = source.length;  // 2*n-1
        int sourceCols = source[0].length;  // 4*n-1

        int destinationRows = destination.length;  // 2*n
        int destinationCols = destination[0].length;  // 4*n

        for (int i = 0; i < sourceRows; i++) {
            for (int j = 0; j < sourceCols; j++) {
                destination[destinationRows - sourceRows + i][destinationCols - sourceCols + j] = source[i][j];
            }
        }
    }
    @Override
    public void after_win(int currentPlayerIndex){
        try{
            if(teamManager.getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[0]).equals(teamManager.getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[1]))) {
                System.out.println("Congratulations! Player" + (currentPlayerIndex+1) +" "+ getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the game!");
                System.out.println("No scoring will be done when members of the same team play against each other.");
                System.out.println(" ");
                for (Team team : teamManager.getTeams()) {
                    System.out.println("Team " + team.getName() + " Score: " + team.getScore());
                    System.out.println("Players in Team " + team.getName() + ":");
                    for (Player player : team.getPlayers()) {
                        System.out.println(player.getName() + " Score: " + player.getScore());
                    }
                }
                for (Player player : getGameManager().getGameUIManager().getSelectedPlayers()) {
                    if (player != null) {
                        player.setChosen(false);
                    }
                }
            }
            else{
                System.out.println("Congratulations! Player" + (currentPlayerIndex+1) +" "+ getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the game!");
                System.out.println("You and your team "+ teamManager.getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getName() + " will get 1 point.");
                teamManager.getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).addScore();
                getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].addScore();
                System.out.println(" ");
                for (Team team : teamManager.getTeams()) {
                    System.out.println("Team " + team.getName() + " Score: " + team.getScore());
                    System.out.println("Players in Team " + team.getName() + ":");
                    for (Player player : team.getPlayers()) {
                        System.out.println(player.getName() + " Score: " + player.getScore());
                    }
                    System.out.println(" ");
                }
                for (Player player : getGameManager().getGameUIManager().getSelectedPlayers()) {
                    if (player != null) {
                        player.setChosen(false);
                    }
                }
            }
            TimeUnit.MILLISECONDS.sleep(900);
            System.out.println("You can choose 1. play another round 2. return to the game selection 3. exit the game system ");
            Finishgame_test();
        }catch(Exception e) {
            System.out.println("Met Error");
        }
    }
    @Override
    public String getRetry() {
        return retry.name();
    }
    @Override
    public void setRetry(String status) {
        if ("RETRY".equals(status)) {
            this.retry = Retry.RETRY;
        } else if ("nRETRY".equals(status)) {
            this.retry = Retry.nRETRY;
        } else {
            throw new IllegalArgumentException("Invalid retry status");
        }
    }
    @Override
    public void Finishgame_test(){
        boolean flag;
        do {        // whether users input a valid number of game choosing
            Scanner scanner = new Scanner(System.in);
            flag = test_input_finishgame(scanner);
        } while (!flag);
    }
    public void generateHorizontalWallView() {
        Tile[][] tile = new Tile[2*board.getSize()][4*board.getSize()];
        for (int i = 0; i < 2*board.getSize(); i++) {
            for (int j = 0; j < 4*board.getSize(); j++) {
                tile[i][j] = new Tile(i, j); // initialize each Tile object
            }
        }
        copyBoard(board.getTiles(), tile);
        int count = 1;
        for(int i=2;i<tile[0].length;i+=4){
            tile[0][i].getPiece().setSymbol(String.valueOf(count++));
        }
        count = 1;
        for(int i=2;i<tile.length;i+=2){
            tile[i][0].getPiece().setSymbol(String.valueOf(count++));
        }
        board.show_board_spe(tile, board.getSize());
    }
    public void generateVerticalWallView() {
        Tile[][] tile = new Tile[2*board.getSize()][4*board.getSize()];
        for (int i = 0; i < 2*board.getSize(); i++) {
            for (int j = 0; j < 4*board.getSize(); j++) {
                tile[i][j] = new Tile(i, j); // initialize each Tile object
            }
        }
        copyBoard(board.getTiles(), tile);
        int count1 = 1;
        for(int i=4;i<tile[0].length;i+=4){
            tile[0][i].getPiece().setSymbol(String.valueOf(count1++));
        }
        count1 = 1;
        for(int i=1;i<tile.length;i+=2){
            tile[i][0].getPiece().setSymbol(String.valueOf(count1++));
        }
        board.show_board_spe(tile, board.getSize());
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
                getGameManager().getGameUIManager().AnotherStart();
                execute();
                return true;
            } else if (n == 2) {
                getGameManager().startNewGame();
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
}
