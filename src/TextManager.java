import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextManager {

  public List<String> getText() throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("data/input.txt"));
    return lines;
  }

  public List<String> getResult(List<String> lines) {
    CustomWordSearch customWordSearch = new CustomWordSearch(lines);
    return customWordSearch.getResult();
  }

  public void getOutputFile(List<String> lines) {
    try {
      PrintWriter writer = new PrintWriter("data/output.txt");
      for (String str : lines) {
        writer.write(str + "\n");
      }
      writer.flush();
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
