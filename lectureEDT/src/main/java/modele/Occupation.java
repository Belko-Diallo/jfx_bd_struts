package modele;

import java.util.Date;

public class Occupation {
    private Date dateDebut;
    private Date dateFin;

    public Occupation(Date dateDebut, Date dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }
}
