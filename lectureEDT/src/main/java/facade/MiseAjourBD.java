package facade;

import modele.ConnexionDB;
import modele.Occupation;
import modele.Salle;

import java.sql.*;
import java.util.*;

public class MiseAjourBD {
    private Map<String, Salle> lesSalles = new DataENT().getDataENT(); //contient les salles avec leurs occupations respectives
    private Collection<String> lesNomsSalles = new ArrayList<>();
    private Map<String, Salle> pasDeSalle = new HashMap<>();

    public MiseAjourBD() {
        run();
    }

    public Map<String, Salle> getPasDeSalle() {
        return pasDeSalle;
    }

    private void run() {
        //premier ajout salle en BDD
        /*for(String nomSalle : lesSalles) {
            Connection connexion = ConnexionDB.getConnexion();
            try {
                PreparedStatement st = connexion.prepareStatement("insert into salle(nomSalle) values(?)");
                st.setString(1,nomSalle);
                st.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/

        // à faire : ajouter créneau si la salle existe

        //1- récupérer les salles et les comparer
        //2- ajouter occupation si la salle existe
        //3- sinon mettre les non inscrits dans un autre map
        //4- établir la partie manuelle plus tard
        //5- finaliser et bien simplifier la partie automatisation à travers Mise à jour BDD(façade)
        //6- commencer métier: administrateur (avec une façade)

        //récupère les nom salle de la BDD
        Connection connexion = ConnexionDB.getConnexion();
        try {
            PreparedStatement st = connexion.prepareStatement("select nomSalle from Salle");
            ResultSet resultSet = st.executeQuery();
            connexion.prepareStatement("truncate table occupation").execute();
            if (resultSet.wasNull()) {
                System.err.println("Pas de salles en bdd !");
            } else {
                while (resultSet.next()) {
                    lesNomsSalles.add(resultSet.getString(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // lesSalles => celle de l'ENT
        // lesNomsSalles => celle de la BDD

        // ajout des occupations par correspondance des salles récupérées de l'ENT avec celle de la BDD
        for (String nomSalle : lesSalles.keySet()) {
            if (lesNomsSalles.contains(nomSalle)) {
                try {
                    PreparedStatement st = connexion.prepareStatement("insert into occupation(nomSalle,dateDebut,dateFin) values (?,?,?)");
                    st.setString(1, nomSalle);
                    for (Occupation occupation : lesSalles.get(nomSalle).getLesOccupations()) {
                        st.setTimestamp(2, new Timestamp(occupation.getDateDebut().getTime()));
                        st.setTimestamp(3, new Timestamp(occupation.getDateFin().getTime()));
                        st.executeUpdate();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                //ensemble des occupations qui non pas eut d'attribution en BDD car la salle n'existe pas en BDD
                pasDeSalle.put(nomSalle, lesSalles.get(nomSalle));
            }
        }
        /*System.out.println("Les occupations non ajoutées à la BDD");
        pasDeSalle.keySet().forEach(System.out::println);*/
    }

}
