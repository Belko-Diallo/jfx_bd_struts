package vues.gestionnaire;

import controleur.Controleur;
import controleur.ordre.LanceurOdre;
import controleur.ordre.Ordre;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import vues.*;

import java.io.IOException;

public class GestionnaireVues implements EcouteurOrdre, VueInterractive {
    private Stage stage;

    private Menu menu;
    private Connexion connexion;
    private MenuUtilisateur menuUtilisateur;
    private ParisOuverts parisOuverts;
    private Horloge horloge;

    public GestionnaireVues(Stage stage) {
        this.stage = stage;
        menu = Menu.creerVue();
        try {
            connexion = Connexion.creerVue();
            menuUtilisateur = MenuUtilisateur.creerVue();
            parisOuverts = ParisOuverts.creerVue();
            horloge = Horloge.creerVue();
        } catch (IOException e) {
            System.err.println("Problème chargement de vue FXML !");
            Platform.exit();
        }
    }

    @Override
    public void setAbonnements(LanceurOdre lanceurOdre) {
        lanceurOdre.abonnement(this, Ordre.values());
        connexion.setAbonnements(lanceurOdre);
        menuUtilisateur.setAbonnements(lanceurOdre);
        parisOuverts.setAbonnements(lanceurOdre);
        horloge.setAbonnements(lanceurOdre);
    }

    @Override
    public void setControleur(Controleur controleur) {
        menu.setControleur(controleur);
        connexion.setControleur(controleur);
        menuUtilisateur.setControleur(controleur);
        parisOuverts.setControleur(controleur);
        horloge.setControleur(controleur);
    }

    @Override
    public void broadcast(Ordre ordre) {
        switch (ordre) {
            case SHOW_MENU:
            case DECONNEXION:
                showMenu();
                break;
            case SHOW_CONNEXION:
                showConnexion();
                break;
            case UTILISATEUR_DEJA_CONNECTE:
                alertConnexion("Utilisateur déjà connecté !");
                break;
            case INFORMATIONS_SAISIES_INCOHERENTES:
                alertConnexion("Informations saisies incorrectes !");
                break;
            case SHOW_MENU_USER:
            case SHOW_MES_PARIS:
                showMenuUser();
                break;
            case SHOW_PARIS_OUVERTS:
                showParisOuverts();
                break;
            case SHOW_HORLOGE:
                showHorloge();
                break;
        }
    }

    private void showHorloge() {
        stage.setScene(horloge.getScene());
        stage.show();
    }

    private void showMenuUser() {
        stage.setScene(menuUtilisateur.getScene());
        stage.show();
    }

    private void alertConnexion(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR, s);
        alert.setTitle("Erreur de connexion");
        alert.showAndWait();
    }

    private void showConnexion() {
        stage.setScene(connexion.getScene());
        stage.show();
    }

    private void showMenu() {
        stage.setScene(menu.getScene());
        stage.show();
    }

    private void showParisOuverts() {
        stage.setScene(parisOuverts.getScene());
        stage.show();
    }
}
