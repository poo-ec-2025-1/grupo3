package lavanderia.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    
    private static double tempoDeFuncionamentoSemana[] = {8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0};
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
       
       Integer jaEstaReservado()
        {
            List<IntervaloDeUso> intervalos = R_Intervalo.loadAll();
            if (intervalos == null || intervalos.isEmpty())
            {
                return null;
            }
            
            for(IntervaloDeUso i : intervalos)
            {
                if(this.getDiaIntervalo() == i.getDiaIntervalo() && this.getHoraInicioIntervalo() == i.getHoraInicioIntervalo() && this.getHoraFimIntervalo() == i.getHoraFimIntervalo() && this.aparelho.getId() == i.getAparelho().getId())
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
            
            return R_Intervalo.loadFromId(reposta.intValue());
        }
              
       private Reserva fazerReserva(Usuario user, Aparelho aparelho)
       {           
           DiaReservavel temp = new DiaReservavel(this.getDiaIntervalo(), aparelho);
           DiaComReserva diaParaReserva = temp.terDiaParaReserva();
           
           
           Duration duracao = Duration.between( this.getHoraInicioIntervalo(), this.getHoraFimIntervalo() );
           double indisponibilidade = duracao.toMinutes() / 60.0;
           if(!diaParaReserva.indisponibilzarTempo(indisponibilidade))
               return null;
           
           R_Dia.update(diaParaReserva);
           
           IntervaloDeUso intervaloParaReserva = this.terIntervaloParaReserva();
           
           Reserva novaReserva = new Reserva( user, aparelho, this.getDiaIntervalo(), this.getHoraInicioIntervalo(), this.getHoraFimIntervalo() );
           R_Reserva.create(novaReserva);   
           return novaReserva;
       }
    }
    
    Agenda(Database database)
    {
        R_Aparelho = new AparelhoRepository(database);
        //R_Aparelho.create(teste);
        R_Usuario = new UsuarioRepository(database);
        R_Reserva = new ReservaRepository(database);
        R_Dia = new DiaComReservaRepository(database);
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
    
    public Reserva fazerReserva(Usuario user, Aparelho aparelho,LocalDate dia, LocalTime inicio, LocalTime fim)
    {
        Agenda.IntervaloReservavel interval = new IntervaloReservavel(inicio, fim, dia);
        return interval.fazerReserva(user, aparelho);
    }
    
    public List<Aparelho> agregarMaquinas(LocalTime inicio, LocalTime fim, LocalDate dia)
    {
        DiaReservavel diaAnalise = new DiaReservavel(dia);
        if ( diaAnalise.getTempoDeFuncionamento() <= 0 )
        {
            return null;
        }
        
        List<Aparelho> maquinasUteis = R_Aparelho.loadAll();
        
        for (Aparelho m: maquinasUteis)
        {
            diaAnalise.setAparelho(m);
            Integer reposta = diaAnalise.jaEstaReservado();
            if(reposta != null)
            {
                if ( diaAnalise.getTempoDisponivel() <= 0 )
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

