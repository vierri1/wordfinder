package finder;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordFinder {
    private List<String> resultList = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();

    public void getOccurrences(String[] sources, String[] words, String res) {
        for(String fileName: sources) {
            Thread thread = new SingleFileParser(resultList, fileName, words);
            thread.start();
            threads.add(thread);
        }

        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(resultList);
        save(res);
    }

    private void save(String fileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(String s: resultList) {
                writer.write(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
