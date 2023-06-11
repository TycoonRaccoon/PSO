import java.util.Arrays;

import net.objecthunter.exp4j.ExpressionBuilder;

public class App {
  public static void main(String[] args) throws Exception {
    String[] variables = { "x", "y" };

    var func = new ExpressionBuilder("x^2+y^2")
        .variables(variables)
        .build();

    var pso = new PSO(20, 500, func, variables);
    System.out.println(Arrays.toString(pso.execute()));
  }
}
