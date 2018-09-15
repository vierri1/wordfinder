package finder;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordFinder {
    private List<String> resultList = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();

    public void getOccurencies(String[] sources, String[] words, String res) {
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
        save("/Users/Andrey/Documents/Учеба/TempFiles/result.txt");
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




    private void parseSingleFile(String source, String[] words) {

    }


}
