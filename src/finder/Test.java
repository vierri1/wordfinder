package finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static finder.SingleFileParser.wordsFind;


public class Test {
    public static void main(String[] args) throws IOException {
        WordFinder finder = new WordFinder();

        String[] words = Files.lines(Paths.get("/Users/Andrey/Documents/Учеба/stc13/projects/wordfinder/src/finder/words.txt"))
                .flatMap(line -> Arrays.stream(line.split("[|]")))
                .limit(1000)
                .map(String::trim)
                .distinct()
                .filter(word -> !word.contains("'") && word.length() != 0)
                .toArray(String[]::new);

        //TODO Сделать нормальное чтение файлов
        String[] files = new String[1];
        for (int i = 1; i <= 1; i++) {
            files[i - 1] = "/Users/Andrey/WORDFINDER/generatedfiles2/file" + i + ".txt";
        }

        Long startTime = System.currentTimeMillis();
        finder.getOccurrences(files, words, "/Users/Andrey/WORDFINDER/result/result.txt");
        System.out.println((System.currentTimeMillis() - startTime) / 1000d);

        System.out.println(wordsFind);
        System.out.println(WordFinder.resultSentencesCount);
    }
}
