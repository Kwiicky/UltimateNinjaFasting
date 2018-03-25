package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import acdc.ultimateninjafasting.Jour;

/**
 * Created by FP13694 on 24/03/2018.
 */

public class Programmation implements Serializable {

    Long id;
    String plageHoraire;
    List<Jour> jours = new ArrayList<>();
    Date heureDebut;
    Date heureFin;
    Boolean selection;

    // -------- GETTEUR + SETTEUR ------- //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlageHoraire() {
        return plageHoraire;
    }

    public void setPlageHoraire(String plageHoraire) {
        this.plageHoraire = plageHoraire;
    }

    public List<Jour> getJours() {
        return jours;
    }

    public void setJours(List<Jour> jours) {
        this.jours = jours;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public Boolean getSelection() {
        return selection;
    }

    public void setSelection(Boolean selection) {
        this.selection = selection;
    }
}
