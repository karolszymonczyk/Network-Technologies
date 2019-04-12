import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    final String working_dir = System.getProperty("user.dir");
    final String dest_dir = working_dir + "\\files";

    final String z = "\\z.txt";
    final String w = "\\w.txt";
    final String zc = "\\zc.txt";

    Encode.encode(dest_dir + z, dest_dir + w);

    Decode.decode(dest_dir + w, dest_dir + zc);
  }
}