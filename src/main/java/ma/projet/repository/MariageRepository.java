package ma.projet.repository;

import ma.projet.beans.Mariage;
import ma.projet.beans.MariagePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MariageRepository extends JpaRepository<Mariage, MariagePK> {

    /**
     * Trouver les mariages d'un homme
     */
    @Query("SELECT m FROM Mariage m WHERE m.homme.id = :hommeId")
    List<Mariage> findByHommeId(@Param("hommeId") int hommeId);

    /**
     * Trouver les mariages entre deux dates
     */
    @Query("SELECT m FROM Mariage m WHERE m.id.dateDebut BETWEEN :dateDebut AND :dateFin")
    List<Mariage> findMariagesEntreDeuxDates(@Param("dateDebut") Date dateDebut,
                                             @Param("dateFin") Date dateFin);
}

