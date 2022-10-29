import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
        simpleQueueTest();
        scheduleTasks("taskset1.txt");
        scheduleTasks("taskset2.txt");
        scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
        scheduleTasks("taskset5.txt");
    }

    public static void scheduleTasks(String taskFile) {
        ArrayList<Task> tasksByDeadline = new ArrayList<>();
        ArrayList<Task> tasksByStart = new ArrayList<>();
        ArrayList<Task> tasksByDuration = new ArrayList<>();

        readTasks(taskFile, tasksByDeadline, tasksByStart, tasksByDuration);

        schedule("Deadline Priority : " + taskFile, tasksByDeadline);
        schedule("Startime Priority : " + taskFile, tasksByStart);
        schedule("Duration priority : " + taskFile, tasksByDuration);
    }

    public static void simpleQueueTest() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.enqueue(9);
        queue.enqueue(7);
        queue.enqueue(5);
        queue.enqueue(3);
        queue.enqueue(1);
        queue.enqueue(10);

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }

    /**
     * Reads the task data from a file, and creates the three different sets of tasks for each
     */
    public static void readTasks(String filename,
                                 ArrayList<Task> tasksByDeadline,
                                 ArrayList<Task> tasksByStart,
                                 ArrayList<Task> tasksByDuration) {
        File file = new File(filename);
        int i = 1;
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] values = line.split("\t");
                // init values
                int start = Integer.parseInt(values[0]);
                int deadline = Integer.parseInt(values[1]);
                int duration = Integer.parseInt(values[2]);
                // create task objects
                TaskByDeadline taskDeadline = new TaskByDeadline(i, start, deadline, duration);
                TaskByStart taskStart = new TaskByStart(i, start, deadline, duration);
                TaskByDuration taskDuration = new TaskByDuration(i, start, deadline, duration);
                // add objects to ArrayLists
                tasksByDeadline.add(taskDeadline);
                tasksByStart.add(taskStart);
                tasksByDuration.add(taskDuration);
                i++;
            }
        } catch (java.io.IOException ex) {
            System.out.println("ERROR");
        }
    }

    /**
     * Given a set of tasks, schedules them and reports the scheduling results
     */
    public static void schedule(String label, ArrayList<Task> tasks) {
        // start line
        System.out.println(label);

        // inits
        int time = 0;
        int tasksLate = 0;
        int totalTimeLate = 0;
        // feed list in queue
        PriorityQueue<Task> queue = new PriorityQueue<>();

        // algorithm
        while (!tasks.isEmpty() || !queue.isEmpty()) {
            // increment time
            time++;
            // arraylist to remember what to remove
            ArrayList<Task> toRemove = new ArrayList<>();
            // enqueue
            for (Task t : tasks) {
                if (t.start == time) {
                    queue.enqueue(t);
                    toRemove.add(t);
                }
            }

            // remove tasks after enqueue
            for (Task t : toRemove) {
                tasks.remove(t);
            }


            // remove from queue and process for one until empty
            Task working = queue.dequeue();

            // format print
            String taskID;
            if (working != null) { // work was done
                // only work if start time has elapsed
                working.duration--;

                if (working.duration == 0) { // finished task
                    taskID = working.toString() + " **";
                    // was task late
                    if (time > working.deadline) {
                        tasksLate++;
                        // how late was the task
                        int timeLate = time - working.deadline;
                        totalTimeLate += timeLate;
                        taskID += " Late " + timeLate;
                    }
                } else { // unfinished task
                    taskID = working.toString();
                    queue.enqueue(working);
                }
            } else { // no work done
                taskID = "---";
            }
            // print work info
            System.out.printf("\tTime %2d: %s %n", time, taskID);
        }
        // final print
        System.out.printf("Tasks late %d // Total Time Late %d%n%n", tasksLate, totalTimeLate);
    }
}
