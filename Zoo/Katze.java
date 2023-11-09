package Zoo;

public class Katze extends Tier  {
    private String fellfarbe;

    public Katze(String name, int alter, String fellfarbe) {
        super(name, alter);
        if (fellfarbe == null || fellfarbe.isEmpty()) {
            throw new IllegalArgumentException("Fellfarbe darf nicht leer sein");
        }
        this.fellfarbe = fellfarbe;
    }

    public String getFellfarbe() {
        return fellfarbe;
    }

    // Methoden aus Lebewesen Interface
    @Override
    public void sichBewegen() {
        System.out.println("Die Katze schleicht.");
    }

    @Override
    public void sprechen() {
        System.out.println("Die Katze miaut.");
    }

    @Override
    public void essen() {
        System.out.println("Die Katze frisst.");
    }

    @Override
    public int getAlter() {
        return super.getAlter();
    }

    @Override
    public void altern() {
        super.setAlter(super.getAlter() + 1);
    }

    // Überschreibung der toString-Methode für eine bessere Ausgabe
    @Override
    public String toString() {
        return "Katze{" +
                "name='" + super.getName() + '\'' +
                ", alter=" + super.getAlter() +
                ", fellfarbe='" + fellfarbe + '\'' +
                '}';
    }

    public void kratzen(){
        System.out.println(getName() + " krazt");
    }
}
