package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.repository.FemmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FemmeService implements IDao<Femme> {

    @Autowired
    private FemmeRepository femmeRepository;

    @Override
    public boolean create(Femme femme) {
        try {
            femmeRepository.save(femme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Femme femme) {
        try {
            femmeRepository.delete(femme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Femme femme) {
        try {
            femmeRepository.save(femme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Femme findById(int id) {
        return femmeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Femme> findAll() {
        return femmeRepository.findAll();
    }

    /**
     * Requête native nommée retournant le nombre d'enfants d'une femme entre deux dates
     */
    public long getNombreEnfantsEntreDeuxDates(Femme femme, Date dateDebut, Date dateFin) {
        Long result = femmeRepository.countEnfantsEntreDeuxDates(femme.getId(), dateDebut, dateFin);
        return result != null ? result : 0;
    }

    /**
     * Requête nommée retournant les femmes mariées au moins deux fois
     */
    public List<Femme> getFemmesMarieesPlusieuresFois() {
        return femmeRepository.findFemmesMarieesDeuxFoisOuPlus();
    }
}

