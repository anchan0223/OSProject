import java.io.*;
import java.util.*;

class Process {
    int pid, arrivalTime, burstTime, priority;
    int waitingTime, turnaroundTime;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
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

public class ProcessScheduler {
    
    // Read processes from file
    public static List<Process> readProcesses(String filename) {
        List<Process> processes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split("\\s+");
                processes.add(new Process(
                    Integer.parseInt(data[0]),
                    Integer.parseInt(data[1]),
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3])
                ));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return processes;
    }

    // FCFS Scheduling
    public static void fcfsScheduling(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.waitingTime = currentTime - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            currentTime += p.burstTime;
        }

        System.out.println("\n📌 First-Come, First-Served (FCFS) Results:");
        for (Process p : processes) System.out.println(p);
    }

    // SJF Scheduling
    public static void sjfScheduling(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.burstTime));

        int currentTime = 0;
        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.waitingTime = currentTime - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            currentTime += p.burstTime;
        }

        System.out.println("\n📌 Shortest Job First (SJF) Results:");
        for (Process p : processes) System.out.println(p);
    }

    public static void main(String[] args) {
        List<Process> processes = readProcesses("processes.txt");

        fcfsScheduling(new ArrayList<>(processes)); // FCFS
        sjfScheduling(new ArrayList<>(processes));  // SJF
    }
}
