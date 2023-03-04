import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class xkcdpwgen {
  public static void main(String[] args) throws IOException {
    List<String> allWords = Files.readAllLines
            (Paths.get("/Users/lamtruong/Documents/cy/xkcdpwgen/src/word.txt"));
    ArrayList<String> words = new ArrayList<>();
    String password = "";
    //default
    for (int i = 0; i < 4; i++) {
      Random rand = new Random();
      String randomWord = allWords.get(rand.nextInt(allWords.size()));
      words.add(randomWord);
    }
    // Print out help for -h
    for (int i = 1; i < args.length; i++) {
        if (args[i].equals("-h")) {
          System.out.println("usage: xkcdpwgen [-h] [-w WORDS] [-c CAPS] [-n NUMBERS] [-s SYMBOLS]\n" +
                  "                \n" +
                  "Generate a secure, memorable password using the XKCD method\n" +
                  "                \n" +
                  "optional arguments:\n" +
                  "    -h, --help            show this help message and exit\n" +
                  "    -w WORDS, --words WORDS\n" +
                  "                          include WORDS words in the password (default=4)\n" +
                  "    -c CAPS, --caps CAPS  capitalize the first letter of CAPS random words\n" +
                  "                          (default=0)\n" +
                  "    -n NUMBERS, --numbers NUMBERS\n" +
                  "                          insert NUMBERS random numbers in the password\n" +
                  "                          (default=0)\n" +
                  "    -s SYMBOLS, --symbols SYMBOLS\n" +
                  "                          insert SYMBOLS random symbols in the password\n" +
                  "                          (default=0)");
        } else if (args[i].equals("-w") && i + 1 < args.length) {
          int num = Integer.parseInt(args[i + 1]);
          words = new ArrayList<>();
          for (int j = 0; j < num; j++) {
            Random rand = new Random();
            String randomWord = allWords.get(rand.nextInt(allWords.size()));
            words.add(randomWord);
          }
        } else if (args[i].equals("-c") && i + 1 < args.length) {
          int num = Integer.parseInt(args[i + 1]);
          if (num >= words.size()) {
            for (int j = 0; j < words.size(); j++) {
              String cap = words.get(j).substring(0, 1).toUpperCase()
                      + words.get(j).substring(1);
              words.set(j, cap);
            }
          } else {
            while (num > 0) {
              int size = words.size();
              int randomeIndex = new Random().nextInt(size);
              String randomeW = words.get(randomeIndex);
              //Check if the letter is already uppercase or not yet
              if (Character.isUpperCase(randomeW.charAt(0))) {
                num++;
              } else {
                String cap = words.get(randomeIndex).substring(0, 1).toUpperCase()
                        + words.get(randomeIndex).substring(1);
                words.set(randomeIndex, cap);
              }
              num--;
            }
          }
        } else if ((args[i].equals("-n") || args[i].equals("-s")) && i + 1 < args.length) {
          String[] list = new String[0];
          if (args[i].equals("-n")) {
            list = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
          }
          if (args[i].equals("-s")) {
            list = new String[]{"~", "!", "@", "#", "$", "%", "^", "&", "*", ".", ":", ";"};
          }
          int num = Integer.parseInt(args[i + 1]);
          while (num > 0) {
            int size = words.size();
            int randomeIndex = new Random().nextInt(size);
            int randNSIndex = new Random().nextInt(list.length);
            String randNS = list[randNSIndex];
            words.add(randomeIndex, randNS);
            num--;
          }
        }
    }
    for (int i = 0; i < words.size(); i++) {
      password = password + words.get(i);
    }
    System.out.println(password);
  }
}
