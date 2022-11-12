import java.util.ArrayList;

public class HexGame {
    // constructor
    public HexGame(int size) {

    }

    // turn for blue
    public boolean playBlue(int position, boolean displayNeighbors) {
        // position is an integer for the hex position
        // if displayNeighbors is true
            // the hex positions of neighboring cell are displayed, occupied or not
            // this is for debugging only
        // returns
            // true if the blue player meets winning condition
            // false if win condition isnt met
            // false if space is already occupied and does not replace value at that position
    }
    // same as play blue
    public boolean playRed(int position, boolean displayNeighbors) {

    }

    // not required
    enum Color {
        NONE,
        RED,
        BLUE
    }
    public Color[] getGrid() {

    }

    // returns size of row or column
    public int getSize() {

    }

    // returns true is cell is occupied, false otherwise
    public int isOccupied(int position) {
        // returns -1 if occupied by blue
        // returns 0 if unoccupied
        // returns 1 if occupied by red
    }

    // returns the neighboring cells
    private ArrayList<Integer> getNeighborsRed(int position) {

    }
    private ArrayList<Integer> getNeighborsBlue(int position) {

    }
}
