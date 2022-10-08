import java.util.ArrayList;

public class LadderGameExhaustive extends LadderGame {
    public LadderGameExhaustive(String dictionaryFile) {
        super(dictionaryFile);
    }

    @Override
    public void play(String start, String end) {
        System.out.println("Seeking exhaustive solution from " + start + " -> " + end);
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
        int enqueueTotal = 1;
        boolean isDone = false;
        while (!queue.isEmpty() && !isDone) {
            WordInfo testWordInfo = queue.dequeue();
            // find one away words, add to queue
            ArrayList<String> oneAwayFromTest = oneAway(testWordInfo.getWord(), true);
            for (String word : oneAwayFromTest) {
                // find words one away from the following word in the queue
                WordInfo tempWordInfo = new WordInfo(word, testWordInfo.getMoves() + 1,
                        testWordInfo.getHistory() + " " + word );
                // check most recent word for completion
                if (tempWordInfo.getWord().equals(end)) {
                    isDone = true;
                    System.out.print(tempWordInfo);
                    System.out.printf(" total enqueues %d", enqueueTotal);
                    System.out.println();
                    break;
                }
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
}
