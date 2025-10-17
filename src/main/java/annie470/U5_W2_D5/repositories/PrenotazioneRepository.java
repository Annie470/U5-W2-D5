package annie470.U5_W2_D5.repositories;

import annie470.U5_W2_D5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    Optional<Prenotazione> findById(UUID uuid);
}
