package finder;


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class WordFinder {
    public static int resultSentencesCount;
    private final Set<String> resultSet = new HashSet<>();
    private final int nThreads = 10;

    public void getOccurrences(String[] sources, String[] words, String res) {
        clearFile(res);

        Thread resultSaver = new Thread(() -> {
            while (true) {
                if (resultSet.size() > 250) {
                    save(res);
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        resultSaver.setDaemon(true);
        resultSaver.start();

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future> futures = new ArrayList<>();

        int partsCount =  nThreads / sources.length;
        if (partsCount < 1) {
            partsCount = 1;
        }
        //TODO посмотреть что будет с маленьким массивом
        int part = (int) Math.ceil((double) words.length / partsCount);
        ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();
        int remains = words.length;
        for (int i = 0; i < partsCount; i++) {
            arrayLists.add(new ArrayList<>());
            for (int j = 0; j < part; j++) {
                arrayLists.get(i).add(words[j + (words.length - remains)]);
            }
            remains = remains - part;
            if (remains < part) {
                part = remains;
            }
        }

        //test
        System.out.println("nThreads: " + nThreads + " cuntsources: " + sources.length);
        System.out.println("words.length: " + words.length);
        System.out.println("parts count " + arrayLists.size());
        int count = 0;
        for (ArrayList<String> list : arrayLists) {
            for (String str : list) {
                count++;
            }
        }
        System.out.println("Arrays.lentgh: " + count);
        //test

        for (String source : sources) {
            for (ArrayList<String> list : arrayLists) {
                Future future = executorService.submit(new SingleFileParser(resultSet,
                        list.toArray(new String[list.size()]), source));
                futures.add(future);
            }
        }

        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
        save(res);
    }

    private void save(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            synchronized (resultSet) {
                for (String s : resultSet) {
                    resultSentencesCount++;
                    writer.write(s);
                }
                resultSet.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFile(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
