package facade;

import java.util.Map;

public interface facadeBDD {
    // mise à jour forcé
    boolean miseAjourBDD();

    // renvoie les informations actuelles de la BDD(version,derniere MAJ, prochaine MAJ auto)
    Map<String, String> etatBDD();

    // pas terminé
    void ajouterSalle(String nomSalle);
}
