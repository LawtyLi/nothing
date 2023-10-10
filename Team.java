import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Player> players;
    private int currentPlayerIndex;
    private int score;
    private Colour colour;
    public static final Team BOT_TEAM;

    static {
        BOT_TEAM = new Team("bot");
        BOT_TEAM.addPlayer(Player.BOT);
        Colour color = new Colour("cyan");
        BOT_TEAM.setColour(color);
    }
    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.score = 0;
    }
    public Colour getColour() {return colour;}
    public void setColour(Colour colour) {this.colour = colour;}
    public String getName() {return name;}
    public List<Player> getPlayers() {return players;}
    public int getPlayersLength(){return players.size();}
    public void addPlayer(Player player) {players.add(player);}
    public int getScore() {return score;}
    public void addScore() {score++;}
}
