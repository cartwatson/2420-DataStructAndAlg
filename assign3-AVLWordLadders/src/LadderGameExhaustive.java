import java.util.ArrayList;

public class LadderGameExhaustive extends LadderGame {
    public LadderGameExhaustive(String dictionaryFile) {
        super(dictionaryFile);
    }

    @Override
    public void play(String start, String end) {
        System.out.println("Seeking exhaustive solution from " + start + " -> " + end);
        if (!wordCompatibility(start, end)) {
            return;
        }

        // play game
        ArrayList<ArrayList<String>> masterListClone = cloneArrayList(masterList); // clone dict
        Queue<WordInfoExhaustive> queue = new Queue<>(); // create queue
        WordInfoExhaustive startInfo = new WordInfoExhaustive(start, 0); // convert start to word info
        masterList.get(start.length()).remove(start);
        queue.enqueue(startInfo);
        int enqueueTotal = 1;
        boolean isDone = false;
        while (!queue.isEmpty() && !isDone) {
            WordInfoExhaustive testWordInfo = queue.dequeue();
            // find one away words, add to queue
            ArrayList<String> oneAwayFromTest = oneAway(testWordInfo.getWord(), true);
            for (String word : oneAwayFromTest) {
                // find words one away from the following word in the queue
                WordInfoExhaustive tempWordInfo = new WordInfoExhaustive(word,
                        testWordInfo.getMoves() + 1,
                        testWordInfo.getHistory() + " " + word
                );
                // check most recent word for completion
                if (tempWordInfo.getWord().equals(end)) {
                    isDone = true;
                    System.out.printf(" [" + tempWordInfo.getHistory() + "] total enqueues %d", enqueueTotal);
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
