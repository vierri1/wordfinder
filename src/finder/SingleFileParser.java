package finder;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SingleFileParser implements Runnable {

    private List<String> resultList;
    private String[] words;
    private String source;
    public static AtomicInteger wordsFind = new AtomicInteger(0);
    public static AtomicInteger sentFind = new AtomicInteger(0);


    public SingleFileParser(List<String> resultList, String[] words, String source) {
        this.resultList = resultList;
        this.words = words;
        this.source = source;
    }

    @Override
    public void run() {
        parse(source);
    }

    private void parse(String source) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(source));
            int chr;
            while ((chr = reader.read()) != -1) {
                if (chr != '\n') {
                    stringBuilder.append((char) Character.toLowerCase(chr));
                }
                if (chr == '?' || chr == '.' || chr == '…' || chr == '!') {
                    checkWordsInSentence(stringBuilder, words);
                    synchronized (sentFind) {
                        sentFind.incrementAndGet();
                    }
                    stringBuilder.delete(0, stringBuilder.length());
                }
            }
            reader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveResultSentence(String sentence) {
        synchronized (resultList) {
            resultList.add("\n" + sentence);
        }
    }

 /*  private boolean checkWordInSentence(StringBuilder sentence, String word) {

        Pattern pattern = Pattern.compile("\\b" + word + "\\b");
        Matcher matcher = pattern.matcher(sentence.toString());
        return matcher.find();
    }*/

    private boolean checkWordInSentence(StringBuilder stringBuilder, String word) {
        int firstInd;
        if ((firstInd = stringBuilder.indexOf(word)) != -1) {
            char nextChar = stringBuilder.charAt(word.length() + firstInd);
            if (nextChar == '!' || nextChar == '?' || nextChar == '.' ||
                    nextChar == ',' || nextChar == ' ' || nextChar == '…') {
                if (firstInd != 0)  {
                    char previousChar = stringBuilder.charAt(firstInd - 1);
                    if (previousChar == ' ' || previousChar == '\n') {
                        return true;
                    }
                } else {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private void checkWordsInSentence(StringBuilder sentence, String[] words) {
        for (String word : words) {
            if (checkWordInSentence(sentence, word)) {
                synchronized (wordsFind) {
                    wordsFind.incrementAndGet();
                }
                saveResultSentence(sentence.toString());
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
