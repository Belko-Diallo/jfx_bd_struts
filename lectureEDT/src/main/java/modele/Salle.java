package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Salle {
    private String nomSalle;
    private Collection<Occupation> lesOccupations;

    public Salle(String nomSalle) {
        this.nomSalle = nomSalle;
        this.lesOccupations = new ArrayList<>();
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public Collection<Occupation> getLesOccupations() {
        return lesOccupations;
    }

    public void addOccupation(Date debut, Date fin) {
        this.lesOccupations.add(new Occupation(debut, fin));
    }
}
