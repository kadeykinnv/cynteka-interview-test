import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    TextManager textManager = new TextManager();
    try {
      List<String> text = textManager.getText();
      List<String> result = textManager.getResult(text);
      textManager.getOutputFile(result);
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}
