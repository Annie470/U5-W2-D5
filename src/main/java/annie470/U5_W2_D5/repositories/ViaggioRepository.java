package annie470.U5_W2_D5.repositories;

import annie470.U5_W2_D5.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {

    Optional<Viaggio> findById(Long aLong);

}
