public class Player {
    private Piece piece;
    private boolean chosen;
    private String name;
    private int score;
    private int numOfWalls;
    private int x;
    private int y;

    public static final Player BOT = new Player("bot");

    public Player(String playerName) {
        this.name = playerName;
        this.chosen = false;
        this.score = 0;
        this.numOfWalls = 10;
        this.x = 0;
        this.y = 0;
    }
    public Player(String playerName, int x, int y, Piece piece) {
        this.name = playerName;
        this.chosen = false;
        this.score = 0;
        this.numOfWalls = 10;
        this.x = x;
        this.y = y;
        this.piece = piece;
    }
    public Piece getPiece() {return piece;}
    public void setPiece(Piece piece) {this.piece = piece;}
    public boolean isChosen() {return chosen;}
    public void setChosen(boolean chosen) {this.chosen = chosen;}
    public int getScore() {return score;}
    public void addScore() {score++;}
    public String getName() {return name;}
    public int getNumOfWalls() {return numOfWalls;}

    public void setNumOfWalls(int numOfWalls) {this.numOfWalls = numOfWalls;}

    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
}
