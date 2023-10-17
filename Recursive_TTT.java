import java.rmi.server.ObjID;
import java.util.*;
import java.util.concurrent.TimeUnit;
// 1. 棋盘大小可变 小棋盘或者大棋盘  2. 可以反悔，从别的地方下


public class Recursive_TTT extends Tic_tac_toe implements WinRelated{
    private Bigboard bigboard;
    private int bigBoardSIZE;
    private Tic_tac_toe[][] board_TTT;
    private Order_and_Chaos[][] board_OC;
    private int smallBoardSize;


    public Recursive_TTT(GameManager gameManager) {
        super(gameManager);
    }
    @Override
    public void execute(){  // execute the game Super TTT
        piece_pos = new ArrayList<Integer>();
        this.board_empty = 0;
        int choice = askForChoice();
        if(choice == 1){
            choose_TTT();
        }
        else{
            choose_OC();
        }
        int currentPlayerIndex = 0;
        if(choice == 1){
            PlayTTT(currentPlayerIndex);
        }else{ // if the user choose OC
            selectColor(currentPlayerIndex);
            PlayOC(currentPlayerIndex);
        }
    }
    private void selectColor(int currentPlayerIndex){ // select the color for the players
        Scanner scanner = new Scanner(System.in);
        String colorX = null;
        String colorO = null;
        if (teamManager.getTeams().size() == 1) {
            System.out.println("There's only one team on the field. Please select the color for your piece.");
            colorX = InputValidator.selectColor("X");
            colorO = InputValidator.selectColor("O", colorX);
            setColorsForAllObjects(colorX, colorO);
        }else{
            System.out.println("Player1 "+getGameManager().getGameUIManager().getSelectedPlayers()[0].getName()+" Your Bigboard piece is \"X\", and your team color will be the same as that of \"X\".");
            System.out.println("Player2 "+getGameManager().getGameUIManager().getSelectedPlayers()[1].getName()+" Your Bigboard piece is \"O\", and your team color will be the same as that of \"O\".");
            setColorsForAllObjects(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getColour().getName(),
                    getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex+1]).getColour().getName());
            getGameManager().getGameUIManager().getSelectedPlayers()[0].setPiece(new Piece("X"));
            getGameManager().getGameUIManager().getSelectedPlayers()[1].setPiece(new Piece("O"));
        }
    }
    private void PlayOC(int currentPlayerIndex){  // play the game OC
        int board_position = 0;
        while(true){
            if(getGameManager().getGameUIManager().getSelectedPlayers()[0].getName().equals("bot") && currentPlayerIndex == 0){
                board_position = botChooseRandomTileFromBigBoard(bigboard);
                System.out.println();
                System.out.println("bot choose the bigboard position: " + board_position);

            } else if (getGameManager().getGameUIManager().getSelectedPlayers()[1].getName().equals("bot") && currentPlayerIndex == 1) {
                board_position = botChooseRandomTileFromBigBoard(bigboard);
                System.out.println();
                System.out.println("bot choose the bigboard position: " + board_position);
            } else{
                System.out.println("Player" + (currentPlayerIndex+1) + " " + getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " go:");
                board_position = InputValidator.ForBoardPosition(bigboard, bigBoardSIZE);  // get the valid position of the board
            }
            int x = 2 * ((board_position - 1) / bigboard.getSize());
            int y = (4 * ((board_position - 1) % bigboard.getSize())) + 1;
            int result = board_OC[x][y].execute_4_reOC(currentPlayerIndex);
            if(result == 1){
                bigboard.setTile(x, y, "X", getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[0]).getColour());
                if(bigboard.getRemainingEmptyTiles() == 1){   // only one tile left
                    win_8(currentPlayerIndex);
                }
                if (check_bigwin(bigboard.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[0])) {
                    bigboard.show_board1();
                    after_win(currentPlayerIndex);
                } else if (bigboard.getRemainingEmptyTiles() == 0) {
                    ifDraw();
                }
            }else if(result == 2){
                bigboard.setTile(x, y, "O", getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[1]).getColour());
                if(bigboard.getRemainingEmptyTiles() == 1){   // only one tile left
                    win_8(currentPlayerIndex);
                }
                if (check_bigwin(bigboard.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[1])) {
                    bigboard.show_board1();
                    after_win(currentPlayerIndex);
                } else if (bigboard.getRemainingEmptyTiles() == 0) {
                    ifDraw();
                }
            }
            if(board_OC[x][y].getRetry().equals("RETRY")){
                board_OC[x][y].setRetry("nRETRY");  // Reset the retry flag
                bigboard.show_board1();
                continue;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
            // if a tile is starting a new game, then change the color of the tile
            changeTileColor_OC();
            // if team's players > 1, ask if the user wanna change the team player
            change_team(currentPlayerIndex);
            bigboard.show_board1();
        }
    }
    private void PlayTTT(int currentPlayerIndex) { // play the game TTT
        int board_position = 0;
        while (true) {
            if(getGameManager().getGameUIManager().getSelectedPlayers()[0].getName().equals("bot") && currentPlayerIndex == 0){
                board_position = botChooseRandomTileFromBigBoard(bigboard);
                System.out.println("bot choose the bigboard position: " + board_position);

            } else if (getGameManager().getGameUIManager().getSelectedPlayers()[1].getName().equals("bot") && currentPlayerIndex == 1) {
                board_position = botChooseRandomTileFromBigBoard(bigboard);
                System.out.println("bot choose the bigboard position: " + board_position);
            } else{
                System.out.println("Player" + (currentPlayerIndex+1) + " " + getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " go:");
                board_position = InputValidator.ForBoardPosition(bigboard, bigBoardSIZE);  // get the valid position of the board
            }
            int x = 2 * ((board_position - 1) / bigboard.getSize());
            int y = (4 * ((board_position - 1) % bigboard.getSize())) + 1;
            if (board_TTT[x][y].execute_4_reTTT(currentPlayerIndex)) {
                if (board_TTT[x][y].tie == Tie.TIE) {
                    bigboard.setTile(x, y, "T", new Colour("cyan"));
                } else {
                    bigboard.setTile(x, y, getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getPiece().getSymbol(),
                            getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getColour());
                }
                if (bigboard.getRemainingEmptyTiles() == 1) {   // only one tile left
                    win_8(currentPlayerIndex);
                }
                if (check_bigwin(bigboard.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex])) {
                    bigboard.show_board1();
                    after_win(currentPlayerIndex);
                } else if (bigboard.getRemainingEmptyTiles() == 0) {
                    ifDraw();
                }
            }
            if (board_TTT[x][y].getRetry().equals("RETRY")) {
                board_TTT[x][y].setRetry("nRETRY");  // Reset the retry flag
                bigboard.show_board1();
                continue;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
            // if a tile is starting a new game, then change the color of the tile
            changeTileColor_TTT();
            // if team's players > 1, ask if the user want to change the team player
            change_team(currentPlayerIndex);
            bigboard.show_board1();
        }
    }
    public int botChooseRandomTileFromBigBoard(Bigboard bigboard) {
        List<Integer> tiles = bigboard.getRemainEmptyTiles();

        if (tiles == null || tiles.isEmpty()) {
            throw new IllegalStateException("No tiles left to choose from!");
        }

        Random random = new Random();
        return tiles.get(random.nextInt(tiles.size()));
    }
    public void setColorsForAllObjects(String color1, String color2) { // set the color for all the objects
        List<Integer> yValues = Board.findPiecePosition(bigBoardSIZE);
        for (int i = 0; i < bigBoardSIZE; i++) {
            for (int j : yValues) {
                if (board_OC[2 * i][j] != null) {  // Check if the object at this position is not null
                    board_OC[2 * i][j].setColor1(color1);
                    board_OC[2 * i][j].setColor2(color2);
                }
            }
        }
    }
    public Order_and_Chaos[][] generateBoard_OC(Bigboard bigboard) { // generate the 9 OC objects
        board_OC = new Order_and_Chaos[2* bigBoardSIZE -1][4* bigBoardSIZE -1];
        List<Integer> yValues = Board.findPiecePosition(bigBoardSIZE);
        for (int i = 0; i < bigBoardSIZE; i++) {
            for (int j : yValues) {
                int newStep = this.step;
                ArrayList<Integer> newPiece_pos = new ArrayList<>(this.piece_pos);
                int newBoard_empty = this.board_empty;
                GameManager game = new GameManager();
                board_OC[2 * i][j] = new Order_and_Chaos(game);
            }
        }
        return board_OC;
    }
    public Tic_tac_toe[][] generateBoard_TTT(Bigboard bigboard) { // generate the 9 TTT objects
        board_TTT = new Tic_tac_toe[2* bigBoardSIZE -1][4* bigBoardSIZE -1];
        List<Integer> yValues = Board.findPiecePosition(bigBoardSIZE);
        for (int i = 0; i < bigBoardSIZE; i++) {
            for (int j : yValues) {
                int newStep = this.step;
                ArrayList<Integer> newPiece_pos = new ArrayList<>(this.piece_pos);
                int newBoard_empty = this.board_empty;
                GameManager game = new GameManager();
                board_TTT[2 * i][j] = new Tic_tac_toe(game);
            }
        }
        return board_TTT;
    }
    public static int promptForBoardSize() { // ask the user to choose the board size
        Scanner scanner = new Scanner(System.in);
        int size = 0;

        while (true) {
            System.out.println("Enter the bigboard size (3-6):");
            String input = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }

            try {
                size = Integer.parseInt(input);
                if (size >= 3 && size <= 6) {
                    return size;
                } else {
                    System.out.println("Invalid input. Please enter a number between 3 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
    private void changeTileColor_TTT(){ // if a tile is starting a new game, then change the color of the tile
        int position = 0;
        List<Integer> yValues = Board.findPiecePosition(bigBoardSIZE);
        for (int i = 0; i < bigBoardSIZE; i++) {
            for (int j : yValues) {
                if(!board_TTT[2*i][j].getPiece_pos().isEmpty() &&
                        (!bigboard.getTiles()[2*i][j].getPiece().equals("X") &&
                                !bigboard.getTiles()[2*i][j].getPiece().equals("O"))){
                    Colour colour = new Colour("cyan");
                    position = (2*i / 2) * bigboard.getSize() + (j - 1) / 4 + 1;
                    if(position > 9){
                        bigboard.getTiles()[2*i][j].setColour(colour);
                        bigboard.getTiles()[2*i][j-1].setColour(colour);
                    }else{
                        bigboard.getTiles()[2*i][j].setColour(colour);
                    }
                }
            }
        }
    }
    private void changeTileColor_OC(){ // if a tile is starting a new game, then change the color of the tile
        int position = 0;
        List<Integer> yValues = Board.findPiecePosition(bigBoardSIZE);
        for (int i = 0; i < bigBoardSIZE; i++) {
            for (int j : yValues) {
                if(!board_OC[2*i][j].getPiece_pos().isEmpty() &&
                        (!bigboard.getTiles()[2*i][j].getPiece().equals("X") &&
                                !bigboard.getTiles()[2*i][j].getPiece().equals("O"))){
                    Colour colour = new Colour("cyan");
                    position = (2*i / 2) * bigboard.getSize() + (j - 1) / 4 + 1;
                    if(position > 9){
                        bigboard.getTiles()[2*i][j].setColour(colour);
                        bigboard.getTiles()[2*i][j-1].setColour(colour);
                    }else{
                        bigboard.getTiles()[2*i][j].setColour(colour);
                    }
                }
            }
        }
    }
    private void choose_TTT(){ // choose the piece for the players
        choosePiece();
        bigBoardSIZE = promptForBoardSize();
        smallBoardSize = askForBoardSize();
        this.bigboard = new Bigboard(bigBoardSIZE, smallBoardSize);
        generateBoard_TTT(bigboard);  //generate the 9 TTT objects
        if (bigboard.getRemainEmptyTiles() != null && !bigboard.getRemainEmptyTiles().isEmpty()) {
            bigboard.getRemainEmptyTiles().clear();
        }
        List<Integer> tiles = new ArrayList<>();
        for (int i = 1; i <= bigBoardSIZE * bigBoardSIZE; i++) {
            tiles.add(i);
        }
        bigboard.setRemainEmptyTiles(tiles);
    }
    private void choose_OC(){ // choose the piece for the players
        bigBoardSIZE = promptForBoardSize();
        this.bigboard = new Bigboard(bigBoardSIZE, 6);
        generateBoard_OC(bigboard);  //generate the 9 OC objects
        if (bigboard.getRemainEmptyTiles() != null && !bigboard.getRemainEmptyTiles().isEmpty()) {
            bigboard.getRemainEmptyTiles().clear();
        }
        List<Integer> tiles = new ArrayList<>();
        for (int i = 1; i <= bigBoardSIZE * bigBoardSIZE; i++) {
            tiles.add(i);
        }
        bigboard.setRemainEmptyTiles(tiles);
    }
    private void change_team(int currentPlayerIndex){ // if team's players > 1, ask if the user wanna change the team player
        if((teamManager.getTeams().size() == 1 && getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getPlayers().size() > 2) ||
                (teamManager.getTeams().size() > 1 && getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getPlayers().size() > 1)
        ){
            System.out.println("Player"+ currentPlayerIndex+ " " +getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName()+ " " +"Do you wanna change the team player? (y/n)");
            if(InputValidator.getYesOrNoInput() == 'y'){
                System.out.println("Please choose another player from team " + getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getName() + ":");
                getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex] = getGameManager().getPlayerManager().selectPlayerFromTeam(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]),
                        getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex], currentPlayerIndex);
            }
        }
    }
    private void ifDraw() { // if the game ends in a draw
        System.out.println();
        bigboard.show_board1();
        System.out.println("It's a draw!");
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
        System.out.println(" ");
        System.out.println("You can choose 1. play another round 2. return to the game selection 3. exit the game system ");
        Finishgame_test();
    }
    private void win_8(int currentPlayerIndex){ // if there is only one tile left
        int x = bigboard.getX();
        int y = bigboard.getY();
        Tile current_state = new Tile(bigboard.getTiles()[x][y]);
        bigboard.setTile(x, y, getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getPiece().getSymbol(),
                getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getColour());
        boolean win_1 = check_win(bigboard.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]);
        currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
        bigboard.setTile(x, y, getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getPiece().getSymbol(),
                getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getColour());
        boolean win_2 = check_win(bigboard.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]);
        bigboard.setTile(current_state.getX(), current_state.getY(), current_state.getPiece().getSymbol(), current_state.getColour());
        if(!win_1 && !win_2){
            System.out.println("The last move won't affect the outcome. The game ends in a draw.");
            ifDraw();
        }
    }
    public int askForBoardSize() { // ask the user to choose the board size
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the small board size (3-6):");
            String input = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(input)) {
                System.exit(0);
            }
            try {
                int size = Integer.parseInt(input);
                if (size >= 3 && size <= 6) {
                    return size;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Invalid input. Please enter a number between 3 and 6.");
        }
    }
    private int askForChoice() { // ask the user to choose the game
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your small board game choice :1. TTT 2. OC");
        while (true) {
            System.out.println("Enter your choice (1 or 2):");
            String input = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(input)) {
                System.exit(0);
            }

            if ("1".equals(input) || "2".equals(input)) {
                return Integer.parseInt(input);
            }

            System.out.println("Invalid input. Please enter 1 or 2.");
        }
    }
    public void choosePiece(){ // choose the piece for the players
        Player player1 = getGameManager().getGameUIManager().getSelectedPlayers()[0];
        Player player2 = getGameManager().getGameUIManager().getSelectedPlayers()[1];
        Scanner scanner = new Scanner(System.in);

        // If player1 is BOT, only ask player2 for their piece choice.
        String player2Piece;
        if (player1 == Player.BOT) {
            System.out.print(player2.getName() + ", choose your piece (X or O): ");
            player2Piece = InputValidator.getPlayerPieceChoice(scanner);
            String botPiece = (player2Piece.equalsIgnoreCase("X")) ? "O" : "X";
            player1.setPiece(new Piece(botPiece));
            player2.setPiece(new Piece(player2Piece));
        } else {
            System.out.print(player1.getName() + ", choose your piece (X or O): ");
            String player1Piece = InputValidator.getPlayerPieceChoice(scanner);
            player2Piece = (player1Piece.equalsIgnoreCase("X")) ? "O" : "X";
            System.out.println(player2.getName() + ", your piece is " + player2Piece + ".");
            player1.setPiece(new Piece(player1Piece));
            player2.setPiece(new Piece(player2Piece));
        }
    }
    public boolean check_bigwin(int board_size, Player player){
        String piece;
        piece = player.getPiece().getSymbol();
        Tile[][] tile = bigboard.getTiles();
        List<Integer> yValues = Board.findPiecePosition(board_size);
        // check the row
        for (int row = 0; row < board_size; row++) {
            int count = 0;
            for (int col : yValues) {
                if (tile[2*row][col].getPiece().getSymbol().equals(piece) && !piece.equals(" ")) {
                    count++;

                    if (count == board_size) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        // check the col
        for (int col : yValues) {
            int count = 0;
            for (int row = 0; row < board_size; row++) {
                if (tile[2*row][col].getPiece().getSymbol().equals(piece) && !piece.equals(" ")) {
                    count++;
                    if (count == board_size) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        // check the diagonal
        int count = 0;
        for (int i = 0; i < yValues.size(); i++) {
            int row = 2 * i;
            int col = yValues.get(i);

            if (tile[row][col].getPiece().getSymbol().equals(piece) && !piece.equals(" ")) {
                count++;
                if (count == board_size) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        for (int i = 0; i < yValues.size(); i++) {
            Collections.reverse(yValues);
            int row = 2 * i;         // 偶数行
            int col = yValues.get(i); // 从yValues列表中获取列值

            if (tile[row][col].getPiece().getSymbol().equals(piece) && !piece.equals(" ")) {
                count++;
                if (count == board_size) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
    @Override
    public void after_win(int currentPlayerIndex){
        try{
            if(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[0]).equals(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[1]))) {
                System.out.println("Congratulations! Player" + (currentPlayerIndex+1) +" "+ getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the Super TTT game!");
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

                System.out.println("Congratulations! Player" + (currentPlayerIndex + 1) + " " + getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the Super TTT game!");
                System.out.println("You and your team "+ getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getName() + " will get 1 point.");
                getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).addScore();
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
}
