import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private Tile[][] tiles;

    public Board(int n) {
        this.size = n;
        tiles = new Tile[2*n-1][4*n-1];
        for (int i = 0; i < 2*n-1; i++) {
            for (int j = 0; j < 4*n-1; j++) {
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
    public void show_board() {
        this.tiles = getTiles();
        for (int i = 0; i < 2*size-1; i++) {
            for (int j = 0; j < 4*size-1; j++) {
                String piece = tiles[i][j].getPiece().getSymbol();
                Colour colour = tiles[i][j].getColour();
                if (piece.equals("X") || piece.equals("O")) { // if the piece is a game piece, then print it in color

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
}
