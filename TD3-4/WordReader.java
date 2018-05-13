import java.util.*;
import java.io.*;

class WordReader {
  private Scanner scan;

  WordReader(String filename) {
    try {
      File fichier = new File(filename);
      scan = new Scanner(fichier);

    } catch (FileNotFoundException e) {
      System.err.println("Malaise: " + e.getMessage());
      System.exit(2);
    }

  }

  WordReader(Readable in) {
    scan = new Scanner(in);
  }

  String read() {
    if (scan == null)
      return null;

    if (scan.hasNext())
      return scan.next();
    else {
      scan.close();
      return null;
    }
  }

  public static void main(String[] arg) {
    WordReader wr = new WordReader(arg[0]);
    int count = 0;
    System.out.println(arg[0]);
    System.out.println(wr.scan.hasNext());
    for (String w = wr.read(); w != null; w = wr.read()) {
      System.out.println("[" + w + "]");
      count++;
    }
    System.out.println(count);
  }
}
