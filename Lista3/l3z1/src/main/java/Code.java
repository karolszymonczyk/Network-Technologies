import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.CRC32;

public abstract class Code {

  static String readFromFile(String fileName) throws IOException {
    return new String(Files.readAllBytes(Paths.get(fileName)));
  }

  static String calcCRC(String message) {
    CRC32 checksum = new CRC32();
    checksum.update(message.getBytes(), 0, message.getBytes().length);
    return make32(Long.toBinaryString(checksum.getValue()));
  }

  static String make32(String crc) {
    StringBuilder str = new StringBuilder(crc);
    while (str.length() < 31) {
      str.insert(0, '0');
    }
    return str.toString();
  }
}
