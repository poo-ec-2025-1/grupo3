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
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "aparelho_id")
    private Aparelho aparelho;
    
    @DatabaseField(dataType=DataType.STRING)
    private String dataReserva;
    
    @DatabaseField(dataType=DataType.STRING)
    private String horaInicio;
    
    @DatabaseField(dataType=DataType.STRING)
    private String horaFim;
    
    @DatabaseField(dataType=DataType.STRING, unique = true)
    private String dadosReserva;

    @DatabaseField(dataType=DataType.STRING)
    private String status;
    
    Reserva() {
        
    }
    
    public String formatadorData(Aparelho aparelho, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim) {
        DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatadorDeHora = DateTimeFormatter.ofPattern("HH:mm");
        
        String dataFormatada = dataReserva.format(formatadorDeData);
        String horaInicioFormatada = horaInicio.format(formatadorDeHora);
        String horaFimFormatada = horaFim.format(formatadorDeHora);
        
        String idAparelho = ""+aparelho.getId();

        String dadosReserva = String.format("%s | %s | %s - %s", idAparelho, dataFormatada, horaInicioFormatada, horaFimFormatada);
                
        return dadosReserva;
    }
    
    public Reserva(Usuario usuario, Aparelho aparelho, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim) {
        this.usuario = usuario;
        this.aparelho = aparelho;
        this.dataReserva = dataReserva.toString();
        this.horaInicio = horaInicio.toString();
        this.horaFim = horaFim.toString();
        this.dadosReserva = formatadorData(aparelho, dataReserva, horaInicio, horaFim);
        this.status = "PENDENTE";
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setAparelho(Aparelho aparelho) {
        this.aparelho = aparelho;
    }
    
    public Aparelho getAparelho() {
        return this.aparelho;
    }
    
    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva.toString();
    }
    
    public String getDataReserva() {
        return this.dataReserva.toString();
    }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio.toString();
    }
    
    public String getHoraInicio() {
        return this.horaInicio.toString();
    }
    
    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim.toString();
    }
    
    public String getHoraFim() {
        return this.horaFim.toString();
    }
    
    public void setDadosReserva(String dadosReserva) {
        this.dadosReserva = dadosReserva;
    }
    
    public String getDadosReserva() {
        return this.dadosReserva;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
      
    public String toString() {
        return "Numero da reserva: "+this.getId();
    }
    
    
}