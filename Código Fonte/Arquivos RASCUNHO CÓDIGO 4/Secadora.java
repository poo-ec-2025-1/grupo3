import java.time.LocalDateTime;

public class Secadora extends Aparelho {
    private int capacidadeKg;
    private boolean reservado;
    private double custo;
    private static Agenda agenda = new Agenda();

    public Secadora() {
        // Construtor sem argumentos necessário para ORMLite
    }

    public Secadora(int id, String modelo, String descricao, boolean agendavel, int capacidadeKg) {
        super(id, modelo, descricao, agendavel);
        this.capacidadeKg = capacidadeKg;
        this.reservado = false;
        this.custo = 8.0;
    }

    @Override
    public boolean reservar() {
        LocalDateTime agora = LocalDateTime.now();
        if (getAgendavel() && getDisponivel() && !this.reservado && agenda.reservar(this, agora)) {
            this.reservado = true;
            setDisponivel(false);
            System.out.println("Secadora " + getModelo() + " (ID: " + getId() + ") reservada com sucesso em " + agora);
            return true;
        } else {
            System.out.println("Secadora " + getModelo() + " (ID: " + getId() + ") não pode ser reservada. Estado atual: Agendável=" + getAgendavel() + ", Disponível=" + getDisponivel() + ", Reservado=" + this.reservado);
            return false;
        }
    }

    @Override
    public void liberar() {
        if (this.reservado) {
            this.reservado = false;
            agenda.liberar(this);
            setDisponivel(true);
            System.out.println("Secadora " + getModelo() + " (ID: " + getId() + ") liberada.");
        } else {
            System.out.println("Secadora " + getModelo() + " (ID: " + getId() + ") não estava reservada.");
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

    public void iniciarCicloSecagem() {
        System.out.println("Ciclo de secagem iniciado para " + getModelo() + " (ID: " + getId() + ")");
    }
}