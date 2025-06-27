import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class IntervaloHorario {

    private final LocalTime horaInicio;
    private final LocalTime horaFim;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public IntervaloHorario(LocalTime horaInicio, LocalTime horaFim) {
        if (horaInicio.isAfter(horaFim)){
            throw new IllegalArgumentException("horaInicio deve ser anterior a horaFim");
        }
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    
    //usar toString para colocar no combobox do javafx
    @Override
    public String toString() {
        return horaInicio.format(FORMATTER) + " - " + horaFim.format(FORMATTER);
    }
}