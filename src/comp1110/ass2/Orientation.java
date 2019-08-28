package comp1110.ass2;

public enum Orientation {
    NORTH (0), SOUTH (1), EAST (2), WEST (3);

    private final int direction;

    Orientation(int direction) {
        this.direction = direction;
    }

    public int toInt() {
        return this.direction;
    }


}
