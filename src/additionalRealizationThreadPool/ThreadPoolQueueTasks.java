//package additionalRealizationThreadPool;
//
//import finder.SingleFileParser;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//public class ThreadPoolQueueTasks {
//    private Queue<String> tasks = new ConcurrentLinkedQueue<>();
//    private ArrayList<Thread> threads = new ArrayList<>();
//    private int threadCount;
//    private List<String> resultList;
//    private String[] words;
//
//    public ThreadPoolQueueTasks(int threadCount, String[] sources, List<String> resultList, String[] words) {
//        this.resultList = resultList;
//        this.words = words;
//        this.threadCount = threadCount;
//        tasks.addAll(Arrays.asList(sources));
//    }
//
//    public ArrayList<Thread> startThreads() {
//        for (int i = 0; i < threadCount; i++) {
//            Thread parser = new SingleFileParser(resultList, tasks, words);
//            parser.start();
//            threads.add(parser);
//        }
//        return threads;
//    }
//}
