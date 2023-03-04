import java.util.*;
import java.io.*;

public class xkcdpwgen {

  private static final String SYMBOLS = "~!@#$%^&*.:;";

  public static void main(String[] args) throws IOException {
    int numWords = 4;
    int numCaps = 0;
    int numNumbers = 0;
    int numSymbols = 0;

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-w":
        case "--words.txt":
          numWords = Integer.parseInt(args[++i]);
          break;
        case "-c":
        case "--caps":
          numCaps = Integer.parseInt(args[++i]);
          break;
        case "-n":
        case "--numbers":
          numNumbers = Integer.parseInt(args[++i]);
          break;
        case "-s":
        case "--symbols":
          numSymbols = Integer.parseInt(args[++i]);
          break;
        case "-h":
        case "--help":
          printHelp();
          return;
      }
    }

    List<String> words = readWordsFromFile();
    if (words.isEmpty()) {
      System.out.println("Could not read words.txt file.");
      return;
    }

    String password = generatePassword(words, numWords, numCaps, numNumbers, numSymbols);
    System.out.println(password);
  }

  private static List<String> readWordsFromFile() throws IOException {
    List<String> words = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader("./words.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
      words.add(line);
    }
    reader.close();
    return words;
  }

  private static String generatePassword(List<String> words, int numWords, int numCaps,
                                         int numNumbers, int numSymbols) {
    StringBuilder password = new StringBuilder();

    Random rand = new Random();
    for (int i = 0; i < numWords; i++) {
      int index = rand.nextInt(words.size());
      String word = words.get(index);

      if (numCaps > 0 && rand.nextBoolean()) {
        word = capitalizeFirstLetter(word);
        numCaps--;
      }

      password.append(word);
    }

    for (int i = 0; i < numNumbers; i++) {
      int index = rand.nextInt(password.length());
      char num = (char) (rand.nextInt(10) + '0');
      password.insert(index, num);
    }

    for (int i = 0; i < numSymbols; i++) {
      int index = rand.nextInt(password.length());
      char symbol = SYMBOLS.charAt(rand.nextInt(SYMBOLS.length()));
      password.insert(index, symbol);
    }

    return password.toString();
  }

  private static String capitalizeFirstLetter(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  private static void printHelp() {
    System.out.println("usage: xkcdpwgen [-h] [-w WORDS] [-c CAPS] [-n NUMBERS] [-s SYMBOLS]");
    System.out.println("");
    System.out.println("Generate a secure, memorable password using the XKCD method");
    System.out.println("");
    System.out.println("optional arguments:");
    System.out.println("  -h, --help");
  }
}
