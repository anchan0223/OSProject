import java.io.*;
import java.util.*;

class Task {
    int pid, arrivalTime, burstTime, priority;
    int waitingTime, turnaroundTime;

    public Task(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "PID: " + pid + ", AT: " + arrivalTime + ", BT: " + burstTime +
               ", WT: " + waitingTime + ", TAT: " + turnaroundTime;
    }
}

public class SimulationScheduler {
    
    // Read tasks from file
    public static List<Task> readTasks(String filename) {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split("\\s+");
                tasks.add(new Task(
                    Integer.parseInt(data[0]),
                    Integer.parseInt(data[1]),
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3])
                ));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return tasks;
    }

    // FCFS Scheduling
    public static void fcfsScheduling(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        List<Task> orderOfExecution = new ArrayList<>();

        for (Task t : tasks) {
            if (currentTime < t.arrivalTime) {
                currentTime = t.arrivalTime;
            }
            t.waitingTime = currentTime - t.arrivalTime;
            t.turnaroundTime = t.waitingTime + t.burstTime;
            currentTime += t.burstTime;
            orderOfExecution.add(t);
        }
// Display Gantt Chart
System.out.println("\n📌 First-Come, First-Served (FCFS) Results:");
System.out.print("Gantt Chart: ");
for (Task t : orderOfExecution) {
    System.out.print("| P" + t.pid + " ");
}
System.out.println("|");
System.out.print("Timeline: ");
currentTime = 0;
for (Task t : orderOfExecution) {
    System.out.print(currentTime + " ");
    currentTime += t.burstTime;
}
System.out.println(currentTime);

// Display the results for each process
for (Task t : tasks) {
    System.out.println(t);
}

// Calculate and print average waiting time and turnaround time
double avgWT = tasks.stream().mapToInt(t -> t.waitingTime).average().orElse(0);
double avgTAT = tasks.stream().mapToInt(t -> t.turnaroundTime).average().orElse(0);

System.out.println("\nAverage Waiting Time (WT): " + avgWT);
System.out.println("Average Turnaround Time (TAT): " + avgTAT);
}

    // SJF Scheduling
    public static void sjfScheduling(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(t -> t.burstTime));

        int currentTime = 0;
        List<Task> orderOfExecution = new ArrayList<>();

        for (Task t : tasks) {
            if (currentTime < t.arrivalTime) {
                currentTime = t.arrivalTime;
            }
            t.waitingTime = currentTime - t.arrivalTime;
            t.turnaroundTime = t.waitingTime + t.burstTime;
            currentTime += t.burstTime;
            orderOfExecution.add(t);
        }
        // Display Gantt Chart
        System.out.println("\n📌 Shortest Job First (SJF) Results:");
        System.out.print("Gantt Chart: ");
        for (Task t : orderOfExecution) {
            System.out.print("| P" + t.pid + " ");
        }
        System.out.println("|");
        System.out.print("Timeline: ");
        currentTime = 0;
        for (Task t : orderOfExecution) {
            System.out.print(currentTime + " ");
            currentTime += t.burstTime;
        }
        System.out.println(currentTime);

        // Display the results for each process
        for (Task t : tasks) {
            System.out.println(t);
        }

        // Calculate and print average waiting time and turnaround time
        double avgWT = tasks.stream().mapToInt(t -> t.waitingTime).average().orElse(0);
        double avgTAT = tasks.stream().mapToInt(t -> t.turnaroundTime).average().orElse(0);

        System.out.println("\nAverage Waiting Time (WT): " + avgWT);
        System.out.println("Average Turnaround Time (TAT): " + avgTAT);
    }

    public static void main(String[] args) {
        List<Task> tasks = readTasks("processes.txt");

        fcfsScheduling(new ArrayList<>(tasks)); // FCFS
        sjfScheduling(new ArrayList<>(tasks));  // SJF
    }
}
