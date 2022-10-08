public class WordInfoPriority extends WordInfo implements Comparable<WordInfoPriority> {
    private final int priority;
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
        return Integer.compare(this.priority, o.priority);
    }
}
