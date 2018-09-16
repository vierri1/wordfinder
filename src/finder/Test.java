package finder;

public class Test {
    public static void main(String[] args) {
        WordFinder finder = new WordFinder();
        String[] files = new String[5];
        for (int i = 1; i <= 5; i++) {
            files[i - 1] = "/Users/Andrey/Documents/Учеба/TempFiles/file" + i + ".txt";
        }
        String words[] = {"innopolis", "stc13", "java", "spring", "hibernate"};
        finder.getOccurrences(files, words, "/Users/Andrey/Documents/Учеба/TempFiles/result.txt");
    }
}
