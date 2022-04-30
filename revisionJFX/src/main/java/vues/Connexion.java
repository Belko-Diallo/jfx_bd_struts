package vues;

import controleur.Controleur;
import controleur.ordre.LanceurOdre;
import controleur.ordre.Ordre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import vues.gestionnaire.EcouteurOrdre;
import vues.gestionnaire.VueInterractive;

import java.io.IOException;

public class Connexion implements VueInterractive, EcouteurOrdre {
    private Scene scene;
    private Controleur controleur;

    @FXML
    BorderPane borderPane;
    @FXML
    TextField id;
    @FXML
    PasswordField mdp;

    public Scene getScene() {
        reset();
        return scene;
    }

    public static Connexion creerVue() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Connexion.class.getResource("connexion.fxml"));
        Parent parent = fxmlLoader.load();
        Connexion res = fxmlLoader.getController();
        res.initialiserVue();
        return res;
    }

    private void initialiserVue() {
        this.scene = new Scene(borderPane, 640, 480);
    }

    public void goToPagePerso() {
        controleur.seConnecter(id, mdp);
    }

    @Override
    public void setAbonnements(LanceurOdre lanceurOdre) {
        lanceurOdre.abonnement(this, Ordre.INFORMATIONS_SAISIES_INCOHERENTES, Ordre.UTILISATEUR_DEJA_CONNECTE);
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void broadcast(Ordre ordre) {
    }

    public void reset() {
        id.setText("");
        mdp.setText("");
    }

    public void goToMenu() {
        controleur.goToMenu();
    }
}
