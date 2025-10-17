package annie470.U5_W2_D5.payloads;

import annie470.U5_W2_D5.entities.Stato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank
        String destinazione,
        @NotNull
        LocalDate date,
        @NotNull
        Stato stato) {}
