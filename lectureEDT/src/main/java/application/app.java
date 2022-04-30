package application;

import facade.facadeBDD;
import facade.facadeBDDImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.Timer;

public class app extends Application {
    @Override
    public void start(Stage stage) {

    }

    public static void main(String[] args) {
        facadeBDD facadeBDD = new facadeBDDImpl();
        Timer timer = new Timer(60000, e -> System.out.println(facadeBDD.miseAjourBDD()));
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
    }
}
