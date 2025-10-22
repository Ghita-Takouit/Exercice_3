package ma.projet.beans;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mariage")
public class Mariage implements Serializable {

    @EmbeddedId
    private MariagePK id;

    @ManyToOne
    @MapsId("hommeId")
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;

    @ManyToOne
    @MapsId("femmeId")
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "nombre_enfants")
    private int nombreEnfants;

    public Mariage() {
    }

    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nombreEnfants) {
        this.id = new MariagePK(homme.getId(), femme.getId(), dateDebut);
        this.homme = homme;
        this.femme = femme;
        this.dateFin = dateFin;
        this.nombreEnfants = nombreEnfants;
    }

    // Getters and Setters
    public MariagePK getId() {
        return id;
    }

    public void setId(MariagePK id) {
        this.id = id;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public Date getDateDebut() {
        return id != null ? id.getDateDebut() : null;
    }
}

