import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.time.DayOfWeek;

/**
 * Fará todas as checagens para garantir as reservas.
 * Focado a um servico com uma implementacao abstrata e facilitada para o cliente.
 * Necessita dos repositórios para funcionar.
 * 
 * @author Miguel Moreira 
 */

public class Agenda
{
    
    private static AparelhoRepository R_Aparelho;
    private static UsuarioRepository R_Usuario;
    private static ReservaRepository R_Reserva;
    private static RepositoriosParaAgenda.IntervaloDeUsoRepository R_Intervalo;
    private static RepositoriosParaAgenda.DiaDaReservaRepository R_Dia;
    
    
    private static double tempoDeFuncionamentoSemana[] = {8.0, 8.0, 8.0, 8.0, 8.0, 8.0, 8.0};
    static final String[] diasSemana = {"segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo"};
    
    
    
    public static Database testD = new Database("testD");
    private static Aparelho teste = new Aparelho("Eletroluz", "Gostosa", 10, true, 8000.0);
    private static LocalDateTime F_Teste = LocalDateTime.of(2025, 6, 30, 14, 30);
    private static LocalDateTime I_Teste = LocalDateTime.of(2025, 6, 30, 6, 17);
    public IntervaloReservavel horarioTeste = new IntervaloReservavel(I_Teste, F_Teste);
    public DiaReservavel diaTeste = new DiaReservavel(I_Teste, teste);
    
    class DiaReservavel extends DiaDaReserva
    {
        DiaReservavel(LocalDateTime tempo, Aparelho aparelho)
        {
            super();
            ano = tempo.getYear();
            diaDoAno = tempo.getDayOfYear();
            diaDaSemana = tempo.getDayOfWeek().getValue() - 1;
            tempoDeFuncionamento = tempoDeFuncionamentoSemana[diaDaSemana];
            tempoDisponivel = tempoDeFuncionamento;
            this.aparelho = aparelho;
            if (diaDoAno == 0)
                throw new IllegalArgumentException("Dia do ano não pode ser zero.");

            if (diaDaSemana < 0 || diaDaSemana > 6)
                throw new IllegalArgumentException("Dia da semana deve estar entre 0 (domingo) e 6 (sábado).");

            if (tempoDeFuncionamento < 0 || tempoDeFuncionamento > 24)
                throw new IllegalArgumentException("Tempo de funcionamento deve estar entre 0 e 24 horas.");
                
            if (aparelho == null)
                    throw new IllegalArgumentException("Aparelho não pode ser nulo.");
                    
        }
        
        Integer jaEstaReservado()
        {
            List<DiaDaReserva> dias = R_Dia.loadAll();
            if (dias == null || dias.isEmpty())
            {
                return null;
            }
            
            for(DiaDaReserva d : dias)
            {
                if(ano == d.getAno() && diaDoAno == d.getDiaDoAno() && aparelho.getId() == d.getAparelho().getId())
                {
                    return d.getId();
                }
            }
            
            return null;
        }
        
        DiaDaReserva terDiaParaReserva()
        {            
            Integer reposta = jaEstaReservado();
            
            if (reposta == null)
            {
                R_Dia.create(this);
                return this;
            }
            
            return R_Dia.loadFromId(reposta.intValue());
        }
    }
    
    class IntervaloReservavel extends IntervaloDeUso
    {
        /*
         * O intervalo de uso mas com métodos que posibilite sua reseva.
         * 
         */
        private LocalDateTime inicio;
        private LocalDateTime fim;
        
        IntervaloReservavel(LocalDateTime inicio, LocalDateTime fim)
        {
           super(inicio, fim);
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Intervalo.formatoData);
           this.inicio = LocalDateTime.parse(super.inicio, formatter);
           this.fim = LocalDateTime.parse(super.fim, formatter);
        }
        
        List<DiaDaReserva> agregarMaquinasPeloDia(int diaDoAno)
        {
            List<DiaDaReserva> maquinasUteis = new ArrayList();
            if(fim.getDayOfYear() != inicio.getDayOfYear())
            {
                return null;
            }
            
            List<DiaDaReserva> dias =  R_Dia.loadAll();
            if (dias == null || dias.isEmpty())
                return null;
            
            for(DiaDaReserva d : dias)
            {
                if( d.getDiaDoAno() == diaDoAno && d.tempoDeFuncionamento > 0 )
                {
                    if( d.tempoDisponivel > 0 ) 
                    {
                        maquinasUteis.add(d);
                    }
                }
            }
            
            return maquinasUteis;
        }
       
       public Reserva fazerReserva(Usuario user, Aparelho aparelho, LocalDateTime inicio, LocalDateTime fim)
       {
           IntervaloReservavel intervalo = new IntervaloReservavel(inicio, fim);
           
           DiaReservavel dia = new DiaReservavel(inicio, aparelho);
           DiaDaReserva diaParaReserva = dia.terDiaParaReserva();
           
           IntervaloDeUso intervaloDaReserva = new IntervaloDeUso(aparelho, inicio, fim);
           R_Intervalo.create(intervaloDaReserva);
           
           Reserva novaReserva = new Reserva(user, intervaloDaReserva);
           R_Reserva.create(novaReserva);
           return novaReserva;
       }
       
       public LocalDateTime getInicioReservavel()
       {
           return this.inicio;
       }
       
       public LocalDateTime getFimReservavel()
       {
           return this.fim;
       }
    }
    
    Agenda(Database database)
    {
        R_Aparelho = new AparelhoRepository(database);
        R_Aparelho.create(teste);
        R_Usuario = new UsuarioRepository(database);
        R_Reserva = new ReservaRepository(database);
        R_Intervalo = new RepositoriosParaAgenda.IntervaloDeUsoRepository(database);
        R_Dia = new RepositoriosParaAgenda.DiaDaReservaRepository(database);
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
    
    public double[] getTempoDeFuncionamentoSemana()
    {
        return tempoDeFuncionamentoSemana.clone();
    }
}
