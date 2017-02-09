
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Student
 */
class CentralMutualExclusion {
    public static void main(String[] args) throws InterruptedException {
        int i;
        long time = 0;
        GeneralProcess current;
        PriorityQueue<GeneralProcess> list = new PriorityQueue<>(10, new GeneralProcess());
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of process present");
        i = input.nextInt();
        System.out.println("Enter the process details as follows:Start_time Timeout");
        for (int j = 0; j < i; j++) {
            list.add(new GeneralProcess(j, input.nextInt(), input.nextInt()));
        }
        while (!list.isEmpty()) {
            current = list.poll();
            if (current != null) {
                time = current.getStart() - time;
                Thread.sleep(time);
                current.request();
            }
        }
        input.close();
    }

    static class CentralProcess {
        static LinkedList<GeneralProcess> pq = new LinkedList<>();
        static GeneralProcess underExec = null;

        static void receiveReq(GeneralProcess p) {
            if (underExec != null)
                pq.add(p);
            else
                reply(p);
        }

        static void putUnderExec(GeneralProcess q) {
            underExec = q;
            underExec.start();
        }

        private static void reply(GeneralProcess p) {
            System.out.println(p.toString() + " has recieved a reply.");
            putUnderExec(p);
        }


        private static void replyToNext() {
            GeneralProcess process = pq.poll();
            if (process != null) {
                System.out.println(process.toString() + " has recieved a reply.");
                putUnderExec(process);
            } else
                System.out.println("No Process Waiting");
        }

        static void releaseReciever(GeneralProcess p) {
            System.out.println(p + " has been released");
            replyToNext();
        }
    }
}


