##

/*
 esta classe sistema será o que conecta todos os processos e os realiza
 */
import java.util.Scanner;

public class Sistema {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite sua matrícula: ");
        int matricula = scanner.nextInt();

        System.out.println("Pese sua roupa, veja o valor e digite aqui: ");
        int peso = scanner.nextInt();

        // Criar a máquina certa para o peso informado
        MaquinaLavar maquina = MaquinaLavar.criarMaquina(peso);

        // Chamar o método lavar
        maquina.lavar();

        System.out.println("Matrícula: " + matricula + " - Processo de lavagem concluído.");

        Caixa.cobrar(maquina, matricula);

        scanner.close();
    }
}

  /*
   esta classe depende diretamente das demais, as quais ainda estão em desenvolvimento
   desta forma esta classe fica assim. Será analisada e desenvolvida mais adiante 
   */




|
|
|
|
|
|




public class User{
    /* 
      a ideia nesta classe usuário é ser um banco de dados
      com a matrícula e senha de cada estudante
      assim, o sistema puxa os dados, verifica e utiliza para proceder nas operações
    */
   private int matricula;
   
   public void setMatricula(int matricula){
       this.matricula = matricula;
   }
   
   /*
    como ainda não consigo criar esse banco de dados, esta classe no momento se limitará a isto
    */
} 


|
|
|
|
|



public abstract class MaquinaLavar {
    public int pesoMax;

    public MaquinaLavar(int pesoMax) {
        this.pesoMax = pesoMax;
    }

    public abstract void lavar();
    
    public abstract double getPreco();

    // Método estático para criar a máquina correta
    public static MaquinaLavar criarMaquina(int pesoRoupa) {
        if (pesoRoupa <= 5) {
            return new MaquinaPequena();
        } else if (pesoRoupa > 5 && pesoRoupa < 10) {
            return new MaquinaMedia();
        } else {
            return new MaquinaPesada();
        }
    }
}


|
|
|
|
|
|


public class MaquinaPequena extends MaquinaLavar {

    public MaquinaPequena() {
        super(5);
    }

    @Override
    public void lavar() {
        System.out.println("Lavando com máquina PEQUENA de até " + pesoMax + "kg.");
    }
    
    @Override
    public double getPreco() {
        return 3.00;
    }
}




|
|
|
|
|



public class MaquinaMedia extends MaquinaLavar {

    public MaquinaMedia() {
        super(8);
    }

    @Override
    public void lavar() {
        System.out.println("Lavando com máquina MÉDIA de até " + pesoMax + "kg.");
    }
    
     @Override
    public double getPreco() {
        return 6.00;
    }
}



|
|
|
|
|
|



public class MaquinaPesada extends MaquinaLavar {

    public MaquinaPesada() {
        super(15);
    }

    @Override
    public void lavar() {
        System.out.println("Lavando com máquina PESADA de até " + pesoMax + "kg.");
    }
    
     @Override
    public double getPreco() {
        return 9.00;
    }
}




|
|
|
|
|
|


public class Caixa {

    public static void cobrar(MaquinaLavar maquina, int matricula) {
        double valor = maquina.getPreco();
        System.out.println("Matrícula: " + matricula);
        System.out.println("Total a pagar: R$ " + String.format("%.2f", valor));
    }
}


Sugestão de Miguel: que tal adicionar isso daqui? (Tenho que testar melhor ainda, mas pode ser interessante...)

public abstract class MaquinaLavar
{
    /**
     * Os atributos estáticos da classe fará parte do banco de dados
     */
    static int qtdMaquina = 0;
    static MaquinaLavar maquinasOfertadas[] = new MaquinaLavar[100];
    static double[] tempoOfertadoMaquinaS = new double[7];
    
    protected String idMaquina;
    protected int pesoMax;
    protected char tipoMaquina;
    protected double tempoOfertadoDaMaquina[] = Agenda.tempoDeFuncionamentoSemana;
    
    MaquinaLavar()
    {
        maquinasOfertadas[qtdMaquina] = this;
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoMaquinaS.length; i++)
        {
            tempoOfertadoMaquinaS[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
}



public class MaquinaPesada extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoOfertadoTotal = new double[7];
    
    public MaquinaPesada(String idMaquina)
    {
        this.idMaquina = idMaquina;
        pesoMax = 15;
        tipoMaquina = 'p';
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoTotal.length; i++)
        {
            tempoOfertadoTotal[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
}


public class MaquinaPequena extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoOfertadoMaquinaS = new double[7];
    
    public MaquinaPequena(String idMaquina)
    {
        this.idMaquina = idMaquina;
        pesoMax = 5;
        tipoMaquina = 'l';
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoMaquinaS.length; i++)
        {
            tempoOfertadoMaquinaS[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
}



public class MaquinaMedia extends MaquinaLavar
{
    static int qtdMaquina = 0;
    static double[] tempoOfertadoMaquinaS = new double[7];
    
    public MaquinaMedia(String idMaquina)
    {
        this.idMaquina = idMaquina;
        pesoMax = 8;
        tipoMaquina = 'm';
        qtdMaquina++;
        for (int i = 0; i < tempoOfertadoMaquinaS.length; i++)
        {
            tempoOfertadoMaquinaS[i] += Agenda.tempoDeFuncionamentoSemana[i];
        }
    }
}




