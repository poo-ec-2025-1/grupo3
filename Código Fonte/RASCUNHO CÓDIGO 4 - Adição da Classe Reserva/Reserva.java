import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@DatabaseTable(tableName = "reserva")
public class Reserva {
    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(dataType = com.j256.ormlite.field.DataType.STRING, format = "yyyy-MM-dd")
    private String dataReserva;

    @DatabaseField(dataType = com.j256.ormlite.field.DataType.STRING, format = "HH:mm")
    private String horaInicio;

    @DatabaseField(dataType = com.j256.ormlite.field.DataType.STRING, format = "HH:mm")
    private String horaFim;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Aparelho aparelho;

    @DatabaseField
    private String status;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public Reserva() {
        // Construtor sem argumentos necessário para ORMLite
    }

    public Reserva(LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim, Aparelho aparelho) {
        if (horaInicio.isAfter(horaFim)) {
            throw new IllegalArgumentException("horaInicio deve ser anterior a horaFim");
        }
        this.dataReserva = dataReserva.format(DATE_FORMATTER);
        this.horaInicio = horaInicio.format(TIME_FORMATTER);
        this.horaFim = horaFim.format(TIME_FORMATTER);
        this.aparelho = aparelho;
        this.status = "PENDENTE";
    }

    // Métodos de conversão para uso interno
    public LocalDate getDataReservaAsLocalDate() {
        return dataReserva != null ? LocalDate.parse(dataReserva, DATE_FORMATTER) : null;
    }

    public LocalTime getHoraInicioAsLocalTime() {
        return horaInicio != null ? LocalTime.parse(horaInicio, TIME_FORMATTER) : null;
    }

    public LocalTime getHoraFimAsLocalTime() {
        return horaFim != null ? LocalTime.parse(horaFim, TIME_FORMATTER) : null;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva != null ? dataReserva.format(DATE_FORMATTER) : null;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio != null ? horaInicio.format(TIME_FORMATTER) : null;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim != null ? horaFim.format(TIME_FORMATTER) : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public Aparelho getAparelho() {
        return aparelho;
    }

    public void setAparelho(Aparelho aparelho) {
        this.aparelho = aparelho;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}