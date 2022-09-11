import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class LadderGame {
    private ArrayList<String>[] masterList;
    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public void play(String start, String end) {
        // TODO: check compatibility of words
        if (start.length() != end.length()) {
            System.out.println("Start and end words not of matching length");
            return;
        }
        // TODO: check to make sure words are in dict

    }

    public void oneAway(String word, boolean withRemoval) {
        System.out.printf("--- Words One Away from '%s' ---%n", word); // header text
        ArrayList<String> oneAwayWords = new ArrayList<>();
        // find words that are one letter away
        
        // print words
        System.out.println(oneAwayWords);
        return;
    }

    public void listWords(int length, int howMany) {
        System.out.printf("--- First %d words of length %d ---%n", length, howMany); // header text
        for (int i = 0; i < howMany; i++) {
            System.out.println(masterList[length-1].get(i));
        }
        System.out.println();
    }

    // Reads a list of words from a file, putting all words of the same length into the same array.
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();

        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            // TODO: You need to do something here to organize the words into groups/arrays of words with the same size
            // create 2D array
            // create master array
            ArrayList<String>[] masterl = new ArrayList[longestWord];
            // add arrayLists to array
            for (int i = 0; i < longestWord; i++) {
                masterl[i] = new ArrayList<String>();
                // add word(s) to inner array
                for (String word : allWords) {
                    if (word.length() == i) {
                        masterl[i].add(word);
                    }
                }
            }
            this.masterList = masterl;
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }

    }
}