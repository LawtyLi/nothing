import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board { // the game board, which is a 2D array of Tile objects
    protected int size;
    protected Tile[][] tiles;
    public Board(int size) {
        this(size, true);
    }
    public Board(int size, boolean initSubBoards) {
        this.size = size;
        tiles = new Tile[2* size -1][4* size -1];
        for (int i = 0; i < 2* size -1; i++) {
            for (int j = 0; j < 4* size -1; j++) {
                tiles[i][j] = initSubBoards ? new Tile(i, j, new Board(size, false)) : new Tile(i, j);
            }
        }
        draw_board();
    }
    public Board(char x) {
        if(x == 'T'){
            TTT_boardsize_test();
        }else{
            Q_boardsize_test();
        }
        tiles = new Tile[2*size-1][4*size-1];
        for (int i = 0; i < 2*size-1; i++) {
            for (int j = 0; j < 4*size-1; j++) {
                tiles[i][j] = new Tile(i, j); // initialize each Tile object
            }
        }
        draw_board();
    }
    public Board(int size, char x) {
        char a = x;
        this.size = size;
        tiles = new Tile[2* size -1][4* size -1];
        for (int i = 0; i < 2* size -1; i++) {
            for (int j = 0; j < 4* size -1; j++) {
                tiles[i][j] = new Tile(i, j); // initialize each Tile object
            }
        }
        draw_board();
    }
    public static List<Integer> calculateXValues(int n) {
        List<Integer> xValues = new ArrayList<>();
        int start = 3;

        for (int i = 1; i < n; i++) {
            xValues.add(start);
            start += 4;
        }

        return xValues;
    }
    public static List<Integer> findPiecePosition(int n) {
        List<Integer> xValues = new ArrayList<>();
        int start = 1;

        for (int i = 0; i < n; i++) {
            xValues.add(start);
            start += 4;
        }

        return xValues;
    }
    public Tile[][] draw_boardForBigboard(Tile[][] tiles, int n){
        int i, j;
        Colour colour = new Colour();
        List<Integer> values = calculateXValues(n);
        for(i = 0; i < 2*n-1; i++){
            if(i % 2 == 0){
                for(j = 0; j < 4*n-1; j++){
                    if(!values.contains(j)){
                        setTile1(i, j ," ", colour, tiles);
                    }
                    else{
                        setTile1(i, j ,"|", colour, tiles);
                    }
                }
            }
            else{
                for(j = 0; j < 4*n-1; j++){
                    if(!values.contains(j)){
                        setTile1(i, j ,"_", colour, tiles);
                    }
                    else{
                        setTile1(i, j ,"+", colour, tiles);
                    }
                }
            }
        }
        return tiles;
    }
    public Tile[][] draw_board(){
        int i, j;
        Colour colour = new Colour("white");
        this.tiles = getTiles();
        List<Integer> values = calculateXValues(size);
        for(i = 0; i < 2*size-1; i++){
            if(i % 2 == 0){
                for(j = 0; j < 4*size-1; j++){
                    if(!values.contains(j)){
                        setTile(i, j ," ", colour);
                    }
                    else{
                        setTile(i, j ,"|", colour);
                    }
                }
            }
            else{
                for(j = 0; j < 4*size-1; j++){
                    if(!values.contains(j)){
                        setTile(i, j ,"_", colour);
                    }
                    else{
                        setTile(i, j ,"+", colour);
                    }
                }
            }
        }
        return tiles;
    }
    public void setTile(int x, int y, String piece, Colour colour) {
        tiles[x][y].getPiece().setSymbol(piece);
        tiles[x][y].setColour(colour);
    }

    public void setTile1(int x, int y, String piece, Colour colour, Tile[][] tiles) {
        tiles[x][y].getPiece().setSymbol(piece);
        tiles[x][y].setColour(colour);
    }
    public boolean test_input_boardsize_Q(Scanner scanner){ // test whether users input a valid number of game board
        String input = scanner.nextLine().trim();
        // Check for "quit" input
        if ("quit".equalsIgnoreCase(input)) {
            System.out.println("Exiting the game...");
            System.exit(0);
        }
        // Check if input is an integer
        try {
            int n = Integer.parseInt(input);
            if (n > 0) {
                if (n == 7 || n == 9 || n == 11) {
                    setSize(n);
                    System.out.println(" ");
                    this.size = n;
                    return true;
                } else if (n > 11) {
                    setSize(n);
                    System.out.println(" ");
                    System.out.println("The size is too large and not suitable for this game board. " +
                            "Pls input again.");
                    return false;
                } else if (n < 7) {
                    setSize(n);
                    System.out.println(" ");
                    System.out.println("The game board is too small? Pls input again.");
                    return false;
                } else {
                    System.out.println("No way!");
                    return false;
                }
            } else {
                System.out.println("Negative numbers and zero have no meaning here. Please input a positive integer.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Pls input a positive integer number!");
            return false;
        }
    }
    public void Q_boardsize_test(){
        boolean flag;
        do {        // whether users input a valid number of game board
            Scanner scanner = new Scanner(System.in);
            flag = test_input_boardsize_Q(scanner);
        } while (!flag);
    }
    public void TTT_boardsize_test(){
        boolean flag;
        do {        // whether users input a valid number of game board
            Scanner scanner = new Scanner(System.in);
            flag = test_input_boardsize_ttt(scanner);
        } while (!flag);
    }
    public boolean test_input_boardsize_ttt(Scanner scanner){ // test whether users input a valid number of game board
        String input = scanner.nextLine().trim();
        // Check for "quit" input
        if ("quit".equalsIgnoreCase(input)) {
            System.out.println("Exiting the game...");
            System.exit(0);
        }
        // Check if input is an integer
        try {
            int n = Integer.parseInt(input);
            if (n > 0) {
                if (n == 3 || n == 4 || n == 5 || n == 6) {
                    setSize(n);
                    System.out.println(" ");
                    System.out.println("Now we can start the game!");
                    this.size = n;
                    return true;
                } else if (n > 6) {
                    setSize(n);
                    System.out.println(" ");
                    System.out.println("We can display the board to you, but the size is too large and not suitable for the game board. " +
                            "Pls input again.");
                    return false;
                } else if (n == 1 || n == 2) {
                    setSize(n);
                    System.out.println(" ");
                    System.out.println("Don't you think the game board is too small? Pls input again.");
                    return false;
                } else {
                    System.out.println("No way!");
                    return false;
                }
            } else {
                System.out.println("Negative numbers and zero have no meaning here. Please input a positive integer.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Pls input a positive integer number! Like 3 or 4 is the best options.");
            return false;
        }
    }
    public void show_board1() {
        this.tiles = getTiles();
        for (int i = 0; i < 2* size -1; i++) {
            for (int j = 0; j < 4* size -1; j++) {
                String piece = tiles[i][j].getPiece().getSymbol();
                Colour colour = tiles[i][j].getColour();
                if (piece.equals("X") || piece.equals("O") || isBetween1and9(piece)) { // if the piece is a game piece, then print it in color

                    String colorCode = (colour != null) ? colour.getName() : "";
                    if (colorCode.equalsIgnoreCase("red")) {
                        colorCode = "\033[31m"; // Red   31
                    } else if (colorCode.equalsIgnoreCase("blue")) {
                        colorCode = "\u001B[34m"; // Blue  34
                    } else if (colorCode.equalsIgnoreCase("green")) {
                        colorCode = "\u001B[32m"; // Green  32
                    } else if (colorCode.equalsIgnoreCase("yellow")) {
                        colorCode = "\u001B[33m"; // Yellow  33
                    } else if (colorCode.equalsIgnoreCase("purple")) {
                        colorCode = "\u001B[35m"; // Purple  35
                    } else if (colorCode.equalsIgnoreCase("cyan")) {
                        colorCode = "\u001B[36m"; // cyan  36
                    } else if (colorCode.equalsIgnoreCase("white")) {
                        colorCode = "\u001B[37m"; // white  37
                    }
                    System.out.print(colorCode + piece + "\u001B[0m");
                } else {
                    System.out.print(piece);
                }
            }
            System.out.println();
        }
    }
    protected static boolean isBetween1and9(String piece) {
        try {
            int number = Integer.parseInt(piece);
            return number >= 1 && number <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void show_board(Tile[][] tiles) {
        for (int i = 0; i < 2 * size - 1; i++) {
            for (int j = 0; j < 4 * size - 1; j++) {
                String piece = tiles[i][j].getPiece().getSymbol();
                Colour colour = tiles[i][j].getColour();

                // Check if the piece matches either of the player's pieces
                    String colorCode = (colour != null) ? colour.getName() : "";
                    switch (colorCode.toLowerCase()) {
                        case "red":
                            colorCode = "\033[31m";
                            break;
                        case "blue":
                            colorCode = "\u001B[34m";
                            break;
                        case "green":
                            colorCode = "\u001B[32m";
                            break;
                        case "yellow":
                            colorCode = "\u001B[33m";
                            break;
                        case "purple":
                            colorCode = "\u001B[35m";
                            break;
                        case "cyan":
                            colorCode = "\u001B[36m";
                            break;
                        case "white":
                            colorCode = "\u001B[37m";
                            break;
                        default:
                            colorCode = "";
                    }
                    System.out.print(colorCode + piece + "\u001B[0m");
                }
            System.out.println();
            }

        }
    public void show_board_spe(Tile[][] tiles, int n) {
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < 4 * n; j++) {
                String piece = tiles[i][j].getPiece().getSymbol();
                Colour colour = tiles[i][j].getColour();

                // Check if the piece matches either of the player's pieces
                String colorCode = (colour != null) ? colour.getName() : "";
                switch (colorCode.toLowerCase()) {
                    case "red":
                        colorCode = "\033[31m";
                        break;
                    case "blue":
                        colorCode = "\u001B[34m";
                        break;
                    case "green":
                        colorCode = "\u001B[32m";
                        break;
                    case "yellow":
                        colorCode = "\u001B[33m";
                        break;
                    case "purple":
                        colorCode = "\u001B[35m";
                        break;
                    case "cyan":
                        colorCode = "\u001B[36m";
                        break;
                    case "white":
                        colorCode = "\u001B[37m";
                        break;
                    default:
                        colorCode = "";
                }
                System.out.print(colorCode + piece + "\u001B[0m");
            }
            System.out.println();
        }

    }
    public Tile[][] getTiles() {return tiles;}
    public void setTiles(Tile[][] tiles) {this.tiles = tiles;}
    public int getSize() {return size;}
    public void setSize(int size) {this.size = size;}
}
