package annie470.U5_W2_D5.services;

import annie470.U5_W2_D5.entities.Dipendente;
import annie470.U5_W2_D5.entities.Prenotazione;
import annie470.U5_W2_D5.entities.Viaggio;
import annie470.U5_W2_D5.exceptions.NotFoundException;
import annie470.U5_W2_D5.exceptions.ValidationException;
import annie470.U5_W2_D5.payloads.PrenotazioneDTO;
import annie470.U5_W2_D5.repositories.DipendenteRepository;
import annie470.U5_W2_D5.repositories.PrenotazioneRepository;
import annie470.U5_W2_D5.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;

    //POST
    public Prenotazione savePrenotazione(PrenotazioneDTO payload) {
        List<String> errors = new ArrayList<>();
        Viaggio viaggioTrovato = viaggioRepository.findById(payload.viaggioId()).orElse(null);
        if (viaggioTrovato == null) {
            errors.add("Viaggio non trovato o id viaggio non corretto!");
        }Dipendente dipendenteTrovato = dipendenteRepository.findById(payload.dipendenteId())
                .orElse(null);
        if (dipendenteTrovato == null) {
            errors.add("Dipendente con id " + payload.dipendenteId() + " non trovato!");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Prenotazione newPrenotazione = new Prenotazione();
        newPrenotazione.setViaggio(viaggioTrovato);
        newPrenotazione.setDipendente(dipendenteTrovato);
        newPrenotazione.setDataRichiesta(payload.dataRichiesta());
        newPrenotazione.setNote(payload.note());
        return this.prenotazioneRepository.save(newPrenotazione);
    }

    //GET ALL
    public Page<Prenotazione> findAll(int pageN, int pageSize) {
        Pageable pageable = PageRequest.of(pageN, pageSize);
        return this.prenotazioneRepository.findAll(pageable);
    }

    //GET SINGLE
    public Prenotazione findById(UUID id) {
        return this.prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non presente in DB o id incorretto!"));
    }

    //PUT
    public Prenotazione getAndUpdate(UUID id, PrenotazioneDTO payload) {
        Prenotazione found = this.findById(id);
        Viaggio viaggio = viaggioRepository.findById(payload.viaggioId())
                .orElseThrow(() -> new NotFoundException("Viaggio non presente in DB o id incorretto!"));
        Dipendente dipendente = dipendenteRepository.findById(payload.dipendenteId())
                .orElseThrow(() -> new NotFoundException("Dipendente non presente in DB o id incorretto!"));
        found.setViaggio(viaggio);
        found.setDipendente(dipendente);
        found.setDataRichiesta(payload.dataRichiesta());
        found.setNote(payload.note());
        return prenotazioneRepository.save(found);
    }

    //DELETE
    public void findAndDelete(UUID id) {
        Prenotazione found = this.findById(id);
        this.prenotazioneRepository.delete(found);
    }

}
