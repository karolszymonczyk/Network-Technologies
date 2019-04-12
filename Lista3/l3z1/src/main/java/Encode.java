import java.io.IOException;
import java.io.PrintWriter;

class Encode extends Code{

  private final static String signCode = "01111110";

  static void encode(String readFile, String writeFile) throws IOException {
    String message = readFromFile(readFile);
    String crc = calcCRC(message);
    String messageWithCRCR = message + crc;
    messageWithCRCR = stretch(messageWithCRCR);
    PrintWriter pw = new PrintWriter(writeFile);
    pw.println(signCode + messageWithCRCR + signCode);
    pw.close();
  }

  private static String stretch(String message) {
    StringBuilder str = new StringBuilder(message);
    int counter = 0;
    for (int i = 0; i < str.length(); i++) {
      Character c = str.charAt(i);
      if (c.equals('1') && counter < 5) {
        counter++;
        if (counter == 5) {
          str.insert(i + 1, '0');
          counter = 0;
        }
      } else {
        counter = 0;
      }
    }
    return str.toString();
  }
}
