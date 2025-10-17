package annie470.U5_W2_D5.controllers;

import annie470.U5_W2_D5.entities.Prenotazione;
import annie470.U5_W2_D5.exceptions.ValidationException;
import annie470.U5_W2_D5.payloads.PrenotazioneDTO;
import annie470.U5_W2_D5.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione salvaPrenotazione(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) { throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } return this.prenotazioneService.savePrenotazione(body);
    }

    //GET ALL
    @GetMapping
    public Page<Prenotazione> getAll(@RequestParam(defaultValue = "0") int pageN, @RequestParam(defaultValue = "10") int pageSize) {
        return this.prenotazioneService.findAll(pageN, pageSize);
    }

    //GET SINGLE
    @GetMapping("/{id}")
    public Prenotazione getById(@PathVariable UUID id) {
        return this.prenotazioneService.findById(id);
    }

    //PUT
    @PutMapping("/{id}")
    public Prenotazione modifyPrenotazione(@PathVariable UUID id, @RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());}
        return this.prenotazioneService.getAndUpdate(id, body);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {this.prenotazioneService.findAndDelete(id);}
}
