package vues;

import controleur.Controleur;
import controleur.ordre.LanceurOdre;
import controleur.ordre.Ordre;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import vues.gestionnaire.EcouteurOrdre;
import vues.gestionnaire.VueInterractive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.swing.*;

public class Horloge implements VueInterractive, EcouteurOrdre {
    private Controleur controleur;
    private Scene scene;
    private long debut = 0;
    private ObservableList<String> data;
    private Collection<Long> lestours = new ArrayList<>();
    private Timer timer1;

    @FXML
    BorderPane borderPane;
    @FXML
    TextField horaire;
    @FXML
    TextField chrono;
    @FXML
    ListView listTime;

    public static Horloge creerVue() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Horloge.class.getResource("horloge.fxml"));
        Parent parent = fxmlLoader.load();
        Horloge res = fxmlLoader.getController();
        res.initialiserVue();
        return res;
    }

    private void initialiserVue() {
        data = FXCollections.observableArrayList();
        listTime.setItems(data);
        this.scene = new Scene(borderPane, 640, 480);
    }

    @Override
    public void setAbonnements(LanceurOdre lanceurOdre) {
        lanceurOdre.abonnement(this, Ordre.SHOW_HORLOGE);
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public void broadcast(Ordre ordre) {
        //SimpleDateFormat s = new SimpleDateFormat(*//*"dd/MM/yyyy*//*" HH:mm:ss");
        //horaire.setText(s.format(date));
        Timer timer = new Timer(1000, new ClockListener());
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        chrono.setText("0 ms");

    }

    public void tour(javafx.event.ActionEvent actionEvent) {
        if (debut != 0) {
            lestours.add(System.currentTimeMillis() - debut);
            data.clear();
            for (Long time : lestours) {
                data.add(time + " ms \n");
            }
        }
    }

    public void reset(javafx.event.ActionEvent actionEvent) {
        if (Objects.nonNull(timer1)) {
            timer1.stop();
        }
        chrono.setText("0 ms");
        data.clear();
        debut = 0;
        lestours.clear();
    }

    public void start(javafx.event.ActionEvent actionEvent) {
        if (Objects.nonNull(timer1)) {
            timer1.stop();
        }
        debut = System.currentTimeMillis();
        timer1 = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chrono.setText(System.currentTimeMillis() - debut + " ms");
            }
        });
        timer1.setRepeats(true);
        timer1.setCoalesce(true);
        timer1.setInitialDelay(0);
        timer1.start();
    }

    class ClockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            horaire.setText(DateFormat.getTimeInstance().format(new Date()));
        }
    }

    public void goToMenu() {
        controleur.goToMenu();
    }
}
