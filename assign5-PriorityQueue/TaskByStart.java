public class TaskByStart extends Task {
    public TaskByStart(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        if (this.start > o.start) {
            return 1;
        } else if (this.start == o.start) {
            if (this.deadline > o.deadline) {
                return 1;
            } else if (this.deadline == o.deadline) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
