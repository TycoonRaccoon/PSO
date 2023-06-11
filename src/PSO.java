import java.util.Arrays;

import lombok.Data;
import net.objecthunter.exp4j.Expression;

@Data
public class PSO {
  private double w = INITIAL_W;
  private double rating;
  private Best globalBest;
  private Expression func;
  private String[] variables;
  private int populationSize;
  private int numberIterations;
  private Particle[] particles;

  private static final double INITIAL_W = 0.9;
  private static final double C1 = 2;
  private static final double C2 = 2;

  public PSO(int populationSize, int numberIterations, Expression func, String[] variables) {
    this.rating = (INITIAL_W - (INITIAL_W / numberIterations)) / numberIterations;
    this.populationSize = populationSize;
    this.numberIterations = numberIterations;
    this.func = func;
    this.variables = variables;
    this.globalBest = new Best(variables.length);
    this.particles = new Particle[populationSize];

    initialize();
  }

  public double[] execute() {
    for (int i = 1; i < numberIterations; i++) {
      for (var particle : particles) {
        particle.nextIteration(w, C1, C2, globalBest);
        particle.calculateLocalBest(func, variables);

        var fitness = particle.getLocalBest();
        if (fitness.getValue() < globalBest.getValue()) {
          globalBest.setValue(fitness.getValue());
          globalBest.setCoordenates(Arrays.copyOf(fitness.getCoordenates(), fitness.getCoordenates().length));
        }
      }
      this.w -= rating;
    }

    return globalBest.getCoordenates();
  }

  private void initialize() {
    for (int i = 0; i < populationSize; i++) {
      var particle = new Particle(variables.length);
      particle.calculateLocalBest(func, variables);

      var localBest = particle.getLocalBest();
      if (localBest.getValue() < globalBest.getValue())
        globalBest = localBest;

      particles[i] = particle;
    }
  }
}
