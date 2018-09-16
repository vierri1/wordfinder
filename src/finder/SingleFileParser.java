package finder;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleFileParser implements Runnable {

    private List<String> resultList;
    private String[] words;
    //private Queue<String> sourcesQueue;
    private String source;

    public SingleFileParser(List<String> resultList, String[] words, String source) {
        this.resultList = resultList;
        this.words = words;
        this.source = source;
    }

    //    public SingleFileParser(List<String> resultList, Queue<String> sourcesQueue, String[] words) {
//        this.resultList = resultList;
//        this.sourcesQueue = sourcesQueue;
//        this.words = words;
//    }

//    @Override
//    public void run() {
//        String source;
//        while ((source = sourcesQueue.poll()) != null) {
//            parse(source);
//            System.out.println("\nПОТОК : " + currentThread().getName());
//        }
//    }

    @Override
    public void run() {
        //System.out.println("\nметод RUN потока " + Thread.currentThread().getName());
        parse(source);
    }

    private void parse(String source) {
        try (Scanner scanner = new Scanner(new File(source))) {
           // Pattern pattern = Pattern.compile("[\\?\\.!]");
            Pattern pattern = Pattern.compile("[\\w\\d\\s]*[\\?\\.…!]");
            Matcher matcher;
            scanner.useDelimiter("[\n]");
            while (scanner.hasNext()) {
                matcher = pattern.matcher(scanner.next());
                while (matcher.find()) {
                    String sentence = matcher.group();
                    checkWordsInSentence(sentence, words);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveResultSentence(String sentence) {
        synchronized (resultList) {
            resultList.add("\n" + sentence.trim());
        }
    }

    private boolean checkWordInSentence(String sentence, String word) {
        sentence = sentence.toLowerCase();
        return sentence.contains(word);
    }

    private void checkWordsInSentence(String sentence, String[] words) {
        for (String word : words) {
            if (checkWordInSentence(sentence, word)) {
                saveResultSentence(sentence);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "SingleFileParser{" +
                "source='" + source + '\'' +
                '}';
    }
}
