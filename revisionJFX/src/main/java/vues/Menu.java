package vues;

import controleur.Controleur;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import vues.gestionnaire.VueInterractive;

public class Menu implements VueInterractive {
    private Scene scene;
    private Controleur controleur;

    @FXML
    BorderPane borderPane;
    @FXML
    TextField id, mdp;

    public Scene getScene() {
        return scene;
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public static Menu creerVue() {
        Menu menu = new Menu();
        menu.initialiserVue();
        return menu;
    }

    public void initialiserVue() {
        BorderPane borderPane = new BorderPane();

        Label label = new Label("Page d'accueil");
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font(42));
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        borderPane.setTop(label);

        VBox vBox = new VBox();

        Button goToConnexion = new Button("Se connecter");
        goToConnexion.setFont(Font.font(24));
        goToConnexion.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button goToHorloge = new Button("Horloge");
        goToHorloge.setFont(Font.font(24));
        goToHorloge.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        vBox.getChildren().addAll(goToConnexion, goToHorloge);

        goToConnexion.setOnAction(actionEvent -> controleur.goToConnexion());
        goToHorloge.setOnAction(actionEvent -> controleur.goToHorloge());

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);

        borderPane.setCenter(vBox);

        this.scene = new Scene(borderPane, 640, 480);
    }

}
