package lavanderia.Model;

/**
 * Servirá como uma unidade de tempo. 
 * De maneira que possibilite uma análise para calcular intervalos disponíveis/indisponíveis na Agenda.
 * 
 * @author Miguel Moreira 
 */

import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.LocalDate;

public class Intervalo 
{
    // Constantes para formatação de data e hora
    public final static String formatoDateTime = "yyyy-MM-dd HH:mm";
    public final static String formatoDate = Reserva.formatoDate;
    public final static String formatoTime = Reserva.formatoTime;

    // Atributos para representar o intervalo completo com data e hora
    protected LocalDateTime inicio;  // Data e hora de início do intervalo
    protected LocalDateTime fim;     // Data e hora de fim do intervalo

    // Atributos separados para facilitar operações apenas com data ou apenas com hora
    protected LocalDate dia;         // Dia do intervalo (quando início e fim são no mesmo dia)
    protected LocalTime horaInicio;  // Apenas a hora de início
    protected LocalTime horaFim;     // Apenas a hora de fim

    /**
     * Construtor padrão vazio
     */
    public Intervalo()
    {
        // Construtor vazio para permitir criação de objetos sem parâmetros
    }

    /**
     * Construtor principal que recebe data/hora completas
     * Útil para quando tiver diferentes marcados
     * @param inicio Data e hora de início
     * @param fim Data e hora de fim
     */
    public Intervalo(LocalDateTime inicio, LocalDateTime fim)
    {
        // Remove nanosegundos e segundos para padronizar em minutos
        // Isso garante que todos os intervalos trabalhem apenas com precisão de minutos
        inicio = inicio.minusNanos(inicio.getNano());
        inicio = inicio.minusSeconds(inicio.getSecond());
        fim = fim.minusNanos(fim.getNano());
        fim = fim.minusSeconds(fim.getSecond());
        
        // Validação: verifica se o horário final é posterior ao inicial
        boolean teste = fim.isAfter(inicio);
        if(fim.isAfter(inicio))
        {
            this.inicio = inicio;
            this.fim = fim;
        }
        else
        {
            // Lança exceção se a ordem dos horários estiver incorreta
            throw new IllegalArgumentException("Horário final é anterior ao inicial.");
        }

        // Se início e fim estão no mesmo dia, extrai informações separadas
        if( inicio.toLocalDate().equals(fim.toLocalDate()) )
        {
            this.dia = inicio.toLocalDate();           // Extrai apenas a data
            this.horaInicio = inicio.toLocalTime();    // Extrai apenas a hora de início
            this.horaFim = fim.toLocalTime();          // Extrai apenas a hora de fim
        }
    }

    /**
     * Construtor que trabalha apenas com horários (sem data específica)
     * Útil para quando não se sabe o dia ou dias nos quais esses horários vão conter.
     * Necessita de análise posterior
     * @param inicio Hora de início
     * @param fim Hora de fim
     */
    public Intervalo(LocalTime inicio, LocalTime fim)
    {
        // Padronização: remove segundos e nanosegundos
        inicio = inicio.minusNanos(inicio.getNano());
        inicio = inicio.minusSeconds(inicio.getSecond());
        fim = fim.minusNanos(fim.getNano());
        fim = fim.minusSeconds(fim.getSecond());

        // Validação da ordem dos horários
        this.horaInicio = inicio;
        this.horaFim = fim;
    }

    /**
     * Construtor que combina horários com uma data específica
     * Útil para quando o intervalo estiver contido em apenas um dia.
     * @param inicio Hora de início
     * @param fim Hora de fim
     * @param dia Data específica
     */
    public Intervalo(LocalTime inicio, LocalTime fim, LocalDate dia)
    {
        // Chama o construtor de horários e depois define o dia
        this(inicio, fim);
        if( !fim.isAfter(inicio) )
        {
            throw new IllegalArgumentException("Horário final é anterior ao inicial.");
        }
        this.dia = dia;
    }
    
    // MÉTODOS GETTERS - Retornam os valores dos atributos
    
    public LocalDateTime getInicioIntervalo() {
        return inicio;
    }
    
    public LocalDateTime getFimIntervalo() {
        return fim;
    }
    
    public LocalDate getDiaIntervalo() {
        return dia;
    }
    
    public LocalTime getHoraInicioIntervalo() {
        return horaInicio;
    }
    
    public LocalTime getHoraFimIntervalo() {
        return horaFim;
    }
}