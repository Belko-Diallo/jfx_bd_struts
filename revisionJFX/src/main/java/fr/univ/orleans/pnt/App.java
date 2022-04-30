package fr.univ.orleans.pnt;

import controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.gestionnaire.GestionnaireVues;

public class App extends Application {

    //private static Scene scene;

    @Override
    public void start(Stage stage) {
        Controleur controleur = new Controleur(new GestionnaireVues(stage));
        controleur.run();
    }

    public static void main(String[] args) {
        launch();
    }

    /*static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }*/

}