import java.time.LocalDate;
import java.time.LocalTime;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

public class Reserva {
    @DatabaseField
    private Long id;
    
    @DatabaseField
    private LocalDate dataReserva;
    
    @DatabaseField
    private LocalTime horaInicio;
    
    @DatabaseField
    private LocalTime horaFim;
    
    @DatabaseField
    private Aparelho aparelho;
    
    @DatabaseField
    private String status;
    
    Reserva() {
        
    }
    
    public Reserva(LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim, Aparelho aparelho) {
        this.dataReserva = dataReserva;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.aparelho = aparelho;
        this.status = "PENDENTE";
    }

//Start GetterSetterExtension Source Code
    
    /**SET Method propertie id*/
    public void setId(long id) {
        this.id = id;
    }
    
    /**GET Method propertie id*/
    public Long getId() {
        return this.id;
    }
    
    /**SET Method propertie dataReserva*/
    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }
    
    /**GET Method propertie dataReserva*/
    public LocalDate getDataReserva() {
        return this.dataReserva;
    }
    
    /**SET Method propertie horaInicio*/
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    /**GET Method propertie horaInicio*/
    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }
    
    /**SET Method propertie horaFim*/
    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }
    
    /**GET Method propertie horaFim*/
    public LocalTime getHoraFim() {
        return this.horaFim;
    }
    
    /**SET Method propertie aparelho*/
    public void setAparelho(Aparelho aparelho) {
        this.aparelho = aparelho;
    }
    
    /**GET Method propertie aparelho*/
    public Aparelho getAparelho() {
        return this.aparelho;
    }
    
    /**SET Method propertie status*/
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**GET Method propertie status*/
    public String getStatus() {
        return this.status;
    }
    
//End GetterSetterExtension Source Code
    
}
