import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Assignment6Driver {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {

        testGame();
//        testDisjointSet();
        playGame("moves1.txt");
        System.out.println();
        playGame("moves2.txt");
    }

    private static void testDisjointSet() {
        DisjointSet set = new DisjointSet(126);
        System.out.println(set);
        set.union(118, 124);
        set.union(124, 125);
        set.union(118, 123);
        System.out.println(set);
        int temp = set.find(123);
        System.out.println("118: " + temp);
    }
    private static void playGame(String filename) {
        // read in file
        ArrayList<Integer> moves = new ArrayList<>();
        File file = new File(filename);
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                moves.add(parseInt(line)); // this assumes good move files
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the moves file: " + ex);
        }

        // play game
        HexGame game = new HexGame(11);
        for (int i = 0; i < moves.size(); i++) {
            if (i % 2 == 0) {
                if (game.playBlue(moves.get(i), false)) {
                    System.out.printf("Blue wins with move at position %d!!\n", moves.get(i));
                    break;
                }
            } else {
                if (game.playRed(moves.get(i), false)) {
                    System.out.printf("Red wins with move at position %d!!\n", moves.get(i));
                    break;
                }
            }
        }
        // print game
        printGrid(game);
    }

    // You can use this to compare with the output show in the assignment while working on your code
    private static void testGame() {
        HexGame game = new HexGame(11);

        System.out.println("--- red ---");
        game.playRed(1, true);
        game.playRed(11, true);
        game.playRed(122 - 12, true);
        game.playRed(122 - 11, true);
        game.playRed(122 - 10, true);
        game.playRed(121, true);
        game.playRed(61, true);

        System.out.println("--- blue ---");
        game.playBlue(1, true);
        game.playBlue(2, true);
        game.playBlue(11, true);
        game.playBlue(12, true);
        game.playBlue(121, true);
        game.playBlue(122 - 11, true);
        game.playBlue(62, true);

        printGrid(game);
    }

    private static void printGrid(HexGame game) {
        System.out.println();
        int size = game.size;
        // iterate through game board
        for (int i = 1; i < (size + 1); i++) {
            System.out.print(" ".repeat(i));
            for (int j = 1; j < (size + 1); j++) {
                int index = (i - 1) * size + j; // compute index
                // determine output
                if (game.colorOfOccupied(index) == HexGame.Color.RED) { // if red: output a red R
                    System.out.print(ANSI_RED + "R" + ANSI_RESET);
                } else if (game.colorOfOccupied(index) == HexGame.Color.BLUE) { // if blue: output a blue B
                    System.out.print(ANSI_BLUE + "B" + ANSI_RESET);
                } else { // else: output a gray 0
                    System.out.print("0");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
