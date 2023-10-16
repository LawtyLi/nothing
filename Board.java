import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private int size;
    private Tile[][] tiles;

    public Board() {
        TTT_boardsize_test();
        tiles = new Tile[2*size-1][4*size-1];
        for (int i = 0; i < 2*size-1; i++) {
            for (int j = 0; j < 4*size-1; j++) {
                tiles[i][j] = new Tile(i, j); // initialize each Tile object
            }
        }
        draw_board();
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
    public static List<Integer> calculateXValues(int n) {
        List<Integer> xValues = new ArrayList<>();
        int start = 3;

        for (int i = 1; i < n; i++) {
            xValues.add(start);
            start += 4;
        }

        return xValues;
    }
    public void setTile(int x, int y, String piece, Colour colour) {
        tiles[x][y].getPiece().setSymbol(piece);
        tiles[x][y].setColour(colour);
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
    public void TTT_boardsize_test(){
        boolean flag;
        do {        // whether users input a valid number of game board
            Scanner scanner = new Scanner(System.in);
            flag = test_input_boardsize_ttt(scanner);
        } while (!flag);
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
    public void show_board1(Tile[][] tiles, int n) {
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
