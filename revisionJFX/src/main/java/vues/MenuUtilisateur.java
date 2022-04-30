package vues;

import controleur.Controleur;
import controleur.ordre.LanceurOdre;
import controleur.ordre.Ordre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import modele.Pari;
import vues.gestionnaire.EcouteurOrdre;
import vues.gestionnaire.VueInterractive;

import java.io.IOException;
import java.util.Collection;

public class MenuUtilisateur implements VueInterractive, EcouteurOrdre {
    private Scene scene;
    private Controleur controleur;
    private ObservableList<String> data;

    @FXML
    BorderPane borderPane;
    @FXML
    Label id;
    @FXML
    ListView affichageListe;

    public Scene getScene() {
        id.setText(controleur.getUtilisateur().getLogin());
        return scene;
    }

    public static MenuUtilisateur creerVue() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuUtilisateur.class.getResource("menuUtilisateur.fxml"));
        Parent parent = fxmlLoader.load();
        MenuUtilisateur res = fxmlLoader.getController();
        res.initialiserVue();
        return res;
    }

    private void initialiserVue() {
        data = FXCollections.observableArrayList();
        affichageListe.setItems(data);
        this.scene = new Scene(borderPane, 640, 480);
    }

    @Override
    public void setAbonnements(LanceurOdre lanceurOdre) {
        lanceurOdre.abonnement(this, Ordre.DECONNEXION, Ordre.SHOW_MES_PARIS);
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void broadcast(Ordre ordre) {
        data.clear();
        Collection<Pari> lesParis = controleur.getParisUser();
        for (Pari pari : lesParis) {
            data.add(pari.getMatch().getSport() + " : " + pari.getMatch().getEquipe1() + " - " + pari.getMatch().getEquipe2()
                    + "mise sur " + pari.getVainqueur() + " pour " + pari.getMontant() + "€" + '\n');
        }
    }

    public void goToDeconnexion(ActionEvent actionEvent) {
        controleur.goToDeconnexion();
    }

    public void showParisOuverts(ActionEvent actionEvent) {
        controleur.showParisOuverts();
    }

    public void showMesParis(ActionEvent actionEvent) {
        controleur.showMesParis();
    }

}
