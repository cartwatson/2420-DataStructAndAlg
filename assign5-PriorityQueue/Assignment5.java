import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
//        simpleQueueTest();
//        scheduleTasks("taskset1.txt");
//        scheduleTasks("taskset2.txt");
//        scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
//        scheduleTasks("taskset5.txt");
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
        // TODO: Write your task reading code here
        File file = new File(filename);
        int i = 0;
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] values = line.split("\t");
                // create task objects
                TaskByDeadline taskDeadline = new TaskByDeadline(i, Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                TaskByStart taskStart = new TaskByStart(i, Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                TaskByDuration taskDuration = new TaskByDuration(i, Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                // add objs to ArrayLists
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
        System.out.println(label);
        // feed list in queue
        PriorityQueue<Task> queue = new PriorityQueue<>();
        for (Task t : tasks) {
            queue.enqueue(t);
        }

        // start "time"
        int time = 0;
        int tasksLate = 0;
        int totalTimeLate = 0;
        while (!queue.isEmpty()) {
            // remove from queue and process for one until empty
            Task working = queue.dequeue();
            if (working.start > time) {
                queue.enqueue(working);
                working = null;
            } else { // add back into queue if necessary
                working.duration--;
            }
            // print out with time
            String taskID;
            if (working != null) { // unfinished task
                if (working.duration == 0) { // finished task
                    taskID = working.toString() + " **";
                    if (time > working.deadline) { // late task
                        tasksLate++;
                        int timeLate = time - working.deadline;
                        totalTimeLate += timeLate;
                        taskID += "Late " + timeLate;
                    }
                } else {
                    taskID = working.toString();
                    queue.enqueue(working);
                }
            } else { // no work done
                taskID = "---";
            }
            System.out.printf("\tTime %2d: %s %n", time, taskID);
            time++;
        }
        // final print
        System.out.printf("Tasks late %d // Total Time Late %d%n%n", tasksLate, totalTimeLate);
    }
}
