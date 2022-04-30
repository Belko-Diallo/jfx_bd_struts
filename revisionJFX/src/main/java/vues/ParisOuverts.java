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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import modele.Match;
import vues.gestionnaire.EcouteurOrdre;
import vues.gestionnaire.VueInterractive;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class ParisOuverts implements VueInterractive, EcouteurOrdre {
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

    public static ParisOuverts creerVue() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ParisOuverts.class.getResource("parisOuverts.fxml"));
        Parent parent = fxmlLoader.load();
        ParisOuverts res = fxmlLoader.getController();
        res.initialiserVue();
        return res;
    }

    private void initialiserVue() {
        data = FXCollections.observableArrayList();
        affichageListe.setItems(data);
        this.scene = new Scene(borderPane, 640, 480);
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void setAbonnements(LanceurOdre lanceurOdre) {
        lanceurOdre.abonnement(this, Ordre.SHOW_PARIS_OUVERTS);
    }

    @Override
    public void broadcast(Ordre ordre) {
        data.clear();
        Collection<Match> lesMatches = controleur.getLesMatches();
        for (Match match : lesMatches) {
            data.add(match.getSport() + " : " + match.getEquipe1() + " VS " + match.getEquipe2() + " => " + match.getQuand() + '\n');
        }
    }

    public void goToDeconnexion(ActionEvent actionEvent) {
        controleur.goToDeconnexion();
    }

    public void goToMenuUser(ActionEvent actionEvent) {
        controleur.goToMenuUser();
    }

    public void showMesParis(ActionEvent actionEvent) {
        controleur.showMesParis();
    }

    private void costumiserListeView() {
        ListView<Match> matchListeView = (ListView<Match>) affichageListe.getItems();
        matchListeView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Match> call(ListView<Match> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Match match, boolean b) {
                        if (!Objects.isNull(match)) {
                            this.setText(match.getSport() + " : " + match.getEquipe2());
                        }
                    }
                };
            }
        });
    }

    public void goToMenu(ActionEvent actionEvent) {
        controleur.goToMenu();
    }
}
