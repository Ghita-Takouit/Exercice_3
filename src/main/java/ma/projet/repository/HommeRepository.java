package ma.projet.repository;

import ma.projet.beans.Homme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HommeRepository extends JpaRepository<Homme, Integer> {
}

