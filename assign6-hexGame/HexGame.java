import java.util.ArrayList;

public class HexGame {
    private final DisjointSet set;
    private ArrayList<Color> colorArrayList;
    public final int size;
    private final int TOP_EDGE;
    private final int BOTTOM_EDGE;
    private final int LEFT_EDGE;
    private final int RIGHT_EDGE;

    // constructor
    public HexGame(int size) {
        this.size = size;
        int gameSize = size * size + 1 + 4; // +1 to start at 1 instead of zero; +4 for the edges;
        set = new DisjointSet(gameSize);
        // create array to hold colors
        colorArrayList = new ArrayList<>();
        for (int i = 0; i < gameSize; i++) {
            colorArrayList.add(Color.NONE);
        }
        // init edges
        TOP_EDGE    = gameSize - 4;
        BOTTOM_EDGE = gameSize - 3;
        LEFT_EDGE   = gameSize - 2;
        RIGHT_EDGE  = gameSize - 1;
        // color edges
        colorArrayList.set(TOP_EDGE, Color.RED);
        colorArrayList.set(BOTTOM_EDGE, Color.RED);
        colorArrayList.set(LEFT_EDGE, Color.BLUE);
        colorArrayList.set(RIGHT_EDGE, Color.BLUE);
    }

    public boolean playBlue(int position, boolean displayNeighbors) {
        return play(position, displayNeighbors, Color.BLUE);
    }

    public boolean playRed(int position, boolean displayNeighbors) {
        return play(position, displayNeighbors, Color.RED);
    }

    public boolean play(int position, boolean displayNeighbors, Color color) {
        // position is a valid integer for the available hex positions
        if (position < 1 || position > (size * size)) {
            return false;
        }
        // print out neighbors (debugging only)
        if (displayNeighbors) {
            System.out.println(getNeighbors(position, color));
        }
        // logic for playing
        if (this.colorOfOccupied(position) == Color.NONE) {
            // get correct neighbors
            ArrayList<Integer> neighbors = getNeighbors(position, color);
            for (Integer neighbor : neighbors) {
                // only union if neighbor is the correct color
                if (this.colorOfOccupied(neighbor) == color) {
                    set.union(position, neighbor);
                }
            }
            colorArrayList.set(position, color);
        } else { // space is occupied
            return false;
        }

        // return result of win condition
        if (color == Color.BLUE) {
            return set.find(LEFT_EDGE) == set.find(RIGHT_EDGE);
        } else {
            return set.find(TOP_EDGE) == set.find(BOTTOM_EDGE);
        }
    }

    // NOT REQUIRED ----------------------------------------------------------------------------------------------------
    enum Color {
        NONE,
        RED,
        BLUE
    }

    // returns color of cell
    public Color colorOfOccupied(int position) {
        return colorArrayList.get(position);
    }

    private ArrayList<Integer> getNeighbors(int position, Color color) {
        // cover case that position is an edge
        ArrayList<Integer> neighbors = new ArrayList<>();
        // init checkers for edge cases
        boolean topEdge = size >= position;
        boolean bottomEdge = size >= (size * size - position);
        boolean leftEdge = 1 == (position % size);
        boolean rightEdge = 0 == (position % size);

        // size of 1, why are you playing a game this small!
        if (topEdge && leftEdge && rightEdge && bottomEdge) {
            return new ArrayList<>();
        }

        // add neighbors
        if (!leftEdge) {
            neighbors.add(position - 1); // left
        } else if (color == Color.BLUE) {
            neighbors.add(LEFT_EDGE);
        }
        if (!rightEdge) {
            neighbors.add(position + 1); // right
        } else if (color == Color.BLUE) {
            neighbors.add(RIGHT_EDGE);
        }
        if (!topEdge) {
            neighbors.add(position - 11); // top left
            if (!rightEdge) {
                neighbors.add(position - 10); // top right
            }
        } else if (color == Color.RED) {
            neighbors.add(TOP_EDGE);
        }
        if (!bottomEdge) {
            neighbors.add(position + 11); // bottom right
            if (!leftEdge) {
                neighbors.add(position + 10); // bottom left
            }
        } else if (color == Color.RED) {
            neighbors.add(BOTTOM_EDGE);
        }

        return neighbors;
    }
}
