package Zoo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZooManager {

    private final List<Tier> alleTiere = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static final String DATEI_PFAD = "C:\\Users\\ttann\\OneDrive - GFN AG (EDU)\\Java Learning\\LF11\\Zoo\\src\\Zoo\\zoo.txt";

    public void zeigeMenu() {
        System.out.println("Was möchten Sie tun?");
        System.out.println("1: Tier erstellen");
        System.out.println("2: Alle Tiere in Speicher anzeigen");
        System.out.println("3: Alle Tiere in Speicher in Datei speichern");
        System.out.println("4: Tiere aus Datei laden");
        System.out.println("0: Beenden");
    }

    public void verarbeiteAuswahl(int auswahl) {
        switch (auswahl) {
            case 1:
                tierErstellen();
                break;
            case 2:
                zeigeAlleTiereImZoo();
                break;
            case 3:
                alleTiereSpeichern();
                break;
            case 4:
                ladeTiereAusDatei();
                break;
            case 0:
                System.out.println("Auf Wiedersehen!");
                schliesseScanner();
                System.exit(0);
                break;
            default:
                System.out.println("Ungültig. Bitte versuchen Sie es erneut.");
                break;
        }
    }

    public void tierErstellen() {
        try {
            System.out.print("Name des Tieres: ");
            String name = scanner.nextLine();

            int alter = integerEingabe("Alter des Tieres: ");

            System.out.print("Typ des Tieres (Tier, Hund, Katze): ");
            String typ = scanner.nextLine();

            if ("Hund".equals(typ)) {
                System.out.print("Rasse des Hundes: ");
                String rasse = scanner.nextLine();
                alleTiere.add(new Hund(name, alter, rasse));
            } else if ("Katze".equals(typ)) {
                System.out.print("Fellfarbe der Katze: ");
                String fellfarbe = scanner.nextLine();
                alleTiere.add(new Katze(name, alter, fellfarbe)); //polymorphie im engeren Sinn Upcast!
            } else {
                alleTiere.add(new Tier(name, alter));
            }

            System.out.println("Tier erfolgreich erstellt.");
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    public void tierErstellenGUI(String name, int alter, String typ, String extra) {
        try {
            if ("Hund".equals(typ)) {
                alleTiere.add(new Hund(name, alter, extra));
            } else if ("Katze".equals(typ)) {
                alleTiere.add(new Katze(name, alter, extra));
            } else {
                alleTiere.add(new Tier(name, alter));
            }

            System.out.println("Tier erfolgreich erstellt.");
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    public void alleTiereSpeichern() {
        if (alleTiere.isEmpty()) {
            System.out.println("Es gibt nichts zum speichern");
            return;
        }
        try (FileWriter datei = new FileWriter(DATEI_PFAD, true)) {
            for (Tier tier : alleTiere) {
                datei.write(tier.toString() + "\n");
            }
            System.out.println("Tiere erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Tiere: " + e.getMessage());
        }
    }


    //Notizen während Besprechung
    //DOWNCAST und die Liste wieder als Objekte erstellen
    //Serialisierung
    //Deseriealisierung (wieder einlesen) --> DOWNCAST Kommt ins Spiel!

    public void ladeTiereAusDatei() {
        alleTiere.clear(); // Löschen der aktuellen Liste im Speicher... User muss zuerst laden!
        try (BufferedReader datei = new BufferedReader(new FileReader(DATEI_PFAD))) {
            String zeile;
            while ((zeile = datei.readLine()) != null) {
                //  Typ des Tieres.
                String typ = zeile.substring(0, zeile.indexOf('{'));
                // Entfernt den Typ + die geschweiften Klammern --> nur die Attribute zu erhalten.
                String attribute = zeile.substring(zeile.indexOf('{') + 1, zeile.indexOf('}'));
                // Teilt die Attribute (Komm) und entfernt Spaces.
                String[] teile = attribute.split(", ");

                // Namen und das Alter  alle Typen.
                String name = teile[0].split("'")[1];
                int alter = Integer.parseInt(teile[1].split("=")[1]);

                //Setzt alles zusammen
                switch (typ) {
                    case "Tier":
                        alleTiere.add(new Tier(name, alter));
                        break;
                    case "Hund":
                        // Rasse für Hunde.
                        String rasse = teile[2].split("'")[1];
                        alleTiere.add(new Hund(name, alter, rasse));
                        break;
                    case "Katze":
                        // Fellfarbe für Katzen.
                        String fellfarbe = teile[2].split("'")[1];
                        alleTiere.add(new Katze(name, alter, fellfarbe));
                        break;
                    default:
                        System.out.println("Unbekannt: " + typ);
                        break;
                }
            }
            System.out.println("Tiere erfolgreich geladen.");
        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Fehler beim Verarbeiten der Datei: " + e.getMessage());
        }
    }



    public void zeigeAlleTiereImZoo() {
        if (alleTiere.isEmpty()) {
            System.out.println("Es gibt keine Tiere im Speicher.");
        } else {
            for (Tier tier : alleTiere) {
                System.out.println(tier);
            }
        }
    }

    public int integerEingabe(String nachricht) {
        int number;
        while (true) {
            try {
                System.out.print(nachricht);
                number = Integer.parseInt(scanner.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Das ist keine gültige Zahl. Bitte versuchen Sie es erneut.");
            }
        }
    }

    public void ausfuehren() {
        while (true) {
            zeigeMenu();
            int auswahl = integerEingabe("Bitte wählen Sie eine Option: ");
            verarbeiteAuswahl(auswahl);
        }
    }

    public void schliesseScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    public String getAlleTiereAlsString() {
        StringBuilder sb = new StringBuilder();
        for (Tier tier : alleTiere) {
            sb.append(tier).append("\n");
        }
        return sb.toString();
    }

    public void alleTiereSpeichernGUI(String dateiPfad) {
        if (alleTiere.isEmpty()) {
            System.out.println("Es gibt nichts zum speichern");
            return;
        }
        try (FileWriter datei = new FileWriter(dateiPfad, true)) {
            for (Tier tier : alleTiere) {
                datei.write(tier.toString() + "\n");
            }
            System.out.println("Tiere erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Tiere: " + e.getMessage());
        }
    }

    public List<String> ladeTiereAusDateiGUI(String dateiPfad) {
        List<String> tiere = new ArrayList<>();
        try (BufferedReader datei = new BufferedReader(new FileReader(dateiPfad))) {
            String zeile;
            while ((zeile = datei.readLine()) != null) {
                tiere.add(zeile);
            }
        } catch (FileNotFoundException e) {
            tiere.add("Datei nicht gefunden: " + dateiPfad);
        } catch (IOException e) {
            tiere.add("Fehler beim Lesen der Datei: " + e.getMessage());
        }
        return tiere;
    }


}








