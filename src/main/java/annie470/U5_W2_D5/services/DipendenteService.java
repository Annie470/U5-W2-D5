package annie470.U5_W2_D5.services;

import annie470.U5_W2_D5.entities.Dipendente;
import annie470.U5_W2_D5.exceptions.BadRequestException;
import annie470.U5_W2_D5.exceptions.NotFoundException;
import annie470.U5_W2_D5.payloads.NewDipendenteDTO;
import annie470.U5_W2_D5.repositories.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;

    //GET ALL
    public Page<Dipendente> findAll(int pageN, int pageSize) {
        Pageable pageable = PageRequest.of(pageN, pageSize);
        return this.dipendenteRepository.findAll(pageable);}

    //GET SINGLE
    public  Dipendente findById(UUID id) { return this.dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Dipendente non presente in DB o id incorretto!"));}

    //POST
    public Dipendente saveDipendente(NewDipendenteDTO payload){
        if (dipendenteRepository.existsByEmail(payload.email())) {
            throw new BadRequestException("Email già in uso!");}
        Dipendente newDipendente = new Dipendente(payload.nome(), payload.cognome(), payload.username(), payload.email());
        this.dipendenteRepository.save(newDipendente);
        return newDipendente;
    }
}
