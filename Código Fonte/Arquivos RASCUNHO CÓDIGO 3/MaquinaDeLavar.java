import java.time.LocalDateTime;

public class MaquinaDeLavar extends Aparelho {
    private int capacidadeKg;
    private boolean reservado;
    private double custo;
    private static Agenda agenda = new Agenda();

    public MaquinaDeLavar() {
        // Construtor sem argumentos necessário para ORMLite
    }

    public MaquinaDeLavar(int id, String modelo, String descricao, boolean agendavel, int capacidadeKg) {
        super(id, modelo, descricao, agendavel);
        this.capacidadeKg = capacidadeKg;
        this.reservado = false;
        this.custo = 12.0;
    }

    @Override
    public boolean reservar() {
        LocalDateTime agora = LocalDateTime.now();
        if (getAgendavel() && getDisponivel() && !this.reservado && agenda.reservar(this, agora)) {
            this.reservado = true;
            setDisponivel(false);
            System.out.println("Máquina de lavar " + getModelo() + " (ID: " + getId() + ") reservada com sucesso em " + agora);
            return true;
        } else {
            System.out.println("Máquina de lavar " + getModelo() + " (ID: " + getId() + ") não pode ser reservada. Estado atual: Agendável=" + getAgendavel() + ", Disponível=" + getDisponivel() + ", Reservado=" + this.reservado);
            return false;
        }
    }

    @Override
    public void liberar() {
        if (this.reservado) {
            this.reservado = false;
            agenda.liberar(this);
            setDisponivel(true);
            System.out.println("Máquina de lavar " + getModelo() + " (ID: " + getId() + ") liberada.");
        } else {
            System.out.println("Máquina de lavar " + getModelo() + " (ID: " + getId() + ") não estava reservada.");
        }
    }

    @Override
    public boolean estaReservado() {
        return this.reservado;
    }

    public int getCapacidadeKg() {
        return capacidadeKg;
    }

    public void setCapacidadeKg(int capacidadeKg) {
        this.capacidadeKg = capacidadeKg;
    }

    @Override
    public double getCusto() {
        return this.custo;
    }

    @Override
    public void setCusto(double custo) {
        this.custo = custo;
    }

    public void iniciarCicloLavagem() {
        System.out.println("Ciclo de lavagem iniciado para " + getModelo() + " (ID: " + getId() + ")");
    }
}