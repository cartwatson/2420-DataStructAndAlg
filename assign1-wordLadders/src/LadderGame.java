import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class LadderGame {
    private ArrayList<String>[] masterList;
    
    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public void play(String start, String end) {
        // check compatibility of words
        if (start.length() != end.length()) {
            System.out.println("Start and end words not of matching length");
            return;
        }
        // check to make sure words are in dict
        boolean startValid = false;
        boolean endValid = false;
        for (int i = 0; i < masterList.length; i++) {
            for (int j = 0; j < masterList[i].size(); j++) {
                if (Objects.equals(masterList[i].get(j), start) ) {
                    startValid = true;
                } else if (Objects.equals(masterList[i].get(j), end)) {
                    endValid = true;
                }
                if (startValid && endValid) { break; }
            }
            if (startValid && endValid) { break; }
        }
        // TODO: play game
        ArrayList<String>[] masterListClone = masterList; // clone dict
        queue<WordInfo> queue = new queue<WordInfo>(); // create queue
        WordInfo startInfo = new WordInfo(start, 0); // convert start to word info
        // find words one away from start word
        boolean isComplete = false;
        while (!queue.isEmpty() && !isComplete) {
            WordInfo testWordInfo = queue.dequeue();
            if (testWordInfo.getWord() == end) {
                System.out.println(testWordInfo);
                break;
            }
            for (int i = 0; i < oneAway(testWordInfo.getWord(), false).size(); i++) {
                // find words one away from the following word in the queue
                WordInfo tempWordInfo = new WordInfo(oneAway(testWordInfo.getWord(), true).get(i),
                        testWordInfo.getMoves() + 1, testWordInfo.getHistory() + testWordInfo.getWord());
                queue.enqueue(tempWordInfo);
            }
        }

        // restore dict
        ArrayList<String>[] masterList = masterListClone;
    }

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        System.out.printf("--- Words One Away from '%s' ---%n", word); // header text
        ArrayList<String> oneAwayWords = new ArrayList<>();
        // TODO: find words that are one letter away

        // print words
        return oneAwayWords;
    }

    private int diff(String word, String testWord) {
        int diffCount = 0;
        for (int i = 0; i < word.length(); i++) {
            char currLetter = word.charAt(i);
            char testLetter = testWord.charAt(i);
            if (currLetter != testLetter) {
                diffCount++;
            }
        }
        return diffCount;
    }

    public void listWords(int length, int howMany) {
        System.out.printf("--- First %d words of length %d ---%n", howMany, length); // header text
        for (int i = 0; i < howMany; i++) {
            System.out.println(masterList[length].get(i));
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
                word = word.toLowerCase(); // fix any case inconsistencies
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            // create 2D array
            // create master array
            ArrayList<String>[] masterl = new ArrayList[longestWord];
            // add arrayLists to array
            for (int i = 2; i < longestWord; i++) {
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