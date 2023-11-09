package Zoo;

public class Tier implements Lebewesen {
  protected String name;
  protected int alter;

  public Tier(String name, int alter) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name darf nicht leer sein");
    }
    if (alter < 0) {
      throw new IllegalArgumentException("Alter darf nicht negativ sein");
    }
    this.name = name;
    this.alter = alter;
  }

  public void setAlter(int alter) {
    this.alter = alter;
  }

  public String getName() {
    return name;
  }

  @Override
  public void sichBewegen() {
    System.out.println(name + " bewegt sich.");
  }

  @Override
  public void sprechen() {
    System.out.println(name + " macht Geräusche.");
  }

  @Override
  public void essen() {
    System.out.println(name + " isst.");
  }

  @Override
  public int getAlter() {
    return alter;
  }

  @Override
  public void altern() {
    alter++;
  }

  // Override toString Methode
  @Override
  public String toString() {
    return "Tier{" +
            "name='" + name + '\'' +
            ", alter=" + alter +
            '}';
  }
}

