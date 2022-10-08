import java.util.ArrayList;

public class LadderGamePriority extends LadderGame {
    public LadderGamePriority(String dictionaryFile) {super(dictionaryFile);}

    @Override
    public void play(String start, String end) {
        // TODO: implement A* algorithm
        // NOTES -------------------------------------------------------------------------------------------------------
        // assign priority based on diff count & length of ladder
        // high priority goes first
        // current.priority = diff(current, end) + current.moves
        // END NOTES ---------------------------------------------------------------------------------------------------

        // ---- cover base cases ----
        System.out.println("Seeking A* solution from " + start + " -> " + end);
        if (!wordCompatibility(start, end)) {
            return;
        }
        AVLTree<WordInfoPriority> AVLTree = new AVLTree<>(); // create AVL Tree
        WordInfoPriority startWordInfo = new WordInfoPriority(start, 0, diff(start, end)); // add start to tree
        AVLTree.insert(startWordInfo);
        // TODO: don't remove words - but keep track of them
        int enqueueTotal = 1;
        boolean isDone = false;
        while (!AVLTree.isEmpty() && !isDone) {
            // remove start from tree
            WordInfoPriority testWordInfo = null; // TODO:FIGURE OUT WHAT THIS IS
            // find one away words, add to tree
            ArrayList<String> oneAwayFromTest = oneAway(testWordInfo.getWord(), true);
            for (String word : oneAwayFromTest) {
                WordInfoPriority tempWordInfo = new WordInfoPriority(word,
                        testWordInfo.getMoves() + 1,
                        diff(word, end) + testWordInfo.getMoves(),
                        testWordInfo.getHistory() + " " + word
                );
                // check most recent word for completion
                if (tempWordInfo.getWord().equals(end)) {
                    isDone = true;
                    System.out.printf(" " + tempWordInfo + " total enqueues %d", enqueueTotal);
                    System.out.println();
                    break;
                }
                AVLTree.insert(tempWordInfo);
                enqueueTotal++;
            }
        }
        if (!isDone) {
            System.out.println(start + " -> " + end + ": No ladder was found");
        }
    }
}
