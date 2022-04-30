package controleur.ordre;

import vues.gestionnaire.EcouteurOrdre;

public interface LanceurOdre {
    void abonnement(EcouteurOrdre ecouteurOrdre, Ordre... lesOrdres);

    void fireOrdre(Ordre ordre);
}
