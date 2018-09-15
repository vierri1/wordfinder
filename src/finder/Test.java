package finder;

public class Test {
    public static void main(String[] args) {
        WordFinder finder = new WordFinder();
        String[] files = new String[6];
        for (int i = 1; i <= 6; i++) {
            files[i - 1] = "/Users/Andrey/Documents/Учеба/TempFiles/file" + i + ".txt";
        }
        String words[] = {"spring", "innopolis", "cat"};
        finder.getOccurrences(files, words, "/Users/Andrey/Documents/Учеба/TempFiles/result.txt");
    }
}
