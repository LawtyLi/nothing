public class Wall { // the wall on the board
    private Colour colour;
    private Direction direction;
    private Coordinate start;
    private Coordinate end;

    public Wall(Colour colour, Direction direction, Coordinate start) {
        this.colour = colour;
        this.direction = direction;
        this.start = start;
        if (direction == Direction.HORIZONTAL) {
            this.end = new Coordinate(start.getX(), start.getY() + 6); // 水平方向的Wall结束坐标
        } else {
            this.end = new Coordinate(start.getX() + 2, start.getY()); // 垂直方向的Wall结束坐标
        }
    }
    public enum Direction {
        VERTICAL, HORIZONTAL
    }
    public static class Coordinate {
        private int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {return x;}
        public int getY() {return y;}

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    public Colour getColour() {return colour;}
    public Direction getDirection() {return direction;}
    public Coordinate getStart() {return start;}
    public Coordinate getEnd() {return end;}
    public void setColour(Colour colour) {this.colour = colour;}
    public void setDirection(Direction direction) {this.direction = direction;}
    public void setStart(Coordinate start) {this.start = start;}
    public void setEnd(Coordinate end) {this.end = end;}
}
