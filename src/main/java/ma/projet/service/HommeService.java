package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.repository.HommeRepository;
import ma.projet.repository.MariageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HommeService implements IDao<Homme> {

    @Autowired
    private HommeRepository hommeRepository;

    @Autowired
    private MariageRepository mariageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Homme homme) {
        try {
            hommeRepository.save(homme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Homme homme) {
        try {
            hommeRepository.delete(homme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Homme homme) {
        try {
            hommeRepository.save(homme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Homme findById(int id) {
        return hommeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Homme> findAll() {
        return hommeRepository.findAll();
    }

    /**
     * Afficher les épouses d'un homme entre deux dates
     */
    public List<Femme> getEpousesEntreDeuxDates(Homme homme, Date dateDebut, Date dateFin) {
        String jpql = "SELECT m.femme FROM Mariage m WHERE m.homme.id = :hommeId " +
                     "AND m.id.dateDebut BETWEEN :dateDebut AND :dateFin";
        return entityManager.createQuery(jpql, Femme.class)
                .setParameter("hommeId", homme.getId())
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

    /**
     * Afficher les mariages d'un homme donné avec les détails (femme, dates, nombre d'enfants)
     */
    public void afficherMariagesHomme(Homme homme) {
        List<Mariage> mariages = mariageRepository.findByHommeId(homme.getId());
        if (!mariages.isEmpty()) {
            System.out.println("\n=== Mariages de " + homme.getNom() + " ===");
            for (Mariage m : mariages) {
                System.out.println("Épouse: " + m.getFemme().getPrenom() + " " + m.getFemme().getNom());
                System.out.println("Date début: " + m.getDateDebut());
                System.out.println("Date fin: " + (m.getDateFin() != null ? m.getDateFin() : "En cours"));
                System.out.println("Nombre d'enfants: " + m.getNombreEnfants());
                System.out.println("-------------------");
            }
        }
    }
}

