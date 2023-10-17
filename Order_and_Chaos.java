import java.util.*;
import java.util.concurrent.TimeUnit;
/* Similar to Tic_tac_toe.java, it includes the flow of the Order and Chaos game, including initialization,
team creation, player selection, gameplay, game termination, and the option to replay.  */
public class Order_and_Chaos extends Game implements WinRelated{
    private static String type;
    private String color1;
    private String color2;
    private enum Retry{RETRY, nRETRY} // enum for retry
    private Retry retry = Retry.nRETRY;
    protected  ArrayList<Integer> piece_pos = new ArrayList<Integer>();
    private int step;
    private int board_empty;
    public Order_and_Chaos(GameManager gameManager){
        super(gameManager);
    }
    public Order_and_Chaos(int step, Board board, ArrayList<Integer> piece_pos, int board_empty) {
        this.step = step;
        this.board = board;
        this.piece_pos = piece_pos;
        this.board_empty = board_empty;
    }
    public void execute(){  // execute the game
        board_empty = 0;
        piece_pos = new ArrayList<Integer>();
        int currentPlayerIndex = 0;
        board = new Board(6,'x');
        board.setSize(6);
        System.out.println("Now the game is starting...");
        board.show_board1();
        String color1 = InputValidator.selectColor("X");
        String color2 = InputValidator.selectColor("O", color1);
        while (true){
            if(board_empty < 6 * 6){
                if(getGameManager().getGameUIManager().getSelectedPlayers()[0].getName().equals("bot") && currentPlayerIndex == 0){
                    List<Integer> availablePositions = getAvailablePositions();
                    Random random = new Random();
                    step = availablePositions.get(random.nextInt(availablePositions.size()));
                    // Randomly assign "X" or "O" to type
                    type = random.nextInt(2) == 0 ? "X" : "O";
                } else if (getGameManager().getGameUIManager().getSelectedPlayers()[1].getName().equals("bot") && currentPlayerIndex == 1) {
                    List<Integer> availablePositions = getAvailablePositions();
                    Random random = new Random();
                    step = availablePositions.get(random.nextInt(availablePositions.size()));
                    // Randomly assign "X" or "O" to type
                    type = random.nextInt(2) == 0 ? "X" : "O";
                }
                    else{
                    System.out.println("Player" + (currentPlayerIndex+1) + " go. ");
                    System.out.println("Pls input the type of piece first('x' or 'o'):");
                    validPiecePosition();
                }
                // place the piece and check if someone win the game
                palce_piece(step, type, color1, color2);
                getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].setPiece_pos(step);
                board_empty++;
                int win = check_win(board_empty);
                if(win == 1){
                    after_win(0);
                }else if(win == 2){
                    after_win(1);
                }else {
                    System.out.println(" ");
                }
                currentPlayerIndex = (currentPlayerIndex + 1) % getGameManager().getGameUIManager().getSelectedPlayers().length;
            }
        }
    }
    public int execute_4_reOC(int currentPlayerIndex){  // execute the game
        boolean flag;
        board_empty = 0;
        if(getGameManager().getGameUIManager().getSelectedPlayers()[0].getName().equals("bot") && currentPlayerIndex == 0){
            List<Integer> availablePositions = getAvailablePositions();
            Random random = new Random();
            // Randomly assign "X" or "O" to type
            type = random.nextInt(2) == 0 ? "X" : "O";
            System.out.println("bot choose the type of small board: " + type);
            step = availablePositions.get(random.nextInt(availablePositions.size()));
            System.out.println("bot choose the step of small board: " + step);
        } else if (getGameManager().getGameUIManager().getSelectedPlayers()[1].getName().equals("bot") && currentPlayerIndex == 1) {
            List<Integer> availablePositions = getAvailablePositions();
            Random random = new Random();
            // Randomly assign "X" or "O" to type
            type = random.nextInt(2) == 0 ? "X" : "O";
            System.out.println("bot choose the type of small board: " + type);
            step = availablePositions.get(random.nextInt(availablePositions.size()));
            System.out.println("bot choose the step of small board: " + step);
        }
        else{
            board.show_board1();
            System.out.println("Player" + (currentPlayerIndex+1) + " go. ");
            System.out.println("Pls input the type of piece first('x' or 'o' or 'r' to re-enter):");
            validPiecePosition();
        }
        if (retry == Retry.RETRY) {
            return 0; // Skip the rest of the loop and start from the beginning
            }
        // place the piece and check if someone win the game
        palce_piece(step, type, color1, color2);
        getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].setPiece_pos(step);
        board_empty++;
        int win = check_win(board_empty);
        if(win == 1){
            return 1;
        }else if(win == 2){
            return 2;
        }else {
            System.out.println(" ");
        }

        return 0;
    }
    private boolean test_type(String typep) {
        if ("quit".equalsIgnoreCase(typep)) {
            System.out.println("Exiting the game...");
            System.exit(0);
        }
        if ("r".equalsIgnoreCase(typep)) {
            retry = Retry.RETRY;
            return false; // exit the function
        }
        if (typep.equalsIgnoreCase("x") || typep.equalsIgnoreCase("o")) {
            type = typep.toUpperCase();
            return false;
        } else {
            System.out.println("Pls input x or o. Both uppercase and lowercase are accepted.");
            return true;
        }
    }

    private void palce_piece(int step, String type, String color1, String color2){
        Colour colour = new Colour();
        String piece = " ";
        if(type.equals("x") || type.equals("X")){
            piece = "X";
            colour.setName(color1);
            piece_pos.add(step);
        }
        else if(type.equals("o") || type.equals("O")){
            piece = "O";
            colour.setName(color2);
            piece_pos.add(step);
        }
            board.setTile(2 * ((step - 1) / 6), (4 * ((step - 1) % 6)) + 1, piece, colour);
            board.show_board1();
    }
    protected Team getPlayerTeam(Player player) {
        for (Team team : getGameManager().getTeamManager().getTeams()) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }
    public void after_win(int currentPlayerIndex){
        try{
            if(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[0]).equals(getPlayerTeam(getGameManager().getGameUIManager().getSelectedPlayers()[1]))) {
                System.out.println("Congratulations! Player" + (currentPlayerIndex) +" "+ getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the game!");
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
                System.out.println("Congratulations! Player" + (currentPlayerIndex) +" "+ getGameManager().getGameUIManager().getSelectedPlayers()[currentPlayerIndex].getName() + " win the game!");
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
    protected List<Integer> getAvailablePositions() {
        List<Integer> availablePositions = new ArrayList<>();
        for (int i = 1; i <= board.getSize() * board.getSize(); i++) {
            if (!piece_pos.contains(i)) {
                availablePositions.add(i);
            }
        }
        return availablePositions;
    }
    public int check_win(int board_empty){
        int p = 2;
        int count = 0;
        String piece = "X";
        Tile[][] tile = board.getTiles();
        List<Integer> yValues;
        yValues = Board.findPiecePosition(6);
   if(board_empty == 36){
       return 2;
   }
   else{
       do{
           // check the row
           for (int row = 0; row < 6; row++) {
               for (int col : yValues) {
                   if (tile[2*row][col].getPiece().getSymbol().equals(piece) && !piece.equals(" ")) {
                       count++;
                       if (count == 5) {
                           return 1;
                       }
                   } else {
                       count = 0;
                   }
               }
           }
           // check the col
           for (int col : yValues) {
               for (int row = 0; row < 6; row++) {
                   if (tile[2*row][col].getPiece().getSymbol().equals(piece) && !piece.equals(" ")) {
                       count++;
                       if (count == 5) {
                           return 1;
                       }
                   } else {
                       count = 0;
                   }
               }
           }
           // check the diagonal
           for (int i = 0; i < yValues.size(); i++) {
               int row = 2 * i;
               int col = yValues.get(i);

               if (tile[row][col].getPiece().getSymbol().equals(piece) && !piece.equals(" ")) {
                   count++;
                   if (count == 5) {
                       return 1;
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
                   if (count == 5) {
                       return 1;
                   }
               } else {
                   count = 0;
               }
           }
           piece = "O";
           p--;
       }while(p != 0);
   }
        return 0;
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

    public void Finishgame_test(){
        boolean flag;
        do {        // whether users input a valid number of game choosing
            Scanner scanner = new Scanner(System.in);
            flag = test_input_finishgame(scanner);
        } while (!flag);
    }
    protected void validPiecePosition(){
        boolean flag;
        do {        // whether users input a valid number of game board
            Scanner scanner = new Scanner(System.in);
            String type = scanner.next();
            flag = test_type(type);
        } while (flag);
        if (retry == Retry.RETRY) {
            return; // Skip the rest of the loop and start from the beginning
        }
        System.out.println("Now input the position(1-36) or 'r' to re-enter:");
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Enter the position on the game board: ");

            String input = scanner.nextLine();
            // Check for the "quit" input
            if ("quit".equalsIgnoreCase(input)) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            if ("r".equalsIgnoreCase(input)) {
                retry = Retry.RETRY;
                return; // exit the function
            }
            // Try to parse the input as an integer
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

    public ArrayList<Integer> getPiece_pos() {return piece_pos;}
    public void setColor2(String color2) {this.color2 = color2;}
    public void setColor1(String color1) {this.color1 = color1;}
}
