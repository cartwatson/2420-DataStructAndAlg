import java.util.ArrayList;

public class HexGame {
    private final DisjointSet set;
    private final int TOP_EDGE;
    private final int BOTTOM_EDGE;
    private final int LEFT_EDGE;
    private final int RIGHT_EDGE;

    // constructor
    public HexGame(int size) {
        int gameSize = size * size + 1 + 4;
        set = new DisjointSet(gameSize);
        // init edges
        TOP_EDGE    = gameSize - 3; // 122
        BOTTOM_EDGE = gameSize - 2; // 123
        LEFT_EDGE   = gameSize - 1; // 124
        RIGHT_EDGE  = gameSize - 0; // 125
    }

    // turn for blue
    public boolean playBlue(int position, boolean displayNeighbors) {
        // position is an integer for the hex position
        // if displayNeighbors is true
            // the hex positions of neighboring cell are displayed, occupied or not
            // this is for debugging only
        // actual logic for playing


        // returns
            // true if the blue player meets winning condition
            // false if win condition isnt met
            // false if space is already occupied and does not replace value at that position
        return false;
    }
    // same as play blue
    public boolean playRed(int position, boolean displayNeighbors) {

        return false;
    }

    // not required
    enum Color {
        NONE,
        RED,
        BLUE
    }
    public Color[] getGrid() {

        return new Color[0];
    }

    // returns size of row or column
    public int getSize() {

        return 0;
    }

    // returns true is cell is occupied, false otherwise
    public int isOccupied(int position) {
        // returns -1 if occupied by blue
        // returns 0 if unoccupied
        // returns 1 if occupied by red
        return 0;
    }

    // returns the neighboring cells
    private ArrayList<Integer> getNeighborsRed(int position) {

        return new ArrayList<>();
    }
    private ArrayList<Integer> getNeighborsBlue(int position) {

        return new ArrayList<>();
    }
}
