package ma.projet.repository;

import ma.projet.beans.Femme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FemmeRepository extends JpaRepository<Femme, Integer> {

    /**
     * Requête nommée: Femmes mariées au moins deux fois
     */
    @Query("SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2")
    List<Femme> findFemmesMarieesDeuxFoisOuPlus();

    /**
     * Requête native: Nombre d'enfants d'une femme entre deux dates
     */
    @Query(value = "SELECT COALESCE(SUM(nombre_enfants), 0) FROM mariage " +
                   "WHERE femme_id = :femmeId AND date_debut BETWEEN :dateDebut AND :dateFin",
           nativeQuery = true)
    Long countEnfantsEntreDeuxDates(@Param("femmeId") int femmeId,
                                    @Param("dateDebut") Date dateDebut,
                                    @Param("dateFin") Date dateFin);
}

