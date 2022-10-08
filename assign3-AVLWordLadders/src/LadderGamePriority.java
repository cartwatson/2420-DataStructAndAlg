import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class LadderGamePriority extends LadderGame {
    public LadderGamePriority(String dictionaryFile) {super(dictionaryFile);}

    @Override
    public void play(String start, String end) {
        // ---- cover base cases ----
        System.out.println("Seeking A* solution from " + start + " -> " + end);
        if (!wordCompatibility(start, end)) {
            return;
        }
        // find the shortest path
        AVLTree<WordInfoPriority> AVLTree = new AVLTree<>(); // create AVL Tree
        WordInfoPriority startWordInfo = new WordInfoPriority(start, 0, diff(start, end)); // add start to tree
        AVLTree.insert(startWordInfo);
        HashMap<String, WordInfoPriority> usedWords = new HashMap<>();
        int enqueueTotal = 1;
        boolean isDone = false;
        while (!AVLTree.isEmpty() && !isDone) {
            // remove start from tree
            WordInfoPriority testWordInfo = AVLTree.deleteMin();
            // find one away words, add to tree
            ArrayList<String> oneAwayFromTest = oneAway(testWordInfo.getWord(), false);
            for (String word : oneAwayFromTest) {
                WordInfoPriority tempWordInfo = new WordInfoPriority(word,
                        testWordInfo.getMoves() + 1,
                        diff(word, end) + testWordInfo.getMoves(),
                        testWordInfo.getHistory() + " " + word
                );

                // check most recent word for completion
                if (tempWordInfo.getWord().equals(end)) {
                    isDone = true;
                    System.out.printf(" [" + tempWordInfo.getHistory() + "] total enqueues %d", enqueueTotal);
                    System.out.println();
                    break;
                }

                // check against used words
                if (usedWords.containsKey(tempWordInfo.getWord())) {
                    if (tempWordInfo.getMoves() < usedWords.get(tempWordInfo.getWord()).getMoves()) {
                        usedWords.replace(tempWordInfo.getWord(), tempWordInfo); // update used list with new length
                        AVLTree.insert(tempWordInfo); // add to priority queue
                        enqueueTotal++;
                    }
                } else {
                    usedWords.put(tempWordInfo.getWord(), tempWordInfo); // add words to words checked
                    AVLTree.insert(tempWordInfo); // add to priority queue
                    enqueueTotal++;
                }
            }
        }
        if (!isDone) {
            System.out.println(start + " -> " + end + ": No ladder was found");
        }
    }
}
