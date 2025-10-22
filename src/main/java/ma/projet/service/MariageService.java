package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.beans.MariagePK;
import ma.projet.dao.IDao;
import ma.projet.repository.MariageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MariageService implements IDao<Mariage> {

    @Autowired
    private MariageRepository mariageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Mariage mariage) {
        try {
            mariageRepository.save(mariage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Mariage mariage) {
        try {
            mariageRepository.delete(mariage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Mariage mariage) {
        try {
            mariageRepository.save(mariage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Mariage findById(int id) {
        // Not applicable for composite key
        return null;
    }

    public Mariage findById(MariagePK id) {
        return mariageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Mariage> findAll() {
        return mariageRepository.findAll();
    }

    /**
     * Afficher le nombre d'hommes mariés à quatre femmes entre deux dates
     * Utilisation de l'API Criteria
     */
    public long getNombreHommesMariesQuatreFemmes(Date dateDebut, Date dateFin) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Homme> homme = cq.from(Homme.class);
        Join<Homme, Mariage> mariages = homme.join("mariages");

        // Créer les prédicats pour filtrer par date
        Predicate datePredicate = cb.between(
            mariages.get("id").get("dateDebut"),
            dateDebut,
            dateFin
        );

        // Grouper par homme et compter les mariages
        cq.select(cb.count(homme))
          .where(datePredicate)
          .groupBy(homme.get("id"))
          .having(cb.equal(cb.count(mariages), 4));

        List<Long> results = entityManager.createQuery(cq).getResultList();
        return results.isEmpty() ? 0 : results.size();
    }

    /**
     * Alternative: Afficher les hommes mariés à exactement quatre femmes entre deux dates
     */
    public List<Homme> getHommesMariesQuatreFemmes(Date dateDebut, Date dateFin) {
        String jpql = "SELECT m.homme FROM Mariage m " +
                     "WHERE m.id.dateDebut BETWEEN :dateDebut AND :dateFin " +
                     "GROUP BY m.homme " +
                     "HAVING COUNT(m.femme) = 4";
        return entityManager.createQuery(jpql, Homme.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }
}

