package ma.projet.beans;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "homme")
public class Homme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance", nullable = false)
    private java.util.Date dateNaissance;

    @OneToMany(mappedBy = "homme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Homme() {
    }

    public Homme(String nom, String telephone, String adresse, java.util.Date dateNaissance) {
        this.nom = nom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public java.util.Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(java.util.Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}

