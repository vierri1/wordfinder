package pool;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyThreadPool {

    private int threadCount;
    private List<Thread> runningThreads = new CopyOnWriteArrayList<>();
    private Queue<Runnable> queueThreads = new ConcurrentLinkedQueue<>();

    public MyThreadPool(int threadCount) {
        this.threadCount = threadCount;
    }

    public Thread runThread(Runnable runnable) {
        Thread thread;
        if (runningThreads.size() < threadCount) {
            thread = new Thread(runnable);
            runningThreads.add(thread);
            thread.start();
            //System.out.println(thread.getName() + " СТАРТОВАЛ из 1 блока для " + runnable.toString() + " runningThreads=" + runningThreads.size());
        } else {
           // System.out.println("Объект Runnable для " + runnable.toString() + "добавился в очередь");
            queueThreads.add(runnable);
            while (true) {
              //  System.out.println("Ожидаем смерти потока");
                if (runningThreads.removeIf(t -> !t.isAlive())) {
                    thread = new Thread(queueThreads.poll());
                    runningThreads.add(thread);
                    thread.start();
                   // System.out.println("поток умер, запустили " + thread.getName() + " из очереди");
                    break;
                }
            }
        }
        return thread;
    }
}
