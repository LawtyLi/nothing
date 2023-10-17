import java.util.ArrayList;
import java.util.List;

public class Bigboard extends Board{
    private int x;
    private int y;
    private List<Integer> values;
    private int smallBoardSize;
    private List<Integer> remainEmptyTiles = new ArrayList<>();
    public Bigboard(int n, int smallBoardSize) {
        super('a');
        this.size = n;
        this.smallBoardSize = smallBoardSize;
        tiles = new Tile[2*n-1][4*n-1];
        values = findPiecePosition(n);
        for (int i = 0; i < 2*n-1; i++) {
            for (int j = 0; j < 4*n-1; j++) {
                if (i % 2 == 0 && values.contains(j)) {
                    Board subBoard = new Board(this.smallBoardSize);  // create a subBoard for each tile
                    tiles[i][j] = new Tile(i, j, subBoard);
                }
                else {
                    tiles[i][j] = new Tile(i, j);
                }
            }
        }
        draw_boardForBigboard(tiles, n);
        FillNuminBoard();  // initialize the board with numbers
    }
    private void FillNuminBoard() { // fill the board with numbers
        int num = 1;
        Colour colour = new Colour("white");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getPiece().equals(" ") && values.contains(j)) {
                    if (num >= 10) {
                        String numStr = Integer.toString(num);
                        tiles[i][j-1].getPiece().setSymbol(Character.toString(numStr.charAt(0))); // 十位
                        tiles[i][j-1].setColour(colour);
                        tiles[i][j].getPiece().setSymbol(Character.toString(numStr.charAt(1)));   // 个位
                    } else {
                        tiles[i][j].getPiece().setSymbol(Integer.toString(num));
                    }
                    tiles[i][j].setColour(colour);
                    num++;
                }
            }
        }
        show_board1();
    }
    public int getRemainingEmptyTiles() {
        int emptyCount = 0;
        int empty_X = -1;
        int empty_Y = -1;

        for (int i = 0; i < this.size; i++) {
            for (int j : values) {
                String piece = tiles[2*i][j].getPiece().getSymbol();
                int num = (i * this.size) + (j / 4) + 1;  // Calculate the corresponding number based on coordinates

                if (!piece.equals("X") && !piece.equals("O") && !piece.equals("T")) {
                    emptyCount++;
                    empty_X = 2 * i;
                    empty_Y = j;
                    if (!remainEmptyTiles.contains(num)) {
                        remainEmptyTiles.add(num);
                    }
                } else {
                    // If it's X, O, or T and the number is in the list, remove it
                    remainEmptyTiles.remove(Integer.valueOf(num));
                }
            }
        }

        if (emptyCount == 1) {
            this.x = empty_X;
            this.y = empty_Y;
        } else {
            this.x = -1;
            this.y = -1;
        }
        return emptyCount;
    }

    public void show_board1() { // print the board
        for (int i = 0; i < 2*size-1; i++) {
            for (int j = 0; j < 4*size-1; j++) {
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
    public int getX() {return x;}

    public void setRemainEmptyTiles(List<Integer> remainEmptyTiles) {this.remainEmptyTiles = remainEmptyTiles;}
    public int getY() {return y;}
    public void setSmallBoardSize(int smallBoardSize) {this.smallBoardSize = smallBoardSize;}
    public List<Integer> getRemainEmptyTiles() {return remainEmptyTiles;}
}
