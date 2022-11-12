public class TaskByDeadline extends Task {
    public TaskByDeadline(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        if (this.deadline > o.deadline) {
            return 1;
        } else if (this.deadline == o.deadline) {
            return 0;
        } else {
            return -1;
        }
    }
}
