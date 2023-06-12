import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;

import lombok.Data;
import net.objecthunter.exp4j.Expression;

@Data
public class Particle {
  private Best localBest;
  private double[] coordenates;
  private double[] velocities;

  private static final Random RAND_GEN = new Random();

  private static final double MAX_VELOCITY = 2;
  private static final double MIN = -40;
  private static final double MAX = 40;

  public Particle(int amountDimensions) {
    coordenates = new double[amountDimensions];
    velocities = new double[amountDimensions];
    localBest = new Best(amountDimensions);

    initialize();
  }

  private void initialize() {
    var best = new double[coordenates.length];
    for (int i = 0; i < coordenates.length; i++) {
      var x = RAND_GEN.nextDouble()*(MAX - MIN) + MIN;

      best[i] = x;
      coordenates[i] = x;
      velocities[i] = RAND_GEN.nextDouble()*(MAX_VELOCITY);
    }
    localBest.setCoordenates(Arrays.copyOf(coordenates, coordenates.length));
  }

  public void calculateLocalBest(Expression func, String[] variables) {
    var map = new HashMap<String, Double>();
    for (int i = 0; i < coordenates.length; i++)
      map.put(variables[i], coordenates[i]);

    func.setVariables(map);
    var result = func.evaluate();

    if (result < localBest.getValue()) {
      localBest.setValue(result);
      localBest.setCoordenates(Arrays.copyOf(coordenates, coordenates.length));
    }
  }

  public void nextIteration(double w, double c1, double c2, Best globalBest) {
    for (int i = 0; i < coordenates.length; i++) {
      var v = w * velocities[i]
          + c1 * RAND_GEN.nextInt(2) * (localBest.getCoordenates()[i] - coordenates[i])
          + c2 * RAND_GEN.nextInt(2) * (globalBest.getCoordenates()[i] - coordenates[i]);

      v = velocityControll(v);

      coordenates[i] = coordenateControll(coordenates[i] + v);

      velocities[i] = v;
    }
  }

  private double velocityControll(double v) {
    if (v > MAX_VELOCITY)
      return MAX_VELOCITY;
    else if (v < -MAX_VELOCITY)
      return -MAX_VELOCITY;
    return v;
  }

  private double coordenateControll(double c) {
    if (c > MAX || c < MIN)
      return (MIN + MAX) / 2;
    return c;
  }
}
