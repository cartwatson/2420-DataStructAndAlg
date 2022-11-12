public class TaskByDuration extends Task {
    public TaskByDuration(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        if (this.duration > o.duration) {
            return 1;
        } else if (this.duration == o.duration) {
            return 0;
        } else {
            return -1;
        }
    }
}
