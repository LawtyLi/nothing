public class Tile {
    private Piece piece;
    private Colour colour;
    private int x;
    private int y;
    public Tile(int x, int y) {
        this.piece = new Piece(" "); // initialize to empty
        this.colour = null; // initialize to null
        this.x = x;
        this.y = y;
    }
    public Piece getPiece() {return piece;}
    public void setPiece(Piece piece) {this.piece = piece;}
    public Colour getColour() {return colour;}
    public void setColour(Colour colour) {this.colour = colour;}
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
}
