import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
/* Inherits from the Game class and includes the initialization of the Tic Tac Toe game, the gameplay flow,
and functions to determine variables during the game, such as methods to check for victory.  */
public class Tic_tac_toe extends Game implements WinRelated{
    protected enum Tie {TIE, nTIE} // enum for tie
    protected Tie tie = Tie.nTIE;
    protected int step;
    protected  ArrayList<Integer> piece_pos = new ArrayList<Integer>();
    protected int board_empty;   //recording how many postions have already placed pieces


    public Tic_tac_toe(GameManager gameManager){
        super(gameManager);
    }

    public Tic_tac_toe(int step, Board board, ArrayList<Integer> piece_pos, int board_empty) {
        super();
        this.step = step;
        this.board = board;
        this.piece_pos = piece_pos;
        this.board_empty = board_empty;
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
    protected List<Integer> getAvailablePositions() {
        List<Integer> availablePositions = new ArrayList<>();
        for (int i = 1; i <= board.getSize() * board.getSize(); i++) {
            if (!piece_pos.contains(i)) {
                availablePositions.add(i);
            }
        }
        return availablePositions;
    }
    public boolean execute_4_reTTT(int currentPlayerIndex){

        if(getGameManager().getGameUIManager().getSelectedPlayers()[0].getName().equals("bot") && currentPlayerIndex == 0){
            List<Integer> availablePositions = getAvailablePositions();
            Random random = new Random();
            step = availablePositions.get(random.nextInt(availablePositions.size()));
            System.out.println("bot choose the position of small board: " + step);
        } else if (getGameManager().getGameUIManager().getSelectedPlayers()[1].getName().equals("bot") && currentPlayerIndex == 1) {
            List<Integer> availablePositions = getAvailablePositions();
            Random random = new Random();
            step = availablePositions.get(random.nextInt(availablePositions.size()));
            System.out.println("bot choose the position of small board: " + step);
        } else{
            board.show_board1();
            System.out.println("Player" + (currentPlayerIndex+1) + " " + getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " go or 'r' to re-enter:");
            validPiecePosition();
        }
        if (retry == Retry.RETRY) {
            return false; // Skip the rest of the loop and start from the beginning
        }
        place_piece(step, getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex], board.getSize());
        getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].setPiece_pos(step);
        board_empty++;
        if(board_empty == board.getSize() * board.getSize()-1){
            if(check_win(board.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex])){
                return true;
            }else{
                if(!(eight_win_4_reTTT(board.getSize(), currentPlayerIndex))){
                    tie = Tie.TIE;
                    return true;
                }
                else{
                    return true;
                }
            }
        }
        if(check_win(board.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex])){
            System.out.println("Congratulations! Player" + (currentPlayerIndex+1) +" "+ getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the game!");
            System.out.println("Player " + (currentPlayerIndex+1) +"'s "+getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getPiece().getSymbol()+ " will occupy the tile.");
            return true;
        }
       return false;
    }
    public void execute(){
        board_empty = 0;
        piece_pos = new ArrayList<Integer>();
        int currentPlayerIndex = 0;
        choose_piece();
        System.out.println("Now input your board size of this round(3-6) ");
        board = new Board('T');
        board.show_board1();
        while(true){
            if(getGameManager().getGameUIManager().getSelectedPlayers()[0].getName().equals("bot") && currentPlayerIndex == 0){
                List<Integer> availablePositions = getAvailablePositions();
                Random random = new Random();
                step = availablePositions.get(random.nextInt(availablePositions.size()));
            } else if (getGameManager().getGameUIManager().getSelectedPlayers()[1].getName().equals("bot") && currentPlayerIndex == 1) {
                List<Integer> availablePositions = getAvailablePositions();
                Random random = new Random();
                step = availablePositions.get(random.nextInt(availablePositions.size()));
                System.out.println("step: " + step);
            } else{
                System.out.println("Player" + (currentPlayerIndex+1) + " " + getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " go:");
                validPiecePosition();
            }
            place_piece(step, getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex], board.getSize());
            getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].setPiece_pos(step);
            board_empty++;
            if(board_empty == board.getSize() * board.getSize()-1){
                eight_win(board.getSize(), currentPlayerIndex);
            }
            if(check_win(board.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex])){
                after_win(currentPlayerIndex);
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
        }
    }
    protected Team getPlayerTeam(Player player) {
        for (Team team : getGameManager().getTeamManager().getTeams()) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }
    public void choose_piece(){
        Scanner scanner = new Scanner(System.in);
        // If player1 is BOT, only ask player2 for their piece choice.
        String player2Piece;
        System.out.print(getGameManager().getGameUIManager().getSelectedPlayers()[0].getName() + ", choose your piece (X or O): ");
        String player1Piece = InputValidator.getPlayerPieceChoice(scanner);
        player2Piece = (player1Piece.equalsIgnoreCase("X")) ? "O" : "X";
        System.out.println(getGameManager().getGameUIManager().getSelectedPlayers()[1].getName() + ", your piece is " + player2Piece + ".");
        getGameManager().getGameUIManager().getSelectedPlayers()[0].setPiece(new Piece(player1Piece.toUpperCase()));
        getGameManager().getGameUIManager().getSelectedPlayers()[1].setPiece(new Piece(player2Piece.toUpperCase()));

    }
    public void place_piece(int step, Player player,int board_size){   // place the piece on the board
        String piece;
        piece = player.getPiece().getSymbol();
        piece_pos.add(step);
        // Determine the color ANSI escape sequence based on the player's team color
        String colorCode = " ";
        if (getPlayerTeam(player).getColour().getName().equalsIgnoreCase("red")) {
            colorCode = "red"; // Red   31
        } else if (getPlayerTeam(player).getColour().getName().equalsIgnoreCase("blue")) {
            colorCode = "blue"; // Blue  34
        } else if (getPlayerTeam(player).getColour().getName().equalsIgnoreCase("green")) {
            colorCode = "green"; // Green  32
        } else if (getPlayerTeam(player).getColour().getName().equalsIgnoreCase("yellow")) {
            colorCode = "yellow"; // Yellow  33
        } else if (getPlayerTeam(player).getColour().getName().equalsIgnoreCase("purple")) {
            colorCode = "purple"; // Purple  35
        } else if(getPlayerTeam(player).getColour().getName().equalsIgnoreCase("cyan")) {
            colorCode = "cyan"; // cyan  36
        }
        Colour color1 = new Colour(colorCode);
            board.setTile(2 * ((step - 1) / board_size), (4 * ((step - 1) % board_size)) + 1, piece, color1);
            board.show_board1();
            System.out.println();

    }
    public void after_win(int currentPlayerIndex){
        try{
            if(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[0]).equals(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[1]))) {

                System.out.println("Congratulations! Player" + (currentPlayerIndex+1) +" "+ getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the game!");
                System.out.println("No scoring will be done when members of the same team play against each other.");
                System.out.println(" ");
                for (Team team : getGameManager().getTeamManager().getTeams()) {
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
                System.out.println("You and your team "+ getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).getName() + " will get 1 point.");
                getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]).addScore();
                getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].addScore();
                System.out.println(" ");
                for (Team team : getGameManager().getTeamManager().getTeams()) {
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
    public ArrayList<Integer> getPiece_pos() {return piece_pos;}
    public boolean eight_win_4_reTTT(int board_size, int currentPlayerIndex){
        ArrayList<Integer> allPositions = new ArrayList<>();
        for (int i = 1; i <= board_size * board_size; i++) {
            allPositions.add(i);
        }
        int missingPosition = -1;
        for (int position : allPositions) {
            if (!piece_pos.contains(position)) {
                missingPosition = position;
                break;
            }
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
        System.out.println("currnetPlayerIndex: " + currentPlayerIndex);
        place_piece(missingPosition, getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex], board.getSize());
        boolean player1Wins = check_win(board.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]);
        return player1Wins;
    }
    private void eight_win(int board_size, int currentPlayerIndex){ //
            ArrayList<Integer> allPositions = new ArrayList<>();
            for (int i = 1; i <= board_size * board_size; i++) {
                allPositions.add(i);
            }
            int missingPosition = -1;
            for (int position : allPositions) {
                if (!piece_pos.contains(position)) {
                    missingPosition = position;
                    break;
                }
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
            place_piece(missingPosition, getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex], board.getSize());
            boolean player1Wins = check_win(board.getSize(), getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex]);
            if (player1Wins) {
                    after_win(currentPlayerIndex);
            } else {
                System.out.println("It's a draw!");
                for (Team team : getGameManager().getTeamManager().getTeams()) {
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
    }
    public boolean check_win(int board_size, Player player){
        String piece;
        piece = player.getPiece().getSymbol();
        Tile[][] tile = board.getTiles();
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
    public void Finishgame_test(){
        boolean flag;
        do {        // whether users input a valid number of game choosing
            Scanner scanner = new Scanner(System.in);
            flag = test_input_finishgame(scanner);
        } while (!flag);
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
    protected void validPiecePosition() {
        Scanner scanner = new Scanner(System.in);
        do { // place the piece on the board
            System.out.print("Enter the position on the game board: ");
            String input = scanner.nextLine();
            // Check for the "quit" input
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            // Check for the "r" input
            if ("r".equalsIgnoreCase(input)) {
                retry = Retry.RETRY;
                return; // exit the function
            }
            // check if the input can be converted to an integer
            try {
                step = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }
            if (step < 1 || step > board.getSize() * board.getSize() || piece_pos.contains(step)) {
                System.out.println("Invalid position. Try another one.");
            }
        } while (step < 1 || step > board.getSize() * board.getSize() || piece_pos.contains(step));
    }

}
