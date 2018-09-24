package finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import static finder.SingleFileParser.sentFind;
import static finder.SingleFileParser.wordsFind;


public class Test {
    public static void main(String[] args) throws IOException {
        WordFinder finder = new WordFinder();


/*        String[] words = Files.lines(Paths.get("/Users/Andrey/IdeaProjects/Test/wordfinder/src/finder/words.txt"))
                .flatMap(line -> Arrays.stream(line.split("[|]")))
                .limit(1000)
                .map(String::trim)
                .filter(word -> !word.contains("'") && word.length() != 0)
                .toArray(String[]::new);*/


        String[] words = {"innopolis", "java", "generator"};


//        System.out.println("words count: " + words.length);
//        for (int i = 0; i < words.length; i++) {
//            System.out.println(words[i]);
//        }
//
        String[] files = new String[1000];
        for (int i = 1; i <= 1000; i++) {
            files[i - 1] = "/Users/Andrey/WORDFINDER/generatedfiles2/file" + i + ".txt";
        }
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        Long startTime = System.currentTimeMillis();
        finder.getOccurrences(files, words, "/Users/Andrey/WORDFINDER/result/result.txt");
        System.out.println((System.currentTimeMillis() - startTime) / 1000d);

        System.out.println(wordsFind);
        System.out.println(sentFind);
    }
}
