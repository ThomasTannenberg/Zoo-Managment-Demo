package Zoo;


// implements Lebewesen nicht notwendig
public class Hund extends Tier  {
    private String rasse;

    public Hund(String name, int alter, String rasse) {
        super(name, alter);
        if (rasse == null || rasse.isEmpty()) {
            throw new IllegalArgumentException("Rasse darf nicht leer sein");
        }
        this.rasse = rasse;
    }

    public String getRasse() {
        return rasse;
    }

    // Methoden aus Lebewesen Interface
    @Override
    public void sichBewegen() {
        System.out.println("Der Hund laeuft.");
    }

    @Override
    public void sprechen() {
        System.out.println("Der Hund bellt.");
    }

    @Override
    public void essen() {
        System.out.println("Der Hund frisst.");
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
        return "Hund{" +
                "name='" + super.getName() + '\'' +
                ", alter=" + super.getAlter() +
                ", rasse='" + rasse + '\'' +
                '}';
    }

    public void grabben(){
        System.out.println(getName() + " graebt");
    }
}