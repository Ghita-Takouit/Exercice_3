package ma.projet.beans;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class MariagePK implements Serializable {

    @Column(name = "homme_id")
    private int hommeId;

    @Column(name = "femme_id")
    private int femmeId;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut", nullable = false)
    private Date dateDebut;

    public MariagePK() {
    }

    public MariagePK(int hommeId, int femmeId, Date dateDebut) {
        this.hommeId = hommeId;
        this.femmeId = femmeId;
        this.dateDebut = dateDebut;
    }

    // Getters and Setters
    public int getHommeId() {
        return hommeId;
    }

    public void setHommeId(int hommeId) {
        this.hommeId = hommeId;
    }

    public int getFemmeId() {
        return femmeId;
    }

    public void setFemmeId(int femmeId) {
        this.femmeId = femmeId;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MariagePK mariagePK = (MariagePK) o;
        return hommeId == mariagePK.hommeId &&
               femmeId == mariagePK.femmeId &&
               Objects.equals(dateDebut, mariagePK.dateDebut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hommeId, femmeId, dateDebut);
    }
}

