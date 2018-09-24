package finder;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static finder.SingleFileParser.sentFind;
import static finder.SingleFileParser.wordsFind;

public class WordFinder {
    private List<String> resultList = new ArrayList<>();
   // private List<Thread> threads = new ArrayList<>();

    public void getOccurrences(String[] sources, String[] words, String res) {
        Long startTime = System.currentTimeMillis();
        clearFile(res);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future> futures = new ArrayList<>();

        for (String source: sources) {
            Future future = executorService.submit(new SingleFileParser(resultList, words, source));
            futures.add(future);
        }

        for (Future future: futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        System.out.println((System.currentTimeMillis() - startTime)/1000d);
        save(res);
    }

    private void save(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (String s : resultList) {
                writer.write(s);
            }
            resultList.clear();
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
