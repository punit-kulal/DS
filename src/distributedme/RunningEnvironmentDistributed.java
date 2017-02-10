package distributedme;

import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class RunningEnvironmentDistributed {


    public static void main(String[] args) throws InterruptedException {
        int n, timestamp = 0, timeout = 0, time = 0;
        LinkedList<Process> list = new LinkedList<>();
        Process temp, current;
        System.out.println("Enter number of process");
        Scanner i = new Scanner(System.in);
        n = i.nextInt();
        System.out.println("Enter process details");
        System.out.println("Timestamp \t" + "Timeout: ");
        //Take input
        for (int j = 1; j <= n; j++) {
            System.out.print("Process" + j + " ");
            timestamp = i.nextInt();
            timeout = i.nextInt();
            temp = new Process(j, timestamp, timeout, list);
            list.add(temp);
            System.out.println("\n");
        }
        //Set up process according to their timestamp
        while (!list.isEmpty()) {
            current = list.poll();
            if (current != null) {
                time = current.getTimestamp() * 1000 - time;
                sleep(time);
                current.sendRequest();
            }
        }
        i.close();
    }

}
