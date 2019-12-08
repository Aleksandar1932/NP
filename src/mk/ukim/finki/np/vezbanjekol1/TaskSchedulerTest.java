package mk.ukim.finki.np.vezbanjekol1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * I Partial exam 2016
 */
public class TaskSchedulerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Task[] timeTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            timeTasks[i] = new TimedTask(x);
        }
        n = scanner.nextInt();
        Task[] priorityTasks = new Task[n];
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            priorityTasks[i] = new PriorityTask(x);
        }
        Arrays.stream(priorityTasks).forEach(System.out::println);
        TaskRunner<Task> runner = new TaskRunner<>();
        System.out.println("=== Ordered tasks ===");
        System.out.println("Timed tasks");
        runner.run(Schedulers.getOrdered(), timeTasks);
        System.out.println("Priority tasks");
        runner.run(Schedulers.getOrdered(), priorityTasks);
        int filter = scanner.nextInt();
        System.out.printf("=== Filtered time tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), timeTasks);
        System.out.printf("=== Filtered priority tasks with order less then %d ===\n", filter);
        runner.run(Schedulers.getFiltered(filter), priorityTasks);
        scanner.close();
    }
}

class TaskRunner<T extends Task> {
    void run(TaskScheduler<T> scheduler, T[] tasks) {
        List<T> order = scheduler.schedule(tasks);
        order.forEach(System.out::println);
    }
}

interface TaskScheduler<T extends Task> {
    List<T> schedule(T[] tasks);
}

interface Task extends Comparable<Task> {
    int getOrder();
}

class PriorityTask implements Task {
    private final int priority;

    PriorityTask(int priority) {
        this.priority = priority;
    }


    @Override
    public String toString() {
        return String.format("PT -> %d", getOrder());
    }

    @Override
    public int getOrder() {
        return priority;
    }

    @Override
    public int compareTo(Task otherTask) {
        return Integer.compare(this.priority,otherTask.getOrder());
    }
}

class TimedTask implements Task {
    private final int time;

    TimedTask(int time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return String.format("TT -> %d", getOrder());
    }

    @Override
    public int getOrder() {
        return time;
    }

    @Override
    public int compareTo(Task otherTask) {
        return Integer.compare(this.time, otherTask.getOrder());
    }
}

class Schedulers {
    static <T extends Task> TaskScheduler<T> getOrdered() {
            return tasks -> Arrays.stream(tasks).sorted().collect(Collectors.toList());

//        return new TaskScheduler<T>() {
//            @Override
//            public List<T> schedule(T[] tasks) {
//                return Arrays.stream(tasks).sorted().collect(Collectors.toList());
//            }
//        };
    }

    static <T extends Task> TaskScheduler<T> getFiltered(int order) {
        return tasks -> Arrays.stream(tasks).filter(task -> task.getOrder()<order).collect(Collectors.toList());
    }
}