package annie470.U5_W2_D5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(name = "data_richiesta", nullable = false)
    private LocalDate dataRichiesta;
    @Column(nullable = true)
    private String note;

    @ManyToOne
    @JoinColumn(name = "viaggio_id", nullable = false)
    private Viaggio viaggio;
    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = false)
    private Dipendente dipendente;


    public Prenotazione(LocalDate dataRichiesta, String note, Viaggio viaggio, Dipendente dipendente) {
        this.dataRichiesta = dataRichiesta;
        this.note = note;
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }
}
