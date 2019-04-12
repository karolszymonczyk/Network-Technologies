import java.io.IOException;
import java.io.PrintWriter;

class Decode extends Code{

  static void decode(String readFile, String writeFile) throws IOException {
    String code = readFromFile(readFile);
    String messageWithCRC = removeSignCode(code);
    messageWithCRC = unstretch(messageWithCRC);
    String message = removeCRC(messageWithCRC);
    String crc = removeMessage(messageWithCRC);
    PrintWriter pw = new PrintWriter(writeFile);
    if (!checkCrc(crc, message)) {
      System.out.println("Distortion found! Please try again.");
      pw.println("Distortion found! Please try again.");
      pw.close();
      return;
    }
    pw.println(message);
    pw.close();
  }

  private static boolean checkCrc(String crc, String message) {
    String currCrc = calcCRC(message);
    return currCrc.equals(crc);
  }

  private static String unstretch(String message) {
    StringBuilder str = new StringBuilder(message);
    int counter = 0;
    for (int i = 0; i < str.length(); i++) {
      Character c = str.charAt(i);
      if (c.equals('1') && counter < 5) {
        counter++;
        if (counter == 5) {
          str.deleteCharAt(i + 1);
          counter = 0;
        }
      } else {
        counter = 0;
      }
    }
    return str.toString();
  }

  private static String removeMessage(String messageWithCRC) {
    return messageWithCRC.substring(messageWithCRC.length() - 31);
  }

  private static String removeCRC(String messageWithCRC) {
    return messageWithCRC.substring(0, messageWithCRC.length() - 31);
  }

  private static String removeSignCode(String code) {
    String message = "";
    StringBuilder str = new StringBuilder(message);
    boolean readMessage = false;
    int counter = 0;
    for (int i = 0; i < code.length(); i++) {
      Character c = code.charAt(i);
      if (readMessage) {
        str.append(code.charAt(i));
      }
      if (c.equals('1') && counter < 6) {
        counter++;
        if (counter == 6) {
          readMessage = !readMessage;
          counter = 0;
        }
      } else {
        counter = 0;
      }
    }
    message = str.toString();

    return message.substring(1, str.length() - 7);
    //remove 0 from the beginning and 0111111 from the end
  }
}
