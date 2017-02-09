import java.util.Comparator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Student
 */

public class GeneralProcess extends Thread implements Comparator<GeneralProcess> {
    private int id;
    private int start;
    private int timeout;
    private final long TIME_INTERVAL;

    {
        TIME_INTERVAL = 1000;
    }

    GeneralProcess() {
    }

    GeneralProcess(int id, int start, int timeout) {
        this.id = id;
        this.start = start * 1000;
        this.timeout = timeout;
    }

    int getStart() {
        return start;
    }

    void request() {
        System.out.println("Process " + id + " has arrived ");
        CentralMutualExclusion.CentralProcess.receiveReq(this);
    }

    private void release() {
        System.out.println("Process " + id + " has finished executing.");
        CentralMutualExclusion.CentralProcess.releaseReciever(this);
    }

    @Override
    public String toString() {
        return ("Process " + id);
    }

    @Override
    public void run() {
        System.out.println(this + " started executing");
        for (int i = 0; i < timeout; i++) {
            System.out.println("Currently executing " + this + " " + i);
            try {
                sleep(TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this + " completed executing and will now release");
        this.release();
    }

    @Override
    public int compare(GeneralProcess o1, GeneralProcess o2) {
        return o1.start - o2.start;
    }
}
