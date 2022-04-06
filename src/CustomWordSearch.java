import java.util.*;
import java.util.regex.Pattern;

public class CustomWordSearch {

  private List<String> lines;

  public CustomWordSearch(List<String> lines) {
    this.lines = lines;
  }

  public List<String> getResult() {
    return sameWordsSearch();
  }

  private List<String> sameWordsSearch() {
    List<List<String>> strings = sortLines();
    List<String[]> firstWordSet = getListIfMassiveWords(strings.get(0));
    List<String[]> secondWordSet = getListIfMassiveWords(strings.get(1));
    Map<Integer, Set<Integer>> firstResult = new HashMap<>();
    Map<Integer, Set<Integer>> secondResult = new HashMap<>();
    //поиск совпадений слов первного множества с вторым
    for (int i = 0; i < firstWordSet.size(); i++) {
      Set<Integer> coincidences = findCoincidences(firstWordSet.get(i), secondWordSet);
      firstResult.put(i, coincidences);
    }
    //поиск совпадений слов второго множества с первым
    for (int i = 0; i < secondWordSet.size(); i++) {
      Set<Integer> coincidences = findCoincidences(secondWordSet.get(i), firstWordSet);
      secondResult.put(i, coincidences);
    }
    List<String> result = new ArrayList<>();
    firstResult.forEach((numberString, required) -> {
      if (required.isEmpty()) {
        result.add(String.join(" ", firstWordSet.get(numberString)) + ":?");
      } else {
        String str = String.join(" ", firstWordSet.get(numberString)) + ":";
        for (Integer i : required) {
          str += String.join(" ", secondWordSet.get(i));
        }
        result.add(str);
      }
    });
    secondResult.forEach(((numberString, required) -> {
      if (required.isEmpty()) {
        result.add(String.join(" ", secondWordSet.get(numberString)) + ":?");
      }
    }));
    return result;
  }

  //поиск одинаковых слов из строки с множеством
  private Set<Integer> findCoincidences(String[] words, List<String[]> wordsList) {
    Set<Integer> result = new HashSet<>();
    //для каждого слова из строки
    for (int i = 0; i < words.length; i++) {
      //сравнение с каждым словом из множества
      for (int j = 0; j < wordsList.size(); j++) {
        for (int k = 0; k < wordsList.get(j).length; k++) {
          if (wordsList.get(j)[k].equalsIgnoreCase(words[i])) {
            result.add(j);
          }
        }
      }
    }
    return result;
  }

  //формируем массивы слов из строк
  private List<String[]> getListIfMassiveWords(List<String> lines) {
    List<String[]> response = new ArrayList<>();
    lines.forEach(s -> response.add(s.split(" ")));
    return response;
  }

  //получаем 2 множества
  private List<List<String>> sortLines() {
    List<List<String>> stringSets = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      //поиск числа (количество строк)
      if (isNumeric(lines.get(i))) {
        //строки от и до
        int from = i + 1;
        int to = Integer.parseInt(lines.get(i)) + i + 1;
        //формируем множества
        stringSets.add(getSetSortedLines(from, to));
      }
    }
    return stringSets;
  }

  //получаем set строк
  private List<String> getSetSortedLines(int from, int to) {
    List<String> response = new ArrayList<>();
    for (int i = from; i < to; i++) {
      response.add(lines.get(i));
    }
    return response;
  }

  //является ли строка числом
  private boolean isNumeric(String string) {
    String regex = "[0-9]+[\\.]?[0-9]*";
    return Pattern.matches(regex, string);
  }

}
