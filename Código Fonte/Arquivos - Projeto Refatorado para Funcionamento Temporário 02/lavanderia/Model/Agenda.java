package lavanderia.Model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Fará todas as checagens para garantir as reservas.
 * Focado a um servico com uma implementacao abstrata e facilitada para o cliente.
 * Necessita dos repositórios para funcionar.
 * 
 * @author Miguel Moreira 
 */

public class Agenda
{
    
    private AparelhoRepository R_Aparelho;
    private UsuarioRepository R_Usuario;
    private ReservaRepository R_Reserva;
    private DiaComReservaRepository R_Dia;
    private IntervaloDeUsoRepository R_Intervalo;
    
    
    private static double tempoDeFuncionamentoSemana[] = {24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0};
    static final String[] diasSemana = {"segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo"};
    
    
    
    public static Database testD = new Database("test_Agenda");
    //private static Aparelho teste = new Aparelho("Eletroluz", "Gostosa", 10, true, 8000.0);
    private static LocalDateTime F_Teste = LocalDateTime.of(2025, 6, 30, 14, 30);
    private static LocalDateTime I_Teste = LocalDateTime.of(2025, 6, 30, 6, 17);
    //public IntervaloReservavel horarioTeste = new IntervaloReservavel(I_Teste, F_Teste);
    //public DiaReservavel diaTeste = new DiaReservavel(I_Teste.toLocalDate(), teste);
    
    public class DiaReservavel extends DiaComReserva
    {
        
        public DiaReservavel(LocalDate tempo, Aparelho aparelho)
        {
            super(tempo, aparelho, tempoDeFuncionamentoSemana[tempo.getDayOfWeek().getValue() - 1]);
            
        }
        
        public DiaReservavel(LocalDate tempo)
        {
            super(tempo, tempoDeFuncionamentoSemana[tempo.getDayOfWeek().getValue() - 1]);
            
        }
        
        Integer jaEstaReservado()
        {
            List<DiaComReserva> dias = R_Dia.loadAll();
            if (dias == null || dias.isEmpty())
            {
                return null;
            }
            
            for(DiaComReserva d : dias)
            {
                if(ano == d.getAno() && diaDoAno == d.getDiaDoAno() && aparelho.getId() == d.getAparelho().getId())
                {
                    return d.getId();
                }
            }
            
            return null;
        }
        
        private DiaComReserva terDiaParaReserva()
        {            
            Integer reposta = jaEstaReservado();
            
            if (reposta == null)
            {
                R_Dia.create(this);
                return this;
            }
            
            return R_Dia.loadFromId(reposta.intValue());
        }
        
        public void setAparelho(Aparelho aparelho)
        {
            this.aparelho = aparelho;
        }
    }
    
    private class IntervaloReservavel extends IntervaloDeUso
    {
        /*
         * O intervalo de uso mas com métodos que posibilite sua reseva.
         * 
         */
        
       public IntervaloReservavel(LocalTime inicio, LocalTime fim, LocalDate dia)
       {
           super(inicio, fim, dia);
       }
       
       public IntervaloReservavel(LocalTime inicio, LocalTime fim, LocalDate dia, Aparelho aparelho)
       {
           super(inicio, fim, dia, aparelho);
       }
       
       Integer jaEstaReservado()
       {
            List<IntervaloDeUso> intervalos = R_Intervalo.loadAll();
            if (intervalos == null || intervalos.isEmpty())
            {
                return null;
            }
            
            for(IntervaloDeUso i : intervalos)
            {
                if( this.getDiaIntervalo().equals(i.getDiaIntervalo())
                  && this.getHoraInicioIntervalo().equals(i.getHoraInicioIntervalo())
                  && this.getHoraFimIntervalo().equals(i.getHoraFimIntervalo())
                  && this.aparelho.getId() == i.getAparelho().getId() )
                {
                    return i.getId();
                }
            }
            
            return null;
        }
        
        private IntervaloDeUso terIntervaloParaReserva()
        {            
            Integer reposta = jaEstaReservado();
            
            if (reposta == null)
            {
                R_Intervalo.create(this);
                return this;
            }
            
            return null;
        }
              
       private Reserva fazerReserva(Usuario user, Aparelho aparelho) throws SQLException
       {   
           DiaReservavel temp = new DiaReservavel(this.getDiaIntervalo(), aparelho);
           DiaComReserva diaParaReserva = temp.terDiaParaReserva();
           
           Duration duracao = Duration.between( this.getHoraInicioIntervalo(), this.getHoraFimIntervalo() );
           double indisponibilidade = duracao.toMinutes() / 60.0;
           try
           {
               diaParaReserva.indisponibilzarTempo(indisponibilidade);
           }
           catch(SQLException e)
           {
               if(Math.abs(diaParaReserva.getTempoDisponivel() - tempoDeFuncionamentoSemana[diaParaReserva.getDiaDaSemana()]) < 0.001)
                   R_Dia.delete(diaParaReserva);
                
                throw e;
           }
           
           
           this.aparelho = aparelho;
           if( this.terIntervaloParaReserva() == null )
               throw new SQLException("Intervalo indisponível.");
           
           
           Reserva novaReserva = new Reserva( user, aparelho, this.getDiaIntervalo(), this.getHoraInicioIntervalo(), this.getHoraFimIntervalo() );
           R_Reserva.create(novaReserva);
                    
           R_Dia.update(diaParaReserva);
           
           return novaReserva;
       }
       
       public void setAparelho(Aparelho aparelho)
       {
           this.aparelho = aparelho;
       }
    }
    
    public Agenda(Database database)
    {
        R_Aparelho = new AparelhoRepository(database);
        //R_Aparelho.create(teste);
        R_Usuario = new UsuarioRepository(database);
        R_Reserva = new ReservaRepository(database);
        R_Dia = new DiaComReservaRepository(database);
        R_Intervalo = new IntervaloDeUsoRepository(database);
    }
    
    /*
     
    
    Agenda()
    {
        for (int i = 0; i < tempoDeFuncionamentoSemana.length; i++)
        {
            tempoDeFuncionamentoSemana[i] = 8.0;
        }
    }
    
    Agenda(double tempo) throws Exception
    {
        if (tempo < 0.0 || tempo > 24.0) {
            throw new Exception("Tempo padrão inválido: " + tempo + "h. Deve estar entre 0.0 e 24.0 horas.");
        }
    
        tempoDeFuncionamentoSemana = new double[7];
        for (int i = 0; i < tempoDeFuncionamentoSemana.length; i++) {
            tempoDeFuncionamentoSemana[i] = tempo;
        }
    }
    
    Agenda(double dom, double seg, double ter, double qua, double qui, double sex, double sab) throws Exception
    {
        double[] novosTempos = {dom, seg, ter, qua, qui, sex, sab};
        StringBuilder erros = new StringBuilder();
        
        for (int i = 0; i < novosTempos.length; i++)
        {
            if (novosTempos[i] < 0.0 || novosTempos[i] > 24.0) {
            erros.append(" - ")
                 .append(diasSemana[i])
                 .append(": ")
                 .append(novosTempos[i])
                 .append(" horas (deve estar entre 0.0 e 24.0)\n");
        }
    }

    if (erros.length() > 0) {
        throw new Exception("Foram encontrados os seguintes erros nos tempos de funcionamento:\n" + erros.toString());
    }

    tempoDeFuncionamentoSemana = novosTempos.clone();
    }
    */
   
    /*public Reserva reservar(Usuario usuario, Aparelho aparelho, LocalDate data, IntervaloHorario horario)
    {
        DiaReservavel
    }*/
    
    public Reserva fazerReserva(Usuario user, Aparelho aparelho,LocalDate dia, LocalTime inicio, LocalTime fim) throws SQLException
    {
        Agenda.IntervaloReservavel interval = new IntervaloReservavel(inicio, fim, dia, aparelho);
        return interval.fazerReserva(user, aparelho);
    }
    
    public void deletarReserva(Reserva reservaDeletada) throws SQLException, IllegalArgumentException {
        // Validação de entrada
        if (reservaDeletada == null) {
            throw new IllegalArgumentException("A reserva não pode ser nula");
        }
        
        try {
            // Parsing das datas com tratamento de erro específico
            DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern(Reserva.formatoDate);
            DateTimeFormatter formatadorDeHora = DateTimeFormatter.ofPattern(Reserva.formatoTime);
            
            LocalDate dia;
            LocalTime inicio;
            LocalTime fim;
            
            try {
                dia = LocalDate.parse(reservaDeletada.getDataReserva(), formatadorDeData);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    String.format("Data da reserva inválida: '%s'. Formato esperado: %s", 
                        reservaDeletada.getDataReserva(), Reserva.formatoDate), e);
            }
            
            try {
                inicio = LocalTime.parse(reservaDeletada.getHoraInicio(), formatadorDeHora);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    String.format("Hora de início inválida: '%s'. Formato esperado: %s", 
                        reservaDeletada.getHoraInicio(), Reserva.formatoTime), e);
            }
            
            try {
                fim = LocalTime.parse(reservaDeletada.getHoraFim(), formatadorDeHora);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    String.format("Hora de fim inválida: '%s'. Formato esperado: %s", 
                        reservaDeletada.getHoraFim(), Reserva.formatoTime), e);
            }
            
            // Processamento do intervalo reservável
            IntervaloReservavel interval = new IntervaloReservavel(inicio, fim, dia, reservaDeletada.getAparelho());
            Integer resposta = interval.jaEstaReservado();
            if (resposta == null) {
                throw new IllegalStateException(
                    String.format("Intervalo com ID %d não encontrado no banco de dados", resposta));
            }
            
            IntervaloDeUso intervaloDeletado = R_Intervalo.loadFromId(resposta);
            R_Intervalo.delete(intervaloDeletado);
            
            // Processamento do dia reservável
            DiaReservavel temp = new DiaReservavel(dia, reservaDeletada.getAparelho());
            resposta = temp.jaEstaReservado();
            if (resposta == null) {
                throw new IllegalStateException(
                    String.format("Dia com ID %d não encontrado no banco de dados", resposta));
            }
            
            DiaComReserva diaTemp = R_Dia.loadFromId(resposta);
            Duration duracao = Duration.between(inicio, fim);
            double disponibilidade = duracao.toMinutes() / 60.0;
            
            if (diaTemp.disponibilizarTempo(disponibilidade)) {
                R_Dia.update(diaTemp);
            } else {
                R_Dia.delete(diaTemp);
            }
            
            // Deletar a reserva principal
            R_Reserva.delete(reservaDeletada);
    
        } catch (SQLException | IllegalArgumentException e) {
            // Log e relança para ser tratado no controller
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // Encapsula qualquer outra exceção
            e.printStackTrace();
            throw new SQLException("Erro inesperado ao deletar reserva: " + e.getMessage(), e);
        }
    }

    
    public List<Aparelho> agregarMaquinas(LocalTime inicio, LocalTime fim, LocalDate dia) throws SQLException
    {
        DiaReservavel diaAnalise = new DiaReservavel(dia);
        if ( diaAnalise.getTempoDeFuncionamento() <= 0 )
        {
            return null;
        }
        
        List<Aparelho> maquinas = R_Aparelho.loadAll();
        List<Aparelho> maquinasUteis = new ArrayList<>(maquinas);
        
        for (Aparelho m: maquinas)
        {
            diaAnalise.setAparelho(m);
            Integer reposta = diaAnalise.jaEstaReservado();
            if(reposta != null)
            {
                DiaComReserva diaReal = R_Dia.loadFromId(reposta);
                if ( diaReal.getTempoDisponivel() <= 0 )
                    maquinasUteis.remove(m);
            }
            
        }
        
        IntervaloReservavel intervalAnalise = new IntervaloReservavel(inicio, fim, dia);       
        for (Aparelho m: maquinas)
        {
            intervalAnalise.setAparelho(m);
            Integer reposta = intervalAnalise.jaEstaReservado();
            if(reposta != null)
            {
                maquinasUteis.remove(m);
            }
            
        }
        
        return maquinasUteis;
    }
    
    
    public double[] getTempoDeFuncionamentoSemana()
    {
        return tempoDeFuncionamentoSemana.clone();
    }
}

