public class WordInfoPriority extends WordInfo implements Comparable<WordInfoPriority> {
    private int priority;
    public WordInfoPriority(String word, int moves, int estimateWork) {
        super(word, moves);
        priority = estimateWork;
    }

    public WordInfoPriority(String word, int moves, int estimateWork, String history) {
        super(word, moves, history);
        priority = estimateWork;
    }

    @Override
    public int compareTo(WordInfoPriority o) {
        // compare priority
        return Integer.compare(this.priority, o.priority);

//        if (this.priority < o.priority) {
//            return -1;
//        } else if (this.priority == o.priority) {
//            return 0;
//        } else {
//            return 1;
//        }
    }
}
