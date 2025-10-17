package annie470.U5_W2_D5.repositories;


import annie470.U5_W2_D5.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {

    Optional<Dipendente> findById(UUID id);
}
