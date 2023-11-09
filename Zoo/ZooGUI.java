package Zoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.io.File;

public class ZooGUI extends JFrame {

    private JPanel zooPanel;
    private JButton alleTiereSpeichernButton;
    private JButton alleTiereAusDateiLesenButton;
    private JTextField textFieldName;
    private JLabel Ueberschrift;
    private JButton beendenButton;
    private JTextArea textFieldAusgabe;
    private JTextField textFieldAlter;
    private JComboBox<String> comboBoxTierTyp;
    private JLabel TierTyp;
    private JLabel TierName;
    private JLabel Alter;
    private JLabel HundKatzeExtra;
    private JTextField textFieldHundKatzeExtra;

    ZooManager manager = new ZooManager();

    public ZooGUI() {

        setTitle("Zoo Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(zooPanel);
        setSize(600, 600);
        setLocationRelativeTo(null);

        alleTiereSpeichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                if (name == null || name.isEmpty()) {
                    textFieldAusgabe.setText("Bitte geben Sie einen Namen für das Tier ein.");
                    return;
                }

                int alter;
                try {
                    alter = Integer.parseInt(textFieldAlter.getText());
                } catch (NumberFormatException ex) {
                    textFieldAusgabe.setText("Bitte geben Sie eine gültige Zahl für das Alter ein.");
                    return;
                }

                if (alter < 0) {
                    textFieldAusgabe.setText("Das Alter eines Tieres kann nicht negativ sein. Bitte geben Sie ein gültiges Alter ein.");
                    return;
                }

                String typ = (String) comboBoxTierTyp.getSelectedItem();
                String extra = textFieldHundKatzeExtra.getText();

                manager.tierErstellenGUI(name, alter, typ, extra);

                String dateiPfad = getDateipfadVomBenutzer();
                if (dateiPfad == null) {
                    textFieldAusgabe.setText("Speichern abgebrochen.");
                    return;
                }

                manager.alleTiereSpeichernGUI(dateiPfad);
                textFieldAusgabe.setText("Tier erfolgreich erstellt und gespeichert!\n" + manager.getAlleTiereAlsString());
            }
        });




        alleTiereAusDateiLesenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dateiPfad = getDateipfadVomBenutzer();
                if (dateiPfad == null) {
                    textFieldAusgabe.setText("Laden abgebrochen.");
                    return;
                }

                List<String> tiere = manager.ladeTiereAusDateiGUI(dateiPfad);
                if (tiere.size() == 1 && (tiere.get(0).startsWith("Fehler") || tiere.get(0).startsWith("Datei nicht gefunden"))) {
                    textFieldAusgabe.setText(tiere.get(0));
                } else {
                    StringBuilder formatierteAusgabe = new StringBuilder();
                    for (String tier : tiere) {
                        formatierteAusgabe.append(tier).append(System.lineSeparator());
                    }
                    textFieldAusgabe.setText(formatierteAusgabe.toString());
                }
            }
        });


        beendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        comboBoxTierTyp.addItem("Tier");
        comboBoxTierTyp.addItem("Hund");
        comboBoxTierTyp.addItem("Katze");
    }



    private String getDateipfadVomBenutzer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Datei auswählen");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Textdateien", "txt"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            return fileToSave.getAbsolutePath();
        }
        return null;
    }


}

