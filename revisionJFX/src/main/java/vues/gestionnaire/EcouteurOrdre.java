package vues.gestionnaire;

import controleur.ordre.LanceurOdre;
import controleur.ordre.Ordre;

public interface EcouteurOrdre {
    void setAbonnements(LanceurOdre lanceurOdre);

    void broadcast(Ordre ordre);
}
