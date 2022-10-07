import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class LadderGame {
    private ArrayList<ArrayList<String>> masterList;

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
        for (ArrayList<String> strings : masterList) {
            for (String string : strings) {
                if (start.equals(string)) {
                    startValid = true;
                } else if (end.equals(string)) {
                    endValid = true;
                }
            }
        }
        if (!startValid || !endValid) {
            System.out.println("Not valid words!");
            return;
        }

        // play game
        ArrayList<ArrayList<String>> masterListClone = cloneArrayList(masterList); // clone dict
        Queue<WordInfo> queue = new Queue<>(); // create queue
        WordInfo startInfo = new WordInfo(start, 0); // convert start to word info
        masterList.get(start.length()).remove(start);
        queue.enqueue(startInfo);
        int enqueueTotal = 0;
        boolean isDone = false;
        while (!queue.isEmpty()) {
            WordInfo testWordInfo = queue.dequeue();
            // check for completion
            if (testWordInfo.getWord().equals(end)) {
                isDone = true;
                System.out.print(start + " -> " + testWordInfo);
                System.out.printf(" total enqueues %d", enqueueTotal);
                System.out.println();
                break;
            }

            // find one away words, add to queue
            ArrayList<String> oneAwayFromTest = oneAway(testWordInfo.getWord(), true);
            for (String word : oneAwayFromTest) {
                // find words one away from the following word in the queue
                WordInfo tempWordInfo = new WordInfo(word, testWordInfo.getMoves() + 1,
                        testWordInfo.getHistory() + " " + word );
                queue.enqueue(tempWordInfo);
                enqueueTotal++;
            }
        }
        if (!isDone) {
            System.out.println(start + " -> " + end + ": No ladder was found");
        }

        // restore dict
        masterList = masterListClone;
    }

    private ArrayList<ArrayList<String>> cloneArrayList(ArrayList<ArrayList<String>> list) {
        ArrayList<ArrayList<String>> clone = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> tempList : list) {
            ArrayList<String> newList = new ArrayList<String>();
            clone.add(newList);
            for (String tempWord : tempList) {
                newList.add(tempWord);
            }
        }
        return clone;
    }

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        ArrayList<String> oneAwayWords = new ArrayList<>();
        // masterlist for all words of same length as word
        for (String dictWord : masterList.get(word.length())) {
            if (diff(word, dictWord)) {
                oneAwayWords.add(dictWord);
            }
        }
        if (withRemoval) {
            for (String oneAwayWord : oneAwayWords) {
                masterList.get(word.length()).remove(oneAwayWord);
            }
        }
        return oneAwayWords;
    }

    private boolean diff(String word, String testWord) {
        int diffCount = 0;
        for (int i = 0; i < word.length(); i++) {
            char currLetter = word.charAt(i);
            char testLetter = testWord.charAt(i);
            if (currLetter != testLetter) {
                diffCount++;
            }
        }
        return diffCount == 1;
    }

    public void listWords(int length, int howMany) {
        System.out.printf("--- First %d words of length %d ---%n", howMany, length); // header text
        for (int i = 0; i < howMany; i++) {
            System.out.println(masterList.get(length).get(i));
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
            // create master array
            ArrayList<ArrayList<String>> masterl = new ArrayList<ArrayList<String>>();
            // add arrayLists to array
            for (int i = 0; i < longestWord-1; i++) {
                masterl.add(new ArrayList<String>());
                // add word(s) to inner array
                for (String word : allWords) {
                    if (word.length() == i) {
                        masterl.get(i).add(word);
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
