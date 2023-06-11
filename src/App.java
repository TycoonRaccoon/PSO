import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import net.objecthunter.exp4j.ExpressionBuilder;

public class App {
  public static void main(String[] args) throws Exception {
    var records = new ArrayList<List<String>>();
    try (var scanner = new Scanner(new File("functions.csv"))) {
      while (scanner.hasNextLine()) {
        records.add(getRecordFromLine(scanner.nextLine()));
      }
    }

    for (var r : records) {
      var f = r.get(0);
      var variables = r.subList(1, r.size()).toArray(new String[r.size() - 1]);
      var func = new ExpressionBuilder(f)
          .variables(variables)
          .build();

      var pso = new PSO(50, 5000, func, variables);
      System.out.println(
          "\033[1;35mFunction: \033[1;37m" + f
              + ":\n\033[1;32mResult: \033[0m" + Arrays.toString(pso.execute()) + "\n");
    }
  }

  private static List<String> getRecordFromLine(String line) {
    var values = new ArrayList<String>();
    try (var rowScanner = new Scanner(line)) {
      rowScanner.useDelimiter(",");
      while (rowScanner.hasNext()) {
        values.add(rowScanner.next());
      }
    }
    return values;
  }
}