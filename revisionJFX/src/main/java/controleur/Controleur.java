package controleur;

import controleur.ordre.LanceurOdre;
import controleur.ordre.Ordre;
import facade.FacadeParis;
import facade.FacadeParisStaticImpl;
import facade.exceptions.InformationsSaisiesIncoherentesException;
import facade.exceptions.UtilisateurDejaConnecteException;
import javafx.application.Application;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Match;
import modele.Pari;
import modele.Utilisateur;
import vues.gestionnaire.EcouteurOrdre;
import vues.gestionnaire.GestionnaireVues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Controleur extends Application implements LanceurOdre {
    private GestionnaireVues gestionnaireVues;
    private FacadeParis facadeParis;
    private Map<Ordre, Collection<EcouteurOrdre>> lesEcouteurs;

    private Utilisateur utilisateur;
    private Collection<Pari> parisUser;
    private Collection<Match> lesMatches;

    @Override
    public void start(Stage stage) {
        this.gestionnaireVues = new GestionnaireVues(stage);
    }

    public void run() {
        goToMenu();
    }

    public Controleur(GestionnaireVues gestionnaireVues) {
        this.gestionnaireVues = gestionnaireVues;
        this.facadeParis = new FacadeParisStaticImpl();
        this.lesEcouteurs = new HashMap<>();
        for (Ordre ordre : Ordre.values()) {
            lesEcouteurs.put(ordre, new ArrayList<>());
        }
        this.gestionnaireVues.setControleur(this);
        this.gestionnaireVues.setAbonnements(this);
    }

    @Override
    public void abonnement(EcouteurOrdre ecouteurOrdre, Ordre... lesOrdres) {
        for (Ordre ordre : lesOrdres) {
            Collection<EcouteurOrdre> ecouteurOrdres = lesEcouteurs.get(ordre);
            ecouteurOrdres.add(ecouteurOrdre);
        }
    }

    @Override
    public void fireOrdre(Ordre ordre) {
        Collection<EcouteurOrdre> ecouteurOrdres = lesEcouteurs.get(ordre);
        for (EcouteurOrdre ecouteurOrdre : ecouteurOrdres) {
            ecouteurOrdre.broadcast(ordre);
        }
    }

    public void goToMenu() {
        fireOrdre(Ordre.SHOW_MENU);
    }

    public void goToConnexion() {
        fireOrdre(Ordre.SHOW_CONNEXION);
    }

    public void seConnecter(TextField id, PasswordField mdp) {
        try {
            utilisateur = facadeParis.connexion(id.getText(), mdp.getText());
            fireOrdre(Ordre.SHOW_MENU_USER);
        } catch (UtilisateurDejaConnecteException e) {
            fireOrdre(Ordre.UTILISATEUR_DEJA_CONNECTE);
        } catch (InformationsSaisiesIncoherentesException e) {
            fireOrdre(Ordre.INFORMATIONS_SAISIES_INCOHERENTES);
        }
    }

    public void goToMenuUser() {
        fireOrdre(Ordre.SHOW_MENU_USER);
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void goToDeconnexion() {
        facadeParis.deconnexion(utilisateur.getLogin());
        utilisateur = null;
        fireOrdre(Ordre.SHOW_MENU);
    }

    public Collection<Pari> getParisUser() {
        return parisUser;
    }

    public Collection<Match> getLesMatches() {
        return lesMatches;
    }

    public void showParisOuverts() {
        lesMatches = facadeParis.getMatchsPasCommences();
        fireOrdre(Ordre.SHOW_PARIS_OUVERTS);
    }

    public void showMesParis() {
        parisUser = facadeParis.getMesParis(utilisateur.getLogin());
        fireOrdre(Ordre.SHOW_MES_PARIS);
    }

    public void parier(String idMatch) {
        System.out.println(idMatch);
        fireOrdre(Ordre.SHOW_MENU);
    }

    public void goToHorloge() {
        fireOrdre(Ordre.SHOW_HORLOGE);
    }
}
