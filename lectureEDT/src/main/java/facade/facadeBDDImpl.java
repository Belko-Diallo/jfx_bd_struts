package facade;

import modele.ConnexionDB;

import java.sql.*;
import java.util.Date;
import java.util.Map;

public class facadeBDDImpl implements facadeBDD {
    MiseAjourBD miseAjourBD;
    Connection connexion = ConnexionDB.getConnexion();

    public facadeBDDImpl() {
        if (majAuto()) {
            this.miseAjourBD = new MiseAjourBD();
            try {
                connexion.prepareStatement("truncate table etat").execute();
                PreparedStatement st = connexion.prepareStatement("insert into etat(version,lastUpdate,nextUpdate) values(?,?,?)");
                st.setInt(1, 1);
                st.setTimestamp(2, new Timestamp(new Date().getTime()));
                st.setTimestamp(3, new Timestamp(new Date().getTime() + 60000 /*604800000*/));
                st.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public boolean miseAjourBDD() {
        this.miseAjourBD = new MiseAjourBD();
        Connection connexion = ConnexionDB.getConnexion();
        try {
            PreparedStatement st = connexion.prepareStatement("select * from etat", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet res = st.executeQuery();
            if (res.next()) {
                res.updateInt("version", res.getInt(1) + 1);
                res.updateTimestamp("lastUpdate", new Timestamp(new Date().getTime()));
                res.updateTimestamp("nextUpdate", new Timestamp(new Date().getTime() + 60000 /*604800000*/));
                res.updateRow();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<String, String> etatBDD() {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("select * from etat");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Map<String, String> etatBD = Map.of(
                        "version", resultSet.getString(1),
                        "lastUpdate", resultSet.getString(2),
                        "nextUpdate", resultSet.getString(3)
                );
                return etatBD;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void ajouterSalle(String nomSalle) {
        try {
            PreparedStatement st = connexion.prepareStatement("insert into salle(nomSalle) values(?)");
            st.setString(1, nomSalle);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        verifierOccupation();
    }

    /*
    devait permettre une mise à jour juste sur la salle ajoutée au cas où il y aurait déjà des cours prévus sur lENT concernant cette salle
     */
    private void verifierOccupation() {

    }

    /*
    true si datetime > nextUpdate
    false si datetime < nextUpdate
    détermine si l'on doit ou pas faire une maj
     */
    private boolean majAuto() {
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement("select nextUpdate from etat");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp(1);
                return timestamp.before(new Timestamp(new Date().getTime()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
