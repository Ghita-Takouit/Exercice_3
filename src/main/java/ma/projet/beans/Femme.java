package ma.projet.beans;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "femme")
@NamedQueries({
    @NamedQuery(
        name = "Femme.findMarriedTwiceOrMore",
        query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
    )
})
@NamedNativeQuery(
    name = "Femme.countChildrenBetweenDates",
    query = "SELECT COUNT(*) FROM mariage WHERE femme_id = :femmeId AND date_debut BETWEEN :dateDebut AND :dateFin",
    resultClass = Long.class
)
public class Femme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance", nullable = false)
    private java.util.Date dateNaissance;

    @OneToMany(mappedBy = "femme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

