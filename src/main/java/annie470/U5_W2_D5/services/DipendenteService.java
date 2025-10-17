package annie470.U5_W2_D5.services;

import annie470.U5_W2_D5.entities.Dipendente;
import annie470.U5_W2_D5.exceptions.BadRequestException;
import annie470.U5_W2_D5.exceptions.NotFoundException;
import annie470.U5_W2_D5.exceptions.ValidationException;
import annie470.U5_W2_D5.payloads.NewDipendenteDTO;
import annie470.U5_W2_D5.repositories.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
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
        List<String> errors = new ArrayList<>();
        if (dipendenteRepository.existsByEmail(payload.email())) {
            errors.add("Email già in uso!");
        }
        if (dipendenteRepository.existsByUsername(payload.username())) {
            errors.add("Username già in uso!");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Dipendente newDipendente = new Dipendente(payload.nome(), payload.cognome(), payload.username(), payload.email());
        this.dipendenteRepository.save(newDipendente);
        return newDipendente;
    }

    //PUT
    public Dipendente getAndUpdate(UUID id, NewDipendenteDTO payload) {
        Dipendente found = this.findById(id);
        this.dipendenteRepository.findByEmail(payload.email()).ifPresent(dipendente -> {if(dipendente.getId() != id) {
            throw new BadRequestException("Email già in utilizzo da altro dipendente!");
        }});
        this.dipendenteRepository.findByUsername(payload.username()).ifPresent(dipendente -> {
            if(!dipendente.getId().equals(id)) {
                throw new BadRequestException("Username già in utilizzo da altro dipendente!");
            }
        });
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setUsername(payload.username());
        found.setEmail(payload.email());
        return dipendenteRepository.save(found);
    }

    //DELETE
    public void findAndDelete(UUID id) {
        Dipendente found = this.findById(id);
        this.dipendenteRepository.delete(found);
    }

    //PATCH AVATAR?

}
