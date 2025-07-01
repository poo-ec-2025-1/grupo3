package lavanderia.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

public class Reserva {
    @DatabaseField(generatedId = true, dataType=DataType.INTEGER)
    private int id;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "usuario_id")
    private Usuario usuario;
    
    @DatabaseField(dataType=DataType.STRING)
    private String dataReserva;
    
    @DatabaseField(dataType=DataType.STRING)
    private String horaInicio;
    
    @DatabaseField(dataType=DataType.STRING)
    private String horaFim;
    
    @DatabaseField(dataType=DataType.STRING, unique = true)
    private String diaMaisHorarios;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Aparelho aparelho;
    
    @DatabaseField(dataType=DataType.STRING)
    private String status;
    
    Reserva() {
        
    }
    
    public String formatadorData(LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim) {
        DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatadorDeHora = DateTimeFormatter.ofPattern("HH:mm");
        
        String dataFormatada = dataReserva.format(formatadorDeData);
        String horaInicioFormatada = horaInicio.format(formatadorDeHora);
        String horaFimFormatada = horaFim.format(formatadorDeHora);

        String diaMaisHorarios = String.format("%s : %s - %s",
                dataFormatada,
                horaInicioFormatada,
                horaFimFormatada);
                
        return diaMaisHorarios;
    }
    
    public Reserva(Usuario usuario, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim, Aparelho aparelho) {
        this.usuario = usuario;
        this.dataReserva = dataReserva.toString();
        this.horaInicio = horaInicio.toString();
        this.horaFim = horaFim.toString();
        this.diaMaisHorarios = formatadorData(dataReserva, horaInicio, horaFim);
        this.aparelho = aparelho;
        this.status = "PENDENTE";
    }

    
//Start GetterSetterExtension Source Code
    
    /**SET Method propertie id*/
    public void setID(int id) {
        this.id = id;
    }
    
    /**GET Method propertie id*/
    public int getID() {
        return this.id;
    }

    /**SET Method propertie usuario*/
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**GET Method propertie usuario*/
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    /**SET Method propertie dataReserva*/
    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva.toString();
    }
    
    /**GET Method propertie dataReserva*/
    public String getDataReserva() {
        return this.dataReserva.toString();
    }
    
    /**SET Method propertie horaInicio*/
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio.toString();
    }
    
    /**GET Method propertie horaInicio*/
    public String getHoraInicio() {
        return this.horaInicio.toString();
    }
    
    /**SET Method propertie horaFim*/
    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim.toString();
    }
    
    /**GET Method propertie horaFim*/
    public String getHoraFim() {
        return this.horaFim.toString();
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
