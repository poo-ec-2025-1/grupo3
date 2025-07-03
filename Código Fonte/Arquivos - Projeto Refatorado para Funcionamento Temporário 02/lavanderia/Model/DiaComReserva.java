package lavanderia.Model;

/**
 * Estará presente no Database.
 * Serve para armazenar os dias com intervalos maracados e sua disponibilidade de tempo.
 * Será um produto de uma análise de dados do Database através da Agenda. 
 * Também possibilitará outras análises na Agenda.
 * Para cada ANO, DIA DO ANO E APARELHO haverá UMA instanciacao!
 * 
 * @author Miguel Moreira
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@DatabaseTable(tableName = "dia com reserva")
public class DiaComReserva {
    
    @DatabaseField(generatedId = true, dataType = DataType.INTEGER)
    int id;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    Aparelho aparelho;
    
    @DatabaseField(dataType = DataType.INTEGER)
    int ano;
    
     @DatabaseField(dataType = DataType.INTEGER)
    int diaDaSemana;
    
    @DatabaseField(dataType = DataType.INTEGER)
    int diaDoAno;
    
    @DatabaseField(dataType = DataType.DOUBLE)
    double tempoDeFuncionamento;
    
    @DatabaseField(dataType = DataType.DOUBLE)
    double tempoDisponivel;
    
    @DatabaseField(dataType = DataType.STRING)
    String data;

    public DiaComReserva()
    {
        
    }
    
    public DiaComReserva(LocalDate data)
    {
        if (data == null)
            throw new IllegalArgumentException("Data não pode ser nula.");
            
        this.data = data.format(DateTimeFormatter.ofPattern(Reserva.formatoDate));
        this.ano = data.getYear();
        this.diaDaSemana = data.getDayOfWeek().getValue() - 1;
        this.diaDoAno = data.getDayOfYear();
    }
    
    public DiaComReserva(LocalDate data, double tempoPadrao)
    {
        this(data);
        if (tempoDeFuncionamento < 0 || tempoDeFuncionamento > 24)
            throw new IllegalArgumentException("Tempo de funcionamento deve estar entre 0 e 24 horas.");

        this.tempoDeFuncionamento = tempoPadrao;
        this.tempoDisponivel = tempoPadrao;
    }
    
    public DiaComReserva(LocalDate data, Aparelho aparelho)
    {
        this(data);
        if (aparelho == null)
            throw new IllegalArgumentException("Aparelho não pode ser nulo.");

        this.aparelho = aparelho;
    }
    
    public DiaComReserva(LocalDate data, Aparelho aparelho, double tempoPadrao)
    {
        this(data, aparelho);
        if (tempoDeFuncionamento < 0 || tempoDeFuncionamento > 24)
            throw new IllegalArgumentException("Tempo de funcionamento deve estar entre 0 e 24 horas.");

        this.tempoDeFuncionamento = tempoPadrao;
        this.tempoDisponivel = tempoPadrao;
    }

    public boolean indisponibilzarTempo(double tempoIndisponivel)
    {
        
        if (tempoIndisponivel < 0)
            throw new IllegalArgumentException("Tempo indisponível deve ser positivo.");
        
        if (tempoIndisponivel > tempoDisponivel)
            throw new IllegalArgumentException("O tempo solicitado vai além do disponível.");
        
        double temp = tempoDisponivel;
        temp -= tempoIndisponivel;
        if(temp < 0)
            return false;
            
        setTempoDisponivel(temp);
        return true;
    }
    
    // Getters
    int getId() {
        return id;
    }

    public LocalDate getData()
    {
        return LocalDate.parse(data, DateTimeFormatter.ofPattern(Reserva.formatoDate));
    }
    
    Aparelho getAparelho() {
        return aparelho;
    }

    public int getAno() {
        return ano;
    }

    public int getDiaDaSemana() {
        return diaDaSemana;
    }

    public int getDiaDoAno() {
        return diaDoAno;
    }

    public double getTempoDeFuncionamento() {
        return tempoDeFuncionamento;
    }

    public double getTempoDisponivel() {
        return tempoDisponivel;
    }

    // Setters
    private void setTempoDisponivel(double tempoDisponivel) {
        if (tempoDisponivel < 0 || tempoDisponivel > tempoDeFuncionamento)
            throw new IllegalArgumentException("Tempo disponível deve estar entre 0 e o tempo de funcionamento.");
        this.tempoDisponivel = tempoDisponivel;
    }
}

