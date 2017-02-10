package distributedme;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Process extends Thread {
    private Set<Process> processInSystem = new HashSet<>();
    private boolean wantsCritic = false, isCritical = false;
    private HashSet<Process> requestPending = new HashSet<>();
    private int id, timestamp, replyPending, timeout;
    private final int TIME_INTERVAL = 1000;


    Process(int id, int timestamp, int timeout, Collection<Process> processInSystem) {
        this.id = id;
        this.wantsCritic = true;
        this.timestamp = timestamp;
        this.timeout = timeout;
        if (!processInSystem.isEmpty())
            this.processInSystem = new HashSet<>(processInSystem);
        else
            this.processInSystem = new HashSet<>();
        //sendRequest();
    }

    int getTimestamp() {
        return timestamp;
    }

    void recieveOK(Process p) {
        replyPending--;
        if (replyPending == 0) {
            enterCritic();
        }
    }

    private void sendOk(Process p) {
        p.recieveOK(this);
    }

    private void recieveRequest(Process p) {
        if (p.timestamp > this.timestamp || isCritical && !wantsCritic)
            requestPending.add(p);
        else
            sendOk(p);
    }

    void sendRequest() {
        System.out.println(toString() + " is sending request.");
        replyPending = processInSystem.size();
        if (replyPending == 0)
            enterCritic();
        for (Iterator<Process> iterator = processInSystem.iterator(); iterator.hasNext(); ) {
            this.recieveRequest(iterator.next());
        }
    }

    private void enterCritic() {
        isCritical = true;
        start();
        System.out.println(this + " started executing");
    }

    private void leaveCritic() {
        isCritical = false;
        Process temp;
        for (Iterator<Process> iterator = requestPending.iterator(); iterator.hasNext(); ) {
            temp = iterator.next();
            iterator.remove();
            sendOk(temp);
        }
    }

    @Override
    public String toString() {
        return "Process :" + id;
    }

    @Override
    public void run() {
        System.out.println(toString() + " is running.");
        for (int i = 0; i < timeout; i++) {
            System.out.println(toString() + " " + i);
            try {
                sleep(TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(toString() + " has finished executing");
        leaveCritic();
    }
}
