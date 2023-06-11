import lombok.Data;

@Data
public class Best {
  private double[] coordenates;
  private double value = Double.POSITIVE_INFINITY;

  public Best(int size) {
    this.coordenates = new double[size];
  }
}
