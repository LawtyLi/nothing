public class Tile { // the tile on the board
    private Piece piece;
    private Colour colour;
    private int x;
    private int y;
    private Board subBoard;
    public Tile(int x, int y) {
        this.piece = new Piece(" "); // initialize to empty
        this.colour = null; // initialize to null
        this.x = x;
        this.y = y;
        this.subBoard = null;
    }
    public Tile(Tile otherTile) {   // copy constructor
        this.x = otherTile.x;
        this.y = otherTile.y;
        this.piece = otherTile.piece;
        this.colour = otherTile.colour;
    }
    public Tile(int x, int y, Board subBoard) {
        this(x, y);
        this.subBoard = subBoard;
    }
    public Piece getPiece() {return piece;}
    public void setPiece(Piece piece) {this.piece = piece;}
    public Colour getColour() {return colour;}
    public void setColour(Colour colour) {this.colour = colour;}
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public Board getSubBoard() {return subBoard;}
}
