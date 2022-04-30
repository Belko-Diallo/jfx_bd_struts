package facade;

import modele.Salle;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataENT {
    private int etat = 0; // été/hiver

    //return lesSalles
    public Map<String, Salle> getDataENT() {
        try {
            FileUtils.copyURLToFile(
                    new URL("https://www.univ-orleans.fr/EDTWeb/export?project=2020-2021&resources=12741&type=ical"),
                    new File("src//main//java//facade//data//EDT-L3Informatique.vcs"));
            FileUtils.copyURLToFile(
                    new URL("https://www.univ-orleans.fr/EDTWeb/export?project=2020-2021&resources=2278&type=ical"),
                    new File("src//main//java//facade//data//EDT-L3MIAGE.vcs"));
            FileUtils.copyURLToFile(
                    new URL("https://www.univ-orleans.fr/EDTWeb/export?project=2020-2021&resources=1127&type=ical"),
                    new File("src//main//java//facade//data//EDT-L2Informatique.vcs"));
        } catch (IOException e) {
            System.err.println("Erreur dans le chargement d'au moins un lien !");
        }
        Path[] fichier = {
                //Paths.get("src//main//java//facade//data//EDT-L3Informatique.vcs"),
                Paths.get("src//main//java//facade//data//EDT-L3MIAGE.vcs"),
                //Paths.get("src//main//java//facade//data//EDT-L2Informatique.vcs")
        };
        Map<String, Salle> bdd = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Iterator<Path> iterator = Arrays.stream(fichier).iterator();

        while (iterator.hasNext()) {
            List<String> lesLignes = null;
            try {
                lesLignes = Files.readAllLines(iterator.next());
            } catch (IOException e) {
                System.err.println("Fichier pas trouvé !");
                System.exit(0);
            }

            String laSalle = null;
            Date debut = null;
            Date fin = null;
            //parcourt tout le path actuel
            for (int i = 0; i < lesLignes.size(); i++) {
                // ligne de date de début
                if (lesLignes.get(i).startsWith("DTSTART")) {
                    String dateDebut = lesLignes.get(i).replace("DTSTART:", "");
                    debut = initialisationDate(dateDebut);
                }
                // ligne de date de fin
                if (lesLignes.get(i).startsWith("DTEND")) {
                    String dateFin = lesLignes.get(i).replace("DTEND:", "");
                    fin = initialisationDate(dateFin);
                }
                // ligne du nom de la salle
                if (lesLignes.get(i).startsWith("LOCATION")) {
                    laSalle = lesLignes.get(i).replace("LOCATION:", "");
                }
                // fin d'un cours
                if (lesLignes.get(i).startsWith("END:VEVENT")) {
                    if (laSalle.equals("")) { // si pas de nom => classe virtuelle ou classe à distance à voir
                        bdd.putIfAbsent("Classe virtuelle", new Salle("Classe virtuelle"));
                        bdd.get("Classe virtuelle").addOccupation(debut, fin);
                    } else {
                        bdd.putIfAbsent(laSalle, new Salle(laSalle));
                        bdd.get(laSalle).addOccupation(debut, fin);
                    }
                    //remise à null les données
                    laSalle = null;
                    debut = null;
                    fin = null;
                }
            }
        }
        /*for(String nomSalle : bdd.keySet()){
            System.out.println(nomSalle + " :\n");
            for(Occupation occupation : bdd.get(nomSalle).getLesOccupations()) {
                if(occupation.getDateDebut() != null || occupation.getDateFin() != null) {
                    System.out.println("     - " + sdf.format(occupation.getDateDebut()) + " à " + sdf.format(occupation.getDateFin()) + '\n');
                }
            }
        }*/
        return bdd;
    }

    private Date initialisationDate(String dateDebut) {
        int annee = Integer.parseInt(dateDebut.substring(0, 4));
        int mois = Integer.parseInt(dateDebut.substring(4, 6));
        int jour = Integer.parseInt(dateDebut.substring(6, 8));
        int heure = Integer.parseInt(dateDebut.substring(9, 11));
        int minute = Integer.parseInt(dateDebut.substring(11, 13));

        fuseauHoraire(jour, mois - 1, annee);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, annee);
        calendar.set(Calendar.MONTH, mois - 1);// Janvier commence à 0 !
        calendar.set(Calendar.DAY_OF_MONTH, jour);
        calendar.set(Calendar.HOUR_OF_DAY, heure + etat);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    /*
    Détermine heure d'été heure d'hiver
     */
    private void fuseauHoraire(int jour, int mois, int annee) {
        switch (mois) {
            case 3, 4, 5, 6, 7, 8 -> this.etat = 2; // avril à septembre
            case 10, 11, 0, 1 -> this.etat = 1; // novembre à février
        }
        // 2= mars & 9 = octobre
        if (mois == 2 || mois == 9) {
            GregorianCalendar c = new GregorianCalendar();
            int passage = 0;
            // détermination du dernier dimanche du mois
            for (int i = 31; i > 22; i--) {
                c.set(annee, mois - 1, i);
                if (c.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
                    passage = i;
                }
            }

            if (passage == 0) System.exit(15);

            if (jour < passage && mois == 2) this.etat = 1; //avant le dernier dimanche de mars
            if (jour < passage && mois == 9) this.etat = 2; // avant le dernier dimanche d'octobre

            if (mois == 2 && jour >= passage) this.etat = 2; // prendant et après le dernier dimanche de mars
            if (mois == 9 && jour >= passage) this.etat = 1; // pendant et après le dernier dimanche d'octobre
        }
    }
}
